package com.js.oa.personalwork.person.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface PersonInnerEJB extends EJBObject {
  Vector list(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception, RemoteException;
  
  Map load(String paramString) throws Exception, RemoteException;
  
  void update(EmployeeVO paramEmployeeVO, String paramString) throws Exception, RemoteException;
  
  List see() throws Exception, RemoteException;
  
  List city(String paramString) throws Exception, RemoteException;
  
  List county(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List setValidOrgs(Long paramLong) throws Exception, RemoteException;
}
