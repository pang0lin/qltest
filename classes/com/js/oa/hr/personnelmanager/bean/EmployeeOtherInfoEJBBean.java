package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class EmployeeOtherInfoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public EmployeeOtherInfoVO load(Long id) throws Exception {
    EmployeeOtherInfoVO vo = new EmployeeOtherInfoVO();
    try {
      begin();
      Query query = this.session.createQuery("FROM com.js.system.vo.usermanager.EmployeeOtherInfoVO emp WHERE emp.empId IN (" + id + ")");
      List<EmployeeOtherInfoVO> list = query.list();
      if (list != null && list.size() > 0)
        vo = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
}
