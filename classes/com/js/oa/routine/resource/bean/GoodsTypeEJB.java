package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsTypePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GoodsTypeEJB extends EJBObject {
  Boolean save(GoodsTypePO paramGoodsTypePO, String paramString) throws Exception, RemoteException;
  
  String[] getSingleGoodsType(String paramString) throws Exception, RemoteException;
  
  Boolean update(GoodsTypePO paramGoodsTypePO, String paramString) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  String getVindicate(String paramString) throws Exception, RemoteException;
  
  List getUserManaGoodsType(String paramString) throws Exception, RemoteException;
  
  List getUserManaGoodsTypeParent(String paramString) throws Exception, RemoteException;
  
  List getUserManaGoodsTypeSub(String paramString1, String paramString2) throws Exception, RemoteException;
}
