package com.github.hgaol.reimu.classpath;

import java.io.File;

/**
 * @author Gao Han
 * @date: 2018年04月08日
 */
public class ClassPathUtils {

  public static Entry newEntry(String path) {
    if (path.contains(File.pathSeparator)) {
      return new CompositeEntry(path);
    } else if (path.endsWith("*")) {
      return new WildCardEntry(path);
    } else if (path.endsWith(".jar") || path.endsWith(".JAR") ||
        path.endsWith(".zip") || path.endsWith(".ZIP")) {
      return new ZipJarEntry(path);
    } else {
      return new DirEntry(path);
    }
  }

}
