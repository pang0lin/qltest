package com.js.oa.audit.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AuditMenuPO implements Serializable {
  private Long auditMenuId;
  
  private Long menuId;
  
  private String menuName;
  
  private String menuLevel;
  
  private String menuView;
  
  private String menuViewUser;
  
  private String menuViewOrg;
  
  private String menuViewGroup;
  
  private String menuParent;
  
  private String menuURL;
  
  private String menuOrder;
  
  private String deskTop1;
  
  private String deskTop2;
  
  private String menuIdString;
  
  private Integer isSystemInit;
  
  private Integer inUse;
  
  private String leftURL;
  
  private String rightURL;
  
  private String menuCode;
  
  private String domainId;
  
  private Long auditLogId;
  
  private String operationType;
  
  private String userOrgGroup;
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("empId", getMenuId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AuditMenuPO))
      return false; 
    AuditMenuPO castOther = (AuditMenuPO)other;
    return (new EqualsBuilder())
      .append(getMenuId(), castOther.getMenuId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getMenuId())
      .toHashCode();
  }
  
  public Long getMenuId() {
    return this.menuId;
  }
  
  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
  
  public String getMenuLevel() {
    return this.menuLevel;
  }
  
  public void setMenuLevel(String menuLevel) {
    this.menuLevel = menuLevel;
  }
  
  public String getMenuView() {
    return this.menuView;
  }
  
  public void setMenuView(String menuView) {
    this.menuView = menuView;
  }
  
  public String getMenuViewUser() {
    return this.menuViewUser;
  }
  
  public void setMenuViewUser(String menuViewUser) {
    this.menuViewUser = menuViewUser;
  }
  
  public String getMenuViewOrg() {
    return this.menuViewOrg;
  }
  
  public void setMenuViewOrg(String menuViewOrg) {
    this.menuViewOrg = menuViewOrg;
  }
  
  public String getMenuViewGroup() {
    return this.menuViewGroup;
  }
  
  public void setMenuViewGroup(String menuViewGroup) {
    this.menuViewGroup = menuViewGroup;
  }
  
  public String getMenuParent() {
    return this.menuParent;
  }
  
  public void setMenuParent(String menuParent) {
    this.menuParent = menuParent;
  }
  
  public String getMenuURL() {
    return this.menuURL;
  }
  
  public void setMenuURL(String menuURL) {
    this.menuURL = menuURL;
  }
  
  public String getMenuOrder() {
    return this.menuOrder;
  }
  
  public void setMenuOrder(String menuOrder) {
    this.menuOrder = menuOrder;
  }
  
  public String getDeskTop1() {
    return this.deskTop1;
  }
  
  public void setDeskTop1(String deskTop1) {
    this.deskTop1 = deskTop1;
  }
  
  public String getDeskTop2() {
    return this.deskTop2;
  }
  
  public void setDeskTop2(String deskTop2) {
    this.deskTop2 = deskTop2;
  }
  
  public String getMenuIdString() {
    return this.menuIdString;
  }
  
  public void setMenuIdString(String menuIdString) {
    this.menuIdString = menuIdString;
  }
  
  public Integer getIsSystemInit() {
    return this.isSystemInit;
  }
  
  public void setIsSystemInit(Integer isSystemInit) {
    this.isSystemInit = isSystemInit;
  }
  
  public Integer getInUse() {
    return this.inUse;
  }
  
  public void setInUse(Integer inUse) {
    this.inUse = inUse;
  }
  
  public String getLeftURL() {
    return this.leftURL;
  }
  
  public void setLeftURL(String leftURL) {
    this.leftURL = leftURL;
  }
  
  public String getRightURL() {
    return this.rightURL;
  }
  
  public void setRightURL(String rightURL) {
    this.rightURL = rightURL;
  }
  
  public String getMenuCode() {
    return this.menuCode;
  }
  
  public void setMenuCode(String menuCode) {
    this.menuCode = menuCode;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public Long getAuditMenuId() {
    return this.auditMenuId;
  }
  
  public void setAuditMenuId(Long auditMenuId) {
    this.auditMenuId = auditMenuId;
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
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
}
