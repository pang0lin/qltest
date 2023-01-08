package com.js.oa.message.bean;

import javax.ejb.EJBObject;

public interface MsHistoryEJB extends EJBObject {
  Boolean saveMsHistory(String paramString1, String paramString2, String paramString3, String paramString4);
  
  String genExtendCode();
}
