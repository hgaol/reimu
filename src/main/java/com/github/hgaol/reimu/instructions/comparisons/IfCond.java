package com.github.hgaol.reimu.instructions.comparisons;

import com.github.hgaol.reimu.instructions.base.BranchInstruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class IfCond {
  public static class Ifeq extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val == 0) {
        branch(frame);
      }
    }
  }

  public static class Ifne extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val != 0) {
        branch(frame);
      }
    }
  }

  public static class Iflt extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val < 0) {
        branch(frame);
      }
    }
  }

  public static class Ifle extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val <= 0) {
        branch(frame);
      }
    }
  }

  public static class Ifgt extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val > 0) {
        branch(frame);
      }
    }
  }

  public static class Ifge extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      int val = frame.getOperandStack().popInt();
      if (val >= 0) {
        branch(frame);
      }
    }
  }
}
