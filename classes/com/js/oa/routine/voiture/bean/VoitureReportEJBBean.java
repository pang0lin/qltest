package com.js.oa.routine.voiture.bean;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class VoitureReportEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List listReportForms(String viewSQL, String fromSQL, String whereSQL) throws Exception {
    begin();
    Connection conn = this.dataSource.getConnection();
    Statement stmt = conn.createStatement();
    List<String[]> list = new ArrayList();
    try {
      ResultSet rs = stmt.executeQuery(String.valueOf(viewSQL) + fromSQL + whereSQL);
      while (rs.next()) {
        String[] totalSUM = new String[8];
        for (int i = 0; i < 8; i++)
          totalSUM[i] = rs.getString(i + 1); 
        list.add(totalSUM);
      } 
    } catch (Exception e) {
      System.out.println("---------------------------");
      e.printStackTrace();
      System.out.println("---------------------------");
    } finally {
      stmt.close();
      conn.close();
    } 
    return list;
  }
}
