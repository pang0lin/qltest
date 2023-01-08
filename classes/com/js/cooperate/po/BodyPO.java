package com.js.cooperate.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BodyPO implements Serializable {
  private Long id;
  
  private String title;
  
  private String content;
  
  private String type;
  
  private Date postTime = null;
  
  private String posterId;
  
  private String posterName;
  
  private String postOrgName;
  
  private String sendToId;
  
  private String sendToName;
  
  private Integer level;
  
  private Integer status;
  
  private Integer hasTerm;
  
  private Date term = null;
  
  private Long relProjectId;
  
  private Integer infoChannelId;
  
  public Integer getInfoChannelId() {
    return this.infoChannelId;
  }
  
  public void setInfoChannelId(Integer infoChannelId) {
    this.infoChannelId = infoChannelId;
  }
  
  public BodyPO(String title) {
    this.title = title;
  }
  
  public BodyPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
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
  
  public Date getPostTime() {
    return this.postTime;
  }
  
  public void setPostTime(Date postTime) {
    this.postTime = postTime;
  }
  
  public Date getTerm() {
    return this.term;
  }
  
  public void setTerm(Date term) {
    this.term = term;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public String getPostOrgName() {
    return this.postOrgName;
  }
  
  public void setPostOrgName(String postOrgName) {
    this.postOrgName = postOrgName;
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
