package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovTopicWordActionForm extends ActionForm {
  private String topicWordName;
  
  public String getTopicWordName() {
    return this.topicWordName;
  }
  
  public void setTopicWordName(String topicWordName) {
    this.topicWordName = topicWordName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.topicWordName = null;
  }
}
