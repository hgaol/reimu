package com.github.hgaol.reimu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ClassNameUtils {

  public static Map<String, String> primitiveTypes = new HashMap<>();

  static {
    primitiveTypes.put("void", "V");
    primitiveTypes.put("boolean", "Z");
    primitiveTypes.put("byte", "B");
    primitiveTypes.put("short", "S");
    primitiveTypes.put("int", "I");
    primitiveTypes.put("long", "J");
    primitiveTypes.put("char", "C");
    primitiveTypes.put("float", "F");
    primitiveTypes.put("double", "D");
  }

  public static String getArrayClassName(String className) {
    return "[" + toDescriptor(className);
  }

  // [[XXX -> [XXX
  // [LXXX; -> XXX
  // [I -> int
  public static String getComponentClassName(String className) {
    if (className.charAt(0) == '[') {
      String componentTypeDescriptor = className.substring(1);
      return toClassName(componentTypeDescriptor);
    }
    throw new Error("Not array: " + className);
  }



  /**
   * 主要用于loadClass
   * [XXX  => [XXX
   * LXXX; => XXX
   * I     => int
   * @param descriptor
   * @return
   */
  private static String toClassName(String descriptor) {
    if (descriptor.charAt(0) == '[') {
      // array
      return descriptor;
    }
    if (descriptor.charAt(0) == 'L') {
      // object
      return descriptor.substring(1, descriptor.length() - 1);
    }
    for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
      if (entry.getValue().equals(descriptor)) {
        // primitive
        return entry.getKey();
      }
    }
    throw new Error("Invalid descriptor: " + descriptor);
  }

  // [XXX => [XXX
  // int  => I
  // XXX  => LXXX;
  private static String toDescriptor(String className) {
    if (className.charAt(0) == '[') {
      // array
      return className;
    }
    String d = primitiveTypes.get(className);
    if (d != null) {
      // primitive
      return d;
    }
    // object
    return "L" + className + ";";
  }
}
