package com.js.oa.report.action;

import java.io.Serializable;
import org.apache.struts.action.ActionForm;

public class ReportTypeActionForm extends ActionForm implements Serializable {
  private String typeName;
  
  private String typeDescribe;
  
  private String viewScopeId;
  
  private String viewScope;
  
  private Long parentId;
  
  private String orderCode;
  
  private Integer sortCode;
  
  private Integer insertSite = Integer.valueOf(1);
  
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
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
  
  public Integer getSortCode() {
    return this.sortCode;
  }
  
  public void setSortCode(Integer sortCode) {
    this.sortCode = sortCode;
  }
  
  public Long getParentId() {
    return this.parentId;
  }
  
  public void setParentId(Long parentId) {
    this.parentId = parentId;
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
  
  public Integer getInsertSite() {
    return this.insertSite;
  }
  
  public void setInsertSite(Integer insertSite) {
    this.insertSite = insertSite;
  }
}
