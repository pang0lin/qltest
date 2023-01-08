package com.js.system.menu.bean;

import com.js.system.menu.po.MenuSetPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface MenuEJB extends EJBObject {
  Map loadMenu(Long paramLong) throws Exception, RemoteException;
  
  List getMenuList(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getSubMenuList(Long paramLong, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getUserTopMenu(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer update(MenuSetPO paramMenuSetPO, Long paramLong, String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getTopMenu(String paramString) throws Exception, RemoteException;
  
  Integer add(MenuSetPO paramMenuSetPO, String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer delete(Long paramLong) throws Exception, RemoteException;
  
  List getDeskTop1(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getDeskTop2(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getMenuList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getAllUserTopMenu(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
}
