package com.js.oa.online.bean;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface OnlinerEJBLocal extends EJBLocalObject {
  List getAllOnliner();
  
  List getByOrg(String paramString) throws Exception;
  
  List getByName(String paramString) throws Exception;
  
  String getName(String paramString) throws Exception;
}
