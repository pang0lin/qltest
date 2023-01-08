package com.js.oa.scheme.event.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EventPO implements Serializable {
  private Long eventId;
  
  private Long eventDomainId;
  
  private String eventTitle;
  
  private String eventAddress;
  
  private Long eventBeginDate;
  
  private Long eventEndDate;
  
  private Integer eventBeginTime;
  
  private Integer eventEndTime;
  
  private Long eventLastTime;
  
  private String attendName;
  
  private String attendEmp;
  
  private String attendOrg;
  
  private String attendGroup;
  
  private Integer eventFullDay;
  
  private Integer eventRemind;
  
  private Long eventRemindTime;
  
  private String eventContent;
  
  private Integer onTimeMode;
  
  private String onTimeContent;
  
  private Integer echoMode;
  
  private Date echoBeginTime = null;
  
  private Date echoEndTime = null;
  
  private Integer echoCounter;
  
  private Date eventDate = null;
  
  private Long eventEmpId;
  
  private String eventEmpName;
  
  private Set eventAttenders = null;
  
  private Integer popRemindMode;
  
  private Integer noteRemindMode;
  
  private Long relProjectId;
  
  private String openEvent = "0";
  
  public EventPO(Long eventDomainId, String eventTitle, String eventAddress, Long eventBeginDate, Long eventEndDate, Integer eventBeginTime, Integer eventEndTime, Long eventLastTime, String attendName, String attendEmp, String attendOrg, String attendGroup, Integer eventFullDay, Integer eventRemind, Long eventRemindTime, String eventContent, Integer onTimeMode, String onTimeContent, Integer echoMode, Date echoBeginTime, Date echoEndTime, Integer echoCounter, Date eventDate, Long eventEmpId, String eventEmpName, Set eventAttenders) {
    this.eventTitle = eventTitle;
    this.eventAddress = eventAddress;
    this.eventBeginDate = eventBeginDate;
    this.eventEndDate = eventEndDate;
    this.eventBeginTime = eventBeginTime;
    this.eventEndTime = eventEndTime;
    this.eventLastTime = eventLastTime;
    this.attendName = attendName;
    this.attendEmp = attendEmp;
    this.attendOrg = attendOrg;
    this.attendGroup = attendGroup;
    this.eventFullDay = eventFullDay;
    this.eventRemind = eventRemind;
    this.eventRemindTime = eventRemindTime;
    this.eventContent = eventContent;
    this.onTimeMode = onTimeMode;
    this.onTimeContent = onTimeContent;
    this.echoMode = echoMode;
    this.echoBeginTime = echoBeginTime;
    this.echoEndTime = echoEndTime;
    this.echoCounter = echoCounter;
    this.eventDate = eventDate;
    this.eventEmpId = eventEmpId;
    this.eventEmpName = eventEmpName;
    this.eventAttenders = eventAttenders;
    this.eventDomainId = eventDomainId;
  }
  
  public EventPO() {}
  
  public Long getEventId() {
    return this.eventId;
  }
  
  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }
  
  public Long getEventDomainId() {
    return this.eventDomainId;
  }
  
  public void setEventDomainId(Long eventDomainId) {
    this.eventDomainId = eventDomainId;
  }
  
  public String getEventTitle() {
    return this.eventTitle;
  }
  
  public void setEventTitle(String eventTitle) {
    this.eventTitle = eventTitle;
  }
  
  public String getEventAddress() {
    return this.eventAddress;
  }
  
  public void setEventAddress(String eventAddress) {
    this.eventAddress = eventAddress;
  }
  
  public Long getEventBeginDate() {
    return this.eventBeginDate;
  }
  
  public void setEventBeginDate(Long eventBeginDate) {
    this.eventBeginDate = eventBeginDate;
  }
  
  public Long getEventEndDate() {
    return this.eventEndDate;
  }
  
  public void setEventEndDate(Long eventEndDate) {
    this.eventEndDate = eventEndDate;
  }
  
  public Integer getEventBeginTime() {
    return this.eventBeginTime;
  }
  
  public void setEventBeginTime(Integer eventBeginTime) {
    this.eventBeginTime = eventBeginTime;
  }
  
  public Integer getEventEndTime() {
    return this.eventEndTime;
  }
  
  public void setEventEndTime(Integer eventEndTime) {
    this.eventEndTime = eventEndTime;
  }
  
  public Long getEventLastTime() {
    return this.eventLastTime;
  }
  
  public void setEventLastTime(Long eventLastTime) {
    this.eventLastTime = eventLastTime;
  }
  
  public String getAttendName() {
    return this.attendName;
  }
  
  public void setAttendName(String attendName) {
    this.attendName = attendName;
  }
  
  public String getAttendEmp() {
    return this.attendEmp;
  }
  
  public void setAttendEmp(String attendEmp) {
    this.attendEmp = attendEmp;
  }
  
  public String getAttendOrg() {
    return this.attendOrg;
  }
  
  public void setAttendOrg(String attendOrg) {
    this.attendOrg = attendOrg;
  }
  
  public String getAttendGroup() {
    return this.attendGroup;
  }
  
  public void setAttendGroup(String attendGroup) {
    this.attendGroup = attendGroup;
  }
  
  public Integer getEventFullDay() {
    return this.eventFullDay;
  }
  
  public void setEventFullDay(Integer eventFullDay) {
    this.eventFullDay = eventFullDay;
  }
  
  public Integer getEventRemind() {
    return this.eventRemind;
  }
  
  public void setEventRemind(Integer eventRemind) {
    this.eventRemind = eventRemind;
  }
  
  public Long getEventRemindTime() {
    return this.eventRemindTime;
  }
  
  public void setEventRemindTime(Long eventRemindTime) {
    this.eventRemindTime = eventRemindTime;
  }
  
  public String getEventContent() {
    return this.eventContent;
  }
  
  public void setEventContent(String eventContent) {
    this.eventContent = eventContent;
  }
  
  public Integer getOnTimeMode() {
    return this.onTimeMode;
  }
  
  public void setOnTimeMode(Integer onTimeMode) {
    this.onTimeMode = onTimeMode;
  }
  
  public String getOnTimeContent() {
    return this.onTimeContent;
  }
  
  public void setOnTimeContent(String onTimeContent) {
    this.onTimeContent = onTimeContent;
  }
  
  public Integer getEchoMode() {
    return this.echoMode;
  }
  
  public void setEchoMode(Integer echoMode) {
    this.echoMode = echoMode;
  }
  
  public Date getEchoBeginTime() {
    return this.echoBeginTime;
  }
  
  public void setEchoBeginTime(Date echoBeginTime) {
    this.echoBeginTime = echoBeginTime;
  }
  
  public Date getEchoEndTime() {
    return this.echoEndTime;
  }
  
  public void setEchoEndTime(Date echoEndTime) {
    this.echoEndTime = echoEndTime;
  }
  
  public Integer getEchoCounter() {
    return this.echoCounter;
  }
  
  public void setEchoCounter(Integer echoCounter) {
    this.echoCounter = echoCounter;
  }
  
  public Date getEventDate() {
    return this.eventDate;
  }
  
  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }
  
  public Long getEventEmpId() {
    return this.eventEmpId;
  }
  
  public void setEventEmpId(Long eventEmpId) {
    this.eventEmpId = eventEmpId;
  }
  
  public String getEventEmpName() {
    return this.eventEmpName;
  }
  
  public void setEventEmpName(String eventEmpName) {
    this.eventEmpName = eventEmpName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("eventId", getEventId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EventPO))
      return false; 
    EventPO castOther = (EventPO)other;
    return (new EqualsBuilder())
      .append(getEventId(), castOther.getEventId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEventId())
      .toHashCode();
  }
  
  public Set getEventAttenders() {
    return this.eventAttenders;
  }
  
  public Integer getPopRemindMode() {
    return this.popRemindMode;
  }
  
  public Integer getNoteRemindMode() {
    return this.noteRemindMode;
  }
  
  public void setEventAttenders(Set eventAttenders) {
    this.eventAttenders = eventAttenders;
  }
  
  public void setPopRemindMode(Integer popRemindMode) {
    this.popRemindMode = popRemindMode;
  }
  
  public void setNoteRemindMode(Integer noteRemindMode) {
    this.noteRemindMode = noteRemindMode;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public String getOpenEvent() {
    return this.openEvent;
  }
  
  public void setOpenEvent(String openEvent) {
    this.openEvent = openEvent;
  }
}
