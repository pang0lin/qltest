package com.js.oa.module.bean;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.AuditModuleMenuPO;
import com.js.oa.audit.service.AuditLogBD;
import com.js.oa.audit.service.AuditModuleMenuBD;
import com.js.oa.form.zgrs.QjUtil;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.po.ModuleSEQPO;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.menu.po.MenuSetPO;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;

public class ModuleMenuEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getMenuListByTopID(String menuId, String where) throws Exception {
    List list = null;
    begin();
    try {
      list = 
        this.session.createQuery(
          "select po.id, po.menuName, po.menuBlone, po.menuLevel, po.menuAction, po.menuActionParams1, po.menuActionParams2 ,po.menuActionParams3, po.menuActionParams4, po.menuActionParams4Value, po.menuListTableMap, po.menuMaintenanceTableMap, po.menuStartFlow, po.menuFileLink, po.menuHtmlLink, po.menuAccess, po.menuScope, po.menuMaintenanceTableName, po.menuOpenStyle, po.menuIsValide, po.menuViewUser, po.menuViewOrg, po.menuViewGroup,po.menuCount,po.menuIsZky,po.menuZkyType from com.js.oa.module.po.ModuleMenuPO po where (" + 



          
          where + ") and po.menuIsValide=1 and po.menuMaintenanceSubTableMap=" + menuId + " order by po.menuLevel asc").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllCustomMenu(String domainId, String menuId, String show) throws HibernateException {
    List list = null;
    Long doId = Long.valueOf(domainId);
    begin();
    try {
      list = 
        this.session.createQuery(
          "select po.id, po.menuName, po.menuBlone, po.menuLevel, po.menuAction, po.menuActionParams1, po.menuActionParams2 ,po.menuActionParams3, po.menuActionParams4, po.menuActionParams4Value, po.menuListTableMap, po.menuMaintenanceTableMap, po.menuStartFlow, po.menuFileLink, po.menuHtmlLink, po.menuAccess, po.menuScope, po.menuMaintenanceTableName, po.menuOpenStyle, po.menuIsValide, po.menuViewUser, po.menuViewOrg, po.menuViewGroup,po.menuCount from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 




          
          doId + ((
          
          show != null && !"".equals(show)) ? (" and po.menuIsValide=" + show) : "") + ((
          
          menuId != null && menuId.length() > 0) ? (
          " and po.menuMaintenanceSubTableMap =" + (
          (menuId.indexOf("_") >= 0) ? 
          menuId.substring(1, menuId.length()) : menuId)) : "") + 
          " order by po.menuLevel asc").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllSubMenus(String domainId, String menuId) throws HibernateException {
    Long doId = Long.valueOf(domainId);
    List list = null;
    begin();
    try {
      list = 
        this.session.createQuery(
          "select po.id, po.menuName, po.menuBlone, po.menuLevel from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
          
          doId + " and po.menuBlone = " + menuId + " and po.id<>" + menuId + " order by po.menuLevel ").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Long saveMenuConfig(ModuleMenuPO po) throws HibernateException {
    Long menuId = null;
    begin();
    try {
      menuId = (Long)this.session.save(po);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return menuId;
  }
  
  public boolean audit(ModuleMenuPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean result = false;
    try {
      HttpSession httpsession = httpServletRequest.getSession(true);
      UserBD userBD = new UserBD();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      AuditLog auditLog = new AuditLog();
      auditLog.setSubmitEmpid(Long.valueOf((String)httpsession.getAttribute("userId")));
      EmployeeVO employee = userBD.getEmpByid(Long.valueOf((String)httpsession.getAttribute("userId")));
      if (employee != null)
        auditLog.setSubmitEmpname(employee.getEmpName()); 
      auditLog.setSubmitOrgid(Long.valueOf(httpsession.getAttribute("orgId").toString()));
      Date ts = Timestamp.valueOf(format.format(new Date()));
      auditLog.setSubmitTime(ts);
      auditLog.setAuditModule(new Long(8L));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditModuleMenuBD auditModuleMenuBD = new AuditModuleMenuBD();
      if ("delete".equals(opreate) || "hideMenu".equals(opreate) || "showMenu".equals(opreate)) {
        begin();
        String sql = "select po.id,po.menuLocation from  com.js.oa.module.po.ModuleMenuPO po where po.id=" + id;
        List<Object[]> tempList = this.session.createQuery(sql).list();
        if (tempList != null && tempList.size() > 0) {
          po = (ModuleMenuPO)this.session.load(ModuleMenuPO.class, id);
        } else {
          sql = "select po.id,po.menuLocation from  com.js.oa.module.po.ModuleMenuPO po where po.menuLocation=" + id + " order by po.menuLocation asc";
          tempList = this.session.createQuery(sql).list();
          if (tempList != null && tempList.size() > 0) {
            Object[] obj = tempList.get(0);
            po = (ModuleMenuPO)this.session.load(ModuleMenuPO.class, Long.valueOf(obj[0].toString()));
          } 
        } 
      } 
      String mode = httpServletRequest.getParameter("mode");
      AuditModuleMenuPO auditPO = (AuditModuleMenuPO)FillBean.transformOTO(po, AuditModuleMenuPO.class);
      Long domainId = (httpsession.getAttribute("domainId") != null) ? Long.valueOf(httpsession.getAttribute("domainId").toString()) : 
        Long.valueOf("0");
      auditPO.setMenuLocationSelValue(po.getMenuLocationSelValue());
      auditPO.setMenuExtId(id);
      auditPO.setDomainId(domainId);
      auditPO.setAuditLogId(auditLogId);
      auditPO.setAuditOperationType(opreate);
      auditPO.setModel(mode);
      if (-1L == auditPO.getMenuBlone().longValue() || 0L == auditPO.getMenuBlone().longValue())
        auditPO.setMenuCount(8); 
      Long auditIpId = auditModuleMenuBD.saveAuditModuleMenu(auditPO);
      if ("insert".equals(opreate))
        opreate = "新增"; 
      if ("update".equals(opreate))
        opreate = "修改"; 
      if ("delete".equals(opreate))
        opreate = "删除"; 
      if ("showMenu".equals(opreate))
        opreate = "显示"; 
      if ("hideMenu".equals(opreate))
        opreate = "隐藏"; 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String userName = (String)httpsession.getAttribute("userName");
      msRemindBeann.auditMsRemind(Long.valueOf((String)httpsession.getAttribute("userId")).longValue(), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          1, 1, new Date(), "审计提醒:" + userName + opreate + "扩展菜单管理\"" + po.getMenuName() + "\"", "audit", auditLogId.longValue(), "AuditModuleMenuAction.do?action=forshenji&id=" + auditLogId + "&flag=fromRemind");
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public boolean autoAudit(ModuleMenuPO po, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
    boolean result = false;
    try {
      HttpSession httpsession = httpServletRequest.getSession(true);
      UserBD userBD = new UserBD();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      AuditLog auditLog = new AuditLog();
      auditLog.setSubmitEmpid(Long.valueOf((String)httpsession.getAttribute("userId")));
      EmployeeVO employee = userBD.getEmpByid(Long.valueOf((String)httpsession.getAttribute("userId")));
      if (employee != null)
        auditLog.setSubmitEmpname(employee.getEmpName()); 
      auditLog.setSubmitOrgid(Long.valueOf(httpsession.getAttribute("orgId").toString()));
      Date ts = Timestamp.valueOf(format.format(new Date()));
      auditLog.setSubmitTime(ts);
      auditLog.setAuditModule(new Long(8L));
      auditLog.setAuditStatus(Integer.valueOf(1));
      auditLog.setIschecked(Integer.valueOf(1));
      auditLog.setCheckEmpid(new Long(0L));
      auditLog.setCheckEmpname("系统自动审核");
      auditLog.setCheckTime(ts);
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditModuleMenuBD auditModuleMenuBD = new AuditModuleMenuBD();
      if ("delete".equals(opreate) || "hideMenu".equals(opreate) || "showMenu".equals(opreate)) {
        begin();
        String sql = "select po.id,po.menuLocation from  com.js.oa.module.po.ModuleMenuPO po where po.id=" + id;
        List<Object[]> tempList = this.session.createQuery(sql).list();
        if (tempList != null && tempList.size() > 0) {
          po = (ModuleMenuPO)this.session.load(ModuleMenuPO.class, id);
        } else {
          sql = "select po.id,po.menuLocation from  com.js.oa.module.po.ModuleMenuPO po where po.menuLocation=" + id + " order by po.menuLocation asc";
          tempList = this.session.createQuery(sql).list();
          if (tempList != null && tempList.size() > 0) {
            Object[] obj = tempList.get(0);
            po = (ModuleMenuPO)this.session.load(ModuleMenuPO.class, Long.valueOf(obj[0].toString()));
          } 
        } 
      } 
      String mode = httpServletRequest.getParameter("mode");
      AuditModuleMenuPO auditPO = (AuditModuleMenuPO)FillBean.transformOTO(po, AuditModuleMenuPO.class);
      Long domainId = (httpsession.getAttribute("domainId") != null) ? Long.valueOf(httpsession.getAttribute("domainId").toString()) : 
        Long.valueOf("0");
      auditPO.setMenuLocationSelValue(po.getMenuLocationSelValue());
      auditPO.setMenuExtId(id);
      auditPO.setDomainId(domainId);
      auditPO.setAuditLogId(auditLogId);
      auditPO.setAuditOperationType(opreate);
      auditPO.setModel(mode);
      if (-1L == auditPO.getMenuBlone().longValue() || 0L == auditPO.getMenuBlone().longValue())
        auditPO.setMenuCount(8); 
      Long auditIpId = auditModuleMenuBD.saveAuditModuleMenu(auditPO);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public List loadParentMenuConfiger(String domainId, String pMenuId) throws HibernateException {
    List list = null;
    ModuleMenuPO po = null;
    begin();
    try {
      list = 
        this.session.createQuery(
          "select po.id, po.menuBlone, po.menuLevel, po.menuCount, po.menuMaintenanceSubTableMap, po.parentOrder from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
          
          domainId + " and po.id = " + pMenuId).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Object getMaxMenuCount(String domainId, String menuBlone) throws Exception {
    begin();
    Object obj = null;
    try {
      obj = this.session.iterate("select max(po.menuLevel)  from com.js.oa.module.po.ModuleMenuPO po where po.domainId = " + 
          
          domainId + " and po.menuBlone = " + 
          menuBlone).next();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return obj;
  }
  
  public String[][] getSearchConfigs(String domainId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = new DbOpt();
    try {
      list = dbopt.executeQueryToStrArr2(
          "select LOCATE_ID,LOCATE_NAME from tLocate where LOCATE_DOMAINID =" + 
          Long.valueOf(domainId), 2);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return list;
  }
  
  public List loadMenuConfig(String domainId, String menuId) throws HibernateException {
    List list = null;
    ModuleMenuPO po = null;
    begin();
    try {
      if (menuId.indexOf("-") >= 0) {
        list = 
          this.session.createQuery(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
            domainId + " and po.menuLevel = '" + 
            menuId + "'")
          .list();
      } else {
        list = 
          this.session.createQuery(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
            domainId + " and po.id = " + menuId)
          .list();
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public String[][] getQueryFields(String domainId, String menuSearchBound) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    DbOpt dbopt = new DbOpt();
    try {
      list = dbopt.executeQueryToStrArr2(
          " SELECT LOCATE_FIELDSREL, LOCATE_SQL FROM tLocate WHERE LOCATE_DOMAINID = " + 
          domainId + " and LOCATE_ID = " + menuSearchBound, 2);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public String[][] getFieldsTypes(String fieldsTrain) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    DbOpt dbopt = new DbOpt();
    try {
      String sql = 
        " SELECT f.field_name, f.field_show, f.field_desname, f.field_id FROM tSHOW s , tField f  WHERE s.show_id = f.field_show and  f.field_name in(" + 
        
        fieldsTrain + ")";
      list = dbopt.executeQueryToStrArr2(sql, 4);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public boolean delAllCustmizeMenus(String domainId) throws HibernateException {
    boolean flag = false;
    ModuleMenuPO po = null;
    begin();
    try {
      this.session.delete(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
          domainId + " and po.menuCount < 9 ");
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public boolean delBatchCustmizeMenus(String domainId, String menuIds) throws HibernateException {
    boolean flag = false;
    ModuleMenuPO po = null;
    begin();
    try {
      List<ModuleMenuPO> list = 
        this.session.createQuery(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
          domainId + " and (po.id in (" + menuIds + 
          ") or po.menuLocation in (" + menuIds + 
          ")) and po.menuCount < 9 ").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          po = list.get(i);
          if (po.getMenuCount() == 8 || po.getMenuCount() == 0) {
            this.session.delete(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
                domainId + " and po.id = " + po.getId() + 
                " or po.menuLevel like '%" + 
                po.getMenuLevel() + 
                "%'");
          } else {
            this.session.delete(" from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
                domainId + " and po.id = " + po.getId());
          } 
        }  
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public boolean updateRootLevel(String domainId) throws HibernateException {
    boolean flag = false;
    ModuleMenuPO po = null;
    begin();
    try {
      po = this.session.find(" from com.js.oa.module.po.ModuleMenuPO po where po.menuBlone = 0")
        .get(0);
      if (po != null) {
        po.setMenuBlone(po.getId());
        po.setMenuLevel(po.getId().toString());
        this.session.update(po);
        this.session.flush();
      } 
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public String getSearchHtmlPart(String domainId, String menuId) throws HibernateException {
    String retStr = null;
    begin();
    try {
      ModuleMenuPO po = this.session.find(" from com.js.oa.module.po.ModuleMenuPO po where po.id = " + 
          menuId + " and po.domainId=" + 
          domainId).get(0);
      if (po != null)
        retStr = po.getMenuSearchHtml(); 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return retStr;
  }
  
  public String[][] getBizTableJoins(String masterTable, String subTable) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    DbOpt dbopt = new DbOpt();
    try {
      list = 
        dbopt.executeQueryToStrArr2(
          " SELECT b.field_table,MM. field_id, MM.field_name from tField b,  (SELECT a.field_id, a.field_name From tField a,  (SELECT LIMIT_FIELD, LIMIT_PRYFIELD FROM tLIMIT   WHERE LIMIT_TABLE = " + 

          
          masterTable + 
          " AND LIMIT_PRYTABLE = " + 
          subTable + 
          " ) TT where a.field_id = TT.LIMIT_FIELD OR A.FIELD_ID = TT.LIMIT_PRYFIELD) MM " + 
          " WHERE B.FIELD_ID = MM.FIELD_ID ", 3);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public String[][] getDefaultLoadDatas(String searchSql, Integer clomns, String paras) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    DbOpt dbopt = new DbOpt();
    try {
      if (searchSql.indexOf("where") > 0 && paras != null && 
        paras.length() > 0) {
        searchSql = String.valueOf(searchSql) + " " + paras.substring(0, paras.lastIndexOf("and"));
      } else {
        searchSql = String.valueOf(searchSql) + " " + paras;
      } 
      list = dbopt.executeQueryToStrArr2(searchSql, clomns.intValue());
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public boolean updateCustomerMenu(ModuleMenuPO po) throws HibernateException {
    boolean flag = false;
    begin();
    try {
      List<ModuleMenuPO> list = this.session.createQuery(" from com.js.oa.module.po.ModuleMenuPO po where po.menuLevel like '%" + 
          po.getMenuLevel() + 
          "%' and po.id not in (" + 
          po.getId() + ")").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          ModuleMenuPO subPo = 
            list.get(i);
          subPo.setMenuIsValide(po.getMenuIsValide());
          subPo.setParentOrder(po.getParentOrder());
          this.session.update(subPo);
          this.session.flush();
        }  
      this.session.update(po);
      this.session.flush();
      updateMenuRight(po.getMenuName(), (String)po.getId(), po.getDomainId());
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return flag;
  }
  
  public String[][] getTableFields(String domainId, String tblId) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    try {
      DbOpt dbopt = new DbOpt();
      String sql = 
        "select FIELD_ID, FIELD_NAME, FIELD_DESNAME from tField where FIELD_TABLE = (select table_id from tTable where table_name ='" + 
        tblId + 
        "')";
      list = 
        dbopt.executeQueryToStrArr2(sql, 3);
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public String[][] getSubTableDatas(String domainId, String tblId, Integer clomns) throws Exception {
    String[][] list = (String[][])null;
    ModuleMenuPO po = null;
    try {
      DbOpt dbopt = new DbOpt();
      list = 
        dbopt.executeQueryToStrArr2("select * from " + tblId + 
          " where 1=1", 
          clomns.intValue());
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public boolean deleteBizDatas(String tblId, String recordId) throws Exception {
    boolean flag = false;
    String[][] relationKeyList = (String[][])null;
    String[][] masterValue = (String[][])null;
    String[][] masterKey = (String[][])null;
    try {
      DbOpt dbopt = new DbOpt();
      deleteWithoutRelation(dbopt, tblId, recordId, "");
      List<String> subTableList = subTables(tblId);
      if (subTableList != null && subTableList.size() > 0)
        for (int i = 0; i < subTableList.size(); i++)
          (new DataSourceBase()).executeSqlUpdate("delete from " + (String)subTableList.get(i) + " where " + (String)subTableList.get(i) + "_foreignkey in (" + 
              recordId.substring(0, recordId.lastIndexOf(",")) + ")");  
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return flag;
  }
  
  public List<String> subTables(String tblId) {
    String sql = "select t.table_Name from ttable t join tlimit l on t.table_id =l.limit_prytable where l.limit_table=(select table_id from ttable where table_name='" + 
      tblId + "')";
    return (new DataSourceUtil()).getQuery(sql, "");
  }
  
  public boolean deleteAllMasterAndSub(String tblId) throws Exception {
    boolean flag = false;
    String[][] relationKeyList = (String[][])null;
    String[][] masterKey = (String[][])null;
    try {
      DbOpt dbopt = new DbOpt();
      deleteOperation(dbopt, relationKeyList, tblId);
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return flag;
  }
  
  public List getWFProcesses(String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      String hSql = " select bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField from com.js.oa.jsflow.po.WFWorkFlowProcessPO  aaa join aaa.wfPackage bbb where aaa.isPublish=1 and aaa.domainId = " + 
        Long.valueOf(domainId) + " order by bbb.wfPackageId ";
      list = this.session.createQuery(hSql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  private String[][] getSubTblAndFieldName(DbOpt dbopt, String tblId) throws SQLException, Exception {
    String quaryStr = 
      "select m.TABLE_NAME, s.FIELD_NAME, s.FIELD_TYPE FROM ( select a.TABLE_NAME, b.limit_pryfield from tTable a, (select limit_prytable , limit_pryfield from tLimit where limit_table = (select table_id from tTable where table_name = '" + 

      
      tblId + 
      "')) b " + 
      "where a.TABLE_ID = b.limit_prytable ) m, tField s  " + 
      "where m.limit_pryfield = s.field_id ";
    String[][] str = dbopt.executeQueryToStrArr2(quaryStr, 3);
    dbopt.close();
    return str;
  }
  
  private String[][] getMasterKey(DbOpt dbopt, String tblId) throws SQLException, Exception {
    String quaryStr = "select field_name from tField where field_id =  (select limit_field from tLimit where limit_table =  (select table_id from tTable where table_name = '" + 

      
      tblId + "'))";
    String[][] str = dbopt.executeQueryToStrArr2(quaryStr, 1);
    dbopt.close();
    return str;
  }
  
  private String[][] getMasterKeyValue(DbOpt dbopt, String tblId, String[][] masterKey, String recordId) throws SQLException, Exception {
    String quaryStr = "select " + masterKey[0][0] + " from " + tblId + 
      " where " + tblId + "_id = " + recordId;
    String[][] str = dbopt.executeQueryToStrArr2(quaryStr, 1);
    dbopt.close();
    return str;
  }
  
  private void deleteOperation(DbOpt dbopt, String[][] relationKeyList, String tblId) throws SQLException, Exception {
    Statement stat = dbopt.getStatement();
    if (relationKeyList != null && relationKeyList.length > 0)
      for (int i = 0; i < relationKeyList.length; i++) {
        String str = "delete from " + relationKeyList[i][0];
        stat.addBatch(str);
        stat.executeBatch();
      }  
    String delStr = "delete from " + tblId;
    stat.addBatch(delStr);
    stat.executeBatch();
    dbopt.close();
  }
  
  private void deleteWithFieldValue(DbOpt dbopt, String[][] relationKeyList, String[][] masterValue, String tblId, String tblFieldValue, String tmpMid) throws SQLException, Exception {
    Statement stat = dbopt.getStatement();
    if (relationKeyList != null && relationKeyList.length > 0) {
      for (int i = 0; i < relationKeyList.length; i++) {
        String str;
        if ("1000002".equals(relationKeyList[i][2]) || 
          "1000003".equals(relationKeyList[i][2])) {
          str = "delete from " + relationKeyList[i][0] + " where " + 
            relationKeyList[i][1] + " =  '" + masterValue[0][0] + 
            "'";
        } else {
          str = "delete from " + relationKeyList[i][0] + " where " + 
            relationKeyList[i][1] + " = " + masterValue[0][0];
        } 
        stat.addBatch(str);
        stat.executeBatch();
      } 
      String delStr = "delete from " + tblId + " where " + tblId + "_id = " + 
        tblFieldValue;
      stat.addBatch(delStr);
      stat.executeBatch();
      dbopt.close();
      tmpMid = tblFieldValue;
    } 
  }
  
  private void deleteWithoutRelation(DbOpt dbopt, String tblId, String tblFieldValue, String tmpMid) throws SQLException, Exception {
    Statement stat = dbopt.getStatement();
    if ("chinaLife".equals(SystemCommon.getCustomerName()))
      (new QjUtil()).deleteShuju(tblId, tblFieldValue, "-2"); 
    String delStr = "delete from " + tblId + " where " + tblId + "_id in (" + 
      tblFieldValue.substring(0, tblFieldValue.lastIndexOf(",")) + ")";
    stat.addBatch(delStr);
    stat.executeBatch();
    dbopt.close();
  }
  
  public String getDefaultLoadDatasCount(String tblId, String condition) throws Exception {
    String count = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String dbType = SystemCommon.getDatabaseType();
      if (dbType.indexOf("mysql") >= 0) {
        count = dbopt.executeQueryToStr("select count(_temp_table._temp_field1) from (select distinct " + tblId.trim().split(" ")[0] + "." + tblId.trim().split(" ")[0] + "_ID as _temp_field1 from " + 
            tblId + ((condition != null && 
            condition.length() > 0) ? (
            " where " + condition) : "") + ") as _temp_table");
      } else {
        count = dbopt.executeQueryToStr("select count(1) from (select distinct " + tblId.trim().split(" ")[0] + "." + tblId.trim().split(" ")[0] + "_ID from " + 
            tblId + ((condition != null && 
            condition.length() > 0) ? (
            " where " + condition) : "") + ")");
      } 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return count;
  }
  
  public String getCountByYourSql(String sql) throws Exception {
    String count = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String dbType = SystemCommon.getDatabaseType();
      String sqle = "select count(*) from ( " + sql + ") wk";
      count = dbopt.executeQueryToStr(sqle);
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return count;
  }
  
  public List listMenuConfigs(String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery(" select po.id, po.menuName, po.menuLocation from com.js.oa.module.po.ModuleMenuPO po where po.domainId = " + 
          domainId).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllSubMenusBySetId(String menuSetId, String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery(
          "select po.id, po.menuName, po.menuBlone, po.menuLocation, po.menuLevel from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + 
          
          domainId + " and po.menuLocation = " + menuSetId + 
          " order by po.menuLevel").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllGroupsByUserId(String userId, String domainId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId + " and po.domainId = " + 
          domainId).list();
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean addMenuListRight(List<HashMap> list) throws HibernateException {
    Boolean ret = null;
    DbOpt opt = null;
    try {
      if (list != null) {
        opt = new DbOpt();
        String sql = "";
        for (int i = 0; i < list.size(); i++) {
          HashMap subMap = list.get(i);
          sql = String.valueOf(sql) + "'" + subMap.get("rightCode") + "',";
        } 
        String[] id = opt.executeQueryToStrArr1(
            "select right_id from org_right where rightcode in (" + 
            sql.substring(0, sql.lastIndexOf(",")) + ")");
        if (id != null && id.length > 0) {
          if (id.length == 4)
            for (int j = 0; j < list.size(); j++) {
              HashMap subMap = list.get(j);
              if ("删除".equals(subMap.get("rightName"))) {
                if ("mssqlserver".equals(DbOpt.dbtype)) {
                  sql = "insert into ORG_RIGHT values('" + 
                    subMap.get("rightName") + "','" + 
                    subMap.get("rightType") + "','" + 
                    subMap.get("rightClass") + 
                    "',1, '11111', '全部/本人/本组织及下属组织/本组织/自定义', '" + 
                    subMap.get("rightCode") + "', " + 
                    subMap.get("domainId") + ",1)";
                } else {
                  sql = "insert into ORG_RIGHT values(" + 
                    getMaxRightId(opt, (Statement)null) + ",'" + 
                    subMap.get("rightName") + "','" + 
                    subMap.get("rightType") + "','" + 
                    subMap.get("rightClass") + 
                    "',1, '11111', '全部/本人/本组织及下属组织/本组织/自定义', '" + 
                    subMap.get("rightCode") + "', " + 
                    subMap.get("domainId") + ",1)";
                } 
                opt.executeUpdate(sql);
              } 
            }  
          ret = new Boolean(false);
        } else {
          for (int j = 0; j < list.size(); j++) {
            HashMap subMap = list.get(j);
            if ("mssqlserver".equals(DbOpt.dbtype)) {
              sql = "insert into ORG_RIGHT values('" + 
                subMap.get("rightName") + "','" + 
                subMap.get("rightType") + "','" + 
                subMap.get("rightClass") + 
                "'," + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? 1 : 0) + ", '" + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? "11111" : 
                "00000") + "', '" + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? 
                "全部/本人/本组织及下属组织/本组织/自定义" : "") + "', '" + 
                subMap.get("rightCode") + "', " + 
                subMap.get("domainId") + ",1,'')";
            } else {
              sql = "insert into ORG_RIGHT values(" + 
                getMaxRightId(opt, (Statement)null) + ",'" + 
                subMap.get("rightName") + "','" + 
                subMap.get("rightType") + "','" + 
                subMap.get("rightClass") + 
                "'," + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? 1 : 0) + ", '" + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? "11111" : 
                "00000") + "', '" + (
                !"新增".equals(subMap.get("rightName")
                  .toString()) ? 
                "全部/本人/本组织及下属组织/本组织/自定义" : "") + "', '" + 
                subMap.get("rightCode") + "', " + 
                subMap.get("domainId") + ",1,'')";
            } 
            opt.executeUpdate(sql);
          } 
        } 
        ret = new Boolean(true);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      ret = new Boolean(false);
    } finally {
      if (opt != null)
        try {
          opt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return ret;
  }
  
  private Long getMaxRightId(DbOpt opt, Statement stat) throws SQLException, Exception {
    if (opt != null) {
      String max = opt.executeQueryToStr("select max(right_id) from org_right");
      return new Long(Long.valueOf(max).longValue() + 1L);
    } 
    return null;
  }
  
  public Boolean deleteMenuRight(String menuId) throws HibernateException {
    Boolean ret = null;
    begin();
    try {
      DbOpt opt = new DbOpt();
      Statement stat = opt.getStatement();
      if (menuId != null && menuId.length() > 0) {
        List<ModuleMenuPO> list = 
          this.session.createQuery(" from com.js.oa.module.po.ModuleMenuPO po where po.menuLevel like '%" + 
            menuId + "-%' or po.id = " + menuId)
          .list();
        if (list != null && list.size() > 0) {
          ModuleMenuPO po = null;
          for (int i = 0; i < list.size(); i++) {
            po = list.get(i);
            if (po.getMenuCount() == 8 || po.getMenuCount() == 0) {
              String sql = " delete from org_role_right where right_id in (select right_id from org_right where rightCode like '%-" + 
                po.getId() + "%')";
              stat.execute(sql);
              sql = " delete from org_rightscope where right_id in (select right_id from org_right where rightCode like '%-" + 
                po.getId() + "%')";
              stat.execute(sql);
              sql = 
                " delete from ORG_RIGHT where rightCode like '%-" + 
                po.getId() + "%'";
              stat.execute(sql);
            } 
          } 
        } 
      } else {
        String sql = " delete from org_role_right where right_id in (select right_id from org_right where rightCode like '%-%')";
        stat.execute(sql);
        sql = " delete from org_rightscope where right_id in (select right_id from org_right where rightCode like '%-%')";
        stat.execute(sql);
        sql = " delete from ORG_RIGHT where rightCode like '%-%'";
        stat.execute(sql);
      } 
      ret = new Boolean(true);
      opt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      ret = new Boolean(false);
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List getAllSubMenus(String menuId) throws HibernateException {
    List list = null;
    begin();
    try {
      list = 
        this.session.createQuery(
          "from com.js.oa.module.po.ModuleMenuPO po where  po.menuLevel like '%" + 
          menuId + 
          "%' and po.menuLevel <> '" + menuId + 
          "' order by po.menuLevel ").list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllQueryCaseByTblId(String tblId, String domainId, String type) throws HibernateException {
    List list = null;
    begin();
    try {
      list = 
        this.session.createQuery(
          " from com.js.oa.module.po.ModuleSEQPO po  where  po.caseType = " + 
          Long.valueOf(type) + " and po.menuId = " + 
          Long.valueOf(tblId) + " and po.domainId = " + Long.valueOf(domainId)).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String[][] getQueryShowFieldsByCase(String caseId, String domainId, String flag) throws Exception {
    String[][] retList = (String[][])null;
    begin();
    try {
      List<Object[]> list = 
        this.session.createQuery(" select po.id, po.qlFields from com.js.oa.module.po.ModuleSEQPO po  where po.caseType = " + 
          Long.valueOf(flag) + 
          " and po.id = " + Long.valueOf(caseId) + 
          " and po.domainId = " + Long.valueOf(domainId)).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        DbOpt dbopt = new DbOpt();
        String sql = 
          "select a.field_id,a.field_desname from tfield a where a.field_id in (" + 
          obj[1] + ") and a.DOMAIN_ID=" + domainId + " ORDER BY FIELD(field_id," + obj[1] + ")";
        retList = dbopt.executeQueryToStrArr2(sql, 2);
        dbopt.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retList;
  }
  
  public Long saveQLCaseSet(String tblId, String qlFields, String domainId, String caseName, String type) throws HibernateException {
    Long retLong = null;
    begin();
    try {
      List<ModuleSEQPO> list = this.session.createQuery(" from com.js.oa.module.po.ModuleSEQPO po where po.menuCaseName = :menuCaseName and po.domainId = " + 
          Long.valueOf(domainId) + " and po.caseType = " + 
          Long.valueOf(type) + " and po.menuId = " + 
          Long.valueOf(tblId)).setString("menuCaseName", caseName).list();
      ModuleSEQPO po = (list != null && list.size() > 0) ? 
        list.get(0) : null;
      if (po == null) {
        po = new ModuleSEQPO();
        po.setMenuId(Long.valueOf(tblId));
        po.setQlFields(qlFields.substring(0, qlFields.lastIndexOf(",")));
        po.setDomainId(Long.valueOf(domainId));
        po.setMenuCaseName(caseName);
        po.setCaseType(Integer.valueOf(type));
        retLong = (Long)this.session.save(po);
      } else {
        po.setMenuId(Long.valueOf(tblId));
        po.setQlFields(qlFields.substring(0, qlFields.lastIndexOf(",")));
        po.setDomainId(Long.valueOf(domainId));
        po.setMenuCaseName(caseName);
        po.setCaseType(Integer.valueOf(type));
        retLong = (Long)this.session.save(po);
        this.session.update(po);
      } 
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retLong;
  }
  
  public String[][] getQueryField(String caseId, String domainId) throws Exception {
    String[][] retList = (String[][])null;
    begin();
    try {
      List<Object[]> list = 
        this.session.createQuery(" select po.id, po.qlFields from com.js.oa.module.po.ModuleSEQPO po  where  po.id = " + 
          caseId + 
          " and po.domainId = " + domainId).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        DbOpt dbopt = new DbOpt();
        String sql = "select a.field_id,a.field_desname,a.field_name, a.field_show, a.field_value from tfield a where a.field_id in (" + 
          obj[1] + ") and a.DOMAIN_ID=" + domainId + 
          " order by a.FIELD_SEQUENCE";
        retList = dbopt.executeQueryToStrArr2(sql, 5);
        dbopt.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retList;
  }
  
  public String[][] getListField(String listCaseId, String domainId) throws Exception {
    String[][] retList = (String[][])null;
    begin();
    try {
      List<Object[]> list = 
        this.session.createQuery(" select po.id, po.qlFields from com.js.oa.module.po.ModuleSEQPO po  where  po.id = " + 
          listCaseId + 
          " and po.domainId = " + domainId).list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        DbOpt dbopt = new DbOpt();
        String sql = "select a.field_id,a.field_desname,a.field_name,a.field_width,a.field_show,a.field_value,field_type from tfield a where a.field_id in (" + 
          obj[1] + ") and a.DOMAIN_ID=" + domainId + " order by a.FIELD_SEQUENCE";
        retList = dbopt.executeQueryToStrArr2(sql, 7);
        dbopt.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retList;
  }
  
  public Boolean delQLCaseSet(String caseId) throws HibernateException {
    Boolean retBool = Boolean.FALSE;
    begin();
    try {
      List<ModuleSEQPO> list = this.session.createQuery(" from com.js.oa.module.po.ModuleSEQPO po where po.id = " + 
          Long.valueOf(caseId)).list();
      ModuleSEQPO po = (list != null && list.size() > 0) ? 
        list.get(0) : null;
      if (po != null) {
        this.session.delete(po);
        this.session.flush();
      } 
      retBool = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retBool;
  }
  
  public String getViewScope(String tblId, String orgIdString, String userId, String orgId, String rightType) throws HibernateException {
    String scope = "";
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      List<Object[]> list = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightType = '" + 
          rightType + 
          "' and  (bbb.rightName = '维护' or bbb.rightName = '查看')  and ccc.empId = " + 
          userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        for (int si = 0; si < list.size(); si++) {
          String singleScope = "";
          buffer = new StringBuffer();
          Object[] obj = list.get(si);
          String scopeType = obj[0].toString();
          if (!scopeType.equals("0")) {
            buffer.append("(");
            if (scopeType.equals("1")) {
              buffer.append(String.valueOf(tblId) + "_OWNER = ").append(userId)
                .append(" or ");
            } else if (scopeType.equals("2")) {
              List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                  orgId + "$%'").list();
              for (int i = 0; i < orgList.size(); i++)
                buffer.append(String.valueOf(tblId) + "_ORG = ").append(orgList
                    .get(i)).append(" or "); 
              buffer.append(String.valueOf(tblId) + 
                  "_ORG = ").append(orgId).append(" or ");
            } else if (scopeType.equals("3")) {
              buffer.append(String.valueOf(tblId) + "_ORG = ").append(orgId)
                .append(" or ");
            } else if (scopeType.equals("4")) {
              String scopeScope = (obj[1] == null) ? "" : 
                obj[1].toString();
              if (!scopeScope.equals("")) {
                try {
                  scopeScope = getJuniorOrg(scopeScope);
                } catch (Exception e) {
                  e.printStackTrace();
                } 
                String sidelineWhere = "";
                Iterator it = this.session.createQuery("select po.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).iterate();
                if (it.hasNext()) {
                  Object slideOrgObj = it.next();
                  if (slideOrgObj != null) {
                    String sidelineOrgIds = slideOrgObj.toString();
                    if (!"".equals(sidelineOrgIds) && !"null".equals(sidelineOrgIds) && sidelineOrgIds.startsWith("*")) {
                      sidelineOrgIds = sidelineOrgIds.substring(1, sidelineOrgIds.length() - 1);
                      String[] sidelineOrgArr = sidelineOrgIds.split("\\*\\*");
                      for (int i = 0; i < sidelineOrgArr.length; i++) {
                        if (i == 0) {
                          sidelineWhere = String.valueOf(sidelineOrgArr[i]) + " in (" + scopeScope + ")";
                        } else {
                          sidelineWhere = String.valueOf(sidelineWhere) + " or " + sidelineOrgArr[i] + " in (" + scopeScope + ")";
                        } 
                      } 
                    } 
                  } 
                } 
                buffer.append(String.valueOf(tblId) + "_ORG in (").append(
                    scopeScope).append(") or ");
                if (!"".equals(sidelineWhere)) {
                  buffer.append("((");
                  buffer.append(sidelineWhere).append(") and ").append(tblId).append("_OWNER = ").append(userId);
                  buffer.append(") or ");
                } 
              } 
            } 
            singleScope = (buffer.length() > 0) ? (

              
              String.valueOf(buffer.toString().substring(0, buffer.toString().lastIndexOf("or"))) + ")") : "";
          } else if (scopeType.equals("0")) {
            buffer.append(" 1=1 ");
            singleScope = buffer.toString();
          } 
          if ("".equals(scope)) {
            scope = singleScope;
          } else {
            scope = String.valueOf(scope) + " or " + singleScope;
          } 
        } 
      } else {
        buffer.append("(");
        buffer.append(String.valueOf(tblId) + "_OWNER = ").append(userId);
        buffer.append(")");
        scope = buffer.toString();
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return "(" + scope + ")";
  }
  
  public Map getViewScopeMap(String userId, String orgIdString, String orgId, String rightCode) throws HibernateException {
    Map<Object, Object> resultMap = new HashMap<Object, Object>();
    begin();
    try {
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      List<Object[]> list = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          rightCode + "'  and ccc.empId = " + userId).list();
      boolean allScope = false;
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        resultMap.put("scopeType", scopeType);
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            resultMap.put("scopeScope", "$" + userId + "$");
          } else if (scopeType.equals("2")) {
            List<String> orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                orgId + "$%'").list();
            String orgStr = "";
            for (int i = 0; i < orgList.size(); i++)
              orgStr = String.valueOf(orgStr) + "*" + orgList
                .get(i) + "*"; 
            resultMap.put("scopeScope", orgStr);
          } else if (scopeType.equals("3")) {
            resultMap.put("scopeScope", orgId);
          } else if (scopeType.equals("4")) {
            String scopeScope = (obj[1] == null) ? "" : 
              obj[1].toString();
            if (!scopeScope.equals("")) {
              try {
                scopeScope = getJuniorOrg(scopeScope);
              } catch (Exception exception) {}
              resultMap.put("scopeScope", scopeScope);
            } 
          } 
        } else if (scopeType.equals("0")) {
          resultMap.put("scopeScope", " 1=1 ");
        } 
      } else {
        resultMap.put("scopeScope", "$" + userId + "$");
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return resultMap;
  }
  
  private String getJuniorOrg(String range) throws Exception {
    StringBuffer buffer = new StringBuffer(" where ");
    range = "*" + range + "*";
    String[] rangeArray = range.split("\\*\\*");
    int i = 0;
    for (i = 0; i < rangeArray.length; i++) {
      if (i > 0)
        buffer.append(" or "); 
      buffer.append(" org.orgIdString like '%$");
      buffer.append(rangeArray[i]);
      buffer.append("$%' ");
    } 
    List list = this.session.createQuery(
        "SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org " + 
        buffer.toString()).list();
    buffer = new StringBuffer();
    for (i = 0; i < list.size(); i++)
      buffer.append(list.get(i)).append(","); 
    range = buffer.toString();
    i = range.length();
    if (i > 0) {
      range = range.substring(0, i - 1);
    } else {
      range = "-1";
    } 
    return range;
  }
  
  public String getMenuScope(String domainId, String menuId) throws Exception {
    String menuscope = "";
    begin();
    try {
      ModuleMenuPO po = this.session.find(" from com.js.oa.module.po.ModuleMenuPO po where po.id = " + 
          Long.valueOf(menuId) + " and po.domainId=" + 
          Long.valueOf(domainId)).get(0);
      if (po != null)
        menuscope = String.valueOf(po.getMenuScope()) + "&" + (
          (po.getMenuViewOrg() == null) ? "" : 
          po.getMenuViewOrg()) + (
          (po.getMenuViewUser() == null) ? "" : 
          po.getMenuViewUser()) + (
          (po.getMenuViewGroup() == null) ? "" : 
          po.getMenuViewGroup()); 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return menuscope;
  }
  
  private void updateMenuRight(String menuName, String menuID, Long domainID) {
    try {
      DbOpt opt = new DbOpt();
      Statement stat = opt.getStatement();
      String sql = " update org_right set righttype='" + menuName + 
        "',rightclass='" + 
        menuName + "' where rightcode like '99-" + menuID + 
        "-%' and domain_id=" + domainID;
      stat.addBatch(sql);
      stat.executeBatch();
      opt.closeStatement();
      opt.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String setMenuDisplay(String menuId, String menuLevel, String menuLocation, String menuCount, String domainId, Integer show) throws Exception {
    begin();
    try {
      if ("9".equals(menuCount)) {
        String menuCode = this.session.createQuery("select po.menuCode from com.js.system.menu.po.MenuSetPO po where po.menuId=" + menuLocation).iterate().next().toString();
        String[] menuCodeArr = menuCode.split("_");
        int menuL = menuCodeArr.length;
        String subCode = menuCodeArr[menuL - 1];
        if (show.intValue() == 1) {
          String codeString = "";
          for (int i = 0; i < menuL; i++) {
            if (i == 0) {
              codeString = "'" + menuCodeArr[i] + "'";
            } else {
              String tmpCode = "";
              for (int j = 0; j <= i; j++) {
                if (j == 0) {
                  tmpCode = menuCodeArr[j];
                } else {
                  tmpCode = String.valueOf(tmpCode) + "_" + menuCodeArr[j];
                } 
              } 
              codeString = String.valueOf(codeString) + ",'" + tmpCode + "'";
            } 
          } 
          StringBuffer buffer = new StringBuffer("select po from com.js.system.menu.po.MenuSetPO po where po.domainId=");
          buffer.append(domainId)
            .append(" and (po.menuCode in(")
            .append(codeString)
            .append(") or po.menuCode like '%")
            .append(subCode)
            .append("_%')");
          String sysQuery = buffer.toString();
          Iterator<MenuSetPO> it = this.session.createQuery(sysQuery).iterate();
          buffer = new StringBuffer("-2");
          while (it.hasNext()) {
            MenuSetPO menuPO = it.next();
            menuPO.setInUse(show);
            buffer.append(",").append(menuPO.getMenuId());
          } 
          this.session.flush();
          String custQuery = "select po from com.js.oa.module.po.ModuleMenuPO po where po.menuLocation in(" + buffer.toString() + ") and po.domainId=" + domainId;
          it = this.session.createQuery(custQuery).iterate();
          while (it.hasNext()) {
            ModuleMenuPO custMenuPO = (ModuleMenuPO)it.next();
            custMenuPO.setMenuIsValide(show.intValue());
          } 
          custQuery = "select po from com.js.oa.module.po.ModuleMenuPO po where po.menuLocation =" + menuId + " and po.domainId=" + domainId;
          it = this.session.createQuery(custQuery).iterate();
          while (it.hasNext()) {
            ModuleMenuPO custMenuPO = (ModuleMenuPO)it.next();
            custMenuPO.setMenuIsValide(show.intValue());
          } 
          this.session.flush();
        } else {
          String sysQuery = 
            "select po from com.js.system.menu.po.MenuSetPO po where po.domainId=" + domainId + " and (po.menuId=" + 
            menuLocation + " or po.menuCode like '%" + 
            subCode + "_%')";
          Iterator<MenuSetPO> it = this.session.createQuery(sysQuery).iterate();
          StringBuffer buffer = new StringBuffer("-2");
          while (it.hasNext()) {
            MenuSetPO menuPO = it.next();
            menuPO.setInUse(show);
            buffer.append(",").append(menuPO.getMenuId());
          } 
          this.session.flush();
          String custQuery = "select po from com.js.oa.module.po.ModuleMenuPO po where po.menuLocation in(" + buffer.toString() + ") and po.domainId=" + domainId;
          it = this.session.createQuery(custQuery).iterate();
          while (it.hasNext()) {
            ModuleMenuPO custMenuPO = (ModuleMenuPO)it.next();
            custMenuPO.setMenuIsValide(show.intValue());
          } 
          custQuery = "select po from com.js.oa.module.po.ModuleMenuPO po where po.menuLocation =" + menuId + " and po.domainId=" + domainId;
          it = this.session.createQuery(custQuery).iterate();
          while (it.hasNext()) {
            ModuleMenuPO custMenuPO = (ModuleMenuPO)it.next();
            custMenuPO.setMenuIsValide(show.intValue());
          } 
          this.session.flush();
        } 
      } else if ("8".equals(menuCount)) {
        String sysQuery = "select po from com.js.system.menu.po.MenuSetPO po where po.menuId=" + menuLocation;
        String custQuery = "select po from com.js.oa.module.po.ModuleMenuPO po where po.menuLocation=" + menuLocation + " or po.menuLevel like '%_" + menuLocation + "-%'";
        Iterator<MenuSetPO> it = this.session.createQuery(sysQuery).iterate();
        while (it.hasNext()) {
          MenuSetPO menuPO = it.next();
          menuPO.setInUse(show);
        } 
        this.session.flush();
        it = this.session.createQuery(custQuery).iterate();
        while (it.hasNext()) {
          ModuleMenuPO custMenuPO = (ModuleMenuPO)it.next();
          custMenuPO.setMenuIsValide(show.intValue());
        } 
        this.session.flush();
      } else {
        int curMenuCount = 0;
        int degree = 0, curDegree = 0;
        ModuleMenuPO custMenuPO = (ModuleMenuPO)this.session.get(
            ModuleMenuPO.class, 
            Long.valueOf(menuId));
        Long parentId = custMenuPO.getMenuBlone();
        curMenuCount = custMenuPO.getMenuCount();
        String curMenuLevel = custMenuPO.getMenuLevel();
        curDegree = (curMenuLevel.split("-")).length;
        custMenuPO.getMenuBlone();
        curMenuLevel = custMenuPO.getMenuLevel();
        Long mainTbId = custMenuPO.getMenuSubTableMap();
        custMenuPO.setMenuIsValide(show.intValue());
        int flag = 0;
        Iterator<ModuleMenuPO> it = this.session.createQuery("select po from com.js.oa.module.po.ModuleMenuPO po where po.menuMaintenanceSubTableMap=" + mainTbId + " order by po.menuLevel").iterate();
        while (it.hasNext()) {
          custMenuPO = it.next();
          if (flag == 0 && !menuId.equals(custMenuPO.getId()))
            continue; 
          flag = 1;
          degree = (custMenuPO.getMenuLevel().split("-")).length;
          if (flag == 1 && !menuId.equals(custMenuPO.getId().toString()) && curDegree <= (custMenuPO.getMenuLevel().split("-")).length)
            break; 
          custMenuPO.setMenuIsValide(show.intValue());
        } 
        if (show.intValue() == 1)
          for (int i = 0; i < 5; i++) {
            custMenuPO = (ModuleMenuPO)this.session.get(
                ModuleMenuPO.class, parentId);
            parentId = custMenuPO.getMenuBlone();
            curMenuCount = custMenuPO.getMenuCount();
            custMenuPO.setMenuIsValide(show.intValue());
            if (curMenuCount == 8) {
              MenuSetPO menuPO = (MenuSetPO)this.session.get(MenuSetPO.class, parentId);
              menuPO.setInUse(show);
              break;
            } 
          }  
        this.session.flush();
      } 
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        this.session.close(); 
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Boolean showMenu(String menuCode, String domainId) throws Exception {
    Boolean show = Boolean.FALSE;
    begin();
    try {
      int use = ((Integer)this.session.createQuery("select po.inUse from com.js.system.menu.po.MenuSetPO po where po.menuCode='" + menuCode + "' and po.domainId=" + domainId).iterate().next()).intValue();
      if (use == 1)
        show = Boolean.TRUE; 
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        this.session.close(); 
      throw ex;
    } 
    return show;
  }
  
  public String getShowMenu(String menuCode, String domainId) throws Exception {
    StringBuffer buffer = new StringBuffer(",");
    begin();
    try {
      List<E> list = null;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("db2") >= 0) {
        list = this.session.createQuery("select po.menuCode from com.js.system.menu.po.MenuSetPO po where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', po.menuCode), ','),'" + menuCode + "' )>0  and po.inUse=1 and po.domainId=" + domainId).list();
      } else if (databaseType.indexOf("mysql") >= 0) {
        list = this.session.createQuery("select po.menuCode from com.js.system.menu.po.MenuSetPO po where '" + menuCode + "' like concat('%,', po.menuCode,',%')  and po.inUse=1 and po.domainId=0" + domainId).list();
      } else {
        list = this.session.createQuery("select po.menuCode from com.js.system.menu.po.MenuSetPO po where '" + menuCode + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%,', po.menuCode), ',%')  and po.inUse=1 and po.domainId=" + domainId).list();
      } 
      for (int i = 0; i < list.size(); i++)
        buffer.append(list.get(i).toString()).append(","); 
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        this.session.close(); 
      throw ex;
    } 
    return buffer.toString();
  }
  
  public String getMenuNameByIds(String ids) throws Exception {
    StringBuffer buffer = new StringBuffer();
    if (ids != null && !"".equals(ids)) {
      if (ids.charAt(ids.length() - 1) == ',')
        ids = ids.substring(0, ids.length() - 1); 
      begin();
      try {
        List list = this.session.createQuery("select po.menuName from com.js.oa.module.po.ModuleMenuPO po where po.id in(" + ids + ")").list();
        for (int i = 0; list != null && i < list.size(); i++)
          buffer.append(list.get(i)).append(","); 
        this.session.close();
      } catch (Exception ex) {
        this.session.close();
      } 
    } 
    return buffer.toString();
  }
  
  public Object[] getMenuActionAndPara(String menuId, String flag) throws Exception {
    StringBuffer buffer = new StringBuffer();
    Object[] str = new Object[2];
    try {
      List<Object[]> list;
      begin();
      if ("1".equals(flag)) {
        list = this.session.createQuery("select po.menuAction,po.menuActionParams1 from com.js.oa.module.po.ModuleMenuPO po where po.menuBlone=" + menuId).list();
      } else {
        list = this.session.createQuery("select po.menuAction,po.menuActionParams1 from com.js.oa.module.po.ModuleMenuPO po where po.id=" + menuId).list();
      } 
      if (list != null)
        str = list.get(0); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
    } 
    return str;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public String isJx(String flowId) {
    String sql = "select isjx from jsf_workflowprocess where wf_workflowprocess_id=" + flowId;
    String isjx = "0";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        isjx = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return isjx;
  }
  
  public String getDefaultViewScope(String scopeType, String scopeRange, String userId, String tblId, String orgId) {
    String scopeReturn = "";
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    try {
      begin();
      if (scopeType.equals("1")) {
        buffer.append(String.valueOf(tblId) + "_OWNER = ").append(userId)
          .append(" or ");
      } else if (scopeType.equals("2")) {
        List orgList = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
            orgId + "$%'").list();
        for (int i = 0; i < orgList.size(); i++)
          buffer.append(String.valueOf(tblId) + "_ORG = ").append(orgList
              .get(i)).append(" or "); 
        buffer.append(String.valueOf(tblId) + 
            "_ORG = ").append(orgId).append(" or ");
      } else if (scopeType.equals("3")) {
        buffer.append(String.valueOf(tblId) + "_ORG = ").append(orgId)
          .append(" or ");
      } else if (scopeType.equals("4")) {
        String scopeScope = (scopeRange == null) ? "" : scopeRange;
        if (!scopeScope.equals("")) {
          try {
            scopeScope = getJuniorOrg(scopeScope);
          } catch (Exception e) {
            e.printStackTrace();
          } 
          String sidelineWhere = "";
          Iterator it = this.session.createQuery("select po.sidelineOrg from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + userId).iterate();
          if (it.hasNext()) {
            Object slideOrgObj = it.next();
            if (slideOrgObj != null) {
              String sidelineOrgIds = slideOrgObj.toString();
              if (!"".equals(sidelineOrgIds) && !"null".equals(sidelineOrgIds) && sidelineOrgIds.startsWith("*")) {
                sidelineOrgIds = sidelineOrgIds.substring(1, sidelineOrgIds.length() - 1);
                String[] sidelineOrgArr = sidelineOrgIds.split("\\*\\*");
                for (int i = 0; i < sidelineOrgArr.length; i++) {
                  if (i == 0) {
                    sidelineWhere = String.valueOf(sidelineOrgArr[i]) + " in (" + scopeScope + ")";
                  } else {
                    sidelineWhere = String.valueOf(sidelineWhere) + " or " + sidelineOrgArr[i] + " in (" + scopeScope + ")";
                  } 
                } 
              } 
            } 
          } 
          buffer.append(String.valueOf(tblId) + "_ORG in (").append(
              scopeScope).append(") or ");
          if (!"".equals(sidelineWhere)) {
            buffer.append("((");
            buffer.append(sidelineWhere).append(") and ").append(tblId).append("_OWNER = ").append(userId);
            buffer.append(") or ");
          } 
        } 
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
      this.transaction = null;
      this.session = null;
    } 
    scopeReturn = (buffer.length() > 2) ? (

      
      String.valueOf(buffer.toString().substring(0, buffer.toString().lastIndexOf("or"))) + ")") : "";
    return scopeReturn;
  }
}
