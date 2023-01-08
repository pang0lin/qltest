package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.ContractVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EmployeeContractEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(ContractVO po, Long empID) throws Exception {
    Boolean ret = new Boolean(false);
    try {
      begin();
      EmployeeVO employeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, empID);
      po.setEmployeeVO(employeeVO);
      this.session.save(po);
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public ContractVO load(Long id) throws Exception {
    ContractVO vo = null;
    try {
      begin();
      vo = (ContractVO)this.session.load(ContractVO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return vo;
  }
  
  public Boolean update(ContractVO vo, Long empID, Long id) throws Exception {
    Boolean retflag = Boolean.FALSE;
    try {
      begin();
      ContractVO needModifyVO = (ContractVO)this.session.load(ContractVO.class, id);
      needModifyVO.setGivenDate(vo.getGivenDate());
      needModifyVO.setContractStyle(vo.getContractStyle());
      needModifyVO.setBeginDate(vo.getBeginDate());
      needModifyVO.setEndDate(vo.getEndDate());
      needModifyVO.setContractNO(vo.getContractNO());
      needModifyVO.setContractType(vo.getContractType());
      if ("0".equals(vo.getContractType())) {
        needModifyVO.setContractLimit(vo.getContractLimit());
      } else {
        needModifyVO.setContractLimit(null);
      } 
      EmployeeVO empVO = (EmployeeVO)this.session.load(EmployeeVO.class, empID);
      needModifyVO.setEmployeeVO(empVO);
      this.session.update(needModifyVO);
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
      ContractVO vo = (ContractVO)this.session.load(ContractVO.class, id);
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
