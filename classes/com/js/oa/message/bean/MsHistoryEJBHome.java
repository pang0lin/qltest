package com.js.oa.message.bean;

import javax.ejb.EJBHome;

public interface MsHistoryEJBHome extends EJBHome {
  Boolean saveMsHistory(String paramString1, String paramString2, String paramString3, String paramString4);
}
