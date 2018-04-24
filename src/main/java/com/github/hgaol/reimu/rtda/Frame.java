package com.github.hgaol.reimu.rtda;

import com.github.hgaol.reimu.rtda.heap.Class;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Frame {
  private Frame lower;
  private LocalVars localVars;
  private OperandStack operandStack;
  private Thread thread;
  private Class.Method method;
  private int nextPc;

  public Frame(Thread thread, Class.Method method) {
    this.thread = thread;
    this.method = method;
    this.localVars = new LocalVars(method.getMaxLocals());
    this.operandStack = new OperandStack(method.getMaxStack());
  }

  public Frame getLower() {
    return lower;
  }

  public Frame setLower(Frame lower) {
    this.lower = lower;
    return this;
  }

  public LocalVars getLocalVars() {
    return localVars;
  }

  public Frame setLocalVars(LocalVars localVars) {
    this.localVars = localVars;
    return this;
  }

  public OperandStack getOperandStack() {
    return operandStack;
  }

  public Frame setOperandStack(OperandStack operandStack) {
    this.operandStack = operandStack;
    return this;
  }

  public Thread getThread() {
    return thread;
  }

  public int getNextPc() {
    return nextPc;
  }

  public Class.Method getMethod() {
    return method;
  }

  public Frame setMethod(Class.Method method) {
    this.method = method;
    return this;
  }

  public Frame setNextPc(int pc) {
    this.nextPc = pc;
    return this;
  }
}
