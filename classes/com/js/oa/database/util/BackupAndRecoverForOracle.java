package com.js.oa.database.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.dom4j.DocumentException;

public class BackupAndRecoverForOracle {
  public static void exp() throws DocumentException, IOException {
    XmlUtil xmlUtil = new XmlUtil();
    if (xmlUtil.del()) {
      String datename = xmlUtil.delFirstBackupNode();
      Delete(datename);
    } 
    Process proc = null;
    StringBuffer errorInfo = new StringBuffer();
    String[] cmds = new String[3];
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String name = formatter.format(new Date());
    String ta = "exp jsdb/12345678@jsdb file=" + name + ".dmp full=y ";
    cmds[0] = " cmd ";
    cmds[1] = " /C ";
    cmds[2] = ta;
    try {
      proc = Runtime.getRuntime().exec(cmds[2].toString());
      InputStream istr = proc.getErrorStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(istr, "utf8"));
      String str;
      while ((str = br.readLine()) != null)
        errorInfo.append(String.valueOf(str) + "\n"); 
      proc.waitFor();
      proc.destroy();
      System.out.println("备份成功");
      xmlUtil.addNode(name);
    } catch (Exception exception) {}
  }
  
  public static void imp(String name) {
    Process proc = null;
    StringBuffer errorInfo = new StringBuffer();
    String ta = "imp jsdb/12345678@jsdb file=" + name + ".dmp full=y ignore=y";
    String[] cmds = new String[3];
    cmds[0] = " cmd ";
    cmds[1] = " /C ";
    cmds[2] = ta;
    try {
      proc = Runtime.getRuntime().exec(cmds[2].toString());
      InputStream istr = proc.getErrorStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(istr, "utf8"));
      String str;
      while ((str = br.readLine()) != null)
        errorInfo.append(String.valueOf(str) + "\n"); 
      proc.waitFor();
      proc.destroy();
      System.out.println("还原成功");
    } catch (Exception exception) {}
  }
  
  public static void Delete(String fileName) {
    String fileName1 = String.valueOf(fileName) + ".dmp";
    File file = new File(fileName1);
    if (file.exists())
      file.delete(); 
  }
}
