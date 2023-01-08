package com.js.oa.portal.action;

import com.js.oa.archives.util.ArchivesUtil;
import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO;
import com.js.oa.hr.subsidiarywork.po.NetSurveyPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import com.js.oa.hr.subsidiarywork.po.ThemeOptionPO;
import com.js.oa.hr.subsidiarywork.service.QuestionnaireBD;
import com.js.oa.info.infomanager.service.InformationAccessoryBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.personalwork.netaddress.bean.AddressEJBBean;
import com.js.oa.personalwork.netemp.po.NetEmpPO;
import com.js.oa.personalwork.netemp.service.NetEmpBD;
import com.js.oa.portal.po.CustomDesktopModuleVO;
import com.js.oa.portal.po.CustomdesktopItemVO;
import com.js.oa.portal.pojo.GwToDo;
import com.js.oa.portal.pojo.Tybg;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.oa.portal.util.GenerateXml;
import com.js.oa.portal.util.JSONAnalyzer;
import com.js.oa.portal.util.RsXMLReader;
import com.js.oa.portal.util.XMLAnalyzer;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.report.service.ReportBD;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.oa.scheme.event.service.EventBD;
import com.js.oa.scheme.event.vo.EventVO;
import com.js.oa.scheme.taskcenter.service.TaskBD;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.workreport.service.WorkReportBD;
import com.js.oa.scheme.workreport.service.WorkReportLeaderBD;
import com.js.oa.tjgzw.bean.TjgzwBean;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.BASE64;
import com.js.util.util.BrowserJudge;
import com.js.util.util.CharacterTool;
import com.js.util.util.MD5;
import com.js.util.util.ParameterFilter;
import com.js.util.util.PicSingleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class CustomDesktopAction extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    resp.setContentType("text/xml; charset=GBK");
    String url = req.getRequestURL().toString();
    url = url.substring(0, url.lastIndexOf("CustomDesktopAction"));
    String type = (req.getParameter("type") == null) ? "" : req.getParameter("type");
    PrintWriter out = resp.getWriter();
    String userId = (session.getAttribute("userId") == null) ? "-1" : session.getAttribute("userId").toString();
    String corpId = (session.getAttribute("corpId") == null) ? "" : session.getAttribute("corpId").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    List<CustomdesktopItemVO> listOut = new ArrayList();
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    PicSingleton picSingleton = PicSingleton.getInstance();
    String urlPath = "/jsoa/portal2/";
    boolean isMobile = BrowserJudge.isMobile(req);
    if (!ParameterFilter.checkParameter(type) || 
      !ParameterFilter.checkParameter(req.getParameter("layoutId")) || 
      !ParameterFilter.isNumber(req.getParameter("channelId")) || 
      !ParameterFilter.checkParameter(req.getParameter("showCount")) || 
      !ParameterFilter.checkParameter(req.getParameter("classId")) || 
      !ParameterFilter.checkParameter(req.getParameter("category")) || 
      !ParameterFilter.checkParameter(req.getParameter("fileType")) || 
      !ParameterFilter.checkParameter(req.getParameter("includeCoop")) || 
      !ParameterFilter.checkParameter(req.getParameter("questionId")) || 
      !ParameterFilter.checkParameter(req.getParameter("openOutside")))
      resp.sendRedirect(String.valueOf(req.getContextPath()) + "/public/jsp/inputerror.jsp"); 
    CustomDesktopModuleVO cmv = new CustomDesktopModuleVO();
    cmv.setTitle("JSOA");
    cmv.setLink("404.html");
    cmv.setDescription("JSOA");
    if (type.equals("LastUpdate")) {
      Map map = null;
      String layoutId = req.getParameter("layoutId");
      String orgId = (session.getAttribute("orgId") == null) ? "" : session.getAttribute("orgId").toString();
      String orgIdString = (session.getAttribute("orgIdString") == null) ? "" : session.getAttribute("orgIdString").toString();
      cmv.setLink("/jsoa/InfoListAction.do?type=all&channelType=0&userChannelName=知识管理&userDefine=0");
      if (SystemCommon.getMultiDepart() == 1) {
        map = customDesktopBD.listLastUpdate(userId, orgId, orgIdString, layoutId, domainId, corpId);
      } else {
        map = customDesktopBD.listLastUpdate(userId, orgId, orgIdString, layoutId, domainId, "");
      } 
      List<Object[]> listInfo = (List)map.get("info");
      boolean displayHref = true;
      String isConfShow = "";
      long currentDate = (new Date()).getTime();
      long infoDate = 0L;
      long scan = 259200000L;
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        String img = "";
        String title = "";
        String readcum = "";
        int channelType = ((Integer)obj[7]).intValue();
        int userDefine = Integer.parseInt(obj[11].toString());
        String channelId = obj[8].toString();
        Object[] objchannel = customDesktopBD.getInformationChannelByChannelId(channelId);
        String checkdepart = "0";
        String userChannelName = objchannel[6].toString();
        if (channelType != 0)
          if (channelType > 0 && 
            userDefine != 1)
            if (userDefine == 0) {
              checkdepart = "1";
              channelType = -1;
            }   
        if ("3".equals(obj[6].toString())) {
          readcum = "(下载" + obj[3] + "次)";
          Map sysMap = SysSetupReader.getInstance().getSysSetupMap(session.getAttribute("domainId").toString());
          String fileServer = session.getAttribute("fileServer").toString();
          int smartInUse = 0;
          if (sysMap != null && sysMap.get("附件上传") != null)
            smartInUse = Integer.parseInt(sysMap.get("附件上传").toString()); 
          String path = (smartInUse == 1) ? "/jsoa" : fileServer;
          cd.setLink(String.valueOf(path) + "/download.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + obj[13].toString() + "&name=" + obj[2].toString() + "&path=information&informationId=" + 
                obj[1].toString()));
          cd.setIstitleread("N");
        } else {
          readcum = "(阅" + obj[3] + "次)";
          cd.setLink("/jsoa/InformationAction.do?action=openInfo&checkdepart=" + checkdepart + "&informationId=" + obj[1] + "&redHead=" + obj[5] + "&informationType=" + obj[6] + 
              "&channelType=" + channelType + "&userChannelName=" + userChannelName);
          cd.setIstitleread("N");
        } 
        Date date = (Date)obj[4];
        infoDate = date.getTime();
        if (currentDate - infoDate < scan)
          img = "<img src='/jsoa/images/new.gif' width='28' height='11'>"; 
        String time = "<font color=\"83939B\">" + dateFormat.format(date) + "</font>";
        String showTitle = "";
        String newtiles = obj[2].toString();
        showTitle = newtiles;
        String tubiao = newtiles.substring(newtiles.lastIndexOf(".") + 1, newtiles.length());
        tubiao = picSingleton.isExist(tubiao.toLowerCase()) ? tubiao : "unknown";
        if ("3".equals(obj[6].toString()))
          title = "<img src=/jsoa/netdisk/filetype/" + tubiao.toLowerCase() + ".gif \\>&nbsp;"; 
        newtiles = getTitleStr(newtiles, SystemCommon.getPortalLong());
        if ("6".equals(obj[6].toString())) {
          String[] fenlei = obj[13].split("_");
          String click = ArchivesUtil.clickImg((fenlei.length == 1) ? "COOP" : fenlei[1], fenlei[0].toString(), "1", (String)obj[0]);
          title = "<a href=\"javascript:" + click + "\">" + newtiles + "</a>";
        } else if (obj[10] != null && obj[10].toString().equals("1")) {
          title = String.valueOf(title) + "<font color=red>" + newtiles + "</font>";
        } else {
          title = String.valueOf(title) + newtiles;
        } 
        cd.setTitle("<div style=\"float:left;width:87%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" title=\"" + showTitle + "\"  class=\"divborder\" ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
            title + readcum + img + 
            "</div><div style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" class=\"divborder\">" + 
            time + isConfShow + 
            "</div> </div>");
        cd.setIsConf(Boolean.valueOf(displayHref).toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyColumnClass")) {
      List<Object[]> listInfo;
      if (SystemCommon.getMultiDepart() == 1) {
        String orgId = session.getAttribute("orgId").toString();
        listInfo = customDesktopBD.listInformationClassByCorp(domainId, userId, orgId, corpId);
      } else {
        listInfo = customDesktopBD.listInformationClass(domainId);
      } 
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        if (obj[2].toString().indexOf("$") < 0) {
          cd.setLink(obj[0].toString());
          cd.setTitle(obj[2].toString());
          listOut.add(cd);
        } 
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyIframeClass")) {
      List<Object[]> listInfo = customDesktopBD.listIframeUrl(domainId, userId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setIsConf(obj[0].toString());
        cd.setTitle(obj[1].toString());
        cd.setLink(obj[2].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyIframe")) {
      String iframeId = req.getParameter("iframeId");
      List<Object[]> listInfo = customDesktopBD.listIframeUrl(domainId, userId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setIsConf(obj[0].toString());
        cd.setTitle(obj[1].toString());
        cd.setLink(obj[2].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
      cmv.setImageNewsTitle(iframeId);
    } else if (type.equals("MyGridReport")) {
      String orgIdString = session.getAttribute("orgIdString").toString();
      List<Object[]> reportList = (new ReportBD()).getReportInfo(userId, orgIdString, domainId, corpId);
      for (int i = 0; i < reportList.size(); i++) {
        Object[] obj = reportList.get(i);
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        cd.setLink((obj[2] == null) ? "" : obj[2].toString());
        cd.setTitle(String.valueOf(obj[3].toString()) + "." + obj[1].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyDeptColumnClass")) {
      List<Object[]> listInfo = customDesktopBD.listInformationDeptClass(domainId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[2].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("LinkSystemClass")) {
      List<Object[]> listInfo = customDesktopBD.listLinkSystemClass(domainId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[1].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyColumn")) {
      String channelId = "0";
      Object[] objchannel = (Object[])null;
      String userChannelName = "";
      if (req.getParameter("channelId") != null) {
        channelId = req.getParameter("channelId");
        objchannel = customDesktopBD.getInformationChannelByChannelId(channelId);
      } 
      String includeChild = "0";
      if (objchannel != null) {
        int channelType = Integer.parseInt(objchannel[3].toString());
        channelId = objchannel[0].toString();
        String channelName = objchannel[1].toString();
        String userDefine = objchannel[4].toString();
        String channelShowType = objchannel[5].toString();
        userChannelName = objchannel[6].toString();
        String orgHasChannel = objchannel[7].toString();
        includeChild = objchannel[8].toString();
        cmv.setTitle(channelName);
        String more = "";
        if (channelType > 0 && userDefine.equals("0")) {
          if (!orgHasChannel.equals("0")) {
            more = "/jsoa/InformationAction.do?channelId=" + channelId + "&channelName=" + channelName + "&channelType=" + channelType + "&userChannelName=" + userChannelName + 
              "&channelShowType=" + channelShowType + "&userDefine=" + userDefine;
            cmv.setDescription("org");
          } 
        } else {
          more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/ChannelMenuAction.do?channelType=" + channelType + "%26userChannelName=" + userChannelName + 
            "&right=/jsoa/InformationAction.do?channelId=" + channelId + "%26channelName=" + channelName + "%26channelType=" + channelType + "%26userChannelName=" + userChannelName + 
            "%26channelShowType=" + channelShowType;
          cmv.setDescription("info");
        } 
        cmv.setLink(more);
      } 
      String orgId = (session.getAttribute("orgId") == null) ? "0" : session.getAttribute("orgId").toString();
      String orgIdString = (session.getAttribute("orgIdString") == null) ? "" : session.getAttribute("orgIdString").toString();
      List<Object[]> listInfo = customDesktopBD.listInformation(channelId, userId, orgId, orgIdString, domainId);
      String imageName = "-1";
      String imageNewsTitle = "-1";
      String imageNewsTitleLink = "-1";
      boolean displayHref = true;
      String isConfShow = "";
      long currentDate = (new Date()).getTime();
      long infoDate = 0L;
      long scan = 259200000L;
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
      String tubiao = "";
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        int channelType = ((Integer)obj[7]).intValue();
        if (imageName.equals("-1")) {
          String informationId = obj[1].toString();
          InformationAccessoryBD accBD = new InformationAccessoryBD();
          List<Object[]> listAcc = accBD.getAccessory(informationId);
          for (int k = 0; k < listAcc.size(); k++) {
            Object[] objAcc = listAcc.get(k);
            if (((Integer)objAcc[4]).intValue() == 1) {
              imageName = objAcc[2].toString();
              Map sysMap = SysSetupReader.getInstance().getSysSetupMap(domainId);
              int smartInUse = 0;
              if (sysMap != null && sysMap.get("附件上传") != null)
                smartInUse = Integer.parseInt(sysMap.get("附件上传").toString()); 
              String srcFive = "0000";
              if (imageName != null && imageName.length() > 6 && imageName.substring(4, 5).equals("_")) {
                srcFive = imageName.substring(0, 4);
              } else {
                srcFive = "0000";
              } 
              String fileServer = session.getAttribute("fileServer").toString();
              imageName = String.valueOf((smartInUse == 1) ? "/jsoa" : fileServer) + "/upload/" + srcFive + "/information/" + imageName;
              imageNewsTitle = obj[2].toString();
              imageNewsTitleLink = "/jsoa/InformationAction.do?action=openInfo&informationId=" + obj[1] + "&redHead=" + obj[5] + "&informationType=" + obj[6] + "&channelType=" + 
                channelType + "&userChannelName=" + userChannelName;
              break;
            } 
          } 
        } 
        String img = "";
        String title = "";
        Date date = (Date)obj[4];
        infoDate = date.getTime();
        Date issueDate = (Date)obj[17];
        long infoIssueDate = issueDate.getTime();
        if (currentDate - infoIssueDate < scan) {
          cd.setIsnewimg("Y");
        } else {
          cd.setIsnewimg("N");
        } 
        String readcum = "";
        if ("3".equals(obj[6].toString())) {
          if (!SystemCommon.getCustomerName().equals("chinaLife"))
            readcum = "(下载" + obj[3] + "次)"; 
          Map sysMap = SysSetupReader.getInstance().getSysSetupMap(session.getAttribute("domainId").toString());
          String fileServer = session.getAttribute("fileServer").toString();
          int smartInUse = 0;
          if (sysMap != null && sysMap.get("附件上传") != null)
            smartInUse = Integer.parseInt(sysMap.get("附件上传").toString()); 
          String path = (smartInUse == 1) ? "/jsoa" : fileServer;
          cd.setLink(String.valueOf(path) + "/download.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + obj[13].toString() + "&name=" + obj[2].toString() + "&path=information&informationId=" + 
                obj[1].toString()));
          String newtiles = obj[2].toString();
          tubiao = newtiles.substring(newtiles.lastIndexOf(".") + 1, newtiles.length());
          tubiao = tubiao.toLowerCase();
          tubiao = picSingleton.isExist(tubiao) ? tubiao : "unknown";
          tubiao = "~_" + tubiao + "_~";
          cd.setIstitleread("Y");
          cd.setReadcount(readcum);
        } else {
          if (!SystemCommon.getCustomerName().equals("chinaLife"))
            readcum = "(阅" + obj[3] + "次)"; 
          if (!"null".equals(obj[13]) && obj[13] != null && !"".equals(obj[13])) {
            String[] fenlei = obj[13].toString().split("_");
            String Id = fenlei[0].toString();
            String fenleiflag = fenlei[1].toString();
            if ("GZLC".equals(fenleiflag)) {
              String[] flowInfo = ArchivesUtil.getFlowInfo(Id);
              if (flowInfo == null || "1".equals(flowInfo[0])) {
                cd.setLink("wu");
              } else {
                cd.setLink("/jsoa/jsflow/workflow_listInfo.jsp?workId=" + Id + "&processStatus=0&workStatus=100&curStatus=" + flowInfo[0] + "&fromDossierData=y");
              } 
            } else if ("COOP".equals(fenleiflag)) {
              String[] coopInfo = ArchivesUtil.getCoopInfo(Id);
              cd.setLink("/jsoa/BodyAction.do?flag=toDealwith&bodyId=" + Id + "&nodeId=" + coopInfo[1] + "&memberId=" + coopInfo[0] + "&status=1001");
            } else {
              cd.setLink("/jsoa/InformationAction.do?action=openInfo&informationId=" + obj[1] + "&redHead=" + obj[5] + "&informationType=" + obj[6] + "&channelType=" + channelType + 
                  "&userChannelName=" + userChannelName);
            } 
          } else {
            cd.setLink("/jsoa/InformationAction.do?action=openInfo&informationId=" + obj[1] + "&redHead=" + obj[5] + "&informationType=" + obj[6] + "&channelType=" + channelType + 
                "&userChannelName=" + userChannelName);
          } 
          cd.setIstitleread("N");
          cd.setReadcount(readcum);
        } 
        String time = dateFormat.format(date);
        if (obj[10] != null && obj[10].toString().equals("1")) {
          title = String.valueOf(title) + obj[2].toString() + "#red#";
        } else {
          title = String.valueOf(title) + obj[2].toString();
        } 
        String isTopString = (obj[14] == null) ? "0" : obj[14].toString();
        if (!"0".equals(isTopString)) {
          SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
          long topEnd = 0L;
          long current = 0L;
          try {
            if (obj[15] != null && !"".equals(obj[15].toString())) {
              Date topEndDate = sdf.parse(obj[15].toString());
              topEnd = topEndDate.getTime();
            } 
          } catch (ParseException e1) {
            e1.printStackTrace();
          } 
          Calendar calendar = Calendar.getInstance();
          try {
            Date dateNow = sdf.parse(sdf.format(calendar.getTime()));
            current = dateNow.getTime();
          } catch (ParseException e) {
            e.printStackTrace();
          } 
          if (current != 0L && current <= topEnd)
            title = "[置顶]" + title; 
        } 
        if ("shensidianzi".equals(SystemCommon.getCustomerName())) {
          cd.setTitle(title);
          cd.setIstitleread("N");
        } else {
          cd.setTitle(String.valueOf(title) + tubiao);
        } 
        tubiao = "";
        cd.setTime(String.valueOf(time) + isConfShow);
        cd.setIsConf(Boolean.valueOf(displayHref).toString());
        listOut.add(cd);
      } 
      cmv.setImageName(imageName);
      cmv.setImageNewsTitle(imageNewsTitle);
      cmv.setImageNewsTitleLink(imageNewsTitleLink);
      cmv.setItemList(listOut);
    } else if (type.equals("LinkSystem")) {
      String channelId = req.getParameter("channelId");
      String addressClassName = "";
      if (channelId != null) {
        addressClassName = customDesktopBD.loadNetAddressClass(channelId);
        cmv.setTitle(addressClassName);
        cmv.setDescription(addressClassName);
        cmv.setLink("#");
      } 
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      AddressEJBBean ab = new AddressEJBBean();
      List<Object[]> addressList = ab.getBox1(userId, orgId, orgIdString, channelId);
      if (addressList != null)
        for (int i = 0; i < addressList.size(); i++) {
          Object[] obj = addressList.get(i);
          String urlNameString = String.valueOf(obj[3]);
          try {
            String urlName = ab.getNetAddressURL(obj[0].toString(), userId);
            if (!"".equals(urlName) && urlName != null)
              urlNameString = urlName; 
          } catch (Exception e) {
            e.printStackTrace();
          } 
          String sysURL = urlNameString;
          String sso = String.valueOf(obj[4]);
          String formaction = String.valueOf(obj[3]);
          String formusername = String.valueOf(obj[6]);
          String formuserpassword = String.valueOf(obj[7]);
          String ssologin = String.valueOf(obj[8]);
          String username = String.valueOf(obj[9]);
          String password = String.valueOf(obj[10]);
          String formelseparam = String.valueOf(obj[12]);
          if ("1".equals(sso)) {
            StringBuffer buffer = new StringBuffer("/jsoa/portal2/linksystemlogin.jsp?");
            buffer.append("jsfaction=").append(formaction).append("&jsfusername=").append(formusername).append("&jsfpassword=").append(formuserpassword).append("&jsfssologin=").append(
                ssologin).append("&systemId=").append(obj[0]).append("&jsfformelseparam=").append(formelseparam);
            sysURL = buffer.toString();
          } 
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          cd.setTitle(obj[1].toString());
          cd.setIsConf(obj[2].toString());
          cd.setLink(sysURL);
          cd.setTime(obj[11].toString());
          listOut.add(cd);
        }  
      String imageName = "-1";
      String imageNewsTitle = "-1";
      String imageNewsTitleLink = "-1";
      cmv.setImageName(imageName);
      cmv.setImageNewsTitle(imageNewsTitle);
      cmv.setImageNewsTitleLink(imageNewsTitleLink);
      cmv.setItemList(listOut);
    } else if ("RsDoc".equals(type)) {
      int showCount;
      String title = "";
      String count = req.getParameter("showCount");
      if (count == null || "undefined".equals(count)) {
        showCount = 7;
      } else {
        showCount = Integer.valueOf(count).intValue();
      } 
      String wsdl = RsXMLReader.getValue("rsgw", "wsdl", "");
      if (wsdl.indexOf("?wsdl") > 0)
        wsdl = wsdl.substring(0, wsdl.indexOf("wsdl") - 1); 
      String getCountMethodName = RsXMLReader.getValue("rsgw", "getCountMethodName", "getTodoCount");
      String getDetailMethodName = RsXMLReader.getValue("rsgw", "getDetailMethodName", "getTodo");
      String detailLinkURL = RsXMLReader.getValue("rsgw", "detailLinkURL", "");
      String logonName = req.getSession().getAttribute("userAccount").toString();
      int todoCount = 0;
      try {
        Call call = (Call)(new Service()).createCall();
        call.setTargetEndpointAddress(wsdl);
        Object obj = call.invoke(getCountMethodName, (Object[])new String[] { logonName, "1" });
        todoCount = Integer.valueOf(obj.toString()).intValue();
      } catch (Exception e) {
        e.printStackTrace();
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        String hhref = "";
        cd.setLink(hhref);
        String time = "";
        title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;服务调用失败！</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + 
          time + 
          "</font></div>";
        cd.setTitle(title);
        listOut.add(cd);
        cmv.setItemList(listOut);
      } 
      if (todoCount > 0) {
        int toShowCount = todoCount;
        if (toShowCount > showCount)
          toShowCount = showCount; 
        try {
          Call call = (Call)(new Service()).createCall();
          call.setTargetEndpointAddress(wsdl);
          Object obj = call.invoke(getDetailMethodName, new Object[] { logonName, "1", Integer.valueOf(toShowCount) });
          String result = obj.toString();
          List<GwToDo> list = XMLAnalyzer.getToDoListForGW(result);
          for (int i = 0; i < list.size(); i++) {
            GwToDo todo = list.get(i);
            CustomdesktopItemVO cd = new CustomdesktopItemVO();
            String hhref = String.valueOf(detailLinkURL) + "?loginName=" + logonName + "&isBase64=true&succeedRedirect=" + todo.getHref();
            cd.setLink(hhref);
            String time = todo.getDate();
            if (time != null && time.length() > 5)
              time = time.substring(0, 5); 
            title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + todo.getTitle() + 
              "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + time + 
              "</font></div>";
            cd.setTitle(title);
            listOut.add(cd);
          } 
          cmv.setItemList(listOut);
        } catch (Exception e) {
          e.printStackTrace();
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          String hhref = "";
          cd.setLink(hhref);
          String time = "";
          title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;服务调用失败！</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + 
            time + 
            "</font></div>";
          cd.setTitle(title);
          listOut.add(cd);
          cmv.setItemList(listOut);
        } 
      } else {
        cmv.setItemList(listOut);
      } 
    } else if ("RsOther".equalsIgnoreCase(type)) {
      String logonName = req.getSession().getAttribute("userAccount").toString();
      String wsdl = "";
      String methodName = "";
      String detailURL = "";
      String taskType = "";
      Object[] paras = { logonName };
      wsdl = RsXMLReader.getValue("bxd", "wsdl", "");
      methodName = RsXMLReader.getValue("bxd", "methodName", "");
      detailURL = RsXMLReader.getValue("bxd", "detailURL", "");
      taskType = RsXMLReader.getValue("bxd", "taskType", "报销单");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("dzd", "wsdl", "");
      methodName = RsXMLReader.getValue("dzd", "methodName", "");
      detailURL = RsXMLReader.getValue("dzd", "detailURL", "");
      taskType = RsXMLReader.getValue("dzd", "taskType", "动支单");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("jzd", "wsdl", "");
      methodName = RsXMLReader.getValue("jzd", "methodName", "");
      detailURL = RsXMLReader.getValue("jzd", "detailURL", "");
      taskType = RsXMLReader.getValue("jzd", "taskType", "借支单");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("zcly", "wsdl", "");
      methodName = RsXMLReader.getValue("zcly", "methodName", "");
      detailURL = RsXMLReader.getValue("zcly", "detailURL", "");
      taskType = RsXMLReader.getValue("zcly", "taskType", "资产领用");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("xtqx", "wsdl", "");
      methodName = RsXMLReader.getValue("xtqx", "methodName", "");
      detailURL = RsXMLReader.getValue("xtqx", "detailURL", "");
      taskType = RsXMLReader.getValue("xtqx", "taskType", "系统权限");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("jx", "wsdl", "");
      methodName = RsXMLReader.getValue("jx", "methodName", "");
      detailURL = RsXMLReader.getValue("jx", "detailURL", "");
      taskType = RsXMLReader.getValue("jx", "taskType", "人力绩效");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      wsdl = RsXMLReader.getValue("ht", "wsdl", "");
      methodName = RsXMLReader.getValue("ht", "methodName", "");
      detailURL = RsXMLReader.getValue("ht", "detailURL", "");
      taskType = RsXMLReader.getValue("ht", "taskType", "合同");
      listOut.add(getTaskCount(wsdl, methodName, detailURL, taskType, paras));
      cmv.setItemList(listOut);
    } else if ("RsInnerTy".equalsIgnoreCase(type)) {
      int showCount;
      String title = "";
      String count = req.getParameter("showCount");
      if (count == null || "undefined".equals(count)) {
        showCount = 7;
      } else {
        showCount = Integer.valueOf(count).intValue();
      } 
      String urlStr = RsXMLReader.getValue("rsty", "inner", "");
      urlStr = String.valueOf(urlStr) + showCount;
      String detailUrl = RsXMLReader.getValue("rsty", "detailLink", "");
      HttpURLConnection httpURLConn = null;
      try {
        String temp = new String();
        URL url_obj = new URL(urlStr);
        httpURLConn = (HttpURLConnection)url_obj.openConnection();
        httpURLConn.setDoOutput(true);
        httpURLConn.setRequestMethod("GET");
        httpURLConn.setIfModifiedSince(999999999L);
        httpURLConn.setRequestProperty("contentType", "UTF-8");
        httpURLConn.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConn.connect();
        InputStream in = httpURLConn.getInputStream();
        BufferedReader bd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String resultStr = "";
        while ((temp = bd.readLine()) != null)
          resultStr = String.valueOf(resultStr) + temp; 
        in.close();
        bd.close();
        List<Tybg> list = JSONAnalyzer.getTybgList(resultStr);
        for (int i = 0; list != null && i < list.size(); i++) {
          Tybg tybg = list.get(i);
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          cd.setLink(String.valueOf(detailUrl) + tybg.getObjid());
          String time = tybg.getWriteTime();
          title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + tybg.getTitle() + 
            "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + time + 
            "</font></div>";
          cd.setTitle(title);
          listOut.add(cd);
        } 
        cmv.setItemList(listOut);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (httpURLConn != null)
          httpURLConn.disconnect(); 
      } 
    } else if ("RsOutterTy".equalsIgnoreCase(type)) {
      int showCount;
      String title = "";
      String count = req.getParameter("showCount");
      if (count == null || "undefined".equals(count)) {
        showCount = 7;
      } else {
        showCount = Integer.valueOf(count).intValue();
      } 
      String urlStr = RsXMLReader.getValue("rsty", "outter", "");
      urlStr = String.valueOf(urlStr) + showCount;
      String detailUrl = RsXMLReader.getValue("rsty", "detailLink", "");
      HttpURLConnection httpURLConn = null;
      try {
        String temp = new String();
        URL url_obj = new URL(urlStr);
        httpURLConn = (HttpURLConnection)url_obj.openConnection();
        httpURLConn.setDoOutput(true);
        httpURLConn.setRequestMethod("GET");
        httpURLConn.setIfModifiedSince(999999999L);
        httpURLConn.setRequestProperty("contentType", "UTF-8");
        httpURLConn.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConn.connect();
        InputStream in = httpURLConn.getInputStream();
        BufferedReader bd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String resultStr = "";
        while ((temp = bd.readLine()) != null)
          resultStr = String.valueOf(resultStr) + temp; 
        in.close();
        bd.close();
        resultStr = resultStr.replaceAll("\"", "'");
        resultStr = new String(resultStr.getBytes("GBK"), "GB2312");
        List<Tybg> list = JSONAnalyzer.getTybgList(resultStr);
        for (int i = 0; list != null && i < list.size(); i++) {
          Tybg tybg = list.get(i);
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          cd.setLink(String.valueOf(detailUrl) + tybg.getObjid());
          String time = tybg.getWriteTime();
          title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + tybg.getTitle() + 
            "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + time + 
            "</font></div>";
          cd.setTitle(title);
          listOut.add(cd);
        } 
        cmv.setItemList(listOut);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (httpURLConn != null)
          httpURLConn.disconnect(); 
      } 
    } else if (type.equals("AllOrgs")) {
      List<Object[]> listInfo = customDesktopBD.listAllOrgs(domainId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[3].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyCalendar")) {
      EventBD eventBD = new EventBD();
      Date date = new Date();
      String day = String.valueOf(date.getDate());
      String month = String.valueOf(date.getMonth() + 1);
      String year = String.valueOf(date.getYear() + 1900);
      String nowdate = "";
      Date time = null;
      if (year != null && !"".equals(year)) {
        nowdate = String.valueOf(year) + "-" + month + "-" + day;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
          time = dateFormat.parse(nowdate);
        } catch (ParseException parseException) {}
      } else {
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowdate = dateFormat.format(nowDate);
        try {
          time = dateFormat.parse(nowdate);
        } catch (ParseException parseException) {}
      } 
      Long newDate = new Long(time.getTime());
      List<EventVO> listInfo = eventBD.getDeskEvent(Long.valueOf(session.getAttribute("userId").toString()), newDate, new Long(domainId), "", "");
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        EventVO obj = listInfo.get(i);
        String eventId = obj.getEventId().toString();
        String newtitle = obj.getEventTitle();
        String link = "";
        String title = "<div class=\"divborder\" style=\"float:left;width:63%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;";
        if (obj.getOnTimeMode().toString().equals("0")) {
          if (obj.getIsShare().toString().equals("true")) {
            title = String.valueOf(title) + newtitle;
            link = String.valueOf(link) + "/jsoa/eventAction.do?action=selectSingleEvent&eventId=" + eventId;
          } else {
            title = String.valueOf(title) + "<font color=\"83939B\">";
            title = String.valueOf(title) + newtitle;
            link = String.valueOf(link) + "/jsoa/eventAction.do?action=selectSingleEvent&eventId=" + eventId;
            title = String.valueOf(title) + "</font>";
          } 
          if (obj.getIsViewed().toString().equals("false"))
            title = String.valueOf(title) + "</b>"; 
        } else {
          if (obj.getIsShare().toString().equals("true")) {
            title = String.valueOf(title) + newtitle;
            link = String.valueOf(link) + "/jsoa/eventAction.do?action=selectSingleEchoEvent&eventId=" + eventId;
          } else {
            title = String.valueOf(title) + "<font color=\"83939B\">";
            title = String.valueOf(title) + newtitle;
            link = String.valueOf(link) + "/jsoa/eventAction.do?action=selectSingleEchoEvent&eventId=" + eventId;
            title = String.valueOf(title) + "</font>";
          } 
          if (obj.getIsViewed().toString().equals("false"))
            title = String.valueOf(title) + "</b>"; 
        } 
        title = String.valueOf(title) + "</div><div class=\"divborder\" style=\"float:left;width:15%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" >";
        if (obj.getEventFullDay().toString().equals("0")) {
          if (obj.getEventBeginDate() == null || obj.getEventBeginDate().equals("")) {
            title = String.valueOf(title) + "<font color=\"83939B\">定期日程</font>";
          } else {
            Date a = new Date(obj.getEventBeginDate().longValue());
            SimpleDateFormat df1 = new SimpleDateFormat("MM-dd");
            title = String.valueOf(title) + "<font color=\"83939B\">" + df1.format(a) + "</font>";
          } 
        } else {
          Date a = new Date(obj.getEventBeginDate().longValue());
          SimpleDateFormat df1 = new SimpleDateFormat("MM-dd");
          title = String.valueOf(title) + "<font color=\"83939B\">" + df1.format(a) + "</font>";
        } 
        title = String.valueOf(title) + "</div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" >";
        if (obj.getIsShare().toString().equals("true")) {
          title = String.valueOf(title) + "<font color=\"83939B\">" + obj.getEventEmpName() + "</font>";
        } else {
          title = String.valueOf(title) + "<font color=\"83939B\">" + req.getSession().getAttribute("userName") + "</font>";
        } 
        title = String.valueOf(title) + "</div>";
        cd.setLink(link);
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyMail")) {
      Map map = customDesktopBD.listMyMail(userId, domainId);
      List<Object[]> listInfo = (List)map.get("myMail");
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        String isConf = (obj[10] == null) ? "0" : obj[10].toString();
        String isConfShow = "";
        boolean displayHref = false;
        if (session.getAttribute("cert") == null) {
          displayHref = true;
        } else {
          if (isConf.equals("1"))
            isConfShow = "<font color='red'>(加密)</font>"; 
          if (isConf.equals("0")) {
            displayHref = true;
          } else if (session.getAttribute("cert").toString().equals("JC_")) {
            displayHref = true;
          } 
        } 
        String img = "<img src=/jsoa/images/dot6.jpg \\>";
        String mailTitle = (obj[4] == null) ? "无主题" : obj[4].toString();
        if (isMobile) {
          if (mailTitle.length() > 15)
            mailTitle = String.valueOf(mailTitle.substring(0, 15)) + "..."; 
        } else if (mailTitle.length() > 25) {
          mailTitle = String.valueOf(mailTitle.substring(0, 23)) + "...";
        } 
        String mailSender = obj[8].toString();
        if (mailSender.indexOf("<") >= 0)
          mailSender = mailSender.substring(mailSender.indexOf("<") + 1); 
        if (mailSender.indexOf("/") >= 0)
          mailSender = mailSender.substring(0, mailSender.indexOf("/")); 
        mailSender = "<font color=\"83939B\">" + mailSender + "</font>";
        if (!"0".equals(obj[1].toString())) {
          mailTitle = "<font color=\"83939B\">" + mailTitle + "</font>";
        } else {
          mailTitle = mailTitle;
        } 
        String time = "<font color=\"83939B\">" + obj[5].toString().substring(5, 11) + "</font>";
        cd.setLink("/jsoa/innerMailAction.do?action=receiveUpdate&mail_id=" + obj[7].toString() + "&showMode=desktop&mailuser_id=" + obj[0].toString() + "&fromdesktop=1");
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:68%;\" border=\"0\" padding=\"0\"  >" + img + "&nbsp;&nbsp;" + mailTitle + "</div>" + 
            "<div class=\"divborder\" style=\"float:left;width:10%;\" border=\"0\" padding=\"0\"   >" + time + isConfShow + 
            "</div> <div class=\"divborder\" style=\"float:left;width:20%;\" border=\"0\" padding=\"0\" align=\"right\" >" + mailSender + "</div>");
        cd.setIsConf(Boolean.valueOf(displayHref).toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("RelProject")) {
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String classId = req.getParameter("classId");
      RelProjectBean bean = new RelProjectBean();
      String para = "select distinct po.id,po.title,po.startTime,po.endTime,po.rate";
      String from = " from com.js.oa.relproject.po.RelProjectPO po join po.projectActor act";
      String where = bean.getCurScopeWhere(userId, orgId, orgIdString, "act.actorId", "act.actorType");
      String category = req.getParameter("category");
      String categorywhere = "";
      if (category != null && !"".equals(category) && !"-1".equals(category))
        categorywhere = " and po.classId=" + category; 
      where = " where " + where + " and po.status=10  " + categorywhere + " order by po.startTime desc ";
      List<Object[]> list = bean.getProjectList(para, from, where);
      String img = "<img src=/jsoa/images/dot6.jpg />";
      for (int i = 0; i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = list.get(i);
        String title = obj[1].toString();
        if (isMobile) {
          if (title.length() > 15)
            title = String.valueOf(title.substring(0, 15)) + "..."; 
        } else if (title.length() > 35) {
          title = String.valueOf(title.substring(0, 33)) + "...";
        } 
        MsManageBD msBD = new MsManageBD();
        String sql = "select p.alarmId,p.alarmDays,p.alarmColor from com.js.oa.relproject.po.ProAlarmSet p where p.alarmEnable='1' order by p.alarmDays desc";
        List<Object[]> msList = null;
        try {
          msList = msBD.getListByYourSQL(sql);
          if (msList != null && msList.size() != 0 && list != null && list.size() > 0) {
            if (obj[3] == null || "".equals(obj[3]))
              continue; 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endDate = sdf.parse(obj[3].toString());
            Date nowDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(nowDate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time1 - time2) / 86400000L;
            String alarmColor = "";
            String alarmDays = "";
            String zhengchang = "";
            for (int m = 0; m < msList.size(); m++) {
              Object[] oj = msList.get(m);
              if (between_days < Long.valueOf(oj[1].toString()).longValue()) {
                alarmColor = oj[2].toString();
                alarmDays = oj[1].toString();
              } else {
                if (m == 0)
                  zhengchang = oj[1].toString(); 
                break;
              } 
            } 
            if (!"".equals(alarmColor))
              title = "<font color='" + alarmColor + "' title='距结束时间小于" + alarmDays + "天'>" + title + "</font>"; 
            if (!"".equals(zhengchang))
              title = "<font color='#000000' title='距结束时间大于" + zhengchang + "天'>" + title + "</font>"; 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd");
        String time = "<font color=\"83939B\">" + timeFormat.format(obj[2]) + " -- " + timeFormat.format(obj[3]) + "</font>";
        cd.setLink("/jsoa/RelProjectAction.do?flag=detail&id=" + obj[0]);
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:78%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  >" + img + "&nbsp;&nbsp;" + 
            title + "</div>" + "<div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"right\"  >" + time + "</div> ");
        listOut.add(cd);
        continue;
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("NetEmp")) {
      NetEmpBD netEmpBD = new NetEmpBD();
      NetEmpPO netEmpPO = new NetEmpPO();
      try {
        netEmpPO = netEmpBD.load(Long.valueOf(req.getSession().getAttribute("userId").toString()).longValue());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String superiorsName = "";
      String superiorsId = "";
      String underlingName = "";
      String underlingId = "";
      String netEmpName = "";
      String netEmpId = "";
      if (netEmpPO == null) {
        UserBD userBD = new UserBD();
        List<Object[]> list = new ArrayList();
        try {
          list = userBD.selectMyUnderling(req.getSession().getAttribute("userId").toString());
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (!list.isEmpty())
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            underlingName = String.valueOf(underlingName) + obj[1] + ",";
            underlingId = String.valueOf(underlingId) + "$" + obj[0] + "$";
          }  
        EmployeeVO employeeVO = new EmployeeVO();
        try {
          employeeVO = userBD.getEmployeeVO(Long.valueOf(userId));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (employeeVO != null) {
          superiorsId = employeeVO.getEmpLeaderId();
          superiorsName = employeeVO.getEmpLeaderName();
        } 
      } else {
        superiorsId = netEmpPO.getSuperiorsId();
        superiorsName = netEmpPO.getSuperiorsName();
        underlingId = netEmpPO.getUnderlingId();
        underlingName = netEmpPO.getUnderlingName();
        netEmpId = netEmpPO.getNetEmpId();
        netEmpName = netEmpPO.getNetEmpName();
      } 
      if (superiorsId != null && !"".equals(superiorsId) && !superiorsId.equals("null")) {
        superiorsId = superiorsId.substring(1, superiorsId.length() - 1);
        String[] superiorsNames = superiorsName.split(",");
        String[] superiorsIds = superiorsId.split("\\$\\$");
        if (superiorsIds.length > 0) {
          String img = "<img src=/jsoa/images/superiors.gif title=\"上级人员\" \\>";
          for (int i = 0; i < superiorsIds.length; i++) {
            CustomdesktopItemVO cd = new CustomdesktopItemVO();
            cd.setLink("/jsoa/personRelation.do?method=relationList&type=1&empId=" + superiorsIds[i]);
            cd.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + superiorsNames[i]);
            listOut.add(cd);
          } 
        } 
      } 
      if (underlingId != null && !"".equals(underlingId) && !underlingId.equals("null")) {
        underlingId = underlingId.substring(1, underlingId.length() - 1);
        String[] underlingNames = underlingName.split(",");
        String[] underlingIds = underlingId.split("\\$\\$");
        if (underlingIds.length > 0) {
          String img = "<img src=/jsoa/images/underling.gif title=\"下级人员\" \\>";
          for (int i = 0; i < underlingIds.length; i++) {
            CustomdesktopItemVO cd = new CustomdesktopItemVO();
            cd.setLink("/jsoa/personRelation.do?method=relationList&type=2&empId=" + underlingIds[i]);
            cd.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + underlingNames[i]);
            listOut.add(cd);
          } 
        } 
      } 
      if (netEmpId != null && !"".equals(netEmpId) && !netEmpId.equals("null")) {
        netEmpId = netEmpId.substring(1, netEmpId.length() - 1);
        String[] netEmpNames = netEmpName.split(",");
        String[] netEmpIds = netEmpId.split("\\$\\$");
        if (netEmpIds.length > 0) {
          String img = "<img src=/jsoa/images/netEmp.gif title=\"相关人员\" \\>";
          for (int i = 0; i < netEmpIds.length; i++) {
            CustomdesktopItemVO cd = new CustomdesktopItemVO();
            cd.setLink("/jsoa/personRelation.do?method=relationList&type=3&empId=" + netEmpIds[i]);
            cd.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + netEmpNames[i]);
            listOut.add(cd);
          } 
        } 
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("OftenFlow")) {
      String img = "<img src=/jsoa/images/dot6.jpg \\>";
      ProcessBD processBD = new ProcessBD();
      List<Object[]> processList = processBD.getUserOftenProcess((String)session.getAttribute("userId"), (String)session.getAttribute("orgIdString"), "1");
      String processName = "";
      for (int i = 0; i < processList.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = processList.get(i);
        processName = CharacterTool.escape(CharacterTool.escape(obj[3].toString()));
        cd.setLink("/jsoa/JsFlowAddAction.do?action=add&processId=" + obj[2] + "&tableId=" + obj[4] + "&processName=" + processName + "&processType=" + obj[5] + "&remindField=" + obj[6] + 
            "&moduleId=1&formType=" + obj[7] + "&jspFile=" + obj[8]);
        cd.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + (String)obj[3]);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("WorkLog")) {
      if ("1".equals(SystemCommon.getReport())) {
        List<Object[]> list = (new WorkReportBD()).getWorkReportPO(session.getAttribute("userId").toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String title = "";
        String time = "";
        for (int i = 0; i < list.size(); i++) {
          title = "";
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          Object[] obj = list.get(i);
          time = "<font color=\"83939B\">" + dateFormat.format(obj[2]) + "</font>";
          title = (obj[1] == null || "".equals(obj[1].toString())) ? (String.valueOf(dateFormat.format(obj[2])) + "日志") : obj[1].toString();
          if (isMobile) {
            if (title.length() > 15)
              title = String.valueOf(title.substring(0, 15)) + "..."; 
          } else if (title.length() > 60) {
            title = String.valueOf(title.substring(0, 60)) + "...";
          } 
          cd.setLink("/jsoa/WorkReportLeaderProductAction.do?action=load&sendRecordId=" + obj[0] + "&status=" + obj[3] + "&pager.offset=1&ribao=1");
          cd.setTitle("<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
              title + 
              "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" >" + 
              time + "</div>");
          listOut.add(cd);
        } 
        cmv.setItemList(listOut);
      } else {
        StringBuffer sb = new StringBuffer(" where 1=1 and workLog.logDomainId=0 and emp.empId=workLog.createdEmp ");
        WorkLogBD worklogBD = new WorkLogBD();
        List<Object[]> list = worklogBD.getDownEmployeeList(userId.toString());
        StringBuffer downEmp = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          downEmp.append(obj[0].toString()).append(",");
        } 
        if (downEmp.toString().indexOf(",") != -1)
          sb.append(" and workLog.createdEmp in (").append(downEmp.toString().substring(0, downEmp.toString().length() - 1)).append(")"); 
        if (list.size() == 0)
          sb.append(" and 1<>1 "); 
        sb.append(" order by workLog.logId desc");
        List<Object[]> workLogList = new ArrayList();
        try {
          workLogList = worklogBD.searchWorkLogForPortal(sb.toString());
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        for (int j = 0; j < workLogList.size(); j++) {
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          WorkLogPO workLogPO = new WorkLogPO();
          Object[] obj = workLogList.get(j);
          workLogPO = (WorkLogPO)obj[0];
          String time = "<font color=\"83939B\">" + dateFormat.format(workLogPO.getLogDate()) + "</font>";
          String title = workLogPO.getLogContent();
          if (isMobile) {
            if (title.length() > 15)
              title = String.valueOf(title.substring(0, 15)) + "..."; 
          } else if (title.length() > 60) {
            title = String.valueOf(title.substring(0, 60)) + "...";
          } 
          cd.setLink("/jsoa/workLogAction.do?action=updateWorkLog&xiashu=1&logId=" + workLogPO.getLogId());
          cd.setTitle("<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
              title + 
              "</div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" >" + 
              time + 
              "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + obj[1] + "</font></div>");
          listOut.add(cd);
        } 
        cmv.setItemList(listOut);
      } 
    } else if (type.equals("MyAffiche")) {
      InformationBD bd = new InformationBD();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      List<Object[]> listInfo = bd.getAfficheList(domainId, userId, orgId, orgIdString);
      InformationBD informationBd = new InformationBD();
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        String isConf = (obj[10] == null) ? "0" : obj[10].toString();
        String isConfShow = "";
        boolean displayHref = false;
        if (session.getAttribute("cert") == null) {
          displayHref = true;
        } else {
          if (isConf.equals("1"))
            isConfShow = "<font color='red'>(加密)</font>"; 
          if (isConf.equals("0")) {
            displayHref = true;
          } else if (session.getAttribute("cert").toString().equals("JC_")) {
            displayHref = true;
          } 
        } 
        String img = "";
        Date date = (Date)obj[5];
        String time = "<font color='83939B'>(" + (date.getMonth() + 1) + "-" + date.getDate() + "阅" + obj[2] + "次)</font>";
        String title = "";
        if (obj[12] != null && obj[12].toString().equals("1")) {
          title = String.valueOf(title) + "&nbsp;&nbsp;<font color=red>" + obj[1] + "</font>";
        } else {
          title = String.valueOf(title) + "&nbsp;&nbsp;" + obj[1].toString();
        } 
        cd.setLink("/jsoa/InformationAction.do?action=openInfo&informationId=" + obj[0] + "&redHead=0&channelName=" + obj[10] + "&informationType=" + obj[8] + "&channelId=" + obj[11] + 
            "&depart=1&channelType=0&orgId=" + orgId + "&userChannelName=信息管理&isAffiche=1");
        cd.setTitle(String.valueOf(title) + time + img + isConfShow);
        if (!informationBd.checkReaded(obj[0].toString(), userId))
          cd.setTitle("<font color=red>" + title + time + img + isConfShow + "</font>"); 
        cd.setIsConf(Boolean.valueOf(displayHref).toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if ("MyForumClass".equals(type)) {
      List<Object[]> listInfo = customDesktopBD.listForumClass(domainId);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[1].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if ("RelProClass".equals(type)) {
      List<Object[]> listProClass = customDesktopBD.listProClass(domainId);
      for (int i = 0; i < listProClass.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listProClass.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[1].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyForum")) {
      String classId = "0";
      ManagerService mbd = new ManagerService();
      ForumClassBD cbd = new ForumClassBD();
      String where = " 1=1";
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String more = "/jsoa/ForumAction.do?action=list%26classId=none";
      if (req.getParameter("classId") != null) {
        classId = req.getParameter("classId");
        if (!"0".equals(classId)) {
          more = "/jsoa/ForumAction.do?action=list%26classId=" + classId;
          where = mbd.getScopeFinalWhere(userId, orgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup", "po.classUserName");
          where = cbd.getClassIdString(userId, "po.forumClass.id", where);
          if (!"".equals(where))
            if (where.startsWith(" or"))
              where = " and (" + where.substring(4) + " or po.forumClass.classUserId is null)";  
          where = " classPO.id=" + classId + where;
        } else {
          where = mbd.getScopeFinalWhere(userId, orgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup", "po.classUserName");
          where = " (1<>1 " + cbd.getClassIdString(userId, "po.forumClass.id", where) + " or po.forumClass.classUserId is null)";
        } 
      } else {
        where = mbd.getScopeFinalWhere(userId, orgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup", "po.classUserName");
        where = " (1<>1 " + cbd.getClassIdString(userId, "po.forumClass.id", where) + " or po.forumClass.classUserId is null)";
      } 
      where = String.valueOf(where) + " and po.domainId=" + domainId;
      Map map = customDesktopBD.listMyForum(where);
      List<Object[]> listInfo = (List)map.get("forum");
      String className = "";
      String time = "";
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        StringBuffer hhref = new StringBuffer();
        hhref.append("/jsoa/ForumAction.do?action=followList");
        hhref.append("&classId=").append(obj[4].toString());
        hhref.append("&mainForumId=").append(obj[0].toString());
        hhref.append("&delRight=0");
        hhref.append("&forumType=").append(obj[2].toString());
        hhref.append("&sign=0");
        hhref.append("&pager.offset1=0");
        className = obj[5].toString();
        time = dateFormat.format(obj[3]);
        String newtitle = obj[1].toString();
        newtitle = getTitleStr(newtitle, SystemCommon.getPortalLong());
        cd.setLink(hhref.toString());
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
            newtitle + 
            "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"left\" ><font color=\"83939B\">" + 
            time + 
            "</font></div><div class=\"divborder\" style=\"float:left;width:20%;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\" border=\"0\" padding=\"0\"  align=\"right\" ><font color=\"83939B\">" + 
            className + "</font></div>");
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
      if ("0".equals(classId)) {
        cmv.setTitle("我的论坛");
      } else {
        ForumClassBD bd = new ForumClassBD();
        ForumClassPO po = bd.load(classId);
        className = po.getClassName();
        cmv.setTitle(className);
      } 
      cmv.setLink(more);
    } else if (type.equals("FileDealList")) {
      String fileType = req.getParameter("fileType");
      String category = req.getParameter("category");
      String includeCoop = req.getParameter("includeCoop");
      List<Object[]> list = (fileType == null) ? new ArrayList() : (new CustomDesktopBD()).listFileDealList(userId, domainId, fileType, category, includeCoop, 20);
      Object[] obj = (Object[])null;
      String title = "";
      String more = "404.html";
      if ("0".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=0&right=/jsoa/FileDealWithAction.do?workStatus=0";
      } else if ("2".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=1&right=/jsoa/FileDealWithAction.do?workStatus=2";
      } else if ("1011".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=2&right=/jsoa/FileDealWithAction.do?workStatus=1011";
      } else if ("1012".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=3&right=/jsoa/FileDealWithAction.do?workStatus=1012";
      } else if ("102".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=4&right=/jsoa/FileDealWithAction.do?workStatus=2";
      } else if ("1".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=5&right=/jsoa/FileDealWithAction.do?workStatus=1";
      } else if ("100".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=6&right=/jsoa/FileDoneWithAction.do";
      } else if ("-1".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=7&right=/jsoa/FileUntreadAction.do?workStatus=-1";
      } else if ("-2".equals(fileType)) {
        more = String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=8&right=/jsoa/FileUntreadAction.do?workStatus=-2";
      } 
      cmv.setLink(more);
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        if (obj[16].toString().indexOf("WorkFlowProcAction") != -1 && "-1".equals(fileType)) {
          StringBuffer hhref = new StringBuffer("/jsoa/WorkFlowReSubmitAction.do?pp=1&moduleId=1&remindField=null&resubmit=1");
          hhref.append("&isSubProcWork=").append(obj[33])
            .append("&pareProcActiId=").append(obj[34])
            .append("&pareStepCount=").append(obj[35])
            .append("&pareTableId=").append(obj[36])
            .append("&pareRecordId=").append(obj[37])
            .append("&work=").append(obj[10])
            .append("&processType=").append(obj[6])
            .append("&workType=").append(obj[6])
            .append("&tableId=").append(obj[8])
            .append("&table=").append(obj[8])
            .append("&record=").append(obj[9])
            .append("&recordId=").append(obj[9])
            .append("&processId=").append(obj[14])
            .append("&processName=").append(obj[0])
            .append("&activity=").append(obj[7])
            .append("&activityName=").append(obj[18])
            .append("&workFileType=").append(obj[0])
            .append("&workStatus=").append(obj[38])
            .append("&submitPerson=").append(obj[11])
            .append("&submitTime=").append(obj[17])
            .append("&editId=").append(obj[10]);
          cd.setLink(hhref.toString());
        } else if ((obj[16].toString().indexOf("GovSendFile") != -1 && "-1".equals(fileType)) || (obj[16].toString().indexOf("/jsoa/WorkFlowReSubmitAction.do?pp=1&moduleId=2&remindField=null") != -1 && "-2".equals(fileType))) {
          StringBuffer hhref = new StringBuffer("/jsoa/GovSendFileAction.do?action=draftModify");
          hhref.append("&sendFileId=").append(obj[9])
            .append("&processId=").append(obj[14])
            .append("&processName=").append(obj[0])
            .append("&processType=2").append("&tableId=")
            .append(obj[8]).append("&remindField=")
            .append("&editId=").append(obj[9])
            .append("&resubmitWorkId=").append(obj[9]);
          cd.setLink(hhref.toString());
        } else if ((obj[16].toString().indexOf("GovReceiveFile") != -1 && "-1".equals(fileType)) || (obj[16].toString().indexOf("/jsoa/WorkFlowReSubmitAction.do?pp=1&moduleId=3&remindField=null") != -1 && "-2".equals(fileType))) {
          TjgzwBean tjBean = new TjgzwBean();
          String receiveFileSendFileUnit = tjBean.getRecfileUnit(obj[9].toString());
          StringBuffer hhref = new StringBuffer("/jsoa/GovReceiveFileAction.do?action=see");
          hhref.append("&processId=").append(obj[14])
            .append("&processName=").append(obj[0])
            .append("&processType=1")
            .append("&tableId=").append(obj[8])
            .append("&editId=").append(obj[9])
            .append("&receiveFileSendFileUnit=").append(receiveFileSendFileUnit)
            .append("&resubmitWorkId=").append(obj[9]);
          cd.setLink(hhref.toString());
        } else {
          StringBuffer hhref = new StringBuffer(obj[16].toString());
          hhref.append("&search=&workTitle=&activityName=").append(obj[18]).append("&submitPersonId=").append(obj[12]).append("&submitPerson=").append(obj[11]).append("&work=").append(obj[10])
            .append("&workType=").append(obj[6]).append("&activity=").append(obj[7]).append("&table=").append(obj[8]).append("&record=").append(obj[9]).append("&processName=").append(
              obj[0]).append("&workStatus=").append(fileType).append("&submitTime=").append(obj[17]).append("&processId=").append(obj[14]).append("&stepCount=").append(obj[15])
            .append("&isStandForWork=").append(obj[20]).append("&standForUserId=").append(obj[21]).append("&standForUserName=").append(obj[22]).append("&initActivity=").append(obj[27])
            .append("&initActivityName=").append(obj[28]).append("&submitPersonTime=").append(obj[5]).append("&tranType=").append(obj[29]).append("&tranFromPersonId=").append(obj[30])
            .append("&fromdesktop=1");
          cd.setLink(hhref.toString());
        } 
        String newtitles = obj[2].toString();
        if (isMobile) {
          if (newtitles.length() > 15)
            newtitles = String.valueOf(newtitles.substring(0, 15)) + "..."; 
        } else if (newtitles.length() > 50) {
          newtitles = String.valueOf(newtitles.substring(0, 50)) + "...";
        } 
        String types = (obj[1] == null) ? "" : obj[1].toString();
        String emergence = "一般", color = "black";
        if ("1".equals(obj[26])) {
          emergence = "加急";
          color = "#ffcc00";
        } else if ("2".equals(obj[26])) {
          emergence = "急件";
          color = "Fuchsia";
        } else if ("3".equals(obj[26])) {
          emergence = "特急";
          color = "brown";
        } else if ("4".equals(obj[26])) {
          emergence = "特提";
          color = "red";
        } 
        title = "<div style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  class=\"divborder\"><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
          newtitles + 
          "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + 
          obj[17].toString().substring(5, 11) + 
          "</font></div><font color=\"83939B\"><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" >" + 
          types + "</font></div>";
        if (fileType.equals("0") || fileType.equals("1011")) {
          String emersty = "";
          if (fileType.equals("0"))
            emersty = "<span style='color:" + color + "'>" + emergence + "</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; 
          String newtiles1 = obj[2].toString();
          if (isMobile) {
            if (newtiles1.length() > 15)
              newtiles1 = String.valueOf(newtiles1.substring(0, 15)) + "..."; 
          } else if (newtiles1.length() > 50) {
            newtiles1 = String.valueOf(newtiles1.substring(0, 50)) + "...";
          } 
          types = (obj[1] == null) ? "" : obj[1].toString();
          String date = new String();
          if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
            date = obj[23].toString().substring(5, 11);
          } else {
            date = obj[17].toString().substring(5, 11);
          } 
          title = "<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
            emersty + 
            newtiles1 + 
            "</div> <div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + 
            date + 
            "</font></div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\"><font color=\"83939B\">" + 
            types + "</font></div> ";
        } 
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("FileDeal")) {
      String fileTitle = SystemCommon.getFileDealWithName();
      Map map = customDesktopBD.listFileDeal(userId, domainId);
      String[] num = new String[9];
      num = (String[])map.get("waitsign");
      String fileType = req.getParameter("fileType");
      CustomdesktopItemVO cd1 = new CustomdesktopItemVO();
      cd1.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=0&right=/jsoa/FileDealWithAction.do?workStatus=0");
      cd1.setTitle("待办" + fileTitle + "<font color='red'>" + num[0] + "</font> 件");
      listOut.add(cd1);
      CustomdesktopItemVO cd2 = new CustomdesktopItemVO();
      cd2.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=1&right=/jsoa/FileDealWithAction.do?workStatus=2");
      cd2.setTitle("待阅" + fileTitle + "<font color='red'>" + num[5] + "</font> 件");
      listOut.add(cd2);
      CustomdesktopItemVO cd3 = new CustomdesktopItemVO();
      cd3.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=2&right=/jsoa/FileDealWithAction.do?workStatus=1011");
      cd3.setTitle("在办" + fileTitle + "<font color='red'>" + num[4] + "</font> 件");
      listOut.add(cd3);
      CustomdesktopItemVO cd4 = new CustomdesktopItemVO();
      cd4.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=3&right=/jsoa/FileDealWithAction.do?workStatus=1012");
      cd4.setTitle("办结" + fileTitle + "<font color='red'>" + num[8] + "</font> 件");
      listOut.add(cd4);
      CustomdesktopItemVO cd5 = new CustomdesktopItemVO();
      cd5.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=4&right=/jsoa/FileDealWithAction.do?workStatus=102");
      cd5.setTitle("已阅" + fileTitle + "<font color='red'>" + num[7] + "</font> 件");
      listOut.add(cd5);
      CustomdesktopItemVO cd6 = new CustomdesktopItemVO();
      cd6.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=5&right=/jsoa/FileDealWithAction.do?workStatus=1");
      cd6.setTitle("我的在办" + fileTitle + "<font color='red'>" + num[1] + "</font> 件");
      listOut.add(cd6);
      CustomdesktopItemVO cd7 = new CustomdesktopItemVO();
      cd7.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=6&right=/jsoa/FileDoneWithAction.do");
      cd7.setTitle("我的办结" + fileTitle + "<font color='red'>" + num[3] + "</font> 件");
      listOut.add(cd7);
      CustomdesktopItemVO cd8 = new CustomdesktopItemVO();
      cd8.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=7&right=/jsoa/FileUntreadAction.do?workStatus=-1");
      cd8.setTitle("我的退回" + fileTitle + "<font color='red'>" + num[2] + "</font> 件");
      listOut.add(cd8);
      CustomdesktopItemVO cd9 = new CustomdesktopItemVO();
      cd9.setLink(String.valueOf(urlPath) + "fileDealFrame.jsp?left=/jsoa/jsflow/item/filedealwith_menu.jsp?type=8&right=/jsoa/FileUntreadAction.do?workStatus=-2");
      cd9.setTitle("我的取消" + fileTitle + "<font color='red'>" + num[6] + "</font> 件");
      listOut.add(cd9);
      cmv.setItemList(listOut);
    } else if (type.equals("Survey")) {
      String orgIdString = session.getAttribute("orgIdString").toString();
      Map map = customDesktopBD.listSurvey(orgIdString, domainId);
      List<NetSurveyPO> listInfo = (List)map.get("survey");
      for (int i = 0; i < listInfo.size(); i++) {
        String title;
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        NetSurveyPO netSurvey = listInfo.get(i);
        if (Byte.toString(netSurvey.getSurveyType()).equals("1")) {
          title = "checkbox";
        } else {
          title = "radio";
        } 
        Iterator<NetSurveyItemPO> it = netSurvey.getSurveyItems().iterator();
        String content = "<form name='frm" + i + "' method='post' action=''>" + "<tr>" + "<input type='hidden' name='netSurveyId" + i + "' value='" + netSurvey.getId() + "'>" + "<td>" + (
          i + 1) + "." + netSurvey.getSurveyContent() + "</td>" + "</tr>" + "<tr>" + "<td><table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        while (it.hasNext()) {
          NetSurveyItemPO netSurveyItem = it.next();
          content = String.valueOf(content) + "<tr><td height='19'><input type='" + title + "' name='surveyItem" + i + "' value='" + netSurveyItem.getId() + "'/>" + netSurveyItem.getItemContent() + 
            "</td>" + "</tr>";
        } 
        content = String.valueOf(content) + "</table></td></tr><BR><tr><td height='15' align='center'><table width='80%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center'><img src='/jsoa/images/tj.jpg' width='40' height='20' border='0' style='cursor:hand'></td><td align='center'><img src='/jsoa/images/ck.jpg' width='75' height='20' border='0' style='cursor:hand'></td></tr></table></td></tr> </form>";
        cd.setLink(String.valueOf(i));
        cd.setTitle(content);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("Question")) {
      QuestionnaireBD qBD = new QuestionnaireBD();
      String channelId = req.getParameter("channelId");
      String questionId = req.getParameter("questionId");
      String openOutside = req.getParameter("openOutside");
      if (channelId == null || "null".equals(channelId)) {
        if ("0".equals(openOutside)) {
          ManagerService mbd = new ManagerService();
          String orgId = session.getAttribute("orgId").toString();
          String orgIdString = session.getAttribute("orgIdString").toString();
          StringBuffer buffer = new StringBuffer();
          orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
          String[] orgIdArray = orgIdString.split("\\$\\$");
          List groupList = null;
          try {
            groupList = customDesktopBD.getGroupById(userId);
          } catch (Exception e) {
            e.printStackTrace();
          } 
          buffer = new StringBuffer("(");
          if (!groupList.isEmpty())
            for (int k = 0; k < groupList.size(); k++)
              buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(k)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(k))
                .append("%' ) or ");  
          for (int i = 0; i < orgIdArray.length; i++) {
            if (!"".equals(orgIdArray[i]))
              buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append(
                  "%' ) or "); 
          } 
          buffer.append(" ( questionnairePO.actorEmp like '%").append(userId).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userId).append("%' )");
          buffer.append(")");
          List<Object[]> listInfo = qBD.answerQuestionnaireList(buffer.toString());
          SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
          for (int j = 0; j < listInfo.size(); j++) {
            CustomdesktopItemVO cd = new CustomdesktopItemVO();
            Object[] obj = listInfo.get(j);
            String begindate = dateFormat.format(obj[2]);
            cd.setLink("/jsoa/QuestionnaireAction.do?action=questionnaireAnswer&questionnaireId=" + obj[0]);
            cd.setTitle("<div class=\"divborder\" style=\"float:left;width:60%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + obj[1].toString() + 
                "</div><div class=\"divborder\" style=\"float:left;width:38%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"right\"  ><font color=\"83939B\">" + begindate + "</font></div>");
            cd.setTime("<font color=\"white\">此处无数据显示</font>");
            listOut.add(cd);
          } 
        } else {
          Map map = qBD.selectQuestionnairePreview(Long.valueOf(Long.parseLong(questionId)));
          QuestionnairePO questionnairePO = (QuestionnairePO)map.get("questionnaire");
          CustomdesktopItemVO cd = new CustomdesktopItemVO();
          String orgIdString = session.getAttribute("orgIdString").toString();
          StringBuffer buffer = new StringBuffer();
          orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
          String[] orgIdArray = orgIdString.split("\\$\\$");
          List groupList = null;
          try {
            groupList = customDesktopBD.getGroupById(userId);
          } catch (Exception e) {
            e.printStackTrace();
          } 
          buffer = new StringBuffer("(");
          if (!groupList.isEmpty())
            for (int j = 0; j < groupList.size(); j++)
              buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(j)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(j))
                .append("%' ) or ");  
          for (int i = 0; i < orgIdArray.length; i++) {
            if (!"".equals(orgIdArray[i]))
              buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append(
                  "%' ) or "); 
          } 
          buffer.append(" ( questionnairePO.actorEmp like '%").append(userId).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userId).append("%' )");
          buffer.append(")and ( questionnairePO.questionnaireId = " + questionId + ")");
          List listInfo = qBD.answerQuestionnaireList(buffer.toString());
          if (!listInfo.isEmpty()) {
            int titleVlue = 0;
            int questionCount = 0;
            float radioScore = 0.0F;
            float checkScore = 0.0F;
            float essayScore = 0.0F;
            int k = 0;
            Long userID = new Long(session.getAttribute("userId").toString());
            String from = "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO";
            String where = "answerSheetPO.ballotEmp ='" + userID + "' and answerSheetPO.questionnaireId =" + Long.parseLong(questionId);
            boolean isRepeatName = qBD.isRepeatName(from, where);
            String message = "";
            if (isRepeatName)
              message = "<font color=\"red\">[已答过]</font>"; 
            List<QuesthemePO> questhemeRadioList = (List)map.get("questhemeRadio");
            String content = " <tr> <td  width=\"100%\" ><table width=\"100%\" border=\"0\"><tr><td width=\"100%\" align=\"center\"><div align=\"center\">" + questionnairePO.getTitle() + 
              message + "</div></td></tr></table></td></tr>" + 
              "<form  name='question' method='post' action='/jsoa/QuestionnaireAction.do?action=addQuestionnaireAnswer1&questionnaireId=" + questionnairePO.getQuestionnaireId() + 
              "' target=\"voteIframe\" >";
            if (questhemeRadioList != null && !"NULL".equals(questhemeRadioList)) {
              content = String.valueOf(content) + "<tr><td valign=\"top\"><input type=\"hidden\" id=\"message11\" value='" + message + "'><table width=\"100%\" border=\"0\">";
              QuesthemePO questheme = null;
              ThemeOptionPO themeOption = null;
              for (int m = 0; m < questhemeRadioList.size(); m++) {
                questionCount++;
                questheme = questhemeRadioList.get(m);
                Set themeOptionSet = questheme.getThemeOption();
                radioScore += Float.parseFloat(questheme.getScore().toString());
                k++;
                content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"questhemeID\" value=\"" + questheme.getQuesthemeId() + 
                  "\">   <table width=\"100%\" border=\"0\">";
                Iterator<ThemeOptionPO> iterator = themeOptionSet.iterator();
                while (iterator.hasNext()) {
                  themeOption = iterator.next();
                  content = String.valueOf(content) + "<tr> <td width=\"5%\"><input type=\"radio\" name=\"Box_" + questheme.getQuesthemeId() + "\" value=\"" + themeOption.getThemeOptionId() + 
                    "\"></td><td width=\"95%\">" + themeOption.getTitle() + "</td> </tr> ";
                } 
                content = String.valueOf(content) + "</table></td> </tr> ";
              } 
              content = String.valueOf(content) + "</table></td> </tr> ";
            } 
            List<QuesthemePO> questhemeCheckList = (List)map.get("questhemeCheck");
            if (questhemeCheckList != null && !"NULL".equals(questhemeCheckList)) {
              content = String.valueOf(content) + "<tr><td valign=\"top\"><table width=\"100%\" border=\"0\">";
              QuesthemePO questheme = null;
              ThemeOptionPO themeOption = null;
              for (int m = 0; m < questhemeCheckList.size(); m++) {
                questionCount++;
                questheme = questhemeCheckList.get(m);
                Set themeOptionSet = questheme.getThemeOption();
                checkScore += Float.parseFloat(questheme.getScore().toString());
                k++;
                content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"questhemeID\" value=\"" + questheme.getQuesthemeId() + 
                  "\">   <table width=\"100%\" border=\"0\">";
                Iterator<ThemeOptionPO> iterator = themeOptionSet.iterator();
                while (iterator.hasNext()) {
                  themeOption = iterator.next();
                  content = String.valueOf(content) + "<tr> <td width=\"5%\"><input type=\"checkbox\" name=\"Box_" + questheme.getQuesthemeId() + "\" value=\"" + themeOption.getThemeOptionId() + 
                    "\"></td><td width=\"95%\">" + themeOption.getTitle() + "</td> </tr> ";
                } 
                content = String.valueOf(content) + "</table></td> </tr> ";
              } 
              content = String.valueOf(content) + "</table></td> </tr> ";
            } 
            List<QuesthemePO> questhemeEssayList = (List)map.get("questhemeEssay");
            if (questhemeEssayList != null && !"NULL".equals(questhemeEssayList)) {
              content = String.valueOf(content) + "<tr><td valign=\"top\"><table width=\"100%\" border=\"0\">";
              QuesthemePO questheme = null;
              for (int m = 0; m < questhemeEssayList.size(); m++) {
                questionCount++;
                questheme = questhemeEssayList.get(m);
                essayScore += Float.parseFloat(questheme.getScore().toString());
                k++;
                content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"essayQuesthemeID\" value=\"" + 
                  questheme.getQuesthemeId() + 
                  "\">   <table width=\"100%\" border=\"0\"><tr><td width=\"100%\"><textarea style=\"width:98%\" rows=\"10\" class=\"inputtextarea\" name=\"Textarea_" + 
                  questheme.getQuesthemeId() + "\"></textarea></td>" + "</tr></table></td></tr>";
              } 
              content = String.valueOf(content) + "</table></td> </tr> ";
            } 
            buffer = new StringBuffer("(");
            if (!groupList.isEmpty())
              for (int m = 0; m < groupList.size(); m++)
                buffer.append(" questionnairePO.actorGroup like '%").append(groupList.get(m)).append("%'  or ");  
            for (int j = 0; j < orgIdArray.length; j++) {
              if (!"".equals(orgIdArray[j]))
                buffer.append(" questionnairePO.actorOrg like '%").append(orgIdArray[j]).append("%' or "); 
            } 
            buffer.append(" questionnairePO.actorEmp like '%").append(userId).append("%'");
            buffer.append(")and ( questionnairePO.questionnaireId = " + questionId + ")");
            List listInfo1 = qBD.answerQuestionnaireList(buffer.toString());
            String submitImg = "&nbsp;&nbsp;";
            if (!listInfo1.isEmpty())
              submitImg = "<img src='/jsoa/images/tj.jpg' width='40' height='20' border='0' onclick=\"questionsubmit()\" style='cursor:hand'>"; 
            Integer dsds = Integer.valueOf(questionCount);
            content = String.valueOf(content) + "<BR><tr><td height='15' align='center'><table width='80%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center'>" + submitImg + "</td>" + 
              "<td align='center'><img src='/jsoa/images/ck.jpg' width='75' height='20' border='0' onclick=\"Statistic()\" style='cursor:hand'></td>" + "</tr>" + "</table></td>" + 
              "</tr> " + "<input type=\"hidden\" id=\"questionId1\" value=\"" + questionnairePO.getQuestionnaireId() + "\">" + 
              "<input type=\"hidden\" id=\"questionName1\" value=\"" + questionnairePO.getTitle() + "\">" + "<input type=\"hidden\" id=\"questionCount11\" value=\"" + 
              dsds.toString() + "\">" + "</form>";
            cd.setLink(String.valueOf(titleVlue));
            cd.setTitle(content);
            listOut.add(cd);
          } else {
            cd.setLink(String.valueOf("zz"));
            cd.setTitle("无权查看问卷调查：" + questionnairePO.getTitle());
            listOut.add(cd);
          } 
        } 
      } else {
        Map map = qBD.selectQuestionnairePreview(Long.valueOf(Long.parseLong(channelId)));
        QuestionnairePO questionnairePO = (QuestionnairePO)map.get("questionnaire");
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        String orgIdString = session.getAttribute("orgIdString").toString();
        StringBuffer buffer = new StringBuffer();
        orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
        String[] orgIdArray = orgIdString.split("\\$\\$");
        List groupList = null;
        try {
          groupList = customDesktopBD.getGroupById(userId);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        buffer = new StringBuffer("(");
        if (!groupList.isEmpty())
          for (int j = 0; j < groupList.size(); j++)
            buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(j)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(j))
              .append("%' ) or ");  
        for (int i = 0; i < orgIdArray.length; i++) {
          if (!"".equals(orgIdArray[i]))
            buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append(
                "%' ) or "); 
        } 
        buffer.append(" ( questionnairePO.actorEmp like '%").append(userId).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userId).append("%' )");
        buffer.append(")and ( questionnairePO.questionnaireId = " + questionId + ")");
        List listInfo = qBD.answerQuestionnaireList(buffer.toString());
        if (!listInfo.isEmpty()) {
          int titleVlue = 0;
          int questionCount = 0;
          float radioScore = 0.0F;
          float checkScore = 0.0F;
          float essayScore = 0.0F;
          int k = 0;
          Long userID = new Long(session.getAttribute("userId").toString());
          String from = "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO";
          String where = "answerSheetPO.ballotEmp ='" + userID + "' and answerSheetPO.questionnaireId =" + Long.parseLong(channelId);
          boolean isRepeatName = qBD.isRepeatName(from, where);
          String message = "";
          if (isRepeatName)
            message = "<font color=\"red\">[已答过]</font>"; 
          List<QuesthemePO> questhemeRadioList = (List)map.get("questhemeRadio");
          String content = " <tr> <td  width=\"100%\" ><table width=\"100%\" border=\"0\"><tr><td width=\"100%\" align=\"center\"><div align=\"center\">" + questionnairePO.getTitle() + 
            message + "</div></td></tr></table></td></tr>" + 
            "<form  name='question' method='post' action='/jsoa/QuestionnaireAction.do?action=addQuestionnaireAnswer1&questionnaireId=" + questionnairePO.getQuestionnaireId() + 
            "' target=\"voteIframe\" >";
          if (questhemeRadioList != null && !"NULL".equals(questhemeRadioList)) {
            content = String.valueOf(content) + "<tr><td valign=\"top\"><table width=\"100%\" border=\"0\">";
            QuesthemePO questheme = null;
            ThemeOptionPO themeOption = null;
            for (int m = 0; m < questhemeRadioList.size(); m++) {
              questionCount++;
              questheme = questhemeRadioList.get(m);
              Set themeOptionSet = questheme.getThemeOption();
              radioScore += Float.parseFloat(questheme.getScore().toString());
              k++;
              content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"questhemeID\" value=\"" + questheme.getQuesthemeId() + 
                "\">   <table width=\"100%\" border=\"0\">";
              Iterator<ThemeOptionPO> iterator = themeOptionSet.iterator();
              while (iterator.hasNext()) {
                themeOption = iterator.next();
                content = String.valueOf(content) + "<tr> <td width=\"5%\"><input type=\"radio\" name=\"Box_" + questheme.getQuesthemeId() + "\" value=\"" + themeOption.getThemeOptionId() + 
                  "\"></td><td width=\"95%\">" + themeOption.getTitle() + "</td> </tr> ";
              } 
              content = String.valueOf(content) + "</table></td> </tr> ";
            } 
            content = String.valueOf(content) + "</table></td> </tr> ";
          } 
          List<QuesthemePO> questhemeCheckList = (List)map.get("questhemeCheck");
          if (questhemeCheckList != null && !"NULL".equals(questhemeCheckList)) {
            content = String.valueOf(content) + "<tr><td valign=\"top\"><table width=\"100%\" border=\"0\">";
            QuesthemePO questheme = null;
            ThemeOptionPO themeOption = null;
            for (int m = 0; m < questhemeCheckList.size(); m++) {
              questionCount++;
              questheme = questhemeCheckList.get(m);
              Set themeOptionSet = questheme.getThemeOption();
              checkScore += Float.parseFloat(questheme.getScore().toString());
              k++;
              content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"questhemeID\" value=\"" + questheme.getQuesthemeId() + 
                "\">   <table width=\"100%\" border=\"0\">";
              Iterator<ThemeOptionPO> iterator = themeOptionSet.iterator();
              while (iterator.hasNext()) {
                themeOption = iterator.next();
                content = String.valueOf(content) + "<tr> <td width=\"5%\"><input type=\"checkbox\" name=\"Box_" + questheme.getQuesthemeId() + "\" value=\"" + themeOption.getThemeOptionId() + 
                  "\"></td><td width=\"95%\">" + themeOption.getTitle() + "</td> </tr> ";
              } 
              content = String.valueOf(content) + "</table></td> </tr> ";
            } 
            content = String.valueOf(content) + "</table></td> </tr> ";
          } 
          List<QuesthemePO> questhemeEssayList = (List)map.get("questhemeEssay");
          if (questhemeEssayList != null && !"NULL".equals(questhemeEssayList)) {
            content = String.valueOf(content) + "<tr><td valign=\"top\"><table width=\"100%\" border=\"0\">";
            QuesthemePO questheme = null;
            for (int m = 0; m < questhemeEssayList.size(); m++) {
              questionCount++;
              questheme = questhemeEssayList.get(m);
              essayScore += Float.parseFloat(questheme.getScore().toString());
              k++;
              content = String.valueOf(content) + " <tr><td width=\"100%\" >&nbsp;" + k + "." + questheme.getTitle() + "<input type=\"hidden\" name=\"essayQuesthemeID\" value=\"" + questheme.getQuesthemeId() + 
                "\">   <table width=\"100%\" border=\"0\"><tr><td width=\"100%\"><textarea style=\"width:98%\" rows=\"10\" class=\"inputtextarea\" name=\"Textarea_" + 
                questheme.getQuesthemeId() + "\"></textarea></td>" + "</tr></table></td></tr>";
            } 
            content = String.valueOf(content) + "</table></td> </tr> ";
          } 
          buffer = new StringBuffer("(");
          if (!groupList.isEmpty())
            for (int m = 0; m < groupList.size(); m++)
              buffer.append(" questionnairePO.actorGroup like '%").append(groupList.get(m)).append("%'  or ");  
          for (int j = 0; j < orgIdArray.length; j++) {
            if (!"".equals(orgIdArray[j]))
              buffer.append(" questionnairePO.actorOrg like '%").append(orgIdArray[j]).append("%' or "); 
          } 
          buffer.append(" questionnairePO.actorEmp like '%").append(userId).append("%'");
          buffer.append(")and ( questionnairePO.questionnaireId = " + questionId + ")");
          List listInfo1 = qBD.answerQuestionnaireList(buffer.toString());
          String submitImg = "&nbsp;&nbsp;";
          if (!listInfo1.isEmpty())
            submitImg = "<img src='/jsoa/images/tj.jpg' width='40' height='20' border='0' onclick=\"questionsubmit()\" style='cursor:hand'>"; 
          content = String.valueOf(content) + "<BR><tr><td height='15' align='center'><table width='80%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center'>" + submitImg + "</td>" + 
            "<td align='center'><img src='/jsoa/images/ck.jpg' width='75' height='20' border='0' style='cursor:hand'></td>" + "</tr>" + "</table></td>" + "</tr> </form>";
          cd.setLink(String.valueOf(titleVlue));
          cd.setTitle(content);
          listOut.add(cd);
        } else {
          cd.setLink(String.valueOf("zz"));
          cd.setTitle("无权查看问卷调查：" + questionnairePO.getTitle());
          listOut.add(cd);
        } 
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("QuestionClass")) {
      ManagerService mbd = new ManagerService();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String questionWhere = mbd.getScopeFinalWhere(userId, orgId, orgIdString, "questionnairePO.actorEmp", "questionnairePO.actorOrg", "questionnairePO.actorGroup", null);
      questionWhere = String.valueOf(questionWhere) + " and questionnairePO.domainId=" + domainId;
      QuestionnaireBD qBD = new QuestionnaireBD();
      List<Object[]> listInfo = qBD.answerQuestionnaireList(questionWhere);
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        cd.setLink(obj[0].toString());
        cd.setTitle(obj[1].toString());
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyScratchpad")) {
      List<Object[]> listInfo = customDesktopBD.listNotePaper(userId, domainId);
      SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        String content = String.valueOf(obj[1]);
        if (content.length() > 30)
          content = String.valueOf(content.substring(0, 30)) + "..."; 
        cd.setLink("/jsoa/NotePaperAction.do?action=modi" + obj[2] + "&showMode=desktop&refreshType=1&editId=" + obj[0] + "&fromdesktop=1");
        String time = sf.format(obj[3]);
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
            content + "</div>");
        cd.setIsConf(obj[0].toString());
        cd.setTime("<div class=\"divborder\"  style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\"   >" + (
            (obj[3] == null || "".equals(obj[3].toString())) ? "&nbsp;" : ("<font color='83939B'>" + time + "</font></div>")));
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MeetingRemind")) {
      BoardRoomBD boardRoomBD = new BoardRoomBD();
      List<Object[]> listInfo = boardRoomBD.getNewMeeting(Long.valueOf(userId));
      for (int i = 0; i < listInfo.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        Object[] obj = listInfo.get(i);
        String beginHours = "";
        String beginMinutes = "";
        String endHours = "";
        String endMinutes = "";
        String date = obj[5].toString();
        date = date.substring(date.indexOf("-") + 1);
        if (date.indexOf(" ") > 0)
          date = date.substring(0, date.indexOf(" ")); 
        beginHours = String.valueOf(Integer.parseInt(obj[6].toString()) / 3600);
        beginMinutes = String.valueOf((Integer.parseInt(obj[6].toString()) - Integer.parseInt(obj[6].toString()) / 3600 * 3600) / 60);
        if (beginHours.length() == 1)
          beginHours = "0" + beginHours; 
        if (beginMinutes.length() == 1)
          beginMinutes = "0" + beginMinutes; 
        endHours = String.valueOf(Integer.parseInt(obj[7].toString()) / 3600);
        endMinutes = String.valueOf((Integer.parseInt(obj[7].toString()) - Integer.parseInt(obj[7].toString()) / 3600 * 3600) / 60);
        if (endHours.length() == 1)
          endHours = "0" + endHours; 
        if (endMinutes.length() == 1)
          endMinutes = "0" + endMinutes; 
        date = String.valueOf(date) + " " + beginHours + ":" + beginMinutes + "-" + endHours + ":" + endMinutes;
        cd.setLink("/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + obj[0] + "&boardroomName=" + obj[2] + "&type=view");
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.setTime((Date)obj[5]);
        tmpCal.set(tmpCal.get(1), tmpCal.get(2), tmpCal.get(5), Integer.parseInt(beginHours), Integer.parseInt(beginMinutes));
        Calendar now = Calendar.getInstance();
        Object object = (obj[1].toString().indexOf("*") >= 0) ? obj[1].toString().replaceAll("\\*", "") : obj[1];
        if (isMobile) {
          if (object.length() > 15)
            object = String.valueOf(object.substring(0, 15)) + "..."; 
        } else if (object.length() > 19) {
          object = String.valueOf(object.substring(0, 17)) + "...";
        } 
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:70%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + object + (
            now.after(tmpCal) ? "(已过期)<font color='gray'>" : "") + 
            "</div><div class=\"divborder\" style=\"float:left;width:28%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\"  ><font color=\"83939B\">" + date + "</font></div>");
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyTask")) {
      List<TaskVO> list = (new TaskBD()).selectPrincipalTask(Long.valueOf(userId), Integer.valueOf("1"), Long.valueOf(domainId), "", "");
      Object[] obj = (Object[])null;
      String title = "";
      SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        TaskVO vo = list.get(i);
        String anpai = vo.getCreatedEmpName();
        Date beginDate = vo.getTaskBeginTime();
        String hhref = "/jsoa/taskAction.do?action=selectSingleTask&taskId=" + vo.getTaskId() + "&isArranged=" + vo.getIsArranged() + "&key=" + (
          new MD5()).toMD5("key-md5" + vo.getTaskId());
        cd.setLink(hhref);
        String taskTitle = vo.getTaskTitle();
        if (isMobile) {
          if (taskTitle.length() > 15)
            taskTitle = String.valueOf(taskTitle.substring(0, 15)) + "..."; 
        } else if (taskTitle.length() > 23) {
          taskTitle = String.valueOf(taskTitle.substring(0, 21)) + "...";
        } 
        title = "<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + taskTitle + 
          "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + sf.format(beginDate) + 
          "</font></div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"right\" ><font color=\"83939B\">" + anpai + "</font></div>";
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyTaskArrange")) {
      List<TaskVO> list = (new TaskBD()).selectSettleTask(Long.valueOf(userId), Integer.valueOf("1"), Long.valueOf(domainId), "", "");
      Object[] obj = (Object[])null;
      String title = "";
      SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        TaskVO vo = list.get(i);
        String fuzhe = vo.getTaskPrincipalName();
        Date beginDate = vo.getTaskBeginTime();
        String hhref = "/jsoa/taskAction.do?action=selectSingleTask&taskId=" + vo.getTaskId() + "&isArranged=" + vo.getIsArranged() + "&key=" + (
          new MD5()).toMD5("key-md5" + vo.getTaskId());
        cd.setLink(hhref);
        String taskTitle = vo.getTaskTitle();
        if (isMobile) {
          if (taskTitle.length() > 15)
            taskTitle = String.valueOf(taskTitle.substring(0, 15)) + "..."; 
        } else if (taskTitle.length() > 23) {
          taskTitle = String.valueOf(taskTitle.substring(0, 21)) + "...";
        } 
        title = "<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + taskTitle + 
          "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + sf.format(beginDate) + 
          "</font></div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"right\" ><font color=\"83939B\">" + fuzhe + "</font></div>";
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyReport")) {
      List<Object[]> list = (new WorkReportLeaderBD()).getTopnNotReadReports(userId, domainId, 20);
      Object[] obj = (Object[])null;
      String title = "";
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        int reportType = Integer.parseInt(obj[3].toString());
        String reportTypeStr = "";
        String hhref = "/jsoa/WorkReportLeaderAction.do?action=load&receiveRecordId=" + obj[0] + "&status=" + obj[1] + "&pager.offset=0&queryText=&queryName=&reportType=none&fromDesktop=yes";
        if (reportType == 1) {
          reportTypeStr = "周报";
          hhref = "/jsoa/WorkReportLeaderAction.do?action=load&receiveRecordId=" + obj[0] + "&status=" + obj[1] + "&pager.offset=0&queryName=&reportType=1&reportEmpId=&reportDepartId=&reportDepart=&selYear=-1&reportJob=";
        } else if (reportType == 3) {
          reportTypeStr = "月报";
          hhref = "/jsoa/WorkReportLeaderAction.do?action=load&receiveRecordId=" + obj[0] + "&status=" + obj[1] + "&pager.offset=0&queryName=&reportType=3&reportEmpId=&reportDepartId=&reportDepart=&selYear=-1&reportJob=";
        } else if ("1".equals(SystemCommon.getReport()) && reportType == 0) {
          reportTypeStr = "日志";
          hhref = "/jsoa/WorkReportLeaderProductAction.do?action=load&receiveRecordId=" + obj[0] + "&status=" + obj[1] + "&pager.offset=0&queryText=&queryName=&reportType=0&ribao=1";
        } else {
          reportTypeStr = "其他";
          hhref = "/jsoa/WorkReportLeaderProductAction.do?action=load&receiveRecordId=" + obj[0] + "&status=" + obj[1] + "&pager.offset=0&queryText=&queryName=&reportType=none";
        } 
        cd.setLink(hhref);
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        time = dateFormat.format(obj[4]);
        if ("1".equals(SystemCommon.getReport())) {
          if (obj[1].equals("0")) {
            title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + obj[2].toString() + 
              "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + time + 
              "</font></div>";
          } else {
            title = "<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;<font color=\"83939B\">" + 
              obj[2].toString() + "</font></div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + time + 
              "</font></div>";
          } 
        } else if (obj[1].equals("0")) {
          title = "<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + obj[2].toString() + 
            "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + time + 
            "</font></div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  align=\"right\" ><font color=\"83939B\">" + reportTypeStr + 
            "</font></div>";
        } else {
          title = "<div class=\"divborder\" style=\"float:left;width:68%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;<font color=\"83939B\">" + 
            obj[2].toString() + "</font></div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"left\" ><font color=\"83939B\">" + time + 
            "</font></div><div class=\"divborder\" style=\"float:left;width:20%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color=\"83939B\">" + reportTypeStr + 
            "</font></div>";
        } 
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("MyReceiveFile")) {
      List<Object[]> list = customDesktopBD.myReceiveFile(userId, domainId, 20);
      Object[] obj = (Object[])null;
      String title = "";
      String processIds = customDesktopBD.getAllSendFileProcessIds();
      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
      String sendFileDate = "";
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        sendFileDate = customDesktopBD.getSendDate(processIds, obj[0].toString());
        if (!"".equals(sendFileDate))
          sendFileDate = sendFileDate.substring(sendFileDate.indexOf("-") + 1); 
        StringBuffer hhref = new StringBuffer("/jsoa/GovReceiveFileBoxAction.do?action=load");
        hhref.append("&tableId=").append(obj[11]);
        hhref.append("&editId=").append(obj[0]);
        hhref.append("&canEdit=1");
        hhref.append("&isEdit=0");
        hhref.append("&viewType=1");
        hhref.append("&pager.offset=0");
        hhref.append("&").append(obj[7]);
        cd.setLink(hhref.toString());
        title = (obj[2] == null) ? "&nbsp;" : obj[2].toString();
        if (isMobile && 
          title.length() > 15)
          title = String.valueOf(title.substring(0, 15)) + "..."; 
        cd.setTitle("<div class=\"divborder\" style=\"float:left;width:88%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\"  ><img src=/jsoa/images/dot6.jpg \\>&nbsp;&nbsp;" + 
            title + 
            "</div><div class=\"divborder\" style=\"float:left;width:10%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;\" border=\"0\" padding=\"0\" align=\"right\" ><font color='83939B'>" + 
            sendFileDate + 
            "</font></div>");
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("FileDealWait")) {
      List<Object[]> list = (new CustomDesktopBD()).listFileDealDT(userId, domainId, "0", 20);
      Object[] obj = (Object[])null;
      String title = "";
      long deadLine = -1L;
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        deadLine = Long.parseLong(obj[3].toString());
        Calendar createDate = getDate(obj[23].toString());
        StringBuffer hhref = new StringBuffer(obj[16].toString());
        hhref.append("&search=&workTitle=&activityName=").append(obj[1]).append("&submitPersonId=").append(obj[12]).append("&submitPerson=").append(obj[11]).append("&work=").append(obj[10])
          .append("&workType=").append(obj[6]).append("&activity=").append(obj[7]).append("&table=").append(obj[8]).append("&record=").append(obj[9]).append("&processName=").append(
            obj[0]).append("&workStatus=0&submitTime=").append(obj[17]).append("&processId=").append(obj[14]).append("&stepCount=").append(obj[15]).append("&isStandForWork=")
          .append(obj[20]).append("&standForUserId=").append(obj[21]).append("&standForUserName=").append(obj[22]);
        cd.setLink(hhref.toString());
        title = String.valueOf(obj[2].toString()) + "(" + (createDate.get(2) + 1) + "-" + createDate.get(5);
        if (deadLine != -1L) {
          title = String.valueOf(title) + " <font color='red'>" + getDeadLine(createDate, deadLine) + "</font>)";
        } else {
          title = String.valueOf(title) + ")";
        } 
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("FileReadWait")) {
      List<Object[]> list = (new CustomDesktopBD()).listFileDealDT(userId, domainId, "2", 20);
      Object[] obj = (Object[])null;
      String title = "";
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        StringBuffer hhref = new StringBuffer(obj[16].toString());
        hhref.append("&search=&workTitle=&activityName=").append(obj[1]).append("&submitPersonId=").append(obj[12]).append("&submitPerson=").append(obj[11]).append("&work=").append(obj[10])
          .append("&workType=").append(obj[6]).append("&activity=").append(obj[7]).append("&table=").append(obj[8]).append("&record=").append(obj[9]).append("&processName=").append(
            obj[0]).append("&workStatus=2&submitTime=").append(obj[17]).append("&processId=").append(obj[14]).append("&stepCount=").append(obj[15]).append("&isStandForWork=")
          .append(obj[20]).append("&standForUserId=").append(obj[21]).append("&standForUserName=").append(obj[22]);
        cd.setLink(hhref.toString());
        title = obj[2].toString();
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } else if (type.equals("FileDealMonitor")) {
      List<Object[]> list = (new CustomDesktopBD()).listFileDealDT(userId, domainId, "1", 20);
      Object[] obj = (Object[])null;
      String title = "";
      for (int i = 0; list != null && i < list.size(); i++) {
        CustomdesktopItemVO cd = new CustomdesktopItemVO();
        obj = list.get(i);
        StringBuffer hhref = new StringBuffer(obj[16].toString());
        hhref.append("&search=&workTitle=&activityName=").append(obj[7]).append("&submitPersonId=").append(obj[12]).append("&submitPerson=").append(obj[11]).append("&work=").append(obj[10])
          .append("&workType=").append(obj[6]).append("&activity=").append(obj[7]).append("&table=").append(obj[8]).append("&record=").append(obj[9]).append("&processName=").append(
            obj[0]).append("&workStatus=1&submitTime=").append(obj[17]).append("&processId=").append(obj[14]).append("&stepCount=").append(obj[15]).append("&isStandForWork=")
          .append(obj[20]).append("&standForUserId=").append(obj[21]).append("&standForUserName=").append(obj[22]);
        cd.setLink(hhref.toString());
        title = String.valueOf(obj[2].toString()) + "(" + obj[1].toString() + ")";
        cd.setTitle(title);
        listOut.add(cd);
      } 
      cmv.setItemList(listOut);
    } 
    try {
      GenerateXml.getOutputStream(cmv, out);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public Calendar getDate(String dateString) {
    Calendar calendar = Calendar.getInstance();
    String year = dateString.substring(0, 4);
    String month = dateString.substring(5, 7);
    String date = dateString.substring(8, 10);
    String hour = dateString.substring(dateString.indexOf(" ") + 1, dateString.indexOf(":"));
    String minute = dateString.substring(dateString.indexOf(":") + 1, dateString.lastIndexOf(":"));
    String second = dateString.substring(dateString.lastIndexOf(":") + 1, dateString.lastIndexOf(":") + 3);
    calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
    return calendar;
  }
  
  public String getDeadLine(Calendar createDate, long time) {
    String deadLine = "";
    boolean overtime = false;
    time = (createDate.getTimeInMillis() + time * 1000L - Calendar.getInstance().getTimeInMillis()) / 1000L;
    if (time < 0L) {
      overtime = true;
      time = 0L - time;
    } 
    long date = time / 86400L;
    time %= 86400L;
    long hour = time / 3600L;
    time /= 3600L;
    long minute = time / 60L;
    if (date > 0L) {
      deadLine = String.valueOf(date) + "天";
      if (hour > 0L)
        deadLine = String.valueOf(deadLine) + hour + "小时"; 
    } else {
      if (hour > 0L)
        deadLine = String.valueOf(deadLine) + hour + "小时"; 
      if (minute > 0L)
        deadLine = String.valueOf(deadLine) + minute + "分"; 
    } 
    if (!"".equals(deadLine))
      if (overtime) {
        deadLine = "超期" + deadLine;
      } else {
        deadLine = "剩" + deadLine;
      }  
    return deadLine;
  }
  
  public String getTitleStr(String title, int length) {
    int num = 0;
    char[] characters = title.toCharArray();
    String returnValue = "";
    int i = 0;
    while (num <= length && num < (title.getBytes()).length) {
      num += ((new StringBuilder(String.valueOf(characters[i]))).toString().getBytes()).length;
      returnValue = String.valueOf(returnValue) + characters[i];
      i++;
    } 
    if ((title.getBytes()).length > length)
      returnValue = String.valueOf(returnValue) + "…"; 
    return returnValue;
  }
  
  public CustomdesktopItemVO getTaskCount(String wsdl, String methodName, String detailURL, String type, Object[] objs) {
    CustomdesktopItemVO task = new CustomdesktopItemVO();
    String img = "<img src=/jsoa/images/dot6.jpg \\>";
    if (wsdl.indexOf("?wsdl") > 0)
      wsdl = wsdl.substring(0, wsdl.indexOf("wsdl") - 1); 
    try {
      Call call = (Call)(new Service()).createCall();
      call.setTargetEndpointAddress(wsdl);
      Object obj = call.invoke(methodName, objs);
      int todoCount = Integer.valueOf(obj.toString()).intValue();
      if (todoCount < 0)
        todoCount = 0; 
      task.setLink(detailURL);
      task.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + type + "&nbsp;(" + todoCount + ")");
      task.setIsConf("0");
    } catch (Exception e) {
      e.printStackTrace();
      task.setLink(detailURL);
      task.setTitle(String.valueOf(img) + "&nbsp;&nbsp;" + type + "&nbsp;(Error)");
      task.setIsConf("0");
    } 
    return task;
  }
}
