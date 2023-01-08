package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HortationPunishPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface HortationPunishEJBLocal extends EJBLocalObject {
  Boolean saveHortationPunish(HortationPunishPO paramHortationPunishPO) throws Exception;
  
  Boolean deleteHortationPunish(String paramString) throws Exception;
  
  HortationPunishPO getSingleHortationPunish(Long paramLong) throws Exception;
  
  Boolean updateHortationPunish(HortationPunishPO paramHortationPunishPO, Long paramLong) throws Exception;
  
  List getHortationPunishList(String paramString) throws Exception;
}
