package com.js.oa.jsflow.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.form.WorkFlowArchives;
import com.js.oa.form.Workflow;
import com.js.oa.form.pengchi.CreateProcessForGdzc;
import com.js.oa.form.pengchi.CreateProcessForZfd;
import com.js.oa.form.pengchi.WorkflowForZfd;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.DocToArchive;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.NewWorkflowUtil;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowToArchive;
import com.js.oa.jsflow.util.YouNengCRMDealWith;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.pressdeal.service.PersonalOAPressManageBD;
import com.js.oa.tjgzw.bean.TjgzwBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.ExcelOperate;
import java.io.File;
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

public class WorkflowButtonAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    httpServletResponse.setHeader("Cache-Control", "no-store");
    httpServletResponse.setHeader("Pragma", "no-cache");
    httpServletResponse.setDateHeader("Expires", 0L);
    String flag = httpServletRequest.getParameter("flag");
    if ("showsend".equals(flag)) {
      setMessageContent(httpServletRequest);
      return actionMapping.findForward("showsend");
    } 
    if ("save".equals(flag)) {
      if (!"0".equals(save(httpServletRequest)))
        httpServletRequest.setAttribute("flowfaild", "1"); 
    } else {
      if ("saveclose".equals(flag)) {
        Workflow wf = new Workflow();
        updateToDraft(httpServletRequest);
        httpServletRequest.setAttribute("noCloseWindow", "1");
        return actionMapping.findForward("saveonly");
      } 
      if ("update".equals(flag)) {
        update(httpServletRequest);
      } else if ("complete".equals(flag)) {
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
      } else {
        if ("saveToDraft".equals(flag)) {
          String[] para = saveToDraft(httpServletRequest);
          httpServletRequest.setAttribute("processPara", para);
          return actionMapping.findForward("saveonly");
        } 
        if ("saveFirstComp".equals(flag)) {
          if (!"0".equals(Integer.valueOf(saveFirstComp(httpServletRequest))))
            httpServletRequest.setAttribute("flowfaild", "1"); 
        } else {
          if ("showback".equals(flag))
            return actionMapping.findForward("showback"); 
          if ("back".equals(flag)) {
            back(httpServletRequest);
            httpServletRequest.setAttribute("resubmit", "1");
          } else if ("undo".equals(flag)) {
            undo(httpServletRequest);
          } else {
            if ("showpress".equals(flag)) {
              if (httpServletRequest.getParameter("press") == null)
                return actionMapping.findForward("showpress"); 
              return actionMapping.findForward("press");
            } 
            if ("press".equals(flag)) {
              press(httpServletRequest);
            } else {
              if ("showfeedback".equals(flag))
                return actionMapping.findForward("showfeedback"); 
              if ("feedback".equals(flag)) {
                feedback(httpServletRequest);
              } else {
                if ("showreturn".equals(flag))
                  return actionMapping.findForward("showreturn"); 
                if ("return".equals(flag)) {
                  receive(httpServletRequest);
                } else {
                  if ("showtran".equals(flag)) {
                    setMessageContent(httpServletRequest);
                    return actionMapping.findForward("showtran");
                  } 
                  if ("delete".equals(flag)) {
                    delete(httpServletRequest);
                  } else if ("end".equals(flag)) {
                    end(httpServletRequest);
                  } else if ("passround".equals(flag)) {
                    passround(httpServletRequest);
                  } else if ("dealpassround".equals(flag)) {
                    dealpassround(httpServletRequest);
                  } else {
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
                    if ("branchsend".equals(flag))
                      branchSend(httpServletRequest); 
                  } 
                } 
              } 
            } 
          } 
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
        if (operProcOrg != null && !"".equals(operProcOrg)) {
          operProcOrg = operProcOrg.substring(1, operProcOrg.length() - 1).replace("$$", ",");
          mainPassRoundUser = operProcOrg.split(",");
        } 
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
    workLogVO.setSendAction("收回到" + request.getParameter("receiveActivityName"));
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
    workLogVO.setSendAction("撤办到" + undoActivity.split(",")[1]);
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
    String workFlowParm = "";
    if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
      String table = request.getParameter("tableId");
      String record = request.getParameter("recordId");
      String processId = request.getParameter("processId");
      workFlowParm = String.valueOf(table) + "$$" + record + "$$" + processId;
    } 
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
          chatUserPO.setIsRead("1");
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
          if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
            messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + chatid + "&isRead=0&workFlowParm=" + workFlowParm);
          } else {
            messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + chatid + "&isRead=0");
          } 
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
    String sendSMS = (request.getParameter("sendSMS") == null) ? "1" : request.getParameter("sendSMS");
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
      Object object = formReflection.execute("com.js.oa.form." + formClassName, "back", request);
    } 
    HttpSession session = request.getSession(true);
    String comment = (request.getParameter("include_comment") == null) ? "" : request.getParameter("include_comment");
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
    String dealTips = request.getParameter("dealTips");
    if (dealTips == null || "null".equals(dealTips))
      dealTips = ""; 
    dealwithMap.put("dealTips", dealTips);
    wfcBD.insertDealWith(dealwithMap);
    String initStepCount = (request.getParameter("backToStep") == null) ? "0" : request.getParameter("backToStep");
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
      para.put("dealTips", dealTips);
      para.put("sendSMS", sendSMS);
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
      String backToActivityName = request.getParameter("backToActivityName");
      para.put("backToActivityName", backToActivityName);
      para.put("workMainLinkFile", request.getParameter("mainLinkFile"));
      para.put("isStandForWork", request.getParameter("isStandForWork"));
      para.put("standForUserId", request.getParameter("standForUserId"));
      para.put("backToUserId", request.getParameter("backToUserId"));
      para.put("comment", comment);
      para.put("backInfo", backInfo);
      para.put("sendSMS", sendSMS);
      exeUser = wfbBD.backToActivity(para);
    } 
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(session.getAttribute("userId").toString());
    workLogVO.setSendUserName(session.getAttribute("userName").toString());
    workLogVO.setSendAction("退回" + request.getParameter("backToActivityName"));
    workLogVO.setReceiveUserId(backInfo);
    workLogVO.setReceiveUserName((request.getParameter("backToUserName") == null) ? "" : request.getParameter("backToUserName"));
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
        if ("shandongguotou".equals(SystemCommon.getCustomerName()))
          title = "【退回】" + title; 
        String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&workId=" + empWorkId;
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          RemindUtil.sendMessageToUsers(title, url, to, "jsflow", new Date(), new Date("2050/1/1"), sendSMS);
        } else {
          RemindUtil.sendMessageToUsers(title, url, to, "jsflow", new Date(), new Date("2050/1/1"), "系统提醒", Long.valueOf(empWorkId));
        } 
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
        String titleTemp = String.valueOf(submitPerson) + "发起的" + title + "被" + object2 + "退回到" + request.getParameter("backToActivityName");
        if ("shandongguotou".equals(SystemCommon.getCustomerName()))
          titleTemp = "【退回】" + titleTemp; 
        for (int i = 0; i < toArr.length; i++) {
          if (exeUser.indexOf("," + toArr[i] + ",") < 0)
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              RemindUtil.sendMessageToUsers(titleTemp, String.valueOf(url) + workIdArr[i], toArr[i], "jsflow", new Date(), new Date("2050/1/1"), sendSMS);
            } else {
              RemindUtil.sendMessageToUsers(titleTemp, String.valueOf(url) + workIdArr[i], toArr[i], "jsflow", new Date(), new Date("2050/1/1"), "系统提醒", Long.valueOf(workIdArr[i]));
            }  
        } 
      } 
      title = "您发起的" + title + "被" + object2 + "退回到" + request.getParameter("backToActivityName");
      if ("shandongguotou".equals(SystemCommon.getCustomerName()))
        title = "【退回】" + title; 
      to = backMap.get("submitEmpId").toString();
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        RemindUtil.sendMessageToUsers(title, String.valueOf(url) + backMap.get("submitWorkId").toString(), to, "jsflow", new Date(), new Date("2050/1/1"), sendSMS);
      } else {
        RemindUtil.sendMessageToUsers(title, String.valueOf(url) + backMap.get("submitWorkId").toString(), to, "jsflow", new Date(), new Date("2050/1/1"), "系统提醒", Long.valueOf(backMap.get("submitWorkId").toString()));
      } 
    } 
    if ("0".equals(request.getParameter("type")))
      backProcessRemind(tableId, recordId, workId, (String)object1, (String)object2); 
    if ("yn".equals(SystemCommon.getCustomerName()) && 
      "0".equals(request.getParameter("type")))
      (new YouNengCRMDealWith()).back((String)object2, tableId, recordId, dealTips); 
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
    String userAccount = httpServletRequest.getSession().getAttribute("userAccount").toString();
    if ("pengchi".equals(SystemCommon.getCustomerName()) && !formClassName.equals(""))
      if ("pengchi.WorkflowForGdzc".equals(formClassName)) {
        if (!"0".equals(recordId)) {
          Map<String, String> info = CreateProcessForGdzc.getInfo(recordId, userAccount);
          CreateProcessForGdzc.insertTable(info);
        } 
      } else if ("pengchi.WorkflowForZfd".equals(formClassName) && !"0".equals(recordId)) {
        List<Map<String, String>> zfList = WorkflowForZfd.getKeyInfo(recordId);
        CreateProcessForZfd.updateTable(zfList);
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
    if (httpServletRequest.getParameter("branchActInfo") != null && !"".equals(httpServletRequest.getParameter("branchActInfo")))
      return doBranchRemind(httpServletRequest, recordId, remindFieldValue); 
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
      if (processName == null || "".equals(processName)) {
        TjgzwBean tjgzwBean = new TjgzwBean();
        processName = tjgzwBean.getWorkflowprocessname(processId);
      } 
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
          if (operId.indexOf("#") >= 0) {
            String[] arr = operId.split("#");
            operId = "";
            for (int i = 0; i < arr.length; i++) {
              if (arr[i].indexOf("$") >= 0)
                operId = String.valueOf(operId) + arr[i]; 
            } 
          } 
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
        workLogVO.setSendAction("送" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : httpServletRequest.getParameter("activityName")));
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
          workLogVO.setSendAction("送" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : httpServletRequest.getParameter("activityName")) + "阅件");
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
  
  public String doBranchRemind(HttpServletRequest httpServletRequest, String recordId, String remindFieldValue) {
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
      if (processName == null || "".equals(processName)) {
        TjgzwBean tjgzwBean = new TjgzwBean();
        processName = tjgzwBean.getWorkflowprocessname(processId);
      } 
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
      String branchActInfo = httpServletRequest.getParameter("branchActInfo");
      String branchActivityNames = "";
      String branchUserId = "", branchUserName = "";
      String[] branchActArray = branchActInfo.split(";;;;");
      for (int i = 0; i < branchActArray.length; i++) {
        if (i == 0) {
          branchActivityNames = String.valueOf(branchActivityNames) + branchActArray[i].split(";;")[1];
          branchUserId = String.valueOf(branchUserId) + branchActArray[i].split(";;")[2];
        } else {
          if (i < 3) {
            branchActivityNames = String.valueOf(branchActivityNames) + "," + branchActArray[i].split(";;")[1];
          } else if (i == 5) {
            branchActivityNames = String.valueOf(branchActivityNames) + "等";
          } 
          branchUserId = String.valueOf(branchUserId) + "," + branchActArray[i].split(";;")[2];
        } 
      } 
      workVO.setCurStep(branchActivityNames);
      branchUserName = StaticParam.getNamesByIds(branchUserId);
      String[] para = { orgIdString, dealTips, processDeadlineDate, sendSMS, branchActInfo };
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      submitSuccess = procSubmit.newProcBranchSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == 0L)
        return "-1"; 
      int count = 0;
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
        workLogVO.setSendAction("送" + workVO.getCurStep());
        workLogVO.setReceiveUserName(branchUserName);
        workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
        workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
        workFlowButtonBD.setDealWithLog(workLogVO);
        if (httpServletRequest.getParameter("mainNeedPassRound") != null && !"".equals(httpServletRequest.getParameter("passRoundName"))) {
          workLogVO = new WorkLogVO();
          workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
          workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
          workLogVO.setSendAction("送" + workVO.getCurStep() + "阅件");
          String passRoundUser = httpServletRequest.getParameter("passRoundName");
          workLogVO.setReceiveUserName(branchUserName);
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
    //   304: ifne -> 4117
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
    //   448: ldc_w 'submitPerson'
    //   451: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   456: astore #14
    //   458: aload_1
    //   459: ldc_w 'mainPressType'
    //   462: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   467: astore #15
    //   469: ldc '-1'
    //   471: astore #16
    //   473: ldc '-1'
    //   475: astore #17
    //   477: aload_1
    //   478: ldc_w 'include_comment'
    //   481: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   486: astore #18
    //   488: aload #18
    //   490: invokestatic escapeHTMLTags2 : (Ljava/lang/String;)Ljava/lang/String;
    //   493: astore #18
    //   495: aload_1
    //   496: ldc_w 'include_signcomment'
    //   499: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   504: astore #19
    //   506: aload_1
    //   507: ldc_w 'commentOrg'
    //   510: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   515: astore #20
    //   517: aload_1
    //   518: ldc_w 'mainAllowStandFor'
    //   521: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   526: astore #21
    //   528: aload_1
    //   529: ldc_w 'mainAllowCancel'
    //   532: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   537: astore #22
    //   539: aload_1
    //   540: ldc_w 'curActivityId'
    //   543: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   548: astore #23
    //   550: aload_1
    //   551: ldc_w 'curActivityName'
    //   554: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   559: astore #24
    //   561: aload_1
    //   562: ldc_w 'mainNextActivityId'
    //   565: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   570: astore #25
    //   572: aload_1
    //   573: ldc_w 'mainNextActivityName'
    //   576: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   581: astore #26
    //   583: aload_1
    //   584: ldc_w 'processName'
    //   587: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   592: astore #27
    //   594: aload_1
    //   595: ldc 'tableId'
    //   597: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   602: astore #28
    //   604: new com/js/oa/eform/service/CustomFormBD
    //   607: dup
    //   608: invokespecial <init> : ()V
    //   611: aload #28
    //   613: invokevirtual getTable : (Ljava/lang/String;)Ljava/lang/String;
    //   616: astore #29
    //   618: aload_1
    //   619: ldc 'recordId'
    //   621: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   626: astore #30
    //   628: aload_1
    //   629: ldc_w 'workId'
    //   632: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   637: astore #31
    //   639: aload_2
    //   640: ldc_w 'userId'
    //   643: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   648: invokevirtual toString : ()Ljava/lang/String;
    //   651: astore #32
    //   653: aload_2
    //   654: ldc_w 'orgId'
    //   657: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   662: invokevirtual toString : ()Ljava/lang/String;
    //   665: astore #33
    //   667: aload_2
    //   668: ldc_w 'orgIdString'
    //   671: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   676: invokevirtual toString : ()Ljava/lang/String;
    //   679: astore #34
    //   681: aload_1
    //   682: ldc_w 'stepCount'
    //   685: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   690: astore #35
    //   692: aload_1
    //   693: ldc_w 'activityClass'
    //   696: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   701: astore #36
    //   703: aload_1
    //   704: ldc_w 'mainDeadLineTime'
    //   707: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   712: astore #16
    //   714: aload_1
    //   715: ldc_w 'mainPressMotionTime'
    //   718: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   723: astore #17
    //   725: aload_1
    //   726: ldc_w 'relproject'
    //   729: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   734: astore #37
    //   736: new com/js/oa/jsflow/service/WorkFlowBD
    //   739: dup
    //   740: invokespecial <init> : ()V
    //   743: astore #38
    //   745: iconst_1
    //   746: anewarray java/lang/String
    //   749: dup
    //   750: iconst_0
    //   751: ldc ''
    //   753: aastore
    //   754: astore #39
    //   756: ldc '3'
    //   758: aload #36
    //   760: invokevirtual equals : (Ljava/lang/Object;)Z
    //   763: ifeq -> 881
    //   766: new com/js/oa/jsflow/util/NewWorkflowUtil
    //   769: dup
    //   770: invokespecial <init> : ()V
    //   773: astore #40
    //   775: aload #40
    //   777: aload #28
    //   779: aload #30
    //   781: aload #23
    //   783: aload #35
    //   785: invokestatic parseInt : (Ljava/lang/String;)I
    //   788: invokevirtual getPreActivity : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lcom/js/oa/jsflow/vo/ActivityVO;
    //   791: astore #41
    //   793: aload #41
    //   795: invokevirtual getId : ()J
    //   798: invokestatic valueOf : (J)Ljava/lang/String;
    //   801: astore #25
    //   803: aload #41
    //   805: invokevirtual getName : ()Ljava/lang/String;
    //   808: astore #26
    //   810: aload #40
    //   812: aload #28
    //   814: aload #30
    //   816: aload #23
    //   818: aload #35
    //   820: invokestatic parseInt : (Ljava/lang/String;)I
    //   823: invokevirtual getPreActivityUser : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)[Ljava/lang/String;
    //   826: astore #39
    //   828: aload #41
    //   830: invokevirtual getPressType : ()I
    //   833: invokestatic valueOf : (I)Ljava/lang/String;
    //   836: astore #15
    //   838: aload #41
    //   840: invokevirtual getDeadlineTime : ()I
    //   843: invokestatic valueOf : (I)Ljava/lang/String;
    //   846: astore #16
    //   848: aload #41
    //   850: invokevirtual getPressMotionTime : ()I
    //   853: invokestatic valueOf : (I)Ljava/lang/String;
    //   856: astore #17
    //   858: aload #41
    //   860: invokevirtual getAllowStandFor : ()I
    //   863: invokestatic valueOf : (I)Ljava/lang/String;
    //   866: astore #21
    //   868: aload #41
    //   870: invokevirtual getAllowcancel : ()I
    //   873: invokestatic valueOf : (I)Ljava/lang/String;
    //   876: astore #22
    //   878: goto -> 1032
    //   881: aload_1
    //   882: ldc_w 'operId'
    //   885: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   890: astore #40
    //   892: aload #40
    //   894: ifnull -> 917
    //   897: ldc_w 'null'
    //   900: aload #40
    //   902: invokevirtual equals : (Ljava/lang/Object;)Z
    //   905: ifne -> 917
    //   908: aload #40
    //   910: invokevirtual length : ()I
    //   913: iconst_2
    //   914: if_icmpge -> 926
    //   917: iconst_0
    //   918: anewarray java/lang/String
    //   921: astore #39
    //   923: goto -> 1032
    //   926: aload #40
    //   928: ldc_w '#'
    //   931: invokevirtual indexOf : (Ljava/lang/String;)I
    //   934: iflt -> 1007
    //   937: aload #40
    //   939: ldc_w '#'
    //   942: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   945: astore #41
    //   947: ldc ''
    //   949: astore #40
    //   951: iconst_0
    //   952: istore #42
    //   954: goto -> 999
    //   957: aload #41
    //   959: iload #42
    //   961: aaload
    //   962: ldc_w '$'
    //   965: invokevirtual indexOf : (Ljava/lang/String;)I
    //   968: iflt -> 996
    //   971: new java/lang/StringBuilder
    //   974: dup
    //   975: aload #40
    //   977: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   980: invokespecial <init> : (Ljava/lang/String;)V
    //   983: aload #41
    //   985: iload #42
    //   987: aaload
    //   988: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   991: invokevirtual toString : ()Ljava/lang/String;
    //   994: astore #40
    //   996: iinc #42, 1
    //   999: iload #42
    //   1001: aload #41
    //   1003: arraylength
    //   1004: if_icmplt -> 957
    //   1007: aload #40
    //   1009: iconst_1
    //   1010: aload #40
    //   1012: invokevirtual length : ()I
    //   1015: iconst_1
    //   1016: isub
    //   1017: invokevirtual substring : (II)Ljava/lang/String;
    //   1020: astore #40
    //   1022: aload #40
    //   1024: ldc_w '\$\$'
    //   1027: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1030: astore #39
    //   1032: aload #39
    //   1034: ifnull -> 4028
    //   1037: aload #39
    //   1039: arraylength
    //   1040: ifle -> 4028
    //   1043: ldc ''
    //   1045: aload #39
    //   1047: iconst_0
    //   1048: aaload
    //   1049: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1052: ifne -> 4028
    //   1055: ldc '3'
    //   1057: aload #36
    //   1059: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1062: ifne -> 1112
    //   1065: aload_1
    //   1066: ldc_w 'operName'
    //   1069: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1074: ifnull -> 4028
    //   1077: ldc_w 'null'
    //   1080: aload_1
    //   1081: ldc_w 'operName'
    //   1084: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1089: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1092: ifne -> 4028
    //   1095: ldc ''
    //   1097: aload_1
    //   1098: ldc_w 'operName'
    //   1101: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1106: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1109: ifne -> 4028
    //   1112: aload #15
    //   1114: invokestatic parseInt : (Ljava/lang/String;)I
    //   1117: tableswitch default -> 1234, 0 -> 1144, 1 -> 1155, 2 -> 1234
    //   1144: ldc '-1'
    //   1146: astore #16
    //   1148: ldc '-1'
    //   1150: astore #17
    //   1152: goto -> 1234
    //   1155: aload_1
    //   1156: ldc_w 'changeDeadLineTime'
    //   1159: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1164: astore #40
    //   1166: aload #40
    //   1168: ifnull -> 1202
    //   1171: ldc_w 'null'
    //   1174: aload #40
    //   1176: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1179: ifne -> 1202
    //   1182: ldc '-1'
    //   1184: aload #40
    //   1186: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1189: ifne -> 1202
    //   1192: ldc ''
    //   1194: aload #40
    //   1196: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1199: ifeq -> 1216
    //   1202: aload_1
    //   1203: ldc_w 'mainDeadLineTime'
    //   1206: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1211: astore #16
    //   1213: goto -> 1220
    //   1216: aload #40
    //   1218: astore #16
    //   1220: aload_1
    //   1221: ldc_w 'mainPressMotionTime'
    //   1224: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1229: astore #17
    //   1231: goto -> 1234
    //   1234: ldc ''
    //   1236: astore #40
    //   1238: iconst_1
    //   1239: anewarray java/lang/String
    //   1242: dup
    //   1243: iconst_0
    //   1244: ldc ''
    //   1246: aastore
    //   1247: astore #41
    //   1249: aload_1
    //   1250: ldc_w 'mainNeedPassRound'
    //   1253: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1258: ifnull -> 1676
    //   1261: aload_1
    //   1262: ldc_w 'mainNeedPassRound'
    //   1265: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1270: astore #40
    //   1272: aload_1
    //   1273: ldc_w 'mainPassRoundUserType'
    //   1276: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1281: invokestatic parseInt : (Ljava/lang/String;)I
    //   1284: istore #42
    //   1286: iload #42
    //   1288: bipush #10
    //   1290: if_icmpne -> 1297
    //   1293: bipush #100
    //   1295: istore #42
    //   1297: iload #42
    //   1299: lookupswitch default -> 1676, 10 -> 1656, 100 -> 1324
    //   1324: aload_1
    //   1325: ldc_w 'passRoundId'
    //   1328: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1333: astore #43
    //   1335: aload #43
    //   1337: ifnull -> 1676
    //   1340: aload #43
    //   1342: ldc ''
    //   1344: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1347: ifne -> 1676
    //   1350: new com/js/util/util/ConversionString
    //   1353: dup
    //   1354: aload #43
    //   1356: invokespecial <init> : (Ljava/lang/String;)V
    //   1359: astore #44
    //   1361: new java/lang/StringBuilder
    //   1364: dup
    //   1365: aload #44
    //   1367: invokevirtual getUserIdString : ()Ljava/lang/String;
    //   1370: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1373: invokespecial <init> : (Ljava/lang/String;)V
    //   1376: ldc_w ','
    //   1379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1382: invokevirtual toString : ()Ljava/lang/String;
    //   1385: astore #45
    //   1387: aload #44
    //   1389: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1392: ifnull -> 1443
    //   1395: ldc ''
    //   1397: aload #44
    //   1399: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1402: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1405: ifne -> 1443
    //   1408: new java/lang/StringBuilder
    //   1411: dup
    //   1412: aload #45
    //   1414: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1417: invokespecial <init> : (Ljava/lang/String;)V
    //   1420: aload_0
    //   1421: aload #44
    //   1423: invokevirtual getGroupIdString : ()Ljava/lang/String;
    //   1426: invokevirtual getUserByGroup : (Ljava/lang/String;)Ljava/lang/String;
    //   1429: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1432: ldc_w ','
    //   1435: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1438: invokevirtual toString : ()Ljava/lang/String;
    //   1441: astore #45
    //   1443: aload #44
    //   1445: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1448: ifnull -> 1493
    //   1451: ldc ''
    //   1453: aload #44
    //   1455: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1458: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1461: ifne -> 1493
    //   1464: new java/lang/StringBuilder
    //   1467: dup
    //   1468: aload #45
    //   1470: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1473: invokespecial <init> : (Ljava/lang/String;)V
    //   1476: aload_0
    //   1477: aload #44
    //   1479: invokevirtual getOrgIdString : ()Ljava/lang/String;
    //   1482: invokevirtual getUserByOrg : (Ljava/lang/String;)Ljava/lang/String;
    //   1485: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1488: invokevirtual toString : ()Ljava/lang/String;
    //   1491: astore #45
    //   1493: aconst_null
    //   1494: astore #46
    //   1496: new com/js/oa/userdb/util/DbOpt
    //   1499: dup
    //   1500: invokespecial <init> : ()V
    //   1503: astore #46
    //   1505: aload #45
    //   1507: ldc_w ',,'
    //   1510: ldc_w ','
    //   1513: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1516: ldc_w ',,'
    //   1519: ldc_w ','
    //   1522: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1525: astore #45
    //   1527: aload #45
    //   1529: ldc_w ','
    //   1532: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   1535: ifeq -> 1553
    //   1538: aload #45
    //   1540: iconst_1
    //   1541: aload #45
    //   1543: invokevirtual length : ()I
    //   1546: iconst_1
    //   1547: isub
    //   1548: invokevirtual substring : (II)Ljava/lang/String;
    //   1551: astore #45
    //   1553: aload #45
    //   1555: ldc_w ','
    //   1558: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   1561: ifeq -> 1579
    //   1564: aload #45
    //   1566: iconst_0
    //   1567: aload #45
    //   1569: invokevirtual length : ()I
    //   1572: iconst_1
    //   1573: isub
    //   1574: invokevirtual substring : (II)Ljava/lang/String;
    //   1577: astore #45
    //   1579: aload #45
    //   1581: invokevirtual length : ()I
    //   1584: ifle -> 1621
    //   1587: aload #46
    //   1589: new java/lang/StringBuilder
    //   1592: dup
    //   1593: ldc_w 'select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in ('
    //   1596: invokespecial <init> : (Ljava/lang/String;)V
    //   1599: aload #45
    //   1601: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1604: ldc_w ')'
    //   1607: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1610: invokevirtual toString : ()Ljava/lang/String;
    //   1613: invokevirtual executeQueryToStrArr1 : (Ljava/lang/String;)[Ljava/lang/String;
    //   1616: astore #41
    //   1618: goto -> 1627
    //   1621: iconst_0
    //   1622: anewarray java/lang/String
    //   1625: astore #41
    //   1627: aload #46
    //   1629: invokevirtual close : ()V
    //   1632: goto -> 1676
    //   1635: astore #47
    //   1637: aload #46
    //   1639: invokevirtual close : ()V
    //   1642: goto -> 1647
    //   1645: astore #48
    //   1647: iconst_0
    //   1648: anewarray java/lang/String
    //   1651: astore #41
    //   1653: goto -> 1676
    //   1656: aload_1
    //   1657: ldc_w 'passRoundId'
    //   1660: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1665: astore #44
    //   1667: aload #38
    //   1669: aload #44
    //   1671: invokevirtual getLeaderListByOrgId : (Ljava/lang/String;)[Ljava/lang/String;
    //   1674: astore #41
    //   1676: aload_1
    //   1677: ldc_w 'subProcWorkId'
    //   1680: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1685: ifnonnull -> 1693
    //   1688: ldc '0'
    //   1690: goto -> 1702
    //   1693: aload_1
    //   1694: ldc_w 'subProcWorkId'
    //   1697: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1702: astore #42
    //   1704: aload_1
    //   1705: ldc_w 'modiCommentId'
    //   1708: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1713: astore #43
    //   1715: aload #38
    //   1717: aload #25
    //   1719: aload_1
    //   1720: ldc 'processId'
    //   1722: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1727: aload #30
    //   1729: aload #28
    //   1731: invokevirtual getActivityDelayTime : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1734: astore #44
    //   1736: ldc_w 'null'
    //   1739: aload #44
    //   1741: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   1744: ifeq -> 1751
    //   1747: ldc ''
    //   1749: astore #44
    //   1751: new java/util/HashMap
    //   1754: dup
    //   1755: invokespecial <init> : ()V
    //   1758: astore #45
    //   1760: aload #45
    //   1762: ldc 'tableId'
    //   1764: aload #28
    //   1766: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1771: pop
    //   1772: aload #45
    //   1774: ldc 'recordId'
    //   1776: aload #30
    //   1778: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1783: pop
    //   1784: aload #45
    //   1786: ldc_w 'curActivityName'
    //   1789: aload #24
    //   1791: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1796: pop
    //   1797: aload #45
    //   1799: ldc_w 'curActivityId'
    //   1802: aload #23
    //   1804: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1809: pop
    //   1810: aload #45
    //   1812: ldc_w 'userId'
    //   1815: aload #32
    //   1817: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1822: pop
    //   1823: aload #45
    //   1825: ldc_w 'orgIdString'
    //   1828: aload #34
    //   1830: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1835: pop
    //   1836: aload #45
    //   1838: ldc_w 'comment'
    //   1841: aload #18
    //   1843: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1848: pop
    //   1849: aload #45
    //   1851: ldc_w 'signcomment'
    //   1854: aload #19
    //   1856: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1861: pop
    //   1862: aload #45
    //   1864: ldc_w 'commentOrg'
    //   1867: aload #20
    //   1869: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1874: pop
    //   1875: aload #45
    //   1877: ldc_w 'nextActivityName'
    //   1880: aload #26
    //   1882: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1887: pop
    //   1888: aload #45
    //   1890: ldc_w 'nextActivityId'
    //   1893: aload #25
    //   1895: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1900: pop
    //   1901: aload #45
    //   1903: ldc_w 'stepCount'
    //   1906: aload #35
    //   1908: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1913: pop
    //   1914: aload #45
    //   1916: ldc_w 'isStandForWork'
    //   1919: aload #10
    //   1921: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1926: pop
    //   1927: aload #45
    //   1929: ldc_w 'standForUserId'
    //   1932: aload #11
    //   1934: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1939: pop
    //   1940: aload #45
    //   1942: ldc_w 'activityClass'
    //   1945: aload #36
    //   1947: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1952: pop
    //   1953: aload #45
    //   1955: ldc_w 'subProcWorkId'
    //   1958: aload #42
    //   1960: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1965: pop
    //   1966: aload_1
    //   1967: ldc_w 'include_commField'
    //   1970: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1975: ifnull -> 1998
    //   1978: aload #45
    //   1980: ldc_w 'commentField'
    //   1983: aload_1
    //   1984: ldc_w 'include_commField'
    //   1987: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   1992: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1997: pop
    //   1998: aload #45
    //   2000: ldc_w 'userScope'
    //   2003: aload_1
    //   2004: ldc_w 'userScope'
    //   2007: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2012: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2017: pop
    //   2018: aload #45
    //   2020: ldc_w 'scopeId'
    //   2023: aload_1
    //   2024: ldc_w 'scopeId'
    //   2027: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2032: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2037: pop
    //   2038: aload #45
    //   2040: ldc_w 'modiCommentId'
    //   2043: aload #43
    //   2045: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2050: pop
    //   2051: aload #45
    //   2053: ldc_w 'relproject'
    //   2056: aload #37
    //   2058: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2063: pop
    //   2064: aload #45
    //   2066: ldc_w 'delayTime'
    //   2069: aload #44
    //   2071: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2076: pop
    //   2077: new com/js/oa/jsflow/service/WorkFlowCommonBD
    //   2080: dup
    //   2081: invokespecial <init> : ()V
    //   2084: astore #46
    //   2086: aload_1
    //   2087: ldc_w 'subProcType'
    //   2090: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2095: ifnonnull -> 2103
    //   2098: ldc '-1'
    //   2100: goto -> 2112
    //   2103: aload_1
    //   2104: ldc_w 'subProcType'
    //   2107: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2112: astore #47
    //   2114: aload_1
    //   2115: ldc_w 'mainLinkFile'
    //   2118: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2123: astore #48
    //   2125: aload #38
    //   2127: aload #28
    //   2129: aload #30
    //   2131: aload #23
    //   2133: invokevirtual getTransactType : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2136: astore #49
    //   2138: aload_1
    //   2139: ldc_w 'approveMode'
    //   2142: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2147: astore #50
    //   2149: aload #8
    //   2151: ifnull -> 2172
    //   2154: aload #8
    //   2156: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   2159: ldc_w 'NULL'
    //   2162: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2165: ifeq -> 2172
    //   2168: ldc ''
    //   2170: astore #8
    //   2172: ldc ''
    //   2174: astore #51
    //   2176: ldc ''
    //   2178: aload_1
    //   2179: ldc_w 'titleFieldName'
    //   2182: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2187: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2190: ifne -> 2210
    //   2193: aload_1
    //   2194: aload_1
    //   2195: ldc_w 'titleFieldName'
    //   2198: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2203: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2208: astore #51
    //   2210: aload_1
    //   2211: ldc_w 'emergence'
    //   2214: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2219: ifnonnull -> 2227
    //   2222: ldc '0'
    //   2224: goto -> 2236
    //   2227: aload_1
    //   2228: ldc_w 'emergence'
    //   2231: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2236: astore #52
    //   2238: aload_1
    //   2239: ldc_w 'dealTips'
    //   2242: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2247: astore #53
    //   2249: aload #53
    //   2251: ifnull -> 2349
    //   2254: aload #53
    //   2256: ldc ''
    //   2258: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2261: ifne -> 2349
    //   2264: new java/text/SimpleDateFormat
    //   2267: dup
    //   2268: ldc_w 'yyyy-MM-dd hh:mm'
    //   2271: invokespecial <init> : (Ljava/lang/String;)V
    //   2274: astore #54
    //   2276: new java/lang/StringBuilder
    //   2279: dup
    //   2280: ldc_w '来自上一环节:'
    //   2283: invokespecial <init> : (Ljava/lang/String;)V
    //   2286: aload #26
    //   2288: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2291: ldc_w ';  办理人：'
    //   2294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2297: aload_2
    //   2298: ldc_w 'userName'
    //   2301: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2306: invokevirtual toString : ()Ljava/lang/String;
    //   2309: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2312: ldc_w '; '
    //   2315: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2318: aload #54
    //   2320: new java/util/Date
    //   2323: dup
    //   2324: invokespecial <init> : ()V
    //   2327: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   2330: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2333: ldc_w ' '
    //   2336: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2339: aload #53
    //   2341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2344: invokevirtual toString : ()Ljava/lang/String;
    //   2347: astore #53
    //   2349: aload_1
    //   2350: ldc_w 'sendSMS'
    //   2353: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2358: ifnonnull -> 2366
    //   2361: ldc '1'
    //   2363: goto -> 2375
    //   2366: aload_1
    //   2367: ldc_w 'sendSMS'
    //   2370: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2375: astore #54
    //   2377: bipush #23
    //   2379: anewarray java/lang/String
    //   2382: dup
    //   2383: iconst_0
    //   2384: aload #26
    //   2386: aastore
    //   2387: dup
    //   2388: iconst_1
    //   2389: aload #25
    //   2391: aastore
    //   2392: dup
    //   2393: iconst_2
    //   2394: aload #31
    //   2396: aastore
    //   2397: dup
    //   2398: iconst_3
    //   2399: aload #16
    //   2401: aastore
    //   2402: dup
    //   2403: iconst_4
    //   2404: aload #17
    //   2406: aastore
    //   2407: dup
    //   2408: iconst_5
    //   2409: aload #23
    //   2411: aastore
    //   2412: dup
    //   2413: bipush #6
    //   2415: aload #22
    //   2417: aastore
    //   2418: dup
    //   2419: bipush #7
    //   2421: aload #35
    //   2423: aastore
    //   2424: dup
    //   2425: bipush #8
    //   2427: aload #8
    //   2429: aastore
    //   2430: dup
    //   2431: bipush #9
    //   2433: aload #49
    //   2435: aastore
    //   2436: dup
    //   2437: bipush #10
    //   2439: aload #48
    //   2441: aastore
    //   2442: dup
    //   2443: bipush #11
    //   2445: aload #21
    //   2447: aastore
    //   2448: dup
    //   2449: bipush #12
    //   2451: aload #10
    //   2453: aastore
    //   2454: dup
    //   2455: bipush #13
    //   2457: aload #32
    //   2459: aastore
    //   2460: dup
    //   2461: bipush #14
    //   2463: aload #11
    //   2465: aastore
    //   2466: dup
    //   2467: bipush #15
    //   2469: aload #12
    //   2471: aastore
    //   2472: dup
    //   2473: bipush #16
    //   2475: aload #36
    //   2477: aastore
    //   2478: dup
    //   2479: bipush #17
    //   2481: aload #47
    //   2483: aastore
    //   2484: dup
    //   2485: bipush #18
    //   2487: aload #51
    //   2489: aastore
    //   2490: dup
    //   2491: bipush #19
    //   2493: aload #52
    //   2495: aastore
    //   2496: dup
    //   2497: bipush #20
    //   2499: aload #50
    //   2501: aastore
    //   2502: dup
    //   2503: bipush #21
    //   2505: aload #53
    //   2507: aastore
    //   2508: dup
    //   2509: bipush #22
    //   2511: aload #54
    //   2513: aastore
    //   2514: astore #55
    //   2516: aload #46
    //   2518: aload #45
    //   2520: aload #55
    //   2522: aload #39
    //   2524: aload #40
    //   2526: aload #41
    //   2528: invokevirtual transWorkflowButton : (Ljava/util/Map;[Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Integer;
    //   2531: astore #56
    //   2533: aload #56
    //   2535: invokevirtual intValue : ()I
    //   2538: ifle -> 2542
    //   2541: return
    //   2542: aload #56
    //   2544: invokevirtual intValue : ()I
    //   2547: ifge -> 2563
    //   2550: aload_1
    //   2551: ldc 'flowfaild'
    //   2553: ldc '1'
    //   2555: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2560: goto -> 3979
    //   2563: new com/js/oa/jsflow/vo/WorkLogVO
    //   2566: dup
    //   2567: invokespecial <init> : ()V
    //   2570: astore #57
    //   2572: ldc '3'
    //   2574: aload #36
    //   2576: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2579: ifeq -> 2860
    //   2582: aload #57
    //   2584: aload_2
    //   2585: ldc_w 'userId'
    //   2588: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2593: invokevirtual toString : ()Ljava/lang/String;
    //   2596: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   2599: aload #57
    //   2601: aload_2
    //   2602: ldc_w 'userName'
    //   2605: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2610: invokevirtual toString : ()Ljava/lang/String;
    //   2613: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   2616: ldc_w 'shandongjiguanshiwuju'
    //   2619: invokestatic getCustomerName : ()Ljava/lang/String;
    //   2622: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2625: ifeq -> 2654
    //   2628: aload #57
    //   2630: new java/lang/StringBuilder
    //   2633: dup
    //   2634: ldc_w '返回'
    //   2637: invokespecial <init> : (Ljava/lang/String;)V
    //   2640: aload #26
    //   2642: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2645: invokevirtual toString : ()Ljava/lang/String;
    //   2648: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2651: goto -> 2677
    //   2654: aload #57
    //   2656: new java/lang/StringBuilder
    //   2659: dup
    //   2660: ldc_w '送'
    //   2663: invokespecial <init> : (Ljava/lang/String;)V
    //   2666: aload #26
    //   2668: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2671: invokevirtual toString : ()Ljava/lang/String;
    //   2674: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2677: ldc '0'
    //   2679: astore #58
    //   2681: iconst_0
    //   2682: istore #59
    //   2684: goto -> 2721
    //   2687: new java/lang/StringBuilder
    //   2690: dup
    //   2691: aload #58
    //   2693: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2696: invokespecial <init> : (Ljava/lang/String;)V
    //   2699: aload #39
    //   2701: iload #59
    //   2703: aaload
    //   2704: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2707: ldc_w ','
    //   2710: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2713: invokevirtual toString : ()Ljava/lang/String;
    //   2716: astore #58
    //   2718: iinc #59, 1
    //   2721: iload #59
    //   2723: aload #39
    //   2725: arraylength
    //   2726: if_icmplt -> 2687
    //   2729: aload #58
    //   2731: ldc_w ','
    //   2734: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2737: ifeq -> 2755
    //   2740: aload #58
    //   2742: iconst_0
    //   2743: aload #58
    //   2745: invokevirtual length : ()I
    //   2748: iconst_1
    //   2749: isub
    //   2750: invokevirtual substring : (II)Ljava/lang/String;
    //   2753: astore #58
    //   2755: new com/js/system/service/usermanager/UserBD
    //   2758: dup
    //   2759: invokespecial <init> : ()V
    //   2762: aload #58
    //   2764: invokevirtual getUserNameById : (Ljava/lang/String;)Ljava/lang/String;
    //   2767: astore #58
    //   2769: aload #58
    //   2771: ldc_w ','
    //   2774: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2777: ifeq -> 2795
    //   2780: aload #58
    //   2782: iconst_0
    //   2783: aload #58
    //   2785: invokevirtual length : ()I
    //   2788: iconst_1
    //   2789: isub
    //   2790: invokevirtual substring : (II)Ljava/lang/String;
    //   2793: astore #58
    //   2795: aload #57
    //   2797: aload #58
    //   2799: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   2802: aload #57
    //   2804: aload_1
    //   2805: ldc 'processId'
    //   2807: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2812: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   2815: aload #57
    //   2817: aload_1
    //   2818: ldc 'tableId'
    //   2820: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2825: invokevirtual setTableId : (Ljava/lang/String;)V
    //   2828: aload #57
    //   2830: aload_1
    //   2831: ldc 'recordId'
    //   2833: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2838: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   2841: aload #57
    //   2843: aload_2
    //   2844: ldc 'domainId'
    //   2846: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2851: invokevirtual toString : ()Ljava/lang/String;
    //   2854: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   2857: goto -> 2986
    //   2860: aload #57
    //   2862: aload_2
    //   2863: ldc_w 'userId'
    //   2866: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2871: invokevirtual toString : ()Ljava/lang/String;
    //   2874: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   2877: aload #57
    //   2879: aload_2
    //   2880: ldc_w 'userName'
    //   2883: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2888: invokevirtual toString : ()Ljava/lang/String;
    //   2891: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   2894: aload #57
    //   2896: new java/lang/StringBuilder
    //   2899: dup
    //   2900: ldc_w '送'
    //   2903: invokespecial <init> : (Ljava/lang/String;)V
    //   2906: aload #26
    //   2908: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2911: invokevirtual toString : ()Ljava/lang/String;
    //   2914: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   2917: aload #57
    //   2919: aload_1
    //   2920: ldc_w 'operName'
    //   2923: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2928: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   2931: aload #57
    //   2933: aload_1
    //   2934: ldc 'processId'
    //   2936: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2941: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   2944: aload #57
    //   2946: aload_1
    //   2947: ldc 'tableId'
    //   2949: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2954: invokevirtual setTableId : (Ljava/lang/String;)V
    //   2957: aload #57
    //   2959: aload_1
    //   2960: ldc 'recordId'
    //   2962: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   2967: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   2970: aload #57
    //   2972: aload_2
    //   2973: ldc 'domainId'
    //   2975: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   2980: invokevirtual toString : ()Ljava/lang/String;
    //   2983: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   2986: aload #4
    //   2988: aload #57
    //   2990: invokevirtual setDealWithLog : (Lcom/js/oa/jsflow/vo/WorkLogVO;)V
    //   2993: aload_1
    //   2994: ldc_w 'mainNeedPassRound'
    //   2997: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3002: ifnull -> 3170
    //   3005: ldc ''
    //   3007: aload_1
    //   3008: ldc_w 'passRoundName'
    //   3011: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3016: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3019: ifne -> 3170
    //   3022: new com/js/oa/jsflow/vo/WorkLogVO
    //   3025: dup
    //   3026: invokespecial <init> : ()V
    //   3029: astore #57
    //   3031: aload #57
    //   3033: aload_2
    //   3034: ldc_w 'userId'
    //   3037: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3042: invokevirtual toString : ()Ljava/lang/String;
    //   3045: invokevirtual setSendUserId : (Ljava/lang/String;)V
    //   3048: aload #57
    //   3050: aload_2
    //   3051: ldc_w 'userName'
    //   3054: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3059: invokevirtual toString : ()Ljava/lang/String;
    //   3062: invokevirtual setSendUserName : (Ljava/lang/String;)V
    //   3065: aload #57
    //   3067: new java/lang/StringBuilder
    //   3070: dup
    //   3071: ldc_w '送'
    //   3074: invokespecial <init> : (Ljava/lang/String;)V
    //   3077: aload #26
    //   3079: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3082: ldc_w '阅件'
    //   3085: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3088: invokevirtual toString : ()Ljava/lang/String;
    //   3091: invokevirtual setSendAction : (Ljava/lang/String;)V
    //   3094: aload #57
    //   3096: aload_1
    //   3097: ldc_w 'passRoundName'
    //   3100: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3105: invokevirtual setReceiveUserName : (Ljava/lang/String;)V
    //   3108: aload #57
    //   3110: aload_1
    //   3111: ldc 'processId'
    //   3113: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3118: invokevirtual setProcessId : (Ljava/lang/String;)V
    //   3121: aload #57
    //   3123: aload_1
    //   3124: ldc 'tableId'
    //   3126: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3131: invokevirtual setTableId : (Ljava/lang/String;)V
    //   3134: aload #57
    //   3136: aload_1
    //   3137: ldc 'recordId'
    //   3139: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3144: invokevirtual setRecordId : (Ljava/lang/String;)V
    //   3147: aload #57
    //   3149: aload_2
    //   3150: ldc 'domainId'
    //   3152: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3157: invokevirtual toString : ()Ljava/lang/String;
    //   3160: invokevirtual setDomainId : (Ljava/lang/String;)V
    //   3163: aload #4
    //   3165: aload #57
    //   3167: invokevirtual setDealWithLog : (Lcom/js/oa/jsflow/vo/WorkLogVO;)V
    //   3170: aload_1
    //   3171: ldc_w 'sendMsgToInitiator'
    //   3174: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3179: ifnull -> 3419
    //   3182: ldc '1'
    //   3184: aload_1
    //   3185: ldc_w 'sendMsgToInitiator'
    //   3188: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3193: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3196: ifeq -> 3419
    //   3199: new java/lang/StringBuilder
    //   3202: dup
    //   3203: ldc_w 'SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID='
    //   3206: invokespecial <init> : (Ljava/lang/String;)V
    //   3209: aload_1
    //   3210: ldc 'processId'
    //   3212: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3220: ldc_w ' AND a.worktable_id='
    //   3223: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3226: aload #28
    //   3228: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3231: ldc_w ' AND a.workrecord_id='
    //   3234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3237: aload #30
    //   3239: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3242: ldc_w ' AND workstepcount=0'
    //   3245: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3248: invokevirtual toString : ()Ljava/lang/String;
    //   3251: astore #58
    //   3253: new com/js/util/util/DataSourceBase
    //   3256: dup
    //   3257: invokespecial <init> : ()V
    //   3260: aload #58
    //   3262: invokevirtual queryArrayBySql : (Ljava/lang/String;)[[Ljava/lang/String;
    //   3265: astore #59
    //   3267: new java/lang/StringBuilder
    //   3270: dup
    //   3271: ldc_w '/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId='
    //   3274: invokespecial <init> : (Ljava/lang/String;)V
    //   3277: aload #31
    //   3279: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3282: invokevirtual toString : ()Ljava/lang/String;
    //   3285: astore #60
    //   3287: new java/lang/StringBuilder
    //   3290: dup
    //   3291: ldc_w '您发起的'
    //   3294: invokespecial <init> : (Ljava/lang/String;)V
    //   3297: aload_1
    //   3298: ldc_w 'processName'
    //   3301: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3309: ldc_w '流程已经被'
    //   3312: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3315: aload_1
    //   3316: invokeinterface getSession : ()Ljavax/servlet/http/HttpSession;
    //   3321: ldc_w 'userName'
    //   3324: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3329: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3332: ldc_w '('
    //   3335: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3338: aload_1
    //   3339: ldc_w 'curActivityName'
    //   3342: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3347: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3350: ldc_w ')处理完毕！'
    //   3353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3356: invokevirtual toString : ()Ljava/lang/String;
    //   3359: aload #60
    //   3361: aload #59
    //   3363: iconst_0
    //   3364: aaload
    //   3365: iconst_0
    //   3366: aaload
    //   3367: ldc_w 'jsflow'
    //   3370: new java/util/Date
    //   3373: dup
    //   3374: invokespecial <init> : ()V
    //   3377: new java/text/SimpleDateFormat
    //   3380: dup
    //   3381: ldc_w 'yyyy-MM-dd HH:mm:ss'
    //   3384: invokespecial <init> : (Ljava/lang/String;)V
    //   3387: ldc_w '2050-01-01 00:00:00'
    //   3390: invokevirtual parse : (Ljava/lang/String;)Ljava/util/Date;
    //   3393: ldc_w '系统提醒'
    //   3396: aload #59
    //   3398: iconst_0
    //   3399: aaload
    //   3400: iconst_2
    //   3401: aaload
    //   3402: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   3405: iconst_1
    //   3406: invokestatic sendMessageToUsers2 : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Long;I)V
    //   3409: goto -> 3419
    //   3412: astore #60
    //   3414: aload #60
    //   3416: invokevirtual printStackTrace : ()V
    //   3419: aload_2
    //   3420: ldc_w 'userId'
    //   3423: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3428: invokevirtual toString : ()Ljava/lang/String;
    //   3431: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Integer;
    //   3434: astore #58
    //   3436: aload_1
    //   3437: ldc_w 'sendMsgToDealDone'
    //   3440: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3445: ifnull -> 3979
    //   3448: ldc '1'
    //   3450: aload_1
    //   3451: ldc_w 'sendMsgToDealDone'
    //   3454: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3459: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3462: ifeq -> 3979
    //   3465: new java/lang/StringBuilder
    //   3468: dup
    //   3469: ldc_w 'SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID='
    //   3472: invokespecial <init> : (Ljava/lang/String;)V
    //   3475: aload_1
    //   3476: ldc 'processId'
    //   3478: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3483: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3486: ldc_w ' AND a.worktable_id='
    //   3489: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3492: aload #28
    //   3494: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3497: ldc_w ' AND a.workrecord_id='
    //   3500: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3503: aload #30
    //   3505: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3508: ldc_w ' AND a.workstatus=101 and a.wf_curemployee_id <> '
    //   3511: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3514: aload #58
    //   3516: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3519: invokevirtual toString : ()Ljava/lang/String;
    //   3522: astore #59
    //   3524: new com/js/util/util/DataSourceBase
    //   3527: dup
    //   3528: invokespecial <init> : ()V
    //   3531: aload #59
    //   3533: invokevirtual queryArrayBySql : (Ljava/lang/String;)[[Ljava/lang/String;
    //   3536: astore #60
    //   3538: new java/lang/StringBuilder
    //   3541: dup
    //   3542: ldc_w '/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId='
    //   3545: invokespecial <init> : (Ljava/lang/String;)V
    //   3548: aload #31
    //   3550: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3553: invokevirtual toString : ()Ljava/lang/String;
    //   3556: astore #61
    //   3558: iconst_0
    //   3559: istore #62
    //   3561: goto -> 3617
    //   3564: iload #62
    //   3566: iconst_1
    //   3567: iadd
    //   3568: istore #63
    //   3570: goto -> 3606
    //   3573: aload #60
    //   3575: iload #62
    //   3577: aaload
    //   3578: iconst_0
    //   3579: aaload
    //   3580: aload #60
    //   3582: iload #63
    //   3584: aaload
    //   3585: iconst_0
    //   3586: aaload
    //   3587: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3590: ifeq -> 3603
    //   3593: aload #60
    //   3595: iload #63
    //   3597: aaload
    //   3598: iconst_0
    //   3599: ldc_w '*'
    //   3602: aastore
    //   3603: iinc #63, 1
    //   3606: iload #63
    //   3608: aload #60
    //   3610: arraylength
    //   3611: if_icmplt -> 3573
    //   3614: iinc #62, 1
    //   3617: iload #62
    //   3619: aload #60
    //   3621: arraylength
    //   3622: if_icmplt -> 3564
    //   3625: iconst_0
    //   3626: istore #62
    //   3628: goto -> 3961
    //   3631: ldc_w '*'
    //   3634: aload #60
    //   3636: iload #62
    //   3638: aaload
    //   3639: iconst_0
    //   3640: aaload
    //   3641: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3644: ifne -> 3958
    //   3647: aload_1
    //   3648: ldc_w 'sendMsgToInitiator'
    //   3651: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3656: ifnull -> 3826
    //   3659: ldc '1'
    //   3661: aload_1
    //   3662: ldc_w 'sendMsgToInitiator'
    //   3665: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3670: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3673: ifeq -> 3826
    //   3676: aload #13
    //   3678: aload #60
    //   3680: iload #62
    //   3682: aaload
    //   3683: iconst_0
    //   3684: aaload
    //   3685: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3688: ifne -> 3958
    //   3691: new java/lang/StringBuilder
    //   3694: dup
    //   3695: aload #14
    //   3697: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3700: invokespecial <init> : (Ljava/lang/String;)V
    //   3703: ldc_w '发起的'
    //   3706: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3709: aload_1
    //   3710: ldc_w 'processName'
    //   3713: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3718: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3721: ldc_w '流程已经被'
    //   3724: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3727: aload_1
    //   3728: invokeinterface getSession : ()Ljavax/servlet/http/HttpSession;
    //   3733: ldc_w 'userName'
    //   3736: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3741: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3744: ldc_w '('
    //   3747: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3750: aload_1
    //   3751: ldc_w 'curActivityName'
    //   3754: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3759: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3762: ldc_w ')处理完毕！'
    //   3765: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3768: invokevirtual toString : ()Ljava/lang/String;
    //   3771: aload #61
    //   3773: aload #60
    //   3775: iload #62
    //   3777: aaload
    //   3778: iconst_0
    //   3779: aaload
    //   3780: ldc_w 'jsflow'
    //   3783: new java/util/Date
    //   3786: dup
    //   3787: invokespecial <init> : ()V
    //   3790: new java/text/SimpleDateFormat
    //   3793: dup
    //   3794: ldc_w 'yyyy-MM-dd HH:mm:ss'
    //   3797: invokespecial <init> : (Ljava/lang/String;)V
    //   3800: ldc_w '2050-01-01 00:00:00'
    //   3803: invokevirtual parse : (Ljava/lang/String;)Ljava/util/Date;
    //   3806: ldc_w '系统提醒'
    //   3809: aload #60
    //   3811: iload #62
    //   3813: aaload
    //   3814: iconst_2
    //   3815: aaload
    //   3816: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   3819: iconst_1
    //   3820: invokestatic sendMessageToUsers2 : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Long;I)V
    //   3823: goto -> 3958
    //   3826: new java/lang/StringBuilder
    //   3829: dup
    //   3830: aload #14
    //   3832: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3835: invokespecial <init> : (Ljava/lang/String;)V
    //   3838: ldc_w '发起的'
    //   3841: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3844: aload_1
    //   3845: ldc_w 'processName'
    //   3848: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3853: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3856: ldc_w '流程已经被'
    //   3859: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3862: aload_1
    //   3863: invokeinterface getSession : ()Ljavax/servlet/http/HttpSession;
    //   3868: ldc_w 'userName'
    //   3871: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   3876: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3879: ldc_w '('
    //   3882: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3885: aload_1
    //   3886: ldc_w 'curActivityName'
    //   3889: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   3894: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3897: ldc_w ')处理完毕！'
    //   3900: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3903: invokevirtual toString : ()Ljava/lang/String;
    //   3906: aload #61
    //   3908: aload #60
    //   3910: iload #62
    //   3912: aaload
    //   3913: iconst_0
    //   3914: aaload
    //   3915: ldc_w 'jsflow'
    //   3918: new java/util/Date
    //   3921: dup
    //   3922: invokespecial <init> : ()V
    //   3925: new java/text/SimpleDateFormat
    //   3928: dup
    //   3929: ldc_w 'yyyy-MM-dd HH:mm:ss'
    //   3932: invokespecial <init> : (Ljava/lang/String;)V
    //   3935: ldc_w '2050-01-01 00:00:00'
    //   3938: invokevirtual parse : (Ljava/lang/String;)Ljava/util/Date;
    //   3941: ldc_w '系统提醒'
    //   3944: aload #60
    //   3946: iload #62
    //   3948: aaload
    //   3949: iconst_2
    //   3950: aaload
    //   3951: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   3954: iconst_1
    //   3955: invokestatic sendMessageToUsers2 : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/Long;I)V
    //   3958: iinc #62, 1
    //   3961: iload #62
    //   3963: aload #60
    //   3965: arraylength
    //   3966: if_icmplt -> 3631
    //   3969: goto -> 3979
    //   3972: astore #61
    //   3974: aload #61
    //   3976: invokevirtual printStackTrace : ()V
    //   3979: new com/js/oa/jsflow/action/WorkFlowOpinionRemind
    //   3982: dup
    //   3983: invokespecial <init> : ()V
    //   3986: astore #57
    //   3988: aload #57
    //   3990: aload_1
    //   3991: invokevirtual userOpinionRemind : (Ljavax/servlet/http/HttpServletRequest;)Z
    //   3994: ifeq -> 4038
    //   3997: ldc_w '同意'
    //   4000: aload #18
    //   4002: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4005: ifne -> 4038
    //   4008: ldc_w '同意。'
    //   4011: aload #18
    //   4013: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4016: ifne -> 4038
    //   4019: aload #57
    //   4021: aload_1
    //   4022: invokevirtual messageRemind : (Ljavax/servlet/http/HttpServletRequest;)V
    //   4025: goto -> 4038
    //   4028: aload_1
    //   4029: ldc 'flowfaild'
    //   4031: ldc '1'
    //   4033: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   4038: aload_1
    //   4039: ldc_w 'subProcTempWorkId'
    //   4042: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   4047: ifnull -> 4085
    //   4050: ldc ''
    //   4052: aload_1
    //   4053: ldc_w 'subProcTempWorkId'
    //   4056: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   4061: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4064: ifne -> 4085
    //   4067: aload_1
    //   4068: ldc_w 'subProcWorkId'
    //   4071: aload_1
    //   4072: ldc_w 'subProcTempWorkId'
    //   4075: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   4080: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   4085: ldc_w 'yn'
    //   4088: invokestatic getCustomerName : ()Ljava/lang/String;
    //   4091: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4094: ifeq -> 4117
    //   4097: new com/js/oa/jsflow/util/YouNengCRMDealWith
    //   4100: dup
    //   4101: invokespecial <init> : ()V
    //   4104: aload #30
    //   4106: aload #23
    //   4108: aload #28
    //   4110: ldc_w '审批中'
    //   4113: invokevirtual visitPage : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   4116: pop
    //   4117: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #2173	-> 0
    //   #2174	-> 8
    //   #2175	-> 16
    //   #2176	-> 25
    //   #2177	-> 40
    //   #2178	-> 44
    //   #2179	-> 48
    //   #2180	-> 53
    //   #2181	-> 66
    //   #2182	-> 87
    //   #2183	-> 112
    //   #2185	-> 127
    //   #2186	-> 140
    //   #2187	-> 161
    //   #2188	-> 186
    //   #2191	-> 201
    //   #2192	-> 205
    //   #2195	-> 225
    //   #2196	-> 234
    //   #2197	-> 262
    //   #2198	-> 267
    //   #2199	-> 284
    //   #2202	-> 294
    //   #2204	-> 296
    //   #2206	-> 307
    //   #2207	-> 318
    //   #2208	-> 340
    //   #2209	-> 354
    //   #2210	-> 365
    //   #2212	-> 372
    //   #2214	-> 396
    //   #2217	-> 403
    //   #2218	-> 414
    //   #2219	-> 425
    //   #2220	-> 436
    //   #2221	-> 447
    //   #2222	-> 458
    //   #2223	-> 469
    //   #2224	-> 473
    //   #2225	-> 477
    //   #2226	-> 488
    //   #2228	-> 495
    //   #2231	-> 506
    //   #2234	-> 517
    //   #2235	-> 528
    //   #2236	-> 539
    //   #2237	-> 550
    //   #2238	-> 561
    //   #2239	-> 572
    //   #2240	-> 583
    //   #2241	-> 594
    //   #2243	-> 604
    //   #2244	-> 618
    //   #2245	-> 628
    //   #2246	-> 639
    //   #2247	-> 653
    //   #2248	-> 667
    //   #2249	-> 681
    //   #2250	-> 692
    //   #2251	-> 703
    //   #2252	-> 714
    //   #2254	-> 725
    //   #2256	-> 736
    //   #2257	-> 745
    //   #2260	-> 756
    //   #2262	-> 766
    //   #2263	-> 775
    //   #2264	-> 793
    //   #2265	-> 803
    //   #2266	-> 810
    //   #2267	-> 828
    //   #2268	-> 838
    //   #2269	-> 848
    //   #2270	-> 858
    //   #2271	-> 868
    //   #2335	-> 881
    //   #2336	-> 892
    //   #2337	-> 917
    //   #2340	-> 926
    //   #2341	-> 937
    //   #2342	-> 947
    //   #2343	-> 951
    //   #2344	-> 957
    //   #2345	-> 971
    //   #2343	-> 996
    //   #2349	-> 1007
    //   #2350	-> 1022
    //   #2355	-> 1032
    //   #2357	-> 1055
    //   #2358	-> 1077
    //   #2359	-> 1095
    //   #2363	-> 1112
    //   #2366	-> 1144
    //   #2367	-> 1148
    //   #2368	-> 1152
    //   #2371	-> 1155
    //   #2372	-> 1166
    //   #2373	-> 1192
    //   #2374	-> 1202
    //   #2376	-> 1216
    //   #2378	-> 1220
    //   #2379	-> 1231
    //   #2390	-> 1234
    //   #2391	-> 1238
    //   #2392	-> 1249
    //   #2393	-> 1261
    //   #2394	-> 1272
    //   #2395	-> 1286
    //   #2397	-> 1293
    //   #2399	-> 1297
    //   #2402	-> 1324
    //   #2403	-> 1335
    //   #2405	-> 1350
    //   #2406	-> 1361
    //   #2407	-> 1387
    //   #2408	-> 1395
    //   #2409	-> 1408
    //   #2411	-> 1443
    //   #2412	-> 1451
    //   #2413	-> 1464
    //   #2416	-> 1493
    //   #2418	-> 1496
    //   #2419	-> 1505
    //   #2420	-> 1527
    //   #2421	-> 1538
    //   #2423	-> 1553
    //   #2424	-> 1564
    //   #2426	-> 1579
    //   #2427	-> 1587
    //   #2428	-> 1599
    //   #2427	-> 1613
    //   #2430	-> 1621
    //   #2431	-> 1627
    //   #2433	-> 1635
    //   #2435	-> 1637
    //   #2436	-> 1645
    //   #2437	-> 1647
    //   #2440	-> 1653
    //   #2443	-> 1656
    //   #2444	-> 1667
    //   #2448	-> 1676
    //   #2450	-> 1704
    //   #2452	-> 1715
    //   #2453	-> 1736
    //   #2454	-> 1747
    //   #2456	-> 1751
    //   #2457	-> 1760
    //   #2458	-> 1772
    //   #2459	-> 1784
    //   #2460	-> 1797
    //   #2461	-> 1810
    //   #2462	-> 1823
    //   #2463	-> 1836
    //   #2464	-> 1849
    //   #2465	-> 1862
    //   #2466	-> 1875
    //   #2467	-> 1888
    //   #2468	-> 1901
    //   #2469	-> 1914
    //   #2470	-> 1927
    //   #2471	-> 1940
    //   #2472	-> 1953
    //   #2473	-> 1966
    //   #2474	-> 1978
    //   #2476	-> 1998
    //   #2477	-> 2018
    //   #2478	-> 2038
    //   #2479	-> 2051
    //   #2480	-> 2064
    //   #2482	-> 2077
    //   #2484	-> 2086
    //   #2485	-> 2114
    //   #2488	-> 2125
    //   #2491	-> 2138
    //   #2493	-> 2149
    //   #2494	-> 2168
    //   #2496	-> 2172
    //   #2497	-> 2176
    //   #2498	-> 2193
    //   #2501	-> 2210
    //   #2504	-> 2238
    //   #2505	-> 2249
    //   #2506	-> 2264
    //   #2507	-> 2276
    //   #2508	-> 2297
    //   #2509	-> 2318
    //   #2507	-> 2344
    //   #2511	-> 2349
    //   #2512	-> 2377
    //   #2512	-> 2384
    //   #2513	-> 2409
    //   #2514	-> 2439
    //   #2515	-> 2475
    //   #2512	-> 2514
    //   #2516	-> 2516
    //   #2517	-> 2533
    //   #2518	-> 2541
    //   #2519	-> 2542
    //   #2521	-> 2550
    //   #2524	-> 2563
    //   #2525	-> 2572
    //   #2527	-> 2582
    //   #2528	-> 2599
    //   #2529	-> 2616
    //   #2530	-> 2628
    //   #2532	-> 2654
    //   #2536	-> 2677
    //   #2537	-> 2681
    //   #2538	-> 2687
    //   #2537	-> 2718
    //   #2540	-> 2729
    //   #2541	-> 2740
    //   #2543	-> 2755
    //   #2544	-> 2769
    //   #2545	-> 2780
    //   #2547	-> 2795
    //   #2548	-> 2802
    //   #2549	-> 2815
    //   #2550	-> 2828
    //   #2551	-> 2841
    //   #2553	-> 2860
    //   #2554	-> 2877
    //   #2555	-> 2894
    //   #2557	-> 2917
    //   #2558	-> 2931
    //   #2559	-> 2944
    //   #2560	-> 2957
    //   #2561	-> 2970
    //   #2563	-> 2986
    //   #2565	-> 2993
    //   #2567	-> 3022
    //   #2568	-> 3031
    //   #2569	-> 3048
    //   #2570	-> 3065
    //   #2572	-> 3094
    //   #2573	-> 3108
    //   #2574	-> 3121
    //   #2575	-> 3134
    //   #2576	-> 3147
    //   #2577	-> 3163
    //   #2581	-> 3170
    //   #2582	-> 3199
    //   #2583	-> 3209
    //   #2584	-> 3242
    //   #2582	-> 3248
    //   #2585	-> 3253
    //   #2587	-> 3267
    //   #2588	-> 3287
    //   #2589	-> 3315
    //   #2590	-> 3350
    //   #2588	-> 3356
    //   #2590	-> 3359
    //   #2591	-> 3377
    //   #2588	-> 3406
    //   #2592	-> 3412
    //   #2593	-> 3414
    //   #2597	-> 3419
    //   #2598	-> 3436
    //   #2599	-> 3465
    //   #2600	-> 3475
    //   #2601	-> 3508
    //   #2599	-> 3519
    //   #2602	-> 3524
    //   #2604	-> 3538
    //   #2606	-> 3558
    //   #2607	-> 3564
    //   #2608	-> 3573
    //   #2609	-> 3593
    //   #2607	-> 3603
    //   #2606	-> 3614
    //   #2613	-> 3625
    //   #2614	-> 3631
    //   #2616	-> 3647
    //   #2617	-> 3676
    //   #2618	-> 3691
    //   #2619	-> 3727
    //   #2620	-> 3762
    //   #2618	-> 3768
    //   #2620	-> 3771
    //   #2621	-> 3790
    //   #2618	-> 3820
    //   #2624	-> 3826
    //   #2625	-> 3862
    //   #2626	-> 3897
    //   #2624	-> 3903
    //   #2626	-> 3906
    //   #2627	-> 3925
    //   #2624	-> 3955
    //   #2613	-> 3958
    //   #2632	-> 3972
    //   #2633	-> 3974
    //   #2642	-> 3979
    //   #2643	-> 3988
    //   #2644	-> 4019
    //   #2649	-> 4028
    //   #2653	-> 4038
    //   #2654	-> 4067
    //   #2656	-> 4085
    //   #2657	-> 4097
    //   #2660	-> 4117
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	4118	0	this	Lcom/js/oa/jsflow/action/WorkflowButtonAction;
    //   0	4118	1	httpServletRequest	Ljavax/servlet/http/HttpServletRequest;
    //   8	4110	2	httpSession	Ljavax/servlet/http/HttpSession;
    //   16	4102	3	workFlowCommonBD	Lcom/js/oa/jsflow/service/WorkFlowCommonBD;
    //   25	4093	4	workFlowButtonBD	Lcom/js/oa/jsflow/service/WorkFlowButtonBD;
    //   40	4078	5	formClassNameMethod	Ljava/util/Map;
    //   44	4074	6	formClassName	Ljava/lang/String;
    //   48	4070	7	formClassMethod	Ljava/lang/String;
    //   205	3913	8	remindFieldValue	Ljava/lang/String;
    //   234	60	9	formReflection	Lcom/js/oa/jsflow/util/FormReflection;
    //   262	32	10	obj	Ljava/lang/Object;
    //   318	3799	9	remindField	Ljava/lang/String;
    //   414	3703	10	isStandForWork	Ljava/lang/String;
    //   425	3692	11	standForUserId	Ljava/lang/String;
    //   436	3681	12	standForUserName	Ljava/lang/String;
    //   447	3670	13	submitPersonId	Ljava/lang/String;
    //   458	3659	14	submitPerson	Ljava/lang/String;
    //   469	3648	15	mainPressType	Ljava/lang/String;
    //   473	3644	16	mainDeadLineTime	Ljava/lang/String;
    //   477	3640	17	mainPressMotionTime	Ljava/lang/String;
    //   488	3629	18	comment	Ljava/lang/String;
    //   506	3611	19	signComment	Ljava/lang/String;
    //   517	3600	20	commentOrg	Ljava/lang/String;
    //   528	3589	21	mainAllowStandFor	Ljava/lang/String;
    //   539	3578	22	mainAllowCancel	Ljava/lang/String;
    //   550	3567	23	curActivityId	Ljava/lang/String;
    //   561	3556	24	curActivityName	Ljava/lang/String;
    //   572	3545	25	mainNextActivityId	Ljava/lang/String;
    //   583	3534	26	mainNextActivityName	Ljava/lang/String;
    //   594	3523	27	processName	Ljava/lang/String;
    //   604	3513	28	tableId	Ljava/lang/String;
    //   618	3499	29	tableName	Ljava/lang/String;
    //   628	3489	30	recordId	Ljava/lang/String;
    //   639	3478	31	workId	Ljava/lang/String;
    //   653	3464	32	userId	Ljava/lang/String;
    //   667	3450	33	orgId	Ljava/lang/String;
    //   681	3436	34	orgIdString	Ljava/lang/String;
    //   692	3425	35	stepCount	Ljava/lang/String;
    //   703	3414	36	activityClass	Ljava/lang/String;
    //   736	3381	37	relprojectId	Ljava/lang/String;
    //   745	3372	38	workFlowBD	Lcom/js/oa/jsflow/service/WorkFlowBD;
    //   756	3361	39	mainTransactUser	[Ljava/lang/String;
    //   775	106	40	newUtil	Lcom/js/oa/jsflow/util/NewWorkflowUtil;
    //   793	88	41	activityVO	Lcom/js/oa/jsflow/vo/ActivityVO;
    //   892	140	40	operId	Ljava/lang/String;
    //   947	60	41	arr	[Ljava/lang/String;
    //   954	53	42	i	I
    //   1166	68	40	changeDeadLineTime	Ljava/lang/String;
    //   1238	2790	40	mainNeedPassRound	Ljava/lang/String;
    //   1249	2779	41	mainPassRoundUser	[Ljava/lang/String;
    //   1286	390	42	mainPassRoundUserType	I
    //   1335	321	43	operId	Ljava/lang/String;
    //   1361	292	44	con	Lcom/js/util/util/ConversionString;
    //   1387	266	45	userIdStr	Ljava/lang/String;
    //   1496	157	46	dbopt	Lcom/js/oa/userdb/util/DbOpt;
    //   1637	16	47	e	Ljava/lang/Exception;
    //   1667	9	44	operProcOrg	Ljava/lang/String;
    //   1704	2324	42	subProcWorkId	Ljava/lang/String;
    //   1715	2313	43	modiCommentId	Ljava/lang/String;
    //   1736	2292	44	delayTime	Ljava/lang/String;
    //   1760	2268	45	dealwithMap	Ljava/util/Map;
    //   2086	1942	46	wfcBD	Lcom/js/oa/jsflow/service/WorkFlowCommonBD;
    //   2114	1914	47	subProcType	Ljava/lang/String;
    //   2125	1903	48	toMainFile	Ljava/lang/String;
    //   2138	1890	49	curTransactType	Ljava/lang/String;
    //   2149	1879	50	nextTransactType	Ljava/lang/String;
    //   2176	1852	51	docTitle	Ljava/lang/String;
    //   2238	1790	52	emergence	Ljava/lang/String;
    //   2249	1779	53	dealTips	Ljava/lang/String;
    //   2276	73	54	df	Ljava/text/SimpleDateFormat;
    //   2377	1651	54	sendSMS	Ljava/lang/String;
    //   2516	1512	55	para	[Ljava/lang/String;
    //   2533	1495	56	result	Ljava/lang/Integer;
    //   2572	1407	57	workLogVO	Lcom/js/oa/jsflow/vo/WorkLogVO;
    //   2681	179	58	tmpUser	Ljava/lang/String;
    //   2684	45	59	i	I
    //   3253	166	58	sql	Ljava/lang/String;
    //   3267	152	59	initWorkId	[[Ljava/lang/String;
    //   3287	125	60	url	Ljava/lang/String;
    //   3414	5	60	e	Ljava/lang/Exception;
    //   3436	543	58	userid	Ljava/lang/Integer;
    //   3524	455	59	sql	Ljava/lang/String;
    //   3538	441	60	initWorkId	[[Ljava/lang/String;
    //   3558	414	61	url	Ljava/lang/String;
    //   3561	64	62	i	I
    //   3570	44	63	j	I
    //   3628	341	62	k	I
    //   3974	5	61	e	Ljava/lang/Exception;
    //   3988	40	57	wor	Lcom/js/oa/jsflow/action/WorkFlowOpinionRemind;
    // Exception table:
    //   from	to	target	type
    //   225	291	294	java/lang/Exception
    //   1496	1632	1635	java/lang/Exception
    //   1637	1642	1645	java/sql/SQLException
    //   3267	3409	3412	java/lang/Exception
    //   3538	3969	3972	java/lang/Exception
  }
  
  private void branchSend(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
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
    if (!"error".equals(remindFieldValue)) {
      String remindField = httpServletRequest.getParameter("remindField");
      if (remindField.startsWith("$") && remindField.endsWith("$")) {
        remindFieldValue = httpServletRequest.getParameter("motif");
      } else if (remindField.indexOf("S") < 0) {
        remindFieldValue = "";
      } else {
        remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
      } 
      remindFieldValue = CharacterTool.deleteEnter(remindFieldValue);
      String isStandForWork = httpServletRequest.getParameter("isStandForWork");
      String standForUserId = httpServletRequest.getParameter("standForUserId");
      String standForUserName = httpServletRequest.getParameter("standForUserName");
      String submitPersonId = httpServletRequest.getParameter("submitPersonId");
      String mainPressType = httpServletRequest.getParameter("mainPressType");
      String mainDeadLineTime = "-1";
      String mainPressMotionTime = "-1";
      String comment = httpServletRequest.getParameter("include_comment");
      comment = CharacterTool.escapeHTMLTags2(comment);
      String signComment = httpServletRequest.getParameter("include_signcomment");
      String commentOrg = httpServletRequest.getParameter("commentOrg");
      String mainAllowStandFor = httpServletRequest.getParameter("mainAllowStandFor");
      String mainAllowCancel = httpServletRequest.getParameter("mainAllowCancel");
      String curActivityId = httpServletRequest.getParameter("curActivityId");
      String curActivityName = httpServletRequest.getParameter("curActivityName");
      String mainNextActivityId = httpServletRequest.getParameter("mainNextActivityId");
      String mainNextActivityName = httpServletRequest.getParameter("mainNextActivityName");
      String processName = httpServletRequest.getParameter("processName");
      String tableId = httpServletRequest.getParameter("tableId");
      String tableName = (new CustomFormBD()).getTable(tableId);
      String recordId = httpServletRequest.getParameter("recordId");
      String workId = httpServletRequest.getParameter("workId");
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      String stepCount = httpServletRequest.getParameter("stepCount");
      String activityClass = httpServletRequest.getParameter("activityClass");
      mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
      mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
      String relprojectId = httpServletRequest.getParameter("relproject");
      WorkFlowBD workFlowBD = new WorkFlowBD();
      String[] mainTransactUser = { "" };
      if ("3".equals(activityClass)) {
        NewWorkflowUtil newUtil = new NewWorkflowUtil();
        ActivityVO activityVO = newUtil.getPreActivity(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
        mainNextActivityId = String.valueOf(activityVO.getId());
        mainNextActivityName = activityVO.getName();
        mainTransactUser = newUtil.getPreActivityUser(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
        mainPressType = String.valueOf(activityVO.getPressType());
        mainDeadLineTime = String.valueOf(activityVO.getDeadlineTime());
        mainPressMotionTime = String.valueOf(activityVO.getPressMotionTime());
        mainAllowStandFor = String.valueOf(activityVO.getAllowStandFor());
        mainAllowCancel = String.valueOf(activityVO.getAllowcancel());
      } else {
        String operId = httpServletRequest.getParameter("operId");
        if (operId == null || "null".equals(operId) || operId.length() < 2) {
          mainTransactUser = new String[0];
        } else {
          operId = operId.substring(1, operId.length() - 1);
          mainTransactUser = operId.split("\\$\\$");
        } 
      } 
      if (!"".equals(httpServletRequest.getParameter("branchActInfo"))) {
        String changeDeadLineTime;
        switch (Integer.parseInt(mainPressType)) {
          case 0:
            mainDeadLineTime = "-1";
            mainPressMotionTime = "-1";
            break;
          case 1:
            changeDeadLineTime = httpServletRequest.getParameter("changeDeadLineTime");
            if (changeDeadLineTime == null || "null".equals(changeDeadLineTime) || "-1".equals(changeDeadLineTime) || 
              "".equals(changeDeadLineTime)) {
              mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
            } else {
              mainDeadLineTime = changeDeadLineTime;
            } 
            mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
            break;
        } 
        String mainNeedPassRound = "";
        String[] mainPassRoundUser = { "" };
        String subProcWorkId = (httpServletRequest.getParameter("subProcWorkId") == null) ? "0" : httpServletRequest.getParameter("subProcWorkId");
        String modiCommentId = httpServletRequest.getParameter("modiCommentId");
        Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
        dealwithMap.put("tableId", tableId);
        dealwithMap.put("recordId", recordId);
        dealwithMap.put("curActivityName", curActivityName);
        dealwithMap.put("curActivityId", curActivityId);
        dealwithMap.put("userId", userId);
        dealwithMap.put("orgIdString", orgIdString);
        dealwithMap.put("comment", comment);
        dealwithMap.put("signcomment", signComment);
        dealwithMap.put("commentOrg", commentOrg);
        dealwithMap.put("nextActivityName", mainNextActivityName);
        dealwithMap.put("nextActivityId", mainNextActivityId);
        dealwithMap.put("stepCount", stepCount);
        dealwithMap.put("isStandForWork", isStandForWork);
        dealwithMap.put("standForUserId", standForUserId);
        dealwithMap.put("activityClass", activityClass);
        dealwithMap.put("subProcWorkId", subProcWorkId);
        if (httpServletRequest.getParameter("include_commField") != null)
          dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
        dealwithMap.put("userScope", httpServletRequest.getParameter("userScope"));
        dealwithMap.put("scopeId", httpServletRequest.getParameter("scopeId"));
        dealwithMap.put("modiCommentId", modiCommentId);
        dealwithMap.put("relproject", relprojectId);
        dealwithMap.put("branchActInfo", httpServletRequest.getParameter("branchActInfo"));
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
        String dealTips = httpServletRequest.getParameter("dealTips");
        if (dealTips != null && !dealTips.equals("")) {
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
          dealTips = "来自上一环节:" + mainNextActivityName + ";  办理人：" + 
            httpSession.getAttribute("userName").toString() + "; " + 
            df.format(new Date()) + " " + dealTips;
        } 
        String branchActInfo = dealwithMap.get("branchActInfo").toString();
        String branchUserId = "", branchActivityNames = "";
        String[] branchActArray = branchActInfo.split(";;;;");
        for (int i = 0; i < branchActArray.length; i++) {
          if (i == 0) {
            branchActivityNames = String.valueOf(branchActivityNames) + branchActArray[i].split(";;")[1];
            branchUserId = String.valueOf(branchUserId) + branchActArray[i].split(";;")[2];
          } else {
            if (i < 3) {
              branchActivityNames = String.valueOf(branchActivityNames) + "," + branchActArray[i].split(";;")[1];
            } else if (i == 5) {
              branchActivityNames = String.valueOf(branchActivityNames) + "等";
            } 
            branchUserId = String.valueOf(branchUserId) + "," + branchActArray[i].split(";;")[2];
          } 
        } 
        String branchUserName = StaticParam.getNamesByIds(branchUserId);
        dealwithMap.put("branchActivityNames", branchActivityNames);
        String sendSMS = (httpServletRequest.getParameter("sendSMS") == null) ? "1" : httpServletRequest.getParameter("sendSMS");
        String[] para = { 
            mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
            curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
            toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
            activityClass, subProcType, docTitle, emergence, 
            nextTransactType, dealTips, sendSMS };
        Integer result = wfcBD.transWorkflowBranch(dealwithMap, para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
        if (result.intValue() < 0) {
          httpServletRequest.setAttribute("flowfaild", "1");
        } else {
          WorkLogVO workLogVO = new WorkLogVO();
          if ("3".equals(activityClass)) {
            workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
            workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
            if ("shandongjiguanshiwuju".equals(SystemCommon.getCustomerName())) {
              workLogVO.setSendAction("返回" + mainNextActivityName);
            } else {
              workLogVO.setSendAction("送" + mainNextActivityName);
            } 
            String tmpUser = "0";
            for (int j = 0; j < mainTransactUser.length; j++)
              tmpUser = String.valueOf(tmpUser) + mainTransactUser[j] + ","; 
            if (tmpUser.endsWith(","))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            tmpUser = (new UserBD()).getUserNameById(tmpUser);
            if (tmpUser.endsWith(","))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            workLogVO.setReceiveUserName(tmpUser);
            workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
            workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
            workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
            workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
          } else {
            workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
            workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
            workLogVO.setSendAction("送" + dealwithMap.get("branchActivityNames").toString());
            workLogVO.setReceiveUserName(branchUserName);
            workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
            workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
            workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
            workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
          } 
          workFlowButtonBD.setDealWithLog(workLogVO);
          if (httpServletRequest.getParameter("sendMsgToInitiator") != null && "1".equals(httpServletRequest.getParameter("sendMsgToInitiator"))) {
            String sql = "SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID=" + 
              httpServletRequest.getParameter("processId") + " AND a.worktable_id=" + tableId + " AND a.workrecord_id=" + recordId + 
              " AND workstepcount=0";
            String[][] initWorkId = (new DataSourceBase()).queryArrayBySql(sql);
            try {
              String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId;
              RemindUtil.sendMessageToUsers2("您发起的" + httpServletRequest.getParameter("processName") + "流程已经被" + 
                  httpServletRequest.getSession().getAttribute("userName") + "(" + httpServletRequest.getParameter("curActivityName") + 
                  ")处理完毕！", url, initWorkId[0][0], "jsflow", new Date(), (
                  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(initWorkId[0][2]), 1);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } 
      } else {
        httpServletRequest.setAttribute("flowfaild", "1");
      } 
    } 
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
    String commentOrg = httpServletRequest.getParameter("commentOrg");
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
    dealwithMap.put("commentOrg", commentOrg);
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
      WorkFlowOpinionRemind wor = new WorkFlowOpinionRemind();
      if (wor.userOpinionRemind(httpServletRequest) && !"同意".equals(comment) && !"同意。".equals(comment))
        wor.messageRemind(httpServletRequest); 
      Integer userid = Integer.valueOf(httpSession.getAttribute("userId").toString());
      if (httpServletRequest.getParameter("sendMsgToDealDone") != null && "1".equals(httpServletRequest.getParameter("sendMsgToDealDone"))) {
        String sql = "SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID=" + 
          httpServletRequest.getParameter("processId") + " AND a.worktable_id=" + tableId + " AND a.workrecord_id=" + recordId + 
          " AND a.workstatus=101 and a.wf_curemployee_id <> " + userid;
        String[][] initWorkId = (new DataSourceBase()).queryArrayBySql(sql);
        try {
          String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId;
          for (int i = 0; i < initWorkId.length; i++) {
            for (int j = i + 1; j < initWorkId.length; j++) {
              if (initWorkId[i][0].equals(initWorkId[j][0]))
                initWorkId[j][0] = "*"; 
            } 
          } 
          for (int k = 0; k < initWorkId.length; k++) {
            if (!"*".equals(initWorkId[k][0]))
              RemindUtil.sendMessageToUsers2(String.valueOf(submitPerson) + "发起的" + httpServletRequest.getParameter("processName") + "流程已经被" + 
                  httpServletRequest.getSession().getAttribute("userName") + "(" + httpServletRequest.getParameter("curActivityName") + 
                  ")处理完毕！", url, initWorkId[k][0], "jsflow", new Date(), (
                  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(initWorkId[k][2]), 1); 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
    if ("yn".equals(SystemCommon.getCustomerName()))
      (new YouNengCRMDealWith()).visitPage(recordId, curActivityId, tableId, "已审批"); 
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
    String relProjectId = request.getParameter("relproject");
    return new String[] { processId, tableId, recordId, resubmit, relProjectId };
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
      String relProjectId = request.getParameter("relproject");
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      String orgName = session.getAttribute("orgName").toString();
      workFlowButtonBD.saveToDraft(processId, tableId, recordId, userId, String.valueOf(remindFieldValue) + processName, processName, relProjectId);
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
      int count = 0;
      if (submitSuccess != 0L && request.getParameter("resubmitWorkId") != null && request.getParameter("resubmitWorkId").trim().length() > 0 && !request.getParameter("resubmitWorkId").toUpperCase().equals("NULL")) {
        List<String> sqlList = new ArrayList();
        String updateSql = "update JSF_WORK set workDelete = 1 where wf_work_id = " + request.getParameter("resubmitWorkId");
        if ("1".equals(SystemCommon.getReSubmitDel()) && request.getParameter("recordId") != null && request.getParameter("recordId").trim().length() > 0 && !request.getParameter("recordId").toUpperCase().equals("NULL"))
          count = getcount(request.getParameter("recordId")); 
        sqlList.add(updateSql);
        if (count < 1)
          workFlowBD.updateTable(sqlList); 
      } 
      if (submitSuccess != 0L) {
        WorkLogVO workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(session.getAttribute("userId").toString());
        workLogVO.setSendUserName(session.getAttribute("userName").toString());
        workLogVO.setSendAction("送" + ((request.getParameter("activityName") == null || "".equals(request.getParameter("activityName")) || "null".equals(request.getParameter("activityName"))) ? "" : request.getParameter("activityName")));
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
          workLogVO.setSendAction("送" + ((request.getParameter("activityName") == null || "".equals(request.getParameter("activityName")) || "null".equals(request.getParameter("activityName"))) ? "" : request.getParameter("activityName")) + "阅件");
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
    workLogVO.setSendAction(String.valueOf(currentStep) + "审批");
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
  
  private void backProcessRemind(String tableId, String recordId, String workId, String userId, String userName) {
    String sql = "select distinct t.wf_curemployee_id,t.wf_work_id from jsf_work t where t.worktable_id=" + 
      tableId + " and t.workrecord_id=" + recordId + " and t.wf_curemployee_id <> " + userId + " and t.wf_curemployee_id <> t.wf_submitemployee_id";
    String sql1 = "select t.worksubmitperson,t.workfiletype from jsf_work t where t.wf_work_id=" + workId;
    DbOpt dbopt = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String[][] data = (String[][])null;
    String submitperson = "";
    String title = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql1);
      if (rs.next()) {
        submitperson = rs.getString(1);
        title = rs.getString(2);
      } 
      dbopt = new DbOpt();
      data = dbopt.executeQueryToStrArr2(sql, 2);
      if (data != null && data.length > 0) {
        for (int i = 0; i < data.length; i++) {
          for (int j = i + 1; j < data.length; j++) {
            if (data[i][0].equals(data[j][0]))
              data[j][1] = "#"; 
          } 
        } 
        String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId;
        for (int k = 0; k < data.length; k++) {
          if (!"#".equals(data[k][1]))
            RemindUtil.sendMessageToUsers2(String.valueOf(submitperson) + "发起的" + title + "流程已经被" + 
                userName + "退回！", url, data[k][0], "jsflow", new Date(), (
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(data[k][1]), 1, 1, 1); 
        } 
      } 
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } finally {
      try {
        if (dbopt != null)
          dbopt.close(); 
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
    } 
  }
  
  private void updateToDraft(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
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
    String comment = httpServletRequest.getParameter("include_comment");
    comment = CharacterTool.escapeHTMLTags2(comment);
    String include_commField = (httpServletRequest.getParameter("include_commField") == null) ? "" : httpServletRequest.getParameter("include_commField");
    String signComment = httpServletRequest.getParameter("include_signcomment");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String userId = httpSession.getAttribute("userId").toString();
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("workId", workId);
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("signcomment", signComment);
    dealwithMap.put("commentField", include_commField);
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    if (comment != null && !"".equals(comment) && !"null".equals(comment))
      wfcBD.updateToDraftButton(dealwithMap); 
  }
}
