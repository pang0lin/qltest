package com.js.db;

import com.js.util.util.DataSourceBase;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.sql.BLOB;

public class FileToDB {
  public void FileToDB() {}
  
  public void wordToMySQL(String path) {
    File file = new File(path);
    String[] filelist = file.list();
    if (filelist.length > 0) {
      Connection conn = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select max(recordId) from document_file");
        rs.close();
        String sql = "insert into Document_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < filelist.length; i++) {
          String fileTemp = filelist[i];
          if (fileTemp.length() < 20 && fileTemp.endsWith(".doc")) {
            System.out.println(fileTemp.substring(0, fileTemp.indexOf(".doc")));
            byte[] mFileBody = getFileBytes(String.valueOf(path) + "/" + fileTemp);
            pstmt.setString(1, fileTemp.substring(0, fileTemp.indexOf(".doc")));
            pstmt.setString(2, fileTemp);
            pstmt.setString(3, ".doc");
            pstmt.setInt(4, mFileBody.length);
            pstmt.setString(5, "2012-10-18 00:00:00");
            pstmt.setBytes(6, mFileBody);
            pstmt.setString(7, path);
            pstmt.setString(8, "管理员");
            pstmt.setString(9, "通用版本");
            pstmt.execute();
          } 
        } 
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
  }
  
  private byte[] getFileBytes(String file) {
    byte[] content = (byte[])null;
    try {
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
      ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
      byte[] temp = new byte[1024];
      int size = 0;
      while ((size = in.read(temp)) != -1)
        out.write(temp, 0, size); 
      in.close();
      content = out.toByteArray();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return content;
  }
  
  public String wordToBLOB(String path) {
    String blobID = "1";
    Connection conn = null;
    boolean commitFlag = true;
    int maxType = 1;
    try {
      conn = (new DataSourceBase()).getDataSource("jdbc/oatest").getConnection();
      commitFlag = conn.getAutoCommit();
      conn.setAutoCommit(false);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select max(type) from NEWOA_ATTACHMENT");
      while (rs.next())
        maxType = rs.getInt(1); 
      rs.close();
      maxType++;
      stmt.executeUpdate("insert into NEWOA_ATTACHMENT(id,type,val) values(1," + maxType + ",empty_blob())");
      rs = stmt.executeQuery("select val from NEWOA_ATTACHMENT where type=" + maxType + " for update");
      rs.next();
      BLOB oracleBLOB = (BLOB)rs.getBlob("val");
      OutputStream out = oracleBLOB.getBinaryOutputStream();
      byte[] mFileBody = getFileBytes(path);
      out.write(mFileBody);
      out.flush();
      rs.close();
      conn.commit();
      stmt.close();
      conn.setAutoCommit(commitFlag);
      conn.close();
      out.close();
    } catch (Exception ex) {
      blobID = "-1";
      if (conn != null) {
        try {
          conn.rollback();
          conn.setAutoCommit(commitFlag);
        } catch (Exception err) {
          err.printStackTrace();
        } 
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        } 
      } 
      ex.printStackTrace();
    } 
    return blobID;
  }
}
