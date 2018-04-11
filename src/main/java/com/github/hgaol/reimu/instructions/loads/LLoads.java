package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class LLoads {
  public static class LLoad extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      lload(frame, index);
    }
  }

  public static class LLoad0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lload(frame, 0);
    }
  }

  public static class LLoad1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lload(frame, 1);
    }
  }

  public static class LLoad2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lload(frame, 2);
    }
  }

  public static class LLoad3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lload(frame, 3);
    }
  }

  private static void lload(Frame frame, int index) {
    long val = frame.getLocalVars().getLong(index);
    frame.getOperandStack().pushLong(val);
  }
}
