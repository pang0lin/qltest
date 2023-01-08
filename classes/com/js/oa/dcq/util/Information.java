package com.js.oa.dcq.util;

import java.util.List;

public class Information {
  private String errorMsg;
  
  private String resultCode;
  
  private List<InformationAccdoc> accNotice;
  
  public String getErrorMsg() {
    return this.errorMsg;
  }
  
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
  
  public String getResultCode() {
    return this.resultCode;
  }
  
  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }
  
  public List<InformationAccdoc> getAccNotice() {
    return this.accNotice;
  }
  
  public void setAccNotice(List<InformationAccdoc> accNotice) {
    this.accNotice = accNotice;
  }
}
