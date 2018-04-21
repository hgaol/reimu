package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ClassFileUtil;
import com.github.hgaol.reimu.classpath.ClassPath;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gao Han
 * @date: 2018年04月12日
 */
public class ClassLoader {
  private ClassPath classPath;
  /**
   * 可以认为简单的方法区，保存已加载的Class
   */
  private Map<String, Class> classMap;

  public ClassLoader(ClassPath classPath) {
    this.classPath = classPath;
    this.classMap = new HashMap<>();
  }

  /**
   * 在classpath中读取class信息，并保存在classMap中
   * @param name 类的全限定名
   * @return
   */
  public Class loadClass(String name) {
    Class clazz = classMap.get(name);
    if (clazz != null) {
      return clazz;
    }
    return loadNonArrayClass(name);
  }

  /**
   * <h4>加载非数组类型的类</h4>
   * @param name Class全限定名
   * @return
   */
  private Class loadNonArrayClass(String name) {
    byte[] data = readClass(name);
    return null;
  }

  /**
   * @param name Class全限定名
   * @return
   */
  private byte[] readClass(String name) {
    byte[] data = classPath.readClass(name);
    if (data == null) {
      throw new Error("java.lang.ClassNotFoundException: " + name);
    }
    return data;
  }

  /**
   * 将byte code转换为Class类
   * @param data class binary data
   * @return
   */
  private Class defineClass(byte[] data) {
    Class clazz = parseClass(data);
    clazz.setLoader(this);
    // TODO
    return clazz;
  }

  public static Class parseClass(byte[] data) {
    ClassFile cf = ClassFileUtil.parse(data);
    return new Class(cf);
  }

}

