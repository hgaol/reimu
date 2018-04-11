package com.github.hgaol.reimu.instructions.constants;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * 把常量push到操作数栈
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Const {
  public static class AconstNull extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushRef(null);
    }
  }

  public static class DConst0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushDouble(0.0);
    }
  }

  public static class DConst1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushDouble(1.0);
    }
  }

  public static class FConst0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushFloat(0.0f);
    }
  }

  public static class FConst1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushFloat(0.0f);
    }
  }

  public static class FConst2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushFloat(0.0f);
    }
  }

  public static class IConstM1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(-1);
    }
  }

  public static class IConst0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(0);
    }
  }

  public static class IConst1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(1);
    }
  }

  public static class IConst2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(2);
    }
  }

  public static class IConst3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(3);
    }
  }

  public static class IConst4 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(4);
    }
  }

  public static class IConst5 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushInt(5);
    }
  }

  public static class LConst0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushLong(0L);
    }
  }

  public static class LConst1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getOperandStack().pushLong(1L);
    }
  }

}
