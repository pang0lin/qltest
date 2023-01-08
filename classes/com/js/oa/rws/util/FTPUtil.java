package com.js.oa.rws.util;

import com.js.util.config.SysConfigReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {
  private static FTPClient ftp = new FTPClient();
  
  private static String encoding = System.getProperty("file.encoding");
  
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
  
  private static void connectServer() {
    try {
      String ip = SysConfigReader.readConfigValue("FtpServer", "server");
      String port = SysConfigReader.readConfigValue("FtpServer", "port");
      String username = SysConfigReader.readConfigValue("FtpServer", "user");
      String password = SysConfigReader.readConfigValue("FtpServer", "password");
      String workingDirectory = SysConfigReader.readConfigValue("FtpServer", "workingDirectory");
      ftp.connect(ip, Integer.valueOf(port).intValue());
      ftp.login(username, password);
      ftp.setControlEncoding(encoding);
      int reply = ftp.getReplyCode();
      if (!FTPReply.isPositiveCompletion(reply)) {
        System.out.println("连接失败！");
        ftp.disconnect();
      } 
      changeWorkingDirectory(workingDirectory);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private static void changeWorkingDirectory(String path) {
    try {
      String temp = path;
      temp = temp.replaceAll("\\\\", "/");
      String[] paths = temp.split("/");
      String curPath = "";
      for (int i = 0; i < paths.length; i++) {
        if (!"".equals(paths[i])) {
          curPath = paths[i];
          boolean f = ftp.changeWorkingDirectory(curPath);
          if (!f) {
            f = ftp.makeDirectory(curPath);
            if (!f)
              System.out.println("生成目录失败！path=" + curPath); 
            f = ftp.changeWorkingDirectory(curPath);
          } 
        } 
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static boolean uploadFile(String filePath, File file) {
    boolean result = false;
    connectServer();
    changeWorkingDirectory("/" + filePath);
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      ftp.setFileType(2);
      result = ftp.storeFile(file.getName(), fis);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null)
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
      closeConnect();
    } 
    return result;
  }
  
  private static void closeConnect() {
    if (ftp != null)
      try {
        ftp.logout();
        ftp.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }  
  }
  
  public static void uploadAllFilesUnderPath(String path, String guid) {
    uploadFile(guid, new File(String.valueOf(path) + "/DataSet.xml"));
    File file = new File(String.valueOf(path) + "/Attachment");
    File[] files = file.listFiles();
    for (int i = 0; i < files.length; i++) {
      File[] contents = files[i].listFiles();
      String temp = files[i].getAbsolutePath();
      temp = temp.replaceAll("\\\\", "/");
      temp = temp.substring(temp.lastIndexOf("/"));
      for (int j = 0; j < contents.length; j++)
        uploadFile(String.valueOf(guid) + "/Attachment" + temp, contents[j]); 
    } 
    File localFile = new File(path);
    localFile.deleteOnExit();
  }
  
  public static void main(String[] args) {
    File file = new File("G:/kqplatform/jining/Co_Browser/AffixFiles/BB0574593F4CA54E75B6FAF37F5BDD4327");
    File[] files = file.listFiles();
    for (int i = 0; i < files.length; i++) {
      boolean flag = uploadFile("jn", files[i]);
      System.out.println(flag);
    } 
  }
}
