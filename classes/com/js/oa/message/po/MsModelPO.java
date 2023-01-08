package com.js.oa.message.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsModelPO implements Serializable {
  private Long modelOutId;
  
  private String modelSend;
  
  private String modelId;
  
  private String reday1;
  
  private String reday7;
  
  private String reday3;
  
  private String reday9;
  
  private String reday8;
  
  private String reday10;
  
  private String reday11;
  
  private String reday6;
  
  private String reday5;
  
  private String reday4;
  
  private String reday2;
  
  private String content;
  
  private String domainId;
  
  public void setModelOutId(Long modelOutId) {
    this.modelOutId = modelOutId;
  }
  
  public void setModelSend(String modelSend) {
    this.modelSend = modelSend;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public void setReday1(String reday1) {
    this.reday1 = reday1;
  }
  
  public void setReday2(String reday2) {
    this.reday2 = reday2;
  }
  
  public void setReday3(String reday3) {
    this.reday3 = reday3;
  }
  
  public void setReday4(String reday4) {
    this.reday4 = reday4;
  }
  
  public void setReday5(String reday5) {
    this.reday5 = reday5;
  }
  
  public void setReday6(String reday6) {
    this.reday6 = reday6;
  }
  
  public void setReday7(String reday7) {
    this.reday7 = reday7;
  }
  
  public void setReday9(String reday9) {
    this.reday9 = reday9;
  }
  
  public void setReday8(String reday8) {
    this.reday8 = reday8;
  }
  
  public void setReday10(String reday10) {
    this.reday10 = reday10;
  }
  
  public void setReday11(String reday11) {
    this.reday11 = reday11;
  }
  
  public void setModelId(String modelId) {
    this.modelId = modelId;
  }
  
  public Long getModelOutId() {
    return this.modelOutId;
  }
  
  public String getModelSend() {
    return this.modelSend;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public String getReday1() {
    return this.reday1;
  }
  
  public String getReday2() {
    return this.reday2;
  }
  
  public String getReday3() {
    return this.reday3;
  }
  
  public String getReday4() {
    return this.reday4;
  }
  
  public String getReday5() {
    return this.reday5;
  }
  
  public String getReday6() {
    return this.reday6;
  }
  
  public String getReday7() {
    return this.reday7;
  }
  
  public String getReday9() {
    return this.reday9;
  }
  
  public String getReday8() {
    return this.reday8;
  }
  
  public String getReday10() {
    return this.reday10;
  }
  
  public String getReday11() {
    return this.reday11;
  }
  
  public String getModelId() {
    return this.modelId;
  }
  
  public MsModelPO() {}
  
  public MsModelPO(String modelSend, String modelId, String reday1, String reday7, String reday3, String reday9, String reday8, String reday10, String reday11, String reday6, String reday5, String reday4, String reday2, String content) {
    this.modelSend = modelSend;
    this.modelId = modelId;
    this.reday1 = reday1;
    this.reday7 = reday7;
    this.reday3 = reday3;
    this.reday9 = reday9;
    this.reday8 = reday8;
    this.reday10 = reday10;
    this.reday11 = reday11;
    this.reday6 = reday6;
    this.reday5 = reday5;
    this.reday4 = reday4;
    this.reday2 = reday2;
    this.content = content;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("modelOutId", getModelOutId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsModelPO))
      return false; 
    MsModelPO castOther = (MsModelPO)other;
    return (new EqualsBuilder())
      .append(getModelOutId(), castOther.getModelOutId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getModelOutId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
