package com.qq.weixin.mp.pojo;

import com.js.oa.weixin.manage.WeixinManageAction;
import com.qq.weixin.mp.bean.MenuCreator;
import org.apache.log4j.Logger;

public class EventKey {
  private static Logger log = Logger.getLogger(EventKey.class);
  
  public static String HOME;
  
  private static final String DEAL_WITH_ACTION = "?/WorkDealWithAction.do?action=list&status=0";
  
  private static final String ONLINE_CHECKIN_ACTION = "?/weixinCheckInAction.do?action=checkin";
  
  private static final String COOPERATE_ACTION = "?/weiXinCoopAction.do?action=listseverals";
  
  static {
    renewServerUrl("dbsx");
    renewServerUrl("xtgz");
    log.debug("类加载：HOME = " + HOME);
  }
  
  public static void renewServerUrl(String app) {
    String serverUrl = WeixinManageAction.getPropValue("serverUrl");
    log.debug("serverUrl = " + serverUrl);
    renewHome(serverUrl, app);
  }
  
  public static String DEAL_WITH = String.valueOf(HOME) + "?/WorkDealWithAction.do?action=list&status=0";
  
  public static String ONLINE_CHECKIN = String.valueOf(HOME) + "?/weixinCheckInAction.do?action=checkin";
  
  public static String COOPERATE = String.valueOf(HOME) + "?/weiXinCoopAction.do?action=listseverals";
  
  private static void renewHome(String serverUrl, String app) {
    String WEIXIN_URL_PATTERN = "/weixin";
    HOME = String.valueOf(serverUrl) + "/weixin";
    DEAL_WITH = String.valueOf(HOME) + "?/WorkDealWithAction.do?action=list&status=0";
    ONLINE_CHECKIN = String.valueOf(HOME) + "?/weixinCheckInAction.do?action=checkin";
    COOPERATE = String.valueOf(HOME) + "?/weiXinCoopAction.do?action=listseverals";
    MenuCreator.execute(app);
  }
}
