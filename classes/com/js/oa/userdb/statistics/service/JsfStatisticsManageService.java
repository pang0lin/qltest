package com.js.oa.userdb.statistics.service;

import com.js.oa.userdb.statistics.action.JsfStatisticsManageForm;
import com.js.oa.userdb.statistics.bean.JsfStatisticsEJBBean;
import com.js.oa.userdb.statistics.po.JsfStatisticsManage;
import com.js.util.util.FillBean;

public class JsfStatisticsManageService {
  public boolean manageAdd(JsfStatisticsManageForm form) {
    boolean b = true;
    try {
      JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
      JsfStatisticsManage po = (JsfStatisticsManage)FillBean.transformOTO(form, JsfStatisticsManage.class);
      Long id = db.manageAdd(po);
      if (id == null || "".equals(id))
        b = false; 
    } catch (Exception e) {
      b = false;
      e.printStackTrace();
    } 
    return b;
  }
  
  public JsfStatisticsManage loadStatisticsManage(Long id) {
    JsfStatisticsManage po = null;
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    try {
      po = db.loadStatisticsManage(id);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public boolean manageUpdate(JsfStatisticsManageForm form) {
    boolean b = true;
    try {
      JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
      JsfStatisticsManage po = (JsfStatisticsManage)FillBean.transformOTO(form, JsfStatisticsManage.class);
      b = db.manageUpdate(po);
    } catch (Exception e) {
      b = false;
      e.printStackTrace();
    } 
    return b;
  }
  
  public boolean deleteManage(String ids) {
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    boolean b = true;
    try {
      b = db.deleteManage(ids);
    } catch (Exception e) {
      b = false;
      e.printStackTrace();
    } 
    return b;
  }
}
