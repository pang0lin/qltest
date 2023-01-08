package com.js.oa.audit.action;

import com.js.oa.module.po.SystemMenuPO;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AuditModuleMenuActionForm extends ActionForm {
  private Long id;
  
  private Long auditMenuExtId;
  
  private Long menuExtId;
  
  private Long auditLogId;
  
  private String auditOperationType;
  
  private String menuName;
  
  private Long menuBlone;
  
  private Long menuBloneHId;
  
  private Long menuLocation;
  
  private String menuScope;
  
  private String menuAction;
  
  private String menuViewUser;
  
  private String menuViewOrg;
  
  private String menuViewGroup;
  
  private String menuActionParams1;
  
  private String menuActionParams2;
  
  private String menuActionParams3;
  
  private String menuActionParams4;
  
  private String menuActionParams4Value;
  
  private Long menuListTableMap;
  
  private String menuListTableName;
  
  private Long menuSubTableMap;
  
  private String menuSubTableName;
  
  private String menuDefQueryCondition;
  
  private String menuDefQueryOrder;
  
  private String menuListQueryConditionElements;
  
  private String menuListDisplayElements;
  
  private String menuAccesss;
  
  private String menuRefFlow;
  
  private String menuRefFlowStatue;
  
  private String menuRelationRefFlow;
  
  private String menuRelationRefFlow2;
  
  private Long menuMaintenanceTableMap;
  
  private String menuMaintenanceTableName;
  
  private Long menuMaintenanceSubTableMap;
  
  private String menuMaintenanceSubTableName;
  
  private String menuStartFlow;
  
  private String menuFileLink;
  
  private String menuHtmlLink;
  
  private int menuOpenStyle;
  
  private int menuIsValide;
  
  private int menuMessageSend;
  
  private Long domainId;
  
  private String menuLevel;
  
  private Long menuSearchBound;
  
  private String recordOperattion;
  
  private String operationType;
  
  private String operationImg;
  
  private String operationComponert;
  
  private String zorder;
  
  private String zorderLocation;
  
  private String zorderLacationOld;
  
  private String defHref;
  
  private String defHrefOld;
  
  private int menuCount;
  
  private String channel;
  
  private String channelOrder;
  
  private Long channelId;
  
  private String queryCaseName;
  
  private String listCaseName;
  
  private Long parentOrder;
  
  private String menuAccess;
  
  private String menuSearchHtml;
  
  private String menuListHtml;
  
  private SystemMenuPO menuSet = null;
  
  private String menuViewId;
  
  private String model;
  
  private String menuLocationSelValue;
  
  private String menuAdd;
  
  private String menuDel;
  
  private String menuImp;
  
  private String menuExp;
  
  private String menuQuery;
  
  public String getMenuAdd() {
    return this.menuAdd;
  }
  
  public void setMenuAdd(String menuAdd) {
    this.menuAdd = menuAdd;
  }
  
  public String getMenuDel() {
    return this.menuDel;
  }
  
  public void setMenuDel(String menuDel) {
    this.menuDel = menuDel;
  }
  
  public String getMenuImp() {
    return this.menuImp;
  }
  
  public void setMenuImp(String menuImp) {
    this.menuImp = menuImp;
  }
  
  public String getMenuExp() {
    return this.menuExp;
  }
  
  public void setMenuExp(String menuExp) {
    this.menuExp = menuExp;
  }
  
  public String getMenuQuery() {
    return this.menuQuery;
  }
  
  public void setMenuQuery(String menuQuery) {
    this.menuQuery = menuQuery;
  }
  
  public String getMenuLocationSelValue() {
    return this.menuLocationSelValue;
  }
  
  public void setMenuLocationSelValue(String menuLocationSelValue) {
    this.menuLocationSelValue = menuLocationSelValue;
  }
  
  public String getModel() {
    return this.model;
  }
  
  public void setModel(String model) {
    this.model = model;
  }
  
  public String getMenuListTableName() {
    return this.menuListTableName;
  }
  
  public String getMenuViewId() {
    return this.menuViewId;
  }
  
  public void setMenuViewId(String menuViewId) {
    this.menuViewId = menuViewId;
  }
  
  public int getMenuIsValide() {
    return this.menuIsValide;
  }
  
  public int getMenuOpenStyle() {
    return this.menuOpenStyle;
  }
  
  public String getMenuHtmlLink() {
    return this.menuHtmlLink;
  }
  
  public Long getAuditMenuExtId() {
    return this.auditMenuExtId;
  }
  
  public void setAuditMenuExtId(Long auditMenuExtId) {
    this.auditMenuExtId = auditMenuExtId;
  }
  
  public Long getMenuExtId() {
    return this.menuExtId;
  }
  
  public void setMenuExtId(Long menuExtId) {
    this.menuExtId = menuExtId;
  }
  
  public Long getAuditLogId() {
    return this.auditLogId;
  }
  
  public void setAuditLogId(Long auditLogId) {
    this.auditLogId = auditLogId;
  }
  
  public String getAuditOperationType() {
    return this.auditOperationType;
  }
  
  public void setAuditOperationType(String auditOperationType) {
    this.auditOperationType = auditOperationType;
  }
  
  public String getMenuStartFlow() {
    return this.menuStartFlow;
  }
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public String getMenuMaintenanceTableName() {
    return this.menuMaintenanceTableName;
  }
  
  public String getMenuListDisplayElements() {
    return this.menuListDisplayElements;
  }
  
  public String getMenuSubTableName() {
    return this.menuSubTableName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getMenuMaintenanceSubTableName() {
    return this.menuMaintenanceSubTableName;
  }
  
  public Long getMenuBlone() {
    return this.menuBlone;
  }
  
  public String getMenuRefFlow() {
    return this.menuRefFlow;
  }
  
  public String getMenuFileLink() {
    return this.menuFileLink;
  }
  
  public Long getMenuMaintenanceTableMap() {
    return this.menuMaintenanceTableMap;
  }
  
  public String getMenuRefFlowStatue() {
    return this.menuRefFlowStatue;
  }
  
  public Long getMenuMaintenanceSubTableMap() {
    return this.menuMaintenanceSubTableMap;
  }
  
  public Long getMenuListTableMap() {
    return this.menuListTableMap;
  }
  
  public String getMenuAction() {
    return this.menuAction;
  }
  
  public String getMenuDefQueryCondition() {
    return this.menuDefQueryCondition;
  }
  
  public int getMenuMessageSend() {
    return this.menuMessageSend;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getMenuDefQueryOrder() {
    return this.menuDefQueryOrder;
  }
  
  public Long getMenuSubTableMap() {
    return this.menuSubTableMap;
  }
  
  public String getMenuListQueryConditionElements() {
    return this.menuListQueryConditionElements;
  }
  
  public void setMenuLocation(Long menuLocation) {
    this.menuLocation = menuLocation;
  }
  
  public void setMenuListTableName(String menuListTableName) {
    this.menuListTableName = menuListTableName;
  }
  
  public void setMenuIsValide(int menuIsValide) {
    this.menuIsValide = menuIsValide;
  }
  
  public void setMenuOpenStyle(int menuOpenStyle) {
    this.menuOpenStyle = menuOpenStyle;
  }
  
  public void setMenuHtmlLink(String menuHtmlLink) {
    this.menuHtmlLink = menuHtmlLink;
  }
  
  public void setMenuStartFlow(String menuStartFlow) {
    this.menuStartFlow = menuStartFlow;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
  
  public void setMenuMaintenanceTableName(String menuMaintenanceTableName) {
    this.menuMaintenanceTableName = menuMaintenanceTableName;
  }
  
  public void setMenuListDisplayElements(String menuListDisplayElements) {
    this.menuListDisplayElements = menuListDisplayElements;
  }
  
  public void setMenuSubTableName(String menuSubTableName) {
    this.menuSubTableName = menuSubTableName;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setMenuMaintenanceSubTableName(String menuMaintenanceSubTableName) {
    this.menuMaintenanceSubTableName = menuMaintenanceSubTableName;
  }
  
  public void setMenuBlone(Long menuBlone) {
    this.menuBlone = menuBlone;
  }
  
  public void setMenuRefFlow(String menuRefFlow) {
    this.menuRefFlow = menuRefFlow;
  }
  
  public void setMenuFileLink(String menuFileLink) {
    this.menuFileLink = menuFileLink;
  }
  
  public void setMenuMaintenanceTableMap(Long menuMaintenanceTableMap) {
    this.menuMaintenanceTableMap = menuMaintenanceTableMap;
  }
  
  public void setMenuRefFlowStatue(String menuRefFlowStatue) {
    this.menuRefFlowStatue = menuRefFlowStatue;
  }
  
  public void setMenuMaintenanceSubTableMap(Long menuMaintenanceSubTableMap) {
    this.menuMaintenanceSubTableMap = menuMaintenanceSubTableMap;
  }
  
  public void setMenuListTableMap(Long menuListTableMap) {
    this.menuListTableMap = menuListTableMap;
  }
  
  public void setMenuAction(String menuAction) {
    this.menuAction = menuAction;
  }
  
  public void setMenuDefQueryCondition(String menuDefQueryCondition) {
    this.menuDefQueryCondition = menuDefQueryCondition;
  }
  
  public void setMenuMessageSend(int menuMessageSend) {
    this.menuMessageSend = menuMessageSend;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMenuDefQueryOrder(String menuDefQueryOrder) {
    this.menuDefQueryOrder = menuDefQueryOrder;
  }
  
  public void setMenuSubTableMap(Long menuSubTableMap) {
    this.menuSubTableMap = menuSubTableMap;
  }
  
  public void setMenuListQueryConditionElements(String menuListQueryConditionElements) {
    this.menuListQueryConditionElements = menuListQueryConditionElements;
  }
  
  public void setMenuActionParams1(String menuActionParams1) {
    this.menuActionParams1 = menuActionParams1;
  }
  
  public void setMenuActionParams4(String menuActionParams4) {
    this.menuActionParams4 = menuActionParams4;
  }
  
  public void setMenuActionParams2(String menuActionParams2) {
    this.menuActionParams2 = menuActionParams2;
  }
  
  public void setMenuActionParams4Value(String menuActionParams4Value) {
    this.menuActionParams4Value = menuActionParams4Value;
  }
  
  public void setMenuActionParams3(String menuActionParams3) {
    this.menuActionParams3 = menuActionParams3;
  }
  
  public void setMenuLevel(String menuLevel) {
    this.menuLevel = menuLevel;
  }
  
  public void setMenuScope(String menuScope) {
    this.menuScope = menuScope;
  }
  
  public void setMenuSearchBound(Long menuSearchBound) {
    this.menuSearchBound = menuSearchBound;
  }
  
  public void setMenuAccesss(String menuAccesss) {
    this.menuAccesss = menuAccesss;
  }
  
  public void setMenuRelationRefFlow(String menuRelationRefFlow) {
    this.menuRelationRefFlow = menuRelationRefFlow;
  }
  
  public void setMenuRelationRefFlow2(String menuRelationRefFlow2) {
    this.menuRelationRefFlow2 = menuRelationRefFlow2;
  }
  
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  
  public void setOperationImg(String operationImg) {
    this.operationImg = operationImg;
  }
  
  public void setRecordOperattion(String recordOperattion) {
    this.recordOperattion = recordOperattion;
  }
  
  public void setOperationComponert(String operationComponert) {
    this.operationComponert = operationComponert;
  }
  
  public void setMenuCount(int menuCount) {
    this.menuCount = menuCount;
  }
  
  public void setZorder(String zorder) {
    this.zorder = zorder;
  }
  
  public void setZorderLocation(String zorderLocation) {
    this.zorderLocation = zorderLocation;
  }
  
  public void setDefHref(String defHref) {
    this.defHref = defHref;
  }
  
  public void setMenuViewUser(String menuViewUser) {
    this.menuViewUser = menuViewUser;
  }
  
  public void setMenuViewOrg(String menuViewOrg) {
    this.menuViewOrg = menuViewOrg;
  }
  
  public void setMenuViewGroup(String menuViewGroup) {
    this.menuViewGroup = menuViewGroup;
  }
  
  public void setChannelOrder(String channelOrder) {
    this.channelOrder = channelOrder;
  }
  
  public void setChannel(String channel) {
    this.channel = channel;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public void setMenuBloneHId(Long menuBloneHId) {
    this.menuBloneHId = menuBloneHId;
  }
  
  public void setQueryCaseName(String queryCaseName) {
    this.queryCaseName = queryCaseName;
  }
  
  public void setListCaseName(String listCaseName) {
    this.listCaseName = listCaseName;
  }
  
  public void setDefHrefOld(String defHrefOld) {
    this.defHrefOld = defHrefOld;
  }
  
  public void setZorderLacationOld(String zorderLacationOld) {
    this.zorderLacationOld = zorderLacationOld;
  }
  
  public Long getMenuLocation() {
    return this.menuLocation;
  }
  
  public String getMenuActionParams1() {
    return this.menuActionParams1;
  }
  
  public String getMenuActionParams4() {
    return this.menuActionParams4;
  }
  
  public String getMenuActionParams2() {
    return this.menuActionParams2;
  }
  
  public String getMenuActionParams4Value() {
    return this.menuActionParams4Value;
  }
  
  public String getMenuActionParams3() {
    return this.menuActionParams3;
  }
  
  public String getMenuLevel() {
    return this.menuLevel;
  }
  
  public String getMenuScope() {
    return this.menuScope;
  }
  
  public Long getMenuSearchBound() {
    return this.menuSearchBound;
  }
  
  public String getMenuAccesss() {
    return this.menuAccesss;
  }
  
  public String getMenuRelationRefFlow() {
    return this.menuRelationRefFlow;
  }
  
  public String getMenuRelationRefFlow2() {
    return this.menuRelationRefFlow2;
  }
  
  public String getOperationType() {
    return this.operationType;
  }
  
  public String getOperationImg() {
    return this.operationImg;
  }
  
  public String getRecordOperattion() {
    return this.recordOperattion;
  }
  
  public String getOperationComponert() {
    return this.operationComponert;
  }
  
  public int getMenuCount() {
    return this.menuCount;
  }
  
  public String getZorder() {
    return this.zorder;
  }
  
  public String getZorderLocation() {
    return this.zorderLocation;
  }
  
  public String getDefHref() {
    return this.defHref;
  }
  
  public String getMenuViewUser() {
    return this.menuViewUser;
  }
  
  public String getMenuViewOrg() {
    return this.menuViewOrg;
  }
  
  public String getMenuViewGroup() {
    return this.menuViewGroup;
  }
  
  public String getChannelOrder() {
    return this.channelOrder;
  }
  
  public String getChannel() {
    return this.channel;
  }
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public Long getMenuBloneHId() {
    return this.menuBloneHId;
  }
  
  public String getQueryCaseName() {
    return this.queryCaseName;
  }
  
  public String getListCaseName() {
    return this.listCaseName;
  }
  
  public String getDefHrefOld() {
    return this.defHrefOld;
  }
  
  public String getZorderLacationOld() {
    return this.zorderLacationOld;
  }
  
  public Long getParentOrder() {
    return this.parentOrder;
  }
  
  public void setParentOrder(Long parentOrder) {
    this.parentOrder = parentOrder;
  }
  
  public String getMenuAccess() {
    return this.menuAccess;
  }
  
  public void setMenuAccess(String menuAccess) {
    this.menuAccess = menuAccess;
  }
  
  public String getMenuSearchHtml() {
    return this.menuSearchHtml;
  }
  
  public void setMenuSearchHtml(String menuSearchHtml) {
    this.menuSearchHtml = menuSearchHtml;
  }
  
  public String getMenuListHtml() {
    return this.menuListHtml;
  }
  
  public void setMenuListHtml(String menuListHtml) {
    this.menuListHtml = menuListHtml;
  }
  
  public SystemMenuPO getMenuSet() {
    return this.menuSet;
  }
  
  public void setMenuSet(SystemMenuPO menuSet) {
    this.menuSet = menuSet;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    reset();
  }
  
  public void reset() {
    this.id = null;
    this.menuName = null;
    this.menuBlone = null;
    this.menuLocation = null;
    this.menuScope = null;
    this.menuAction = null;
    this.menuActionParams1 = null;
    this.menuActionParams2 = null;
    this.menuActionParams3 = null;
    this.menuActionParams4 = null;
    this.menuActionParams4Value = null;
    this.menuListTableMap = null;
    this.menuListTableName = null;
    this.menuSubTableMap = null;
    this.menuSubTableName = null;
    this.menuDefQueryCondition = null;
    this.menuDefQueryOrder = null;
    this.menuListQueryConditionElements = null;
    this.menuListDisplayElements = null;
    this.menuRefFlow = null;
    this.menuRefFlowStatue = null;
    this.menuMaintenanceTableMap = null;
    this.menuMaintenanceTableName = null;
    this.menuMaintenanceSubTableMap = null;
    this.menuMaintenanceSubTableName = null;
    this.menuStartFlow = null;
    this.menuFileLink = null;
    this.menuHtmlLink = null;
    this.menuOpenStyle = 0;
    this.menuIsValide = 0;
    this.menuMessageSend = 0;
    this.domainId = null;
    this.menuLevel = null;
    this.menuSearchBound = null;
    this.menuAccesss = null;
    this.menuRelationRefFlow = null;
    this.menuRelationRefFlow2 = null;
    this.recordOperattion = null;
    this.operationType = null;
    this.operationImg = null;
    this.operationComponert = null;
    this.zorderLocation = "1";
    this.zorder = "1";
    this.menuViewGroup = "";
    this.menuViewOrg = "";
    this.menuViewUser = "";
    this.channel = null;
    this.channelOrder = null;
    this.channelId = null;
    this.queryCaseName = null;
    this.listCaseName = null;
  }
}
