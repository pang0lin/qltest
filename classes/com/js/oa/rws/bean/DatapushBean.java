package com.js.oa.rws.bean;

import com.js.util.util.DataSourceBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

public class DatapushBean {
  public List<String[]> getDataList() {
    String sql = "select a.id,a.url,a.smsInfo,a.sendtime,b.workflowprocessname from pro_temptable a left join JSF_WORKFLOWPROCESS b on a.processid=b.wf_workflowprocess_id order by a.id desc";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) });
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public String DataSend(String id) {
    String sql = "select a.url,a.smsInfo from pro_temptable a where a.id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String url = "", smsInfo = "", flag = "1";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        url = rs.getString(1);
        smsInfo = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      conn.close();
      if (!"".equals(url))
        flag = sendMess(url, smsInfo, id); 
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return flag;
  }
  
  private String sendMess(String urlTemp, String smsInfo, String id) {
    String temp = null;
    urlTemp = String.valueOf(urlTemp) + "?";
    System.out.println("urlTemp:" + urlTemp);
    System.out.println("推送的数据：" + smsInfo);
    StringBuffer buffer = new StringBuffer();
    JSONObject jsonObject = null;
    String success = "1";
    try {
      URL url = new URL(urlTemp);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(true);
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
      out.write(smsInfo);
      out.flush();
      out.close();
      InputStream in = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      while ((temp = reader.readLine()) != null) {
        buffer.append(temp);
        setSendStatus(id);
      } 
      jsonObject = JSONObject.fromObject(buffer.toString());
      System.out.println("返回结果：_Status：" + jsonObject.get("_Status") + ",_Message：" + jsonObject.get("_Message"));
      in.close();
      reader.close();
      connection.disconnect();
    } catch (IOException e) {
      System.out.println("推送失败：");
      success = "-1";
      e.printStackTrace();
    } 
    return success;
  }
  
  private void setSendStatus(String id) {
    String sql = "delete from pro_temptable where id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      int count = pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public List<String[]> view(String id) {
    String sql = "select a.url,a.smsInfo,a.sendtime,b.workflowprocessname from pro_temptable a left join JSF_WORKFLOWPROCESS b on a.processid=b.wf_workflowprocess_id where a.id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) });
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
}
