package com.js.oa.eform.bean;

import javax.ejb.EJBLocalObject;

public interface CustomFormEJBLocal extends EJBLocalObject {
  String getCode(String paramString) throws Exception;
  
  String getEditHTML(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String[] getField(String paramString) throws Exception;
  
  String[][] getPageField(String paramString) throws Exception;
  
  String[][] getFieldInfo(String paramString) throws Exception;
  
  String getHTML(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getHTMLBatchAdd(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getTable(String paramString) throws Exception;
  
  String getValue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String[][] setValue(String paramString1, String paramString2) throws Exception;
  
  String[][] getQueryFormByTblId(String paramString1, String paramString2) throws Exception;
  
  String[][] getTableIDAndName(String paramString) throws Exception;
  
  String[][] loadDataBySQL(String paramString1, String paramString2) throws Exception;
  
  String getComputeFieldHTML(String paramString) throws Exception;
  
  String getAutoCode(String paramString1, String paramString2) throws Exception;
  
  String[][] getCommentField(String paramString) throws Exception;
  
  String getFieldShowValue(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getRemindValue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String pasteForm(String paramString1, String paramString2) throws Exception;
  
  String[][] getQueryMainFormByTblId(String paramString1, String paramString2);
  
  String getFieldShowValue(String paramString1, String paramString2, String paramString3);
  
  String getPageName(String[] paramArrayOfString) throws Exception;
  
  String getCurStep(String paramString) throws Exception;
  
  String[] getJSCode(String paramString) throws Exception;
}
