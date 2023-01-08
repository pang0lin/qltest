package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OptionSetPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class OptionSetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  private void add(OptionSetPO po, String userID) throws Exception {
    try {
      po.setEmpId(Long.parseLong(userID));
      this.session.save(po);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } 
  }
  
  public OptionSetPO load(String userId) throws Exception {
    OptionSetPO po = null;
    try {
      begin();
      List<OptionSetPO> list = this.session.createQuery("select po from com.js.oa.personalwork.setup.po.OptionSetPO po where po.empId = " + 
          userId).list();
      if (list.size() > 0)
        po = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public String update(OptionSetPO paraPO, String userId) throws Exception {
    String message = "";
    try {
      begin();
      List<OptionSetPO> list = this.session.createQuery("select po from com.js.oa.personalwork.setup.po.OptionSetPO po where po.empId = " + 
          userId).list();
      if (list.size() > 0) {
        OptionSetPO po = list.get(0);
        po.setBeginTimeOfDay(paraPO.getBeginTimeOfDay());
        po.setCalendarRemind(paraPO.getCalendarRemind());
        po.setEndTimeOfDay(paraPO.getEndTimeOfDay());
        po.setFinishTaskColor(paraPO.getFinishTaskColor());
        po.setFirstDayOfWeek(paraPO.getFirstDayOfWeek());
        po.setFisrtWeekOfYear(paraPO.getFisrtWeekOfYear());
        po.setNotePaperColor(paraPO.getNotePaperColor());
        po.setOverdueTaskColor(paraPO.getOverdueTaskColor());
        po.setWorkingDay(paraPO.getWorkingDay());
        po.setEmpId(Long.parseLong(userId));
        this.session.update(po);
        message = "修改了选项设置";
      } else {
        add(paraPO, userId);
      } 
      this.session.flush();
      message = "true";
    } catch (Exception e) {
      e.printStackTrace();
      message = "false";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return message;
  }
}
