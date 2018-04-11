package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class FStores {
  public static class FStore extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      fstore(frame, index);
    }
  }

  public static class FStore0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fstore(frame, 0);
    }
  }

  public static class FStore1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fstore(frame, 1);
    }
  }

  public static class FStore2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fstore(frame, 2);
    }
  }

  public static class FStore3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      fstore(frame, 3);
    }
  }

  private static void fstore(Frame frame, int index) {
    float val = frame.getOperandStack().popFloat();
    frame.getLocalVars().setFloat(index, val);
  }
}
