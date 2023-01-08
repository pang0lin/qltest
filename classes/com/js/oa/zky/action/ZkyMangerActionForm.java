package com.js.oa.zky.action;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ZkyMangerActionForm extends ActionForm implements Serializable {
  private Long mangerId;
  
  private String mangerType;
  
  private String mangerUsers;
  
  private String mangerUsername;
  
  private String mangerMenu;
  
  private String mangerMenuName;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getMangerId() {
    return this.mangerId;
  }
  
  public void setMangerId(Long mangerId) {
    this.mangerId = mangerId;
  }
  
  public String getMangerType() {
    return this.mangerType;
  }
  
  public void setMangerType(String mangerType) {
    this.mangerType = mangerType;
  }
  
  public String getMangerUsers() {
    return this.mangerUsers;
  }
  
  public void setMangerUsers(String mangerUsers) {
    this.mangerUsers = mangerUsers;
  }
  
  public String getMangerUsername() {
    return this.mangerUsername;
  }
  
  public void setMangerUsername(String mangerUsername) {
    this.mangerUsername = mangerUsername;
  }
  
  public String getMangerMenu() {
    return this.mangerMenu;
  }
  
  public void setMangerMenu(String mangerMenu) {
    this.mangerMenu = mangerMenu;
  }
  
  public String getMangerMenuName() {
    return this.mangerMenuName;
  }
  
  public void setMangerMenuName(String mangerMenuName) {
    this.mangerMenuName = mangerMenuName;
  }
}
