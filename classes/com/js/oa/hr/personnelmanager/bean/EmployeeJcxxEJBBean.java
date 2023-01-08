package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import com.js.system.vo.usermanager.JcxxVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeJcxxEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(JcxxVO vo, Long empID) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          empID);
      vo.setEmployeeVO(employeeVO);
      this.session.save(vo);
      this.session.flush();
      retflag = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retflag;
  }
  
  public JcxxVO load(Long id) throws Exception {
    JcxxVO vo = new JcxxVO();
    try {
      begin();
      vo = (JcxxVO)this.session.load(JcxxVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(JcxxVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      JcxxVO oldvo = (JcxxVO)this.session.load(JcxxVO.class, id);
      oldvo.setHjsj(vo.getHjsj());
      oldvo.setHjmc(vo.getHjmc());
      oldvo.setHjjb(vo.getHjjb());
      oldvo.setHjsx(vo.getHjsx());
      oldvo.setCfsj(vo.getCfsj());
      oldvo.setCfmc(vo.getCfmc());
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, 
          empID);
      oldvo.setEmployeeVO(employeeVO);
      this.session.update(oldvo);
      this.session.flush();
      retflag = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retflag;
  }
  
  public Boolean delete(Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      JcxxVO vo = (JcxxVO)this.session.load(JcxxVO.class, id);
      this.session.delete(vo);
      this.session.flush();
      retflag = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retflag;
  }
}
