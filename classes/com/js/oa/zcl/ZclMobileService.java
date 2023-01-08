package com.js.oa.zcl;

import com.js.oa.zcl.util.AESUtils;
import com.js.oa.zcl.util.HttpClientUtil;
import com.js.oa.zcl.util.WebserviceUtil;
import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import net.sf.json.JSONObject;
import org.jdom.JDOMException;

public class ZclMobileService {
  private static String AES_key = "InspurCB&JiusiOA=NB502";
  
  public String perService(String xml) throws Exception {
    String useraccount = "";
    String empid = "";
    String taskXml = "";
    String decrypt = "";
    String loginSuccess = "";
    String userid = "";
    String jsonStr = "";
    String type = "";
    WebserviceUtil wu = new WebserviceUtil();
    AESUtils aes = new AESUtils();
    System.out.println("接收到的xml----：" + xml);
    try {
      try {
        decrypt = AESUtils.aesDecrypt(xml, AES_key);
        System.out.println("获取用户信息解密后的数据：");
        System.out.println(decrypt);
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      Map map = wu.getLogInfo(decrypt);
      String charset = "utf-8";
      String url = (String)map.get("url");
      String sessionId = (String)map.get("sessionid");
      String uuid = (String)map.get("uuid");
      userid = uuid.substring(uuid.indexOf(".") + 1);
      userid = AESUtils.aesEncrypt(userid, AES_key);
      sessionId = AESUtils.aesEncrypt(sessionId, AES_key);
      HttpClientUtil httpClientUtil = new HttpClientUtil();
      String httpOrgCreate = String.valueOf(url) + "&sessionId=" + sessionId + "&userid=" + userid;
      String returnXml = httpClientUtil.doGet(httpOrgCreate, charset);
      try {
        returnXml = AESUtils.aesDecrypt(returnXml, AES_key);
        System.out.println("获取session验证后解密后的数据：");
        System.out.println(returnXml);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (!"".equals(returnXml)) {
        JSONObject jsonobj = JSONObject.fromObject(returnXml);
        returnXml = jsonobj.get("data").toString();
        Map map1 = wu.getLogInfo(returnXml);
        loginSuccess = (String)map1.get("flag");
        if ("true".equals(loginSuccess)) {
          useraccount = (String)map.get("uuid");
          type = (String)map.get("type");
          empid = getEmpId(useraccount);
          if ("gwcy".equals(type)) {
            taskXml = docCheck(empid, useraccount, type);
          } else {
            taskXml = dealTask(empid, useraccount, type);
          } 
          taskXml = AESUtils.aesEncrypt(taskXml, AES_key);
        } 
      } 
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return taskXml;
  }
  
  public String dealTask(String empid, String useraccount, String type) {
    String sql = "SELECT b.workrecord_id,b.worktitle,b.wf_submitemployee_id,b.worksubmitperson,b.worksubmittime,b.workmainlinkfile,b.wf_work_id FROM jsf_workflowprocess a LEFT JOIN jsf_work b ON a.wf_workflowprocess_id = b.workprocess_id LEFT JOIN jsf_package c ON c.wf_package_id = a.wf_package_id WHERE b.wf_curemployee_id=? AND b.WORKSTATUS = ? AND c.wf_module_id IN(2,3) AND b.WORKLISTCONTROL = 1 AND b.WORKDELETE = 0 ORDER BY  b.emergence DESC, b.WF_WORK_ID DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    int count = 0;
    String time = "";
    String workid = "";
    String status = "0";
    if ("ybsx".equals(type))
      status = "101"; 
    StringBuffer sb = new StringBuffer("");
    StringBuffer sb1 = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      pstmt.setString(2, status);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        time = rs.getString(5);
        workid = rs.getString(7);
        if (time.lastIndexOf(".0") > 0)
          time = time.substring(0, time.lastIndexOf(".0")); 
        sb.append("<data>");
        sb.append("<uuid>" + rs.getString(1) + "</uuid>");
        sb.append("<title>" + rs.getString(2) + "</title>");
        sb.append("<sender>" + rs.getString(4) + "</sender>");
        sb.append("<time>" + time + "</time>");
        sb.append("<href>/jsoa/zcl/goPageUrl.jsp?userName=" + useraccount + "&workid=" + workid + "&type=" + type + "</href>");
        sb.append("</data>");
        count++;
      } 
      long timelong = (new Date()).getTime();
      sb1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      sb1.append("<datas>");
      sb1.append("<result>1</result>");
      sb1.append("<user_id>" + empid + "</user_id>");
      sb1.append("<count>" + count + "</count>");
      sb1.append("<timestamp>" + timelong + "</timestamp>");
      sb1.append(sb);
      sb1.append("</datas>");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
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
    return sb1.toString();
  }
  
  public String docCheck(String empid, String useraccount, String type) {
    String sql = "SELECT DISTINCT a.DOCUMENTSENDFILE_ID,a.DOCUMENTSENDFILE_TITLE,a.createdTime,a.CREATEDEMP,b.SENDFILE_USER_ID FROM DOC_DOCUMENTSENDFILE a INNER JOIN DOC_SENDFILE_USER b ON a.DOCUMENTSENDFILE_ID = b.SENDFILE_ID  WHERE ((b.outSeeType IS NULL) OR (b.outSeeType <> '1')) AND ((b.EMP_ID = ?) OR ((1 <> 1)AND (b.orgId IS NOT NULL) ))  AND (a.SendFile_OverSee <> 2) AND (a.DOMAIN_ID = 0) ORDER BY a.DOCUMENTSENDFILE_ID DESC ";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    int count = 0;
    String time = "";
    StringBuffer sb = new StringBuffer("");
    StringBuffer sb1 = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empid);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        time = rs.getString(3);
        if (time.lastIndexOf(".0") > 0)
          time = time.substring(0, time.lastIndexOf(".0")); 
        sb.append("<data>");
        sb.append("<uuid>" + rs.getString(1) + "</uuid>");
        sb.append("<title>" + rs.getString(2) + "</title>");
        sb.append("<sender>" + rs.getString(4) + "</sender>");
        sb.append("<time>" + time + "</time>");
        sb.append("<href>/jsoa/zcl/goPageUrl.jsp?userName=" + useraccount + "&sendfileId=" + rs.getString(1) + "&type=" + type + "</href>");
        sb.append("</data>");
        count++;
      } 
      long timelong = (new Date()).getTime();
      sb1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      sb1.append("<datas>");
      sb1.append("<result>1</result>");
      sb1.append("<user_id>" + empid + "</user_id>");
      sb1.append("<count>" + count + "</count>");
      sb1.append("<timestamp>" + timelong + "</timestamp>");
      sb1.append(sb);
      sb1.append("</datas>");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
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
    return sb1.toString();
  }
  
  private String getEmpId(String useraccount) {
    String empId = "";
    String sql = "select emp_id from org_employee where useraccounts=? and USERISFORMALUSER = 1 and DOMAIN_ID = 0 and USERISACTIVE = 1 and USERISDELETED = 0";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
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
    return empId;
  }
}
