package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsReportGroup;

public final class SmsReportGroupHolder implements Holder {
  public SmsReportGroup value = null;
  
  public SmsReportGroupHolder() {}
  
  public SmsReportGroupHolder(SmsReportGroup value) {
    this.value = value;
  }
}
