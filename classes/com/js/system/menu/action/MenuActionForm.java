package com.js.system.menu.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MenuActionForm extends ActionForm {
  private Long menuId;
  
  private String menuView;
  
  private String menuViewId;
  
  private String menuURL;
  
  private String menuOrder;
  
  private String menuParent;
  
  private String menuName;
  
  private String deskTop1;
  
  private String deskTop2;
  
  public Long getMenuId() {
    return this.menuId;
  }
  
  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }
  
  public String getMenuView() {
    return this.menuView;
  }
  
  public void setMenuView(String menuView) {
    this.menuView = menuView;
  }
  
  public String getMenuViewId() {
    return this.menuViewId;
  }
  
  public void setMenuViewId(String menuViewId) {
    this.menuViewId = menuViewId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getMenuURL() {
    return this.menuURL;
  }
  
  public void setMenuURL(String menuURL) {
    this.menuURL = menuURL;
  }
  
  public String getMenuOrder() {
    return this.menuOrder;
  }
  
  public void setMenuOrder(String menuOrder) {
    this.menuOrder = menuOrder;
  }
  
  public String getMenuParent() {
    return this.menuParent;
  }
  
  public void setMenuParent(String menuParent) {
    this.menuParent = menuParent;
  }
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
  
  public String getDeskTop1() {
    return this.deskTop1;
  }
  
  public void setDeskTop1(String deskTop1) {
    this.deskTop1 = deskTop1;
  }
  
  public String getDeskTop2() {
    return this.deskTop2;
  }
  
  public void setDeskTop2(String deskTop2) {
    this.deskTop2 = deskTop2;
  }
}
