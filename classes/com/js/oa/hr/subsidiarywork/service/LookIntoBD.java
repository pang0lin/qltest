package com.js.oa.hr.subsidiarywork.service;

import com.js.oa.hr.subsidiarywork.action.LookIntoActionForm;
import com.js.oa.hr.subsidiarywork.bean.LookIntoEJBHome;
import com.js.oa.hr.subsidiarywork.po.NetSurveyPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.apache.log4j.Logger;

public class LookIntoBD {
  private static Logger logger = Logger.getLogger(LookIntoBD.class.getName());
  
  public LookIntoActionForm ejbMethodGetForm(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    LookIntoActionForm form = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      form = (LookIntoActionForm)ejbProxy.invoke(methodName, 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return form;
  }
  
  public void ejbMethod(LookIntoActionForm form, HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(form, LookIntoActionForm.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ejbMethod(HttpServletRequest request, String methodName) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      ejbProxy.invoke(methodName, pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(HttpServletRequest request, String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(request, HttpServletRequest.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void delAll(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(wherePara, "String");
      ejbProxy.invoke("delAll", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public void add(Object[] obj, String userId, String orgId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(obj, Object[].class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void update(String surveyBeginTime, String surveyEndTime, String surveyContent, String surveyRange, String surveyRangeName, String surveyStatus, String surveyType, String[] updateItems, String[] updateItemsIds, String[] newItems, String delItems, String editId) {
    ParameterGenerator pg = new ParameterGenerator(12);
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(surveyBeginTime, String.class);
      pg.put(surveyEndTime, String.class);
      pg.put(surveyContent, String.class);
      pg.put(surveyRange, String.class);
      pg.put(surveyRangeName, String.class);
      pg.put(surveyStatus, String.class);
      pg.put(surveyType, String.class);
      pg.put(updateItems, String[].class);
      pg.put(updateItemsIds, String[].class);
      pg.put(newItems, String[].class);
      pg.put(delItems, String.class);
      pg.put(editId, String.class);
      ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public Map load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(editId, "String");
      result = (Map<Object, Object>)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String voteAdd(String surveyId, String itemIds, String curUserId, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(4);
    Object object = "false";
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(surveyId, "String");
      pg.put(itemIds, "String");
      pg.put(curUserId, "String");
      pg.put(domainId, "String");
      object = ejbProxy.invoke("voteAdd", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return (String)object;
  }
  
  public List voteList(String surveyId, String itemIds) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("LookIntoEJB", 
          "LookIntoEJBLocal", LookIntoEJBHome.class);
      pg.put(surveyId, "String");
      pg.put(itemIds, "String");
      list = (List)ejbProxy.invoke("voteList", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List getBrowser(String surveyId, String searchName, String read, int volume, int currentPage, String domainId) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      if ("1".equals(read)) {
        query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts,nsvPO.voteDate from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO nsvPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where nsvPO.employeeId=empPO.empId and nsvPO.surveyId ='" + 
            surveyId + "' " + sWhere + 
            " order by orgPO.orgName desc");
      } else {
        NetSurveyPO po = (NetSurveyPO)session.load(NetSurveyPO.class, 
            new Long(surveyId));
        if (po.getSurveyRange() == null || "".equals(po.getSurveyRange())) {
          query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.employeeId={b}.empId  and {a}.surveyId ='" + 
              surveyId + "') " + sWhere + 
              " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
              domainId + 
              " order by orgPO.orgName desc");
        } else {
          String tmp = po.getSurveyRange().substring(1, 
              po.getSurveyRange().length() - 1);
          String[] idsArr = tmp.split("\\*\\*");
          tmp = "";
          for (int i = 0; i < idsArr.length; i++)
            tmp = String.valueOf(tmp) + idsArr[i] + ","; 
          tmp = String.valueOf(tmp) + "-1";
          query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.employeeId={b}.empId  and {a}.surveyId ='" + 
              surveyId + "') " + sWhere + 
              " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
              domainId + 
              " and orgPO.orgId in (" + tmp + 
              ")" + 
              " order by orgPO.orgName desc");
        } 
      } 
      query.setFirstResult((currentPage - 1) * volume);
      query.setMaxResults(volume);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list;
  }
  
  public int getBrowserCount(String surveyId, String searchName, String read, String domainId) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      if ("1".equals(read)) {
        query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO nsvPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where nsvPO.employeeId=empPO.empId and nsvPO.surveyId ='" + 
            surveyId + "' " + sWhere + 
            " order by orgPO.orgName desc");
      } else {
        NetSurveyPO po = (NetSurveyPO)session.load(NetSurveyPO.class, 
            new Long(surveyId));
        if (po.getSurveyRange() == null || "".equals(po.getSurveyRange())) {
          query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.employeeId={b}.empId  and {a}.surveyId ='" + 
              surveyId + "') " + sWhere + 
              " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
              domainId + 
              " order by orgPO.orgName desc");
        } else {
          String tmp = po.getSurveyRange().substring(1, 
              po.getSurveyRange().length() - 1);
          String[] idsArr = tmp.split("\\*\\*");
          tmp = "";
          for (int i = 0; i < idsArr.length; i++)
            tmp = String.valueOf(tmp) + idsArr[i] + ","; 
          tmp = String.valueOf(tmp) + "-1";
          query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.employeeId={b}.empId  and {a}.surveyId ='" + 
              surveyId + "') " + sWhere + 
              " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
              domainId + 
              " and orgPO.orgId in (" + tmp + 
              ")" + 
              " order by orgPO.orgName desc");
        } 
      } 
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list.size();
  }
  
  public List getBrowser(String items, String searchName, int volume, int currentPage) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO nsvPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where nsvPO.employeeId=empPO.empId and nsvPO.item.id ='" + 
          items + "' " + sWhere + 
          " order by orgPO.orgName desc");
      query.setFirstResult((currentPage - 1) * volume);
      query.setMaxResults(volume);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list;
  }
  
  public int getBrowserCount(String items, String searchName) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO nsvPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where nsvPO.employeeId=empPO.empId and nsvPO.item.id ='" + 
          items + "' " + sWhere + 
          " order by orgPO.orgName desc");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list.size();
  }
}
