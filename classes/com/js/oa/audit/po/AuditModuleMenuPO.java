package com.js.oa.audit.po;

import com.js.oa.module.po.SystemMenuPO;
import java.io.Serializable;

public class AuditModuleMenuPO implements Serializable {
  private Long auditMenuExtId;
  
  private Long menuExtId;
  
  private Long auditLogId;
  
  private String auditOperationType;
  
  private String menuName;
  
  private Long menuBlone;
  
  private Long menuLocation;
  
  private String menuScope;
  
  private String menuViewUser;
  
  private String menuViewOrg;
  
  private String menuViewGroup;
  
  private String menuAction;
  
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
  
  private String menuRefFlow;
  
  private String menuRefFlowStatue;
  
  private String menuRelationRefFlow;
  
  private Long menuMaintenanceTableMap;
  
  private String menuMaintenanceTableName;
  
  private Long menuMaintenanceSubTableMap;
  
  private String menuMaintenanceSubTableName;
  
  private String menuAccess;
  
  private String menuStartFlow;
  
  private String menuFileLink;
  
  private String menuHtmlLink;
  
  private int menuOpenStyle;
  
  private int menuIsValide;
  
  private int menuMessageSend;
  
  private Long domainId;
  
  private String menuLevel;
  
  private int menuCount;
  
  private Long menuSearchBound;
  
  private String menuSearchHtml;
  
  private String menuListHtml;
  
  private SystemMenuPO menuSet = null;
  
  private String recordOperattion;
  
  private String operationType;
  
  private String operationImg;
  
  private String operationComponert;
  
  private Long parentOrder;
  
  private Long menuBloneHId;
  
  private String menuAccesss;
  
  private String menuRelationRefFlow2;
  
  private String zorder;
  
  private String zorderLocation;
  
  private String zorderLacationOld;
  
  private String defHref;
  
  private String defHrefOld;
  
  private String channel;
  
  private String channelOrder;
  
  private Long channelId;
  
  private String queryCaseName;
  
  private String listCaseName;
  
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
  
  public String getMenuListTableName() {
    return this.menuListTableName;
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
  
  public int getMenuIsValide() {
    return this.menuIsValide;
  }
  
  public int getMenuOpenStyle() {
    return this.menuOpenStyle;
  }
  
  public String getMenuHtmlLink() {
    return this.menuHtmlLink;
  }
  
  public Long getMenuBloneHId() {
    return this.menuBloneHId;
  }
  
  public void setMenuBloneHId(Long menuBloneHId) {
    this.menuBloneHId = menuBloneHId;
  }
  
  public String getMenuAccesss() {
    return this.menuAccesss;
  }
  
  public void setMenuAccesss(String menuAccesss) {
    this.menuAccesss = menuAccesss;
  }
  
  public String getMenuRelationRefFlow2() {
    return this.menuRelationRefFlow2;
  }
  
  public void setMenuRelationRefFlow2(String menuRelationRefFlow2) {
    this.menuRelationRefFlow2 = menuRelationRefFlow2;
  }
  
  public String getZorder() {
    return this.zorder;
  }
  
  public void setZorder(String zorder) {
    this.zorder = zorder;
  }
  
  public String getZorderLocation() {
    return this.zorderLocation;
  }
  
  public void setZorderLocation(String zorderLocation) {
    this.zorderLocation = zorderLocation;
  }
  
  public String getZorderLacationOld() {
    return this.zorderLacationOld;
  }
  
  public void setZorderLacationOld(String zorderLacationOld) {
    this.zorderLacationOld = zorderLacationOld;
  }
  
  public String getDefHref() {
    return this.defHref;
  }
  
  public void setDefHref(String defHref) {
    this.defHref = defHref;
  }
  
  public String getDefHrefOld() {
    return this.defHrefOld;
  }
  
  public void setDefHrefOld(String defHrefOld) {
    this.defHrefOld = defHrefOld;
  }
  
  public String getChannel() {
    return this.channel;
  }
  
  public void setChannel(String channel) {
    this.channel = channel;
  }
  
  public String getChannelOrder() {
    return this.channelOrder;
  }
  
  public void setChannelOrder(String channelOrder) {
    this.channelOrder = channelOrder;
  }
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public String getQueryCaseName() {
    return this.queryCaseName;
  }
  
  public void setQueryCaseName(String queryCaseName) {
    this.queryCaseName = queryCaseName;
  }
  
  public String getListCaseName() {
    return this.listCaseName;
  }
  
  public void setListCaseName(String listCaseName) {
    this.listCaseName = listCaseName;
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
  
  public void setMenuDefQueryOrder(String menuDefQueryOrder) {
    this.menuDefQueryOrder = menuDefQueryOrder;
  }
  
  public void setMenuSubTableMap(Long menuSubTableMap) {
    this.menuSubTableMap = menuSubTableMap;
  }
  
  public void setMenuListQueryConditionElements(String menuListQueryConditionElements) {
    this.menuListQueryConditionElements = menuListQueryConditionElements;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
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
  
  public void setMenuCount(int menuCount) {
    this.menuCount = menuCount;
  }
  
  public void setMenuSearchBound(Long menuSearchBound) {
    this.menuSearchBound = menuSearchBound;
  }
  
  public void setMenuSearchHtml(String menuSearchHtml) {
    this.menuSearchHtml = menuSearchHtml;
  }
  
  public void setMenuListHtml(String menuListHtml) {
    this.menuListHtml = menuListHtml;
  }
  
  public void setMenuAccess(String menuAccess) {
    this.menuAccess = menuAccess;
  }
  
  public void setMenuRelationRefFlow(String menuRelationRefFlow) {
    this.menuRelationRefFlow = menuRelationRefFlow;
  }
  
  public void setMenuSet(SystemMenuPO menuSet) {
    this.menuSet = menuSet;
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
  
  public void setMenuViewUser(String menuViewUser) {
    this.menuViewUser = menuViewUser;
  }
  
  public void setMenuViewOrg(String menuViewOrg) {
    this.menuViewOrg = menuViewOrg;
  }
  
  public void setMenuViewGroup(String menuViewGroup) {
    this.menuViewGroup = menuViewGroup;
  }
  
  public void setParentOrder(Long parentOrder) {
    this.parentOrder = parentOrder;
  }
  
  public Long getMenuLocation() {
    return this.menuLocation;
  }
  
  public Long getDomainId() {
    return this.domainId;
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
  
  public int getMenuCount() {
    return this.menuCount;
  }
  
  public Long getMenuSearchBound() {
    return this.menuSearchBound;
  }
  
  public String getMenuSearchHtml() {
    return this.menuSearchHtml;
  }
  
  public String getMenuListHtml() {
    return this.menuListHtml;
  }
  
  public String getMenuAccess() {
    return this.menuAccess;
  }
  
  public String getMenuRelationRefFlow() {
    return this.menuRelationRefFlow;
  }
  
  public SystemMenuPO getMenuSet() {
    return this.menuSet;
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
  
  public String getMenuViewUser() {
    return this.menuViewUser;
  }
  
  public String getMenuViewOrg() {
    return this.menuViewOrg;
  }
  
  public String getMenuViewGroup() {
    return this.menuViewGroup;
  }
  
  public Long getParentOrder() {
    return this.parentOrder;
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
}
