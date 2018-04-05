package com.github.hgaol.reimu.classfile;

import java.nio.ByteOrder;

/**
 * @author Gao Han
 * @date: 2018年04月05日
 */
public class ClassFileUtil {

  public static ClassFile parse(byte[] data) {
    BytesReader reader = new BytesReader(data, ByteOrder.BIG_ENDIAN);
    ClassFile cf = new ClassFile();
    cf.read(reader);
    return cf;
  }

}
