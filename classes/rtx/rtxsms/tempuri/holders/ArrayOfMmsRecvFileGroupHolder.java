package rtx.rtxsms.tempuri.holders;

import javax.xml.rpc.holders.Holder;
import rtx.rtxsms.tempuri.MmsRecvFileGroup;

public final class ArrayOfMmsRecvFileGroupHolder implements Holder {
  public MmsRecvFileGroup[] value;
  
  public ArrayOfMmsRecvFileGroupHolder() {}
  
  public ArrayOfMmsRecvFileGroupHolder(MmsRecvFileGroup[] value) {
    this.value = value;
  }
}
