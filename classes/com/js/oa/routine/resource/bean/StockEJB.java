package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.StockPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface StockEJB extends EJBObject {
  Boolean save(StockPO paramStockPO) throws Exception, RemoteException;
  
  List getStockIDName(String paramString) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  String[] getSingleStock(String paramString) throws Exception, RemoteException;
  
  Boolean update(StockPO paramStockPO) throws Exception, RemoteException;
  
  String getVindicate(String paramString) throws Exception, RemoteException;
  
  List getUserManaStock(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getUserManaStock(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getStockIDName(String paramString1, String paramString2) throws Exception;
  
  List getWorkFlowStock(Long paramLong) throws Exception, RemoteException;
  
  List getAllStock(String paramString1, String paramString2) throws Exception, RemoteException;
}
