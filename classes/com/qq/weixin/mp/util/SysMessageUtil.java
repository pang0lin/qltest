package com.qq.weixin.mp.util;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMap;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMapRoom;
import java.util.List;

public class SysMessageUtil {
  public static String getAppBhByMessageId(String messageid) {
    String appBh = "";
    DbOpt db = new DbOpt();
    try {
      String messageType = db.executeQueryToStr("select max(message_type) from sys_messages where message_id= " + messageid);
      db.close();
      MessageTypeAppBhMap map = getMessageTypeAppBhMap(messageType);
      if (map != null)
        appBh = map.getAppBh(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return appBh;
  }
  
  public static MessageTypeAppBhMap getMessageTypeAppBhMap(String messageType) {
    MessageTypeAppBhMap result = null;
    List<MessageTypeAppBhMap> list = MessageTypeAppBhMapRoom.getList();
    if (list == null || list.size() == 0)
      WeiXinConfigReader.readMessageTypeAppBhMap(); 
    for (int i = 0; i < list.size(); i++) {
      MessageTypeAppBhMap map = list.get(i);
      if (messageType.equals(map.getMessageType()))
        return map; 
    } 
    return result;
  }
}
