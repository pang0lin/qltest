package com.js.util.util;

import com.js.system.util.SysSetupReader;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class DeleteFile {
  private String directory;
  
  private String fileName;
  
  private String domainId;
  
  public DeleteFile(String dir, String fileName, String domainId) {
    this.directory = dir;
    this.fileName = fileName;
    this.domainId = domainId;
  }
  
  public DeleteFile(String dir, String fileName) {
    this.directory = dir;
    this.fileName = fileName;
  }
  
  public DeleteFile() {}
  
  public void delete() {
    String path = "";
    String srcLove = "0000";
    if (this.fileName != null && this.fileName.length() > 6 && this.fileName.substring(4, 5) == "_") {
      srcLove = this.fileName.substring(0, 4);
    } else {
      srcLove = "0000";
    } 
    path = String.valueOf(path) + "/upload/" + srcLove + "/" + this.directory + "/";
    try {
      File file = new File(String.valueOf(path) + this.fileName);
      if (file.exists())
        file.delete(); 
    } catch (Exception e) {
      System.out.println("Error to delete File");
    } 
  }
  
  public void delete(String directory, String fileName) {
    String path = "";
    String srcLove = "0000";
    if (fileName != null && fileName.length() > 6 && fileName.substring(4, 5).equals("_")) {
      srcLove = fileName.substring(0, 4);
    } else {
      srcLove = "0000";
    } 
    path = String.valueOf(path) + "/upload/" + srcLove + "/" + directory + "/";
    try {
      File file = new File(String.valueOf(path) + fileName);
      if (file.exists())
        file.delete(); 
    } catch (Exception e) {
      System.out.println("Error to delete File");
    } 
  }
  
  public boolean deleteFile(String path, String fileName, String domainId) {
    boolean result = false;
    Map sysMap11 = SysSetupReader.getInstance().getSysSetupMap(domainId);
    int smartInUse1 = 0;
    if (sysMap11 != null && sysMap11.get("附件上传") != null)
      smartInUse1 = Integer.parseInt(sysMap11.get("附件上传").toString()); 
    try {
      long fileSize = 0L;
      if (smartInUse1 != 0) {
        String filePath = System.getProperty("user.dir");
        filePath = filePath.replaceAll("\\\\", "/");
        String srcLove = "0000";
        if (fileName != null && fileName.length() > 6 && fileName.substring(4, 5).equals("_")) {
          srcLove = fileName.substring(0, 4);
        } else {
          srcLove = "0000";
        } 
        filePath = String.valueOf(filePath) + "/jsoa/upload/" + srcLove + "/" + path + "/";
        File file = new File(String.valueOf(filePath) + fileName);
        if (file.exists()) {
          fileSize = file.length();
          updateSpaceSize(domainId, fileSize);
          file.delete();
        } 
      } 
      result = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean updateSpaceSize(String domainId, long fileSize) {
    boolean result = false;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("update org_domain set domain_usedsize=domain_usedsize-" + fileSize + " where domain_id=" + domainId);
      stmt.close();
      conn.close();
      result = true;
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return result;
  }
}
