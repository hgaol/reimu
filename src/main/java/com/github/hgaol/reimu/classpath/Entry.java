package com.github.hgaol.reimu.classpath;

/**
 * @author Gao Han
 * @date: 2018年04月08日
 */
public interface Entry {
  /**
   * return class byte data
   * @param classNamme class name, ex: java/lang/Object.class
   * @return class file byte data
   */
  byte[] readClass(String classNamme);

  /**
   * @return class absolute path
   */
  String getPath();
}
