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
package org.graalvm.compiler.hotspot.sparc;

import static jdk.vm.ci.code.ValueUtil.asRegister;
import static jdk.vm.ci.sparc.SPARC.g0;

import org.graalvm.compiler.asm.sparc.SPARCAddress;
import org.graalvm.compiler.asm.sparc.SPARCMacroAssembler;
import org.graalvm.compiler.hotspot.GraalHotSpotVMConfig;
import org.graalvm.compiler.lir.LIRFrameState;
import org.graalvm.compiler.lir.LIRInstructionClass;
import org.graalvm.compiler.lir.Opcode;
import org.graalvm.compiler.lir.asm.CompilationResultBuilder;
import org.graalvm.compiler.lir.gen.LIRGeneratorTool;
import org.graalvm.compiler.lir.sparc.SPARCLIRInstruction;

import jdk.vm.ci.code.Register;
import jdk.vm.ci.code.ValueUtil;
import jdk.vm.ci.code.site.InfopointReason;
import jdk.vm.ci.meta.AllocatableValue;

/**
 * Emits a safepoint poll.
 */
@Opcode("SAFEPOINT")
public class SPARCHotSpotSafepointOp extends SPARCLIRInstruction {
    public static final LIRInstructionClass<SPARCHotSpotSafepointOp> TYPE = LIRInstructionClass.create(SPARCHotSpotSafepointOp.class);
    public static final SizeEstimate SIZE = SizeEstimate.create(9);

    @State protected LIRFrameState state;
    @Use({OperandFlag.REG}) AllocatableValue safepointPollAddress;
    private final GraalHotSpotVMConfig config;

    public SPARCHotSpotSafepointOp(LIRFrameState state, GraalHotSpotVMConfig config, LIRGeneratorTool tool) {
        super(TYPE, SIZE);
        this.state = state;
        this.config = config;
        SPARCHotSpotLIRGenerator lirGen = (SPARCHotSpotLIRGenerator) tool;
        safepointPollAddress = lirGen.getSafepointAddressValue();
    }

    @Override
    public void emitCode(CompilationResultBuilder crb, SPARCMacroAssembler masm) {
        emitCode(crb, masm, config, false, state, asRegister(safepointPollAddress));
    }

    public static void emitCode(CompilationResultBuilder crb, SPARCMacroAssembler masm, GraalHotSpotVMConfig config, boolean atReturn, LIRFrameState state, Register safepointPollAddress) {
        crb.recordMark(atReturn ? config.MARKID_POLL_RETURN_FAR : config.MARKID_POLL_FAR);
        if (state != null) {
            final int pos = masm.position();
            crb.recordInfopoint(pos, state, InfopointReason.SAFEPOINT);
        }
        masm.ldx(new SPARCAddress(safepointPollAddress, 0), g0);
    }

    public static class SPARCLoadSafepointPollAddress extends SPARCLIRInstruction {
        public static final LIRInstructionClass<SPARCLoadSafepointPollAddress> TYPE = LIRInstructionClass.create(SPARCLoadSafepointPollAddress.class);
        public static final SizeEstimate SIZE = SizeEstimate.create(2);

        @Def({OperandFlag.REG}) protected AllocatableValue result;
        private final GraalHotSpotVMConfig config;

        public SPARCLoadSafepointPollAddress(AllocatableValue result, GraalHotSpotVMConfig config) {
            super(TYPE, SIZE);
            this.result = result;
            this.config = config;
        }

        @Override
        public void emitCode(CompilationResultBuilder crb, SPARCMacroAssembler masm) {
            masm.setx(config.safepointPollingAddress, ValueUtil.asRegister(result), false);
        }
    }
}
