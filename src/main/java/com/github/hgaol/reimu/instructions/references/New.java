package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.Class;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 操作数是一个unit16索引，通过该索引，可以从当前类的运行时常量池中找到一个类符号引用
 * ，解析这个类符号引用，拿到类数据，然后创建对象，并把对象推入栈顶
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class New extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.ClassRef classRef = (CpInfos.ClassRef) cp.getConstant(index);
    Class clazz = classRef.resolvedClass();
    // todo: init class

    if (clazz.isInterface() || clazz.isAbstract()) {
      throw new Error("java.lang.InstantiationError");
    }

    ReObject ref = clazz.newObject();
    frame.getOperandStack().pushRef(ref);
  }
}
