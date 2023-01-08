package com.qq.weixin.mp.service;

import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.logon.WeixinLogonAction;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.util.StaticParam;
import com.qq.weixin.mp.bean.TimeCrypt;
import com.qq.weixin.mp.bean.URLCodeConverter;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import com.qq.weixin.mp.oauth.CoreOAuth;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.util.GetUrlUtil;
import com.qq.weixin.mp.util.SysMessageUtil;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import com.qq.weixin.mp.util.WeixinUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class ViewService {
  private static Logger log = Logger.getLogger(ViewService.class);
  
  private static final String LOGON_FAILURE_PAGE = "/weixinError.jsp";
  
  private static final String AND_CODE = "&code=";
  
  private static final String MARK = "mark=";
  
  public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getQueryString();
    log.debug("query = " + request.getQueryString());
    if (query != null && query.indexOf("mark=") != -1) {
      log.debug("MARK");
      processViewButtonOAuth(request, response);
    } else if (query != null && query.indexOf("/message=") != -1) {
      log.debug("MESSAGE");
      processMessageLink(request, response);
    } else if (query != null && query.indexOf("/outsideId=") != -1) {
      log.debug("OUTSIDEID");
      processViewByoutsideId(request, response);
    } else if (query != null && query.indexOf("code=") != -1) {
      log.debug("CODE");
      processOAuthChat(request, response, "dbsx");
    } else if (query != null && query.indexOf("openId=") != -1) {
      log.debug("CLICK");
      processClickButtonChat(request, response);
    } 
  }
  
  private static void processOAuthChat(HttpServletRequest request, HttpServletResponse response, String app) throws ServletException, IOException {
    String userid = accessUserId(request);
    request.setAttribute("weixin_UserId", userid);
    String action = GetUrlUtil.getFront(request.getQueryString(), "&code=");
    log.debug("--- --- action = " + action);
    request.setAttribute("action", action);
    forwardRequestDispather(request, response);
  }
  
  private static void processMessageLink(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = accessUserIdForMessage(request);
    request.setAttribute("weixin_UserId", userId);
    String messageId = pickMessageIdFromRequest(request);
    String emp_id = StaticParam.getEmpIdByAccount(userId);
    String action = getWeixinMessageUrl(messageId, emp_id);
    request.setAttribute("action", action);
    DbOpt db = new DbOpt();
    try {
      db.executeUpdate("update sys_messages set message_status=0 where message_id=" + messageId);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    forwardRequestDispather(request, response);
  }
  
  private static String getWeixinMessageUrl(String messageId, String emp_id) {
    String action = "";
    DbOpt db = new DbOpt();
    String[][] result = (String[][])null;
    try {
      result = db.executeQueryToStrArr2("select message_type,message_url,data_id from sys_messages where message_id=" + messageId, 3);
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    if (result != null && result.length > 0) {
      String messageType = result[0][0];
      String messageUrl = result[0][1];
      String dataId = result[0][2];
      WeixinMessageUrlGetter getter = WeixinMessageUrlGetterFactory.getWeixinMessageUrlGetter(messageType);
      if (getter == null) {
        System.out.println("系统不支持的消息类型：" + messageType);
      } else {
        action = getter.getWeiXinUrl(messageUrl, dataId, emp_id);
      } 
    } else {
      action = "/weixin/workflow/failure.jsp";
    } 
    return action;
  }
  
  private static String getWeixinOutsideUrl(String outsideId, String emp_id) {
    String action = "";
    String outsideType = "outside";
    WeixinOutsideUrlGetter getter = WeixinOutsideUrlGetterFactory.getWeixinOutsideUrlGetter(outsideType);
    if (getter == null) {
      System.out.println("系统不支持的消息类型：" + outsideType);
    } else {
      action = getter.getWeiXinUrl(outsideId, emp_id);
    } 
    return action;
  }
  
  private static void processViewButtonOAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = accessUserId(request);
    request.setAttribute("weixin_UserId", userId);
    String action = pickActionFromRequest(request);
    request.setAttribute("action", action);
    forwardRequestDispather(request, response);
  }
  
  private static void processViewByoutsideId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = accessUserIdForOutside(request);
    request.setAttribute("weixin_UserId", userId);
    String outsideId = pickOutsideIdFromRequest(request);
    String emp_id = StaticParam.getEmpIdByAccount(userId);
    String action = getWeixinOutsideUrl(outsideId, emp_id);
    request.setAttribute("action", action);
    forwardRequestDispather(request, response);
  }
  
  private static String accessUserId(HttpServletRequest request) {
    log.debug("query = " + request.getQueryString());
    String code = GetUrlUtil.getCode(request);
    log.debug("code = " + code);
    String appId = GetUrlUtil.getAppId(request);
    String exchangeUri = CoreOAuth.exchangeAccessUri(code, appId);
    log.debug("exchangeUri = " + exchangeUri);
    JSONObject jsonObj = WeixinUtil.httpRequest(exchangeUri, "GET", null);
    log.debug("jsonStr = " + jsonObj.toString());
    String userId = "";
    if (jsonObj.containsKey("UserId"))
      userId = jsonObj.getString("UserId"); 
    log.debug("userId = " + userId);
    return userId;
  }
  
  private static String accessUserIdForMessage(HttpServletRequest request) {
    log.debug("query = " + request.getQueryString());
    String code = GetUrlUtil.getCode(request);
    log.debug("code = " + code);
    String messageId = GetUrlUtil.getMessageId(request);
    String appId = AppRoom.getAppIdByAppBh(SysMessageUtil.getAppBhByMessageId(messageId));
    String exchangeUri = CoreOAuth.exchangeAccessUri(code, appId);
    log.debug("exchangeUri = " + exchangeUri);
    JSONObject jsonObj = WeixinUtil.httpRequest(exchangeUri, "GET", null);
    log.debug("jsonStr = " + jsonObj.toString());
    String userId = "";
    if (jsonObj.containsKey("UserId"))
      userId = jsonObj.getString("UserId"); 
    log.debug("userId = " + userId);
    return userId;
  }
  
  private static String accessUserIdForOutside(HttpServletRequest request) {
    log.debug("query = " + request.getQueryString());
    String code = GetUrlUtil.getCode(request);
    log.debug("code = " + code);
    String appId = AppRoom.getAppIdByAppBh(WeiXinGlobalNames.APP_NAME_ZXDK);
    String exchangeUri = CoreOAuth.exchangeAccessUri(code, appId);
    log.debug("exchangeUri = " + exchangeUri);
    JSONObject jsonObj = WeixinUtil.httpRequest(exchangeUri, "GET", null);
    log.debug("jsonStr = " + jsonObj.toString());
    String userId = "";
    if (jsonObj.containsKey("UserId"))
      userId = jsonObj.getString("UserId"); 
    log.debug("userId = " + userId);
    return userId;
  }
  
  private static String pickActionFromRequest(HttpServletRequest request) {
    String action = "";
    String url = request.getRequestURL().append("?").append(request.getQueryString()).toString();
    String mark = url;
    if (url.indexOf("&") > 0)
      mark = mark.substring(0, mark.indexOf("&")); 
    log.debug("mark = " + mark);
    mark = mark.substring(mark.indexOf("mark=") + "mark=".length());
    String link = "";
    ViewMenu menu = (ViewMenu)URLCodeConverter.getMenuByCode(mark);
    link = menu.getUrl();
    log.debug("link = " + link);
    action = GetUrlUtil.getMiddle(link, "?/", "&code=");
    return action;
  }
  
  private static String pickMessageIdFromRequest(HttpServletRequest request) {
    String url = request.getRequestURL().append("?").append(request.getQueryString()).toString();
    String messageId = url;
    if (url.indexOf("&") > 0)
      messageId = messageId.substring(0, messageId.indexOf("&")); 
    messageId = messageId.substring(messageId.indexOf("message=") + "message=".length());
    return messageId;
  }
  
  private static String pickOutsideIdFromRequest(HttpServletRequest request) {
    String url = request.getRequestURL().append("?").append(request.getQueryString()).toString();
    String outsideId = url;
    if (url.indexOf("&") > 0)
      outsideId = outsideId.substring(0, outsideId.indexOf("&")); 
    outsideId = outsideId.substring(outsideId.indexOf("outsideId=") + "outsideId=".length());
    return outsideId;
  }
  
  private static void processClickButtonChat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getQueryString();
    if (!TimeCrypt.isTimely(query)) {
      log.debug("query = " + query + " : 已过时，点击了一分钟以前的链接");
      request.setAttribute("expire", "true");
      request.getRequestDispatcher("/weixinError.jsp").forward((ServletRequest)request, (ServletResponse)response);
      return;
    } 
    String weixin_UserId = GetUrlUtil.getOpenId(request);
    String action = pickActionFromRequest(request);
    request.setAttribute("weixin_UserId", weixin_UserId);
    request.setAttribute("action", action);
    forwardRequestDispather(request, response);
  }
  
  private static void forwardRequestDispather(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = (String)request.getAttribute("action");
    String weixin_UserId = (String)request.getAttribute("weixin_UserId");
    log.debug("action = " + action);
    try {
      String result = (new UserEJBBean()).getWeiXinLoginCheckInfo(weixin_UserId);
      if (!"0".equals(result)) {
        if ("1".equals(result)) {
          if (weixin_UserId == null || "".equals(weixin_UserId))
            request.setAttribute("idIsNull", "idIsNull"); 
          request.setAttribute("errInfo", "您未被管理员授权通过微信登录OA，请与管理员联系！");
        } else if ("2".equals(result)) {
          request.setAttribute("errInfo", "微信客户(手机)端超出授权范围，请与管理员联系！");
        } 
        request.getRequestDispatcher("/weixinError.jsp").forward((ServletRequest)request, (ServletResponse)response);
        return;
      } 
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    if (letUserLogin(request, response)) {
      log.debug("用户已登录。。。。。");
      request.getRequestDispatcher(action).forward((ServletRequest)request, (ServletResponse)response);
    } else {
      request.setAttribute("weixin_UserId", weixin_UserId);
      request.setAttribute("errInfo", "登录失败，请与管理员联系！");
      request.getRequestDispatcher("/weixinError.jsp").forward((ServletRequest)request, (ServletResponse)response);
    } 
  }
  
  private static boolean letUserLogin(HttpServletRequest request, HttpServletResponse response) {
    boolean isLogon = false;
    String weixin_UserId = (String)request.getAttribute("weixin_UserId");
    log.debug(request.getRequestURL() + "?" + request.getQueryString());
    HttpSession session = request.getSession(false);
    if (session != null && weixin_UserId.equals(session.getAttribute("weixin_UserId"))) {
      isLogon = true;
      log.debug("用户已登录");
      session.setAttribute("weixin_UserId", weixin_UserId);
    } else {
      log.debug("用户尚未登录， 登录中");
      isLogon = WeixinLogonAction.logon(request, response);
    } 
    return isLogon;
  }
}
