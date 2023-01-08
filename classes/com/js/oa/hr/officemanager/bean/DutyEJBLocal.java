package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface DutyEJBLocal extends EJBLocalObject {
  Integer add(DutyPO paramDutyPO) throws Exception;
  
  Boolean del(String[] paramArrayOfString) throws Exception;
  
  List getDuteList(String paramString) throws Exception;
}
