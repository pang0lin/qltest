package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WorkAddressEJBLocal extends EJBLocalObject {
  Boolean save(WorkAddressPO paramWorkAddressPO) throws Exception;
  
  WorkAddressPO load(Long paramLong) throws Exception;
  
  Boolean update(WorkAddressPO paramWorkAddressPO, Long paramLong) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  List list(Long paramLong) throws Exception;
}
