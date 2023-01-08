package com.js.oa.message.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MsFlowForm extends ActionForm {
  private String saveType;
  
  private String modelSend;
  
  private Long modelId;
  
  private String receiveMan;
  
  private Date sendTime = null;
  
  private String sendMan;
  
  private String msContent;
  
  private String receiveCode;
  
  private Long sendId;
  
  private Long receiveId;
  
  private String msgtoid;
  
  private String msgto;
  
  private String msgtoidprivate;
  
  private String msgtoidpublic;
  
  private String msgtopublic;
  
  private String msgtoprivate;
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public void setReceiveId(Long receiveId) {
    this.receiveId = receiveId;
  }
  
  public void setSendId(Long sendId) {
    this.sendId = sendId;
  }
  
  public void setReceiveMan(String receiveMan) {
    this.receiveMan = receiveMan;
  }
  
  public void setSendMan(String sendMan) {
    this.sendMan = sendMan;
  }
  
  public void setMsContent(String msContent) {
    this.msContent = msContent;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
  
  public void setModelId(Long modelId) {
    this.modelId = modelId;
  }
  
  public void setModelSend(String modelSend) {
    this.modelSend = modelSend;
  }
  
  public Long getReceiveId() {
    return this.receiveId;
  }
  
  public Long getSendId() {
    return this.sendId;
  }
  
  public String getReceiveMan() {
    return this.receiveMan;
  }
  
  public String getSendMan() {
    return this.sendMan;
  }
  
  public String getMsContent() {
    return this.msContent;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public Long getModelId() {
    return this.modelId;
  }
  
  public String getModelSend() {
    return this.modelSend;
  }
  
  public void setMsgto(String msgto) {
    this.msgto = msgto;
  }
  
  public void setMsgtoid(String msgtoid) {
    this.msgtoid = msgtoid;
  }
  
  public void setMsgtoprivate(String msgtoprivate) {
    this.msgtoprivate = msgtoprivate;
  }
  
  public void setMsgtoidprivate(String msgtoidprivate) {
    this.msgtoidprivate = msgtoidprivate;
  }
  
  public void setMsgtopublic(String msgtopublic) {
    this.msgtopublic = msgtopublic;
  }
  
  public void setMsgtoidpublic(String msgtoidpublic) {
    this.msgtoidpublic = msgtoidpublic;
  }
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public String getMsgto() {
    return this.msgto;
  }
  
  public String getMsgtoid() {
    return this.msgtoid;
  }
  
  public String getMsgtoprivate() {
    return this.msgtoprivate;
  }
  
  public String getMsgtoidprivate() {
    return this.msgtoidprivate;
  }
  
  public String getMsgtopublic() {
    return this.msgtopublic;
  }
  
  public String getMsgtoidpublic() {
    return this.msgtoidpublic;
  }
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.receiveMan = null;
    this.sendTime = null;
    this.sendMan = null;
    this.msContent = null;
    this.receiveCode = null;
    this.sendId = null;
    this.receiveId = null;
    this.modelId = null;
    this.modelSend = null;
    this.msgtoid = null;
    this.msgto = null;
    this.msgtoidprivate = null;
    this.msgtoidpublic = null;
    this.msgtopublic = null;
    this.msgtoprivate = null;
  }
}
