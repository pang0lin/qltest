package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovPleaseCheckPO implements Serializable {
  private long id;
  
  private String pleaseCheckContent;
  
  private String pleaseCheckWriterComment;
  
  private String pleaseCheckFinishDate;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private Set idea = null;
  
  private String pleaseCheckLeaderComment;
  
  private byte canEdit;
  
  private String field1;
  
  private String field2;
  
  private String field3;
  
  private String field4;
  
  private String field5;
  
  private String field6;
  
  private String field7;
  
  private String field8;
  
  private String field9;
  
  private String field10;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getPleaseCheckContent() {
    return this.pleaseCheckContent;
  }
  
  public void setPleaseCheckContent(String pleaseCheckContent) {
    this.pleaseCheckContent = pleaseCheckContent;
  }
  
  public String getPleaseCheckWriterComment() {
    return this.pleaseCheckWriterComment;
  }
  
  public void setPleaseCheckWriterComment(String pleaseCheckWriterComment) {
    this.pleaseCheckWriterComment = pleaseCheckWriterComment;
  }
  
  public String getPleaseCheckFinishDate() {
    return this.pleaseCheckFinishDate;
  }
  
  public void setPleaseCheckFinishDate(String pleaseCheckFinishDate) {
    this.pleaseCheckFinishDate = pleaseCheckFinishDate;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovPleaseCheckPO))
      return false; 
    GovPleaseCheckPO castOther = (GovPleaseCheckPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Set getIdea() {
    return this.idea;
  }
  
  public void setIdea(Set idea) {
    this.idea = idea;
  }
  
  public String getPleaseCheckLeaderComment() {
    return this.pleaseCheckLeaderComment;
  }
  
  public void setPleaseCheckLeaderComment(String pleaseCheckLeaderComment) {
    this.pleaseCheckLeaderComment = pleaseCheckLeaderComment;
  }
  
  public byte getCanEdit() {
    return this.canEdit;
  }
  
  public void setCanEdit(byte canEdit) {
    this.canEdit = canEdit;
  }
  
  public String getField1() {
    return this.field1;
  }
  
  public void setField1(String field1) {
    this.field1 = field1;
  }
  
  public String getField2() {
    return this.field2;
  }
  
  public void setField2(String field2) {
    this.field2 = field2;
  }
  
  public String getField3() {
    return this.field3;
  }
  
  public void setField3(String field3) {
    this.field3 = field3;
  }
  
  public String getField4() {
    return this.field4;
  }
  
  public void setField4(String field4) {
    this.field4 = field4;
  }
  
  public String getField5() {
    return this.field5;
  }
  
  public void setField5(String field5) {
    this.field5 = field5;
  }
  
  public String getField6() {
    return this.field6;
  }
  
  public void setField6(String field6) {
    this.field6 = field6;
  }
  
  public String getField7() {
    return this.field7;
  }
  
  public void setField7(String field7) {
    this.field7 = field7;
  }
  
  public String getField8() {
    return this.field8;
  }
  
  public void setField8(String field8) {
    this.field8 = field8;
  }
  
  public String getField9() {
    return this.field9;
  }
  
  public void setField9(String field9) {
    this.field9 = field9;
  }
  
  public String getField10() {
    return this.field10;
  }
  
  public void setField10(String field10) {
    this.field10 = field10;
  }
}
