package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class DStores {
  public static class DStore extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      dstore(frame, index);
    }
  }

  public static class DStore0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dstore(frame, 0);
    }
  }

  public static class DStore1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dstore(frame, 1);
    }
  }

  public static class DStore2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dstore(frame, 2);
    }
  }

  public static class DStore3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      dstore(frame, 3);
    }
  }

  private static void dstore(Frame frame, int index) {
    double val = frame.getOperandStack().popDouble();
    frame.getLocalVars().setDouble(index, val);
  }
}
