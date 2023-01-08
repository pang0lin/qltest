package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeOtherInfoEJB extends EJBObject {
  EmployeeOtherInfoVO load(Long paramLong) throws Exception, RemoteException;
}
