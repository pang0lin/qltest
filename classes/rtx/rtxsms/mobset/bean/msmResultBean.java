package rtx.rtxsms.mobset.bean;

import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.tempuri.MobileListGroup;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsRecvListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsReportListHolder;

public class msmResultBean {
  private StringHolder errMsg = null;
  
  private ArrayOfSmsIDListHolder smsIDList = null;
  
  private MobileListGroup[] mobileList;
  
  private ArrayOfSmsReportListHolder smsReportList = null;
  
  private ArrayOfSmsRecvListHolder smsRecvList = null;
  
  private LongHolder balance = null;
  
  private StringHolder sign = null;
  
  private LongHolder errCode = null;
  
  public LongHolder getErrCode() {
    return this.errCode;
  }
  
  public void setErrCode(LongHolder errCode) {
    this.errCode = errCode;
  }
  
  public StringHolder getSign() {
    return this.sign;
  }
  
  public void setSign(StringHolder sign) {
    this.sign = sign;
  }
  
  public LongHolder getBalance() {
    return this.balance;
  }
  
  public void setBalance(LongHolder balance) {
    this.balance = balance;
  }
  
  public ArrayOfSmsRecvListHolder getSmsRecvList() {
    return this.smsRecvList;
  }
  
  public void setSmsRecvList(ArrayOfSmsRecvListHolder smsRecvList) {
    this.smsRecvList = smsRecvList;
  }
  
  public ArrayOfSmsReportListHolder getSmsReportList() {
    return this.smsReportList;
  }
  
  public void setSmsReportList(ArrayOfSmsReportListHolder smsReportList) {
    this.smsReportList = smsReportList;
  }
  
  public StringHolder getErrMsg() {
    return this.errMsg;
  }
  
  public void setErrMsg(StringHolder errMsg) {
    this.errMsg = errMsg;
  }
  
  public ArrayOfSmsIDListHolder getSmsIDList() {
    return this.smsIDList;
  }
  
  public void setSmsIDList(ArrayOfSmsIDListHolder smsIDList) {
    this.smsIDList = smsIDList;
  }
  
  public MobileListGroup[] getMobileList() {
    return this.mobileList;
  }
  
  public void setMobileList(MobileListGroup[] mobileList) {
    this.mobileList = mobileList;
  }
  
  public msmResultBean() {}
  
  public msmResultBean(StringHolder errMsg, ArrayOfSmsIDListHolder smsIDList, MobileListGroup[] mobileList) {
    this.errMsg = errMsg;
    this.smsIDList = smsIDList;
    this.mobileList = mobileList;
  }
}
