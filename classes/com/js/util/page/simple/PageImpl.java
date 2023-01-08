package com.js.util.page.simple;

import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;

public class PageImpl {
  private int recordCount = 0;
  
  public List getResultList(String para, String PO, String where, int pageSize, int currentPage) {
    SessionFactory sf = null;
    Session session = null;
    Transaction transaction = null;
    List list = null;
    int beginRecord = pageSize * (currentPage - 1);
    StringBuffer queryBuffer = new StringBuffer();
    StringBuffer queryCount = new StringBuffer();
    queryCount.append("SELECT COUNT(*) FROM ");
    queryCount.append(PO);
    queryCount.append(" ");
    int p = where.toUpperCase().indexOf("ORDER BY");
    if (p >= 0) {
      queryCount.append(where.substring(0, p));
    } else {
      queryCount.append(where);
    } 
    queryBuffer.append("SELECT ");
    queryBuffer.append(para);
    queryBuffer.append(" FROM ");
    queryBuffer.append(PO);
    queryBuffer.append(" ");
    queryBuffer.append(where);
    try {
      Context ctx = new InitialContext();
      sf = (SessionFactory)ctx.lookup("hibernate");
      session = sf.openSession();
      Query query = null;
      if (para.toUpperCase().indexOf("DISTINCT") >= 0) {
        this.recordCount = session.createQuery(queryBuffer.toString()).list().size();
      } else {
        this.recordCount = ((Integer)session.iterate(queryCount.toString()).next()).intValue();
      } 
      setRecordCount(this.recordCount);
      query = session.createQuery(queryBuffer.toString());
      query.setFirstResult(beginRecord);
      query.setMaxResults(pageSize);
      list = query.list();
    } catch (Exception e) {
      list = null;
      e.printStackTrace();
    } finally {}
    try {
      session.close();
    } catch (HibernateException ex) {
      System.out.println(ex.getMessage());
    } 
    return list;
  }
  
  public Iterator getResultPO(String para, String PO, String where, int pageSize, int currentPage) {
    return null;
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }
}
