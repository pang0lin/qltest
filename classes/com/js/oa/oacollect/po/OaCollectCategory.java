package com.js.oa.oacollect.po;

import java.io.Serializable;
import java.util.Date;

public class OaCollectCategory implements Serializable {
  private Long categoryId;
  
  private String categoryName;
  
  private String categoryNote;
  
  private Long createEmp;
  
  private String createEmpName;
  
  private Long createOrg;
  
  private String createOrgName;
  
  private Integer categorySortCode;
  
  private String categorySort;
  
  private Long parentId;
  
  private Integer categoryLevel;
  
  private Date createdDate = null;
  
  private String readerId;
  
  private String readerName;
  
  public String getReaderName() {
    return this.readerName;
  }
  
  public void setReaderName(String readerName) {
    this.readerName = readerName;
  }
  
  public String getReaderId() {
    return this.readerId;
  }
  
  public void setReaderId(String readerId) {
    this.readerId = readerId;
  }
  
  public OaCollectCategory() {}
  
  public OaCollectCategory(String categoryName, String categoryNote, Long createEmp, String createEmpName, Long createOrg, String createOrgName, Integer categorySortCode, String categorySort, Long parentId, Integer categoryLevel, Date createdDate) {
    this.categoryName = categoryName;
    this.categoryNote = categoryNote;
    this.createEmp = createEmp;
    this.createEmpName = createEmpName;
    this.createOrg = createOrg;
    this.createOrgName = createOrgName;
    this.categorySortCode = categorySortCode;
    this.categorySort = categorySort;
    this.parentId = parentId;
    this.categoryLevel = categoryLevel;
    this.createdDate = createdDate;
    this.readerName = "";
    this.readerId = "";
  }
  
  public Long getCategoryId() {
    return this.categoryId;
  }
  
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
  
  public String getCategoryName() {
    return this.categoryName;
  }
  
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
  
  public String getCategoryNote() {
    return this.categoryNote;
  }
  
  public void setCategoryNote(String categoryNote) {
    this.categoryNote = categoryNote;
  }
  
  public Long getCreateEmp() {
    return this.createEmp;
  }
  
  public void setCreateEmp(Long createEmp) {
    this.createEmp = createEmp;
  }
  
  public String getCreateEmpName() {
    return this.createEmpName;
  }
  
  public void setCreateEmpName(String createEmpName) {
    this.createEmpName = createEmpName;
  }
  
  public Long getCreateOrg() {
    return this.createOrg;
  }
  
  public void setCreateOrg(Long createOrg) {
    this.createOrg = createOrg;
  }
  
  public String getCreateOrgName() {
    return this.createOrgName;
  }
  
  public void setCreateOrgName(String createOrgName) {
    this.createOrgName = createOrgName;
  }
  
  public Integer getCategorySortCode() {
    return this.categorySortCode;
  }
  
  public void setCategorySortCode(Integer categorySortCode) {
    this.categorySortCode = categorySortCode;
  }
  
  public String getCategorySort() {
    return this.categorySort;
  }
  
  public void setCategorySort(String categorySort) {
    this.categorySort = categorySort;
  }
  
  public Long getParentId() {
    return this.parentId;
  }
  
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
  
  public Integer getCategoryLevel() {
    return this.categoryLevel;
  }
  
  public void setCategoryLevel(Integer categoryLevel) {
    this.categoryLevel = categoryLevel;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
