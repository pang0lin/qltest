package com.js.oa.database.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupAndRecover {
  public static void backup() {
    try {
      XmlUtil xmlUtil = new XmlUtil();
      if (xmlUtil.del()) {
        String datename = xmlUtil.delFirstBackupNode();
        Delete(datename);
      } 
      Runtime rt = Runtime.getRuntime();
      String path = NewTask.class.getResource("").toString();
      String path1 = path.substring(6, path.length());
      String path2 = path1.substring(0, path1.length() - 53);
      String path3 = "cmd /c " + path2 + 
        "db/bin/mysqldump -ujsdb -p12345678 jsdb --skip-lock-tables";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String name = formatter.format(new Date());
      String path4 = String.valueOf(path3) + " > " + path2 + "bin/" + name + ".sql";
      Process child = rt.exec(path4);
      xmlUtil.addNode(name);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void load(String filename) {
    try {
      String fPath = String.valueOf(filename) + ".sql";
      Runtime rt = Runtime.getRuntime();
      String path = NewTask.class.getResource("").toString();
      String path1 = path.substring(6, path.length());
      String path2 = path1.substring(0, path1.length() - 53);
      String path3 = "cmd /c " + path2 + "db/bin/mysql -ujsdb -p12345678 jsdb";
      Process child = rt.exec(path3);
      OutputStream out = child.getOutputStream();
      StringBuffer sb = new StringBuffer("");
      BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(fPath), "utf8"));
      String inStr;
      while ((inStr = br.readLine()) != null)
        sb.append(String.valueOf(inStr) + "\r\n"); 
      String outStr = sb.toString();
      OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
      writer.write(outStr);
      writer.flush();
      out.close();
      br.close();
      writer.close();
      System.out.println("还原成功");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void Delete(String fileName) {
    String fileName1 = String.valueOf(fileName) + ".sql";
    File file = new File(fileName1);
    if (file.exists())
      file.delete(); 
  }
}
