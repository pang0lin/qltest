package com.js.oa.crm.po;

import java.io.Serializable;

public class CstTypeRelat implements Serializable {
  private long customerId;
  
  private long customerTypeId;
  
  public long getCustomerId() {
    return this.customerId;
  }
  
  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }
  
  public long getCustomerTypeId() {
    return this.customerTypeId;
  }
  
  public void setCustomerTypeId(long customerTypeId) {
    this.customerTypeId = customerTypeId;
  }
}
