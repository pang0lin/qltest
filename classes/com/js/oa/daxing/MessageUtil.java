package com.js.oa.daxing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpException;

public class MessageUtil {
  public void testGetUserInfoByUserName(String username, String title, String messageUrl) throws HttpException, IOException {
    DaxingUtil daxingUtil = new DaxingUtil();
    String url = daxingUtil.getConfig("url");
    String appId = daxingUtil.getConfig("appId");
    String apiKey = daxingUtil.getConfig("apiKey");
    String serviceIp = daxingUtil.getConfig("serviceIp");
    String type = "5";
    messageUrl = String.valueOf(serviceIp) + messageUrl;
    if (StringUtils.isNotEmpty(url)) {
      HttpClient httpClient = new HttpClient();
      PostMethod postMethod = new PostMethod(url);
      postMethod.getParams().setParameter(
          "http.protocol.content-charset", "UTF-8");
      String timestamp = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString();
      String nonce = (new StringBuilder(String.valueOf(RandomUtils.nextInt()))).toString();
      String signature = daxingUtil.getMd5Code(String.valueOf(apiKey) + nonce + timestamp);
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      if (!messageUrl.substring(messageUrl.indexOf("status") + 7, messageUrl.indexOf("status") + 10).equals("100")) {
        type = "5";
      } else {
        type = "4";
      } 
      Map<String, Object> message = MessageDecode(new String[] { "touser", 
            username, "type", type, "content", title, "url", 
            messageUrl });
      list.add(message);
      NameValuePair[] data = { new NameValuePair("appId", 
            appId), 
          new NameValuePair("timestamp", timestamp), 
          new NameValuePair("nonce", nonce), 
          new NameValuePair("signature", signature), 
          
          new NameValuePair("message", JSONSerializer.toJSON(list)
            .toString()) };
      postMethod.setRequestBody(data);
      try {
        int statusCode = httpClient.executeMethod((HttpMethod)postMethod);
        if (statusCode != 200) {
          System.out.println("澶辫触");
        } else {
          String res = postMethod.getResponseBodyAsString().trim();
          System.out.println(res);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        postMethod.releaseConnection();
      } 
    } 
  }
  
  public Map<String, Object> MessageDecode(String... msgs) {
    Map<String, Object> message = new HashMap<String, Object>();
    if (msgs.length == 8)
      for (int i = 0; i < 8; i += 2)
        message.put(msgs[i], msgs[i + 1]);  
    return message;
  }
  
  public static void main(String[] args) {
    MessageUtil message = new MessageUtil();
    try {
      message.testGetUserInfoByUserName("wangfeng111", "涔濇�娴嬭瘯", "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&resubmit=1&workId=2995372");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
