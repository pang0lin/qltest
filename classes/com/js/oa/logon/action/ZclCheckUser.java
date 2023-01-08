package com.js.oa.logon.action;

import cn.tofirst.wszf.utils.CipherTools;
import com.js.ldap.LDAP;
import com.js.ldap.LdapInterface;
import com.js.ldap.MSAD;
import com.js.ldap.MSADNoCert;
import com.js.ldap.OpenLDAP;
import com.js.oa.hr.kq.po.KqDutySetPO;
import com.js.oa.hr.kq.po.KqRecordPO;
import com.js.oa.hr.kq.service.KqDutySetBD;
import com.js.oa.hr.kq.service.KqHolidayBD;
import com.js.oa.hr.kq.service.KqNosignBD;
import com.js.oa.hr.kq.service.KqRecordBD;
import com.js.oa.logon.service.LogonBD;
import com.js.oa.logon.service.SecurityStractegyGetterFactory;
import com.js.oa.logon.service.SecurityStrategyGetter;
import com.js.oa.logon.util.CACkeckUtil;
import com.js.oa.logon.util.DesUtil;
import com.js.oa.logon.util.SSOUtil;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.security.seamoon.service.SeaMoonService;
import com.js.oa.zcl.util.AESUtils;
import com.js.oa.zcl.util.HttpClientUtil;
import com.js.oa.zcl.util.WebserviceUtil;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.config.UploadConfig;
import com.js.util.util.BASE64;
import com.js.util.util.DateHelper;
import com.js.util.util.MD5;
import com.js.util.util.OnlineUserMap;
import com.js.util.util.RTXStrSingleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nestframework.commons.utils.RSA_Encrypt;
import seamoon.seamoonclient;
import sun.misc.BASE64Decoder;

