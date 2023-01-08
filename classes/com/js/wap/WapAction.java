package com.js.wap;

import com.js.ldap.LDAP;
import com.js.ldap.LdapInterface;
import com.js.ldap.MSAD;
import com.js.ldap.MSADNoCert;
import com.js.ldap.OpenLDAP;
import com.js.oa.chinaLife.ladp.OperateLdap;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.scheme.event.service.EventBD;
import com.js.oa.scheme.event.vo.EventVO;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.AES;
import com.js.util.util.BASE64;
import com.js.util.util.MD5;
import com.js.util.util.OnlineUserMap;
import com.js.wap.service.WapBD;
import com.js.wap.util.WapStringTool;
import com.js.wap.util.WapUtil;
import com.qq.weixin.mp.util.WeixinUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.nestframework.commons.utils.RSA_Encrypt;

public class WapAction extends DispatchAction {
  public ActionForward wapLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String name;
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(true);
    String userIP = request.getRemoteAddr();
    String version = "3G";
    String auth_key = "";
    String pwd = (new StringBuilder(String.valueOf(request.getParameter("userPassword")))).toString();
    String logDevice = "";
    String appToken = request.getParameter("apptoken");
    String workid = "";
    String sendfileId = "";
    String type = "";
    if (SystemCommon.getCustomerName().equals("zcl")) {
      workid = (new StringBuilder(String.valueOf(request.getParameter("workid")))).toString();
      sendfileId = (new StringBuilder(String.valueOf(request.getParameter("sendfileId")))).toString();
      type = (new StringBuilder(String.valueOf(request.getParameter("type")))).toString();
      request.setAttribute("workid", workid);
      request.setAttribute("sendfileId", sendfileId);
      request.setAttribute("type", type);
    } 
    String useraccount = "";
    if (SystemCommon.getCustomerName().equals("ynnt")) {
      String key = "ff77ba474e1ef8fc";
      String token = request.getParameter("token");
      String tokenstr = AES.Decrypt(token, key);
      useraccount = tokenstr.substring(0, tokenstr.indexOf(";"));
    } 
    if (appToken != null) {
      logDevice = "WebApp";
      long timeSign = 0L;
      long currentTime = (new Date()).getTime();
      appToken = BASE64.BASE64DecoderNoBR(appToken);
      int index = appToken.indexOf("&time=");
      name = appToken.substring(8, index);
      timeSign = Long.valueOf(appToken.substring(index + 6)).longValue();
      if (currentTime - timeSign > 120000L) {
        request.setAttribute("ERROR", "登录超时!");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
    } else {
      String userName = request.getParameter("userName");
      if (SystemCommon.getCustomerName().equals("ynnt"))
        userName = useraccount; 
      name = StaticParam.getAccountByUserName(URLDecoder.decode(userName, "utf-8"));
    } 
    WapUtil wUtil = new WapUtil();
    String canLog = wUtil.userCanLogMobile(name);
    if (!"1".equals(canLog)) {
      if ("-1".equals(canLog)) {
        request.setAttribute("ERROR", "用户不存在！");
      } else if ("0".equals(canLog)) {
        request.setAttribute("ERROR", "您未被授权使用移动登录！");
      } else if ("9".equals(canLog)) {
        request.setAttribute("ERROR", "移动登录用户数超出授权范围！");
      } 
      return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
    } 
    String openDS = (new OpenLDAP()).getLdapInfo().get("ldapUse");
    if (name == null && "1".equals(SystemCommon.getUnitCertifySwitch())) {
      name = request.getParameter("auth_username");
      auth_key = request.getParameter("auth_key");
      if (name == null || "null".equals(name) || "".equals(name)) {
        request.setAttribute("ERROR", "统一认证验证失败！");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
    } 
    if (name != null && pwd != null && "1".equals(openDS) && 
      !name.trim().toLowerCase().equals("admin") && 
      !name.trim().toLowerCase().equals("audit-admin") && appToken == null) {
      String openDSv = (new LdapInterface()).validateUser(name, pwd);
      if ("-1".equals(openDSv)) {
        request.setAttribute("ERROR", "此用户不存在！");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
      if ("0".equals(openDSv)) {
        request.setAttribute("ERROR", "密码输入错误！");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
    } 
    int useLDAP = (new LDAP()).getUseLDAP();
    String rs = "-1";
    if (useLDAP == 1 && appToken == null) {
      LDAP ldap = new MSAD();
      if (LDAP.getUseCert() == 0) {
        rs = (new MSADNoCert()).Authenticate(name, pwd);
      } else {
        rs = ldap.Authenticate(name, pwd);
      } 
      if (!"0".equals(rs)) {
        request.setAttribute("ERROR", "密码错误");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
    } 
    if (SystemCommon.getCustomerName().equals("chinaLife")) {
      String imageCode = request.getParameter("imageCode");
      String sessionCode = (session.getAttribute("sessionCode_") == null) ? "-1" : (String)session.getAttribute("sessionCode_");
      if (!sessionCode.toLowerCase().equals(imageCode.toLowerCase())) {
        request.setAttribute("ERROR", "验证码错误");
        System.out.println("------------mobile login 验证码错误:");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
      rs = (new OperateLdap()).authenticateUser(name, pwd);
      if (!"0".equals(rs)) {
        request.setAttribute("ERROR", "密码错误");
        return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
      } 
    } 
    String ldapPwd = pwd;
    MD5 md5 = new MD5();
    if (pwd != null)
      pwd = md5.getMD5Code(pwd); 
    Map infoMap = WapUtil.getUserInfoByLogonName(name);
    if (infoMap == null || infoMap.size() == 0) {
      request.setAttribute("ERROR", "用户名错误");
      return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
    } 
    String pwdTemp = (String)infoMap.get("userPassword");
    String initPwd = (String)infoMap.get("initpassword");
    if ("".equals(auth_key) && !md5.equals(pwdTemp, pwd) && !md5.equals(initPwd, pwd) && !"1".equals(openDS) && 
      useLDAP != 1 && !SystemCommon.getCustomerName().equals("chinaLife") && !"WebApp".equals(logDevice) && !SystemCommon.getCustomerName().equals("zcl") && !SystemCommon.getCustomerName().equals("ynnt")) {
      request.setAttribute("ERROR", "密码错误");
      return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
    } 
    if (infoMap.get("userissuper").toString().equals("1")) {
      if (infoMap.get("usersuperbegin") != null) {
        Calendar date = 
          Calendar.getInstance();
        Date superBegin = (Date)infoMap.get("usersuperbegin");
        Date superEnd = (Date)infoMap.get("usersuperend");
        if ((date.getTimeInMillis() > superEnd.getTime() || 
          date.getTimeInMillis() < superBegin.getTime()) && 
          !wUtil.ifIP(userIP)) {
          request.setAttribute("ERROR", "对不起，您的IP地址不在可访问范围内！");
          return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
        } 
      } 
    } else if (!wUtil.ifIP(userIP)) {
      request.setAttribute("ERROR", "对不起，您的IP地址不在可访问范围内！");
      return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
    } 
    boolean reb = false;
    if ("0".equals(SystemCommon.getUnitCertifySwitch())) {
      reb = true;
    } else if (!"".equals(auth_key) && !"".equals(name)) {
      reb = RSA_Encrypt.verify(String.valueOf(session.getId()) + name, auth_key);
    } 
    if (reb) {
      String userId = (String)infoMap.get("userId");
      String userName = (String)infoMap.get("userName");
      String orgId = (String)infoMap.get("orgId");
      String orgIdString = (String)infoMap.get("orgIdString");
      String orgName = (String)infoMap.get("orgName");
      session.setAttribute("userId", userId);
      session.setAttribute("orgId", orgId);
      session.setAttribute("orgIdString", orgIdString);
      session.setAttribute("orgNameString", infoMap.get("orgNameString"));
      session.setAttribute("userName", userName);
      session.setAttribute("departId", infoMap.get("orgId").toString());
      session.setAttribute("corpId", infoMap.get("corpId").toString());
      session.setAttribute("orgName", orgName);
      session.setAttribute("wapVersion", version);
      session.setAttribute("domainId", infoMap.get("domainId"));
      if ("1".equals(SystemCommon.getUseBrowseRange())) {
        if (infoMap.get("browseRange") == null || "".equals(infoMap.get("browseRange").toString())) {
          String browseRangeType = SystemCommon.getDefaultBrowseRange();
          if ("1".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*" + infoMap.get("corpId").toString() + "*");
          } else if ("0".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*0*");
          } else if ("2".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*" + infoMap.get("orgId").toString() + "*");
          } 
        } else {
          String browseRangeType = infoMap.get("browseRange").toString();
          if ("1".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*" + infoMap.get("corpId").toString() + "*");
          } else if ("0".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*0*");
          } else if ("2".equals(browseRangeType)) {
            session.setAttribute("browseRange", "*" + infoMap.get("orgId").toString() + "*");
          } else {
            session.setAttribute("browseRange", infoMap.get("browseRange"));
          } 
        } 
      } else {
        session.setAttribute("browseRange", "*0*");
      } 
      String browserVersion = request.getHeader("User-Agent");
      WeixinUtil.browserTypeToSession(browserVersion, session);
      session.setAttribute("userAccount", name);
      session.setAttribute("skin", "blue");
      Locale loc = new Locale("zh_cn");
      session.setAttribute("org.apache.struts.action.LOCALE", loc);
      if (SystemCommon.getCustomerName().equals("ynnt")) {
        session.setAttribute("loginType", "weixin");
      } else {
        session.setAttribute("loginType", "wap");
      } 
      session.setAttribute("logDevice", logDevice);
      if (request.getParameter("html5Page") != null)
        session.setAttribute("html5Page", "yes"); 
      String sessionId = session.getId();
      if (OnlineUserMap.getInstance().get(name) != null)
        OnlineUserMap.getInstance().remove(name); 
      OnlineUserMap.getInstance().put(name, sessionId);
      return mapping.findForward(WapStringTool.getForwardStr(version, "wapList"));
    } 
    request.setAttribute("ERROR", "统一认证验证失败！");
    return mapping.findForward(WapStringTool.getForwardStr(version, "logonError"));
  }
  
  public ActionForward getDetailContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String conType = request.getParameter("conType");
    String userId = (String)session.getAttribute("userId");
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String version = (String)session.getAttribute("wapVersion");
    if (conType.equals("_news_") || conType.equals("_notices_"))
      getWapNewsList(request, userId, orgId, orgIdString, "0"); 
    if (conType.equals("_notices_"))
      getWapNoticeList(request, userId, orgId, orgIdString, "0"); 
    if (conType.equals("_coops_"))
      getWapCoopList(request, userId, orgId, orgIdString, "0"); 
    if (conType.equals("_events_"))
      getWapEventList(request, userId, orgId, orgIdString, "0"); 
    if (conType.equals("_projects_")) {
      List showList = getWapProjectList(userId, orgId, orgIdString, "0");
      request.setAttribute("SHOW_LIST", showList);
      request.setAttribute("cardTitle", "我的项目");
    } 
    if (conType.equals("_meets_")) {
      getWapMeetList(request, userId, orgId, orgIdString, "0");
      request.setAttribute("cardTitle", "会议通知");
    } 
    if (version.equals("3G"))
      return mapping.findForward("getDetailContent_3g"); 
    if (version.equals("COLOR"))
      return mapping.findForward("getDetailContent"); 
    return mapping.findForward("getDetailContent_3g");
  }
  
  public ActionForward readInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String conType = request.getParameter("conType");
    String userId = (String)session.getAttribute("userId");
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = (String)session.getAttribute("orgIdString");
    Long readId = Long.valueOf(request.getParameter("readId"));
    String version = (String)session.getAttribute("wapVersion");
    EventVO eventVO = null;
    if (conType.equals("_event_")) {
      EventBD eventBD = new EventBD();
      eventVO = eventBD.selectSingleEvent(readId, Long.valueOf(userId), Long.valueOf(0L));
      request.setAttribute("eventVO", eventVO);
    } 
    if (conType.equals("_reportday_")) {
      WorkLogBD worklogBD = new WorkLogBD();
      WorkLogPO worklogPO = worklogBD.selectSingleWorkLogPO(readId);
      Set worklogComment = worklogPO.getWorklogComment();
      request.setAttribute("worklogComment", worklogComment);
      request.setAttribute("worklogPO", worklogPO);
    } 
    if (conType.equals("_reportweek_") || conType.equals("_reportmonth_")) {
      String reportType = request.getParameter("reportType");
      WapBD cdb = new WapBD();
      Map map = cdb.getReportContentByWeek(userId, orgId, orgIdString, "0", reportType, String.valueOf(readId));
      List<WorkReportPO> reList = (List)map.get("REPORTlIST");
      List commList = (List)map.get("COMMENTlIST");
      request.setAttribute("WorkReportPO", (reList != null && reList.size() > 0) ? reList.get(0) : null);
      request.setAttribute("commList", commList);
    } 
    if (conType.equals("_meets_")) {
      WapBD cdb = new WapBD();
      List<BoardRoomApplyPO> list = cdb.getMeetInfoById(String.valueOf(readId));
      request.setAttribute("meetingInfo", (list != null && list.size() > 0) ? list.get(0) : null);
    } 
    if (conType.equals("_news_") || conType.equals("_notices_")) {
      Map map = WapUtil.getInfoById(String.valueOf(readId));
      request.setAttribute("contentMap", map);
    } 
    if (conType.equals("_coops_")) {
      WapBD cdb = new WapBD();
      Map coopInfo = cdb.getCoopInfoByEmpId(String.valueOf(readId));
      request.setAttribute("coop_info", coopInfo.get("coopInfo"));
      request.setAttribute("coop_bug", coopInfo.get("coopInfoBug"));
      request.setAttribute("coop_affix", coopInfo.get("coopInfoAffix"));
    } 
    if (version.equals("3G"))
      return mapping.findForward("readInfo_3g"); 
    if (version.equals("COLOR"))
      return mapping.findForward("readInfo"); 
    return mapping.findForward("readInfo_3g");
  }
  
  public void getWapNewsList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) {
    WapBD cdb = new WapBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String corpId = request.getSession().getAttribute("corpId").toString();
    String info = "100";
    if (SystemCommon.getMultiDepart() == 1) {
      ChannelBD bd = new ChannelBD();
      info = bd.getChannelSimpleInfoByCorpId(corpId, 0)[0][0];
    } 
    Map newsMap = cdb.getNewsList(info, userId, orgId, orgIdString, domainId, beginIndex, WapUtil.LIMITED);
    List showList = (List)newsMap.get("QUERY_LIST");
    int recordCount = ((Integer)newsMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("cardTitle", "内部新闻");
  }
  
  public void getWapNoticeList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) {
    WapBD cdb = new WapBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String corpId = request.getSession().getAttribute("corpId").toString();
    String info = "101";
    if (SystemCommon.getMultiDepart() == 1) {
      ChannelBD bd = new ChannelBD();
      info = bd.getChannelSimpleInfoByCorpId(corpId, 0)[1][0];
    } 
    Map newsMap = cdb.getNewsList(info, userId, orgId, orgIdString, domainId, beginIndex, WapUtil.LIMITED);
    List showList = (List)newsMap.get("QUERY_LIST");
    int recordCount = ((Integer)newsMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("cardTitle", "通知通告");
  }
  
  public void getWapCoopList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) {
    WapBD cdb = new WapBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map coopMap = cdb.getCoopListByEmpId(userId, beginIndex, WapUtil.LIMITED);
    List showList = (List)coopMap.get("QUERY_LIST");
    int recordCount = ((Integer)coopMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("cardTitle", "待办事项");
  }
  
  public void getWapEventList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) {
    WapBD cdb = new WapBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map eventMap = cdb.getEventListByEmpId(userId, domainId, beginIndex, WapUtil.LIMITED);
    List showList = (List)eventMap.get("QUERY_LIST");
    int recordCount = ((Integer)eventMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("cardTitle", "我的日程");
  }
  
  public List getWapProjectList(String userId, String orgId, String orgIdString, String domainId) {
    RelProjectBean bean = new RelProjectBean();
    String para = " select distinct po.id,po.title,po.startTime,po.endTime,po.rate ";
    String from = " from com.js.oa.relproject.po.RelProjectPO po join po.projectActor act ";
    String where = bean.getCurScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
    where = "where  " + where;
    WapBD cdb = new WapBD();
    List projectList = cdb.getProjectListByRangeParam(para, from, where);
    return projectList;
  }
  
  public void getWapMeetList(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    StringBuffer para = new StringBuffer("SELECT boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomApplyPO.applyEmpName,");
    para.append("boardRoomApplyPO.attendeeLeader,boardRoomApplyPO.emceeName,boardRoomPO.name,boardRoomPO.location,");
    para.append("poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum ");
    StringBuffer from = new StringBuffer(" FROM BoardRoomApplyPO boardRoomApplyPO ");
    from.append(" join boardRoomApplyPO.boardroom boardRoomPO ");
    from.append(" join boardRoomApplyPO.meetingTime poo  ");
    StringBuffer where = new StringBuffer(" where (boardRoomApplyPO.emcee like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.attendeeEmpId like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.attendeeLeaderId like '%$" + userId + "$%' ");
    where.append(" or boardRoomApplyPO.nonvotingEmpId like '%$" + userId + "$%') ");
    where.append(" and boardRoomApplyPO.status =0 and poo.meetingDate = '" + destineDate + "' ");
    where.append(" and boardRoomApplyPO.domainId=0 order by poo.meetingDate, poo.startTime desc ");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    WapBD cdb = new WapBD();
    Map meetMap = cdb.getMeetListByRangeParam(para.toString(), from.toString(), where.toString(), beginIndex, WapUtil.LIMITED);
    List showList = (List)meetMap.get("QUERY_LIST");
    int recordCount = ((Integer)meetMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
  }
  
  public ActionForward goReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String cardTitle = "";
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String forword = request.getParameter("forword");
    String type = request.getParameter("type");
    if (type.equals("all"))
      cardTitle = "工作汇报"; 
    if (type.equals("mine"))
      cardTitle = "我的汇报"; 
    if (type.equals("other"))
      cardTitle = "下属汇报"; 
    request.setAttribute("cardTitle", cardTitle);
    int hasUnderEmp = Integer.parseInt(getUnderEmpIdString(userId, false));
    request.setAttribute("hasUnderEmp", Boolean.valueOf((hasUnderEmp > 0)));
    return mapping.findForward(forword);
  }
  
  public ActionForward getReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String conType = request.getParameter("conType");
    String userId = (String)session.getAttribute("userId");
    String orgId = (String)session.getAttribute("orgId");
    String orgIdString = (String)session.getAttribute("orgIdString");
    String type = request.getParameter("type");
    List showList = null;
    String cardTitle = "", forword = "";
    if (type.equals("day")) {
      getWapReportListByDay(request, userId, orgId, orgIdString, "0", false);
      cardTitle = "我的日报";
      forword = "day";
    } 
    if (type.equals("unday")) {
      getWapReportListByDay(request, userId, orgId, orgIdString, "0", true);
      cardTitle = "下属日报";
      forword = "day";
    } 
    if (type.equals("week")) {
      getWapReportListByWeek(request, userId, orgId, orgIdString, "0", false);
      cardTitle = "我的周报";
      forword = "week";
    } 
    if (type.equals("unweek")) {
      getWapReportListByWeek(request, userId, orgId, orgIdString, "0", true);
      cardTitle = "下属周报";
      forword = "week";
    } 
    if (type.equals("month")) {
      getWapReportListByMonth(request, userId, orgId, orgIdString, "0", false);
      cardTitle = "我的月报";
      forword = "month";
    } 
    if (type.equals("unmonth")) {
      getWapReportListByMonth(request, userId, orgId, orgIdString, "0", true);
      cardTitle = "下属月报";
      forword = "month";
    } 
    request.setAttribute("cardTitle", cardTitle);
    return mapping.findForward(forword);
  }
  
  public String getUnderEmpIdString(String userId, boolean flag) {
    WorkLogBD wb = new WorkLogBD();
    List<Object[]> list = wb.getDownEmployeeList(userId);
    String str = "";
    if (flag) {
      StringBuffer idStr = new StringBuffer("");
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          idStr.append(obj[0]).append(",");
        }  
      str = idStr.toString();
      str = str.equals("") ? "-1" : str.substring(0, str.length() - 1);
    } else {
      str = String.valueOf((list == null) ? 0 : list.size());
    } 
    return str;
  }
  
  public void getWapReportListByDay(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId, boolean flag) {
    WapBD cdb = new WapBD();
    String underEmpIdString = "";
    StringBuffer hql = new StringBuffer("SELECT  workLog  FROM com.js.oa.scheme.worklog.po.WorkLogPO workLog  ");
    if (flag) {
      underEmpIdString = getUnderEmpIdString(userId, flag);
      hql.append(" where workLog.createdEmp in(" + underEmpIdString + ")");
    } else {
      hql.append(" where workLog.createdEmp=" + userId);
    } 
    hql.append(" order by workLog.logDate desc,workLog.logId desc ");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map dayReportMap = cdb.getReportByDay(hql.toString(), beginIndex, WapUtil.LIMITED);
    List showList = (List)dayReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)dayReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
  }
  
