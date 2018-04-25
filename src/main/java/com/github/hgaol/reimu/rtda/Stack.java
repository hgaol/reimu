package com.github.hgaol.reimu.rtda;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Stack {

  private int maxStack;
  private int size;
  private Frame top;

  public Stack(int maxSize) {
    this.maxStack = maxSize;
  }

  public void push(Frame frame) {
    if (size >= maxStack) {
      throw new Error("java.lang.StackOverflowError");
    }
    if (top != null) {
      frame.setLower(top);
    }
    top = frame;
    size++;
  }

  public Frame pop() {
    if (top == null) {
      throw new Error("jvm stack is empty!");
    }
    Frame ret = top;
    top = top.getLower();
    size--;
    return ret;
  }

  public Frame top() {
    if (size >= maxStack) {
      throw new Error("java.lang.StackOverflowError");
    }
    return top;
  }

  public boolean isEmpty() {
    return top == null;
  }
}
