package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class IO2File {
  private static Map<String, String> map = null;
  
  public static void printFile(String context) {
    printFile(context, "", -1);
  }
  
  public static void printFile(String context, int print) {
    printFile(context, "", print);
  }
  
  public static void printFile(String context, String fileName) {
    printFile(context, fileName, -1);
  }
  
  public static void printFile(String context, String fileName, int print) {
    map = new HashMap<String, String>();
    if (print == 0) {
      map.put("print", "0");
      map.put("file", "0");
    } else if (print == 1) {
      map.put("print", "1");
      map.put("file", "1");
    } else if (print == 2) {
      map.put("print", "1");
      map.put("file", "0");
    } else if (print == 3) {
      map.put("print", "0");
      map.put("file", "1");
    } else {
      map = ReadConfig();
    } 
    "1".equals(map.get("print"));
    if ("1".equals(map.get("file"))) {
      String dateTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
      String separator = System.getProperty("file.separator");
      String date = dateTime.substring(0, 10);
      String filePath = String.valueOf(System.getProperty("user.dir")) + separator + "logs" + separator + date + separator + date;
      if (!fileName.equals("")) {
        if (fileName.endsWith(".log") || fileName.endsWith(".txt")) {
          filePath = String.valueOf(filePath) + "_" + fileName;
        } else {
          filePath = String.valueOf(filePath) + "_" + fileName + ".log";
        } 
      } else {
        filePath = String.valueOf(filePath) + "_log.log";
      } 
      File outputFile = new File(filePath);
      try {
        if (!outputFile.exists()) {
          File parentFile = outputFile.getParentFile();
          while (parentFile != null && !parentFile.exists())
            parentFile.mkdirs(); 
          outputFile.createNewFile();
        } 
        OutputStreamWriter osw = new OutputStreamWriter(
            new FileOutputStream(outputFile, true), 
            "UTF-8");
        osw.write("[" + dateTime + "]>>>：" + context + "\n");
        osw.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static void printStackTrace(Exception exception) {
    printStackTrace(exception, "exception.log");
  }
  
  public static void printStackTrace(Exception exception, String fileName) {
    exception.getMessage();
    String exceptionStr = "";
    try {
      StringWriter sw = new StringWriter();
      exception.printStackTrace(new PrintWriter(sw));
      exceptionStr = String.valueOf(sw.toString()) + "\r\n";
    } catch (Exception e2) {
      exceptionStr = "获得异常信息出错：" + exception.getMessage();
    } 
    printFile(exceptionStr, fileName);
  }
  
  private static Map<String, String> ReadConfig() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("print", "0");
    map.put("file", "0");
    String configFile = String.valueOf(System.getProperty("user.dir")) + "/jsconfig/sysconfig.xml";
    try {
      FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element iO2File = root.getChild("IO2File");
      if (iO2File != null) {
        map.put("print", (iO2File.getAttributeValue("print") == null) ? "0" : iO2File.getAttributeValue("print"));
        map.put("file", (iO2File.getAttributeValue("file") == null) ? "0" : iO2File.getAttributeValue("file"));
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
}
