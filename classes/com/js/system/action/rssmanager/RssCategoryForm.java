package com.js.system.action.rssmanager;

import com.js.system.service.rssmanager.RssCategoryBD;
import org.apache.struts.action.ActionForm;

public class RssCategoryForm extends ActionForm {
  private Long categoryId;
  
  private String categoryName;
  
  private Long createUserId;
  
  private String createTime;
  
  private String rangeUser;
  
  private String rangeOrg;
  
  private String rangeGroup;
  
  private String string1;
  
  private String string2;
  
  public Long getCategoryId() {
    return this.categoryId;
  }
  
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
  
  public String getCategoryName() {
    return this.categoryName;
  }
  
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public Long getCreateUserId() {
    return this.createUserId;
  }
  
  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }
  
  public String getRangeGroup() {
    return this.rangeGroup;
  }
  
  public void setRangeGroup(String rangeGroup) {
    this.rangeGroup = rangeGroup;
  }
  
  public String getRangeOrg() {
    return this.rangeOrg;
  }
  
  public void setRangeOrg(String rangeOrg) {
    this.rangeOrg = rangeOrg;
  }
  
  public String getRangeUser() {
    return this.rangeUser;
  }
  
  public void setRangeUser(String rangeUser) {
    this.rangeUser = rangeUser;
  }
  
  public String getString1() {
    return this.string1;
  }
  
  public void setString1(String string1) {
    this.string1 = string1;
  }
  
  public String getString2() {
    return this.string2;
  }
  
  public void setString2(String string2) {
    this.string2 = string2;
  }
  
  public static String getCategoryNameById(String categoryId) {
    RssCategoryBD rcb = new RssCategoryBD();
    String name = null;
    if (rcb.getSingleRssCategory(categoryId) != null)
      name = rcb.getSingleRssCategory(categoryId).getCategoryName(); 
    return name;
  }
}
