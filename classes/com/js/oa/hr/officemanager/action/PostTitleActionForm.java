package com.js.oa.hr.officemanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PostTitleActionForm extends ActionForm {
  private String action;
  
  private String postTitle;
  
  private String postTitleSeries;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getPostTitle() {
    return this.postTitle;
  }
  
  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }
  
  public String getPostTitleSeries() {
    return this.postTitleSeries;
  }
  
  public void setPostTitleSeries(String postTitleSeries) {
    this.postTitleSeries = postTitleSeries;
  }
}
