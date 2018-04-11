package com.github.hgaol.reimu.instructions.math;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Negs {
  public static class INeg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      frame.getOperandStack().pushInt(-val);
    }
  }

  public static class FNeg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      float val = frame.getOperandStack().popFloat();
      frame.getOperandStack().pushFloat(-val);
    }
  }

  public static class LNeg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      long val = frame.getOperandStack().popLong();
      frame.getOperandStack().pushLong(-val);
    }
  }

  public static class DNeg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      double val = frame.getOperandStack().popDouble();
      frame.getOperandStack().pushDouble(-val);
    }
  }
}
