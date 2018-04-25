package com.github.hgaol.reimu.classpath;

import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author Gao Han
 * @date: 2018年04月04日
 */
public class ClassPath {

  private Entry bootClasspath;
  private Entry extClasspath;
  private Entry userClasspath;

  /**
   * @param jreOption boot classpath
   * @param cpOption
   */
  public ClassPath(String jreOption, String cpOption) {
    parseBootAndExtClasspath(jreOption);
    parseUserClasspath(cpOption);
  }

  public byte[] readClass(String className) {
    className += ".class";
    byte[] data;
    if ((data = bootClasspath.readClass(className)) != null) {
      return data;
    }
    if ((data = extClasspath.readClass(className)) != null) {
      return data;
    }
    return userClasspath.readClass(className);
  }

  private void parseBootAndExtClasspath(String jreOption) {
    String jreDir = getJreDir(jreOption);

    // jre/lib/*
    String jreLibPath = String.join(File.separator, jreDir, "lib", "*");
    bootClasspath = new WildCardEntry(jreLibPath);

    // jre/lib/ext/*
    String jreExtPath = String.join(File.separator, jreDir, "lib", "ext", "*");
    extClasspath = new WildCardEntry(jreLibPath);
  }

  private void parseUserClasspath(String cpOption) {
    if (VMUtils.isEmpty(cpOption)) {
      cpOption = ".";
    }
    userClasspath = ClassPathUtils.newEntry(cpOption);
  }

  private String getJreDir(String jreOption) {
    if (jreOption != null && !"".equals(jreOption)) {
      return jreOption;
    }
    File f = new File("./jre");
    if (f.exists() && f.isDirectory()) {
      return "./jre";
    }
    String javaHome = System.getenv("JAVA_HOME");
    if (StringUtils.isEmpty(javaHome)) {
      throw new Error("Cannot env JAVA_HOME.");
    }
    if (!new File(javaHome).exists()) {
      throw new Error("Cannot find jre path");
    }
    return String.join(File.separator, javaHome, "jre");
  }

  @Override
  public String toString() {
    return "ClassPath{" +
        "bootClasspath=" + bootClasspath +
        ", extClasspath=" + extClasspath +
        ", userClasspath=" + userClasspath +
        '}';
  }
}

