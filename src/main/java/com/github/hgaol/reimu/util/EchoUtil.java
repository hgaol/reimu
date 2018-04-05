package com.github.hgaol.reimu.util;

/**
 * @author Gao Han
 * @date: 2018年04月05日
 */
public class EchoUtil {
  public static void echoln(Object obj) {
    System.out.println(obj);
  }
  public static void echof(String format, Object obj) {
    System.out.printf(format, obj);
  }
  public static void echofln(String format, Object obj) {
    System.out.printf(format, obj);
    System.out.println();
  }
}
