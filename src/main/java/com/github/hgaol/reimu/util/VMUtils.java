package com.github.hgaol.reimu.util;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Gao Han
 * @date: 2018年04月09日
 */
public class VMUtils {
  public static boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static class ReimuToStringStyle extends ToStringStyle {
//    public static class ReimuToStringStyle extends {
    public ReimuToStringStyle() {
      super();
      this.setUseShortClassName(true);
      this.setUseIdentityHashCode(true);

      // set multi line format
//      this.setContentStart("[");
//      this.setFieldSeparator(System.lineSeparator() + "  ");
//      this.setFieldSeparatorAtStart(true);
//      this.setContentEnd(System.lineSeparator() + "]");
    }
  }
}
