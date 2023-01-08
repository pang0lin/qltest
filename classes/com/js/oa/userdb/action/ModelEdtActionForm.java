package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class ModelEdtActionForm extends ActionForm {
  private String modelid;
  
  private String modelname;
  
  private String modelcode;
  
  private String modeldis;
  
  public String getModelid() {
    return this.modelid;
  }
  
  public void setModelid(String modelid) {
    this.modelid = modelid;
  }
  
  public String getModelname() {
    return this.modelname;
  }
  
  public void setModelname(String modelname) {
    this.modelname = modelname;
  }
  
  public String getModelcode() {
    return this.modelcode;
  }
  
  public void setModelcode(String modelcode) {
    this.modelcode = modelcode;
  }
  
  public String getModeldis() {
    return this.modeldis;
  }
  
  public void setModeldis(String modeldis) {
    this.modeldis = modeldis;
  }
}
