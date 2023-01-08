package com.js.oa.oasysremind.bean;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.oasysremind.po.OASysRemindPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class OASysRemindEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveOASysRemind(OASysRemindPO oaSysRemind) throws Exception {
    Long auditOrgGroupId = null;
    begin();
    try {
      auditOrgGroupId = (Long)this.session.save(oaSysRemind);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return auditOrgGroupId;
  }
  
  public boolean updateOASysRemind(OASysRemindPO oaSysRemind) throws Exception {
    begin();
    boolean re = true;
    try {
      this.session.update(oaSysRemind);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean deleteOASysRemind(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from OASysRemindPO po where po.remindId in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public OASysRemindPO loadOASysRemind(long remindId) throws Exception {
    begin();
    OASysRemindPO osSysRemindPO = null;
    try {
      osSysRemindPO = (OASysRemindPO)this.session.get(OASysRemindPO.class, Long.valueOf(remindId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return osSysRemindPO;
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
  
  public String[][] getQueryFiled(String tableId) throws SQLException, Exception {
    DbOpt dbopt = new DbOpt();
    String sql = "select t.table_id,t.table_desname,v.field_name,v.field_desname from ttable t,tfield v  where v.field_table=t.table_id and t.table_id=" + 
      tableId;
    String[][] result = dbopt.executeQueryToStrArr2(sql, 4);
    dbopt.close();
    return result;
  }
  
  public String getProcessRemindUrl(String processId, String tId) {
    StringBuffer url = new StringBuffer("");
    try {
      String sql = "select po.workTitle,po.workCurStep,po.wfSubmitEmployeeId,po.workSubmitPerson,po.wfWorkId,po.workType, po.workActivity,po.workTableId,po.workRecordId,po.workFileType,po.workStatus, po.workSubmitTime,po.workProcessId,po.workStepCount from com.js.oa.jsflow.po.WFWorkPO  po  where po.workProcessId=" + 

        
        processId + " and po.workStatus in(1,100)" + 
        " and po.workRecordId=" + tId;
      MsManageBD msBD = new MsManageBD();
      List<Object[]> ttableList = msBD.getListByYourSQL(sql);
      if (ttableList != null && ttableList.size() == 1) {
        url.append("/jsoa/WorkFlowProcAction.do?flowpara=1&search=");
        for (int i = 0; i < ttableList.size(); i++) {
          Object[] obj = ttableList.get(i);
          url.append("from=dealwith&workTitle=&activityName=").append(obj[1]).append("&submitPersonId=").append(obj[2]).append("&submitPerson=")
            .append(obj[3]).append("&work=").append(obj[4]).append("&workType=1").append(obj[5]).append("&activity=").append(obj[6]).append("&table=")
            .append(obj[7]).append("&record=").append(obj[8]).append("&processName=").append(obj[9]).append("&workStatus=").append(obj[10])
            .append("&submitTime=").append(obj[11]).append("&processId=").append(obj[12]).append("&stepCount=").append(obj[13])
            .append("&isStandForWork=null&standForUserId=null&standForUserName=null&initActivity=null&initActivityName=null")
            .append("&submitPersonTime=").append(obj[11]).append("&tranType=null&tranFromPersonId=null&processDeadlineDate=null");
        } 
      } else {
        url.append("/jsoa/oasysremind/close.jsp?flag=dateOut");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return url.toString();
  }
  
  public String getMenuRemindUrl(String menuId, String tId) {
    StringBuffer url = new StringBuffer("");
    try {
      String sql = "select po.id,po.menuSearchBound from com.js.oa.module.po.ModuleMenuPO  po  where po.id=" + 
        
        menuId;
      MsManageBD msBD = new MsManageBD();
      List<Object[]> ttableList = msBD.getListByYourSQL(sql);
      if (ttableList != null && ttableList.size() == 1) {
        url.append("/jsoa/EFormPageAction.do?action=update&flag=0&formId=");
        for (int i = 0; i < ttableList.size(); i++) {
          Object[] obj = ttableList.get(i);
          url.append(obj[1]);
        } 
        url.append("&recordId=").append(tId).append("&menuId=").append(menuId);
      } else {
        url.append("/jsoa/oasysremind/close.jsp?flag=dateOut");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return url.toString();
  }
  
  public String getFieldDesname(String fieldName, String tableName) {
    DbOpt dbOpt = new DbOpt();
    String desName = "";
    try {
      desName = dbOpt.executeQueryToStr("Select field_desname from tfield where field_name='" + fieldName + "' and field_table=" + tableName);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbOpt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return desName;
  }
}
