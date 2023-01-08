package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.MmsIDGroup;

public final class MmsIDGroupHolder implements Holder {
  public MmsIDGroup value = null;
  
  public MmsIDGroupHolder() {}
  
  public MmsIDGroupHolder(MmsIDGroup value) {
    this.value = value;
  }
}
