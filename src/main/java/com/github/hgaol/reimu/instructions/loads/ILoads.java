package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class ILoads {
  public static class ILoad extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      iload(frame, index);
    }
  }

  public static class ILoad0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      iload(frame, 0);
    }
  }

  public static class ILoad1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      iload(frame, 1);
    }
  }

  public static class ILoad2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      iload(frame, 2);
    }
  }

  public static class ILoad3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      iload(frame, 3);
    }
  }

  private static void iload(Frame frame, int index) {
    int val = frame.getLocalVars().getInt(index);
    frame.getOperandStack().pushInt(val);
  }
}
