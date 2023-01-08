package com.js.oa.webservice.appservice;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import java.util.List;

public class JPushUtils {
  public static String sendByAlias(String secret, String key, boolean isRun, boolean isIos, String content, String alert, String data, List<String> alias) {
    String result = "";
    Notification notification = isIos ? createIosNotification(alert, content, data) : createAndroidNotification(alert, content, data);
    PushClient _client = new PushClient(secret, key);
    PushPayload payload = PushPayload.newBuilder().setPlatform(
        isIos ? Platform.ios() : Platform.android()).setAudience(Audience.alias(alias)).setOptions(
        Options.newBuilder().setApnsProduction(isRun).build()).setNotification(notification).build();
    try {
      result = _client.sendPush(payload).getOriginalContent();
    } catch (APIConnectionException e) {
      result = e.getMessage();
    } catch (APIRequestException e) {
      result = e.getMessage();
    } 
    return result;
  }
  
  private static Notification createAndroidNotification(String alert, String content, String data) {
    AndroidNotification android = ((AndroidNotification.Builder)AndroidNotification.newBuilder().setTitle(alert).addExtra("data", data)).setAlert(content).build();
    Notification notification = Notification.newBuilder()
      .addPlatformNotification((PlatformNotification)android).build();
    return notification;
  }
  
  private static Notification createIosNotification(String alert, String content, String data) {
    IosNotification ios = ((IosNotification.Builder)((IosNotification.Builder)IosNotification.newBuilder().setSound("default")
      .addExtra("content", content)).addExtra(
        "data", data)).setAlert(alert).setBadge(1)
      .build();
    Notification notification = Notification.newBuilder()
      .addPlatformNotification((PlatformNotification)ios).build();
    return notification;
  }
}
