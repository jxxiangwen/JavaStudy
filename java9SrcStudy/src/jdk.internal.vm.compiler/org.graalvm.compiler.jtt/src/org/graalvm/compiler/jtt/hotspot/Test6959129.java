/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package org.graalvm.compiler.jtt.hotspot;

import org.junit.Test;

import org.graalvm.compiler.jtt.JTTTest;

public class Test6959129 extends JTTTest {

    public static long test() {
        int min = Integer.MAX_VALUE - 30000;
        int max = Integer.MAX_VALUE;
        return maxMoves(min, max);
    }

    /**
     * Imperative implementation that returns the length hailstone moves for a given number.
     */
    public static long hailstoneLengthImp(long n2) {
        long n = n2;
        long moves = 0;
        while (n != 1) {
            if (n <= 1) {
                throw new IllegalStateException();
            }
            if (isEven(n)) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            ++moves;
        }
        return moves;
    }

    private static boolean isEven(long n) {
        return n % 2 == 0;
    }

    /**
     * Returns the maximum length of the hailstone sequence for numbers between min to max.
     *
     * For rec1 - Assume that min is bigger than max.
     */
    public static long maxMoves(int min, int max) {
        long maxmoves = 0;
        for (int n = min; n <= max; n++) {
            long moves = hailstoneLengthImp(n);
            if (moves > maxmoves) {
                maxmoves = moves;
            }
        }
        return maxmoves;
    }

    @Test(timeout = 20000)
    public void run0() throws Throwable {
        runTest("test");
    }

}
