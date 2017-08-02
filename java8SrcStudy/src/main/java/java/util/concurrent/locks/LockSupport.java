/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.locks;
import sun.misc.Unsafe;

/**
 * Basic thread blocking primitives for creating locks and other
 * synchronization classes.
 *
 * <p>This class associates, with each thread that uses it, a permit
 * (in the sense of the {@link java.util.concurrent.Semaphore
 * Semaphore} class). A call to {@code park} will return immediately
 * if the permit is available, consuming it in the process; otherwise
 * it <em>may</em> block.  A call to {@code unpark} makes the permit
 * available, if it was not already available. (Unlike with Semaphores
 * though, permits do not accumulate. There is at most one.)
 *
 * <p>Methods {@code park} and {@code unpark} provide efficient
 * means of blocking and unblocking threads that do not encounter the
 * problems that cause the deprecated methods {@code Thread.suspend}
 * and {@code Thread.resume} to be unusable for such purposes: Races
 * between one thread invoking {@code park} and another thread trying
 * to {@code unpark} it will preserve liveness, due to the
 * permit. Additionally, {@code park} will return if the caller's
 * thread was interrupted, and timeout versions are supported. The
 * {@code park} method may also return at any other time, for "no
 * reason", so in general must be invoked within a loop that rechecks
 * conditions upon return. In this sense {@code park} serves as an
 * optimization of a "busy wait" that does not waste as much time
 * spinning, but must be paired with an {@code unpark} to be
 * effective.
 *
 * <p>The three forms of {@code park} each also support a
 * {@code blocker} object parameter. This object is recorded while
 * the thread is blocked to permit monitoring and diagnostic tools to
 * identify the reasons that threads are blocked. (Such tools may
 * access blockers using method {@link #getBlocker(Thread)}.)
 * The use of these forms rather than the original forms without this
 * parameter is strongly encouraged. The normal argument to supply as
 * a {@code blocker} within a lock implementation is {@code this}.
 *
 * <p>These methods are designed to be used as tools for creating
 * higher-level synchronization utilities, and are not in themselves
 * useful for most concurrency control applications.  The {@code park}
 * method is designed for use only in constructions of the form:
 *
 *  <pre> {@code
 * while (!canProceed()) { ... LockSupport.park(this); }}</pre>
 *
 * where neither {@code canProceed} nor any other actions prior to the
 * call to {@code park} entail locking or blocking.  Because only one
 * permit is associated with each thread, any intermediary uses of
 * {@code park} could interfere with its intended effects.
 *
 * <p><b>Sample Usage.</b> Here is a sketch of a first-in-first-out
 * non-reentrant lock class:
 *  <pre> {@code
 * class FIFOMutex {
 *   private final AtomicBoolean locked = new AtomicBoolean(false);
 *   private final Queue<Thread> waiters
 *     = new ConcurrentLinkedQueue<Thread>();
 *
 *   public void lock() {
 *     boolean wasInterrupted = false;
 *     Thread current = Thread.currentThread();
 *     waiters.add(current);
 *
 *     // Block while not first in queue or cannot acquire lock
 *     while (waiters.peek() != current ||
 *            !locked.compareAndSet(false, true)) {
 *       LockSupport.park(this);
 *       if (Thread.interrupted()) // ignore interrupts while waiting
 *         wasInterrupted = true;
 *     }
 *
 *     waiters.remove();
 *     if (wasInterrupted)          // reassert interrupt status on exit
 *       current.interrupt();
 *   }
 *
 *   public void unlock() {
 *     locked.set(false);
 *     LockSupport.unpark(waiters.peek());
 *   }
 * }}</pre>
 */
public class LockSupport {
    private LockSupport() {} // Cannot be instantiated.
    //私有方法,防止被乱用
    private static void setBlocker(Thread t, Object arg) {
        // Even though volatile, hotspot doesn't need a write barrier here.
        UNSAFE.putObject(t, parkBlockerOffset, arg);
    }

    /**
     * Makes available the permit for the given thread, if it
     * was not already available. If the thread was blocked on
     * {@code park} then it will unblock. Otherwise, its next call
     * to {@code park} is guaranteed not to block.(如果线程被阻塞,将会释放,
     * 否则下一次调用park将保证不会被阻塞) This operation is not guaranteed to
     * have any effect at all if the given thread has not been started.
     * (如果线程没有被启动,不保证没有任何影响)
     *
     * @param thread the thread to unpark, or {@code null}, in which case
     *        this operation has no effect
     */
    public static void unpark(Thread thread) {
        if (thread != null)
            //unpark：当指定线程被park命令阻塞时unpark命令可以恢复阻塞。
            // 在park命令没有被先调用过的时候，调用unpark，线程仍然不被阻塞
            UNSAFE.unpark(thread);
    }

