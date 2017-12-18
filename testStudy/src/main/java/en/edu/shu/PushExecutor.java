package en.edu.shu;

import java.util.concurrent.*;

public class PushExecutor {
	
	private static volatile int rejectedExecutionCount = 0;
	private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	private static ThreadPoolExecutor executor;
	private static final PushExecutor INSTANCE = new PushExecutor();
	
	private PushExecutor() {
		executor = new ThreadPoolExecutor(50, 500, 10,
				TimeUnit.SECONDS, queue);
		
	}
	
	public static PushExecutor getInstance() {
		return INSTANCE;
	}
	
	public void execute(Runnable command) {
		try {
			if(queue.size() < 1000000) {
				executor.execute(command);
			} else {
				System.out.println( "Warning: Task Queue Out of Range!");
			}
		} catch (RejectedExecutionException e) {
			rejectedExecutionCount++;
		}
	}
	
	public void shutdown() {
		executor.shutdown();
	}
	
	public String showText() {
		return "Active:" + executor.getActiveCount() + " Completed:" + executor.getCompletedTaskCount() + " TaskCount:" + executor.getTaskCount() +
		" CoolPoolSize:" + executor.getCorePoolSize() + " PoolSize:" + executor.getPoolSize() +" RejectedExecutionCount:" + rejectedExecutionCount;
	}
	
}
