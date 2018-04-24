package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.rtda.Slots;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class ReObject {
  // 类类型的指针
  private Class clazz;
  // 实例变量
  private Slots fields;

  public ReObject(Class clazz) {
    this.clazz = clazz;
    this.fields = new Slots(clazz.getInstanceSlotCount());
  }

  public Slots getFields() {
    return fields;
  }

  public boolean isInstanceOf(Class clazz) {
    return clazz.isAssignableFrom(this.clazz);
  }
}
