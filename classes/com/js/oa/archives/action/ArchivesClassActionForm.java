package com.js.oa.archives.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ArchivesClassActionForm extends ActionForm {
  private String className;
  
  private String classReadName;
  
  private String classReadID;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String saveType;
  
  private String state;
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getClassReadName() {
    return this.classReadName;
  }
  
  public void setClassReadName(String classReadName) {
    this.classReadName = classReadName;
  }
  
  public String getClassReadID() {
    return this.classReadID;
  }
  
  public void setClassReadID(String classReadID) {
    this.classReadID = classReadID;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.className = null;
    this.classReadName = null;
    this.classReadID = null;
    this.createdEmp = null;
    this.createdOrg = null;
  }
}
