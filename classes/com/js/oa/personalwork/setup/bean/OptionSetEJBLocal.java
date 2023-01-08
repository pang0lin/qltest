package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OptionSetPO;
import javax.ejb.EJBLocalObject;

public interface OptionSetEJBLocal extends EJBLocalObject {
  OptionSetPO load(String paramString) throws Exception;
  
  String update(OptionSetPO paramOptionSetPO, String paramString) throws Exception;
}
