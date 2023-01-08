package com.js.oa.scheme.event.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventVO implements Serializable {
  private Long eventId;
  
  private Long eventDomainId;
  
  private String eventTitle;
  
  private String eventAddress;
  
  private Long eventBeginDate;
  
  private String eventBeginDateFormat;
  
  private Long eventEndDate;
  
  private String eventEndDateFormat;
  
  private Integer eventBeginTime;
  
  private String eventBeginTimeFormat;
  
  private Integer eventEndTime;
  
  private String eventEndTimeFormat;
  
  private Long eventLastTime;
  
  private String eventLastTimeformat;
  
  private String attendName;
  
  private String attendEmp;
  
  private String attendOrg;
  
  private String attendGroup;
  
  private Integer eventFullDay;
  
  private Integer eventRemind;
  
  private Long eventRemindTime;
  
  private String eventRemindTimeFormat;
  
  private String eventContent;
  
  private Integer onTimeMode;
  
  private String onTimeContent;
  
  private Integer echoMode;
  
  private Date echoBeginTime = null;
  
  private String echoBeginTimeFormat;
  
  private Date echoEndTime = null;
  
  private String echoEndTimeFormat;
  
  private Integer echoCounter;
  
  private Date eventDate = null;
  
  private String eventDateFormat;
  
  private Long eventEmpId;
  
  private String eventEmpName;
  
  private Boolean eventIsEcho;
  
  private Boolean canDelete;
  
  private Boolean canModify;
  
  private Boolean isShare;
  
  private Boolean isViewed;
  
  private Boolean isAffirmRemind;
  
  private Integer popRemindMode;
  
  private Integer noteRemindMode;
  
  private Long relProjectId;
  
  private String openEvent = "0";
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof EventVO))
      return false; 
    EventVO eventVO = (EventVO)o;
    if ((this.attendEmp != null) ? !this.attendEmp.equals(eventVO.attendEmp) : (eventVO.attendEmp != null))
      return false; 
    if ((this.attendGroup != null) ? !this.attendGroup.equals(eventVO.attendGroup) : (eventVO.attendGroup != null))
      return false; 
    if ((this.attendName != null) ? !this.attendName.equals(eventVO.attendName) : (eventVO.attendName != null))
      return false; 
    if ((this.attendOrg != null) ? !this.attendOrg.equals(eventVO.attendOrg) : (eventVO.attendOrg != null))
      return false; 
    if ((this.canDelete != null) ? !this.canDelete.equals(eventVO.canDelete) : (eventVO.canDelete != null))
      return false; 
    if ((this.canModify != null) ? !this.canModify.equals(eventVO.canModify) : (eventVO.canModify != null))
      return false; 
    if ((this.echoBeginTime != null) ? !this.echoBeginTime.equals(eventVO.echoBeginTime) : (eventVO.echoBeginTime != null))
      return false; 
    if ((this.echoBeginTimeFormat != null) ? !this.echoBeginTimeFormat.equals(eventVO.echoBeginTimeFormat) : (eventVO.echoBeginTimeFormat != null))
      return false; 
    if ((this.echoCounter != null) ? !this.echoCounter.equals(eventVO.echoCounter) : (eventVO.echoCounter != null))
      return false; 
    if ((this.echoEndTime != null) ? !this.echoEndTime.equals(eventVO.echoEndTime) : (eventVO.echoEndTime != null))
      return false; 
    if ((this.echoEndTimeFormat != null) ? !this.echoEndTimeFormat.equals(eventVO.echoEndTimeFormat) : (eventVO.echoEndTimeFormat != null))
      return false; 
    if ((this.echoMode != null) ? !this.echoMode.equals(eventVO.echoMode) : (eventVO.echoMode != null))
      return false; 
    if ((this.eventAddress != null) ? !this.eventAddress.equals(eventVO.eventAddress) : (eventVO.eventAddress != null))
      return false; 
    if ((this.eventBeginDate != null) ? !this.eventBeginDate.equals(eventVO.eventBeginDate) : (eventVO.eventBeginDate != null))
      return false; 
    if ((this.eventBeginDateFormat != null) ? !this.eventBeginDateFormat.equals(eventVO.eventBeginDateFormat) : (eventVO.eventBeginDateFormat != null))
      return false; 
    if ((this.eventBeginTime != null) ? !this.eventBeginTime.equals(eventVO.eventBeginTime) : (eventVO.eventBeginTime != null))
      return false; 
    if ((this.eventBeginTimeFormat != null) ? !this.eventBeginTimeFormat.equals(eventVO.eventBeginTimeFormat) : (eventVO.eventBeginTimeFormat != null))
      return false; 
    if ((this.eventContent != null) ? !this.eventContent.equals(eventVO.eventContent) : (eventVO.eventContent != null))
      return false; 
    if ((this.eventDate != null) ? !this.eventDate.equals(eventVO.eventDate) : (eventVO.eventDate != null))
      return false; 
    if ((this.eventDateFormat != null) ? !this.eventDateFormat.equals(eventVO.eventDateFormat) : (eventVO.eventDateFormat != null))
      return false; 
    if ((this.eventEmpId != null) ? !this.eventEmpId.equals(eventVO.eventEmpId) : (eventVO.eventEmpId != null))
      return false; 
    if ((this.eventEmpName != null) ? !this.eventEmpName.equals(eventVO.eventEmpName) : (eventVO.eventEmpName != null))
      return false; 
    if ((this.eventEndDate != null) ? !this.eventEndDate.equals(eventVO.eventEndDate) : (eventVO.eventEndDate != null))
      return false; 
    if ((this.eventEndDateFormat != null) ? !this.eventEndDateFormat.equals(eventVO.eventEndDateFormat) : (eventVO.eventEndDateFormat != null))
      return false; 
    if ((this.eventEndTime != null) ? !this.eventEndTime.equals(eventVO.eventEndTime) : (eventVO.eventEndTime != null))
      return false; 
    if ((this.eventEndTimeFormat != null) ? !this.eventEndTimeFormat.equals(eventVO.eventEndTimeFormat) : (eventVO.eventEndTimeFormat != null))
      return false; 
    if ((this.eventFullDay != null) ? !this.eventFullDay.equals(eventVO.eventFullDay) : (eventVO.eventFullDay != null))
      return false; 
    if (!this.eventId.equals(eventVO.eventId))
      return false; 
    if ((this.eventIsEcho != null) ? !this.eventIsEcho.equals(eventVO.eventIsEcho) : (eventVO.eventIsEcho != null))
      return false; 
    if ((this.eventLastTime != null) ? !this.eventLastTime.equals(eventVO.eventLastTime) : (eventVO.eventLastTime != null))
      return false; 
    if ((this.eventLastTimeformat != null) ? !this.eventLastTimeformat.equals(eventVO.eventLastTimeformat) : (eventVO.eventLastTimeformat != null))
      return false; 
    if ((this.eventRemind != null) ? !this.eventRemind.equals(eventVO.eventRemind) : (eventVO.eventRemind != null))
      return false; 
    if ((this.eventRemindTime != null) ? !this.eventRemindTime.equals(eventVO.eventRemindTime) : (eventVO.eventRemindTime != null))
      return false; 
    if ((this.eventRemindTimeFormat != null) ? !this.eventRemindTimeFormat.equals(eventVO.eventRemindTimeFormat) : (eventVO.eventRemindTimeFormat != null))
      return false; 
    if ((this.eventTitle != null) ? !this.eventTitle.equals(eventVO.eventTitle) : (eventVO.eventTitle != null))
      return false; 
    if ((this.onTimeContent != null) ? !this.onTimeContent.equals(eventVO.onTimeContent) : (eventVO.onTimeContent != null))
      return false; 
    if ((this.onTimeMode != null) ? !this.onTimeMode.equals(eventVO.onTimeMode) : (eventVO.onTimeMode != null))
      return false; 
    if ((this.isShare != null) ? !this.isShare.equals(eventVO.isShare) : (eventVO.isShare != null))
      return false; 
    if ((this.isViewed != null) ? !this.isViewed.equals(eventVO.isViewed) : (eventVO.isViewed != null))
      return false; 
    if ((this.isAffirmRemind != null) ? !this.isAffirmRemind.equals(eventVO.isAffirmRemind) : (eventVO.isAffirmRemind != null))
      return false; 
    if ((this.eventDomainId != null) ? !this.eventDomainId.equals(eventVO.eventDomainId) : (eventVO.eventDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.eventId.hashCode();
    result = 29 * result + ((this.eventTitle != null) ? this.eventTitle.hashCode() : 0);
    result = 29 * result + ((this.eventAddress != null) ? this.eventAddress.hashCode() : 0);
    result = 29 * result + ((this.eventBeginDate != null) ? this.eventBeginDate.hashCode() : 0);
    result = 29 * result + ((this.eventBeginDateFormat != null) ? this.eventBeginDateFormat.hashCode() : 0);
    result = 29 * result + ((this.eventEndDate != null) ? this.eventEndDate.hashCode() : 0);
    result = 29 * result + ((this.eventEndDateFormat != null) ? this.eventEndDateFormat.hashCode() : 0);
    result = 29 * result + ((this.eventBeginTime != null) ? this.eventBeginTime.hashCode() : 0);
    result = 29 * result + ((this.eventBeginTimeFormat != null) ? this.eventBeginTimeFormat.hashCode() : 0);
    result = 29 * result + ((this.eventEndTime != null) ? this.eventEndTime.hashCode() : 0);
    result = 29 * result + ((this.eventEndTimeFormat != null) ? this.eventEndTimeFormat.hashCode() : 0);
    result = 29 * result + ((this.eventLastTime != null) ? this.eventLastTime.hashCode() : 0);
    result = 29 * result + ((this.eventLastTimeformat != null) ? this.eventLastTimeformat.hashCode() : 0);
    result = 29 * result + ((this.attendName != null) ? this.attendName.hashCode() : 0);
    result = 29 * result + ((this.attendEmp != null) ? this.attendEmp.hashCode() : 0);
    result = 29 * result + ((this.attendOrg != null) ? this.attendOrg.hashCode() : 0);
    result = 29 * result + ((this.attendGroup != null) ? this.attendGroup.hashCode() : 0);
    result = 29 * result + ((this.eventFullDay != null) ? this.eventFullDay.hashCode() : 0);
    result = 29 * result + ((this.eventRemind != null) ? this.eventRemind.hashCode() : 0);
    result = 29 * result + ((this.eventRemindTime != null) ? this.eventRemindTime.hashCode() : 0);
    result = 29 * result + ((this.eventRemindTimeFormat != null) ? this.eventRemindTimeFormat.hashCode() : 0);
    result = 29 * result + ((this.eventContent != null) ? this.eventContent.hashCode() : 0);
    result = 29 * result + ((this.onTimeMode != null) ? this.onTimeMode.hashCode() : 0);
    result = 29 * result + ((this.onTimeContent != null) ? this.onTimeContent.hashCode() : 0);
    result = 29 * result + ((this.echoMode != null) ? this.echoMode.hashCode() : 0);
    result = 29 * result + ((this.echoBeginTime != null) ? this.echoBeginTime.hashCode() : 0);
    result = 29 * result + ((this.echoBeginTimeFormat != null) ? this.echoBeginTimeFormat.hashCode() : 0);
    result = 29 * result + ((this.echoEndTime != null) ? this.echoEndTime.hashCode() : 0);
    result = 29 * result + ((this.echoEndTimeFormat != null) ? this.echoEndTimeFormat.hashCode() : 0);
    result = 29 * result + ((this.echoCounter != null) ? this.echoCounter.hashCode() : 0);
    result = 29 * result + ((this.eventDate != null) ? this.eventDate.hashCode() : 0);
    result = 29 * result + ((this.eventDateFormat != null) ? this.eventDateFormat.hashCode() : 0);
    result = 29 * result + ((this.eventEmpId != null) ? this.eventEmpId.hashCode() : 0);
    result = 29 * result + ((this.eventEmpName != null) ? this.eventEmpName.hashCode() : 0);
    result = 29 * result + ((this.eventIsEcho != null) ? this.eventIsEcho.hashCode() : 0);
    result = 29 * result + ((this.canDelete != null) ? this.canDelete.hashCode() : 0);
    result = 29 * result + ((this.canModify != null) ? this.canModify.hashCode() : 0);
    result = 29 * result + ((this.isShare != null) ? this.isShare.hashCode() : 0);
    result = 29 * result + ((this.isViewed != null) ? this.isViewed.hashCode() : 0);
    result = 29 * result + ((this.isAffirmRemind != null) ? this.isAffirmRemind.hashCode() : 0);
    result = 29 * result + ((this.eventDomainId != null) ? this.eventDomainId.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "EventVO{eventId=" + 
      this.eventId + 
      ", eventDomainId=" + this.eventDomainId + 
      ", eventTitle='" + this.eventTitle + "'" + 
      ", eventAddress='" + this.eventAddress + "'" + 
      ", eventBeginDate=" + this.eventBeginDate + 
      ", eventBeginDateFormat='" + this.eventBeginDateFormat + "'" + 
      ", eventEndDate=" + this.eventEndDate + 
      ", eventEndDateFormat='" + this.eventEndDateFormat + "'" + 
      ", eventBeginTime=" + this.eventBeginTime + 
      ", eventBeginTimeFormat='" + this.eventBeginTimeFormat + "'" + 
      ", eventEndTime=" + this.eventEndTime + 
      ", eventEndTimeFormat='" + this.eventEndTimeFormat + "'" + 
      ", eventLastTime=" + this.eventLastTime + 
      ", eventLastTimeformat='" + this.eventLastTimeformat + "'" + 
      ", attendName='" + this.attendName + "'" + 
      ", attendEmp='" + this.attendEmp + "'" + 
      ", attendOrg='" + this.attendOrg + "'" + 
      ", attendGroup='" + this.attendGroup + "'" + 
      ", eventFullDay=" + this.eventFullDay + 
      ", eventRemind=" + this.eventRemind + 
      ", eventRemindTime=" + this.eventRemindTime + 
      ", eventRemindTimeFormat='" + this.eventRemindTimeFormat + "'" + 
      ", eventContent='" + this.eventContent + "'" + 
      ", onTimeMode=" + this.onTimeMode + 
      ", onTimeContent='" + this.onTimeContent + "'" + 
      ", echoMode=" + this.echoMode + 
      ", echoBeginTime=" + this.echoBeginTime + 
      ", echoBeginTimeFormat='" + this.echoBeginTimeFormat + "'" + 
      ", echoEndTime=" + this.echoEndTime + 
      ", echoEndTimeFormat='" + this.echoEndTimeFormat + "'" + 
      ", echoCounter=" + this.echoCounter + 
      ", eventDate=" + this.eventDate + 
      ", eventDateFormat='" + this.eventDateFormat + "'" + 
      ", eventEmpId=" + this.eventEmpId + 
      ", eventEmpName='" + this.eventEmpName + "'" + 
      ", eventIsEcho=" + this.eventIsEcho + 
      ", canDelete=" + this.canDelete + 
      ", canModify=" + this.canModify + 
      ", isShare=" + this.isShare + 
      ", isViewed=" + this.isViewed + 
      ", isAffirmRemind=" + this.isAffirmRemind + 
      "}";
  }
  
  public EventVO() {}
  
  public EventVO(Long eventId, Long eventDomainId, String eventTitle, String eventAddress, Long eventBeginDate, String eventBeginDateFormat, Long eventEndDate, String eventEndDateFormat, Integer eventBeginTime, String eventBeginTimeFormat, Integer eventEndTime, String eventEndTimeFormat, Long eventLastTime, String eventLastTimeformat, String attendName, String attendEmp, String attendOrg, String attendGroup, Integer eventFullDay, Integer eventRemind, Long eventRemindTime, String eventRemindTimeFormat, String eventContent, Integer onTimeMode, String onTimeContent, Integer echoMode, Date echoBeginTime, String echoBeginTimeFormat, Date echoEndTime, String echoEndTimeFormat, Integer echoCounter, Date eventDate, String eventDateFormat, Long eventEmpId, String eventEmpName, Boolean eventIsEcho, Boolean canDelete, Boolean canModify, Boolean isShare, Boolean isViewed, Boolean isAffirmRemind) {
    this.eventId = eventId;
    this.eventDomainId = eventDomainId;
    this.eventTitle = eventTitle;
    this.eventAddress = eventAddress;
    this.eventBeginDate = eventBeginDate;
    this.eventBeginDateFormat = eventBeginDateFormat;
    this.eventEndDate = eventEndDate;
    this.eventEndDateFormat = eventEndDateFormat;
    this.eventBeginTime = eventBeginTime;
    this.eventBeginTimeFormat = eventBeginTimeFormat;
    this.eventEndTime = eventEndTime;
    this.eventEndTimeFormat = eventEndTimeFormat;
    this.eventLastTime = eventLastTime;
    this.eventLastTimeformat = eventLastTimeformat;
    this.attendName = attendName;
    this.attendEmp = attendEmp;
    this.attendOrg = attendOrg;
    this.attendGroup = attendGroup;
    this.eventFullDay = eventFullDay;
    this.eventRemind = eventRemind;
    this.eventRemindTime = eventRemindTime;
    this.eventRemindTimeFormat = eventRemindTimeFormat;
    this.eventContent = eventContent;
    this.onTimeMode = onTimeMode;
    this.onTimeContent = onTimeContent;
    this.echoMode = echoMode;
    this.echoBeginTime = echoBeginTime;
    this.echoBeginTimeFormat = echoBeginTimeFormat;
    this.echoEndTime = echoEndTime;
    this.echoEndTimeFormat = echoEndTimeFormat;
    this.echoCounter = echoCounter;
    this.eventDate = eventDate;
    this.eventDateFormat = eventDateFormat;
    this.eventEmpId = eventEmpId;
    this.eventEmpName = eventEmpName;
    this.eventIsEcho = eventIsEcho;
    this.canDelete = canDelete;
    this.canModify = canModify;
    this.isShare = isShare;
    this.isViewed = isViewed;
    this.isAffirmRemind = isAffirmRemind;
  }
  
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
  
  public Boolean getEventIsEcho() {
    return this.eventIsEcho;
  }
  
  public void setEventIsEcho(Boolean eventIsEcho) {
    this.eventIsEcho = eventIsEcho;
  }
  
  public Boolean getCanDelete() {
    return this.canDelete;
  }
  
  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }
  
  public Boolean getCanModify() {
    return this.canModify;
  }
  
  public void setCanModify(Boolean canModify) {
    this.canModify = canModify;
  }
  
  private String dateToString(Date date) {
    if (date != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
      String s = null;
      s = formatter.format(date);
      return s;
    } 
    return null;
  }
  
  public String getEchoBeginTimeFormat() {
    return dateToString(this.echoBeginTime);
  }
  
  public String getEchoEndTimeFormat() {
    return dateToString(this.echoEndTime);
  }
  
  public String getEventBeginDateFormat() {
    String result = "";
    if (this.eventBeginDate != null)
      result = dateToString(new Date(this.eventBeginDate.longValue())); 
    return result;
  }
  
  public String getEventBeginTimeFormat() {
    StringBuffer result = new StringBuffer();
    int time = this.eventBeginTime.intValue();
    int hour = time / 3600;
    result.append(hour);
    result.append(":");
    int minute = (time - hour * 60 * 60) / 60;
    if (minute < 10)
      result.append('0'); 
    result.append(minute);
    return result.toString();
  }
  
  public String getEventDateFormat() {
    return dateToString(this.eventDate);
  }
  
  public String getEventEndDateFormat() {
    String result = "";
    if (this.eventEndDate != null)
      result = dateToString(new Date(this.eventEndDate.longValue())); 
    return result;
  }
  
  public String getEventEndTimeFormat() {
    StringBuffer result = new StringBuffer();
    int time = this.eventEndTime.intValue();
    int hour = time / 3600;
    result.append(hour);
    result.append(":");
    int minute = (time - hour * 60 * 60) / 60;
    if (minute < 10)
      result.append('0'); 
    result.append(minute);
    return result.toString();
  }
  
  public String getEventLastTimeformat() {
    StringBuffer result = new StringBuffer();
    int time = this.eventLastTime.intValue() / 1000;
    int day = time / 86400;
    int modday = time % 86400;
    int hour = time / 3600;
    int minute = (time - hour * 60 * 60) / 60;
    if (hour < 1) {
      result.append(minute);
      result.append("分钟");
    } else if (hour >= 1 && hour < 24) {
      result.append(hour);
      if (minute > 0) {
        result.append(".");
        result.append(minute);
      } 
      result.append("小时");
    } else if (modday == 0 && day > 0) {
      if (day < 7) {
        result.append(day);
        result.append("天");
      } 
      if (day == 7)
        result.append("1周"); 
      if (day == 14)
        result.append("2周"); 
    } 
    return result.toString();
  }
  
  public String getEventRemindTimeFormat() {
    StringBuffer result = new StringBuffer();
    int time = this.eventRemindTime.intValue();
    int hour = time / 3600;
    if (hour < 10)
      result.append('0'); 
    result.append(hour);
    result.append(":");
    int minute = (time - hour * 60 * 60) / 60;
    if (minute < 10)
      result.append('0'); 
    result.append(minute);
    return result.toString();
  }
  
  public Boolean getIsShare() {
    return this.isShare;
  }
  
  public void setIsShare(Boolean isShare) {
    this.isShare = isShare;
  }
  
  public Boolean getIsViewed() {
    return this.isViewed;
  }
  
  public void setIsViewed(Boolean isViewed) {
    this.isViewed = isViewed;
  }
  
  public Boolean getIsAffirmRemind() {
    return this.isAffirmRemind;
  }
  
  public Integer getPopRemindMode() {
    return this.popRemindMode;
  }
  
  public Integer getNoteRemindMode() {
    return this.noteRemindMode;
  }
  
  public void setIsAffirmRemind(Boolean isAffirmRemind) {
    this.isAffirmRemind = isAffirmRemind;
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
