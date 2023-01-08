package com.qq.weixin.mp.pojo;

import java.util.HashMap;
import java.util.Map;

public class PositionInfoRoom {
  private static Map<String, PositionInfo> positions = new HashMap<String, PositionInfo>();
  
  public static void setUserPosition(String userid, PositionInfo pos) {
    if (positions.containsKey(userid))
      positions.remove(userid); 
    positions.put(userid, pos);
  }
  
  public static PositionInfo getUserPosition(String userid) {
    if (!positions.containsKey(userid))
      return null; 
    return positions.get(userid);
  }
}
