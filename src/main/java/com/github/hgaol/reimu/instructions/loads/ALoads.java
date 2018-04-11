package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class ALoads {
  public static class ALoad extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      aload(frame, index);
    }
  }

  public static class ALoad0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      aload(frame, 0);
    }
  }

  public static class ALoad1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      aload(frame, 1);
    }
  }

  public static class ALoad2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      aload(frame, 2);
    }
  }

  public static class ALoad3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      aload(frame, 3);
    }
  }

  private static void aload(Frame frame, int index) {
    ReObject val = frame.getLocalVars().getRef(index);
    frame.getOperandStack().pushRef(val);
  }
}
