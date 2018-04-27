package com.github.hgaol.reimu.rtda.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class StringPool {

  public static Map<String, ReObject> internedStrings = new HashMap<>();

  public static ReObject getReString(ReClassLoader loader, String key) {
    ReObject reStr = internedStrings.get(key);
    if (reStr != null) {
      return reStr;
    }

    ReObject jChars = new ReObject(loader.loadClass("[C"), key.toCharArray());

    reStr = loader.loadClass("java/lang/String").newObject();
    reStr.setRefVar("value", "[C", jChars);

    internedStrings.put(key, reStr);
    return reStr;
  }

  public static String getOrigString(ReObject jStr) {
    ReObject charArr = jStr.getRefVar("value", "[C");
    return String.valueOf(charArr.getChars());
  }

  /**
   * 主要是为了放入池中
   * 如果不在常量池中，则得到orig_string，放入池中
   * 否则直接返回池中的jStr
   * @param jStr 准备在StringPool中查找的jStr
   * @return jStr
   */
  public static ReObject getInternString(ReObject jStr) {
    String key = getOrigString(jStr);
    ReObject internedStr = internedStrings.get(key);
    if (internedStr != null) {
      return internedStr;
    } else {
      internedStrings.put(key, jStr);
      return jStr;
    }

  }

}
