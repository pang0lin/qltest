package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.ContractVO;
import javax.ejb.EJBLocalObject;

public interface EmployeeContractEJBLocal extends EJBLocalObject {
  Boolean save(ContractVO paramContractVO, Long paramLong) throws Exception;
  
  ContractVO load(Long paramLong) throws Exception;
  
  Boolean update(ContractVO paramContractVO, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
