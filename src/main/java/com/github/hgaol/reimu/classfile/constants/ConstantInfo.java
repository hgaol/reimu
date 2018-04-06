package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

/**
 * @author Gao Han
 * @date: 2018年04月04日
 */
public interface ConstantInfo {
  void readInfo(BytesReader reader);
}
