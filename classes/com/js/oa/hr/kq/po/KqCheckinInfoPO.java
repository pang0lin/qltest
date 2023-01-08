package com.js.oa.hr.kq.po;

import java.io.Serializable;
import java.util.Date;

public class KqCheckinInfoPO implements Serializable {
  private long id;
  
  private String userId;
  
  private Date checkinTime = null;
  
  private String weidu;
  
  private String jingdu;
  
  private String position;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public Date getCheckinTime() {
    return this.checkinTime;
  }
  
  public void setCheckinTime(Date checkinTime) {
    this.checkinTime = checkinTime;
  }
  
  public String getWeidu() {
    return this.weidu;
  }
  
  public void setWeidu(String weidu) {
    this.weidu = weidu;
  }
  
  public String getJingdu() {
    return this.jingdu;
  }
  
  public void setJingdu(String jingdu) {
    this.jingdu = jingdu;
  }
  
  public String getPosition() {
    return this.position;
  }
  
  public void setPosition(String position) {
    this.position = position;
  }
  
  public KqCheckinInfoPO() {}
  
  public KqCheckinInfoPO(long id, String userId, Date checkinTime, String weidu, String jingdu, String position) {
    this.id = id;
    this.userId = userId;
    this.checkinTime = checkinTime;
    this.weidu = weidu;
    this.jingdu = jingdu;
    this.position = position;
  }
}
