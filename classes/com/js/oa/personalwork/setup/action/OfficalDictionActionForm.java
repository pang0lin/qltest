package com.js.oa.personalwork.setup.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OfficalDictionActionForm extends ActionForm {
  private String dictionContent;
  
  private byte dictionIsShare;
  
  public String getDictionContent() {
    return this.dictionContent;
  }
  
  public void setDictionContent(String dictionContent) {
    this.dictionContent = dictionContent;
  }
  
  public byte getDictionIsShare() {
    return this.dictionIsShare;
  }
  
  public void setDictionIsShare(byte dictionIsShare) {
    this.dictionIsShare = dictionIsShare;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.dictionContent = null;
    this.dictionIsShare = 0;
  }
}
