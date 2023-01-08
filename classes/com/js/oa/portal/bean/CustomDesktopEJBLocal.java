package com.js.oa.portal.bean;

import com.js.oa.portal.po.CustomDesktopLayoutPO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface CustomDesktopEJBLocal extends EJBLocalObject {
  CustomDesktopLayoutPO getLayoutById(String paramString) throws HibernateException;
  
  boolean delLayout(String paramString) throws HibernateException;
  
  Integer saveLayout(CustomDesktopLayoutPO paramCustomDesktopLayoutPO, String paramString1, String paramString2) throws HibernateException;
  
  Integer updateLayout(CustomDesktopLayoutPO paramCustomDesktopLayoutPO, String paramString1, String paramString2) throws HibernateException;
  
  List listInformation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException;
  
  List listInformationClass(String paramString) throws HibernateException;
  
  List listInformationDeptClass(String paramString) throws HibernateException;
  
  Map listMyMail(String paramString1, String paramString2) throws HibernateException;
  
  Map listMyForum(String paramString) throws HibernateException;
  
  Map listFileDeal(String paramString1, String paramString2) throws Exception;
  
  Map listSurvey(String paramString1, String paramString2) throws HibernateException;
  
  void setChannelOnDesktop(String paramString1, String paramString2) throws Exception;
  
  Object[] getInformationChannelByChannelId(String paramString) throws HibernateException;
  
  List listNotePaper(String paramString1, String paramString2) throws HibernateException;
  
  String getPersonalDesktop(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  List getPersonalDesktopList(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  Map listLastUpdate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, Exception;
  
  void setChannelOnDesktopSToLayout(String paramString1, String paramString2) throws Exception;
  
  List listAllOrgs(String paramString) throws HibernateException;
  
  Object[] getOrgByOrgId(String paramString) throws HibernateException;
  
  List listForumClass(String paramString) throws HibernateException;
  
  List listFileDealDT(String paramString1, String paramString2, String paramString3, Integer paramInteger) throws Exception;
  
  List listFileDealList(String paramString1, String paramString2, String paramString3, Integer paramInteger) throws Exception;
  
  List myReceiveFile(String paramString1, String paramString2, Integer paramInteger) throws Exception;
  
  List getMyPrincipalTask(String paramString1, String paramString2) throws Exception;
  
  String getOaUserAccountByOrg(String paramString) throws Exception;
  
  String getOaUserAccount(String paramString) throws Exception;
  
  Map getRelationInfo(String paramString) throws Exception;
  
  Map getRelationInfoByOrgId(String paramString) throws Exception;
  
  List getRelationObject(String paramString1, String paramString2) throws Exception;
  
  Boolean saveRelationModule(String paramString1, String paramString2, String[] paramArrayOfString, String paramString3) throws Exception;
  
  List getDefinedRelationObject(String paramString1, String paramString2) throws Exception;
  
  List getRelationInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean saveRelationInfo(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString5) throws Exception;
  
  Boolean delRelationInfo(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Boolean delRelationModule(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String[] getWorkFlowInfoByRelation(String paramString1, String paramString2) throws Exception;
  
  List getMyJoinTask(String paramString1, String paramString2) throws Exception;
}
