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

}
