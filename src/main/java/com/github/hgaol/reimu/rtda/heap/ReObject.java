package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.rtda.Slots;
import com.rits.cloning.Cloner;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class ReObject {

  // 类类型的指针
  private ReClass clazz;
  // 实例变量
  private Object data;
  // 如果为类对象，指向对应的ReClass；如果非类对象，空
  private Object extra;

  public ReObject(ReClass clazz, Object data) {
    this.clazz = clazz;
    this.data = data;
  }

  // 普通类实例方法
  public static ReObject newObject(ReClass clazz) {
    ReObject obj = new ReObject(clazz, new Slots(clazz.getInstanceSlotCount()));
    return obj;
  }

  public Slots getFields() {
    return (Slots) data;
  }

  public Object getArrayData() {
    return data;
  }

  public ReClass getClazz() {
    return clazz;
  }

  public boolean isInstanceOf(ReClass clazz) {
    return clazz.isAssignableFrom(this.clazz);
  }

  public Object getExtra() {
    return extra;
  }

  public ReObject setExtra(Object extra) {
    this.extra = extra;
    return this;
  }

  /**
   * array method
   */
  public static ReObject newArray(ReClass clazz, int count) {
    if (!clazz.isArray()) {
      throw new Error("Not array class: " + clazz.getName());
    }
    switch (clazz.getName()) {
      case "[Z":
        return new ReObject(clazz, new int[count]);
      case "[B":
        return new ReObject(clazz, new byte[count]);
      case "[C":
        return new ReObject(clazz, new char[count]);
      case "[S":
        return new ReObject(clazz, new short[count]);
      case "[I":
        return new ReObject(clazz, new int[count]);
      case "[J":
        return new ReObject(clazz, new long[count]);
      case "[F":
        return new ReObject(clazz, new float[count]);
      case "[D":
        return new ReObject(clazz, new double[count]);
      default:
        return new ReObject(clazz, new ReObject[count]);
    }
  }

  public byte[] getBytes() {
    return (byte[]) this.data;
  }

  public short[] getShorts() {
    return (short[]) this.data;
  }

  public int[] getInts() {
    return (int[]) this.data;
  }

  public long[] getLongs() {
    return (long[]) this.data;
  }

  public char[] getChars() {
    return (char[]) this.data;
  }

  public float[] getFloats() {
    return (float[]) this.data;
  }

  public double[] getDoubles() {
    return (double[]) this.data;
  }

  public ReObject[] getRefs() {
    return (ReObject[]) this.data;
  }

  public ReObject getRefVar(String name, String descriptor) {
    ReClass.Field field = this.clazz.getField(name, descriptor, false);
    Slots slots = (Slots) this.data;
    return slots.getRef(field.slotId);
  }

  public void setRefVar(String name, String descriptor, ReObject ref) {
    ReClass.Field field = this.clazz.getField(name, descriptor, false);
    Slots slots = (Slots) this.data;
    slots.setRef(field.slotId, ref);
  }

  public int getArrayLenth() {
    String arrayType = this.data.getClass().getSimpleName();
    switch (arrayType) {
      case "byte[]":
        return ((byte[]) this.data).length;
      case "short[]":
        return ((short[]) this.data).length;
      case "int[]":
        return ((int[]) this.data).length;
      case "long[]":
        return ((long[]) this.data).length;
      case "char[]":
        return ((char[]) this.data).length;
      case "double[]":
        return ((double[]) this.data).length;
      case "ReObject[]":
        return ((ReObject[]) this.data).length;
      default:
        throw new Error("Not array!");
    }
  }

  public ReObject reClone() {
    return new ReObject(this.clazz, cloneData());
  }

  public Object cloneData() {
    Cloner cloner = new Cloner();
    return cloner.deepClone(this.data);
  }

}
