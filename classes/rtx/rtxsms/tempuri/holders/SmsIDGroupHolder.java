package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsIDGroup;

public final class SmsIDGroupHolder implements Holder {
  public SmsIDGroup value = null;
  
  public SmsIDGroupHolder() {}
  
  public SmsIDGroupHolder(SmsIDGroup value) {
    this.value = value;
  }
}
