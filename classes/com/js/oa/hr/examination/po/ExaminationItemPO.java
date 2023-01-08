package com.js.oa.hr.examination.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationItemPO implements Serializable {
  private Long itemID;
  
  private String itemOption;
  
  private String isResult;
  
  private String orderCode;
  
  private ExaminationStockPO examinationStockPO;
  
  public ExaminationStockPO getExaminationStockPO() {
    return this.examinationStockPO;
  }
  
  public void setExaminationStockPO(ExaminationStockPO examinationStockPO) {
    this.examinationStockPO = examinationStockPO;
  }
  
  public String getIsResult() {
    return this.isResult;
  }
  
  public void setIsResult(String isResult) {
    this.isResult = isResult;
  }
  
  public Long getItemID() {
    return this.itemID;
  }
  
  public void setItemID(Long itemID) {
    this.itemID = itemID;
  }
  
  public String getItemOption() {
    return this.itemOption;
  }
  
  public void setItemOption(String itemOption) {
    this.itemOption = itemOption;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getItemID())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationItemPO))
      return false; 
    ExaminationItemPO castOther = (ExaminationItemPO)other;
    return (new EqualsBuilder())
      .append(getItemID(), castOther.getItemID())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getItemID())
      .toHashCode();
  }
}
