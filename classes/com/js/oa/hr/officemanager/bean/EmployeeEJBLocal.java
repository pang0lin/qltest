package com.js.oa.hr.officemanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface EmployeeEJBLocal extends EJBLocalObject {
  Integer add(EmployeeVO paramEmployeeVO, String paramString) throws Exception;
  
  Boolean del(String[] paramArrayOfString) throws Exception;
  
  Boolean rehis(String paramString) throws Exception;
  
  List listDuty(String paramString) throws Exception;
  
  List listStation(String paramString) throws Exception;
  
  Integer update(EmployeeVO paramEmployeeVO, String paramString, Long paramLong) throws Exception;
  
  List selectSingle(Long paramLong) throws Exception;
  
  List listCountry() throws Exception;
  
  List city(String paramString) throws Exception;
  
  List county(String paramString1, String paramString2) throws Exception;
  
  List postTitle(String paramString) throws Exception;
  
  String batchResume(String[] paramArrayOfString) throws Exception;
  
  List export(String paramString) throws Exception;
  
  Integer containUsersCount(String paramString) throws Exception;
  
  Boolean regain(String paramString) throws Exception;
  
  Boolean forbid(String paramString) throws Exception;
  
  String judgeAccount(String paramString) throws Exception;
  
  String judgeAccountById(String paramString) throws Exception;
  
  String setUserAccountAndPass(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List export_contract(String paramString) throws Exception;
  
  List export_edusty(String paramString) throws Exception;
  
  List export_work(String paramString) throws Exception;
  
  List export_trainhistory(String paramString) throws Exception;
  
  List export_competence(String paramString) throws Exception;
}
