package com.ali.dingding.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccessTokenRoom {
  private static List<AccessTokenItem> accessTokens = new ArrayList<AccessTokenItem>();
  
  private static String appid = "jsoa";
  
  public static AccessTokenItem getAccessTokenItem() {
    for (int i = 0; i < accessTokens.size(); i++) {
      if (appid.equals(((AccessTokenItem)accessTokens.get(i)).getAppid()))
        return accessTokens.get(i); 
    } 
    return null;
  }
  
  public static void setAccessToken(AccessToken accessToken) {
    AccessTokenItem item = null;
    for (int i = 0; i < accessTokens.size(); i++) {
      if (appid.equals(((AccessTokenItem)accessTokens.get(i)).getAppid()))
        item = accessTokens.get(i); 
    } 
    if (item == null) {
      item = new AccessTokenItem();
      item.setAppid(appid);
      accessTokens.add(item);
    } 
    item.setAccessToken(accessToken);
    AccessTokenItem.setBegin((new Date()).getTime());
  }
}
