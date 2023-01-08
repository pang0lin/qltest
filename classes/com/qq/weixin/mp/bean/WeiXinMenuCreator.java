package com.qq.weixin.mp.bean;

import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.util.config.SystemCommon;
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
import com.qq.weixin.mp.oauth.CoreOAuth;
import com.qq.weixin.mp.pojo.AccessToken;
import com.qq.weixin.mp.pojo.menu.Button;
import com.qq.weixin.mp.pojo.menu.ClickButton;
import com.qq.weixin.mp.pojo.menu.ComplexButton;
import com.qq.weixin.mp.pojo.menu.Location_SelectButton;
import com.qq.weixin.mp.pojo.menu.Menu;
import com.qq.weixin.mp.pojo.menu.Pic_Photo_Or_AlbumButton;
import com.qq.weixin.mp.pojo.menu.Pic_SysphotoButton;
import com.qq.weixin.mp.pojo.menu.Pic_WeiXinButton;
import com.qq.weixin.mp.pojo.menu.Scancode_PushButton;
import com.qq.weixin.mp.pojo.menu.Scancode_WaitmsgButton;
import com.qq.weixin.mp.pojo.menu.ViewButton;
import com.qq.weixin.mp.util.WeixinUtil;
import java.util.List;

public class WeiXinMenuCreator {
  static {
    String WEIXIN_URL_PATTERN = "/weixin";
  }
  
  public static String HOME = String.valueOf(WeixinManageAction.getPropValue("serverUrl")) + 
    "/weixin";
  
  public static void createMenu(List<App> apps) {
    for (int i = 0; i < apps.size(); i++) {
      App app = apps.get(i);
      if (SystemCommon.getModules().indexOf("," + app.getAppbh() + ",") >= 0) {
        AccessToken at = WeixinUtil.getAccessToken(app.getAppid());
        if (at != null) {
          int result = 1;
          int exeCount = 0;
          result = WeixinUtil.createMenu(getMenu(app), at.getToken(), 
              app.getAppid());
          while (result != 0 && exeCount < 3) {
            try {
              Thread.sleep(5000L);
            } catch (Exception exception) {}
            result = WeixinUtil.createMenu(getMenu(app), at.getToken(), 
                app.getAppid());
            exeCount++;
          } 
          if (result == 0) {
            System.out.println("应用【" + app.getAppname() + "】菜单创建成功！");
          } else {
            System.out.println("菜单创建失败，错误码：" + result);
          } 
        } else {
          System.out.println("获取应用【" + app.getAppname() + "】的令牌失败，请检查!");
        } 
      } 
    } 
  }
  
  private static Menu getMenu(App app) {
    Menu menu = new Menu();
    List<Menu> configMenus = app.getMenu();
    Button[] btns = new Button[configMenus.size()];
    for (int i = 0; i < configMenus.size(); i++) {
      Menu configMenu = configMenus.get(i);
      if (configMenu instanceof ComplexMenu) {
        ComplexButton cb = new ComplexButton();
        cb.setName(configMenu.getName());
        Button[] subBtns = new Button[((ComplexMenu)configMenu)
            .getSubMenu().size()];
        int j = 0;
        for (; j < ((ComplexMenu)configMenu).getSubMenu()
          .size(); j++) {
          URLCodeConverter.addMenu(((ComplexMenu)configMenu)
              .getSubMenu().get(j));
          subBtns[j] = createButton(((ComplexMenu)configMenu)
              .getSubMenu().get(j));
        } 
        cb.setSub_button(subBtns);
        btns[i] = cb;
      } else {
        btns[i] = createButton(configMenu);
      } 
    } 
    menu.setButton(btns);
    return menu;
  }
  
  private static ViewButton createOauthViewButton(String name, String linkUrl) {
    ViewButton btn = new ViewButton();
    btn.setName(name);
    String code = URLCodeConverter.getCodeByURL(linkUrl);
    String markUrl = String.valueOf(HOME) + "?/mark=" + code;
    String url = CoreOAuth.gainGuideUserUri(markUrl);
    btn.setUrl(url);
    return btn;
  }
  
  private static Button createButton(Menu menu) {
    URLCodeConverter.addMenu(menu);
    if (menu instanceof ViewMenu)
      return createOauthViewButton(menu.getName(), ((ViewMenu)menu).getUrl()); 
    if (menu instanceof ClickMenu) {
      ClickButton btn = new ClickButton();
      btn.setName(menu.getName());
      btn.setKey(((ClickMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Scancode_PushMenu) {
      Scancode_PushButton btn = new Scancode_PushButton();
      btn.setName(menu.getName());
      btn.setKey(((Scancode_PushMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Scancode_WaitmsgMenu) {
      Scancode_WaitmsgButton btn = new Scancode_WaitmsgButton();
      btn.setName(menu.getName());
      btn.setKey(((Scancode_WaitmsgMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Pic_SysphotoMenu) {
      Pic_SysphotoButton btn = new Pic_SysphotoButton();
      btn.setName(menu.getName());
      btn.setKey(((Pic_SysphotoMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Pic_Photo_Or_AlbumMenu) {
      Pic_Photo_Or_AlbumButton btn = new Pic_Photo_Or_AlbumButton();
      btn.setName(menu.getName());
      btn.setKey(((Pic_Photo_Or_AlbumMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Pic_WeixinMenu) {
      Pic_WeiXinButton btn = new Pic_WeiXinButton();
      btn.setName(menu.getName());
      btn.setKey(((Pic_WeixinMenu)menu).getKey());
      return btn;
    } 
    if (menu instanceof Location_SelectMenu) {
      Location_SelectButton btn = new Location_SelectButton();
      btn.setName(menu.getName());
      btn.setKey(((Location_SelectMenu)menu).getKey());
      return btn;
    } 
    return null;
  }
}
