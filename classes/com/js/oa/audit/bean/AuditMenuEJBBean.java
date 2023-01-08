package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditMenuPO;
import com.js.oa.message.service.MsManageBD;
import com.js.system.menu.po.MenuSetPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuditMenuEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditMenu(AuditMenuPO auditMenuPO) throws Exception {
    Long result = new Long(0L);
    begin();
    try {
      result = (Long)this.session.save(auditMenuPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public AuditMenuPO loadAuditMenu(long auditMenuId) throws Exception {
    begin();
    AuditMenuPO auditMenuPO = null;
    try {
      auditMenuPO = (AuditMenuPO)this.session.get(AuditMenuPO.class, Long.valueOf(auditMenuId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return auditMenuPO;
  }
  
  public void auditMenu(String auditMenuId, String logId, String auditSattus, String operationType, HttpServletRequest request) throws Exception {
    HttpSession httpsession = request.getSession(true);
    try {
      begin();
      AuditMenuPO auditMenuPO = loadAuditMenu(Long.valueOf(auditMenuId).longValue());
      if ("1".equals(auditSattus)) {
        MenuSetPO po = (MenuSetPO)FillBean.transformOTO(auditMenuPO, MenuSetPO.class);
        begin();
        if ("insert".equals(operationType))
          this.session.flush(); 
        if ("update".equals(operationType)) {
          this.session.update(po);
          updateMenuExtByUpadte(po);
          this.session.flush();
        } 
        if ("delete".equals(operationType)) {
          this.session.delete(po);
          this.session.flush();
        } 
        if ("enable".equals(operationType) || "disable".equals(operationType)) {
          this.session.update(po);
          updateMenuExtByAble(po);
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
      if ("enable".equals(operationType))
        operationType = "启用"; 
      if ("disable".equals(operationType))
        operationType = "禁用"; 
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
        typeString = "通过审核";
      } else {
        typeString = "未通过审核";
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditRemind(Long.valueOf(httpsession.getAttribute("userId").toString()), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + operationType + "的菜单管理“" + auditMenuPO.getMenuName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", submitEmpid);
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
  }
  
  public void updateMenuExtByUpadte(MenuSetPO po) throws SQLException {
    Connection conn = null;
    PreparedStatement pStmt = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = null;
      String temp = "1";
      if (po != null) {
        temp = String.valueOf(po.getIsSystemInit());
        if ("0".equals(temp)) {
          temp = "-1";
          rs = stmt.executeQuery("select id from menu_ext where menu_blone='" + po.getMenuId() + "' and menu_location='" + po.getMenuId() + "'");
          if (rs.next())
            temp = rs.getString(1); 
          rs.close();
          stmt.executeUpdate("update menu_ext set menu_scope='" + po.getMenuView() + "',menu_viewuser='" + po.getMenuViewUser() + 
              "',menu_vieworg='" + po.getMenuViewOrg() + "',menu_viewgroup='" + po.getMenuViewOrg() + "',menu_isvalide=" + po.getInUse() + 
              " where id=" + temp);
          stmt.close();
          conn.close();
        } 
      } 
    } catch (SQLException e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
    } 
  }
  
  public void updateMenuExtByAble(MenuSetPO po) throws SQLException {
    Connection conn = null;
    PreparedStatement pStmt = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = null;
      if (po != null) {
        String temp = "-1";
        rs = stmt.executeQuery("select id from menu_ext where menu_blone='" + po.getMenuId() + "' and menu_location='" + po.getMenuId() + "'");
        if (rs.next())
          temp = rs.getString(1); 
        rs.close();
        if (!"-1".equals(temp))
          stmt.executeUpdate("update menu_ext set menu_isvalide=" + po.getInUse() + " where id=" + temp); 
        stmt.close();
        conn.close();
      } 
    } catch (SQLException e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
    } 
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
