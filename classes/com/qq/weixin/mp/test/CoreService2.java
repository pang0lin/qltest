package com.qq.weixin.mp.test;

import com.qq.weixin.mp.message.resp.Article;
import com.qq.weixin.mp.message.resp.NewsMessage;
import com.qq.weixin.mp.message.resp.TextMessage;
import com.qq.weixin.mp.util.MessageUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class CoreService2 {
  public static String processRequest(HttpServletRequest request) {
    String respMessage = null;
    try {
      Map<String, String> requestMap = MessageUtil.parseXml(request);
      String fromUserName = requestMap.get("FromUserName");
      String toUserName = requestMap.get("ToUserName");
      String msgType = requestMap.get("MsgType");
      TextMessage textMessage = new TextMessage();
      textMessage.setToUserName(fromUserName);
      textMessage.setFromUserName(toUserName);
      textMessage.setCreateTime((new Date()).getTime());
      textMessage.setMsgType("text");
      textMessage.setFuncFlag(0);
      textMessage
        .setContent("欢迎访问<a href=\"http://blog.csdn.net/lyq8479\">柳峰的博客</a>!");
      respMessage = MessageUtil.textMessageToXml(textMessage);
      if (msgType.equals("text")) {
        String content = requestMap.get("Content");
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime((new Date()).getTime());
        newsMessage.setMsgType("news");
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        if ("1".equals(content)) {
          Article article = new Article();
          article.setTitle("微信公众帐号开发教程Java版");
          article.setDescription("柳峰，80后，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
          article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
          article.setUrl("http://blog.csdn.net/lyq8479");
          articleList.add(article);
          newsMessage.setArticleCount(articleList.size());
          newsMessage.setArticles(articleList);
          respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } else if ("2".equals(content)) {
          Article article = new Article();
          article.setTitle("微信公众帐号开发教程Java版");
          article.setDescription("柳峰，80后，" + 
              emoji(128697) + 
              "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
          article.setPicUrl("");
          article.setUrl("http://blog.csdn.net/lyq8479");
          articleList.add(article);
          newsMessage.setArticleCount(articleList.size());
          newsMessage.setArticles(articleList);
          respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } else if ("3".equals(content)) {
          Article article1 = new Article();
          article1.setTitle("微信公众帐号开发教程\n引言");
          article1.setDescription("");
          article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
          article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");
          Article article2 = new Article();
          article2.setTitle("第2篇\n微信公众帐号的类型");
          article2.setDescription("");
          article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");
          Article article3 = new Article();
          article3.setTitle("第3篇\n开发模式启用及接口配置");
          article3.setDescription("");
          article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");
          articleList.add(article1);
          articleList.add(article2);
          articleList.add(article3);
          newsMessage.setArticleCount(articleList.size());
          newsMessage.setArticles(articleList);
          respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } else if ("4".equals(content)) {
          Article article1 = new Article();
          article1.setTitle("微信公众帐号开发教程Java版");
          article1.setDescription("");
          article1.setPicUrl("");
          article1.setUrl("http://blog.csdn.net/lyq8479");
          Article article2 = new Article();
          article2.setTitle("第4篇\n消息及消息处理工具的封装");
          article2.setDescription("");
          article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");
          Article article3 = new Article();
          article3.setTitle("第5篇\n各种消息的接收与响应");
          article3.setDescription("");
          article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");
          Article article4 = new Article();
          article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
          article4.setDescription("");
          article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");
          articleList.add(article1);
          articleList.add(article2);
          articleList.add(article3);
          articleList.add(article4);
          newsMessage.setArticleCount(articleList.size());
          newsMessage.setArticles(articleList);
          respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } else if ("5".equals(content)) {
          Article article1 = new Article();
          article1.setTitle("第7篇\n文本消息中换行符的使用");
          article1.setDescription("");
          article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
          article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");
          Article article2 = new Article();
          article2.setTitle("第8篇\n文本消息中使用网页超链接");
          article2.setDescription("");
          article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
          article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");
          Article article3 = new Article();
          article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
          article3.setDescription("");
          article3.setPicUrl("");
          article3.setUrl("http://blog.csdn.net/lyq8479");
          articleList.add(article1);
          articleList.add(article2);
          articleList.add(article3);
          newsMessage.setArticleCount(articleList.size());
          newsMessage.setArticles(articleList);
          respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return respMessage;
  }
  
  public static String emoji(int hexEmoji) {
    return String.valueOf(Character.toChars(hexEmoji));
  }
}
