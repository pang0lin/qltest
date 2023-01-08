package com.qq.weixin.mp.bean;

import com.qq.weixin.mp.message.resp.Article;
import com.qq.weixin.mp.message.resp.NewsMessage;
import com.qq.weixin.mp.message.resp.TextMessage;
import com.qq.weixin.mp.pojo.EventKey;
import com.qq.weixin.mp.util.MessageUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class MsgXmlPacker {
  private static Logger log = Logger.getLogger(MsgXmlPacker.class);
  
  public static String packText(Map<String, String> requestMap, String respContent) {
    String respMessage = null;
    String fromUserName = requestMap.get("FromUserName");
    String toUserName = requestMap.get("ToUserName");
    TextMessage textMessage = new TextMessage();
    textMessage.setToUserName(fromUserName);
    textMessage.setFromUserName(toUserName);
    textMessage.setCreateTime((new Date()).getTime());
    textMessage.setMsgType("text");
    textMessage.setFuncFlag(0);
    textMessage.setContent(respContent);
    respMessage = MessageUtil.textMessageToXml(textMessage);
    return respMessage;
  }
  
  public static String packLinkText(Map<String, String> requestMap, String respContent) {
    String respMessage = null;
    String HYPER_LINK = "点此访问<a href=\"HREF?/openid=OPENID&END\">TEXT</a>";
    if (respContent.equals(EventKey.DEAL_WITH))
      respContent = "点此访问<a href=\"HREF?/openid=OPENID&END\">TEXT</a>".replace("HREF", respContent).replace("TEXT", "接受待办"); 
    String openid = requestMap.get("FromUserName");
    respContent = respContent.replace("OPENID", openid);
    respContent = TimeCrypt.addEncryptedParam(respContent);
    log.debug("respContent = " + respContent);
    respMessage = packText(requestMap, respContent);
    return respMessage;
  }
  
  public static String packNews(Map<String, String> requestMap, String url) {
    String respMessage = null;
    String fromUserName = requestMap.get("FromUserName");
    String toUserName = requestMap.get("ToUserName");
    NewsMessage newsMessage = new NewsMessage();
    newsMessage.setToUserName(fromUserName);
    newsMessage.setFromUserName(toUserName);
    newsMessage.setCreateTime((new Date()).getTime());
    newsMessage.setMsgType("news");
    newsMessage.setFuncFlag(0);
    List<Article> articleList = new ArrayList<Article>();
    Article article = new Article();
    article.setTitle(requestMap.get("title"));
    article.setDescription(requestMap.get("description"));
    article.setPicUrl(requestMap.get("picUrl"));
    article.setUrl(url);
    articleList.add(article);
    newsMessage.setArticleCount(articleList.size());
    newsMessage.setArticles(articleList);
    respMessage = MessageUtil.newsMessageToXml(newsMessage);
    return respMessage;
  }
  
  public static String packLinkNews(Map<String, String> requestMap, String url) {
    String respMessage = null;
    String URL = "HREF?/openid=OPENID&END";
    if (url.equals(EventKey.DEAL_WITH)) {
      url = "HREF?/openid=OPENID&END".replace("HREF", url);
      processNewsRequestMap(requestMap, "接受待办", "点我访问您的接受待办");
    } 
    String openid = requestMap.get("FromUserName");
    url = url.replace("OPENID", openid);
    url = TimeCrypt.addEncryptedParam(url);
    log.debug("url = " + url);
    respMessage = packNews(requestMap, url);
    return respMessage;
  }
  
  private static Map<String, String> processNewsRequestMap(Map<String, String> requestMap, String title, String description) {
    requestMap.put("title", title);
    requestMap.put("description", description);
    requestMap.put("picUrl", null);
    return requestMap;
  }
}
