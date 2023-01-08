package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.portal.util.RsXMLReader;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeJxxxEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[][] list(String logonName) throws Exception {
    String[][] result = (String[][])null;
    try {
      String dbName = "jdbc/rsjx";
      String userName = RsXMLReader.getValue("jxdb", "userName", "");
      String viewName = RsXMLReader.getValue("jxdb", "viewName", "v_finalrank");
      if (!"".equals(userName))
        userName = String.valueOf(userName) + "."; 
      String sql = "select year,rank from " + userName + viewName + " where username='" + logonName + "' order by year desc";
      DbOpt opt = new DbOpt(dbName);
      opt.createStatement();
      result = opt.executeQueryToStrArr2(sql);
      opt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
