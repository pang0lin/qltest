package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpAttendancePO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class EmpAttendanceEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EmpAttendancePO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public EmpAttendancePO load(Long id) throws HibernateException {
    EmpAttendancePO po = null;
    try {
      begin();
      po = (EmpAttendancePO)this.session.load(EmpAttendancePO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean modify(EmpAttendancePO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      EmpAttendancePO o = (EmpAttendancePO)this.session.load(EmpAttendancePO.class, 
          po.getId());
      o.setEmp(po.getEmp());
      o.setEmpOrg(po.getEmpOrg());
      o.setEmpName(po.getEmpName());
      o.setYearMonth(po.getYearMonth());
      o.setPlanHours(po.getPlanHours());
      o.setRealHours(po.getRealHours());
      o.setDelayHours(po.getDelayHours());
      o.setDbrestHours(po.getDbrestHours());
      o.setOfficialHours(po.getOfficialHours());
      o.setLeaveHours(po.getLeaveHours());
      o.setOffworkHours(po.getOffworkHours());
      o.setOfftuneHours(po.getOfftuneHours());
      o.setUncardHours(po.getUncardHours());
      o.setLateHours(po.getLateHours());
      o.setDescriptions(po.getDescriptions());
      this.session.saveOrUpdate(o);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean delete(Long id) throws HibernateException {
    Boolean result = new Boolean(false);
    EmpAttendancePO po = null;
    try {
      begin();
      po = (EmpAttendancePO)this.session.load(EmpAttendancePO.class, id);
      if (po != null) {
        this.session.delete(po);
        this.session.flush();
        result = Boolean.TRUE;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean batchDel(String ids) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.delete(
          "from com.js.oa.hr.personnelmanager.po.EmpAttendancePO po where po.id in (" + 
          ids + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean checkExists(Long userId, Long id, String yearMonth) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      List list = this.session.createQuery(
          "select po from com.js.oa.hr.personnelmanager.po.EmpAttendancePO po where " + ((
          id != null && !"".equals(id)) ? (
          " po.id != " + id + " and ") : "") + 
          " po.emp.empId = " + userId + " and po.yearMonth ='" + yearMonth + "'")
        .list();
      if (list != null && list.size() > 0)
        ret = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
}
