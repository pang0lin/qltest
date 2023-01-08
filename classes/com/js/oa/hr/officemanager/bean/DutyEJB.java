package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import javax.servlet.http.HttpServletRequest;

public interface DutyEJB extends EJBObject {
  Integer add(DutyPO paramDutyPO) throws Exception, RemoteException;
  
  Boolean del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getDuteList(String paramString) throws Exception, RemoteException;
  
  Map ImportDuty(HttpServletRequest paramHttpServletRequest) throws FileNotFoundException;
  
  boolean updateByDutySql(String paramString) throws Exception;
}
