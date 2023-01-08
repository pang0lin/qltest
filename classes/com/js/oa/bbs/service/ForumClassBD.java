package com.js.oa.bbs.service;

import com.js.oa.bbs.bean.ForumClassEJBBean;
import com.js.oa.bbs.bean.ForumClassEJBHome;
import com.js.oa.bbs.po.ForumClassPO;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class ForumClassBD {
  private static Logger logger = Logger.getLogger(ForumClassBD.class.getName());
  
  public String selectSimpleName(String simpleNames, String domainId) {
    String simpleNameStr = "";
    Connection conn = null;
    Statement stmt = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      ResultSet rs = null;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String Sql = "select emp.EMP_ID,emp.empName,org.orgSimpleName  from ORG_EMPLOYEE emp,ORG_ORGANIZATION_USER emporg,org_organization org  where emp.emp_id = emporg.emp_id and org.org_id=emporg.org_id and emp.emp_id in(" + 
        
        simpleNames + 
        ") and emp.domain_Id = " + Long.valueOf(domainId);
      rs = stmt.executeQuery(Sql);
      while (rs.next())
        simpleNameStr = 
          String.valueOf(simpleNameStr) + 
          rs.getString(1) + 
          "<" + 
          rs.getString(2) + 
          "/" + 
          rs.getString(3) + 
          ">,"; 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      System.out.println("Error in selectSimpleName  " + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return simpleNameStr;
  }
  
  public List see(String rightWhere, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(rightWhere, "String");
      pg.put(domainId, String.class);
      list = (List)ejbProxy.invoke("see", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public String add(ForumClassPO forumClassPO, String parentId, String currentSortCode, String insertSite, String parentSort) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(forumClassPO, ForumClassPO.class);
      pg.put(parentId, "String");
      pg.put(currentSortCode, "String");
      pg.put(insertSite, "String");
      pg.put(parentSort, "String");
      object = ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public List listMenu(String wherePara, String curUserId, String domainId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(wherePara, "String");
      pg.put(curUserId, "String");
      pg.put(domainId, "String");
      list = (List)ejbProxy.invoke("listMenu", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public Vector list(Integer offset, String queryText, String wherePara) {
    Vector vec = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(offset, "Integer");
      pg.put(queryText, "String");
      pg.put(wherePara, "String");
      vec = (Vector)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return vec;
  }
  
  public String del(String id) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(id, "String");
      object = ejbProxy.invoke("del", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public ForumClassPO load(String editId) {
    ForumClassPO po = new ForumClassPO();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(editId, "String");
      po = (ForumClassPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public String update(String classEmail, String BanPrint, String ownerIdss, String editType, String editId, String userOrgGroup, Long classOwnId, String classOwnerName, String classUserName, Short classLevel, String className, String classRemark, String parentId, String currentSortCode, String insertSite, String parentSort, String checkExamin, String startPeriod, String endPeriod, Integer fullDay, String estopAnonymity) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(21);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", "ForumClassEJBLocal", ForumClassEJBHome.class);
      pg.put(classEmail, "String");
      pg.put(BanPrint, "String");
      pg.put(ownerIdss, "String");
      pg.put(editType, "String");
      pg.put(editId, "String");
      pg.put(userOrgGroup, "String");
      pg.put(classOwnId, "Long");
      pg.put(classOwnerName, "String");
      pg.put(classUserName, "String");
      pg.put(classLevel, "Short");
      pg.put(className, "String");
      pg.put(classRemark, "String");
      pg.put(parentId, "String");
      pg.put(currentSortCode, "String");
      pg.put(insertSite, "String");
      pg.put(parentSort, "String");
      pg.put(checkExamin, "String");
      pg.put(startPeriod, "String");
      pg.put(endPeriod, "String");
      pg.put(fullDay, "Integer");
      pg.put(estopAnonymity, "String");
      object = ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String getClassIdString(String curUserId, String poId) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(curUserId, "String");
      pg.put(poId, "String");
      object = 
        ejbProxy.invoke("getClassIdString", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public String getClassIdString(String curUserId, String poId, String wherePara) {
    Object object = "";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(curUserId, "String");
      pg.put(poId, "String");
      pg.put(wherePara, "String");
      object = 
        ejbProxy.invoke("getClassIdString", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return (String)object;
  }
  
  public Boolean isClassOwner(Long userId, Long domainId) {
    Boolean result = new Boolean(false);
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ForumClassEJB", 
          "ForumClassEJBLocal", 
          ForumClassEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (Boolean)ejbProxy.invoke("isClassOwner", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Object[] searchClassHasJunior(long fornmId) throws Exception {
    Object[] obj = new Object[2];
    ForumClassEJBBean forumClassEJBBean = new ForumClassEJBBean();
    obj = forumClassEJBBean.searchClassHasJunior(fornmId);
    return obj;
  }
  
  public String canIssureNewTitle(String userId, String orgId, String orgIdString, String classId) {
    String res = "0";
    ForumClassEJBBean forumClassEJBBean = new ForumClassEJBBean();
    try {
      res = forumClassEJBBean.canIssureNewTitle(userId, orgId, orgIdString, classId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
    return res;
  }
}
