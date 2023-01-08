package com.qq.weixin.mp.bean;

import com.qq.weixin.mp.config.pojo.ClickMenu;
import com.qq.weixin.mp.config.pojo.Location_SelectMenu;
import com.qq.weixin.mp.config.pojo.Menu;
import com.qq.weixin.mp.config.pojo.Pic_Photo_Or_AlbumMenu;
import com.qq.weixin.mp.config.pojo.Pic_SysphotoMenu;
import com.qq.weixin.mp.config.pojo.Pic_WeixinMenu;
import com.qq.weixin.mp.config.pojo.Scancode_PushMenu;
import com.qq.weixin.mp.config.pojo.Scancode_WaitmsgMenu;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import java.util.ArrayList;
import java.util.List;

public class URLCodeConverter {
  private static List<Menu> list = new ArrayList<Menu>();
  
  public static void addMenu(Menu menu) {
    list.add(menu);
  }
  
  public static Menu getMenuByCode(String code) {
    for (int i = 0; i < list.size(); i++) {
      Menu menu = list.get(i);
      if (menu instanceof ViewMenu && code.equals(((ViewMenu)menu).getMenuId()))
        return menu; 
      if (menu instanceof ClickMenu && code.equals(((ClickMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Scancode_PushMenu && code.equals(((Scancode_PushMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Scancode_WaitmsgMenu && code.equals(((Scancode_WaitmsgMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Pic_SysphotoMenu && code.equals(((Pic_SysphotoMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Pic_Photo_Or_AlbumMenu && code.equals(((Pic_Photo_Or_AlbumMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Pic_WeixinMenu && code.equals(((Pic_WeixinMenu)menu).getKey()))
        return menu; 
      if (menu instanceof Location_SelectMenu && code.equals(((Location_SelectMenu)menu).getKey()))
        return menu; 
    } 
    return null;
  }
  
  public static String getURLByCode(String code) {
    for (int i = 0; i < list.size(); i++) {
      Menu menu = list.get(i);
      if (menu instanceof ViewMenu && code.equals(((ViewMenu)menu).getMenuId()))
        return ((ViewMenu)menu).getUrl(); 
      if (menu instanceof ClickMenu && code.equals(((ClickMenu)menu).getKey()))
        return ((ClickMenu)menu).getUrl(); 
      if (menu instanceof Scancode_PushMenu && code.equals(((Scancode_PushMenu)menu).getKey()))
        return ((Scancode_PushMenu)menu).getUrl(); 
      if (menu instanceof Scancode_WaitmsgMenu && code.equals(((Scancode_WaitmsgMenu)menu).getKey()))
        return ((Scancode_WaitmsgMenu)menu).getUrl(); 
      if (menu instanceof Pic_SysphotoMenu && code.equals(((Pic_SysphotoMenu)menu).getKey()))
        return ((Pic_SysphotoMenu)menu).getUrl(); 
      if (menu instanceof Pic_Photo_Or_AlbumMenu && code.equals(((Pic_Photo_Or_AlbumMenu)menu).getKey()))
        return ((Pic_Photo_Or_AlbumMenu)menu).getUrl(); 
      if (menu instanceof Pic_WeixinMenu && code.equals(((Pic_WeixinMenu)menu).getKey()))
        return ((Pic_WeixinMenu)menu).getUrl(); 
      if (menu instanceof Location_SelectMenu && code.equals(((Location_SelectMenu)menu).getKey()))
        return ((Location_SelectMenu)menu).getUrl(); 
    } 
    return "";
  }
  
  public static String getCodeByURL(String url) {
    for (int i = 0; i < list.size(); i++) {
      Menu menu = list.get(i);
      if (menu instanceof ViewMenu && url.equals(((ViewMenu)menu).getUrl()))
        return ((ViewMenu)menu).getMenuId(); 
      if (menu instanceof ClickMenu && url.equals(((ClickMenu)menu).getUrl()))
        return ((ClickMenu)menu).getKey(); 
      if (menu instanceof Scancode_PushMenu && url.equals(((Scancode_PushMenu)menu).getUrl()))
        return ((Scancode_PushMenu)menu).getKey(); 
      if (menu instanceof Scancode_WaitmsgMenu && url.equals(((Scancode_WaitmsgMenu)menu).getUrl()))
        return ((Scancode_WaitmsgMenu)menu).getKey(); 
      if (menu instanceof Pic_SysphotoMenu && url.equals(((Pic_SysphotoMenu)menu).getUrl()))
        return ((Pic_SysphotoMenu)menu).getKey(); 
      if (menu instanceof Pic_Photo_Or_AlbumMenu && url.equals(((Pic_Photo_Or_AlbumMenu)menu).getUrl()))
        return ((Pic_Photo_Or_AlbumMenu)menu).getKey(); 
      if (menu instanceof Pic_WeixinMenu && url.equals(((Pic_WeixinMenu)menu).getUrl()))
        return ((Pic_WeixinMenu)menu).getKey(); 
      if (menu instanceof Location_SelectMenu && url.equals(((Location_SelectMenu)menu).getUrl()))
        return ((Location_SelectMenu)menu).getKey(); 
    } 
    return "";
  }
}
