package com.github.hgaol.reimu.instructions.stack;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Pops {
  public static class Pop extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().popSlot();
    }
  }

  public static class Pop2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().popSlot();
      frame.getOperandStack().popSlot();
    }
  }
}
