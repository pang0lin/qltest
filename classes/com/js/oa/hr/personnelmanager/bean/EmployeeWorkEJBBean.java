package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import com.js.system.vo.usermanager.WorkVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeWorkEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(WorkVO vo, Long empID) throws Exception {
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
  
  public WorkVO load(Long id) throws Exception {
    WorkVO vo = new WorkVO();
    try {
      begin();
      vo = (WorkVO)this.session.load(WorkVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(WorkVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      WorkVO oldvo = (WorkVO)this.session.load(WorkVO.class, id);
      oldvo.setBeginDate(vo.getBeginDate());
      oldvo.setEndDate(vo.getEndDate());
      oldvo.setWorkunit(vo.getWorkunit());
      oldvo.setWorkduty(vo.getWorkduty());
      oldvo.setProveperson(vo.getProveperson());
      oldvo.setTelephone(vo.getTelephone());
      oldvo.setWorkMemo(vo.getWorkMemo());
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
      WorkVO vo = (WorkVO)this.session.load(WorkVO.class, id);
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
