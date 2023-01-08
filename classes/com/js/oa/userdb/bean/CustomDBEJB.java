package com.js.oa.userdb.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface CustomDBEJB extends EJBObject {
  String[][] getModelInfo(String paramString) throws Exception, RemoteException;
  
  int addModel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  int batchDelete(String[] paramArrayOfString) throws Exception, RemoteException;
  
  int delSingleModel(String paramString) throws Exception, RemoteException;
  
  void updateModel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String[][] getTableInfo(String paramString) throws Exception, RemoteException;
  
  String[][] getTableInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String[][] getModelIDName(String paramString) throws Exception, RemoteException;
  
  String getTableName(String paramString) throws Exception, RemoteException;
  
  void batchDeleteTable(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delSignleTable(String paramString) throws Exception, RemoteException;
  
  String[][] getFieldInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getType() throws Exception, RemoteException;
  
  String getModelid(String paramString) throws Exception, RemoteException;
  
  void delSignleField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void batchDeleteField(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  int updateField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13) throws Exception, RemoteException;
  
  int updateSimpleField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18) throws Exception;
  
  void updateFieldHide(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
  
  int addField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19) throws Exception, RemoteException;
  
  String getFieldName(String paramString) throws Exception, RemoteException;
  
  void setShow(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  int updateTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  int addTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String[][] getAssociateInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void batchDelAssociate(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delSignleAssociate(String paramString) throws Exception, RemoteException;
  
  String[][] getMainFieldInfo(String paramString) throws Exception, RemoteException;
  
  String[][] getPryTableInfo(String paramString) throws Exception, RemoteException;
  
  String[][] getPryFieldInfo(String paramString) throws Exception, RemoteException;
  
  int addAssociate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  int updateAssociate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String[][] getShow(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getCountField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[][] getQueryField(String paramString) throws Exception, RemoteException;
  
  Boolean addQueryField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean editQueryField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean delQueryField(String paramString) throws Exception, RemoteException;
  
  Boolean delQueryField(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String getQueryFieldHTML(String paramString) throws Exception, RemoteException;
  
  String[][] getListField(String paramString) throws Exception, RemoteException;
  
  String[][] getSingleTable(String paramString) throws Exception, RemoteException;
  
  Boolean batchUpdateShow(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
  
  String getSystemMark() throws Exception, RemoteException;
  
  String getMarkCode(String paramString) throws Exception, RemoteException;
  
  String checkSame(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getFiledCode(String paramString) throws Exception, RemoteException;
  
  List getAssociateTable(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean addAssociateTable(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String[][] getTotField(String paramString) throws RemoteException;
  
  Integer saveTotField(String paramString1, String paramString2) throws RemoteException;
  
  String getTableTotField(String paramString) throws RemoteException;
  
  String getTableTotFieldValue(String paramString1, String paramString2) throws RemoteException;
  
  String pasteTable(String paramString1, String paramString2) throws Exception;
  
  String getModuelName(String[] paramArrayOfString) throws Exception;
  
  String getTableName(String[] paramArrayOfString) throws Exception;
  
  String getTableDesName(String paramString) throws Exception;
  
  List getTotFieldInfo(String paramString) throws Exception;
  
  String[][] getAllFieldInfo(String paramString1, String paramString2) throws Exception;
  
  String[][] getSimpleTableInfo(String paramString1, String paramString2) throws Exception;
  
  int updateSimpleTable(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getShouldDelId(String paramString, String[] paramArrayOfString) throws Exception;
  
  String checkTableName(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getDefaultModelId(String paramString) throws Exception;
  
  String[][] getBoxSearchField(String paramString1, String paramString2) throws Exception;
  
  List getOnchangeMethodInfo(String paramString1, String paramString2) throws Exception;
  
  String[] getFieldExtProperty(String paramString) throws Exception;
  
  String[][] getFieldSimpleInfo(String paramString) throws Exception;
}
