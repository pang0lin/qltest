package com.js.oa.audit.bean;

import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AuditMsRemindEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean auditMsRemind(long messageSendUserId, String messageSendUserOrgId, String messageSendUserName, int messageShow, int messageStatus, Date messageTime, String MessageTitle, String messageType, long DataId, String messageUrl) {
    boolean result = false;
    try {
      List<MessagesVO> msgList = new ArrayList();
      Date now = new Date();
      MessagesBD messageBD = new MessagesBD();
      String sendToUserIds = getSendToUsers(String.valueOf(messageSendUserId), messageSendUserOrgId);
      String[] arr = sendToUserIds.split(",");
      if (arr != null) {
        for (int i = 0; i < arr.length; i++) {
          MessagesVO msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(messageSendUserId);
          msgVO.setMessage_send_UserName(messageSendUserName);
          msgVO.setMessage_show(messageShow);
          msgVO.setMessage_status(messageStatus);
          msgVO.setMessage_time(messageTime);
          msgVO.setMessage_title(MessageTitle);
          msgVO.setMessage_toUserId(Long.parseLong("".equals(arr[i]) ? "-99" : arr[i]));
          msgVO.setMessage_type(messageType);
          msgVO.setData_id(DataId);
          msgVO.setMessage_url(messageUrl);
          msgList.add(msgVO);
        } 
        if (msgList != null)
          messageBD.messageArrayAdd(msgList); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean auditRemind(Long messageSendUserId, String messageSendUserOrgId, String messageSendUserName, int messageShow, int messageStatus, Date messageTime, String MessageTitle, String messageType, long DataId, String messageUrl, String toUser) {
    boolean result = false;
    try {
      List<MessagesVO> msgList = new ArrayList();
      Date now = new Date();
      MessagesBD messageBD = new MessagesBD();
      String sendToUserIds = getSendToUsers(String.valueOf(messageSendUserId), messageSendUserOrgId);
      String[] arr = sendToUserIds.split(",");
      MessagesVO msgVO = new MessagesVO();
      msgVO.setMessage_date_begin(now);
      msgVO.setMessage_date_end(new Date("2050/1/1"));
      msgVO.setMessage_send_UserId(messageSendUserId.longValue());
      msgVO.setMessage_send_UserName(messageSendUserName);
      msgVO.setMessage_show(messageShow);
      msgVO.setMessage_status(messageStatus);
      msgVO.setMessage_time(messageTime);
      msgVO.setMessage_title(MessageTitle);
      msgVO.setMessage_toUserId(Long.parseLong(toUser));
      msgVO.setMessage_type(messageType);
      msgVO.setData_id(DataId);
      msgVO.setMessage_url(messageUrl);
      msgList.add(msgVO);
      if (msgList != null)
        messageBD.messageArrayAdd(msgList); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getSendToUsers(String userId, String orgId) {
    String reStr = "";
    String reSult = "";
    String sql = "";
    try {
      sql = "SELECT t.MANAGER_ID,t.EMP_ID FROM audit_manager t WHERE  t.RIGHTSCOPETYPE=0 AND t.EMP_ID=" + userId;
      reStr = "".equals(reStr) ? getResultByYourSql(sql) : (String.valueOf(reStr) + "," + getResultByYourSql(sql));
      sql = "SELECT t.MANAGER_ID,t.EMP_ID FROM audit_manager t,org_organization_user org  WHERE  t.RIGHTSCOPETYPE=1 AND org.org_id='" + 
        orgId + "' AND t.EMP_ID=org.emp_id";
      reStr = "".equals(reStr) ? getResultByYourSql(sql) : (String.valueOf(reStr) + "," + getResultByYourSql(sql));
      sql = "SELECT t.MANAGER_ID,t.EMP_ID FROM audit_manager t,org_organization_user orgu  WHERE  t.RIGHTSCOPETYPE=2 AND orgu.org_id IN( SELECT org_id FROM org_organization org WHERE org.ORGIDSTRING LIKE '%$" + 
        
        orgId + "$%') AND t.EMP_ID=orgu.emp_id";
      reStr = "".equals(reStr) ? getResultByYourSql(sql) : (String.valueOf(reStr) + "," + getResultByYourSql(sql));
      sql = "SELECT t.MANAGER_ID,t.EMP_ID FROM audit_manager t WHERE  t.RIGHTSCOPETYPE=4 AND INSTR(t.RIGHTSCOPESCOPE,'*" + orgId + "*')>0";
      reStr = "".equals(reStr) ? getResultByYourSql(sql) : (String.valueOf(reStr) + "," + getResultByYourSql(sql));
      sql = "SELECT t.MANAGER_ID,t.EMP_ID  FROM audit_manager t WHERE  t.RIGHTSCOPETYPE=3";
      reStr = "".equals(reStr) ? getResultByYourSql(sql) : (String.valueOf(reStr) + "," + getResultByYourSql(sql));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return reStr;
  }
  
  public String getResultByYourSql(String sql) throws SQLException {
    Connection conn = null;
    ResultSet rs = null;
    String result = "";
    int i = 0;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        if (i == 0) {
          result = rs.getString("EMP_ID");
          i = 1;
          continue;
        } 
        result = String.valueOf(result) + "," + rs.getString("EMP_ID");
      } 
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.close();
    } 
    return result;
  }
}
