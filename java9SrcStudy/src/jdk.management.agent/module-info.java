/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines the JMX management agent.
 *
 * <p> This module allows a Java Virtual Machine to be monitored and managed
 * via JMX API.  See more information from the
 * {@extLink monitoring_and_management_using_jmx_technology
 * Monitoring and Management Using JMX} guide.
 *
 * @moduleGraph
 * @since 9
 */
module jdk.management.agent {
    // source file: file:///scratch/HUDSON/workspace/9-2-build-linux-amd64-phase2/jdk9/6725/jdk/src/jdk.management.agent/share/classes/module-info.java
    //              file:///scratch/HUDSON/workspace/9-2-build-linux-amd64-phase2/jdk9/6725/jdk/src/closed/jdk.management.agent/share/classes/module-info.java.extra
    requires java.management;
    requires java.management.rmi;
    exports jdk.internal.agent to jdk.jconsole;
    exports jdk.internal.agent.spi to jdk.snmp;

    uses jdk.internal.agent.spi.AgentProvider;
}
