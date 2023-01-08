package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface GoodsEJB extends EJBObject {
  Boolean save(GoodsPO paramGoodsPO, String paramString) throws Exception, RemoteException;
  
  String[] getSingleGoods(String paramString) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  Boolean update(GoodsPO paramGoodsPO, String paramString) throws Exception, RemoteException;
  
  String getVindicate(String paramString) throws Exception, RemoteException;
}
