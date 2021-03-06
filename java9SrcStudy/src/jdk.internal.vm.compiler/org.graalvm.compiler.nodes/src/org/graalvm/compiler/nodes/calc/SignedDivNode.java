/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
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

import org.graalvm.compiler.core.common.type.IntegerStamp;
import org.graalvm.compiler.core.common.type.PrimitiveStamp;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.graph.spi.CanonicalizerTool;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.ConstantNode;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.spi.LIRLowerable;
import org.graalvm.compiler.nodes.spi.NodeLIRBuilderTool;

import jdk.vm.ci.code.CodeUtil;

@NodeInfo(shortName = "/")
public class SignedDivNode extends IntegerDivRemNode implements LIRLowerable {

    public static final NodeClass<SignedDivNode> TYPE = NodeClass.create(SignedDivNode.class);

    public SignedDivNode(ValueNode x, ValueNode y) {
        this(TYPE, x, y);
    }

    protected SignedDivNode(NodeClass<? extends SignedDivNode> c, ValueNode x, ValueNode y) {
        super(c, IntegerStamp.OPS.getDiv().foldStamp(x.stamp(), y.stamp()), Op.DIV, Type.SIGNED, x, y);
    }

    @Override
    public boolean inferStamp() {
        return updateStamp(IntegerStamp.OPS.getDiv().foldStamp(getX().stamp(), getY().stamp()));
    }

    @Override
    public ValueNode canonical(CanonicalizerTool tool, ValueNode forX, ValueNode forY) {
        if (forX.isConstant() && forY.isConstant()) {
            @SuppressWarnings("hiding")
            long y = forY.asJavaConstant().asLong();
            if (y == 0) {
                return this; // this will trap, can not canonicalize
            }
            return ConstantNode.forIntegerStamp(stamp(), forX.asJavaConstant().asLong() / y);
        } else if (forY.isConstant()) {
            long c = forY.asJavaConstant().asLong();
            if (c == 1) {
                return forX;
            }
            if (c == -1) {
                return new NegateNode(forX);
            }
            long abs = Math.abs(c);
            if (CodeUtil.isPowerOf2(abs) && forX.stamp() instanceof IntegerStamp) {
                ValueNode dividend = forX;
                IntegerStamp stampX = (IntegerStamp) forX.stamp();
                int log2 = CodeUtil.log2(abs);
                // no rounding if dividend is positive or if its low bits are always 0
                if (stampX.canBeNegative() || (stampX.upMask() & (abs - 1)) != 0) {
                    int bits = PrimitiveStamp.getBits(stamp());
                    RightShiftNode sign = new RightShiftNode(forX, ConstantNode.forInt(bits - 1));
                    UnsignedRightShiftNode round = new UnsignedRightShiftNode(sign, ConstantNode.forInt(bits - log2));
                    dividend = BinaryArithmeticNode.add(dividend, round);
                }
                RightShiftNode shift = new RightShiftNode(dividend, ConstantNode.forInt(log2));
                if (c < 0) {
                    return new NegateNode(shift);
                }
                return shift;
            }
        }

        // Convert the expression ((a - a % b) / b) into (a / b).
        if (forX instanceof SubNode) {
            SubNode integerSubNode = (SubNode) forX;
            if (integerSubNode.getY() instanceof SignedRemNode) {
                SignedRemNode integerRemNode = (SignedRemNode) integerSubNode.getY();
                if (integerSubNode.stamp().isCompatible(this.stamp()) && integerRemNode.stamp().isCompatible(this.stamp()) && integerSubNode.getX() == integerRemNode.getX() &&
                                forY == integerRemNode.getY()) {
                    return new SignedDivNode(integerSubNode.getX(), forY);
                }
            }
        }

        if (next() instanceof SignedDivNode) {
            NodeClass<?> nodeClass = getNodeClass();
            if (next().getClass() == this.getClass() && nodeClass.equalInputs(this, next()) && valueEquals(next())) {
                return next();
            }
        }

        return this;
    }

    @Override
    public void generate(NodeLIRBuilderTool gen) {
        gen.setResult(this, gen.getLIRGeneratorTool().getArithmetic().emitDiv(gen.operand(getX()), gen.operand(getY()), gen.state(this)));
    }
}
