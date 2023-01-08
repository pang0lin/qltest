package com.js.system.vo.messages;

import java.util.Date;

public class MessagesVO {
  private long message_id;
  
  private long message_toUserId;
  
  private long data_id;
  
  private String message_type;
  
  private String message_title;
  
  private String message_url;
  
  private Date message_time = null;
  
  private String message_send_UserName;
  
  private int message_status;
  
  private int message_show;
  
  private long message_send_UserId;
  
  private Date message_date_begin = null;
  
  private Date message_date_end = null;
  
  private int sendSMS = 1;
  
  public long getMessage_send_UserId() {
    return this.message_send_UserId;
  }
  
  public void setMessage_send_UserId(long message_send_UserId) {
    this.message_send_UserId = message_send_UserId;
  }
  
  public long getMessage_id() {
    return this.message_id;
  }
  
  public void setMessage_id(long message_id) {
    this.message_id = message_id;
  }
  
  public long getMessage_toUserId() {
    return this.message_toUserId;
  }
  
  public void setMessage_toUserId(long message_toUserId) {
    this.message_toUserId = message_toUserId;
  }
  
  public String getMessage_type() {
    return this.message_type;
  }
  
  public void setMessage_type(String message_type) {
    this.message_type = message_type;
  }
  
  public String getMessage_title() {
    return this.message_title;
  }
  
  public void setMessage_title(String message_title) {
    this.message_title = message_title;
  }
  
  public String getMessage_url() {
    return this.message_url;
  }
  
  public void setMessage_url(String message_url) {
    this.message_url = message_url;
  }
  
  public Date getMessage_time() {
    return this.message_time;
  }
  
  public void setMessage_time(Date message_time) {
    this.message_time = message_time;
  }
  
  public String getMessage_send_UserName() {
    return this.message_send_UserName;
  }
  
  public void setMessage_send_UserName(String message_send_UserName) {
    this.message_send_UserName = message_send_UserName;
  }
  
  public int getMessage_status() {
    return this.message_status;
  }
  
  public void setMessage_status(int message_status) {
    this.message_status = message_status;
  }
  
  public int getMessage_show() {
    return this.message_show;
  }
  
  public void setMessage_show(int message_show) {
    this.message_show = message_show;
  }
  
  public Date getMessage_date_begin() {
    return this.message_date_begin;
  }
  
  public void setMessage_date_begin(Date message_date_begin) {
    this.message_date_begin = message_date_begin;
  }
  
  public Date getMessage_date_end() {
    return this.message_date_end;
  }
  
  public void setMessage_date_end(Date message_date_end) {
    this.message_date_end = message_date_end;
  }
  
  public long getData_id() {
    return this.data_id;
  }
  
  public void setData_id(long data_id) {
    this.data_id = data_id;
  }
  
  public int getSendSMS() {
    return this.sendSMS;
  }
  
  public void setSendSMS(int sendSMS) {
    this.sendSMS = sendSMS;
  }
}
