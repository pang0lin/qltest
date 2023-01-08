package com.js.oa.module.service;

import com.js.oa.module.bean.ModuleMenuAllEJBHome;
import com.js.oa.module.po.SystemMenuPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;

public class ModuleMenuAllService {
  private static Logger logger = Logger.getLogger(ModuleMenuService.class
      .getName());
  
  private List oriMenuList = null;
  
  public ModuleMenuAllService() {}
  
  public ModuleMenuAllService(boolean loadFlag) {
    try {
      if (loadFlag) {
        this.oriMenuList = new ArrayList();
        loadOriginalMenusetConfigeration(this.oriMenuList);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public Long saveTopMenu(SystemMenuPO po) {
    Long retFlg = new Long(0L);
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, SystemMenuPO.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      retFlg = (Long)ejbProxy.invoke("saveOriginalMenuSet", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to saveTopMenu information :" + e.getMessage());
      logger.error("*************************************");
    } finally {}
    return retFlg;
  }
  
  public SystemMenuPO loadMenuSet(String menuId) {
    SystemMenuPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      po = (SystemMenuPO)ejbProxy.invoke("loadMneuSet", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to loadMenuSet information :" + e.getMessage());
      logger.error("*************************************");
    } finally {}
    return po;
  }
  
  public List getAllMenuSet(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      list = (List)ejbProxy.invoke("getAllMenuSet", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to loadMenuSet information :" + e.getMessage());
      logger.error("*************************************");
    } finally {}
    return list;
  }
  
  public boolean updateMenuSet(SystemMenuPO po) {
    boolean retFlg = false;
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, SystemMenuPO.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      retFlg = ((Boolean)ejbProxy.invoke("updateOriginalMenuSet", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to updateMenuSet information :" + e.getMessage());
      logger.error("*************************************");
    } finally {}
    return retFlg;
  }
  
  public boolean delBatchMenuSet(String domainId, String ids) {
    boolean flag = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put((ids.lastIndexOf(",") > 1) ? 
          ids.substring(0, ids.lastIndexOf(",")) : ids, 
          "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      flag = ((Boolean)ejbProxy.invoke("delBatchMenuSet", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to delBatchMenuSet information :" + 
          e.getMessage());
      logger.error("*************************************");
      throw e;
    } finally {}
    return flag;
  }
  
  public List loadOriMenuSetByMenuCode(String menuCode, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuCode, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      list = (List)ejbProxy.invoke("loadMneuSetByCode", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to loadOriMenuSetByMenuCode information :" + e.getMessage());
      logger.error("*************************************");
    } finally {}
    return list;
  }
  
  public boolean delAllCustomizeMenuSet(String domainId) {
    boolean flag = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuAllEJB", 
          "ModuleMenuAllEJBLocal", ModuleMenuAllEJBHome.class);
      flag = ((Boolean)ejbProxy.invoke("delAllCustomizeMenuSet", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("*************************************");
      logger.error("error to delAllCustomizeMenuSet information :" + 
          e.getMessage());
      logger.error("*************************************");
    } finally {}
    return flag;
  }
  
  private void loadOriginalMenusetConfigeration(List oriMenuList) throws FileNotFoundException, IOException, JDOMException {}
}
