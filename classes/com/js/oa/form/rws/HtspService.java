package com.js.oa.form.rws;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class HtspService extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String processId = request.getParameter("processId");
    String tableName = getTableName(tableId);
    JSONObject json = getData(recordId, tableName, "complete");
    sendMess(json, recordId, processId);
    return result;
  }
  
  private JSONObject getData(String recordId, String tableName, String flag) {
    String xml = "";
    String sql = "select t.jst_3069_f4521,t.jst_3069_f4522,t.jst_3069_f4523 from jst_3069 t where t.jst_3069_id=?";
    if ("jst_3064".equals(tableName))
      sql = "select t.jst_3064_f4524,t.jst_3064_f4525,t.jst_3064_f4526 from jst_3064 t where t.jst_3064_id=?"; 
    if ("jst_3047".equals(tableName))
      sql = "select t.jst_3047_f4530,t.jst_3047_f4531,t.jst_3047_f4532 from jst_3047 t where t.jst_3047_id=?"; 
    if ("jst_3133".equals(tableName))
      sql = "select t.jst_3133_f4527,t.jst_3133_f4528,t.jst_3133_f4529 from jst_3133 t where t.jst_3133_id=?"; 
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    String contractid = "", audittype = "", auditstatus = "";
    JSONObject json = new JSONObject();
    String status = "审核通过";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        contractid = rs.getString(1);
        audittype = rs.getString(2);
        auditstatus = rs.getString(3);
      } 
      if (!"1".equals(auditstatus))
        status = "审核退回"; 
      if ("delete".equals(flag))
        status = "审核退回"; 
      json.put("contractid", contractid);
      json.put("audittype", audittype);
      json.put("auditstatus", status);
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
    return json;
  }
  
  private void sendMess(JSONObject json, String recordId, String processId) {
    String temp = null;
    StringBuffer buffer = new StringBuffer();
    JSONObject jsonObject = null;
    String jsonStr = json.toString();
    String smsInfo = "";
    String urltemp = "http://zvingbj.ticp.net:86/zcms/api/json?";
    urltemp = SystemCommon.getRwsurl();
    System.out.println("urltemp:" + urltemp);
    HibernateBase hb = new HibernateBase();
    try {
      smsInfo = "method=contractaudit&username=oaaudit&password=admin&params=" + 

        
        jsonStr;
      System.out.println("推送的数据:" + smsInfo);
      URL url = new URL(urltemp);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
      out.write(smsInfo);
      out.flush();
      out.close();
      InputStream in = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      while ((temp = reader.readLine()) != null)
        buffer.append(temp); 
      jsonObject = JSONObject.fromObject(buffer.toString());
      String result = "_Status：" + jsonObject.get("_Status") + ",_Message：" + jsonObject.get("_Message");
      System.out.println("result:" + result);
      in.close();
      reader.close();
      connection.disconnect();
    } catch (IOException e) {
      System.out.println("推送失败：");
      if (temp == null) {
        String sql = "insert into pro_temptable(id,url,smsInfo,recordId,processId,sendtime) values(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        DataSourceBase base = new DataSourceBase();
        DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtm.format(new Date());
        try {
          conn = base.getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          long rightScopeSeq = hb.getTableId().longValue();
          pstmt.setLong(1, rightScopeSeq);
          pstmt.setString(2, urltemp);
          pstmt.setString(3, smsInfo);
          pstmt.setString(4, recordId);
          pstmt.setString(5, processId);
          pstmt.setString(6, timeStr);
          pstmt.executeUpdate();
          pstmt.close();
          conn.close();
        } catch (Exception e1) {
          System.out.println("保存失败：");
          try {
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (Exception e2) {
            e2.printStackTrace();
          } 
          e1.printStackTrace();
        } 
      } 
      e.printStackTrace();
    } 
  }
  
  private String getTableName(String tableId) {
    String sql = "select area_table from tarea where page_id=? and area_name='form1'";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String tableName = "";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableId);
      rs = pstmt.executeQuery();
      if (rs.next())
        tableName = rs.getString(1); 
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
    return tableName;
  }
  
  public void delete(HttpServletRequest request) {
    super.delete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String processId = request.getParameter("processId");
    String tableName = getTableName(tableId);
    JSONObject json = getData(recordId, tableName, "delete");
    sendMess(json, recordId, processId);
  }
}
