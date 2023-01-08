package com.qq.weixin.mp.bean;

import com.qq.weixin.mp.oauth.CoreOAuth;
import com.qq.weixin.mp.pojo.AccessToken;
import com.qq.weixin.mp.pojo.EventKey;
import com.qq.weixin.mp.pojo.EventKeyConverter;
import com.qq.weixin.mp.pojo.menu.Button;
import com.qq.weixin.mp.pojo.menu.Menu;
import com.qq.weixin.mp.pojo.menu.ViewButton;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import com.qq.weixin.mp.util.WeixinUtil;
import org.apache.log4j.Logger;

public class MenuCreator {
  private static Logger log = Logger.getLogger(MenuCreator.class);
  
  public static boolean execute(String app) {
    boolean res = false;
    AccessToken at = WeixinUtil.getAccessToken();
    if (at != null) {
      int result = WeixinUtil.createMenu(getMenu(app), at.getToken(), app);
      if (result == 0) {
        log.info("菜单创建成功！");
        res = true;
      } else {
        log.info("菜单创建失败，错误码：" + result);
      } 
    } else {
      System.out.println("获取应用【" + app + "】的令牌失败，请检查!");
    } 
    return res;
  }
  
  private static Menu getMenu(String app) {
    Menu menu = new Menu();
    if (WeiXinGlobalNames.APP_NAME_DBSX.equals(app)) {
      ViewButton btn1 = createOauthViewButton("我的待办", EventKey.DEAL_WITH);
      menu.setButton(new Button[] { btn1 });
    } else if (WeiXinGlobalNames.APP_NAME_ZXDK.equals(app)) {
      ViewButton btn1 = createOauthViewButton("我要打卡", EventKey.ONLINE_CHECKIN);
      menu.setButton(new Button[] { btn1 });
    } else if (WeiXinGlobalNames.APP_NAME_XTGZ.equals(app)) {
      ViewButton btn1 = createOauthViewButton("协同工作", EventKey.COOPERATE);
      menu.setButton(new Button[] { btn1 });
    } 
    return menu;
  }
  
  private static ViewButton createOauthViewButton(String name, String linkUrl) {
    ViewButton btn = new ViewButton();
    btn.setName(name);
    String markUrl = EventKeyConverter.link2Mark(linkUrl);
    String url = CoreOAuth.gainGuideUserUri(markUrl);
    btn.setUrl(url);
    return btn;
  }
}
