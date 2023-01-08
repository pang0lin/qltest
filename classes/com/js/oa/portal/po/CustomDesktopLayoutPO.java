package com.js.oa.portal.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomDesktopLayoutPO implements Serializable {
  private String layoutName;
  
  private String ViewUser;
  
  private String ViewOrg;
  
  private String ViewGroup;
  
  private String viewRangeName;
  
  private Long id;
  
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
  
  private String menuId;
  
  private String ispublic;
  
  private String personEmpId;
  
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
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
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
  
  public boolean equals(Object other) {
    if (!(other instanceof CustomDesktopLayoutPO))
      return false; 
    CustomDesktopLayoutPO castOther = (CustomDesktopLayoutPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
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
  
  public String getMenuId() {
    return this.menuId;
  }
  
  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }
  
  public String getIspublic() {
    return this.ispublic;
  }
  
  public void setIspublic(String ispublic) {
    this.ispublic = ispublic;
  }
  
  public String getPersonEmpId() {
    return this.personEmpId;
  }
  
  public void setPersonEmpId(String personEmpId) {
    this.personEmpId = personEmpId;
  }
}
