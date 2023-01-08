package com.qq.weixin.mp.util;

import com.js.util.util.IO2File;
import com.qq.weixin.mp.message.resp.Article;
import com.qq.weixin.mp.message.resp.MusicMessage;
import com.qq.weixin.mp.message.resp.NewsMessage;
import com.qq.weixin.mp.message.resp.TextMessage;
import com.qq.weixin.mp.pojo.AccessToken;
import com.qq.weixin.mp.pojo.AppRoom;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtil {
  public static final String RESP_MESSAGE_TYPE_TEXT = "text";
  
  public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
  
  public static final String RESP_MESSAGE_TYPE_NEWS = "news";
  
  public static final String REQ_MESSAGE_TYPE_TEXT = "text";
  
  public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
  
  public static final String REQ_MESSAGE_TYPE_LINK = "link";
  
  public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
  
  public static final Object REQ_MESSAGE_TYPE_VOICE = "voice";
  
  public static final String REQ_MESSAGE_TYPE_EVENT = "event";
  
  public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
  
  public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
  
  public static final String EVENT_TYPE_CLICK = "CLICK";
  
  public static final String EVENT_TYPE_VIEW = "VIEW";
  
  public static Map<String, String> parseXml(HttpServletRequest request) throws IOException, DocumentException {
    Map<String, String> map = new HashMap<String, String>();
    ServletInputStream servletInputStream = request.getInputStream();
    SAXReader reader = new SAXReader();
    Document document = reader.read((InputStream)servletInputStream);
    Element root = document.getRootElement();
    List<Element> elementList = root.elements();
    for (Element e : elementList)
      map.put(e.getName(), e.getText()); 
    servletInputStream.close();
    return map;
  }
  
  public static String textMessageToXml(TextMessage textMessage) {
    xstream.alias("xml", textMessage.getClass());
    return xstream.toXML(textMessage);
  }
  
  public static String musicMessageToXml(MusicMessage musicMessage) {
    xstream.alias("xml", musicMessage.getClass());
    return xstream.toXML(musicMessage);
  }
  
  public static String newsMessageToXml(NewsMessage newsMessage) {
    xstream.alias("xml", newsMessage.getClass());
    xstream.alias("item", (new Article()).getClass());
    return xstream.toXML(newsMessage);
  }
  
  private static XStream xstream = new XStream((HierarchicalStreamDriver)new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
          return (HierarchicalStreamWriter)new PrettyPrintWriter(out) {
              boolean cdata = true;
              
              public void startNode(String name, Class clazz) {
                super.startNode(name, clazz);
              }
              
              protected void writeText(QuickWriter writer, String text) {
                if (this.cdata) {
                  writer.write("<![CDATA[");
                  writer.write(text);
                  writer.write("]]>");
                } else {
                  writer.write(text);
                } 
              }
            };
        }
      });
  
  public static void sendTextMessageToUser(String userId, String appbh, String content) {
    String appId = AppRoom.getAppIdByAppBh(appbh);
    String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
    AccessToken at = WeixinUtil.getAccessToken(appId);
    url = url.replace("ACCESS_TOKEN", at.getToken());
    String para = "{\"touser\":\"" + userId + "\",";
    para = String.valueOf(para) + "\"msgtype\":\"text\",";
    para = String.valueOf(para) + "\"agentid\":\"" + appId + "\",";
    para = String.valueOf(para) + "\"text\":{";
    para = String.valueOf(para) + "\"content\":\"" + content + "\"";
    para = String.valueOf(para) + "},";
    para = String.valueOf(para) + "\"safe\":\"0\"";
    para = String.valueOf(para) + "}";
    JSONObject result = WeixinUtil.httpRequest(url, "POST", para);
    if (!"0".equals(result.getString("errcode")))
      System.out.println("发送消息失败！"); 
  }
  
  public static void sendTextMessageToUser(String userId, int appId, String content) {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
    AccessToken at = WeixinUtil.getAccessToken(String.valueOf(appId));
    url = url.replace("ACCESS_TOKEN", at.getToken());
    String para = "{\"touser\":\"" + userId + "\",";
    para = String.valueOf(para) + "\"msgtype\":\"text\",";
    para = String.valueOf(para) + "\"agentid\":\"" + appId + "\",";
    para = String.valueOf(para) + "\"text\":{";
    para = String.valueOf(para) + "\"content\":\"" + content + "\"";
    para = String.valueOf(para) + "},";
    para = String.valueOf(para) + "\"safe\":\"0\"";
    para = String.valueOf(para) + "}";
    JSONObject result = WeixinUtil.httpRequest(url, "POST", para);
    if (!"0".equals(result.getString("errcode")))
      System.out.println("发送消息失败！"); 
  }
  
  public static void sendNewMessageToUser(String userId, String appbh, String title, String description, String linkUrl, String picUrl) {
    if (linkUrl == null)
      linkUrl = ""; 
    if (picUrl == null)
      picUrl = ""; 
    JSONObject result = null;
    long time1 = (new Date()).getTime();
    try {
      String appId = AppRoom.getAppIdByAppBh(appbh);
      String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
      AccessToken at = WeixinUtil.getAccessToken(appId);
      url = url.replace("ACCESS_TOKEN", at.getToken());
      String para = "{\"touser\":\"" + userId + "\",";
      para = String.valueOf(para) + "\"msgtype\":\"news\",";
      para = String.valueOf(para) + "\"agentid\":\"" + appId + "\",";
      para = String.valueOf(para) + "\"news\":{";
      para = String.valueOf(para) + "     \"articles\":[";
      para = String.valueOf(para) + "        {";
      para = String.valueOf(para) + "        \"title\":\"" + title + "\",";
      para = String.valueOf(para) + "        \"description\":\"" + description + "\",";
      para = String.valueOf(para) + "        \"url\":\"" + linkUrl + "\",";
      para = String.valueOf(para) + "        \"picurl\":\"" + picUrl + "\"";
      para = String.valueOf(para) + "        }";
      para = String.valueOf(para) + "    ]";
      para = String.valueOf(para) + "  }";
      para = String.valueOf(para) + "}";
      result = WeixinUtil.httpRequest(url, "POST", para);
      if (!"0".equals(result.getString("errcode")))
        IO2File.printFile(String.valueOf(time1) + "发送新闻消息失败！错误代码：" + result.getString("errcode") + "   消息参数:" + para, "发送微信消息", 3); 
    } catch (Exception e) {
      IO2File.printFile(String.valueOf(time1) + "发送新闻消息失败，微信返回数据为：" + result, "发送微信消息", 3);
      e.printStackTrace();
    } 
  }
  
  public static void sendNewMessageToUser(String userId, int appId, String title, String description, String linkUrl, String picUrl) {
    if (linkUrl == null)
      linkUrl = ""; 
    if (picUrl == null)
      picUrl = ""; 
    String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
    AccessToken at = WeixinUtil.getAccessToken(String.valueOf(appId));
    url = url.replace("ACCESS_TOKEN", at.getToken());
    String para = "{\"touser\":\"" + userId + "\",";
    para = String.valueOf(para) + "\"msgtype\":\"news\",";
    para = String.valueOf(para) + "\"agentid\":\"" + appId + "\",";
    para = String.valueOf(para) + "\"news\":{";
    para = String.valueOf(para) + "     \"articles\":[";
    para = String.valueOf(para) + "        {";
    para = String.valueOf(para) + "        \"title\":\"" + title + "\",";
    para = String.valueOf(para) + "        \"description\":\"" + description + "\",";
    para = String.valueOf(para) + "        \"url\":\"" + linkUrl + "\",";
    para = String.valueOf(para) + "        \"picurl\":\"" + picUrl + "\"";
    para = String.valueOf(para) + "        }";
    para = String.valueOf(para) + "    ]";
    para = String.valueOf(para) + "  }";
    para = String.valueOf(para) + "}";
    JSONObject result = WeixinUtil.httpRequest(url, "POST", para);
    "0".equals(result.getString("errcode"));
  }
}
