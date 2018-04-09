package com.github.hgaol.reimu.classpath;

import java.io.File;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class WildCardEntry implements Entry {

  private Entry[] entries;
  private String absPath;

  public WildCardEntry(String path) {
    this.absPath = path;
    if (!path.endsWith("*")) {
      throw new Error("wrong wildcard path.");
    }
    String basePath = path.substring(0, path.length() - 1);
    File baseDir = new File(basePath);
    File[] files = baseDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip") || name.toLowerCase().endsWith(".jar"));
    entries = new Entry[files.length];
    for (int i = 0; i < files.length; i++) {
      entries[i] = new ZipJarEntry(files[i].getAbsolutePath());
    }
  }

  @Override
  public byte[] readClass(String classNamme) {
    for (Entry entry : entries) {
      byte[] data = entry.readClass(classNamme);
      if (data != null) {
        return data;
      }
    }
    return null;
  }

  @Override
  public String getPath() {
    return absPath;
  }

  @Override
  public String toString() {
    return "WildCardEntry{" +
        "absPath='" + absPath + '\'' +
        '}';
  }
}
