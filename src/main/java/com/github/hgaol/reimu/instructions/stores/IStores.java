package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class IStores {
  public static class IStore extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      istore(frame, index);
    }
  }

  public static class IStore0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      istore(frame, 0);
    }
  }

  public static class IStore1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      istore(frame, 1);
    }
  }

  public static class IStore2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      istore(frame, 2);
    }
  }

  public static class IStore3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      istore(frame, 3);
    }
  }

  private static void istore(Frame frame, int index) {
    int val = frame.getOperandStack().popInt();
    frame.getLocalVars().setInt(index, val);
  }
}
