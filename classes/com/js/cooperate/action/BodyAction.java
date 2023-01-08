package com.js.cooperate.action;

import com.js.cooperate.bean.BodyBean;
import com.js.cooperate.bean.CooperateBean;
import com.js.cooperate.po.BodyExPO;
import com.js.cooperate.po.BodyPO;
import com.js.cooperate.po.NodeOpinionPO;
import com.js.oa.archives.action.ArchivesAction;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.info.templatemanager.service.TemplateBD;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.search.client.SearchService;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.JSFile;
import com.js.util.util.ParameterFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BodyAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    String operate = request.getParameter("flag");
    if (!ParameterFilter.isNumber(request.getParameter("bodyId")) || 
      !ParameterFilter.isNumber(request.getParameter("nodeId")) || 
      !ParameterFilter.isNumber(request.getParameter("status")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    String tag = "add";
    if ("end".equals(operate)) {
      String bodyId = request.getParameter("bodyId");
      BodyBean bodyBean = new BodyBean();
      String message = "";
      try {
        bodyBean.closeBodyById(bodyId);
        message = "ok";
      } catch (Exception e) {
        message = e.getMessage();
      } 
      response.getWriter().print(message);
      return null;
    } 
    if ("add".equals(operate)) {
      if (SystemCommon.getArchiveToInfo() == 1) {
        List list = (new InfoArchives()).getCanIssueChannel(userId, orgId, orgIdString, domainId);
        request.setAttribute("canIssueChannel", list);
      } 
      request.setAttribute("templateList", getTemplate(request));
    } else {
      if ("gd".equals(operate)) {
        gd(request);
        return null;
      } 
      if ("send".equals(operate)) {
        tag = "send";
        BodyActionForm bodyForm = (BodyActionForm)form;
        int userNum = (SystemCommon.getCooperateUserNum() <= 0) ? 0 : StaticParam.getUserNum(bodyForm.getSendToId());
        if (SystemCommon.getCooperateUserNum() >= 0 && userNum <= SystemCommon.getCooperateUserNum()) {
          BodyPO bpo = new BodyPO();
          BeanUtils.copyProperties(bpo, bodyForm);
          int hasTerm = Integer.parseInt((request.getParameter("hasTerm") != null) ? request.getParameter("hasTerm") : "0");
          bpo.setPosterId(userId);
          bpo.setPosterName(session.getAttribute("userName").toString());
          bpo.setTerm(new Date(request.getParameter("term")));
          bpo.setHasTerm(Integer.valueOf(hasTerm));
          if (request.getParameter("relproject") != null)
            bpo.setRelProjectId(Long.valueOf(request.getParameter("relproject"))); 
          bpo.setPostOrgName(orgName);
          String bodyId = request.getParameter("bodyId");
          if (bodyId != null)
            bpo.setId(Long.valueOf(bodyId)); 
          String nodestr = request.getParameter("selPeoples");
          String[] attachName = request.getParameterValues("attachName");
          String[] attachSaveName = request.getParameterValues("attachSaveName");
          String hisAttarch = request.getParameter("hisAttach");
          if (hisAttarch != null && !"".equals(hisAttarch) && 
            attachSaveName != null) {
            JSFile jsFile = new JSFile();
            for (int i = 0; i < attachSaveName.length; i++)
              jsFile.copyFile("webmail", "cooperate", attachSaveName[i]); 
          } 
          BodyBean bodyBean = new BodyBean();
          String sendSMS = (request.getParameter("sendSMS") == null) ? "1" : request.getParameter("sendSMS");
          boolean res = bodyBean.send(bpo, nodestr, userId, attachName, attachSaveName, 1, Integer.valueOf(sendSMS).intValue());
          if (res) {
            request.setAttribute("operate", "send");
            tag = "close";
          } 
          SearchService.getInstance();
          String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
          SearchService.getInstance();
          String isearchSwitch = SearchService.getiSearchSwitch();
          if ("1".equals(isearchSwitch) && ifActiveUpdateDelete != null && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.addIndex("", "jsf_coordination");
          } 
        } else {
          request.setAttribute("operate", "numMore");
          tag = "close";
        } 
      } else if ("toDealwith".equals(operate)) {
        String printFlag = request.getParameter("printFlag");
        if ("1".equals(printFlag)) {
          tag = "toDealwidthPrint";
        } else {
          tag = "toDealwidth";
        } 
        String bodyId = request.getParameter("bodyId");
        String memberId = request.getParameter("memberId");
        BodyBean bodyBean = new BodyBean();
        boolean test = bodyBean.updateRecord(memberId);
        Map map = bodyBean.getBodyInfo(bodyId, memberId);
        BodyPO po = (BodyPO)map.get("bodyInfo");
        request.setAttribute("bodyInfo", map.get("bodyInfo"));
        request.setAttribute("bodyEx", map.get("bodyEx"));
        request.setAttribute("opinion", map.get("opinion"));
        request.setAttribute("subOpinion", map.get("subOpinion"));
        request.setAttribute("bodyAttach", map.get("bodyAttach"));
        request.setAttribute("bodyExAttach", map.get("bodyExAttach"));
        request.setAttribute("opinionAttach", map.get("opinionAttach"));
        request.setAttribute("dealwithStatus", map.get("dealwithStatus"));
        request.setAttribute("relProjectName", map.get("relProjectName"));
        request.setAttribute("nodeRight", map.get("nodeRight"));
        List<Object[]> list = (new InfoArchives()).getCanIssueChannel(userId, orgId, orgIdString, domainId);
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          if (po.getInfoChannelId().equals(obj[0])) {
            request.setAttribute("infoChannel", obj[2].toString());
            break;
          } 
        } 
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.changeMessageStatus(bodyId, userId, "Cooperate", "a");
        bodyBean.addViewTimeForCooperate(bodyId, userId);
      } else if ("dealwith".equals(operate)) {
        tag = "dealwith";
        String bodyId = request.getParameter("bodyId");
        String memberId = request.getParameter("memberId");
        String content = request.getParameter("opinion");
        String selPersonIds = request.getParameter("selPersonIds");
        String selPersonNames = request.getParameter("selPersonNames");
        String[] attachName = request.getParameterValues("attachName");
        String[] attachSaveName = request.getParameterValues("attachSaveName");
        String tracker = (request.getParameter("tracker") == null) ? "0" : "1";
        request.setAttribute("fromdesktop", request.getParameter("fromdesktop"));
        NodeOpinionPO nopo = new NodeOpinionPO();
        nopo.setBodyId(Long.valueOf(bodyId));
        nopo.setContent(content);
        nopo.setEmpId(Long.valueOf(userId));
        nopo.setEmpName(userName);
        nopo.setSendTime(new Date());
        nopo.setPreId(Long.valueOf(0L));
        if (request.getParameter("isHidden") == null) {
          nopo.setIsHidden(Integer.valueOf(0));
        } else {
          nopo.setIsHidden(Integer.valueOf(1));
        } 
        String nodestr = request.getParameter("selPeoples");
        CooperateBean cbean = new CooperateBean();
        String sendSMS = (request.getParameter("sendSMS") == null) ? "1" : request.getParameter("sendSMS");
        cbean.dealwith(nopo, memberId, tracker, selPersonIds, selPersonNames, attachName, attachSaveName, 
            nodestr, Integer.valueOf(sendSMS).intValue());
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        Connection conn = null;
        Statement stmt = null;
        String workId = "";
        ResultSet rs = null;
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          stmt = conn.createStatement();
          rs = stmt.executeQuery("select wf_work_id from jsf_work where WF_CUREMPLOYEE_ID=" + userId + " and WORKRECORD_ID=" + bodyId);
          while (rs.next())
            workId = rs.getString(1); 
          rs.close();
          stmt.close();
          conn.close();
        } catch (Exception e) {
          if (rs != null)
            try {
              rs.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
            }  
          if (stmt != null)
            try {
              stmt.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
            }  
          if (conn != null)
            try {
              conn.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
            }  
          e.printStackTrace();
        } 
        if ("1".equals(isearchSwitch) && workId != null && ifActiveUpdateDelete != null && !"".equals(workId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.updateIndex(workId.toString(), "jsf_coordination");
        } 
      } else if ("reopinion".equals(operate)) {
        tag = "reopinion";
        String bodyId = request.getParameter("bodyId");
        String content = request.getParameter("opinion");
        String preId = request.getParameter("opId");
        NodeOpinionPO nopo = new NodeOpinionPO();
        nopo.setBodyId(Long.valueOf(bodyId));
        nopo.setContent(content);
        nopo.setEmpId(Long.valueOf(userId));
        nopo.setEmpName(userName);
        nopo.setSendTime(new Date());
        nopo.setPreId(Long.valueOf(preId));
        if (request.getParameter("isHidden") == null) {
          nopo.setIsHidden(Integer.valueOf(0));
        } else {
          nopo.setIsHidden(Integer.valueOf(1));
        } 
        CooperateBean cbean = new CooperateBean();
        if (cbean.saveOpinion(nopo, null, null, null, null, 0, preId, userName, userId) != null)
          request.setAttribute("success", "1"); 
      } else if ("save".equals(operate)) {
        tag = "save";
        BodyActionForm bodyForm = (BodyActionForm)form;
        BodyPO bpo = new BodyPO();
        BeanUtils.copyProperties(bpo, bodyForm);
        bpo.setPosterId(userId);
        bpo.setPosterName(session.getAttribute("userName").toString());
        bpo.setTerm(new Date(request.getParameter("term")));
        if (request.getParameter("relproject") != null)
          bpo.setRelProjectId(Long.valueOf(request.getParameter("relproject"))); 
        bpo.setPostOrgName(orgName);
        String bodyId = request.getParameter("bodyId");
        if (bodyId != null)
          bpo.setId(Long.valueOf(bodyId)); 
        String nodestr = request.getParameter("selPeoples");
        String[] attachName = request.getParameterValues("attachName");
        String[] attachSaveName = request.getParameterValues("attachSaveName");
        String hisAttarch = request.getParameter("hisAttach");
        if (hisAttarch != null && !"".equals(hisAttarch) && 
          attachSaveName != null) {
          JSFile jsFile = new JSFile();
          for (int i = 0; i < attachSaveName.length; i++)
            jsFile.copyFile("webmail", "cooperate", attachSaveName[i]); 
        } 
        BodyBean bodyBean = new BodyBean();
        boolean res = bodyBean.send(bpo, nodestr, userId, attachName, attachSaveName, 0);
        if (res) {
          request.setAttribute("operate", "save");
          tag = "close";
        } 
      } else if ("load".equals(operate)) {
        tag = "load";
        request.setAttribute("templateList", getTemplate(request));
        String bodyId = request.getParameter("bodyId");
        if (SystemCommon.getArchiveToInfo() == 1) {
          List list = (new InfoArchives()).getCanIssueChannel(userId, orgId, orgIdString, domainId);
          request.setAttribute("canIssueChannel", list);
        } 
        BodyBean bodyBean = new BodyBean();
        Map map = bodyBean.getBodyInfoSimple(bodyId);
        if (map != null) {
          request.setAttribute("bodyInfo", map.get("bodyInfo"));
          request.setAttribute("bodyAttach", map.get("bodyAttach"));
          request.setAttribute("nodeList", map.get("nodeList"));
          request.setAttribute("tempalteList", getTemplate(request));
        } 
      } else if ("recordList".equals(operate)) {
        tag = "recordList";
        String bodyId = request.getParameter("bodyId");
        BodyBean bodyBean = new BodyBean();
        request.setAttribute("recordList", bodyBean.getRecordList(bodyId));
      } else if ("appendBody".equals(operate)) {
        tag = "appendBody";
        BodyExPO po = new BodyExPO();
        po.setAppendTime(new Date());
        po.setBodyId(Long.valueOf(request.getParameter("bodyId")));
        po.setContent(request.getParameter("content"));
        po.setEmpId(userId);
        po.setEmpName(userName);
        String[] attachName = request.getParameterValues("attachName");
        String[] attachSaveName = request.getParameterValues("attachSaveName");
        BodyBean bodyBean = new BodyBean();
        boolean res = bodyBean.appendBody(po, attachName, attachSaveName, userId, userName);
        if (res)
          request.setAttribute("success", "1"); 
      } else if ("loadContent".equals(operate)) {
        tag = "loadContent";
        String bodyId = request.getParameter("bodyId");
        BodyBean bodyBean = new BodyBean();
        Map mainBody = bodyBean.getBodyMainInfo(bodyId);
        request.setAttribute("bodyInfo", mainBody.get("bodyInfo"));
        request.setAttribute("bodyAttach", mainBody.get("bodyAttach"));
      } else if ("modifyContent".equals(operate)) {
        tag = "close";
        request.setAttribute("operate", "modifyContent");
        String bodyId = request.getParameter("bodyId");
        String content = request.getParameter("content");
        String[] attachName = request.getParameterValues("attachName");
        String[] attachSaveName = request.getParameterValues("attachSaveName");
        BodyBean bodyBean = new BodyBean();
        String res = bodyBean.modifyContent(bodyId, content, attachName, attachSaveName, userId, userName);
        if ("1".equals(res))
          request.setAttribute("success", "1"); 
      } else if (!"resave".equals(operate)) {
        if ("dealwithturntodo".equals(operate)) {
          tag = "dealwithturntodo";
          String bodyId = request.getParameter("bodyId");
          String memberId = request.getParameter("memberId");
          String content = request.getParameter("opinion");
          String selPersonIds = request.getParameter("selPersonIds");
          String selPersonNames = request.getParameter("selPersonNames");
          String[] attachName = request.getParameterValues("attachName");
          String[] attachSaveName = request.getParameterValues("attachSaveName");
          String tracker = (request.getParameter("tracker") == null) ? "0" : "1";
          String turnToDoNode = request.getParameter("turnToDoNode");
          request.setAttribute("fromdesktop", request.getParameter("fromdesktop"));
          NodeOpinionPO nopo = new NodeOpinionPO();
          nopo.setBodyId(Long.valueOf(bodyId));
          nopo.setContent(content);
          nopo.setEmpId(Long.valueOf(userId));
          nopo.setEmpName(userName);
          nopo.setSendTime(new Date());
          nopo.setPreId(Long.valueOf(0L));
          if (request.getParameter("isHidden") == null) {
            nopo.setIsHidden(Integer.valueOf(0));
          } else {
            nopo.setIsHidden(Integer.valueOf(1));
          } 
          CooperateBean cbean = new CooperateBean();
          String nodestr = request.getParameter("selPeoples");
          String sendSMS = (request.getParameter("sendSMS") == null) ? "1" : request.getParameter("sendSMS");
          cbean.dealwithTurnToDo(nopo, memberId, tracker, selPersonIds, selPersonNames, attachName, attachSaveName, 
              nodestr, Integer.valueOf(sendSMS).intValue(), turnToDoNode);
        } else if ("dealwithpresstodo".equals(operate)) {
          BodyBean bb = new BodyBean();
          bb.sendPressMessge(request);
          return null;
        } 
      } 
    } 
    return mapping.findForward(tag);
  }
  
  public List getTemplate(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String userId = httpSession.getAttribute("userId").toString();
    TemplateBD templateBD = new TemplateBD();
    String where = (new ManagerService()).getScopeFinalWhere(userId, httpSession
        .getAttribute("orgId").toString(), httpSession
        .getAttribute("orgIdString").toString(), "aaa.useEmp", 
        "aaa.useOrg", "aaa.useGroup");
    where = "((aaa.ispublic=0 and aaa.createdEmp=" + userId + ") or (aaa.ispublic=1 and (" + where + ")))";
    List list = templateBD.getAvailableTemplateByUser(where, domainId, "2");
    return list;
  }
  
  public void gd(HttpServletRequest request) {
    String pageContent = request.getParameter("pageContent");
    HttpSession session = request.getSession(true);
    if (pageContent.indexOf("gd();") > 0)
      pageContent = 
        String.valueOf(pageContent.substring(0, pageContent.indexOf("gd();"))) + 
        pageContent.substring(pageContent.indexOf("gd();") + 5, 
          pageContent.length()); 
    pageContent = String.valueOf(pageContent) + "\n";
    pageContent = String.valueOf(pageContent) + "<script language=javascript>Load();</script>";
    Calendar now = Calendar.getInstance();
    String tmp = "COOP_" + request.getParameter("bodyId") + 
      "_" + now.get(1) + (now.get(2) + 1) + 
      now.get(5) + ".html";
    String fileName = String.valueOf(getServlet().getServletContext().getRealPath("/")) + 
      "/archivesfile/" + tmp;
    (new InformationBD()).setDossierStatus(request
        .getParameter("informationId"), "1");
    (new ArchivesAction())
      .addArchivesWaitPigeonhole(
        request.getParameter("coTitle"), 
        tmp, 
        Long.valueOf(request.getParameter("bodyId")), 
        "COOP", 
        session.getAttribute("userName").toString(), 
        new Date(), 
        "1", 
        request, session.getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), 
        "");
    try {
      PrintWriter pw = new PrintWriter(
          new FileOutputStream(fileName));
      pw.println("<html><head><title>协同查看</title>");
      pw.println("<link href=\"/jsoa/skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" /></head>");
      pw.println("<body class=\"MainFrameBox PupwinForFlow\" style=\"overflow-y:hidden;overflow-x:auto;\">");
      pw.println(pageContent.replace("guidang();////", "").replace("id=commandBar", "id=commandBar style='display:none'"));
      pw.println("</body></html>");
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
