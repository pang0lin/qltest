package com.js.oa.scheme.event.vo;

import java.io.Serializable;

public class EventAttenderVO implements Serializable {
  private Long attenderId;
  
  private Long empId;
  
  private Long eventId;
  
  private Integer affirmRemind;
  
  private Integer eventIsEcho;
  
  private Integer eventIsViewed;
  
  private Integer eventIsPoped;
  
  public EventAttenderVO() {}
  
  public EventAttenderVO(Long attenderId, Long empId, Long eventId, Integer affirmRemind, Integer eventIsEcho, Integer eventIsViewed) {
    this.attenderId = attenderId;
    this.empId = empId;
    this.eventId = eventId;
    this.affirmRemind = affirmRemind;
    this.eventIsEcho = eventIsEcho;
    this.eventIsViewed = eventIsViewed;
  }
  
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
  
  public Long getEventId() {
    return this.eventId;
  }
  
  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }
  
  public Integer getAffirmRemind() {
    return this.affirmRemind;
  }
  
  public void setAffirmRemind(Integer affirmRemind) {
    this.affirmRemind = affirmRemind;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof EventAttenderVO))
      return false; 
    EventAttenderVO eventAttenderVO = (EventAttenderVO)o;
    if ((this.eventIsEcho != null) ? !this.eventIsEcho.equals(eventAttenderVO.eventIsEcho) : (eventAttenderVO.eventIsEcho != null))
      return false; 
    if ((this.eventIsViewed != null) ? !this.eventIsViewed.equals(eventAttenderVO.eventIsViewed) : (eventAttenderVO.eventIsViewed != null))
      return false; 
    if ((this.affirmRemind != null) ? !this.affirmRemind.equals(eventAttenderVO.affirmRemind) : (eventAttenderVO.affirmRemind != null))
      return false; 
    if (!this.attenderId.equals(eventAttenderVO.attenderId))
      return false; 
    if ((this.empId != null) ? !this.empId.equals(eventAttenderVO.empId) : (eventAttenderVO.empId != null))
      return false; 
    if (!this.eventId.equals(eventAttenderVO.eventId))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.attenderId.hashCode();
    result = 29 * result + ((this.empId != null) ? this.empId.hashCode() : 0);
    result = 29 * result + this.eventId.hashCode();
    result = 29 * result + ((this.affirmRemind != null) ? this.affirmRemind.hashCode() : 0);
    result = 29 * result + ((this.eventIsEcho != null) ? this.eventIsEcho.hashCode() : 0);
    result = 29 * result + ((this.eventIsViewed != null) ? this.eventIsViewed.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "EventAttenderVO{attenderId=" + 
      this.attenderId + 
      ", empId=" + this.empId + 
      ", eventId=" + this.eventId + 
      ", affirmRemind=" + this.affirmRemind + 
      ", eventIsEcho" + this.eventIsEcho + 
      ", eventIsViewed" + this.eventIsViewed + 
      "}";
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
  
  public Integer getEventIsPoped() {
    return this.eventIsPoped;
  }
  
  public void setEventIsViewed(Integer eventIsViewed) {
    this.eventIsViewed = eventIsViewed;
  }
  
  public void setEventIsPoped(Integer eventIsPoped) {
    this.eventIsPoped = eventIsPoped;
  }
}
