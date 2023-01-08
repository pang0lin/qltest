package com.qq.weixin.mp.push;

import com.js.oa.weixin.push.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class MessageSorter {
  private static Logger log = Logger.getLogger(MessageSorter.class);
  
  public static Map<Integer, List<Message>> classify(List<Message> msgList) {
    Map<Integer, List<Message>> map = new HashMap<Integer, List<Message>>();
    for (int i = 0; i < msgList.size(); i++) {
      Message msg = msgList.get(i);
      Integer toUserId = msg.getToUserId();
      if (toUserId != null)
        if (map.containsKey(toUserId)) {
          List<Message> list = map.get(toUserId);
          list.add(msg);
          map.put(toUserId, list);
        } else {
          List<Message> list = new ArrayList<Message>();
          list.add(msg);
          map.put(toUserId, list);
        }  
    } 
    return map;
  }
}