  public void getWapReportListByWeek(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId, boolean flag) {
    WapBD cdb = new WapBD();
    StringBuffer hql = new StringBuffer("SELECT  po.id,po.previousReport,po.reportType,po.reportTime,po.reportReader,po.templateId,po.hadRead,po.reportCourse,po.sendType,po.reportEmpName ");
    hql.append(" FROM WorkReportPO po ");
    String underEmpIdString = "";
    if (flag) {
      underEmpIdString = getUnderEmpIdString(userId, flag);
      hql.append(" where po.empId in (" + underEmpIdString + ")");
    } else {
      hql.append(" where po.empId=" + userId);
    } 
    hql.append(" and 1=1 and po.reportType=1 and po.reportDomainId=" + domainId + " order by po.reportCourse desc ,po.reportTime desc ");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map weekReportMap = cdb.getReportByWeek(hql.toString(), beginIndex, WapUtil.LIMITED);
    List showList = (List)weekReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)weekReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
  }
  
  public void getWapReportListByMonth(HttpServletRequest request, String userId, String orgId, String orgIdString, String domainId, boolean flag) {
    WapBD cdb = new WapBD();
    String underEmpIdString = "";
    StringBuffer hql = new StringBuffer("SELECT  po.id,po.previousReport,po.reportType,po.reportTime,po.reportReader,po.reportReader,po.templateId,po.hadRead,po.reportCourse,po.sendType,po.reportEmpName  ");
    hql.append(" FROM  com.js.oa.scheme.workreport.po.WorkReportPO po ");
    if (flag) {
      underEmpIdString = getUnderEmpIdString(userId, flag);
      hql.append(" where po.empId in (" + underEmpIdString + ")");
    } else {
      hql.append(" where po.empId=" + userId);
    } 
    hql.append(" and 1=1   and po.reportType=3 and po.reportDomainId=" + domainId + " order by po.reportCourse desc ,po.reportTime desc,po.id desc");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map monthReportMap = cdb.getReportByMonth(hql.toString(), beginIndex, WapUtil.LIMITED);
    List showList = (List)monthReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)monthReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("SHOW_LIST", showList);
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
  }
  
  public ActionForward downLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    String path = request.getParameter("path");
    String name = request.getParameter("name");
    String filename = request.getParameter("FileName");
    String src = "0000";
    if (filename != null && filename.length() > 6 && filename.substring(4, 5).equals("_")) {
      src = filename.substring(0, 4);
    } else {
      src = "0000";
    } 
    if (path.indexOf("./") >= 0 || path.indexOf(".\\") >= 0 || filename.indexOf("./") >= 0 || filename.indexOf(".\\") >= 0) {
      System.out.println("不允许下載upload以外的目录文件！");
      throw new Exception("不允许下載upload以外的目录文件！");
    } 
    String filepath = String.valueOf(request.getRealPath("/upload/")) + src + "/" + path + "/";
    name = new String(name.getBytes("GBK"), "iso-8859-1");
    File file = new File(String.valueOf(filepath) + filename);
    if (file.exists()) {
      response.setContentType("csv");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
      FileInputStream fileInputStream = new FileInputStream(String.valueOf(filepath) + filename);
      int i;
      while ((i = fileInputStream.read()) != -1)
        out.write(i); 
      fileInputStream.close();
      out.close();
    } 
    return null;
  }
}
