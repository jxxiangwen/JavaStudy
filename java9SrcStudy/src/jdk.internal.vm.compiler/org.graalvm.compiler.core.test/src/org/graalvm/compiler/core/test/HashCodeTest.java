/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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

import org.junit.Assert;
import org.junit.Test;

import org.graalvm.compiler.core.phases.HighTier;
import org.graalvm.compiler.core.phases.MidTier;
import org.graalvm.compiler.nodes.InvokeNode;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.extended.LoadHubNode;
import org.graalvm.compiler.nodes.extended.LoadMethodNode;
import org.graalvm.compiler.phases.OptimisticOptimizations;
import org.graalvm.compiler.phases.tiers.MidTierContext;

public class HashCodeTest extends GraalCompilerTest {

    static class OverrideHashCode {
        @Override
        public int hashCode() {
            return 42;
        }
    }

    static final class DontOverrideHashCode {
    }

    public static final Object NonOverridingConstant = new Object();
    public static final Object OverridingConstant = new OverrideHashCode();

    private static void initialize(Class<?> c) {
        try {
            Class.forName(c.getName(), true, c.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    public static final int hashCodeSnippet01(Object o) {
        return o.hashCode();
    }

    public static final int systemIdentityHashCodeSnippet01(Object o) {
        return System.identityHashCode(o);
    }

    public static final int hashCodeFoldSnippet01() {
        return NonOverridingConstant.hashCode();
    }

    public static final int identityHashCodeFoldSnippet01() {
        return System.identityHashCode(NonOverridingConstant);
    }

    public static final int hashCodeNoFoldOverridingSnippet01(Object o) {
        return o.hashCode();
    }

    public static final int identityHashCodeFoldOverridingSnippet01() {
        return System.identityHashCode(OverridingConstant);
    }

    public static final int dontOverrideHashCodeFinalClass(DontOverrideHashCode o) {
        return o.hashCode();
    }

    @Test
    public void test01() {
        test("hashCodeSnippet01", new Object());
    }

    @Test
    public void test02() {
        test("systemIdentityHashCodeSnippet01", new Object());
    }

    @Test
    public void test03() {
        StructuredGraph g = buildGraphAfterMidTier("hashCodeFoldSnippet01");
        Assert.assertEquals(0, g.getNodes().filter(InvokeNode.class).count());
    }

    @Test
    public void test04() {
        StructuredGraph g = buildGraphAfterMidTier("identityHashCodeFoldSnippet01");
        Assert.assertEquals(0, g.getNodes().filter(InvokeNode.class).count());
    }

    @Test
    public void test05() {
        checkForGuardedIntrinsicPattern("hashCodeNoFoldOverridingSnippet01");

        Object nullObject = null;
        test("hashCodeNoFoldOverridingSnippet01", nullObject);
        test("hashCodeNoFoldOverridingSnippet01", new Object());
        test("hashCodeNoFoldOverridingSnippet01", new DontOverrideHashCode());
    }

    @Test
    public void test06() {
        StructuredGraph g = buildGraphAfterMidTier("identityHashCodeFoldOverridingSnippet01");
        Assert.assertEquals(0, g.getNodes().filter(InvokeNode.class).count());
    }

    @Test
    public void test07() {
        initialize(DontOverrideHashCode.class);
        StructuredGraph g = buildGraphAfterMidTier("dontOverrideHashCodeFinalClass");
        Assert.assertEquals(0, g.getNodes().filter(InvokeNode.class).count());
    }

    public static final int hashCodeInterface(Appendable o) {
        return o.hashCode();
    }

    @Test
    public void test08() {
        initialize(Appendable.class);
        checkForGuardedIntrinsicPattern("hashCodeInterface");
        checkForGuardedIntrinsicPattern("hashCodeSnippet01");
    }

    private void checkForGuardedIntrinsicPattern(String name) {
        StructuredGraph g = parseForCompile(getResolvedJavaMethod(name));
        Assert.assertEquals(1, g.getNodes().filter(InvokeNode.class).count());
        Assert.assertEquals(1, g.getNodes().filter(LoadHubNode.class).count());
        Assert.assertEquals(1, g.getNodes().filter(LoadMethodNode.class).count());
    }

    @SuppressWarnings("try")
    private StructuredGraph buildGraphAfterMidTier(String name) {
        StructuredGraph g = parseForCompile(getResolvedJavaMethod(name));
        new HighTier().apply(g, getDefaultHighTierContext());
        new MidTier().apply(g, new MidTierContext(getProviders(), getTargetProvider(), OptimisticOptimizations.ALL, g.getProfilingInfo()));
        return g;
    }

}
