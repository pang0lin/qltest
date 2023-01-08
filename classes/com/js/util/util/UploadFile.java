package com.js.util.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

public class UploadFile {
  private long docSize = 0L;
  
  public boolean setFileSize(String fileName, int fileSize) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    try {
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("insert into oa_allattach(filename,filesize) values('" + fileName + "'," + fileSize + ")");
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return false;
  }
  
  public String getFileName(String saveName) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    String fileName = "";
    try {
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select disname from oa_allattach where fileName='" + saveName + "'");
      if (rs.next())
        fileName = rs.getString(1); 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return fileName;
  }
  
  public String getFileSize(String fileName) {
    String fileSize = "OK";
    long accessorySize = 0L;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    try {
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select filesize from oa_allattach where fileName='" + fileName + "'");
      if (rs.next())
        accessorySize = rs.getLong(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    this.docSize = accessorySize;
    if (accessorySize < 1048576L) {
      fileSize = String.valueOf(accessorySize / 1024.0D);
      if (fileSize.length() > fileSize.indexOf(".") + 3)
        fileSize = fileSize.substring(0, fileSize.indexOf(".") + 3); 
      fileSize = String.valueOf(fileSize) + "K";
    } else {
      fileSize = String.valueOf(accessorySize / 1048576.0D);
      if (fileSize.length() > fileSize.indexOf(".") + 3)
        fileSize = fileSize.substring(0, fileSize.indexOf(".") + 3); 
      fileSize = String.valueOf(fileSize) + "M";
    } 
    return fileSize;
  }
  
  public int getFileSizeStr(String fileName) {
    long accessorySize = 0L;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    try {
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select filesize from oa_allattach where fileName='" + fileName + "'");
      if (rs.next())
        accessorySize = rs.getLong(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return Integer.parseInt(String.valueOf(accessorySize));
  }
  
  public long getFileLongSize() {
    return this.docSize;
  }
  
  public boolean setFileSizeArray(String fileName, String fileSize) {
    boolean res = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    try {
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      if (fileName.indexOf("|") >= 0) {
        String[] fileNameArr = fileName.split("\\|");
        String[] fileSizeArr = fileSize.split("\\|");
        for (int i = 0; i < fileNameArr.length; i++) {
          if (fileNameArr[i] != null && !"".equals(fileNameArr[i]))
            stmt.executeUpdate(
                "insert into oa_allattach(filename,filesize) values('" + 
                fileNameArr[i] + "'," + fileSizeArr[i] + ")"); 
        } 
      } else {
        stmt.executeUpdate(
            "insert into oa_allattach(filename,filesize) values('" + 
            fileName + "'," + fileSize + ")");
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res;
  }
}
