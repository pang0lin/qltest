package com.js.oa.audit.action;

import org.apache.struts.action.ActionForm;

public class AuditPortalActionForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long auditPortalId;
  
  private Long portalId;
  
  private String layoutName;
  
  private String userOrgGroup;
  
  private String ViewUser;
  
  private String ViewOrg;
  
  private String ViewGroup;
  
  private String viewRangeName;
  
  private Long porttalId;
  
  private String xmlFile;
  
  private String myColumn;
  
  private String domainId;
  
  private String showOrder;
  
  private String menuName;
  
  private int menuType;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String isUrl;
  
  private String url;
  
  private Long auditLogId;
  
  private String operationType;
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getLayoutName() {
    return this.layoutName;
  }
  
  public void setLayoutName(String layoutName) {
    this.layoutName = layoutName;
  }
  
  public String getIsUrl() {
    return this.isUrl;
  }
  
  public void setIsUrl(String isUrl) {
    this.isUrl = isUrl;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getViewUser() {
    return this.ViewUser;
  }
  
  public void setViewUser(String viewUser) {
    this.ViewUser = viewUser;
  }
  
  public String getViewOrg() {
    return this.ViewOrg;
  }
  
  public void setViewOrg(String viewOrg) {
    this.ViewOrg = viewOrg;
  }
  
  public String getViewGroup() {
    return this.ViewGroup;
  }
  
  public void setViewGroup(String viewGroup) {
    this.ViewGroup = viewGroup;
  }
  
  public String getViewRangeName() {
    return this.viewRangeName;
  }
  
  public void setViewRangeName(String viewRangeName) {
    this.viewRangeName = viewRangeName;
  }
  
  public String getXmlFile() {
    return this.xmlFile;
  }
  
  public void setXmlFile(String xmlFile) {
    this.xmlFile = xmlFile;
  }
  
  public String getMyColumn() {
    return this.myColumn;
  }
  
  public void setMyColumn(String myColumn) {
    this.myColumn = myColumn;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public int getMenuType() {
    return this.menuType;
  }
  
  public void setMenuType(int menuType) {
    this.menuType = menuType;
  }
  
  public String getShowOrder() {
    return this.showOrder;
  }
  
  public void setShowOrder(String showOrder) {
    this.showOrder = showOrder;
  }
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
  
  public Long getAuditPortalId() {
    return this.auditPortalId;
  }
  
  public void setAuditPortalId(Long auditPortalId) {
    this.auditPortalId = auditPortalId;
  }
  
  public Long getPorttalId() {
    return this.porttalId;
  }
  
  public void setPorttalId(Long porttalId) {
    this.porttalId = porttalId;
  }
  
  public Long getAuditLogId() {
    return this.auditLogId;
  }
  
  public void setAuditLogId(Long auditLogId) {
    this.auditLogId = auditLogId;
  }
  
  public String getOperationType() {
    return this.operationType;
  }
  
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  
  public Long getPortalId() {
    return this.portalId;
  }
  
  public void setPortalId(Long portalId) {
    this.portalId = portalId;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
}
