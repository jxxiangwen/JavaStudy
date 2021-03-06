/*
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.hotspot.replacements;

import static org.graalvm.compiler.nodeinfo.NodeCycles.CYCLES_4;
import static org.graalvm.compiler.nodeinfo.NodeSize.SIZE_1;

import org.graalvm.compiler.core.common.LocationIdentity;
import org.graalvm.compiler.core.common.calc.Condition;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.graph.spi.Canonicalizable;
import org.graalvm.compiler.graph.spi.CanonicalizerTool;
import org.graalvm.compiler.hotspot.nodes.type.KlassPointerStamp;
import org.graalvm.compiler.hotspot.word.KlassPointer;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.ConstantNode;
import org.graalvm.compiler.nodes.FloatingGuardedNode;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.calc.ConvertNode;
import org.graalvm.compiler.nodes.extended.GetClassNode;
import org.graalvm.compiler.nodes.extended.GuardingNode;
import org.graalvm.compiler.nodes.extended.LoadHubNode;
import org.graalvm.compiler.nodes.memory.ReadNode;
import org.graalvm.compiler.nodes.memory.address.AddressNode;
import org.graalvm.compiler.nodes.spi.Lowerable;
import org.graalvm.compiler.nodes.spi.LoweringTool;

import jdk.vm.ci.meta.Constant;
import jdk.vm.ci.meta.ConstantReflectionProvider;
import jdk.vm.ci.meta.JavaConstant;
import jdk.vm.ci.meta.MetaAccessProvider;
import jdk.vm.ci.meta.ResolvedJavaType;

/**
 * Read {@code Class::_klass} to get the hub for a {@link java.lang.Class}. This node mostly exists
 * to replace {@code _klass._java_mirror._klass} with {@code _klass}. The constant folding could be
 * handled by
 * {@link ReadNode#canonicalizeRead(ValueNode, AddressNode, LocationIdentity, CanonicalizerTool)}.
 */
@NodeInfo(cycles = CYCLES_4, size = SIZE_1)
public final class ClassGetHubNode extends FloatingGuardedNode implements Lowerable, Canonicalizable, ConvertNode {
    public static final NodeClass<ClassGetHubNode> TYPE = NodeClass.create(ClassGetHubNode.class);
    @Input protected ValueNode clazz;

    public ClassGetHubNode(ValueNode clazz) {
        this(clazz, null);
    }

    public ClassGetHubNode(ValueNode clazz, ValueNode guard) {
        super(TYPE, KlassPointerStamp.klass(), (GuardingNode) guard);
        this.clazz = clazz;
    }

    @Override
    public Node canonical(CanonicalizerTool tool) {
        if (tool.allUsagesAvailable() && hasNoUsages()) {
            return null;
        } else {
            if (clazz.isConstant()) {
                MetaAccessProvider metaAccess = tool.getMetaAccess();
                if (metaAccess != null) {
                    ResolvedJavaType exactType = tool.getConstantReflection().asJavaType(clazz.asJavaConstant());
                    if (exactType.isPrimitive()) {
                        return ConstantNode.forConstant(stamp(), JavaConstant.NULL_POINTER, metaAccess);
                    } else {
                        return ConstantNode.forConstant(stamp(), tool.getConstantReflection().asObjectHub(exactType), metaAccess);
                    }
                }
            }
            if (clazz instanceof GetClassNode) {
                GetClassNode getClass = (GetClassNode) clazz;
                return new LoadHubNode(KlassPointerStamp.klassNonNull(), getClass.getObject());
            }
            if (clazz instanceof HubGetClassNode) {
                // replace _klass._java_mirror._klass -> _klass
                return ((HubGetClassNode) clazz).getHub();
            }
            return this;
        }
    }

    @Override
    public void lower(LoweringTool tool) {
        tool.getLowerer().lower(this, tool);
    }

    @NodeIntrinsic
    public static native KlassPointer readClass(Class<?> clazz);

    @NodeIntrinsic
    public static native KlassPointer readClass(Class<?> clazz, GuardingNode guard);

    @Override
    public ValueNode getValue() {
        return clazz;
    }

    @Override
    public Constant convert(Constant c, ConstantReflectionProvider constantReflection) {
        ResolvedJavaType exactType = constantReflection.asJavaType(c);
        if (exactType.isPrimitive()) {
            return JavaConstant.NULL_POINTER;
        } else {
            return constantReflection.asObjectHub(exactType);
        }
    }

    @Override
    public Constant reverse(Constant c, ConstantReflectionProvider constantReflection) {
        assert !c.equals(JavaConstant.NULL_POINTER);
        ResolvedJavaType objectType = constantReflection.asJavaType(c);
        return constantReflection.asJavaClass(objectType);
    }

    @Override
    public boolean isLossless() {
        return false;
    }

    @Override
    public boolean preservesOrder(Condition op, Constant value, ConstantReflectionProvider constantReflection) {
        assert op == Condition.EQ || op == Condition.NE;
        ResolvedJavaType exactType = constantReflection.asJavaType(value);
        return !exactType.isPrimitive();
    }

}
