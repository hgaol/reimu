package com.github.hgaol.reimu.rtda;

import com.github.hgaol.reimu.rtda.heap.ReClass;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Thread {
  private int pc;
  private Stack stack;

  public Thread() {
    stack = new Stack(1024);
  }

  public int getPc() {
    return pc;
  }

  public Thread setPc(int pc) {
    this.pc = pc;
    return this;
  }

  public void pushFrame(Frame frame) {
    stack.push(frame);
  }

  public Frame newFrame(ReClass.Method method) {
    return new Frame(this, method);
  }

  public Frame popFrame() {
    return stack.pop();
  }

  public Frame topFrame() {
    return stack.top();
  }

  public boolean isStackEmpty() {
    return stack.isEmpty();
  }

}
