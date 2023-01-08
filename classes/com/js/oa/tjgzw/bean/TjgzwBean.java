package com.js.oa.tjgzw.bean;

import com.js.oa.personalwork.setup.po.MyInfoPO;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class TjgzwBean {
  public MyInfoPO getUserInfo(Long curUserId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    MyInfoPO myInfoPO = new MyInfoPO();
    String sql = "select a.empemail2,a.empemail3,a.emplivingphoto from org_employee a where a.emp_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, curUserId.longValue());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        myInfoPO.setEmpEmail2(rs.getString(1));
        myInfoPO.setEmpEmail3(rs.getString(2));
        myInfoPO.setEmpLivingPhoto(rs.getString(3));
      } 
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
    return myInfoPO;
  }
  
  public List<String[]> getLunchInfo(String time) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select mondaylunch,tuesdaylunch,wednesdaylunch,thursdaylunch,fridaylunch,saterdaylunch,sundaylunch from gzw_week_lunch where weekstartdate<=? and weekenddate>=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, time);
      pstmt.setString(2, time);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] obj1 = (String[])null;
        obj1 = rs.getString(1).split("&");
        list.add(obj1);
        obj1 = rs.getString(2).split("&");
        list.add(obj1);
        obj1 = rs.getString(3).split("&");
        list.add(obj1);
        obj1 = rs.getString(4).split("&");
        list.add(obj1);
        obj1 = rs.getString(5).split("&");
        list.add(obj1);
        obj1 = rs.getString(6).split("&");
        list.add(obj1);
        obj1 = rs.getString(7).split("&");
        list.add(obj1);
      } 
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
    return list;
  }
  
  public Object[] getLunchInfoWeek(String year, String weeknum) {
    Object[] object = new Object[2];
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select mondaylunch,tuesdaylunch,wednesdaylunch,thursdaylunch,fridaylunch,saterdaylunch,sundaylunch,id from gzw_week_lunch where year=? and weeknum=? ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, year);
      pstmt.setString(2, weeknum);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] obj1 = (String[])null;
        obj1 = rs.getString(1).split("&");
        list.add(obj1);
        obj1 = rs.getString(2).split("&");
        list.add(obj1);
        obj1 = rs.getString(3).split("&");
        list.add(obj1);
        obj1 = rs.getString(4).split("&");
        list.add(obj1);
        obj1 = rs.getString(5).split("&");
        list.add(obj1);
        obj1 = rs.getString(6).split("&");
        list.add(obj1);
        obj1 = rs.getString(7).split("&");
        list.add(obj1);
        object[0] = rs.getString(8);
      } 
      object[1] = list;
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
    return object;
  }
  
  public String getreceiveFileSendFileUnit(String receiveFileSendFileUnitId) {
    String receiveFileSendFileUnit = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select receivefile_sendfileunit from doc_receivefile where receivefile_id=" + receiveFileSendFileUnitId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        receiveFileSendFileUnit = rs.getString(1); 
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
    return receiveFileSendFileUnit;
  }
  
  public String getEmpIdByApplyId(String applyID) {
    String empId = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select emcee from oa_boardroomapply where BOARDROOMAPPLYID=" + applyID;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        empId = rs.getString(1); 
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
    return empId;
  }
  
  public String getWorkFlowType(String processId) {
    String workFlowType = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select a.formclassname from jsf_workflowprocess a where a.wf_workflowprocess_id=" + processId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        workFlowType = rs.getString(1); 
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
    return workFlowType;
  }
  
  public String getWorkflowprocessname(String processId) {
    String workFlowType = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select a.workflowprocessname from jsf_workflowprocess a where a.wf_workflowprocess_id=" + processId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        workFlowType = rs.getString(1); 
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
    return workFlowType;
  }
  
  public String getRecfileUnit(String workRecordId) {
    String recfileUnit = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select RECEIVEFILE_SENDFILEUNIT from doc_receivefile a where a.RECEIVEFILE_ID=" + workRecordId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        recfileUnit = rs.getString(1); 
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
    return recfileUnit;
  }
  
  public List getReceiveFilePro() {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select workflowprocessname  as v1,workflowprocessname as v2 from jsf_workflowprocess where formclassname='ReceiveFile'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] strs = new String[2];
        strs[0] = rs.getString(1);
        strs[1] = rs.getString(1);
        list.add(strs);
      } 
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
    return list;
  }
  
  public String[] getYearP(String dept, String year, String month, String p1, String p2) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String[] strs = new String[2];
    if (month.length() == 1)
      month = "0" + month; 
    String sql = 
      "select sum((b.jst_3002_f3014 * e.price)) as sum_price                            from jst_3001         a,                                 jst_3002         b,                                 org_employee     c,                                 org_organization d,                                 st_goods         e                           where a.jst_3001_id = b.jst_3002_foreignkey                             and a.jst_3001_owner = c.emp_id                             and a.jst_3001_org = d.org_id                             and b.jst_3002_f3009 = e.goods_name  and b.jst_3002_f3014 > 0                              and  to_CHAR(TO_DATE(a.jst_3001_date,'YYYY-MM-DD'),'MM')='" + 








      
      month + "'" + 
      "                             and to_CHAR(TO_DATE(a.jst_3001_date,'YYYY-MM-DD'),'YYYY')='" + year + "'" + 
      "                             and d.orgname='" + dept + "'";
    if (StringUtils.isNotEmpty(p1))
      sql = String.valueOf(sql) + " and e.goods_name like '%" + p1 + "%' "; 
    if (StringUtils.isNotEmpty(p2))
      sql = String.valueOf(sql) + " and d.orgname like '%" + p2 + "%' "; 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        strs[0] = rs.getString(1); 
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
    return strs;
  }
  
  public String[] getYearP2(String dept, String year, String month, String p1, String p2) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String[] strs = new String[2];
    if (month.length() == 1)
      month = "0" + month; 
    String sql = 
      "select sum(b.jst_3002_f3014) as sum_price                            from jst_3001         a,                                 jst_3002         b,                                 org_employee     c,                                 org_organization d,                                 st_goods         e                           where a.jst_3001_id = b.jst_3002_foreignkey                             and a.jst_3001_owner = c.emp_id                             and a.jst_3001_org = d.org_id                             and b.jst_3002_f3009 = e.goods_name  and b.jst_3002_f3014 > 0                              and  to_CHAR(TO_DATE(a.jst_3001_date,'YYYY-MM-DD'),'MM')='" + 








      
      month + "'" + 
      "                             and to_CHAR(TO_DATE(a.jst_3001_date,'YYYY-MM-DD'),'YYYY')='" + year + "'" + 
      "                             and b.jst_3002_f3009='" + dept + "'";
    if (StringUtils.isNotEmpty(p1))
      sql = String.valueOf(sql) + " and e.goods_name like '%" + p1 + "%' "; 
    if (StringUtils.isNotEmpty(p2))
      sql = String.valueOf(sql) + " and d.orgname like '%" + p2 + "%' "; 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        strs[0] = rs.getString(1); 
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
    return strs;
  }
  
  public int getGoodPrice(String goodName) {
    int price = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = 
      "select price from st_goods where goods_name = '" + goodName + "'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        price = rs.getInt(1); 
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
    return price;
  }
  
  public List<String> getOrgList() {
    List<String> list = new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = 
      "select orgname from org_organization";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      String orgName = "";
      while (rs.next()) {
        orgName = rs.getString(1);
        list.add(orgName);
      } 
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
    return list;
  }
}
