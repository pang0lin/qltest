package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import com.js.system.vo.usermanager.TrainhistoryVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeTrainhistoryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(TrainhistoryVO vo, Long empID) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, empID);
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
  
  public TrainhistoryVO load(Long id) throws Exception {
    TrainhistoryVO vo = null;
    try {
      begin();
      vo = (TrainhistoryVO)this.session.load(TrainhistoryVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(TrainhistoryVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      TrainhistoryVO oldvo = (TrainhistoryVO)this.session.load(TrainhistoryVO.class, id);
      oldvo.setBeginDate(vo.getBeginDate());
      oldvo.setEndDate(vo.getEndDate());
      oldvo.setTrainMemo(vo.getTrainMemo());
      oldvo.setTrainName(vo.getTrainName());
      oldvo.setTrainUnit(vo.getTrainUnit());
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, empID);
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
      TrainhistoryVO vo = (TrainhistoryVO)this.session.load(TrainhistoryVO.class, id);
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
