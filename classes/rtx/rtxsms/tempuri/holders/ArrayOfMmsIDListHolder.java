package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.MmsIDGroup;

public final class ArrayOfMmsIDListHolder implements Holder {
  public MmsIDGroup[] value;
  
  public ArrayOfMmsIDListHolder() {}
  
  public ArrayOfMmsIDListHolder(MmsIDGroup[] value) {
    this.value = value;
  }
}
