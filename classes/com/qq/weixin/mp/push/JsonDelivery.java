package com.qq.weixin.mp.push;

import com.qq.weixin.mp.pojo.push.NewsPush;
import com.qq.weixin.mp.util.WeixinUtil;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class JsonDelivery {
  private static Logger log = Logger.getLogger(JsonDelivery.class);
  
  private static final String URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
  
  public static void send(List<NewsPush> pushList, String app) {
    List<String> list = parseJson(pushList);
    for (int i = 0; i < list.size(); i++) {
      String jsonStr = list.get(i);
      String accessToken = WeixinUtil.getAccessToken().getToken();
      String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", accessToken);
      WeixinUtil.httpRequest(url, "POST", jsonStr);
    } 
    log.debug("jsonList发送完毕");
  }
  
  private static List<String> parseJson(List<NewsPush> list) {
    List<String> jsonList = new ArrayList<String>();
    for (int i = 0; i < list.size(); i++) {
      JSONObject json = JSONObject.fromObject(list.get(i));
      String jsonStr = json.toString();
      log.debug("jsonStr = " + jsonStr);
      jsonList.add(jsonStr);
    } 
    return jsonList;
  }
}
