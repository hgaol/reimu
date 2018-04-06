package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ClassFileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.hgaol.reimu.util.EchoUtil.echoln;

/**
 * Hello world!
 */
public class ClassParser {
  public static void main(String[] args) throws IOException {
    Path path = Paths.get(args[0]);
    byte[] data = Files.readAllBytes(path);
    ClassFile cf = ClassFileUtil.parse(data);
    echoln(cf);
  }

}
