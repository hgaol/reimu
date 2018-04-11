package com.github.hgaol.reimu.instructions.math;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Shs {
  public static class IShl extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      int v1 = frame.getOperandStack().popInt();
      frame.getOperandStack().pushInt(v1 << v2);
    }
  }

  public static class IShr extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      int v1 = frame.getOperandStack().popInt();
      frame.getOperandStack().pushInt(v1 >> v2);
    }
  }

  public static class IUShr extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      int v1 = frame.getOperandStack().popInt();
      frame.getOperandStack().pushInt(v1 >>> v2);
    }
  }

  public static class LShl extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      long v1 = frame.getOperandStack().popLong();
      frame.getOperandStack().pushLong(v1 << v2);
    }
  }

  public static class LShr extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      long v1 = frame.getOperandStack().popLong();
      frame.getOperandStack().pushLong(v1 >> v2);
    }
  }

  public static class LUShr extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      int v2 = frame.getOperandStack().popInt();
      long v1 = frame.getOperandStack().popLong();
      frame.getOperandStack().pushLong(v1 >>> v2);
    }
  }
}
