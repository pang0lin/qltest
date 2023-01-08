package com.js.oa.relproject.po;

import java.io.Serializable;

public class ProAlarmSet implements Serializable {
  private Long alarmId;
  
  private String alarmName;
  
  private Long alarmDays;
  
  private String alarmColor;
  
  private String alarmEnable;
  
  public ProAlarmSet() {}
  
  public ProAlarmSet(String alarmName, Long alarmDays, String alarmColor, String alarmEnable) {
    this.alarmName = alarmName;
    this.alarmDays = alarmDays;
    this.alarmColor = alarmColor;
    this.alarmEnable = alarmEnable;
  }
  
  public Long getAlarmId() {
    return this.alarmId;
  }
  
  public void setAlarmId(Long alarmId) {
    this.alarmId = alarmId;
  }
  
  public String getAlarmName() {
    return this.alarmName;
  }
  
  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }
  
  public Long getAlarmDays() {
    return this.alarmDays;
  }
  
  public void setAlarmDays(Long alarmDays) {
    this.alarmDays = alarmDays;
  }
  
  public String getAlarmColor() {
    return this.alarmColor;
  }
  
  public void setAlarmColor(String alarmColor) {
    this.alarmColor = alarmColor;
  }
  
  public String getAlarmEnable() {
    return this.alarmEnable;
  }
  
  public void setAlarmEnable(String alarmEnable) {
    this.alarmEnable = alarmEnable;
  }
}
