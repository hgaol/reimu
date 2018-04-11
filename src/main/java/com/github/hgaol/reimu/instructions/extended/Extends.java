package com.github.hgaol.reimu.instructions.extended;

import com.github.hgaol.reimu.instructions.base.BranchInstruction;
import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.instructions.loads.*;
import com.github.hgaol.reimu.instructions.math.Incs;
import com.github.hgaol.reimu.instructions.stores.*;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Extends {
  public static class Wide implements Instruction {

    private Instruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) {
      int opcode = Byte.toUnsignedInt(reader.getByte());
      Index8Instruction inst;
      switch (opcode) {
        case 0x15:
          inst = new ILoads.ILoad();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x16:
          inst = new LLoads.LLoad();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x17:
          inst = new FLoads.FLoad();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x18:
          inst = new DLoads.DLoad();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x19:
          inst = new ALoads.ALoad();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x36:
          inst = new IStores.IStore();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x37:
          inst = new LStores.LStore();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x38:
          inst = new FStores.FStore();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x39:
          inst = new DStores.DStore();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x3a:
          inst = new AStores.AStore();
          inst.setIndex(reader.getUnsignedShort());
          this.modifiedInstruction = inst;
          break;
        case 0x84:
          Incs.IInc insInc = new Incs.IInc();
          insInc.setIndex(reader.getUnsignedShort());
          insInc.setNum(reader.getShort());
          this.modifiedInstruction = insInc;
          break;
        case 0xa9:
          // ret
        default:
          throw new Error("Unsupported opcode: 0xa9!");
      }
    }

    @Override
    public void execute(Frame frame) {
      modifiedInstruction.execute(frame);
    }
  }

  public static class IfNull extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      ReObject ref = frame.getOperandStack().popRef();
      if (ref == null) {
        branch(frame);
      }
    }
  }

  public static class IfNonNull extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
      ReObject ref = frame.getOperandStack().popRef();
      if (ref != null) {
        branch(frame);
      }
    }
  }

  public static class GotoW extends BranchInstruction {
    @Override
    public void fetchOperands(BytecodeReader reader) {
      this.offset = reader.getInt();
    }

    @Override
    public void execute(Frame frame) {
      branch(frame);
    }
  }

}
