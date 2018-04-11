package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Index16Instruction implements Instruction {
  protected int index;
  @Override
  public void fetchOperands(BytecodeReader reader) {
    this.index = Short.toUnsignedInt(reader.getShort());
  }

  @Override
  public void execute(Frame frame) {
    // nothing
  }
}
