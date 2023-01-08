package com.js.oa.jsflow.util;

import bsh.Interpreter;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.message.action.ModelSendMsg;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProcessStep {
  public String firstStep(String processId, int processType) {
    StringBuffer firstStep = new StringBuffer();
    if (processType == 0) {
      firstStep.append("<tr>");
      firstStep.append("<td>下一办理人：</td>");
      firstStep.append("<td><input type=text name=randomProcUserName readonly size=60 class=inputText>&nbsp;<label class=mustFillcolor>*</label>");
      firstStep.append("<input type=hidden name=randomProcUser>&nbsp;&nbsp;");
      firstStep.append("<img src=/jsoa/images/person.gif>&nbsp;<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('randomProcUser','randomProcUserName',document.all.randomProcUser.value,document.all.randomProcUserName.value,'user','yes','user','*0*');>");
      firstStep.append("</td>");
      firstStep.append("</tr>");
    } else {
      ProcessBD newProcessBD = new ProcessBD();
      ActivityVO activityVO = newProcessBD.getFirstActivity(processId);
      if (activityVO != null && activityVO.getId() != 0L) {
        int i;
        firstStep.append("<tr>");
        firstStep.append("<td>下一节点：</td>");
        firstStep.append("<td>节点名称：<label class=mustFillcolor>" + activityVO.getName() + "</label>");
        firstStep.append("&nbsp;&nbsp;办理人：");
        int participantType = activityVO.getParticipantType();
        List<Object[]> candidate = null;
        Object[] obj = (Object[])null;
        switch (participantType) {
          case 0:
            firstStep.append("<label style=color:red>流程发起人的上级领导</label>");
            firstStep.append("<input type=hidden name=userType value=0>");
            break;
          case 1:
            firstStep.append("<input type=text name=operProcUserName readonly size=30 class=inputText>");
            firstStep.append("&nbsp;<img src=/jsoa/images/group.gif>");
            firstStep.append("<input type=hidden name=operProcUser>&nbsp;&nbsp;");
            firstStep.append("<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('operProcUser','operProcUserName','','','user','no','user','*0*');>");
            firstStep.append("&nbsp;<label style=color:red>*</label>");
            firstStep.append("<input type=hidden name=userType value=1>");
            break;
          case 2:
            firstStep.append("<select name=candidateUser size=4 multiple=multiple>");
            candidate = activityVO.getParticipantUser();
            for (i = 0; i < candidate.size(); i++) {
              obj = candidate.get(i);
              firstStep.append("<option value=" + obj[0] + " ");
              if (i == 0)
                firstStep.append("selected"); 
              firstStep.append(">" + obj[1] + "</option>");
            } 
            firstStep.append("</select>");
            firstStep.append("&nbsp;<label style=color:red>*</label>");
            firstStep.append("<input type=hidden name=userType value=2>");
            break;
          case 3:
            candidate = activityVO.getParticipantUser();
            firstStep.append("<label class=mustFillcolor>指定全部办理人</label>");
            for (i = 0; i < candidate.size(); i++) {
              obj = candidate.get(i);
              firstStep.append("<input type=hidden name=allUser value=" + obj[0] + ">");
            } 
            firstStep.append("<input type=hidden name=userType value=3>");
            break;
          case 4:
            firstStep.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
            firstStep.append("<input type=hidden name=userField value=" + activityVO.getParticipantUserField() + ">");
            firstStep.append("<input type=hidden name=userType value=4>");
            break;
          case 5:
            firstStep.append("<label class=mustFillcolor>流程发起人</label>");
            firstStep.append("<input type=hidden name=userType value=5>");
            break;
          case 6:
            firstStep.append("<label class=mustFillcolor>" + activityVO.getPartRoleName() + "</label>");
            firstStep.append("<input type=hidden name=userType value=6>");
            firstStep.append("<input type=hidden name=partRole value=" + activityVO.getPartRole() + ">");
            break;
        } 
        if (activityVO.getNeedPassRound().booleanValue()) {
          firstStep.append("&nbsp;&nbsp;阅件用户：<input type=hidden name=needPassRound value=1>");
          switch (activityVO.getPassRoundUserType()) {
            case 0:
              firstStep.append("<label class=mustFillcolor>流程发起人的上级领导</label>");
              firstStep.append("<input type=hidden name=passRoundUserType value=0>");
              break;
            case 1:
              firstStep.append("<input type=text name=passRoundUserName readonly size=30 class=css0>");
              firstStep.append("&nbsp;<img src=/jsoa/images/group.gif>");
              firstStep.append("<input type=hidden name=passRoundUser>&nbsp;&nbsp;");
              firstStep.append("<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('passRoundUser','passRoundUserName','','','user','no','user','*0*');>");
              firstStep.append("<input type=hidden name=passRoundUserType value=1>");
              break;
            case 2:
              firstStep.append("<select name=passRoundCandUser size=4 multiple=multiple>");
              candidate = activityVO.getPassRoundUser();
              for (i = 0; i < candidate.size(); i++) {
                obj = candidate.get(i);
                firstStep.append("<option value=" + obj[0] + " ");
                if (i == 0)
                  firstStep.append("selected"); 
                firstStep.append(">" + obj[1] + "</option>");
              } 
              firstStep.append("</select>");
              firstStep.append("<input type=hidden name=passRoundUserType value=2>");
              break;
            case 3:
              candidate = activityVO.getPassRoundUser();
              firstStep.append("<label class=mustFillcolor>指定全部办理人</label>");
              for (i = 0; i < candidate.size(); i++) {
                obj = candidate.get(i);
                firstStep.append("<input type=hidden name=passRoundAllUser value=" + obj[0] + ">");
              } 
              firstStep.append("<input type=hidden name=passRoundUserType value=3>");
              break;
            case 4:
              firstStep.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
              firstStep.append("<input type=hidden name=passRoundUserField value=" + activityVO.getPassRoundUserField() + ">");
              firstStep.append("<input type=hidden name=passRoundUserType value=4>");
              break;
            case 5:
              firstStep.append("<label class=mustFillcolor>流程发起人</label>");
              firstStep.append("<input type=hidden name=passRoundUserType value=5>");
              break;
            case 6:
              firstStep.append("<label class=mustFillcolor>>" + activityVO.getPartRoleName() + "</label>");
              firstStep.append("<input type=hidden name=passRoundUserType value=6>");
              firstStep.append("<input type=hidden name=passRole value=" + activityVO.getPassRole() + ">");
              break;
          } 
        } 
        firstStep.append("</td>");
        firstStep.append("</tr>");
        firstStep.append("<input type=hidden name=allowStandFor value=" + activityVO.getAllowStandFor() + ">");
        firstStep.append("<input type=hidden name=allowCancel value=" + activityVO.getAllowcancel() + ">");
        firstStep.append("<input type=hidden name=activityId value=" + activityVO.getId() + ">");
        firstStep.append("<input type=hidden name=activityName value=" + activityVO.getName() + ">");
        firstStep.append("<input type=hidden name=pressType value=" + activityVO.getPressType() + ">");
        firstStep.append("<input type=hidden name=deadLineTime value=" + activityVO.getDeadlineTime() + ">");
        firstStep.append("<input type=hidden name=pressMotionTime value=" + activityVO.getPressMotionTime() + ">");
      } else {
        firstStep.append("noActivity");
      } 
    } 
    return firstStep.toString();
  }
  
  public String firstStep(String processId, int processType, HttpServletRequest request) {
    StringBuffer firstStep = new StringBuffer();
    String domainId = request.getSession(true).getAttribute("domainId").toString();
    if (processType == 0) {
      firstStep.append("<tr>");
      firstStep.append("<td>下一办理人：</td>");
      firstStep.append("<td><input type=text name=randomProcUserName readonly size=60 class=inputText>&nbsp;<label class=mustFillcolor>*</label>");
      firstStep.append("<input type=hidden name=randomProcUser>&nbsp;&nbsp;");
      firstStep.append("<img src=/jsoa/images/person.gif>&nbsp;<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('randomProcUser','randomProcUserName',document.all.randomProcUser.value,document.all.randomProcUserName.value,'user','yes','user','*0*');>");
      firstStep.append("</td>");
      firstStep.append("</tr>");
    } else {
      ProcessBD newProcessBD = new ProcessBD();
      ActivityVO activityVO = newProcessBD.getFirstActivity(processId);
      if (activityVO != null && activityVO.getId() != 0L) {
        if (activityVO.getActivityClass() == 2)
          activityVO = getNextActivity((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), request); 
        if (activityVO != null && activityVO.getId() != 0L) {
          List<Object[]> leaderList;
          int i;
          firstStep.append("<tr>");
          firstStep.append("<td>下一步骤：</td>");
          firstStep.append("<td>步骤名称：<label class=mustFillcolor>" + activityVO.getName() + "</label>");
          firstStep.append("&nbsp;&nbsp;办理人：");
          int participantType = activityVO.getParticipantType();
          List<Object[]> candidate = null;
          Object[] obj = (Object[])null;
          switch (participantType) {
            case 0:
              firstStep.append("<select name=candidateUser size=4 multiple=multiple>");
              leaderList = (new WorkFlowBD()).getLeaderList(request.getSession(true).getAttribute("userId").toString());
              for (i = 0; i < leaderList.size(); i++) {
                obj = leaderList.get(i);
                firstStep.append("<option value=" + obj[0] + " ");
                if (i == 0)
                  firstStep.append("selected"); 
                firstStep.append(">" + obj[1] + "</option>");
              } 
              firstStep.append("</select>");
              firstStep.append("&nbsp;<label style=color:red>*</label>");
              firstStep.append("<input type=hidden name=userType value=0>");
              break;
            case 1:
              firstStep.append("<input type=text name=operProcUserName readonly size=30 class=css0>");
              firstStep.append("&nbsp;<img src=/jsoa/images/group.gif>");
              firstStep.append("<input type=hidden name=operProcUser>&nbsp;&nbsp;");
              firstStep.append("<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('operProcUser','operProcUserName',operProcUser.value,operProcUserName.value,'user','no','user','*0*');>");
              firstStep.append("&nbsp;<label style=color:red>*</label>");
              firstStep.append("<input type=hidden name=userType value=1>");
              break;
            case 2:
              firstStep.append("<select name=candidateUser size=4 multiple=multiple>");
              candidate = activityVO.getParticipantUser();
              for (i = 0; i < candidate.size(); i++) {
                obj = candidate.get(i);
                firstStep.append("<option value=" + obj[0] + " ");
                if (i == 0)
                  firstStep.append("selected"); 
                firstStep.append(">" + obj[1] + "</option>");
              } 
              firstStep.append("</select>");
              firstStep.append("&nbsp;<label style=color:red>*</label>");
              firstStep.append("<input type=hidden name=userType value=2>");
              break;
            case 3:
              candidate = activityVO.getParticipantUser();
              firstStep.append("<label style=color:red>指定全部办理人</label>");
              for (i = 0; i < candidate.size(); i++) {
                obj = candidate.get(i);
                firstStep.append("<input type=hidden name=allUser value=" + obj[0] + ">");
              } 
              firstStep.append("<input type=hidden name=userType value=3>");
              break;
            case 4:
              firstStep.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
              firstStep.append("<input type=hidden name=userField value=" + activityVO.getParticipantUserField() + ">");
              firstStep.append("<input type=hidden name=userType value=4>");
              break;
            case 5:
              firstStep.append("<label class=mustFillcolor>流程发起人</label>");
              firstStep.append("<input type=hidden name=userType value=5>");
              break;
            case 6:
              firstStep.append("<label class=mustFillcolor>" + activityVO.getPartRoleName() + "</label>");
              firstStep.append("<input type=hidden name=userType value=6>");
              firstStep.append("<input type=hidden name=partRole value=" + activityVO.getPartRole() + ">");
              break;
          } 
          if (activityVO.getNeedPassRound().booleanValue()) {
            List<Object[]> list;
            firstStep.append("&nbsp;&nbsp;阅件用户：<input type=hidden name=needPassRound value=1>");
            switch (activityVO.getPassRoundUserType()) {
              case 0:
                firstStep.append("<select name=passRoundCandUser size=4 multiple=multiple>");
                list = (new WorkFlowBD()).getLeaderList(request.getSession(true).getAttribute("userId").toString());
                for (i = 0; i < list.size(); i++) {
                  obj = list.get(i);
                  firstStep.append("<option value=" + obj[0] + " ");
                  if (i == 0)
                    firstStep.append("selected"); 
                  firstStep.append(">" + obj[1] + "</option>");
                } 
                firstStep.append("</select>");
                firstStep.append("&nbsp;<label style=color:red>*</label>");
                firstStep.append("<input type=hidden name=passRoundUserType value=0>");
                break;
              case 1:
                firstStep.append("<input type=text name=passRoundUserName readonly size=30 class=css0>");
                firstStep.append("&nbsp;<img src=/jsoa/images/group.gif>");
                firstStep.append("<input type=hidden name=passRoundUser>&nbsp;&nbsp;");
                firstStep.append("<img width=15 height=16 style=cursor:hand src=/jsoa/images/select.gif onclick=openEndow('passRoundUser','passRoundUserName',passRoundUser.value,passRoundUserName.value,'user','no','user','*0*');>");
                firstStep.append("<input type=hidden name=passRoundUserType value=1>");
                break;
              case 2:
                firstStep.append("<select name=passRoundCandUser size=4 multiple=multiple>");
                candidate = activityVO.getPassRoundUser();
                for (i = 0; i < candidate.size(); i++) {
                  obj = candidate.get(i);
                  firstStep.append("<option value=" + obj[0] + " ");
                  if (i == 0)
                    firstStep.append("selected"); 
                  firstStep.append(">" + obj[1] + "</option>");
                } 
                firstStep.append("</select>");
                firstStep.append("<input type=hidden name=passRoundUserType value=2>");
                break;
              case 3:
                candidate = activityVO.getPassRoundUser();
                firstStep.append("<label class=mustFillcolor>指定全部办理人</label>");
                for (i = 0; i < candidate.size(); i++) {
                  obj = candidate.get(i);
                  firstStep.append("<input type=hidden name=passRoundAllUser value=" + obj[0] + ">");
                } 
                firstStep.append("<input type=hidden name=passRoundUserType value=3>");
                break;
              case 4:
                firstStep.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
                firstStep.append("<input type=hidden name=passRoundUserField value=" + activityVO.getPassRoundUserField() + ">");
                firstStep.append("<input type=hidden name=passRoundUserType value=4>");
                break;
              case 5:
                firstStep.append("<label class=mustFillcolor>流程发起人</label>");
                firstStep.append("<input type=hidden name=passRoundUserType value=5>");
                break;
              case 6:
                firstStep.append("<label class=mustFillcolor>" + activityVO.getPartRoleName() + "</label>");
                firstStep.append("<input type=hidden name=passRoundUserType value=6>");
                firstStep.append("<input type=hidden name=passRole value=" + activityVO.getPassRole() + ">");
                break;
            } 
          } 
          if (request.getServletPath().endsWith("sendfile_add.jsp")) {
            String isSendMessage = "";
            if (request.getAttribute("isSendMessage") != null)
              isSendMessage = request.getAttribute("isSendMessage").toString(); 
            if ("true".equals(isSendMessage))
              firstStep.append("<input type=checkbox name=isSendMSG1 value=1>短信提醒"); 
          } else if ((new ModelSendMsg()).judgePurviewMessage("工作流程", domainId)) {
            firstStep.append("<input type=checkbox name=needSendMsg value=1>短信提醒");
          } 
          firstStep.append("</td>");
          firstStep.append("</tr>");
          firstStep.append("<input type=hidden name=allowStandFor value=" + activityVO.getAllowStandFor() + ">");
          firstStep.append("<input type=hidden name=allowCancel value=" + activityVO.getAllowcancel() + ">");
          firstStep.append("<input type=hidden name=activityId value=" + activityVO.getId() + ">");
          firstStep.append("<input type=hidden name=activityName value=" + activityVO.getName() + ">");
          firstStep.append("<input type=hidden name=pressType value=" + activityVO.getPressType() + ">");
          firstStep.append("<input type=hidden name=deadLineTime value=" + activityVO.getDeadlineTime() + ">");
          firstStep.append("<input type=hidden name=pressMotionTime value=" + activityVO.getPressMotionTime() + ">");
        } else {
          firstStep.append("noActivity");
        } 
      } else {
        firstStep.append("noActivity");
      } 
    } 
    return firstStep.toString();
  }
  
  public ActivityVO getNextActivity(String fatherActivityId, HttpServletRequest request) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String nextActivityId = getNextActivityIdWithExp(workFlowBD.getFirstNextActi(fatherActivityId), request, "", "");
    ActivityVO activityVO = null;
    if (nextActivityId != null && !nextActivityId.equals("0") && !nextActivityId.equals("")) {
      activityVO = workFlowBD.getFirstProcActiVO(nextActivityId);
      if (activityVO.getActivityClass() == 2)
        activityVO = getNextActivity((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), request); 
    } 
    return activityVO;
  }
  
  public ActivityVO getProceedNextActi(String fatherId, String tableId, String recordId, HttpServletRequest request) {
    Map<String, String> requestMap = new HashMap<String, String>();
    requestMap.put("moduleId", request.getParameter("moduleId"));
    requestMap.put("processId", request.getParameter("processId"));
    requestMap.put("tranFromPersonId", request.getParameter("tranFromPersonId"));
    requestMap.put("standForUserId", request.getParameter("standForUserId"));
    requestMap.put("curActivityId", request.getParameter("curActivityId"));
    requestMap.put("userId", request.getSession().getAttribute("userId"));
    requestMap.put("dutyLevel", request.getSession().getAttribute("dutyLevel"));
    return getProceedNextActi(fatherId, tableId, recordId, requestMap, request);
  }
  
  public ActivityVO getProceedNextActi(String fatherId, String tableId, String recordId, Map<String, String> requestMap, HttpServletRequest request) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String nextActivityId = getNextActivityIdWithExp(workFlowBD.getNextActivity(tableId, recordId, fatherId), requestMap, tableId, recordId, request);
    if ("0".equals(nextActivityId))
      nextActivityId = workFlowBD.getNextDefaultActivityId(fatherId); 
    ActivityVO activityVO = null;
    if (nextActivityId != null && !nextActivityId.equals("0") && !nextActivityId.equals("")) {
      String moduleId = requestMap.get("moduleId");
      if (moduleId == null || moduleId.toUpperCase().equals("NULL") || 
        moduleId.trim().equals("")) {
        Connection conn = null;
        Statement stmt = null;
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("select WF_MODULE_ID from JSF_PACKAGE a,JSF_WORKFLOWPROCESS b where b.WF_WORKFLOWPROCESS_ID=" + 
              (String)requestMap.get("processId") + "  and a.WF_PACKAGE_ID=b.WF_PACKAGE_ID");
          if (rs.next())
            moduleId = rs.getString(1); 
          rs.close();
          stmt.close();
          conn.close();
        } catch (Exception e) {
          if (conn != null)
            try {
              conn.close();
            } catch (Exception exception) {} 
          e.printStackTrace();
        } 
      } 
      activityVO = workFlowBD.getProceedActiVO(tableId, recordId, nextActivityId, moduleId);
      if (activityVO.getActivityClass() == 2)
        activityVO = getProceedNextActi((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), tableId, recordId, requestMap, request); 
    } 
    return activityVO;
  }
  
  public ActivityVO getProceedNextActi(String moduleId, String processId, String nextActivityId, String tableId, String recordId, String fatherId) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    if ("0".equals(nextActivityId))
      nextActivityId = workFlowBD.getNextDefaultActivityId(fatherId); 
    ActivityVO activityVO = null;
    if (nextActivityId != null && !nextActivityId.equals("0") && !nextActivityId.equals("")) {
      if (moduleId == null || moduleId.toUpperCase().equals("NULL") || 
        moduleId.trim().equals("")) {
        DataSourceBase base = new DataSourceBase();
        try {
          base.begin();
          String sql = "select WF_MODULE_ID from JSF_PACKAGE a,JSF_WORKFLOWPROCESS b where a.WF_PACKAGE_ID=b.WF_PACKAGE_ID and b.WF_WORKFLOWPROCESS_ID=" + processId + "  ";
          ResultSet rs = base.executeQuery(sql);
          if (rs.next())
            moduleId = rs.getString(1); 
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          base.end();
        } 
      } 
      activityVO = workFlowBD.getProceedActiVO(tableId, recordId, nextActivityId, moduleId);
    } 
    return activityVO;
  }
  
  public ActivityVO getProceedNextActi(String fatherId, String tableId, String recordId, String moduleId) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String nextActivityId = getNextActivityId(workFlowBD.getNextActivity(tableId, recordId, fatherId), 
        tableId, recordId, moduleId);
    if ("0".equals(nextActivityId))
      nextActivityId = workFlowBD.getNextDefaultActivityId(fatherId); 
    ActivityVO activityVO = null;
    if (nextActivityId != null && !nextActivityId.equals("0") && !nextActivityId.equals("")) {
      activityVO = (new WorkFlowBD()).getProceedActiVO(tableId, recordId, nextActivityId, moduleId);
      if (activityVO.getActivityClass() == 2)
        activityVO = getProceedNextActi((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), tableId, recordId, moduleId); 
    } 
    return activityVO;
  }
  
  public String getNextActivityIdWithExp(List nextActivityList, HttpServletRequest request, String tableId, String recordId) {
    Map<String, String> requestMap = new HashMap<String, String>();
    requestMap.put("tranFromPersonId", request.getParameter("tranFromPersonId"));
    requestMap.put("standForUserId", request.getParameter("standForUserId"));
    requestMap.put("curActivityId", request.getParameter("curActivityId"));
    requestMap.put("userId", request.getSession().getAttribute("userId"));
    requestMap.put("dutyLevel", request.getSession().getAttribute("dutyLevel"));
    return getNextActivityIdWithExp(nextActivityList, requestMap, tableId, recordId, request);
  }
  
  public String getNextActivityIdWithExp(List<String[]> nextActivityList, Map<String, String> requestMap, String tableId, String recordId, HttpServletRequest request) {
    String nextActivityId = "0";
    String submitPersonId = "";
    String tranFromPersonId = "";
    if (requestMap.get("tranFromPersonId") != null && !"null".equals(requestMap.get("tranFromPersonId")) && 
      !"".equals(requestMap.get("tranFromPersonId")) && !"-1".equals(requestMap.get("tranFromPersonId")))
      tranFromPersonId = requestMap.get("tranFromPersonId"); 
    String standForUserId = requestMap.get("standForUserId");
    String curEmployeeId = (requestMap.get("userId") == null) ? "" : ((String)requestMap.get("userId")).toString();
    if ("".equals(curEmployeeId))
      curEmployeeId = request.getSession(true).getAttribute("userId").toString(); 
    WorkFlowBD workFlowBD = new WorkFlowBD();
    if (!tableId.equals("") && !recordId.equals("")) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select wf_submitemployee_id From JSF_WORK where worktable_id=" + tableId + " and workrecord_id=" + recordId);
        if (rs.next())
          submitPersonId = rs.getString("wf_submitemployee_id"); 
        rs.close();
        String curActivityId = requestMap.get("curActivityId");
        String tranFromPersonIdTemp = tranFromPersonId;
        int num = 0;
        while (!"".equals(tranFromPersonId) && num < 10) {
          rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
          if (rs.next())
            tranFromPersonId = rs.getString(1); 
          rs.close();
          if (tranFromPersonId == null || "null".equals(tranFromPersonId)) {
            tranFromPersonId = "";
          } else {
            tranFromPersonIdTemp = tranFromPersonId;
          } 
          num++;
        } 
        tranFromPersonId = tranFromPersonIdTemp;
        stmt.close();
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception exception) {} 
      } 
    } 
    if (nextActivityList != null && nextActivityList.size() > 0) {
      String[] tmp = (String[])null;
      for (int j = 0; j < nextActivityList.size(); j++) {
        tmp = nextActivityList.get(j);
        if (tmp[4] != null && !"".equals(tmp[4]) && !"null".equals(tmp[4])) {
          StringBuffer expression = new StringBuffer(256);
          String[] exps = tmp[4].split("\\@\\$\\@");
          for (int i = 0; i < exps.length; i++) {
            if (!"".equals(exps[i])) {
              String expTemp = exps[i];
              String leftTag = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String fieldName = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String oprate = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String oprateValue = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String rightTag = expTemp.substring(0, expTemp.indexOf("$:$"));
              String nextTag = expTemp.substring(expTemp.indexOf("$:$") + 3);
              expression.append(leftTag);
              if ("-1".equals(fieldName)) {
                if (submitPersonId.equals(""))
                  submitPersonId = requestMap.get("userId"); 
                if (oprate.equals("<>")) {
                  if (!getEmpDuty(submitPersonId).equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (getEmpDuty(submitPersonId).indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (getEmpDuty(submitPersonId).indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (getEmpDuty(submitPersonId).equals(oprateValue)) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-2".equals(fieldName)) {
                if (submitPersonId.equals(""))
                  submitPersonId = requestMap.get("userId"); 
                String dutyLevel = getEmpDutyLevel(submitPersonId);
                if (oprate.equals("<>")) {
                  if (!dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("=")) {
                  if (dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<")) {
                  try {
                    if (Float.parseFloat(dutyLevel) < 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) <= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">")) {
                  try {
                    if (Float.parseFloat(dutyLevel) > 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) >= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } 
              } else if ("-3".equals(fieldName)) {
                String dutyLevel = requestMap.get("dutyLevel");
                if (standForUserId != null && !"null".equals(standForUserId) && !"0".equals(standForUserId)) {
                  dutyLevel = getEmpDutyLevel(standForUserId);
                } else if (tranFromPersonId != null && !"".equals(tranFromPersonId)) {
                  dutyLevel = getEmpDutyLevel(tranFromPersonId);
                } else if (!"".equals(curEmployeeId)) {
                  dutyLevel = getEmpDutyLevel(curEmployeeId);
                } 
                if (oprate.equals("<>")) {
                  if (!dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("=")) {
                  if (dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<")) {
                  try {
                    if (Float.parseFloat(dutyLevel) < 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) <= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">")) {
                  try {
                    if (Float.parseFloat(dutyLevel) > 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) >= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } 
              } else if ("-4".equals(fieldName)) {
                if (submitPersonId.equals(""))
                  submitPersonId = requestMap.get("userId"); 
                String tmpRole = getEmpRoleString(submitPersonId);
                if (oprate.equals("<>")) {
                  if (tmpRole.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpRole.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-5".equals(fieldName)) {
                if (submitPersonId.equals(""))
                  submitPersonId = requestMap.get("userId"); 
                String tmpPosition = getEmpPosition(submitPersonId);
                if (oprate.equals("<>")) {
                  if (tmpPosition.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpPosition.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-6".equals(fieldName)) {
                String tmpRole = "";
                if (standForUserId != null && !"null".equals(standForUserId) && !"0".equals(standForUserId)) {
                  tmpRole = getEmpRoleString(standForUserId);
                } else if (tranFromPersonId != null && !"".equals(tranFromPersonId)) {
                  tmpRole = getEmpRoleString(tranFromPersonId);
                } else if (!"".equals(curEmployeeId)) {
                  tmpRole = getEmpRoleString(curEmployeeId);
                } 
                if (oprate.equals("<>")) {
                  if (tmpRole.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpRole.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-7".equals(fieldName)) {
                String tmpPosition = "";
                if (standForUserId != null && !"null".equals(standForUserId) && !"0".equals(standForUserId)) {
                  tmpPosition = getEmpPosition(standForUserId);
                } else if (tranFromPersonId != null && !"".equals(tranFromPersonId)) {
                  tmpPosition = getEmpPosition(tranFromPersonId);
                } else if (!"".equals(curEmployeeId)) {
                  tmpPosition = getEmpPosition(curEmployeeId);
                } 
                if (oprate.equals("<>")) {
                  if (tmpPosition.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpPosition.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else {
                String userValue = null;
                if (request != null)
                  userValue = (request.getParameter(fieldName) == null) ? request.getParameter(String.valueOf(fieldName) + "_Name") : request.getParameter(fieldName); 
                if (userValue == null || userValue.toUpperCase().equals("NULL"))
                  userValue = (new CustomFormBD()).getValue(fieldName, recordId, tableId); 
                if (userValue != null)
                  if (oprate.equals("<")) {
                    try {
                      if (Float.parseFloat(userValue) < 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("<=")) {
                    try {
                      if (Float.parseFloat(userValue) <= 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("<>")) {
                    if (!userValue.equals(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("=")) {
                    if (userValue.equals(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals(">")) {
                    try {
                      if (Float.parseFloat(userValue) > 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals(">=")) {
                    try {
                      if (Float.parseFloat(userValue) >= 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("like")) {
                    if (userValue.indexOf(oprateValue) >= 0) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("not like")) {
                    if (userValue.indexOf(oprateValue) >= 0) {
                      expression.append("false");
                    } else {
                      expression.append("true");
                    } 
                  }  
                IO2File.printFile("字段值判断：" + userValue + " " + oprate + " " + oprateValue, "流程节点条件判断");
              } 
              expression.append(rightTag);
              if ("0".equals(nextTag))
                break; 
              if ("1".equals(nextTag)) {
                expression.append(" && ");
              } else {
                expression.append(" || ");
              } 
            } 
          } 
          try {
            Interpreter inter = new Interpreter();
            Object obj = inter.eval(expression.toString());
            if (obj != null && 
              "TRUE".equals(obj.toString().trim().toUpperCase())) {
              nextActivityId = tmp[0];
              break;
            } 
          } catch (Exception e) {
            System.out.println("解析后的表达式语法错误！");
          } 
        } else {
          if (tmp[1].equals("0")) {
            nextActivityId = tmp[0];
            break;
          } 
          if (tmp[1].equals("-1")) {
            if (submitPersonId.equals(""))
              submitPersonId = requestMap.get("userId"); 
            if (tmp[3].equals("<>")) {
              if (!getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like") && 
              getEmpDuty(submitPersonId).indexOf(tmp[2]) < 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if (tmp[1].equals("-2")) {
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (submitPersonId.equals(""))
              submitPersonId = ((String)requestMap.get("userId")).toString(); 
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if (tmp[1].equals("-3")) {
            String dutyLevel = requestMap.get("dutyLevel");
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (!"".equals(tranFromPersonId))
                dutyLevel = getEmpDutyLevel(tranFromPersonId); 
            } else {
              dutyLevel = getEmpDutyLevel(standForUserId);
            } 
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if ("-4".equals(tmp[1])) {
            if (submitPersonId.equals(""))
              submitPersonId = requestMap.get("userId"); 
            String tmpRole = getEmpRoleString(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (tmpRole.indexOf(",," + tmp[2] + ",,") < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (tmpRole.indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (tmpRole.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmpRole.indexOf(",," + tmp[2] + ",,") >= 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if ("-5".equals(tmp[1])) {
            if (submitPersonId.equals(""))
              submitPersonId = requestMap.get("userId"); 
            String tmpPosition = getEmpPosition(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (tmpPosition.indexOf(",," + tmp[2] + ",,") < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (tmpPosition.indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (tmpPosition.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmpPosition.indexOf(",," + tmp[2] + ",,") >= 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if ("-6".equals(tmp[1])) {
            String tmpRole = "";
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (!"".equals(tranFromPersonId))
                tmpRole = getEmpRoleString(tranFromPersonId); 
            } else {
              tmpRole = getEmpRoleString(standForUserId);
            } 
            if (tmp[3].equals("<>")) {
              if (tmpRole.indexOf(",," + tmp[2] + ",,") < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (tmpRole.indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (tmpRole.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmpRole.indexOf(",," + tmp[2] + ",,") >= 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if ("-7".equals(tmp[1])) {
            if (submitPersonId.equals(""))
              submitPersonId = requestMap.get("userId"); 
            String tmpPosition = "";
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (!"".equals(tranFromPersonId))
                tmpPosition = getEmpPosition(tranFromPersonId); 
            } else {
              tmpPosition = getEmpPosition(standForUserId);
            } 
            if (tmp[3].equals("<>")) {
              if (tmpPosition.indexOf(",," + tmp[2] + ",,") < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (tmpPosition.indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (tmpPosition.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmpPosition.indexOf(",," + tmp[2] + ",,") >= 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else {
            String myFieldName = workFlowBD.getFieldName(tmp[1]);
            String userValue = null;
            if (request != null)
              userValue = (request.getParameter(myFieldName) == null) ? request.getParameter(String.valueOf(myFieldName) + "_Name") : request.getParameter(myFieldName); 
            if (userValue == null || userValue.toUpperCase().equals("NULL"))
              userValue = (new CustomFormBD()).getValue(myFieldName, recordId, tableId); 
            if (userValue != null)
              if (tmp[3].equals("<")) {
                try {
                  if (Float.parseFloat(userValue) < 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<=")) {
                try {
                  if (Float.parseFloat(userValue) <= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<>")) {
                if (!userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("=")) {
                if (userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals(">")) {
                try {
                  if (Float.parseFloat(userValue) > 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals(">=")) {
                try {
                  if (Float.parseFloat(userValue) >= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("like")) {
                if (userValue.indexOf(tmp[2]) >= 0) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("not like") && 
                userValue.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              }  
          } 
        } 
      } 
    } 
    return nextActivityId;
  }
  
  public String getNextActivityId(List<String[]> nextActivityList, HttpServletRequest request, String tableId, String recordId) {
    String nextActivityId = "0";
    String submitPersonId = "";
    String tranFromPersonId = "";
    if (request.getParameter("tranFromPersonId") != null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId")))
      tranFromPersonId = request.getParameter("tranFromPersonId"); 
    String standForUserId = request.getParameter("standForUserId");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    if (!tableId.equals("") && !recordId.equals("")) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select wf_submitemployee_id From JSF_WORK where worktable_id=" + tableId + " and workrecord_id=" + recordId);
        if (rs.next())
          submitPersonId = rs.getString("wf_submitemployee_id"); 
        rs.close();
        String curActivityId = request.getParameter("curActivityId");
        String tranFromPersonIdTemp = tranFromPersonId;
        while (!"".equals(tranFromPersonId)) {
          rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
          if (rs.next())
            tranFromPersonId = rs.getString(1); 
          rs.close();
          if (tranFromPersonId == null || "null".equals(tranFromPersonId)) {
            tranFromPersonId = "";
            continue;
          } 
          tranFromPersonIdTemp = tranFromPersonId;
        } 
        tranFromPersonId = tranFromPersonIdTemp;
        stmt.close();
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception exception) {} 
      } 
    } 
    if (nextActivityList != null && nextActivityList.size() > 0) {
      String[] tmp = (String[])null;
      for (int j = 0; j < nextActivityList.size(); j++) {
        tmp = nextActivityList.get(j);
        if (tmp.length == 6 && "1".equals(tmp[5])) {
          if (checkExpression((tmp[4] == null) ? "" : tmp[4].toString(), request))
            nextActivityId = tmp[0]; 
        } else {
          if (tmp[1].equals("0")) {
            nextActivityId = tmp[0];
            break;
          } 
          if (tmp[1].equals("-1")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            if (tmp[3].equals("<>")) {
              if (!getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like") && 
              getEmpDuty(submitPersonId).indexOf(tmp[2]) < 0) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if (tmp[1].equals("-2")) {
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if (tmp[1].equals("-3")) {
            Object object = request.getSession().getAttribute("dutyLevel");
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (!"".equals(tranFromPersonId))
                object = getEmpDutyLevel(tranFromPersonId); 
            } else {
              object = getEmpDutyLevel(standForUserId);
            } 
            if (tmp[3].equals("<>")) {
              if (!object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat((String)object) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat((String)object) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat((String)object) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat((String)object) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else {
            String myFieldName = workFlowBD.getFieldName(tmp[1]);
            String userValue = (request.getParameter(myFieldName) == null) ? request.getParameter(String.valueOf(myFieldName) + "_Name") : request.getParameter(myFieldName);
            if (userValue == null || 
              userValue.toUpperCase().equals("NULL"))
              userValue = (new CustomFormBD()).getValue(myFieldName, 
                  recordId, tableId); 
            if (userValue != null)
              if (tmp[3].equals("<")) {
                try {
                  if (Float.parseFloat(userValue) < 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<=")) {
                try {
                  if (Float.parseFloat(userValue) <= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<>")) {
                if (!userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("=")) {
                if (userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals(">")) {
                try {
                  if (Float.parseFloat(userValue) > 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals(">=")) {
                try {
                  if (Float.parseFloat(userValue) >= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("like")) {
                if (userValue.indexOf(tmp[2]) >= 0) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("not like") && 
                userValue.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              }  
          } 
        } 
      } 
    } 
    return nextActivityId;
  }
  
  public String getNextActivityId(List<String[]> nextActivityList, String tableId, String recordId, String moduleId) {
    String nextActivityId = "0";
    WorkFlowBD workFlowBD = new WorkFlowBD();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
    } catch (Exception exception) {}
    if (conn != null && stmt != null) {
      ResultSet rs = null;
      String table_name = "", submitPersonId = "";
      try {
        if ("1".equals(moduleId)) {
          rs = stmt.executeQuery("select area_table from tarea where page_id=" + tableId);
        } else {
          rs = stmt.executeQuery("select table_name from ttable where table_id in(" + tableId + ")");
        } 
        if (rs.next())
          table_name = rs.getString(1); 
        rs.close();
        if (table_name.equals("")) {
          rs = stmt.executeQuery("SELECT IMMOFORM_REALNAME FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_IMMOFORM_ID in(" + tableId + ")");
          if (rs.next())
            table_name = rs.getString(1); 
        } 
        rs = stmt.executeQuery("select wf_submitemployee_id from JSF_WORK where worktable_id in(" + tableId + ")" + 
            " and workrecord_id=" + recordId);
        if (rs.next())
          submitPersonId = rs.getString(1); 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (nextActivityList != null && nextActivityList.size() > 0) {
        String[] tmp = (String[])null;
        for (int j = 0; j < nextActivityList.size(); j++) {
          tmp = nextActivityList.get(j);
          if (tmp[1].equals("0")) {
            nextActivityId = tmp[0];
            break;
          } 
          if (tmp[1].equals("-1")) {
            if (tmp[3].equals("<>") && 
              !getEmpDuty(submitPersonId).equals(tmp[2])) {
              nextActivityId = tmp[0];
              break;
            } 
            if (tmp[3].equals("=") && 
              getEmpDuty(submitPersonId).equals(tmp[2])) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if (tmp[1].equals("-2")) {
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if (tmp[1].equals("-3")) {
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else {
            String myFieldName = workFlowBD.getFieldName(tmp[1]);
            String userValue = "";
            try {
              rs = stmt.executeQuery("SELECT IMMOFORM_PRIMARYKEY FROM JSDB.JSF_IMMOBILITYFORM WHERE IMMOFORM_REALNAME='" + table_name + "'");
              if (rs.next()) {
                String pk = rs.getString(1);
                String columnName = "";
                rs = stmt.executeQuery("SELECT A.IMMOFIELD_REALNAME FROM JSF_IMMOBILITYFIELD A,JSF_IMMOBILITYFORM B WHERE A.WF_IMMOFORM_ID=B.WF_IMMOFORM_ID AND B.IMMOFORM_REALNAME='" + table_name + "' AND A.IMMOFIELD_POFIELD='" + myFieldName + "'");
                if (rs.next())
                  columnName = rs.getString(1); 
                rs = stmt.executeQuery("select " + columnName + " from " + table_name + " where " + pk + "=" + recordId);
                if (rs.next())
                  userValue = rs.getString(1); 
              } else {
                rs = stmt.executeQuery("select " + myFieldName + " from " + table_name + 
                    " where " + table_name + "_id=" + recordId);
                if (rs.next())
                  userValue = rs.getString(1); 
              } 
              rs.close();
            } catch (Exception e) {
              e.printStackTrace();
            } 
            if (userValue != null && !userValue.equals(""))
              if (tmp[3].equals("<")) {
                try {
                  if (Float.parseFloat(userValue) < Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<=")) {
                try {
                  if (Float.parseFloat(userValue) <= Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<>")) {
                if (!userValue.equals(tmp[2]))
                  nextActivityId = tmp[0]; 
              } else if (tmp[3].equals("=")) {
                if (userValue.equals(tmp[2]))
                  nextActivityId = tmp[0]; 
              } else if (tmp[3].equals(">")) {
                try {
                  if (Float.parseFloat(userValue) > Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals(">=")) {
                try {
                  if (Float.parseFloat(userValue) >= Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              }  
          } 
        } 
      } 
      try {
        stmt.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return nextActivityId;
  }
  
  public String getEmpDuty(String empId) {
    String empDuty = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select empduty from org_employee where emp_id=?");
      pstmt.setString(1, empId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        empDuty = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
    } 
    return (empDuty == null) ? "" : empDuty;
  }
  
  private String getEmpRoleString(String empId) {
    StringBuffer roles = new StringBuffer(",,");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT rolename FROM org_user_role our,org_role o WHERE our.role_id=o.role_id AND emp_id=?");
      pstmt.setString(1, empId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        roles.append(rs.getString(1)).append(",,"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
    } 
    return roles.toString();
  }
  
  private String getEmpPosition(String empId) {
    String position = ",,";
    Connection conn = null;
    try {
      String positionId = "", positionOtherId = "";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT emppositionid,emppositionotherid FROM org_employee WHERE emp_id=?");
      pstmt.setString(1, empId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        positionId = rs.getString(1);
        positionOtherId = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      if (positionId != null && !"".equals(positionId)) {
        pstmt = conn.prepareStatement("SELECT station_name FROM st_station WHERE id=?");
        pstmt.setString(1, positionId);
        rs = pstmt.executeQuery();
        if (rs.next())
          position = String.valueOf(position) + rs.getString(1) + ",,"; 
        rs.close();
        pstmt.close();
      } 
      if (positionOtherId != null && !"".equals(positionOtherId)) {
        pstmt = conn.prepareStatement("SELECT station_name FROM st_station WHERE id=?");
        pstmt.setString(1, positionOtherId);
        rs = pstmt.executeQuery();
        if (rs.next())
          position = String.valueOf(position) + rs.getString(1) + ",,"; 
        rs.close();
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
    } 
    return position;
  }
  
  public String getEmpDutyLevel(String empId) {
    String empDutyLevel = "0";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String empDuty = "";
      ResultSet rs = stmt.executeQuery("select empduty from org_employee where emp_id=" + empId);
      if (rs.next())
        empDuty = rs.getString(1); 
      rs.close();
      rs = stmt.executeQuery("select d.dutyLevel from oa_duty d where d.dutyName='" + empDuty + "'");
      if (rs.next())
        empDutyLevel = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return empDutyLevel;
  }
  
  public List listDutyLevel(String domainId) throws Exception {
    List<Object[]> list = new ArrayList();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select d.duty_id,d.dutyname,d.dutyLevel from oa_duty d where d.domain_Id=" + domainId);
      while (rs.next()) {
        Object[] obj = { "", "", "" };
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    } 
    return list;
  }
  
  public boolean checkExpression(String expression, HttpServletRequest request) {
    boolean isRight = false;
    if (expression == null || expression.length() < 1)
      return true; 
    expression = expression.replaceAll("''", "'").replaceAll("@@@@", "&");
    try {
      Interpreter inter = new Interpreter();
      inter.set("request", request);
      Object obj = inter.eval(expression);
      if (obj != null && 
        "TRUE".equals(obj.toString().trim().toUpperCase()))
        isRight = true; 
    } catch (Exception e) {
      System.out.println("----------------checkExpression()------------------");
      System.out.println("解析后的表达式语法错误！");
      System.out.println("----------------------------------------------");
    } finally {}
    return isRight;
  }
}
