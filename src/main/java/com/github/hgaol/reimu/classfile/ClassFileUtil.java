package com.github.hgaol.reimu.classfile;

import java.nio.ByteOrder;

/**
 * @author Gao Han
 * @date: 2018年04月05日
 */
public class ClassFileUtil {

  /**
   * 将字节码转换为ClassFile类型
   * @param data class binary data
   * @return
   */
  public static ClassFile parse(byte[] data) {
    BytesReader reader = new BytesReader(data, ByteOrder.BIG_ENDIAN);
    ClassFile cf = new ClassFile();
    cf.read(reader);
    return cf;
  }

}
