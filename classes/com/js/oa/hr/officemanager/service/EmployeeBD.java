package com.js.oa.hr.officemanager.service;

import com.js.oa.hr.officemanager.bean.EmployeeEJBBean;
import com.js.oa.hr.officemanager.bean.EmployeeEJBHome;
import com.js.system.manager.service.ManagerService;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class EmployeeBD {
  private static Logger logger = Logger.getLogger(EmployeeBD.class.getName());
  
  private static Map positionMap = null;
  
  public Map getEmpPositionMap() {
    if (positionMap == null)
      initPositionMap(); 
    return positionMap;
  }
  
  private void initPositionMap() {
    if (positionMap != null) {
      positionMap.clear();
    } else {
      positionMap = new HashMap<Object, Object>();
    } 
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select id,station_name from st_station order by id desc");
      while (rs.next())
        positionMap.put(rs.getString(1), rs.getString(2)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
  }
  
  public void resetPositionMap() {
    if (positionMap != null)
      positionMap.clear(); 
    initPositionMap();
  }
  
  public int add(EmployeeVO employeeVO, String orgId) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(orgId, "String");
      addResult = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.info("officemanager employeebd:" + e);
    } finally {}
    return addResult;
  }
  
  public int update(EmployeeVO employeeVO, String orgId, Long empId) {
    int result = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(orgId, "String");
      pg.put(empId, "Long");
      result = ((Integer)ejbProxy.invoke("update", pg.getParameters()))
        .intValue();
    } catch (Exception e) {
      logger.info("officemanager empoyeebd:" + e);
    } finally {}
    return result;
  }
  
  public String getEmpIdByAccounts(String account) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(account, "String");
      result = (String)ejbProxy.invoke("getEmpIdByAccounts", pg.getParameters());
    } catch (Exception e) {
      logger.info("getEmpIdByAccounts employeebd:" + e);
    } finally {}
    return result;
  }
  
  public String getEmp_name(String empID) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empID, "String");
      result = (String)ejbProxy.invoke("getEmp_name", pg.getParameters());
    } catch (Exception e) {
      logger.info("getEmp_name employeebd:" + e);
    } finally {}
    return result;
  }
  
  public boolean del(String[] id) {
    boolean delResult = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to del Employee:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public boolean del(String[] id, String[] log) {
    boolean delResult = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String[].class);
      pg.put(log, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to del Employee:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public boolean rehis(String id) {
    boolean delResult = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      delResult = ((Boolean)ejbProxy.invoke("rehis", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to rehis Employee:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public boolean rehis(String id, String[] log) {
    boolean delResult = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      pg.put(log, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("rehis", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to rehis Employee:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public boolean forbid(String id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      result = ((Boolean)ejbProxy.invoke("forbid", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to forbid  Employee:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean forbid(String id, String[] log) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      pg.put(log, String[].class);
      result = ((Boolean)ejbProxy.invoke("forbid", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to forbid  Employee:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean regain(String id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      result = ((Boolean)ejbProxy.invoke("regain", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to regain  Employee:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean regain(String id, String[] log) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      pg.put(id, String.class);
      pg.put(log, String[].class);
      result = ((Boolean)ejbProxy.invoke("regain", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("Error to regain  Employee:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List selectSingle(Long empId) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, "Long");
      list = (List)ejbProxy.invoke("selectSingle", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List listPostTitle() {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      list = (List)ejbProxy.invoke("listPostTitle", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select listPostTitle information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List listDuty(String domainId) {
    return listDuty(domainId, "");
  }
  
  public List listDuty(String domainId, String corpId) {
    List list = new ArrayList();
    try {
      list = (new EmployeeEJBBean()).listDuty(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List listStation(String domainId) {
    return listStation(domainId, "");
  }
  
  public List listStation(String domainId, String corpId) {
    List list = new ArrayList();
    try {
      list = (new EmployeeEJBBean()).listStation(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public List listCountry() {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      list = (List)ejbProxy.invoke("listCountry", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select listCountry information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List city(String country) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(country, "String");
      list = (List)ejbProxy.invoke("city", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select city information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List county(String country, String city) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(country, "String");
      pg.put(city, "String");
      list = (List)ejbProxy.invoke("county", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select county information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List postTitle(String postTitleSeries) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitleSeries, "String");
      list = (List)ejbProxy.invoke("postTitle", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public String batchResume(String[] empId) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, String[].class);
      result = (String)ejbProxy.invoke("batchResume", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + e.getMessage());
    } 
    return result;
  }
  
  public String batchResume(String[] empId, String[] log) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(empId, String[].class);
      pg.put(log, String[].class);
      result = (String)ejbProxy.invoke("batchResume", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error to select postTitle information:" + e.getMessage());
    } 
    return result;
  }
  
  public List export(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + e.getMessage());
    } 
    return alist;
  }
  
  public String judgeAccountById(String userId) {
    String result = "0";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      result = (String)ejbProxy.invoke("judgeAccountById", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to judgeAccountById information:" + e.getMessage());
    } 
    return result;
  }
  
  public String judgeAccount(String account) {
    String result = "0";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(account, String.class);
      result = (String)ejbProxy.invoke("judgeAccount", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to judgeAccount information:" + e.getMessage());
    } 
    return result;
  }
  
  public String setUserAccountAndPass(String userId, String account, String password) {
    String result = "0";
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(account, String.class);
      pg.put(password, String.class);
      result = (String)ejbProxy.invoke("setUserAccountAndPass", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to setUserAccountAndPass information:" + e.getMessage());
    } 
    return result;
  }
  
  public Integer containUsersCount(String orgId) {
    Integer ret = new Integer(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      ret = (Integer)ejbProxy.invoke("containUsersCount", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to containUsersCount information:" + e.getMessage());
    } 
    return ret;
  }
  
  public List getContractFellinList(String curUserId, String curOrgId, String domainId) {
    String where = "";
    int pageSize = 99999;
    int offset = 0;
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*01", "orgpo.id", "");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    String databaseType = 
      SystemCommon.getDatabaseType();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String searchWorkPackStartDate = format.format(new Date());
    String searchWorkPackEndDate = getNextDay(searchWorkPackStartDate, "45");
    if (databaseType.indexOf("mysql") >= 0) {
      where = String.valueOf(where) + " and po.workPackEndDate>='" + 
        searchWorkPackStartDate + "' and " + 
        "po.workPackEndDate<='" + searchWorkPackEndDate + 
        "'";
    } else {
      where = String.valueOf(where) + 
        " and po.workPackEndDate>=JSDB.FN_STRTODATE('" + 
        searchWorkPackStartDate + "','S') and " + 
        "po.workPackEndDate<=JSDB.FN_STRTODATE('" + 
        searchWorkPackEndDate + "','S')";
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 " + where + 
          " and po.domainId=" + domainId + 
          " order by po.workPackEndDate";
      } else {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 and " + where + 
          " and po.domainId=" + domainId + 
          " order by po.workPackEndDate";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + domainId + 
        " order po.workPackEndDate";
    } 
    Page page = new Page(
        "po.empId,po.empName,orgpo.orgNameString,po.workPackEndDate", 
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
  
  public List getBirthDayList(String curUserId, String curOrgId, String domainId) {
    String where = "";
    int pageSize = 99999;
    int offset = 0;
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*01", "orgpo.id", "");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    String databaseType = 
      SystemCommon.getDatabaseType();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String searchWorkPackStartDate = format.format(new Date());
    String searchWorkPackEndDate = getNextDay(searchWorkPackStartDate, "7");
    searchWorkPackStartDate = searchWorkPackStartDate.substring(5, 10);
    searchWorkPackEndDate = searchWorkPackEndDate.substring(5, 10);
    if (databaseType.indexOf("oracle") >= 0) {
      where = String.valueOf(where) + " and to_char(po.empBirth,'mm-dd')>='" + 
        searchWorkPackStartDate + "' and " + 
        "to_char(po.empBirth,'mm-dd')<='" + searchWorkPackEndDate + 
        "'";
    } else {
      where = String.valueOf(where) + 
        " and substring(convert(varchar,po.empBirth,110),1,5)>='" + 
        searchWorkPackStartDate + "' and " + 
        "substring(convert(varchar,po.empBirth,110),1,5)<='" + 
        searchWorkPackEndDate + "'";
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 " + where + 
          " and po.domainId=" + domainId + 
          " order by po.empBirth";
      } else {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 and " + where + 
          " and po.domainId=" + domainId + 
          " order by po.empBirth";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + domainId + 
        " order po.empBirth";
    } 
    Page page = new Page(
        "po.empId,po.empName,orgpo.orgNameString,po.empBirth", 
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
  
  private static String getNextDay(String nowdate, String delay) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String mdate = "";
      Date d = strToDate(nowdate);
      long myTime = d.getTime() / 1000L + (
        Integer.parseInt(delay) * 24 * 60 * 60);
      d.setTime(myTime * 1000L);
      mdate = format.format(d);
      return mdate;
    } catch (Exception e) {
      return "";
    } 
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
  
  public List export_contract(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export_contract", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List export_edusty(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export_edusty", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List export_work(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export_work", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List export_trainhistory(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export_trainhistory", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List export_competence(String where) {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeEJB", 
          "EmployeeEJBLocal", EmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      alist = (List)ejbProxy.invoke("export_competence", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select postTitle information:" + 
          e.getMessage());
    } 
    return alist;
  }
}
