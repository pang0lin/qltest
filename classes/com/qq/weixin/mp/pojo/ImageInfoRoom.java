package com.qq.weixin.mp.pojo;

import java.util.HashMap;
import java.util.Map;

public class ImageInfoRoom {
  private static Map<String, ImageInfo> images = new HashMap<String, ImageInfo>();
  
  public static void setUserPosition(String userid, ImageInfo pos) {
    if (images.containsKey(userid))
      images.remove(userid); 
    images.put(userid, pos);
  }
  
  public static ImageInfo getUserImage(String userid) {
    if (!images.containsKey(userid))
      return null; 
    return images.get(userid);
  }
}
