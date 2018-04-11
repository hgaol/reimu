package com.github.hgaol.reimu.instructions.math;

import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.LocalVars;

/**
 * Increment local variable by constant
 *
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Incs {

  public static class IInc implements Instruction {

    public int index;
    public int num;

    @Override
    public void fetchOperands(BytecodeReader reader) {
      this.index = Byte.toUnsignedInt(reader.getByte());
      this.num = reader.getByte();
    }

    @Override
    public void execute(Frame frame) {
      LocalVars localVars = frame.getLocalVars();
      int val = localVars.getInt(index);
      val += num;
      localVars.setInt(index, val);
    }

    public int getIndex() {
      return index;
    }

    public IInc setIndex(int index) {
      this.index = index;
      return this;
    }

    public int getNum() {
      return num;
    }

    public IInc setNum(int num) {
      this.num = num;
      return this;
    }
  }
}
