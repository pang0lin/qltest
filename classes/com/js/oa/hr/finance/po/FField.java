package com.js.oa.hr.finance.po;

import java.io.Serializable;

public class FField implements Serializable {
  private Long fieldId;
  
  private String tableName;
  
  private String fieldName;
  
  private String fieldDesname;
  
  private String fieldType;
  
  private Long fieldLen;
  
  private String fieldDefault;
  
  private Long fieldListShow;
  
  private Long fieldIsTotal;
  
  private Long fieldIsNull;
  
  private Long fieldIsEnable;
  
  private Long fieldIsSys;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long fieldOrder;
  
  public FField() {}
  
  public FField(String tableName, String fieldName, String fieldDesname, String fieldType, Long fieldLen, String fieldDefault, Long fieldListShow, Long fieldIsTotal, Long fieldIsNull, Long fieldIsEnable, Long fieldIsSys, Long createdEmp, Long createdOrg) {
    this.tableName = tableName;
    this.fieldName = fieldName;
    this.fieldDesname = fieldDesname;
    this.fieldType = fieldType;
    this.fieldLen = fieldLen;
    this.fieldDefault = fieldDefault;
    this.fieldListShow = fieldListShow;
    this.fieldIsTotal = fieldIsTotal;
    this.fieldIsNull = fieldIsNull;
    this.fieldIsEnable = fieldIsEnable;
    this.fieldIsSys = fieldIsSys;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getFieldId() {
    return this.fieldId;
  }
  
  public void setFieldId(Long fieldId) {
    this.fieldId = fieldId;
  }
  
  public String getTableName() {
    return this.tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public Long getFieldOrder() {
    return this.fieldOrder;
  }
  
  public void setFieldOrder(Long fieldOrder) {
    this.fieldOrder = fieldOrder;
  }
  
  public String getFieldName() {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }
  
  public String getFieldDesname() {
    return this.fieldDesname;
  }
  
  public void setFieldDesname(String fieldDesname) {
    this.fieldDesname = fieldDesname;
  }
  
  public String getFieldType() {
    return this.fieldType;
  }
  
  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }
  
  public Long getFieldLen() {
    return this.fieldLen;
  }
  
  public void setFieldLen(Long fieldLen) {
    this.fieldLen = fieldLen;
  }
  
  public String getFieldDefault() {
    return this.fieldDefault;
  }
  
  public void setFieldDefault(String fieldDefault) {
    this.fieldDefault = fieldDefault;
  }
  
  public Long getFieldListShow() {
    return this.fieldListShow;
  }
  
  public void setFieldListShow(Long fieldListShow) {
    this.fieldListShow = fieldListShow;
  }
  
  public Long getFieldIsTotal() {
    return this.fieldIsTotal;
  }
  
  public void setFieldIsTotal(Long fieldIsTotal) {
    this.fieldIsTotal = fieldIsTotal;
  }
  
  public Long getFieldIsNull() {
    return this.fieldIsNull;
  }
  
  public void setFieldIsNull(Long fieldIsNull) {
    this.fieldIsNull = fieldIsNull;
  }
  
  public Long getFieldIsEnable() {
    return this.fieldIsEnable;
  }
  
  public void setFieldIsEnable(Long fieldIsEnable) {
    this.fieldIsEnable = fieldIsEnable;
  }
  
  public Long getFieldIsSys() {
    return this.fieldIsSys;
  }
  
  public void setFieldIsSys(Long fieldIsSys) {
    this.fieldIsSys = fieldIsSys;
  }
  
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
}
