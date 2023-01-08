package com.ali.dingding.services;

import com.ali.dingding.action.DingLogonAction;
import com.ali.dingding.message.GetMessageUrl;
import com.ali.dingding.util.DingdingUtil;
import com.ali.dingding.util.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.util.config.SystemCommon;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class DingService {
  private static Logger log = Logger.getLogger(DingService.class);
  
  private static final String LOGON_FAILURE_PAGE = "/weixinError.jsp";
  
  public void processRequest(HttpServletRequest request, HttpServletResponse response) {
    try {
      if ("1".equals(SystemCommon.getUseDingDing())) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String token = DingdingUtil.getAccessToken().getToken();
        String requestURL = "https://oapi.dingtalk.com/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
        JSONObject json = HttpHelper.httpGet(requestURL.replace("ACCESS_TOKEN", token).replace("CODE", code));
        if (json.get("userid") != null && !"".equals(json.getString("userid"))) {
          String userId = json.getString("userid");
          request.setAttribute("ddUserId", userId);
          String result = (new UserEJBBean()).getDingLoginCheckInfo(userId);
          if ("1".equals(result)) {
            if (letUserLogin(request, response)) {
              String appBh = request.getParameter("bh");
              String messageId = request.getParameter("id");
              if (SystemCommon.getModules().indexOf(",dd_" + appBh + ",") < 0) {
                request.setAttribute("errInfo", "您的企业未被授权该应用，请与管理员联系！");
              } else if (messageId != null && !"".equals(messageId)) {
                String messageUrl = (new GetMessageUrl()).getUrlByMessageId(messageId);
                if (messageUrl != null && !"".equals(messageUrl)) {
                  messageUrl = messageUrl.startsWith("/jsoa") ? messageUrl.substring(5) : messageUrl;
                  request.getRequestDispatcher(messageUrl).forward((ServletRequest)request, (ServletResponse)response);
                  return;
                } 
              } else if (appBh != null && !"".equals(appBh)) {
                request.getSession(true).setAttribute("loginType", "dingding");
                request.getRequestDispatcher("wap2/subMenu.jsp?appBh=" + appBh).forward((ServletRequest)request, (ServletResponse)response);
                return;
              } 
            } 
          } else if ("0".equals(result)) {
            request.setAttribute("errInfo", "您未被管理员授权通过钉钉登录OA，请与管理员联系！");
          } else {
            request.setAttribute("errInfo", result);
          } 
        } else {
          request.setAttribute("errInfo", "钉钉帐号信息验证错误，请与管理员联系！");
        } 
      } else {
        request.setAttribute("errInfo", "企业未开启钉钉登录功能！");
      } 
      request.getRequestDispatcher("/weixinError.jsp").forward((ServletRequest)request, (ServletResponse)response);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private static boolean letUserLogin(HttpServletRequest request, HttpServletResponse response) {
    boolean isLogon = false;
    String ddUserId = (String)request.getAttribute("ddUserId");
    log.debug(request.getRequestURL() + "?" + request.getQueryString());
    HttpSession session = request.getSession(false);
    if (session != null && ddUserId.equals(session.getAttribute("ddUserId"))) {
      isLogon = true;
      log.debug("用户已登录");
      session.setAttribute("ddUserId", ddUserId);
    } else {
      log.debug("用户尚未登录， 登录中");
      isLogon = DingLogonAction.logon(request, response);
    } 
    return isLogon;
  }
}
