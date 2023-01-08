package com.js.oa.message.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class MsAccountBookEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String getMeAccountInfo(String domainId) throws Exception {
    String result = "";
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      ResultSet rs = null;
      String databaseType = DbOpt.dbtype;
      if ("oracle".equals(databaseType)) {
        databaseType = "nvl";
      } else if ("sqlserver".equals(databaseType)) {
        databaseType = "isnull";
      } 
      rs = stat.executeQuery("select sum(" + databaseType + "(BOOK_COUNT,0)) from MS_ACCOUNTBOOK accountBook where accountBook.DOMAIN_ID=" + 
          domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      rs = stat.executeQuery("select sum(" + databaseType + "(BOOK_MONEY,0)) from MS_ACCOUNTBOOK accountBook where accountBook.DOMAIN_ID=" + domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      rs = stat.executeQuery("select sum(" + databaseType + "(SUNCOUTER,0)) from MS_COUNT count where count.DOMAIN_ID=" + domainId);
      if (rs.next()) {
        result = String.valueOf(result) + rs.getObject(1) + ";";
      } else {
        result = String.valueOf(result) + "0;";
      } 
      result.replaceAll("null", "0");
      rs.close();
      stat.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("Error in MsAccountBookEJB's getMeAccountInfo Exception:" + e.toString());
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
}
