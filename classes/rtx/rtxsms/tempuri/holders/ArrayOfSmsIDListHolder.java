package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.SmsIDGroup;

public final class ArrayOfSmsIDListHolder implements Holder {
  public SmsIDGroup[] value;
  
  public ArrayOfSmsIDListHolder() {}
  
  public ArrayOfSmsIDListHolder(SmsIDGroup[] value) {
    this.value = value;
  }
}
