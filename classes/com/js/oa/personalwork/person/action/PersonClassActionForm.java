package com.js.oa.personalwork.person.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PersonClassActionForm extends ActionForm {
  private String classDescribe;
  
  private String className;
  
  private String done;
  
  private String editId;
  
  public String getClassDescribe() {
    return this.classDescribe;
  }
  
  public void setClassDescribe(String classDescribe) {
    this.classDescribe = classDescribe;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.classDescribe = null;
    this.className = null;
    this.done = null;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
}
