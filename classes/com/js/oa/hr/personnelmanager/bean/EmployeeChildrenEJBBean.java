package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.ChildrenVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeChildrenEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(ChildrenVO vo, Long empID) throws Exception {
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
  
  public ChildrenVO load(Long id) throws Exception {
    ChildrenVO vo = new ChildrenVO();
    try {
      begin();
      vo = (ChildrenVO)this.session.load(ChildrenVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(ChildrenVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      ChildrenVO oldvo = (ChildrenVO)this.session.load(ChildrenVO.class, id);
      oldvo.setGx(vo.getGx());
      oldvo.setXm(vo.getXm());
      oldvo.setCsny(vo.getCsny());
      oldvo.setZzmm(vo.getZzmm());
      oldvo.setGzdwjbm(vo.getGzdwjbm());
      oldvo.setZw(vo.getZw());
      oldvo.setBz(vo.getBz());
      oldvo.setSfzhm(vo.getSfzhm());
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
      ChildrenVO vo = (ChildrenVO)this.session.load(ChildrenVO.class, id);
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
