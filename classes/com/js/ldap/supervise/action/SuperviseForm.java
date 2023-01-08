package com.js.ldap.supervise.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class SuperviseForm extends ActionForm {
  private FormFile file = null;
  
  public FormFile getFile() {
    return this.file;
  }
  
  public void setFile(FormFile file) {
    this.file = file;
  }
}
