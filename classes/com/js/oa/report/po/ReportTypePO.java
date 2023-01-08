package com.js.oa.report.po;

import java.util.Date;

public class ReportTypePO {
  private Long typeId;
  
  private String typeName;
  
  private String typeDescribe;
  
  private String viewScopeId;
  
  private String viewScope;
  
  private String createEmp;
  
  private Long createEmpId;
  
  private Date createDate = null;
  
  private String domainId;
  
  private String corpId;
  
  private Long createOrgId;
  
  private Long parentId;
  
  private String orderCode;
  
  private Integer typeLevel;
  
  private Integer sortCode;
  
  private Integer insertSite;
  
  private String oprEmpId;
  
  private String oprEmpName;
  
  public String getOprEmpId() {
    return this.oprEmpId;
  }
  
  public void setOprEmpId(String oprEmpId) {
    this.oprEmpId = oprEmpId;
  }
  
  public String getOprEmpName() {
    return this.oprEmpName;
  }
  
  public void setOprEmpName(String oprEmpName) {
    this.oprEmpName = oprEmpName;
  }
  
  public Long getParentId() {
    return this.parentId;
  }
  
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
  
  public Long getCreateOrgId() {
    return this.createOrgId;
  }
  
  public void setCreateOrgId(Long createOrgId) {
    this.createOrgId = createOrgId;
  }
  
  public Long getTypeId() {
    return this.typeId;
  }
  
  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }
  
  public String getTypeName() {
    return this.typeName;
  }
  
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }
  
  public String getTypeDescribe() {
    return this.typeDescribe;
  }
  
  public void setTypeDescribe(String typeDescribe) {
    this.typeDescribe = typeDescribe;
  }
  
  public String getViewScopeId() {
    return this.viewScopeId;
  }
  
  public void setViewScopeId(String viewScopeId) {
    this.viewScopeId = viewScopeId;
  }
  
  public String getViewScope() {
    return this.viewScope;
  }
  
  public void setViewScope(String viewScope) {
    this.viewScope = viewScope;
  }
  
  public String getCreateEmp() {
    return this.createEmp;
  }
  
  public void setCreateEmp(String createEmp) {
    this.createEmp = createEmp;
  }
  
  public Long getCreateEmpId() {
    return this.createEmpId;
  }
  
  public void setCreateEmpId(Long createEmpId) {
    this.createEmpId = createEmpId;
  }
  
  public Date getCreateDate() {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public Integer getSortCode() {
    return this.sortCode;
  }
  
  public void setSortCode(Integer sortCode) {
    this.sortCode = sortCode;
  }
  
  public Integer getInsertSite() {
    return this.insertSite;
  }
  
  public void setInsertSite(Integer insertSite) {
    this.insertSite = insertSite;
  }
  
  public Integer getTypeLevel() {
    return this.typeLevel;
  }
  
  public void setTypeLevel(Integer typeLevel) {
    this.typeLevel = typeLevel;
  }
}
