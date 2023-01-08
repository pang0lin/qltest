package com.js.oa.form.hzzf;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class SendPostUtil {
  public static String sendFile(String xml) {
    String sendStatus = "";
    try {
      PostMethod postMethod = new PostMethod(
          "http://ebs.iboxpay.com:8000/webservices/rest/TransferData/transfer_data/");
      StringRequestEntity stringRequestEntity = new StringRequestEntity(xml, 
          "application/xml", "utf-8");
      postMethod.setRequestEntity((RequestEntity)stringRequestEntity);
      HttpClient httpClient = new HttpClient();
      UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
          "INTERFACE_USER", "g6RO@TiX");
      httpClient.getState().setCredentials(AuthScope.ANY, (Credentials)creds);
      int statusCode = httpClient.executeMethod((HttpMethod)postMethod);
      if (statusCode == 200) {
        sendStatus = "S";
        System.out.println("调用成功！");
        byte[] soapResponseData = postMethod.getResponseBody();
        String str = new String(soapResponseData, "UTF-8");
      } else {
        sendStatus = "F";
        System.out.println("调用失败！错误码：" + statusCode);
      } 
    } catch (Exception e) {
      sendStatus = "ERROR";
      e.printStackTrace();
    } 
    return sendStatus;
  }
  
  public static void insertFailRecord(String recordId, String xml, String processStatus) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "INSERT INTO SENDFAILTABLE values (?,?,?)";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      pstmt.setString(2, xml);
      pstmt.setString(3, processStatus);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      free(rs, pstmt, conn);
      e.printStackTrace();
    } 
  }
  
  public static void deleteFailRecord(String xml) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String sql = "DELETE FROM SENDFAILTABLE where xml=?";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, xml);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      free(rs, pstmt, conn);
      e.printStackTrace();
    } 
  }
  
  public static void free(ResultSet rs, PreparedStatement pstmt, Connection conn) {
    try {
      if (rs != null)
        rs.close(); 
      if (pstmt != null)
        pstmt.close(); 
      if (conn != null)
        conn.close(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
