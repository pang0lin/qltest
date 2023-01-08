package com.js.system.menu.bean;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.AuditMenuPO;
import com.js.oa.audit.service.AuditLogBD;
import com.js.oa.audit.service.AuditMenuBD;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.system.menu.po.MenuSetPO;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class MenuEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map loadMenu(Long menuId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    MenuSetPO po = null;
    begin();
    try {
      po = (MenuSetPO)this.session.load(MenuSetPO.class, menuId);
      result.put("menuSetPO", po);
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getMenuList(String userId, String orgIdString) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer();
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("(po.menuViewUser is null or po.menuViewUser='' ) and (po.menuViewOrg is null or po.menuViewOrg='') and (po.menuViewGroup is null or po.menuViewGroup=''))");
      list = this.session.createQuery("select po.menuName from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuOrder").list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getSubMenuList(Long parentId, String userId, String orgIdString) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("(");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("po.menuViewUser is null and po.menuViewOrg is null and po.menuViewGroup is null)");
      buffer.append(") and po.menuLevel=1 and po.menuId>5000 and po.menuParent=").append(parentId);
      list = this.session.createQuery("select po.menuName,po.menuURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuId").list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getDeskTop1(String userId, String orgIdString) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("(");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("po.menuViewUser is null and po.menuViewOrg is null and po.menuViewGroup is null)");
      buffer.append(") and po.deskTop1=1 and po.menuId>5000 ");
      list = this.session.createQuery("select po.menuId,po.menuName,po.menuURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuId").list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getDeskTop2(String userId, String orgIdString) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("(");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("po.menuViewUser is null and po.menuViewOrg is null and po.menuViewGroup is null)");
      buffer.append(") and po.deskTop2=1 and po.menuId>5000 ");
      list = this.session.createQuery("select po.menuId,po.menuName,po.menuURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuId").list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Integer add(MenuSetPO po, String menuViewId, String menuView, String domainId) throws Exception {
    int result = 2;
    begin();
    try {
      po.setMenuView(menuView);
      po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
      po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
      po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
      Long menuId = (Long)this.session.save(po);
      if ("0".equals(po.getMenuParent())) {
        po.setMenuLevel("0");
        po.setMenuIdString(menuId.toString());
      } else {
        po.setMenuLevel("1");
        po.setMenuIdString(String.valueOf(po.getMenuParent()) + "-" + menuId);
      } 
      po.setDomainId(domainId);
      this.session.flush();
      result = 0;
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.transaction = null;
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public Integer update(MenuSetPO modiPO, Long menuId, String menuViewId, String menuView, String domainId) throws Exception {
    int result = 2;
    begin();
    try {
      MenuSetPO po = (MenuSetPO)this.session.load(MenuSetPO.class, menuId);
      if (modiPO.getMenuParent() != null)
        if ("0".equals(modiPO.getMenuParent())) {
          po.setMenuLevel("0");
          po.setMenuOrder(menuId.toString());
          po.setMenuParent(modiPO.getMenuParent());
        } else {
          po.setMenuLevel("1");
          po.setMenuOrder(String.valueOf(modiPO.getMenuParent()) + "-" + menuId);
          po.setMenuParent(modiPO.getMenuParent());
        }  
      po.setDeskTop1(modiPO.getDeskTop1());
      po.setDeskTop2(modiPO.getDeskTop2());
      po.setMenuURL(modiPO.getMenuURL());
      if (modiPO.getMenuName() != null)
        po.setMenuName(modiPO.getMenuName()); 
      po.setMenuView(menuView);
      po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
      po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
      po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
      po.setInUse(modiPO.getInUse());
      po.setMenuOrder(modiPO.getMenuOrder());
      this.session.flush();
      Iterator<ModuleMenuPO> it = this.session.createQuery("select po from com.js.oa.module.po.ModuleMenuPO po where po.domainId =" + domainId + " and po.menuLocation=" + menuId.toString()).iterate();
      ModuleMenuPO ppo = it.next();
      ppo.setMenuIsValide(po.getInUse().intValue());
      this.session.flush();
      result = 0;
    } catch (Exception ex) {
      System.out.println("---------------------------------------------");
      ex.printStackTrace();
      System.out.println("---------------------------------------------");
      throw ex;
    } finally {
      this.transaction = null;
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public List getTopMenu(String domainId) throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select po.menuId,po.menuName from com.js.system.menu.po.MenuSetPO po where po.domainId=" + domainId + " and po.isSystemInit=0 and po.menuLevel=0 order by po.menuId").list();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Integer delete(Long delId) throws Exception {
    int result = 2;
    begin();
    try {
      this.session.delete("from com.js.system.menu.po.MenuSetPO po where po.menuParent=" + delId + " or po.menuId=" + delId);
      this.session.flush();
      result = 0;
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return new Integer(result);
  }
  
  public List getMenuList(String userId, String orgIdString, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      DomainVO domainVO = (DomainVO)this.session.load(DomainVO.class, Long.valueOf(domainId));
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("(");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("(po.menuViewUser is null or po.menuViewUser='' ) and (po.menuViewOrg is null or po.menuViewOrg='') and (po.menuViewGroup is null or po.menuViewGroup=''))");
      buffer.append(") and po.domainId=").append(domainId).append(" and po.inUse=1 and po.isSystemInit=1");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        list = this.session.createQuery("select po.menuName, po.leftURL, po.rightURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " and '" + domainVO.getModule() + "' like concat('%', po.menuCode, '%') order by po.menuOrder").list();
      } else if (databaseType.indexOf("db2") >= 0) {
        list = this.session.createQuery("select po.menuName, po.leftURL, po.rightURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " and locate(po.menuCode,'" + domainVO.getModule() + "')>0 order by po.menuOrder").list();
      } else {
        list = this.session.createQuery("select po.menuName, po.leftURL, po.rightURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " and '" + domainVO.getModule() + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%',po.menuCode),'%') order by po.menuOrder").list();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getUserTopMenu(String userId, String orgIdString, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("(");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%").append(userId).append("%' or (");
      buffer.append("(po.menuViewUser is null or po.menuViewUser='') and (po.menuViewOrg is null or po.menuViewOrg='') and (po.menuViewGroup is null or po.menuViewGroup=''))");
      buffer.append(") and po.menuLevel=0 and po.isSystemInit=0 and po.inUse=1 and po.domainId=").append(domainId);
      list = this.session.createQuery("select po.menuId,po.menuName,po.menuURL from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuOrder").list();
    } catch (Exception ex) {
      System.out.println("---------------------------------------------");
      ex.printStackTrace();
      System.out.println("---------------------------------------------");
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllUserTopMenu(String userId, String orgIdString, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("((");
      int i;
      for (i = 0; i < groupList.size(); i++)
        buffer.append(" po.menuViewGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.menuViewOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      buffer.append(" po.menuViewUser like '%$").append(userId).append("$%'");
      buffer.append(") or ( ( po.menuViewGroup='null' or po.menuViewGroup is null or po.menuViewGroup='') and (po.menuViewOrg ='null' or po.menuViewOrg is null or po.menuViewOrg ='' ) and  ( po.menuViewUser ='null' or po.menuViewUser is null or po.menuViewUser ='') )) and po.menuLevel=0 and po.inUse=1 and po.domainId=").append(domainId);
      list = this.session.createQuery("select po.menuName, po.leftURL, po.rightURL,po.isSystemInit,po.menuId,po.menuURL,po.menuCode from com.js.system.menu.po.MenuSetPO po where " + buffer.toString() + " order by po.menuOrder").list();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getAllTopMenu() throws HibernateException {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select po from com.js.system.menu.po.MenuSetPO po where po.menuLevel = '0'").list();
    } catch (RuntimeException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void setTotMenu(String id, String inuse, String userOrgGroup, String classUserName, String menuName, String menuOrder) throws HibernateException, SQLException {
    Connection conn = null;
    PreparedStatement pStmt = null;
    String userIds = StringSplit.splitWith(userOrgGroup, "$", "*@");
    String orgIds = StringSplit.splitWith(userOrgGroup, "*", "@$");
    String groupIds = StringSplit.splitWith(userOrgGroup, "@", "$*");
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pStmt = conn.prepareStatement("update JSDB.menu_sys set MENUVIEWUSER=?,MENUVIEWORG=?,MENUVIEWGROUP=?,MENUVIEW=?,INUSE=? ,MENUNAME=? ,MENUORDER=? where MENU_ID=? ");
      pStmt.setString(1, userIds);
      pStmt.setString(2, orgIds);
      pStmt.setString(3, groupIds);
      pStmt.setString(4, classUserName);
      pStmt.setInt(5, Integer.parseInt(inuse));
      pStmt.setString(6, menuName);
      pStmt.setString(7, menuOrder);
      pStmt.setLong(8, Long.parseLong(id));
      pStmt.executeUpdate();
      pStmt.close();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select issysteminit from menu_sys where menu_id=" + id);
      String temp = "1";
      if (rs.next())
        temp = rs.getString(1); 
      rs.close();
      if ("0".equals(temp)) {
        temp = "-1";
        rs = stmt.executeQuery("select id from menu_ext where menu_blone='" + id + "' and menu_location='" + id + "'");
        if (rs.next())
          temp = rs.getString(1); 
        rs.close();
        stmt.executeUpdate("update menu_ext set menu_scope='" + classUserName + "',menu_viewuser='" + userIds + "',menu_vieworg='" + orgIds + "',menu_viewgroup='" + groupIds + "',menu_isvalide=" + inuse + " where id=" + temp);
      } 
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
    } 
  }
  
  public boolean audit(MenuSetPO setPo, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
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
      auditLog.setAuditModule(new Long(7L));
      auditLog.setAuditStatus(Integer.valueOf(0));
      auditLog.setIschecked(Integer.valueOf(0));
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditMenuBD auditMenuBD = new AuditMenuBD();
      begin();
      MenuSetPO po = (MenuSetPO)this.session.load(MenuSetPO.class, id);
      po.setMenuId(setPo.getMenuId());
      po.setInUse(setPo.getInUse());
      if ("update".equals(opreate)) {
        String userOrgGroup = setPo.getUserOrgGroup();
        String userIds = StringSplit.splitWith(userOrgGroup, "$", "*@");
        String orgIds = StringSplit.splitWith(userOrgGroup, "*", "@$");
        String groupIds = StringSplit.splitWith(userOrgGroup, "@", "$*");
        po.setMenuViewUser(userIds);
        po.setMenuViewOrg(orgIds);
        po.setMenuViewGroup(groupIds);
        po.setMenuView(setPo.getMenuView());
        po.setMenuName(setPo.getMenuName());
        po.setMenuOrder(setPo.getMenuOrder());
      } 
      AuditMenuPO auditMenuPO = (AuditMenuPO)FillBean.transformOTO(po, AuditMenuPO.class);
      auditMenuPO.setMenuId(id);
      auditMenuPO.setAuditLogId(auditLogId);
      auditMenuPO.setOperationType(opreate);
      Long auditIpId = auditMenuBD.saveAuditMenu(auditMenuPO);
      if ("insert".equals(opreate))
        opreate = "新增"; 
      if ("update".equals(opreate))
        opreate = "修改"; 
      if ("delete".equals(opreate))
        opreate = "删除"; 
      if ("enable".equals(opreate))
        opreate = "启用"; 
      if ("disable".equals(opreate))
        opreate = "禁用"; 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String userName = (String)httpsession.getAttribute("userName");
      msRemindBeann.auditMsRemind(Long.valueOf((String)httpsession.getAttribute("userId")).longValue(), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          1, 1, new Date(), "审计提醒：" + userName + opreate + "菜单管理\"" + po.getMenuName() + "\"", "audit", auditLogId.longValue(), "AuditMenuAction.do?action=forshenji&id=" + auditLogId + "&flag=fromRemind");
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public boolean autoAudit(MenuSetPO setPo, String opreate, Long id, HttpServletRequest httpServletRequest) throws Exception {
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
      auditLog.setAuditModule(new Long(7L));
      auditLog.setAuditStatus(Integer.valueOf(1));
      auditLog.setIschecked(Integer.valueOf(1));
      auditLog.setCheckEmpid(new Long(0L));
      auditLog.setCheckEmpname("系统自动审核");
      auditLog.setCheckTime(ts);
      AuditLogBD auditLogBD = new AuditLogBD();
      Long auditLogId = auditLogBD.saveAuditLog(auditLog);
      AuditMenuBD auditMenuBD = new AuditMenuBD();
      begin();
      MenuSetPO po = (MenuSetPO)this.session.load(MenuSetPO.class, id);
      po.setMenuId(setPo.getMenuId());
      po.setInUse(setPo.getInUse());
      if ("update".equals(opreate)) {
        String userOrgGroup = setPo.getUserOrgGroup();
        String userIds = StringSplit.splitWith(userOrgGroup, "$", "*@");
        String orgIds = StringSplit.splitWith(userOrgGroup, "*", "@$");
        String groupIds = StringSplit.splitWith(userOrgGroup, "@", "$*");
        po.setMenuViewUser(userIds);
        po.setMenuViewOrg(orgIds);
        po.setMenuViewGroup(groupIds);
        po.setMenuView(setPo.getMenuView());
        po.setMenuName(setPo.getMenuName());
        po.setMenuOrder(setPo.getMenuOrder());
      } 
      AuditMenuPO auditMenuPO = (AuditMenuPO)FillBean.transformOTO(po, AuditMenuPO.class);
      auditMenuPO.setMenuId(id);
      auditMenuPO.setAuditLogId(auditLogId);
      auditMenuPO.setOperationType(opreate);
      Long auditIpId = auditMenuBD.saveAuditMenu(auditMenuPO);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        this.session.close(); 
    } 
    return result;
  }
  
  public void setTotMenu1(String ids, String inuse) throws HibernateException, SQLException {
    Connection conn = null;
    PreparedStatement pStmt = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pStmt = conn.prepareStatement("update JSDB.menu_sys set INUSE=? where MENU_ID in ( " + ids + ")");
      pStmt.setString(1, inuse);
      pStmt.executeUpdate();
      pStmt.close();
      stmt = conn.createStatement();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        String temp = "-1";
        ResultSet rs = stmt.executeQuery("select id from menu_ext where menu_blone='" + idsArr[i] + "' and menu_location='" + idsArr[i] + "'");
        if (rs.next())
          temp = rs.getString(1); 
        rs.close();
        if (!"-1".equals(temp))
          stmt.executeUpdate("update menu_ext set menu_isvalide=" + inuse + " where id=" + temp); 
      } 
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        conn.close(); 
      e.printStackTrace();
    } 
  }
  
  public void executeSQL(String sql) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
