package com.js.oa.hr.kq.szgt.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadKqDataTemplateForm extends ActionForm {
  private FormFile file = null;
  
  public FormFile getFile() {
    return this.file;
  }
  
  public void setFile(FormFile file) {
    this.file = file;
  }
}
