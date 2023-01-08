package com.js.system.menu.bean;

import com.js.system.menu.po.MenuSetPO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface MenuEJBLocal extends EJBLocalObject {
  Map loadMenu(Long paramLong) throws Exception;
  
  List getMenuList(String paramString1, String paramString2) throws Exception;
  
  List getSubMenuList(Long paramLong, String paramString1, String paramString2) throws Exception;
  
  List getUserTopMenu(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer update(MenuSetPO paramMenuSetPO, Long paramLong, String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getTopMenu(String paramString) throws Exception;
  
  Integer add(MenuSetPO paramMenuSetPO, String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer delete(Long paramLong) throws Exception;
  
  List getDeskTop1(String paramString1, String paramString2) throws Exception;
  
  List getDeskTop2(String paramString1, String paramString2) throws Exception;
  
  List getMenuList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getAllUserTopMenu(String paramString1, String paramString2, String paramString3) throws Exception;
}
