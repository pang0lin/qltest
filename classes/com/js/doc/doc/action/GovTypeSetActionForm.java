package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovTypeSetActionForm extends ActionForm {
  private String typeSetName;
  
  private String typeSetWordNumber;
  
  private String sendToName;
  
  private String sendToId;
  
  private String redHeadId;
  
  public String getTypeSetName() {
    return this.typeSetName;
  }
  
  public void setTypeSetName(String typeSetName) {
    this.typeSetName = typeSetName;
  }
  
  public String getTypeSetWordNumber() {
    return this.typeSetWordNumber;
  }
  
  public void setTypeSetWordNumber(String typeSetWordNumber) {
    this.typeSetWordNumber = typeSetWordNumber;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.typeSetName = null;
    this.typeSetWordNumber = null;
  }
  
  public String getSendToName() {
    return this.sendToName;
  }
  
  public void setSendToName(String sendToName) {
    this.sendToName = sendToName;
  }
  
  public String getSendToId() {
    return this.sendToId;
  }
  
  public void setSendToId(String sendToId) {
    this.sendToId = sendToId;
  }
  
  public String getRedHeadId() {
    return this.redHeadId;
  }
  
  public void setRedHeadId(String redHeadId) {
    this.redHeadId = redHeadId;
  }
}
