package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EdustoryVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeEdustoryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EdustoryVO vo, Long empID) throws Exception {
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
  
  public EdustoryVO load(Long id) throws Exception {
    EdustoryVO vo = new EdustoryVO();
    try {
      begin();
      vo = (EdustoryVO)this.session.load(EdustoryVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(EdustoryVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      EdustoryVO oldvo = (EdustoryVO)this.session.load(EdustoryVO.class, id);
      oldvo.setBeginDate(vo.getBeginDate());
      oldvo.setEndDate(vo.getEndDate());
      oldvo.setSchools(vo.getSchools());
      oldvo.setSpeciality(vo.getSpeciality());
      oldvo.setEducation(vo.getEducation());
      oldvo.setDegree(vo.getDegree());
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
      EdustoryVO vo = (EdustoryVO)this.session.load(EdustoryVO.class, id);
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
