package com.active.e_uc.user.bean;

import com.active.e_uc.util.ActiveHibernateBase;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

public class TblUserStatusBean extends ActiveHibernateBase {
  public String findstatus(int userid) throws HibernateException {
    String status = "true";
    beginTransaction();
    Query query = this.session
      .createQuery("select s from com.active.e_uc.user.po.TblUserStatus s where s.uid =" + 
        userid);
    List list = query.list();
    if (list.isEmpty())
      status = "false"; 
    endTransaction(true);
    return status;
  }
  
  public List findUserOnline() throws HibernateException {
    List<String> list = new ArrayList();
    List jslist = new ArrayList();
    beginTransaction();
    Query query = this.session
      .createQuery("select user.userName from com.active.e_uc.user.po.TblUser user, com.active.e_uc.user.po.TblUserStatus s where user.id = s.uid");
    list = query.list();
    if (!list.isEmpty()) {
      String accountStr = "";
      for (int i = 0; i < list.size(); i++) {
        String dd = list.get(i);
        accountStr = String.valueOf(accountStr) + dd + "','";
      } 
      accountStr = "'" + accountStr.substring(0, accountStr.length() - 2);
      try {
        HibernateBase hbase = new HibernateBase();
        Session jssession = hbase.getSession();
        jslist = jssession.createQuery(
            "select user.empId from com.js.system.vo.usermanager.EmployeeVO user where user.userAccounts in(" + 
            accountStr + ")").list();
        jssession.close();
        endTransaction(true);
      } catch (Exception exception) {}
    } 
    return jslist;
  }
}
