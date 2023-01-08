package com.js.system.servlet;

import com.js.thread.DingdingTask;
import com.js.thread.WeixinTask;
import com.js.util.config.SystemCommon;
import com.qq.weixin.mp.bean.URLCodeConverter;
import com.qq.weixin.mp.bean.WeiXinMenuCreator;
import com.qq.weixin.mp.config.pojo.App;
import com.qq.weixin.mp.config.pojo.ComplexMenu;
import com.qq.weixin.mp.config.pojo.Menu;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.menu.Button;
import com.qq.weixin.mp.pojo.menu.ComplexButton;
import com.qq.weixin.mp.pojo.menu.Menu;
import com.qq.weixin.mp.util.WeiXinConfigReader;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MobileInit extends HttpServlet {
  public void init() throws ServletException {
    List<App> apps = null;
    if (AppRoom.getApps().size() == 0)
      apps = WeiXinConfigReader.getApps(); 
    if (!"0".equals(SystemCommon.getMobileLogonNum()) && 
      apps != null && apps.size() > 0)
      for (int k = 0; k < apps.size(); k++) {
        App app = apps.get(k);
        if (!app.getAppbh().startsWith("wap_")) {
          Menu menu = new Menu();
          List<Menu> configMenus = app.getMenu();
          for (int i = 0; i < configMenus.size(); i++) {
            Menu configMenu = configMenus.get(i);
            if (configMenu instanceof ComplexMenu) {
              ComplexButton cb = new ComplexButton();
              cb.setName(configMenu.getName());
              Button[] subBtns = new Button[((ComplexMenu)configMenu).getSubMenu().size()];
              for (int j = 0; j < ((ComplexMenu)configMenu).getSubMenu().size(); j++)
                URLCodeConverter.addMenu(((ComplexMenu)configMenu).getSubMenu().get(j)); 
            } else {
              URLCodeConverter.addMenu(configMenu);
            } 
          } 
        } 
      }  
    if ("1".equals(SystemCommon.getUseWeiXinQYH()))
      if ("1".equals(SystemCommon.getUseWeiXinQYH())) {
        WeiXinConfigReader.readMessageTypeAppBhMap();
        initWeiXinMenu();
        WeixinTask task = new WeixinTask();
        task.start();
      }  
    if ("1".equals(SystemCommon.getUseDingDing())) {
      DingdingTask task = new DingdingTask();
      task.start();
      System.out.println("钉钉用户信息推送开始...");
    } 
  }
  
  private void initWeiXinMenu() {
    System.out.println("总共有" + AppRoom.getApps().size() + "个微信应用");
    WeiXinMenuCreator.createMenu(AppRoom.getApps());
  }
}
