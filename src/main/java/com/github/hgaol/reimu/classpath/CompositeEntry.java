package com.github.hgaol.reimu.classpath;

import java.io.File;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class CompositeEntry implements Entry {

  private Entry[] entries;

  public CompositeEntry(String pathList) {
    String[] paths = pathList.split(File.pathSeparator);
    entries = new Entry[paths.length];
    for (int i = 0; i < paths.length; i++) {
      entries[i] = ClassPathUtils.newEntry(paths[i]);
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
    StringBuilder sb = new StringBuilder();
    for (Entry entry : entries) {
      sb.append(File.pathSeparatorChar).append(entry.getPath());
    }
    return sb.substring(1);
  }
}
