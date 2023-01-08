package com.js.oa.module.bean;

import com.js.oa.module.po.ModuleMenuPO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface ModuleMenuEJBLocal extends EJBLocalObject {
  List getMenuListByTopID(String paramString1, String paramString2) throws Exception;
  
  List getAllSubMenus(String paramString1, String paramString2) throws HibernateException;
  
  Long saveMenuConfig(ModuleMenuPO paramModuleMenuPO) throws HibernateException;
  
  List loadParentMenuConfiger(String paramString1, String paramString2) throws HibernateException;
  
  Object getMaxMenuCount(String paramString1, String paramString2) throws Exception;
  
  String[][] getSearchConfigs(String paramString);
  
  List loadMenuConfig(String paramString1, String paramString2) throws HibernateException;
  
  String[][] getQueryFields(String paramString1, String paramString2) throws Exception;
  
  String[][] getFieldsTypes(String paramString) throws Exception;
  
  boolean delAllCustmizeMenus(String paramString) throws HibernateException;
  
  boolean delBatchCustmizeMenus(String paramString1, String paramString2) throws HibernateException;
  
  boolean updateRootLevel(String paramString) throws HibernateException;
  
  String getSearchHtmlPart(String paramString1, String paramString2) throws HibernateException;
  
  String[][] getBizTableJoins(String paramString1, String paramString2) throws Exception;
  
  String[][] getDefaultLoadDatas(String paramString1, Integer paramInteger, String paramString2) throws Exception;
  
  boolean updateCustomerMenu(ModuleMenuPO paramModuleMenuPO) throws HibernateException;
  
  String[][] getTableFields(String paramString1, String paramString2) throws Exception;
  
  String[][] getSubTableDatas(String paramString1, String paramString2, Integer paramInteger) throws Exception;
  
  boolean deleteBizDatas(String paramString1, String paramString2) throws Exception;
  
  boolean deleteAllMasterAndSub(String paramString) throws Exception;
  
  List getWFProcesses(String paramString) throws HibernateException;
  
  String getDefaultLoadDatasCount(String paramString1, String paramString2) throws Exception;
  
  List listMenuConfigs(String paramString) throws HibernateException;
  
  List getAllSubMenusBySetId(String paramString1, String paramString2) throws HibernateException;
  
  List getAllGroupsByUserId(String paramString1, String paramString2) throws HibernateException;
  
  Boolean addMenuListRight(List paramList) throws HibernateException;
  
  Boolean deleteMenuRight(String paramString) throws HibernateException;
  
  List getAllSubMenus(String paramString) throws HibernateException;
  
  List getAllQueryCaseByTblId(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  String[][] getQueryShowFieldsByCase(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Long saveQLCaseSet(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException;
  
  String[][] getQueryField(String paramString1, String paramString2) throws Exception;
  
  String[][] getListField(String paramString1, String paramString2) throws Exception;
  
  Boolean delQLCaseSet(String paramString) throws HibernateException;
  
  List getAllCustomMenu(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  String getViewScope(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException;
  
  String getMenuScope(String paramString1, String paramString2) throws Exception;
  
  String setMenuDisplay(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger) throws Exception;
  
  Boolean showMenu(String paramString1, String paramString2) throws Exception;
  
  String getShowMenu(String paramString1, String paramString2) throws Exception;
  
  String getMenuNameByIds(String paramString) throws Exception;
  
  Object[] getMenuActionAndPara(String paramString1, String paramString2) throws Exception;
}
