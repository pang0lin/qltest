package com.js.oa.scheme.taskcenter.bean;

import com.js.oa.scheme.taskcenter.po.TaskPO;
import com.js.oa.scheme.taskcenter.po.TaskPressPO;
import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class TaskPressEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long savePO(TaskPressPO po) throws Exception {
    Long result = Long.valueOf(0L);
    try {
      begin();
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (HibernateException ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public TaskPO getTaskPO(Long taskId) throws Exception {
    TaskPO po = new TaskPO();
    try {
      begin();
      po = (TaskPO)this.session.load(TaskPO.class, taskId);
    } catch (HibernateException ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return po;
  }
  
  public List<TaskPressPO> getTaskPressPOList(String hql) throws Exception {
    List<TaskPressPO> list = new ArrayList<TaskPressPO>();
    try {
      begin();
      list = this.session.find(hql);
    } catch (HibernateException ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
}
