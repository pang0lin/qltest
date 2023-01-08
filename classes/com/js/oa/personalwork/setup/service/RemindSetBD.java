package com.js.oa.personalwork.setup.service;

import com.js.oa.personalwork.setup.bean.RemindSetEJBBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class RemindSetBD {
  private static Logger logger = Logger.getLogger(RemindSetBD.class.getName());
  
  RemindSetEJBBean remindSetEJBBean = new RemindSetEJBBean();
  
  public List seachByUserId(long userId) throws HibernateException {
    List list = new ArrayList();
    list = this.remindSetEJBBean.seachByUserId(userId);
    return list;
  }
  
  public void remindSet(long userId, String type, String status) throws HibernateException {
    this.remindSetEJBBean.remindSet(userId, type, status);
  }
  
  public String getStatusByEmpId(long userId, String type) throws HibernateException {
    String re = "";
    re = this.remindSetEJBBean.getStatusByEmpId(userId, type);
    return re;
  }
  
  public List getRemindType() throws HibernateException {
    List list = new ArrayList();
    list = this.remindSetEJBBean.getRemindType();
    return list;
  }
  
  public Map<String, String> getRemindTypeMap() throws HibernateException {
    Map<String, String> map = new HashMap<String, String>();
    map = this.remindSetEJBBean.getRemindTypeMap();
    return map;
  }
}
