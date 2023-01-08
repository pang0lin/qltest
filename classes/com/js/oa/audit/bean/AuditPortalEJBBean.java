package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditPortalPO;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.portal.po.CustomDesktopLayoutPO;
import com.js.oa.portal.util.GenerateLayout;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuditPortalEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditPortal(AuditPortalPO auditPortalPO) throws Exception {
    Long result = new Long(0L);
    begin();
    try {
      result = (Long)this.session.save(auditPortalPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public AuditPortalPO loadAuditPortal(long auditPortalId) throws Exception {
    begin();
    AuditPortalPO auditPortalPO = null;
    try {
      auditPortalPO = (AuditPortalPO)this.session.get(AuditPortalPO.class, Long.valueOf(auditPortalId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return auditPortalPO;
  }
  
  public void auditPortal(String auditPortalId, String logId, String auditSattus, String operationType, HttpServletRequest request) throws Exception {
    try {
      HttpSession httpsession = request.getSession(true);
      begin();
      AuditPortalPO auditPortalPO = loadAuditPortal(Long.valueOf(auditPortalId).longValue());
      if ("1".equals(auditSattus)) {
        CustomDesktopLayoutPO po = (CustomDesktopLayoutPO)FillBean.transformOTO(auditPortalPO, CustomDesktopLayoutPO.class);
        begin();
        if ("insert".equals(operationType)) {
          String urlPath = "portal2/";
          ServletContext sct = httpsession.getServletContext();
          String path = String.valueOf(sct.getRealPath("/")) + urlPath + "layout";
          if (SystemCommon.getUseClusterServer() == 1)
            path = String.valueOf(sct.getRealPath("/")) + "upload/layout"; 
          begin();
          String domainId = (httpsession.getAttribute("domainId") == null) ? "0" : httpsession.getAttribute("domainId").toString();
          int re = Integer.parseInt(this.session.createQuery("select count(*) from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + 
                domainId + " and po.createdOrg=" + po.getCreatedOrg() + " and po.layoutName='" + 
                po.getLayoutName() + "'").iterate().next()
              .toString());
          if (re > 0)
            return; 
          SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
          String date = formatter.format(new Date());
          po.setXmlFile((String.valueOf(path) + "\\layout" + date + ".xml")
              .replace('\\', '/'));
          Long layoutId = (Long)this.session.save(po);
          GenerateLayout.generLayoutFile(path, "layout" + date + ".xml", 
              String.valueOf(layoutId));
          this.session.flush();
        } 
        if ("update".equals(operationType)) {
          po.setId(auditPortalPO.getPortalId());
          this.session.update(po);
          this.session.flush();
        } 
        if ("delete".equals(operationType)) {
          System.out.println("auditPortalPO.getPorttalId()=" + auditPortalPO.getPorttalId());
          po.setId(auditPortalPO.getPortalId());
          this.session.delete(po);
          this.session.flush();
        } 
      } 
      String databaseType = SystemCommon.getDatabaseType();
      String dateString = "now()";
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update audit_log set ISCHECKED=1,AUDIT_MODULE=5,AUDIT_STATUS=" + auditSattus + ",CHECK_EMPID=" + httpsession.getAttribute("userId") + ",CHECK_EMPNAME='" + 
        httpsession.getAttribute("userName") + "',CHECK_TIME= " + dateString + "  where LOG_ID=" + logId;
      updateYourSql(sql);
      if ("insert".equals(operationType))
        operationType = "新增"; 
      if ("update".equals(operationType))
        operationType = "修改"; 
      if ("delete".equals(operationType))
        operationType = "删除"; 
      String sqlq = "select po.logId,po.submitEmpid from com.js.oa.audit.po.AuditLog po where po.logId=" + logId;
      MsManageBD msBD = new MsManageBD();
      List<Object[]> msList = msBD.getListByYourSQL(sqlq);
      String submitEmpid = null;
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          submitEmpid = obj[1].toString();
        }  
      String typeString = "";
      if ("1".equals(auditSattus)) {
        typeString = "审核通过";
      } else {
        typeString = "审核未通过";
      } 
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null) {
        this.session.close();
        this.session = null;
      } 
      this.transaction = null;
    } 
    if (this.session != null) {
      this.session.close();
      this.session = null;
    } 
    this.transaction = null;
  }
  
  public boolean updateYourSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      stat.execute(sql);
      stat.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
}
