package com.active.e_uc.user.service;

import com.active.e_uc.user.bean.TblSysmsgBean;
import com.active.e_uc.user.po.TblSysmsg;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class TblSysmsgBD {
  public void addTblSysmsg(TblSysmsg dp) throws HibernateException {
    TblSysmsgBean tblSysmsgBean = new TblSysmsgBean();
    tblSysmsgBean.addTblSysmsg(dp);
  }
  
  public List findUserId(String str) throws HibernateException {
    List list = new ArrayList();
    TblSysmsgBean tblSysmsgBean = new TblSysmsgBean();
    list = tblSysmsgBean.findUserId(str);
    return list;
  }
}
