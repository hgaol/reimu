package com.github.hgaol.reimu.instructions.comparisons;

import com.github.hgaol.reimu.instructions.base.BranchInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class IfCmp {

  public static class IfIcmpEQ extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) == 0) {
        branch(frame);
      }
    }
  }

  public static class IfIcmpNE extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) != 0) {
        branch(frame);
      }
    }
  }

  public static class IfIcmpLT extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) < 0) {
        branch(frame);
      }
    }
  }

  public static class IfIcmpLE extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) <= 0) {
        branch(frame);
      }
    }
  }

  public static class IfIcmpGT extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) > 0) {
        branch(frame);
      }
    }
  }

  public static class IfIcmpGE extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (icmp(frame) >= 0) {
        branch(frame);
      }
    }
  }

  private static int icmp(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    int v2 = stack.popInt();
    int v1 = stack.popInt();
    if (v1 < v2) {
      return -1;
    } else if (v1 == v2) {
      return 0;
    } else {
      return 1;
    }
  }

  public static class IfAcmpEQ extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (acmp(frame)) {
        branch(frame);
      }
    }
  }

  public static class IfAcmpNE extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      if (!acmp(frame)) {
        branch(frame);
      }
    }
  }

  private static boolean acmp(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    ReObject ref2 = stack.popRef();
    ReObject ref1 = stack.popRef();
    // TODO
    return ref1 == ref2;
  }

}
