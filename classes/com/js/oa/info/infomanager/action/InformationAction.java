package com.js.oa.info.infomanager.action;

import cn.zzy.service.MessageService;
import com.js.oa.archives.action.ArchivesAction;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.archives.util.ArchivesUtil;
import com.js.oa.form.InformationWorkFlow;
import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.channelmanager.service.DepartmentPageBD;
import com.js.oa.info.infomanager.bean.InformationEJBBean;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.infomanager.service.InformationAccessoryBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.oa.info.templatemanager.service.TemplateBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.rws.service.TongBuService;
import com.js.oa.search.client.SearchService;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.util.StaticParam;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SysConfigReader;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import com.js.util.util.ParameterFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InformationAction extends Action {
  private InformationBD informationBD = new InformationBD();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    InformationActionForm informationActionForm = (InformationActionForm)actionForm;
    HttpSession session = request.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String action = request.getParameter("action");
    if ("needCheckUp".equals(action)) {
      String str1 = request.getParameter("channelId");
      ChannelBD channelBD1 = new ChannelBD();
      List<Object[]> list = channelBD1.getSingleChannel(str1);
      Object[] obj = list.get(0);
      String needCheckUp = obj[3].toString();
      String processId = "null";
      String nextStep = "";
      String tagls = "b";
      if (needCheckUp.equals("1"))
        processId = String.valueOf(channelBD1.getChannelProcessId(str1)); 
      if (!processId.equals("null") && !processId.equals("0")) {
        ProcessStep processStep = new ProcessStep();
        nextStep = processStep.firstStep(processId.toString(), 1, request);
        tagls = "a";
      } 
      StringBuffer xml = new StringBuffer(1024);
      httpServletResponse.setContentType("text/xml;charset=GBK");
      PrintWriter out = httpServletResponse.getWriter();
      xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
      xml.append("<result>\n");
      xml.append("  <needCheckUp>" + needCheckUp + "</needCheckUp>\n");
      xml.append("  <isAllow>" + obj[28].toString() + "</isAllow>\n");
      xml.append("  <processId>" + processId + "</processId>\n");
      xml.append("  <tagls>" + tagls + "</tagls>\n");
      xml.append("</result>\n");
      out.print(xml.toString());
      out.close();
      return null;
    } 
    if ("updateClose".equals(action)) {
      update(request, httpServletResponse);
      return actionMapping.findForward("updateClose");
    } 
    String channelType = request.getParameter("channelType");
    String userChannelName = request.getParameter("userChannelName");
    String projectId_ = request.getParameter("projectId_");
    request.setAttribute("projectId_", projectId_);
    request.setAttribute("channelType", channelType);
    request.setAttribute("userChannelName", userChannelName);
    if (!ParameterFilter.isNumber(channelType) || 
      !ParameterFilter.isNumber(request.getParameter("informationType")) || 
      !ParameterFilter.isNumber(request.getParameter("channelId")) || 
      !ParameterFilter.isNumber(request.getParameter("informationId")) || 
      !ParameterFilter.checkParameter(request.getParameter("userDefine")) || 
      !ParameterFilter.checkParameter(request.getParameter("userChannelName")) || 
      !ParameterFilter.checkParameter(request.getParameter("channelShowType")) || 
      !ParameterFilter.checkParameter(request.getParameter("channelName")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    String isAffiche = (request.getParameter("userChannelName") == null) ? "" : 
      request.getParameter("userChannelName").toString();
    String userDefine1 = this.informationBD.getInfoUserdefine(request.getParameter("informationId"));
    String isIso = (request.getParameter("isISO") == null) ? "0" : request.getParameter("isISO");
    String channelStatusType = "0";
    if (isIso.equals("1"))
      channelStatusType = "2"; 
    String channelId = "";
    channelId = request.getParameter("channelId");
    String includeChild = "";
    ChannelBD channelBD = new ChannelBD();
    List<Object[]> lists = new ArrayList();
    lists = channelBD.getSingleChannel(channelId);
    if (lists != null && lists.size() > 0) {
      Object[] obj = (Object[])null;
      obj = lists.get(0);
      includeChild = obj[22].toString();
    } 
    if (action == null)
      action = "view"; 
    String tag = "view";
    if (action.equals("view") || action.equals("commend") || 
      action.equals("transfer") || action.equals("batchDelete") || 
      action.equals("deleteAll") || action.equals("singleDelete"))
      if (includeChild.equals("0")) {
        List<String> list;
        String returnValue = "";
        try {
          returnValue = (new ArchivesBD())
            .archivesPigeonholeSet("ZSGL", session
              .getAttribute("domainId").toString());
        } catch (Exception exception) {}
        if (returnValue.equals(""))
          returnValue = "0,1,0"; 
        Boolean dossier = Boolean.FALSE;
        String isSendMessage = "0";
        if (!returnValue.equals("-1")) {
          String[] tmp = returnValue.split(",");
          if (tmp[0].startsWith("0"))
            dossier = Boolean.TRUE; 
          isSendMessage = tmp[2];
        } 
        request.setAttribute("dossier", dossier);
        request.setAttribute("isSendMessage", isSendMessage);
        if (request.getParameter("channelShowType") != null && 
          request.getParameter("channelShowType").equals("1")) {
          tag = "photoShow";
        } else {
          tag = "view";
        } 
        int listType = 0;
        if (request.getParameter("listType") != null)
          listType = Integer.parseInt(request.getParameter("listType")); 
        if (listType == 1)
          tag = "listType"; 
        request.setAttribute("channelShowType", request.getParameter("channelShowType"));
        String orderBy = request.getParameter("orderBy");
        String sortType = request.getParameter("sortType");
        if (sortType == null)
          sortType = "desc"; 
        HttpSession httpSession = request.getSession(true);
        String userId = httpSession.getAttribute("userId").toString();
        String orgId = httpSession.getAttribute("orgId").toString();
        String channelName = request.getParameter("channelName");
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelName", channelName);
        channelBD = new ChannelBD();
        String userDefine = request.getParameter("userDefine");
        if (SystemCommon.getMultiDepart() == 1) {
          list = channelBD.getUserViewCh(userId, orgId, request.getParameter("channelType"), userDefine, domainId, httpSession.getAttribute("corpId").toString(), httpSession.getAttribute("sidelineCorpId").toString());
        } else {
          list = channelBD.getUserViewCh(userId, orgId, request.getParameter("channelType"), userDefine, domainId, "", "");
        } 
        request.setAttribute("channelList", list);
        boolean canVindicate = channelBD.canVindicate(userId, orgId, channelId);
        if (canVindicate) {
          request.setAttribute("canVindicate", "1");
        } else {
          request.setAttribute("canVindicate", "0");
        } 
        request.setAttribute("channelType", channelType);
        String delNames = "";
        if (action.equals("commend") || action.equals("transfer") || 
          action.equals("batchDelete") || 
          action.equals("delete") || 
          action.equals("deleteAll")) {
          String[] batchId = request
            .getParameterValues("batchId");
          if (action.equals("commend")) {
            this.informationBD.commend(batchId);
          } else if (!action.equals("transfer")) {
            if (action.equals("batchDelete")) {
              if ("tjqnzyxy".equals(SystemCommon.getCustomerName())) {
                String ids = "0";
                for (int i = 0; i < batchId.length; i++)
                  ids = String.valueOf(ids) + "," + batchId[i]; 
                (new MessageService()).deleteMessage(ids);
              } 
              list = this.informationBD.batchDelete(batchId);
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size() - 1; i++)
                  deleteFile.delete("information", list
                      .get(i)); 
                delNames = list.get(list.size() - 1).toString();
              } 
            } else if (action.equals("deleteAll")) {
              delNames = "全部信息";
              list = null;
              String path = request.getRealPath("/");
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size(); i++)
                  deleteFile.delete(String.valueOf(path) + "/upload/information", 
                      list.get(i)); 
              } 
            } 
          } 
        } else if (action.equals("singleDelete")) {
          String id = request.getParameter("id");
          if ("tjqnzyxy".equals(SystemCommon.getCustomerName()))
            (new MessageService()).deleteMessage(id); 
          if (request.getParameter("isISO") != null && 
            request.getParameter("isISO").equals("1")) {
            IsoDocBD bd = new IsoDocBD();
            bd.setInformationStatus(id, "0", "3");
            request.setAttribute("userChannelName", userChannelName);
            String href1 = "/IsoInfoListAction.do?channelId=" + 
              request.getParameter("channelId") + 
              "&channelName=" + channelName + 
              "&channelType=0&userChannelName=" + 
              userChannelName + 
              "&userDefine=0&isoViewType=2";
            ActionForward forward = new ActionForward();
            forward.setPath(href1);
            return forward;
          } 
          list = this.informationBD.singleDelete(channelId, id);
          if (list != null && list.size() > 0) {
            String path = request.getRealPath("/");
            DeleteFile deleteFile = new DeleteFile();
            for (int i = 0; i < list.size() - 1; i++)
              deleteFile.delete(String.valueOf(path) + "/upload/information", 
                  list.get(i)); 
            delNames = list.get(list.size() - 1).toString();
          } 
          if (request.getParameter("isAffiche") != null && 
            request.getParameter("isAffiche")
            .toString().equals("1"))
            tag = "affichedelete"; 
          SearchService.getInstance();
          String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
          SearchService.getInstance();
          String isearchSwitch = SearchService.getiSearchSwitch();
          if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.deleteIndex(id.toString(), "oa_information");
          } 
        } 
        if ((action.equals("batchDelete") || action.equals("delete") || 
          action.equals("deleteAll") || action.equals("singleDelete")) && 
          !"".equals(delNames)) {
          LogBD logBD = new LogBD();
          logBD.log(session.getAttribute("userId").toString(), 
              session.getAttribute("userName").toString(), 
              session.getAttribute("orgName").toString(), 
              "oa_information", "", new Date(), 
              new Date(), "3", delNames, session.getAttribute("userIP").toString(), 
              domainId);
        } 
        request.setAttribute("userChannelName", userChannelName);
        String participationProId = request.getParameter("participationProId");
        request.setAttribute("participationProId", participationProId);
        if (("".equals(participationProId) || "null".equals(participationProId) || participationProId == null) && !"2".equals(channelType)) {
          view(request, channelId, canVindicate, orderBy, sortType, includeChild);
        } else {
          view(request, channelId, canVindicate, orderBy, sortType, includeChild, participationProId);
        } 
      } else if (includeChild.equals("1")) {
        List<String> list;
        String returnValue = "";
        try {
          returnValue = (new ArchivesBD()).archivesPigeonholeSet("ZSGL", session.getAttribute("domainId").toString());
        } catch (Exception exception) {}
        if (returnValue.equals(""))
          returnValue = "0,1,0"; 
        Boolean dossier = Boolean.FALSE;
        String isSendMessage = "0";
        if (!returnValue.equals("-1")) {
          String[] tmp = returnValue.split(",");
          if (tmp[0].startsWith("0"))
            dossier = Boolean.TRUE; 
          isSendMessage = tmp[2];
        } 
        request.setAttribute("dossier", dossier);
        request.setAttribute("isSendMessage", isSendMessage);
        if (request.getParameter("channelShowType") != null && 
          request.getParameter("channelShowType").equals("1")) {
          tag = "photoShow";
        } else {
          tag = "view";
        } 
        int listType = 0;
        if (request.getParameter("listType") != null)
          listType = Integer.parseInt(request.getParameter("listType")); 
        if (listType == 1)
          tag = "listType"; 
        request.setAttribute("channelShowType", request.getParameter("channelShowType"));
        String orderBy = request.getParameter("orderBy");
        String sortType = request.getParameter("sortType");
        if (sortType == null)
          sortType = "desc"; 
        HttpSession httpSession = request.getSession(true);
        String userId = httpSession.getAttribute("userId").toString();
        String orgId = httpSession.getAttribute("orgId").toString();
        String channelName = request.getParameter("channelName");
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelName", channelName);
        channelBD = new ChannelBD();
        String userDefine = request.getParameter("userDefine");
        if (SystemCommon.getMultiDepart() == 1) {
          list = channelBD.getUserViewCh(userId, orgId, request.getParameter("channelType"), userDefine, domainId, httpSession.getAttribute("corpId").toString(), httpSession.getAttribute("sidelineCorpId").toString());
        } else {
          list = channelBD.getUserViewCh(userId, orgId, request.getParameter("channelType"), userDefine, domainId, "", "");
        } 
        request.setAttribute("channelList", list);
        boolean canVindicate = channelBD.canVindicate(userId, orgId, channelId);
        if (canVindicate) {
          request.setAttribute("canVindicate", "1");
        } else {
          request.setAttribute("canVindicate", "0");
        } 
        String delNames = "";
        request.setAttribute("channelType", channelType);
        if (action.equals("commend") || action.equals("transfer") || 
          action.equals("batchDelete") || 
          action.equals("delete") || 
          action.equals("deleteAll")) {
          String[] batchId = request.getParameterValues("batchId");
          if (action.equals("commend")) {
            this.informationBD.commend(batchId);
          } else if (!action.equals("transfer")) {
            if (action.equals("batchDelete")) {
              list = this.informationBD.batchDelete(batchId);
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size() - 1; i++)
                  deleteFile.delete("information", list
                      .get(i)); 
                delNames = list.get(list.size() - 1).toString();
              } 
            } else if (action.equals("deleteAll")) {
              list = this.informationBD.allDelete(channelId);
              String path = request.getRealPath("/");
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size(); i++)
                  deleteFile.delete(String.valueOf(path) + "/upload/information", 
                      list.get(i)); 
              } 
              delNames = "全部信息";
            } 
          } 
        } else if (action.equals("singleDelete")) {
          String id = request.getParameter("id");
          list = this.informationBD.singleDelete(channelId, id);
          if (list != null && list.size() > 0) {
            String path = request.getRealPath("/");
            DeleteFile deleteFile = new DeleteFile();
            for (int i = 0; i < list.size() - 1; i++)
              deleteFile.delete(String.valueOf(path) + "/upload/information", 
                  list.get(i)); 
            delNames = list.get(list.size() - 1).toString();
          } 
          if (request.getParameter("isAffiche") != null && 
            request.getParameter("isAffiche").toString().equals("1"))
            tag = "affichedelete"; 
          if (request.getParameter("isISO") != null && 
            request.getParameter("isISO").equals("1")) {
            request.setAttribute("userChannelName", userChannelName);
            String href1 = "/IsoInfoListAction.do?channelId=" + 
              request.getParameter("channelId") + 
              "&channelName=" + channelName + 
              "&channelType=0&userChannelName=" + 
              userChannelName + "&userDefine=0";
            ActionForward forward = new ActionForward();
            forward.setPath(href1);
            return forward;
          } 
        } 
        if ((action.equals("batchDelete") || action.equals("delete") || 
          action.equals("deleteAll") || action.equals("singleDelete")) && 
          !"".equals(delNames)) {
          LogBD logBD = new LogBD();
          logBD.log(session.getAttribute("userId").toString(), 
              session.getAttribute("userName").toString(), 
              session.getAttribute("orgName").toString(), 
              "oa_information", "", new Date(), 
              new Date(), "3", delNames, session.getAttribute("userIP").toString(), 
              domainId);
        } 
        request.setAttribute("userChannelName", userChannelName);
        String participationProId = request.getParameter("participationProId");
        request.setAttribute("participationProId", participationProId);
        if (("".equals(participationProId) || "null".equals(participationProId) || participationProId == null) && !channelType.equals("2")) {
          view(request, channelId, canVindicate, orderBy, sortType, includeChild);
        } else {
          view(request, channelId, canVindicate, orderBy, sortType, includeChild, participationProId);
        } 
      } else {
        request.setAttribute("channelId", "-1");
        request.setAttribute("recordCount", "0");
        request.setAttribute("maxPageItems", "0");
        request.setAttribute("channelList", new ArrayList());
        request.setAttribute("informationList", new ArrayList());
      }  
    if (action.equals("continue") || action.equals("close") || (request.getParameter("flag") != null && request.getParameter("flag").equals("close"))) {
      InformationPO informationPO = null;
      request.setAttribute("channelType", channelType);
      request.setAttribute("channelId", (new StringBuilder(String.valueOf(informationActionForm.getChannelId()))).toString());
      request.setAttribute("channelName", informationActionForm.getChannelName());
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      String isAllow = (request.getParameter("isAllow") == null) ? "0" : request.getParameter("isAllow");
      String[] para = { userId, userName, orgId, orgName, orgIdString };
      Long infoId = null;
      if (request.getParameter("activityId") != null) {
        String operProcUser;
        informationPO = setPO(informationActionForm, request);
        informationPO.setInformationStatus(1);
        informationPO.setIsAllow(isAllow);
        informationPO.setOrderCodeTemp(Integer.valueOf(0));
        infoId = this.informationBD.add(informationPO, 
            para, 
            request.getParameterValues("associateInfo"), 
            request.getParameterValues("infoPicName"), 
            request.getParameterValues("infoPicSaveName"), 
            request.getParameterValues("infoAppendName"), 
            request.getParameterValues("infoAppendSaveName"), 
            domainId, corpId);
        WorkVO workVO = new WorkVO();
        workVO.setWorkType(1);
        workVO.setSubmitPerson(httpSession.getAttribute("userName").toString());
        workVO.setSubmitEmployeeId(new Long(httpSession.getAttribute("userId").toString()));
        workVO.setRemindValue("");
        String channelName = request.getParameter("channelName");
        workVO.setFileType(String.valueOf(channelName) + "发布");
        workVO.setProcessId((new ChannelBD()).getChannelProcessId(request.getParameter("channelId")));
        workVO.setRecordId(infoId);
        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setFormType(0);
        moduleVO.setId(4);
        if (informationPO.getInformationOrISODoc().equals("1"))
          moduleVO.setId(50); 
        if (informationPO.getAfficeHistoryDate() != null && 
          !informationPO.getAfficeHistoryDate().toString().equals(""))
          moduleVO.setId(51); 
        List<AccessTableVO> list = (new WorkFlowBD()).getAccessTable(moduleVO);
        AccessTableVO tableVO = list.get(0);
        workVO.setTableId(new Long(tableVO.getId()));
        workVO.setToMainLinkFile("/jsoa/InfoProcAction.do?channelType=" + 
            channelType + "&informationType=" + 
            informationPO.getInformationType() + "&redhead=" + 
            informationPO.getInformationHead());
        workVO
          .setSelfMainLinkFile("/jsoa/InfoProcAction.do?channelType=" + 
            channelType + 
            "&informationType=" + 
            informationPO.getInformationType() + 
            "&redhead=" + 
            informationPO.getInformationHead());
        workVO.setActivity(new Long(request.getParameter("activityId")));
        workVO.setCurStep(request.getParameter("activityName"));
        workVO.setAllowStandFor(Integer.parseInt(request.getParameter("allowStandFor")));
        workVO.setPressType(Integer.parseInt(request.getParameter("pressType")));
        workVO.setDeadLine(request.getParameter("deadLineTime"));
        workVO.setPressTime(request.getParameter("pressTime"));
        String usertype1 = request.getParameter("userType");
        int usertype2 = 0;
        if (usertype1 == null)
          usertype2 = 0; 
        workVO.setUserType(usertype2);
        workVO.setSubmitOrg(httpSession.getAttribute("orgName").toString());
        String[] toUser = { "" };
        switch (usertype2) {
          case 0:
            toUser = request.getParameterValues("candidateUser");
            break;
          case 1:
            operProcUser = request
              .getParameter("operProcUser");
            if (operProcUser != null && !operProcUser.equals("")) {
              operProcUser = operProcUser.substring(1, operProcUser
                  .length() - 1);
              if (operProcUser.indexOf("$$") >= 0) {
                toUser = operProcUser.split("\\$\\$");
                break;
              } 
              toUser[0] = operProcUser;
            } 
            break;
          case 2:
            toUser = request.getParameterValues("candidateUser");
            break;
          case 3:
            toUser = request.getParameterValues("allUser");
            break;
          case 5:
            toUser[0] = userId;
            break;
          case 6:
            toUser = (new WorkFlowBD()).getRoleUser(request.getParameter("partRole"), userId);
            break;
        } 
        workVO.setPressType(Integer.parseInt(request.getParameter("pressType")));
        int pressType = Integer.parseInt(request.getParameter("pressType"));
        switch (pressType) {
          case 0:
            workVO.setDeadLine("-1");
            workVO.setPressTime("-1");
            break;
          case 1:
            workVO.setDeadLine(request
                .getParameter("deadLineTime"));
            workVO.setPressTime(request
                .getParameter("pressMotionTime"));
            break;
        } 
        if (request.getParameter("needPassRound") != null) {
          String passRoundUser;
          workVO.setNeedPassRound(true);
          int passRoundUserType = Integer.parseInt(request.getParameter("passRoundUserType"));
          workVO.setPassRoundUserType(passRoundUserType);
          String[] passUser = { "" };
          switch (passRoundUserType) {
            case 1:
              passRoundUser = request.getParameter("passRoundUser");
              if (passRoundUser != null && !passRoundUser.equals("")) {
                passRoundUser = passRoundUser.substring(1, passRoundUser.length() - 1);
                if (passRoundUser.indexOf("$$") >= 0) {
                  passUser = passRoundUser.split("\\$\\$");
                  break;
                } 
                passUser[0] = passRoundUser;
              } 
              break;
            case 2:
              passUser = request.getParameterValues("passRoundCandUser");
              break;
            case 3:
              passUser = request.getParameterValues("passRoundAllUser");
              break;
            case 5:
              passUser[0] = userId;
              break;
            case 6:
              passUser = (new WorkFlowBD()).getRoleUser(request.getParameter("passRole"), userId);
              break;
          } 
          workVO.setPassRoundUser(passUser);
        } else {
          workVO.setNeedPassRound(false);
        } 
        ProcessSubmit procSubmit = new ProcessSubmit();
        workVO
          .setCreatorCancelLink("window.open('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=" + 
            tableVO.getId() + 
            "&processName=" + 
            channelName + 
            "发布" + 
            "&recordId=" + 
            infoId + 
            "&processId=" + (
            new ChannelBD()).getChannelProcessId(request.getParameter("channelId")) + 
            "&remindValue=&channelType=" + 
            channelType + 
            "& =" + 
            informationPO.getInformationType() + 
            "&redhead=" + 
            informationPO.getInformationHead() + 
            "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')");
        String[] para2 = { orgIdString, "" };
        long k = procSubmit.newProcSubmit(workVO, toUser, "4", para2);
        if (k == -1000L)
          request.setAttribute("submitFailed", "1"); 
      } else {
        informationPO = setPO(informationActionForm, request);
        informationPO.setInforModifyMen("");
        informationPO.setInforModifyOrg("");
        informationPO.setInformationStatus(0);
        informationPO.setIsAllow(isAllow);
        informationPO.setOrderCodeTemp(Integer.valueOf(0));
        infoId = this.informationBD
          .add(
            informationPO, 
            para, 
            request.getParameterValues("associateInfo"), 
            request.getParameterValues("infoPicName"), 
            request.getParameterValues("infoPicSaveName"), 
            request.getParameterValues("infoAppendName"), 
            request.getParameterValues("infoAppendSaveName"), 
            domainId, corpId);
        if ("rws".equals(SystemCommon.getCustomerName()) && "1".equals(informationPO.getInformationType())) {
          String gdChannels = SysConfigReader.readConfigValue("RwsGDInfChannelIdList", "ids");
          String sql = "select count(8) from oa_informationchannel where channel_id = " + channelId + " and (";
          String[] chs = gdChannels.split(",");
          for (int i = 0; i < chs.length; i++) {
            if (i == 0) {
              sql = String.valueOf(sql) + " channelidstring like '%$" + chs[0] + "$%' ";
            } else {
              sql = String.valueOf(sql) + " or channelidstring like '%$" + chs[i] + "$%' ";
            } 
          } 
          sql = String.valueOf(sql) + ")";
          DbOpt db = new DbOpt();
          try {
            String count = db.executeQueryToStr(sql);
            if ("1".equals(count))
              (new WorkFlowEJBBeanForRWS()).yuguidang("information", (String)infoId, request.getSession().getAttribute("userId").toString()); 
            db.close();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && infoId != null && ifActiveUpdateDelete != null && !"".equals(infoId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.addIndex(infoId.toString(), "oa_information");
        } 
        if (request.getParameter("formGOVDocument") != null) {
          String[] fileName = request.getParameterValues("infoAppendSaveName");
          String localPath = request.getRealPath("upload");
          copyFileFromDocument(fileName, session, localPath);
          fileName = new String[1];
          String content = informationPO.getInformationContent();
          if (content != null && !"null".equals(content) && !"".equals(content)) {
            fileName[0] = String.valueOf(content) + ".doc";
            copyFileFromDocument(fileName, session, localPath);
          } 
        } 
      } 
      if (action.equals("continue")) {
        tag = "add";
        request.setAttribute("publicinfo", request.getParameter("publicinfo"));
        request.setAttribute("publicchannel", request.getParameter("publicchannel"));
        request.setAttribute("templateList", getTemplate(request));
        request.setAttribute("headList", getHead(userId, orgId));
        request.setAttribute("sealList", getSeal());
        informationActionForm.reset(actionMapping, request);
        List list = new ArrayList();
        if (request.getParameter("informationOrISODoc") != null && request.getParameter("informationOrISODoc").toString().equals("1")) {
          tag = "isoNoAdd";
          list = getIsoCanIssueChannel(userId, orgId, orgIdString, domainId);
          request.setAttribute("canIssueChannel", list);
          request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
        } else {
          list = getCanIssueChannel(userId, orgId, orgIdString, domainId);
          request.setAttribute("canIssueChannel", list);
          request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
        } 
      } else {
        tag = "close";
      } 
      if (request.getParameter("remindUser") != null && "0".equals(request.getParameter("remindUser"))) {
        String title, toUserIds = this.informationBD.getCanReadUserIds(userId, infoId.toString());
        if (toUserIds.equals("-1") && SystemCommon.getMultiDepart() == 1) {
          toUserIds = StaticParam.getEmpIdsByOrgId((String)session.getAttribute("corpId"));
          if (toUserIds.length() > 0)
            toUserIds = toUserIds.substring(0, toUserIds.length() - 1); 
        } 
        boolean flag = true;
        if (toUserIds.trim().length() > 0 && !toUserIds.equals("-1")) {
          toUserIds = toUserIds.replaceAll(String.valueOf(userId) + ",", "").replaceAll(userId, "");
          flag = false;
        } 
        if ("rws".equals(SystemCommon.getCustomerName())) {
          title = informationPO.getInformationTitle();
        } else {
          title = String.valueOf(informationPO.getInformationIssuer()) + "发布了:" + informationPO.getInformationTitle();
        } 
        String url = "/jsoa/info/view_detail.jsp?informationId=" + infoId;
        RemindUtil.sendMessageToUsers(title, url, toUserIds, "Info", informationPO.getInformationIssueTime(), new Date("2050/1/1"));
        if (flag)
          StaticParam.deleteMessage("Info", title, url, userId); 
      } 
      if ("tjqnzyxy".equals(SystemCommon.getCustomerName()) && 
        ",227,228,230,231,".indexOf("," + channelId + ",") > -1)
        (new MessageService()).sendMessages(channelId, "1", informationPO.getInformationHeadFile(), infoId.longValue(), userName, 
            orgName, userName, orgName, "", informationPO.getInformationTitle(), informationPO.getInformationContent()); 
    } else if (action.equals("openInfo")) {
      String informationId = request.getParameter("informationId");
      InformationBD informationBD = new InformationBD();
      String channelIds = "";
      String channelNames = "";
      String isAllow = "";
      List<Object[]> channelstringids = informationBD.getchannleinfo(informationId);
      if (channelstringids != null && channelstringids.size() > 0) {
        Object[] objid = (Object[])null;
        objid = channelstringids.get(0);
        channelIds = objid[0].toString();
        channelNames = objid[1].toString();
        isAllow = objid[2].toString();
      } 
      String returnValue = "1,1,0";
      Boolean dossier = Boolean.FALSE;
      String isSendMessage = "0";
      if (!returnValue.equals("-1")) {
        String[] tmp = returnValue.split(",");
        if (tmp[0].equals("1") || tmp[0].equals("01"))
          dossier = Boolean.TRUE; 
        isSendMessage = tmp[2];
      } 
      request.setAttribute("dossier", dossier);
      request.setAttribute("isSendMessage", isSendMessage);
      request.setAttribute("isAllow", isAllow);
      channelId = channelIds;
      if (request.getParameter("channelId") != null && 
        !request.getParameter("channelId").toString().equals("") && 
        !request.getParameter("channelId").toString().equals("null")) {
        request.setAttribute("channelId", request.getParameter("channelId"));
      } else {
        request.setAttribute("channelId", channelId);
      } 
      String checkdepart = "";
      if (request.getParameter("checkdepart") != null)
        checkdepart = request.getParameter("checkdepart"); 
      String channelName = channelNames;
      String OrgName = "";
      request.setAttribute("channelName", channelName);
      String channelidstring = "";
      if (channelId == null || "".equals(channelId))
        channelId = "null"; 
      List<Object[]> channelstringid = channelBD.getSingleChannel(channelId);
      if (channelstringid != null && channelstringid.size() > 0) {
        Object[] objid = (Object[])null;
        objid = channelstringid.get(0);
        channelidstring = objid[13].toString();
      } 
      if (channelidstring != "") {
        String address = "";
        String[] temp_channelidstring = channelidstring.split("_");
        for (int i = 0; i < temp_channelidstring.length; i++) {
          String channelidstring_new = "";
          channelidstring_new = temp_channelidstring[i];
          String[] temp_channelidstring_new = channelidstring_new
            .split("\\$");
          String channelidstring_new_now = "";
          channelidstring_new_now = temp_channelidstring_new[1];
          channelId = channelidstring_new_now;
          List<Object[]> channelname = channelBD.getSingleChannel(channelId);
          if (channelname != null && channelname.size() > 0) {
            Object[] objid = (Object[])null;
            objid = channelname.get(0);
            channelidstring = objid[0].toString();
          } 
          address = String.valueOf(address) + "." + channelidstring;
        } 
        if (checkdepart.equals("1")) {
          List<Object[]> getOrgName = informationBD.getOrgName(channelId);
          if (getOrgName != null && getOrgName.size() > 0) {
            Object[] objorgname = (Object[])null;
            objorgname = getOrgName.get(0);
            OrgName = objorgname[0].toString();
            channelName = OrgName;
          } 
        } 
        if (checkdepart.equals("1")) {
          request.setAttribute("userChannelName", channelName);
        } else {
          request.setAttribute("userChannelName", userChannelName);
        } 
        request.setAttribute("channelId", channelId);
        request.setAttribute("channelName", channelidstring);
        request.setAttribute("address", address);
      } 
      tag = "noRedHead";
      String printFlag = request.getParameter("printFlag");
      if (printFlag != null && "1".equals(printFlag))
        tag = "noRedHeadPrint"; 
      if (request.getParameter("isISO") != null && 
        request.getParameter("isISO").toString()
        .equals("1"))
        tag = "isoNoRedHead"; 
      String redHead = request.getParameter("redHead");
      String informationType = request.getParameter("informationType");
      request.setAttribute("informationType", informationType);
      List commentList = informationBD.getOrderedComment(request.getParameter("informationId"));
      request.setAttribute("commentList", commentList);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      boolean canVindicate = (new ChannelBD()).canVindicate(userId, orgId, channelId);
      if (!canVindicate) {
        ManagerService mbd = new ManagerService();
        List rightList = mbd.getRightScope(userId, "01*03*03");
        String canModiIds = informationBD.getInformationModiIds(
            channelId, userId, orgId, orgIdString, informationId, 
            rightList);
        if (canModiIds.indexOf("," + informationId + ",") >= 0)
          canVindicate = true; 
      } 
      request.setAttribute("delComment", canVindicate ? "1" : "0");
      if ("-1".equals(channelType) && "0".equals(userDefine1))
        channelType = "2"; 
      if (informationType.equals("2")) {
        String content = informationBD.getContent(request.getParameter("informationId"));
        if (informationType.equals("2")) {
          if (!content.startsWith("http://"))
            content = "http://" + content; 
          request.setAttribute("midType", "url");
          request.setAttribute("content", content);
        } 
        tag = "mid";
      } else {
        request.setAttribute("informationId", request.getParameter("informationId"));
        request.setAttribute("informationType", informationType);
        List<Object[]> list = informationBD.getSingleInfo(request.getParameter("informationId"), request
            .getParameter("channelId"));
        String content = "";
        if (list != null && list.size() > 0) {
          Object object, obj[] = list.get(0);
          request.setAttribute("informationTitle", obj[0]);
          request.setAttribute("forbidCopy", obj[26]);
          if (obj[1] == null) {
            request.setAttribute("informationSubTitle", "");
          } else {
            request.setAttribute("informationSubTitle", obj[1].toString());
          } 
          request.setAttribute("informationIssuer", obj[3].toString());
          request.setAttribute("informationIssueOrg", obj[4].toString());
          String tmp = obj[5].toString();
          if (tmp.indexOf(".") > 0) {
            request.setAttribute("informationIssueTime", 
                tmp.substring(0, tmp.indexOf(".")));
          } else {
            request.setAttribute("informationIssueTime", 
                tmp);
          } 
          tmp = obj[6].toString();
          if (tmp.indexOf(".") > 0) {
            request.setAttribute(
                "informationModifyTime", tmp.substring(0, tmp.indexOf(".")));
          } else {
            request.setAttribute("informationModifyTime", tmp);
          } 
          if (obj[7] == null) {
            request.setAttribute("informationVersion", "");
          } else {
            request.setAttribute("informationVersion", obj[7].toString());
          } 
          if (obj[8] == null) {
            request.setAttribute("informationAuthor", "");
          } else {
            request.setAttribute("informationAuthor", obj[8].toString());
          } 
          if (obj[36] == null) {
            request.setAttribute("modifyEmp", "");
          } else {
            request.setAttribute("modifyEmp", obj[36].toString());
          } 
          if (obj[37] == null) {
            request.setAttribute("dossierStatus", "0");
          } else {
            request.setAttribute("dossierStatus", obj[37].toString());
          } 
          if (obj[41] != null) {
            request.setAttribute("documentNo", obj[41]);
            informationActionForm.setDocumentNo(obj[41].toString());
          } else {
            request.setAttribute("documentNo", "");
            informationActionForm.setDocumentNo("");
          } 
          if (obj[15] == null) {
            request.setAttribute("informationKey", "");
          } else {
            request.setAttribute("informationKey", obj[15]);
          } 
          if (obj[14] == null) {
            request.setAttribute("informationSummary", "");
          } else {
            request.setAttribute("informationSummary", obj[14]);
          } 
          if (obj[43] == null) {
            request.setAttribute("documentType", "");
          } else {
            request.setAttribute("documentType", obj[43]);
          } 
          request.setAttribute("wordDisplayType", obj[46]);
          request.setAttribute("forbidCopy", obj[26]);
          request.setAttribute("displayTitle", obj[31]);
          request.setAttribute("displayImage", obj[44]);
          request.setAttribute("isoDealCategory", obj[54]);
          request.setAttribute("isoModifyReason", obj[59]);
          request.setAttribute("isoAmendmentPage", obj[60]);
          request.setAttribute("inforModifyMen", obj[62]);
          request.setAttribute("inforModifyOrg", obj[63]);
          content = (obj[2] == null) ? "" : obj[2].toString();
          if (request.getParameter("isISO") != null && request.getParameter("isISO")
            .toString().equals("1"))
            if (request.getParameter("isomanager") != null && 
              request.getParameter("isomanager").toString().equals("1")) {
              List historyList = informationBD.getHistoryVersion(informationId);
              request.setAttribute("historyList", historyList);
              request.setAttribute("isomanager", request.getParameter("isomanager"));
            } else if (obj[7] != null && !"".equals(obj[7])) {
              Object object1 = obj[7];
              String middle = "0";
              int a = Integer.parseInt(object1.substring(0, object1.indexOf(".")));
              String b = object1.substring(object1.indexOf(".") + 1, object1.length());
              Object object2 = obj[54];
              if (!b.equals("0") || object2.equals("2") || 
                object2.equals("3") || 
                object2.equals("4")) {
                middle = b.substring(0, 1);
                object1 = a + ".0";
                IsoDocBD bd = new IsoDocBD();
                List<Object[]> versionList = bd
                  .getInforByVersion(
                    request.getParameter("informationId"), (String)object1);
                if (versionList != null && 
                  versionList.size() > 0) {
                  Object[] bObj = versionList.get(0);
                  request.setAttribute("informationTitle", bObj[8]);
                  if (bObj[9] == null) {
                    request.setAttribute("informationSubTitle", "");
                  } else {
                    request.setAttribute("informationSubTitle", bObj[9].toString());
                  } 
                  object = (bObj[10] == null) ? "" : bObj[10];
                  if (bObj[1] != null && 
                    !bObj[1].toString().equals("")) {
                    request.setAttribute(
                        "modifyEmp", bObj[1] + "." + bObj[2]);
                  } else {
                    request.setAttribute("modifyEmp", " ");
                  } 
                } 
              } 
            }  
          if (informationType.equals("3")) {
            if (object != null && object.toString().length() > 1) {
              String[] tmp2 = object.toString().split(":");
              request.setAttribute("midType", 
                  "fileLink");
              if (tmp2.length == 2) {
                request.setAttribute("fileName", tmp2[0]);
                request.setAttribute("saveName", tmp2[1]);
              } 
            } 
            tag = "fileLink";
            if (request.getParameter("isAffiche") != null && 
              request.getParameter("isAffiche").toString().equals("1"))
              tag = "AfficefileLink"; 
          } else {
            if (object == null) {
              request.setAttribute("content", "");
            } else {
              request.setAttribute("content", object);
            } 
            if (redHead != null && redHead.equals("1"))
              tag = "hasRedHead"; 
            request.setAttribute("redHead", redHead);
            if (tag.equals("hasRedHead")) {
              if (obj[9] == null) {
                request.setAttribute("informationMark", "");
              } else {
                request.setAttribute("informationMark", obj[9].toString());
              } 
              if (obj[10] == null) {
                request.setAttribute("informationHeadFile", "");
              } else {
                request.setAttribute("informationHeadFile", obj[10].toString());
              } 
              if (obj[11] == null) {
                request.setAttribute("informationSeal", "");
              } else {
                request.setAttribute("informationSeal", obj[11].toString());
              } 
              if (obj[12] == null) {
                request.setAttribute("infoRedIssueOrg", "");
              } else {
                request.setAttribute("infoRedIssueOrg", obj[12].toString());
              } 
              request.setAttribute("infoRedIssueTime", obj[13].toString());
            } 
            InformationAccessoryBD informationAccessoryBD = new InformationAccessoryBD();
            list = informationAccessoryBD
              .getAccessory(request.getParameter("informationId"));
            request.setAttribute("accessoryList", list);
          } 
          String depart = request.getParameter("depart");
          if (depart != null) {
            request.setAttribute("depart", depart);
            request.setAttribute("canVindicate", (
                new DepartmentPageBD()).deptVindicateInfo(userId, orgId, channelType));
            request.setAttribute("infoDepaFlag", obj[28]);
            request.setAttribute("infoDepaFlag2", obj[29]);
          } 
        } else {
          tag = "noMessage";
        } 
      } 
      informationBD.setBrowserKits(userId, userName, orgName, 
          request.getParameter("informationId"), 
          httpSession.getAttribute("orgIdString").toString(), domainId);
      if (!canVindicate) {
        ManagerService mbd = new ManagerService();
        List rightList = mbd.getRightScope(userId, "01*03*03");
        if (rightList != null && rightList.size() > 0 && 
          rightList.get(0) != null)
          if (channelBD.isChannelManager(channelId, userId, orgId, 
              orgIdString)) {
            String ids = (new InformationBD()).getInformationModiIds(channelId, userId, orgId, orgIdString, String.valueOf(informationId) + ",-1", rightList);
            request.setAttribute("canModifiedIds", ids);
          }  
      } else {
        request.setAttribute("guidang", "1");
      } 
      if (request.getParameter("directOpen") != null)
        tag = "directOpen"; 
      if (request.getParameter("statOpen") != null && 
        request.getParameter("statOpen").toString().equals("1"))
        tag = "statOpen"; 
    } else if (action.equals("modify")) {
      String printCheckup = (request
        .getParameter("printCheckup") == null) ? "0" : 
        request.getParameter("printCheckup").toString();
      if ("1".equals(request.getParameter("needCheckup"))) {
        tag = "modifyCheckup";
        WorkFlowBD wfBD = new WorkFlowBD();
        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setId(4);
        if (isIso.equals("1")) {
          tag = "isomodifyCheckup";
          if (printCheckup.equals("1"))
            tag = "printCheckup"; 
          moduleVO.setId(50);
        } 
        if (isAffiche.equals("1"))
          moduleVO.setId(51); 
        moduleVO.setFormType(0);
        AccessTableVO tableVO = wfBD.getAccessTable(
            moduleVO).get(0);
        String tableId = String.valueOf(tableVO.getId());
        if (request.getParameter("processId") != null) {
          request.setAttribute("processId", request.getParameter("processId"));
        } else {
          request.setAttribute("processId", channelBD
              .getInformationProcessId(request.getParameter("informationId")));
        } 
        request.setAttribute("tableId", tableId);
        request.setAttribute("moduleId", "4");
        request.setAttribute("processName", "信息发布流程");
        if (isIso.equals("1")) {
          request.setAttribute("moduleId", "50");
          request.setAttribute("processName", "文档发布流程");
        } 
        request.setAttribute("processType", "1");
        request.setAttribute("remindField", "");
        request.setAttribute("recordId", request.getParameter("informationId"));
        if (request.getParameter("isAffiche") != null && 
          request.getParameter("isAffiche")
          .toString().equals("1")) {
          tag = "afficheModifyCheckup";
          request.setAttribute("isAffiche", "1");
          String afficheType = (request
            .getParameter("afficheType") == null) ? "" : 
            request.getParameter("afficheType")
            .toString();
          request.setAttribute("afficheType", afficheType);
        } 
        List<Object[]> list1 = channelBD.getSingleChannel(channelId);
        String str1 = null;
        if (list1 != null && list1.size() > 0) {
          Object[] ch = (Object[])null;
          ch = list1.get(0);
          str1 = ch[28].toString();
        } 
        request.setAttribute("isAllowView", str1);
      } else {
        tag = "modify";
        if (request.getParameter("isISO") != null && 
          request.getParameter("isISO").toString()
          .equals("1")) {
          tag = "isomodify";
          if (printCheckup.equals("1"))
            tag = "printmodify"; 
          if (request.getParameter("docDestroy") != null && 
            request.getParameter("docDestroy").equals("1")) {
            String str1 = request.getParameter("channelName");
            IsoDocBD isoDocBD2 = new IsoDocBD();
            this.informationBD.saveHistory(request.getParameter("informationId"));
            isoDocBD2.setInformationStatus(request.getParameter("informationId"), "0", "2", 
                (String)session.getAttribute("orgName"), 
                (String)session.getAttribute("userName"));
            String furl = "/IsoInfoListAction.do?channelType=0&userChannelName=知识管理&userDefine=0&isISO=1";
            if (request.getParameter("channelId") != null && 
              !request.getParameter("channelId").toString().equals("")) {
              furl = String.valueOf(furl) + "&channelId=" + request.getParameter("channelId") + "&channelName=" + str1;
            } else {
              furl = String.valueOf(furl) + "&type=all";
            } 
            furl = String.valueOf(furl) + "&isoViewType=" + request.getParameter("isoViewType");
            ActionForward forward = new ActionForward();
            forward.setPath(furl);
            return forward;
          } 
        } 
      } 
      request.setAttribute("channelType", channelType);
      String informationId = request.getParameter("informationId");
      String informationType = request.getParameter("informationType");
      request.setAttribute("informationId", informationId);
      request.setAttribute("informationType", informationType);
      request.setAttribute("redHead", request.getParameter("redHead"));
      HttpSession httpSession = request.getSession(true);
      if (request.getParameter("isISO") != null && 
        request.getParameter("isISO").toString().equals("1")) {
        List isoList = getIsoCanIssueChannel(httpSession.getAttribute(
              "userId").toString(), httpSession.getAttribute("orgId")
            .toString(), httpSession.getAttribute("orgIdString")
            .toString(), domainId);
        request.setAttribute("canIssueChannel", isoList);
      } else {
        List canIssueList = getCanIssueChannel(httpSession
            .getAttribute("userId").toString(), httpSession
            .getAttribute("orgId").toString(), httpSession
            .getAttribute("orgIdString").toString(), domainId);
        request.setAttribute("canIssueChannel", canIssueList);
      } 
      String channelName = request.getParameter("channelName");
      if (request.getParameter("channelId") != null) {
        informationActionForm
          .setChannelId(Integer.parseInt(request.getParameter("channelId")));
        informationActionForm.setChannelName(channelName);
      } 
      request.setAttribute("channelName", channelName);
      List<Object[]> list = this.informationBD.getSingleInfo(informationId, request.getParameter("channelId"));
      Object[] obj = list.get(0);
      if (list.size() > 1)
        request.setAttribute("channelName", list.get(1).toString()); 
      String tmpTitle = obj[0].toString();
      List<Object[]> channelList = channelBD.getSingleChannel(channelId);
      String isAllowView = null;
      if (channelList != null && channelList.size() > 0) {
        Object[] ch = (Object[])null;
        ch = channelList.get(0);
        isAllowView = ch[28].toString();
      } 
      request.setAttribute("isAllowView", isAllowView);
      request.setAttribute("isAllow", obj[64]);
      request.setAttribute("forbidCopy", obj[26]);
      if (tmpTitle.startsWith("<font color=red>")) {
        request.setAttribute("displayColor", "1");
        tmpTitle = tmpTitle.replaceAll("<font color=red>", "");
        tmpTitle = tmpTitle.replaceAll("</font>", "");
      } 
      informationActionForm.setInformationTitle(tmpTitle);
      request.setAttribute("informationTitle", tmpTitle);
      if (obj[33] != null && obj[33].toString().equals("1"))
        request.setAttribute("displayColor", "1"); 
      if (obj[38] != null) {
        request.setAttribute("mustRead", obj[38]);
      } else {
        request.setAttribute("mustRead", "0");
      } 
      request.setAttribute("comeFrom", (obj[39] == null) ? "" : obj[39]);
      informationActionForm.setComeFrom((obj[39] == null) ? "" : obj[39].toString());
      if (obj[40] != null) {
        request.setAttribute("isConf", obj[40]);
      } else {
        request.setAttribute("isConf", "0");
      } 
      if (obj[41] != null) {
        request.setAttribute("documentNo", obj[41]);
        informationActionForm.setDocumentNo(obj[41].toString());
      } else {
        request.setAttribute("documentNo", "");
        informationActionForm.setDocumentNo("");
      } 
      if (obj[42] != null) {
        request.setAttribute("documentEditor", obj[42]);
        informationActionForm.setDocumentEditor(obj[42].toString());
      } else {
        request.setAttribute("documentEditor", "");
        informationActionForm.setDocumentEditor("");
      } 
      if (obj[43] != null) {
        request.setAttribute("documentType", obj[43]);
      } else {
        request.setAttribute("documentType", "");
      } 
      request.setAttribute("informationHeadFile", obj[10]);
      if (obj[1] == null) {
        informationActionForm.setInformationSubTitle("");
      } else {
        informationActionForm.setInformationSubTitle(obj[1].toString());
      } 
      request.setAttribute("informationVersion", obj[7]);
      String content = (obj[2] == null) ? "" : obj[2].toString();
      informationActionForm.setInformationContent(content);
      request.setAttribute("content", content);
      request.setAttribute("issueTime", obj[5]);
      if (obj[8] == null) {
        informationActionForm.setInformationAuthor("");
      } else {
        informationActionForm.setInformationAuthor(obj[8].toString());
      } 
      if (obj[9] == null) {
        informationActionForm.setInformationMark("");
      } else {
        informationActionForm.setInformationMark(obj[9].toString());
      } 
      if (obj[12] == null) {
        informationActionForm.setInfoRedIssueOrg("");
      } else {
        informationActionForm.setInfoRedIssueOrg(obj[12].toString());
      } 
      if (obj[14] == null) {
        informationActionForm.setInformationSummary("");
      } else {
        informationActionForm.setInformationSummary(obj[14].toString());
      } 
      if (obj[15] == null) {
        informationActionForm.setInformationKey("");
      } else {
        informationActionForm.setInformationKey(obj[15].toString());
      } 
      if (obj[16] == null) {
        informationActionForm.setInformationReaderName("");
      } else {
        informationActionForm.setInformationReaderName(obj[16]
            .toString());
      } 
      if (obj[17] == null) {
        informationActionForm.setInformationReader("");
      } else {
        informationActionForm.setInformationReader(obj[17].toString());
      } 
      if (obj[18] == null) {
        informationActionForm.setInformationReaderOrg("");
      } else {
        informationActionForm.setInformationReaderOrg(obj[18]
            .toString());
      } 
      if (obj[19] == null) {
        informationActionForm.setInformationReaderGroup("");
      } else {
        informationActionForm.setInformationReaderGroup(obj[19]
            .toString());
      } 
      request.setAttribute("informationValidType", obj[20]);
      request.setAttribute("validBeginTime", obj[21]);
      request.setAttribute("validEndTime", obj[22]);
      request.setAttribute("inforamtionHead", obj[23]);
      request.setAttribute("infoRedIssueTime", obj[13]);
      request.setAttribute("informationHeadId", obj[24]);
      request.setAttribute("informationSealId", obj[25]);
      request.setAttribute("transmitToWebsite", obj[27]);
      request.setAttribute("orderCode", obj[30]);
      request.setAttribute("displayTitle", obj[31]);
      request.setAttribute("displayImage", obj[44]);
      request.setAttribute("afficeHistoryDate", 
          (obj[45] == null) ? "" : obj[45]);
      request.setAttribute("wordDisplayType", obj[46]);
      if (obj[50] == null) {
        request.setAttribute("informationOrISODoc", "0");
      } else {
        request.setAttribute("informationOrISODoc", 
            obj[50]);
      } 
      if (obj[51] == null) {
        request.setAttribute("isoDocStatus", "0");
      } else {
        request.setAttribute("isoDocStatus", obj[51]);
      } 
      if (obj[52] == null) {
        request.setAttribute("isoOldInfoId", "");
      } else {
        request.setAttribute("isoOldInfoId", obj[52]);
      } 
      if (obj[53] == null) {
        request.setAttribute("isoSecretStatus", "0");
      } else {
        request.setAttribute("isoSecretStatus", obj[53]);
      } 
      if (obj[54] == null) {
        request.setAttribute("isoDealCategory", "0");
      } else {
        request.setAttribute("isoDealCategory", obj[54]);
      } 
      if (obj[55] == null) {
        request.setAttribute("isoApplyName", "");
        request.setAttribute("isoApplyId", "");
        informationActionForm.setIsoApplyName("");
        informationActionForm.setIsoApplyId("");
      } else {
        request.setAttribute("isoApplyName", obj[55]);
        request.setAttribute("isoApplyId", obj[56]);
        informationActionForm.setIsoApplyName((String)obj[55]);
        informationActionForm.setIsoApplyId((String)obj[56]);
      } 
      if (obj[57] == null) {
        request.setAttribute("isoReceiveName", "");
        request.setAttribute("isoReceiveId", "");
        informationActionForm.setIsoReceiveName("");
        informationActionForm.setIsoReceiveId("");
      } else {
        request.setAttribute("isoReceiveName", obj[57]);
        request.setAttribute("isoReceiveId", obj[58]);
        informationActionForm.setIsoReceiveName((String)obj[57]);
        informationActionForm.setIsoReceiveId((String)obj[58]);
      } 
      if (obj[59] == null) {
        request.setAttribute("isoModifyReason", "");
        informationActionForm.setIsoModifyReason("");
      } else {
        request
          .setAttribute("isoModifyReason", obj[59]);
        informationActionForm.setIsoModifyReason((String)obj[59]);
      } 
      if (obj[60] == null) {
        request.setAttribute("isoAmendmentPage", "");
        informationActionForm.setIsoAmendmentPage("");
      } else {
        request.setAttribute("isoAmendmentPage", 
            obj[60]);
        informationActionForm.setIsoAmendmentPage((String)obj[60]);
      } 
      if (obj[61] == null) {
        request.setAttribute("isoModifyVersion", "");
        informationActionForm.setIsoModifyVersion("");
      } else {
        request.setAttribute("isoModifyVersion", 
            obj[61]);
        informationActionForm.setIsoModifyVersion((String)obj[61]);
      } 
      InformationChannelPO po = channelBD.loadChannel(channelId);
      String channelCanReader = "";
      if (po.getChannelReader() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReader(); 
      if (po.getChannelReaderGroup() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReaderGroup(); 
      if (po.getChannelReaderOrg() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReaderOrg(); 
      request.setAttribute("channelCanReaderID", 
          channelCanReader);
      if (obj[32] == null) {
        request.setAttribute("otherChannel", "");
      } else {
        request.setAttribute("otherChannel", obj[32]);
      } 
      if (obj[24] != null)
        request.setAttribute("headSealList", null); 
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      request.setAttribute("otherChannelList", 
          getAllCanIssueWithoutCheck(userId, orgId, domainId));
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
      InformationAccessoryBD informationAccessoryBD = new InformationAccessoryBD();
      list = informationAccessoryBD.getAccessory(request
          .getParameter("informationId"));
      ArrayList picList = new ArrayList();
      ArrayList appList = new ArrayList();
      Object[] tmp = (Object[])null;
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          tmp = list.get(i);
          if (tmp[4].toString().equals("1")) {
            picList.add(list.get(i));
          } else {
            appList.add(list.get(i));
          } 
        }  
      request.setAttribute("picList", picList);
      request.setAttribute("appList", appList);
      if ("-1".equals(channelType) && "0".equals(userDefine1))
        channelType = "2"; 
      request.setAttribute("assoicateInfo", (
          new InformationBD()).getAssociateInfo(orgId, 
            request.getParameter("informationId"), 
            userId, httpSession.getAttribute("orgIdString")
            .toString(), channelType, userDefine1, 
            channelStatusType));
    } else if (action.equals("modifys")) {
      tag = "modifys";
      String curModifyField = "";
      WorkflowCommon workflowCommon = new WorkflowCommon();
      if ("1".equals(request.getParameter("resubmit"))) {
        curModifyField = workflowCommon.getFirstActivityWriteField(request.getParameter("processId"), "4");
      } else {
        curModifyField = workflowCommon.getCurActivityWriteField(request);
      } 
      request.setAttribute("curModifyField", curModifyField);
      request.setAttribute("channelType", channelType);
      String record = request.getParameter("record");
      String informationId = request.getParameter("record");
      String informationType = "";
      InformationBD informationBD = new InformationBD();
      List<Object[]> infortype = new ArrayList();
      infortype = informationBD.getinformation(informationId);
      Object[] objlistype = (Object[])null;
      if (infortype != null && infortype.size() > 0) {
        objlistype = infortype.get(0);
        informationType = objlistype[0].toString();
      } 
      request.setAttribute("informationId", informationId);
      request.setAttribute("informationType", informationType);
      request.setAttribute("redHead", request
          .getParameter("redHead"));
      HttpSession httpSession = request.getSession(true);
      List canIssueList = getCanIssueChannel(httpSession.getAttribute(
            "userId").toString(), httpSession.getAttribute("orgId")
          .toString(), httpSession.getAttribute("orgIdString")
          .toString(), domainId);
      request.setAttribute("isAllow", objlistype[2]);
      if (request.getParameter("isISO") != null && 
        request.getParameter("isISO").equals("1")) {
        tag = "isomodifys";
        canIssueList = channelBD.getAllIsoChannel("");
      } 
      request.setAttribute("canIssueChannel", canIssueList);
      if (request.getParameter("channelId") != null) {
        informationActionForm
          .setChannelId(Integer.parseInt(request
              .getParameter("channelId")));
        String channelName = request
          .getParameter("channelName");
        informationActionForm.setChannelName(channelName);
        List<Object[]> channelList = channelBD
          .getSingleChannel(request
            .getParameter("channelId"));
        if (channelList != null && channelList.size() > 0) {
          Object[] arrayOfObject = (Object[])null;
          arrayOfObject = channelList.get(0);
          request.setAttribute("isAllowVie", arrayOfObject[28]);
          Object object = arrayOfObject[27];
          if (object != null && object.equals("1")) {
            tag = "affichemodifys";
            List affichecanIssueList = getAfficheCanIssueChannel(
                httpSession.getAttribute("userId").toString(), 
                httpSession.getAttribute("orgId").toString(), 
                httpSession.getAttribute("orgIdString")
                .toString(), domainId);
            request.setAttribute("canIssueChannel", 
                affichecanIssueList);
          } 
        } 
      } 
      List<E> list = informationBD.getSingleInfo(informationId, 
          request.getParameter("channelId"));
      if (list.size() > 1)
        request.setAttribute("channelName", list.get(1)
            .toString()); 
      Object[] obj = (Object[])list.get(0);
      request.setAttribute("informationIssuer", obj[3]);
      request.setAttribute("informationIssuerOrg", obj[4]);
      String tmpTitle = obj[0].toString();
      if (tmpTitle.startsWith("<font color=red>")) {
        request.setAttribute("displayColor", "1");
        tmpTitle = tmpTitle.replaceAll("<font color=red>", "");
        tmpTitle = tmpTitle.replaceAll("</font>", "");
      } 
      informationActionForm.setInformationTitle(tmpTitle);
      if (obj[33] != null && obj[33].toString().equals("1"))
        request.setAttribute("displayColor", "1"); 
      if (obj[38] != null) {
        request.setAttribute("mustRead", obj[38]);
      } else {
        request.setAttribute("mustRead", "0");
      } 
      request.setAttribute("comeFrom", (obj[39] == null) ? "" : 
          obj[39]);
      informationActionForm.setComeFrom((obj[39] == null) ? "" : obj[39]
          .toString());
      if (obj[40] != null) {
        request.setAttribute("isConf", obj[40]);
      } else {
        request.setAttribute("isConf", "0");
      } 
      if (obj[41] != null) {
        request.setAttribute("documentNo", obj[41]);
        informationActionForm.setDocumentNo(obj[41].toString());
      } else {
        request.setAttribute("documentNo", "");
        informationActionForm.setDocumentNo("");
      } 
      if (obj[42] != null) {
        request.setAttribute("documentEditor", obj[42]);
        informationActionForm.setDocumentEditor(obj[42].toString());
      } else {
        request.setAttribute("documentEditor", "");
        informationActionForm.setDocumentEditor("");
      } 
      if (obj[43] != null) {
        request.setAttribute("documentType", obj[43]);
      } else {
        request.setAttribute("documentType", "");
      } 
      if (obj[43] != null) {
        request.setAttribute("displayImage", obj[44]);
      } else {
        request.setAttribute("displayImage", "1");
      } 
      if (obj[1] == null) {
        informationActionForm.setInformationSubTitle("");
      } else {
        informationActionForm.setInformationSubTitle(obj[1].toString());
      } 
      String informationcontent = (obj[2] == null) ? "" : obj[2].toString();
      informationActionForm.setInformationContent(informationcontent);
      request.setAttribute("informationcontent", 
          informationcontent);
      request.setAttribute("issueTime", obj[5]);
      if (obj[8] == null) {
        informationActionForm.setInformationAuthor("");
      } else {
        informationActionForm.setInformationAuthor(obj[8].toString());
      } 
      if (obj[9] == null) {
        informationActionForm.setInformationMark("");
      } else {
        informationActionForm.setInformationMark(obj[9].toString());
      } 
      if (obj[12] == null) {
        informationActionForm.setInfoRedIssueOrg("");
      } else {
        informationActionForm.setInfoRedIssueOrg(obj[12].toString());
      } 
      if (obj[14] == null) {
        informationActionForm.setInformationSummary("");
      } else {
        informationActionForm.setInformationSummary(obj[14].toString());
      } 
      String informationKey = "";
      if (obj[15] == null) {
        informationActionForm.setInformationKey("");
        request.setAttribute("informationKey", 
            informationKey);
      } else {
        informationActionForm.setInformationKey(obj[15].toString());
        informationKey = obj[15].toString();
        request.setAttribute("informationKey", 
            informationKey);
      } 
      if (obj[16] == null) {
        informationActionForm.setInformationReaderName("");
      } else {
        informationActionForm.setInformationReaderName(obj[16]
            .toString());
      } 
      if (obj[17] == null) {
        informationActionForm.setInformationReader("");
      } else {
        informationActionForm.setInformationReader(obj[17].toString());
      } 
      if (obj[18] == null) {
        informationActionForm.setInformationReaderOrg("");
      } else {
        informationActionForm.setInformationReaderOrg(obj[18]
            .toString());
      } 
      if (obj[19] == null) {
        informationActionForm.setInformationReaderGroup("");
      } else {
        informationActionForm.setInformationReaderGroup(obj[19]
            .toString());
      } 
      request.setAttribute("informationVersion", obj[7]);
      request.setAttribute("informationValidType", obj[20]);
      request.setAttribute("validBeginTime", obj[21]);
      request.setAttribute("validEndTime", obj[22]);
      request.setAttribute("inforamtionHead", obj[23]);
      request.setAttribute("infoRedIssueTime", obj[13]);
      request.setAttribute("informationHeadId", obj[24]);
      request.setAttribute("informationSealId", obj[25]);
      request.setAttribute("forbidCopy", obj[26]);
      request.setAttribute("transmitToWebsite", obj[27]);
      request.setAttribute("orderCode", obj[30]);
      request.setAttribute("displayTitle", obj[31]);
      request.setAttribute("afficeHistoryDate", 
          (obj[45] == null) ? "" : obj[45]);
      request.setAttribute("wordDisplayType", obj[46]);
      InformationChannelPO po = channelBD.loadChannel(channelId);
      String channelCanReader = "";
      if (po.getChannelReader() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReader(); 
      if (po.getChannelReaderGroup() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReaderGroup(); 
      if (po.getChannelReaderOrg() != null)
        channelCanReader = String.valueOf(channelCanReader) + po.getChannelReaderOrg(); 
      request.setAttribute("channelCanReaderID", 
          channelCanReader);
      if (obj[32] == null) {
        request.setAttribute("otherChannel", "");
      } else {
        request.setAttribute("otherChannel", obj[32]);
      } 
      if (obj[24] != null)
        request.setAttribute("headSealList", null); 
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      request.setAttribute("otherChannelList", 
          getAllCanIssueWithoutCheck(userId, orgId, domainId));
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
      InformationAccessoryBD informationAccessoryBD = new InformationAccessoryBD();
      list = informationAccessoryBD.getAccessory(request
          .getParameter("record"));
      ArrayList picList = new ArrayList();
      ArrayList appList = new ArrayList();
      Object[] tmp = (Object[])null;
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          tmp = (Object[])list.get(i);
          if (tmp[4].toString().equals("1")) {
            picList.add(list.get(i));
          } else {
            appList.add(list.get(i));
          } 
        }  
      request.setAttribute("picList", picList);
      request.setAttribute("appList", appList);
      String userDefine2 = informationBD
        .getInfoUserdefine(request
          .getParameter("record"));
      if ("-1".equals(channelType) && "0".equals(userDefine2))
        channelType = "2"; 
      request.setAttribute("assoicateInfo", (
          new InformationBD()).getAssociateInfo(orgId, 
            request.getParameter("record"), userId, 
            httpSession.getAttribute("orgIdString").toString(), 
            channelType, userDefine2, channelStatusType));
      if ("1".equals(request.getParameter("needCheckup")) && 
        request.getParameter("isAffiche") != null && 
        request.getParameter("isAffiche")
        .toString().equals("1")) {
        tag = "afficheModifyCheckup";
        request.setAttribute("isAffiche", "1");
        String afficheType = (request
          .getParameter("afficheType") == null) ? "" : 
          request.getParameter("afficheType")
          .toString();
        request.setAttribute("afficheType", afficheType);
      } 
      if (obj[50] == null) {
        request.setAttribute("informationOrISODoc", "0");
      } else {
        request.setAttribute("informationOrISODoc", 
            obj[50]);
      } 
      if (obj[51] == null) {
        request.setAttribute("isoDocStatus", "0");
      } else {
        request.setAttribute("isoDocStatus", obj[51]);
      } 
      if (obj[52] == null) {
        request.setAttribute("isoOldInfoId", "");
      } else {
        request.setAttribute("isoOldInfoId", obj[52]);
      } 
      if (obj[53] == null) {
        request.setAttribute("isoSecretStatus", "0");
      } else {
        request
          .setAttribute("isoSecretStatus", obj[53]);
      } 
      if (obj[54] == null) {
        request.setAttribute("isoDealCategory", "0");
      } else {
        request
          .setAttribute("isoDealCategory", obj[54]);
      } 
      if (obj[55] == null) {
        request.setAttribute("isoApplyName", "");
        request.setAttribute("isoApplyId", "");
        informationActionForm.setIsoApplyName("");
        informationActionForm.setIsoApplyId("");
      } else {
        request.setAttribute("isoApplyName", obj[55]);
        request.setAttribute("isoApplyId", obj[56]);
        informationActionForm.setIsoApplyName((String)obj[55]);
        informationActionForm.setIsoApplyId((String)obj[56]);
      } 
      if (obj[57] == null) {
        request.setAttribute("isoReceiveName", "");
        request.setAttribute("isoReceiveId", "");
        informationActionForm.setIsoReceiveName("");
        informationActionForm.setIsoReceiveId("");
      } else {
        request.setAttribute("isoReceiveName", obj[57]);
        request.setAttribute("isoReceiveId", obj[58]);
        informationActionForm.setIsoReceiveName((String)obj[57]);
        informationActionForm.setIsoReceiveId((String)obj[58]);
      } 
      if (obj[59] == null) {
        request.setAttribute("isoModifyReason", "");
        informationActionForm.setIsoModifyReason("");
      } else {
        request
          .setAttribute("isoModifyReason", obj[59]);
        informationActionForm.setIsoModifyReason((String)obj[59]);
      } 
      if (obj[60] == null) {
        request.setAttribute("isoAmendmentPage", "");
        informationActionForm.setIsoAmendmentPage("");
      } else {
        request.setAttribute("isoAmendmentPage", 
            obj[60]);
        informationActionForm.setIsoAmendmentPage((String)obj[60]);
      } 
      if (obj[61] == null) {
        request.setAttribute("isoModifyVersion", "");
        informationActionForm.setIsoModifyVersion("");
      } else {
        request.setAttribute("isoModifyVersion", 
            obj[61]);
        informationActionForm.setIsoModifyVersion((String)obj[61]);
      } 
    } else if (action.equals("trueModify")) {
      String informationId = request.getParameter("informationId");
      this.informationBD.saveHistory(informationId);
      request.setAttribute("channelType", channelType);
      HttpSession httpSession = request.getSession(true);
      String informationTitle = informationActionForm
        .getInformationTitle();
      String titleColor = (request.getParameter("displayColor") == null) ? "0" : 
        request.getParameter("displayColor");
      String informationSubTitle = informationActionForm
        .getInformationSubTitle();
      String informationSummary = informationActionForm
        .getInformationSummary();
      String informationContent = informationActionForm
        .getInformationContent();
      String inforamtionKey = informationActionForm.getInformationKey();
      String informationVaildType = informationActionForm
        .getInformationVaildType();
      String validBeginTime = informationActionForm.getValidBeginTime();
      String validEndTime = informationActionForm.getValidEndTime();
      String informationAuthor = informationActionForm
        .getInformationAuthor();
      String informationReaderName = informationActionForm
        .getInformationReaderName();
      String inforamtionReader = request.getParameter("informationReaderId");
      String informationReaderOrg = informationActionForm
        .getInformationReaderOrg();
      String inforamtionReaderGroup = informationActionForm
        .getInformationReaderGroup();
      String informationHead = informationActionForm.getInformationHead();
      String informationHeadFile = "", informationSeal = "", informationMark = "", infoRedIssueOrg = "", infoRedIssueTime = "", informationHeadId = "0", informationSealId = "0";
      if (informationHead != null && informationHead.equals("1")) {
        informationHeadId = informationActionForm
          .getInformationHeadFile();
        informationHeadFile = getHeadFile(informationActionForm
            .getInformationHeadFile());
        informationSealId = informationActionForm.getInformationSeal();
        informationSeal = getSealFile(informationActionForm
            .getInformationSeal());
        informationMark = informationActionForm.getInformationMark();
        infoRedIssueOrg = informationActionForm.getInfoRedIssueOrg();
        String[] tmp = informationActionForm.getInfoRedIssueTime()
          .split("/");
        infoRedIssueTime = String.valueOf(tmp[0]) + "年" + tmp[1] + "月" + tmp[2] + "日";
      } 
      String wordDisplayType = request.getParameter("wordDisplayType");
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String displayImage = "1";
      if (request.getParameter("displayImage") != null)
        displayImage = request.getParameter("displayImage")
          .toString(); 
      String displayTitle = "1";
      if (request.getParameter("displayTitle") != null)
        displayTitle = request.getParameter("displayTitle"); 
      StringBuffer otherChannel = new StringBuffer();
      if (request.getParameterValues("otherChannel") != null) {
        String[] otherTmp = request
          .getParameterValues("otherChannel");
        for (int i = 0; i < otherTmp.length; i++)
          otherChannel.append("," + otherTmp[i] + ","); 
      } 
      String informationOrISODoc = (request
        .getParameter("informationOrISODoc") == null) ? "0" : 
        request.getParameter("informationOrISODoc")
        .toString();
      String isoDocStatus = (request
        .getParameter("isoDocStatus") == null) ? "" : 
        request.getParameter("isoDocStatus")
        .toString();
      String isoOldInfoId = (request
        .getParameter("isoOldInfoId") == null) ? "" : 
        request.getParameter("isoOldInfoId")
        .toString();
      String isoSecretStatus = (request
        .getParameter("isoSecretStatus") == null) ? "" : 
        request.getParameter("isoSecretStatus")
        .toString();
      String isoDealCategory = (request
        .getParameter("isoDealCategory") == null) ? "" : 
        request.getParameter("isoDealCategory")
        .toString();
      String isoApplyName = (request
        .getParameter("isoApplyName") == null) ? "" : 
        request.getParameter("isoApplyName")
        .toString();
      String isoApplyId = (request.getParameter("isoApplyId") == null) ? "" : 
        request.getParameter("isoApplyId").toString();
      String isoReceiveName = (request
        .getParameter("isoReceiveName") == null) ? "" : 
        request.getParameter("isoReceiveName")
        .toString();
      String isoReceiveId = (request
        .getParameter("isoReceiveId") == null) ? "" : 
        request.getParameter("isoReceiveId")
        .toString();
      String isoModifyReason = (request
        .getParameter("isoModifyReason") == null) ? "" : 
        request.getParameter("isoModifyReason")
        .toString();
      String isoAmendmentPage = (request
        .getParameter("isoAmendmentPage") == null) ? "" : 
        request.getParameter("isoAmendmentPage")
        .toString();
      String isoModifyVersion = (request
        .getParameter("isoModifyVersion") == null) ? "" : 
        request.getParameter("isoModifyVersion")
        .toString();
      String forbitCopy = (request.getParameter("forbidCopy") == null) ? "1" : "0";
      String[] parameters = { 
          informationTitle, 
          informationSubTitle, 
          informationSummary, 
          inforamtionKey, 
          informationContent, 
          userId, 
          userName, 
          orgName, 
          informationReaderName, 
          inforamtionReader, 
          informationReaderOrg, 
          inforamtionReaderGroup, 
          informationVaildType, 
          validBeginTime, 
          validEndTime, 
          informationHead, 
          informationHeadFile, 
          informationSeal, 
          informationMark, 
          infoRedIssueOrg, 
          infoRedIssueTime, 
          informationHeadId, 
          informationSealId, 
          request.getParameter("transmitToWebsite"), 
          forbitCopy, 
          request.getParameter("orderCode"), 
          displayTitle, 
          otherChannel.toString(), 
          informationAuthor, 
          request.getParameter("issueTime"), 
          titleColor, 
          request.getParameter("channelId"), 
          request.getParameter("mustRead"), 
          request.getParameter("comeFrom"), 
          request.getParameter("isConf"), 
          request.getParameter("documentNo"), 
          request.getParameter("documentEditor"), 
          request.getParameter("documentTypeFinal"), 
          displayImage, 
          wordDisplayType, 
          orgId, 
          informationOrISODoc, 
          isoDocStatus, 
          isoOldInfoId, 
          isoSecretStatus, 
          isoDealCategory, 
          isoApplyName, 
          isoApplyId, 
          isoReceiveName, 
          isoReceiveId, 
          isoModifyReason, 
          isoAmendmentPage, 
          isoModifyVersion, 
          httpSession.getAttribute("userName").toString(), 
          httpSession.getAttribute("orgName").toString(), 
          (request.getParameter("isAllow") == null) ? "0" : 
          request.getParameter("isAllow") };
      try {
        this.informationBD.update(informationId, parameters, request
            .getParameterValues("associateInfo"), request
            .getParameterValues("infoAppendName"), request
            .getParameterValues("infoAppendSaveName"), 
            request.getParameterValues("infoPicName"), 
            request.getParameterValues("infoPicSaveName"));
        if (!"6".equals(request.getParameter("informationType"))) {
          SearchService.getInstance();
          String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
          SearchService.getInstance();
          String isearchSwitch = SearchService.getiSearchSwitch();
          if ("1".equals(isearchSwitch) && informationId != null && ifActiveUpdateDelete != null && !"".equals(informationId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.updateIndex(informationId.toString(), "oa_information");
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "modifyClose";
      if (request.getParameter("remindUser") != null && "0".equals(request.getParameter("remindUser"))) {
        String title, toUserIds = this.informationBD.getCanReadUserIds(userId, informationId);
        if (toUserIds.equals("-1") && SystemCommon.getMultiDepart() == 1) {
          toUserIds = StaticParam.getEmpIdsByOrgId((String)session.getAttribute("corpId"));
          if (toUserIds.length() > 0)
            toUserIds = toUserIds.substring(0, toUserIds.length() - 1); 
        } 
        if (toUserIds.indexOf(userId) > -1)
          toUserIds = toUserIds.replaceAll(String.valueOf(userId) + ",", "").replaceAll(userId, ""); 
        if ("rws".equals(SystemCommon.getCustomerName())) {
          title = informationTitle;
        } else {
          title = String.valueOf(userName) + "修改了:" + informationTitle;
        } 
        if ("3".equals(request.getParameter("informationType"))) {
          String url = "/jsoa/info/view_onlyfile.jsp?informationId=" + informationId;
          RemindUtil.sendMessageToUsersWithType(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"), 3);
          if ("-1".equals(toUserIds))
            StaticParam.deleteMessage("Info", title, url, userId); 
        } 
        if ("6".equals(request.getParameter("informationType"))) {
          String[] informationHeadFileName = request.getParameter("informationHeadFileName").split("_");
          String url = ArchivesUtil.clickImg(informationHeadFileName[1], informationHeadFileName[0], "2");
          if (toUserIds.equals("-1")) {
            RemindUtil.sendMessagesExceptMe(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"), 1, userId);
          } else if (!toUserIds.equals("")) {
            RemindUtil.sendMessageToUsers(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"));
          } 
        } else {
          String url = "/jsoa/info/view_detail.jsp?informationId=" + informationId;
          if (toUserIds.equals("-1")) {
            RemindUtil.sendMessagesExceptMe(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"), 1, userId);
          } else if (!toUserIds.equals("")) {
            RemindUtil.sendMessageToUsers(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"));
          } 
        } 
        if ("tjqnzyxy".equals(SystemCommon.getCustomerName()) && 
          ",227,228,230,231,".indexOf("," + channelId + ",") > -1)
          (new MessageService()).sendMessages(channelId, "Info", informationHeadFile, Long.parseLong(informationId), userName, 
              orgName, userName, orgName, "", informationTitle, informationContent); 
      } 
    } else if (action.equals("deleteAccessory")) {
      List<Object[]> userViewlist;
      String accessoryId = request.getParameter("id");
      String informationId = request
        .getParameter("informationId");
      this.informationBD.saveHistory(informationId);
      request.setAttribute("channelType", channelType);
      InformationAccessoryBD informationAccessoryBD = new InformationAccessoryBD();
      String saveName = informationAccessoryBD
        .getAccessoryFile(accessoryId);
      DeleteFile deleteFile = new DeleteFile("information", saveName);
      deleteFile.delete();
      this.informationBD.deleteAccessory(informationId, accessoryId);
      tag = "modify";
      request.setAttribute("channelType", channelType);
      informationId = request.getParameter("informationId");
      String informationType = request
        .getParameter("informationType");
      request.setAttribute("informationId", informationId);
      request.setAttribute("informationType", informationType);
      request.setAttribute("redHead", request
          .getParameter("redHead"));
      if (request.getParameter("channelId") != null) {
        informationActionForm
          .setChannelId(Integer.parseInt(request
              .getParameter("channelId")));
        String channelName = request
          .getParameter("channelName");
        informationActionForm.setChannelName(channelName);
      } 
      List<Object[]> list = this.informationBD.getSingleInfo(informationId, 
          request.getParameter("channelId"));
      Object[] obj = list.get(0);
      informationActionForm.setInformationTitle(obj[0].toString());
      if (obj[1] == null) {
        informationActionForm.setInformationSubTitle("");
      } else {
        informationActionForm.setInformationSubTitle(obj[1].toString());
      } 
      String content = this.informationBD.getContent(informationId);
      if (content == null) {
        informationActionForm.setInformationContent("");
        request.setAttribute("content", "");
      } else {
        informationActionForm.setInformationContent(content);
        request.setAttribute("content", content);
      } 
      if (obj[9] == null) {
        informationActionForm.setInformationMark("");
      } else {
        informationActionForm.setInformationMark(obj[9].toString());
      } 
      if (obj[12] == null) {
        informationActionForm.setInfoRedIssueOrg("");
      } else {
        informationActionForm.setInfoRedIssueOrg(obj[12].toString());
      } 
      if (obj[14] == null) {
        informationActionForm.setInformationSummary("");
      } else {
        informationActionForm.setInformationSummary(obj[14].toString());
      } 
      if (obj[15] == null) {
        informationActionForm.setInformationKey("");
      } else {
        informationActionForm.setInformationKey(obj[15].toString());
      } 
      if (obj[16] == null) {
        informationActionForm.setInformationReaderName("");
      } else {
        informationActionForm.setInformationReaderName(obj[16]
            .toString());
      } 
      if (obj[17] == null) {
        informationActionForm.setInformationReader("");
      } else {
        informationActionForm.setInformationReader(obj[17].toString());
      } 
      if (obj[18] == null) {
        informationActionForm.setInformationReaderOrg("");
      } else {
        informationActionForm.setInformationReaderOrg(obj[18]
            .toString());
      } 
      if (obj[19] == null) {
        informationActionForm.setInformationReaderGroup("");
      } else {
        informationActionForm.setInformationReaderGroup(obj[19]
            .toString());
      } 
      request.setAttribute("informationValidType", obj[20]);
      request.setAttribute("validBeginTime", obj[21]);
      request.setAttribute("validEndTime", obj[22]);
      request.setAttribute("inforamtionHead", obj[23]);
      request.setAttribute("infoRedIssueTime", obj[13]);
      request.setAttribute("informationHeadId", obj[24]);
      request.setAttribute("informationSealId", obj[25]);
      request.setAttribute("forbidCopy", obj[26]);
      request.setAttribute("transmitToWebsite", obj[27]);
      request.setAttribute("orderCode", obj[30]);
      if (obj[32] != null) {
        request.setAttribute("otherChannel", obj[32]);
      } else {
        request.setAttribute("otherChannel", "");
      } 
      if (obj[24] != null)
        request.setAttribute("headSealList", null); 
      request.setAttribute("displayTitle", obj[31]);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      request.setAttribute("otherChannelList", 
          getAllCanIssueWithoutCheck(userId, orgId, domainId));
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
      list = informationAccessoryBD.getAccessory(request
          .getParameter("informationId"));
      request.setAttribute("accessoryList", list);
      if (SystemCommon.getMultiDepart() == 1) {
        userViewlist = channelBD.getUserViewCh(userId, orgId, 
            channelType, this.informationBD
            .getInfoUserdefine(request
              .getParameter("informationId")), domainId, httpSession.getAttribute("corpId").toString(), httpSession.getAttribute("sidelineCorpId").toString());
      } else {
        userViewlist = channelBD.getUserViewCh(userId, orgId, 
            channelType, this.informationBD
            .getInfoUserdefine(request
              .getParameter("informationId")), domainId, "", "");
      } 
      String canReadChannel = "";
      if (userViewlist != null && userViewlist.size() > 0 && 
        userViewlist.get(0) != null) {
        obj = (Object[])null;
        for (int i = 0; i < userViewlist.size(); i++) {
          obj = userViewlist.get(i);
          canReadChannel = String.valueOf(canReadChannel) + "$" + obj[0] + "$";
        } 
      } 
      if ("-1".equals(channelType) && "0".equals(userDefine1))
        channelType = "2"; 
      request.setAttribute("assoicateInfo", (
          new InformationBD()).getAssociateInfo(orgId, 
            request.getParameter("informationId"), 
            userId, httpSession.getAttribute("orgIdString")
            .toString(), channelType, userDefine1, 
            channelStatusType));
    } else if (action.equals("myIssue")) {
      String depart = request.getParameter("depart");
      request.setAttribute("depart", depart);
      if (depart == null) {
        tag = "myIssue";
      } else {
        tag = "myIssueDepa";
        String styleId = request.getParameter("styleId");
        request.setAttribute("styleId", styleId);
        request.setAttribute("stylePO", (new ChannelBD())
            .getDepaStyle(styleId));
      } 
      HttpSession httpSession = request.getSession(true);
      String curUserID = request.getParameter("userId");
      if (curUserID == null || "null".equals(curUserID) || 
        "".equals(curUserID))
        curUserID = httpSession.getAttribute("userId").toString(); 
      String viewSQL = " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,aaa.informationSummary, aaa.informationHead,  aaa.informationType,aaa.informationCommonNum,bbb.channelId,bbb.channelName,aaa.documentNo,bbb.channelNeedCheckup,aaa.informationModifyTime,aaa.informationHeadFile ";
      String fromSQL = " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
      String whereSQL = " where informationIssuerId = " + 
        curUserID + 
        " and bbb.channelType = " + 
        channelType + 
        " and aaa.informationStatus=0 and aaa.domainId=" + 
        domainId + 
        " and ( aaa.afficeHistoryDate is null or aaa.afficeHistoryDate= -1 ) and (aaa.informationOrISODoc is null or aaa.informationOrISODoc='0' )";
      String title = (request.getParameter("title") == null) ? "" : 
        request.getParameter("title");
      request.setAttribute("title", title);
      String date = (request.getParameter("date") == null) ? "" : 
        request.getParameter("date");
      request.setAttribute("date", date);
      if (title != null && title.length() != 0)
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationTitle like '%" + title + "%'"; 
      if (date != null && date.length() != 0) {
        date = date.replaceAll("/", "-");
        String dataType = 
          SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime>='" + date + 
            " 00:00:00'";
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<='" + date + 
            " 23:59:59'";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime>=JSDB.FN_STRTODATE('" + 
            date + " " + "00:00:00" + "','L')";
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<=JSDB.FN_STRTODATE('" + 
            date + " " + "23:59:59" + "','L')";
        } 
      } 
      String orderBy = request.getParameter("orderBy");
      if (orderBy == null || orderBy.equals("") || orderBy.equals("null"))
        orderBy = "default"; 
      String sortType = request.getParameter("sortType");
      if (sortType == null || sortType.equals("") || 
        sortType.equals("null"))
        sortType = "desc"; 
      if (orderBy.equals("date")) {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
          sortType;
      } else if (orderBy.equals("kits")) {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.informationKits " + 
          sortType;
      } else if (orderBy.equals("commonNum")) {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.informationCommonNum " + 
          sortType;
      } else if (orderBy.equals("modifydate")) {
        whereSQL = String.valueOf(whereSQL) + " order by aaa.informationModifyTime " + 
          sortType;
      } else {
        whereSQL = String.valueOf(whereSQL) + 
          " order by aaa.orderCode desc,aaa.informationModifyTime desc,aaa.informationIssueTime desc, aaa.informationId desc";
      } 
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request
            .getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSQL, fromSQL, whereSQL);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("informationList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      if (depart != null) {
        request
          .setAttribute("pageParameters", 
            "action,channelType,userChannelName,orderBy,depart,styleId,sortType,userId");
      } else {
        request
          .setAttribute("pageParameters", 
            "action,channelType,userChannelName,orderBy,sortType,userId");
      } 
    } else if (action.equals("addNote")) {
      request.setAttribute("noteLink", 
          "/jsoa/InformationAction.do?action=openInfo&informationId=" + 
          request.getParameter("informationId") + 
          "&redHead=" + 
          request.getParameter("redHead") + 
          "&userChannelName=" + 
          userChannelName + 
          "&channelType=" + 
          request.getParameter("channelType") + 
          "&informationType=" + 
          request
          .getParameter("informationType"));
      request.setAttribute("noteTitle", request
          .getParameter("informationTitle"));
      request.setAttribute("userChannelName", userChannelName);
      request.setAttribute("noteClassName", "信息");
      tag = "note";
    } else if (action.equals("gd")) {
      if (gd(request))
        request.setAttribute("gdresult", "1"); 
      String gd = (String)request.getAttribute("gd");
      request.setAttribute("flag", "1");
      request.setAttribute("gd", gd);
      tag = "gd";
    } 
    if (action.equals("add") || "add".equals(tag)) {
      tag = "add";
      request.setAttribute("channelType", channelType);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString")
        .toString();
      channelId = request.getParameter("channelId");
      String channelName = request.getParameter("channelName");
      request.setAttribute("channelId", channelId);
      request.setAttribute("channelName", channelName);
      String publicchannel = request.getParameter("publicchannel");
      List<Object[]> channelList = channelBD.getSingleChannel(publicchannel);
      String isAllow = "0";
      if (channelList != null && channelList.size() > 0) {
        Object[] ch = (Object[])null;
        ch = channelList.get(0);
        isAllow = ch[28].toString();
      } 
      request.setAttribute("isAllow", isAllow);
      if (isIso.equals("1")) {
        List list = getIsoCanIssueChannel(userId, orgId, orgIdString, 
            domainId);
        request.setAttribute("canIssueChannel", list);
        request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
        tag = "isoNoAdd";
      } else {
        List list = getCanIssueChannel(userId, orgId, orgIdString, domainId);
        request.setAttribute("canIssueChannel", list);
        request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
      } 
      String isAllowView = null;
      if (channelList != null && channelList.size() > 0) {
        Object[] ch = (Object[])null;
        ch = channelList.get(0);
        isAllowView = ch[28].toString();
      } 
      request.setAttribute("isAllowView", isAllowView);
      List templateList = getTemplate(request);
      if (templateList != null) {
        request.setAttribute("templateList", templateList);
      } else {
        request.setAttribute("templateList", new ArrayList());
      } 
      String publicChannel = request.getParameter("publicchannel");
      if (publicChannel != null && !"null".equals(publicChannel) && !"".equals(publicChannel)) {
        setChannelViewer(request, publicChannel);
      } else if (request.getParameter("newaddress1") != null) {
        setChannelViewer(request);
      } 
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
    } else if (action.equals("otherAdd")) {
      tag = "otherAdd";
      request.setAttribute("channelType", channelType);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      channelId = request.getParameter("channelId");
      String channelName = request.getParameter("channelName");
      request.setAttribute("channelId", channelId);
      request.setAttribute("channelName", channelName);
      List list = getCanIssueChannel(userId, orgId, orgIdString, domainId);
      request.setAttribute("canIssueChannel", list);
      request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
      List templateList = getTemplate(request);
      if (templateList != null) {
        request.setAttribute("templateList", templateList);
      } else {
        request.setAttribute("templateList", new ArrayList());
      } 
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
    } else if (action.equals("adds")) {
      tag = "adds";
      request.setAttribute("channelType", channelType);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      channelId = request.getParameter("channelId");
      String channelName = request.getParameter("channelName");
      request.setAttribute("channelId", channelId);
      request.setAttribute("channelName", channelName);
      List list = getCanIssueChannel(userId, orgId, orgIdString, domainId);
      request.setAttribute("canIssueChannel", list);
      request.setAttribute("otherChannel", getAllCanIssueWithoutCheck(userId, orgId, domainId));
      List<Object[]> channelList = channelBD.getSingleChannel(channelId);
      String isAllowView = null;
      if (channelList != null && channelList.size() > 0) {
        Object[] ch = (Object[])null;
        ch = channelList.get(0);
        isAllowView = ch[28].toString();
      } 
      request.setAttribute("isAllowView", isAllowView);
      List templateList = getTemplate(request);
      if (templateList != null) {
        request.setAttribute("templateList", templateList);
      } else {
        request.setAttribute("templateList", new ArrayList());
      } 
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
      if (request.getParameter("newaddress1") != null)
        setChannelViewer(request); 
      if (request.getParameter("isAffiche") != null && 
        request.getParameter("isAffiche").toString()
        .equals("1"))
        tag = "afficheadds"; 
      if (request.getParameter("afficheReaderId") != null && 
        
        !request.getParameter("afficheReaderId").toString().equals(""))
        request.setAttribute("afficheReaderId", 
            request.getParameter("afficheReaderId")); 
      if (request.getParameter("afficheReaderName") != null && 
        
        !request.getParameter("afficheReaderName").toString().equals(""))
        request.setAttribute("afficheReaderName", 
            request.getParameter("afficheReaderName")); 
    } else if (action.equals("isoAdd")) {
      tag = "adds";
      request.setAttribute("channelType", channelType);
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString")
        .toString();
      channelId = request.getParameter("channelId");
      String channelName = request.getParameter("channelName");
      request.setAttribute("channelId", channelId);
      request.setAttribute("channelName", channelName);
      List list = getIsoCanIssueChannel(userId, orgId, orgIdString, 
          domainId);
      request.setAttribute("canIssueChannel", list);
      request.setAttribute("otherChannel", 
          getAllCanIssueWithoutCheck(userId, orgId, domainId));
      List templateList = getTemplate(request);
      if (templateList != null) {
        request.setAttribute("templateList", templateList);
      } else {
        request.setAttribute("templateList", 
            new ArrayList());
      } 
      request.setAttribute("headList", getHead(userId, orgId));
      request.setAttribute("sealList", getSeal());
      if (request.getParameter("newaddress1") != null)
        setChannelViewer(request); 
      tag = "isoAdd";
    } else if ("proView".equals(action)) {
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      request.setAttribute("userChannelName", request.getParameter("userChannelName"));
      String s = request.getParameter("informationTitle");
      request.setAttribute("informationTitle", request.getParameter("informationTitle"));
      String forbidCopy = request.getParameter("forbidCopy");
      request.setAttribute("forbidCopy", (forbidCopy == null) ? "0" : forbidCopy);
      request.setAttribute("informationSubTitle", request.getParameter("informationSubTitle"));
      request.setAttribute("informationIssuer", request.getParameter("informationIssuer"));
      request.setAttribute("informationIssueOrg", request.getParameter("informationIssueOrg"));
      request.setAttribute("informationIssueTime", request.getParameter("informationIssueTime"));
      request.setAttribute("informationModifyTime", request.getParameter("informationModifyTime"));
      request.setAttribute("informationVersion", request.getParameter("informationVersion"));
      request.setAttribute("informationAuthor", request.getParameter("informationAuthor"));
      request.setAttribute("modifyEmp", request.getParameter("modifyEmp"));
      request.setAttribute("dossierStatus", request.getParameter("dossierStatus"));
      request.setAttribute("documentNo", request.getParameter("documentNo"));
      request.setAttribute("informationKey", request.getParameter("informationKey"));
      request.setAttribute("informationSummary", request.getParameter("informationSummary"));
      request.setAttribute("documentType", request.getParameter("documentType"));
      request.setAttribute("wordDisplayType", request.getParameter("documentType"));
      int displayTitle = 1;
      if (request.getParameter("displayTitle") != null)
        displayTitle = Integer.parseInt(request.getParameter("displayTitle")); 
      request.setAttribute("displayTitle", Integer.valueOf(displayTitle));
      request.setAttribute("displayImage", request.getParameter("displayImage"));
      request.setAttribute("isoDealCategory", request.getParameter("isoDealCategory"));
      request.setAttribute("isoModifyReason", request.getParameter("isoModifyReason"));
      request.setAttribute("isoAmendmentPage", request.getParameter("isoAmendmentPage"));
      request.setAttribute("inforModifyMen", request.getParameter("inforModifyMen"));
      request.setAttribute("inforModifyOrg", request.getParameter("inforModifyOrg"));
      String informationType = request.getParameter("informationType");
      request.setAttribute("informationType", informationType);
      String content = informationActionForm.getInformationContent();
      request.setAttribute("content", informationActionForm.getInformationContent());
      String redHead = request.getParameter("redHead");
      request.setAttribute("redHead", redHead);
      if (informationType.equals("3")) {
        if (content != null && content.toString().length() > 1) {
          String[] tmp2 = content.toString().split(":");
          request.setAttribute("midType", 
              "fileLink");
          if (tmp2.length == 2) {
            request.setAttribute("fileName", tmp2[0]);
            request.setAttribute("saveName", tmp2[1]);
          } 
        } 
        tag = "fileLink";
        if (request.getParameter("isAffiche") != null && 
          request.getParameter("isAffiche").toString().equals("1"))
          tag = "AfficefileLink"; 
      } else {
        if (content == null) {
          request.setAttribute("content", "");
        } else {
          request.setAttribute("content", content);
        } 
        if (redHead != null && redHead.equals("1"))
          tag = "hasRedHead"; 
        request.setAttribute("redHead", redHead);
        if (tag.equals("hasRedHead")) {
          request.setAttribute("informationMark", request.getParameter("informationMark"));
          request.setAttribute("informationHeadFile", request.getParameter("informationHeadFile"));
          request.setAttribute("informationSeal", request.getParameter("informationSeal"));
          request.setAttribute("infoRedIssueOrg", request.getParameter("infoRedIssueOrg"));
          request.setAttribute("infoRedIssueTime", request.getParameter("infoRedIssueTime"));
        } 
      } 
      String depart = request.getParameter("depart");
      if (depart != null) {
        request.setAttribute("depart", depart);
        request.setAttribute("canVindicate", (
            new DepartmentPageBD()).deptVindicateInfo(userId, orgId, channelType));
        request.setAttribute("infoDepaFlag", request.getParameter("infoDepaFlag"));
        request.setAttribute("infoDepaFlag2", request.getParameter("infoDepaFlag2"));
      } 
      request.setAttribute("address", request.getParameter("address"));
      return actionMapping.findForward(action);
    } 
    if (tag.equals("noRedHead") && 
      request.getParameter("isAffiche") != null && 
      request.getParameter("isAffiche").toString()
      .equals("1"))
      tag = "afficheNoRedHead"; 
    if ("selectLM".equals(action))
      return actionMapping.findForward("selectLM"); 
    if ("export".equals(action)) {
      String title = request.getParameter("title");
      String subTitle = request.getParameter("subTitle");
      String key = request.getParameter("key");
      String searchIssuerName = request.getParameter("searchIssuerName");
      String orgIds = request.getParameter("orgIds");
      String append = request.getParameter("append");
      String searchDate = request.getParameter("searchDate");
      String beginDate = request.getParameter("beginDate");
      String endDate = request.getParameter("endDate");
      String selIds = request.getParameter("selIds");
      String view = "aaa.informationId,aaa.informationTitle,aaa.informationIssuer,aaa.informationIssueOrg,ccc.orgManagerEmpName";
      String from = " com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb,com.js.system.vo.organizationmanager.OrganizationVO ccc ";
      String where = " where informationStatus=0 and aaa.corpId=ccc.orgId and bbb.channelId=" + channelId + " ";
      if (title != null && !"".equals(title))
        where = String.valueOf(where) + " and aaa.informationTitle like '%" + title + "%' "; 
      if (subTitle != null && !"".equals(subTitle))
        where = String.valueOf(where) + " and aaa.informationSubTitle like '%" + subTitle + "%' "; 
      if (key != null && !"".equals(key))
        where = String.valueOf(where) + " and aaa.informationKey like '%" + key + "%' "; 
      if (searchIssuerName != null && !"".equals(searchIssuerName))
        where = String.valueOf(where) + " and aaa.informationIssuer like '%" + searchIssuerName + "%' "; 
      if (orgIds != null && !"".equals(orgIds)) {
        if (orgIds.endsWith(","))
          orgIds = orgIds.substring(0, orgIds.length() - 1); 
        where = String.valueOf(where) + " and aaa.corpId in (" + orgIds + ") ";
      } 
      if (searchDate != null && "1".equals(searchDate))
        where = String.valueOf(where) + " and aaa.informationIssueTime>='" + beginDate + "' and aaa.informationIssueTime<='" + endDate + "' "; 
      if (selIds != null && !"".equals(selIds)) {
        if (selIds.startsWith(","))
          selIds = selIds.substring(1); 
        where = String.valueOf(where) + " and aaa.informationId in (" + selIds + ") ";
      } 
      where = String.valueOf(where) + "order by aaa.informationId desc";
      Page page = new Page(view, from, where);
      request.setAttribute("list", page.getResultList());
      return actionMapping.findForward("export");
    } 
    return actionMapping.findForward(tag);
  }
  
  public List getTemplate(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String userId = httpSession.getAttribute("userId").toString();
    TemplateBD templateBD = new TemplateBD();
    String where = (new ManagerService()).getScopeFinalWhere(userId, httpSession
        .getAttribute("orgId").toString(), httpSession
        .getAttribute("orgIdString").toString(), "aaa.useEmp", 
        "aaa.useOrg", "aaa.useGroup");
    where = "((aaa.ispublic=0 and aaa.createdEmp=" + userId + ") or (aaa.ispublic=1 and (" + where + ")))";
    List list = templateBD.getAvailableTemplateByUser(where, domainId, "1");
    return list;
  }
  
  public InformationPO setPO(InformationActionForm informationActionForm, HttpServletRequest request) {
    InformationPO informationPO = new InformationPO();
    HttpSession httpSession = request.getSession(true);
    informationPO.setIssueOrgIdString(httpSession.getAttribute("orgIdString").toString());
    informationPO.setMustRead(Integer.valueOf(request.getParameter("mustRead")));
    informationPO.setInformationTitle(informationActionForm.getInformationTitle());
    String forbidCopy = (request.getParameter("forbidCopy") == null) ? "1" : "0";
    if (request.getParameter("displayColor") == null) {
      informationPO.setTitleColor(new Integer(0));
    } else {
      informationPO.setTitleColor(new Integer(1));
    } 
    if (informationActionForm.getIsAllow() == null || 
      informationActionForm.getIsAllow().equals("")) {
      informationPO.setIsAllow("0");
    } else {
      informationPO.setIsAllow("1");
    } 
    informationPO.setDossierStatus(Integer.valueOf("0"));
    informationPO.setInformationSubTitle(informationActionForm
        .getInformationSubTitle());
    informationPO.setInformationSummary(informationActionForm
        .getInformationSummary());
    informationPO.setInformationReader(informationActionForm.getInformationReader());
    informationPO.setInformationReaderOrg(informationActionForm
        .getInformationReaderOrg());
    informationPO.setInformationReaderGroup(informationActionForm
        .getInformationReaderGroup());
    informationPO.setInformationReaderName(informationActionForm
        .getInformationReaderName());
    informationPO.setInformationAuthor(informationActionForm
        .getInformationAuthor());
    Calendar now = Calendar.getInstance();
    informationPO.setModifyEmp("");
    informationPO.setInformationIssueOrgId(httpSession
        .getAttribute("orgId").toString());
    if (request.getParameter("issueTime") != null) {
      String[] issueTime = request.getParameter("issueTime")
        .split("/");
      now
        .set(Integer.parseInt(issueTime[0]), 
          Integer.parseInt(issueTime[1]) - 1, 
          Integer.parseInt(issueTime[2]));
    } 
    informationPO.setInformationIssueTime(now.getTime());
    informationPO.setInformationModifyTime(new Date());
    informationPO.setInformationIssuer(httpSession.getAttribute("userName")
        .toString());
    informationPO.setInformationIssueOrg(httpSession
        .getAttribute("orgName").toString());
    informationPO.setInforModifyMen(httpSession.getAttribute("userName")
        .toString());
    informationPO.setInforModifyOrg(httpSession.getAttribute("orgName")
        .toString());
    informationPO.setInformationContent(informationActionForm
        .getInformationContent());
    informationPO.setInformationKey(informationActionForm
        .getInformationKey());
    informationPO.setInformationIssuerId(new Long((String)httpSession
          .getAttribute("userId")));
    informationPO.setInformationVersion("1.0");
    informationPO.setInformationType(informationActionForm
        .getInformationType());
    informationPO.setInformationCommonNum(0);
    informationPO.setForbidCopy(Integer.parseInt(forbidCopy));
    informationPO.setTransmitToWebsite(Integer.parseInt(request
          .getParameter("transmitToWebsite")));
    if (request.getParameter("orderCode").equals("")) {
      informationPO.setOrderCode("1000");
    } else {
      informationPO.setOrderCode(request
          .getParameter("orderCode"));
    } 
    int displayTitle = 1;
    if (request.getParameter("displayTitle") != null)
      displayTitle = Integer.parseInt(request.getParameter("displayTitle")); 
    informationPO.setDisplayTitle(displayTitle);
    String displayImage = "1";
    if (request.getParameter("displayImage") != null)
      displayImage = request.getParameter("displayImage"); 
    informationPO.setDisplayImage(displayImage);
    String[] otherChannel = request
      .getParameterValues("otherChannel");
    if (otherChannel != null) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < otherChannel.length; i++)
        sb.append("," + otherChannel[i] + ","); 
      informationPO.setOtherChannel(sb.toString());
    } 
    String informationValidType = "0";
    if (informationActionForm.getInformationVaildType() != null && 
      !informationActionForm.getInformationVaildType().equals("")) {
      informationValidType = informationActionForm
        .getInformationVaildType();
    } else {
      informationValidType = request
        .getParameter("informationValidType");
    } 
    informationPO.setInformationValidType(
        Integer.parseInt(informationValidType));
    if (informationValidType.equals("1")) {
      informationPO.setValidBeginTime(new Date(
            informationActionForm.getValidBeginTime()));
      informationPO.setValidEndTime(new Date(
            informationActionForm.getValidEndTime()));
    } 
    informationPO.setInformationHead(Integer.parseInt(informationActionForm
          .getInformationHead()));
    if (informationActionForm.getInformationHead().equals("1")) {
      informationPO.setInformationHeadId(new Long(informationActionForm.getInformationHeadFile()));
      informationPO.setInformationHeadFile(getHeadFile(informationActionForm.getInformationHeadFile()));
      informationPO.setInformationSealId(new Long(informationActionForm.getInformationSeal()));
      informationPO.setInformationSeal(getSealFile(informationActionForm.getInformationSeal()));
      informationPO.setInformationMark(informationActionForm.getInformationMark());
      informationPO.setInfoRedIssueOrg(informationActionForm.getInfoRedIssueOrg());
      String[] tmp = informationActionForm.getInfoRedIssueTime().split("/");
      informationPO.setInfoRedIssueTime(String.valueOf(tmp[0]) + "年" + tmp[1] + "月" + 
          tmp[2] + "日");
    } 
    ChannelBD channelBD = new ChannelBD();
    informationPO.setInformationChannel(channelBD
        .loadChannel((new StringBuilder(String.valueOf(informationActionForm.getChannelId()))).toString()));
    informationPO.setComeFrom(request.getParameter("comeFrom"));
    if (request.getParameter("isConf") != null) {
      informationPO.setIsConf(Integer.valueOf("1"));
    } else {
      informationPO.setIsConf(Integer.valueOf("0"));
    } 
    if (request.getParameter("documentNo") != null)
      informationPO.setDocumentNo(request.getParameter("documentNo")); 
    if (request.getParameter("documentNo").length() == 0)
      informationPO.setDocumentNo("0"); 
    informationPO.setWordDisplayType(informationActionForm.getWordDisplayType());
    informationPO.setDocumentEditor(request.getParameter("documentEditor"));
    informationPO.setDocumentType(request.getParameter("documentTypeFinal"));
    String domainId = "";
    if (httpSession.getAttribute("domainId") == null) {
      domainId = "";
    } else {
      domainId = httpSession.getAttribute("domainId").toString();
    } 
    if ("".equals(domainId)) {
      informationPO.setDomainId(new Long(0L));
    } else {
      informationPO.setDomainId(Long.valueOf(domainId));
    } 
    String informationOrISODoc = "0";
    String isoDocStatus = "0";
    String isoOldInfoId = "";
    String isoSecretStatus = "0";
    if (request.getParameter("isISO") != null && 
      request.getParameter("isISO").toString().equals(
        "1")) {
      informationOrISODoc = "1";
      isoDocStatus = "0";
      isoOldInfoId = "";
      if (request.getParameter("isoSecretStatus") != null && 
        
        !request.getParameter("isoSecretStatus").toString().equals(""))
        isoSecretStatus = request.getParameter("isoSecretStatus"); 
      informationPO.setInformationVersion("1.0");
    } 
    informationPO.setInformationOrISODoc(informationOrISODoc);
    informationPO.setIsoDocStatus(isoDocStatus);
    informationPO.setIsoOldInfoId(isoOldInfoId);
    informationPO.setIsoSecretStatus(isoSecretStatus);
    if (request.getParameter("isoDealCategory") != null) {
      informationPO.setIsoDealCategory(request
          .getParameter("isoDealCategory"));
    } else {
      informationPO.setIsoDealCategory("");
    } 
    if (request.getParameter("isoApplyName") != null) {
      informationPO.setIsoApplyName(request
          .getParameter("isoApplyName"));
    } else {
      informationPO.setIsoApplyName("");
    } 
    if (request.getParameter("isoApplyId") != null) {
      informationPO.setIsoApplyId(request
          .getParameter("isoApplyId"));
    } else {
      informationPO.setIsoApplyId("");
    } 
    if (request.getParameter("isoReceiveName") != null) {
      informationPO.setIsoReceiveName(request
          .getParameter("isoReceiveName"));
    } else {
      informationPO.setIsoReceiveName("");
    } 
    if (request.getParameter("isoReceiveId") != null) {
      informationPO.setIsoReceiveId(request
          .getParameter("isoReceiveId"));
    } else {
      informationPO.setIsoReceiveId("");
    } 
    if (request.getParameter("isoModifyReason") != null) {
      informationPO.setIsoModifyReason(request
          .getParameter("isoModifyReason"));
    } else {
      informationPO.setIsoModifyReason("");
    } 
    if (request.getParameter("isoAmendmentPage") != null && 
      
      !request.getParameter("isoAmendmentPage").toString().equals("")) {
      informationPO.setIsoAmendmentPage(request
          .getParameter("isoAmendmentPage"));
    } else {
      informationPO.setIsoAmendmentPage("");
    } 
    if (request.getParameter("isoModifyVersion") != null && 
      
      !request.getParameter("isoModifyVersion").toString().equals("")) {
      informationPO.setIsoModifyVersion(request
          .getParameter("isoModifyVersion"));
    } else {
      informationPO.setIsoModifyVersion("");
    } 
    return informationPO;
  }
  
  public List getHead(String userId, String orgId) {
    return new ArrayList();
  }
  
  public List getSeal() {
    return new ArrayList();
  }
  
  public String getHeadFile(String headId) {
    return "";
  }
  
  public String getSealFile(String sealId) {
    return "";
  }
  
  public List getCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    return channelBD.getCanIssue(where, domainId);
  }
  
  public List getIsoCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    return channelBD.getIsoCanIssue(where, domainId);
  }
  
  public List getAfficheCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    return channelBD.getAfficheCanIssue(where, domainId);
  }
  
  public List getAllCanIssueWithoutCheck(String userId, String orgId, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    return channelBD.getAllCanIssueWithoutCheck(userId, orgId, domainId, 
        "test");
  }
  
  private void list(HttpServletRequest request, String viewSQL, String fromSQL, String whereSQL, boolean canVindicate) {
    int pageSize = 15;
    if (request.getParameter("channelShowType") != null && 
      request.getParameter("channelShowType").equals("1"))
      pageSize = 15; 
    int listType = 0;
    if (request.getParameter("listType") != null)
      listType = Integer.parseInt(request
          .getParameter("listType")); 
    if (listType == 1)
      pageSize = 30; 
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    if (list != null && list.size() < 1) {
      currentPage--;
      if (currentPage > 0) {
        page.setcurrentPage(currentPage);
        list = page.getResultList();
      } 
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    if (!canVindicate) {
      HttpSession httpSession = request.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString")
        .toString();
      String channelId = request.getParameter("channelId");
      ManagerService mbd = new ManagerService();
      List rightList = mbd.getRightScope(userId, "01*03*03");
      if (rightList != null && rightList.size() > 0 && 
        rightList.get(0) != null) {
        ChannelBD channelBD = new ChannelBD();
        if (channelBD.isChannelManager(channelId, userId, orgId, 
            orgIdString)) {
          StringBuffer inforIds = new StringBuffer();
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            inforIds.append(obj[0]).append(",");
          } 
          inforIds.append("-1");
          String ids = (new InformationBD()).getInformationModiIds(channelId, userId, orgId, orgIdString, inforIds.toString(), rightList);
          request.setAttribute("canModifiedIds", ids);
        } 
      } 
    } 
    request.setAttribute("informationList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute(
        "pageParameters", 
        "action,channelId,channelName,channelType,userChannelName,channelShowType,orderBy,sortType,listType,userDefine");
  }
  
  private void view(HttpServletRequest request, String channelId, boolean canVindicate, String orderBy, String sortType, String includeChild) {
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowString = format.format(now);
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String channelType = request.getParameter("channelType");
    String userDefine = request.getParameter("userDefine");
    String domainId = httpSession.getAttribute("domainId").toString();
    String viewSQL = "";
    if (request.getParameter("channelShowType") != null && request.getParameter("channelShowType").equals("1")) {
      viewSQL = " aaa.informationId,aaa.informationTitle,aaa.informationHead, aaa.informationType,aaa.validBeginTime,aaa.validEndTime, aaa.informationValidType,aaa.titleColor,aaa.informationIssueTime, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime";
    } else {
      viewSQL = " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,  aaa.informationSummary, aaa.informationHead, aaa.informationType,  aaa.validBeginTime, aaa.validEndTime, aaa.informationCommonNum,  aaa.informationIssuer,aaa.informationValidType,aaa.titleColor, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime";
    } 
    viewSQL = String.valueOf(viewSQL) + ",bbb.channelNeedCheckup,aaa.informationHeadFile,aaa.orderCodeTemp,aaa.informationReader,aaa.informationIssuerId,aaa.oid,aaa.reprocess";
    String fromSQL = " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
    String whereSQL = "";
    if (!this.informationBD.channelCanView2(userId, orgId, channelType, userDefine, channelId)) {
      whereSQL = " where (1>2) ";
    } else {
      if (canVindicate) {
        if (includeChild.equals("0")) {
          whereSQL = " where (bbb.channelId = " + channelId + " or aaa.otherChannel like '%," + channelId + ",%') ";
        } else if (includeChild.equals("1")) {
          String str1 = SystemCommon.getDatabaseType();
          String childChannelIds = this.informationBD.getAllChildChannelIds(channelId);
          whereSQL = "where (bbb.channelId in(" + childChannelIds + ") or ";
          if (str1.indexOf("mysql") >= 0) {
            whereSQL = String.valueOf(whereSQL) + "('" + childChannelIds + "' like concat('%',aaa.otherChannel,'%') and aaa.otherChannel<>''))";
          } else {
            whereSQL = String.valueOf(whereSQL) + "('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', aaa.otherChannel), '%') and aaa.otherChannel is not null))";
          } 
        } 
      } else {
        String str = SystemCommon.getDatabaseType();
        if (str.indexOf("mysql") >= 0) {
          whereSQL = " where ( aaa.informationValidType = 0 or '" + 
            nowString + 
            "' between aaa.validBeginTime and aaa.validEndTime )";
          if (includeChild.equals("1")) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            String childChannelIds = "0";
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, channelId);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId in(" + childChannelIds + ") or ";
            whereSQL = String.valueOf(whereSQL) + "('" + childChannelIds + "' like concat('%',aaa.otherChannel,'%') and aaa.otherChannel<>''))";
          } else {
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + channelId + " or aaa.otherChannel like '%," + channelId + ",%') ";
          } 
        } else {
          whereSQL = " where ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
            nowString + 
            "','S') between aaa.validBeginTime and aaa.validEndTime )";
          if (includeChild.equals("1")) {
            ChannelEJBBean cbean = new ChannelEJBBean();
            String childChannelIds = "0";
            try {
              childChannelIds = cbean.getUserViewChildCh(userId, orgId, channelId);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId  in  (" + childChannelIds + ")  or ";
            whereSQL = String.valueOf(whereSQL) + "('" + childChannelIds + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%', aaa.otherChannel), '%') and aaa.otherChannel is not null))";
          } else {
            whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + channelId + 
              " or aaa.otherChannel like '%," + channelId + 
              ",%') ";
          } 
        } 
      } 
      if (request.getParameter("isAffiche") != null && request.getParameter("isAffiche").toString().equals("1")) {
        whereSQL = String.valueOf(whereSQL) + " and ( bbb.afficheChannelStatus='1' )";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and ( bbb.afficheChannelStatus is null or bbb.afficheChannelStatus='0' )";
      } 
      InformationBD infoBD = new InformationBD();
      String readerWhere = "1<>2";
      if (!canVindicate)
        readerWhere = infoBD.getInfoReader(userId, orgId, orgIdString, 
            "aaa"); 
      String rightWhere = "1>2";
      if ((new ChannelBD()).isChannelManager(channelId, userId, orgId, orgIdString))
        rightWhere = (new ManagerService()).getRightFinalWhere(userId, 
            orgId, "01*03*03", "aaa.informationIssueOrgId", 
            "aaa.informationIssuerId"); 
      StringBuffer channelReaderWhere = new StringBuffer("(aaa.informationReader is null or aaa.informationReader='') and ");
      channelReaderWhere.append("(aaa.informationReaderOrg is null or aaa.informationReaderOrg='') and ");
      channelReaderWhere.append("(aaa.informationReaderGroup is null or aaa.informationReaderGroup='') and (");
      channelReaderWhere.append("bbb.channelReader like '%$" + userId + "$%' ");
      DbOpt db = new DbOpt();
      try {
        String[] res = db.executeQueryToStrArr1("select org_id from org_organization_user where emp_id=" + userId);
        if (res != null && res.length > 0)
          for (int i = 0; i < res.length; i++)
            channelReaderWhere.append(" or bbb.channelReaderOrg like '%*" + res[i] + "*%'");  
        res = db.executeQueryToStrArr1("select group_id from org_user_group where emp_id=" + userId);
        if (res != null && res.length > 0)
          for (int i = 0; i < res.length; i++)
            channelReaderWhere.append(" or bbb.channelReaderGroup like '%@" + res[i] + "@%'");  
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          db.close();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
      channelReaderWhere.append(")");
      whereSQL = String.valueOf(whereSQL) + " and ((" + readerWhere + ") or (" + channelReaderWhere.toString() + ") or (" + 
        rightWhere + ")) and aaa.informationStatus=0 ";
    } 
    whereSQL = String.valueOf(whereSQL) + " and aaa.domainId=" + domainId;
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<='" + nowString + "'";
    } else {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<=JSDB.FN_STRTODATE('" + nowString + "','S')";
    } 
    if (orderBy == null) {
      whereSQL = String.valueOf(whereSQL) + 
        " order by aaa.orderCodeTemp desc,aaa.informationModifyTime desc, aaa.informationIssueTime desc, aaa.informationId desc";
    } else if (orderBy.equals("date")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
        sortType;
    } else if (orderBy.equals("kits")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationKits " + sortType;
    } else if (orderBy.equals("commonNum")) {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationCommonNum " + 
        sortType;
    } else {
      whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
        sortType;
    } 
    list(request, viewSQL, fromSQL, whereSQL, canVindicate);
  }
  
  private void list_pro(HttpServletRequest request, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    if (request.getParameter("channelShowType") != null && 
      request.getParameter("channelShowType").equals("1"))
      pageSize = 15; 
    int listType = 0;
    if (request.getParameter("listType") != null)
      listType = Integer.parseInt(request
          .getParameter("listType")); 
    if (listType == 1)
      pageSize = 30; 
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    if (list != null && list.size() < 1) {
      currentPage--;
      if (currentPage > 0) {
        page.setcurrentPage(currentPage);
        list = page.getResultList();
      } 
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    request.setAttribute("informationList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute(
        "pageParameters", 
        "action,channelId,channelName,channelType,userChannelName,channelShowType,orderBy,sortType,listType,userDefine,participationProId,projectId_");
  }
  
  private void view(HttpServletRequest request, String channelId, boolean canVindicate, String orderBy, String sortType, String includeChild, String participationProId) {
    Date now = new Date();
    String nowString = now.toLocaleString();
    nowString = nowString.substring(0, nowString.indexOf(" "));
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    String channelType = request.getParameter("channelType");
    String userDefine = request.getParameter("userDefine");
    String domainId = httpSession.getAttribute("domainId").toString();
    String viewSQL = "";
    if (request.getParameter("channelShowType") != null && request.getParameter("channelShowType").equals("1")) {
      viewSQL = " aaa.informationId,aaa.informationTitle,aaa.informationHead, aaa.informationType,aaa.validBeginTime,aaa.validEndTime, aaa.informationValidType,aaa.titleColor,aaa.informationIssueTime, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime";
    } else {
      viewSQL = " aaa.informationId, aaa.informationTitle, aaa.informationKits,  aaa.informationAuthor, aaa.informationVersion, aaa.informationIssueTime,  aaa.informationSummary, aaa.informationHead, aaa.informationType,  aaa.validBeginTime, aaa.validEndTime, aaa.informationCommonNum,  aaa.informationIssuer,aaa.informationValidType,aaa.titleColor, aaa.dossierStatus,bbb.channelName,aaa.isConf, aaa.documentNo,bbb.channelId,aaa.transmitToWebsite,aaa.informationModifyTime";
    } 
    viewSQL = String.valueOf(viewSQL) + ",bbb.channelNeedCheckup,aaa.informationHeadFile,aaa.orderCodeTemp,aaa.informationReader,aaa.informationIssuerId";
    String fromSQL = " com.js.oa.info.infomanager.po.InformationPO aaa  join aaa.informationChannel bbb ";
    String whereSQL = "";
    InformationEJBBean info = new InformationEJBBean();
    try {
      if (!info.judgeUserIsProLeader_2(userId, channelId)) {
        whereSQL = " where 1>2 ";
      } else {
        if (includeChild.equals("0")) {
          whereSQL = " where (bbb.channelId = " + channelId + 
            " or aaa.otherChannel like '%," + channelId + ",%') ";
        } else if (includeChild.equals("1")) {
          whereSQL = " where (bbb.channelIdString like '%$" + channelId + 
            "$%' or aaa.otherChannel like '%," + channelId + 
            ",%') ";
        } else {
          String databaseType = 
            SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            whereSQL = " where ( aaa.informationValidType = 0 or '" + 
              nowString + 
              "' between aaa.validBeginTime and aaa.validEndTime )";
            if (includeChild.equals("1")) {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelIdString like '%$" + 
                channelId + 
                "$%' or aaa.otherChannel like '%," + 
                channelId + ",%') ";
            } else {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + channelId + 
                " or aaa.otherChannel like '%," + channelId + 
                ",%') ";
            } 
          } else {
            whereSQL = " where ( aaa.informationValidType = 0 or JSDB.FN_STRTODATE('" + 
              nowString + 
              "','S') between aaa.validBeginTime and aaa.validEndTime )";
            if (includeChild.equals("1")) {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelIdString like '%$" + 
                channelId + 
                "$%' or aaa.otherChannel like '%," + 
                channelId + ",%') ";
            } else {
              whereSQL = String.valueOf(whereSQL) + " and (bbb.channelId = " + channelId + 
                " or aaa.otherChannel like '%," + channelId + 
                ",%') ";
            } 
          } 
        } 
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationStatus=0 ";
        whereSQL = String.valueOf(whereSQL) + " and aaa.domainId=" + domainId;
        if ("".equals(participationProId)) {
          whereSQL = String.valueOf(whereSQL) + " and aaa.informationChannel.relProjectId=-1";
        } else {
          String[] ids = participationProId.split(",");
          whereSQL = String.valueOf(whereSQL) + " and ((1=1)";
          for (int i = 0; i < ids.length; i++)
            whereSQL = String.valueOf(whereSQL) + " or (aaa.informationChannel.relProjectId=" + ids[i] + ")"; 
          whereSQL = String.valueOf(whereSQL) + ")";
        } 
        if (orderBy == null) {
          whereSQL = String.valueOf(whereSQL) + " order by aaa.orderCodeTemp desc,aaa.informationModifyTime desc, aaa.informationIssueTime desc, aaa.informationId desc";
        } else if (orderBy.equals("date")) {
          whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
            sortType;
        } else if (orderBy.equals("kits")) {
          whereSQL = String.valueOf(whereSQL) + " order by aaa.informationKits " + sortType;
        } else if (orderBy.equals("commonNum")) {
          whereSQL = String.valueOf(whereSQL) + " order by aaa.informationCommonNum " + 
            sortType;
        } else {
          whereSQL = String.valueOf(whereSQL) + " order by aaa.informationIssueTime " + 
            sortType;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    list_pro(request, viewSQL, fromSQL, whereSQL);
  }
  
  public boolean gd(HttpServletRequest request) {
    boolean guidangResult = false;
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
    String tmp = "ZS_" + request.getParameter("informationId") + 
      "_" + now.get(1) + (now.get(2) + 1) + 
      now.get(5) + ".htm";
    String fileName = String.valueOf(getServlet().getServletContext().getRealPath("/")) + 
      "archivesfile\\" + tmp;
    (new InformationBD()).setDossierStatus(request
        .getParameter("informationId"), "1");
    try {
      PrintWriter pw = new PrintWriter(
          new FileOutputStream(fileName));
      pw.println("<html><head><title>信息查看</title></head>");
      pw.println("<body bgcolor=8A9AA2 leftmargin=0 topmargin=0>");
      pw.println(pageContent.replace("guidang();", ""));
      pw.println("</body></html>");
      pw.close();
      if ("rws".equals(SystemCommon.getCustomerName())) {
        (new TongBuService()).tongbuForInformation(request.getParameter("informationId"), fileName, session.getAttribute("userId").toString());
      } else {
        (new ArchivesAction())
          .addArchivesWaitPigeonhole(request
            .getParameter("informationTitle"), tmp, 
            Long.valueOf(request
              .getParameter("informationId")), "ZSGL", 
            request.getSession(true).getAttribute(
              "userName").toString(), new Date(), 
            request.getParameter("isSendMessage"), 
            request, session.getAttribute("userId")
            .toString(), session.getAttribute("orgId")
            .toString(), "");
      } 
      guidangResult = true;
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return guidangResult;
  }
  
  private void setChannelViewer(HttpServletRequest request) {
    ChannelBD bd = new ChannelBD();
    String channelId = request.getParameter("newaddress1");
    channelId = channelId.substring(0, channelId.indexOf(","));
    setChannelViewer(request, channelId);
  }
  
  private void setChannelViewer(HttpServletRequest request, String channelId) {
    ChannelBD bd = new ChannelBD();
    InformationChannelPO po = bd.loadChannel(channelId);
    String reader = "";
    String readerName = "";
    if (po.getChannelReader() != null)
      reader = String.valueOf(reader) + po.getChannelReader(); 
    if (po.getChannelReaderGroup() != null)
      reader = String.valueOf(reader) + po.getChannelReaderGroup(); 
    if (po.getChannelReaderOrg() != null)
      reader = String.valueOf(reader) + po.getChannelReaderOrg(); 
    readerName = (po.getChannelReaderName() == null) ? "" : po.getChannelReaderName();
    StringBuffer readerTemp = new StringBuffer("");
    if (!reader.equals("")) {
      if (reader.indexOf("*") >= 0) {
        String[] readerArray = reader.substring(reader.indexOf("*") + 1, reader.lastIndexOf("*")).split("\\*\\*");
        for (int i = 0; i < readerArray.length; i++) {
          if (readerTemp.indexOf(readerArray[i]) == -1)
            readerTemp.append("*" + readerArray[i] + "*"); 
        } 
      } 
      if (reader.indexOf("@") >= 0) {
        String[] readerArray = reader.substring(reader.indexOf("@") + 1, reader.lastIndexOf("@")).split("\\@\\@");
        for (int i = 0; i < readerArray.length; i++) {
          if (readerTemp.indexOf(readerArray[i]) == -1)
            readerTemp.append("@" + readerArray[i] + "@"); 
        } 
      } 
      if (reader.indexOf("$") >= 0) {
        String[] readerArray = reader.substring(reader.indexOf("$") + 1, reader.lastIndexOf("$")).split("\\$\\$");
        for (int i = 0; i < readerArray.length; i++) {
          if (readerTemp.indexOf(readerArray[i]) == -1)
            readerTemp.append("$" + readerArray[i] + "$"); 
        } 
      } 
    } else {
      readerTemp.append(reader);
    } 
    StringBuffer readerNameTemp = new StringBuffer("");
    if (!readerName.equals("")) {
      String[] readerArray = readerName.split(",");
      for (int i = 0; i < readerArray.length; i++) {
        if (readerNameTemp.indexOf(readerArray[i]) == -1)
          readerNameTemp.append(String.valueOf(readerArray[i]) + ","); 
      } 
    } 
    request.setAttribute("canReader", readerTemp.toString());
    request.setAttribute("canReaderName", readerNameTemp.toString());
  }
  
  private boolean copyFileFromDocument(String[] fileNames, HttpSession session, String localPath) {
    boolean rs = false;
    if (fileNames != null && fileNames.length > 0)
      try {
        Map sysMap = 
          SysSetupReader.getInstance().getSysSetupMap(
            session.getAttribute("domainId").toString());
        for (int i = 0; i < fileNames.length; i++)
          copyFile(String.valueOf(localPath) + "\\govdocumentmanager\\" + 
              fileNames[i], String.valueOf(localPath) + "\\information\\" + 
              fileNames[i]); 
      } catch (Exception ex) {
        ex.printStackTrace();
      }  
    return true;
  }
  
  public boolean copyFile(String srcName, String aimName) {
    FileInputStream from = null;
    FileOutputStream to = null;
    try {
      from = new FileInputStream(srcName);
      to = new FileOutputStream(aimName);
      byte[] buffer = new byte[4096];
      int pos;
      while ((pos = from.read(buffer)) != -1)
        to.write(buffer, 0, pos); 
      if (from != null)
        from.close(); 
      if (to != null)
        to.close(); 
      return true;
    } catch (Exception ex) {
      System.out.println("复制文件出错！" + ex);
      return false;
    } 
  }
  
  private void update(HttpServletRequest request, HttpServletResponse httpServletResponse) {
    (new InformationWorkFlow()).update(request);
  }
}
