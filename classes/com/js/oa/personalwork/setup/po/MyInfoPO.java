package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MyInfoPO implements Serializable {
  private long id;
  
  private String empMobilePhone;
  
  private String empEmail;
  
  private String empEmail2;
  
  private String empEmail3;
  
  private String empDescribe;
  
  private String empLivingPhoto;
  
  private String empGnome;
  
  private String userPassword;
  
  private String empPhone;
  
  private String isChangePwd;
  
  private String empbusPhone;
  
  private Integer empsex;
  
  private Date empBirth = null;
  
  private String empName;
  
  private String empEnglishName;
  
  private String userAccounts;
  
  private String userSimpleName;
  
  private String weixinId;
  
  public MyInfoPO(String empMobilePhone, String empEmail, String empEmail2, String empEmail3, String empDescribe, String empLivingPhoto, String empGnome, String userPassword, String empPhone, String isChangePwd, String empbusPhone, Integer empsex, Date empBirth, String empName, String empEnglishName, String userAccounts, String userSimpleName) {
    this.empMobilePhone = empMobilePhone;
    this.empEmail = empEmail;
    this.empEmail2 = empEmail2;
    this.empEmail3 = empEmail3;
    this.empDescribe = empDescribe;
    this.empLivingPhoto = empLivingPhoto;
    this.empGnome = empGnome;
    this.userPassword = userPassword;
    this.empPhone = empPhone;
    this.isChangePwd = isChangePwd;
    this.empbusPhone = empbusPhone;
    this.empsex = empsex;
    this.empBirth = empBirth;
    this.empName = empName;
    this.empEnglishName = empEnglishName;
    this.userAccounts = userAccounts;
    this.userSimpleName = userSimpleName;
  }
  
  public MyInfoPO(String empphone, String empmobilephone, String empemail, String empemail2, String empemail3, String empdescribe, String emplivingphoto, String empgnome, String userpassword) {
    this.empMobilePhone = empmobilephone;
    this.empPhone = empphone;
    this.empEmail = empemail;
    this.empEmail2 = empemail2;
    this.empEmail3 = empemail3;
    this.empDescribe = empdescribe;
    this.empLivingPhoto = emplivingphoto;
    this.empGnome = empgnome;
    this.userPassword = userpassword;
  }
  
  public MyInfoPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MyInfoPO))
      return false; 
    MyInfoPO castOther = (MyInfoPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
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
  
  public void setEmpEmail2(String empEmail2) {
    this.empEmail2 = empEmail2;
  }
  
  public String getEmpEmail2() {
    return this.empEmail2;
  }
  
  public String getEmpEmail3() {
    return this.empEmail3;
  }
  
  public void setEmpEmail3(String empEmail3) {
    this.empEmail3 = empEmail3;
  }
  
  public void setEmpGnome(String empGnome) {
    this.empGnome = empGnome;
  }
  
  public String getEmpGnome() {
    return this.empGnome;
  }
  
  public String getEmpLivingPhoto() {
    return this.empLivingPhoto;
  }
  
  public void setEmpLivingPhoto(String empLivingPhoto) {
    this.empLivingPhoto = empLivingPhoto;
  }
  
  public void setEmpMobilePhone(String empMobilePhone) {
    this.empMobilePhone = empMobilePhone;
  }
  
  public String getEmpMobilePhone() {
    return this.empMobilePhone;
  }
  
  public String getEmpPhone() {
    return this.empPhone;
  }
  
  public void setEmpPhone(String empPhone) {
    this.empPhone = empPhone;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getId() {
    return this.id;
  }
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }
  
  public String getIsChangePwd() {
    return this.isChangePwd;
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
  
  public void setIsChangePwd(String isChangePwd) {
    this.isChangePwd = isChangePwd;
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
  
  public Date getEmpBirth() {
    return this.empBirth;
  }
  
  public void setEmpBirth(Date empBirth) {
    this.empBirth = empBirth;
  }
  
  public String getWeixinId() {
    return this.weixinId;
  }
  
  public void setWeixinId(String weixinId) {
    this.weixinId = weixinId;
  }
}
