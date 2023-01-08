package com.js.oa.logon.bean;

import java.util.HashMap;
import javax.ejb.EJBLocalObject;

public interface LogonEJBLocal extends EJBLocalObject {
  HashMap logon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
}
