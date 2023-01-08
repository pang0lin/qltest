package com.qq.weixin.mp.util;

import com.qq.weixin.mp.util.positionservice.PositionService;
import com.qq.weixin.mp.util.positionservice.impl.PosServiceImpl_baidu;

public class PositionUtil {
  public static void main(String[] args) {
    System.out.println(getPosition("36.664215", "117.055038"));
  }
  
  public static String getPosition(String latitude, String longitude) {
    PositionService service = new PosServiceImpl_baidu();
    String position = service.getPosition(latitude, longitude);
    return position;
  }
}
