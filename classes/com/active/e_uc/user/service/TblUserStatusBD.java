package com.active.e_uc.user.service;

import com.active.e_uc.user.bean.TblUserStatusBean;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class TblUserStatusBD {
  public String findstatus(int userid) throws HibernateException {
    String status = "true";
    try {
      TblUserStatusBean tblUserStatusBean = new TblUserStatusBean();
      status = tblUserStatusBean.findstatus(userid);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return status;
  }
  
  public List findUserOnline() throws HibernateException {
    List list = new ArrayList();
    try {
      TblUserStatusBean tblUserStatusBean = new TblUserStatusBean();
      list = tblUserStatusBean.findUserOnline();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return list;
  }
}
