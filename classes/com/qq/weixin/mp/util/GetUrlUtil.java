package com.qq.weixin.mp.util;

import com.qq.weixin.mp.bean.URLCodeConverter;
import com.qq.weixin.mp.bean.WeiXinMenuCreator;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import com.qq.weixin.mp.oauth.CoreOAuth;
import javax.servlet.http.HttpServletRequest;

public class GetUrlUtil {
  public static final String CUT = "?/";
  
  public static final String OPENID = "openId=";
  
  public static final String CODE = "code=";
  
  public static final String AND = "&";
  
  public static final String WEIXIN = "/weixin";
  
  public static final String MARK = "/mark=";
  
  public static final String MESSAGE = "/message=";
  
  public static final String OUTSIDEID = "/outsideId=";
  
  public static String getCode(HttpServletRequest request) {
    String query = request.getQueryString();
    String code = getMiddle(query, "code=", "&");
    return code;
  }
  
  public static String getMessageId(HttpServletRequest request) {
    String messageId = "";
    String query = request.getQueryString();
    messageId = getMiddle(query, "message=", "&");
    return messageId;
  }
  
  public static String getAppId(HttpServletRequest request) {
    String url = request.getRequestURL().append("?")
      .append(request.getQueryString()).toString();
    String mark = url;
    if (url.indexOf("&") > 0)
      mark = mark.substring(0, mark.indexOf("&")); 
    mark = mark.substring(mark.indexOf("mark=") + "mark=".length());
    ViewMenu menu = (ViewMenu)URLCodeConverter.getMenuByCode(mark);
    return menu.getAppid();
  }
  
  public static String getOpenId(HttpServletRequest request) {
    String query = request.getQueryString();
    String weixin_UserId = getMiddle(query, "weixin_UserId=", "&");
    if (weixin_UserId == null || weixin_UserId.equals(""))
      weixin_UserId = getMiddle(query, "openId=", "&"); 
    return weixin_UserId;
  }
  
  public static String getMiddle(String s1, String s2, String s3) {
    s1 = getBehind(s1, s2);
    s1 = getFront(s1, s3);
    return s1;
  }
  
  public static String getBehind(String s1, String s2) {
    if (s1 == null)
      return ""; 
    if (s2 == null || s1.indexOf(s2) == -1)
      return ""; 
    return s1.substring(s1.indexOf(s2) + s2.length(), s1.length());
  }
  
  public static String getFront(String s1, String s2) {
    if (s1 == null)
      return ""; 
    if (s2 == null || s1.indexOf(s2) == -1)
      return s1; 
    return s1.substring(0, s1.indexOf(s2));
  }
  
  public static String getMessageLinkURL(String messageid) {
    String url = "";
    String messageUrl = String.valueOf(WeiXinMenuCreator.HOME) + "?" + "/message=" + messageid;
    url = CoreOAuth.gainGuideUserUri(messageUrl);
    return url;
  }
  
  public static String getOutsideLinkURL(String outsideId) {
    String url = "";
    String outsideUrl = String.valueOf(WeiXinMenuCreator.HOME) + "?" + "/outsideId=" + outsideId;
    url = CoreOAuth.gainGuideUserUri(outsideUrl);
    return url;
  }
}
