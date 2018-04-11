package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class NoOperandsInstruction implements Instruction {
  @Override
  public void fetchOperands(BytecodeReader reader) {
    // do nothing
  }

  @Override
  public void execute(Frame frame) {
    // do nothing
  }
}
