package com.js.oa.module.service;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.module.bean.ModuleMenuEJBBean;
import com.js.oa.module.bean.ModuleMenuEJBHome;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.po.ModuleSEQPO;
import com.js.oa.module.util.CommPagesGeneration;
import com.js.oa.module.vo.ListItem;
import com.js.oa.module.vo.MenuItem;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class ModuleMenuService {
  private Map bikes = null;
  
  private static Logger logger = Logger.getLogger(ModuleMenuService.class
      .getName());
  
  public ModuleMenuService() {
    this.bikes = new HashMap<Object, Object>();
    this.bikes.put("2000", new String[] { "2000:T1", "2000:T2" });
    this.bikes.put("2001", new String[] { "2001:A1", "2001:A2" });
    this.bikes.put("2002", new String[] { "2002:BW1", "2002:BW2", "2002:BW" });
    this.bikes.put("2003", new String[] { "2003:S320" });
    this.bikes.put("2004", new String[] { "2004:TA1", "2004:TA2", "2004:TA3" });
  }
  
  public List getMenuListByTopID(String menuId, String where) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuId, String.class);
      pg.put(where, String.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (List)ejbProxy.invoke("getMenuListByTopID", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listMenuConfigs information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List listMenuConfigs(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (List)ejbProxy.invoke("listMenuConfigs", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listMenuConfigs information :" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public Long saveMenuConfig(ModuleMenuPO po) {
    Long retFlg = null;
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, ModuleMenuPO.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (Long)ejbProxy.invoke("saveMenuConfig", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retFlg;
  }
  
  public boolean audit(ModuleMenuPO po, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    ModuleMenuEJBBean bean = new ModuleMenuEJBBean();
    try {
      result = bean.audit(po, opreate, id, httpServletRequest);
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean autoAudit(ModuleMenuPO po, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    ModuleMenuEJBBean bean = new ModuleMenuEJBBean();
    try {
      result = bean.autoAudit(po, opreate, id, httpServletRequest);
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Boolean addMenuRights(List list) {
    Boolean retFlg = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(list, List.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (Boolean)ejbProxy.invoke("addMenuListRight", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retFlg;
  }
  
  public Boolean delMenuRights(String id) {
    Boolean retFlg = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (Boolean)ejbProxy.invoke("deleteMenuRight", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retFlg;
  }
  
  public ModuleMenuPO loadParentMenuConfiger(String domainId, String pMenuId) {
    ModuleMenuPO po = null;
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(pMenuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      po = ConvertToPo((List)ejbProxy.invoke("loadParentMenuConfiger", 
            pg.getParameters()));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public boolean updateMenuConfig(ModuleMenuPO po) {
    boolean retFlg = false;
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, ModuleMenuPO.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = ((Boolean)ejbProxy.invoke("updateCustomerMenu", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retFlg;
  }
  
  public List getAllCustomMenu(String domainId, String menuId, boolean converFlag, boolean transFlag, String show) {
    List retList = null;
    List custList = null;
    List oriList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      pg.put(show, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      if (converFlag) {
        retList = convertToShow((List)ejbProxy.invoke(
              "getAllCustomMenu", 
              pg.getParameters()), true, false, transFlag);
      } else {
        retList = (List)ejbProxy.invoke("getAllCustomMenu", 
            pg.getParameters());
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retList;
  }
  
  public List getAllCustomMenuList(String domainId, String menuId, boolean converFlag, boolean transFlag, String show) {
    List retList = null;
    List custList = null;
    List oriList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      pg.put(show, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      if (converFlag) {
        retList = convertToShow((List)ejbProxy.invoke(
              "getAllCustomMenu", 
              pg.getParameters()), true, false, transFlag);
      } else {
        retList = (List)ejbProxy.invoke("getAllCustomMenu", 
            pg.getParameters());
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retList;
  }
  
  public List getAllGroupsByUserId(String userId, String domainId) {
    List retList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retList = (List)ejbProxy.invoke("getAllGroupsByUserId", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return retList;
  }
  
  public List getAllCustMenuAndSetInOnce(String domainId) {
    List retList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retList = (List)ejbProxy.invoke("getAllCustomMenu", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllCustomMenu information :" + e.getMessage());
    } finally {}
    return retList;
  }
  
  private void converListToListItems(List<ListItem> retList, List<ListItem> oriList, List<ListItem> custList) {
    if (oriList != null)
      for (int i = 0; i < oriList.size(); i++) {
        ListItem pPo = oriList.get(i);
        retList.add(pPo);
        for (int s = 0; s < custList.size(); s++) {
          ListItem sPo = custList.get(s);
          if (pPo.getId().equals(sPo.getField2().substring(0, 
                sPo.getField2().indexOf("-"))))
            retList.add(sPo); 
        } 
      }  
  }
  
  private void notConvertList(List<Object[]> retList, List<Object[]> oriList, List<Object[]> custList) {
    try {
      if (oriList != null)
        for (int i = 0; i < oriList.size(); i++) {
          Object[] pPo = oriList.get(i);
          for (int s = 0; s < custList.size(); s++) {
            Object[] sPo = custList.get(s);
            if (pPo[0].toString().equals(sPo[3].toString()
                .substring(0, 
                  sPo[3].toString().indexOf("-"))))
              retList.add(sPo); 
          } 
        }  
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public List getAllSubMenus(String domainId, String menuId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = convertToShow((List)ejbProxy.invoke("getAllSubMenus", 
            pg.getParameters()), false, true, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllSubMenus information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllSubMenusByLevel(String menuId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (List)ejbProxy.invoke("getAllSubMenus", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllSubMenus(1) information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllSubMenusForOrder(String domainId, String menuId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = convertToShow((List)ejbProxy.invoke("getAllSubMenus", 
            pg.getParameters()), false, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllSubMenus information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getSubMenuHtml(String menuSetId, String domainId) {
    String htmlStr = "";
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuSetId, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (List)ejbProxy.invoke("getAllSubMenusBySetId", 
          pg.getParameters());
      CommPagesGeneration commGener = new CommPagesGeneration();
      htmlStr = commGener.generHtmlTable(list);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllSubMenus information :" + e.getMessage());
    } finally {}
    return htmlStr;
  }
  
  public List getAllWFProcessPackage(String userId, String orgId, String domainId, String moduleId) {
    List<ListItem> list = new ArrayList();
    try {
      ProcessBD prBd = new ProcessBD();
      List<Object[]> tmpList = prBd.getUserProcess(userId, orgId, moduleId);
      if (tmpList != null) {
        String upPackageId = "";
        for (int i = 0; i < tmpList.size(); i++) {
          Object[] processObj = tmpList.get(i);
          if (!upPackageId.equals(processObj[0].toString())) {
            upPackageId = processObj[0].toString();
            ListItem item = new ListItem();
            item.setId(processObj[0].toString());
            item.setName(processObj[1].toString());
            list.add(item);
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllWFProcessPackage information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllWFProcessUnderPackageID(String userId, String orgId, String domainId, String moduleId, String packageId) {
    List<ListItem> list = new ArrayList();
    try {
      ProcessBD prBd = new ProcessBD();
      List<Object[]> tmpList = prBd.getUserProcess(userId, orgId, moduleId);
      if (tmpList != null)
        for (int i = 0; i < tmpList.size(); i++) {
          Object[] processObj = tmpList.get(i);
          if (packageId.equals(processObj[0].toString())) {
            ListItem item = new ListItem();
            item.setId(processObj[2].toString());
            item.setName(processObj[3].toString());
            list.add(item);
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllWFProcessUnderPackageID information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllWFProcesses(String userId, String orgId, String domainId, String moduleId) {
    List<ListItem> list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      List<Object[]> tmpList = (List)ejbProxy.invoke("getWFProcesses", 
          pg.getParameters());
      if (tmpList != null) {
        ListItem item = new ListItem();
        item.setId("-1");
        item.setName("--请选择--");
        list.add(item);
        for (int i = 0; i < tmpList.size(); i++) {
          Object[] processObj = tmpList.get(i);
          item = new ListItem();
          item.setId(
              String.valueOf((processObj[2] != null) ? processObj[2].toString() : "") + "$" + (
              (processObj[4] != null) ? processObj[4].toString() : 
              "") + 
              "$" + (
              (processObj[3] != null) ? processObj[3].toString() : 
              "") + 
              "$" + (
              (processObj[5] != null) ? processObj[5].toString() : 
              "") + 
              "$" + (
              (processObj[6] != null) ? processObj[6].toString() : 
              ""));
          item.setName(processObj[3].toString());
          list.add(item);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllWFProcesses information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getQueryFormByTblId(String domainId, String tblId) {
    List list = new ArrayList();
    try {
      CustomFormBD dbBD = new CustomFormBD();
      list = convertStrArrToShow(dbBD.getQueryMainFormByTblId(domainId, tblId), 
          2, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryFormByTblId information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllCustTables(String domainId) {
    List list = null;
    try {
      CustomDatabaseBD dbBD = new CustomDatabaseBD();
      list = convertStrArrToShow(dbBD.getTableInfo(domainId), 2, true, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllCustTables information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getTableFieldsById(String domainId, String tblId) {
    List list = null;
    try {
      CustomDatabaseBD dbBD = new CustomDatabaseBD();
      list = convertStrArrToShow(dbBD.getFieldInfo(tblId, domainId), 0, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllCustTableSub information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getTableFieldsByIdForRemind(String domainId, String tblId) {
    List list = null;
    try {
      CustomDatabaseBD dbBD = new CustomDatabaseBD();
      list = convertStrArrToShow(dbBD.getFieldInfo(tblId, domainId), 0, true, true);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllCustTableSub information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getTableFieldsByIdCont(String domainId, String tblId) {
    List list = null;
    try {
      CustomDatabaseBD dbBD = new CustomDatabaseBD();
      list = convertStrArrToShow(dbBD.getFieldInfo(tblId, domainId), 0, true, true);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllCustTableSub information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List loadMenuConfig(String domainId, String menuId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (List)ejbProxy.invoke("loadMenuConfig", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to loadMenuConfig information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSearchConfigs(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = convertStrArrToShow((String[][])ejbProxy.invoke(
            "getSearchConfigs", 
            pg.getParameters()), 0, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getSearchConfigs information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getQueryFields(String domainId, String menuSearchBound) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuSearchBound, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String[][])ejbProxy.invoke("getQueryFields", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryFields information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getTableFields(String domainId, String tblId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(tblId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String[][])ejbProxy.invoke("getTableFields", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableFields information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSubTableDatas(String domainId, String tblId, Integer clomns) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, "String");
      pg.put(tblId, "String");
      pg.put(clomns, "Integer");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String[][])ejbProxy.invoke("getSubTableDatas", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getSubTableDatas information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean delAllCustmizeMenus(String domainId) {
    boolean flag = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      flag = ((Boolean)ejbProxy.invoke("delAllCustmizeMenus", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delAllCustmizeMenus information :" + e.getMessage());
    } finally {}
    return flag;
  }
  
  public boolean delBatchCustmizeMenus(String domainId, String menuIds) {
    boolean flag = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put((menuIds.lastIndexOf(",") > 1) ? 
          menuIds.substring(0, menuIds.lastIndexOf(",")) : menuIds, 
          "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      flag = ((Boolean)ejbProxy.invoke("delBatchCustmizeMenus", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delBatchCustmizeMenus information :" + e.getMessage());
      throw e;
    } finally {}
    return flag;
  }
  
  public boolean deleteMenuRights(String menuId) {
    boolean flag = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      flag = ((Boolean)ejbProxy.invoke("deleteMenuRight", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to deleteMenuRight information :" + e.getMessage());
      throw e;
    } finally {}
    return flag;
  }
  
  public String[][] getFieldsTypes(String fieldsTrain) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldsTrain, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String[][])ejbProxy.invoke("getFieldsTypes", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryFields information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getMenuMaxCount(String domainId, String menuBelone) {
    String max = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuBelone, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      Object obj = ejbProxy.invoke("getMaxMenuCount", 
          pg.getParameters());
      max = (obj != null) ? obj.toString() : null;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuMaxCount information :" + e.getMessage());
    } finally {}
    return max;
  }
  
  public boolean updateRootLevel(String domainId) {
    boolean retFlg = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = ((Boolean)ejbProxy.invoke("updateRootLevel", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuMaxCount information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public boolean deleteBizDatas(String tbl, String recordId) {
    boolean retFlg = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tbl, "String");
      pg.put(recordId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = ((Boolean)ejbProxy.invoke("deleteBizDatas", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to deleteBizDatas information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public boolean deleteAllMasterAndSub(String tbl) {
    boolean retFlg = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tbl, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = ((Boolean)ejbProxy.invoke("deleteAllMasterAndSub", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to deleteAllMasterAndSub information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public String getSearchHtmlPart(String domainId, String menuId) {
    String retFlg = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (String)ejbProxy.invoke("getSearchHtmlPart", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getSearchHtmlPart information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public String[][] getBizTableJoins(String masterTable, String subTable) {
    String[][] retFlg = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(masterTable, "String");
      pg.put(subTable, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (String[][])ejbProxy.invoke("getBizTableJoins", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getBizTableJoins information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public String[][] getDefaultLoadDatas(String searchSql, int clomns, String paras) {
    String[][] retFlg = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(searchSql, "String");
      pg.put(new Integer(clomns), "Integer");
      pg.put(paras, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (String[][])ejbProxy.invoke("getDefaultLoadDatas", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getDefaultLoadDatas information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  public String getDefaultLoadDatasCount(String tblId, String condition) {
    String retFlg = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tblId, "String");
      pg.put(condition, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retFlg = (String)ejbProxy.invoke("getDefaultLoadDatasCount", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getDefaultLoadDatasCount information :" + e.getMessage());
    } finally {}
    return retFlg;
  }
  
  private Object tackoutSingleValue(List list, int row, int offset) {
    if (list != null && list.size() > 0)
      return list.get(row); 
    return null;
  }
  
  private ModuleMenuPO ConvertToPo(List<Object[]> list) {
    if (list != null && list.size() > 0) {
      ModuleMenuPO po = new ModuleMenuPO();
      Object[] obj = list.get(0);
      po.setId(Long.valueOf(obj[0].toString()));
      po.setMenuBlone(Long.valueOf(obj[1].toString()));
      po.setMenuLevel(obj[2].toString());
      po.setMenuCount(Integer.valueOf(obj[3].toString()).intValue());
      if (obj[4] != null && obj[4].toString().length() > 0)
        po.setMenuMaintenanceSubTableMap(Long.valueOf(obj[4].toString())); 
      po.setParentOrder(Long.valueOf(obj[5].toString()));
      return po;
    } 
    return null;
  }
  
  public List convertStrArrToShow(String[][] tables, int offset, boolean flag, boolean fieldFlag) {
    ArrayList<ListItem> retList = new ArrayList();
    if (tables != null && tables.length > 0)
      for (int i = 0; i < tables.length; i++) {
        ListItem item = new ListItem();
        if (fieldFlag) {
          item.setId(tables[i][3]);
        } else {
          item.setId(tables[i][0]);
        } 
        item.setName(tables[i][1]);
        if (flag)
          item.setField1(tables[i][offset]); 
        retList.add(item);
      }  
    return retList;
  }
  
  public List convertToShow(List<Object[]> list, boolean spaceFlg, boolean filterFlg, boolean transFlag) {
    ArrayList<ListItem> retList = new ArrayList();
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] subRow = list.get(i);
        if (!filterFlg || subRow[3].toString().indexOf("-") > 0)
          if (subRow.length <= 23 || !subRow[23].toString().equals("9") || subRow[3].toString().indexOf("-") == subRow[3].toString().lastIndexOf("-")) {
            ListItem item = new ListItem();
            item.setId(subRow[0].toString());
            if (spaceFlg) {
              item.setName(String.valueOf(appendSpace(subRow, transFlag)) + 
                  subRow[1].toString());
            } else {
              item.setName(subRow[1].toString());
            } 
            item.setField1(subRow[2].toString());
            item.setField2(subRow[3].toString());
            if (subRow.length > 4)
              item.setField3((subRow[4] != null) ? subRow[4].toString() : 
                  ""); 
            retList.add(item);
          }  
      }  
    return retList;
  }
  
  private String appendSpace(Object[] subRow, boolean transFlag) {
    String space = "";
    if (subRow != null) {
      int level = 0;
      space = subRow[3].toString();
      int i;
      for (i = 0; i < space.length(); i++) {
        if (space.charAt(i) == '-')
          level++; 
      } 
      if (subRow[23].toString().equals("9"))
        level--; 
      space = "";
      for (i = 0; i < level; i++) {
        if (transFlag) {
          space = String.valueOf(space) + "&nbsp;&nbsp;";
        } else {
          space = String.valueOf(space) + "    ";
        } 
      } 
    } 
    return space;
  }
  
  public String createScript(List<MenuItem> menuList) {
    if (menuList != null) {
      StringBuffer bf = new StringBuffer(
          "<SCRIPT>menuItems = new Array();");
      for (int i = 0; i < menuList.size(); i++) {
        MenuItem item = menuList.get(i);
        bf.append("menuItems[" + i + "]=new MItem(\"" + item.getAction() + 
            "\",\"" + item.getName() + "\",\"\"," + 
            item.getParent() + 
            "," + item.getLevel() + "," + item.isIsLeaf() + ");");
      } 
      bf.append(" config = new Config(\"/jsoa/images/menuplus_b.gif\",\"/jsoa/images/menuminus_b.gif\",\"#5E8CBE\",\"#5E8CBE\",\"\",\"#FFFFCC\",\"#325579\",\"#505050\");  InitMenuItems();  </SCRIPT>");
      return bf.toString();
    } 
    return "";
  }
  
  public String createScriptForOriginal(List<MenuItem> menuList, int index) {
    if (menuList != null && menuList.size() > 0) {
      StringBuffer bf = new StringBuffer("");
      String treeRoot = "";
      int scoll = 0;
      if (menuList != null) {
        MenuItem item = menuList.get(0);
        int init = 0;
        for (int i = 0; i < menuList.size(); i++) {
          scoll++;
          item = menuList.get(i);
          if (isRootMenu(item.getMenuLevel())) {
            if (init > 0) {
              bf.append("tree" + treeRoot + 
                  ".setTarget(\"mainFrame\");");
              bf.append("document.write(tree" + treeRoot + 
                  ".toString());");
              bf.append("</script></td></tr>");
              bf.append("<%menuIndex++;%>");
            } 
            treeRoot = (new StringBuilder(String.valueOf(1000 + i))).toString();
            bf.append("<tr><td class=\"btnSubModule\" onClick=\"OpenCloseSubMenu(" + 
                
                index + ");");
            if (item.getAction().length() > 0)
              bf.append(
                  (item.getAction().indexOf("javascript") >= 0) ? (
                  
                  "menuJumpNew('" + 
                  item.getAction().substring(24, 
                    item.getAction().indexOf(",") - 
                    1) + "');") : (
                  "menuJump('" + item.getAction() + "');")); 
            bf.append("\" id=\"menuTitleBox" + index + 
                "\">" + 
                "<div class=\"\" id=\"menuTitle" + index + 
                "\">" + 
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" + 
                "<tr>" + 
                "<td nowrap>" + item.getName() + "</td>" + 
                "<td align=\"right\" width=\"16\">" + 
                "<span class=\"btnSubMenuOpen\">&nbsp;</span>" + 
                "</td>" + 
                "</tr>" + 
                "</table>" + 
                "</div></td>" + 
                "</tr>");
            if (i + 1 < menuList.size()) {
              MenuItem mItem = menuList.get(i + 1);
              if (mItem.getParent() == item.getMenuLocation()) {
                bf.append("<tr id=\"submenuBox" + index + 
                    "\" valign=\"top\" style=\"display:'none';\">" + 
                    "<td height=\"100\" class=\"btnSubModuleBox\">" + 
                    "<script language=\"JavaScript\">" + 
                    "var tree" + treeRoot + 
                    " = new MzTreeView(\"tree" + treeRoot + 
                    "\");" + 
                    "tree" + treeRoot + 
                    ".icons[\"level1\"] = \"square_blueS.gif\"; " + 
                    "tree" + treeRoot + 
                    ".setIconPath(\"\");" + 
                    "tree" + treeRoot + 
                    ".nodes[\"0_" + item.getMenuLocation() + 
                    "\"] = \"\";");
                init++;
              } else {
                init = 0;
              } 
            } 
            index++;
          } else {
            bf.append("tree" + treeRoot + ".nodes[\"" + 
                item.getParent() + "_" + 
                item.getMenuLocation() + "\"] = \"text:" + 
                item.getName() + "; icon:level1; url:");
            if (item.getAction().indexOf("javascript") < 0) {
              bf.append(String.valueOf(item.getAction()) + "\"; ");
            } else {
              String tmp = item.getAction().substring(24, 
                  item.getAction().indexOf(",") - 1);
              bf.append(String.valueOf(tmp) + "; target:_blank \";");
            } 
            if (i + 1 == menuList.size())
              bf.append(" tree" + treeRoot + 
                  ".setTarget(\"mainFrame\"); document.write(tree" + 
                  treeRoot + 
                  ".toString()); </script></td></tr>"); 
          } 
        } 
      } 
      return bf.toString();
    } 
    return "";
  }
  
  private boolean isRootMenu(String level) {
    if (level != null && level.length() > 0) {
      int length = 0;
      for (int i = 0; i < level.length(); i++) {
        if (level.charAt(i) == '-')
          length++; 
      } 
      if (level.startsWith("0")) {
        if (length == 2)
          return true; 
      } else if (length == 1) {
        return true;
      } 
    } 
    return false;
  }
  
  public List managerMenuItem(List list, String curUserId, String orgId, List menuList, HttpServletRequest request, int defMenuStart, String domainId) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 388
    //   4: aload_1
    //   5: invokeinterface size : ()I
    //   10: ifle -> 388
    //   13: iconst_0
    //   14: istore #8
    //   16: goto -> 367
    //   19: aload_1
    //   20: iload #8
    //   22: invokeinterface get : (I)Ljava/lang/Object;
    //   27: checkcast [Ljava/lang/Object;
    //   30: astore #9
    //   32: aload #9
    //   34: iconst_3
    //   35: aaload
    //   36: invokevirtual toString : ()Ljava/lang/String;
    //   39: ldc_w '-'
    //   42: invokevirtual indexOf : (Ljava/lang/String;)I
    //   45: ifge -> 51
    //   48: goto -> 364
    //   51: aload #9
    //   53: bipush #20
    //   55: aaload
    //   56: ifnull -> 74
    //   59: aload #9
    //   61: bipush #20
    //   63: aaload
    //   64: invokevirtual toString : ()Ljava/lang/String;
    //   67: aload_2
    //   68: invokevirtual indexOf : (Ljava/lang/String;)I
    //   71: ifge -> 127
    //   74: aload #9
    //   76: bipush #21
    //   78: aaload
    //   79: ifnull -> 98
    //   82: aload_0
    //   83: aload #9
    //   85: bipush #21
    //   87: aaload
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: aload_3
    //   92: invokevirtual notExistsOrg : (Ljava/lang/String;Ljava/lang/String;)Z
    //   95: ifeq -> 127
    //   98: aload #9
    //   100: bipush #22
    //   102: aaload
    //   103: ifnull -> 364
    //   106: aload_0
    //   107: aload #9
    //   109: bipush #22
    //   111: aaload
    //   112: invokevirtual toString : ()Ljava/lang/String;
    //   115: aload_2
    //   116: aload #7
    //   118: invokevirtual notExistsGroup : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   121: ifeq -> 127
    //   124: goto -> 364
    //   127: ldc_w '0'
    //   130: aload #9
    //   132: bipush #19
    //   134: aaload
    //   135: invokevirtual toString : ()Ljava/lang/String;
    //   138: invokevirtual equals : (Ljava/lang/Object;)Z
    //   141: ifeq -> 147
    //   144: goto -> 364
    //   147: iconst_0
    //   148: istore #10
    //   150: bipush #101
    //   152: istore #11
    //   154: iconst_0
    //   155: istore #12
    //   157: goto -> 183
    //   160: aload #9
    //   162: iconst_3
    //   163: aaload
    //   164: invokevirtual toString : ()Ljava/lang/String;
    //   167: iload #12
    //   169: invokevirtual charAt : (I)C
    //   172: bipush #45
    //   174: if_icmpne -> 180
    //   177: iinc #10, 1
    //   180: iinc #12, 1
    //   183: iload #12
    //   185: aload #9
    //   187: iconst_3
    //   188: aaload
    //   189: invokevirtual toString : ()Ljava/lang/String;
    //   192: invokevirtual length : ()I
    //   195: if_icmplt -> 160
    //   198: iload #10
    //   200: iconst_1
    //   201: if_icmpgt -> 269
    //   204: new com/js/oa/module/vo/MenuItem
    //   207: dup
    //   208: aload #9
    //   210: iconst_3
    //   211: aaload
    //   212: invokevirtual toString : ()Ljava/lang/String;
    //   215: iload #8
    //   217: iload #11
    //   219: iadd
    //   220: iload #6
    //   222: iadd
    //   223: new com/js/oa/module/service/ModuleMenuService
    //   226: dup
    //   227: invokespecial <init> : ()V
    //   230: aload #9
    //   232: aload #5
    //   234: invokevirtual confirmAction : ([Ljava/lang/Object;Ljavax/servlet/http/HttpServletRequest;)Ljava/lang/String;
    //   237: aload #9
    //   239: iconst_1
    //   240: aaload
    //   241: invokevirtual toString : ()Ljava/lang/String;
    //   244: ldc_w ''
    //   247: iload #6
    //   249: iconst_0
    //   250: iconst_0
    //   251: invokespecial <init> : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZ)V
    //   254: astore #12
    //   256: aload #4
    //   258: aload #12
    //   260: invokeinterface add : (Ljava/lang/Object;)Z
    //   265: pop
    //   266: goto -> 361
    //   269: aload #9
    //   271: iconst_3
    //   272: aaload
    //   273: invokevirtual toString : ()Ljava/lang/String;
    //   276: astore #12
    //   278: aload #12
    //   280: iconst_0
    //   281: aload #12
    //   283: ldc_w '-'
    //   286: invokevirtual lastIndexOf : (Ljava/lang/String;)I
    //   289: invokevirtual substring : (II)Ljava/lang/String;
    //   292: astore #13
    //   294: iconst_0
    //   295: istore #14
    //   297: iconst_0
    //   298: istore #15
    //   300: goto -> 321
    //   303: aload #12
    //   305: iload #15
    //   307: invokevirtual charAt : (I)C
    //   310: bipush #45
    //   312: if_icmpne -> 318
    //   315: iinc #14, 1
    //   318: iinc #15, 1
    //   321: iload #15
    //   323: aload #12
    //   325: invokevirtual length : ()I
    //   328: if_icmplt -> 303
    //   331: aload #4
    //   333: aload_0
    //   334: aload #12
    //   336: iload #8
    //   338: iload #14
    //   340: iconst_1
    //   341: isub
    //   342: aload #4
    //   344: aload #9
    //   346: aload #5
    //   348: iload #6
    //   350: iload #11
    //   352: invokevirtual createMenuItem : (Ljava/lang/String;IILjava/util/List;[Ljava/lang/Object;Ljavax/servlet/http/HttpServletRequest;II)Lcom/js/oa/module/vo/MenuItem;
    //   355: invokeinterface add : (Ljava/lang/Object;)Z
    //   360: pop
    //   361: iinc #11, 1
    //   364: iinc #8, 1
    //   367: iload #8
    //   369: aload_1
    //   370: invokeinterface size : ()I
    //   375: if_icmplt -> 19
    //   378: goto -> 388
    //   381: astore #8
    //   383: aload #8
    //   385: invokevirtual printStackTrace : ()V
    //   388: aload #4
    //   390: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #1433	-> 0
    //   #1434	-> 13
    //   #1435	-> 19
    //   #1436	-> 32
    //   #1437	-> 48
    //   #1440	-> 51
    //   #1442	-> 74
    //   #1443	-> 98
    //   #1444	-> 106
    //   #1445	-> 124
    //   #1451	-> 127
    //   #1452	-> 144
    //   #1457	-> 147
    //   #1458	-> 150
    //   #1459	-> 154
    //   #1460	-> 160
    //   #1461	-> 177
    //   #1459	-> 180
    //   #1465	-> 198
    //   #1467	-> 204
    //   #1468	-> 215
    //   #1469	-> 223
    //   #1470	-> 230
    //   #1469	-> 234
    //   #1471	-> 237
    //   #1472	-> 247
    //   #1467	-> 251
    //   #1466	-> 254
    //   #1473	-> 256
    //   #1475	-> 269
    //   #1477	-> 278
    //   #1478	-> 281
    //   #1477	-> 289
    //   #1480	-> 294
    //   #1482	-> 297
    //   #1483	-> 303
    //   #1484	-> 315
    //   #1482	-> 318
    //   #1489	-> 331
    //   #1490	-> 342
    //   #1491	-> 346
    //   #1489	-> 352
    //   #1493	-> 361
    //   #1434	-> 364
    //   #1496	-> 381
    //   #1497	-> 383
    //   #1499	-> 388
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	391	0	this	Lcom/js/oa/module/service/ModuleMenuService;
    //   0	391	1	list	Ljava/util/List;
    //   0	391	2	curUserId	Ljava/lang/String;
    //   0	391	3	orgId	Ljava/lang/String;
    //   0	391	4	menuList	Ljava/util/List;
    //   0	391	5	request	Ljavax/servlet/http/HttpServletRequest;
    //   0	391	6	defMenuStart	I
    //   0	391	7	domainId	Ljava/lang/String;
    //   16	362	8	i	I
    //   32	332	9	mItem	[Ljava/lang/Object;
    //   150	214	10	root	I
    //   154	210	11	subStep	I
    //   157	41	12	j	I
    //   256	13	12	menuItem	Lcom/js/oa/module/vo/MenuItem;
    //   278	83	12	level	Ljava/lang/String;
    //   294	67	13	parent	Ljava/lang/String;
    //   297	64	14	order	I
    //   300	31	15	j	I
    //   383	5	8	ex	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   0	378	381	java/lang/Exception
  }
  
  public List generateMenuItem(List<Object[]> menuList) {
    List<Object[]> list = new ArrayList();
    for (int i = 0; i < menuList.size(); i++) {
      Object[] obj = menuList.get(i);
      Object[] menuItem = new Object[4];
      String menuURL = generateMenuURL(obj);
      obj[4] = menuURL;
      list.add(obj);
    } 
    return list;
  }
  
  public boolean notExistsGroup(String scope, String userId, String domainId) {
    boolean retFlag = true;
    if (scope != null && scope.length() > 0) {
      List<E> list = getAllGroupsByUserId(userId, domainId);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          if (scope.indexOf(list.get(0).toString()) > 0) {
            retFlag = false;
            break;
          } 
        }  
    } 
    return retFlag;
  }
  
  public boolean notExistsOrg(String scope, String orgId) {
    boolean retFlag = true;
    if (scope != null && scope.length() > 0 && orgId != null) {
      StringTokenizer st = new StringTokenizer(orgId, "$");
      String id = "";
      while (st.hasMoreTokens()) {
        id = st.nextToken();
        if (scope.indexOf(id) > 0) {
          retFlag = false;
          break;
        } 
      } 
    } 
    return retFlag;
  }
  
  public String[][] getUserTopMenuOpenType(String menuId, String domainId) throws SQLException, Exception {
    if (menuId != null && domainId != null) {
      DbOpt opt = new DbOpt();
      String[][] strTemp = opt.executeQueryToStrArr2(
          " select menu_opertype, MENU_ACTIONPARAMS1,MENU_ACTIONPARAMS2,MENU_ACTIONPARAMS3,MENU_ACTIONPARAMS4,MENU_ACTIONPARAMS4VALUE from MENU_EXT where menu_blone = " + 
          menuId + " and menu_domainid = " + domainId, 6);
      opt.close();
      return strTemp;
    } 
    return new String[1][];
  }
  
  public MenuItem createMenuItem(String parent, int location, int level, List<MenuItem> menuList, Object[] mItem, HttpServletRequest request, int defMenuStart, int subStep) {
    MenuItem menuItem = null;
    if (parent != null && parent.length() > 0 && 
      menuList != null && mItem != null) {
      int pOrder = -1;
      for (int i = 0; i < menuList.size(); i++) {
        MenuItem item = menuList.get(i);
        if (parent.substring(0, 
            parent.lastIndexOf("-")).equals(item
            .getMenuLevel())) {
          pOrder = item.getMenuLocation();
          item.setIsLeaf(false);
        } 
      } 
      menuItem = new MenuItem(mItem[3].toString(), 
          location + defMenuStart + subStep, 
          confirmAction(mItem, request), 
          mItem[1].toString(), 
          "", pOrder, level, true);
    } 
    return menuItem;
  }
  
  public String confirmAction(Object[] mItem, HttpServletRequest request) {
    String action = "";
    if (mItem != null && mItem.length > 0) {
      if (mItem[4] != null && mItem[4].toString().length() > 0) {
        String compUrl = 
          "/jsoa/module/jump_link.jsp" + 
          concatActionUrl(mItem);
        action = "0".equals(mItem[18].toString()) ? 
          compUrl : (
          "javascript:window.open('" + 
          compUrl + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } 
      if (mItem[10] != null && mItem[10].toString().length() > 0 && 
        !"0".equals(mItem[10].toString()))
        action = "0".equals(mItem[18].toString()) ? (
          "/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          mItem[0].toString()) : (
          "javascript:window.open('/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          mItem[0].toString() + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');"); 
      if (mItem[10] != null && mItem[10].toString().length() <= 0 && 
        !"0".equals(mItem[10].toString()) && 
        mItem[11] != null && mItem[11].toString().length() > 0 && 
        !"0".equals(mItem[11].toString()))
        action = "0".equals(mItem[18].toString()) ? (
          "/jsoa/ModuleDealwithAction.do?action=maintenance?tblid=" + 
          mItem[17].toString()) : (
          "javascript:window.open('/jsoa/ModuleDealwithAction.do?action=maintenance?tblid=" + 
          mItem[17].toString() + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');"); 
      if (mItem[12] != null && mItem[12].toString().length() > 0 && 
        !"-1".equals(mItem[12].toString())) {
        String[] values = new String[5];
        int m = 0;
        StringTokenizer tk = new StringTokenizer(mItem[12].toString(), 
            "$");
        while (tk.hasMoreTokens()) {
          values[m] = tk.nextToken();
          m++;
        } 
        String path = 
          "/jsoa/JsFlowAddAction.do?action=add&processId=" + 
          values[0] + 
          "&tableId=" + values[1] + "&processName=" + 
          values[2] + 
          "&processType=" + 
          values[3] + "&remindField=" + ((
          values[4] != null && values[4].length() > 0) ? 
          values[4] : "") + 
          "&moduleId=null";
        action = "javascript:window.open('" + 
          path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');";
      } 
      if (mItem[13] != null && mItem[13].toString().length() > 0) {
        String path = request.getRealPath("\\");
        String srcFive = "0000";
        if (mItem[13].toString() != null && mItem[13].toString().length() > 6 && mItem[13].toString().substring(4, 5).equals("_")) {
          srcFive = mItem[13].toString().substring(0, 4);
        } else {
          srcFive = "0000";
        } 
        path = 
          "/jsoa/upload/" + srcFive + "/module/" + 
          mItem[13].toString();
        action = "0".equals(mItem[18]
            .toString()) ? path : (
          "javascript:window.open('" + 
          path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } 
      if (mItem[14] != null && mItem[14].toString().length() > 0) {
        String path = request.getRealPath("\\");
        String src = "0000";
        if (mItem[14].toString() != null && mItem[14].toString().length() > 6 && mItem[14].toString().substring(4, 5).equals("_")) {
          src = mItem[14].toString().substring(0, 4);
        } else {
          src = "0000";
        } 
        path = "/jsoa/upload/" + src + "/module/" + 
          mItem[14].toString();
        action = "0".equals(mItem[18].toString()) ? path : (
          "javascript:window.open('" + path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } 
    } 
    return action;
  }
  
  public String generateMenuURL(Object[] mItem) {
    String action = "";
    if (mItem != null && mItem.length > 0) {
      if (mItem[4] != null && mItem[4].toString().length() > 0) {
        String compUrl = "/jsoa/module/jump_link.jsp?flag=2&menuid=" + mItem[0];
        action = "0".equals(mItem[18].toString()) ? 
          compUrl : (
          "javascript:window.open('" + 
          compUrl + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } 
      if (mItem[10] != null && mItem[10].toString().length() > 0 && 
        !"0".equals(mItem[10].toString()))
        action = "0".equals(mItem[18].toString()) ? (
          "/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          mItem[0].toString()) : (
          "javascript:window.open('/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          mItem[0].toString() + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');"); 
      if (mItem[10] != null && mItem[10].toString().length() <= 0 && 
        !"0".equals(mItem[10].toString()) && 
        mItem[11] != null && mItem[11].toString().length() > 0 && 
        !"0".equals(mItem[11].toString()))
        action = "0".equals(mItem[18].toString()) ? (
          "/jsoa/ModuleDealwithAction.do?action=maintenance?tblid=" + 
          mItem[17].toString()) : (
          "javascript:window.open('/jsoa/ModuleDealwithAction.do?action=maintenance?tblid=" + 
          mItem[17].toString() + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');"); 
      if (mItem[12] != null && mItem[12].toString().length() > 0 && 
        !"-1".equals(mItem[12].toString())) {
        String[] values = new String[5];
        int m = 0;
        StringTokenizer tk = new StringTokenizer(mItem[12].toString(), "$");
        while (tk.hasMoreTokens()) {
          values[m] = tk.nextToken();
          m++;
        } 
        String path = 
          "/jsoa/JsFlowAddAction.do?action=add&processId=" + 
          values[0] + 
          "&tableId=" + values[1] + "&processName=" + 
          values[2] + 
          "&processType=" + 
          values[3] + "&remindField=" + ((
          values[4] != null && values[4].length() > 0) ? 
          values[4] : "") + 
          "&moduleId=null";
        action = "javascript:window.open('" + 
          path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');";
      } 
    } 
    return action;
  }
  
  public String confirmAction(ModuleMenuPO po, HttpServletRequest request) {
    String action = "";
    if (po != null)
      if (po.getMenuAction() != null && po.getMenuAction().length() > 0) {
        String compUrl = 
          "/jsoa/module/jump_link.jsp" + 
          concatActionUrl(po);
        action = "0".equals(String.valueOf(po.getMenuOpenStyle())) ? 
          compUrl : (
          "javascript:window.open('" + 
          compUrl + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } else if (po.getMenuListTableMap() != null && 
        !"0".equals(po.getMenuListTableMap())) {
        action = "0".equals(String.valueOf(po.getMenuOpenStyle())) ? (
          "/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          po.getId()) : (
          "javascript:window.open('/jsoa/ModuleDealwithAction.do?action=getPage&menuId=" + 
          po.getId() + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } else if (po.getMenuStartFlow() != null && 
        !"-1".equals(po.getMenuStartFlow().toString())) {
        String[] values = new String[5];
        int m = 0;
        StringTokenizer tk = new StringTokenizer(po.getMenuStartFlow(), 
            "$");
        while (tk.hasMoreTokens()) {
          values[m] = tk.nextToken();
          m++;
        } 
        String path = 
          "/jsoa/JsFlowAddAction.do?action=add&processId=" + 
          values[0] + 
          "&tableId=" + values[1] + "&processName=" + 
          values[2] + 
          "&processType=" + 
          values[3] + "&remindField=" + ((
          values[4] != null && values[4].length() > 0) ? 
          values[4] : "") + 
          "&moduleId=null";
        action = "javascript:window.open('" + 
          path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');";
      } else if (po.getMenuFileLink() != null) {
        String path = request.getRealPath("\\");
        String src = "0000";
        if (po.getMenuFileLink() != null && po.getMenuFileLink().length() > 6 && po.getMenuFileLink().substring(4, 5) == "_") {
          src = po.getMenuFileLink().substring(0, 4);
        } else {
          src = "0000";
        } 
        path = 
          "/jsoa/upload/" + src + "/module/" + 
          po.getMenuFileLink();
        action = "0".equals(String.valueOf(po.getMenuOpenStyle())) ? 
          path : (
          "javascript:window.open('" + 
          path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      } else if (po.getMenuHtmlLink() != null) {
        String path = request.getRealPath("\\");
        String srcTr = "0000";
        if (po.getMenuFileLink() != null && po.getMenuFileLink().length() > 6 && po.getMenuFileLink().substring(4, 5).equals("_")) {
          srcTr = po.getMenuFileLink().substring(0, 4);
        } else {
          srcTr = "0000";
        } 
        path = "/jsoa/upload/" + srcTr + "/module/" + 
          po.getMenuHtmlLink();
        action = "0".equals(String.valueOf(po.getMenuOpenStyle())) ? 
          path : (
          "javascript:window.open('" + path + 
          "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
      }  
    return action;
  }
  
  public String concatActionUrl(Object[] mItem) {
    String url = "";
    boolean firstPara = true;
    if (mItem != null && mItem.length > 0) {
      if (mItem[4] != null) {
        url = "?action=" + mItem[4].toString();
        firstPara = false;
      } 
      if (mItem[5] != null) {
        url = String.valueOf(url) + (firstPara ? "?" : "&") + "menuActionParams1=" + 
          mItem[5].toString();
        firstPara = false;
      } 
      if (mItem[6] != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&menuActionParams2=" + mItem[6].toString()));
        firstPara = false;
      } 
      if (mItem[7] != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&menuActionParams3=" + mItem[7].toString()));
        firstPara = false;
      } 
      if (mItem[8] != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&" + mItem[8] + "=" + mItem[9].toString()));
        firstPara = false;
      } 
    } 
    return url;
  }
  
  private String concatActionUrl(ModuleMenuPO po) {
    String url = "";
    boolean firstPara = true;
    if (po != null) {
      if (po.getMenuAction() != null) {
        url = "?action=" + po.getMenuAction().toString();
        firstPara = false;
      } 
      if (po.getMenuActionParams1() != null) {
        url = String.valueOf(url) + (firstPara ? "?" : "&") + "menuActionParams1=" + 
          po.getMenuActionParams1();
        firstPara = false;
      } 
      if (po.getMenuActionParams2() != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&menuActionParams2=" + po.getMenuActionParams1()));
        firstPara = false;
      } 
      if (po.getMenuActionParams3() != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&menuActionParams3=" + po.getMenuActionParams1()));
        firstPara = false;
      } 
      if (po.getMenuActionParams4() != null) {
        url = String.valueOf(url) + (
          firstPara ? "?" : (
          "&menuActionParams4=" + po.getMenuActionParams4()));
        firstPara = false;
      } 
    } 
    return url;
  }
  
  public List getAllQueryCaseByTblId(String tblId, String domainId, String type) {
    List<ListItem> retlist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tblId, "String");
      pg.put(domainId, "String");
      pg.put(type, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      List<ModuleSEQPO> list = (List)ejbProxy.invoke("getAllQueryCaseByTblId", 
          pg.getParameters());
      if (list != null && list.size() > 0) {
        retlist = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
          ListItem item = new ListItem();
          ModuleSEQPO po = list.get(i);
          item.setId(po.getId().toString());
          item.setName(po.getMenuCaseName());
          retlist.add(item);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllQueryCaseByTblId information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public List getQueryShowFieldsByCase(String caseId, String domainId, String flag) {
    List retlist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(caseId, "String");
      pg.put(domainId, "String");
      pg.put(flag, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retlist = convertStrArrToShow((String[][])ejbProxy.invoke(
            "getQueryShowFieldsByCase", 
            pg.getParameters()), 0, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryShowFieldsByCase information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public List getDefListFields(String tblId) {
    List retlist = null;
    try {
      retlist = convertStrArrToShow((new CustomDatabaseBD())
          .getListField(tblId), 0, false, false);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getDefListFields information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public Long saveQLCaseSet(String tblId, String qlFields, String domainId, String caseName, String type) {
    Long retlist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(tblId, "String");
      pg.put(qlFields, "String");
      pg.put(domainId, "String");
      pg.put(caseName, "String");
      pg.put(type, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retlist = (Long)ejbProxy.invoke("saveQLCaseSet", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to saveQLCaseSet information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public boolean delQLCaseSet(String caseId) {
    boolean retlist = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(caseId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retlist = ((Boolean)ejbProxy.invoke("delQLCaseSet", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delQLCaseSet information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public String[][] getQueryField(String queryCaseId, String domainId) {
    String[][] retlist = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(queryCaseId, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retlist = (String[][])ejbProxy.invoke("getQueryField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return retlist;
  }
  
  public String[][] getListField(String listCaseId, String domainId) {
    String[][] retlist = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(listCaseId, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      retlist = (String[][])ejbProxy.invoke("getListField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + 
          e.getMessage());
    } finally {}
    return retlist;
  }
  
  public int getClomns() {
    return 10;
  }
  
  public String[] getYears() {
    String[] keys = new String[this.bikes.size()];
    int i = 0;
    Iterator<String> it = this.bikes.keySet().iterator();
    while (it.hasNext())
      keys[i++] = it.next(); 
    return keys;
  }
  
  public String[] getBikes(String year) {
    return (String[])this.bikes.get(year);
  }
  
  public List getSamples() {
    ArrayList<ListItem> list = new ArrayList();
    for (int i = 0; i < 10; i++) {
      ListItem item = new ListItem();
      item.setId(String.valueOf(i));
      item.setName(String.valueOf(i) + " Name");
      list.add(item);
    } 
    return list;
  }
  
  public String getViewScope(String tblId, String orgIdString, String userId, String orgId, String rightType) {
    String list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(tblId, "String");
      pg.put(orgIdString, "String");
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(rightType, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String)ejbProxy.invoke("getViewScope", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getViewScope information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getMenuScope(String domainId, String menuId) throws Exception {
    String menuscope = "&*0*";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, "String");
      pg.put(menuId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      menuscope = (String)ejbProxy.invoke("getMenuScope", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuScope information :" + e.getMessage());
    } 
    return menuscope;
  }
  
  public String setMenuDisplay(String menuId, String menuLevel, String menuLocation, String menuCount, String domainId, int show) {
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(menuId, "String");
      pg.put(menuLevel, "String");
      pg.put(menuLocation, "String");
      pg.put(menuCount, "String");
      pg.put(domainId, "String");
      pg.put(new Integer(show), Integer.class);
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      ejbProxy.invoke("setMenuDisplay", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuScope information :" + e.getMessage());
    } 
    return null;
  }
  
  public boolean showMenu(String menuCode, String domainId) {
    boolean rs = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuCode, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      rs = ((Boolean)ejbProxy.invoke("showMenu", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to showMenu information :" + e.getMessage());
    } 
    return rs;
  }
  
  public String getShowMenu(String menuCode, String domainId) {
    String rs = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuCode, "String");
      pg.put(domainId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      rs = (String)ejbProxy.invoke("getShowMenu", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuScope information :" + e.getMessage());
    } 
    return rs;
  }
  
  public List getAllCustomMenu(String domainId, String menuId, boolean converFlag, boolean transFlag) {
    return getAllCustomMenu(domainId, menuId, converFlag, transFlag, "");
  }
  
  public String getMenuNameByIds(String ids) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      result = (String)ejbProxy.invoke("getMenuNameByIds", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuNameByIds information :" + e.getMessage());
    } 
    return result;
  }
  
  public Object[] getMenuActionAndPara(String menuId, String flag) {
    Object[] result = (Object[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(menuId, "String");
      pg.put(flag, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      result = (Object[])ejbProxy.invoke("getMenuActionAndPara", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMenuNameByIds information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getManagerRange(HttpSession session, String rightCode) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    if ("1".equals(session.getAttribute("sysManager").toString())) {
      managerScope = "*0*";
    } else {
      List<Object[]> tempList = (new ManagerService()).getRightScope(session
          .getAttribute("userId").toString(), rightCode);
      if (tempList != null && tempList.size() > 0) {
        Object[] obj = tempList.get(0);
        String type = obj[0].toString();
        if ("4".equals(type)) {
          if (obj[1] != null)
            managerScope = obj[1].toString(); 
        } else if ("1".equals(type)) {
          managerScope = "*AAAA*";
        } else if ("0".equals(type)) {
          managerScope = "*0*";
        } 
      } 
    } 
    return managerScope;
  }
  
  public boolean ifUserHasRight(String currenOrgIdStr, String currenOrgId, String currenUserId, String userId, String orgId, String rightCode) {
    boolean re = false;
    ModuleMenuEJBBean bean = new ModuleMenuEJBBean();
    try {
      Map reMap = bean.getViewScopeMap(currenUserId, currenOrgIdStr, currenOrgId, rightCode);
      if (reMap == null || reMap.get("scopeType") == null)
        return false; 
      String scopeType = reMap.get("scopeType").toString();
      if ("0".equals(scopeType)) {
        re = true;
      } else if ("1".equals(scopeType)) {
        if (currenUserId.equals(userId))
          re = true; 
      } else if ("2".equals(scopeType)) {
        String scopeScope = reMap.get("scopeScope").toString();
        int t = scopeScope.indexOf("*" + orgId + "*");
        if (t >= 0)
          re = true; 
      } else if ("3".equals(scopeType)) {
        if (currenOrgId.equals(orgId))
          re = true; 
      } else if ("4".equals(scopeType)) {
        if (reMap != null && reMap.get("scopeScope") != null) {
          String scopeScope = reMap.get("scopeScope").toString();
          if (scopeScope.indexOf(orgId) > 0)
            re = true; 
        } 
      } else {
        re = false;
      } 
    } catch (HibernateException e) {
      re = false;
      e.printStackTrace();
    } 
    return re;
  }
  
  public String isJx(String flowId) {
    ModuleMenuEJBBean bean = new ModuleMenuEJBBean();
    return bean.isJx(flowId);
  }
  
  public String getDefaultViewScope(String scopeType, String scopeRange, String userId, String tblId, String orgId) {
    String list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(scopeType, "String");
      pg.put(scopeRange, "String");
      pg.put(userId, "String");
      pg.put(tblId, "String");
      pg.put(orgId, "String");
      EJBProxy ejbProxy = new EJBProxy("ModuleMenuEJB", 
          "ModuleMenuEJBLocal", ModuleMenuEJBHome.class);
      list = (String)ejbProxy.invoke("getDefaultViewScope", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getDefaultViewScope information :" + e.getMessage());
    } finally {}
    return list;
  }
}
