package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsRecvGroup;

public final class ArrayOfSmsRecvListHolder implements Holder {
  public SmsRecvGroup[] value;
  
  public ArrayOfSmsRecvListHolder() {}
  
  public ArrayOfSmsRecvListHolder(SmsRecvGroup[] value) {
    this.value = value;
  }
}
