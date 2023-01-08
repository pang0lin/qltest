package com.js.oa.personalwork.setup.service;

import com.js.oa.audit.po.OrganizationPO;
import com.js.oa.personalwork.setup.action.MyInfoActionForm;
import com.js.oa.personalwork.setup.bean.MyInfoEJBHome;
import com.js.oa.personalwork.setup.po.MyInfoPO;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class MyInfoBD {
  private static Logger logger = Logger.getLogger(MyInfoBD.class.getName());
  
  public MyInfoActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    MyInfoActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (MyInfoActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public MyInfoPO load(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    MyInfoPO myInfoPO = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, "String");
      myInfoPO = (MyInfoPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return myInfoPO;
  }
  
  public String update(MyInfoPO paraPO, String userId, String userAccount) {
    ParameterGenerator pg = new ParameterGenerator(3);
    Object object = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(paraPO, MyInfoPO.class);
      pg.put(userId, "String");
      pg.put(userAccount, "String");
      object = ejbProxy.invoke("update", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public String updatePass(String sessionPass, String oldPass, String newPass, String userId, String userAccount) {
    ParameterGenerator pg = new ParameterGenerator(5);
    Object object = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(sessionPass, String.class);
      pg.put(oldPass, String.class);
      pg.put(newPass, String.class);
      pg.put(userId, "String");
      pg.put(userAccount, String.class);
      object = ejbProxy.invoke("updatePass", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public void ejbMethod(MyInfoActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(form, MyInfoActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }
  
  public boolean updateRTXLogin(String userId, String rtxLogin) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(rtxLogin, String.class);
      result = ((Boolean)ejbProxy.invoke("updateRTXLogin", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String loadRTXLogin(String userId) {
    String result = "0";
    ParameterGenerator pg = new ParameterGenerator(1);
    String mes = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      result = (String)ejbProxy.invoke("loadRTXLogin", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer skinSetup(String userId, String skin) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(skin, String.class);
      result = (Integer)ejbProxy.invoke("skinSetup", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Object[] getEmpStatus(String userId) {
    Object[] result = (Object[])null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      result = (Object[])ejbProxy.invoke("getEmpStatus", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer setEmpStatus(String userId, String curStatus, String rtxlogin) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(curStatus, String.class);
      pg.put(rtxlogin, String.class);
      result = (Integer)ejbProxy.invoke("setEmpStatus", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer saveEmpDefineStatus(String userId, String DefineStatus) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(DefineStatus, String.class);
      result = (Integer)ejbProxy.invoke("saveEmpDefineStatus", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer saveEmpNewStatus(String userId, String NewStatus) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(NewStatus, String.class);
      result = (Integer)ejbProxy.invoke("saveEmpNewStatus", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer delEmpStatus(String userId, String curStatus) {
    Integer result = Integer.valueOf("0");
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", 
          "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String.class);
      pg.put(curStatus, String.class);
      result = (Integer)ejbProxy.invoke("delEmpStatus", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getEmpStatusByEmpIdArr(String[] userId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userId, String[].class);
      result = (List)ejbProxy.invoke("getEmpStatusByEmpIdArr", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getEmpStatusByEmpIdStr(String userIds) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(userIds, String.class);
      result = (List)ejbProxy.invoke("getEmpStatusByEmpIdStr", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getEmpStatusByEmpOrgGrp(String empIdStr, String orgIdStr, String grpIdStr) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("MyInfoEJB", "MyInfoEJBLocal", MyInfoEJBHome.class);
      pg.put(empIdStr, String.class);
      pg.put(orgIdStr, String.class);
      pg.put(grpIdStr, String.class);
      result = (List)ejbProxy.invoke("getEmpStatusByEmpOrgGrp", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public OrganizationPO getOrgOfEmp(String empId) {
    String sql = "select b.org_id,b.ORGNAME from org_organization_user a,org_organization b where a.EMP_ID = ? and a.ORG_ID = b.ORG_ID";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    OrganizationPO po = new OrganizationPO();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, Long.valueOf(empId).longValue());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Long orgId = Long.valueOf(rs.getLong(1));
        String orgName = rs.getString(2);
        po.setOrgId(orgId);
        po.setOrgName(orgName);
      } 
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
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return po;
  }
}
