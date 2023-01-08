package com.js.oa.box.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BoxActionForm extends ActionForm implements Serializable {
  private Long id;
  
  private String netStr;
  
  private String userID;
  
  private String saveImg;
  
  private String synopsis;
  
  private String Name;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getNetStr() {
    return this.netStr;
  }
  
  public void setNetStr(String netStr) {
    this.netStr = netStr;
  }
  
  public String getUserID() {
    return this.userID;
  }
  
  public void setUserID(String userID) {
    this.userID = userID;
  }
  
  public String getSaveImg() {
    return this.saveImg;
  }
  
  public void setSaveImg(String saveImg) {
    this.saveImg = saveImg;
  }
  
  public String getSynopsis() {
    return this.synopsis;
  }
  
  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }
  
  public String getName() {
    return this.Name;
  }
  
  public void setName(String name) {
    this.Name = name;
  }
}
