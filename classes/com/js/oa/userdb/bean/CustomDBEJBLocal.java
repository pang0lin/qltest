package com.js.oa.userdb.bean;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface CustomDBEJBLocal extends EJBLocalObject {
  String[][] getModelInfo(String paramString) throws Exception;
  
  int addModel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  int batchDelete(String[] paramArrayOfString) throws Exception;
  
  int delSingleModel(String paramString) throws Exception;
  
  void updateModel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String[][] getTableInfo(String paramString) throws Exception;
  
  String[][] getTableInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String[][] getModelIDName(String paramString) throws Exception;
  
  String getTableName(String paramString) throws Exception;
  
  void batchDeleteTable(String[] paramArrayOfString) throws Exception;
  
  void delSignleTable(String paramString) throws Exception;
  
  String[][] getFieldInfo(String paramString1, String paramString2) throws Exception;
  
  void delSignleField(String paramString1, String paramString2) throws Exception;
  
  String[][] getType() throws Exception;
  
  String getModelid(String paramString) throws Exception;
  
  int updateField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13) throws Exception;
  
  int updateSimpleField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18) throws Exception;
  
  void updateFieldHide(String[] paramArrayOfString, String paramString) throws Exception;
  
  int addField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19) throws Exception;
  
  String getFieldName(String paramString) throws Exception;
  
  void setShow(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  void batchDeleteField(String paramString, String[] paramArrayOfString) throws Exception;
  
  int updateTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  int addTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String[][] getAssociateInfo(String paramString1, String paramString2) throws Exception;
  
  void batchDelAssociate(String[] paramArrayOfString) throws Exception;
  
  void delSignleAssociate(String paramString) throws Exception;
  
  String[][] getMainFieldInfo(String paramString) throws Exception;
  
  String[][] getPryTableInfo(String paramString) throws Exception;
  
  String[][] getPryFieldInfo(String paramString) throws Exception;
  
  int addAssociate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  int updateAssociate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String[][] getShow(String paramString1, String paramString2) throws Exception;
  
  String[][] getCountField(String paramString1, String paramString2) throws Exception;
  
  String[][] getQueryField(String paramString) throws Exception;
  
  Boolean addQueryField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean editQueryField(String paramString1, String paramString2) throws Exception;
  
  Boolean delQueryField(String paramString) throws Exception;
  
  Boolean delQueryField(String[] paramArrayOfString) throws Exception;
  
  String getQueryFieldHTML(String paramString) throws Exception;
  
  String[][] getListField(String paramString) throws Exception;
  
  String[][] getSingleTable(String paramString) throws Exception;
  
  Boolean batchUpdateShow(String[] paramArrayOfString, String paramString) throws Exception;
  
  String getSystemMark() throws Exception;
  
  String getMarkCode(String paramString) throws Exception;
  
  String checkSame(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getFiledCode(String paramString) throws Exception;
  
  List getAssociateTable(String paramString1, String paramString2) throws Exception;
  
  Boolean addAssociateTable(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String[][] getTotField(String paramString);
  
  Integer saveTotField(String paramString1, String paramString2);
  
  String getTableTotField(String paramString);
  
  String getTableTotFieldValue(String paramString1, String paramString2);
  
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
