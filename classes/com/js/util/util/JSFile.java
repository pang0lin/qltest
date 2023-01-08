package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class JSFile {
  public static boolean copyToFileServer(String fileName, String toPath) {
    FileInputStream fromStream = null;
    FileOutputStream toStream = null;
    String subPathAndName = "";
    if (fileName.indexOf("upload/") >= 0)
      subPathAndName = fileName.substring(fileName.lastIndexOf("upload/")); 
    if (!"".equals(subPathAndName)) {
      toPath = String.valueOf(toPath) + "/" + subPathAndName;
      System.out.println("应用服务器文件路径:" + fileName);
      System.out.println("文件服务器文件路径:" + toPath);
      try {
        fromStream = new FileInputStream(fileName);
        toStream = new FileOutputStream(toPath);
        byte[] buffer = new byte[4096];
        int pos;
        while ((pos = fromStream.read(buffer)) != -1)
          toStream.write(buffer, 0, pos); 
        if (fromStream != null)
          fromStream.close(); 
        if (toStream != null)
          toStream.close(); 
        return true;
      } catch (Exception ex) {
        try {
          if (fromStream != null)
            fromStream.close(); 
          if (toStream != null)
            toStream.close(); 
        } catch (Exception err) {
          Exception exception1;
          exception1.printStackTrace();
        } 
        System.out.println("复制文件出错！" + ex);
        return false;
      } 
    } 
    return true;
  }
  
  public static boolean copyToFileServerPath(String from, String fileName, String toPath, String subPath) {
    FileInputStream fromStream = null;
    FileOutputStream toStream = null;
    String toFileName = String.valueOf(toPath) + subPath + fileName;
    try {
      fromStream = new FileInputStream(from);
      toStream = new FileOutputStream(toFileName);
      byte[] buffer = new byte[4096];
      int pos;
      while ((pos = fromStream.read(buffer)) != -1)
        toStream.write(buffer, 0, pos); 
      if (fromStream != null)
        fromStream.close(); 
      if (toStream != null)
        toStream.close(); 
      return true;
    } catch (Exception ex) {
      try {
        if (fromStream != null)
          fromStream.close(); 
        if (toStream != null)
          toStream.close(); 
      } catch (Exception err) {
        Exception exception1;
        exception1.printStackTrace();
      } 
      System.out.println("复制文件出错！" + ex);
      return false;
    } 
  }
  
  public static void delete(String fileName) {
    try {
      File file = new File(fileName);
      if (file.exists())
        file.delete(); 
    } catch (Exception e) {
      System.out.println("Error to delete File");
    } 
  }
  
  public boolean copyFile(String from, String to, String fileName) {
    FileInputStream fromStream = null;
    FileOutputStream toStream = null;
    String path = System.getProperty("user.dir");
    path = path.substring(0, path.length() - 3);
    path = String.valueOf(path) + "/webapps/jsoa/upload/";
    from = String.valueOf(path) + from + "/" + fileName;
    to = String.valueOf(path) + to + "/" + fileName;
    try {
      fromStream = new FileInputStream(from);
      toStream = new FileOutputStream(to);
      byte[] buffer = new byte[4096];
      int pos;
      while ((pos = fromStream.read(buffer)) != -1)
        toStream.write(buffer, 0, pos); 
      if (fromStream != null)
        fromStream.close(); 
      if (toStream != null)
        toStream.close(); 
      return true;
    } catch (Exception ex) {
      System.out.println("复制文件出错！" + ex);
      return false;
    } 
  }
  
  public boolean deleteFile(String path, String fileName, String domainId) {
    boolean result = false;
    DeleteFile delF = new DeleteFile();
    return delF.deleteFile(path, fileName, domainId);
  }
  
  public boolean copyUploadFolder(String uploadPath, String oldSubPath, String newSubPath) {
    boolean result = false;
    List<File> oldFolderList = new ArrayList<File>();
    File oldFolder = new File(String.valueOf(uploadPath) + "/" + oldSubPath);
    if (oldFolder.exists()) {
      File[] file = oldFolder.listFiles();
      for (int j = 0; j < file.length; j++) {
        if (file[j].isDirectory())
          oldFolderList.add(file[j]); 
      } 
    } 
    File desFolder = new File(String.valueOf(uploadPath) + "/" + newSubPath);
    if (!desFolder.exists())
      desFolder.mkdir(); 
    for (int i = 0; i < oldFolderList.size(); i++) {
      File tmp = new File(String.valueOf(uploadPath) + "/" + newSubPath + "/" + ((File)oldFolderList.get(i)).getName());
      if (!tmp.exists())
        tmp.mkdir(); 
    } 
    oldFolderList.clear();
    return result;
  }
  
  public static boolean fileIsExists(String fileName) {
    boolean exists = false;
    try {
      File file = new File(fileName);
      if (file.exists())
        exists = true; 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return exists;
  }
}
