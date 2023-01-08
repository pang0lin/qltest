package rtx.rtxsms.mobset.bean;

import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.tempuri.MobileFileGroup;

public class taskResultBean {
  private StringHolder errMsg = null;
  
  private StringHolder subject = null;
  
  private LongHolder taskFileID = null;
  
  private long autoDelete;
  
  private LongHolder taskSmsID = null;
  
  private MobileFileGroup[] mobileList;
  
  private LongHolder status = null;
  
  private LongHolder mobileCount = null;
  
  private LongHolder YFMobileCount = null;
  
  private StringHolder beginTime = null;
  
  private StringHolder endTime = null;
  
  public LongHolder getStatus() {
    return this.status;
  }
  
  public void setStatus(LongHolder status) {
    this.status = status;
  }
  
  public LongHolder getMobileCount() {
    return this.mobileCount;
  }
  
  public void setMobileCount(LongHolder mobileCount) {
    this.mobileCount = mobileCount;
  }
  
  public LongHolder getYFMobileCount() {
    return this.YFMobileCount;
  }
  
  public void setYFMobileCount(LongHolder yFMobileCount) {
    this.YFMobileCount = yFMobileCount;
  }
  
  public StringHolder getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(StringHolder beginTime) {
    this.beginTime = beginTime;
  }
  
  public StringHolder getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(StringHolder endTime) {
    this.endTime = endTime;
  }
  
  public LongHolder getTaskSmsID() {
    return this.taskSmsID;
  }
  
  public void setTaskSmsID(LongHolder taskSmsID) {
    this.taskSmsID = taskSmsID;
  }
  
  public MobileFileGroup[] getMobileList() {
    return this.mobileList;
  }
  
  public void setMobileList(MobileFileGroup[] mobileList) {
    this.mobileList = mobileList;
  }
  
  public StringHolder getErrMsg() {
    return this.errMsg;
  }
  
  public void setErrMsg(StringHolder errMsg) {
    this.errMsg = errMsg;
  }
  
  public StringHolder getSubject() {
    return this.subject;
  }
  
  public void setSubject(StringHolder subject) {
    this.subject = subject;
  }
  
  public LongHolder getTaskFileID() {
    return this.taskFileID;
  }
  
  public void setTaskFileID(LongHolder taskFileID) {
    this.taskFileID = taskFileID;
  }
  
  public long getAutoDelete() {
    return this.autoDelete;
  }
  
  public void setAutoDelete(long autoDelete) {
    this.autoDelete = autoDelete;
  }
  
  public taskResultBean() {}
  
  public taskResultBean(StringHolder subject, StringHolder errMsg, LongHolder taskFileID) {
    this.subject = subject;
    this.errMsg = errMsg;
    this.taskFileID = taskFileID;
  }
}
