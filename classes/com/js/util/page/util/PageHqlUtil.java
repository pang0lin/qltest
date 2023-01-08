package com.js.util.page.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;

public class PageHqlUtil extends PageUtil {
  public PageHqlUtil() {
    setEveryNum(15);
  }
  
  public PageHqlUtil(int everyPageNum) {
    setEveryNum(everyPageNum);
  }
  
  protected void getData(String select, String from, String where, String orderBy, int curPage) {
    this.curPageNum = (curPage <= 0) ? 1 : curPage;
    SessionFactory sf = null;
    Session session = null;
    String listHql = "select " + select + " from " + from + " where " + (where.equals("") ? "1=1" : where) + ("".equals(orderBy) ? "" : (" order by " + orderBy));
    String countHql = "select count(*) from " + from + " where " + (where.equals("") ? "1=1" : where);
    try {
      Context ctx = new InitialContext();
      sf = (SessionFactory)ctx.lookup("hibernate");
      session = sf.openSession();
      Query query = null;
      if (select.toUpperCase().indexOf("DISTINCT") >= 0) {
        this.allItem = session.createQuery(listHql).list().size();
      } else {
        this.allItem = ((Integer)session.iterate(countHql).next()).intValue();
      } 
      query = session.createQuery(listHql);
      query.setFirstResult((this.curPageNum - 1) * this.everyNum);
      query.setMaxResults(this.everyNum);
      setList(query.list());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        session.close();
      } catch (HibernateException ex) {
        ex.printStackTrace();
      } 
    } 
    getPageDate();
  }
}
