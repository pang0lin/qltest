package com.js.oa.hr.subsidiarywork.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BirthCardActionForm extends ActionForm {
  private String wishCard;
  
  private String wishContent;
  
  private String wishEmployees;
  
  private String wishEmployeesName;
  
  private String wishSign;
  
  private String editId;
  
  private String done;
  
  private String wishCardName;
  
  private String wishCard1;
  
  public String getWishCard() {
    return this.wishCard;
  }
  
  public void setWishCard(String wishCard) {
    this.wishCard = wishCard;
  }
  
  public String getWishContent() {
    return this.wishContent;
  }
  
  public void setWishContent(String wishContent) {
    this.wishContent = wishContent;
  }
  
  public String getWishEmployees() {
    return this.wishEmployees;
  }
  
  public void setWishEmployees(String wishEmployees) {
    this.wishEmployees = wishEmployees;
  }
  
  public String getWishEmployeesName() {
    return this.wishEmployeesName;
  }
  
  public void setWishEmployeesName(String wishEmployeesName) {
    this.wishEmployeesName = wishEmployeesName;
  }
  
  public String getWishSign() {
    return this.wishSign;
  }
  
  public void setWishSign(String wishSign) {
    this.wishSign = wishSign;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.wishCard = null;
    this.wishContent = null;
    this.wishEmployees = null;
    this.wishEmployeesName = null;
    this.wishSign = null;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getWishCardName() {
    return this.wishCardName;
  }
  
  public void setWishCardName(String wishCardName) {
    this.wishCardName = wishCardName;
  }
  
  public String getWishCard1() {
    return this.wishCard1;
  }
  
  public void setWishCard1(String wishCard1) {
    this.wishCard1 = wishCard1;
  }
}
