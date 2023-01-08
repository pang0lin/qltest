package com.js.oa.personalwork.netaddress.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AddressActionForm extends ActionForm {
  private String done;
  
  private String myAddressClass;
  
  private String netAddressName;
  
  private String netAddressUrl;
  
  private String shareScope;
  
  private String shareToName;
  
  private byte isShare;
  
  private String editId;
  
  private byte isShow;
  
  private String synopsis;
  
  private String saveImg;
  
  private int operate;
  
  int sso = 0;
  
  String formaction;
  
  String formusername;
  
  String formuserpassword;
  
  int ssologin = 0;
  
  String username;
  
  String password;
  
  String formelseparam;
  
  String elseparam;
  
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
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public byte getIsShare() {
    return this.isShare;
  }
  
  public void setIsShare(byte isShare) {
    this.isShare = isShare;
  }
  
  public String getMyAddressClass() {
    return this.myAddressClass;
  }
  
  public void setMyAddressClass(String myAddressClass) {
    this.myAddressClass = myAddressClass;
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
  
  public String getShareScope() {
    return this.shareScope;
  }
  
  public void setShareScope(String shareScope) {
    this.shareScope = shareScope;
  }
  
  public String getShareToName() {
    return this.shareToName;
  }
  
  public void setShareToName(String shareToName) {
    this.shareToName = shareToName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public byte getIsShow() {
    return this.isShow;
  }
  
  public void setIsShow(byte isShow) {
    this.isShow = isShow;
  }
  
  public int getOperate() {
    return this.operate;
  }
  
  public void setOperate(int operate) {
    this.operate = operate;
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
}
