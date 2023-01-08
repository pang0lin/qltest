package com.js.oa.eform.bean;

import javax.ejb.EJBLocalObject;

public interface DataBaseInfoEJBLocal extends EJBLocalObject {
  String[][] getTableInfo(String paramString) throws Exception;
  
  String[][] getFieldInfo(String paramString) throws Exception;
}
