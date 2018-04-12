package com.github.hgaol.reimu.instructions.control;

import com.github.hgaol.reimu.instructions.base.BranchInstruction;
import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Controls {
  public static class Goto extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      branch(frame);
    }
  }

  /**
   * 非顺序的
   */
  public static class LookupSwitch extends BranchInstruction {

    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
      reader.skipPadding();
      defaultOffset = reader.getInt();
      npairs = reader.getInt();
      matchOffsets = reader.getInts(npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
      int key = frame.getOperandStack().popInt();
      for (int i = 0; i < this.npairs * 2; i += 2) {
        if (this.matchOffsets[i] == key) {
          branch(frame, this.matchOffsets[i + 1]);
          return;
        }
      }
      branch(frame, defaultOffset);
    }
  }

  /**
   * 对应顺序的
   */
  public static class TableSwitch extends BranchInstruction {
    private int defaultOffset;
    private int low;
    private int high;
    private int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
      reader.skipPadding();
      defaultOffset = reader.getInt();
      low = reader.getInt();
      high = reader.getInt();
      jumpOffsets = reader.getInts(high - low + 1);
    }

    @Override
    public void execute(Frame frame) {
      int index = frame.getOperandStack().popInt();
      int offset = defaultOffset;
      if (low <= index && index <= high) {
        offset = jumpOffsets[index - low];
      }

      branch(frame, offset);
    }
  }


}
