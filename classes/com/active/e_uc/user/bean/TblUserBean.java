package com.active.e_uc.user.bean;

import com.active.e_uc.user.po.TblRoomUser;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.po.TblUserApp;
import com.active.e_uc.util.ActiveHibernateBase;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class TblUserBean extends ActiveHibernateBase {
  public void addTblUser(TblUser dp) throws HibernateException {
    TblUserApp tblUserApp = new TblUserApp();
    tblUserApp.setTblUser(dp);
    tblUserApp.setApp(3);
    tblUserApp.setFunc(799L);
    TblUserApp tblUserApp1 = new TblUserApp();
    tblUserApp1.setTblUser(dp);
    tblUserApp1.setApp(2);
    tblUserApp1.setFunc(0L);
    TblRoomUser tblRoomUser = new TblRoomUser();
    tblRoomUser.setTblUser(dp);
    tblRoomUser.setCid(1188);
    tblRoomUser.setUtp((byte)2);
    tblRoomUser.setGrade(0);
    tblRoomUser.setConsumeType(2);
    try {
      beginTransaction();
      this.session.save(dp);
      this.session.save(tblUserApp);
      this.session.save(tblUserApp1);
      this.session.save(tblRoomUser);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public TblUser findTblUser(String userName) throws HibernateException {
    TblUser tblUser = null;
    try {
      beginTransaction();
      List<TblUser> list = this.session
        .createQuery("select user from com.active.e_uc.user.po.TblUser user where user.userName like '" + 
          userName + "'").list();
      if (!list.isEmpty())
        tblUser = list.get(0); 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return tblUser;
  }
  
  public void updateTblUser(TblUser dp) throws HibernateException {
    try {
      beginTransaction();
      this.session.update(dp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public void delTblUser(TblUser tblUser) throws HibernateException {
    try {
      beginTransaction();
      this.session.delete(tblUser);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public List findTblUserApp(int tblUserId) throws HibernateException {
    List list = new ArrayList();
    try {
      beginTransaction();
      Query query = this.session
        .createQuery("select userApp from com.active.e_uc.user.po.TblUserApp userApp where userApp.tblUser.id like " + 
          tblUserId);
      list = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return list;
  }
  
  public void delTblUserApp(TblUserApp tblUserApp) throws HibernateException {
    try {
      beginTransaction();
      this.session.delete(tblUserApp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public void addTblUserApp(TblUser tblUser) throws HibernateException {
    TblUserApp tblUserApp = new TblUserApp();
    try {
      beginTransaction();
      tblUserApp.setTblUser(tblUser);
      tblUserApp.setApp(3);
      tblUserApp.setFunc(799L);
      this.session.save(tblUserApp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public List<TblUser> selectAllUser() throws HibernateException {
    List<TblUser> list = new ArrayList<TblUser>();
    try {
      beginTransaction();
      list = this.session.createQuery("select tblUser from com.active.e_uc.user.po.TblUser tblUser where tblUser.userName <> 'admin' order by id").list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return list;
  }
  
  public void addTblUser1(TblUser dp) throws HibernateException {
    TblUserApp tblUserApp1 = new TblUserApp();
    tblUserApp1.setTblUser(dp);
    tblUserApp1.setApp(2);
    tblUserApp1.setFunc(0L);
    TblRoomUser tblRoomUser = new TblRoomUser();
    tblRoomUser.setTblUser(dp);
    tblRoomUser.setCid(1188);
    tblRoomUser.setUtp((byte)2);
    tblRoomUser.setGrade(0);
    tblRoomUser.setConsumeType(2);
    try {
      beginTransaction();
      this.session.save(dp);
      this.session.save(tblUserApp1);
      this.session.save(tblRoomUser);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public List<String> selectAllUserName() throws HibernateException {
    List<String> list = new ArrayList<String>();
    try {
      beginTransaction();
      list = this.session.createQuery("select tblUser.userName from com.active.e_uc.user.po.TblUser tblUser where tblUser.userName <> 'admin' order by id").list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
    return list;
  }
}
