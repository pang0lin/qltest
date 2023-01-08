package com.ali.dingding.util;

import com.ali.dingding.message.LightAppMessageDelivery;
import com.ali.dingding.message.LinkMessage;
import com.ali.dingding.message.MessageHelper;
import com.ali.dingding.pojo.AccessToken;
import com.qq.weixin.mp.pojo.AppRoom;

public class DingMessageUtil {
  public static void sendDingMessageToUser(String userId, String appbh, String title, String description, String linkUrl, String picUrl) {
    String appId = AppRoom.getDingIdByAppBh(appbh);
    if (linkUrl == null)
      linkUrl = ""; 
    if (picUrl == null)
      picUrl = ""; 
    AccessToken at = null;
    try {
      at = DingdingUtil.getAccessToken();
    } catch (OApiException e1) {
      e1.printStackTrace();
    } 
    LightAppMessageDelivery lightAppMessageDelivery = 
      new LightAppMessageDelivery(userId, "", appId);
    LinkMessage linkMessage = new LinkMessage(linkUrl, picUrl, 
        title, description);
    lightAppMessageDelivery.withMessage(linkMessage);
    try {
      MessageHelper.send(at.getToken(), lightAppMessageDelivery);
      System.out.println("成功向用户【" + userId + "】的应用【" + appId + "】发送一条钉钉消息");
    } catch (OApiException e1) {
      System.out.println("发送钉钉消息失败！");
      e1.printStackTrace();
    } 
  }
  
  public static void main(String[] args) {
    DingMessageUtil test = new DingMessageUtil();
    sendDingMessageToUser("yanwenbin", "3872906", "test11", "test11", "http://39.64.43.198:85/jsoa/dingding?id=530298&dd_share=false&bh=xtgz", "@lALOACZwe2Rk");
  }
}
