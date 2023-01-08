package com.js.oa.jsflow.util.pdf;

import com.js.util.config.SystemCommon;
import java.io.File;

public class HtmlToPdf {
  private static String toPdfTool = SystemCommon.getExePath();
  
  public static void main(String[] args) {}
  
  public static boolean convert(String srcPath, String destPath) {
    File file = new File(destPath);
    File parent = file.getParentFile();
    if (!parent.exists())
      parent.mkdirs(); 
    StringBuilder cmd = new StringBuilder();
    cmd.append(toPdfTool);
    cmd.append(" ");
    cmd.append(srcPath);
    cmd.append(" ");
    cmd.append(destPath);
    boolean result = true;
    try {
      Process proc = Runtime.getRuntime().exec(cmd.toString());
      HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
      HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
      error.start();
      output.start();
      proc.waitFor();
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    } 
    return result;
  }
}
