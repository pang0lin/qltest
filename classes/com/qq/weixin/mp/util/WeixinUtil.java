package com.qq.weixin.mp.util;

import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.util.util.IO2File;
import com.qq.weixin.mp.pojo.AccessToken;
import com.qq.weixin.mp.pojo.AccessTokenItem;
import com.qq.weixin.mp.pojo.AccessTokenRoom;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.JsapiTicket;
import com.qq.weixin.mp.pojo.JsapiTicketItem;
import com.qq.weixin.mp.pojo.JsapiTicketRoom;
import com.qq.weixin.mp.pojo.menu.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class WeixinUtil {
  private static Logger log = Logger.getLogger(WeixinUtil.class);
  
  public static final String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
  
  public static final String getcallbackip_url = "https://qyapi.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
  
  public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    HttpsURLConnection httpUrlConn = null;
    try {
      long time1 = (new Date()).getTime();
      IO2File.printFile(String.valueOf(time1) + "发送2:WEiinUtil.java requestUrl:" + requestUrl, "发送微信消息", 3);
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());
      SSLSocketFactory ssf = sslContext.getSocketFactory();
      URL url = new URL(requestUrl);
      httpUrlConn = (HttpsURLConnection)url.openConnection();
      httpUrlConn.setConnectTimeout(60000);
      httpUrlConn.setReadTimeout(90000);
      httpUrlConn.setSSLSocketFactory(ssf);
      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);
      httpUrlConn.setRequestMethod(requestMethod);
      if ("GET".equalsIgnoreCase(requestMethod))
        httpUrlConn.connect(); 
      if (outputStr != null) {
        OutputStream outputStream = httpUrlConn.getOutputStream();
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      } 
      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null)
        buffer.append(str); 
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      httpUrlConn.disconnect();
      jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (ConnectException ex) {
      if (httpUrlConn != null)
        httpUrlConn.disconnect(); 
      System.out.println("buffer:" + buffer.toString());
      log.error("https request error:{}", ex);
    } catch (SocketTimeoutException ex) {
      if (httpUrlConn != null)
        httpUrlConn.disconnect(); 
      System.out.println("buffer:" + buffer.toString());
      log.error("https request error:{}", ex);
    } catch (Exception ex) {
      if (httpUrlConn != null)
        httpUrlConn.disconnect(); 
      System.out.println("buffer:" + buffer.toString());
      log.error("https request error:{}", ex);
    } 
    return jsonObject;
  }
  
  public static JSONObject httpRequestForHttp(String requestUrl, String requestMethod, String outputStr) {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    URLConnection connection = null;
    try {
      connection = (new URL(requestUrl)).openConnection();
      connection.connect();
      InputStream fin = connection.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(fin));
      String temp = null;
      while ((temp = br.readLine()) != null)
        buffer.append(temp); 
      jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return jsonObject;
  }
  
  public static AccessToken getAccessToken() {
    return getAccessToken("jsoa");
  }
  
  public static AccessToken getAccessToken(String appId) {
    AccessToken accessToken = null;
    AccessTokenRoom.getAccessTokenItem(appId);
    long time = (new Date()).getTime() - AccessTokenItem.getBegin();
    AccessTokenItem accessTokenItemTemp = AccessTokenRoom.getAccessTokenItem(appId);
    boolean isFirstGetToken = false;
    if (accessTokenItemTemp == null)
      isFirstGetToken = true; 
    if (accessTokenItemTemp != null && time < (accessTokenItemTemp.getAccessToken().getExpiresIn() * 1000)) {
      accessToken = accessTokenItemTemp.getAccessToken();
    } else {
      String appSecret;
      IO2File.printFile("获取新的token", "token", 3);
      if (appId == null || "qyh".equals(AppRoom.getWeixinType())) {
        appSecret = WeixinManageAction.getPropValue("C_Secret");
      } else {
        appSecret = AppRoom.getAppSecretByAppId(appId);
      } 
      String corpId = WeixinManageAction.getPropValue("sCorpID");
      String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET".replace("CORPID", corpId).replace(
          "CORPSECRET", appSecret);
      JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
      if (jsonObject != null)
        try {
          accessToken = new AccessToken();
          accessToken.setToken(jsonObject.getString("access_token"));
          accessToken.setExpiresIn(7000);
          AccessTokenRoom.setAccessToken(accessToken);
          IO2File.printFile("获取access_token成功，access_token = " + accessToken.getToken(), "token", 3);
          log.info("获取access_token成功，access_token = " + accessToken.getToken());
        } catch (JSONException e) {
          accessToken = null;
          IO2File.printFile("获取token失败 errcode:{" + jsonObject.getInt("errcode") + 
              "} errmsg:{" + jsonObject.getString("errmsg") + "}", "token", 3);
          log.error("获取token失败 errcode:{" + jsonObject.getInt("errcode") + 
              "} errmsg:{" + jsonObject.getString("errmsg") + "}");
        }  
      IO2File.printFile("获取新的token过程结束", "token", 3);
    } 
    return accessToken;
  }
  
  public static String menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";
  
  public static int createMenu(Menu menu, String accessToken, String appId) {
    int result = 0;
    AccessToken at = getAccessToken(appId);
    String url = menu_create_url.replace("ACCESS_TOKEN", at.getToken());
    url = url.replace("AGENTID", appId);
    String jsonMenu = JSONObject.fromObject(menu).toString();
    JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
    log.info("jsonMenu = " + jsonMenu);
    if (jsonObject != null)
      try {
        if (jsonObject.getInt("errcode") != 0) {
          result = jsonObject.getInt("errcode");
          log.error("创建菜单失败 errcode:{" + jsonObject.getInt("errcode") + 
              "} errmsg:{" + jsonObject.getString("errmsg") + "}");
        } 
      } catch (Exception err) {
        err.printStackTrace();
      }  
    return result;
  }
  
  public static String Jsapi_ticket_url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKE";
  
  public static JsapiTicket getJsapi_ticket() {
    JsapiTicket jsapiTicket = null;
    JsapiTicketRoom.getJsapiTicketItem();
    long time = (new Date()).getTime() - JsapiTicketItem.getBegin();
    JsapiTicketItem jsapiTicketItemTemp = JsapiTicketRoom.getJsapiTicketItem();
    if (jsapiTicketItemTemp != null && time < (jsapiTicketItemTemp.getJsapiTicket().getExpiresIn() * 1000)) {
      jsapiTicket = jsapiTicketItemTemp.getJsapiTicket();
    } else {
      String requestUrl = Jsapi_ticket_url.replace("ACCESS_TOKE", getAccessToken().getToken());
      JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
      if (jsonObject != null)
        try {
          jsapiTicket = new JsapiTicket();
          jsapiTicket.setTicket(jsonObject.getString("ticket"));
          jsapiTicket.setExpiresIn(7200);
          JsapiTicketRoom.setJsapiTicket(jsapiTicket);
          log.info("获取jsapi_ticket成功，jsapi_ticket = " + jsapiTicket.getTicket());
        } catch (JSONException e) {
          jsapiTicket = null;
          log.error("获取jsapi_ticket失败 errcode:{" + jsonObject.getInt("errcode") + 
              "} errmsg:{" + jsonObject.getString("errmsg") + "}");
        }  
    } 
    return jsapiTicket;
  }
  
  public static void browserTypeToSession(String browserVersion, HttpSession session) {
    if (browserVersion.indexOf("MSIE") >= 0) {
      session.setAttribute("browserVersion", "MSIEx");
      if (browserVersion.indexOf("MSIE 6.0") >= 0) {
        session.setAttribute("browserVersion", "MSIE6");
      } else if (browserVersion.indexOf("MSIE 10.0") >= 0) {
        session.setAttribute("browserVersion", "IE10");
      } 
    } else if (browserVersion.indexOf("Trident/7.0") >= 0) {
      session.setAttribute("browserVersion", "IE11");
    } else if (browserVersion.indexOf("Firefox") >= 0) {
      session.setAttribute("browserVersion", "Firefox");
    } else if (browserVersion.indexOf("Chrome") >= 0) {
      session.setAttribute("browserVersion", "Chrome");
    } else if (browserVersion.indexOf("Safari") >= 0) {
      session.setAttribute("browserVersion", "Safari");
    } else {
      session.setAttribute("browserVersion", "MSIEx");
    } 
    if (browserVersion.indexOf("iPad") >= 0) {
      session.setAttribute("OSType", "ipad");
    } else if (browserVersion.indexOf("Android") >= 0) {
      session.setAttribute("OSType", "Android");
    } else if (browserVersion.indexOf("iPhone") >= 0) {
      session.setAttribute("OSType", "iPhone");
    } else {
      session.setAttribute("OSType", "pc");
    } 
  }
}
