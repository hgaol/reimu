package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.nativee.java.lang.NaThrowable;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.StringPool;

/**
 * @author Gao Han
 * @date: 2018年05月02日
 */
public class AThrow extends NoOperandsInstruction {
  @Override
  public void execute(Frame frame) {
    ReObject ex = frame.getOperandStack().popRef();
    if (ex == null) {
      throw new Error("java.lang.NullPointerException");
    }

    Thread thread = frame.getThread();
    if (!findAndGotoExceptionHandler(thread, ex)) {
      handleUncaughtException(thread, ex);
    }
  }

  private boolean findAndGotoExceptionHandler(Thread thread, ReObject ex) {
    while (true) {
      Frame frame = thread.topFrame();
      int pc = frame.getNextPc() - 1;

      int handlerPc = frame.getMethod().findExceptionHandler(ex.getClazz(), pc);
      // 找到了异常处理项
      if (handlerPc > 0) {
        OperandStack stack = frame.getOperandStack();
        stack.clear();
        stack.pushRef(ex);
        frame.setNextPc(handlerPc);
        return true;
      }

      // 当前frame没有找到对应的异常处理项，往上继续找
      thread.popFrame();
      if (thread.isStackEmpty()) {
        break;
      }
    }
    return false;
  }

  private void handleUncaughtException(Thread thread, ReObject ex) {
    thread.clearStack();

    ReObject jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;");
    System.err.println(ex.getClazz().getJavaName() + ":" + StringPool.getOrigString(jMsg));

    NaThrowable.StackTraceElement[] stes = (NaThrowable.StackTraceElement[]) ex.getExtra();
    System.err.println("Exception " + ex.getClazz().getName());
    for (int i = stes.length - 1; i >= 0; i--) {
      System.err.println("\t\t" + stes[i]);
    }
    System.exit(1);
  }

}
