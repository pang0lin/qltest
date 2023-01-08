package com.js.oa.portal.bean;

import com.js.oa.portal.po.CustomDesktopLayoutPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface CustomDesktopEJB extends EJBObject {
  CustomDesktopLayoutPO getLayoutById(String paramString) throws HibernateException, RemoteException;
  
  boolean delLayout(String paramString) throws HibernateException, RemoteException;
  
  Integer saveLayout(CustomDesktopLayoutPO paramCustomDesktopLayoutPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer updateLayout(CustomDesktopLayoutPO paramCustomDesktopLayoutPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List listInformation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  List listInformationClass(String paramString) throws HibernateException, RemoteException;
  
  List listInformationDeptClass(String paramString) throws HibernateException, RemoteException;
  
  Map listMyMail(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Map listMyForum(String paramString) throws HibernateException, RemoteException;
  
  Map listFileDeal(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map listSurvey(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  void setChannelOnDesktop(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Object[] getInformationChannelByChannelId(String paramString) throws HibernateException, RemoteException;
  
  List listNotePaper(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String getPersonalDesktop(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  List getPersonalDesktopList(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  Map listLastUpdate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, Exception, RemoteException;
  
  void setChannelOnDesktopSToLayout(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List listAllOrgs(String paramString) throws HibernateException, RemoteException;
  
  Object[] getOrgByOrgId(String paramString) throws HibernateException, RemoteException;
  
  List listForumClass(String paramString) throws HibernateException, RemoteException;
  
  List listFileDealDT(String paramString1, String paramString2, String paramString3, Integer paramInteger) throws Exception, RemoteException;
  
  List listFileDealList(String paramString1, String paramString2, String paramString3, Integer paramInteger) throws Exception, RemoteException;
  
  List myReceiveFile(String paramString1, String paramString2, Integer paramInteger) throws Exception, RemoteException;
  
  List getMyPrincipalTask(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getOaUserAccountByOrg(String paramString) throws Exception, RemoteException;
  
  String getOaUserAccount(String paramString) throws Exception, RemoteException;
  
  Map getRelationInfo(String paramString) throws Exception, RemoteException;
  
  Map getRelationInfoByOrgId(String paramString) throws Exception, RemoteException;
  
  List getRelationObject(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean saveRelationModule(String paramString1, String paramString2, String[] paramArrayOfString, String paramString3) throws Exception, RemoteException;
  
  List getDefinedRelationObject(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getRelationInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean saveRelationInfo(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString5) throws Exception, RemoteException;
  
  Boolean delRelationInfo(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Boolean delRelationModule(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String[] getWorkFlowInfoByRelation(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getMyJoinTask(String paramString1, String paramString2) throws Exception, RemoteException;
}
