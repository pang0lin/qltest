package rtx;

import java.util.Calendar;

public class RTXSync {
  public boolean Sync() {
    Long beginTime = Long.valueOf(Calendar.getInstance().getTimeInMillis());
    boolean turn = true;
    TransRTX rtx = new TransRTX();
    rtx.Sync();
    rtx.transUsers();
    Long endTime = Long.valueOf(Calendar.getInstance().getTimeInMillis());
    System.out.println((endTime.longValue() - beginTime.longValue()) / 1000L);
    return turn;
  }
}
