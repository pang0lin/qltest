package com.js.oa.personalwork.person.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PersonInnerActionForm extends ActionForm {
  private String editId;
  
  private String empAddress;
  
  private String empBusinessFax;
  
  private String empBusinessPhone;
  
  private String empCountry;
  
  private String empCounty;
  
  private String empDescribe;
  
  private String empDuty;
  
  private String empEmail;
  
  private String empEmail2;
  
  private String empEmail3;
  
  private String empEnglishName;
  
  private String empMobilePhone;
  
  private String empName;
  
  private String empPhone;
  
  private String empPosition;
  
  private byte empSex;
  
  private String empState;
  
  private String empWebAddress;
  
  private String queryItem;
  
  private String queryText;
  
  private String userOrganization;
  
  private String myQueryItem;
  
  private String myEmpSex;
  
  private Date myEmpBirth = null;
  
  private String empBirth1;
  
  private String empStateSelect;
  
  private String empStateText;
  
  private String empCountySelect;
  
  private String empCountyText;
  
  private String section;
  
  private String orgName;
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getEmpAddress() {
    return this.empAddress;
  }
  
  public void setEmpAddress(String empAddress) {
    this.empAddress = empAddress;
  }
  
  public String getEmpBusinessFax() {
    return this.empBusinessFax;
  }
  
  public void setEmpBusinessFax(String empBusinessFax) {
    this.empBusinessFax = empBusinessFax;
  }
  
  public String getEmpBusinessPhone() {
    return this.empBusinessPhone;
  }
  
  public void setEmpBusinessPhone(String empBusinessPhone) {
    this.empBusinessPhone = empBusinessPhone;
  }
  
  public String getEmpCountry() {
    return this.empCountry;
  }
  
  public void setEmpCountry(String empCountry) {
    this.empCountry = empCountry;
  }
  
  public String getEmpCounty() {
    return this.empCounty;
  }
  
  public void setEmpCounty(String empCounty) {
    this.empCounty = empCounty;
  }
  
  public String getEmpDescribe() {
    return this.empDescribe;
  }
  
  public void setEmpDescribe(String empDescribe) {
    this.empDescribe = empDescribe;
  }
  
  public String getEmpDuty() {
    return this.empDuty;
  }
  
  public void setEmpDuty(String empDuty) {
    this.empDuty = empDuty;
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
  
  public String getEmpEnglishName() {
    return this.empEnglishName;
  }
  
  public void setEmpEnglishName(String empEnglishName) {
    this.empEnglishName = empEnglishName;
  }
  
  public String getEmpMobilePhone() {
    return this.empMobilePhone;
  }
  
  public void setEmpMobilePhone(String empMobilePhone) {
    this.empMobilePhone = empMobilePhone;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpPhone() {
    return this.empPhone;
  }
  
  public void setEmpPhone(String empPhone) {
    this.empPhone = empPhone;
  }
  
  public String getEmpPosition() {
    return this.empPosition;
  }
  
  public void setEmpPosition(String empPosition) {
    this.empPosition = empPosition;
  }
  
  public byte getEmpSex() {
    return this.empSex;
  }
  
  public void setEmpSex(byte empSex) {
    this.empSex = empSex;
  }
  
  public String getEmpState() {
    return this.empState;
  }
  
  public void setEmpState(String empState) {
    this.empState = empState;
  }
  
  public String getEmpWebAddress() {
    return this.empWebAddress;
  }
  
  public void setEmpWebAddress(String empWebAddress) {
    this.empWebAddress = empWebAddress;
  }
  
  public Date getMyEmpBirth() {
    return this.myEmpBirth;
  }
  
  public void setMyEmpBirth(Date myEmpBirth) {
    this.myEmpBirth = myEmpBirth;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.editId = null;
    this.empAddress = null;
    this.empBusinessFax = null;
    this.empBusinessPhone = null;
    this.empCountry = null;
    this.empCounty = null;
    this.empDescribe = null;
    this.empDuty = null;
    this.empEmail = null;
    this.empEmail2 = null;
    this.empEmail3 = null;
    this.empEnglishName = null;
    this.empMobilePhone = null;
    this.empName = null;
    this.empPhone = null;
    this.empPosition = null;
    this.empSex = 0;
    this.empState = null;
    this.empWebAddress = null;
    this.myEmpBirth = null;
  }
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getUserOrganization() {
    return this.userOrganization;
  }
  
  public void setUserOrganization(String userOrganization) {
    this.userOrganization = userOrganization;
  }
  
  public String getMyQueryItem() {
    return this.myQueryItem;
  }
  
  public void setMyQueryItem(String myQueryItem) {
    this.myQueryItem = myQueryItem;
  }
  
  public String getMyEmpSex() {
    return this.myEmpSex;
  }
  
  public void setMyEmpSex(String myEmpSex) {
    this.myEmpSex = myEmpSex;
  }
  
  public String getEmpBirth1() {
    return this.empBirth1;
  }
  
  public void setEmpBirth1(String empBirth1) {
    this.empBirth1 = empBirth1;
  }
  
  public String getEmpStateSelect() {
    return this.empStateSelect;
  }
  
  public void setEmpStateSelect(String empStateSelect) {
    this.empStateSelect = empStateSelect;
  }
  
  public String getEmpStateText() {
    return this.empStateText;
  }
  
  public void setEmpStateText(String empStateText) {
    this.empStateText = empStateText;
  }
  
  public String getEmpCountySelect() {
    return this.empCountySelect;
  }
  
  public void setEmpCountySelect(String empCountySelect) {
    this.empCountySelect = empCountySelect;
  }
  
  public String getEmpCountyText() {
    return this.empCountyText;
  }
  
  public void setEmpCountyText(String empCountyText) {
    this.empCountyText = empCountyText;
  }
  
  public String getSection() {
    return this.section;
  }
  
  public void setSection(String section) {
    this.section = section;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
}
