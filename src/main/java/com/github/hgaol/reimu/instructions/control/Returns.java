package com.github.hgaol.reimu.instructions.control;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * 返回指令，从
 * @author Gao Han
 * @date: 2018年04月25日
 */
public class Returns {

  public static class Return extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      frame.getThread().popFrame();
    }
  }

  /**
   * Return reference from method
   */
  public static class AReturn extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Thread thread = frame.getThread();
      Frame curFrame = thread.popFrame();
      Frame invokerFrame = thread.topFrame();

      ReObject ref = curFrame.getOperandStack().popRef();
      invokerFrame.getOperandStack().pushRef(ref);
    }
  }

  /**
   * Return double from method
   */
  public static class DReturn extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Thread thread = frame.getThread();
      Frame curFrame = thread.popFrame();
      Frame invokerFrame = thread.topFrame();

      double val = curFrame.getOperandStack().popDouble();
      invokerFrame.getOperandStack().pushDouble(val);
    }
  }

  /**
   * Return float from method
   */
  public static class FReturn extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Thread thread = frame.getThread();
      Frame curFrame = thread.popFrame();
      Frame invokerFrame = thread.topFrame();

      float val = curFrame.getOperandStack().popFloat();
      invokerFrame.getOperandStack().pushFloat(val);
    }
  }

  /**
   * Return int from method
   */
  public static class IReturn extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Thread thread = frame.getThread();
      Frame curFrame = thread.popFrame();
      Frame invokerFrame = thread.topFrame();

      int val = curFrame.getOperandStack().popInt();
      invokerFrame.getOperandStack().pushInt(val);
    }
  }

  /**
   * Return double from method
   */
  public static class LReturn extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      Thread thread = frame.getThread();
      Frame curFrame = thread.popFrame();
      Frame invokerFrame = thread.topFrame();

      long val = curFrame.getOperandStack().popLong();
      invokerFrame.getOperandStack().pushLong(val);
    }
  }

}
