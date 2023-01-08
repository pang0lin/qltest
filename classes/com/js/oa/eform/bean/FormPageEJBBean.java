package com.js.oa.eform.bean;

import com.js.oa.eform.po.TPagePO;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.userdb.util.RS;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.InfoUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class FormPageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(TPagePO pagePO) throws HibernateException {
    Long pageId = null;
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.id from com.js.oa.eform.po.TPagePO aaa where aaa.pageName='" + 
          pagePO.getPageName() + "' and aaa.domainId=" + 
          pagePO.getDomainId());
      DbOpt dbopt = null;
      dbopt = new DbOpt();
      Object dirtyPageId = new Object();
      Iterator itor = query.iterate();
      while (itor.hasNext()) {
        dirtyPageId = itor.next();
        dbopt.executeUpdate("delete from TELT where PAGE_ID in (" + dirtyPageId + ")");
        dbopt.executeUpdate("delete from TArea where PAGE_ID in (" + dirtyPageId + ")");
        dbopt.executeUpdate("delete from TPage where PAGE_ID in (" + dirtyPageId + ")");
      } 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        String content = pagePO.getPageContent();
        pagePO.setPageContent("");
        pageId = (Long)this.session.save(pagePO);
        this.session.flush();
        InfoUtil.insert_oracle_clob("tpage", "page_content", "page_id", pageId, content);
      } else {
        pageId = (Long)this.session.save(pagePO);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    this.session.close();
    this.session = null;
    return pageId;
  }
  
  public List search(String pageName, String domainId) throws HibernateException {
    List result = null;
    String sql = "select page_id,page_Name,page_FileName,print_page_id from TPage where domain_Id=" + 
      domainId;
    if (pageName != null && pageName.trim().length() > 0)
      sql = String.valueOf(sql) + " and page_Name like '%" + pageName + "%'"; 
    sql = String.valueOf(sql) + " and page_Type=0 order by page_id desc";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      result = RS.toList(conn.createStatement().executeQuery(sql));
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getSingleForm(String id) throws HibernateException {
    List result = null;
    String sql = "select aaa.id,aaa.pageName,aaa.pageFileName,aaa.pageType,aaa.pageRef,aaa.pageContent,bbb.areaTable,aaa.pagePath,aaa.jsOnload,aaa.jsBeforeCommit,aaa.formClassName,aaa.formClassSaveMethod,aaa.formClassUpdateMethod,aaa.initdata,aaa.initdatatype,aaa.datasourcename,aaa.fetchsql,aaa.interfacename,aaa.interfacemethod,aaa.interfacemethodpara,aaa.mappingfields  from com.js.oa.eform.po.TAreaPO bbb right join  bbb.tpage aaa where aaa.id=" + 




      
      id + " and bbb.areaName='form1'";
    begin();
    try {
      Query query = this.session.createQuery(sql);
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    this.session.close();
    this.session = null;
    return result;
  }
  
  public List getFormDocBaseInfo(HttpServletRequest httpServletRequest) throws HibernateException {
    List result = null;
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") != null) ? httpSession.getAttribute("domainId").toString() : "0";
    ManagerService managerBD = new ManagerService();
    String scopeSQl = managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), httpSession.getAttribute("orgId").toString(), "03*06*01", "po.createOrg", "po.createEmp");
    String govFormType = "0";
    String moudleId = httpServletRequest.getParameter("moduleId");
    if ("2".equals(moudleId)) {
      govFormType = "2";
    } else if ("3".equals(moudleId)) {
      govFormType = "3";
    } 
    String viewSql = " select po.immoFormId,po.displayName ";
    String fromSql = " from com.js.oa.jsflow.po.WorkFlowImmoFormPO po";
    String whereSql = " where   ";
    whereSql = String.valueOf(whereSql) + "  po.moduleId=" + govFormType;
    whereSql = String.valueOf(whereSql) + " order by po.immoFormId desc ";
    begin();
    try {
      Query query = this.session.createQuery(String.valueOf(viewSql) + fromSql + whereSql);
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    this.session.close();
    this.session = null;
    return result;
  }
  
  public List getFeildsList(String id) throws HibernateException {
    List<Object[]> result = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "select AREA_TABLE from TAREA  where PAGE_ID=" + id + " and AREA_NAME='form1'";
      rs = stmt.executeQuery(sql);
      String areaTable = "";
      while (rs.next())
        areaTable = rs.getString(1); 
      if (!"".equals(areaTable)) {
        String sqlString = "Select TABLE_ID from TTABLE  where TABLE_NAME='" + areaTable + "'";
        System.out.println(sqlString);
        rs = stmt.executeQuery(sqlString);
        String tableId = "";
        while (rs.next())
          tableId = rs.getString(1); 
        if (!"".equals(tableId)) {
          String sqlStr = "select aaa.FIELD_DESNAME,aaa.FIELD_NAME from tfield aaa where aaa.FIELD_TABLE=" + tableId;
          rs = stmt.executeQuery(sqlStr);
          while (rs.next()) {
            Object[] objects = new Object[2];
            objects[0] = rs.getString(1);
            objects[1] = rs.getString(2);
            result.add(objects);
          } 
        } 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e3) {
          e1.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return result;
  }
  
  public Boolean update(TPagePO pagePO) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.eform.po.TPagePO aaa where aaa.pageName='" + 
          pagePO.getPageName() + "' and aaa.id<>" + pagePO.getId() + " and aaa.domainId=" + pagePO.getDomainId());
      Iterator itor = query.iterate();
      if (itor.hasNext()) {
        success = Boolean.FALSE;
      } else {
        Long pageId = pagePO.getId();
        TPagePO updatePagePO = (TPagePO)this.session.load(TPagePO.class, pageId);
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0) {
          String content = pagePO.getPageContent();
          updatePagePO.setPageName(pagePO.getPageName());
          updatePagePO.setPageFileName(pagePO.getPageFileName());
          updatePagePO.setPageRef(pagePO.getPageRef());
          updatePagePO.setPageContent("");
          updatePagePO.setPagePath(pagePO.getPagePath());
          updatePagePO.setJsBeforeCommit(pagePO.getJsBeforeCommit());
          updatePagePO.setJsOnload(pagePO.getJsOnload());
          updatePagePO.setFormClassName(pagePO.getFormClassName());
          updatePagePO.setFormClassSaveMethod(pagePO.getFormClassSaveMethod());
          updatePagePO.setFormClassUpdateMethod(pagePO.getFormClassUpdateMethod());
          updatePagePO.setInitdata(pagePO.getInitdata());
          updatePagePO.setInitdatatype(pagePO.getInitdatatype());
          updatePagePO.setDatasourcename(pagePO.getDatasourcename());
          updatePagePO.setFetchsql(pagePO.getFetchsql());
          updatePagePO.setInterfacename(pagePO.getInterfacename());
          updatePagePO.setInterfacemethod(pagePO.getInterfacemethod());
          updatePagePO.setInterfacemethodpara(pagePO.getInterfacemethodpara());
          updatePagePO.setMappingfields(pagePO.getMappingfields());
          this.session.update(updatePagePO);
          this.session.flush();
          InfoUtil.insert_oracle_clob("tpage", "page_content", "page_id", pageId, content);
        } else {
          updatePagePO.setPageName(pagePO.getPageName());
          updatePagePO.setPageFileName(pagePO.getPageFileName());
          updatePagePO.setPageRef(pagePO.getPageRef());
          updatePagePO.setPageContent(pagePO.getPageContent());
          updatePagePO.setPagePath(pagePO.getPagePath());
          updatePagePO.setJsBeforeCommit(pagePO.getJsBeforeCommit());
          updatePagePO.setJsOnload(pagePO.getJsOnload());
          updatePagePO.setFormClassName(pagePO.getFormClassName());
          updatePagePO.setFormClassSaveMethod(pagePO.getFormClassSaveMethod());
          updatePagePO.setFormClassUpdateMethod(pagePO.getFormClassUpdateMethod());
          updatePagePO.setInitdata(pagePO.getInitdata());
          updatePagePO.setInitdatatype(pagePO.getInitdatatype());
          updatePagePO.setDatasourcename(pagePO.getDatasourcename());
          updatePagePO.setFetchsql(pagePO.getFetchsql());
          updatePagePO.setInterfacename(pagePO.getInterfacename());
          updatePagePO.setInterfacemethod(pagePO.getInterfacemethod());
          updatePagePO.setInterfacemethodpara(pagePO.getInterfacemethodpara());
          updatePagePO.setMappingfields(pagePO.getMappingfields());
          this.session.update(updatePagePO);
          this.session.flush();
        } 
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean delete(String pageIds) throws Exception {
    DbOpt dbopt = null;
    Boolean success = new Boolean(false);
    try {
      dbopt = new DbOpt();
      String printIds = getPrintIds(pageIds);
      String mobileIds = getMobileIds(pageIds);
      dbopt.executeUpdate("delete from TELT where PAGE_ID in (" + printIds + ")");
      dbopt.executeUpdate("delete from TArea where PAGE_ID in (" + printIds + ")");
      dbopt.executeUpdate("delete from TPage where PAGE_ID in (" + printIds + ")");
      dbopt.executeUpdate("delete from TELT where PAGE_ID in (" + mobileIds + ")");
      dbopt.executeUpdate("delete from TArea where PAGE_ID in (" + mobileIds + ")");
      dbopt.executeUpdate("delete from TPage where PAGE_ID in (" + mobileIds + ")");
      dbopt.executeUpdate("delete from TELT where PAGE_ID in (" + pageIds + ")");
      dbopt.executeUpdate("delete from TArea where PAGE_ID in (" + pageIds + ")");
      dbopt.executeUpdate("delete from TPage where PAGE_ID in (" + pageIds + ")");
      success = Boolean.TRUE;
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (Exception exception) {}
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {
      this.transaction = null;
    } 
    return success;
  }
  
  public String getPrintIds(String pageIds) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String pids = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      String sql = "select print_page_id from tpage where page_id in (" + pageIds + ")";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        pids = String.valueOf(pids) + "," + rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if (pids.equals("") || pids.equals("null"))
      return "-1"; 
    pids.replaceAll(",null", ",-1");
    pids.replaceAll(",,", ",-1,");
    if (pids.endsWith(","))
      return pids.substring(1, pids.length() - 1); 
    return pids.substring(1);
  }
  
  public String getMobileIds(String pageIds) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String pids = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      String sql = "select mobile_page_id from tpage where page_id in (" + pageIds + ")";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        pids = String.valueOf(pids) + "," + rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if (pids.equals("") || pids.equals("null"))
      return "-1"; 
    pids.replaceAll(",null", ",-1");
    pids.replaceAll(",,", ",-1,");
    if (pids.endsWith(","))
      return pids.substring(1, pids.length() - 1); 
    return pids.substring(1);
  }
  
  public List getFormBaseInfo(String domainId) throws HibernateException {
    List result = null;
    String sql = "select distinct aaa.id,aaa.pageName,bbb.areaTable from com.js.oa.eform.po.TAreaPO bbb right join  bbb.tpage aaa where bbb.areaName='form1' and aaa.pageType=0 and aaa.domainId=" + 
      domainId + " order by aaa.id desc";
    begin();
    try {
      Query query = this.session.createQuery(sql);
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    this.session.close();
    return result;
  }
  
  public List getFormBaseInfoByRange(String domainId, String userId, String orgId) throws HibernateException {
    List result = null;
    String where = " aaa.domainId=" + domainId;
    ManagerService managerBD = new ManagerService();
    String managerWhere = managerBD.getRightFinalWhere(userId, orgId, "02*02*02", 
        "aaa.createdOrg", "aaa.createdEmp");
    where = String.valueOf(where) + " and " + managerWhere;
    String sql = "select distinct aaa.id,aaa.pageName,bbb.areaTable from com.js.oa.eform.po.TAreaPO bbb right join  bbb.tpage aaa where bbb.areaName='form1' and aaa.pageType=0 and " + 
      where + " order by aaa.id desc";
    begin();
    try {
      Query query = this.session.createQuery(sql);
      result = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getSelectedSubField(String pageId) throws Exception {
    StringBuffer result = new StringBuffer(",");
    begin();
    try {
      String sql = "select elt.eltName from com.js.oa.eform.po.TEltPO elt right join elt.tarea area right join area.tpage aaa where aaa.id=" + pageId + " and area.areaName<>'form1'";
      Iterator it = this.session.createQuery(sql).iterate();
      while (it.hasNext())
        result.append(it.next()).append(","); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      ex.printStackTrace();
    } 
    return result.toString();
  }
  
  public void updatePageEmp(String pageId, String empId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      String orgId = "1";
      base.begin();
      String sql = "select org_id from org_organization_user where emp_Id=" + empId;
      rs = base.executeQuery(sql);
      if (rs.next())
        orgId = rs.getString(1); 
      rs.close();
      sql = "SELECT print_page_id FROM tpage WHERE page_id=" + pageId;
      rs = base.executeQuery(sql);
      if (rs.next() && 
        rs.getString(1) != null && !"".equals(rs.getString(1)) && !"null".equals(rs.getString(1)))
        pageId = String.valueOf(pageId) + "," + rs.getString(1); 
      rs.close();
      sql = "update tpage set createdemp='" + empId + "' ,createdorg='" + orgId + "' WHERE page_id in (" + pageId + ")";
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public String getPageEmp(String pageId) throws Exception {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select e.emp_id,e.empname from org_employee e,tpage t where e.emp_id=t.createdemp and t.page_id=" + pageId;
      rs = base.executeQuery(sql);
      if (rs.next())
        empId = String.valueOf(rs.getString(1)) + ":" + rs.getString(2); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empId;
  }
  
  public TPagePO getPageFromPageId(Long pageId) throws Exception {
    begin();
    TPagePO tPagePO = new TPagePO();
    try {
      tPagePO = (TPagePO)this.session.load(TPagePO.class, pageId);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return tPagePO;
  }
}
