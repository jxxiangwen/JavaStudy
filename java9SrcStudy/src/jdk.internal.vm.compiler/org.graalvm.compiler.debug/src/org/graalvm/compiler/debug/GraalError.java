/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.debug;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Indicates a condition that should never occur during normal operation.
 */
public class GraalError extends Error {

    private static final long serialVersionUID = 531632331813456233L;
    private final ArrayList<String> context = new ArrayList<>();

    public static RuntimeException unimplemented() {
        throw new GraalError("unimplemented");
    }

    public static RuntimeException unimplemented(String msg) {
        throw new GraalError("unimplemented: %s", msg);
    }

    public static RuntimeException shouldNotReachHere() {
        throw new GraalError("should not reach here");
    }

    public static RuntimeException shouldNotReachHere(String msg) {
        throw new GraalError("should not reach here: %s", msg);
    }

    public static RuntimeException shouldNotReachHere(Throwable cause) {
        throw new GraalError(cause);
    }

    /**
     * Checks a given condition and throws a {@link GraalError} if it is false. Guarantees are
     * stronger than assertions in that they are always checked. Error messages for guarantee
     * violations should clearly indicate the nature of the problem as well as a suggested solution
     * if possible.
     *
     * @param condition the condition to check
     * @param msg the message that will be associated with the error, in
     *            {@link String#format(String, Object...)} syntax
     * @param args arguments to the format string
     */
    public static void guarantee(boolean condition, String msg, Object... args) {
        if (!condition) {
            throw new GraalError("failed guarantee: " + msg, args);
        }
    }

    /**
     * This constructor creates a {@link GraalError} with a given message.
     *
     * @param msg the message that will be associated with the error
     */
    public GraalError(String msg) {
        super(msg);
    }

    /**
     * This constructor creates a {@link GraalError} with a message assembled via
     * {@link String#format(String, Object...)}. It always uses the ENGLISH locale in order to
     * always generate the same output.
     *
     * @param msg the message that will be associated with the error, in String.format syntax
     * @param args parameters to String.format - parameters that implement {@link Iterable} will be
     *            expanded into a [x, x, ...] representation.
     */
    public GraalError(String msg, Object... args) {
        super(format(msg, args));
    }

    /**
     * This constructor creates a {@link GraalError} for a given causing Throwable instance.
     *
     * @param cause the original exception that contains additional information on this error
     */
    public GraalError(Throwable cause) {
        super(cause);
    }

    /**
     * This constructor creates a {@link GraalError} and adds all the
     * {@linkplain #addContext(String) context} of another {@link GraalError}.
     *
     * @param e the original {@link GraalError}
     */
    public GraalError(GraalError e) {
        super(e);
        context.addAll(e.context);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        for (String s : context) {
            str.append("\n\tat ").append(s);
        }
        return str.toString();
    }

    private static String format(String msg, Object... args) {
        if (args != null) {
            // expand Iterable parameters into a list representation
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Iterable<?>) {
                    ArrayList<Object> list = new ArrayList<>();
                    for (Object o : (Iterable<?>) args[i]) {
                        list.add(o);
                    }
                    args[i] = list.toString();
                }
            }
        }
        return String.format(Locale.ENGLISH, msg, args);
    }

    public GraalError addContext(String newContext) {
        this.context.add(newContext);
        return this;
    }

    public GraalError addContext(String name, Object obj) {
        return addContext(format("%s: %s", name, obj));
    }
}