package com.github.hgaol.reimu.classpath;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class ZipJarEntry implements Entry {
  private String absPath;

  public ZipJarEntry(String absPath) {
    this.absPath = absPath;
  }

  @Override
  public byte[] readClass(String classNamme) {
    ZipInputStream zin = null;
    try {
      ZipFile zf = isJar() ? new JarFile(absPath) : new ZipFile(absPath);
      Iterator<ZipEntry> it = (Iterator) zf.entries();
      while (it.hasNext()) {
        ZipEntry ze = it.next();
        if (ze.getName().equals(classNamme)) {
          byte[] data = new byte[(int) ze.getSize()];
          IOUtils.readFully(zf.getInputStream(ze), data);
          return data;
        }
      }
    } catch (IOException e) {
      throw new Error(e);
    } finally {
      IOUtils.closeQuietly(zin);
    }
    return null;
  }

  @Override
  public String getPath() {
    return absPath;
  }

  private boolean isJar() {
    return absPath.toLowerCase().endsWith(".jar");
  }

  @Override
  public String toString() {
    return "ZipJarEntry{" +
        "absPath='" + absPath + '\'' +
        '}';
  }
}
