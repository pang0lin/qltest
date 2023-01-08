package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.MyInfoPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface MyInfoEJBLocal extends EJBLocalObject {
  MyInfoPO load(String paramString) throws Exception;
  
  String update(MyInfoPO paramMyInfoPO, String paramString1, String paramString2) throws Exception;
  
  String updatePass(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String loadRTXLogin(String paramString) throws Exception;
  
  Boolean updateRTXLogin(String paramString1, String paramString2) throws Exception;
  
  Integer skinSetup(String paramString1, String paramString2) throws Exception;
  
  Object[] getEmpStatus(String paramString) throws Exception;
  
  Integer setEmpStatus(String paramString1, String paramString2) throws Exception;
  
  Integer delEmpStatus(String paramString1, String paramString2) throws Exception;
  
  List getEmpStatusByEmpIdArr(String[] paramArrayOfString) throws Exception;
  
  List getEmpStatusByEmpIdStr(String paramString) throws Exception;
  
  List getEmpStatusByEmpOrgGrp(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer saveEmpDefineStatus(String paramString1, String paramString2) throws Exception;
  
  Integer saveEmpNewStatus(String paramString1, String paramString2) throws Exception;
}
