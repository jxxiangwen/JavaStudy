/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.nodes.calc;

import static org.graalvm.compiler.nodeinfo.NodeCycles.CYCLES_2;
import static org.graalvm.compiler.nodeinfo.NodeSize.SIZE_2;

import org.graalvm.compiler.core.common.type.IntegerStamp;
import org.graalvm.compiler.core.common.type.Stamp;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.graph.spi.Canonicalizable.BinaryCommutative;
import org.graalvm.compiler.graph.spi.CanonicalizerTool;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.BinaryOpLogicNode;
import org.graalvm.compiler.nodes.LogicConstantNode;
import org.graalvm.compiler.nodes.ValueNode;

import jdk.vm.ci.meta.TriState;

/**
 * This node will perform a "test" operation on its arguments. Its result is equivalent to the
 * expression "(x &amp; y) == 0", meaning that it will return true if (and only if) no bit is set in
 * both x and y.
 */
@NodeInfo(cycles = CYCLES_2, size = SIZE_2)
public final class IntegerTestNode extends BinaryOpLogicNode implements BinaryCommutative<ValueNode> {
    public static final NodeClass<IntegerTestNode> TYPE = NodeClass.create(IntegerTestNode.class);

    public IntegerTestNode(ValueNode x, ValueNode y) {
        super(TYPE, x, y);
    }

    @Override
    public ValueNode canonical(CanonicalizerTool tool, ValueNode forX, ValueNode forY) {
        if (forX.isConstant() && forY.isConstant()) {
            return LogicConstantNode.forBoolean((forX.asJavaConstant().asLong() & forY.asJavaConstant().asLong()) == 0);
        }
        if (forX.stamp() instanceof IntegerStamp && forY.stamp() instanceof IntegerStamp) {
            IntegerStamp xStamp = (IntegerStamp) forX.stamp();
            IntegerStamp yStamp = (IntegerStamp) forY.stamp();
            if ((xStamp.upMask() & yStamp.upMask()) == 0) {
                return LogicConstantNode.tautology();
            } else if ((xStamp.downMask() & yStamp.downMask()) != 0) {
                return LogicConstantNode.contradiction();
            }
        }
        return this;
    }

    @Override
    public Stamp getSucceedingStampForX(boolean negated) {
        Stamp xStampGeneric = this.getX().stamp();
        Stamp yStampGeneric = this.getY().stamp();
        return getSucceedingStamp(negated, xStampGeneric, yStampGeneric);
    }

    private static Stamp getSucceedingStamp(boolean negated, Stamp xStampGeneric, Stamp otherStampGeneric) {
        if (xStampGeneric instanceof IntegerStamp && otherStampGeneric instanceof IntegerStamp) {
            IntegerStamp xStamp = (IntegerStamp) xStampGeneric;
            IntegerStamp otherStamp = (IntegerStamp) otherStampGeneric;
            if (negated) {
                if (Long.bitCount(otherStamp.upMask()) == 1) {
                    long newDownMask = xStamp.downMask() | otherStamp.upMask();
                    if (xStamp.downMask() != newDownMask) {
                        return IntegerStamp.stampForMask(xStamp.getBits(), newDownMask, xStamp.upMask()).join(xStamp);
                    }
                }
            } else {
                long restrictedUpMask = ((~otherStamp.downMask()) & xStamp.upMask());
                if (xStamp.upMask() != restrictedUpMask) {
                    return IntegerStamp.stampForMask(xStamp.getBits(), xStamp.downMask(), restrictedUpMask).join(xStamp);
                }
            }
        }
        return null;
    }

    @Override
    public Stamp getSucceedingStampForY(boolean negated) {
        Stamp xStampGeneric = this.getX().stamp();
        Stamp yStampGeneric = this.getY().stamp();
        return getSucceedingStamp(negated, yStampGeneric, xStampGeneric);
    }

    @Override
    public TriState tryFold(Stamp xStampGeneric, Stamp yStampGeneric) {
        if (xStampGeneric instanceof IntegerStamp && yStampGeneric instanceof IntegerStamp) {
            IntegerStamp xStamp = (IntegerStamp) xStampGeneric;
            IntegerStamp yStamp = (IntegerStamp) yStampGeneric;
            if ((xStamp.upMask() & yStamp.upMask()) == 0) {
                return TriState.TRUE;
            } else if ((xStamp.downMask() & yStamp.downMask()) != 0) {
                return TriState.FALSE;
            }
        }
        return TriState.UNKNOWN;
    }
}