    /**
     * Disables the current thread for thread scheduling purposes unless the
     * permit is available.
     *
     * <p>If the permit is available then it is consumed and the call returns
     * immediately; otherwise
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant(潜伏的,休眠的) until one of three things happens:
     *
     * <ul>其他线程unpark当前线程
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     * 其他线程interrupt当前线程
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * 本次调用伪造返回
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread upon return.
     * (调用者要首先检查引起本线程park的条件,同时也需要在返回前决定终端状态)
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @since 1.6
     */
    public static void park(Object blocker) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        // 阻塞当前线程:除非
        // (1)当配对的unpark发生
        // (2)配对的unpark已经发生或者线程被中断时恢复（unpark先行，再执行park）
        // (3)当absolute是false时，如果给定的时间是非0（负数）或者给定的时间
        // （正数, 时间单位时毫秒）已经过去了(0的时候会一直阻塞着)。
        // (4)当Absolute是true时，如果给定的时间（时间单位是纳秒）
        // 过去了或者伪造的（在我理解是参数不合法时）线程会恢复中断。
        // 这个操作是不安全的，所以在其他调用会很奇怪
        UNSAFE.park(false, 0L);
        setBlocker(t, null);
    }

    /**
     * Disables the current thread for thread scheduling purposes, for up to
     * the specified waiting time, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * 等待时间结束
     * <li>The specified waiting time elapses; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the elapsed time
     * upon return.
     *
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @param nanos the maximum number of nanoseconds to wait
     * @since 1.6
     */
    public static void parkNanos(Object blocker, long nanos) {
        if (nanos > 0) {
            Thread t = Thread.currentThread();
            setBlocker(t, blocker);
            UNSAFE.park(false, nanos);
            setBlocker(t, null);
        }
    }

    /**
     * Disables the current thread for thread scheduling purposes, until
     * the specified deadline, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread; or
     * 到达截止时间
     * <li>The specified deadline passes; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the current time
     * upon return.
     *
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @param deadline the absolute time, in milliseconds from the Epoch,
     *        to wait until
     * @since 1.6
     */
    public static void parkUntil(Object blocker, long deadline) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        UNSAFE.park(true, deadline);
        setBlocker(t, null);
    }

    /**
     * Returns the blocker object supplied to the most recent
     * invocation of a park method that has not yet unblocked, or null
     * if not blocked. (未阻塞返回null) The value returned is just a momentary
     * snapshot -- the thread may have since unblocked or blocked on a
     * different blocker object.
     * 为什么要用偏移量来获取对象?
     * 这个parkBlocker就是在线程处于阻塞的情况下才会被赋值。线程都已经阻塞了，
     * 如果不通过这种内存的方法，而是直接调用线程内的方法，线程是不会回应调用的。
     * @param t the thread
     * @return the blocker
     * @throws NullPointerException if argument is null
     * @since 1.6
     */
    public static Object getBlocker(Thread t) {
        if (t == null)
            throw new NullPointerException();
        return UNSAFE.getObjectVolatile(t, parkBlockerOffset);
    }

    /**
     * Disables the current thread for thread scheduling purposes unless the
     * permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of three
     * things happens:
     *
     * <ul>
     *
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread upon return.
     */
    public static void park() {
        UNSAFE.park(false, 0L);
    }

    /**
     * Disables the current thread for thread scheduling purposes, for up to
     * the specified waiting time, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified waiting time elapses; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the elapsed time
     * upon return.
     *
     * @param nanos the maximum number of nanoseconds to wait
     */
    public static void parkNanos(long nanos) {
        if (nanos > 0)
            UNSAFE.park(false, nanos);
    }

    /**
     * Disables the current thread for thread scheduling purposes, until
     * the specified deadline, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified deadline passes; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the current time
     * upon return.
     *
     * @param deadline the absolute time, in milliseconds from the Epoch,
     *        to wait until
     */
    public static void parkUntil(long deadline) {
        UNSAFE.park(true, deadline);
    }

    /**
     * Returns the pseudo-randomly initialized or updated secondary seed.
     * Copied from ThreadLocalRandom due to package access restrictions.
     */
    static final int nextSecondarySeed() {
        int r;
        Thread t = Thread.currentThread();
        if ((r = UNSAFE.getInt(t, SECONDARY)) != 0) {
            r ^= r << 13;   // xorshift
            r ^= r >>> 17;
            r ^= r << 5;
        }
        else if ((r = java.util.concurrent.ThreadLocalRandom.current().nextInt()) == 0)
            r = 1; // avoid zero
        UNSAFE.putInt(t, SECONDARY, r);
        return r;
    }

    // Hotspot implementation via intrinsics API
    private static final sun.misc.Unsafe UNSAFE;
    private static final long parkBlockerOffset;
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            parkBlockerOffset = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("parkBlocker"));
            SEED = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSeed"));
            PROBE = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSecondarySeed"));
        } catch (Exception ex) { throw new Error(ex); }
    }

}
