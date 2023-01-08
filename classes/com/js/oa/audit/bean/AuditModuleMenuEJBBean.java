package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditModuleMenuPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.module.action.ModuleMenuActionForm;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.po.SystemMenuPO;
import com.js.oa.module.service.ModuleMenuAllService;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.module.vo.ListItem;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuditModuleMenuEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditModuleMenu(AuditModuleMenuPO auditModuleMenuPO) throws Exception {
    Long result = new Long(0L);
    begin();
    try {
      result = (Long)this.session.save(auditModuleMenuPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public AuditModuleMenuPO loadAuditModuleMenu(long auditModuleMenuId) throws Exception {
    begin();
    AuditModuleMenuPO auditModuleMenuPO = null;
    try {
      auditModuleMenuPO = (AuditModuleMenuPO)this.session.get(AuditModuleMenuPO.class, Long.valueOf(auditModuleMenuId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return auditModuleMenuPO;
  }
  
  public void auditModuleMenu(String auditModuleMenuId, String logId, String auditSattus, String operationType, HttpServletRequest request) throws Exception {
    try {
      HttpSession httpsession = request.getSession(true);
      begin();
      AuditModuleMenuPO po = loadAuditModuleMenu(Long.valueOf(auditModuleMenuId).longValue());
      if (po.getMenuAction() == null)
        po.setMenuAction(""); 
      if (po.getMenuActionParams1() == null)
        po.setMenuActionParams1(""); 
      if (po.getMenuDefQueryCondition() == null)
        po.setMenuDefQueryCondition(""); 
      if (po.getMenuDefQueryOrder() == null)
        po.setMenuDefQueryOrder(""); 
      if (po.getMenuRelationRefFlow() == null)
        po.setMenuRelationRefFlow(""); 
      if ("1".equals(auditSattus)) {
        ModuleMenuActionForm form = (ModuleMenuActionForm)FillBean.transformOTO(po, ModuleMenuActionForm.class);
        form.setId(po.getMenuExtId());
        begin();
        if ("insert".equals(operationType)) {
          addModuleMenu(form, request);
          this.session.flush();
        } 
        if ("update".equals(operationType)) {
          updateAuditModuleMenu(form, request);
          this.session.flush();
        } 
        if ("delete".equals(operationType)) {
          String ids = po.getMenuExtId().toString();
          ModuleMenuService bd = new ModuleMenuService();
          if (ids != null && ids.length() > 0)
            bd.deleteMenuRights(ids); 
          bd.delBatchCustmizeMenus(po.getDomainId().toString(), ids);
          if (!(new ModuleMenuAllService()).delBatchMenuSet(po.getDomainId().toString(), ids))
            request.setAttribute("errs", "当前菜单包含子菜单！"); 
          this.session.flush();
        } 
        if ("showMenu".equals(operationType) || "hideMenu".equals(operationType)) {
          ModuleMenuService bd = new ModuleMenuService();
          bd.setMenuDisplay(po.getMenuExtId().toString(), po.getMenuLevel(), po.getMenuLocation().toString(), String.valueOf(po.getMenuCount()), 
              po.getDomainId().toString(), "showMenu".equals(operationType) ? 1 : 0);
        } 
      } 
      String databaseType = SystemCommon.getDatabaseType();
      String dateString = "now()";
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update audit_log set ISCHECKED=1,AUDIT_MODULE=8,AUDIT_STATUS=" + auditSattus + ",CHECK_EMPID=" + httpsession.getAttribute("userId") + ",CHECK_EMPNAME='" + 
        httpsession.getAttribute("userName") + "',CHECK_TIME= " + dateString + "  where LOG_ID=" + logId;
      updateYourSql(sql);
      if ("insert".equals(operationType))
        operationType = "新增"; 
      if ("update".equals(operationType))
        operationType = "修改"; 
      if ("delete".equals(operationType))
        operationType = "删除"; 
      if ("hideMenu".equals(operationType))
        operationType = "隐藏"; 
      if ("showMenu".equals(operationType))
        operationType = "显示"; 
      String sqlq = "select po.logId,po.submitEmpid from com.js.oa.audit.po.AuditLog po where po.logId=" + logId;
      MsManageBD msBD = new MsManageBD();
      List<Object[]> msList = msBD.getListByYourSQL(sqlq);
      String submitEmpid = null;
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          submitEmpid = obj[1].toString();
        }  
      String typeString = "";
      if ("1".equals(auditSattus)) {
        typeString = "通过审核";
      } else {
        typeString = "未通过审核";
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      msRemindBeann.auditRemind(Long.valueOf(httpsession.getAttribute("userId").toString()), httpsession.getAttribute("orgId").toString(), httpsession.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + operationType + "的扩展菜单管理“" + po.getMenuName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", submitEmpid);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.session != null) {
        this.session.close();
        this.session = null;
      } 
      this.transaction = null;
    } 
  }
  
  public boolean addModuleMenu(ModuleMenuActionForm form, HttpServletRequest request) throws Exception {
    boolean result = false;
    ModuleMenuService bd = new ModuleMenuService();
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    ModuleMenuPO pPo = null;
    if (form.getChannel() != null && form.getChannel().length() > 0) {
      ChannelBD channelBD = new ChannelBD();
      channelBD.addUserChannel(form.getChannel(), 
          form.getChannelOrder(), 
          form.getDomainId().toString());
    } else if (-1L == form.getMenuBlone().longValue() || 0L == form.getMenuBlone().longValue()) {
      try {
        ModuleMenuAllService mms = new ModuleMenuAllService();
        SystemMenuPO oMenu = setMenuSetPo(form, form.getDomainId().toString(), request);
        Long retNo = mms.saveTopMenu(oMenu);
        if (retNo.longValue() > 0L) {
          ModuleMenuPO po = new ModuleMenuPO();
          po.setMenuName(form.getMenuName());
          po.setMenuLocation(retNo);
          po.setMenuAction(form.getMenuAction());
          po.setMenuActionParams1(form.getMenuActionParams1());
          po.setMenuActionParams2(form.getMenuActionParams2());
          po.setMenuActionParams3(form.getMenuActionParams3());
          po.setMenuActionParams4(form.getMenuActionParams4());
          po.setMenuActionParams4Value(form.getMenuActionParams4Value());
          po.setMenuBlone(retNo);
          po.setMenuLevel("_" + retNo.toString());
          po.setDomainId(form.getDomainId());
          po.setMenuCount(8);
          po.setMenuOpenStyle(form.getMenuOpenStyle());
          po.setMenuListTableMap(new Long(0L));
          po.setMenuRefFlow("|");
          po.setMenuScope(form.getMenuScope());
          po.setMenuViewUser(form.getMenuViewUser());
          po.setMenuViewGroup(form.getMenuViewGroup());
          po.setMenuViewOrg(form.getMenuViewOrg());
          po.setMenuListQueryConditionElements(form.getMenuListQueryConditionElements());
          po.setMenuListDisplayElements(form.getMenuListDisplayElements());
          po.setMenuIsValide(form.getMenuIsValide());
          po.setMenuMaintenanceSubTableMap(po.getMenuBlone());
          po.setParentOrder(form.getParentOrder());
          po.setMenuAdd(form.getMenuAdd());
          po.setMenuDel(form.getMenuDel());
          po.setMenuImp(form.getMenuImp());
          po.setMenuExp(form.getMenuExp());
          po.setMenuQuery(form.getMenuQuery());
          bd.saveMenuConfig(po);
        } 
      } catch (Exception ex) {
        ex.printStackTrace();
        throw ex;
      } 
    } else {
      pPo = bd.loadParentMenuConfiger(form.getDomainId().toString(), 
          form.getMenuBlone().toString());
      ModuleMenuPO po = new ModuleMenuPO();
      injectPo(po, form.getDomainId(), form, curUserId, form.getModel());
      if (pPo.getMenuMaintenanceSubTableMap() != null && 
        pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
        po.setMenuMaintenanceSubTableMap(pPo.getMenuMaintenanceSubTableMap()); 
      setMenuOrderRule(pPo, po, form.getDomainId().toString(), bd, form);
      po.setParentOrder(pPo.getParentOrder());
      if (form.getZorder() != null && form.getMenuBlone() != null && 
        form.getMenuBlone().longValue() > 0L && 
        form.getMenuLocation() != null && 
        form.getMenuLocation().intValue() > 0)
        menuZorder(po, request, form, form.getDomainId().toString(), "0"); 
      Long menuId = bd.saveMenuConfig(po);
      if (menuId != null && menuId.longValue() > 0L)
        if (po.getMenuListTableMap() != null && 
          po.getMenuListTableMap().longValue() > 0L)
          addMenuListRight(menuId, po.getMenuName(), form.getDomainId(), bd);  
    } 
    request.setAttribute("validate", request.getParameter("validate"));
    return result;
  }
  
  private SystemMenuPO setMenuSetPo(ModuleMenuActionForm form, String domainId, HttpServletRequest request) {
    if (form != null) {
      SystemMenuPO po = new SystemMenuPO();
      if (form.getMenuLocation() != null && 
        form.getMenuLocation().longValue() > 0L) {
        po.setMenuId(form.getMenuLocation());
        po.setLeftURL(
            "/jsoa/ModuleMenuAction.do?menuid=" + 
            form.getMenuLocation());
        po.setMenuIdString(form.getMenuLocation().toString());
      } 
      setDefMenuRightUrl(form, domainId, po, request);
      po.setIsSystemInit(Integer.valueOf("0"));
      po.setInUse(new Integer(form.getMenuIsValide()));
      po.setMenuView(form.getMenuScope());
      po.setMenuViewUser(form.getMenuViewUser());
      po.setMenuViewGroup(form.getMenuViewGroup());
      po.setMenuViewOrg(form.getMenuViewOrg());
      po.setMenuName(form.getMenuName());
      po.setMenuLevel("0");
      po.setMenuParent("0");
      po.setDeskTop1("0");
      po.setDeskTop2("0");
      po.setDomainId(domainId);
      po.setMenuOrder(String.valueOf(form.getParentOrder()));
      return po;
    } 
    return null;
  }
  
  private void setDefMenuRightUrl(ModuleMenuActionForm form, String domainId, SystemMenuPO po, HttpServletRequest request) {
    if (form.getDefHref() != null && form.getDefHref().length() > 0)
      if ("-1".equals(form.getDefHref())) {
        po.setMenuURL((form.getMenuAction().indexOf("&") > 0 || 
            !"-1".equals(form.getDefHref())) ? (
            String.valueOf(form.getMenuAction()) + "&d=" + form.getDefHref()) : (
            String.valueOf(form.getMenuAction()) + "?d=" + form.getDefHref()));
        po.setRightURL((form.getMenuAction().indexOf("&") > 0 || 
            !"-1".equals(form.getDefHref())) ? (
            String.valueOf(form.getMenuAction()) + "&d=" + form.getDefHref()) : (
            String.valueOf(form.getMenuAction()) + "?d=" + form.getDefHref()));
      } else if (form.getDefHref().indexOf("-") <= 0) {
        ModuleMenuAllService setBd = new ModuleMenuAllService();
        SystemMenuPO setPo = setBd.loadMenuSet(form.getDefHref());
        if (setPo != null) {
          po.setRightURL(String.valueOf(setPo.getRightURL()) + ((
              form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
          po.setMenuURL(String.valueOf(setPo.getRightURL()) + (
              (form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
        } 
      } else {
        ModuleMenuService bd = new ModuleMenuService();
        ModuleMenuPO ppo = 
          bd.loadMenuConfig(domainId, 
            form.getDefHref()).get(
            0);
        if (ppo != null) {
          po.setMenuURL(String.valueOf(bd.confirmAction(ppo, request)) + ((
              form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
          po.setRightURL(String.valueOf(bd.confirmAction(ppo, request)) + (
              (form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
        } 
      }  
  }
  
  private void injectPo(ModuleMenuPO po, Long domainId, ModuleMenuActionForm form, String curUserId, String mode) {
    if (po.getMenuBlone() == null)
      po.setMenuBlone(form.getMenuBlone()); 
    if (po.getMenuLevel() == null)
      po.setMenuLevel(form.getMenuLevel()); 
    po.setMenuName(form.getMenuName());
    po.setMenuScope(form.getMenuScope());
    po.setMenuViewUser(form.getMenuViewUser());
    po.setMenuViewGroup(form.getMenuViewGroup());
    po.setMenuViewOrg(form.getMenuViewOrg());
    po.setMenuAction(form.getMenuAction());
    po.setMenuActionParams1(form.getMenuActionParams1());
    po.setMenuActionParams2(form.getMenuActionParams2());
    po.setMenuActionParams3(form.getMenuActionParams3());
    po.setMenuActionParams4(form.getMenuActionParams4());
    po.setMenuActionParams4Value(form.getMenuActionParams4Value());
    po.setMenuListTableMap(form.getMenuListTableMap());
    po.setMenuListTableName(form.getMenuListTableName());
    po.setMenuMaintenanceTableMap(form.getMenuMaintenanceTableMap());
    po.setMenuMaintenanceSubTableName(form.getMenuMaintenanceSubTableName());
    po.setMenuListDisplayElements(form.getMenuListDisplayElements());
    po.setMenuListQueryConditionElements(form.getMenuListQueryConditionElements());
    po.setMenuDefQueryCondition(form.getMenuDefQueryCondition());
    po.setMenuDefQueryOrder(form.getMenuDefQueryOrder());
    po.setMenuSearchBound(form.getMenuSearchBound());
    po.setMenuAccess(form.getMenuAccesss());
    po.setMenuOpenStyle(form.getMenuOpenStyle());
    po.setMenuIsValide(form.getMenuIsValide());
    po.setMenuMessageSend(form.getMenuMessageSend());
    po.setMenuFileLink(form.getMenuFileLink());
    po.setMenuHtmlLink(form.getMenuHtmlLink());
    po.setMenuStartFlow(form.getMenuStartFlow());
    po.setMenuRefFlow(form.getMenuRefFlow());
    po.setMenuRelationRefFlow(String.valueOf(form.getMenuRelationRefFlow()) + "|" + form.getMenuRelationRefFlow2());
    po.setRecordOperattion(form.getRecordOperattion());
    po.setOperationType(form.getOperationType());
    po.setOperationImg(form.getOperationImg());
    po.setMenuCount(form.getMenuCount());
    po.setOperationComponert(form.getOperationComponert());
    po.setDomainId(domainId);
    po.setMenuAdd(form.getMenuAdd());
    po.setMenuDel(form.getMenuDel());
    po.setMenuImp(form.getMenuImp());
    po.setMenuExp(form.getMenuExp());
    po.setMenuQuery(form.getMenuQuery());
  }
  
  private void setMenuOrderRule(ModuleMenuPO pPo, ModuleMenuPO po, String domainId, ModuleMenuService bd, ModuleMenuActionForm form) throws Exception {
    String maxLevel = bd.getMenuMaxCount(domainId, 
        form.getMenuBlone().toString());
    long level = -1L;
    if (maxLevel != null && maxLevel.length() > 0 && 
      maxLevel.indexOf("-") > 0)
      level = 
        Long.valueOf(maxLevel
          .substring(maxLevel.lastIndexOf("-") + 1, 
            maxLevel.length())).longValue(); 
    if (pPo != null) {
      po.setMenuBlone(form.getMenuBlone());
      if (pPo.getId().toString().equals(form.getMenuBlone().toString()) && !pPo.getId().equals(pPo.getMenuBlone()) && pPo.getMenuCount() != 9) {
        if (level < 0L) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
        } else if (pPo.getMenuLevel().indexOf("-") < 0) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-" + 
              String.valueOf((level + 5000000L) / 2L));
        } else {
          po.setMenuLevel(String.valueOf(maxLevel.substring(0, 
                  maxLevel.lastIndexOf("-") + 1)) + 
              String.valueOf((level + 5000000L) / 2L));
        } 
      } else {
        po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
      } 
    } else {
      po.setMenuBlone(form.getMenuBlone());
      po.setMenuLevel((level < 0L) ? (form.getMenuBlone() + "-5000000") : (
          String.valueOf(maxLevel.substring(0, maxLevel.lastIndexOf("-") + 1)) + ((
          level + 5000000L) / 2L)));
    } 
    if (pPo.getMenuCount() == 8 || pPo.getMenuCount() == 9) {
      po.setMenuLocation(pPo.getId());
    } else {
      po.setMenuLocation(form.getMenuLocation());
    } 
  }
  
  private void menuZorder(ModuleMenuPO po, HttpServletRequest request, ModuleMenuActionForm form, String domainId, String flag) {
    String zorder = form.getZorder();
    String locMenuId = "";
    if ("0".equals(flag)) {
      locMenuId = form.getMenuLocation().toString();
    } else {
      locMenuId = form.getZorderLocation().toString();
    } 
    ModuleMenuService bd = new ModuleMenuService();
    String preMenuLevel = "";
    String surMenuLevel = "";
    String tmpLevel = "";
    ListItem preItem = null;
    ListItem choItem = null;
    ListItem surItem = null;
    String retNum = "";
    Long preNum = new Long(0L);
    Long surNum = new Long(0L);
    List<ListItem> list = 
      bd.getAllSubMenusForOrder(domainId, po.getMenuBlone().toString());
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        ListItem item = list.get(i);
        if (item.getField2().indexOf("-") > 0)
          if (item.getId().equals(locMenuId)) {
            choItem = item;
            if ("1".equals(zorder) && i + 1 < list.size()) {
              surItem = list.get(i + 1);
              break;
            } 
            if (i == 0) {
              preItem = null;
            } else {
              preItem = list.get(i - 1);
            } 
          }  
      } 
      if ("0".equals(zorder) && preItem != null) {
        preNum = Long.valueOf(preItem.getField2()
            .substring(preItem.getField2()
              .lastIndexOf(
                "-"), preItem.getField2().length()));
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf((surNum.intValue() + preNum.intValue()) / 2 + 1))).toString();
      } else if ("0".equals(zorder) && preItem == null) {
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf(surNum.longValue() + 90000L + 1L))).toString();
      } else if ("1".equals(zorder) && surItem != null) {
        preNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        surNum = Long.valueOf(surItem.getField2()
            .substring(surItem.getField2()
              .lastIndexOf(
                "-"), surItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf((surNum.longValue() + preNum.longValue()) / 2L + 1L))).toString();
      } else if ("1".equals(zorder) && surItem == null) {
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf(surNum.intValue() - 90000 + 1))).toString();
      } 
      po.setMenuLevel(String.valueOf(po.getMenuLevel().substring(0, 
              po.getMenuLevel().lastIndexOf("-"))) + String.valueOf(retNum));
    } 
  }
  
  private void addMenuListRight(Long menuId, String menuName, Long domainId, ModuleMenuService bd) {
    if (menuId != null && menuId.longValue() > 0L) {
      ArrayList<HashMap<Object, Object>> list = new ArrayList();
      HashMap<Object, Object> subMap1 = new HashMap<Object, Object>();
      subMap1.put("rightName", "查看");
      subMap1.put("rightType", menuName);
      subMap1.put("rightClass", menuName);
      subMap1.put("rightCode", "99-" + menuId + "-01");
      subMap1.put("domainId", domainId);
      list.add(subMap1);
      HashMap<Object, Object> subMap2 = new HashMap<Object, Object>();
      subMap2.put("rightName", "新增");
      subMap2.put("rightType", menuName);
      subMap2.put("rightClass", menuName);
      subMap2.put("rightCode", "99-" + menuId + "-02");
      subMap2.put("domainId", domainId);
      list.add(subMap2);
      HashMap<Object, Object> subMap3 = new HashMap<Object, Object>();
      subMap3.put("rightName", "维护");
      subMap3.put("rightType", menuName);
      subMap3.put("rightClass", menuName);
      subMap3.put("rightCode", "99-" + menuId + "-03");
      subMap3.put("domainId", domainId);
      list.add(subMap3);
      HashMap<Object, Object> subMap4 = new HashMap<Object, Object>();
      subMap4.put("rightName", "导出");
      subMap4.put("rightType", menuName);
      subMap4.put("rightClass", menuName);
      subMap4.put("rightCode", "99-" + menuId + "-04");
      subMap4.put("domainId", domainId);
      list.add(subMap4);
      HashMap<Object, Object> subMap5 = new HashMap<Object, Object>();
      subMap5.put("rightName", "删除");
      subMap5.put("rightType", menuName);
      subMap5.put("rightClass", menuName);
      subMap5.put("rightCode", "99-" + menuId + "-05");
      subMap5.put("domainId", domainId);
      list.add(subMap5);
      bd.addMenuRights(list);
    } 
  }
  
  public boolean updateAuditModuleMenu(ModuleMenuActionForm form, HttpServletRequest request) throws Exception {
    ModuleMenuPO po = new ModuleMenuPO();
    ModuleMenuPO pPo = null;
    ModuleMenuService bd = new ModuleMenuService();
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    if (form.getMenuLevel().indexOf("-") <= 0) {
      ModuleMenuAllService oBd = new ModuleMenuAllService();
      SystemMenuPO oMenu = oBd.loadMenuSet(
          (form.getMenuLevel().indexOf("-") <= 0) ? 
          form.getMenuLevel().substring(1, 
            form.getMenuLevel().length()) : form.getMenuLevel());
      oMenu.setIsSystemInit(Integer.valueOf("0"));
      oMenu.setMenuView(form.getMenuScope());
      oMenu.setMenuViewUser(form.getMenuViewUser());
      oMenu.setMenuViewGroup(form.getMenuViewGroup());
      oMenu.setMenuViewOrg(form.getMenuViewOrg());
      oMenu.setMenuName(form.getMenuName());
      oMenu.setMenuCode(form.getMenuName());
      oMenu.setInUse(new Integer(form.getMenuIsValide()));
      setDefMenuRightUrl(form, form.getDomainId().toString(), oMenu, request);
      oMenu.setMenuOrder(String.valueOf(form.getParentOrder()));
      oBd.updateMenuSet(oMenu);
      ModuleMenuPO ppo = bd
        .loadMenuConfig(form.getDomainId().toString(), 
          form.getId().toString()).get(0);
      injectPo(ppo, form.getDomainId(), form, curUserId, form.getModel());
      ppo.setParentOrder(form.getParentOrder());
      ppo.setMenuAdd(form.getMenuAdd());
      ppo.setMenuDel(form.getMenuDel());
      ppo.setMenuImp(form.getMenuImp());
      ppo.setMenuExp(form.getMenuExp());
      ppo.setMenuQuery(form.getMenuQuery());
      bd.updateMenuConfig(ppo);
    } else {
      pPo = bd.loadParentMenuConfiger(form.getDomainId().toString(), 
          form.getMenuBlone().toString());
      po.setId(form.getId());
      List<ModuleMenuPO> changeMenuSublist = null;
      boolean changeSub = false;
      if (!form.getMenuBlone().equals(form.getMenuBloneHId())) {
        changeMenuSublist = bd.getAllSubMenusByLevel(form
            .getMenuLevel().toString());
        if (changeMenuSublist != null && 
          changeMenuSublist.size() > 0)
          changeSub = true; 
      } 
      if (!form.getMenuBlone().equals(form.getMenuBloneHId())) {
        po.setMenuBlone(form.getMenuBlone());
        po.setMenuLocation(form.getMenuBlone());
        if (pPo.getMenuLevel().indexOf("_") >= 0 || 
          pPo.getMenuLevel().indexOf("-") <= 0) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
        } else if (pPo.getMenuLevel().length() == 
          form.getMenuLevel().length()) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + 
              form
              .getMenuLevel().substring(form
                .getMenuLevel()
                .indexOf("-"), 
                form.getMenuLevel().length()));
        } else {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + 
              form
              .getMenuLevel().substring(form
                .getMenuLevel().lastIndexOf("-"), 
                form.getMenuLevel().length()));
        } 
      } else {
        po.setMenuBlone(form.getMenuBlone());
        po.setMenuLocation(form.getMenuBlone());
        po.setMenuLevel(form.getMenuLevel());
      } 
      injectPo(po, form.getDomainId(), form, curUserId, form.getModel());
      if (pPo.getMenuMaintenanceSubTableMap() != null && 
        pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
        po.setMenuMaintenanceSubTableMap(pPo
            .getMenuMaintenanceSubTableMap()); 
      if (form.getZorder() != null && form.getZorderLocation() != null && 
        form.getZorderLocation().length() > 1 && 
        !form.getZorderLocation().equals(form.getZorderLacationOld()) && 
        !"-1".equals(form.getZorderLocation())) {
        menuZorder(po, request, form, form.getDomainId().toString(), "1");
        changeMenuSublist = bd.getAllSubMenusByLevel(form
            .getMenuLevel().toString());
        if (changeMenuSublist != null && 
          changeMenuSublist.size() > 0)
          changeSub = true; 
      } else {
        form.getMenuBlone().equals(form.getMenuBloneHId());
      } 
      if (po.getMenuListTableMap() != null && 
        po.getMenuListTableMap().longValue() > 0L) {
        addMenuListRight(po.getId(), po.getMenuName(), form.getDomainId(), bd);
      } else {
        delMenuListRight(po.getId().toString(), form.getDomainId().toString(), 
            bd);
      } 
      po.setParentOrder(pPo.getParentOrder());
      po.setId(form.getMenuExtId());
      if (!bd.updateMenuConfig(po))
        throw new Exception("自定义菜单更新失败！"); 
      if (changeSub && changeMenuSublist.size() > 0) {
        int stepDif = 0;
        String oriLevel = "";
        for (int i = 0; i < changeMenuSublist.size(); i++) {
          ModuleMenuPO subPo = 
            changeMenuSublist.get(i);
          oriLevel = po.getMenuLevel();
          subPo.setMenuLevel(String.valueOf(oriLevel) + 
              subPo.getMenuLevel()
              .substring(form.getMenuLevel().length(), 
                subPo.getMenuLevel().length()));
          if (pPo.getMenuMaintenanceSubTableMap() != null && 
            pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
            subPo.setMenuMaintenanceSubTableMap(pPo
                .getMenuMaintenanceSubTableMap()); 
          subPo.setParentOrder(pPo.getParentOrder());
          bd.updateMenuConfig(subPo);
        } 
      } 
    } 
    request.setAttribute("validate", request.getParameter("validate"));
    return true;
  }
  
  private void delMenuListRight(String menuId, String domainId, ModuleMenuService bd) {
    bd.delMenuRights(menuId);
  }
  
  public boolean updateYourSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      stat.execute(sql);
      stat.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
}
