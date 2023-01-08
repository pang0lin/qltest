package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InfoTransferActionForm extends ActionForm {
  private String idString;
  
  private String transferChannel;
  
  public String getIdString() {
    return this.idString;
  }
  
  public void setIdString(String idString) {
    this.idString = idString;
  }
  
  public String getTransferChannel() {
    return this.transferChannel;
  }
  
  public void setTransferChannel(String transferChannel) {
    this.transferChannel = transferChannel;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
