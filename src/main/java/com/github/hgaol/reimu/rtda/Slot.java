package com.github.hgaol.reimu.rtda;

import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class Slot {
  int num;
  ReObject ref;

  public Slot() {}

  public Slot(Slot other) {
    this.num = other.num;
    this.ref = other.ref;
  }

  @Override
  public String toString() {
    return "Slot{" +
        "num=" + num +
        ", ref=" + ref +
        '}';
  }
}
