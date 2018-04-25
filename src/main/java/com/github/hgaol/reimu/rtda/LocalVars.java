package com.github.hgaol.reimu.rtda;

import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class LocalVars {
  private Slot[] slots;

  public LocalVars(int maxSlots) {
    this.slots = new Slot[maxSlots];
    for (int i = 0; i < maxSlots; i++) {
      slots[i] = new Slot();
    }
  }

  public void setInt(int index, int val) {
    slots[index].num = val;
  }

  public int getInt(int index) {
    return slots[index].num;
  }

  public void setBoolean(int index, boolean val) {
    slots[index].num = val ? 1 : 0;
  }

  public boolean getBoolean(int index) {
    return slots[index].num == 1;
  }

  public void setFloat(int index, float val) {
    int num = Float.floatToIntBits(val);
    setInt(index, num);
  }

  public float getFloat(int index) {
    int num = getInt(index);
    return Float.intBitsToFloat(num);
  }

  public void setLong(int index, long val) {
    int low = (int) val;
    int high = (int) (val >> 32);
    setInt(index, low);
    setInt(index + 1, high);
  }

  public long getLong(int index) {
    long low = (getInt(index) & 0x00000000ffffffffL);
    long high = (getInt(index + 1) & 0x00000000ffffffffL) << 32;
    return high | low;
  }

  public void setDouble(int index, double val) {
    long num = Double.doubleToLongBits(val);
    setLong(index, num);
  }

  public double getDouble(int index) {
    long num = getLong(index);
    return Double.longBitsToDouble(num);
  }

  public void setRef(int index, ReObject ref) {
    slots[index].ref = ref;
  }

  public ReObject getRef(int index) {
    return slots[index].ref;
  }

  public int length() {
    return slots.length;
  }

  public ReObject getThis() {
    return getRef(0);
  }

  public void setSlot(int index, Slot slot) {
    slots[index] = slot;
  }

}
