package com.js.oa.personalwork.netaddress.bean;

import com.js.oa.personalwork.netaddress.po.AddressPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface AddressEJBLocal extends EJBLocalObject {
  void delBatch(String paramString1, String paramString2) throws Exception;
  
  void delShare(String paramString1, String paramString2) throws Exception;
  
  void delAll(String paramString) throws Exception;
  
  void add(AddressPO paramAddressPO, String paramString1, String paramString2, String paramString3, Byte paramByte, String paramString4) throws Exception;
  
  AddressPO load(String paramString) throws Exception;
  
  void update(Byte paramByte, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  List see(String paramString) throws Exception;
  
  String showDesktop(String paramString1, String paramString2, String paramString3) throws Exception;
}
