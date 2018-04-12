package com.github.hgaol.reimu.rtda;

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

  public Frame newFrame(int maxLocals, int maxStack) {
    return new Frame(this, maxLocals, maxStack);
  }

  public Frame popFrame() {
    return stack.pop();
  }

  public Frame currentFrame() {
    return stack.top();
  }

}
