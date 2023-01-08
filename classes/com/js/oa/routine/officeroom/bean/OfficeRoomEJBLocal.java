package com.js.oa.routine.officeroom.bean;

import com.js.oa.routine.officeroom.po.OfficeBuildPO;
import com.js.oa.routine.officeroom.po.OfficePO;
import com.js.oa.routine.officeroom.po.OfficeUsePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface OfficeRoomEJBLocal extends EJBLocalObject {
  Boolean save(OfficeBuildPO paramOfficeBuildPO) throws Exception;
  
  OfficeBuildPO load(Long paramLong) throws Exception;
  
  Boolean update(OfficeBuildPO paramOfficeBuildPO, Long paramLong) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
  
  Boolean officeSave(OfficePO paramOfficePO) throws Exception;
  
  OfficePO officeLoad(Long paramLong) throws Exception;
  
  Boolean officeUpdate(OfficePO paramOfficePO, Long paramLong) throws Exception;
  
  Boolean officeDelete(Long paramLong) throws Exception;
  
  List list(String paramString, Long paramLong) throws Exception;
  
  Boolean officeUseSave(OfficeUsePO paramOfficeUsePO) throws Exception;
  
  OfficeUsePO officeUseLoad(Long paramLong) throws Exception;
  
  Boolean officeUseUpdate(OfficeUsePO paramOfficeUsePO, Long paramLong) throws Exception;
  
  Boolean officeUseDelete(Long paramLong) throws Exception;
}
