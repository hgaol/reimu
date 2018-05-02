package com.github.hgaol.reimu.instructions;

import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.instructions.comparisons.Cmps;
import com.github.hgaol.reimu.instructions.comparisons.IfCmp;
import com.github.hgaol.reimu.instructions.comparisons.IfCond;
import com.github.hgaol.reimu.instructions.constants.Const;
import com.github.hgaol.reimu.instructions.constants.IPush;
import com.github.hgaol.reimu.instructions.constants.Ldcs;
import com.github.hgaol.reimu.instructions.constants.Nop;
import com.github.hgaol.reimu.instructions.control.Controls;
import com.github.hgaol.reimu.instructions.control.Returns;
import com.github.hgaol.reimu.instructions.conversions.D2x;
import com.github.hgaol.reimu.instructions.conversions.F2x;
import com.github.hgaol.reimu.instructions.conversions.I2x;
import com.github.hgaol.reimu.instructions.conversions.L2x;
import com.github.hgaol.reimu.instructions.extended.Extends;
import com.github.hgaol.reimu.instructions.loads.*;
import com.github.hgaol.reimu.instructions.math.*;
import com.github.hgaol.reimu.instructions.references.*;
import com.github.hgaol.reimu.instructions.reserved.InvokeNative;
import com.github.hgaol.reimu.instructions.stack.Dups;
import com.github.hgaol.reimu.instructions.stack.Pops;
import com.github.hgaol.reimu.instructions.stack.Swaps;
import com.github.hgaol.reimu.instructions.stores.*;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class InstructionFactory {

  // 以下为没有操作数的
  private static Instruction Nop = new Nop();
  private static Instruction AConstNull = new Const.AconstNull();
  private static Instruction IConstM1 = new Const.IConstM1();
  private static Instruction IConst0 = new Const.IConst0();
  private static Instruction IConst1 = new Const.IConst1();
  private static Instruction IConst2 = new Const.IConst2();
  private static Instruction IConst3 = new Const.IConst3();
  private static Instruction IConst4 = new Const.IConst4();
  private static Instruction IConst5 = new Const.IConst5();
  private static Instruction LConst0 = new Const.LConst0();
  private static Instruction LConst1 = new Const.LConst1();
  private static Instruction FConst0 = new Const.FConst0();
  private static Instruction FConst1 = new Const.FConst1();
  private static Instruction FConst2 = new Const.FConst2();
  private static Instruction DConst0 = new Const.DConst0();
  private static Instruction DConst1 = new Const.DConst1();

  private static Instruction ILoad0 = new ILoads.ILoad0();
  private static Instruction ILoad1 = new ILoads.ILoad1();
  private static Instruction ILoad2 = new ILoads.ILoad2();
  private static Instruction ILoad3 = new ILoads.ILoad3();
  private static Instruction LLoad0 = new LLoads.LLoad0();
  private static Instruction LLoad1 = new LLoads.LLoad1();
  private static Instruction LLoad2 = new LLoads.LLoad2();
  private static Instruction LLoad3 = new LLoads.LLoad3();
  private static Instruction FLoad0 = new FLoads.FLoad0();
  private static Instruction FLoad1 = new FLoads.FLoad1();
  private static Instruction FLoad2 = new FLoads.FLoad2();
  private static Instruction FLoad3 = new FLoads.FLoad3();
  private static Instruction DLoad0 = new DLoads.DLoad0();
  private static Instruction DLoad1 = new DLoads.DLoad1();
  private static Instruction DLoad2 = new DLoads.DLoad2();
  private static Instruction DLoad3 = new DLoads.DLoad3();
  private static Instruction ALoad0 = new ALoads.ALoad0();
  private static Instruction ALoad1 = new ALoads.ALoad1();
  private static Instruction ALoad2 = new ALoads.ALoad2();
  private static Instruction ALoad3 = new ALoads.ALoad3();
  private static Instruction IALoad = new XAloads.IAload();
  private static Instruction LALoad = new XAloads.LAload();
  private static Instruction FALoad = new XAloads.FAload();
  private static Instruction DALoad = new XAloads.DAload();
  private static Instruction AALoad = new XAloads.AAload();
  private static Instruction BALoad = new XAloads.BAload();
  private static Instruction CALoad = new XAloads.CAload();
  private static Instruction SALoad = new XAloads.SAload();

  private static Instruction IStore0 = new IStores.IStore0();
  private static Instruction IStore1 = new IStores.IStore1();
  private static Instruction IStore2 = new IStores.IStore2();
  private static Instruction IStore3 = new IStores.IStore3();
  private static Instruction LStore0 = new LStores.LStore0();
  private static Instruction LStore1 = new LStores.LStore1();
  private static Instruction LStore2 = new LStores.LStore2();
  private static Instruction LStore3 = new LStores.LStore3();
  private static Instruction FStore0 = new FStores.FStore0();
  private static Instruction FStore1 = new FStores.FStore1();
  private static Instruction FStore2 = new FStores.FStore2();
  private static Instruction FStore3 = new FStores.FStore3();
  private static Instruction DStore0 = new DStores.DStore0();
  private static Instruction DStore1 = new DStores.DStore1();
  private static Instruction DStore2 = new DStores.DStore2();
  private static Instruction DStore3 = new DStores.DStore3();
  private static Instruction AStore0 = new AStores.AStore0();
  private static Instruction AStore1 = new AStores.AStore1();
  private static Instruction AStore2 = new AStores.AStore2();
  private static Instruction AStore3 = new AStores.AStore3();
  private static Instruction IAStore = new XAStore.IAStore();
  private static Instruction LAStore = new XAStore.LAStore();
  private static Instruction FAStore = new XAStore.FAStore();
  private static Instruction DAStore = new XAStore.DAStore();
  private static Instruction AAStore = new XAStore.AAStore();
  private static Instruction BAStore = new XAStore.BAStore();
  private static Instruction CAStore = new XAStore.CAStore();
  private static Instruction SAStore = new XAStore.SAStore();

  private static Instruction Pop = new Pops.Pop();
  private static Instruction Pop2 = new Pops.Pop2();
  private static Instruction Dup = new Dups.Dup();
  private static Instruction DupX1 = new Dups.DupX1();
  private static Instruction DupX2 = new Dups.DupX2();
  private static Instruction Dup2 = new Dups.Dup2();
  private static Instruction Dup2X1 = new Dups.Dup2X1();
  private static Instruction Dup2X2 = new Dups.Dup2X2();
  private static Instruction Swap = new Swaps.Swap();

  private static Instruction IAdd = new Adds.IAdd();
  private static Instruction LAdd = new Adds.LAdd();
  private static Instruction FAdd = new Adds.FAdd();
  private static Instruction DAdd = new Adds.DAdd();
  private static Instruction ISub = new Subs.ISub();
  private static Instruction LSub = new Subs.LSub();
  private static Instruction FSub = new Subs.FSub();
  private static Instruction Dsub = new Subs.DSub();
  private static Instruction IMul = new Muls.IMul();
  private static Instruction LMul = new Muls.LMul();
  private static Instruction FMul = new Muls.FMul();
  private static Instruction DMul = new Muls.DMul();
  private static Instruction IDiv = new Divs.IDiv();
  private static Instruction LDiv = new Divs.LDiv();
  private static Instruction FDiv = new Divs.FDiv();
  private static Instruction DDiv = new Divs.DDiv();
  private static Instruction IRem = new Rems.IRem();
  private static Instruction LRem = new Rems.LRem();
  private static Instruction FRem = new Rems.FRem();
  private static Instruction DRem = new Rems.DRem();
  private static Instruction INeg = new Negs.INeg();
  private static Instruction LNeg = new Negs.LNeg();
  private static Instruction FNeg = new Negs.FNeg();
  private static Instruction DNeg = new Negs.DNeg();

  private static Instruction IShl = new Shs.IShl();
  private static Instruction LShl = new Shs.LShl();
  private static Instruction IShr = new Shs.IShr();
  private static Instruction LShr = new Shs.LShr();
  private static Instruction IUShr = new Shs.IUShr();
  private static Instruction LUShr = new Shs.LUShr();

  private static Instruction IAnd = new Ands.IAnd();
  private static Instruction LAnd = new Ands.LAnd();
  private static Instruction IOr = new Ors.IOr();
  private static Instruction LOr = new Ors.LOr();
  private static Instruction IXor = new Xors.IXor();
  private static Instruction LXor = new Xors.LXor();

  private static Instruction I2l = new I2x.I2l();
  private static Instruction I2f = new I2x.I2f();
  private static Instruction I2d = new I2x.I2d();
  private static Instruction L2i = new L2x.L2i();
  private static Instruction L2f = new L2x.L2f();
  private static Instruction L2d = new L2x.L2d();
  private static Instruction F2i = new F2x.F2i();
  private static Instruction F2l = new F2x.F2l();
  private static Instruction F2d = new F2x.F2d();
  private static Instruction D2i = new D2x.D2i();
  private static Instruction D2l = new D2x.D2l();
  private static Instruction D2f = new D2x.D2f();
  private static Instruction I2b = new I2x.I2b();
  private static Instruction I2c = new I2x.I2c();
  private static Instruction I2s = new I2x.I2s();

  private static Instruction LCmp = new Cmps.LCmp();
  private static Instruction FCmpl = new Cmps.FCmpl();
  private static Instruction FCmpg = new Cmps.FCmpg();
  private static Instruction DCmpl = new Cmps.DCmpl();
  private static Instruction DCmpg = new Cmps.DCmpg();

  private static Instruction Return = new Returns.Return();
  private static Instruction IReturn = new Returns.IReturn();
  private static Instruction FReturn = new Returns.FReturn();
  private static Instruction DReturn = new Returns.DReturn();
  private static Instruction LReturn = new Returns.LReturn();
  private static Instruction AReturn = new Returns.AReturn();

  private static Instruction ArrayLength = new ArrayLength();

  private static Instruction InvokeNative = new InvokeNative();

  private static Instruction AThrow = new AThrow();

  public static Instruction newInstruction(byte opcode) {
    int code = Byte.toUnsignedInt(opcode);

    switch (code) {
      case 0x00:
        return Nop;
      case 0x01:
        return AConstNull;
      case 0x02:
        return IConstM1;
      case 0x03:
        return IConst0;
      case 0x04:
        return IConst1;
      case 0x05:
        return IConst2;
      case 0x06:
        return IConst3;
      case 0x07:
        return IConst4;
      case 0x08:
        return IConst5;
      case 0x09:
        return LConst0;
      case 0x0a:
        return LConst1;
      case 0x0b:
        return FConst0;
      case 0x0c:
        return FConst1;
      case 0x0d:
        return FConst2;
      case 0x0e:
        return DConst0;
      case 0x0f:
        return DConst1;
      case 0x10:
        return new IPush.BIPush();
      case 0x11:
        return new IPush.SIPush();
      case 0x12:
        return new Ldcs.Ldc();
      case 0x13:
        return new Ldcs.LdcW();
      case 0x14:
        return new Ldcs.Ldc2W();
      case 0x15:
        return new ILoads.ILoad();
      case 0x16:
        return new LLoads.LLoad();
      case 0x17:
        return new FLoads.FLoad();
      case 0x18:
        return new DLoads.DLoad();
      case 0x19:
        return new ALoads.ALoad();
      case 0x1a:
        return ILoad0;
      case 0x1b:
        return ILoad1;
      case 0x1c:
        return ILoad2;
      case 0x1d:
        return ILoad3;
      case 0x1e:
        return LLoad0;
      case 0x1f:
        return LLoad1;
      case 0x20:
        return LLoad2;
      case 0x21:
        return LLoad3;
      case 0x22:
        return FLoad0;
      case 0x23:
        return FLoad1;
      case 0x24:
        return FLoad2;
      case 0x25:
        return FLoad3;
      case 0x26:
        return DLoad0;
      case 0x27:
        return DLoad1;
      case 0x28:
        return DLoad2;
      case 0x29:
        return DLoad3;
      case 0x2a:
        return ALoad0;
      case 0x2b:
        return ALoad1;
      case 0x2c:
        return ALoad2;
      case 0x2d:
        return ALoad3;
      case 0x2e:
        return IALoad;
      case 0x2f:
        return LALoad;
      case 0x30:
        return FALoad;
      case 0x31:
        return DALoad;
      case 0x32:
        return AALoad;
      case 0x33:
        return BALoad;
      case 0x34:
        return CALoad;
      case 0x35:
        return SALoad;
      case 0x36:
        return new IStores.IStore();
      case 0x37:
        return new LStores.LStore();
      case 0x38:
        return new FStores.FStore();
      case 0x39:
        return new DStores.DStore();
      case 0x3a:
        return new AStores.AStore();
      case 0x3b:
        return IStore0;
      case 0x3c:
        return IStore1;
      case 0x3d:
        return IStore2;
      case 0x3e:
        return IStore3;
      case 0x3f:
        return LStore0;
      case 0x40:
        return LStore1;
      case 0x41:
        return LStore2;
      case 0x42:
        return LStore3;
      case 0x43:
        return FStore0;
      case 0x44:
        return FStore1;
      case 0x45:
        return FStore2;
      case 0x46:
        return FStore3;
      case 0x47:
        return DStore0;
      case 0x48:
        return DStore1;
      case 0x49:
        return DStore2;
      case 0x4a:
        return DStore3;
      case 0x4b:
        return AStore0;
      case 0x4c:
        return AStore1;
      case 0x4d:
        return AStore2;
      case 0x4e:
        return AStore3;
      case 0x4f:
        return IAStore;
      case 0x50:
        return LAStore;
      case 0x51:
        return FAStore;
      case 0x52:
        return DAStore;
      case 0x53:
        return AAStore;
      case 0x54:
        return BAStore;
      case 0x55:
        return CAStore;
      case 0x56:
        return SAStore;
      case 0x57:
        return Pop;
      case 0x58:
        return Pop2;
      case 0x59:
        return Dup;
      case 0x5a:
        return DupX1;
      case 0x5b:
        return DupX2;
      case 0x5c:
        return Dup2;
      case 0x5d:
        return Dup2X1;
      case 0x5e:
        return Dup2X2;
      case 0x5f:
        return Swap;
      case 0x60:
        return IAdd;
      case 0x61:
        return LAdd;
      case 0x62:
        return FAdd;
      case 0x63:
        return DAdd;
      case 0x64:
        return ISub;
      case 0x65:
        return LSub;
      case 0x66:
        return FSub;
      case 0x67:
        return Dsub;
      case 0x68:
        return IMul;
      case 0x69:
        return LMul;
      case 0x6a:
        return FMul;
      case 0x6b:
        return DMul;
      case 0x6c:
        return IDiv;
      case 0x6d:
        return LDiv;
      case 0x6e:
        return FDiv;
      case 0x6f:
        return DDiv;
      case 0x70:
        return IRem;
      case 0x71:
        return LRem;
      case 0x72:
        return FRem;
      case 0x73:
        return DRem;
      case 0x74:
        return INeg;
      case 0x75:
        return LNeg;
      case 0x76:
        return FNeg;
      case 0x77:
        return DNeg;
      case 0x78:
        return IShl;
      case 0x79:
        return LShl;
      case 0x7a:
        return IShr;
      case 0x7b:
        return LShr;
      case 0x7c:
        return IUShr;
      case 0x7d:
        return LUShr;
      case 0x7e:
        return IAnd;
      case 0x7f:
        return LAnd;
      case 0x80:
        return IOr;
      case 0x81:
        return LOr;
      case 0x82:
        return IXor;
      case 0x83:
        return LXor;
      case 0x84:
        return new Incs.IInc();
      case 0x85:
        return I2l;
      case 0x86:
        return I2f;
      case 0x87:
        return I2d;
      case 0x88:
        return L2i;
      case 0x89:
        return L2f;
      case 0x8a:
        return L2d;
      case 0x8b:
        return F2i;
      case 0x8c:
        return F2l;
      case 0x8d:
        return F2d;
      case 0x8e:
        return D2i;
      case 0x8f:
        return D2l;
      case 0x90:
        return D2f;
      case 0x91:
        return I2b;
      case 0x92:
        return I2c;
      case 0x93:
        return I2s;
      case 0x94:
        return LCmp;
      case 0x95:
        return FCmpl;
      case 0x96:
        return FCmpg;
      case 0x97:
        return DCmpl;
      case 0x98:
        return DCmpg;
      case 0x99:
        return new IfCond.Ifeq();
      case 0x9a:
        return new IfCond.Ifne();
      case 0x9b:
        return new IfCond.Iflt();
      case 0x9c:
        return new IfCond.Ifge();
      case 0x9d:
        return new IfCond.Ifgt();
      case 0x9e:
        return new IfCond.Ifle();
      case 0x9f:
        return new IfCmp.IfIcmpEQ();
      case 0xa0:
        return new IfCmp.IfIcmpNE();
      case 0xa1:
        return new IfCmp.IfIcmpLT();
      case 0xa2:
        return new IfCmp.IfIcmpGE();
      case 0xa3:
        return new IfCmp.IfIcmpGT();
      case 0xa4:
        return new IfCmp.IfIcmpLE();
      case 0xa5:
        return new IfCmp.IfAcmpEQ();
      case 0xa6:
        return new IfCmp.IfAcmpNE();
      case 0xa7:
        return new Controls.Goto();
      // case 0xa8:
      // 	return &JSR{}
      // case 0xa9:
      // 	return &RET{}
      case 0xaa:
        return new Controls.TableSwitch();
      case 0xab:
        return new Controls.LookupSwitch();
      case 0xac:
        return IReturn;
      case 0xad:
        return LReturn;
      case 0xae:
        return FReturn;
      case 0xaf:
        return DReturn;
      case 0xb0:
        return AReturn;
      case 0xb1:
        return Return;
      case 0xb2:
        return new GetStatic();
      case 0xb3:
        return new PutStatic();
      case 0xb4:
        return new GetField();
      case 0xb5:
        return new PutField();
      case 0xb6:
        return new InvokeVirtual();
      case 0xb7:
        return new InvokeSpecial();
      case 0xb8:
        return new InvokeStatic();
      case 0xb9:
        return new InvokeInterface();
      // case 0xba:
      // 	return &INVOKE_DYNAMIC{}
      case 0xbb:
        return new New();
      case 0xbc:
        return new NewArray();
      case 0xbd:
        return new ANewArray();
      case 0xbe:
        return ArrayLength;
      case 0xbf:
        return AThrow;
      case 0xc0:
        return new CheckCast();
      case 0xc1:
        return new InstanceOf();
      // case 0xc2:
      // 	return monitorenter
      // case 0xc3:
      // 	return monitorexit
      case 0xc4:
        return new Extends.Wide();
      case 0xc5:
        return new MultiANewArray();
      case 0xc6:
        return new Extends.IfNull();
      case 0xc7:
        return new Extends.IfNonNull();
      case 0xc8:
        return new Extends.GotoW();
      // case 0xc9:
      // 	return &JSR_W{}
      // case 0xca: breakpoint
      case 0xfe:
        return InvokeNative;
      // case 0xff: impdep2
      default:
        unsupport(code);
    }
    return null;
  }

  private static void unsupport(int code) {
    throw new RuntimeException("Unsupport byte code: " + Integer.toHexString(code));
  }
}
