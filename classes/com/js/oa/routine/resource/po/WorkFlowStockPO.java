package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkFlowStockPO implements Serializable {
  private Long workFlowStockId;
  
  private Long processId;
  
  private StockPO stock;
  
  private String stockType;
  
  public Long getWorkFlowStockId() {
    return this.workFlowStockId;
  }
  
  public void setWorkFlowStockId(Long workFlowStockId) {
    this.workFlowStockId = workFlowStockId;
  }
  
  public StockPO getStock() {
    return this.stock;
  }
  
  public void setStock(StockPO stock) {
    this.stock = stock;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public String getStockType() {
    return this.stockType;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public void setStockType(String stockType) {
    this.stockType = stockType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkFlowStockPO))
      return false; 
    WorkFlowStockPO castOther = (WorkFlowStockPO)other;
    return (new EqualsBuilder()).append(getWorkFlowStockId(), castOther.getWorkFlowStockId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWorkFlowStockId()).toHashCode();
  }
}
