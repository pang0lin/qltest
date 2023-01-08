package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditOrgGroup;
import com.js.oa.message.service.MsManageBD;
import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuditOrgGroupEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditOrgGroup(AuditOrgGroup auditOrgGroup) throws Exception {
    Long result = new Long(0L);
    begin();
    try {
      result = (Long)this.session.save(auditOrgGroup);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public AuditOrgGroup loadAuditOrgGroup(long auditOrgGroupId) throws Exception {
    begin();
    AuditOrgGroup auditOrgGroup = null;
    try {
      auditOrgGroup = (AuditOrgGroup)this.session.get(AuditOrgGroup.class, Long.valueOf(auditOrgGroupId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return auditOrgGroup;
  }
  
  public void auditOrgGroup(String auditOrgGroupId, String logId, String auditSattus, String operationType, HttpServletRequest request) throws Exception {
    try {
      begin();
      AuditOrgGroup auditOrgGroup = loadAuditOrgGroup(Long.valueOf(auditOrgGroupId).longValue());
      if ("1".equals(auditSattus)) {
        GroupVO groupVO = new GroupVO();
        groupVO.setGroupName(auditOrgGroup.getGroupName());
        groupVO.setGroupDescription(auditOrgGroup.getGroupdescription());
        groupVO.setCreatedOrg(String.valueOf(auditOrgGroup.getCreatedorg()));
        groupVO.setCreatedEmp(String.valueOf(auditOrgGroup.getCreatedemp()));
        groupVO.setGroupUserNames(auditOrgGroup.getGroupUserNames());
        groupVO.setGroupUserString(auditOrgGroup.getGroupUserString());
        groupVO.setRangeName(auditOrgGroup.getRangename());
        groupVO.setRangeEmp(auditOrgGroup.getRangeemp());
        groupVO.setRangeOrg(auditOrgGroup.getRangeorg());
        groupVO.setRangeGroup(auditOrgGroup.getRangegroup());
        groupVO.setDomainId(String.valueOf(auditOrgGroup.getDomainId()));
        groupVO.setGroupType(String.valueOf(auditOrgGroup.getGroupType()));
        groupVO.setGroupOrder(auditOrgGroup.getGroupOrder());
        begin();
        ConvertIdAndName cIdAndName = new ConvertIdAndName();
        EndowVO endowVO = cIdAndName.splitId(auditOrgGroup.getGroupUserString());
        String strId = endowVO.getEmpIdArray();
        String[] id = strId.split(",");
        Set<Object> userSet = new HashSet();
        for (int i = 0; i < id.length; i++) {
          if (!"".equals(id[i]))
            userSet.add(this.session.load(EmployeeVO.class, new Long(id[i]))); 
        } 
        if ("".equals(groupVO.getGroupOrder()))
          groupVO.setGroupOrder("1000"); 
        groupVO.setEmployees(userSet);
        begin();
        if ("insert".equals(operationType)) {
          this.session.save(groupVO);
          this.session.flush();
        } 
        if ("update".equals(operationType)) {
          groupVO.setGroupId(auditOrgGroup.getGroupId());
          this.session.update(groupVO);
          this.session.flush();
        } 
        if ("delete".equals(operationType)) {
          groupVO.setGroupId(auditOrgGroup.getGroupId());
          this.session.delete(groupVO);
          this.session.flush();
        } 
      } 
      HttpSession httpsession = request.getSession(true);
      String databaseType = SystemCommon.getDatabaseType();
      String dateString = "now()";
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update audit_log set ISCHECKED=1,AUDIT_MODULE=2,AUDIT_STATUS=" + auditSattus + ",CHECK_EMPID=" + httpsession.getAttribute("userId") + ",CHECK_EMPNAME='" + 
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
        typeString = "通过审核";
      } else {
        typeString = "未通过审核";
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditRemind(Long.valueOf(httpsession.getAttribute("userId").toString()), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + operationType + "的群组管理“" + auditOrgGroup.getGroupName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", submitEmpid);
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
