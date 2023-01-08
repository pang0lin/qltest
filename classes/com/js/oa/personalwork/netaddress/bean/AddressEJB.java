package com.js.oa.personalwork.netaddress.bean;

import com.js.oa.personalwork.netaddress.po.AddressPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface AddressEJB extends EJBObject {
  void delBatch(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delShare(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  void add(AddressPO paramAddressPO, String paramString1, String paramString2, String paramString3, Byte paramByte, String paramString4) throws Exception, RemoteException;
  
  AddressPO load(String paramString) throws Exception, RemoteException;
  
  void update(Byte paramByte, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11) throws Exception, RemoteException;
  
  List see(String paramString) throws Exception, RemoteException;
  
  String showDesktop(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getFormelseParam(String paramString) throws Exception;
  
  String getFormelseAllParam(String paramString) throws Exception;
  
  String getElseParam(String paramString1, String paramString2) throws Exception;
}
