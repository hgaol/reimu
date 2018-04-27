package com.github.hgaol.reimu.rtda;

import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class OperandStack {
  private int size;
  private Slot[] slots;

  public OperandStack(int maxSlots) {
    this.slots = new Slot[maxSlots];
    for (int i = 0; i < maxSlots; i++) {
      slots[i] = new Slot();
    }
  }

  public void pushInt(int val) {
    slots[size].num = val;
    size++;
  }

  public int popInt() {
    size--;
    return slots[size].num;
  }

  public void pushFloat(float val) {
    int num = Float.floatToIntBits(val);
    pushInt(num);
  }

  public float popFloat() {
    int num = popInt();
    return Float.intBitsToFloat(num);
  }

  public void pushBoolean(boolean val) {
    int num = val ? 1 : 0;
    pushInt(num);
  }

  public boolean popBoolean() {
    return popInt() == 1;
  }

  public void pushLong(long val) {
    int low = (int) val;
    int high = (int) (val >> 32);
    pushInt(low);
    pushInt(high);
  }

  public long popLong() {
    long high = (popInt() & 0x00000000FFFFFFFFL) << 32;
    long low = popInt() & 0x00000000FFFFFFFFL;
    return high | low;
  }

  public void pushDouble(double val) {
    long num = Double.doubleToLongBits(val);
    pushLong(num);
  }

  public double popDouble() {
    long num = popLong();
    return Double.longBitsToDouble(num);
  }

  public void pushRef(ReObject ref) {
    slots[size].ref = ref;
    size++;
  }

  public ReObject popRef() {
    size--;
    return slots[size].ref;
  }

  public void pushSlot(Slot slot) {
    slots[size] = new Slot(slot);
    size++;
  }

  public Slot popSlot() {
    size--;
    return slots[size];
  }

  public int getSize() {
    return size;
  }

  public ReObject getRefFromTop(int n) {
    return slots[size - 1 - n].ref;
  }

  public Slot[] getSlots() {
    return slots;
  }
}

