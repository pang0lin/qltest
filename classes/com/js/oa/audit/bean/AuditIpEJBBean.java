package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditIpPO;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.security.ip.po.IPPO;
import com.js.oa.security.ip.service.IPBD;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuditIpEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditIp(AuditIpPO auditIpPO) throws Exception {
    Long auditOrgGroupId = null;
    begin();
    try {
      auditOrgGroupId = (Long)this.session.save(auditIpPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return auditOrgGroupId;
  }
  
  public AuditIpPO loadAuditIp(long auditIpId) throws Exception {
    begin();
    AuditIpPO auditIpPO = null;
    try {
      auditIpPO = (AuditIpPO)this.session.get(AuditIpPO.class, Long.valueOf(auditIpId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return auditIpPO;
  }
  
  public void auditIp(String auditIpId, String logId, String auditSattus, String operationType, HttpServletRequest request) throws Exception {
    try {
      begin();
      AuditIpPO auditIpPO = loadAuditIp(Long.valueOf(auditIpId).longValue());
      if ("1".equals(auditSattus)) {
        IPPO iPPO = (IPPO)FillBean.transformOTO(auditIpPO, IPPO.class);
        begin();
        if ("insert".equals(operationType)) {
          this.session.save(iPPO);
          this.session.flush();
        } 
        if ("update".equals(operationType)) {
          IPPO oldPO = (IPPO)this.session.load(IPPO.class, new Long(auditIpPO.getIpId()));
          oldPO.setIpAddressBegin(auditIpPO.getIpAddressBegin());
          oldPO.setIpAddressEnd(auditIpPO.getIpAddressEnd());
          oldPO.setIpApplyTime(auditIpPO.getIpApplyTime());
          oldPO.setIpIsOpen(auditIpPO.getIpIsOpen());
          oldPO.setIpOpenBeginTime(auditIpPO.getIpOpenBeginTime());
          oldPO.setIpOpenEndTime(auditIpPO.getIpOpenEndTime());
          oldPO.setIpProposer(auditIpPO.getIpProposer());
          this.session.update(oldPO);
          this.session.flush();
        } 
        if ("delete".equals(operationType)) {
          iPPO.setId(auditIpPO.getIpId());
          this.session.delete(iPPO);
          this.session.flush();
        } 
        if ("pass".equals(operationType)) {
          IPBD ipBD = new IPBD();
          String ipId = String.valueOf(auditIpPO.getIpId());
          ipBD.pass(String.valueOf(ipId) + ",");
        } 
      } 
      HttpSession httpsession = request.getSession(true);
      String databaseType = SystemCommon.getDatabaseType();
      String dateString = "now()";
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update audit_log set ISCHECKED=1,AUDIT_MODULE=4,AUDIT_STATUS=" + auditSattus + ",CHECK_EMPID=" + httpsession.getAttribute("userId") + ",CHECK_EMPNAME='" + 
        httpsession.getAttribute("userName") + "',CHECK_TIME= " + dateString + "  where LOG_ID=" + logId;
      updateYourSql(sql);
      if ("insert".equals(operationType))
        operationType = "新增"; 
      if ("update".equals(operationType))
        operationType = "修改"; 
      if ("delete".equals(operationType))
        operationType = "删除"; 
      if ("pass".equals(operationType))
        operationType = "开通"; 
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
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditRemind(Long.valueOf(httpsession.getAttribute("userId").toString()), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + operationType + "的IP管理“" + auditIpPO.getIpAddressBegin() + "-" + auditIpPO.getIpAddressEnd() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", submitEmpid);
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
