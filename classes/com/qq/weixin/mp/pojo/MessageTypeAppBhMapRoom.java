package com.qq.weixin.mp.pojo;

import java.util.ArrayList;
import java.util.List;

public class MessageTypeAppBhMapRoom {
  private static List<MessageTypeAppBhMap> list = new ArrayList<MessageTypeAppBhMap>();
  
  public static void addMessageTypeAppBhMap(MessageTypeAppBhMap map) {
    list.add(map);
  }
  
  public static List<MessageTypeAppBhMap> getList() {
    return list;
  }
}
