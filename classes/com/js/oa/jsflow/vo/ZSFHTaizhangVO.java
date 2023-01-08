package com.js.oa.jsflow.vo;

import java.io.Serializable;
import java.util.List;

public class ZSFHTaizhangVO implements Serializable {
  private Object[] mainTable;
  
  private List<Object[]> invoiceTable;
  
  private List<Object[]> sealApplyTable;
  
  private List<Object[]> paymentTable;
  
  private int maxlen;
  
  public int maxLen() {
    int maxLength = 1;
    if (this.invoiceTable.size() == 0 && this.sealApplyTable.size() == 0 && 
      this.paymentTable.size() == 0) {
      maxLength = 1;
    } else {
      if (this.invoiceTable.size() > this.sealApplyTable.size()) {
        maxLength = this.invoiceTable.size();
      } else {
        maxLength = this.sealApplyTable.size();
      } 
      if (maxLength < this.paymentTable.size())
        maxLength = this.paymentTable.size(); 
    } 
    return maxLength;
  }
  
  public Object[] getMainTable() {
    return this.mainTable;
  }
  
  public void setMainTable(Object[] mainTable) {
    this.mainTable = mainTable;
  }
  
  public List<Object[]> getInvoiceTable() {
    return this.invoiceTable;
  }
  
  public void setInvoiceTable(List<Object[]> invoiceTable) {
    this.invoiceTable = invoiceTable;
  }
  
  public List<Object[]> getSealApplyTable() {
    return this.sealApplyTable;
  }
  
  public void setSealApplyTable(List<Object[]> sealApplyTable) {
    this.sealApplyTable = sealApplyTable;
  }
  
  public List<Object[]> getPaymentTable() {
    return this.paymentTable;
  }
  
  public void setPaymentTable(List<Object[]> paymentTable) {
    this.paymentTable = paymentTable;
  }
  
  public int getMaxlen() {
    return this.maxlen;
  }
  
  public void setMaxlen(int maxlen) {
    this.maxlen = maxlen;
  }
}
