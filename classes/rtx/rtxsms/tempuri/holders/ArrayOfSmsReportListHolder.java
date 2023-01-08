package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsReportGroup;

public final class ArrayOfSmsReportListHolder implements Holder {
  public SmsReportGroup[] value;
  
  public ArrayOfSmsReportListHolder() {}
  
  public ArrayOfSmsReportListHolder(SmsReportGroup[] value) {
    this.value = value;
  }
}
