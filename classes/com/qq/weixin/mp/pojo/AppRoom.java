package com.qq.weixin.mp.pojo;

import com.qq.weixin.mp.config.pojo.App;
import com.qq.weixin.mp.config.pojo.ComplexMenu;
import com.qq.weixin.mp.config.pojo.Menu;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import java.util.ArrayList;
import java.util.List;

public class AppRoom {
  private static List<App> apps = new ArrayList<App>();
  
  private static String weixinType = "qyh";
  
  public static String getWeixinType() {
    return weixinType;
  }
  
  public static void setWeixinType(String weixinType) {
    AppRoom.weixinType = weixinType;
  }
  
  public static void addAPP(App app) {
    apps.add(app);
  }
  
  public static String getAppIdByAppBh(String appbh) {
    String appid = "";
    for (int i = 0; i < apps.size(); i++) {
      if (appbh.equals(((App)apps.get(i)).getAppbh())) {
        appid = ((App)apps.get(i)).getAppid();
        break;
      } 
    } 
    return appid;
  }
  
  public static String getAppSecretByAppBh(String appbh) {
    String secret = "";
    for (int i = 0; i < apps.size(); i++) {
      if (appbh.equals(((App)apps.get(i)).getAppbh())) {
        secret = ((App)apps.get(i)).getAppSecret();
        break;
      } 
    } 
    return secret;
  }
  
  public static String getAppSecretByAppId(String appId) {
    String secret = "";
    for (int i = 0; i < apps.size(); i++) {
      if (appId.equals(((App)apps.get(i)).getAppid())) {
        secret = ((App)apps.get(i)).getAppSecret();
        break;
      } 
    } 
    return secret;
  }
  
  public static String getDingIdByAppBh(String appbh) {
    String dingid = "";
    for (int i = 0; i < apps.size(); i++) {
      if (appbh.equals(((App)apps.get(i)).getAppbh())) {
        dingid = ((App)apps.get(i)).getDingId();
        break;
      } 
    } 
    return dingid;
  }
  
  public static List<App> getApps() {
    return apps;
  }
  
  public static boolean isAppUse(String appbh) {
    boolean result = false;
    for (int i = 0; i < apps.size(); i++) {
      App app = apps.get(i);
      if (appbh.equals(app.getAppbh())) {
        result = true;
        break;
      } 
    } 
    return result;
  }
  
  public static App getAppByAppBh(String appBh) {
    App app = null;
    for (App a : apps) {
      if (appBh.equals(a.getAppbh())) {
        app = a;
        break;
      } 
    } 
    return app;
  }
  
  public static Menu getMenuByAppBhAndMenuId(String appBh, String menuId) {
    Menu menu = null;
    List<Menu> menus = null;
    for (App app : apps) {
      if (appBh.equals(app.getAppbh())) {
        menus = app.getMenu();
        for (Menu m : menus) {
          if (m instanceof ViewMenu) {
            if (menuId.equals(((ViewMenu)m).getMenuId())) {
              menu = m;
              break;
            } 
            continue;
          } 
          if (m instanceof ComplexMenu) {
            List<Menu> ms = ((ComplexMenu)m).getSubMenu();
            for (Menu mm : ms) {
              if (mm instanceof ViewMenu && 
                menuId.equals(((ViewMenu)mm).getMenuId())) {
                menu = mm;
                break;
              } 
            } 
          } 
        } 
        break;
      } 
    } 
    return menu;
  }
}
