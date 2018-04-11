package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class FLoads {
  public static class FLoad extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      fload(frame, index);
    }
  }

  public static class FLoad0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fload(frame, 0);
    }
  }

  public static class FLoad1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fload(frame, 1);
    }
  }

  public static class FLoad2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fload(frame, 2);
    }
  }

  public static class FLoad3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fload(frame, 3);
    }
  }

  private static void fload(Frame frame, int index) {
    float val = frame.getLocalVars().getFloat(index);
    frame.getOperandStack().pushFloat(val);
  }
}
