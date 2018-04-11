package com.github.hgaol.reimu.instructions.stack;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.Slot;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Swaps {
  public static class Swap extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      stack.pushSlot(slot1);
      stack.pushSlot(slot2);
    }
  }
}
