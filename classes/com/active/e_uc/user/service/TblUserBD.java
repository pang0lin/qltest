package com.active.e_uc.user.service;

import com.active.e_uc.user.bean.TblUserBean;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.po.TblUserApp;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class TblUserBD {
  public void addTblUser(TblUser dp) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.addTblUser(dp);
  }
  
  public TblUser findTblUser(String userName) throws HibernateException {
    TblUser tblUser = new TblUser();
    TblUserBean tblUserBean = new TblUserBean();
    tblUser = tblUserBean.findTblUser(userName);
    return tblUser;
  }
  
  public void updateTblUser(TblUser dp) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.updateTblUser(dp);
  }
  
  public void delTblUser(TblUser tblUser) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.delTblUser(tblUser);
  }
  
  public void delTblUserApp(TblUserApp tblUserApp) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.delTblUserApp(tblUserApp);
  }
  
  public List findTblUserApp(int tblUserId) throws HibernateException {
    List list = new ArrayList();
    TblUserBean tblUserBean = new TblUserBean();
    list = tblUserBean.findTblUserApp(tblUserId);
    return list;
  }
  
  public void addTblUserApp(TblUser tblUser) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.addTblUserApp(tblUser);
  }
  
  public List<TblUser> selectAllUser() throws HibernateException {
    List<TblUser> list = new ArrayList<TblUser>();
    TblUserBean tblUserBean = new TblUserBean();
    list = tblUserBean.selectAllUser();
    return list;
  }
  
  public void addTblUser1(TblUser dp) throws HibernateException {
    TblUserBean tblUserBean = new TblUserBean();
    tblUserBean.addTblUser1(dp);
  }
  
  public List<String> selectAllUserName() throws HibernateException {
    List<String> list = new ArrayList<String>();
    TblUserBean tblUserBean = new TblUserBean();
    list = tblUserBean.selectAllUserName();
    return list;
  }
}
