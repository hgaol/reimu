package com.github.hgaol.reimu.rtda;


/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Frame {
  private Frame lower;
  private LocalVars localVars;
  private OperandStack operandStack;
  private Thread thread;
  private int nextPc;

  public Frame(Thread thread, int maxLocalVars, int maxOperandStack) {
    this.thread = thread;
    this.localVars = new LocalVars(maxLocalVars);
    this.operandStack = new OperandStack(maxOperandStack);
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

  public Frame setNextPc(int pc) {
    this.nextPc = pc;
    return this;
  }
}
