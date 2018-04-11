package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class DLoads {
  public static class DLoad extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      dload(frame, index);
    }
  }

  public static class DLoad0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dload(frame, 0);
    }
  }

  public static class DLoad1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dload(frame, 1);
    }
  }

  public static class DLoad2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dload(frame, 2);
    }
  }

  public static class DLoad3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dload(frame, 3);
    }
  }

  private static void dload(Frame frame, int index) {
    double val = frame.getLocalVars().getDouble(index);
    frame.getOperandStack().pushDouble(val);
  }
}
