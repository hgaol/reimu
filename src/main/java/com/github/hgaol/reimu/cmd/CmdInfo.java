package com.github.hgaol.reimu.cmd;

import com.github.hgaol.reimu.util.EchoUtils;
import org.apache.commons.cli.*;

import java.util.Arrays;

import static com.github.hgaol.reimu.util.EchoUtils.*;

/**
 * @author Gao Han
 * @date: 2018年04月08日
 */
public class CmdInfo {

  public String[] args;
  public boolean version;
  public boolean help;
  public String cp;
  public String cls;
  public String xjre;

  private Options options = new Options();

  public CmdInfo(String[] args) {
    init();
    fill(args);
  }

  private void init() {
    options.addOption("?", "show help.");
    options.addOption("h", "help", false, "show help.");
    options.addOption("v", "version", false, "show version.");
    options.addOption("cp", "classpath", true, "classpath");
    options.addOption("Xjre", true, "path to jre");
  }

  private void fill(String[] args) {
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (cmd == null) {
      help = true;
    }
    if (cmd.hasOption("v")) {
      version = true;
    }
    if (cmd.hasOption("h") || cmd.hasOption("?")) {
      help = true;
    }
    if (cmd.hasOption("cp")) {
      cp = cmd.getOptionValue("cp");
    }
    if (cmd.hasOption("Xjre")) {
      xjre = cmd.getOptionValue("Xjre");
    }

    String[] otherArgs = cmd.getArgs();
    if (otherArgs.length > 0) {
      cls = otherArgs[0];
    }
    if (otherArgs.length > 1) {
      this.args = new String[otherArgs.length - 1];
      System.arraycopy(otherArgs, 1, this.args, 0, otherArgs.length - 1);
    }
  }

  public void printVersion() {
    EchoUtils.echoln("class-parser version 0.1.0");
  }

  public void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("class-parser [-options] class [args...]", options);
  }

  @Override
  public String toString() {
    return "CmdInfo{" +
        "args=" + Arrays.toString(args) +
        ", version=" + version +
        ", help=" + help +
        ", cp='" + cp + '\'' +
        ", cls='" + cls + '\'' +
        ", Xjre='" + xjre + '\'' +
        '}';
  }
}
