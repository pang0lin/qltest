package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PersonalKindPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface PersonalKindEJBLocal extends EJBLocalObject {
  Boolean save(PersonalKindPO paramPersonalKindPO) throws Exception;
  
  PersonalKindPO load(Long paramLong) throws Exception;
  
  Boolean update(PersonalKindPO paramPersonalKindPO, Long paramLong) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  List list() throws Exception;
  
  Boolean checkExistKind(Long paramLong, String paramString) throws Exception;
}
