package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classfile.attribute.CodeAttribute;
import com.github.hgaol.reimu.instructions.InstructionFactory;
import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.classfile.MemberInfo;
import com.github.hgaol.reimu.classfile.attribute.CodeAttribute;
import com.github.hgaol.reimu.instructions.InstructionFactory;
import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.github.hgaol.reimu.util.EchoUtils.echof;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Interpreter {

  public static void interpret(MemberInfo methodInfo) {
    CodeAttribute codeAttr = methodInfo.getCodeAttribute();

    Thread thread = new Thread();
    Frame frame = thread.newFrame(codeAttr.maxLocals, codeAttr.maxStack);
    thread.pushFrame(frame);

    loop(thread, codeAttr.code);
  }

  private static void loop(Thread thread, byte[] bytecode) {
    Frame frame = thread.popFrame();
    BytecodeReader reader = new BytecodeReader();
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());

    while (true) {
      int pc = frame.getNextPc();
      thread.setPc(pc);

      // decode
      reader.reset(bytecode, pc);
      byte opcode = reader.getByte();
      Instruction inst = InstructionFactory.newInstruction(opcode);
      assert inst != null;
      inst.fetchOperands(reader);
      frame.setNextPc(reader.getPc());

      // execute
      echof("pc: 0x%-2d inst: %s %s \n\tlocal-vars: %s, \n\toperand-stack: %s\n",
          pc, inst.getClass().getSimpleName(),
          ToStringBuilder.reflectionToString(inst, ToStringStyle.SIMPLE_STYLE),
          ToStringBuilder.reflectionToString(frame.getLocalVars()),
          ToStringBuilder.reflectionToString(frame.getOperandStack()));
      inst.execute(frame);
    }
  }

}
