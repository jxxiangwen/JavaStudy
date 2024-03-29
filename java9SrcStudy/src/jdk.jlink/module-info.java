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
 *
 *
 */

/**
 * Defines the <em>{@index jlink jlink tool}</em> tool for creating run-time
 * images, the <em>{@index jmod jmod tool}</em> tool for creating and manipulating
 * JMOD files, and the <em>{@index jimage jimage tool}</em> tool for inspecting
 * the JDK implementation-specific container file for classes and resources.
 *
 * <p> This module provides the equivalent of command-line access to the
 * <em>{@extLink jlink_tool_reference jlink}</em> and
 * <em>{@extLink jmod_tool_reference jmod}</em> tools via the
 * {@link java.util.spi.ToolProvider ToolProvider} SPI.
 * Instances of the tools can be obtained by calling
 * {@link java.util.spi.ToolProvider#findFirst ToolProvider.findFirst}
 * or the {@link java.util.ServiceLoader service loader} with the name
 * {@code "jlink"} or {@code "jmod"} as appropriate.
 *
 * <p> <em>{@extLink jimage_tool_reference jimage}</em> only exists
 * as a command-line tool, and does not provide any direct API.
 *
 * <dl style="font-family:'DejaVu Sans', Arial, Helvetica, sans serif">
 * <dt class="simpleTagLabel">Tool Guides:
 * <dd>{@extLink jlink_tool_reference jlink},
 *     {@extLink jmod_tool_reference jmod},
 *     {@extLink jimage_tool_reference jimage}
 * </dl>
 *
 * @provides java.util.spi.ToolProvider
 *
 * @moduleGraph
 * @since 9
 */
module jdk.jlink {
    // source file: file:///scratch/HUDSON/workspace/9-2-build-linux-amd64-phase2/jdk9/6725/jdk/src/jdk.jlink/share/classes/module-info.java
    //              file:///var/tmp/jib-java_re/install/java/re/javafx/9/promoted/all/181/bundles/linux-x64/javafx-exports.zip/modules_src/jdk.jlink/module-info.java.extra
    requires jdk.internal.opt;
    requires jdk.jdeps;
    exports jdk.tools.jlink.internal.packager to jdk.packager;

    uses jdk.tools.jlink.plugin.Plugin;
    provides java.util.spi.ToolProvider with
        jdk.tools.jmod.Main.JmodToolProvider,
        jdk.tools.jlink.internal.Main.JlinkToolProvider;
    provides jdk.tools.jlink.plugin.Plugin with
        jdk.tools.jlink.internal.plugins.StripDebugPlugin,
        jdk.tools.jlink.internal.plugins.ExcludePlugin,
        jdk.tools.jlink.internal.plugins.ExcludeFilesPlugin,
        jdk.tools.jlink.internal.plugins.ExcludeJmodSectionPlugin,
        jdk.tools.jlink.internal.plugins.LegalNoticeFilePlugin,
        jdk.tools.jlink.internal.plugins.SystemModulesPlugin,
        jdk.tools.jlink.internal.plugins.StripNativeCommandsPlugin,
        jdk.tools.jlink.internal.plugins.OrderResourcesPlugin,
        jdk.tools.jlink.internal.plugins.DefaultCompressPlugin,
        jdk.tools.jlink.internal.plugins.ExcludeVMPlugin,
        jdk.tools.jlink.internal.plugins.IncludeLocalesPlugin,
        jdk.tools.jlink.internal.plugins.GenerateJLIClassesPlugin,
        jdk.tools.jlink.internal.plugins.ReleaseInfoPlugin,
        jdk.tools.jlink.internal.plugins.ClassForNamePlugin;
}
