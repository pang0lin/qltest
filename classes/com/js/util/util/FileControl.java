package com.js.util.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FileControl {
  public long getSpaceCanUse(String domainId, String userId) {
    long space = 0L;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select domain_spacesize-domain_usedsize as space from org_domain where domain_id=" + domainId);
      if (rs.next())
        space = rs.getLong(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception er) {
          System.out.println("close connection error.");
        }  
      ex.printStackTrace();
    } 
    return space;
  }
  
  public boolean updateUsedSpace(String domainId, String userId, long fileSize) {
    boolean result = false;
    Connection conn = null;
    Statement stmt = null;
    try {
      int i = 0;
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeQuery("update org_domain set domain_usedsize=domain_usedsize+" + fileSize + " where domain_id=" + domainId);
      stmt.close();
      conn.close();
      result = true;
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception er) {
          System.out.println("close connection error.");
        }  
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean deleteFile(String path, String fileName, String domainId) {
    boolean result = false;
    DeleteFile delF = new DeleteFile();
    return delF.deleteFile(path, fileName, domainId);
  }
}
