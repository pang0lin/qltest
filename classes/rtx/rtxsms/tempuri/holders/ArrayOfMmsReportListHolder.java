package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.MmsReportGroup;

public final class ArrayOfMmsReportListHolder implements Holder {
  public MmsReportGroup[] value;
  
  public ArrayOfMmsReportListHolder() {}
  
  public ArrayOfMmsReportListHolder(MmsReportGroup[] value) {
    this.value = value;
  }
}
