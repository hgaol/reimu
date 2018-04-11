package com.github.hgaol.reimu.instructions.conversions;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class F2x {
  public static class F2b extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushInt((byte) val);
    }
  }

  public static class F2c extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushInt((char) val);
    }
  }

  public static class F2s extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushInt((short) val);
    }
  }

  public static class F2l extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushLong(Float.floatToIntBits(val));
    }
  }

  public static class F2i extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushInt(Float.floatToIntBits(val));
    }
  }

  public static class F2d extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      stack.pushDouble(val);
    }
  }
}
