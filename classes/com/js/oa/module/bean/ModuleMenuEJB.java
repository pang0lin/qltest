package com.js.oa.module.bean;

import com.js.oa.module.po.ModuleMenuPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface ModuleMenuEJB extends EJBObject {
  List getMenuListByTopID(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllSubMenus(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Long saveMenuConfig(ModuleMenuPO paramModuleMenuPO) throws HibernateException, RemoteException;
  
  List loadParentMenuConfiger(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Object getMaxMenuCount(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getSearchConfigs(String paramString) throws RemoteException;
  
  List loadMenuConfig(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String[][] getQueryFields(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getFieldsTypes(String paramString) throws Exception, RemoteException;
  
  boolean delAllCustmizeMenus(String paramString) throws HibernateException, RemoteException;
  
  boolean delBatchCustmizeMenus(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  boolean updateRootLevel(String paramString) throws HibernateException, RemoteException;
  
  String getSearchHtmlPart(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String[][] getBizTableJoins(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getDefaultLoadDatas(String paramString1, Integer paramInteger, String paramString2) throws Exception, RemoteException;
  
  boolean updateCustomerMenu(ModuleMenuPO paramModuleMenuPO) throws HibernateException, RemoteException;
  
  String[][] getTableFields(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getSubTableDatas(String paramString1, String paramString2, Integer paramInteger) throws Exception, RemoteException;
  
  boolean deleteBizDatas(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean deleteAllMasterAndSub(String paramString) throws Exception, RemoteException;
  
  List getWFProcesses(String paramString) throws HibernateException, RemoteException;
  
  String getDefaultLoadDatasCount(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List listMenuConfigs(String paramString) throws HibernateException, RemoteException;
  
  List getAllSubMenusBySetId(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List getAllGroupsByUserId(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Boolean addMenuListRight(List paramList) throws HibernateException, RemoteException;
  
  Boolean deleteMenuRight(String paramString) throws HibernateException, RemoteException;
  
  List getAllSubMenus(String paramString) throws HibernateException, RemoteException;
  
  List getAllQueryCaseByTblId(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  String[][] getQueryShowFieldsByCase(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Long saveQLCaseSet(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  String[][] getQueryField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getListField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean delQLCaseSet(String paramString) throws HibernateException, RemoteException;
  
  List getAllCustomMenu(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  String getViewScope(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  String getMenuScope(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String setMenuDisplay(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger) throws Exception, RemoteException;
  
  Boolean showMenu(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getShowMenu(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getMenuNameByIds(String paramString) throws Exception;
  
  Object[] getMenuActionAndPara(String paramString1, String paramString2) throws Exception;
}