public class ZclCheckUser extends Action {
  private static String AES_key = "InspurCB&JiusiOA=NB502";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    httpServletResponse.setContentType("text/html;charset=UTF-8");
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    String userAccount = "";
    String userPassword = "";
    String userIP = "";
    String sessionId = "";
    String serverIP = "";
    Object object = "";
    String sessionIdtemp = "", uuid = "", url = "";
    String loginSuccess = "";
    if (httpServletRequest.getParameter("TransferUrl") != null) {
      object = httpServletRequest.getQueryString();
      object = object.substring(object
          .indexOf("TransferUrl=") + 12);
    } else if (session.getAttribute("TransferUrl") != null) {
      object = session.getAttribute("TransferUrl");
    } 
    sessionId = session.getId();
    String rtxFlag = httpServletRequest.getParameter("rtxFlag");
    CheckUserForm userForm = (CheckUserForm)actionForm;
    if (userForm != null) {
      userAccount = StaticParam.getAccountByUserName((userForm.getUserName() == null) ? (
          (httpServletRequest.getParameter("userAccount") == null) ? "" : httpServletRequest
          .getParameter("userAccount")) : userForm.getUserName());
      userPassword = (userForm.getUserPassword() == null) ? httpServletRequest
        .getParameter("userPassword") : userForm.getUserPassword();
      if ((userPassword != null && !"null".equals(userPassword) && userPassword.lastIndexOf("ABCDEF") > 15) || "1".equals(SystemCommon.getPasswordEncryption())) {
        if (userPassword.lastIndexOf("ABCDEF") < 1)
          userPassword = String.valueOf(userPassword) + "ABCDEF"; 
        userPassword = (new DesUtil()).decodeStr(userPassword);
      } 
    } 
    AESUtils aes = new AESUtils();
    sessionIdtemp = httpServletRequest.getParameter("sessionIdtemp");
    System.out.println("接收到的sessionIdtemp：" + sessionIdtemp);
    uuid = httpServletRequest.getParameter("uuid");
    System.out.println("接收到的uuid：" + uuid);
    url = httpServletRequest.getParameter("url");
    System.out.println("接收到的url：" + url);
    sessionIdtemp = AESUtils.aesDecrypt(sessionIdtemp, AES_key);
    System.out.println("解密后sessionIdtemp：" + sessionIdtemp);
    uuid = AESUtils.aesDecrypt(uuid, AES_key);
    System.out.println("解密后uuid：" + uuid);
    url = AESUtils.aesDecrypt(url, AES_key);
    System.out.println("解密后url：" + url);
    String userid = uuid.substring(uuid.indexOf(".") + 1);
    System.out.println("用户userid：" + userid);
    String charset = "utf-8";
    HttpClientUtil httpClientUtil = new HttpClientUtil();
    sessionIdtemp = AESUtils.aesEncrypt(sessionIdtemp, AES_key);
    System.out.println("加密后sessionIdtemp：" + sessionIdtemp);
    userid = AESUtils.aesEncrypt(userid, AES_key);
    System.out.println("加密后userid：" + userid);
    String httpOrgCreate = String.valueOf(url) + "?sessionIdtemp=" + sessionIdtemp + "&userId=" + userid;
    httpOrgCreate = httpOrgCreate.replace("+", "%2B").replace(" ", "%20");
    System.out.println("访问的url:" + httpOrgCreate);
    String returnXml = httpClientUtil.doGet(httpOrgCreate, charset);
    System.out.println("返回值解密前return:" + returnXml);
    returnXml = AESUtils.aesDecrypt(returnXml, AES_key);
    System.out.println("返回值解密后return:" + returnXml);
    WebserviceUtil wu = new WebserviceUtil();
    if (!"".equals(returnXml) && returnXml != null) {
      Map map1 = wu.getLogInfo(returnXml);
      loginSuccess = (String)map1.get("flag");
      if ("true".equals(loginSuccess))
        userAccount = uuid; 
    } 
    String opCode = httpServletRequest.getParameter("opCode");
    userIP = httpServletRequest.getRemoteAddr();
    if ("0:0:0:0:0:0:0:1".equals(userIP))
      userIP = "127.0.0.1"; 
    Date newdate = new Date();
    HashMap<Object, Object> map = new HashMap<Object, Object>(httpServletRequest.getParameterMap());
    filterParameterChar(map, httpServletRequest);
    String action = httpServletRequest.getParameter("action");
    String logonFlag = httpServletRequest.getParameter("logonFlag");
    if (logonFlag != null && "dandian".equals(logonFlag) && 
      userPassword != null) {
      BASE64Decoder decoder = new BASE64Decoder();
      try {
        byte[] b = decoder.decodeBuffer(userPassword);
        userPassword = new String(b);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("1".equals(SystemCommon.gethuanbaobuCA())) {
      String checkCA = httpServletRequest.getParameter("checkCA");
      if (!"true".equals(checkCA)) {
        String errDesc = CACkeckUtil.checkCALogin(httpServletRequest, httpServletResponse);
        if (!"".equals(errDesc) && errDesc != null) {
          httpServletRequest.setAttribute("message", errDesc);
          httpServletRequest.setAttribute("CA", "false");
          return actionMapping.findForward("error");
        } 
      } 
    } 
    if ("1".equals(SystemCommon.getUnitCertifySwitch()) && 
      "auth".equals(httpServletRequest.getParameter("flag"))) {
      httpServletRequest.setAttribute("flag", "auth");
      httpServletRequest.setAttribute("userPass", userPassword);
      httpServletRequest.setAttribute("userAcc", userAccount);
      return actionMapping.findForward("toUnitCertify");
    } 
    String auth_username = "";
    if (httpServletRequest.getParameter("auth_username") != null) {
      auth_username = httpServletRequest.getParameter("auth_username");
    } else if (httpServletRequest.getParameter("authtest_username") != null) {
      auth_username = httpServletRequest
        .getParameter("authtest_username");
    } 
    String pwdError = (httpServletRequest.getParameter("pwdError") == null) ? "0" : 
      httpServletRequest.getParameter("pwdError");
    String imageCode = httpServletRequest.getParameter("ImageCode");
    if (imageCode != null) {
      String sessionCode = (session.getAttribute("sessionCode_") != null) ? (String)session.getAttribute("sessionCode_") : "-1";
      if (!sessionCode.toLowerCase().equals(imageCode.toLowerCase()))
        return new ActionForward("/login.jsp?errorType=imageCode"); 
    } 
    try {
      serverIP = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    session.setAttribute("userPassword", userPassword);
    HashMap<Object, Object> userInfo = new HashMap<Object, Object>(10, 1.0F);
    int useLDAP = (new LDAP()).getUseLDAP();
    String openDS = (new StringBuilder(String.valueOf((new OpenLDAP()).getLdapInfo().get("ldapUse")))).toString();
    if ("rtxLogin".equals(rtxFlag)) {
      String rtxMsg = httpServletRequest.getParameter("rtxMsg");
      String rtxSign = httpServletRequest.getParameter("rtxSign");
      rtxSign = asc2Str(rtxSign);
      userAccount = StaticParam.getAccountByUserName(httpServletRequest.getParameter("rtxAccount"));
      if (rtxSignAuth(userAccount, rtxSign))
        userInfo = loginByAccount(rtxMsg, userAccount, 
            userPassword, userIP, serverIP, sessionId); 
    } else if ("saiTong".equals(rtxFlag)) {
      userAccount = StaticParam.getAccountByUserName(httpServletRequest.getParameter("rtxAccount"));
      String rtxMsg = httpServletRequest.getParameter("rtxMsg");
      userInfo = loginByAccount(rtxMsg, userAccount, userPassword, 
          userIP, serverIP, sessionId);
    } else if ("bgnLogin".equals(rtxFlag)) {
      String code = httpServletRequest.getParameter("bgnLoginCode");
      code = BASE64.BASE64DecoderNoBR(code);
      String msgAction = code.substring(13, code.indexOf("&"));
      code = code.substring(code.indexOf("&") + 1);
      String suffix = code.substring(7, code.indexOf("&"));
      code = code.substring(code.indexOf("&") + 1);
      userAccount = StaticParam.getAccountByUserName(code.substring(10, code.indexOf("&")));
      code = code.substring(code.indexOf("&") + 1);
      msgAction = String.valueOf(msgAction) + "." + suffix + "?" + code;
      if (msgAction.startsWith("/jsoa/"))
        msgAction = msgAction.substring(5); 
      httpServletRequest.setAttribute("msgAction", msgAction);
      String rtxMsg = httpServletRequest.getParameter("rtxMsg");
      userInfo = loginByAccount(rtxMsg, userAccount, userPassword, 
          userIP, serverIP, sessionId);
      Locale loc = new Locale("zh_cn");
      session.setAttribute("org.apache.struts.action.LOCALE", loc);
    } else if (!"".equals(opCode) && opCode != null) {
      String decode = "";
      String urlStr = "";
      String datesStr = "";
      String urlStrings = "";
      String urlStrng = "";
      try {
        CipherTools tools = new CipherTools(
            "7668E0B2AEBA477BA1EAE12F0D3CBA8D");
        decode = tools.decrypt(opCode);
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      if (!"".equals(decode)) {
        decode = decode.replace("{", "").replace("}", "");
        String[] argStrings = decode.split(",");
        String[] useStringser = argStrings[0].split(":");
        String[] dateStrings = argStrings[1].split(":");
        urlStrings = argStrings[2].toString().substring(9, 
            argStrings[2].toString().length() - 1);
        userAccount = StaticParam.getAccountByUserName(useStringser[1].replace("\"", ""));
        datesStr = dateStrings[1].replace("\"", "");
        urlStrng = urlStrings;
        if (!"".equals(urlStrng))
          urlStr = String.valueOf(urlStrng) + 
            "json/AuthorityOAAction!getUserSession?opCode="; 
      } 
      URL urlName = new URL(String.valueOf(urlStr) + opCode);
      HttpURLConnection httpURLConnection = (HttpURLConnection)urlName
        .openConnection();
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setDoOutput(true);
      InputStream is = httpURLConnection.getInputStream();
      BufferedReader br = new BufferedReader(
          new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      while (br.read() != -1)
        sb.append(br.readLine()); 
      String content = new String(sb);
      content = new String(content.getBytes("GB2312"), "ISO-8859-1");
      br.close();
      if (!"".equals(decode)) {
        LogonBD logonBD = new LogonBD();
        if (content.replace("\"", "").equals("10000")) {
          userInfo = logonBD.logon(userAccount, "", userIP, 
              serverIP, sessionId, "jiusi", "0");
        } else {
          if ("10001".equals(content.replace("\"", ""))) {
            httpServletRequest.setAttribute("userAccountName", 
                userAccount);
            httpServletRequest.setAttribute("flagStr", "false");
            return actionMapping.findForward("error");
          } 
          if ("10002".equals(content.replace("\"", ""))) {
            httpServletRequest.setAttribute("userAccountName", 
                userAccount);
            httpServletRequest.setAttribute("flagStr", "false");
            return actionMapping.findForward("error");
          } 
          if ("10003".equals(content.replace("\"", ""))) {
            httpServletRequest.setAttribute("userAccountName", 
                userAccount);
            httpServletRequest.setAttribute("flagStr", "false");
            return actionMapping.findForward("error");
          } 
          if ("10004".equals(content.replace("\"", ""))) {
            httpServletRequest.setAttribute("userAccountName", 
                userAccount);
            httpServletRequest.setAttribute("flagStr", "false");
            return actionMapping.findForward("error");
          } 
          if ("10005".equals(content.replace("\"", ""))) {
            httpServletRequest.setAttribute("userAccountName", 
                userAccount);
            httpServletRequest.setAttribute("flagStr", "false");
            return actionMapping.findForward("error");
          } 
        } 
      } 
    } else if (userAccount != null && userPassword != null && (
      useLDAP == 1 || "1".equals(openDS)) && 
      !userAccount.trim().toLowerCase().equals("admin") && 
      !userAccount.trim().toLowerCase().equals("audit-admin")) {
      if (useLDAP == 1) {
        String rs;
        LDAP ldap = new MSAD();
        if (LDAP.getUseCert() == 0) {
          rs = (new MSADNoCert()).Authenticate(userAccount, userPassword);
        } else {
          rs = ldap.Authenticate(userAccount, userPassword);
        } 
        if ("0".equals(rs)) {
          userInfo = (new LogonBD()).logon(userAccount, 
              userPassword, userIP, serverIP, sessionId, 
              "jiusi", "0");
        } else {
          if ("-1".equals(rs)) {
            httpServletRequest.setAttribute("errorType", "user");
            return actionMapping.findForward("error");
          } 
          if ("1".equals(rs)) {
            httpServletRequest.setAttribute("errorType", "password");
            return actionMapping.findForward("error");
          } 
        } 
      } else {
        String openDSv = (new LdapInterface()).validateUser(
            userAccount, userPassword);
        if ("-1".equals(openDSv)) {
          httpServletRequest.setAttribute("errorType", "user");
          return actionMapping.findForward("error");
        } 
        if ("0".equals(openDSv)) {
          httpServletRequest
            .setAttribute("errorType", "password");
          return actionMapping.findForward("error");
        } 
        userInfo = (new LogonBD()).logon(openDSv, userPassword, 
            userIP, serverIP, sessionId, "jiusi", "0");
      } 
    } else if ("2".equals(SystemCommon.getUKey()) && userAccount != null && 
      !userAccount.trim().toLowerCase().equals("admin")) {
      seamoonclient sc = new seamoonclient();
      if ("3".equals(SystemCommon.getLogType())) {
        if (userPassword != null) {
          MD5 md5 = new MD5();
          userPassword = md5.getMD5Code(userPassword);
        } 
        userInfo = (new LogonBD()).logon(userAccount, 
            userPassword, userIP, serverIP, sessionId, 
            "jiusi", "1");
      } else {
        SeaMoonService seaMoonService = new SeaMoonService();
        if ("0".equals(SystemCommon.getLogType())) {
          String ckResult;
          if ("1".equals(SystemCommon.getIsSeaMoonService())) {
            String dynPassword = userPassword
              .substring(userPassword.length() - 6);
            int re = sc.checkpassword(
                SystemCommon.getSeaMoonServiceIp(), 
                Integer.valueOf(
                  SystemCommon.getSeaMoonServicePort()).intValue(), 
                userAccount, dynPassword);
            ckResult = String.valueOf(re);
          } else {
            ckResult = seaMoonService
              .checkPasswordByUserAccountDynKey(
                userAccount, userPassword);
          } 
          if ("1".equals(ckResult)) {
            userInfo = (new LogonBD()).logon(userAccount, 
                userPassword, userIP, serverIP, 
                sessionId, "jiusi", "0");
          } else {
            httpServletRequest.setAttribute("errorType", 
                "password");
            return actionMapping.findForward("error");
          } 
        } else if ("1".equals(SystemCommon.getLogType())) {
          int dynLen = 6;
          if (userPassword.length() > 6) {
            String ckResult, dynPassword = userPassword
              .substring(userPassword.length() - 6);
            userPassword = userPassword.substring(0, 
                userPassword.length() - 6);
            if ("1".equals(
                SystemCommon.getIsSeaMoonService())) {
              int re = sc.checkpassword(
                  SystemCommon.getSeaMoonServiceIp(), 
                  Integer.valueOf(
                    SystemCommon.getSeaMoonServicePort()).intValue(), 
                  userAccount, dynPassword);
              ckResult = String.valueOf(re);
            } else {
              ckResult = seaMoonService
                .checkPasswordByUserAccountDynKey(
                  userAccount, dynPassword);
            } 
            if ("1".equals(ckResult) || 
              "2".equals(ckResult)) {
              if (userPassword != null) {
                MD5 md5 = new MD5();
                userPassword = md5
                  .getMD5Code(userPassword);
              } 
              userInfo = (new LogonBD()).logon(userAccount, 
                  userPassword, userIP, serverIP, 
                  sessionId, "jiusi", "1");
            } else {
              httpServletRequest.setAttribute(
                  "errorType", "password");
              return actionMapping.findForward("error");
            } 
          } else {
            httpServletRequest.setAttribute("errorType", 
                "password");
            return actionMapping.findForward("error");
          } 
        } else if ("2".equals(SystemCommon.getLogType())) {
          String ckResult, dynPassword = httpServletRequest
            .getParameter("dynPassword");
          if ("1".equals(SystemCommon.getIsSeaMoonService())) {
            int re = sc.checkpassword(
                SystemCommon.getSeaMoonServiceIp(), 
                Integer.valueOf(
                  SystemCommon.getSeaMoonServicePort()).intValue(), 
                userAccount, dynPassword);
            ckResult = String.valueOf(re);
          } else {
            ckResult = seaMoonService
              .checkPasswordByUserAccountDynKey(
                userAccount, dynPassword);
          } 
          if ("1".equals(ckResult) || "2".equals(ckResult)) {
            if (userPassword != null) {
              MD5 md5 = new MD5();
              userPassword = md5.getMD5Code(userPassword);
            } 
            userInfo = (new LogonBD()).logon(userAccount, 
                userPassword, userIP, serverIP, 
                sessionId, "jiusi", "1");
          } else {
            httpServletRequest.setAttribute("errorType", 
                "password");
            return actionMapping.findForward("error");
          } 
        } 
      } 
    } else if ("1".equals(SystemCommon.getUnitCertifySwitch()) && 
      !"".equals(auth_username) && 
      !auth_username.trim().toLowerCase().equals("admin") && 
      
      !auth_username.trim().toLowerCase().equals("audit-admin")) {
      if (httpServletRequest.getParameter("TransferUrl") != null)
        session.setAttribute("TransferUrl", object); 
      if (httpServletRequest.getParameter("urlTarget") != null)
        session.setAttribute("urlTarget", 
            (httpServletRequest.getParameter("urlTarget") == null) ? "" : 
            httpServletRequest.getParameter("urlTarget")); 
      if (httpServletRequest.getParameter("auth_error") != null && 
        "1".equals(httpServletRequest
          .getParameter("auth_error"))) {
        httpServletRequest
          .setAttribute("errorType", "password");
        return actionMapping.findForward("error");
      } 
      if ("sso".equals(httpServletRequest.getParameter("flag"))) {
        userPassword = "";
        httpServletRequest.setAttribute("flag", "ssoCheck");
        return actionMapping.findForward("toUnitCertify");
      } 
      userPassword = "";
      Object object1 = "";
      if (httpServletRequest.getParameter("auth_key") != null || 
        httpServletRequest.getAttribute("auth_key") != null) {
        if (httpServletRequest.getParameter("auth_key") != null) {
          object1 = httpServletRequest
            .getParameter("auth_key");
        } else {
          Object object2 = httpServletRequest
            .getAttribute("auth_key");
        } 
      } else {
        System.out.println(String.valueOf(auth_username) + "单点登录登录……");
        session.setAttribute("ssoType", "sso");
        if (httpServletRequest.getParameter("authtest_key") != null || 
          httpServletRequest
          .getAttribute("authtest_key") != null) {
          object1 = httpServletRequest
            .getParameter("authtest_key");
        } else {
          object1 = httpServletRequest
            .getAttribute("authtest_key");
        } 
      } 
      boolean reb = false;
      if (object1 != null && !"".equals(object1) && 
        auth_username != null && 
        !"".equals(auth_username))
        reb = RSA_Encrypt.verify(String.valueOf(sessionId) + auth_username, 
            (String)object1); 
      if (reb) {
        userAccount = StaticParam.getAccountByUserName(auth_username);
        userInfo = loginByAccount("", auth_username, userPassword, userIP, serverIP, sessionId);
      } else {
        String ip = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        httpServletRequest.setAttribute("unitCertifUrl", 
            "http://" + ip + ":" + port + 
            "/jsoa/CheckUser.do");
        return actionMapping.findForward("toUnitCertify");
      } 
    } else if ("zcl".equals(SystemCommon.getCustomerName())) {
      if (userAccount != null && !"null".equals(userAccount) && !"".equals(userAccount))
        userInfo = loginByAccount("", userAccount, 
            userPassword, userIP, serverIP, sessionId); 
      System.out.println("empid:" + userInfo.get("userId"));
      if (userInfo.get("userName") == null || "".equals(userAccount)) {
        httpServletResponse.getWriter().write("<script>alert('账号不正确或连接超时！');window.opener=null;window.open('','_self');window.close();</script>");
        return null;
      } 
    } else if (httpServletRequest.getHeader("iv-user") != null) {
      userAccount = StaticParam.getAccountByUserName(httpServletRequest.getHeader("iv-user"));
      userInfo = (new LogonBD()).logon(userAccount, userPassword, 
          userIP, serverIP, sessionId, "jiusi", "0");
    } else if (httpServletRequest.getParameter("flag") != null && 
      "casSSO".equals(httpServletRequest.getParameter("flag"))) {
      System.out.println("大兴区cas验证单点登录");
      userAccount = StaticParam.getAccountByUserName((httpServletRequest.getRemoteUser() == null) ? "" : 
          httpServletRequest.getRemoteUser());
      System.out.println("【" + userAccount + "】单点登录跳转……");
      if (userAccount != null && !"".equals(userAccount))
        userInfo = loginByAccount("", userAccount, 
            userPassword, userIP, serverIP, sessionId); 
    } else if (session.getAttribute("edu.yale.its.tp.cas.client.filter.user") != null) {
      userAccount = StaticParam.getAccountByUserName((String)session
          .getAttribute("edu.yale.its.tp.cas.client.filter.user"));
      userInfo = (new LogonBD()).logon(userAccount, userPassword, 
          userIP, serverIP, sessionId, "jiusi", "0");
    } else if (session.getAttribute("_const_cas_assertion_") == null || 
      "daxingedu".equals(SystemCommon.getCustomerName())) {
      if ("rws".equals(SystemCommon.getCustomerName())) {
        SecurityStrategyGetter getter = SecurityStractegyGetterFactory.getSecurityStrategyGetter(SystemCommon.getCustomerName());
        String userAccountTemp = getter.getUserAccountFromRequest(httpServletRequest);
        if (!"".equals(userAccountTemp)) {
          userAccount = StaticParam.getAccountByUserName(userAccountTemp);
          userInfo = (new LogonBD()).logon(userAccount, userPassword, 
              userIP, serverIP, sessionId, "jiusi", "0");
          SSOUtil.setSSOType(httpServletRequest);
        } else {
          if (userPassword != null) {
            MD5 md5 = new MD5();
            userPassword = md5.getMD5Code(userPassword);
          } 
          userInfo = (new LogonBD()).logon(userAccount, 
              userPassword, userIP, serverIP, sessionId, 
              "jiusi", "1");
        } 
      } else if ((!"chinaLife".equals(SystemCommon.getCustomerName()) && 
        !"daxingedu".equals(SystemCommon.getCustomerName())) || 
        "admin".equals(userAccount)) {
        if (!"".equals(userAccount)) {
          if (userPassword != null) {
            MD5 md5 = new MD5();
            userPassword = md5.getMD5Code(userPassword);
          } 
          userInfo = (new LogonBD()).logon(userAccount, 
              userPassword, userIP, serverIP, sessionId, 
              "jiusi", "1");
        } 
      } 
    } 
    if (session.getAttribute("TransferUrl") == null) {
      httpServletRequest.setAttribute("TransferUrl", object);
      httpServletRequest.setAttribute("urlTarget", 
          (httpServletRequest.getParameter("urlTarget") == null) ? "" : 
          httpServletRequest.getParameter("urlTarget"));
    } else {
      httpServletRequest.setAttribute("TransferUrl", object);
      httpServletRequest.setAttribute("urlTarget", 
          (session.getAttribute("urlTarget") == null) ? "" : 
          session.getAttribute("urlTarget"));
      session.removeAttribute("TransferUrl");
      session.removeAttribute("urlTarget");
    } 
    if (userInfo == null) {
      httpServletRequest.setAttribute("errorType", "user");
    } else if (userInfo.get("error") != null) {
      httpServletRequest.setAttribute("errorType", 
          userInfo.get("error"));
      if (!pwdError.equals("0"))
        httpServletRequest.setAttribute("PWDError", 
            check(userAccount, pwdError)); 
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
        if (!"2".equals(SystemCommon.getUKey()) && 
          userInfo.get("keySerial") != null) {
          session.setAttribute("keySerial", userInfo.get("keySerial"));
        } else {
          session.setAttribute("keySerial", null);
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
        if ("yes".equals(httpServletRequest.getParameter("rtxMsg"))) {
          if ("bgnLogin".equals(rtxFlag))
            return new ActionForward(httpServletRequest
                .getAttribute("msgAction").toString()); 
          Map<String, String> rtxParaMap = new HashMap<String, String>();
          Enumeration<String> nameEnum = httpServletRequest
            .getParameterNames();
          String pvalue = "";
          while (nameEnum.hasMoreElements()) {
            String pname = nameEnum.nextElement();
            pvalue = httpServletRequest.getParameter(pname);
            rtxParaMap.put(pname, pvalue);
            pname = null;
            pvalue = null;
          } 
          rtxParaMap.remove("rtxFlag");
          rtxParaMap.remove("rtxMsg");
          rtxParaMap.remove("rtxAccount");
          rtxParaMap.remove("rtxSign");
          rtxParaMap.remove("rtxMsgAction");
          rtxParaMap.remove("suffix");
          String rtxMsgAction = httpServletRequest
            .getParameter("rtxMsgAction");
          String suffix = httpServletRequest.getParameter("suffix");
          httpServletRequest.setAttribute("rtxMsgAction", 
              rtxMsgAction);
          httpServletRequest.setAttribute("suffix", suffix);
          httpServletRequest.setAttribute("rtxParaMap", rtxParaMap);
          return actionMapping.findForward("rtxOpenMsg");
        } 
        if (OnlineUserMap.getInstance().get(userAccount) != null)
          OnlineUserMap.getInstance().remove(userAccount); 
        OnlineUserMap.getInstance().put(userAccount, sessionId);
        if (!"0".equals(pwdError)) {
          String time = pwdError.substring(1, pwdError.length() - 1)
            .replaceAll("\\$", ";");
          String booleans = StaticParam.operLogonTempByEmpId(
              userAccount, "checkAgain", null, time);
          if (booleans.equals(""))
            return checkPwdPolicy(domainId, userAccount, 
                userPassword, httpServletRequest, httpServletResponse, 
                actionMapping, userInfo); 
          httpServletRequest.setAttribute("PWDError", 
              check(userAccount, pwdError));
          httpServletRequest
            .setAttribute("errorType", "password");
          return actionMapping.findForward("error");
        } 
        return checkPwdPolicy(domainId, userAccount, userPassword, 
            httpServletRequest, httpServletResponse, actionMapping, userInfo);
      } 
      httpServletRequest.setAttribute("errorType", "user");
      Date endDate = new Date();
      moduleCode = "oa_index";
      moduleName = "首页";
      oprType = "0";
      oprContent = "用户名错误";
      String tempid = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString().substring(4);
      logBD.log(tempid, userAccount, "", moduleCode, moduleName, startDate, endDate, oprType, oprContent, userIP, "0");
    } 
    return actionMapping.findForward("error");
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
}
