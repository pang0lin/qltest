package com.js.util.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SeqPO implements Serializable {
  private Long seq;
  
  private Long id;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SeqPO))
      return false; 
    SeqPO castOther = (SeqPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getSeq() {
    return this.seq;
  }
  
  public void setSeq(Long seq) {
    this.seq = seq;
  }
}
