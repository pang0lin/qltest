package com.js.sms.corp;

import com.js.oa.message.service.MsHistoryBD;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageService {
  public void addMsHistory(Long fromUserId, String phoneList, String context, String result) {
    MsHistoryBD msHistoryBD = new MsHistoryBD();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String sendDate = format.format(new Date());
    String[] phoneLists = phoneList.split(",");
    try {
      for (int i = 0; i < phoneLists.length; i++)
        boolean bool = msHistoryBD.saveMsHistory(fromUserId.toString(), "", phoneLists[i], sendDate, context, result).booleanValue(); 
    } catch (Exception e) {
      System.out.println(e);
    } 
  }
}
