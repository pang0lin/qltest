package com.js.oa.message.bean;

import com.js.oa.message.po.MsHistoryPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class MsHistoryEJBBean extends HibernateBase implements SessionBean {
  private static final long serialVersionUID = 1L;
  
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean saveMsHistory(String fromUserId, String fromOrgId, String sendToPerson, String sendTime, String msContext, String result) throws Exception {
    begin();
    Boolean falg = Boolean.valueOf(false);
    Date date = null;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (sendTime != null && !"".equals(sendTime))
        date = format.parse(sendTime); 
      MsHistoryPO po = new MsHistoryPO();
      po.setFromUserId(fromUserId);
      po.setFromOrgId(fromOrgId);
      po.setSendToPerson(sendToPerson);
      po.setSendTime(date);
      po.setMsContext(msContext);
      po.setResult(result);
      po.setSendLong(Long.valueOf((new Date()).getTime()));
      po.setFlag(Integer.valueOf((result.length() > 4) ? 0 : Integer.valueOf(result).intValue()));
      this.session.save(po);
      this.session.flush();
      falg = Boolean.valueOf(true);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return falg;
  }
  
  public String genExtendCode() throws Exception {
    Connection conn = (new DataSourceBase()).getDataSource()
      .getConnection();
    Statement stat = null;
    ResultSet rs = null;
    String extendCode = null;
    try {
      stat = conn.createStatement();
      String Sql = "select max(extendCode) from ms_history A where length(A.extendCode)>=14";
      rs = stat.executeQuery(Sql);
      if (rs.next())
        extendCode = rs.getString(1); 
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      String dateStr = sdf.format(new Date());
      if (extendCode != null && extendCode.length() > 8) {
        String tmpExtendCode = extendCode.substring(8);
        int intExtendCode = Integer.valueOf(tmpExtendCode).intValue();
        extendCode = String.valueOf(dateStr) + ++intExtendCode;
        if (extendCode.length() > 14)
          extendCode = String.valueOf(dateStr) + "100001"; 
      } else {
        extendCode = String.valueOf(dateStr) + "100001";
      } 
    } catch (Exception e) {
      System.out.println("MsHistoryEJBBean in genExtendCode Exception:" + 
          e.toString());
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stat != null)
        stat.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return extendCode;
  }
  
  public Boolean saveMsHistory(String fromUserId, String fromOrgId, String sendToPerson, String sendTime, String msContext, String result, String receiveCode, String extendCode) throws Exception {
    begin();
    Boolean falg = Boolean.valueOf(false);
    Date date = null;
    try {
      SimpleDateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
      if (sendTime != null && !"".equals(sendTime))
        date = format.parse(sendTime); 
      MsHistoryPO po = new MsHistoryPO();
      po.setFromUserId(fromUserId);
      po.setFromOrgId(fromOrgId);
      po.setSendToPerson(sendToPerson);
      po.setSendTime(date);
      po.setMsContext(msContext);
      po.setResult(result);
      po.setSendLong(Long.valueOf((new Date()).getTime()));
      po.setReceiveCode(receiveCode);
      if (result.equals("0")) {
        po.setFlag(Integer.valueOf(4000));
      } else if (result.equals("1")) {
        po.setFlag(Integer.valueOf(200));
      } else {
        po.setFlag(Integer.valueOf((result.length() > 4) ? 0 : Integer.valueOf(result).intValue()));
      } 
      po.setExtendCode(extendCode);
      this.session.save(po);
      this.session.flush();
      falg = Boolean.valueOf(true);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return falg;
  }
}
