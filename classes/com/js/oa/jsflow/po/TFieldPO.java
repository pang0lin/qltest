package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TFieldPO implements Serializable {
  private Long fieldId;
  
  private String fieldCode;
  
  private String fieldDesName;
  
  private String fieldName;
  
  private int fieldModel;
  
  private int fieldLen;
  
  private int fieldRef;
  
  private int fieldIndex;
  
  private int fieldNull;
  
  private int fieldSort;
  
  private String fieldDefault;
  
  private TTablePO table;
  
  private int fieldOnly;
  
  private int fieldUpdate;
  
  private String fieldDes;
  
  private int fieldList;
  
  private String fieldWidth;
  
  private String fieldValue;
  
  private int fieldLimit;
  
  private String fieldOwner;
  
  private Date fieldDate = null;
  
  private TTypePO type;
  
  private TShowPO show;
  
  private String fieldInterfacename;
  
  private String fieldInterfacemethod;
  
  private String fieldInterfacemethodpara;
  
  private String fieldInterfacetype;
  
  public Long getFieldId() {
    return this.fieldId;
  }
  
  public void setFieldId(Long fieldId) {
    this.fieldId = fieldId;
  }
  
  public String getFieldCode() {
    return this.fieldCode;
  }
  
  public void setFieldCode(String fieldCode) {
    this.fieldCode = fieldCode;
  }
  
  public String getFieldDesName() {
    return this.fieldDesName;
  }
  
  public void setFieldDesName(String fieldDesName) {
    this.fieldDesName = fieldDesName;
  }
  
  public String getFieldName() {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }
  
  public int getFieldModel() {
    return this.fieldModel;
  }
  
  public void setFieldModel(int fieldModel) {
    this.fieldModel = fieldModel;
  }
  
  public int getFieldLen() {
    return this.fieldLen;
  }
  
  public void setFieldLen(int fieldLen) {
    this.fieldLen = fieldLen;
  }
  
  public int getFieldRef() {
    return this.fieldRef;
  }
  
  public void setFieldRef(int fieldRef) {
    this.fieldRef = fieldRef;
  }
  
  public int getFieldIndex() {
    return this.fieldIndex;
  }
  
  public void setFieldIndex(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  public int getFieldNull() {
    return this.fieldNull;
  }
  
  public void setFieldNull(int fieldNull) {
    this.fieldNull = fieldNull;
  }
  
  public int getFieldSort() {
    return this.fieldSort;
  }
  
  public void setFieldSort(int fieldSort) {
    this.fieldSort = fieldSort;
  }
  
  public String getFieldDefault() {
    return this.fieldDefault;
  }
  
  public void setFieldDefault(String fieldDefault) {
    this.fieldDefault = fieldDefault;
  }
  
  public TTablePO getTable() {
    return this.table;
  }
  
  public void setTable(TTablePO table) {
    this.table = table;
  }
  
  public int getFieldOnly() {
    return this.fieldOnly;
  }
  
  public void setFieldOnly(int fieldOnly) {
    this.fieldOnly = fieldOnly;
  }
  
  public int getFieldUpdate() {
    return this.fieldUpdate;
  }
  
  public void setFieldUpdate(int fieldUpdate) {
    this.fieldUpdate = fieldUpdate;
  }
  
  public String getFieldDes() {
    return this.fieldDes;
  }
  
  public void setFieldDes(String fieldDes) {
    this.fieldDes = fieldDes;
  }
  
  public int getFieldList() {
    return this.fieldList;
  }
  
  public void setFieldList(int fieldList) {
    this.fieldList = fieldList;
  }
  
  public String getFieldWidth() {
    return this.fieldWidth;
  }
  
  public void setFieldWidth(String fieldWidth) {
    this.fieldWidth = fieldWidth;
  }
  
  public String getFieldValue() {
    return this.fieldValue;
  }
  
  public void setFieldValue(String fieldValue) {
    this.fieldValue = fieldValue;
  }
  
  public int getFieldLimit() {
    return this.fieldLimit;
  }
  
  public void setFieldLimit(int fieldLimit) {
    this.fieldLimit = fieldLimit;
  }
  
  public String getFieldOwner() {
    return this.fieldOwner;
  }
  
  public void setFieldOwner(String fieldOwner) {
    this.fieldOwner = fieldOwner;
  }
  
  public Date getFieldDate() {
    return this.fieldDate;
  }
  
  public void setFieldDate(Date fieldDate) {
    this.fieldDate = fieldDate;
  }
  
  public TTypePO getType() {
    return this.type;
  }
  
  public void setType(TTypePO type) {
    this.type = type;
  }
  
  public TShowPO getShow() {
    return this.show;
  }
  
  public void setShow(TShowPO show) {
    this.show = show;
  }
  
  public String getFieldInterfacename() {
    return this.fieldInterfacename;
  }
  
  public void setFieldInterfacename(String fieldInterfacename) {
    this.fieldInterfacename = fieldInterfacename;
  }
  
  public String getFieldInterfacemethod() {
    return this.fieldInterfacemethod;
  }
  
  public void setFieldInterfacemethod(String fieldInterfacemethod) {
    this.fieldInterfacemethod = fieldInterfacemethod;
  }
  
  public String getFieldInterfacemethodpara() {
    return this.fieldInterfacemethodpara;
  }
  
  public void setFieldInterfacemethodpara(String fieldInterfacemethodpara) {
    this.fieldInterfacemethodpara = fieldInterfacemethodpara;
  }
  
  public String getFieldInterfacetype() {
    return this.fieldInterfacetype;
  }
  
  public void setFieldInterfacetype(String fieldInterfacetype) {
    this.fieldInterfacetype = fieldInterfacetype;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TFieldPO))
      return false; 
    TFieldPO castOther = (TFieldPO)other;
    return (new EqualsBuilder()).append(getFieldId(), castOther.getFieldId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getFieldId()).toHashCode();
  }
}
