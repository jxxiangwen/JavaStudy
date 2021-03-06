/*
 * Copyright (c) 2013, 2015, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.phases.verify;

import org.graalvm.compiler.core.common.type.ObjectStamp;
import org.graalvm.compiler.nodes.ParameterNode;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.calc.ObjectEqualsNode;
import org.graalvm.compiler.nodes.type.StampTool;
import org.graalvm.compiler.phases.VerifyPhase;
import org.graalvm.compiler.phases.tiers.PhaseContext;

import jdk.vm.ci.meta.JavaField;
import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.JavaMethod;
import jdk.vm.ci.meta.JavaType;
import jdk.vm.ci.meta.MetaAccessProvider;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import jdk.vm.ci.meta.ResolvedJavaType;
import jdk.vm.ci.meta.Signature;

/**
 * For certain types, object identity should not be used for object equality check. This phase
 * checks the correct usage of the given type. Equality checks with == or != (except null checks)
 * results in an {@link AssertionError}.
 */
public class VerifyUsageWithEquals extends VerifyPhase<PhaseContext> {

    @Override
    public boolean checkContract() {
        return false;
    }

    /**
     * The type of values that must not use identity for testing object equality.
     */
    private final Class<?> restrictedClass;

    public VerifyUsageWithEquals(Class<?> restrictedClass) {
        this.restrictedClass = restrictedClass;
        assert !restrictedClass.isInterface() || isTrustedInterface(restrictedClass);
    }

    private static final Class<?>[] trustedInterfaceTypes = {JavaType.class, JavaField.class, JavaMethod.class};

    private static boolean isTrustedInterface(Class<?> cls) {
        for (Class<?> trusted : trustedInterfaceTypes) {
            if (trusted.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the type of {@code node} is assignable to the {@link #restrictedClass}.
     */
    private boolean isAssignableToRestrictedType(ValueNode node, MetaAccessProvider metaAccess) {
        if (node.stamp() instanceof ObjectStamp) {
            ResolvedJavaType restrictedType = metaAccess.lookupJavaType(restrictedClass);
            ResolvedJavaType nodeType = StampTool.typeOrNull(node);

            if (nodeType != null && restrictedType.isAssignableFrom(nodeType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNullConstant(ValueNode node) {
        return node.isConstant() && node.isNullConstant();
    }

    private static boolean isEqualsMethod(ResolvedJavaMethod method) {
        if (method.getName().equals("equals")) {
            Signature sig = method.getSignature();
            if (sig.getReturnKind() == JavaKind.Boolean) {
                if (sig.getParameterCount(false) == 1) {
                    ResolvedJavaType ptype = (ResolvedJavaType) sig.getParameterType(0, method.getDeclaringClass());
                    if (ptype.isJavaLangObject()) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    private static boolean isThisParameter(ValueNode node) {
        return node instanceof ParameterNode && ((ParameterNode) node).index() == 0;
    }

    /**
     * Checks whether the type of {@code x} is assignable to the restricted type and that {@code y}
     * is not a null constant.
     */
    private boolean isIllegalUsage(ResolvedJavaMethod method, ValueNode x, ValueNode y, MetaAccessProvider metaAccess) {
        if (isAssignableToRestrictedType(x, metaAccess) && !isNullConstant(y)) {
            if (isEqualsMethod(method) && isThisParameter(x) || isThisParameter(y)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean verify(StructuredGraph graph, PhaseContext context) {
        for (ObjectEqualsNode cn : graph.getNodes().filter(ObjectEqualsNode.class)) {
            // bail out if we compare an object of type klass with == or != (except null checks)
            ResolvedJavaMethod method = graph.method();
            ResolvedJavaType restrictedType = context.getMetaAccess().lookupJavaType(restrictedClass);

            if (method.getDeclaringClass().equals(restrictedType)) {
                // Allow violation in methods of the restricted type itself.
            } else if (isIllegalUsage(method, cn.getX(), cn.getY(), context.getMetaAccess()) || isIllegalUsage(method, cn.getY(), cn.getX(), context.getMetaAccess())) {
                throw new VerificationError("Verification of " + restrictedClass.getName() + " usage failed: Comparing " + cn.getX() + " and " + cn.getY() + " in " + method +
                                " must use .equals() for object equality, not '==' or '!='");
            }
        }
        return true;
    }
}
