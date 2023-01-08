package com.js.cooperate.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NodeRelPO implements Serializable {
  private Long id;
  
  private Long bodyId;
  
  private Long nodeId;
  
  private Long parentNode;
  
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
  
  public Long getNodeId() {
    return this.nodeId;
  }
  
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }
  
  public Long getParentNode() {
    return this.parentNode;
  }
  
  public void setParentNode(Long parentNode) {
    this.parentNode = parentNode;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
}
