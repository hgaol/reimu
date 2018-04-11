package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Index8Instruction implements Instruction{

  protected int index;

  @Override
  public void fetchOperands(BytecodeReader reader) {
    this.index = Byte.toUnsignedInt(reader.getByte());
  }

  @Override
  public void execute(Frame frame) {
    //
  }

  public int getIndex() {
    return index;
  }

  public Index8Instruction setIndex(int index) {
    this.index = index;
    return this;
  }
}
