package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OfficalDictionPO;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface OfficalDictionEJBLocal extends EJBLocalObject {
  Vector list(Long paramLong, Integer paramInteger) throws Exception;
  
  String add(OfficalDictionPO paramOfficalDictionPO, Long paramLong, String paramString) throws Exception;
  
  String update(String paramString1, Byte paramByte, Long paramLong1, Long paramLong2, String paramString2) throws Exception;
  
  void delAll(Long paramLong) throws Exception;
  
  void delBatch(String paramString) throws Exception;
  
  OfficalDictionPO load(Long paramLong) throws Exception;
}
