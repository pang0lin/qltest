package com.js.oa.eform.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class DataBaseInfoEJBBean implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[][] getTableInfo(String domainId) {
    String[][] result = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr2("select table_id,table_desname,table_name from ttable where DOMAIN_ID=" + domainId + " order by table_model,table_date desc", 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    try {
      dbopt.close();
    } catch (Exception exception) {}
    return result;
  }
  
  public String[][] getTableInfoByRange(String domainId, String userId, String orgId) {
    String[][] result = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String where = "DOMAIN_ID=" + domainId;
      ManagerService managerBD = new ManagerService();
      String managerWhere = managerBD.getRightFinalWhere(userId, orgId, "02*02*02", 
          "createdOrg", "createdEmp");
      where = String.valueOf(where) + " and " + managerWhere;
      result = dbopt.executeQueryToStrArr2("select table_id,table_desname,table_name from ttable where " + where + " order by table_model,table_date desc", 3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    try {
      dbopt.close();
    } catch (Exception exception) {}
    return result;
  }
  
  public String[][] getFieldInfo(String tableId) {
    String[][] result = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr2("select field_id,field_desname,field_name,field_show,field_null from tfield where field_table=" + tableId, 5);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    try {
      dbopt.close();
    } catch (Exception exception) {}
    return result;
  }
}
