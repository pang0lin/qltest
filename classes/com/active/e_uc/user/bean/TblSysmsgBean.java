package com.active.e_uc.user.bean;

import com.active.e_uc.user.po.TblSysmsg;
import com.active.e_uc.util.ActiveHibernateBase;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;

public class TblSysmsgBean extends ActiveHibernateBase {
  public void addTblSysmsg(TblSysmsg dp) throws HibernateException {
    try {
      beginTransaction();
      this.session.save(dp);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      endTransaction(true);
    } 
  }
  
  public List findUserId(String str) throws HibernateException {
    List list = new ArrayList();
    try {
      beginTransaction();
      list = this.session.createQuery(
          "select user.id from com.active.e_uc.user.po.TblUser user where user.userName in(" + 
          str + ")").list();
    } catch (Exception exception) {
    
    } finally {
      endTransaction(true);
    } 
    return list;
  }
}
