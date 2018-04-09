package com.github.hgaol.reimu.classpath;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class DirEntry implements Entry {

  String absDir;

  public DirEntry(String path) {
    this.absDir = new File(path).getAbsolutePath();
  }

  @Override
  public byte[] readClass(String classNamme) {
    byte[] data = null;
    try {
      String fileName = String.join(File.separator, this.absDir, classNamme);
      data = FileUtils.readFileToByteArray(new File(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  @Override
  public String getPath() {
    return this.absDir;
  }

  @Override
  public String toString() {
    return "DirEntry{" +
        "absDir='" + absDir + '\'' +
        '}';
  }
}
