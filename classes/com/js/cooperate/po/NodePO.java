package com.js.cooperate.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NodePO implements Serializable {
  private Long id;
  
  private Long bodyId;
  
  private String nodeTitle;
  
  private String nodeRight;
  
  private Integer nodeType;
  
  private Integer nodeStart;
  
  private Integer status;
  
  private Date term = null;
  
  private String nickName;
  
  private String parentNick;
  
  private String isEnd;
  
  private String chuan;
  
  public String getNickName() {
    return this.nickName;
  }
  
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
  
  public String getParentNick() {
    return this.parentNick;
  }
  
  public void setParentNick(String parentNick) {
    this.parentNick = parentNick;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getBodyId() {
    return this.bodyId;
  }
  
  public void setBodyId(Long bodyId) {
    this.bodyId = bodyId;
  }
  
  public String getNodeTitle() {
    return this.nodeTitle;
  }
  
  public void setNodeTitle(String nodeTitle) {
    this.nodeTitle = nodeTitle;
  }
  
  public String getNodeRight() {
    return this.nodeRight;
  }
  
  public void setNodeRight(String nodeRight) {
    this.nodeRight = nodeRight;
  }
  
  public Integer getNodeType() {
    return this.nodeType;
  }
  
  public void setNodeType(Integer nodeType) {
    this.nodeType = nodeType;
  }
  
  public Integer getNodeStart() {
    return this.nodeStart;
  }
  
  public void setNodeStart(Integer nodeStart) {
    this.nodeStart = nodeStart;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Date getTerm() {
    return this.term;
  }
  
  public void setTerm(Date term) {
    this.term = term;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public String getIsEnd() {
    return this.isEnd;
  }
  
  public void setIsEnd(String isEnd) {
    this.isEnd = isEnd;
  }
  
  public String getChuan() {
    return this.chuan;
  }
  
  public void setChuan(String chuan) {
    this.chuan = chuan;
  }
}
