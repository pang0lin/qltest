package com.js.oa.personalwork.paper.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NotePaperPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String notePaperContent;
  
  private String notePaperColor;
  
  private Date createdTime = null;
  
  private Date updateTime = null;
  
  private String domainId;
  
  private int showno;
  
  public NotePaperPO(long empId, String notepapercontent, String notepapercolor, Date createdtime, Date updatetime, int showno) {
    this.empId = empId;
    this.notePaperContent = notepapercontent;
    this.notePaperColor = notepapercolor;
    this.createdTime = createdtime;
    this.updateTime = updatetime;
    this.showno = showno;
  }
  
  public NotePaperPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getNotePaperContent() {
    return this.notePaperContent;
  }
  
  public void setNotePaperContent(String notePaperContent) {
    this.notePaperContent = notePaperContent;
  }
  
  public String getNotePaperColor() {
    return this.notePaperColor;
  }
  
  public void setNotePaperColor(String notePaperColor) {
    this.notePaperColor = notePaperColor;
  }
  
  public Date getCreatedTime() {
    return this.createdTime;
  }
  
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  
  public Date getUpdateTime() {
    return this.updateTime;
  }
  
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof NotePaperPO))
      return false; 
    NotePaperPO castOther = (NotePaperPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public int getShowno() {
    return this.showno;
  }
  
  public void setShowno(int showno) {
    this.showno = showno;
  }
}
