package com.js.system.menu.service;

import com.js.system.menu.bean.MenuEJBBean;
import com.js.system.menu.bean.MenuEJBHome;
import com.js.system.menu.po.MenuSetPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class MenuBD {
  private static Logger logger = Logger.getLogger(MenuBD.class);
  
  public int add(MenuSetPO po, String menuViewId, String menuView, String domainId) {
    int result = 2;
    ParameterGenerator pg = new ParameterGenerator(4);
    pg.put(po, MenuSetPO.class);
    pg.put(menuViewId, "String");
    pg.put(menuView, "String");
    pg.put(domainId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      result = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return result;
  }
  
  public int update(MenuSetPO modiPO, Long menuId, String menuViewId, String menuView, String domainId) {
    int result = 2;
    ParameterGenerator pg = new ParameterGenerator(5);
    pg.put(modiPO, MenuSetPO.class);
    pg.put(menuId, Long.class);
    pg.put(menuViewId, "String");
    pg.put(menuView, "String");
    pg.put(domainId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      result = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return result;
  }
  
  public int delete(Long delId) {
    int result = 2;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(delId, Long.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return result;
  }
  
  public Map loadMenu(Long menuId) {
    Map po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(menuId, Long.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      po = (Map)ejbProxy.invoke("loadMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return po;
  }
  
  public List getMenuList(String userId, String orgIdString) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getMenuList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getSubMenuList(Long parentId, String userId, String orgIdString) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(parentId, Long.class);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getSubMenuList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserTopMenu(String userId, String orgIdString, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getUserTopMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getTopMenu(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getTopMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getDeskTop1(String userId, String orgIdString) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getDeskTop1", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getDeskTop2(String userId, String orgIdString) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getDeskTop2", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getMenuList(String userId, String orgIdString, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getMenuList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not update menu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllUserTopMenu(String userId, String orgIdString, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(userId, String.class);
    pg.put(orgIdString, String.class);
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("MenuEJB", "MenuEJBLocal", MenuEJBHome.class);
      list = (List)ejbProxy.invoke("getAllUserTopMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not getAllUserTopMenu info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllTopMenu() throws HibernateException {
    List list = null;
    MenuEJBBean menuEJBBean = new MenuEJBBean();
    list = menuEJBBean.getAllTopMenu();
    return list;
  }
  
  public void setTotMenu(String id, String inuse, String userOrgGroup, String classUserName, String menuName, String menuOrder) throws HibernateException, SQLException {
    MenuEJBBean menuEJBBean = new MenuEJBBean();
    menuEJBBean.setTotMenu(id, inuse, userOrgGroup, classUserName, menuName, menuOrder);
  }
  
  public boolean audit(MenuSetPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean re = false;
    MenuEJBBean menuEJBBean = new MenuEJBBean();
    re = menuEJBBean.audit(po, opreate, id, httpServletRequest);
    return re;
  }
  
  public boolean autoAudit(MenuSetPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean re = false;
    MenuEJBBean menuEJBBean = new MenuEJBBean();
    re = menuEJBBean.autoAudit(po, opreate, id, httpServletRequest);
    return re;
  }
  
  public void setTotMenu1(String ids, String inuse) throws HibernateException, SQLException {
    MenuEJBBean menuEJBBean = new MenuEJBBean();
    menuEJBBean.setTotMenu1(ids, inuse);
  }
}
