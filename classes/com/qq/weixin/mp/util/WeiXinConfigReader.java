package com.qq.weixin.mp.util;

import com.qq.weixin.mp.config.pojo.App;
import com.qq.weixin.mp.config.pojo.ClickMenu;
import com.qq.weixin.mp.config.pojo.ComplexMenu;
import com.qq.weixin.mp.config.pojo.Location_SelectMenu;
import com.qq.weixin.mp.config.pojo.Menu;
import com.qq.weixin.mp.config.pojo.Pic_Photo_Or_AlbumMenu;
import com.qq.weixin.mp.config.pojo.Pic_SysphotoMenu;
import com.qq.weixin.mp.config.pojo.Pic_WeixinMenu;
import com.qq.weixin.mp.config.pojo.Scancode_PushMenu;
import com.qq.weixin.mp.config.pojo.Scancode_WaitmsgMenu;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMap;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMapRoom;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class WeiXinConfigReader {
  private static final String weixinConfigFileName = "/jsconfig/weixin.xml";
  
  public static List<App> getApps() {
    List<App> apps = new ArrayList<App>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/weixin.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      if (root.getChild("apptype") != null)
        AppRoom.setWeixinType(root.getChildText("apptype")); 
      List<Element> appNodes = root.getChildren();
      for (int i = 0; i < appNodes.size(); i++) {
        Element appNode = appNodes.get(i);
        if ("app".equalsIgnoreCase(appNode.getName())) {
          App app = new App();
          app.setAppid(appNode.getAttributeValue("appid"));
          app.setAppbh(appNode.getAttributeValue("appbh"));
          app.setAppname(appNode.getAttributeValue("appname"));
          app.setSubscribeTip(appNode.getAttributeValue("subscribetip"));
          app.setImgURL(appNode.getAttributeValue("imgurl"));
          app.setDingId(appNode.getAttributeValue("dingId"));
          app.setDingUrl(appNode.getAttributeValue("dingUrl"));
          if (appNode.getAttributeValue("appSecret") != null) {
            app.setAppSecret(appNode.getAttributeValue("appSecret"));
          } else {
            app.setAppSecret("");
          } 
          List<Menu> menus = new ArrayList<Menu>();
          List<Element> subNodes = appNode.getChildren();
          for (int j = 0; j < subNodes.size(); j++)
            menus.add(readMenu(subNodes.get(j), app.getAppid(), app.getAppbh())); 
          app.setMenu(menus);
          apps.add(app);
          AppRoom.addAPP(app);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return apps;
  }
  
  private static Menu readMenu(Element element, String appid, String appbh) {
    if (WeiXinGlobalNames.MENU_TYPE_VIEW.equals(element.getAttributeValue("type"))) {
      ViewMenu menu = new ViewMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setMenuId(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_CLICK.equals(element.getAttributeValue("type"))) {
      ClickMenu menu = new ClickMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_SCANCODE_PUSH.equals(element.getAttributeValue("type"))) {
      Scancode_PushMenu menu = new Scancode_PushMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_SCANCODE_WAITMSG.equals(element.getAttributeValue("type"))) {
      Scancode_WaitmsgMenu menu = new Scancode_WaitmsgMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_PIC_SYSPHOTO.equals(element.getAttributeValue("type"))) {
      Pic_SysphotoMenu menu = new Pic_SysphotoMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_PIC_PHOTO_OR_ALBUM.equals(element.getAttributeValue("type"))) {
      Pic_Photo_Or_AlbumMenu menu = new Pic_Photo_Or_AlbumMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_PIC_WEIXIN.equals(element.getAttributeValue("type"))) {
      Pic_WeixinMenu menu = new Pic_WeixinMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    if (WeiXinGlobalNames.MENU_TYPE_LOCATION_SELECT.equals(element.getAttributeValue("type"))) {
      Location_SelectMenu menu = new Location_SelectMenu();
      menu.setAppbh(appbh);
      menu.setAppid(appid);
      menu.setName(element.getAttributeValue("name"));
      menu.setKey(element.getAttributeValue("id"));
      menu.setUrl(element.getValue());
      menu.setImgURL(element.getAttributeValue("imgurl"));
      return menu;
    } 
    ComplexMenu cm = new ComplexMenu();
    cm.setAppbh(appbh);
    cm.setAppid(appid);
    cm.setName(element.getAttributeValue("name"));
    List<Menu> subMenus = new ArrayList<Menu>();
    List<Element> subNode = element.getChildren();
    for (int i = 0; i < subNode.size(); i++)
      subMenus.add(readMenu(subNode.get(i), appid, appbh)); 
    cm.setSubMenu(subMenus);
    cm.setImgURL(element.getAttributeValue("imgurl"));
    return cm;
  }
  
  public static void readMessageTypeAppBhMap() {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/weixin.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> appNodes = root.getChildren();
      for (int i = 0; i < appNodes.size(); i++) {
        Element appNode = appNodes.get(i);
        if ("msgappmap".equalsIgnoreCase(appNode.getName())) {
          MessageTypeAppBhMap map = new MessageTypeAppBhMap();
          String msgType = appNode.getAttributeValue("msgtype");
          String appBh = appNode.getAttributeValue("appbh");
          String withLink = appNode.getAttributeValue("withlink");
          map.setMessageType(msgType);
          map.setAppBh(appBh);
          map.setWithLink(withLink);
          MessageTypeAppBhMapRoom.addMessageTypeAppBhMap(map);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
