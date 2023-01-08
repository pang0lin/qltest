package rtx.rtxsms.mobset.bean;

import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsReportListHolder;

public class mmsResultBean {
  private StringHolder errMsg = null;
  
  private LongHolder mmsFileID = null;
  
  private LongHolder status = null;
  
  private StringHolder title = null;
  
  private LongHolder size = null;
  
  private StringHolder createTime = null;
  
  private ArrayOfMmsIDListHolder mmsIDList = null;
  
  private ArrayOfMmsReportListHolder mmsReportList = null;
  
  public ArrayOfMmsReportListHolder getMmsReportList() {
    return this.mmsReportList;
  }
  
  public void setMmsReportList(ArrayOfMmsReportListHolder mmsReportList) {
    this.mmsReportList = mmsReportList;
  }
  
  public ArrayOfMmsIDListHolder getMmsIDList() {
    return this.mmsIDList;
  }
  
  public void setMmsIDList(ArrayOfMmsIDListHolder mmsIDList) {
    this.mmsIDList = mmsIDList;
  }
  
  public StringHolder getTitle() {
    return this.title;
  }
  
  public void setTitle(StringHolder title) {
    this.title = title;
  }
  
  public LongHolder getSize() {
    return this.size;
  }
  
  public void setSize(LongHolder size) {
    this.size = size;
  }
  
  public StringHolder getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(StringHolder createTime) {
    this.createTime = createTime;
  }
  
  public LongHolder getStatus() {
    return this.status;
  }
  
  public void setStatus(LongHolder status) {
    this.status = status;
  }
  
  public StringHolder getErrMsg() {
    return this.errMsg;
  }
  
  public void setErrMsg(StringHolder errMsg) {
    this.errMsg = errMsg;
  }
  
  public LongHolder getMmsFileID() {
    return this.mmsFileID;
  }
  
  public void setMmsFileID(LongHolder mmsFileID) {
    this.mmsFileID = mmsFileID;
  }
  
  public mmsResultBean() {}
  
  public mmsResultBean(StringHolder errMsg, LongHolder mmsFileID) {
    this.errMsg = errMsg;
    this.mmsFileID = mmsFileID;
  }
}
