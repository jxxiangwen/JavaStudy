/*
 * Copyright (c) 2014, 2015, Oracle and/or its affiliates. All rights reserved.
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
package java.lang.invoke;

import jdk.internal.vm.annotation.ForceInline;
import jdk.internal.vm.annotation.Stable;

import java.lang.invoke.VarHandle.AccessMode;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * A var handle form containing a set of member name, one for each operation.
 * Each member characterizes a static method.
 */
final class VarForm {

    final @Stable MethodType[] methodType_table;

    final @Stable MemberName[] memberName_table;

    VarForm(Class<?> implClass, Class<?> receiver, Class<?> value, Class<?>... intermediate) {
        this.methodType_table = new MethodType[java.lang.invoke.VarHandle.AccessType.values().length];

        // TODO lazily calculate
        this.memberName_table = linkFromStatic(implClass);

        // (Receiver, <Intermediates>)
        List<Class<?>> l = new ArrayList<>();
        if (receiver != null)
            l.add(receiver);
        for (Class<?> c : intermediate)
            l.add(c);

        // (Receiver, <Intermediates>)Value
        methodType_table[java.lang.invoke.VarHandle.AccessType.GET.ordinal()] =
                MethodType.methodType(value, l).erase();

        // (Receiver, <Intermediates>, Value)void
        l.add(value);
        methodType_table[java.lang.invoke.VarHandle.AccessType.SET.ordinal()] =
                MethodType.methodType(void.class, l).erase();

        // (Receiver, <Intermediates>, Value)Value
        methodType_table[java.lang.invoke.VarHandle.AccessType.GET_AND_UPDATE.ordinal()] =
                MethodType.methodType(value, l).erase();

        // (Receiver, <Intermediates>, Value, Value)boolean
        l.add(value);
        methodType_table[java.lang.invoke.VarHandle.AccessType.COMPARE_AND_SWAP.ordinal()] =
                MethodType.methodType(boolean.class, l).erase();

        // (Receiver, <Intermediates>, Value, Value)Value
        methodType_table[java.lang.invoke.VarHandle.AccessType.COMPARE_AND_EXCHANGE.ordinal()] =
                MethodType.methodType(value, l).erase();
    }

    @ForceInline
    final MethodType getMethodType(int type) {
        return methodType_table[type];
    }

    @ForceInline
    final MemberName getMemberName(int mode) {
        // TODO calculate lazily
        MemberName mn = memberName_table[mode];
        if (mn == null) {
            throw new UnsupportedOperationException();
        }
        return mn;
    }


    @Stable
    MethodType[] methodType_V_table;

    @ForceInline
    final MethodType[] getMethodType_V_init() {
        MethodType[] table = new MethodType[java.lang.invoke.VarHandle.AccessType.values().length];
        for (int i = 0; i < methodType_table.length; i++) {
            MethodType mt = methodType_table[i];
            // TODO only adjust for sig-poly methods returning Object
            table[i] = mt.changeReturnType(void.class);
        }
        methodType_V_table = table;
        return table;
    }

    @ForceInline
    final MethodType getMethodType_V(int type) {
        MethodType[] table = methodType_V_table;
        if (table == null) {
            table = getMethodType_V_init();
        }
        return table[type];
    }


    /**
     * Link all signature polymorphic methods.
     */
    private static MemberName[] linkFromStatic(Class<?> implClass) {
        MemberName[] table = new MemberName[AccessMode.values().length];

        for (Class<?> c = implClass; c != java.lang.invoke.VarHandle.class; c = c.getSuperclass()) {
            for (Method m : c.getDeclaredMethods()) {
                if (Modifier.isStatic(m.getModifiers())) {
                    AccessMode am = AccessMode.methodNameToAccessMode.get(m.getName());
                    if (am != null) {
                        assert table[am.ordinal()] == null;
                        table[am.ordinal()] = new MemberName(m);
                    }
                }
            }
        }
        return table;
    }
}