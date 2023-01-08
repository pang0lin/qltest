package com.js.oa.personalwork.netaddress.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressPO implements Serializable {
  private long id;
  
  private String netAddressName;
  
  private String netAddressUrl;
  
  private long createdEmpId;
  
  private String createdEmpName;
  
  private byte isShare;
  
  private String shareToName;
  
  private String shareToEmp;
  
  private String shareToOrg;
  
  private String shareToGroup;
  
  private AddressClassPO addressClass;
  
  private String deskShowUser;
  
  private String shareDelUserId;
  
  private String domainId;
  
  private String synopsis;
  
  private String saveImg;
  
  private int operate;
  
  int sso;
  
  String formaction;
  
  String formusername;
  
  String formuserpassword;
  
  int ssologin;
  
  String username;
  
  String password;
  
  String formelseparam;
  
  String formelseparamType;
  
  String formelseparamValue;
  
  String elseparam;
  
  int ordernumber;
  
  public int getOrdernumber() {
    return this.ordernumber;
  }
  
  public void setOrdernumber(int ordernumber) {
    this.ordernumber = ordernumber;
  }
  
  public int getSso() {
    return this.sso;
  }
  
  public void setSso(int sso) {
    this.sso = sso;
  }
  
  public String getFormaction() {
    return this.formaction;
  }
  
  public void setFormaction(String formaction) {
    this.formaction = formaction;
  }
  
  public String getFormusername() {
    return this.formusername;
  }
  
  public void setFormusername(String formusername) {
    this.formusername = formusername;
  }
  
  public String getFormuserpassword() {
    return this.formuserpassword;
  }
  
  public void setFormuserpassword(String formuserpassword) {
    this.formuserpassword = formuserpassword;
  }
  
  public int getSsologin() {
    return this.ssologin;
  }
  
  public void setSsologin(int ssologin) {
    this.ssologin = ssologin;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public int getOperate() {
    return this.operate;
  }
  
  public void setOperate(int operate) {
    this.operate = operate;
  }
  
  public AddressPO(AddressClassPO addressClass, byte isShow, String netaddressname, String netaddressurl, long createdempid, String createdempname, byte isshare, String sharetoname, String sharetoemp, String sharetoorg, String sharetogroup, String synopsis, String saveImg, int operate, int sso, String formaction, String formusername, String formuserpassword, int ssologin, String username, String password) {
    this.addressClass = addressClass;
    this.netAddressName = netaddressname;
    this.netAddressUrl = netaddressurl;
    this.createdEmpId = createdempid;
    this.createdEmpName = createdempname;
    this.isShare = isshare;
    this.shareToName = sharetoname;
    this.shareToEmp = sharetoemp;
    this.shareToOrg = sharetoorg;
    this.shareToGroup = sharetogroup;
    this.synopsis = synopsis;
    this.saveImg = saveImg;
    this.operate = operate;
    this.sso = sso;
    this.formaction = formaction;
    this.formusername = formusername;
    this.formuserpassword = formuserpassword;
    this.ssologin = ssologin;
    this.username = username;
    this.password = password;
  }
  
  public AddressPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getNetAddressName() {
    return this.netAddressName;
  }
  
  public void setNetAddressName(String netAddressName) {
    this.netAddressName = netAddressName;
  }
  
  public String getNetAddressUrl() {
    return this.netAddressUrl;
  }
  
  public void setNetAddressUrl(String netAddressUrl) {
    this.netAddressUrl = netAddressUrl;
  }
  
  public long getCreatedEmpId() {
    return this.createdEmpId;
  }
  
  public void setCreatedEmpId(long createdEmpId) {
    this.createdEmpId = createdEmpId;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public byte getIsShare() {
    return this.isShare;
  }
  
  public void setIsShare(byte isShare) {
    this.isShare = isShare;
  }
  
  public String getShareToName() {
    return this.shareToName;
  }
  
  public void setShareToName(String shareToName) {
    this.shareToName = shareToName;
  }
  
  public String getShareToOrg() {
    return this.shareToOrg;
  }
  
  public void setShareToOrg(String shareToOrg) {
    this.shareToOrg = shareToOrg;
  }
  
  public String getShareToGroup() {
    return this.shareToGroup;
  }
  
  public void setShareToGroup(String shareToGroup) {
    this.shareToGroup = shareToGroup;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AddressPO))
      return false; 
    AddressPO castOther = (AddressPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getShareToEmp() {
    return this.shareToEmp;
  }
  
  public void setShareToEmp(String shareToEmp) {
    this.shareToEmp = shareToEmp;
  }
  
  public AddressClassPO getAddressClass() {
    return this.addressClass;
  }
  
  public void setAddressClass(AddressClassPO addressClass) {
    this.addressClass = addressClass;
  }
  
  public String getDeskShowUser() {
    return this.deskShowUser;
  }
  
  public void setDeskShowUser(String deskShowUser) {
    this.deskShowUser = deskShowUser;
  }
  
  public String getShareDelUserId() {
    return this.shareDelUserId;
  }
  
  public void setShareDelUserId(String shareDelUserId) {
    this.shareDelUserId = shareDelUserId;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getSynopsis() {
    return this.synopsis;
  }
  
  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }
  
  public String getSaveImg() {
    return this.saveImg;
  }
  
  public void setSaveImg(String saveImg) {
    this.saveImg = saveImg;
  }
  
  public String getFormelseparam() {
    return this.formelseparam;
  }
  
  public void setFormelseparam(String formelseparam) {
    this.formelseparam = formelseparam;
  }
  
  public String getElseparam() {
    return this.elseparam;
  }
  
  public void setElseparam(String elseparam) {
    this.elseparam = elseparam;
  }
  
  public String getFormelseparamType() {
    return this.formelseparamType;
  }
  
  public void setFormelseparamType(String formelseparamType) {
    this.formelseparamType = formelseparamType;
  }
  
  public String getFormelseparamValue() {
    return this.formelseparamValue;
  }
  
  public void setFormelseparamValue(String formelseparamValue) {
    this.formelseparamValue = formelseparamValue;
  }
}
