package com.ali.dingding.message;

import com.ali.dingding.util.HttpHelper;
import com.ali.dingding.util.OApiException;
import com.ali.dingding.util.OApiResultException;
import com.alibaba.fastjson.JSONObject;

public class MessageHelper {
  public static class Receipt {
    String invaliduser;
    
    String invalidparty;
  }
  
  public static Receipt send(String accessToken, LightAppMessageDelivery delivery) throws OApiException {
    String url = "https://oapi.dingtalk.com/message/send?access_token=" + 
      accessToken;
    JSONObject response = HttpHelper.httpPost(url, delivery.toJsonObject());
    if (response.containsKey("invaliduser") || response.containsKey("invalidparty")) {
      Receipt receipt = new Receipt();
      receipt.invaliduser = response.getString("invaliduser");
      receipt.invalidparty = response.getString("invalidparty");
      return receipt;
    } 
    throw new OApiResultException("invaliduser or invalidparty");
  }
  
  public static String send(String accessToken, ConversationMessageDelivery delivery) throws OApiException {
    String url = "https://oapi.dingtalk.com/message/send_to_conversation?access_token=" + 
      accessToken;
    JSONObject response = HttpHelper.httpPost(url, delivery.toJsonObject());
    if (response.containsKey("receiver"))
      return response.getString("receiver"); 
    throw new OApiResultException("receiver");
  }
}
