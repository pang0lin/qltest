package com.js.oa.scheme.event.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EventAttenderPO implements Serializable {
  private Long attenderId;
  
  private Long empId;
  
  private EventPO event;
  
  private Integer affirmRemind;
  
  private Integer eventIsEcho;
  
  private Integer eventIsViewed;
  
  private Long eventDomainId;
  
  private Integer eventIsPoped;
  
  public EventAttenderPO(Long empId, EventPO event, Integer affirmRemind, Integer eventIsEcho, Integer eventIsViewed) {
    this.empId = empId;
    this.event = event;
    this.affirmRemind = affirmRemind;
    this.eventIsEcho = eventIsEcho;
    this.eventIsViewed = eventIsViewed;
  }
  
  public EventAttenderPO() {}
  
  public Long getAttenderId() {
    return this.attenderId;
  }
  
  public void setAttenderId(Long attenderId) {
    this.attenderId = attenderId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public EventPO getEvent() {
    return this.event;
  }
  
  public void setEvent(EventPO event) {
    this.event = event;
  }
  
  public Integer getAffirmRemind() {
    return this.affirmRemind;
  }
  
  public void setAffirmRemind(Integer affirmRemind) {
    this.affirmRemind = affirmRemind;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("attenderId", getAttenderId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EventAttenderPO))
      return false; 
    EventAttenderPO castOther = (EventAttenderPO)other;
    return (new EqualsBuilder())
      .append(getAttenderId(), castOther.getAttenderId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAttenderId())
      .toHashCode();
  }
  
  public Integer getEventIsEcho() {
    return this.eventIsEcho;
  }
  
  public void setEventIsEcho(Integer eventIsEcho) {
    this.eventIsEcho = eventIsEcho;
  }
  
  public Integer getEventIsViewed() {
    return this.eventIsViewed;
  }
  
  public Long getEventDomainId() {
    return this.eventDomainId;
  }
  
  public Integer getEventIsPoped() {
    return this.eventIsPoped;
  }
  
  public void setEventIsViewed(Integer eventIsViewed) {
    this.eventIsViewed = eventIsViewed;
  }
  
  public void setEventDomainId(Long eventDomainId) {
    this.eventDomainId = eventDomainId;
  }
  
  public void setEventIsPoped(Integer eventIsPoped) {
    this.eventIsPoped = eventIsPoped;
  }
}
