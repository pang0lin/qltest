package com.js.oa.logon.action;

import com.js.oa.hr.kq.po.KqDutySetPO;
import com.js.oa.hr.kq.po.KqRecordPO;
import com.js.oa.hr.kq.service.KqDutySetBD;
import com.js.oa.hr.kq.service.KqHolidayBD;
import com.js.oa.hr.kq.service.KqNosignBD;
import com.js.oa.hr.kq.service.KqRecordBD;
import com.js.oa.logon.service.LogonBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.config.UploadConfig;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import com.js.util.util.MD5;
import com.js.util.util.OnlineUserMap;
import com.js.util.util.RTXStrSingleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class lhydCheckUser extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    httpServletResponse.setContentType("text/html;charset=UTF-8");
    HttpSession session = httpServletRequest.getSession(true);
    String userAccount = "";
    String userPassword = "";
    String userIP = "";
    String sessionId = "";
    String serverIP = "";
    Object object = "";
    String flag = "success";
    if (httpServletRequest.getParameter("TransferUrl") != null) {
      object = httpServletRequest.getQueryString();
      object = object.substring(object
          .indexOf("TransferUrl=") + 12);
    } else if (session.getAttribute("TransferUrl") != null) {
      object = session.getAttribute("TransferUrl");
    } 
    sessionId = session.getId();
    userAccount = httpServletRequest.getParameter("userName");
    userIP = httpServletRequest.getRemoteAddr();
    if ("0:0:0:0:0:0:0:1".equals(userIP))
      userIP = "127.0.0.1"; 
    try {
      serverIP = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    HashMap<Object, Object> map = new HashMap<Object, Object>(httpServletRequest.getParameterMap());
    filterParameterChar(map, httpServletRequest);
    if (session.getAttribute("TransferUrl") == null) {
      httpServletRequest.setAttribute("TransferUrl", object);
      httpServletRequest.setAttribute("urlTarget", 
          (httpServletRequest.getParameter("urlTarget") == null) ? "" : 
          httpServletRequest.getParameter("urlTarget"));
    } 
    HashMap<Object, Object> userInfo = new HashMap<Object, Object>(10, 1.0F);
    userInfo = loginByAccount("", userAccount, 
        userPassword, userIP, serverIP, sessionId);
    if (userInfo == null) {
      httpServletRequest.setAttribute("errorType", "user");
    } else if (userInfo.get("error") != null) {
      httpServletRequest.setAttribute("errorType", 
          userInfo.get("error"));
    } else {
      if (userInfo.get("userName") != null) {
        String browserVersion = httpServletRequest.getHeader("User-Agent");
        if (browserVersion.indexOf("MSIE") >= 0) {
          session.setAttribute("browserVersion", "MSIEx");
          if (browserVersion.indexOf("MSIE 6.") >= 0) {
            session.setAttribute("browserVersion", "MSIE6");
          } else if (browserVersion.indexOf("MSIE 10.0") >= 0) {
            session.setAttribute("browserVersion", "IE10");
          } 
        } else if (browserVersion.indexOf("Trident/7.0") >= 0) {
          session.setAttribute("browserVersion", "IE11");
        } else if (browserVersion.indexOf("Firefox") >= 0) {
          session.setAttribute("browserVersion", "Firefox");
        } else if (browserVersion.indexOf("Chrome") >= 0) {
          session.setAttribute("browserVersion", "Chrome");
        } else if (browserVersion.indexOf("Safari") >= 0) {
          session.setAttribute("browserVersion", "Safari");
        } else {
          session.setAttribute("browserVersion", "MSIEx");
        } 
        if (browserVersion.indexOf("iPad") >= 0) {
          session.setAttribute("OSType", "ipad");
        } else if (browserVersion.indexOf("Android") >= 0) {
          session.setAttribute("OSType", "Android");
        } else if (browserVersion.indexOf("iPhone") >= 0) {
          session.setAttribute("OSType", "iPhone");
        } else {
          session.setAttribute("OSType", "pc");
        } 
        String IE11 = httpServletRequest.getParameter("checkBrow");
        if (!"".equals(IE11) && IE11 != null) {
          session.removeAttribute("browserVersion");
          session.setAttribute("browserVersion", IE11);
        } 
        Map uploadMap = UploadConfig.getInstance()
          .getUploadMap();
        String lanIP = uploadMap.get("LanIP").toString();
        if (userIP.startsWith(lanIP)) {
          session.setAttribute("fileServer", 
              uploadMap.get("FileInnerServer"));
          session.setAttribute("ftpMap", uploadMap.get("FtpInnerMap"));
        } else {
          session.setAttribute("fileServer", 
              uploadMap.get("FileServer"));
          session.setAttribute("ftpMap", uploadMap.get("FtpMap"));
        } 
        session.setAttribute("domainId", userInfo.get("domainId"));
        session.setAttribute("personID", userInfo.get("guid"));
        session.setAttribute("orgID", userInfo.get("orgGUID"));
        if (userInfo.get("userAccount") != null && (
          "admin".equals(userInfo.get("userAccount")) || "audit-admin"
          .equals(userInfo.get("userAccount")))) {
          session.setAttribute("userName", "admin".equals(userInfo
                .get("userAccount")) ? "系统管理员" : "审计管理员");
          session.setAttribute("userId", "admin".equals(userInfo
                .get("userAccount")) ? "0" : "-99");
          session.setAttribute("userAccount", 
              userInfo.get("userAccount").toString());
          session.setAttribute("orgName", "");
          session.setAttribute("orgId", "0");
          session.setAttribute("orgIdString", "");
          session.setAttribute("browseRange", "0");
          session.setAttribute("sysManager", "1");
          session.setAttribute("skin", "blue");
          session.setAttribute("rootCorpId", "0");
          session.setAttribute("corpId", "0");
          session.setAttribute("departId", "0");
        } else {
          session.setAttribute("userName", userInfo.get("userName"));
          session.setAttribute("userId", userInfo.get("userId"));
          session.setAttribute("orgName", userInfo.get("orgName"));
          session.setAttribute("orgId", userInfo.get("orgId"));
          session.setAttribute("orgIdString", 
              userInfo.get("orgIdString"));
          session.setAttribute("skin", "blue");
          session.setAttribute("rootCorpId", 
              userInfo.get("rootCorpId"));
          session.setAttribute("corpId", userInfo.get("corpId"));
          session.setAttribute("departId", userInfo.get("departId"));
          if (userInfo.get("sidelineList") != null)
            session.setAttribute("sidelineList", userInfo.get("sidelineList")); 
          if (userInfo.get("sidelineDepartId") != null) {
            session.setAttribute("sidelineCorpId", 
                userInfo.get("sidelineCorpId"));
            session.setAttribute("sidelineDepartId", 
                userInfo.get("sidelineDepartId"));
          } else {
            session.setAttribute("sidelineCorpId", "0");
            session.setAttribute("sidelineDepartId", "0");
          } 
          if ("1".equals(
              SystemCommon.getUseBrowseRange())) {
            if (userInfo.get("browseRange") == null || 
              "".equals(userInfo.get("browseRange")
                .toString())) {
              String browseRangeType = 
                SystemCommon.getDefaultBrowseRange();
              if ("1".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*" + 
                    userInfo.get("corpId").toString() + 
                    "*");
              } else if ("0".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*0*");
              } else if ("2".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*" + 
                    userInfo.get("departId").toString() + 
                    "*");
              } 
            } else {
              String browseRangeType = userInfo
                .get("browseRange").toString();
              if ("1".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*" + 
                    userInfo.get("corpId").toString() + 
                    "*");
              } else if ("0".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*0*");
              } else if ("2".equals(browseRangeType)) {
                session.setAttribute("browseRange", "*" + 
                    userInfo.get("departId").toString() + 
                    "*");
              } else {
                session.setAttribute("browseRange", 
                    userInfo.get("browseRange"));
              } 
            } 
          } else {
            session.setAttribute("browseRange", "*0*");
          } 
          if ("1".equals(
              SystemCommon.getUseGrantRange())) {
            if (userInfo.get("grantRange") == null || 
              "".equals(userInfo.get("grantRange")
                .toString())) {
              String grantRangeType = 
                SystemCommon.getDefaultGrantRange();
              if ("1".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*" + 
                    userInfo.get("corpId").toString() + 
                    "*");
              } else if ("0".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*0*");
              } else if ("2".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*" + 
                    userInfo.get("departId").toString() + 
                    "*");
              } 
            } else {
              String grantRangeType = userInfo.get("grantRange")
                .toString();
              if ("1".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*" + 
                    userInfo.get("corpId").toString() + 
                    "*");
              } else if ("0".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*0*");
              } else if ("2".equals(grantRangeType)) {
                session.setAttribute("grantRange", "*" + 
                    userInfo.get("departId").toString() + 
                    "*");
              } else {
                session.setAttribute("grantRange", 
                    userInfo.get("grantRange"));
              } 
            } 
          } else {
            session.setAttribute("grantRange", 
                session.getAttribute("browseRange"));
          } 
          session.setAttribute("userAccount", userAccount);
          session.setAttribute("sysManager", 
              userInfo.get("sysManager"));
          if (userInfo.get("userSimpleName") != null) {
            session.setAttribute("userSimpleName", 
                userInfo.get("userSimpleName"));
          } else {
            session.setAttribute("userSimpleName", "");
          } 
          if (userInfo.get("orgSerial") != null) {
            session.setAttribute("orgSerial", 
                userInfo.get("orgSerial"));
          } else {
            session.setAttribute("orgSerial", "");
          } 
          if (userInfo.get("orgSimpleName") != null) {
            session.setAttribute("orgSimpleName", 
                userInfo.get("orgSimpleName"));
          } else {
            session.setAttribute("orgSimpleName", "");
          } 
          session.setAttribute(
              "dutyName", 
              (userInfo.get("dutyName") == null) ? "" : userInfo
              .get("dutyName"));
          session.setAttribute(
              "dutyLevel", 
              (userInfo.get("dutyLevel") == null) ? "0" : userInfo
              .get("dutyLevel"));
          session.setAttribute(
              "imID", 
              (userInfo.get("imID") == null) ? "0" : userInfo.get(
                "imID").toString());
        } 
        session.setAttribute("hasLoged", null);
        session.setAttribute("serverIP", serverIP);
        session.setAttribute("userIP", userIP);
        session.setAttribute("empEnglishName", 
            userInfo.get("empEnglishName"));
        String other = httpServletRequest.getParameter("other");
        String tan = httpServletRequest.getParameter("tan");
        String showTypeForVB = (httpServletRequest
          .getParameter("showTypeForVB") == null) ? "2" : 
          httpServletRequest.getParameter("showTypeForVB");
        httpServletRequest.setAttribute("showTypeForVB", showTypeForVB);
        httpServletRequest.setAttribute("other", other);
        httpServletRequest.setAttribute("tan", tan);
        String domainId = (String)session.getAttribute("domainId");
        if (OnlineUserMap.getInstance().get(userAccount) != null)
          OnlineUserMap.getInstance().remove(userAccount); 
        OnlineUserMap.getInstance().put(userAccount, sessionId);
        return actionMapping.findForward(flag);
      } 
      httpServletRequest.setAttribute("errorType", "user");
      flag = "error";
    } 
    httpServletResponse.getWriter().write("<script>alert('该用户没有OA权限，请联系OA管理员！');window.opener=null;window.open('','_self');window.close();</script>");
    return null;
  }
  
  public String check(String userAccount, String pwdError) throws Exception {
    String flag = "";
    if (!pwdError.equals("0")) {
      String time = pwdError.substring(1, pwdError.length() - 1)
        .replaceAll("\\$", ";");
      flag = StaticParam.operLogonTempByEmpId(userAccount, "select", 
          "sau", time);
    } 
    return flag;
  }
  
  private void KQManger(HashMap userInfo) {
    String kqflag = "0";
    if ("1".equals(kqflag) && 
      userInfo.get("userAccount") == null) {
      SimpleDateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm");
      Date nowDate = new Date();
      KqRecordBD kqRecordBD = new KqRecordBD();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(nowDate);
      calendar.set(11, 0);
      calendar.set(12, 1);
      String beginDate = format.format(calendar.getTime());
      calendar.set(11, 23);
      calendar.set(12, 59);
      String endDate = format.format(calendar.getTime());
      try {
        boolean isNullRecord = kqRecordBD.seachByDate(
            Long.valueOf(userInfo.get("userId").toString()).longValue(), 
            beginDate, endDate);
        if (isNullRecord) {
          KqHolidayBD kqHolidayBD = new KqHolidayBD();
          boolean isHoliday = kqHolidayBD.searchByDate(format
              .format(nowDate));
          if (isHoliday) {
            KqDutySetBD kqDutySetBD = new KqDutySetBD();
            KqDutySetPO kqDutySetPO = kqDutySetBD
              .searchByUserId(Long.valueOf(userInfo.get(
                    "userId").toString()).longValue());
            if ("1".equals(String.valueOf(kqDutySetPO
                  .getWorkday()
                  .charAt(calendar.get(7) - 1)))) {
              KqNosignBD kqNosignBD = new KqNosignBD();
              boolean isNosign = false;
              for (int i = 0; i < 6; i++) {
                String dutyTime = "0";
                if (i == 0) {
                  dutyTime = kqDutySetPO.getDutyTime1();
                } else if (i == 1) {
                  dutyTime = kqDutySetPO.getDutyTime2();
                } else if (i == 2) {
                  dutyTime = kqDutySetPO.getDutyTime3();
                } else if (i == 3) {
                  dutyTime = kqDutySetPO.getDutyTime4();
                } else if (i == 4) {
                  dutyTime = kqDutySetPO.getDutyTime5();
                } else if (i == 5) {
                  dutyTime = kqDutySetPO.getDutyTime6();
                } 
                if (!"0".equals(dutyTime)) {
                  KqRecordPO kqRecordPO = new KqRecordPO();
                  kqRecordPO.setUserId(
                      Long.valueOf(userInfo.get("userId")
                        .toString()).longValue());
                  kqRecordPO.setOrgId(
                      Long.valueOf(userInfo.get("orgId")
                        .toString()).longValue());
                  kqRecordPO.setRecordSeq(i + 1);
                  kqRecordPO.setDutyName(kqDutySetPO
                      .getDutyName());
                  if (i % 2 == 0) {
                    kqRecordPO.setDutyType(1);
                  } else {
                    kqRecordPO.setDutyType(2);
                  } 
                  String standard = dutyTime;
                  String[] standards = standard
                    .split(":");
                  calendar.set(11, 
                      Integer.valueOf(standards[0]
                        .toString()).intValue());
                  calendar.set(12, 
                      Integer.valueOf(standards[1]
                        .toString()).intValue());
                  kqRecordPO.setDutyTime(calendar
                      .getTime());
                  isNosign = kqNosignBD.isNosignByUser(
                      Long.valueOf(userInfo.get(
                          "userId").toString()).longValue(), 
                      i + 1, "", "");
                  if (isNosign) {
                    kqRecordPO.setRecordStatus(0);
                    kqRecordPO.setNosign(1);
                    kqRecordPO.setTimeDiff(0L);
                    kqRecordPO.setRecordTime(calendar
                        .getTime());
                  } else {
                    kqRecordPO.setRecordStatus(-1);
                    kqRecordPO.setNosign(0);
                  } 
                  kqRecordBD.add(kqRecordPO);
                } 
              } 
            } 
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private boolean isFirstTimeLogin(String userAccount) throws Exception {
    boolean flag = StaticParam.isFirstTimeLogin(userAccount);
    return flag;
  }
  
  private boolean isPasswordOutOfDate(String userAccount, String domainId) throws Exception {
    String pwd_date = StaticParam.isValidatePasswordOutDate(domainId);
    if (Integer.parseInt(pwd_date) > 0) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String currentDate = DateHelper.date2String(new Date(), 
          "yyyy-MM-dd HH:mm:ss");
      String last_modify_pwd_date = 
        StaticParam.getLastModifyPwdDate(userAccount);
      if (last_modify_pwd_date == null || last_modify_pwd_date.equals(""))
        last_modify_pwd_date = "1970-01-01 00:00:00"; 
      if (last_modify_pwd_date != null && 
        !"".equals(last_modify_pwd_date)) {
        int dateDiffer = 
          (int)DateHelper.getDistance(sdf.parse(last_modify_pwd_date), 
            sdf.parse(currentDate));
        int date_num = Integer.parseInt(pwd_date);
        if (dateDiffer >= date_num)
          return true; 
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  private ActionForward resetPwdBasic(String domainId, String userAccount, String userPassword, HttpServletRequest httpServletRequest, ActionMapping actionMapping) {
    String pwdStrong = StaticParam.getValidatePasswordStrong(domainId);
    if (!pwdStrong.equals("0")) {
      httpServletRequest.setAttribute("isDisplay", "inline");
      pwdStrong = pwdStrong.substring(1, pwdStrong.length() - 1)
        .replaceAll("\\$\\$", ";");
      String[] parameters = pwdStrong.split(";");
      String minLength = parameters[0];
      String maxLength = parameters[1];
      String mustCotainNumAndLetter = parameters[2];
      httpServletRequest.setAttribute("minLength", minLength);
      httpServletRequest.setAttribute("maxLength", maxLength);
      httpServletRequest.setAttribute("containNumAndLetter", 
          mustCotainNumAndLetter);
    } else {
      httpServletRequest.setAttribute("isDisplay", "none");
    } 
    httpServletRequest.setAttribute("userAccount", userAccount);
    if (userPassword != null) {
      httpServletRequest.setAttribute("password", (
          new MD5()).getMD5Code(userPassword));
    } else {
      httpServletRequest.setAttribute("password", "");
    } 
    return actionMapping.findForward("resetPassword");
  }
  
  private ActionForward checkPwdPolicy(String domainId, String userAccount, String userPassword, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ActionMapping actionMapping, HashMap userInfo) throws Exception {
    if ("chinaLife".equals(SystemCommon.getCustomerName())) {
      boolean isInner = false;
      String ip = httpServletRequest.getRemoteAddr();
      if (ip.equals("192.168.32.136") || ip.equals("192.168.32.137") || ip.equals("192.168.032.136") || ip.equals("192.168.032.137"))
        isInner = true; 
      if (!isInner)
        return actionMapping.findForward("success"); 
    } 
    if (StaticParam.isFirstTimeLoginValidate(domainId) && 
      isFirstTimeLogin(userAccount)) {
      httpServletRequest.setAttribute("title", "首次登录请重置密码!");
      return resetPwdBasic(domainId, userAccount, userPassword, 
          httpServletRequest, actionMapping);
    } 
    if (isPasswordOutOfDate(userAccount, domainId)) {
      httpServletRequest.setAttribute("title", "您的密码已过期,请重置密码!");
      return resetPwdBasic(domainId, userAccount, userPassword, 
          httpServletRequest, actionMapping);
    } 
    KQManger(userInfo);
    StaticParam.operLogonTempByEmpId(userAccount, "delete", null, 
        null);
    String keySerial = (String)userInfo.get("keySerial");
    if (keySerial != null && !"".equals(keySerial) && 
      !"null".equals(keySerial))
      return actionMapping.findForward("ikeyValidate"); 
    if ("1".equals(httpServletRequest.getParameter("portalLogon"))) {
      httpServletResponse.sendRedirect("/jsoa/ssdz/desktopss.jsp");
      return null;
    } 
    return actionMapping.findForward("success");
  }
  
  private boolean rtxSignAuth(String rtxAccount, String rtxSign) {
    boolean signTag = false;
    URL url = null;
    URLConnection con = null;
    String retValue = "";
    BufferedReader bufferReader = null;
    InputStream in = null;
    try {
      url = new URL(String.valueOf(RTXStrSingleton.getInstance().getRtxStr(
              "rtxServerStr")) + 
          "user=" + 
          rtxAccount + 
          "&sign=" + 
          URLEncoder.encode(rtxSign));
      con = url.openConnection();
      con.setDoInput(true);
      in = con.getInputStream();
      bufferReader = new BufferedReader(new InputStreamReader(in));
      retValue = bufferReader.readLine();
      if (retValue != null)
        retValue = retValue.trim(); 
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      try {
        if (bufferReader != null)
          bufferReader.close(); 
        if (in != null)
          in.close(); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    if ("success!".equals(retValue)) {
      signTag = true;
    } else {
      "failed!".equals(retValue);
    } 
    return signTag;
  }
  
  public HashMap loginByAccount() {
    return loginByAccount("", "", "", "", "", "");
  }
  
  public HashMap loginByAccount(String flag, String userAccount, String userPassword, String userIP, String serverIP, String sessionId) {
    HashMap<Object, Object> userInfo = new HashMap<Object, Object>();
    LogonBD logonBD = new LogonBD();
    if ("yes".equals(flag)) {
      userInfo = logonBD.logonDealWith(userAccount, userPassword, userIP, 
          serverIP, sessionId, "jiusi", "0");
    } else {
      userInfo = logonBD.logon(userAccount, userPassword, userIP, 
          serverIP, sessionId, "jiusi", "0");
    } 
    return userInfo;
  }
  
  private String asc2Str(String str) {
    StringBuffer strBuffer = new StringBuffer();
    if (str != null && !"".equals(str)) {
      int i = 0;
      while (i < str.length()) {
        String tempStr = str.substring(i, i + 2);
        strBuffer.append((char)Integer.parseInt(tempStr, 16));
        tempStr = null;
        i += 2;
      } 
    } 
    return strBuffer.toString();
  }
  
  private boolean filterParameterChar(Map paraMap, HttpServletRequest req) {
    return true;
  }
  
  private List getInformationDetail(String informationId) {
    List<String> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "select a.INFORMATIONHEAD,a.INFORMATIONTYPE,b.CHANNELTYPE from OA_INFORMATION a left join OA_INFORMATIONCHANNEL b on a.CHANNEL_ID=b.CHANNEL_ID where a.INFORMATION_ID=" + informationId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        list.add(rs.getString(1));
        list.add(rs.getString(2));
        list.add(rs.getString(3));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
}
