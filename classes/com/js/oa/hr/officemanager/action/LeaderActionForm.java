package com.js.oa.hr.officemanager.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LeaderActionForm extends ActionForm implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String field;
  
  private String fileName;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public String getField() {
    return this.field;
  }
  
  public void setField(String field) {
    this.field = field;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
