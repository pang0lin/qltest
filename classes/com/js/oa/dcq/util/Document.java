package com.js.oa.dcq.util;

import java.util.List;

public class Document {
  private String execMsg;
  
  private String execCode;
  
  private List<DocumentAccdoc> accDoc;
  
  public String getExecMsg() {
    return this.execMsg;
  }
  
  public void setExecMsg(String execMsg) {
    this.execMsg = execMsg;
  }
  
  public String getExecCode() {
    return this.execCode;
  }
  
  public void setExecCode(String execCode) {
    this.execCode = execCode;
  }
  
  public List<DocumentAccdoc> getAccDoc() {
    return this.accDoc;
  }
  
  public void setAccDoc(List<DocumentAccdoc> accDoc) {
    this.accDoc = accDoc;
  }
}
