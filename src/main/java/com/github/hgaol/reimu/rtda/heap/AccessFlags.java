package com.github.hgaol.reimu.rtda.heap;

/**
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class AccessFlags {
  final static int ACC_PUBLIC = 0x0001;
  final static int ACC_PRIVATE = 0x0002;
  final static int ACC_PROTECTED = 0x0004;
  final static int ACC_STATIC = 0x0008;
  final static int ACC_FINAL = 0x0010;
  final static int ACC_SUPER = 0x0020;
  final static int ACC_SYNCHRONIZED = 0x0020;
  final static int ACC_VOLATILE = 0x0040;
  final static int ACC_BRIDGE = 0x0040;
  final static int ACC_TRANSIENT = 0x0080;
  final static int ACC_VARAGES = 0x0080;
  final static int ACC_NATIVE = 0x0100;
  final static int ACC_INTERFACE = 0x0200;
  final static int ACC_ABSTRACT = 0x0400;
  final static int ACC_STRICT = 0x0800;
  final static int ACC_SYNTHETIC = 0x1000;
  final static int ACC_ANNOTATION = 0x2000;
  final static int ACC_ENUM = 0x4000;
}
