package com.js.util.util;

import com.js.system.manager.service.ManagerService;
import com.js.util.config.SysConfigReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckFilter implements Filter {
  private static SysConfigReader scr = new SysConfigReader();
  
  private static String sysTimeOut = SysConfigReader.readConfigValue("SysTimeOut", "min");
  
  private static int minTime = 60;
  
  static {
    if (sysTimeOut != null && !"".equals(sysTimeOut))
      minTime = Integer.valueOf(sysTimeOut).intValue(); 
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    HttpSession session = req.getSession();
    String servletPath = req.getServletPath();
    String sessionId = session.getId();
    String userAccount = (String)session.getAttribute("userAccount");
    if (userAccount != null)
      if (OnlineUserMap.getInstance().get(userAccount) == null) {
        session.invalidate();
        session = req.getSession();
        System.out.println(String.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())) + " " + userAccount + " 因系统重启登录失效");
      } else if (!OnlineUserMap.getInstance().get(userAccount).equals(sessionId)) {
        session.invalidate();
        session = req.getSession();
        System.out.println(String.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())) + " " + userAccount + " 因异地登录前次登录失效");
        UserOffLineReasonMap.getInstance().put(session.getId(), "otherPlace");
      } else if (servletPath.indexOf("sysmessage.jsp") == -1 && servletPath.indexOf("jsflow_updateonlineTime.jsp") == -1) {
        session.setAttribute("START_TIME", Long.valueOf(Calendar.getInstance().getTimeInMillis()));
      } else {
        long startTime = 0L;
        if (session.getAttribute("START_TIME") != null)
          startTime = ((Long)session.getAttribute("START_TIME")).longValue(); 
        if (startTime == 0L) {
          session.setAttribute("START_TIME", Long.valueOf(Calendar.getInstance().getTimeInMillis()));
        } else {
          long curMillis = Calendar.getInstance().getTimeInMillis();
          long millis = curMillis - startTime;
          if (millis / 1000L / 60L >= minTime) {
            session.invalidate();
            session = req.getSession();
            System.out.println(String.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())) + " " + userAccount + " 因" + minTime + "分钟未操作下线");
            session.setAttribute("outTimeDown", "1");
            UserOffLineReasonMap.getInstance().put(session.getId(), "outLine");
          } 
        } 
      }  
    if (servletPath != null && session != null && servletPath.indexOf("getUserAvatar") < 0 && 
      session.getAttribute("userId") == null && 
      servletPath.indexOf("login.jsp") == -1 && 
      servletPath.indexOf("CheckUser.") == -1 && 
      servletPath.indexOf("HntdxyCheckUser.") == -1 && 
      servletPath.indexOf("lhydCheckUser.") == -1 && 
      servletPath.indexOf("ZclCheckUser.") == -1 && 
      servletPath.indexOf("/zcl/goPageUrl.") == -1 && 
      servletPath.indexOf("wap.jsp") == -1 && 
      servletPath.indexOf("wap.do") == -1 && 
      servletPath.indexOf("RTXLogin.jsp") == -1 && 
      servletPath.indexOf("saiLogin.jsp") == -1 && 
      servletPath.indexOf("saitong.jsp") == -1 && 
      servletPath.indexOf("jumperrorMsg.jsp") == -1 && 
      servletPath.indexOf("/wap2/errorShow.jsp") == -1)
      if (servletPath.indexOf("dl.jsp") == -1 && 
        servletPath.indexOf("login_gzw.jsp") == -1 && 
        servletPath.indexOf("/lhyd/index.jsp") == -1 && 
        servletPath.indexOf("/hntdxy/test.jsp") == -1 && 
        servletPath.indexOf("hntdCustomDesktopAction.") == -1 && 
        servletPath.indexOf("recallPassword.jsp") == -1 && 
        servletPath.indexOf("recallPasswordAjax.jsp") == -1) {
        res.sendRedirect("/jsoa/login.jsp");
        return;
      }  
    String path = servletPath.toLowerCase();
    if (path.indexOf("/upload/") >= 0 && path.indexOf(".jsp") >= 0) {
      res.sendRedirect("/jsoa/login.jsp");
      return;
    } 
    List<String> list = ParameterFilter.getManagerActionURL();
    if (list.contains(servletPath)) {
      Map<String, String> map = ParameterFilter.getManagerActionRight();
      if (session.getAttribute("userId") != null && 
        !(new ManagerService()).hasRights(session.getAttribute("userId").toString(), map.get(servletPath))) {
        res.sendRedirect(String.valueOf(req.getContextPath()) + "/public/jsp/inputerror.jsp");
        return;
      } 
    } 
    List ignoreList = ParameterFilter.getIgnoreList();
    if (ignoreList.contains(servletPath)) {
      chain.doFilter(request, response);
    } else if ((session.getAttribute("wapVersion") != null && "3G".equals(session.getAttribute("wapVersion").toString())) || 
      servletPath.indexOf("/wap/") >= 0 || 
      servletPath.indexOf("/wap2/") >= 0 || 
      servletPath.indexOf("/wap.do") >= 0 || 
      servletPath.indexOf("/WapCoopAction") >= 0 || 
      servletPath.indexOf("/WorkDealWithAction") >= 0) {
      boolean isWapUTF8 = false;
      boolean isWapGBK = false;
      if (servletPath.indexOf("/wap/action/WapNotePaperAction.do") >= 0) {
        request.setCharacterEncoding("utf-8");
        isWapUTF8 = true;
      } else {
        request.setCharacterEncoding("utf-8");
        isWapGBK = true;
      } 
      HashMap<Object, Object> map = new HashMap<Object, Object>(req.getParameterMap());
      if (!filterWapParameter(map, req, isWapUTF8, isWapGBK)) {
        System.out.println("提交数据中有非法字符:访问的路径为:" + servletPath);
        res.sendRedirect(String.valueOf(req.getContextPath()) + "/wap2/waperror_3g.jsp");
      } else {
        chain.doFilter(request, response);
      } 
    } else {
      request.setCharacterEncoding("GBK");
      HashMap<Object, Object> map = new HashMap<Object, Object>(req.getParameterMap());
      if (!filterParameterChar(map, req)) {
        System.out.println("过滤出非法字符，可能是sql注入或xss攻击代码：访问路径为：" + servletPath);
        res.sendRedirect(String.valueOf(req.getContextPath()) + "/public/jsp/inputerror.jsp");
      } else {
        chain.doFilter(request, response);
      } 
    } 
  }
  
  public void init(FilterConfig filterConfig) throws ServletException {}
  
  public void destroy() {}
  
  private boolean filterWapParameter(Map paraMap, HttpServletRequest req, boolean isWapUTF8, boolean isWapGBK) {
    String tempValue = "";
    Iterator<Map.Entry> it = paraMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object key = entry.getKey();
      String[] array = req.getParameterValues(key.toString());
      for (int i = 0; i < array.length; i++) {
        try {
          if (isWapUTF8) {
            tempValue = URLDecoder.decode(array[i], "utf-8");
          } else if (isWapGBK) {
            tempValue = array[i];
          } else {
            tempValue = URLDecoder.decode(array[i]);
          } 
        } catch (Exception err) {
          err.printStackTrace();
        } 
        if (!filterOneParameter(tempValue))
          return false; 
      } 
    } 
    return true;
  }
  
  private boolean filterParameterChar(Map paraMap, HttpServletRequest req) {
    List ignorePara = ParameterFilter.getIgnorePara();
    Iterator<Map.Entry> it = paraMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object key2 = entry.getKey();
      String keyName = key2.toString();
      if (!ignorePara.contains(keyName)) {
        String[] array = req.getParameterValues(keyName);
        for (int i = 0; i < array.length; i++) {
          if (!filterOneParameter(array[i])) {
            System.out.println("key:" + keyName + "  vlaue:" + array[i]);
            return false;
          } 
        } 
      } 
    } 
    return true;
  }
  
  private boolean filterOneParameter(String value) {
    return ParameterFilter.checkParameter(value);
  }
}
