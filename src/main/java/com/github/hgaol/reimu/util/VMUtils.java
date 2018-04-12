package com.github.hgaol.reimu.util;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class VMUtils {
  public static boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static class ReimuToStringStyle extends RecursiveToStringStyle {
    public ReimuToStringStyle() {
      super();
      this.setUseShortClassName(true);
      this.setUseIdentityHashCode(false);
    }
  }
}
