package com.js.oa.eform.action;

import org.apache.struts.action.ActionForm;

public class CustomFormActionForm extends ActionForm {
  private String formname;
  
  private String content;
  
  private String code;
  
  private String operate;
  
  private String fieldelt;
  
  private String path;
  
  private String sname;
  
  private String[] id;
  
  private String pageid;
  
  private String filenm;
  
  public String getFormname() {
    return this.formname;
  }
  
  public void setFormname(String formname) {
    this.formname = formname;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getFieldelt() {
    return this.fieldelt;
  }
  
  public void setFieldelt(String fieldelt) {
    this.fieldelt = fieldelt;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
  
  public String getSname() {
    return this.sname;
  }
  
  public void setSname(String sname) {
    this.sname = sname;
  }
  
  public String[] getId() {
    return this.id;
  }
  
  public void setId(String[] id) {
    this.id = id;
  }
  
  public String getPageid() {
    return this.pageid;
  }
  
  public void setPageid(String pageid) {
    this.pageid = pageid;
  }
  
  public String getFilenm() {
    return this.filenm;
  }
  
  public void setFilenm(String filenm) {
    this.filenm = filenm;
  }
}
