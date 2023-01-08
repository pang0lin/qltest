package com.active.e_uc.user.service;

import com.active.e_uc.user.bean.TblDepartmentBean;
import com.active.e_uc.user.po.TblDepartment;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class TblDepartmentBD {
  public void addDepartment(TblDepartment dp) throws HibernateException {
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    tblDepartmentBean.addDepartment(dp);
  }
  
  public void delDepartment(String url) {
    try {
      TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
      tblDepartmentBean.delDepartment(url);
    } catch (HibernateException e1) {
      e1.printStackTrace();
    } 
  }
  
  public int findID(String url) {
    int kd = 0;
    try {
      TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
      kd = tblDepartmentBean.findID(url);
    } catch (HibernateException e1) {
      e1.printStackTrace();
    } 
    return kd;
  }
  
  public TblDepartment findTblDepartment(String url) throws HibernateException {
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    TblDepartment tblDepartment = tblDepartmentBean.findTblDepartment(url);
    return tblDepartment;
  }
  
  public void updateDepartment(TblDepartment dp) throws HibernateException {
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    tblDepartmentBean.updateDepartment(dp);
  }
  
  public List<TblDepartment> selectAllDepartment() throws HibernateException {
    List<TblDepartment> list = new ArrayList<TblDepartment>();
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    list = tblDepartmentBean.selectAllDepartment();
    return list;
  }
  
  public String findURL(int id) throws HibernateException {
    String url = "";
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    url = tblDepartmentBean.findURL(id);
    return url;
  }
  
  public List<String> selectAllUrl() throws HibernateException {
    List<String> list = new ArrayList<String>();
    TblDepartmentBean tblDepartmentBean = new TblDepartmentBean();
    list = tblDepartmentBean.selectAllUrl();
    return list;
  }
}
