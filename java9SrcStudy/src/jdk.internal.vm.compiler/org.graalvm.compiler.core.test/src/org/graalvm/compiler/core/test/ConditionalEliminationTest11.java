/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.core.test;

import org.junit.Ignore;
import org.junit.Test;

import org.graalvm.compiler.api.directives.GraalDirectives;

/**
 * Collection of tests for
 * {@link org.graalvm.compiler.phases.common.DominatorConditionalEliminationPhase} including those
 * that triggered bugs in this phase.
 */
public class ConditionalEliminationTest11 extends ConditionalEliminationTestBase {
    public ConditionalEliminationTest11() {
        // Don't disable simplification
        super(false);
    }

    @SuppressWarnings("all")
    public static int referenceSnippet(int a) {
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    @Test
    public void test1() {
        testConditionalElimination("test1Snippet", "referenceSnippet");
    }

    @SuppressWarnings("all")
    public static int test1Snippet(int a) {
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    @SuppressWarnings("all")
    public static int test2Snippet(int a) {
        if ((a & 8) == 0) {
            GraalDirectives.deoptimize();
        }
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    @Test
    public void test2() {
        testConditionalElimination("test2Snippet", "referenceSnippet");
    }

    @SuppressWarnings("all")
    public static int test3Snippet(int a) {
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    @Test
    public void test3() {
        // Test forward elimination of bitwise tests
        testConditionalElimination("test3Snippet", "referenceSnippet");
    }

    @SuppressWarnings("all")
    public static int test4Snippet(int a) {
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) == 0) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    @Test
    public void test4() {
        // Test forward elimination of bitwise tests
        testConditionalElimination("test4Snippet", "referenceSnippet");
    }

    public static int test5Snippet(int a) {
        if ((a & 5) == 5) {
            GraalDirectives.deoptimize();
        }
        if ((a & 7) != 0) {
            return 0;
        }
        return 1;
    }

    @Test
    public void test5() {
        // Shouldn't be possible to optimize this
        testConditionalElimination("test5Snippet", "test5Snippet");
    }

    public static int test6Snippet(int a) {
        if ((a & 8) != 0) {
            GraalDirectives.deoptimize();
        }
        if ((a & 15) != 15) {
            GraalDirectives.deoptimize();
        }
        return 0;
    }

    public static int reference6Snippet(int a) {
        if ((a & 8) != 0) {
            GraalDirectives.deoptimize();
        }
        GraalDirectives.deoptimize();
        return 0;
    }

    @Test
    public void test6() {
        testConditionalElimination("test6Snippet", "reference6Snippet");
    }

    public static int test7Snippet(int a) {
        if ((a & 15) == 15) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) == 8) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    public static int reference7Snippet(int a) {
        if ((a & 8) == 8) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    @Test
    public void test7() {
        testConditionalElimination("test7Snippet", "reference7Snippet");
    }

    public static int test8Snippet(int a) {
        if ((a & 16) == 16) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        if ((a & 44) != 44) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    public static int reference8Snippet(int a) {
        if ((a & 60) != 44) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    @Ignore("requires merging of bit tests")
    @Test
    public void test8() {
        testConditionalElimination("test8Snippet", "reference8Snippet");
    }

    public static int test9Snippet(int a) {
        if ((a & 16) == 16) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        if ((a & 44) != 44) {
            GraalDirectives.deoptimize();
        }
        if (a != 44) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    public static int reference9Snippet(int a) {
        if (a != 44) {
            GraalDirectives.deoptimize();
        }
        return a;
    }

    @Test
    public void test9() {
        testConditionalElimination("test9Snippet", "reference9Snippet");
    }

    static class ByteHolder {
        public byte b;

        byte byteValue() {
            return b;
        }
    }

    public static int test10Snippet(ByteHolder b) {
        int v = b.byteValue();
        long a = v & 0xffffffff;
        if (v != 44) {
            GraalDirectives.deoptimize();
        }
        if ((a & 16) == 16) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        if ((a & 44) != 44) {
            GraalDirectives.deoptimize();
        }

        return v;
    }

    public static int reference10Snippet(ByteHolder b) {
        byte v = b.byteValue();
        if (v != 44) {
            GraalDirectives.deoptimize();
        }
        return v;
    }

    @Test
    public void test10() {
        testConditionalElimination("test10Snippet", "reference10Snippet");
    }

    public static int test11Snippet(ByteHolder b) {
        int v = b.byteValue();
        long a = v & 0xffffffff;

        if ((a & 16) == 16) {
            GraalDirectives.deoptimize();
        }
        if ((a & 8) != 8) {
            GraalDirectives.deoptimize();
        }
        if ((a & 44) != 44) {
            GraalDirectives.deoptimize();
        }
        if (v != 44) {
            GraalDirectives.deoptimize();
        }
        return v;
    }

    public static int reference11Snippet(ByteHolder b) {
        byte v = b.byteValue();
        if (v != 44) {
            GraalDirectives.deoptimize();
        }
        return v;
    }

    @Test
    public void test11() {
        testConditionalElimination("test11Snippet", "reference11Snippet");
    }

}
