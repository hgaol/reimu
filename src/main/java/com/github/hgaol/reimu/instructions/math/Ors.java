package com.github.hgaol.reimu.instructions.math;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Ors {
  public static class IAdd extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      int v1 = frame.getOperandStack().popInt();
      frame.getOperandStack().pushInt(v1 | v2);
    }
  }

  public static class LAdd extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      long v2 = frame.getOperandStack().popLong();
      long v1 = frame.getOperandStack().popLong();
      frame.getOperandStack().pushLong(v1 | v2);
    }
  }

}
