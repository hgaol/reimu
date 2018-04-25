package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.Slots;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 给类的某个静态变量赋值，需要两个操作数。第一个索引，从运行时常量池找到要复制的变量，
 * 第二个操作数为要赋的值，从操作数栈弹出
 *
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class PutStatic extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    ReClass.Method curMethod = frame.getMethod();
    ReClass curClass = curMethod.getClazz();
    RtConstantPool cp = curClass.getConstantPool();
    CpInfos.FieldRef fieldRef = (CpInfos.FieldRef) cp.getConstant(index);
    ReClass.Field field = fieldRef.resolvedField();
    ReClass fieldClass = field.getClazz();
    // todo: init class

    if (!field.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }
    // 如果是final字段，需在类初始化中给它赋值
    if (field.isFinal()) {
      if (curClass != fieldClass || !curMethod.getName().equals("<clinit>")) {
        throw new Error("java.lang.IllegalAccessError");
      }
    }

    String descriptor = field.getDescriptor();
    int slotId = field.getSlotId();
    Slots slots = fieldClass.getStaticVars();
    OperandStack stack = frame.getOperandStack();

    switch (descriptor.charAt(0)) {
      case 'Z':
      case 'B':
      case 'C':
      case 'S':
      case 'I':
        slots.setInt(slotId, stack.popInt());
        break;
      case 'F':
        slots.setFloat(slotId, stack.popFloat());
        break;
      case 'J':
        slots.setLong(slotId, stack.popLong());
        break;
      case 'D':
        slots.setDouble(slotId, stack.popDouble());
        break;
      case 'L':
        slots.setRef(slotId, stack.popRef());
        break;
    }
  }
}
