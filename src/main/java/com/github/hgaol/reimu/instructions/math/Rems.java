package com.github.hgaol.reimu.instructions.math;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Rems {
  public static class IRem extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      int v1 = frame.getOperandStack().popInt();
      if (v2 == 0) {
        throw new Error("java.lang.ArithmeticException: / by zero");
      }
      frame.getOperandStack().pushInt(v1 % v2);
    }
  }

  public static class FRem extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      float v2 = frame.getOperandStack().popFloat();
      float v1 = frame.getOperandStack().popFloat();
      frame.getOperandStack().pushFloat(v1 % v2);
    }
  }

  public static class LRem extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      long v2 = frame.getOperandStack().popLong();
      long v1 = frame.getOperandStack().popLong();
      if (v2 == 0) {
        throw new Error("java.lang.ArithmeticException: / by zero");
      }
      frame.getOperandStack().pushLong(v1 % v2);
    }
  }

  public static class DRem extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      double v2 = frame.getOperandStack().popDouble();
      double v1 = frame.getOperandStack().popDouble();
      frame.getOperandStack().pushDouble(v1 % v2);
    }
  }
}
