package com.js.oa.jsflow.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SysConfigReader;
import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WorkFlowEJBBeanForRWS extends HibernateBase implements SessionBean {
  private static final long serialVersionUID = 1L;
  
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
    this.sessionContext = arg0;
  }
  
  public String yuguidang(String tableName, String recordId, String empId) throws Exception {
    DbOpt db = new DbOpt();
    String count = db.executeQueryToStr("select count(*) n from RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordId=" + recordId);
    if ("0".equals(count)) {
      db.executeUpdate("insert into RWS_WORKFLOWSTATUS(id,tablename,recordid,status,yuguidang_empid,yuguidang_time)values(hibernate_sequence.nextval,'" + tableName + "'," + recordId + ",0," + empId + ",sysdate)");
    } else {
      db.executeUpdate("update RWS_WORKFLOWSTATUS set status=0,yuguidang_empid=" + empId + " , yuguidang_time=sysdate where tablename='" + tableName + "' and recordId=" + recordId);
    } 
    db.close();
    return "0";
  }
  
  public String chehuiguidang(String tableName, String recordId) throws Exception {
    DbOpt db = new DbOpt();
    db.executeUpdate("delete from  RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordId=" + recordId);
    db.close();
    return "0";
  }
  
  public String guidang(String tableName, String recordId, String empId, String gdid) throws Exception {
    DbOpt db = new DbOpt();
    db.executeUpdate("update RWS_WORKFLOWSTATUS set status=1,guidang_empid=" + empId + " ,guidang_time=sysdate,guidang_guid='" + gdid + "' where tablename='" + tableName + "' and recordId=" + recordId);
    db.close();
    return "0";
  }
  
  public String quxiaoguidang(String tableName, String recordId, String empId) throws Exception {
    DbOpt db = new DbOpt();
    db.executeUpdate("update RWS_WORKFLOWSTATUS set status=2,quxiaoguidang_empid=" + empId + " ,quxiaoguidang_time=sysdate where tablename='" + tableName + "' and recordId=" + recordId);
    db.close();
    return "0";
  }
  
  public String tongyiguidang(String tableName, String recordId, String empId) throws Exception {
    DbOpt db = new DbOpt();
    db.executeUpdate("update RWS_WORKFLOWSTATUS set status=3,quxiaoguidang_empid=" + empId + " ,quxiaoguidang_time=sysdate where tablename='" + tableName + "' and recordId=" + recordId);
    db.close();
    return "0";
  }
  
  public String guidangbukejian(String tableName, String recordId, String empId) throws Exception {
    DbOpt db = new DbOpt();
    db.executeUpdate("update RWS_WORKFLOWSTATUS set status=4,quxiaoguidang_empid=" + empId + " ,quxiaoguidang_time=sysdate where tablename='" + tableName + "' and recordId=" + recordId);
    db.close();
    return "0";
  }
  
  public static String getStatusForSendDoc(String id) {
    String result = "9";
    DbOpt db = new DbOpt();
    try {
      result = db.executeQueryToStr("select max(status) from RWS_WORKFLOWSTATUS where tablename='DOC_DOCUMENTSENDFILE' and recordid=" + id);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String getStatusForCooperate(String id) {
    String result = "9";
    DbOpt db = new DbOpt();
    try {
      result = db.executeQueryToStr("select max(status) from RWS_WORKFLOWSTATUS where tablename='cooperate' and recordid=" + id);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String getStatusForReceiveDoc(String id) {
    String result = "9";
    DbOpt db = new DbOpt();
    try {
      result = db.executeQueryToStr("select max(status) from RWS_WORKFLOWSTATUS where tablename='DOC_receiveFile' and recordid=" + id);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String guidangDocOperate() {
    String guid = "";
    return guid;
  }
  
  public static boolean canBeYuGuiDang(String tableName, String id) {
    if (!tableName.startsWith("jst_") && 
      !"DOC_DOCUMENTSENDFILE".equals(tableName) && 
      !"DOC_receivefile".equals(tableName) && 
      !"cooperate".equals(tableName))
      return false; 
    DbOpt db = new DbOpt();
    try {
      String processIdList = SysConfigReader.readConfigValue("RwsGDWorkFlowIdList", "ids");
      processIdList = processIdList.substring(1);
      processIdList = processIdList.substring(0, processIdList.length() - 1);
      if (tableName.startsWith("jst_")) {
        String inCount = db.executeQueryToStr("select count(8) n  from jsf_workflowprocess a, tarea b,ttable c  where a.main_formid = b.page_id and b.area_name='form1'     and a.wf_workflowprocess_id in(" + 

            
            processIdList + ")" + 
            "     and b.area_table = c.table_name" + 
            "     and area_table='" + tableName + "'");
        if ("0".equals(inCount)) {
          db.close();
          return false;
        } 
      } 
      String count = db.executeQueryToStr("select count(8) n from RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordid=" + id + " and status <>2");
      db.close();
      if (!"0".equals(count))
        return false; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return true;
  }
  
  public static boolean canBeGuiDang(String tableName, String id) {
    if (!tableName.startsWith("jst_") && 
      !"DOC_DOCUMENTSENDFILE".equals(tableName) && 
      !"DOC_receivefile".equals(tableName) && 
      !"cooperate".equals(tableName))
      return false; 
    DbOpt db = new DbOpt();
    try {
      String processIdList = SysConfigReader.readConfigValue("RwsGDWorkFlowIdList", "ids");
      processIdList = processIdList.substring(1);
      processIdList = processIdList.substring(0, processIdList.length() - 1);
      if (tableName.startsWith("jst_")) {
        String inCount = db.executeQueryToStr("select count(8) n  from jsf_workflowprocess a, tarea b,ttable c  where a.main_formid = b.page_id and b.area_name='form1'     and a.wf_workflowprocess_id in(" + 

            
            processIdList + ")" + 
            "     and b.area_table = c.table_name" + 
            "     and area_table='" + tableName + "'");
        if ("0".equals(inCount)) {
          db.close();
          return false;
        } 
      } 
      String count = db.executeQueryToStr("select count(8) n from RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordid=" + id + " and status =3");
      db.close();
      if ("1".equals(count))
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return false;
  }
  
  public static boolean canBeCheHuiGuiDang(String tableName, String id) {
    if (!tableName.startsWith("jst_") && 
      !"DOC_DOCUMENTSENDFILE".equals(tableName) && 
      !"DOC_receivefile".equals(tableName) && 
      !"cooperate".equals(tableName))
      return false; 
    DbOpt db = new DbOpt();
    try {
      String count = db.executeQueryToStr("select count(8) n from RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordid=" + id + " and status =0");
      db.close();
      if ("1".equals(count))
        return true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return false;
  }
}
