package com.js.oa.personalwork.setup.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MyInfoActionForm extends ActionForm {
  private String empDescribe;
  
  private String empEmail;
  
  private String empEmail2;
  
  private String empEmail3;
  
  private String empGnome;
  
  private String empLivingPhoto;
  
  private String empMobilePhone;
  
  private String empPhone;
  
  private String newPass;
  
  private String oldPass;
  
  private String newPassCon;
  
  private String empbusPhone;
  
  private Integer empsex = new Integer("0");
  
  private String empName;
  
  private String empEnglishName;
  
  private String userAccounts;
  
  private String userSimpleName;
  
  private String weixinId;
  
  public String getEmpbusPhone() {
    return this.empbusPhone;
  }
  
  public void setEmpbusPhone(String empbusPhone) {
    this.empbusPhone = empbusPhone;
  }
  
  public Integer getEmpsex() {
    return this.empsex;
  }
  
  public void setEmpsex(Integer empsex) {
    this.empsex = empsex;
  }
  
  public String getEmpDescribe() {
    return this.empDescribe;
  }
  
  public void setEmpDescribe(String empDescribe) {
    this.empDescribe = empDescribe;
  }
  
  public String getEmpEmail() {
    return this.empEmail;
  }
  
  public void setEmpEmail(String empEmail) {
    this.empEmail = empEmail;
  }
  
  public String getEmpEmail2() {
    return this.empEmail2;
  }
  
  public void setEmpEmail2(String empEmail2) {
    this.empEmail2 = empEmail2;
  }
  
  public String getEmpEmail3() {
    return this.empEmail3;
  }
  
  public void setEmpEmail3(String empEmail3) {
    this.empEmail3 = empEmail3;
  }
  
  public String getEmpGnome() {
    return this.empGnome;
  }
  
  public void setEmpGnome(String empGnome) {
    this.empGnome = empGnome;
  }
  
  public String getEmpLivingPhoto() {
    return this.empLivingPhoto;
  }
  
  public void setEmpLivingPhoto(String empLivingPhoto) {
    this.empLivingPhoto = empLivingPhoto;
  }
  
  public String getEmpMobilePhone() {
    return this.empMobilePhone;
  }
  
  public void setEmpMobilePhone(String empMobilePhone) {
    this.empMobilePhone = empMobilePhone;
  }
  
  public String getEmpPhone() {
    return this.empPhone;
  }
  
  public void setEmpPhone(String empPhone) {
    this.empPhone = empPhone;
  }
  
  public String getNewPass() {
    return this.newPass;
  }
  
  public void setNewPass(String newPass) {
    this.newPass = newPass;
  }
  
  public String getOldPass() {
    return this.oldPass;
  }
  
  public void setOldPass(String oldPass) {
    this.oldPass = oldPass;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.empDescribe = null;
    this.empEmail = null;
    this.empEmail2 = null;
    this.empEmail3 = null;
    this.empGnome = null;
    this.empLivingPhoto = null;
    this.empMobilePhone = null;
    this.empPhone = null;
    this.newPass = null;
    this.oldPass = null;
  }
  
  public String getNewPassCon() {
    return this.newPassCon;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getEmpEnglishName() {
    return this.empEnglishName;
  }
  
  public String getUserAccounts() {
    return this.userAccounts;
  }
  
  public String getUserSimpleName() {
    return this.userSimpleName;
  }
  
  public void setNewPassCon(String newPassCon) {
    this.newPassCon = newPassCon;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setEmpEnglishName(String empEnglishName) {
    this.empEnglishName = empEnglishName;
  }
  
  public void setUserAccounts(String userAccounts) {
    this.userAccounts = userAccounts;
  }
  
  public void setUserSimpleName(String userSimpleName) {
    this.userSimpleName = userSimpleName;
  }
  
  public String getWeixinId() {
    return this.weixinId;
  }
  
  public void setWeixinId(String weixinId) {
    this.weixinId = weixinId;
  }
}
