package com.js.oa.hr.officemanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface EmployeeEJB extends EJBObject {
  Integer add(EmployeeVO paramEmployeeVO, String paramString) throws Exception, RemoteException;
  
  Boolean del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  Boolean rehis(String paramString) throws Exception, RemoteException;
  
  List listDuty(String paramString) throws Exception, RemoteException;
  
  List listStation(String paramString) throws Exception, RemoteException;
  
  Integer update(EmployeeVO paramEmployeeVO, String paramString, Long paramLong) throws Exception, RemoteException;
  
  List selectSingle(Long paramLong) throws Exception, RemoteException;
  
  List listCountry() throws Exception, RemoteException;
  
  List city(String paramString) throws Exception, RemoteException;
  
  List county(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List postTitle(String paramString) throws Exception, RemoteException;
  
  String batchResume(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List export(String paramString) throws Exception, RemoteException;
  
  Integer containUsersCount(String paramString) throws Exception, RemoteException;
  
  Boolean regain(String paramString) throws Exception, RemoteException;
  
  Boolean forbid(String paramString) throws Exception, RemoteException;
  
  String judgeAccount(String paramString) throws Exception, RemoteException;
  
  String judgeAccountById(String paramString) throws Exception, RemoteException;
  
  String setUserAccountAndPass(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List export_contract(String paramString) throws Exception, RemoteException;
  
  List export_edusty(String paramString) throws Exception, RemoteException;
  
  List export_work(String paramString) throws Exception, RemoteException;
  
  List export_trainhistory(String paramString) throws Exception, RemoteException;
  
  List export_competence(String paramString) throws Exception, RemoteException;
}
