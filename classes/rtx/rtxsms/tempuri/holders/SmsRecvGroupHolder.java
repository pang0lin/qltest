package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsRecvGroup;

public final class SmsRecvGroupHolder implements Holder {
  public SmsRecvGroup value = null;
  
  public SmsRecvGroupHolder() {}
  
  public SmsRecvGroupHolder(SmsRecvGroup value) {
    this.value = value;
  }
}
