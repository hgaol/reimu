package com.github.hgaol.reimu.instructions.stack;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.Slot;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Dups {
  public static class Dup extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Slot slot = frame.getOperandStack().popSlot();
      frame.getOperandStack().pushSlot(slot);
      frame.getOperandStack().pushSlot(slot);
    }
  }

  public static class DupX1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      stack.pushSlot(slot1);
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
    }
  }

  public static class DupX2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      Slot slot3 = stack.popSlot();
      stack.pushSlot(slot1);
      stack.pushSlot(slot3);
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
    }
  }

  public static class Dup2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
    }
  }

  public static class Dup2X1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      Slot slot3 = stack.popSlot();
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
      stack.pushSlot(slot3);
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
    }
  }

  public static class Dup2X2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      Slot slot1 = stack.popSlot();
      Slot slot2 = stack.popSlot();
      Slot slot3 = stack.popSlot();
      Slot slot4 = stack.popSlot();
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
      stack.pushSlot(slot4);
      stack.pushSlot(slot3);
      stack.pushSlot(slot2);
      stack.pushSlot(slot1);
    }
  }

}
