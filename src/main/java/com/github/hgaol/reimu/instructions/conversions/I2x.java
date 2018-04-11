package com.github.hgaol.reimu.instructions.conversions;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class I2x {
  public static class I2b extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushInt((byte) val);
    }
  }

  public static class I2c extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushInt((char) val);
    }
  }

  public static class I2s extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushInt((short) val);
    }
  }

  public static class I2l extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushLong(val);
    }
  }

  public static class I2f extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushFloat(val);
    }
  }

  public static class I2d extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      stack.pushDouble(val);
    }
  }
}
