package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class LStores {
  public static class LStore extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      lstore(frame, index);
    }
  }

  public static class LStore0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lstore(frame, 0);
    }
  }

  public static class LStore1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lstore(frame, 1);
    }
  }

  public static class LStore2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lstore(frame, 2);
    }
  }

  public static class LStore3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      lstore(frame, 3);
    }
  }

  private static void lstore(Frame frame, int index) {
    long val = frame.getOperandStack().popLong();
    frame.getLocalVars().setLong(index, val);
  }
}
