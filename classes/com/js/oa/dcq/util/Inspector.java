package com.js.oa.dcq.util;

import java.util.List;

public class Inspector {
  private String execMsg;
  
  private String exeCode;
  
  private List<InspectorAccdoc> jsonArray;
  
  public String getExecMsg() {
    return this.execMsg;
  }
  
  public void setExecMsg(String execMsg) {
    this.execMsg = execMsg;
  }
  
  public String getExeCode() {
    return this.exeCode;
  }
  
  public void setExeCode(String exeCode) {
    this.exeCode = exeCode;
  }
  
  public List<InspectorAccdoc> getJsonArray() {
    return this.jsonArray;
  }
  
  public void setJsonArray(List<InspectorAccdoc> jsonArray) {
    this.jsonArray = jsonArray;
  }
}
