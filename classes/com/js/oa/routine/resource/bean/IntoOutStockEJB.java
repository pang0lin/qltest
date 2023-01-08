package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.CsMasterPO;
import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.po.SsMasterPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface IntoOutStockEJB extends EJBObject {
  Map saveIntoStock(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Map getIntoStock(String paramString) throws Exception, RemoteException;
  
  Boolean updateIntoStock(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Boolean unCheckIntoStock(String paramString) throws Exception, RemoteException;
  
  Boolean deleteIntoStock(String paramString) throws Exception, RemoteException;
  
  Long saveOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Map getOutStock(String paramString) throws Exception, RemoteException;
  
  Boolean updateOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Boolean uncheckOutStock(String paramString) throws Exception, RemoteException;
  
  Boolean deleteOutStock(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getStockGoods(String paramString) throws Exception, RemoteException;
  
  Boolean saveStockCheck(CsMasterPO paramCsMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Map getStockCheck(String paramString) throws Exception, RemoteException;
  
  Boolean updateStockCheck(CsMasterPO paramCsMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Boolean uncheckStockCheck(String paramString) throws Exception, RemoteException;
  
  Boolean deleteStockCheck(String paramString) throws Exception, RemoteException;
  
  String getGoodsAmount(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getWFProcessInfoByStockId(String paramString) throws Exception, RemoteException;
  
  String getStockCharger(String paramString) throws HibernateException, RemoteException;
  
  Long saveDirectOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Integer updateOutStockCheck(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map updateOutFlag(String paramString) throws Exception, RemoteException;
  
  Boolean updateIntoStockMoney(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  Integer updateIntoStockCheck(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getWFProcessInfoByStockId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean updateOutStockAmnout(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Boolean updateBackOutStockAmnout(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception;
}
