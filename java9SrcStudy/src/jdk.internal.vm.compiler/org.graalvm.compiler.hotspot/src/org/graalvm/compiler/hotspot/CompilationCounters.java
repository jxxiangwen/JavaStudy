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
package org.graalvm.compiler.hotspot;

import static org.graalvm.compiler.hotspot.HotSpotGraalCompiler.fmt;
import static org.graalvm.compiler.hotspot.HotSpotGraalCompiler.str;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.graalvm.compiler.debug.TTY;
import org.graalvm.compiler.options.Option;
import org.graalvm.compiler.options.OptionType;
import org.graalvm.compiler.options.OptionValue;
import org.graalvm.compiler.options.StableOptionValue;

import jdk.vm.ci.code.CompilationRequest;
import jdk.vm.ci.meta.ResolvedJavaMethod;

class CompilationCounters {

    public static class Options {
        // @formatter:off
        @Option(help = "The number of compilations allowed for any method before " +
                       "the VM exits (a value of 0 means there is no limit).", type = OptionType.Debug)
        public static final OptionValue<Integer> CompilationCountLimit = new StableOptionValue<>(0);
        // @formatter:on
    }

    CompilationCounters() {
        TTY.println("Warning: Compilation counters enabled, excessive recompilation of a method will cause a failure!");
    }

    private final Map<ResolvedJavaMethod, Integer> counters = new HashMap<>();

    /**
     * Counts the number of compilations for the {@link ResolvedJavaMethod} of the
     * {@link CompilationRequest}. If the number of compilations exceeds
     * {@link Options#CompilationCountLimit} this method prints an error message and exits the VM.
     *
     * @param method the method about to be compiled
     */
    synchronized void countCompilation(ResolvedJavaMethod method) {
        Integer val = counters.get(method);
        val = val != null ? val + 1 : 1;
        counters.put(method, val);
        if (val > Options.CompilationCountLimit.getValue()) {
            TTY.printf("Error. Method %s was compiled too many times. Number of compilations: %d\n", fmt(method),
                            CompilationCounters.Options.CompilationCountLimit.getValue());
            TTY.println("==================================== High compilation counters ====================================");
            SortedSet<Map.Entry<ResolvedJavaMethod, Integer>> sortedCounters = new TreeSet<>(new CounterComparator());
            for (Map.Entry<ResolvedJavaMethod, Integer> e : counters.entrySet()) {
                sortedCounters.add(e);
            }
            for (Map.Entry<ResolvedJavaMethod, Integer> entry : sortedCounters) {
                if (entry.getValue() >= Options.CompilationCountLimit.getValue() / 2) {
                    TTY.out.printf("%d\t%s%n", entry.getValue(), str(entry.getKey()));
                }
            }
            TTY.flush();
            System.exit(-1);
        }
    }

    static final class CounterComparator implements Comparator<Map.Entry<ResolvedJavaMethod, Integer>> {
        @Override
        public int compare(Entry<ResolvedJavaMethod, Integer> o1, Entry<ResolvedJavaMethod, Integer> o2) {
            if (o1.getValue() < o2.getValue()) {
                return -1;
            }
            if (o1.getValue() > o2.getValue()) {
                return 1;
            }
            return str(o1.getKey()).compareTo(str(o2.getKey()));
        }
    }
}
