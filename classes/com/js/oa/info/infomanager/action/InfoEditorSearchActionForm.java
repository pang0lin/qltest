package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InfoEditorSearchActionForm extends ActionForm {
  private String infoeditor;
  
  private String infoeditorcomp;
  
  private String searchtype;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getInfoeditor() {
    return this.infoeditor;
  }
  
  public void setInfoeditor(String infoeditor) {
    this.infoeditor = infoeditor;
  }
  
  public String getInfoeditorcomp() {
    return this.infoeditorcomp;
  }
  
  public void setInfoeditorcomp(String infoeditorcomp) {
    this.infoeditorcomp = infoeditorcomp;
  }
  
  public String getSearchtype() {
    return this.searchtype;
  }
  
  public void setSearchtype(String searchtype) {
    this.searchtype = searchtype;
  }
}
