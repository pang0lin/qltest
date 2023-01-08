package com.js.oa.info.infomanager.action;

import cn.zzy.service.MessageService;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.channelmanager.service.DepartmentPageBD;
import com.js.oa.info.infomanager.bean.InformationEJBBean;
import com.js.oa.info.infomanager.service.InformationAccessoryBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import com.js.util.util.ParameterFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SsdzInformationAction extends Action {
  private InformationBD informationBD = new InformationBD();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    InformationActionForm informationActionForm = (InformationActionForm)actionForm;
    HttpSession session = request.getSession(true);
    request.setCharacterEncoding("UTF8");
    String domainId = session.getAttribute("domainId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String action = request.getParameter("action");
    String channelType = request.getParameter("channelType");
    String userChannelName = request.getParameter("userChannelName");
    String projectId_ = request.getParameter("projectId_");
    request.setAttribute("projectId_", projectId_);
    request.setAttribute("channelType", channelType);
    request.setAttribute("userChannelName", userChannelName);
    if (!ParameterFilter.isNumber(channelType) || 
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
    request.setAttribute("includeChild", includeChild);
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
                  deleteFile.delete("information", 
                      list
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
                  deleteFile.delete("information", 
                      list
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
      }  
    if (action.equals("openInfo")) {
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
      if (request.getParameter("isISO") != null)
        if (request.getParameter("isISO").toString()
          .equals("1"))
          tag = "isoNoRedHead";  
      String redHead = request.getParameter("redHead");
      String informationType = request.getParameter("informationType");
      request.setAttribute("informationType", informationType);
      List commentList = informationBD.getComment(request.getParameter("informationId"));
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
          Object[] obj = list.get(0);
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
          if (request.getParameter("isISO") != null && 
            request.getParameter("isISO")
            .toString().equals("1"))
            if (request.getParameter("isomanager") != null && 
              request.getParameter("isomanager").toString().equals("1")) {
              List historyList = informationBD.getHistoryVersion(informationId);
              request.setAttribute("historyList", historyList);
              request.setAttribute("isomanager", request.getParameter("isomanager"));
            } else if (obj[7] != null && !"".equals(obj[7])) {
              String version = (String)obj[7];
              String middle = "0";
              int a = Integer.parseInt(version.substring(0, version.indexOf(".")));
              String b = version.substring(version.indexOf(".") + 1, version.length());
              String isoDeal = (String)obj[54];
              if (!b.equals("0") || isoDeal.equals("2") || 
                isoDeal.equals("3") || 
                isoDeal.equals("4")) {
                middle = b.substring(0, 1);
                version = String.valueOf(a) + ".0";
                IsoDocBD bd = new IsoDocBD();
                List<Object[]> versionList = bd
                  .getInforByVersion(
                    request.getParameter("informationId"), version);
                if (versionList != null && 
                  versionList.size() > 0) {
                  Object[] bObj = versionList.get(0);
                  request.setAttribute("informationTitle", bObj[8]);
                  if (bObj[9] == null) {
                    request.setAttribute("informationSubTitle", "");
                  } else {
                    request.setAttribute("informationSubTitle", bObj[9].toString());
                  } 
                  content = (String)bObj[10];
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
    } else {
      if ("proView".equals(action)) {
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
      if ("fornum".equals(action)) {
        String curuserId = (String)request.getSession().getAttribute("userId");
        String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
        ForumBD bd = new ForumBD();
        int offset = 0;
        if (request.getParameter("pager.offset") != null)
          offset = Integer.parseInt(request.getParameter("pager.offset")); 
        int pageSize = 15;
        int currentPage = offset / pageSize + 1;
        String orderString = "";
        String orderByReplay = "";
        String orderByKit = "";
        String orderBybTime = "";
        String forwardName = "";
        orderString = String.valueOf(orderString) + "po.forumType desc,po.forumTopOrder desc,po.forumIsSoul desc, ";
        String moreType = request.getParameter("moreType");
        request.setAttribute("moreType", moreType);
        String whereString = "";
        String forumClassIds = request.getParameter("forumClassIds");
        request.setAttribute("forumClassIds", forumClassIds);
        List classList = bd.getForumClass(forumClassIds, "0");
        request.setAttribute("classList", classList);
        ArrayList canMoveForumList = bd.getHasMoveRightForumList(curuserId, orgIdString, forumClassIds.split(","));
        request.setAttribute("canMoveForumList", canMoveForumList);
        Page page = null;
        if (moreType.equals("new")) {
          whereString = bd.getNewUpdateMoreSql(forumClassIds, orderString, "", "", "", "", "", "none", "");
          forwardName = "viewFornum";
          page = getMorePage(whereString);
        } 
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List dataList = page.getResultList();
        request.setAttribute("myList", dataList);
        request.setAttribute("maxPageItems", String.valueOf(pageSize));
        request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
        request.setAttribute("pageParameters", 
            "action,sortFirst,kitDesc,replyDesc,btimeDesc,queryItem,queryMan,queryTitle,queryClass,queryForumType,startDate,endDate,moreType,forumClassIds");
        return actionMapping.findForward(forwardName);
      } 
    } 
    return actionMapping.findForward(tag);
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
      pageSize = 15; 
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
    viewSQL = String.valueOf(viewSQL) + ",bbb.channelNeedCheckup,aaa.informationHeadFile,aaa.orderCodeTemp,aaa.informationReader,aaa.informationIssuerId,aaa.oid";
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
        try {
          db.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        } 
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
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<=now()";
    } else {
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime<=sysdate";
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
      pageSize = 15; 
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
  
  private Page getMorePage(String whereString) throws Exception {
    Page page = new Page("po.id,po.forumAuthor,po.forumRevertNum,po.forumKits,po.forumIssueTime,po.forumAuthorId,po.forumClass.id,po.forumClass.classUserId,po.forumClass.classUserOrg,po.forumClass.classUserGroup,po.forumClass.createdEmp,po.forumClass.createdOrg,po.forumType,po.forumIsSoul,po.forumClass.classOwnerIds,po.anonymous,po.examinNum,po.forumNotPrint,po.forumNotUpd,po.forumNotFlow,po.forumModifyTime,po.forumTopOrder,po.forumClass.banPrint,po.forumClass.className,po.forumTitle,po.forumClass.fullDay,po.forumClass.startPeriod,po.forumClass.endPeriod ", 






        
        "com.js.oa.bbs.po.ForumPO po ", 
        whereString);
    return page;
  }
}
