package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.ClassInitLogic;
import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.Slots;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 从类的静态变量中去除某个值，然后推入栈顶
 *
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class GetStatic extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.FieldRef fieldRef = (CpInfos.FieldRef) cp.getConstant(index);
    ReClass.Field field = fieldRef.resolvedField();
    ReClass clazz = field.getClazz();
    if (!clazz.isInitStarted()) {
      frame.revertNextPc();
      ClassInitLogic.initClass(frame.getThread(), clazz);
      return;
    }

    if (!field.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    String descriptor = field.getDescriptor();
    int slotId = field.getSlotId();
    Slots slots = clazz.getStaticVars();
    OperandStack stack = frame.getOperandStack();

    switch (descriptor.charAt(0)) {
      case 'Z':
      case 'B':
      case 'C':
      case 'S':
      case 'I':
        stack.pushInt(slots.getInt(slotId));
        break;
      case 'F':
        stack.pushFloat(slots.getFloat(slotId));
        break;
      case 'J':
        stack.pushLong(slots.getLong(slotId));
        break;
      case 'D':
        stack.pushDouble(slots.getDouble(slotId));
        break;
      case 'L':
        stack.pushRef(slots.getRef(slotId));
        break;
      default:
        // todo
    }
  }
}
