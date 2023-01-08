package com.js.oa.security.ip.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ipActionForm extends ActionForm {
  private String ipAddressBegin;
  
  private String ipAddressEnd;
  
  private byte ipIsOpen;
  
  private String ipProposer;
  
  public String getIpAddressBegin() {
    return this.ipAddressBegin;
  }
  
  public void setIpAddressBegin(String ipAddressBegin) {
    this.ipAddressBegin = ipAddressBegin;
  }
  
  public String getIpAddressEnd() {
    return this.ipAddressEnd;
  }
  
  public void setIpAddressEnd(String ipAddressEnd) {
    this.ipAddressEnd = ipAddressEnd;
  }
  
  public byte getIpIsOpen() {
    return this.ipIsOpen;
  }
  
  public void setIpIsOpen(byte ipIsOpen) {
    this.ipIsOpen = ipIsOpen;
  }
  
  public String getIpProposer() {
    return this.ipProposer;
  }
  
  public void setIpProposer(String ipProposer) {
    this.ipProposer = ipProposer;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.ipAddressBegin = null;
    this.ipAddressEnd = null;
    this.ipIsOpen = 0;
    this.ipProposer = null;
  }
}
