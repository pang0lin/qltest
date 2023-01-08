package com.js.oa.personalwork.paper.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class NotePaperActionForm extends ActionForm {
  private String done;
  
  private String notePaperColor;
  
  private String notePaperContent;
  
  private String editId;
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getNotePaperColor() {
    return this.notePaperColor;
  }
  
  public void setNotePaperColor(String notePaperColor) {
    this.notePaperColor = notePaperColor;
  }
  
  public String getNotePaperContent() {
    return this.notePaperContent;
  }
  
  public void setNotePaperContent(String notePaperContent) {
    this.notePaperContent = notePaperContent;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.done = null;
    this.notePaperColor = null;
    this.notePaperContent = null;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
}
