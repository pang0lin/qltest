package com.js.oa.jsflow.action;

import com.js.message.RealTimeUtil;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.util.NewWorkflowUtil;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

public class WorkflowCommonAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String flag = httpServletRequest.getParameter("flag");
    if ("save".equals(flag)) {
      save(httpServletRequest);
      if (httpServletRequest.getParameter("continue") != null) {
        ActionForward forward = new ActionForward();
        String tmpPath = httpServletRequest.getParameter("winHref");
        String[] tmp2 = tmpPath.split("jsoa");
        forward.setPath(tmp2[1]);
        return forward;
      } 
    } else if ("update".equals(flag)) {
      update(httpServletRequest);
    } else if ("complete".equals(flag)) {
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
    } else if ("back".equals(flag)) {
      back(httpServletRequest);
    } else if ("trans".equals(flag)) {
      trans(httpServletRequest);
    } else if ("random".equals(flag)) {
      random(httpServletRequest);
    } else if ("passround".equals(flag)) {
      passround(httpServletRequest);
    } else if ("batchRead".equals(flag)) {
      batchRead(httpServletRequest);
    } 
    return actionMapping.findForward("success");
  }
  
  private void passround(HttpServletRequest httpServletRequest) {
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("comment", httpServletRequest.getParameter("include_comment"));
    if (httpServletRequest.getParameter("include_commField") != null)
      dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
    dealwithMap.put("userId", httpServletRequest.getSession(true).getAttribute("userId"));
    dealwithMap.put("workId", httpServletRequest.getParameter("workId"));
    dealwithMap.put("activityName", httpServletRequest.getParameter("curActivityName"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    httpServletRequest.setAttribute("passround", wfcBD.insertPassroundDealWith(dealwithMap));
  }
  
  private void save(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
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
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.indexOf("S") < 0 || recordId == null || recordId.trim().length() < 1) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, recordId, httpServletRequest.getParameter("tableId"));
    } 
    if (recordId != null && Long.parseLong(recordId) > 0L) {
      String processType = (httpServletRequest.getParameter("processType") == null || "".equals(httpServletRequest.getParameter("processType"))) ? "1" : httpServletRequest.getParameter("processType");
      String processName = httpServletRequest.getParameter("processName");
      String processId = httpServletRequest.getParameter("processId");
      String tableId = httpServletRequest.getParameter("tableId");
      String moduleId = httpServletRequest.getParameter("moduleId");
      String userName = httpSession.getAttribute("userName").toString();
      String userId = httpSession.getAttribute("userId").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
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
      workVO.setTransactType("0");
      String cancelHref = httpServletRequest.getParameter("cancelHref");
      cancelHref = cancelHref.replaceAll("tableIdValue", tableId);
      cancelHref = cancelHref.replaceAll("processNameValue", processName);
      cancelHref = cancelHref.replaceAll("recordIdValue", recordId);
      cancelHref = cancelHref.replaceAll("processIdValue", processId);
      cancelHref = cancelHref.replaceAll("remindValueValue", remindFieldValue.toString());
      cancelHref = cancelHref.replaceAll("fileTitleValue", httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")));
      workVO.setCreatorCancelLink(cancelHref);
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
        String operProcUser, operProcOrg, operId;
        workVO.setActivity(new Long(httpServletRequest.getParameter("activityId")));
        workVO.setCurStep(httpServletRequest.getParameter("activityName"));
        workVO.setAllowStandFor(Integer.parseInt(httpServletRequest.getParameter("allowStandFor")));
        workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
        workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
        workVO.setPressTime(httpServletRequest.getParameter("pressTime"));
        workVO.setUserType(Integer.parseInt(httpServletRequest.getParameter("userType")));
        switch (Integer.parseInt(httpServletRequest.getParameter("userType"))) {
          case 0:
            toUser = httpServletRequest.getParameterValues("candidateUser");
            break;
          case 1:
            operProcUser = httpServletRequest.getParameter("operProcUser");
            if (operProcUser != null && !operProcUser.equals("")) {
              ConversionString con = new ConversionString(operProcUser);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
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
          case 2:
            toUser = httpServletRequest.getParameterValues("candidateUser");
            break;
          case 3:
            toUser = httpServletRequest.getParameterValues("allUser");
            break;
          case 5:
            toUser[0] = userId;
            break;
          case 6:
            toUser = workFlowBD.getRoleUser(httpServletRequest.getParameter("partRole"), userId);
            break;
          case 10:
            operProcOrg = httpServletRequest.getParameter("operProcOrg");
            toUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
            break;
          case 100:
            operId = httpServletRequest.getParameter("operId");
            if (operId != null && !operId.equals("")) {
              ConversionString con = new ConversionString(operId);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
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
        } 
        workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
        int pressType = Integer.parseInt(httpServletRequest.getParameter("pressType"));
        switch (pressType) {
          case 0:
            workVO.setDeadLine("-1");
            workVO.setPressTime("-1");
            break;
          case 1:
            workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
            workVO.setPressTime(httpServletRequest.getParameter("pressMotionTime"));
            break;
        } 
      } 
      String[] passUser = { "" };
      if (httpServletRequest.getParameter("needPassRound") != null) {
        String passRoundUser, mainPassRoundOrg;
        workVO.setNeedPassRound(true);
        int passRoundUserType = Integer.parseInt(httpServletRequest.getParameter("passRoundUserType"));
        workVO.setPassRoundUserType(passRoundUserType);
        switch (passRoundUserType) {
          case 0:
            passUser = httpServletRequest.getParameterValues("passRoundCandUser");
            break;
          case 1:
            passRoundUser = httpServletRequest.getParameter("passRoundUser");
            if (passRoundUser != null && !passRoundUser.equals("")) {
              ConversionString con = new ConversionString(passRoundUser);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
              DbOpt dbopt = null;
              try {
                dbopt = new DbOpt();
                userIdStr = userIdStr.replace('$', ',');
                userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
                if (userIdStr.startsWith(","))
                  userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
                if (userIdStr.endsWith(","))
                  userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
                if (userIdStr.length() > 0) {
                  passUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
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
          case 2:
            passUser = httpServletRequest.getParameterValues("passRoundCandUser");
            break;
          case 3:
            passUser = httpServletRequest.getParameterValues("passRoundAllUser");
            break;
          case 5:
            passUser[0] = userId;
            break;
          case 6:
            passUser = workFlowBD.getRoleUser(httpServletRequest.getParameter("passRole"), userId);
            break;
          case 10:
            mainPassRoundOrg = httpServletRequest.getParameter("mainPassRoundOrg");
            if (mainPassRoundOrg != null && !"".equals(mainPassRoundOrg))
              passUser = workFlowBD.getLeaderListByOrgId(mainPassRoundOrg); 
            break;
        } 
        workVO.setPassRoundUser(passUser);
      } else {
        workVO.setNeedPassRound(false);
      } 
      if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
        workVO.setDocTitle(httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName"))); 
      workVO.setDomainId(httpSession.getAttribute("domainId").toString());
      workFlowBD.insertCurFieldStr(processId, tableId, (new StringBuilder(String.valueOf(recordId))).toString(), httpServletRequest.getParameter("curFieldStr"));
      String[] para = new String[0];
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess != 0L && httpServletRequest.getParameter("resubmitWorkId") != null && httpServletRequest.getParameter("resubmitWorkId").trim().length() > 0 && !httpServletRequest.getParameter("resubmitWorkId").toUpperCase().equals("NULL")) {
        List<String> sqlList = new ArrayList();
        sqlList.add("update JSF_WORK set workDelete = 1 where wf_work_id = " + httpServletRequest.getParameter("resubmitWorkId"));
        workFlowBD.updateTable(sqlList);
      } 
      sendMsg(toUser, httpServletRequest);
      sendMsg(passUser, httpServletRequest);
      StringBuffer allUserIdBuffer = new StringBuffer();
      int i;
      for (i = 0; i < toUser.length; i++) {
        if (!"".equals(toUser[i]) && allUserIdBuffer.indexOf(toUser[i]) < 0)
          allUserIdBuffer.append(toUser[i]).append(","); 
      } 
      if (passUser != null)
        for (i = 0; i < passUser.length; i++) {
          if (!"".equals(passUser[i]) && allUserIdBuffer.indexOf(passUser[i]) < 0)
            allUserIdBuffer.append(passUser[i]).append(","); 
        }  
      if (allUserIdBuffer.length() > 1) {
        allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer.length() - 1);
      } else {
        allUserIdBuffer.append("-1");
      } 
      DataSource ds = null;
      try {
        ds = (new DataSourceBase()).getDataSource();
        Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select useraccounts from org_employee where emp_id in (" + allUserIdBuffer.toString() + ")");
        allUserIdBuffer = new StringBuffer();
        while (rs.next())
          allUserIdBuffer.append(rs.getString(1)).append(","); 
        rs.next();
        stmt.close();
        conn.close();
        if (allUserIdBuffer.length() > 1)
          allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer.length() - 1); 
        sendRTXNotify(allUserIdBuffer.toString(), "新待批文件提醒\n[" + workVO.getSubmitPerson() + "] " + workVO.getSubmitPerson() + "的" + workVO.getFileType() + "等待你的办理！\n(" + String.valueOf(3) + "-" + String.valueOf((new Date()).getDate()) + ")");
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      if (httpServletRequest.getParameter("subProc") != null)
        httpServletRequest.setAttribute("subProcWorkId", (new StringBuilder(String.valueOf(submitSuccess))).toString()); 
    } 
  }
  
  private void update(HttpServletRequest httpServletRequest) {
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
    if (remindField.indexOf("S") < 0) {
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
    String mainNextActivityId = httpServletRequest.getParameter("mainNextActivityId");
    String mainNextActivityName = httpServletRequest.getParameter("mainNextActivityName");
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
      String mainOperProcUser, mainUserField, operProcOrg, operId;
      int mainUserType = Integer.parseInt(httpServletRequest.getParameter(
            "mainUserType"));
      switch (mainUserType) {
        case 0:
          mainTransactUser = httpServletRequest.getParameterValues(
              "mainCandidateUser");
          break;
        case 1:
          mainOperProcUser = httpServletRequest.getParameter(
              "mainOperProcUser");
          if (mainOperProcUser != null && !mainOperProcUser.equals("")) {
            ConversionString con = new ConversionString(
                mainOperProcUser);
            String userIdStr = String.valueOf(con.getUserIdString()) + ",";
            userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + 
              ",";
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
                mainTransactUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
              } else {
                mainTransactUser = new String[0];
              } 
              dbopt.close();
            } catch (Exception e) {
              try {
                dbopt.close();
              } catch (SQLException sQLException) {}
              mainTransactUser = new String[0];
            } 
          } 
          break;
        case 2:
          mainTransactUser = httpServletRequest.getParameterValues(
              "mainCandidateUser");
          break;
        case 3:
          mainTransactUser = httpServletRequest.getParameterValues(
              "mainAllUser");
          break;
        case 4:
          mainUserField = httpServletRequest.getParameter(
              "mainUserField");
          if (mainUserField != null && !mainUserField.equals("")) {
            mainUserField = mainUserField.substring(1, 
                mainUserField.length() - 1);
            if (mainUserField.indexOf("$$") >= 0) {
              mainTransactUser = mainUserField.split("\\$\\$");
              break;
            } 
            mainTransactUser[0] = mainUserField;
          } 
          break;
        case 5:
          mainTransactUser[0] = submitPersonId;
          break;
        case 6:
          mainTransactUser = workFlowBD.getRoleUser(httpServletRequest
              .getParameter("mainPartRole"), submitPersonId);
          break;
        case 10:
          operProcOrg = httpServletRequest.getParameter("operProcOrg");
          mainTransactUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
          break;
        case 100:
          operId = httpServletRequest.getParameter("operId");
          if (operId != null && !operId.equals("")) {
            ConversionString con = new ConversionString(operId);
            String userIdStr = String.valueOf(con.getUserIdString()) + ",";
            userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + 
              ",";
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
                mainTransactUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
              } else {
                mainTransactUser = new String[0];
              } 
              dbopt.close();
            } catch (Exception e) {
              try {
                dbopt.close();
              } catch (SQLException sQLException) {}
              mainTransactUser = new String[0];
            } 
          } 
          break;
      } 
    } 
    switch (Integer.parseInt(mainPressType)) {
      case 0:
        mainDeadLineTime = "-1";
        mainPressMotionTime = "-1";
        break;
    } 
    String mainNeedPassRound = "";
    String[] mainPassRoundUser = { "" };
    if (httpServletRequest.getParameter("mainNeedPassRound") != null) {
      String mainPassRoundUserStr, mainPassRoundUserField, mainPassRoundOrg;
      mainNeedPassRound = httpServletRequest.getParameter("mainNeedPassRound");
      switch (Integer.parseInt(httpServletRequest.getParameter("mainPassRoundUserType"))) {
        case 0:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 1:
          mainPassRoundUserStr = httpServletRequest.getParameter("mainPassRoundUser");
          if (mainPassRoundUserStr != null && !mainPassRoundUserStr.equals("")) {
            ConversionString con = new ConversionString(mainPassRoundUserStr);
            String userIdStr = String.valueOf(con.getUserIdString()) + ",";
            userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
            userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
            DbOpt dbopt = null;
            try {
              dbopt = new DbOpt();
              userIdStr = userIdStr.replace('$', ',');
              userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
              if (userIdStr.startsWith(","))
                userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
              if (userIdStr.endsWith(","))
                userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
              if (userIdStr.length() > 0) {
                mainPassRoundUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + 
                    userIdStr + ")");
              } else {
                mainPassRoundUser = new String[0];
              } 
              dbopt.close();
            } catch (Exception e) {
              try {
                dbopt.close();
              } catch (SQLException sQLException) {}
              mainPassRoundUser = new String[0];
            } 
          } 
          break;
        case 2:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 3:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundAllUser");
          break;
        case 4:
          mainPassRoundUserField = httpServletRequest.getParameter("mainPassRoundUserField");
          if (mainPassRoundUserField != null && !mainPassRoundUserField.equals("")) {
            mainPassRoundUserField = mainPassRoundUserField.substring(1, mainPassRoundUserField.length() - 1);
            if (mainPassRoundUserField.indexOf("$$") >= 0) {
              mainPassRoundUser = mainPassRoundUserField.split("\\$\\$");
              break;
            } 
            mainPassRoundUser[0] = mainPassRoundUserField;
          } 
          break;
        case 5:
          mainPassRoundUser[0] = submitPersonId;
          break;
        case 6:
          mainPassRoundUser = workFlowBD.getRoleUser(httpServletRequest.getParameter("mainPassRole"), submitPersonId);
          break;
        case 10:
          mainPassRoundOrg = httpServletRequest.getParameter("mainPassRoundOrg");
          mainPassRoundUser = workFlowBD.getLeaderListByOrgId(mainPassRoundOrg);
          break;
      } 
    } 
    String subProcWorkId = (httpServletRequest.getParameter("subProcWorkId") == null) ? "0" : httpServletRequest.getParameter("subProcWorkId");
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
    if (httpServletRequest.getParameter("include_commField") != null)
      dealwithMap.put("commentField", httpServletRequest.getParameter("include_commField")); 
    dealwithMap.put("userScope", httpServletRequest.getParameter("userScope"));
    dealwithMap.put("scopeId", httpServletRequest.getParameter("scopeId"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String subProcType = (httpServletRequest.getParameter("subProcType") == null) ? "-1" : httpServletRequest.getParameter("subProcType");
    String toMainFile = httpServletRequest.getParameter("mainLinkFile");
    String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);
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
        activityClass, subProcType, docTitle, emergence };
    Integer result = wfcBD.transWorkflow(dealwithMap, para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
    if (result.intValue() < 0)
      httpServletRequest.setAttribute("flowfaild", "1"); 
    sendMsg(mainTransactUser, httpServletRequest);
    sendMsg(mainPassRoundUser, httpServletRequest);
    StringBuffer allUserIdBuffer = new StringBuffer();
    int i;
    for (i = 0; i < mainTransactUser.length; i++) {
      if (!"".equals(mainTransactUser[i]) && allUserIdBuffer.indexOf(mainTransactUser[i]) < 0)
        allUserIdBuffer.append(mainTransactUser[i]).append(","); 
    } 
    if (mainPassRoundUser != null)
      for (i = 0; i < mainPassRoundUser.length; i++) {
        if (!"".equals(mainPassRoundUser[i]) && allUserIdBuffer.indexOf(mainPassRoundUser[i]) < 0)
          allUserIdBuffer.append(mainPassRoundUser[i]).append(","); 
      }  
    if (allUserIdBuffer.length() > 1) {
      allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer.length() - 1);
    } else {
      allUserIdBuffer.append("-1");
    } 
    DataSource ds = null;
    try {
      ds = (new DataSourceBase()).getDataSource();
      Connection conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select useraccounts from org_employee where emp_id in (" + allUserIdBuffer.toString() + ")");
      allUserIdBuffer = new StringBuffer();
      while (rs.next())
        allUserIdBuffer.append(rs.getString(1)).append(","); 
      rs.next();
      stmt.close();
      conn.close();
      if (allUserIdBuffer.length() > 1)
        allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer.length() - 1); 
      sendRTXNotify(allUserIdBuffer.toString(), "新待批文件提醒\n[" + ((httpServletRequest.getParameter("submitPerson") == null) ? "" : httpServletRequest.getParameter("submitPerson")) + "] " + ((httpServletRequest.getParameter("submitPerson") == null) ? "" : httpServletRequest.getParameter("submitPerson")) + "的" + ((httpServletRequest.getParameter("processName") == null) ? "" : httpServletRequest.getParameter("processName")) + "等待你的办理！\n" + ((httpServletRequest.getParameter("submitTime") == null || httpServletRequest.getParameter("submitTime").trim().length() < 9) ? "" : ("(" + httpServletRequest.getParameter("submitTime").substring(5, 10) + ")")));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private String complete(HttpServletRequest httpServletRequest) {
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
    if (!"".equals(formClassName) && !"".equals(formClassMethod)) {
      FormReflection formReflection = new FormReflection();
      Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, httpServletRequest);
      if (obj != null && 
        obj.getClass().toString().equals("class java.lang.String"))
        remindFieldValue = obj.toString(); 
    } 
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    if (remindFieldValue == null || remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String comment = httpServletRequest.getParameter("include_comment");
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
    dealwithMap.put("nextActivityName", "");
    dealwithMap.put("nextActivityId", "-1");
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
      if (",2,3,34,".indexOf(moduleId) >= 0)
        returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString()); 
      if (!"".equals(returnValue) && !"-1".equals(returnValue))
        if (",2,3,34,".indexOf(tableId) >= 0 && (new ProcessBD()).getIsDossier(work[3]).intValue() == 1) {
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
    } 
    return href;
  }
  
  private void back(HttpServletRequest request) {
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    Map formClassNameMethod = workFlowCommonBD.getActivityClassMethod(request.getParameter("curActivityId"));
    String formClassName = "";
    String formClassMethod = "";
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
      Object object = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, request);
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
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", object1);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", "");
    dealwithMap.put("nextActivityId", "0");
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", request.getParameter("isStandForWork"));
    dealwithMap.put("standForUserId", request.getParameter("standForUserId"));
    if (request.getParameter("include_commField") != null)
      dealwithMap.put("commentField", request.getParameter("include_commField")); 
    dealwithMap.put("userScope", request.getParameter("userScope"));
    dealwithMap.put("scopeId", request.getParameter("scopeId"));
    wfcBD.insertDealWith(dealwithMap);
    String initStepCount = "0";
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
      wfcBD.backToSubmitPerson(para);
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
      initStepCount = request.getParameter("backToStep");
      para.put("backToStep", initStepCount);
      String backToActivityName = request.getParameter("backToActivityName");
      para.put("backToActivityName", backToActivityName);
      para.put("workMainLinkFile", request.getParameter("mainLinkFile"));
      para.put("isStandForWork", request.getParameter("isStandForWork"));
      para.put("standForUserId", request.getParameter("standForUserId"));
      wfcBD.backToActivity(para);
    } 
    Map backMap = wfcBD.getBackToPerson(tableId, recordId, stepCount, initStepCount, workId);
  }
  
  private void trans(HttpServletRequest httpServletRequest) {
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
    if (!"InformationWorkFlow".equals(formClassName) && 
      !"".equals(formClassName) && !"".equals(formClassMethod) && !"transend".equals(httpServletRequest.getParameter("titleFieldName"))) {
      FormReflection formReflection = new FormReflection();
      Object obj = formReflection.execute("com.js.oa.form." + formClassName, formClassMethod, httpServletRequest);
      if (obj != null && 
        obj.getClass().toString().equals("class java.lang.String"))
        remindFieldValue = obj.toString(); 
    } 
    String remindField = httpServletRequest.getParameter("remindField");
    if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    if (remindFieldValue == null || remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    HttpSession httpSession = httpServletRequest.getSession(true);
    Object object = httpSession.getAttribute("userId");
    String transitionUser = httpServletRequest.getParameter("transitionUser");
    String workId = httpServletRequest.getParameter("workId");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String comment = httpServletRequest.getParameter("include_comment");
    String recordId = httpServletRequest.getParameter("recordId");
    String tableId = httpServletRequest.getParameter("tableId");
    String activityName = httpServletRequest.getParameter("curActivityName");
    String activityId = httpServletRequest.getParameter("curActivityId");
    transitionUser = transitionUser.replace('$', ',');
    String[] user = ("," + transitionUser + ",").split(",,");
    String docTitle = "";
    if (!"".equals(httpServletRequest.getParameter("titleFieldName")))
      docTitle = httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName")); 
    if ("transend".equals(httpServletRequest.getParameter("titleFieldName")))
      docTitle = "transend"; 
    String actionType = httpServletRequest.getParameter("actiontype");
    String tranType = httpServletRequest.getParameter("tranType");
    String tranFromPersonId = httpServletRequest.getParameter("tranFromPersonId");
    String[] para = { workId, stepCount, remindFieldValue.toString(), httpServletRequest.getParameter("mainLinkFile"), 
        docTitle, actionType, (String)object, tranType, tranFromPersonId };
    if ("addperson".equals(actionType)) {
      (new WorkFlowButtonBD()).addPersonWork(para, user);
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
      workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
      workLogVO.setSendAction("增加批阅人");
      String tmpUser = httpServletRequest.getParameter("transitionUserName");
      if (tmpUser.endsWith(","))
        tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
      workLogVO.setReceiveUserName(tmpUser);
      workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
      workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
      workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
      workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
      workFlowButtonBD.setDealWithLog(workLogVO);
    } else {
      if ("tranAutoReturn".equals(actionType)) {
        (new WorkFlowButtonBD()).tranAutoReturn(para);
      } else {
        (new WorkFlowBD()).transitionWork(para, user);
      } 
      WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
      String modiCommentId = httpServletRequest.getParameter(
          "modiCommentId");
      Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
      dealwithMap.put("tableId", tableId);
      dealwithMap.put("recordId", recordId);
      dealwithMap.put("curActivityName", activityName);
      dealwithMap.put("curActivityId", activityId);
      dealwithMap.put("userId", object);
      dealwithMap.put("comment", comment);
      dealwithMap.put("nextActivityName", "");
      dealwithMap.put("nextActivityId", "0");
      dealwithMap.put("stepCount", stepCount);
      dealwithMap.put("isStandForWork", 
          httpServletRequest.getParameter("isStandForWork"));
      dealwithMap.put("standForUserId", 
          httpServletRequest.getParameter("standForUserId"));
      if (httpServletRequest.getParameter("include_commField") != null)
        dealwithMap.put("commentField", 
            httpServletRequest.getParameter("include_commField")); 
      dealwithMap.put("commType", 
          httpServletRequest.getParameter("commType"));
      dealwithMap.put("userScope", 
          httpServletRequest.getParameter("userScope"));
      dealwithMap.put("scopeId", 
          httpServletRequest.getParameter("scopeId"));
      dealwithMap.put("modiCommentId", modiCommentId);
      if (!"1".equals(httpServletRequest.getParameter("transend")))
        wfcBD.insertTransDealWith(dealwithMap); 
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
      workLogVO.setSendUserName(httpSession.getAttribute("userName")
          .toString());
      workLogVO.setSendAction("转办");
      String tmpUser = httpServletRequest.getParameter(
          "transitionUserName");
      if (tmpUser.endsWith(","))
        tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
      workLogVO.setReceiveUserName(tmpUser);
      workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
      workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
      workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
      workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
      if ("tranAutoReturn".equals(actionType)) {
        workLogVO.setSendAction("转办自动返回");
        workLogVO.setReceiveUserName(" ");
      } 
      workFlowButtonBD.setDealWithLog(workLogVO);
    } 
  }
  
  private void sendMsgOld(String[] userId, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("needSendMsg") != null) {
      StringBuffer tmp = new StringBuffer();
      for (int i = 0; i < userId.length; i++) {
        if (!"".equals(userId[i]))
          tmp.append(String.valueOf(userId[i]) + ","); 
      } 
      if (tmp.toString().endsWith(","))
        tmp.substring(0, tmp.length() - 1); 
      String msgFrom = httpServletRequest.getParameter("msgFrom");
      String title = null;
      if ("".equals(httpServletRequest.getParameter("titleFieldName"))) {
        title = httpServletRequest.getParameter("processName");
      } else {
        title = httpServletRequest.getParameter(httpServletRequest.getParameter("titleFieldName"));
      } 
      (new ModelSendMsg()).sendSystemMessage(msgFrom, title, tmp.toString(), "", httpServletRequest);
    } 
  }
  
  private void sendMsg(String[] userId, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("needSendMsg") != null && !"".equals(httpServletRequest.getParameter("needSendMsg"))) {
      StringBuffer tmp = new StringBuffer();
      for (int i = 0; i < userId.length; i++) {
        if (!"".equals(userId[i]))
          tmp.append(String.valueOf(userId[i]) + ","); 
      } 
      if (tmp.toString().endsWith(","))
        tmp.substring(0, tmp.length() - 1); 
      String msgFrom = httpServletRequest.getParameter("msgFrom");
      String title = null;
      if ("".equals(httpServletRequest.getParameter("titleFieldName"))) {
        title = httpServletRequest.getParameter("processName");
      } else {
        title = httpServletRequest.getParameter(httpServletRequest
            .getParameter("titleFieldName"));
      } 
      (new ModelSendMsg())
        .sendSystemMessage(msgFrom, httpServletRequest.getParameter("smsContent"), tmp.toString(), "", httpServletRequest, "1");
    } 
  }
  
  private void random(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    String formClassName = "Workflow";
    Map formClassNameMethod = workFlowCommonBD.getProcessClassMethod(httpServletRequest.getParameter("processId"));
    if (formClassNameMethod != null && 
      formClassNameMethod.get("formClassName") != null && 
      !formClassNameMethod.get("formClassName").toString().equals("") && 
      !formClassNameMethod.get("formClassName").toString().toUpperCase().equals("NULL"))
      formClassName = formClassNameMethod.get("formClassName").toString(); 
    String formClassMethod = ("24".equals(httpServletRequest.getParameter("tableId")) || "10".equals(httpServletRequest.getParameter("tableId"))) ? "complete" : "update";
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
    if (remindField.indexOf("S") < 0) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, httpServletRequest.getParameter("recordId"), httpServletRequest.getParameter("tableId"));
    } 
    Map<Object, Object> randMap = new HashMap<Object, Object>();
    randMap.put("type", httpServletRequest.getParameter("type"));
    randMap.put("comment", httpServletRequest.getParameter("comment"));
    randMap.put("userId", httpServletRequest.getSession(true).getAttribute("userId"));
    randMap.put("tableId", httpServletRequest.getParameter("tableId"));
    randMap.put("recordId", httpServletRequest.getParameter("recordId"));
    randMap.put("submitPerson", httpServletRequest.getParameter("submitPerson"));
    randMap.put("remindFieldValue", remindFieldValue);
    randMap.put("processName", httpServletRequest.getParameter("processName"));
    randMap.put("processId", httpServletRequest.getParameter("processId"));
    randMap.put("workId", httpServletRequest.getParameter("workId"));
    randMap.put("randUser", httpServletRequest.getParameter("randUser"));
    randMap.put("stepCount", httpServletRequest.getParameter("stepCount"));
    randMap.put("randomProcUser", httpServletRequest.getParameter("randomProcUser"));
    randMap.put("mainLinkFile", httpServletRequest.getParameter("mainLinkFile"));
    randMap.put("emergence", httpServletRequest.getParameter("emergence"));
    (new WorkFlowCommonBD()).randomProcess(randMap);
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(httpSession.getAttribute("userId").toString());
    workLogVO.setSendUserName(httpSession.getAttribute("userName").toString());
    if ("next".equals(httpServletRequest.getParameter("type"))) {
      workLogVO.setSendAction("送" + ((httpServletRequest.getParameter("activityName") == null || "".equals(httpServletRequest.getParameter("activityName")) || "null".equals(httpServletRequest.getParameter("activityName"))) ? "" : httpServletRequest.getParameter("activityName")));
      workLogVO.setReceiveUserName(httpServletRequest.getParameter("randomProcUserName"));
    } else {
      workLogVO.setSendAction("结束流程");
      workLogVO.setReceiveUserName("");
    } 
    workLogVO.setProcessId(httpServletRequest.getParameter("processId"));
    workLogVO.setTableId(httpServletRequest.getParameter("tableId"));
    workLogVO.setRecordId(httpServletRequest.getParameter("recordId"));
    workLogVO.setDomainId(httpSession.getAttribute("domainId").toString());
    workFlowButtonBD.setDealWithLog(workLogVO);
  }
  
  private String getRemindValue(String remindField, String recordId, String formId) {
    String remindValue = "";
    if (remindField == null || remindField.length() < 1 || remindField.toUpperCase().equals("null") || 
      recordId == null || recordId.length() < 1 || recordId.toUpperCase().equals("null") || 
      formId == null || formId.length() < 1 || formId.toUpperCase().equals("null"))
      return ""; 
    remindField = "S" + remindField + "S";
    String[] remindFieldArr = remindField.split("SS");
    CustomFormBD formBD = new CustomFormBD();
    for (int i = 0; i < remindFieldArr.length; i++) {
      String temp = formBD.getRemindValue(remindFieldArr[i], recordId, formId);
      remindValue = String.valueOf(remindValue) + ((temp == null || temp.length() < 1 || temp.toUpperCase().equals("NULL")) ? "" : temp);
    } 
    return remindValue;
  }
  
  public void sendRTXNotify(String account, String content) {
    RealTimeUtil util = new RealTimeUtil();
    util.sendNotify(account, "待办文件提醒", content, "0", "0");
  }
  
  public void batchRead(HttpServletRequest request) {
    String userId = request.getSession(true).getAttribute("userId").toString();
    String comment = request.getParameter("content");
    String batchValues = request.getParameter("batchValues");
    String[] batchValuesArr = batchValues.split("\\$\\$");
    int passRound = 0;
    WorkflowCommon workflowCommon = new WorkflowCommon();
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    for (int i = 0; i < batchValuesArr.length; i++) {
      Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
      String singleValue = batchValuesArr[i];
      String[] singleValueArr = singleValue.split("\\*");
      String workId = singleValueArr[0];
      String activityId = singleValueArr[1];
      String table = singleValueArr[2];
      String record = singleValueArr[3];
      Map commFieldMap = workflowCommon.getCurActivityCommField(activityId, table, record, workId);
      if (commFieldMap.get("passRoundCommField") != null && 
        !commFieldMap.get("passRoundCommField").toString().equals("") && 
        !commFieldMap.get("passRoundCommField").toString().equals("-1") && 
        !commFieldMap.get("passRoundCommField").toString().toUpperCase().equals("NULL"))
        dealwithMap.put("commentField", commFieldMap.get("passRoundCommField")); 
      dealwithMap.put("comment", comment);
      dealwithMap.put("userId", userId);
      dealwithMap.put("workId", workId);
      if (wfcBD.insertPassroundDealWith(dealwithMap).intValue() != 0)
        passRound = -1; 
    } 
    request.setAttribute("passround", String.valueOf(passRound));
  }
  
  public Integer insertPassroundDealWith(Map para) {
    Integer result = Integer.valueOf("0");
    DataSourceBase dsBase = new DataSourceBase();
    DataSource ds = dsBase.getDataSource();
    try {
      Connection conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String activityId = "0", stepCount = "0", tableId = "0", recordId = "0", title = "";
      rs = stmt.executeQuery("select initactivity, initstepcount,worktable_id,workrecord_id,worktitle from JSDB.JSF_WORK where wf_work_id=" + para.get("workId"));
      if (rs.next()) {
        activityId = rs.getString(1);
        stepCount = rs.getString(2);
        tableId = rs.getString(3);
        recordId = rs.getString(4);
        title = rs.getString(5);
      } 
      rs.close();
      rs = stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + " AND ACTIVITY_ID=" + activityId + " AND CURSTEPCOUNT=" + stepCount);
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals(""))
        return Integer.valueOf("-1"); 
      Date now = new Date();
      String commentField = "";
      if (para.get("commentField") != null)
        commentField = para.get("commentField").toString(); 
      String wf_dealwithcomment_id = dsBase.getTableId();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "','" + now.toLocaleString() + "'," + wf_dealwith_id + ",0,'','" + commentField + "')";
      } else {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + ",0,'','" + commentField + "')";
      } 
      stmt.execute(tmpSql);
      stmt.execute("update JSDB.jsf_work set workstatus=102, worktitle='" + title.replaceAll("等待您的审阅", "审阅完毕") + "' where wf_work_id=" + para.get("workId"));
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } 
    return result;
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
        String orgCode = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && orgIds.indexOf(empId.toString()) < 0)
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
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      groupIdStr = groupIdStr.equals("") ? "-1" : groupIdStr;
      ResultSet rs = stmt.executeQuery("SELECT DISTINCT EMP_ID FROM ORG_USER_GROUP WHERE GROUP_ID IN (" + groupIdStr + ")");
      while (rs.next())
        userStr = String.valueOf(userStr) + rs.getString(1) + ","; 
      if (userStr.endsWith(","))
        userStr = userStr.substring(0, userStr.length() - 1); 
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return userStr;
  }
}
