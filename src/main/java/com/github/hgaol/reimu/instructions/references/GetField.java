package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.Slots;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 获取对象的实例变量值，推入操作数栈
 * @author Gao Han
 * @date: 2018年04月23日
 */
public class GetField extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.FieldRef fieldRef = (CpInfos.FieldRef) cp.getConstant(index);
    ReClass.Field field = fieldRef.resolvedField();

    if (field.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    OperandStack stack = frame.getOperandStack();
    ReObject ref = stack.popRef();

    if (ref == null) {
      throw new Error("java.lang.NullPointerException");
    }

    String descriptor = field.getDescriptor();
    int slotId = field.getSlotId();
    Slots slots = ref.getFields();

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
      case '[':
        stack.pushRef(slots.getRef(slotId));
        break;
      default:
        // todo
    }
  }
}
