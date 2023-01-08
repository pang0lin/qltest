package com.js.sms.bbnyxt;

import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class BbnYxtSMS {
  private static String serviceUrl = "";
  
  private static String account = "";
  
  private static String passwd = "";
  
  public static void main(String[] args) {}
  
  private static void init() {
    ResourceBundle rb = ResourceBundle.getBundle("bbnyxt");
    serviceUrl = rb.getString("url");
    account = rb.getString("account");
    passwd = rb.getString("password");
  }
  
  public static boolean sendSMS(String phoneCode, String content) {
    boolean result = false;
    if ("".equals(serviceUrl))
      init(); 
    try {
      int reqId = 1;
      JSONObject reqJson = new JSONObject();
      reqJson.put("id", Integer.valueOf(reqId++));
      reqJson.put("jsonrpc", "2.0");
      reqJson.put("method", "genLoginToken");
      JSONArray params = new JSONArray();
      params.add(account);
      params.add(passwd);
      reqJson.put("params", params);
      String resStr = postJson(String.valueOf(serviceUrl) + "power/authService", reqJson.toString());
      JSONObject resJson = JSONObject.fromObject(resStr);
      if (resJson.has("result")) {
        JSONObject resultJson = resJson.getJSONObject("result");
        if (resultJson.getBoolean("result")) {
          String loginToken = resultJson.getString("others");
          reqJson = new JSONObject();
          reqJson.put("id", Integer.valueOf(reqId++));
          reqJson.put("jsonrpc", "2.0");
          reqJson.put("method", "save");
          JSONObject smsJson = new JSONObject();
          smsJson.put("businessType", "短信发送");
          smsJson.put("sendText", content);
          smsJson.put("sendTo", "api测试");
          smsJson.put("toDetail", phoneCode);
          smsJson.put("sendFrom", "api");
          params = new JSONArray();
          params.add(smsJson);
          reqJson.put("params", params);
          JSONObject authJson = new JSONObject();
          authJson.put("loginToken", loginToken);
          reqJson.put("auth", authJson);
          resStr = postJson(String.valueOf(serviceUrl) + "smsSave", reqJson.toString());
          resJson = JSONObject.fromObject(resStr);
          resultJson = resJson.getJSONObject("result");
          if (resultJson.getBoolean("result")) {
            System.out.println("send success");
          } else {
            System.out.println("send fail: " + resultJson.getString("msg"));
          } 
        } else {
          System.out.println("get loginToken fail:" + resultJson.getString("msg"));
        } 
      } else {
        System.out.println("get loginToken fail");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("send fail");
    } 
    return result;
  }
  
  public static String postJson(String url, String jsonStr) throws Exception {
    DefaultHttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonStr, "utf-8");
    post.setEntity((HttpEntity)entity);
    CloseableHttpResponse closeableHttpResponse = client.execute((HttpUriRequest)post);
    int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
    HttpEntity httpEntity = closeableHttpResponse.getEntity();
    if (httpEntity != null)
      post.abort(); 
    if (statusCode == 200) {
      ByteArrayOutputStream resHolder = new ByteArrayOutputStream();
      httpEntity.writeTo(resHolder);
      resHolder.flush();
      return resHolder.toString("utf-8");
    } 
    throw new Exception("服务异常");
  }
}
