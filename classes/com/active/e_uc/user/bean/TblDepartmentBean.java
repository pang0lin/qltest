package com.active.e_uc.user.bean;

import com.active.e_uc.user.po.TblDepartment;
import com.active.e_uc.util.ActiveHibernateBase;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class TblDepartmentBean extends ActiveHibernateBase {
  public void addDepartment(TblDepartment dp) throws HibernateException {
    try {
      beginTransaction();
      this.session.save(dp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public void delDepartment(String url) throws HibernateException {
    try {
      beginTransaction();
      Query query = this.session
        .createQuery("select org from com.active.e_uc.user.po.TblDepartment org where org.url like '" + 
          url + "'");
      TblDepartment tblDepartment = (TblDepartment)query.uniqueResult();
      int pid = tblDepartment.getId();
      this.session.delete(tblDepartment);
      Query query1 = this.session
        .createQuery("select org from com.active.e_uc.user.po.TblDepartment org where org.pid like '" + 
          pid + "'");
      List<TblDepartment> list = query1.list();
      TblDepartment tblDepartment1 = new TblDepartment();
      for (int i = 0; i < list.size(); i++) {
        tblDepartment1 = list.get(i);
        this.session.delete(tblDepartment1);
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public int findID(String url) throws HibernateException {
    int id = 0;
    try {
      beginTransaction();
      Query query = this.session
        .createQuery("select org from com.active.e_uc.user.po.TblDepartment org where org.url like '" + 
          url + "'");
      TblDepartment tblDepartment = (TblDepartment)query.uniqueResult();
      id = tblDepartment.getId();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return id;
  }
  
  public TblDepartment findTblDepartment(String url) throws HibernateException {
    TblDepartment tblDepartment = null;
    try {
      beginTransaction();
      Query query = this.session
        .createQuery("select org from com.active.e_uc.user.po.TblDepartment org where org.url like '" + 
          url + "'");
      tblDepartment = (TblDepartment)query.uniqueResult();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return tblDepartment;
  }
  
  public void updateDepartment(TblDepartment dp) throws HibernateException {
    try {
      beginTransaction();
      this.session.update(dp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public List<TblDepartment> selectAllDepartment() throws HibernateException {
    List<TblDepartment> list = new ArrayList<TblDepartment>();
    try {
      beginTransaction();
      list = this.session.createQuery("select department from com.active.e_uc.user.po.TblDepartment department order by id").list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return list;
  }
  
  public String findURL(int id) throws HibernateException {
    String url = "";
    try {
      beginTransaction();
      url = ((TblDepartment)this.session.load(TblDepartment.class, Integer.valueOf(id))).getUrl();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return url;
  }
  
  public List<String> selectAllUrl() throws HibernateException {
    List<String> list = new ArrayList<String>();
    try {
      beginTransaction();
      list = this.session.createQuery("select department.url from com.active.e_uc.user.po.TblDepartment department order by id").list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return list;
  }
}
