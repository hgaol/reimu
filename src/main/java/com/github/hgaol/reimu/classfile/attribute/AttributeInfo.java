package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

/**
 * @author Gao Han
 * @date: 2018年04月04日
 */
public interface AttributeInfo {
  void readInfo(BytesReader reader);
}
