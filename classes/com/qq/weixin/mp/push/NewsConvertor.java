package com.qq.weixin.mp.push;

import com.js.oa.weixin.push.Message;
import com.qq.weixin.mp.oauth.CoreOAuth;
import com.qq.weixin.mp.pojo.EventKey;
import com.qq.weixin.mp.pojo.push.NewsPush;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class NewsConvertor {
  private static Logger log = Logger.getLogger(NewsConvertor.class);
  
  public static List<NewsPush> convert(Map<Integer, List<Message>> map) {
    List<NewsPush> list = new ArrayList<NewsPush>();
    Set<Map.Entry<Integer, List<Message>>> set = map.entrySet();
    for (Map.Entry<Integer, List<Message>> entry : set) {
      NewsPush push = parseNewsPush(entry);
      list.add(push);
    } 
    return list;
  }
  
  private static NewsPush parseNewsPush(Map.Entry<Integer, List<Message>> entry) {
    NewsPush push = new NewsPush();
    Integer toUserId = entry.getKey();
    List<Message> msgList = entry.getValue();
    log.debug("toUserId = " + toUserId);
    String touser = OpenIdDict.lookup(toUserId);
    push.setTouser(touser);
    NewsPush.News news = parseNews(push, msgList);
    push.setNews(news);
    return push;
  }
  
  private static NewsPush.News parseNews(NewsPush push, List<Message> msgList) {
    push.getClass();
    NewsPush.News news = new NewsPush.News(push);
    int SIZE = msgList.size();
    NewsPush.News.Article[] articles = new NewsPush.News.Article[SIZE];
    for (int i = 0; i < SIZE; i++) {
      Message msg = msgList.get(i);
      NewsPush.News.Article article = parseArticle(news, msg);
      articles[i] = article;
    } 
    news.setArticles(articles);
    return news;
  }
  
  private static NewsPush.News.Article parseArticle(NewsPush.News news, Message msg) {
    news.getClass();
    NewsPush.News.Article article = new NewsPush.News.Article(news);
    String title = msg.getTitle();
    String sendUserName = msg.getSendUserName();
    Date time = msg.getTime();
    String url = msg.getUrl();
    String type = msg.getType();
    String typeStr = MsgTypeDict.lookUp(type);
    String timeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E")).format(time);
    String description = 
      "消息类型:" + typeStr + "; " + 
      "发送者:" + sendUserName + "; " + 
      "发送时间:" + timeStr + "; ";
    String urlStr = CoreOAuth.gainGuideUserUri(String.valueOf(EventKey.HOME) + url.replace("/jsoa", "?"));
    article.setTitle(title);
    article.setDescription(description);
    article.setPicurl("");
    article.setUrl(urlStr);
    return article;
  }
}
