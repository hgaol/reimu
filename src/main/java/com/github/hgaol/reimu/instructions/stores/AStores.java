package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class AStores {
  public static class AStore extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      astore(frame, index);
    }
  }

  public static class AStore0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      astore(frame, 0);
    }
  }

  public static class AStore1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      astore(frame, 1);
    }
  }

  public static class AStore2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      astore(frame, 2);
    }
  }

  public static class AStore3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      astore(frame, 3);
    }
  }

  private static void astore(Frame frame, int index) {
    ReObject val = frame.getOperandStack().popRef();
    frame.getLocalVars().setRef(index, val);
  }
}
