package com.js.oa.portal.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CustomDesktopLayoutActionForm extends ActionForm {
  private String id;
  
  private String layoutName;
  
  private String userOrgGroup;
  
  private String viewRangeName;
  
  private String xmlFile;
  
  private String showOrder;
  
  private String menuName;
  
  private int menuType;
  
  private String isUrl;
  
  private String url;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getLayoutName() {
    return this.layoutName;
  }
  
  public void setLayoutName(String layoutName) {
    this.layoutName = layoutName;
  }
  
  public String getIsUrl() {
    return this.isUrl;
  }
  
  public void setIsUrl(String isUrl) {
    this.isUrl = isUrl;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
  
  public String getViewRangeName() {
    return this.viewRangeName;
  }
  
  public void setViewRangeName(String viewRangeName) {
    this.viewRangeName = viewRangeName;
  }
  
  public int getMenuType() {
    return this.menuType;
  }
  
  public void setMenuType(int menuType) {
    this.menuType = menuType;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getXmlFile() {
    return this.xmlFile;
  }
  
  public void setXmlFile(String xmlFile) {
    this.xmlFile = xmlFile;
  }
  
  public String getShowOrder() {
    return this.showOrder;
  }
  
  public void setShowOrder(String showOrder) {
    this.showOrder = showOrder;
  }
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
}
