package com.js.oa.weixin.workflow;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.form.WorkFlowArchives;
import com.js.oa.form.Workflow;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.DocToArchive;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowToArchive;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.pressdeal.service.PersonalOAPressManageBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.common.util.WorkflowForWeiXinUtil;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.ExcelOperate;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import javax.sql.DataSource;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import sun.misc.BASE64Decoder;

public class WorkflowButtonForWeiXinAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    httpServletResponse.setHeader("Cache-Control", "no-store");
    httpServletResponse.setHeader("Pragma", "no-cache");
    httpServletResponse.setDateHeader("Expires", 0L);
    String flag = httpServletRequest.getParameter("flag");
    String page_token = httpServletRequest.getParameter("page_token");
    HttpSession session = httpServletRequest.getSession(true);
    if (page_token != null)
      if (session.getAttribute("page_token") != null) {
        if (page_token.equals(session.getAttribute("page_token").toString()))
          return new ActionForward("/public/jsp/repeatdata.jsp"); 
        session.setAttribute("page_token", page_token);
      } else {
        session.setAttribute("page_token", page_token);
      }  
    String status = "0";
    if ("showsend".equals(flag)) {
      setMessageContent(httpServletRequest);
      return actionMapping.findForward("showsend");
    } 
    if ("save".equals(flag)) {
      if (!"0".equals(save(httpServletRequest)))
        httpServletRequest.setAttribute("flowfaild", "1"); 
      return null;
    } 
    if ("saveclose".equals(flag)) {
      Workflow wf = new Workflow();
      wf.update(httpServletRequest);
      httpServletRequest.setAttribute("noCloseWindow", "1");
      return actionMapping.findForward("saveonly");
    } 
    if ("update".equals(flag)) {
      update(httpServletRequest);
      return null;
    } 
    if ("complete".equals(flag)) {
      if (1 == completeSaveData(httpServletRequest)) {
        String processId = httpServletRequest.getParameter("processId");
        String archiveResult = "-1";
        if (WorkflowToArchive.isNeedDocArchive()) {
          WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
          String tableId = httpServletRequest.getParameter("tableId");
          String moduleId = workFlowCommonBD.getModuleId(tableId);
          if ("2".equals(moduleId)) {
            archiveResult = (new DocToArchive()).insertFileTable_send(httpServletRequest);
          } else if ("3".equals(moduleId)) {
            archiveResult = (new DocToArchive()).insertFileTable_receive(httpServletRequest);
          } 
        } 
        if (WorkflowToArchive.isNeedArchive(processId))
          archiveResult = (new WorkflowToArchive()).transToArchive(httpServletRequest); 
        WorkFlowArchives wfa = new WorkFlowArchives();
        if (WorkFlowArchives.isUse() && wfa.isLendProcess(httpServletRequest.getParameter("recordId")))
          archiveResult = wfa.finishApply(httpServletRequest); 
        if (archiveResult == null || "0".equals(archiveResult)) {
          httpServletRequest.setAttribute("flowfaild", "2");
        } else {
          String href = complete(httpServletRequest);
          if (!href.equals("")) {
            httpServletRequest.setAttribute("myhref", href);
            if (href.indexOf("WorkFlowProcAction.do") > 0) {
              String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GZLC", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
              if (!"".equals(returnValue) && !"-1".equals(returnValue))
                return actionMapping.findForward("workflowGD"); 
            } else if (href.indexOf("GovSendFileLoadAction.do") > 0) {
              String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
              if (!"".equals(returnValue) && !"-1".equals(returnValue))
                return actionMapping.findForward("sendFileGD"); 
            } else if (href.indexOf("GovReceiveFileLoadAction.do") > 0) {
              String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
              if (!"".equals(returnValue) && !"-1".equals(returnValue))
                return actionMapping.findForward("receiveFileGD"); 
            } else if (href.indexOf("GovSendFileCheckWithWorkFlowAction.do") > 0) {
              String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
              if (!"".equals(returnValue) && !"-1".equals(returnValue))
                return actionMapping.findForward("sendFileCheckGD"); 
            } 
          } 
        } 
      } else {
        httpServletRequest.setAttribute("flowfaild", "100");
      } 
      String backto = httpServletRequest.getParameter("backto");
      httpServletRequest.setAttribute("backto", (backto == null) ? "" : backto);
      return actionMapping.findForward("wxCloseWindow");
    } 
    if ("saveToDraft".equals(flag)) {
      String[] para = saveToDraft(httpServletRequest);
      httpServletRequest.setAttribute("processPara", para);
      return null;
    } 
    if ("saveFirstComp".equals(flag)) {
      if (!"0".equals(Integer.valueOf(saveFirstComp(httpServletRequest))))
        httpServletRequest.setAttribute("flowfaild", "1"); 
    } else {
      if ("showback".equals(flag)) {
        httpServletRequest.setAttribute("backto", httpServletRequest.getParameter("backto"));
        return actionMapping.findForward("showback");
      } 
      if ("back".equals(flag)) {
        back(httpServletRequest);
        httpServletRequest.setAttribute("backto", httpServletRequest.getParameter("backto"));
        httpServletRequest.setAttribute("resubmit", "1");
        return null;
      } 
      if ("undo".equals(flag)) {
        undo(httpServletRequest);
        String backto = httpServletRequest.getParameter("backto");
        httpServletRequest.setAttribute("backto", (backto == null) ? "" : backto);
        return actionMapping.findForward("wxCloseWindow");
      } 
      if ("showpress".equals(flag)) {
        if (httpServletRequest.getParameter("press") == null)
          return actionMapping.findForward("showpress"); 
        return actionMapping.findForward("press");
      } 
      if ("press".equals(flag)) {
        press(httpServletRequest);
        return null;
      } 
      if ("showfeedback".equals(flag))
        return actionMapping.findForward("showfeedback"); 
      if ("feedback".equals(flag)) {
        feedback(httpServletRequest);
        return null;
      } 
      if ("showreturn".equals(flag))
        return actionMapping.findForward("showreturn"); 
      if ("return".equals(flag)) {
        receive(httpServletRequest);
        return null;
      } 
      if ("showtran".equals(flag)) {
        setMessageContent(httpServletRequest);
        return actionMapping.findForward("showtran");
      } 
      if ("delete".equals(flag)) {
        delete(httpServletRequest);
      } else {
        if ("end".equals(flag)) {
          status = "0";
          httpServletRequest.setAttribute("status", status);
          end(httpServletRequest);
          String backto = httpServletRequest.getParameter("backto");
          httpServletRequest.setAttribute("backto", (backto == null) ? "" : backto);
          return actionMapping.findForward("wxCloseWindow");
        } 
        if ("passround".equals(flag)) {
          passround(httpServletRequest);
          return null;
        } 
        if ("dealpassround".equals(flag)) {
          status = "2";
          dealpassround(httpServletRequest);
          String backto = httpServletRequest.getParameter("backto");
          httpServletRequest.setAttribute("backto", (backto == null) ? "" : backto);
          httpServletRequest.setAttribute("status", status);
          return actionMapping.findForward("wxCloseWindow");
        } 
        if ("showselfsend".equals(flag)) {
          setMessageContent(httpServletRequest);
          return actionMapping.findForward("showselfsend");
        } 
        if ("showtranread".equals(flag)) {
          setMessageContent(httpServletRequest);
          return actionMapping.findForward("showtranread");
        } 
        if ("setCurrentStep".equals(flag)) {
          setCurrentStep(httpServletRequest);
          return actionMapping.findForward("setCurrentStep");
        } 
        if (flag.equals("importExcel")) {
          String tableName = httpServletRequest.getParameter("subTableName");
          String dataSaveName = httpServletRequest.getParameter("dataSaveName");
          String srcTr = "0000";
          if (dataSaveName != null && dataSaveName.length() > 6 && dataSaveName.substring(4, 5).equals("_")) {
            srcTr = dataSaveName.substring(0, 4);
          } else {
            srcTr = "0000";
          } 
          String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/upload/" + srcTr + "/jsflowSub/");
          String[][] subData = ExcelOperate.getData(new File(String.valueOf(filePath) + "\\" + dataSaveName));
          String sql = "select field_desname,field_name,field_show from tfield f join ttable t on f.field_table=t.table_id where t.table_name in ('" + 
            tableName.replace(",", "','") + "')";
          List<String[]> fields = (new DataSourceUtil()).getListQuery(sql, "");
          Map<String, String[]> map = (Map)new HashMap<String, String>();
          for (int i = 0; i < fields.size(); i++) {
            String[] field = fields.get(i);
            map.put(field[0], new String[] { field[1], field[2] });
          } 
          if (subData.length > 2) {
            httpServletRequest.setAttribute("flag", "1");
          } else {
            httpServletRequest.setAttribute("flag", "2");
          } 
          httpServletRequest.setAttribute("subData", subData);
          httpServletRequest.setAttribute("fieldMap", map);
          httpServletRequest.setAttribute("subTableName", tableName);
          return actionMapping.findForward("importExcel");
        } 
      } 
    } 
    return actionMapping.findForward("success");
  }
  
  private void setMessageContent(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("smsContent", "");
  }
  
  private void dealpassround(HttpServletRequest httpServletRequest) {
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    HttpSession sesison = httpServletRequest.getSession(true);
    dealwithMap.put("comment", httpServletRequest.getParameter("include_comment"));
    if (httpServletRequest.getParameter("include_commField") != null)
      dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
    dealwithMap.put("userId", httpServletRequest.getSession(true).getAttribute("userId"));
    dealwithMap.put("workId", httpServletRequest.getParameter("workId"));
    dealwithMap.put("activityName", httpServletRequest.getParameter("curActivityName"));
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    httpServletRequest.setAttribute("passround", workFlowButtonBD.insertPassroundDealWith(dealwithMap));
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(sesison.getAttribute("userId").toString());
    workLogVO.setSendUserName(sesison.getAttribute("userName").toString());
    workLogVO.setSendAction("阅件完毕");
    workLogVO.setReceiveUserName(" ");
    workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
    workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
    workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
    workLogVO.setDomainId(sesison.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
  }
  
  private void passround(HttpServletRequest httpServletRequest) {
    String operId, operProcOrg;
    HttpSession httpSession = httpServletRequest.getSession(true);
    String remindFieldValue = "";
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.startsWith("$") && remindField.endsWith("$")) {
      remindFieldValue = httpServletRequest.getParameter("motif");
    } else if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String mainPressType = httpServletRequest.getParameter("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    String comment = httpServletRequest.getParameter("include_comment");
    String mainAllowStandFor = httpServletRequest.getParameter("mainAllowStandFor");
    String mainAllowCancel = httpServletRequest.getParameter("mainAllowCancel");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    if (curActivityName != null && !"".equals(curActivityName))
      try {
        curActivityName = URLDecoder.decode(curActivityName, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String mainNextActivityId = httpServletRequest.getParameter("curActivityId");
    String mainNextActivityName = httpServletRequest.getParameter("curActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String tableName = (new CustomFormBD()).getTable(tableId);
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String userId = httpSession.getAttribute("userId").toString();
    String stepCount = httpServletRequest.getParameter("stepCount");
    String activityClass = httpServletRequest.getParameter("activityClass");
    mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
    mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String subProcType = (httpServletRequest.getParameter("subProcType") == null) ? "-1" : httpServletRequest.getParameter("subProcType");
    String toMainFile = httpServletRequest.getParameter("passLinkFile");
    String curTransactType = "";
    String nextTransactType = httpServletRequest.getParameter("approveMode");
    if (remindFieldValue != null && remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    String docTitle = "";
    if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
      docTitle = httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")); 
    String emergence = (httpServletRequest.getParameter("emergence") == null) ? "0" : httpServletRequest.getParameter("emergence");
    String[] mainPassRoundUser = { "" };
    switch (Integer.parseInt(httpServletRequest.getParameter("mainPassRoundUserType"))) {
      case 100:
        operId = httpServletRequest.getParameter("operId");
        if (operId != null && !operId.equals("")) {
          ConversionString con = new ConversionString(operId);
          String userIdStr = String.valueOf(con.getUserIdString()) + ",";
          if (con.getGroupIdString() != null && !"".equals(con.getGroupIdString()))
            userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ","; 
          if (con.getOrgIdString() != null && !"".equals(con.getOrgIdString()))
            userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString()); 
          DbOpt dbopt = null;
          try {
            dbopt = new DbOpt();
            userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
            if (userIdStr.startsWith(","))
              userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
            if (userIdStr.endsWith(","))
              userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
            if (userIdStr.length() > 0) {
              mainPassRoundUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
            } else {
              mainPassRoundUser = new String[0];
            } 
            dbopt.close();
          } catch (Exception e) {
            try {
              dbopt.close();
            } catch (SQLException ex1) {
              ex1.printStackTrace();
            } 
            e.printStackTrace();
            mainPassRoundUser = new String[0];
          } 
        } 
        break;
      case 10:
        operProcOrg = httpServletRequest.getParameter("operId");
        mainPassRoundUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
        break;
    } 
    String sendType = httpServletRequest.getParameter("type");
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
        curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
        activityClass, subProcType, docTitle, emergence, 
        nextTransactType, sendType, curActivityName };
    Integer result = wfcBD.selfSendWorkflowButton(para, mainPassRoundUser);
    if (result.intValue() < 0) {
      httpServletRequest.setAttribute("flowfaild", "1");
    } else {
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
      workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
      if ("reSend".equals(sendType)) {
        workLogVO.setSendAction("重新发送" + curActivityName + "阅件");
      } else if ("transRead".equals(sendType)) {
        workLogVO.setSendAction("转发" + curActivityName + "阅件");
      } else {
        workLogVO.setSendAction("补发" + curActivityName + "阅件");
      } 
      String passRoundName = httpServletRequest.getParameter("operName");
      if (passRoundName.endsWith(","))
        passRoundName = passRoundName.substring(0, passRoundName.length() - 1); 
      workLogVO.setReceiveUserName(passRoundName);
      workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
      workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
      workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
      workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      workFlowButtonBD.setDealWithLog(workLogVO);
      sendMsg(mainPassRoundUser, httpServletRequest);
    } 
  }
  
  private void end(HttpServletRequest httpServletRequest) {
    String operId;
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = workFlowCommonBD.getActivityClassMethod(httpServletRequest.getParameter("curActivityId"));
    String formClassName = "";
    String formClassMethod = "";
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("formClassMethod") != null && 
        !formClassNameMethod.get("formClassMethod").toString().equals("") && 
        !formClassNameMethod.get("formClassMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("formClassMethod").toString(); 
    } 
    String remindFieldValue = "";
    if (!"".equals(formClassName) && !"".equals(formClassMethod))
      try {
        FormReflection formReflection = new FormReflection();
        Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, httpServletRequest);
        if (obj != null && 
          obj.getClass().toString().equals("class java.lang.String"))
          remindFieldValue = obj.toString(); 
      } catch (Exception exception) {} 
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.startsWith("$") && remindField.endsWith("$")) {
      remindFieldValue = httpServletRequest.getParameter("motif");
    } else if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String mainPressType = httpServletRequest.getParameter("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    String comment = httpServletRequest.getParameter("include_comment");
    String mainAllowStandFor = httpServletRequest.getParameter("mainAllowStandFor");
    String mainAllowCancel = httpServletRequest.getParameter("mainAllowCancel");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String mainNextActivityId = "0";
    String mainNextActivityName = "0";
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String tableName = (new CustomFormBD()).getTable(tableId);
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String userId = httpSession.getAttribute("userId").toString();
    String stepCount = httpServletRequest.getParameter("stepCount");
    String activityClass = httpServletRequest.getParameter("activityClass");
    mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
    mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] mainTransactUser = { "" };
    int mainUserType = Integer.parseInt(httpServletRequest.getParameter("mainUserType"));
    switch (mainUserType) {
      case 100:
        operId = httpServletRequest.getParameter("operId");
        if (operId != null && !operId.equals("")) {
          ConversionString con = new ConversionString(operId);
          String userIdStr = String.valueOf(con.getUserIdString()) + ",";
          userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
          userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
          DbOpt dbopt = null;
          try {
            userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
            if (userIdStr.startsWith(","))
              userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
            if (userIdStr.endsWith(","))
              userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
            if (userIdStr.length() > 0) {
              dbopt = new DbOpt();
              mainTransactUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
              dbopt.close();
              break;
            } 
            mainTransactUser = new String[0];
          } catch (Exception e) {
            if (dbopt != null)
              try {
                dbopt.close();
              } catch (SQLException sQLException) {} 
            mainTransactUser = new String[0];
          } 
        } 
        break;
    } 
    switch (Integer.parseInt(mainPressType)) {
      case 0:
        mainDeadLineTime = "-1";
        mainPressMotionTime = "-1";
        break;
    } 
    String subProcWorkId = (httpServletRequest.getParameter("subProcWorkId") == null) ? "0" : httpServletRequest.getParameter("subProcWorkId");
    String modiCommentId = httpServletRequest.getParameter("modiCommentId");
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", mainNextActivityName);
    dealwithMap.put("nextActivityId", mainNextActivityId);
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    dealwithMap.put("activityClass", activityClass);
    dealwithMap.put("subProcWorkId", subProcWorkId);
    dealwithMap.put("modiCommentId", modiCommentId);
    if (httpServletRequest.getParameter("include_commField") != null)
      dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
    dealwithMap.put("userScope", httpServletRequest.getParameter("userScope"));
    dealwithMap.put("scopeId", httpServletRequest.getParameter("scopeId"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String subProcType = (httpServletRequest.getParameter("subProcType") == null) ? "-1" : httpServletRequest.getParameter("subProcType");
    String toMainFile = httpServletRequest.getParameter("mainLinkFile");
    String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);
    String nextTransactType = httpServletRequest.getParameter("approveMode");
    if (remindFieldValue != null && remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    String docTitle = "";
    if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
      docTitle = httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")); 
    String emergence = (httpServletRequest.getParameter("emergence") == null) ? "0" : httpServletRequest.getParameter("emergence");
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
        curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
        activityClass, subProcType, docTitle, emergence, 
        nextTransactType };
    Integer result = wfcBD.endWorkflowButton(dealwithMap, para);
    if (result.intValue() < 0) {
      httpServletRequest.setAttribute("flowfaild", "1");
    } else {
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
      workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
      workLogVO.setSendAction("办理完毕");
      WorkVO workVO = new WorkVO();
      workVO.setTableId(Long.valueOf(tableId));
      workVO.setRecordId(Long.valueOf(recordId));
      List<Object[]> tmpList = workFlowButtonBD.getAllDealwithUsersByStatus2(workVO, "0");
      String tmpUser = "";
      for (int i = 0; i < tmpList.size(); i++) {
        Object[] obj = tmpList.get(i);
        tmpUser = String.valueOf(tmpUser) + obj[1] + ",";
      } 
      if (tmpUser.endsWith(","))
        tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
      workLogVO.setReceiveUserName(tmpUser);
      workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
      workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
      workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
      workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
      workFlowButtonBD.setDealWithLog(workLogVO);
      if (httpServletRequest.getParameter("sendMsgToInitiator") != null && "1".equals(httpServletRequest.getParameter("sendMsgToInitiator"))) {
        String sql = "SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID=" + 
          httpServletRequest.getParameter("processId") + " AND a.worktable_id=" + tableId + " AND a.workrecord_id=" + recordId + 
          " AND workstepcount=0";
        String[][] initWorkId = (new DataSourceBase()).queryArrayBySql(sql);
        try {
          String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId;
          RemindUtil.sendMessageToUsers2("您发起的" + httpServletRequest.getParameter("processName") + "流程已经被" + 
              httpServletRequest.getParameter("user_Name") + "(" + httpServletRequest.getParameter("curActivityName") + 
              ")处理完毕！", url, initWorkId[0][0], "jsflow", new Date(), (
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(initWorkId[0][2]), 1);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private void delete(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String submitPerson = request.getParameter("submitPerson");
    String submitTime = request.getParameter("submitTime");
    String curActivityName = request.getParameter("curActivityName");
    Object object = session.getAttribute("userName");
    String workId = request.getParameter("workId");
    String stepCount = request.getParameter("stepCount");
    SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    WorkFlowButtonBD workflowButtonBD = new WorkFlowButtonBD();
    String to = workflowButtonBD.getSubmitPerson(workId, tableId, recordId);
    String title = request.getParameter("titleFieldName").equals("") ? request.getParameter("processName") : request.getParameter(request.getParameter("titleFieldName"));
    if (to != null && to.length() > 1) {
      title = "您发起的" + title + "被" + object + "[" + curActivityName + "]作废";
      RemindUtil.sendMessageToUsersNoURL(title, "", to, "jsflow", new Date(), new Date("2050/1/1"));
    } 
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = workFlowCommonBD.getProcessClassMethod(request.getParameter("processId"));
    String formClassName = "";
    if (formClassNameMethod != null && 
      formClassNameMethod.get("formClassName") != null && 
      !formClassNameMethod.get("formClassName").toString().equals("") && 
      !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
      formClassName = formClassNameMethod.get("formClassName").toString(); 
    if (!"".equals(formClassName)) {
      FormReflection formReflection = new FormReflection();
      Object object1 = formReflection.execute("com.js.oa.form." + formClassName, "delete", request);
    } 
    WorkVO workVO = new WorkVO();
    workVO.setProcessId(Long.valueOf(processId));
    workVO.setTableId(Long.valueOf(tableId));
    workVO.setRecordId(Long.valueOf(recordId));
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    bd.deleteWork(workVO);
  }
  
  private void receive(HttpServletRequest request) {
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = null;
    String formClassName = "";
    String formClassMethod = "";
    if (request.getParameter("curActivityId") != null && !"0".equals(request.getParameter("curActivityId"))) {
      formClassNameMethod = workFlowCommonBD.getActivityClassMethod(request.getParameter("curActivityId"));
    } else {
      formClassNameMethod = workFlowCommonBD.getProcessClassMethod(request.getParameter("processId"));
    } 
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("untreadMethod") != null && 
        !formClassNameMethod.get("untreadMethod").toString().equals("") && 
        !formClassNameMethod.get("untreadMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("untreadMethod").toString(); 
    } 
    if (!"".equals(formClassName) && !"".equals(formClassMethod)) {
      FormReflection formReflection = new FormReflection();
      Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, request);
    } else {
      FormReflection formReflection = new FormReflection();
      Object object1 = formReflection.execute("com.js.oa.form." + formClassName, "back", request);
    } 
    HttpSession session = request.getSession(true);
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String receiveActivityId = request.getParameter("receiveActivityId");
    String curStepCount = request.getParameter("stepCount");
    Object object = session.getAttribute("userId");
    String receiveActivityName = request.getParameter("receiveActivityName");
    if (receiveActivityName != null && !"".equals(receiveActivityName))
      try {
        receiveActivityName = URLDecoder.decode(receiveActivityName, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    WorkVO workVO = new WorkVO();
    workVO.setProcessId(Long.valueOf(processId));
    workVO.setTableId(Long.valueOf(tableId));
    workVO.setRecordId(Long.valueOf(recordId));
    workVO.setActivity(Long.valueOf(receiveActivityId));
    workVO.setCurStep(curStepCount);
    workVO.setCurEmployeeId(Long.valueOf((String)object));
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    bd.receiveWork(workVO);
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(session.getAttribute("userId").toString());
    workLogVO.setSendUserName(session.getAttribute("userName").toString());
    workLogVO.setSendAction("收回到" + receiveActivityName);
    workLogVO.setReceiveUserName(request.getParameter("receiveUserName"));
    workLogVO.setProcessId(request.getParameter("processId"));
    workLogVO.setTableId(request.getParameter("tableId"));
    workLogVO.setRecordId(request.getParameter("recordId"));
    workLogVO.setDomainId(session.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
  }
  
  private void undo(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String curActivityId = request.getParameter("curActivityId");
    String stepCount = request.getParameter("stepCount");
    Object object = session.getAttribute("userId");
    String workId = request.getParameter("workId");
    WorkVO workVO = new WorkVO();
    workVO.setId(Long.valueOf(workId));
    workVO.setProcessId(Long.valueOf(processId));
    workVO.setTableId(Long.valueOf(tableId));
    workVO.setRecordId(Long.valueOf(recordId));
    workVO.setActivity(Long.valueOf(curActivityId));
    workVO.setPareStepCount(stepCount);
    workVO.setCurEmployeeId(Long.valueOf((String)object));
    workVO.setTransactType(request.getParameter("curTransactType"));
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    String undoActivity = bd.undoWork(workVO);
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(session.getAttribute("userId").toString());
    workLogVO.setSendUserName(session.getAttribute("userName").toString());
    String undoActivityName = undoActivity.split(",")[1];
    if (undoActivityName != null && !"".equals(undoActivityName))
      try {
        undoActivityName = URLDecoder.decode(undoActivityName, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    workLogVO.setSendAction("撤办到" + undoActivityName);
    workLogVO.setReceiveUserName(session.getAttribute("userName").toString());
    workLogVO.setProcessId(request.getParameter("processId"));
    workLogVO.setTableId(request.getParameter("tableId"));
    workLogVO.setRecordId(request.getParameter("recordId"));
    workLogVO.setDomainId(session.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
  }
  
  private void press(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    PersonalOAPressManageBD bd = new PersonalOAPressManageBD();
    boolean pressSMS = (request.getParameter("pressSMS") != null && request.getParameter("pressSMS").equals("true"));
    try {
      String rslt = bd.sendNewPress(
          request.getParameter("pressUserId"), 
          request.getParameter("pressUserName"), 
          httpSession.getAttribute("userId").toString(), 
          httpSession.getAttribute("userName").toString(), 
          httpSession.getAttribute("orgName").toString(), 
          request.getParameter("pressTitle"), 
          request.getParameter("pressContent"), 
          request.getParameter("msgFrom"), 
          request.getParameter("processName"), 
          httpSession.getAttribute("domainId").toString(), 
          pressSMS, 
          request);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void feedback(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String toUserId = request.getParameter("feedUserId");
    String feedUserName = request.getParameter("feedUserName");
    String title = request.getParameter("feedTitle");
    String content = request.getParameter("feedContent");
    if (title.length() > 50)
      title = String.valueOf(title.substring(0, 45)) + ".."; 
    ChatPO chatPO = new ChatPO();
    ChatService chatDB = new ChatService();
    MessagesBD messagesBD = new MessagesBD();
    try {
      String[] toUserIdArr = toUserId.substring(1, toUserId.length() - 1).split("\\$\\$");
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      String chatTime = getCurrentTime().substring(5, 16);
      chatPO.setChatContent(content);
      chatPO.setChatTime(new Date());
      chatPO.setSenderId(userId);
      chatPO.setSenderName(userName);
      chatPO.setChatTo(feedUserName);
      chatPO.setChatAttachsize("0");
      chatPO.setChatHasattach("0");
      long chatid = chatDB.saveChat(chatPO);
      ChatUserPO chatUserPO = new ChatUserPO();
      chatUserPO.setChatStatus("0");
      chatUserPO.setEmpId(userId);
      chatUserPO.setIsRead("0");
      chatUserPO.setChat(chatPO);
      chatDB.saveChatUser(chatUserPO);
      for (int i = 0; i < toUserIdArr.length; i++) {
        if (toUserIdArr[i] != null && !"".equals(toUserIdArr[i])) {
          chatUserPO = new ChatUserPO();
          chatUserPO.setChatStatus("0");
          chatUserPO.setEmpId(toUserIdArr[i]);
          chatUserPO.setIsRead("0");
          chatUserPO.setChat(chatPO);
          chatDB.saveChatUser(chatUserPO);
          MessagesVO messagesVO = new MessagesVO();
          messagesVO.setMessage_send_UserName(userName);
          messagesVO.setMessage_type("Chat");
          messagesVO.setMessage_send_UserId(Long.parseLong(chatPO.getSenderId()));
          messagesVO.setMessage_show(1);
          messagesVO.setMessage_status(1);
          messagesVO.setMessage_time(new Date());
          messagesVO.setMessage_title(title);
          messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + chatid + "&isRead=0");
          messagesVO.setMessage_toUserId(Long.parseLong(toUserIdArr[i]));
          messagesVO.setMessage_date_begin(new Date());
          messagesVO.setData_id(chatid);
          messagesVO.setMessage_date_end(tmp.getTime());
          messagesBD.messageAdd(messagesVO);
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String getCurrentTime() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String dateString = formatter.format(currentTime);
    return dateString;
  }
  
  private void back(HttpServletRequest request) {
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = null;
    String formClassName = "";
    String formClassMethod = "";
    if (request.getParameter("curActivityId") != null && !"0".equals(request.getParameter("curActivityId"))) {
      formClassNameMethod = workFlowCommonBD.getActivityClassMethod(request.getParameter("curActivityId"));
    } else {
      formClassNameMethod = workFlowCommonBD.getProcessClassMethod(request.getParameter("processId"));
    } 
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("untreadMethod") != null && 
        !formClassNameMethod.get("untreadMethod").toString().equals("") && 
        !formClassNameMethod.get("untreadMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("untreadMethod").toString(); 
    } 
    if (!"".equals(formClassName) && !"".equals(formClassMethod)) {
      FormReflection formReflection = new FormReflection();
      System.out.println("com.js.oa.form." + formClassName);
      System.out.println(formClassMethod);
      Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, request);
    } else {
      System.out.println("com.js.oa.form." + formClassName);
      FormReflection formReflection = new FormReflection();
      Object object = formReflection.execute("com.js.oa.form." + formClassName, "back", request);
    } 
    HttpSession session = request.getSession(true);
    String comment = request.getParameter("include_comment");
    String curActivityId = request.getParameter("curActivityId");
    String curActivityName = request.getParameter("curActivityName");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String workId = request.getParameter("workId");
    String stepCount = request.getParameter("stepCount");
    Object object1 = session.getAttribute("userId");
    Object object2 = session.getAttribute("userName");
    String submitPerson = request.getParameter("submitPerson");
    String submitPersonId = request.getParameter("submitPersonId");
    String submitTime = request.getParameter("submitTime");
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    WorkFlowButtonBD wfbBD = new WorkFlowButtonBD();
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", object1);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", "退回");
    dealwithMap.put("nextActivityId", "-1");
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", request.getParameter("isStandForWork"));
    dealwithMap.put("standForUserId", request.getParameter("standForUserId"));
    if (request.getParameter("include_commField") != null)
      dealwithMap.put("commentField", request.getParameter("include_commField")); 
    dealwithMap.put("userScope", request.getParameter("userScope"));
    dealwithMap.put("scopeId", request.getParameter("scopeId"));
    wfcBD.insertDealWith(dealwithMap);
    String initStepCount = (request.getParameter("backToStep") == null) ? "0" : request.getParameter("backToStep");
    String dealTips = request.getParameter("dealTips");
    if (dealTips == null || "null".equals(dealTips))
      dealTips = ""; 
    String title = request.getParameter("titleFieldName").equals("") ? request.getParameter("processName") : request.getParameter(request.getParameter("titleFieldName"));
    String backInfo = "";
    if (!"".equals(dealTips))
      backInfo = String.valueOf(submitPerson) + submitTime.substring(2, 16) + "发起的" + title + "被" + object2 + "退回.<br>" + 
        "办理提示：<br>" + dealTips; 
    Map backMap = wfbBD.getBackToPerson(tableId, recordId, stepCount, initStepCount, workId);
    String exeUser = "";
    if ("0".equals(request.getParameter("type"))) {
      Map<Object, Object> para = new HashMap<Object, Object>();
      para.put("workId", workId);
      para.put("tableId", tableId);
      para.put("recordId", recordId);
      para.put("workId", workId);
      para.put("stepCount", stepCount);
      para.put("userName", object2);
      para.put("isStandForWork", request.getParameter("isStandForWork"));
      para.put("standForUserId", request.getParameter("standForUserId"));
      para.put("userId", object1);
      para.put("comment", comment);
      para.put("backInfo", backInfo);
      wfbBD.backToSubmitPerson(para);
    } else {
      Map<Object, Object> para = new HashMap<Object, Object>();
      para.put("workId", workId);
      para.put("recordId", recordId);
      para.put("tableId", tableId);
      para.put("workId", workId);
      para.put("stepCount", stepCount);
      para.put("userName", object2);
      para.put("userId", object1);
      para.put("backToActivityId", request.getParameter("backToActivityId"));
      para.put("backToStep", initStepCount);
      String str = request.getParameter("backToActivityName");
      para.put("backToActivityName", str);
      para.put("workMainLinkFile", request.getParameter("mainLinkFile"));
      para.put("isStandForWork", request.getParameter("isStandForWork"));
      para.put("standForUserId", request.getParameter("standForUserId"));
      para.put("backToUserId", request.getParameter("backToUserId"));
      para.put("comment", comment);
      para.put("backInfo", backInfo);
      exeUser = wfbBD.backToActivity(para);
    } 
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(session.getAttribute("userId").toString());
    workLogVO.setSendUserName(session.getAttribute("userName").toString());
    String backToActivityName = WorkflowForWeiXinUtil.decordStr(request.getParameter("backToActivityName"));
    workLogVO.setSendAction("退回" + backToActivityName);
    workLogVO.setReceiveUserId(backInfo);
    String backToUserName = WorkflowForWeiXinUtil.decordStr(request.getParameter("backToUserName"));
    workLogVO.setReceiveUserName(backToUserName);
    workLogVO.setProcessId(request.getParameter("processId"));
    workLogVO.setTableId(request.getParameter("tableId"));
    workLogVO.setRecordId(request.getParameter("recordId"));
    workLogVO.setDomainId(session.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
    if ("0".equals(request.getParameter("type"))) {
      String to = backMap.get("empId").toString();
      String empWorkId = backMap.get("empWorkId").toString();
      if (!"".equals(to)) {
        to = userIdStringToArray(to);
        empWorkId = empWorkId.substring(0, empWorkId.length() - 1);
        title = "您发起的" + title + "被" + object2 + "退回";
        String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&resubmit=1&workId=" + empWorkId;
        RemindUtil.sendMessageToUsers(title, url, to, "jsflow", new Date(), new Date("2050/1/1"));
      } 
    } else {
      String to = backMap.get("empId").toString();
      String empWorkId = backMap.get("empWorkId").toString();
      String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&workId=";
      if (!"".equals(to)) {
        to = userIdStringToArray(to);
        empWorkId = empWorkId.substring(0, empWorkId.length() - 1);
        String[] toArr = to.split(",");
        String[] workIdArr = empWorkId.split(",");
        String titleTemp = String.valueOf(submitPerson) + "发起的" + title + "被" + object2 + "退回到" + backToActivityName;
        for (int i = 0; i < toArr.length; i++) {
          if (exeUser.indexOf("," + toArr[i] + ",") < 0)
            RemindUtil.sendMessageToUsers(titleTemp, String.valueOf(url) + workIdArr[i], toArr[i], "jsflow", new Date(), new Date("2050/1/1")); 
        } 
      } 
      title = "您发起的" + title + "被" + object2 + "退回到" + backToActivityName;
      to = backMap.get("submitEmpId").toString();
      RemindUtil.sendMessageToUsers(title, String.valueOf(url) + backMap.get("submitWorkId").toString(), to, "jsflow", new Date(), new Date("2050/1/1"));
    } 
  }
  
  private String save(HttpServletRequest httpServletRequest) {
    String result = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String formClassName = "Workflow";
    String formClassMethod = "save";
    Map formClassNameMethod = workFlowCommonBD.getProcessClassMethod(httpServletRequest.getParameter("processId"));
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("formClassMethod") != null && 
        !formClassNameMethod.get("formClassMethod").toString().equals("") && 
        !formClassNameMethod.get("formClassMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("formClassMethod").toString(); 
    } 
    FormReflection formReflection = new FormReflection();
    Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, httpServletRequest);
    String recordId = "0";
    String remindFieldValue = "";
    String recordIdStr = "";
    if (obj != null)
      if (obj.getClass().toString().equals("class java.lang.Long")) {
        recordId = obj.toString();
      } else if (obj.getClass().toString().equals("class java.util.HashMap")) {
        Map tmpMap = (Map)obj;
        if (tmpMap.get("id") != null)
          recordId = tmpMap.get("id").toString(); 
        if (tmpMap.get("idStr") != null) {
          recordIdStr = tmpMap.get("idStr").toString();
          recordId = recordIdStr;
        } 
        if (tmpMap.get("remindFieldValue") != null)
          remindFieldValue = tmpMap.get("remindFieldValue").toString(); 
      }  
    if ("0".equals(recordId))
      return "-1"; 
    String addFlag = httpServletRequest.getParameter("addFlag");
    if (addFlag != null && "batchAdd".equals(addFlag)) {
      if (recordIdStr != null && !"".equals(recordIdStr)) {
        String[] recordIdStrs = recordIdStr.split(",");
        for (int i = 0; i < recordIdStrs.length; i++) {
          result = doRemind(httpServletRequest, recordIdStrs[i], remindFieldValue);
          if (!"0".equals(result))
            break; 
        } 
      } 
    } else {
      result = doRemind(httpServletRequest, recordId, remindFieldValue);
    } 
    return result;
  }
  
  public String doRemind(HttpServletRequest httpServletRequest, String recordId, String remindFieldValue) {
    String result = "0";
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    HttpSession httpSession = httpServletRequest.getSession(true);
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.startsWith("$") && remindField.endsWith("$")) {
      remindFieldValue = httpServletRequest.getParameter("motif");
    } else if (remindField.indexOf("S") < 0 || recordId == null || recordId.trim().length() < 1) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, recordId, httpServletRequest.getParameter("tableId"));
    } 
    remindFieldValue = CharacterTool.deleteEnter(remindFieldValue);
    if (recordId != null && Long.parseLong(recordId) > 0L) {
      String processType = (httpServletRequest.getParameter("processType") == null || "".equals(httpServletRequest.getParameter("processType"))) ? "1" : httpServletRequest.getParameter("processType");
      BASE64Decoder base64 = new BASE64Decoder();
      String processName = httpServletRequest.getParameter("processName");
      String processId = httpServletRequest.getParameter("processId");
      String tableId = httpServletRequest.getParameter("tableId");
      String moduleId = httpServletRequest.getParameter("moduleId");
      moduleId = workFlowCommonBD.getModuleId(tableId);
      String userName = httpSession.getAttribute("userName").toString();
      String userId = httpSession.getAttribute("userId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      if ("3".equals(moduleId))
        orgName = httpServletRequest.getParameter("receiveFileSendFileUnit"); 
      WorkVO workVO = new WorkVO();
      workVO.setWorkType(Integer.parseInt(processType));
      workVO.setFileType(processName);
      workVO.setRemindValue(remindFieldValue.toString());
      workVO.setSelfMainLinkFile(httpServletRequest.getParameter("mainLinkFile"));
      workVO.setToMainLinkFile(httpServletRequest.getParameter("mainLinkFile"));
      workVO.setSubmitPerson(userName);
      workVO.setSubmitEmployeeId(new Long(userId));
      workVO.setSubmitOrg(orgName);
      workVO.setProcessId(new Long(processId));
      workVO.setTableId(new Long(tableId));
      workVO.setRecordId(new Long(recordId));
      workVO.setCreatorCancelLink("");
      String isSubProcWork = (httpServletRequest.getParameter("isSubProcWork") == null) ? "0" : httpServletRequest.getParameter("isSubProcWork");
      workVO.setIsSubProcWork(isSubProcWork);
      String pareProcActiId = (httpServletRequest.getParameter("pareProcActiId") == null) ? "0" : httpServletRequest.getParameter("pareProcActiId");
      workVO.setPareProcActiId(pareProcActiId);
      String pareStepCount = (httpServletRequest.getParameter("pareStepCount") == null) ? "0" : httpServletRequest.getParameter("pareStepCount");
      workVO.setPareStepCount(pareStepCount);
      String pareTableId = (httpServletRequest.getParameter("pareTableId") == null) ? "0" : httpServletRequest.getParameter("pareTableId");
      workVO.setPareTableId(pareTableId);
      String pareRecordId = (httpServletRequest.getParameter("pareRecordId") == null) ? "0" : httpServletRequest.getParameter("pareRecordId");
      workVO.setPareRecordId(pareRecordId);
      workVO.setPareProcNextActiId("0");
      workVO.setEmergence((httpServletRequest.getParameter("emergence") == null) ? "0" : httpServletRequest.getParameter("emergence"));
      String cancelHref = httpServletRequest.getParameter("cancelHref");
      cancelHref = cancelHref.replaceAll("tableIdValue", tableId);
      cancelHref = cancelHref.replaceAll("processNameValue", processName);
      cancelHref = cancelHref.replaceAll("recordIdValue", recordId);
      cancelHref = cancelHref.replaceAll("processIdValue", processId);
      cancelHref = cancelHref.replaceAll("remindValueValue", remindFieldValue.toString());
      cancelHref = cancelHref.replaceAll("fileTitleValue", httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")));
      workVO.setCreatorCancelLink(cancelHref);
      String relProjectId = httpServletRequest.getParameter("relproject");
      if (relProjectId != null && !"null".equals(relProjectId) && !"".equals(relProjectId)) {
        workVO.setRelProjectId(Long.valueOf(relProjectId));
      } else {
        workVO.setRelProjectId(Long.valueOf(-1L));
      } 
      String[] toUser = { "" };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      if (processType.equals("0")) {
        workVO.setDeadLine("-1");
        workVO.setPressTime("-1");
        workVO.setCurStep("");
        String randomProcUser = httpServletRequest.getParameter("randomProcUser");
        if (randomProcUser.startsWith("$"))
          randomProcUser = randomProcUser.substring(1, randomProcUser.length()); 
        if (randomProcUser.endsWith("$"))
          randomProcUser = randomProcUser.substring(0, randomProcUser.length() - 1); 
        toUser[0] = randomProcUser;
      } else {
        String changeDeadLineTime;
        workVO.setActivity(new Long(httpServletRequest.getParameter("activityId")));
        workVO.setCurStep(httpServletRequest.getParameter("activityName"));
        workVO.setAllowStandFor(Integer.parseInt(httpServletRequest.getParameter("allowStandFor")));
        workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
        workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
        workVO.setPressTime(httpServletRequest.getParameter("pressTime"));
        workVO.setUserType(Integer.parseInt(httpServletRequest.getParameter("userType")));
        int userType = Integer.parseInt(httpServletRequest.getParameter("userType"));
        String operId = httpServletRequest.getParameter("operId");
        if (operId == null || "null".equals(operId) || operId.length() < 2) {
          toUser = new String[0];
        } else {
          operId = operId.substring(1, operId.length() - 1);
          toUser = operId.split("\\$\\$");
        } 
        int pressType = Integer.parseInt(httpServletRequest.getParameter("pressType"));
        workVO.setPressType(pressType);
        switch (pressType) {
          case 0:
            workVO.setDeadLine("-1");
            workVO.setPressTime("-1");
            break;
          case 1:
            changeDeadLineTime = httpServletRequest.getParameter("changeDeadLineTime");
            if (changeDeadLineTime == null || "null".equals(changeDeadLineTime) || "-1".equals(changeDeadLineTime) || 
              "".equals(changeDeadLineTime)) {
              workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
            } else {
              workVO.setDeadLine(changeDeadLineTime);
            } 
            workVO.setPressTime(httpServletRequest.getParameter("pressMotionTime"));
            break;
        } 
      } 
      String[] passUser = { "" };
      if (httpServletRequest.getParameter("mainNeedPassRound") != null) {
        String operId, operProcOrg;
        workVO.setNeedPassRound(true);
        int passRoundUserType = Integer.parseInt(httpServletRequest.getParameter("mainPassRoundUserType"));
        workVO.setPassRoundUserType(passRoundUserType);
        switch (passRoundUserType) {
          case 100:
            operId = httpServletRequest.getParameter("passRoundId");
            if (operId != null && !operId.equals("")) {
              ConversionString con = new ConversionString(operId);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              if (con.getGroupIdString() != null && 
                !"".equals(con.getGroupIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ","; 
              if (con.getOrgIdString() != null && 
                !"".equals(con.getOrgIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString()); 
              DbOpt dbopt = null;
              try {
                dbopt = new DbOpt();
                userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
                if (userIdStr.startsWith(","))
                  userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
                if (userIdStr.endsWith(","))
                  userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
                if (userIdStr.length() > 0) {
                  passUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + 
                      userIdStr + ")");
                } else {
                  passUser = new String[0];
                } 
                dbopt.close();
              } catch (Exception e) {
                try {
                  dbopt.close();
                } catch (SQLException sQLException) {}
                passUser = new String[0];
              } 
            } 
            break;
          case 10:
            operProcOrg = httpServletRequest.getParameter("passRoundId");
            if (operProcOrg != null && !"".equals(operProcOrg)) {
              operProcOrg = operProcOrg.replace("$", ",").substring(1, operProcOrg.length() - 1);
              passUser = operProcOrg.split(",,");
            } 
            break;
        } 
        workVO.setPassRoundUser(passUser);
      } else {
        workVO.setNeedPassRound(false);
      } 
      if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
        workVO.setDocTitle(httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName"))); 
      workVO.setDomainId(httpSession.getAttribute("domainId").toString());
      String nextTransactType = httpServletRequest.getParameter("approveMode");
      workVO.setTransactType(nextTransactType);
      String insertResult = workFlowBD.insertCurFieldStr(processId, tableId, (new StringBuilder(String.valueOf(recordId))).toString(), httpServletRequest.getParameter("curFieldStr"));
      if (!"0".equals(insertResult))
        return "-1"; 
      String dealTips = httpServletRequest.getParameter("dealTips");
      if (dealTips != null && !dealTips.equals("")) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        dealTips = "来自上一环节：" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : httpServletRequest.getParameter("activityName")) + ";  办理人：" + 
          httpSession.getAttribute("userName").toString() + 
          "; " + 
          df.format(new Date()) + " " + dealTips;
      } 
      String processDeadlineDate = httpServletRequest.getParameter("processDeadlineDate");
      String sendSMS = (httpServletRequest.getParameter("sendSMS") == null) ? "1" : httpServletRequest.getParameter("sendSMS");
      String[] para = { orgIdString, dealTips, processDeadlineDate, sendSMS };
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == 0L)
        return "-1"; 
      int count = 0;
      String sss = httpServletRequest.getParameter("resubmitWorkId");
      if (submitSuccess != 0L && httpServletRequest.getParameter("resubmitWorkId") != null && httpServletRequest.getParameter("resubmitWorkId").trim().length() > 0 && !httpServletRequest.getParameter("resubmitWorkId").toUpperCase().equals("NULL")) {
        List<String> sqlList = new ArrayList();
        String updateSql = "update JSF_WORK set workDelete = 1 where wf_work_id = " + httpServletRequest.getParameter("resubmitWorkId");
        if ("1".equals(SystemCommon.getReSubmitDel()) && httpServletRequest.getParameter("recordId") != null && httpServletRequest.getParameter("recordId").trim().length() > 0 && !httpServletRequest.getParameter("recordId").toUpperCase().equals("NULL"))
          count = getcount(httpServletRequest.getParameter("recordId")); 
        sqlList.add(updateSql);
        if (count < 1)
          workFlowBD.updateTable(sqlList); 
      } 
      if (submitSuccess != 0L) {
        WorkLogVO workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
        workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
        workLogVO.setSendAction("送" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : WorkflowForWeiXinUtil.decordStr(httpServletRequest.getParameter("activityName"))));
        if (processType.equals("0")) {
          workLogVO.setReceiveUserName(httpServletRequest.getParameter("randomProcUserName"));
        } else {
          workLogVO.setReceiveUserName(httpServletRequest.getParameter("operName"));
        } 
        workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
        workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
        workFlowButtonBD.setDealWithLog(workLogVO);
        if (httpServletRequest.getParameter("mainNeedPassRound") != null && !"".equals(httpServletRequest.getParameter("passRoundName"))) {
          workLogVO = new WorkLogVO();
          workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
          workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
          workLogVO.setSendAction("送" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : WorkflowForWeiXinUtil.decordStr(httpServletRequest.getParameter("activityName"))) + "阅件");
          String passRoundUser = httpServletRequest.getParameter("passRoundName");
          if (passRoundUser.endsWith(","))
            passRoundUser = passRoundUser.substring(0, passRoundUser.length() - 1); 
          workLogVO.setReceiveUserName(passRoundUser);
          workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
          workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
          workLogVO.setRecordId(recordId);
          workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
          workFlowButtonBD.setDealWithLog(workLogVO);
        } 
      } 
      if (httpServletRequest.getParameter("subProc") != null)
        httpServletRequest.setAttribute("subProcWorkId", (new StringBuilder(String.valueOf(submitSuccess))).toString()); 
      if ("1".equals(httpServletRequest.getParameter("savefirst"))) {
        WorkFlowButtonBD bd = new WorkFlowButtonBD();
        String workUrl = bd.getSaveFirstWorkUrl(processId, processName, tableId, recordId);
        httpServletRequest.setAttribute("sendUrl", workUrl);
        httpServletRequest.setAttribute("savefirst", "1");
      } 
      if (submitSuccess != 0L) {
        String fromFlagFlow = httpServletRequest.getParameter("fromFlagFlow");
        if ("fromDaiBan".equals(fromFlagFlow) || "fromDuBan".equals(fromFlagFlow)) {
          String collectId = httpServletRequest.getParameter("collectId");
          String wfWorkId = httpServletRequest.getParameter("wfWorkId");
          OACollectEJBBean collectBean = new OACollectEJBBean();
          String url = "/jsoa/oacollect/collectMessage.jsp?collectId=" + collectId + "&fromFlagFlow=fromZaiBan&wfWorkId=" + wfWorkId + "&sendWfWorkId=" + submitSuccess;
          String dateString = "now()";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("oracle") >= 0)
            dateString = "sysdate"; 
          String sql = "update JSF_WORK set WORKSTATUS=101,DEALWITHTIME=" + dateString + ",WORKMAINLINKFILE='" + url + "' where WF_WORK_ID=" + wfWorkId;
          try {
            collectBean.updateByYourYuanShengSql(sql);
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } 
    } 
    return result;
  }
  
  private void update(HttpServletRequest httpServletRequest) {
    // Byte code:
    //   0: aload_1
    //   1: iconst_1
    //   2: invokeinterface getSession : (Z)Ljavax/servlet/http/HttpSession;
    //   7: astore_2
    //   8: new com/js/oa/jsflow/service/WorkFlowCommonBD
    //   11: dup
    //   12: invokespecial <init> : ()V
    //   15: astore_3
    //   16: new com/js/oa/jsflow/service/WorkFlowButtonBD
    //   19: dup
    //   20: invokespecial <init> : ()V
    //   23: astore #4
    //   25: aload_3
    //   26: aload_1
    //   27: ldc_w 'curActivityId'
    //   30: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   35: invokevirtual getActivityClassMethod : (Ljava/lang/String;)Ljava/util/Map;
    //   38: astore #5
    //   40: ldc ''
    //   42: astore #6
    //   44: ldc ''
    //   46: astore #7
    //   48: aload #5
    //   50: ifnull -> 201
    //   53: aload #5
    //   55: ldc_w 'formClassName'
    //   58: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   63: ifnull -> 127
    //   66: aload #5
    //   68: ldc_w 'formClassName'
    //   71: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   76: invokevirtual toString : ()Ljava/lang/String;
    //   79: ldc ''
    //   81: invokevirtual equals : (Ljava/lang/Object;)Z
    //   84: ifne -> 127
    //   87: aload #5
    //   89: ldc_w 'formClassName'
    //   92: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   97: invokevirtual toString : ()Ljava/lang/String;
    //   100: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   103: ldc_w 'NULL'
    //   106: invokevirtual equals : (Ljava/lang/Object;)Z
    //   109: ifne -> 127
    //   112: aload #5
    //   114: ldc_w 'formClassName'
    //   117: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   122: invokevirtual toString : ()Ljava/lang/String;
    //   125: astore #6
    //   127: aload #5
    //   129: ldc_w 'formClassMethod'
    //   132: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   137: ifnull -> 201
    //   140: aload #5
    //   142: ldc_w 'formClassMethod'
    //   145: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   150: invokevirtual toString : ()Ljava/lang/String;
    //   153: ldc ''
    //   155: invokevirtual equals : (Ljava/lang/Object;)Z
    //   158: ifne -> 201
    //   161: aload #5
    //   163: ldc_w 'formClassMethod'
    //   166: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   171: invokevirtual toString : ()Ljava/lang/String;
    //   174: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   177: ldc_w 'NULL'
    //   180: invokevirtual equals : (Ljava/lang/Object;)Z
    //   183: ifne -> 201
    //   186: aload #5
    //   188: ldc_w 'formClassMethod'
    //   191: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   196: invokevirtual toString : ()Ljava/lang/String;
    //   199: astore #7
    //   201: ldc ''
    //   203: astore #8
    //   205: ldc ''
    //   207: aload #6
    //   209: invokevirtual equals : (Ljava/lang/Object;)Z
    //   212: ifne -> 296
    //   215: ldc ''
    //   217: aload #7
    //   219: invokevirtual equals : (Ljava/lang/Object;)Z
    //   222: ifne -> 296
    //   225: new com/js/oa/jsflow/util/FormReflection
    //   228: dup
    //   229: invokespecial <init> : ()V
    //   232: astore #9
    //   234: aload #9
    //   236: new java/lang/StringBuilder
    //   239: dup
    //   240: ldc_w 'com.js.oa.form.'
    //   243: invokespecial <init> : (Ljava/lang/String;)V
    //   246: aload #6
    //   248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: invokevirtual toString : ()Ljava/lang/String;
    //   254: aload #7
    //   256: aload_1
    //   257: invokevirtual execute : (Ljava/lang/String;Ljava/lang/String;Ljavax/servlet/http/HttpServletRequest;)Ljava/lang/Object;
    //   260: astore #10
    //   262: aload #10
    //   264: ifnull -> 296
    //   267: aload #10
    //   269: invokevirtual getClass : ()Ljava/lang/Class;
    //   272: invokevirtual toString : ()Ljava/lang/String;
    //   275: ldc_w 'class java.lang.String'
    //   278: invokevirtual equals : (Ljava/lang/Object;)Z
    //   281: ifeq -> 296
    //   284: aload #10
    //   286: invokevirtual toString : ()Ljava/lang/String;
    //   289: astore #8
    //   291: goto -> 296
    //   294: astore #9
    //   296: ldc_w 'error'
    //   299: aload #8
    //   301: invokevirtual equals : (Ljava/lang/Object;)Z
    //   304: ifne -> 3314
    //   307: aload_1
    //   308: ldc_w 'remindField'
    //   311: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   316: astore #9
    //   318: aload #9
    //   320: ldc_w '$'
    //   323: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   326: ifeq -> 354
    //   329: aload #9
    //   331: ldc_w '$'
    //   334: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   337: ifeq -> 354
    //   340: aload_1
    //   341: ldc_w 'motif'
    //   344: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   349: astore #8
    //   351: goto -> 396
    //   354: aload #9
    //   356: ldc_w 'S'
    //   359: invokevirtual indexOf : (Ljava/lang/String;)I
    //   362: ifge -> 372
    //   365: ldc ''
    //   367: astore #8
    //   369: goto -> 396
    //   372: aload_0
    //   373: aload #9
    //   375: aload_1
    //   376: ldc 'recordId'
    //   378: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   383: aload_1
    //   384: ldc 'tableId'
    //   386: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   391: invokespecial getRemindValue : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   394: astore #8
    //   396: aload #8
    //   398: invokestatic deleteEnter : (Ljava/lang/String;)Ljava/lang/String;
    //   401: astore #8
    //   403: aload_1
    //   404: ldc_w 'isStandForWork'
    //   407: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   412: astore #10
    //   414: aload_1
    //   415: ldc_w 'standForUserId'
    //   418: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   423: astore #11
    //   425: aload_1
    //   426: ldc_w 'standForUserName'
    //   429: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   434: astore #12
    //   436: aload_1
    //   437: ldc_w 'submitPersonId'
    //   440: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   445: astore #13
    //   447: aload_1
    //   448: ldc_w 'mainPressType'
    //   451: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   456: astore #14
    //   458: ldc '-1'
    //   460: astore #15
    //   462: ldc '-1'
    //   464: astore #16
    //   466: aload_1
    //   467: ldc_w 'include_comment'
    //   470: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   475: astore #17
    //   477: aload #17
    //   479: invokestatic escapeHTMLTags2 : (Ljava/lang/String;)Ljava/lang/String;
    //   482: astore #17
    //   484: aload_1
    //   485: ldc_w 'include_signcomment'
    //   488: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   493: astore #18
    //   495: aload_1
    //   496: ldc_w 'mainAllowStandFor'
    //   499: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   504: astore #19
    //   506: aload_1
    //   507: ldc_w 'mainAllowCancel'
    //   510: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   515: astore #20
    //   517: aload_1
    //   518: ldc_w 'curActivityId'
    //   521: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   526: astore #21
    //   528: aload_1
    //   529: ldc_w 'curActivityName'
    //   532: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   537: astore #22
    //   539: aload_1
    //   540: ldc_w 'mainNextActivityId'
    //   543: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   548: astore #23
    //   550: aload_1
    //   551: ldc_w 'mainNextActivityName'
    //   554: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   559: astore #24
    //   561: aload_1
    //   562: ldc_w 'processName'
    //   565: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   570: astore #25
    //   572: aload_1
    //   573: ldc 'tableId'
    //   575: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   580: astore #26
    //   582: new com/js/oa/eform/service/CustomFormBD
    //   585: dup
    //   586: invokespecial <init> : ()V
    //   589: aload #26
    //   591: invokevirtual getTable : (Ljava/lang/String;)Ljava/lang/String;
    //   594: astore #27
    //   596: aload_1
    //   597: ldc 'recordId'
    //   599: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   604: astore #28
    //   606: aload_1
    //   607: ldc_w 'workId'
    //   610: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   615: astore #29
    //   617: aload_2
    //   618: ldc_w 'userId'
    //   621: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   626: invokevirtual toString : ()Ljava/lang/String;
    //   629: astore #30
    //   631: aload_2
    //   632: ldc_w 'orgId'
    //   635: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   640: invokevirtual toString : ()Ljava/lang/String;
    //   643: astore #31
    //   645: aload_2
    //   646: ldc_w 'orgIdString'
    //   649: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   654: invokevirtual toString : ()Ljava/lang/String;
    //   657: astore #32
    //   659: aload_1
    //   660: ldc_w 'stepCount'
    //   663: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   668: astore #33
    //   670: aload_1
    //   671: ldc_w 'activityClass'
    //   674: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   679: astore #34
    //   681: aload_1
    //   682: ldc_w 'mainDeadLineTime'
    //   685: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   690: astore #15
    //   692: aload_1
    //   693: ldc_w 'mainPressMotionTime'
    //   696: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   701: astore #16
    //   703: aload_1
    //   704: ldc_w 'relproject'
    //   707: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   712: astore #35
    //   714: new com/js/oa/jsflow/service/WorkFlowBD
    //   717: dup
    //   718: invokespecial <init> : ()V
    //   721: astore #36
    //   723: iconst_1
    //   724: anewarray java/lang/String
    //   727: dup
    //   728: iconst_0
    //   729: ldc ''
    //   731: aastore
    //   732: astore #37
    //   734: ldc '3'
    //   736: aload #34
    //   738: invokevirtual equals : (Ljava/lang/Object;)Z
    //   741: ifeq -> 859
    //   744: new com/js/oa/jsflow/util/NewWorkflowUtil
    //   747: dup
    //   748: invokespecial <init> : ()V
    //   751: astore #38
    //   753: aload #38
    //   755: aload #26
    //   757: aload #28
    //   759: aload #21
    //   761: aload #33
    //   763: invokestatic parseInt : (Ljava/lang/String;)I
    //   766: invokevirtual getPreActivity : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lcom/js/oa/jsflow/vo/ActivityVO;
    //   769: astore #39
    //   771: aload #39
    //   773: invokevirtual getId : ()J
    //   776: invokestatic valueOf : (J)Ljava/lang/String;
    //   779: astore #23
    //   781: aload #39
    //   783: invokevirtual getName : ()Ljava/lang/String;
    //   786: astore #24
    //   788: aload #38
    //   790: aload #26
    //   792: aload #28
    //   794: aload #21
    //   796: aload #33
    //   798: invokestatic parseInt : (Ljava/lang/String;)I
    //   801: invokevirtual getPreActivityUser : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)[Ljava/lang/String;
    //   804: astore #37
    //   806: aload #39
    //   808: invokevirtual getPressType : ()I
    //   811: invokestatic valueOf : (I)Ljava/lang/String;
    //   814: astore #14
    //   816: aload #39
    //   818: invokevirtual getDeadlineTime : ()I
    //   821: invokestatic valueOf : (I)Ljava/lang/String;
    //   824: astore #15
    //   826: aload #39
    //   828: invokevirtual getPressMotionTime : ()I
    //   831: invokestatic valueOf : (I)Ljava/lang/String;
    //   834: astore #16
    //   836: aload #39
    //   838: invokevirtual getAllowStandFor : ()I
    //   841: invokestatic valueOf : (I)Ljava/lang/String;
    //   844: astore #19
    //   846: aload #39
    //   848: invokevirtual getAllowcancel : ()I
    //   851: invokestatic valueOf : (I)Ljava/lang/String;
    //   854: astore #20
    //   856: goto -> 929
    //   859: aload_1
    //   860: ldc_w 'operId'
    //   863: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   868: astore #38
    //   870: aload #38
    //   872: ifnull -> 895
    //   875: ldc_w 'null'
    //   878: aload #38
    //   880: invokevirtual equals : (Ljava/lang/Object;)Z
    //   883: ifne -> 895
    //   886: aload #38
    //   888: invokevirtual length : ()I
    //   891: iconst_2
    //   892: if_icmpge -> 904
    //   895: iconst_0
    //   896: anewarray java/lang/String
    //   899: astore #37
    //   901: goto -> 929
    //   904: aload #38
    //   906: iconst_1
    //   907: aload #38
    //   909: invokevirtual length : ()I
    //   912: iconst_1
    //   913: isub
    //   914: invokevirtual substring : (II)Ljava/lang/String;
    //   917: astore #38
    //   919: aload #38
    //   921: ldc_w '\$\$'
    //   924: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   927: astore #37
    //   929: aload #37
    //   931: ifnull -> 3257
    //   934: aload #37
    //   936: arraylength
    //   937: ifle -> 3257
    //   940: ldc ''
    //   942: aload #37
    //   944: iconst_0
    //   945: aaload
    //   946: invokevirtual equals : (Ljava/lang/Object;)Z
    //   949: ifne -> 3257
    //   952: ldc '3'
    //   954: aload #34
    //   956: invokevirtual equals : (Ljava/lang/Object;)Z
    //   959: ifne -> 1009
    //   962: aload_1
    //   963: ldc_w 'operName'
    //   966: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   971: ifnull -> 3257
    //   974: ldc_w 'null'
    //   977: aload_1
    //   978: ldc_w 'operName'
    //   981: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   986: invokevirtual equals : (Ljava/lang/Object;)Z
    //   989: ifne -> 3257
    //   992: ldc ''
    //   994: aload_1
    //   995: ldc_w 'operName'
    //   998: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1003: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1006: ifne -> 3257
    //   1009: aload #14
    //   1011: invokestatic parseInt : (Ljava/lang/String;)I
    //   1014: tableswitch default -> 1130, 0 -> 1040, 1 -> 1051, 2 -> 1130
    //   1040: ldc '-1'
    //   1042: astore #15
    //   1044: ldc '-1'
    //   1046: astore #16
    //   1048: goto -> 1130
    //   1051: aload_1
    //   1052: ldc_w 'changeDeadLineTime'
    //   1055: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1060: astore #38
    //   1062: aload #38
    //   1064: ifnull -> 1098
    //   1067: ldc_w 'null'
    //   1070: aload #38
    //   1072: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1075: ifne -> 1098
    //   1078: ldc '-1'
    //   1080: aload #38
    //   1082: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1085: ifne -> 1098
    //   1088: ldc ''
    //   1090: aload #38
    //   1092: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1095: ifeq -> 1112
    //   1098: aload_1
    //   1099: ldc_w 'mainDeadLineTime'
    //   1102: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1107: astore #15
    //   1109: goto -> 1116
    //   1112: aload #38
    //   1114: astore #15
    //   1116: aload_1
    //   1117: ldc_w 'mainPressMotionTime'
    //   1120: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1125: astore #16
    //   1127: goto -> 1130
    //   1130: ldc ''
    //   1132: astore #38
    //   1134: iconst_1
    //   1135: anewarray java/lang/String
    //   1138: dup
    //   1139: iconst_0
    //   1140: ldc ''
    //   1142: aastore
    //   1143: astore #39
    //   1145: aload_1
    //   1146: ldc_w 'mainNeedPassRound'
    //   1149: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1154: ifnull -> 1572
    //   1157: aload_1
    //   1158: ldc_w 'mainNeedPassRound'
    //   1161: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1166: astore #38
    //   1168: aload_1
    //   1169: ldc_w 'mainPassRoundUserType'
    //   1172: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1177: invokestatic parseInt : (Ljava/lang/String;)I
    //   1180: istore #40
    //   1182: iload #40
    //   1184: bipush #10
    //   1186: if_icmpne -> 1193
    //   1189: bipush #100
    //   1191: istore #40
    //   1193: iload #40
    //   1195: lookupswitch default -> 1572, 10 -> 1552, 100 -> 1220
    //   1220: aload_1
    //   1221: ldc_w 'passRoundId'
    //   1224: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1229: astore #41
    //   1231: aload #41
    //   1233: ifnull -> 1572
    //   1236: aload #41
    //   1238: ldc ''
    //   1240: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1243: ifne -> 1572
    //   1246: new com/js/util/util/ConversionString
    //   1249: dup
    //   1250: aload #41
    //   1252: invokespecial <init> : (Ljava/lang/String;)V
    //   1255: astore #42
    //   1257: new java/lang/StringBuilder
    //   1260: dup
    //   1261: aload #42
    //   1263: invokevirtual getUserIdString : ()Ljava/lang/String;
    //   1266: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1269: invokespecial <init> : (Ljava/lang/String;)V
    //   1272: ldc_w ','
    //   1275: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1278: invokevirtual toString : ()Ljava/lang/String;
    //   1281: astore #43
    //   1283: aload #42
    //   1285: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1288: ifnull -> 1339
    //   1291: ldc ''
    //   1293: aload #42
    //   1295: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1298: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1301: ifne -> 1339
    //   1304: new java/lang/StringBuilder
    //   1307: dup
    //   1308: aload #43
    //   1310: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1313: invokespecial <init> : (Ljava/lang/String;)V
    //   1316: aload_0
    //   1317: aload #42
    //   1319: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1322: invokevirtual getUserByGroup : (Ljava/lang/String;)Ljava/lang/String;
    //   1325: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1328: ldc_w ','
    //   1331: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1334: invokevirtual toString : ()Ljava/lang/String;
    //   1337: astore #43
    //   1339: aload #42
    //   1341: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1344: ifnull -> 1389
    //   1347: ldc ''
    //   1349: aload #42
    //   1351: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1354: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1357: ifne -> 1389
    //   1360: new java/lang/StringBuilder
    //   1363: dup
    //   1364: aload #43
    //   1366: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1369: invokespecial <init> : (Ljava/lang/String;)V
    //   1372: aload_0
    //   1373: aload #42
    //   1375: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1378: invokevirtual getUserByOrg : (Ljava/lang/String;)Ljava/lang/String;
    //   1381: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1384: invokevirtual toString : ()Ljava/lang/String;
    //   1387: astore #43
    //   1389: aconst_null
    //   1390: astore #44
    //   1392: new com/js/oa/userdb/util/DbOpt
    //   1395: dup
    //   1396: invokespecial <init> : ()V
    //   1399: astore #44
    //   1401: aload #43
    //   1403: ldc_w ',,'
    //   1406: ldc_w ','
    //   1409: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1412: ldc_w ',,'
    //   1415: ldc_w ','
    //   1418: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1421: astore #43
    //   1423: aload #43
    //   1425: ldc_w ','
    //   1428: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   1431: ifeq -> 1449
    //   1434: aload #43
    //   1436: iconst_1
    //   1437: aload #43
    //   1439: invokevirtual length : ()I
    //   1442: iconst_1
    //   1443: isub
    //   1444: invokevirtual substring : (II)Ljava/lang/String;
    //   1447: astore #43
    //   1449: aload #43
    //   1451: ldc_w ','
    //   1454: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   1457: ifeq -> 1475
    //   1460: aload #43
    //   1462: iconst_0
    //   1463: aload #43
    //   1465: invokevirtual length : ()I
    //   1468: iconst_1
    //   1469: isub
    //   1470: invokevirtual substring : (II)Ljava/lang/String;
    //   1473: astore #43
    //   1475: aload #43
    //   1477: invokevirtual length : ()I
    //   1480: ifle -> 1517
    //   1483: aload #44
    //   1485: new java/lang/StringBuilder
    //   1488: dup
    //   1489: ldc_w 'select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in ('
    //   1492: invokespecial <init> : (Ljava/lang/String;)V
    //   1495: aload #43
    //   1497: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1500: ldc_w ')'
    //   1503: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1506: invokevirtual toString : ()Ljava/lang/String;
    //   1509: invokevirtual executeQueryToStrArr1 : (Ljava/lang/String;)[Ljava/lang/String;
    //   1512: astore #39
    //   1514: goto -> 1523
    //   1517: iconst_0
    //   1518: anewarray java/lang/String
    //   1521: astore #39
    //   1523: aload #44
    //   1525: invokevirtual close : ()V
    //   1528: goto -> 1572
    //   1531: astore #45
    //   1533: aload #44
    //   1535: invokevirtual close : ()V
    //   1538: goto -> 1543
    //   1541: astore #46
    //   1543: iconst_0
    //   1544: anewarray java/lang/String
    //   1547: astore #39
    //   1549: goto -> 1572
    //   1552: aload_1
    //   1553: ldc_w 'passRoundId'
    //   1556: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1561: astore #42
    //   1563: aload #36
    //   1565: aload #42
    //   1567: invokevirtual getLeaderListByOrgId : (Ljava/lang/String;)[Ljava/lang/String;
    //   1570: astore #39
    //   1572: aload_1
    //   1573: ldc_w 'subProcWorkId'
    //   1576: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1581: ifnonnull -> 1589
    //   1584: ldc '0'
    //   1586: goto -> 1598
    //   1589: aload_1
    //   1590: ldc_w 'subProcWorkId'
    //   1593: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1598: astore #40
    //   1600: aload_1
    //   1601: ldc_w 'modiCommentId'
    //   1604: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1609: astore #41
    //   1611: new java/util/HashMap
    //   1614: dup
    //   1615: invokespecial <init> : ()V
    //   1618: astore #42
    //   1620: aload #42
    //   1622: ldc 'tableId'
    //   1624: aload #26
    //   1626: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1631: pop
    //   1632: aload #42
    //   1634: ldc 'recordId'
    //   1636: aload #28
    //   1638: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1643: pop
    //   1644: aload #42
    //   1646: ldc_w 'curActivityName'
    //   1649: aload #22
    //   1651: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1656: pop
    //   1657: aload #42
    //   1659: ldc_w 'curActivityId'
    //   1662: aload #21
    //   1664: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1669: pop
    //   1670: aload #42
    //   1672: ldc_w 'userId'
    //   1675: aload #30
    //   1677: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1682: pop
    //   1683: aload #42
    //   1685: ldc_w 'orgIdString'
    //   1688: aload #32
    //   1690: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1695: pop
    //   1696: aload #42
    //   1698: ldc_w 'comment'
    //   1701: aload #17
    //   1703: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1708: pop
    //   1709: aload #42
    //   1711: ldc_w 'signcomment'
    //   1714: aload #18
    //   1716: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1721: pop
    //   1722: aload #42
    //   1724: ldc_w 'nextActivityName'
    //   1727: aload #24
    //   1729: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1734: pop
    //   1735: aload #42
    //   1737: ldc_w 'nextActivityId'
    //   1740: aload #23
    //   1742: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1747: pop
    //   1748: aload #42
    //   1750: ldc_w 'stepCount'
    //   1753: aload #33
    //   1755: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1760: pop
    //   1761: aload #42
    //   1763: ldc_w 'isStandForWork'
    //   1766: aload #10
    //   1768: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1773: pop
    //   1774: aload #42
    //   1776: ldc_w 'standForUserId'
    //   1779: aload #11
    //   1781: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1786: pop
    //   1787: aload #42
    //   1789: ldc_w 'activityClass'
    //   1792: aload #34
    //   1794: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1799: pop
    //   1800: aload #42
    //   1802: ldc_w 'subProcWorkId'
    //   1805: aload #40
    //   1807: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1812: pop
    //   1813: aload_1
    //   1814: ldc_w 'include_commField'
    //   1817: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1822: ifnull -> 1845
    //   1825: aload #42
    //   1827: ldc_w 'commentField'
    //   1830: aload_1
    //   1831: ldc_w 'include_commField'
    //   1834: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1839: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1844: pop
    //   1845: aload #42
    //   1847: ldc_w 'userScope'
    //   1850: aload_1
    //   1851: ldc_w 'userScope'
    //   1854: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1859: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1864: pop
    //   1865: aload #42
    //   1867: ldc_w 'scopeId'
    //   1870: aload_1
    //   1871: ldc_w 'scopeId'
    //   1874: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1879: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1884: pop
    //   1885: aload #42
    //   1887: ldc_w 'modiCommentId'
    //   1890: aload #41
    //   1892: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1897: pop
    //   1898: aload #42
    //   1900: ldc_w 'relproject'
    //   1903: aload #35
    //   1905: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1910: pop
    //   1911: new com/js/oa/jsflow/service/WorkFlowCommonBD
    //   1914: dup
    //   1915: invokespecial <init> : ()V
    //   1918: astore #43
    //   1920: aload_1
    //   1921: ldc_w 'subProcType'
    //   1924: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1929: ifnonnull -> 1937
    //   1932: ldc '-1'
    //   1934: goto -> 1946
    //   1937: aload_1
    //   1938: ldc_w 'subProcType'
    //   1941: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1946: astore #44
    //   1948: aload_1
    //   1949: ldc_w 'mainLinkFile'
    //   1952: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1957: astore #45
    //   1959: aload #36
    //   1961: aload #26
    //   1963: aload #28
    //   1965: aload #21
    //   1967: invokevirtual getTransactType : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1970: astore #46
    //   1972: aload_1
    //   1973: ldc_w 'approveMode'
    //   1976: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1981: astore #47
    //   1983: aload #8
    //   1985: ifnull -> 2006
    //   1988: aload #8
    //   1990: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   1993: ldc_w 'NULL'
    //   1996: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1999: ifeq -> 2006
    //   2002: ldc ''
    //   2004: astore #8
    //   2006: ldc ''
    //   2008: astore #48
    //   2010: ldc ''
    //   2012: aload_1
    //   2013: ldc_w 'titleFieldName'
    //   2016: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2021: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2024: ifne -> 2044
    //   2027: aload_1
    //   2028: aload_1
    //   2029: ldc_w 'titleFieldName'
    //   2032: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2037: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2042: astore #48
    //   2044: aload_1
    //   2045: ldc_w 'emergence'
    //   2048: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2053: ifnonnull -> 2061
    //   2056: ldc '0'
    //   2058: goto -> 2070
    //   2061: aload_1
    //   2062: ldc_w 'emergence'
    //   2065: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2070: astore #49
    //   2072: aload_1
    //   2073: ldc_w 'dealTips'
    //   2076: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2081: astore #50
    //   2083: aload #50
    //   2085: ifnull -> 2183
    //   2088: aload #50
    //   2090: ldc ''
    //   2092: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2095: ifne -> 2183
    //   2098: new java/text/SimpleDateFormat
    //   2101: dup
    //   2102: ldc_w 'yyyy-MM-dd hh:mm'
    //   2105: invokespecial <init> : (Ljava/lang/String;)V
    //   2108: astore #51
    //   2110: new java/lang/StringBuilder
    //   2113: dup
    //   2114: ldc_w '来自上一环节:'
    //   2117: invokespecial <init> : (Ljava/lang/String;)V
    //   2120: aload #24
    //   2122: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2125: ldc_w ';  办理人：'
    //   2128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2131: aload_2
    //   2132: ldc_w 'userName'
    //   2135: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2140: invokevirtual toString : ()Ljava/lang/String;
    //   2143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2146: ldc_w '; '
    //   2149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2152: aload #51
    //   2154: new java/util/Date
    //   2157: dup
    //   2158: invokespecial <init> : ()V
    //   2161: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   2164: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2167: ldc_w ' '
    //   2170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2173: aload #50
    //   2175: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2178: invokevirtual toString : ()Ljava/lang/String;
    //   2181: astore #50
    //   2183: aload_1
    //   2184: ldc_w 'sendSMS'
    //   2187: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2192: ifnonnull -> 2200
    //   2195: ldc '1'
    //   2197: goto -> 2209
    //   2200: aload_1
    //   2201: ldc_w 'sendSMS'
    //   2204: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2209: astore #51
    //   2211: bipush #23
    //   2213: anewarray java/lang/String
    //   2216: dup
    //   2217: iconst_0
    //   2218: aload #24
    //   2220: aastore
    //   2221: dup
    //   2222: iconst_1
    //   2223: aload #23
    //   2225: aastore
    //   2226: dup
    //   2227: iconst_2
    //   2228: aload #29
    //   2230: aastore
    //   2231: dup
    //   2232: iconst_3
    //   2233: aload #15
    //   2235: aastore
    //   2236: dup
    //   2237: iconst_4
    //   2238: aload #16
    //   2240: aastore
    //   2241: dup
    //   2242: iconst_5
    //   2243: aload #21
    //   2245: aastore
    //   2246: dup
    //   2247: bipush #6
    //   2249: aload #20
    //   2251: aastore
    //   2252: dup
    //   2253: bipush #7
    //   2255: aload #33
    //   2257: aastore
    //   2258: dup
    //   2259: bipush #8
    //   2261: aload #8
    //   2263: aastore
    //   2264: dup
    //   2265: bipush #9
    //   2267: aload #46
    //   2269: aastore
    //   2270: dup
    //   2271: bipush #10
    //   2273: aload #45
    //   2275: aastore
    //   2276: dup
    //   2277: bipush #11
    //   2279: aload #19
    //   2281: aastore
    //   2282: dup
    //   2283: bipush #12
    //   2285: aload #10
    //   2287: aastore
    //   2288: dup
    //   2289: bipush #13
    //   2291: aload #30
    //   2293: aastore
    //   2294: dup
    //   2295: bipush #14
    //   2297: aload #11
    //   2299: aastore
    //   2300: dup
    //   2301: bipush #15
    //   2303: aload #12
    //   2305: aastore
    //   2306: dup
    //   2307: bipush #16
    //   2309: aload #34
    //   2311: aastore
    //   2312: dup
    //   2313: bipush #17
    //   2315: aload #44
    //   2317: aastore
    //   2318: dup
    //   2319: bipush #18
    //   2321: aload #48
    //   2323: aastore
    //   2324: dup
    //   2325: bipush #19
    //   2327: aload #49
    //   2329: aastore
    //   2330: dup
    //   2331: bipush #20
    //   2333: aload #47
    //   2335: aastore
    //   2336: dup
    //   2337: bipush #21
    //   2339: aload #50
    //   2341: aastore
    //   2342: dup
    //   2343: bipush #22
    //   2345: aload #51
    //   2347: aastore
    //   2348: astore #52
    //   2350: aload #43
    //   2352: aload #42
    //   2354: aload #52
    //   2356: aload #37
    //   2358: aload #38
    //   2360: aload #39
    //   2362: invokevirtual transWorkflowButton : (Ljava/util/Map;[Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Integer;
    //   2365: astore #53
    //   2367: aload #53
    //   2369: invokevirtual intValue : ()I
    //   2372: ifle -> 2376
    //   2375: return
    //   2376: aload #53
    //   2378: invokevirtual intValue : ()I
    //   2381: ifge -> 2397
    //   2384: aload_1
    //   2385: ldc 'flowfaild'
    //   2387: ldc '1'
    //   2389: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2394: goto -> 3267
    //   2397: new com/js/oa/jsflow/vo/WorkLogVO
    //   2400: dup
    //   2401: invokespecial <init> : ()V
    //   2404: astore #54
    //   2406: ldc '3'
    //   2408: aload #34
    //   2410: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2413: ifeq -> 2694
    //   2416: aload #54
    //   2418: aload_2
    //   2419: ldc_w 'userId'
    //   2422: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2427: invokevirtual toString : ()Ljava/lang/String;
    //   2430: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   2433: aload #54
    //   2435: aload_2
    //   2436: ldc_w 'userName'
    //   2439: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2444: invokevirtual toString : ()Ljava/lang/String;
    //   2447: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   2450: ldc_w 'shandongjiguanshiwuju'
    //   2453: invokestatic getCustomerName : ()Ljava/lang/String;
    //   2456: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2459: ifeq -> 2488
    //   2462: aload #54
    //   2464: new java/lang/StringBuilder
    //   2467: dup
    //   2468: ldc_w '返回'
    //   2471: invokespecial <init> : (Ljava/lang/String;)V
    //   2474: aload #24
    //   2476: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2479: invokevirtual toString : ()Ljava/lang/String;
    //   2482: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2485: goto -> 2511
    //   2488: aload #54
    //   2490: new java/lang/StringBuilder
    //   2493: dup
    //   2494: ldc_w '送'
    //   2497: invokespecial <init> : (Ljava/lang/String;)V
    //   2500: aload #24
    //   2502: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2505: invokevirtual toString : ()Ljava/lang/String;
    //   2508: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2511: ldc '0'
    //   2513: astore #55
    //   2515: iconst_0
    //   2516: istore #56
    //   2518: goto -> 2555
    //   2521: new java/lang/StringBuilder
    //   2524: dup
    //   2525: aload #55
    //   2527: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2530: invokespecial <init> : (Ljava/lang/String;)V
    //   2533: aload #37
    //   2535: iload #56
    //   2537: aaload
    //   2538: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2541: ldc_w ','
    //   2544: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2547: invokevirtual toString : ()Ljava/lang/String;
    //   2550: astore #55
    //   2552: iinc #56, 1
    //   2555: iload #56
    //   2557: aload #37
    //   2559: arraylength
    //   2560: if_icmplt -> 2521
    //   2563: aload #55
    //   2565: ldc_w ','
    //   2568: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2571: ifeq -> 2589
    //   2574: aload #55
    //   2576: iconst_0
    //   2577: aload #55
    //   2579: invokevirtual length : ()I
    //   2582: iconst_1
    //   2583: isub
    //   2584: invokevirtual substring : (II)Ljava/lang/String;
    //   2587: astore #55
    //   2589: new com/js/system/service/usermanager/UserBD
    //   2592: dup
    //   2593: invokespecial <init> : ()V
    //   2596: aload #55
    //   2598: invokevirtual getUserNameById : (Ljava/lang/String;)Ljava/lang/String;
    //   2601: astore #55
    //   2603: aload #55
    //   2605: ldc_w ','
    //   2608: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2611: ifeq -> 2629
    //   2614: aload #55
    //   2616: iconst_0
    //   2617: aload #55
    //   2619: invokevirtual length : ()I
    //   2622: iconst_1
    //   2623: isub
    //   2624: invokevirtual substring : (II)Ljava/lang/String;
    //   2627: astore #55
    //   2629: aload #54
    //   2631: aload #55
    //   2633: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   2636: aload #54
    //   2638: aload_1
    //   2639: ldc 'processId'
    //   2641: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2646: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   2649: aload #54
    //   2651: aload_1
    //   2652: ldc 'tableId'
    //   2654: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2659: invokevirtual setTableId : (Ljava/lang/String;)V
    //   2662: aload #54
    //   2664: aload_1
    //   2665: ldc 'recordId'
    //   2667: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2672: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   2675: aload #54
    //   2677: aload_2
    //   2678: ldc 'domainId'
    //   2680: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2685: invokevirtual toString : ()Ljava/lang/String;
    //   2688: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   2691: goto -> 2823
    //   2694: aload #54
    //   2696: aload_2
    //   2697: ldc_w 'userId'
    //   2700: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2705: invokevirtual toString : ()Ljava/lang/String;
    //   2708: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   2711: aload #54
    //   2713: aload_2
    //   2714: ldc_w 'userName'
    //   2717: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2722: invokevirtual toString : ()Ljava/lang/String;
    //   2725: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   2728: aload #54
    //   2730: new java/lang/StringBuilder
    //   2733: dup
    //   2734: ldc_w '送'
    //   2737: invokespecial <init> : (Ljava/lang/String;)V
    //   2740: aload #24
    //   2742: invokestatic decordStr : (Ljava/lang/Object;)Ljava/lang/String;
    //   2745: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2748: invokevirtual toString : ()Ljava/lang/String;
    //   2751: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2754: aload #54
    //   2756: aload_1
    //   2757: ldc_w 'operName'
    //   2760: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2765: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   2768: aload #54
    //   2770: aload_1
    //   2771: ldc 'processId'
    //   2773: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2778: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   2781: aload #54
    //   2783: aload_1
    //   2784: ldc 'tableId'
    //   2786: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2791: invokevirtual setTableId : (Ljava/lang/String;)V
    //   2794: aload #54
    //   2796: aload_1
    //   2797: ldc 'recordId'
    //   2799: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2804: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   2807: aload #54
    //   2809: aload_2
    //   2810: ldc 'domainId'
    //   2812: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2817: invokevirtual toString : ()Ljava/lang/String;
    //   2820: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   2823: aload #4
    //   2825: aload #54
    //   2827: invokevirtual setDealWithLog : (Lcom/js/oa/jsflow/vo/WorkLogVO;)V
    //   2830: aload_1
    //   2831: ldc_w 'mainNeedPassRound'
    //   2834: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2839: ifnull -> 3010
    //   2842: ldc ''
    //   2844: aload_1
    //   2845: ldc_w 'passRoundName'
    //   2848: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2853: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2856: ifne -> 3010
    //   2859: new com/js/oa/jsflow/vo/WorkLogVO
    //   2862: dup
    //   2863: invokespecial <init> : ()V
    //   2866: astore #54
    //   2868: aload #54
    //   2870: aload_2
    //   2871: ldc_w 'userId'
    //   2874: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2879: invokevirtual toString : ()Ljava/lang/String;
    //   2882: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   2885: aload #54
    //   2887: aload_2
    //   2888: ldc_w 'userName'
    //   2891: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2896: invokevirtual toString : ()Ljava/lang/String;
    //   2899: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   2902: aload #54
    //   2904: new java/lang/StringBuilder
    //   2907: dup
    //   2908: ldc_w '送'
    //   2911: invokespecial <init> : (Ljava/lang/String;)V
    //   2914: aload #24
    //   2916: invokestatic decordStr : (Ljava/lang/Object;)Ljava/lang/String;
    //   2919: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2922: ldc_w '阅件'
    //   2925: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2928: invokevirtual toString : ()Ljava/lang/String;
    //   2931: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2934: aload #54
    //   2936: aload_1
    //   2937: ldc_w 'passRoundName'
    //   2940: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2945: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   2948: aload #54
    //   2950: aload_1
    //   2951: ldc 'processId'
    //   2953: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2958: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   2961: aload #54
    //   2963: aload_1
    //   2964: ldc 'tableId'
    //   2966: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2971: invokevirtual setTableId : (Ljava/lang/String;)V
    //   2974: aload #54
    //   2976: aload_1
    //   2977: ldc 'recordId'
    //   2979: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2984: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   2987: aload #54
    //   2989: aload_2
    //   2990: ldc 'domainId'
    //   2992: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2997: invokevirtual toString : ()Ljava/lang/String;
    //   3000: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   3003: aload #4
    //   3005: aload #54
    //   3007: invokevirtual setDealWithLog : (Lcom/js/oa/jsflow/vo/WorkLogVO;)V
    //   3010: aload_1
    //   3011: ldc_w 'sendMsgToInitiator'
    //   3014: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3019: ifnull -> 3267
    //   3022: ldc '1'
    //   3024: aload_1
    //   3025: ldc_w 'sendMsgToInitiator'
    //   3028: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3033: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3036: ifeq -> 3267
    //   3039: new java/lang/StringBuilder
    //   3042: dup
    //   3043: ldc_w 'SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID='
    //   3046: invokespecial <init> : (Ljava/lang/String;)V
    //   3049: aload_1
    //   3050: ldc 'processId'
    //   3052: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3057: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3060: ldc_w ' AND a.worktable_id='
    //   3063: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3066: aload #26
    //   3068: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3071: ldc_w ' AND a.workrecord_id='
    //   3074: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3077: aload #28
    //   3079: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3082: ldc_w ' AND workstepcount=0'
    //   3085: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3088: invokevirtual toString : ()Ljava/lang/String;
    //   3091: astore #55
    //   3093: new com/js/util/util/DataSourceBase
    //   3096: dup
    //   3097: invokespecial <init> : ()V
    //   3100: aload #55
    //   3102: invokevirtual queryArrayBySql : (Ljava/lang/String;)[[Ljava/lang/String;
    //   3105: astore #56
    //   3107: new java/lang/StringBuilder
    //   3110: dup
    //   3111: ldc_w '/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId='
    //   3114: invokespecial <init> : (Ljava/lang/String;)V
    //   3117: aload #29
    //   3119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3122: invokevirtual toString : ()Ljava/lang/String;
    //   3125: astore #57
    //   3127: new java/lang/StringBuilder
    //   3130: dup
    //   3131: ldc_w '您发起的'
    //   3134: invokespecial <init> : (Ljava/lang/String;)V
    //   3137: aload_1
    //   3138: ldc_w 'processName'
    //   3141: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3146: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3149: ldc_w '流程已经被'
    //   3152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3155: aload_1
    //   3156: ldc_w 'user_Name'
    //   3159: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3164: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3167: ldc_w '('
    //   3170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3173: aload_1
    //   3174: ldc_w 'curActivityName'
    //   3177: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3182: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3185: ldc_w ')处理完毕！'
    //   3188: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3191: invokevirtual toString : ()Ljava/lang/String;
    //   3194: aload #57
    //   3196: aload #56
    //   3198: iconst_0
    //   3199: aaload
    //   3200: iconst_0
    //   3201: aaload
    //   3202: ldc_w 'jsflow'
    //   3205: new java/util/Date
    //   3208: dup
    //   3209: invokespecial <init> : ()V
    //   3212: new java/text/SimpleDateFormat
    //   3215: dup
    //   3216: ldc_w 'yyyy-MM-dd HH:mm:ss'
    //   3219: invokespecial <init> : (Ljava/lang/String;)V
    //   3222: ldc_w '2050-01-01 00:00:00'
    //   3225: invokevirtual parse : (Ljava/lang/String;)Ljava/util/Date;
    //   3228: ldc_w '系统提醒'
    //   3231: aload #56
    //   3233: iconst_0
    //   3234: aaload
    //   3235: iconst_2
    //   3236: aaload
    //   3237: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   3240: iconst_1
    //   3241: invokestatic sendMessageToUsers2 : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Long;I)V
    //   3244: goto -> 3267
    //   3247: astore #57
    //   3249: aload #57
    //   3251: invokevirtual printStackTrace : ()V
    //   3254: goto -> 3267
    //   3257: aload_1
    //   3258: ldc 'flowfaild'
    //   3260: ldc '1'
    //   3262: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3267: aload_1
    //   3268: ldc_w 'subProcTempWorkId'
    //   3271: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3276: ifnull -> 3314
    //   3279: ldc ''
    //   3281: aload_1
    //   3282: ldc_w 'subProcTempWorkId'
    //   3285: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3290: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3293: ifne -> 3314
    //   3296: aload_1
    //   3297: ldc_w 'subProcWorkId'
    //   3300: aload_1
    //   3301: ldc_w 'subProcTempWorkId'
    //   3304: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3309: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3314: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #1822	-> 0
    //   #1823	-> 8
    //   #1824	-> 16
    //   #1825	-> 25
    //   #1826	-> 40
    //   #1827	-> 44
    //   #1828	-> 48
    //   #1829	-> 53
    //   #1830	-> 66
    //   #1831	-> 87
    //   #1832	-> 112
    //   #1834	-> 127
    //   #1835	-> 140
    //   #1836	-> 161
    //   #1837	-> 186
    //   #1840	-> 201
    //   #1841	-> 205
    //   #1844	-> 225
    //   #1845	-> 234
    //   #1846	-> 262
    //   #1847	-> 267
    //   #1848	-> 284
    //   #1851	-> 294
    //   #1853	-> 296
    //   #1855	-> 307
    //   #1856	-> 318
    //   #1857	-> 340
    //   #1858	-> 354
    //   #1859	-> 365
    //   #1861	-> 372
    //   #1863	-> 396
    //   #1866	-> 403
    //   #1867	-> 414
    //   #1868	-> 425
    //   #1869	-> 436
    //   #1870	-> 447
    //   #1871	-> 458
    //   #1872	-> 462
    //   #1873	-> 466
    //   #1874	-> 477
    //   #1876	-> 484
    //   #1879	-> 495
    //   #1880	-> 506
    //   #1881	-> 517
    //   #1882	-> 528
    //   #1883	-> 539
    //   #1884	-> 550
    //   #1885	-> 561
    //   #1886	-> 572
    //   #1888	-> 582
    //   #1889	-> 596
    //   #1890	-> 606
    //   #1891	-> 617
    //   #1892	-> 631
    //   #1893	-> 645
    //   #1894	-> 659
    //   #1895	-> 670
    //   #1896	-> 681
    //   #1897	-> 692
    //   #1899	-> 703
    //   #1901	-> 714
    //   #1902	-> 723
    //   #1905	-> 734
    //   #1907	-> 744
    //   #1908	-> 753
    //   #1909	-> 771
    //   #1910	-> 781
    //   #1911	-> 788
    //   #1912	-> 806
    //   #1913	-> 816
    //   #1914	-> 826
    //   #1915	-> 836
    //   #1916	-> 846
    //   #1980	-> 859
    //   #1981	-> 870
    //   #1982	-> 895
    //   #1984	-> 904
    //   #1985	-> 919
    //   #1990	-> 929
    //   #1992	-> 952
    //   #1993	-> 974
    //   #1994	-> 992
    //   #1998	-> 1009
    //   #2001	-> 1040
    //   #2002	-> 1044
    //   #2003	-> 1048
    //   #2006	-> 1051
    //   #2007	-> 1062
    //   #2008	-> 1088
    //   #2009	-> 1098
    //   #2011	-> 1112
    //   #2013	-> 1116
    //   #2014	-> 1127
    //   #2025	-> 1130
    //   #2026	-> 1134
    //   #2027	-> 1145
    //   #2028	-> 1157
    //   #2029	-> 1168
    //   #2030	-> 1182
    //   #2032	-> 1189
    //   #2034	-> 1193
    //   #2037	-> 1220
    //   #2038	-> 1231
    //   #2040	-> 1246
    //   #2041	-> 1257
    //   #2042	-> 1283
    //   #2043	-> 1291
    //   #2044	-> 1304
    //   #2046	-> 1339
    //   #2047	-> 1347
    //   #2048	-> 1360
    //   #2051	-> 1389
    //   #2053	-> 1392
    //   #2054	-> 1401
    //   #2055	-> 1423
    //   #2056	-> 1434
    //   #2058	-> 1449
    //   #2059	-> 1460
    //   #2061	-> 1475
    //   #2062	-> 1483
    //   #2063	-> 1495
    //   #2062	-> 1509
    //   #2065	-> 1517
    //   #2066	-> 1523
    //   #2068	-> 1531
    //   #2070	-> 1533
    //   #2071	-> 1541
    //   #2072	-> 1543
    //   #2075	-> 1549
    //   #2078	-> 1552
    //   #2079	-> 1563
    //   #2083	-> 1572
    //   #2085	-> 1600
    //   #2087	-> 1611
    //   #2088	-> 1620
    //   #2089	-> 1632
    //   #2090	-> 1644
    //   #2091	-> 1657
    //   #2092	-> 1670
    //   #2093	-> 1683
    //   #2094	-> 1696
    //   #2095	-> 1709
    //   #2096	-> 1722
    //   #2097	-> 1735
    //   #2098	-> 1748
    //   #2099	-> 1761
    //   #2100	-> 1774
    //   #2101	-> 1787
    //   #2102	-> 1800
    //   #2103	-> 1813
    //   #2104	-> 1825
    //   #2106	-> 1845
    //   #2107	-> 1865
    //   #2108	-> 1885
    //   #2109	-> 1898
    //   #2111	-> 1911
    //   #2113	-> 1920
    //   #2114	-> 1948
    //   #2117	-> 1959
    //   #2120	-> 1972
    //   #2122	-> 1983
    //   #2123	-> 2002
    //   #2125	-> 2006
    //   #2126	-> 2010
    //   #2127	-> 2027
    //   #2130	-> 2044
    //   #2133	-> 2072
    //   #2134	-> 2083
    //   #2135	-> 2098
    //   #2136	-> 2110
    //   #2137	-> 2131
    //   #2138	-> 2152
    //   #2136	-> 2178
    //   #2140	-> 2183
    //   #2141	-> 2211
    //   #2141	-> 2218
    //   #2142	-> 2243
    //   #2143	-> 2273
    //   #2144	-> 2309
    //   #2141	-> 2348
    //   #2145	-> 2350
    //   #2146	-> 2367
    //   #2147	-> 2375
    //   #2148	-> 2376
    //   #2150	-> 2384
    //   #2153	-> 2397
    //   #2154	-> 2406
    //   #2156	-> 2416
    //   #2157	-> 2433
    //   #2158	-> 2450
    //   #2159	-> 2462
    //   #2161	-> 2488
    //   #2164	-> 2511
    //   #2165	-> 2515
    //   #2166	-> 2521
    //   #2165	-> 2552
    //   #2168	-> 2563
    //   #2169	-> 2574
    //   #2171	-> 2589
    //   #2172	-> 2603
    //   #2173	-> 2614
    //   #2175	-> 2629
    //   #2176	-> 2636
    //   #2177	-> 2649
    //   #2178	-> 2662
    //   #2179	-> 2675
    //   #2181	-> 2694
    //   #2182	-> 2711
    //   #2183	-> 2728
    //   #2185	-> 2754
    //   #2186	-> 2768
    //   #2187	-> 2781
    //   #2188	-> 2794
    //   #2189	-> 2807
    //   #2191	-> 2823
    //   #2193	-> 2830
    //   #2195	-> 2859
    //   #2196	-> 2868
    //   #2197	-> 2885
    //   #2198	-> 2902
    //   #2200	-> 2934
    //   #2201	-> 2948
    //   #2202	-> 2961
    //   #2203	-> 2974
    //   #2204	-> 2987
    //   #2205	-> 3003
    //   #2209	-> 3010
    //   #2210	-> 3039
    //   #2211	-> 3049
    //   #2212	-> 3082
    //   #2210	-> 3088
    //   #2213	-> 3093
    //   #2215	-> 3107
    //   #2216	-> 3127
    //   #2217	-> 3155
    //   #2218	-> 3185
    //   #2216	-> 3191
    //   #2218	-> 3194
    //   #2219	-> 3212
    //   #2216	-> 3241
    //   #2220	-> 3247
    //   #2221	-> 3249
    //   #2232	-> 3257
    //   #2236	-> 3267
    //   #2237	-> 3296
    //   #2240	-> 3314
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	3315	0	this	Lcom/js/oa/weixin/workflow/WorkflowButtonForWeiXinAction;
    //   0	3315	1	httpServletRequest	Ljavax/servlet/http/HttpServletRequest;
    //   8	3307	2	httpSession	Ljavax/servlet/http/HttpSession;
    //   16	3299	3	workFlowCommonBD	Lcom/js/oa/jsflow/service/WorkFlowCommonBD;
    //   25	3290	4	workFlowButtonBD	Lcom/js/oa/jsflow/service/WorkFlowButtonBD;
    //   40	3275	5	formClassNameMethod	Ljava/util/Map;
    //   44	3271	6	formClassName	Ljava/lang/String;
    //   48	3267	7	formClassMethod	Ljava/lang/String;
    //   205	3110	8	remindFieldValue	Ljava/lang/String;
    //   234	60	9	formReflection	Lcom/js/oa/jsflow/util/FormReflection;
    //   262	32	10	obj	Ljava/lang/Object;
    //   318	2996	9	remindField	Ljava/lang/String;
    //   414	2900	10	isStandForWork	Ljava/lang/String;
    //   425	2889	11	standForUserId	Ljava/lang/String;
    //   436	2878	12	standForUserName	Ljava/lang/String;
    //   447	2867	13	submitPersonId	Ljava/lang/String;
    //   458	2856	14	mainPressType	Ljava/lang/String;
    //   462	2852	15	mainDeadLineTime	Ljava/lang/String;
    //   466	2848	16	mainPressMotionTime	Ljava/lang/String;
    //   477	2837	17	comment	Ljava/lang/String;
    //   495	2819	18	signComment	Ljava/lang/String;
    //   506	2808	19	mainAllowStandFor	Ljava/lang/String;
    //   517	2797	20	mainAllowCancel	Ljava/lang/String;
    //   528	2786	21	curActivityId	Ljava/lang/String;
    //   539	2775	22	curActivityName	Ljava/lang/String;
    //   550	2764	23	mainNextActivityId	Ljava/lang/String;
    //   561	2753	24	mainNextActivityName	Ljava/lang/String;
    //   572	2742	25	processName	Ljava/lang/String;
    //   582	2732	26	tableId	Ljava/lang/String;
    //   596	2718	27	tableName	Ljava/lang/String;
    //   606	2708	28	recordId	Ljava/lang/String;
    //   617	2697	29	workId	Ljava/lang/String;
    //   631	2683	30	userId	Ljava/lang/String;
    //   645	2669	31	orgId	Ljava/lang/String;
    //   659	2655	32	orgIdString	Ljava/lang/String;
    //   670	2644	33	stepCount	Ljava/lang/String;
    //   681	2633	34	activityClass	Ljava/lang/String;
    //   714	2600	35	relprojectId	Ljava/lang/String;
    //   723	2591	36	workFlowBD	Lcom/js/oa/jsflow/service/WorkFlowBD;
    //   734	2580	37	mainTransactUser	[Ljava/lang/String;
    //   753	106	38	newUtil	Lcom/js/oa/jsflow/util/NewWorkflowUtil;
    //   771	88	39	activityVO	Lcom/js/oa/jsflow/vo/ActivityVO;
    //   870	59	38	operId	Ljava/lang/String;
    //   1062	68	38	changeDeadLineTime	Ljava/lang/String;
    //   1134	2123	38	mainNeedPassRound	Ljava/lang/String;
    //   1145	2112	39	mainPassRoundUser	[Ljava/lang/String;
    //   1182	390	40	mainPassRoundUserType	I
    //   1231	321	41	operId	Ljava/lang/String;
    //   1257	292	42	con	Lcom/js/util/util/ConversionString;
    //   1283	266	43	userIdStr	Ljava/lang/String;
    //   1392	157	44	dbopt	Lcom/js/oa/userdb/util/DbOpt;
    //   1533	16	45	e	Ljava/lang/Exception;
    //   1563	9	42	operProcOrg	Ljava/lang/String;
    //   1600	1657	40	subProcWorkId	Ljava/lang/String;
    //   1611	1646	41	modiCommentId	Ljava/lang/String;
    //   1620	1637	42	dealwithMap	Ljava/util/Map;
    //   1920	1337	43	wfcBD	Lcom/js/oa/jsflow/service/WorkFlowCommonBD;
    //   1948	1309	44	subProcType	Ljava/lang/String;
    //   1959	1298	45	toMainFile	Ljava/lang/String;
    //   1972	1285	46	curTransactType	Ljava/lang/String;
    //   1983	1274	47	nextTransactType	Ljava/lang/String;
    //   2010	1247	48	docTitle	Ljava/lang/String;
    //   2072	1185	49	emergence	Ljava/lang/String;
    //   2083	1174	50	dealTips	Ljava/lang/String;
    //   2110	73	51	df	Ljava/text/SimpleDateFormat;
    //   2211	1046	51	sendSMS	Ljava/lang/String;
    //   2350	907	52	para	[Ljava/lang/String;
    //   2367	890	53	result	Ljava/lang/Integer;
    //   2406	848	54	workLogVO	Lcom/js/oa/jsflow/vo/WorkLogVO;
    //   2515	179	55	tmpUser	Ljava/lang/String;
    //   2518	45	56	i	I
    //   3093	161	55	sql	Ljava/lang/String;
    //   3107	147	56	initWorkId	[[Ljava/lang/String;
    //   3127	120	57	url	Ljava/lang/String;
    //   3249	5	57	e	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   225	291	294	java/lang/Exception
    //   1392	1528	1531	java/lang/Exception
    //   1533	1538	1541	java/sql/SQLException
    //   3107	3244	3247	java/lang/Exception
  }
  
  private int completeSaveData(HttpServletRequest httpServletRequest) {
    int result = 0;
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = workFlowCommonBD.getActivityClassMethod(httpServletRequest.getParameter("curActivityId"));
    String formClassName = "";
    String formClassMethod = "";
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("formClassMethod") != null && 
        !formClassNameMethod.get("formClassMethod").toString().equals("") && 
        !formClassNameMethod.get("formClassMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("formClassMethod").toString(); 
    } 
    if (!"".equals(formClassName) && !"".equals(formClassMethod)) {
      FormReflection formReflection = new FormReflection();
      Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, httpServletRequest);
      if (obj != null) {
        if (obj.getClass().toString().equals("class java.lang.String")) {
          if ("error".equals(obj.toString())) {
            result = 0;
          } else {
            result = 1;
          } 
        } else {
          result = 1;
        } 
      } else {
        result = 1;
      } 
    } 
    return result;
  }
  
  private String complete(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String remindFieldValue = "";
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.startsWith("$") && remindField.endsWith("$")) {
      remindFieldValue = httpServletRequest.getParameter("motif");
    } else if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    if (remindFieldValue == null || remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String comment = CharacterTool.escapeHTMLTags2(httpServletRequest.getParameter("include_comment"));
    String signComment = httpServletRequest.getParameter("include_signcomment");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String userId = httpSession.getAttribute("userId").toString();
    String stepCount = httpServletRequest.getParameter("stepCount");
    String activityClass = httpServletRequest.getParameter("activityClass");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String curTransactType = httpServletRequest.getParameter("curTransactType");
    String moduleId = workFlowCommonBD.getModuleId(tableId);
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("signcomment", signComment);
    dealwithMap.put("nextActivityName", "");
    dealwithMap.put("nextActivityId", "0");
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    dealwithMap.put("activityClass", activityClass);
    dealwithMap.put("subProcWorkId", (httpServletRequest.getParameter("subProcWorkId") == null) ? "0" : httpServletRequest.getParameter("subProcWorkId"));
    if (httpServletRequest.getParameter("include_commField") != null)
      dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
    dealwithMap.put("userScope", httpServletRequest.getParameter("userScope"));
    dealwithMap.put("scopeId", httpServletRequest.getParameter("scopeId"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    wfcBD.insertDealWith(dealwithMap);
    String docTitle = "";
    if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
      docTitle = httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")); 
    String[] para = { 
        tableId, recordId, curActivityId, processName, submitPerson, 
        remindFieldValue, curTransactType, stepCount, 
        isStandForWork, 
        userId, 
        standForUserId, 
        standForUserName, docTitle };
    Integer result = workFlowCommonBD.completeWork(para, workId);
    String href = "";
    if (result.intValue() == 1) {
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
      workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
      workLogVO.setSendAction("流程办理完毕");
      workLogVO.setReceiveUserName(" ");
      workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
      workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
      workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
      workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
      workFlowButtonBD.setDealWithLog(workLogVO);
      Map formClassNameMethod2 = workFlowCommonBD.getProcessClassMethod(httpServletRequest.getParameter("processId"));
      String formClassName2 = "";
      String formClassMethod2 = "";
      if (formClassNameMethod2 != null && formClassNameMethod2.get("formClassName") != null && formClassNameMethod2.get("formClassCompMethod") != null) {
        formClassName2 = formClassNameMethod2.get("formClassName").toString();
        formClassMethod2 = formClassNameMethod2.get("formClassCompMethod").toString();
        if (!formClassName2.equals("") && !formClassName2.toUpperCase().equals("") && 
          !formClassMethod2.equals("") && !formClassMethod2.toUpperCase().equals("")) {
          FormReflection formReflection2 = new FormReflection();
          formReflection2.execute("com.js.oa.form." + formClassName2, formClassMethod2, httpServletRequest);
        } 
      } 
      WorkFlowBD newWorkFlowBD = new WorkFlowBD();
      String[] work = newWorkFlowBD.getCurCompleteWork(tableId, recordId);
      String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GZLC", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
      if ("2".equals(moduleId) || "3".equals(moduleId) || "34".equals(moduleId))
        returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString()); 
      if (!"".equals(returnValue) && !"-1".equals(returnValue))
        if (("2".equals(moduleId) || "3".equals(moduleId) || "34".equals(moduleId)) && (new ProcessBD()).getIsDossier(work[3]).intValue() == 1) {
          href = String.valueOf(work[4]) + "&activityName=" + curActivityName + 
            "&submitPersonId=" + 
            httpServletRequest.getParameter("submitPersonId") + 
            "&submitPerson=" + submitPerson + "&work=" + work[0] + 
            "&workType=" + 
            work[1] + "&activity=" + curActivityId + "&table=" + 
            tableId + "&record=" + 
            recordId + "&processName=" + processName + 
            "&processId=" + work[3] + 
            "&workStatus=100&submitTime=" + work[2];
        } else if ((new ProcessBD()).getIsDossier(work[3]).intValue() == 1) {
          href = String.valueOf(work[4]) + "&activityName=" + curActivityName + 
            "&submitPersonId=" + 
            httpServletRequest.getParameter("submitPersonId") + 
            "&submitPerson=" + submitPerson + "&work=" + work[0] + 
            "&workType=" + 
            work[1] + "&activity=" + curActivityId + "&table=" + 
            tableId + "&record=" + 
            recordId + "&processName=" + processName + 
            "&processId=" + work[3] + 
            "&workStatus=100&submitTime=" + work[2];
        }  
      if (!"1".equals(SystemCommon.getFlowCommentRange())) {
        if ("2".equals(moduleId) || "3".equals(moduleId)) {
          workFlowButtonBD.deleteFlowTempData(httpServletRequest.getParameter("processId"), tableId, recordId, "1");
        } else {
          workFlowButtonBD.deleteFlowTempData(httpServletRequest.getParameter("processId"), tableId, recordId, "2");
        } 
      } else {
        workFlowButtonBD.deleteFlowTempData(httpServletRequest.getParameter("processId"), tableId, recordId, "1");
      } 
    } 
    return href;
  }
  
  private String[] saveToDraft(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = "-100";
    String preparetype = "saveToDraft";
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String formClassName = "Workflow";
    String formClassMethod = "save";
    Map formClassNameMethod = workFlowCommonBD.getProcessClassMethod(processId);
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("formClassMethod") != null && 
        !formClassNameMethod.get("formClassMethod").toString().equals("") && 
        !formClassNameMethod.get("formClassMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("formClassMethod").toString(); 
    } 
    FormReflection formReflection = new FormReflection();
    request.setAttribute("preparetype", preparetype);
    Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, request);
    String remindFieldValue = "";
    String recordIdStr = "";
    if (obj != null)
      if (obj.getClass().toString().equals("class java.lang.Long")) {
        recordId = obj.toString();
      } else if (obj.getClass().toString().equals("class java.util.HashMap")) {
        Map tmpMap = (Map)obj;
        if (tmpMap.get("id") != null)
          recordId = tmpMap.get("id").toString(); 
        if (tmpMap.get("idStr") != null) {
          recordIdStr = tmpMap.get("idStr").toString();
          recordId = recordIdStr;
        } 
        if (tmpMap.get("remindFieldValue") != null)
          remindFieldValue = tmpMap.get("remindFieldValue").toString(); 
      }  
    String resubmit = "0";
    if (request.getParameter("resubmitWorkId") != null && request.getParameter("resubmitWorkId").trim().length() > 0 && !request.getParameter("resubmitWorkId").toUpperCase().equals("NULL")) {
      List<String> sqlList = new ArrayList();
      sqlList.add("update JSF_WORK set workDelete = 1 where wf_work_id = " + request.getParameter("resubmitWorkId"));
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.updateTable(sqlList);
      resubmit = "1";
    } 
    String addFlag = request.getParameter("addFlag");
    if (addFlag != null && "batchAdd".equals(addFlag)) {
      request.setAttribute("addFlag", addFlag);
      if (recordIdStr != null && !"".equals(recordIdStr)) {
        String[] recordIdStrs = recordIdStr.split(",");
        for (int i = 0; i < recordIdStrs.length; i++)
          doRemindToDraft(request, recordIdStrs[i], remindFieldValue); 
      } 
    } else {
      doRemindToDraft(request, recordId, remindFieldValue);
    } 
    return new String[] { processId, tableId, recordId, resubmit };
  }
  
  public void doRemindToDraft(HttpServletRequest request, String recordId, String remindFieldValue) {
    HttpSession session = request.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String remindField = request.getParameter("remindField");
    if (remindField.startsWith("$") && remindField.endsWith("$")) {
      remindFieldValue = request.getParameter("motif");
    } else if (remindField.indexOf("S") < 0 || recordId == null || recordId.trim().length() < 1) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, recordId, request.getParameter("tableId"));
    } 
    String resubmit = "0";
    if (recordId != null && Long.parseLong(recordId) > 0L) {
      String processType = (request.getParameter("processType") == null || "".equals(request.getParameter("processType"))) ? "1" : request.getParameter("processType");
      BASE64Decoder base64 = new BASE64Decoder();
      String processName = request.getParameter("processName");
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String orgName = session.getAttribute("orgName").toString();
      workFlowButtonBD.saveToDraft(processId, tableId, recordId, userId, processName, processName);
      String fromFlagFlow = request.getParameter("fromFlagFlow");
      if ("fromDaiBan".equals(fromFlagFlow) || "fromDuBan".equals(fromFlagFlow)) {
        String collectId = request.getParameter("collectId");
        String wfWorkId = request.getParameter("wfWorkId");
        OACollectEJBBean collectBean = new OACollectEJBBean();
        String url = collectBean.getUrlForZaiBan(collectId, recordId);
        String dateString = "now()";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0)
          dateString = "sysdate"; 
        String sql = "update JSF_WORK set WORKSTATUS=101,DEALWITHTIME=" + dateString + ",WORKMAINLINKFILE='" + url + "' where WF_WORK_ID=" + wfWorkId;
        try {
          collectBean.updateByYourYuanShengSql(sql);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private int saveFirstComp(HttpServletRequest request) {
    int result = -1;
    HttpSession session = request.getSession(true);
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = "-100";
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String formClassName = "Workflow";
    String formClassMethod = "save";
    Map formClassNameMethod = workFlowCommonBD.getProcessClassMethod(processId);
    if (formClassNameMethod != null) {
      if (formClassNameMethod.get("formClassName") != null && 
        !formClassNameMethod.get("formClassName").toString().equals("") && 
        !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
        formClassName = formClassNameMethod.get("formClassName").toString(); 
      if (formClassNameMethod.get("formClassMethod") != null && 
        !formClassNameMethod.get("formClassMethod").toString().equals("") && 
        !formClassNameMethod.get("formClassMethod").toString().toUpperCase().equals("NULL"))
        formClassMethod = formClassNameMethod.get("formClassMethod").toString(); 
    } 
    FormReflection formReflection = new FormReflection();
    Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, request);
    String remindFieldValue = "";
    if (obj != null)
      if (obj.getClass().toString().equals("class java.lang.Long")) {
        recordId = obj.toString();
      } else if (obj.getClass().toString().equals("class java.util.HashMap")) {
        Map tmpMap = (Map)obj;
        if (tmpMap.get("id") != null)
          recordId = tmpMap.get("id").toString(); 
        if (tmpMap.get("remindFieldValue") != null)
          remindFieldValue = tmpMap.get("remindFieldValue").toString(); 
      }  
    if (recordId != null && Long.parseLong(recordId) > 0L) {
      String processType = (request.getParameter("processType") == null || "".equals(request.getParameter("processType"))) ? "1" : request.getParameter("processType");
      BASE64Decoder base64 = new BASE64Decoder();
      String processName = request.getParameter("processName");
      String moduleId = request.getParameter("moduleId");
      moduleId = workFlowCommonBD.getModuleId(tableId);
      String userName = session.getAttribute("userName").toString();
      String userId = session.getAttribute("userId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String orgName = session.getAttribute("orgName").toString();
      if ("3".equals(moduleId))
        orgName = request.getParameter("receiveFileSendFileUnit"); 
      WorkVO workVO = new WorkVO();
      workVO.setWorkType(Integer.parseInt(processType));
      workVO.setFileType(processName);
      workVO.setRemindValue(remindFieldValue.toString());
      workVO.setSelfMainLinkFile(request.getParameter("mainLinkFile"));
      workVO.setToMainLinkFile(request.getParameter("mainLinkFile"));
      workVO.setSubmitPerson(userName);
      workVO.setSubmitEmployeeId(new Long(userId));
      workVO.setSubmitOrg(orgName);
      workVO.setProcessId(new Long(processId));
      workVO.setTableId(new Long(tableId));
      workVO.setRecordId(new Long(recordId));
      workVO.setCreatorCancelLink("");
      String isSubProcWork = (request.getParameter("isSubProcWork") == null) ? "0" : request.getParameter("isSubProcWork");
      workVO.setIsSubProcWork(isSubProcWork);
      String pareProcActiId = (request.getParameter("pareProcActiId") == null) ? "0" : request.getParameter("pareProcActiId");
      workVO.setPareProcActiId(pareProcActiId);
      String pareStepCount = (request.getParameter("pareStepCount") == null) ? "0" : request.getParameter("pareStepCount");
      workVO.setPareStepCount(pareStepCount);
      String pareTableId = (request.getParameter("pareTableId") == null) ? "0" : request.getParameter("pareTableId");
      workVO.setPareTableId(pareTableId);
      String pareRecordId = (request.getParameter("pareRecordId") == null) ? "0" : request.getParameter("pareRecordId");
      workVO.setPareRecordId(pareRecordId);
      workVO.setPareProcNextActiId("0");
      workVO.setEmergence((request.getParameter("emergence") == null) ? "0" : request.getParameter("emergence"));
      String cancelHref = request.getParameter("cancelHref");
      cancelHref = cancelHref.replaceAll("tableIdValue", tableId);
      cancelHref = cancelHref.replaceAll("processNameValue", processName);
      cancelHref = cancelHref.replaceAll("recordIdValue", recordId);
      cancelHref = cancelHref.replaceAll("processIdValue", processId);
      cancelHref = cancelHref.replaceAll("remindValueValue", remindFieldValue.toString());
      cancelHref = cancelHref.replaceAll("fileTitleValue", request.getParameter(request.getParameter("titleFieldName")));
      workVO.setCreatorCancelLink(cancelHref);
      String relProjectId = request.getParameter("relproject");
      if (relProjectId != null && !"null".equals(relProjectId) && !"".equals(relProjectId)) {
        workVO.setRelProjectId(Long.valueOf(relProjectId));
      } else {
        workVO.setRelProjectId(Long.valueOf(-1L));
      } 
      String[] toUser = { "" };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      if (processType.equals("0")) {
        workVO.setDeadLine("-1");
        workVO.setPressTime("-1");
        workVO.setCurStep("");
        String randomProcUser = request.getParameter("randomProcUser");
        if (randomProcUser.startsWith("$"))
          randomProcUser = randomProcUser.substring(1, randomProcUser.length()); 
        if (randomProcUser.endsWith("$"))
          randomProcUser = randomProcUser.substring(0, randomProcUser.length() - 1); 
        toUser[0] = randomProcUser;
      } else {
        String operId, operProcOrg;
        workVO.setActivity(new Long(request.getParameter("activityId")));
        workVO.setCurStep(request.getParameter("activityName"));
        workVO.setAllowStandFor(Integer.parseInt(request.getParameter("allowStandFor")));
        workVO.setPressType(Integer.parseInt(request.getParameter("pressType")));
        workVO.setDeadLine(request.getParameter("deadLineTime"));
        workVO.setPressTime(request.getParameter("pressTime"));
        workVO.setUserType(Integer.parseInt(request.getParameter("userType")));
        int userType = Integer.parseInt(request.getParameter("userType"));
        if (userType == 10)
          userType = 100; 
        switch (userType) {
          case 100:
            operId = request.getParameter("operId");
            if (operId != null && !operId.equals("")) {
              ConversionString con = new ConversionString(operId);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              if (con.getGroupIdString() != null && !"".equals(con.getGroupIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ","; 
              if (con.getOrgIdString() != null && !"".equals(con.getOrgIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString()); 
              DbOpt dbopt = null;
              try {
                dbopt = new DbOpt();
                userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
                if (userIdStr.startsWith(","))
                  userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
                if (userIdStr.endsWith(","))
                  userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
                if (userIdStr.length() > 0) {
                  toUser = dbopt.executeQueryToStrArr1(
                      "select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + 
                      userIdStr + ")");
                } else {
                  toUser = new String[0];
                } 
                dbopt.close();
              } catch (Exception e) {
                try {
                  dbopt.close();
                } catch (SQLException sQLException) {}
                toUser = new String[0];
              } 
            } 
            break;
          case 10:
            operProcOrg = request.getParameter("operId");
            toUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
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
            workVO.setDeadLine(request.getParameter("deadLineTime"));
            workVO.setPressTime(request.getParameter("pressMotionTime"));
            break;
        } 
      } 
      String[] passUser = { "" };
      if (request.getParameter("mainNeedPassRound") != null) {
        String operId, operProcOrg;
        workVO.setNeedPassRound(true);
        int passRoundUserType = Integer.parseInt(request.getParameter("mainPassRoundUserType"));
        workVO.setPassRoundUserType(passRoundUserType);
        switch (passRoundUserType) {
          case 100:
            operId = request.getParameter("passRoundId");
            if (operId != null && !operId.equals("")) {
              ConversionString con = new ConversionString(operId);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              if (con.getGroupIdString() != null && 
                !"".equals(con.getGroupIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ","; 
              if (con.getOrgIdString() != null && 
                !"".equals(con.getOrgIdString()))
                userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString()); 
              DbOpt dbopt = null;
              try {
                dbopt = new DbOpt();
                userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
                if (userIdStr.startsWith(","))
                  userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
                if (userIdStr.endsWith(","))
                  userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
                if (userIdStr.length() > 0) {
                  passUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + 
                      userIdStr + ")");
                } else {
                  passUser = new String[0];
                } 
                dbopt.close();
              } catch (Exception e) {
                try {
                  dbopt.close();
                } catch (SQLException sQLException) {}
                passUser = new String[0];
              } 
            } 
            break;
          case 10:
            operProcOrg = request.getParameter("passRoundId");
            passUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
            break;
        } 
        workVO.setPassRoundUser(passUser);
      } else {
        workVO.setNeedPassRound(false);
      } 
      if (!"".equals(request.getParameter("titleFieldName")))
        workVO.setDocTitle(request.getParameter(request.getParameter("titleFieldName"))); 
      workVO.setDomainId(session.getAttribute("domainId").toString());
      String nextTransactType = request.getParameter("approveMode");
      workVO.setTransactType(nextTransactType);
      String insertResult = workFlowBD.insertCurFieldStr(processId, tableId, (new StringBuilder(String.valueOf(recordId))).toString(), request.getParameter("curFieldStr"));
      if (!"0".equals(insertResult))
        return -1; 
      String dealTips = request.getParameter("dealTips");
      if (dealTips != null && !dealTips.equals("")) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        dealTips = "来自上一环节：" + ((request.getParameter("activityName") == null || "".equals(request.getParameter("activityName")) || "null".equals(request.getParameter("activityName"))) ? "" : request.getParameter("activityName")) + ";  办理人：" + 
          session.getAttribute("userName").toString() + 
          "; " + 
          df.format(new Date()) + " " + dealTips;
      } 
      String processDeadlineDate = request.getParameter("processDeadlineDate");
      String[] para = { orgIdString, dealTips, processDeadlineDate };
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == 0L)
        return -1; 
      if (submitSuccess != 0L && request.getParameter("resubmitWorkId") != null && request.getParameter("resubmitWorkId").trim().length() > 0 && !request.getParameter("resubmitWorkId").toUpperCase().equals("NULL")) {
        List<String> sqlList = new ArrayList();
        String updateSql = "update JSF_WORK set workDelete = 1 where wf_work_id = " + request.getParameter("resubmitWorkId");
        if ("1".equals(SystemCommon.getReSubmitDel()))
          updateSql = String.valueOf(updateSql) + " AND NOT EXISTS (SELECT 1 FROM jsf_work WHERE WORKRECORD_ID=" + request.getParameter("resubmitWorkId") + ")"; 
        sqlList.add(updateSql);
        workFlowBD.updateTable(sqlList);
      } 
      if (submitSuccess != 0L) {
        WorkLogVO workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(session.getAttribute("userId").toString());
        workLogVO.setSendUserName(session.getAttribute("userName").toString());
        workLogVO.setSendAction("送" + ((request.getParameter("activityName") == null || "".equals(request.getParameter("activityName")) || "null".equals(request.getParameter("activityName"))) ? "" : WorkflowForWeiXinUtil.decordStr(request.getParameter("activityName"))));
        if (processType.equals("0")) {
          workLogVO.setReceiveUserName(request.getParameter("randomProcUserName"));
        } else {
          workLogVO.setReceiveUserName(request.getParameter("operName"));
        } 
        workLogVO.setProcessId(request.getParameter("processId"));
        workLogVO.setTableId(request.getParameter("tableId"));
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId(session.getAttribute("domainId").toString());
        workFlowButtonBD.setDealWithLog(workLogVO);
        if (request.getParameter("mainNeedPassRound") != null && !"".equals(request.getParameter("passRoundName"))) {
          workLogVO = new WorkLogVO();
          workLogVO.setSendUserId(session.getAttribute("userId").toString());
          workLogVO.setSendUserName(session.getAttribute("userName").toString());
          workLogVO.setSendAction("送" + ((request.getParameter("activityName") == null || "".equals(request.getParameter("activityName")) || "null".equals(request.getParameter("activityName"))) ? "" : WorkflowForWeiXinUtil.decordStr(request.getParameter("activityName"))) + "阅件");
          String passRoundUser = request.getParameter("passRoundName");
          if (passRoundUser.endsWith(","))
            passRoundUser = passRoundUser.substring(0, passRoundUser.length() - 1); 
          workLogVO.setReceiveUserName(passRoundUser);
          workLogVO.setProcessId(request.getParameter("processId"));
          workLogVO.setTableId(request.getParameter("tableId"));
          workLogVO.setRecordId(recordId);
          workLogVO.setDomainId(session.getAttribute("domainId").toString());
          workFlowButtonBD.setDealWithLog(workLogVO);
        } 
      } 
      if (request.getParameter("subProc") != null)
        request.setAttribute("subProcWorkId", (new StringBuilder(String.valueOf(submitSuccess))).toString()); 
      if ("1".equals(request.getParameter("savefirst"))) {
        WorkFlowButtonBD bd = new WorkFlowButtonBD();
        String workUrl = bd.getSaveFirstWorkUrl(processId, processName, tableId, recordId);
        request.setAttribute("sendUrl", workUrl);
        request.setAttribute("savefirst", "1");
      } 
    } 
    return result;
  }
  
  public void setCurrentStep(HttpServletRequest request) {
    String processId = request.getParameter("processId");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String currentStep = request.getParameter("currentStep");
    String selUserId = request.getParameter("selUserId");
    String selUserName = request.getParameter("selUserName");
    String transactType = request.getParameter("transactType");
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    bd.setCurrentStep(processId, tableId, recordId, currentStep, selUserId, transactType);
    HttpSession httpSession = request.getSession(true);
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
    workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
    int index = currentStep.indexOf(",");
    currentStep = currentStep.substring(index + 1);
    workLogVO.setSendAction(String.valueOf(WorkflowForWeiXinUtil.decordStr(currentStep)) + "审批");
    if (selUserName.endsWith(","))
      selUserName = selUserName.substring(0, selUserName.length() - 1); 
    workLogVO.setReceiveUserName(selUserName);
    workLogVO.setProcessId(request.getParameter("processId"));
    workLogVO.setTableId(request.getParameter("tableId"));
    workLogVO.setRecordId(request.getParameter("recordId"));
    workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
  }
  
  public String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    String[] orgIdArr = orgIdStr.split(",");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr(
            "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    return orgIds;
  }
  
  public String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || "".equals(groupIdStr.trim()))
      return ""; 
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT DISTINCT EMP_ID FROM ORG_USER_GROUP WHERE GROUP_ID IN (" + 
          groupIdStr + ")");
      while (rs.next())
        userStr = String.valueOf(userStr) + rs.getString(1) + ","; 
      if (userStr.endsWith(","))
        userStr = userStr.substring(0, userStr.length() - 1); 
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return userStr;
  }
  
  private String getRemindValue(String remindField, String recordId, String formId) {
    String remindValue = "";
    if (remindField == null || remindField.length() < 1 || 
      remindField.toUpperCase().equals("null") || 
      recordId == null || recordId.length() < 1 || 
      recordId.toUpperCase().equals("null") || 
      formId == null || formId.length() < 1 || 
      formId.toUpperCase().equals("null"))
      return ""; 
    remindField = "S" + remindField + "S";
    String[] remindFieldArr = remindField.split("SS");
    CustomFormBD formBD = new CustomFormBD();
    for (int i = 0; i < remindFieldArr.length; i++) {
      String temp = formBD.getRemindValue(remindFieldArr[i], recordId, formId);
      if (temp == null || temp.length() < 1 || 
        temp.toUpperCase().equals("NULL")) {
        remindValue = (new StringBuilder(String.valueOf(remindValue))).toString();
      } else {
        if (temp.indexOf("`~`~`") >= 0)
          temp = temp.split("`~`~`")[0]; 
        remindValue = String.valueOf(remindValue) + temp;
      } 
    } 
    return remindValue;
  }
  
  public void sendRTXNotify(String account, String content) {}
  
  private void sendMsg(String[] userId, HttpServletRequest httpServletRequest) {}
  
  private String userIdStringToArray(String userId) {
    userId = userId.substring(1, userId.length() - 1);
    userId = userId.replaceAll("\\$\\$", ",");
    return userId;
  }
  
  private int getcount(String record) {
    int count = 0;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "SELECT count(*) FROM jsf_work WHERE WORKRECORD_ID=" + record;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        count = rs.getInt(1); 
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
    return count;
  }
}
