package com.github.hgaol.reimu.instructions.conversions;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class L2x {
  public static class L2b extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushInt((byte) val);
    }
  }

  public static class L2c extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushInt((char) val);
    }
  }

  public static class L2s extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushInt((short) val);
    }
  }

  public static class L2i extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushInt((int) val);
    }
  }

  public static class L2f extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushFloat(val);
    }
  }

  public static class L2d extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      stack.pushDouble(val);
    }
  }
}
