package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class BranchInstruction implements Instruction {

  protected int offset;

  @Override
  public void fetchOperands(BytecodeReader reader) {
    this.offset = (int) reader.getShort();
  }

  @Override
  public void execute(Frame frame) {
    // TODO
  }

  public void branch(Frame frame) {
    int pc = frame.getThread().getPc();
    int nextPc = pc + offset;
    frame.setNextPc(nextPc);
  }

  public void branch(Frame frame, int offset) {
    int pc = frame.getThread().getPc();
    int nextPc = pc + offset;
    frame.setNextPc(nextPc);
  }
}
