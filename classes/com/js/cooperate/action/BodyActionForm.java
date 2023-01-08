package com.js.cooperate.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BodyActionForm extends ActionForm {
  private Long id;
  
  private String title;
  
  private String content;
  
  private String type;
  
  private String posterId;
  
  private String posterName;
  
  private String sendToId;
  
  private String sendToName;
  
  private Integer level;
  
  private Integer status;
  
  private Integer hasTerm;
  
  private Integer InfoChannelId;
  
  public Integer getInfoChannelId() {
    return this.InfoChannelId;
  }
  
  public void setInfoChannelId(Integer infoChannelId) {
    this.InfoChannelId = infoChannelId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getPosterId() {
    return this.posterId;
  }
  
  public void setPosterId(String posterId) {
    this.posterId = posterId;
  }
  
  public String getPosterName() {
    return this.posterName;
  }
  
  public void setPosterName(String posterName) {
    this.posterName = posterName;
  }
  
  public Integer getLevel() {
    return this.level;
  }
  
  public void setLevel(Integer level) {
    this.level = level;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getSendToId() {
    return this.sendToId;
  }
  
  public void setSendToId(String sendToId) {
    this.sendToId = sendToId;
  }
  
  public String getSendToName() {
    return this.sendToName;
  }
  
  public void setSendToName(String sendToName) {
    this.sendToName = sendToName;
  }
  
  public Integer getHasTerm() {
    return this.hasTerm;
  }
  
  public void setHasTerm(Integer hasTerm) {
    this.hasTerm = hasTerm;
  }
}
