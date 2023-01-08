package com.js.oa.message.bean;

import com.js.oa.message.po.MsHistoryPO;
import javax.ejb.EJBLocalObject;

public interface MsHistoryEJBLocal extends EJBLocalObject {
  Boolean saveMsHistory(MsHistoryPO paramMsHistoryPO) throws Exception;
}
