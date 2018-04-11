package com.github.hgaol.reimu.instructions.constants;

import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class IPush {

  public static class BIPush implements Instruction {
    int val;
    @Override
    public void fetchOperands(BytecodeReader reader) {
      this.val = reader.getByte();
    }

    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(val);
    }
  }

  public static class SIPush implements Instruction {
    int val;
    @Override
    public void fetchOperands(BytecodeReader reader) {
      this.val = Short.toUnsignedInt(reader.getShort());
    }
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(val);
    }
  }

}
