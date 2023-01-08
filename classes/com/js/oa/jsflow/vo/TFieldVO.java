package com.js.oa.jsflow.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TFieldVO implements Serializable {
  private int fieldId;
  
  private String fieldName;
  
  private int fieldShow;
  
  private String fieldDesName;
  
  private int fieldLen;
  
  private String fieldValue;
  
  private String fieldType;
  
  private int fieldNull;
  
  private String fieldDefault;
  
  public int getFieldId() {
    return this.fieldId;
  }
  
  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }
  
  public String getFieldName() {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }
  
  public int getFieldShow() {
    return this.fieldShow;
  }
  
  public void setFieldShow(int fieldShow) {
    this.fieldShow = fieldShow;
  }
  
  public String getFieldDesName() {
    return this.fieldDesName;
  }
  
  public void setFieldDesName(String fieldDesName) {
    this.fieldDesName = fieldDesName;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TFieldVO))
      return false; 
    TFieldVO castOther = (TFieldVO)other;
    return (new EqualsBuilder())
      .append(getFieldId(), castOther.getFieldId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getFieldId())
      .toHashCode();
  }
  
  public int getFieldLen() {
    return this.fieldLen;
  }
  
  public void setFieldLen(int fieldLen) {
    this.fieldLen = fieldLen;
  }
  
  public String getFieldValue() {
    return this.fieldValue;
  }
  
  public void setFieldValue(String fieldValue) {
    this.fieldValue = fieldValue;
  }
  
  public String getFieldType() {
    return this.fieldType;
  }
  
  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }
  
  public int getFieldNull() {
    return this.fieldNull;
  }
  
  public void setFieldNull(int fieldNull) {
    this.fieldNull = fieldNull;
  }
  
  public String getFieldDefault() {
    return this.fieldDefault;
  }
  
  public void setFieldDefault(String fieldDefault) {
    this.fieldDefault = fieldDefault;
  }
}
