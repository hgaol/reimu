package com.github.hgaol.reimu.instructions.conversions;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class D2x {
  public static class D2b extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushInt((byte) val);
    }
  }

  public static class D2c extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushInt((char) val);
    }
  }

  public static class D2s extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushInt((short) val);
    }
  }

  public static class D2l extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushLong(Double.doubleToLongBits(val));
    }
  }

  public static class D2i extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushInt((int) Double.doubleToLongBits(val));
    }
  }

  public static class D2f extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      stack.pushFloat((float) val);
    }
  }
}
