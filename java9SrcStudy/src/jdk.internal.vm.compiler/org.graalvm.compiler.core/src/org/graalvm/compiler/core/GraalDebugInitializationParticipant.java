/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.core;

import org.graalvm.compiler.debug.Debug;
import org.graalvm.compiler.debug.Debug.Params;
import org.graalvm.compiler.debug.DebugInitializationParticipant;
import org.graalvm.compiler.debug.GraalDebugConfig;
import org.graalvm.compiler.debug.TTY;
import org.graalvm.compiler.debug.internal.method.MethodMetricsPrinter;
import org.graalvm.compiler.serviceprovider.ServiceProvider;

/**
 * A service provider that may modify the initialization of {@link Debug} based on the values
 * specified for various {@link GraalDebugConfig} options.
 */
@ServiceProvider(DebugInitializationParticipant.class)
public class GraalDebugInitializationParticipant implements DebugInitializationParticipant {

    @Override
    public void apply(Params params) {
        if (GraalDebugConfig.areDebugScopePatternsEnabled()) {
            params.enable = true;
        }
        if ("".equals(GraalDebugConfig.Options.Count.getValue())) {
            params.enableUnscopedCounters = true;
        }
        if ("".equals(GraalDebugConfig.Options.MethodMeter.getValue())) {
            params.enableUnscopedMethodMetrics = true;
            // mm requires full debugging support
            params.enable = true;
        }
        if ("".equals(GraalDebugConfig.Options.Time.getValue())) {
            params.enableUnscopedTimers = true;
        }
        if ("".equals(GraalDebugConfig.Options.TrackMemUse.getValue())) {
            params.enableUnscopedMemUseTrackers = true;
        }
        // unscoped counters/timers/mem use trackers/method metrics should respect method filter
        // semantics
        if (!params.enable && (params.enableUnscopedMemUseTrackers || params.enableUnscopedMethodMetrics || params.enableUnscopedCounters || params.enableUnscopedTimers) &&
                        GraalDebugConfig.isNotEmpty(GraalDebugConfig.Options.MethodFilter)) {
            params.enable = true;
            params.enableMethodFilter = true;
        }

        if (!params.enableUnscopedMethodMetrics && GraalDebugConfig.Options.MethodMeter.getValue() != null) {
            // mm requires full debugging support
            params.enable = true;
        }

        if (GraalDebugConfig.isGlobalMetricsInterceptedByMethodMetricsEnabled()) {
            if (!params.enable) {
                TTY.println("WARNING: MethodMeter is disabled but GlobalMetricsInterceptedByMethodMetrics is enabled. Ignoring MethodMeter and GlobalMetricsInterceptedByMethodMetrics.");
            } else {
                parseMethodMetricsDebugValueInterception(params);
            }
        }
        if (GraalDebugConfig.isNotEmpty(GraalDebugConfig.Options.MethodMeter) || params.enableUnscopedMethodMetrics) {
            if (!MethodMetricsPrinter.methodMetricsDumpingEnabled()) {
                TTY.println("WARNING: MethodMeter is enabled but MethodMeter dumping is disabled. Output will not contain MethodMetrics.");
            }
        }
    }

    private static void parseMethodMetricsDebugValueInterception(Params params) {
        String interceptionGroup = GraalDebugConfig.Options.GlobalMetricsInterceptedByMethodMetrics.getValue();
        boolean intercepted = false;
        if (interceptionGroup.contains("Timers")) {
            params.interceptTime = true;
            intercepted = true;
        }
        if (interceptionGroup.contains("Counters")) {
            params.interceptCount = true;
            intercepted = true;
        }
        if (interceptionGroup.contains("MemUseTrackers")) {
            params.interceptMem = true;
            intercepted = true;
        }

        if (!intercepted) {
            TTY.println("WARNING: Ignoring GlobalMetricsInterceptedByMethodMetrics as the supplied argument does not contain Timers/Counters/MemUseTrackers.");
            GraalDebugConfig.Options.GlobalMetricsInterceptedByMethodMetrics.setValue(null);
        }
    }
}
