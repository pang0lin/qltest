package com.active.e_uc.util;

import java.io.File;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

public class ActiveHibernateBase {
  protected static SessionFactory sessionFactory = null;
  
  protected Session session = null;
  
  protected Transaction transaction = null;
  
  public ActiveHibernateBase() {
    if (sessionFactory == null)
      try {
        initHibernate();
      } catch (HibernateException e) {
        e.printStackTrace();
      }  
  }
  
  protected void initHibernate() throws HibernateException {
    String path = System.getProperty("user.dir").toString();
    path = String.valueOf(path) + "/jsconfig/active.cfg.xml";
    File cfgxml = new File(path);
    Configuration conf = (new Configuration()).configure(cfgxml);
    sessionFactory = conf.buildSessionFactory();
  }
  
  protected void beginTransaction() throws HibernateException {
    this.session = sessionFactory.openSession();
    this.transaction = this.session.beginTransaction();
  }
  
  protected void endTransaction(boolean commit) throws HibernateException {
    if (commit) {
      this.transaction.commit();
    } else {
      this.transaction.rollback();
    } 
    this.session.close();
  }
}
