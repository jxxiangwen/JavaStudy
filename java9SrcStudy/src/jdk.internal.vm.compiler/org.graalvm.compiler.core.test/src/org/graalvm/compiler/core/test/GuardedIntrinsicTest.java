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

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.graalvm.compiler.core.common.CompilationIdentifier;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.nodes.ConstantNode;
import org.graalvm.compiler.nodes.Invoke;
import org.graalvm.compiler.nodes.InvokeNode;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.StructuredGraph.AllowAssumptions;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.debug.OpaqueNode;
import org.graalvm.compiler.nodes.extended.LoadHubNode;
import org.graalvm.compiler.nodes.extended.LoadMethodNode;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderConfiguration;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderContext;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugin;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugin.Receiver;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugins;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugins.Registration;

import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.ResolvedJavaMethod;

/**
 * Tests use of an intrinsic for virtual methods where the call site is indirect. A prime example is
 * an intrinsic for {@link Object#hashCode()}. The intrinsic can only be used if the call site would
 * actually dispatch to {@link Object#hashCode()} and not a method that overrides it.
 */
public class GuardedIntrinsicTest extends GraalCompilerTest {

    static class Super {
        int getAge() {
            return 11;
        }
    }

    public static class Person extends Super {
        int age;

        public Person(int age) {
            this.age = age;
        }

        @Override
        public int getAge() {
            return age;
        }
    }

    @BytecodeParserForceInline
    public static final Super createSuper() {
        return new Super();
    }

    @BytecodeParserNeverInline
    public static final Super createPerson() {
        return new Person(42);
    }

    public static int getSuperAge(Super s) {
        return s.getAge();
    }

    public static int getPersonAge(Person p) {
        return p.getAge();
    }

    public static int makeSuperAge() {
        return createSuper().getAge();
    }

    public static int makePersonAge() {
        return createPerson().getAge();
    }

    @BeforeClass
    public static void init() {
        // Ensure classes are initialized
        new Person(0).toString();
    }

    private StructuredGraph graph;
    private StructuredGraph parsedForCompile;

    @Override
    protected StructuredGraph parseForCompile(ResolvedJavaMethod method, CompilationIdentifier compilationId) {
        graph = super.parseForCompile(method, compilationId);
        parsedForCompile = (StructuredGraph) graph.copy();
        return graph;
    }

    @Override
    protected GraphBuilderConfiguration editGraphBuilderConfiguration(GraphBuilderConfiguration conf) {
        InvocationPlugins invocationPlugins = conf.getPlugins().getInvocationPlugins();
        Registration r = new Registration(invocationPlugins, Super.class);

        r.register1("getAge", Receiver.class, new InvocationPlugin() {
            @Override
            public boolean apply(GraphBuilderContext b, ResolvedJavaMethod targetMethod, Receiver receiver) {
                ConstantNode res = b.add(ConstantNode.forInt(new Super().getAge()));
                b.add(new OpaqueNode(res));
                b.push(JavaKind.Int, res);
                return true;
            }
        });
        return super.editGraphBuilderConfiguration(conf);
    }

    public static int referenceMakeSuperAge() {
        return 11;
    }

    public static int referenceMakePersonAge() {
        return 42;
    }

    @Test
    public void test01() {
        Super inheritsHC = new Super();
        Person overridesHC = new Person(0);
        test("getSuperAge", inheritsHC);
        test("getSuperAge", overridesHC);

        // Check that the virtual dispatch test exists after bytecode parsing
        Assert.assertEquals(1, parsedForCompile.getNodes().filter(LoadMethodNode.class).count());
        Assert.assertEquals(1, parsedForCompile.getNodes().filter(LoadHubNode.class).count());

        // Check for the marker node indicating the intrinsic was applied
        Assert.assertEquals(1, parsedForCompile.getNodes().filter(OpaqueNode.class).count());

        // Final graph should have a single invoke
        List<Node> invokes = graph.getNodes().filter(n -> n instanceof Invoke).snapshot();
        Assert.assertEquals(invokes.toString(), 1, invokes.size());
    }

    @Test
    public void test02() {
        test("getPersonAge", new Person(0));

        // Check that the virtual dispatch test does not exist after bytecode parsing
        Assert.assertEquals(0, parsedForCompile.getNodes().filter(LoadMethodNode.class).count());
        Assert.assertEquals(0, parsedForCompile.getNodes().filter(LoadHubNode.class).count());

        Assert.assertEquals(0, parsedForCompile.getNodes().filter(InvokeNode.class).count());
    }

    @Test
    public void test03() {
        test("makeSuperAge");

        // Check that the virtual dispatch test does not exist after bytecode parsing
        Assert.assertEquals(0, parsedForCompile.getNodes().filter(LoadMethodNode.class).count());
        Assert.assertEquals(0, parsedForCompile.getNodes().filter(LoadHubNode.class).count());

        StructuredGraph referenceGraph = parseEager("referenceMakeSuperAge", AllowAssumptions.NO);
        assertEquals(referenceGraph, graph, true, true);
    }

    @Test
    public void test04() {
        test("makePersonAge");

        // Check that the virtual dispatch test exists after bytecode parsing
        Assert.assertEquals(1, parsedForCompile.getNodes().filter(LoadMethodNode.class).count());
        Assert.assertEquals(1, parsedForCompile.getNodes().filter(LoadHubNode.class).count());

        StructuredGraph referenceGraph = parseEager("referenceMakePersonAge", AllowAssumptions.NO);
        assertEquals(referenceGraph, graph, true, true);
    }

    static final class ReadCacheEntry {

        public final Object identity;
        public final ValueNode object;

        ReadCacheEntry(Object identity, ValueNode object) {
            this.identity = identity;
            this.object = object;
        }

        @Override
        public int hashCode() {
            int result = ((identity == null) ? 0 : identity.hashCode());
            return result + object.hashCode();
        }
    }

    public static int getHashCode(ReadCacheEntry obj) {
        return obj.hashCode();
    }

    @Test
    public void test05() {
        ReadCacheEntry val1 = new ReadCacheEntry("identity", ConstantNode.forBoolean(false));
        ReadCacheEntry val2 = new ReadCacheEntry(Integer.valueOf(34), ConstantNode.forInt(42));
        for (int i = 0; i < 10000; i++) {
            getHashCode(val2);
        }
        test("getHashCode", val1);
    }
}
