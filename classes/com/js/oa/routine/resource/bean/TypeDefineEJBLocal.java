package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.TypeDefinePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface TypeDefineEJBLocal extends EJBLocalObject {
  String save(TypeDefinePO paramTypeDefinePO) throws Exception;
  
  TypeDefinePO load(Long paramLong) throws Exception;
  
  Boolean update(TypeDefinePO paramTypeDefinePO, Long paramLong) throws Exception;
  
  List list(String paramString, Long paramLong) throws Exception;
  
  Boolean delete(Long paramLong) throws Exception;
}
