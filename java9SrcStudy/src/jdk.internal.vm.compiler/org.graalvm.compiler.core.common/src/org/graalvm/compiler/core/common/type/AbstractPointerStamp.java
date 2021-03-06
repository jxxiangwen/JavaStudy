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
 */
package org.graalvm.compiler.core.common.type;

import jdk.vm.ci.meta.Constant;
import jdk.vm.ci.meta.JavaConstant;
import jdk.vm.ci.meta.JavaKind;

/**
 * Abstract base class of all pointer types.
 */
public abstract class AbstractPointerStamp extends Stamp {

    private final boolean nonNull;
    private final boolean alwaysNull;

    protected AbstractPointerStamp(boolean nonNull, boolean alwaysNull) {
        this.nonNull = nonNull;
        this.alwaysNull = alwaysNull;
    }

    public boolean nonNull() {
        assert !this.isEmpty() || nonNull;
        return nonNull;
    }

    public boolean alwaysNull() {
        return alwaysNull;
    }

    protected abstract AbstractPointerStamp copyWith(boolean newNonNull, boolean newAlwaysNull);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (alwaysNull ? 1231 : 1237);
        result = prime * result + (nonNull ? 1231 : 1237);
        return result;
    }

    @Override
    public Stamp join(Stamp stamp) {
        AbstractPointerStamp other = (AbstractPointerStamp) stamp;
        boolean joinNonNull = this.nonNull || other.nonNull;
        boolean joinAlwaysNull = this.alwaysNull || other.alwaysNull;
        if (joinNonNull && joinAlwaysNull) {
            return empty();
        } else {
            return copyWith(joinNonNull, joinAlwaysNull);
        }
    }

    @Override
    public Stamp improveWith(Stamp other) {
        return join(other);
    }

    @Override
    public Stamp meet(Stamp stamp) {
        AbstractPointerStamp other = (AbstractPointerStamp) stamp;
        boolean meetNonNull = this.nonNull && other.nonNull;
        boolean meetAlwaysNull = this.alwaysNull && other.alwaysNull;
        return copyWith(meetNonNull, meetAlwaysNull);
    }

    @Override
    public Stamp unrestricted() {
        return copyWith(false, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractPointerStamp other = (AbstractPointerStamp) obj;
        return this.alwaysNull == other.alwaysNull && this.nonNull == other.nonNull;
    }

    @Override
    public Constant asConstant() {
        if (alwaysNull) {
            return JavaConstant.NULL_POINTER;
        } else {
            return null;
        }
    }

    @Override
    public JavaKind getStackKind() {
        return JavaKind.Illegal;
    }
}
