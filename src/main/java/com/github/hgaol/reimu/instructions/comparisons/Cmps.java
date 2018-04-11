package com.github.hgaol.reimu.instructions.comparisons;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Cmps {
  public static class LCmp extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long v2 = stack.popLong();
      long v1 = stack.popLong();
      if (v1 > v2) {
        stack.pushInt(1);
      } else if (v1 == v2) {
        stack.pushInt(0);
      } else {
        stack.pushInt(-1);
      }
    }
  }

  public static class FCmpg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fcmp(frame, true);
    }
  }

  public static class FCmpl extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fcmp(frame, false);
    }
  }

  private static void fcmp(Frame frame, boolean flag) {
    OperandStack stack = frame.getOperandStack();
    float v2 = stack.popFloat();
    float v1 = stack.popFloat();
    if (v1 > v2) {
      stack.pushInt(1);
    } else if (v1 == v2) {
      stack.pushInt(0);
    } else if (v1 < v2) {
      stack.pushInt(-1);
    } else if (flag) {
      stack.pushInt(1);
    } else {
      stack.pushInt(-1);
    }
  }

  public static class DCmpg extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dcmp(frame, true);
    }
  }

  public static class DCmpl extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dcmp(frame, false);
    }
  }

  private static void dcmp(Frame frame, boolean flag) {
    OperandStack stack = frame.getOperandStack();
    double v2 = stack.popDouble();
    double v1 = stack.popDouble();
    if (v1 > v2) {
      stack.pushInt(1);
    } else if (v1 == v2) {
      stack.pushInt(0);
    } else if (v1 < v2) {
      stack.pushInt(-1);
    } else if (flag) {
      stack.pushInt(1);
    } else {
      stack.pushInt(-1);
    }
  }

}
