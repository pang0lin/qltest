package com.js.oa.jsflow.action;

import com.js.oa.eform.bean.FormPageEJBBean;
import com.js.oa.eform.service.FormBD;
import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.service.ActivityBD;
import com.js.oa.jsflow.service.ModuleBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.DataSynchro;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.security.log.service.LogBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ActivityAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ActivityActionForm activityActionForm = (ActivityActionForm)actionForm;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    if (action == null)
      action = "view"; 
    String tag = "";
    String processId = httpServletRequest.getParameter("processId");
    String tableId = httpServletRequest.getParameter("tableId");
    int moduleId = 0;
    if (httpServletRequest.getParameter("moduleId") != null)
      moduleId = Integer.parseInt(httpServletRequest.getParameter("moduleId")); 
    httpServletRequest.setAttribute("moduleId", (new StringBuilder(String.valueOf(moduleId))).toString());
    switch (moduleId) {
      case 2:
        moduleCode = "oa_gw_fw";
        moduleName = "发文管理";
        break;
      case 3:
        moduleCode = "oa_gw_fw";
        moduleName = "收文管理";
        break;
      default:
        moduleCode = "oa_workflow_set";
        moduleName = "流程设置";
        break;
    } 
    if (action.equals("view")) {
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      tag = "view";
      list(httpServletRequest, processId);
    } else if (action.equals("add")) {
      tag = "add";
      FormBD formBD = new FormBD();
      List<Object[]> temp = formBD.getSingleForm(httpServletRequest.getParameter("tableId"));
      if (temp != null && temp.size() > 0) {
        Object[] obj = temp.get(0);
        httpServletRequest.setAttribute("realTableName", obj[6]);
      } 
      httpServletRequest.setAttribute("formList", formBD.getFormBaseInfo(httpServletRequest.getSession(true).getAttribute("domainId").toString()));
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      if (moduleVO.getActivityFormMethod() != null && 
        !moduleVO.getActivityFormMethod().equals("") && 
        !moduleVO.getActivityFormMethod().toUpperCase().equals("NULL")) {
        httpServletRequest.setAttribute("activityFormMethod", moduleVO.getActivityFormMethod());
      } else {
        httpServletRequest.setAttribute("activityFormMethod", "");
      } 
      httpServletRequest.setAttribute("chanActiType", new Boolean(moduleVO.isChanActiType()));
      httpServletRequest.setAttribute("actiType", (new StringBuilder(String.valueOf(moduleVO.getActiType()))).toString());
      httpServletRequest.setAttribute("chanActiClass", new Boolean(moduleVO.isChanActiClass()));
      httpServletRequest.setAttribute("actiClass", (new StringBuilder(String.valueOf(moduleVO.getActiClass()))).toString());
      if (moduleVO.isChanActiClass()) {
        HttpSession httpSession = httpServletRequest.getSession(true);
        String domainId = httpSession.getAttribute("domainId").toString();
        httpServletRequest.setAttribute("userProc", (new ProcessBD()).getAllProcessByModule(String.valueOf(moduleId), domainId));
      } 
      httpServletRequest.setAttribute("chanTranType", new Boolean(moduleVO.isChanTranType()));
      if ("1".equals(httpServletRequest.getParameter("moduleId"))) {
        httpServletRequest.setAttribute("actiCommType", "1");
      } else {
        httpServletRequest.setAttribute("actiCommType", (new StringBuilder(String.valueOf(moduleVO.getActiCommType()))).toString());
      } 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("orgList", workFlowBD.getOrg(session.getAttribute("domainId").toString()));
      httpServletRequest.setAttribute("roleList", workFlowBD.getRole(session.getAttribute("domainId").toString()));
      try {
        List dutyList = (new ProcessStep()).listDutyLevel(httpServletRequest.getSession(true).getAttribute("domainId").toString());
        httpServletRequest.setAttribute("dutyList", dutyList);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else if (action.equals("continue") || action.equals("close") || action.equals("trueModify")) {
      String[] readField, writeField, noDisplayField;
      if (action.equals("close") || action.equals("trueModify")) {
        tag = "close";
        httpServletRequest.setAttribute("tableId", tableId);
        httpServletRequest.setAttribute("processId", processId);
      } else {
        FormBD formBD = new FormBD();
        List<Object[]> temp = formBD.getSingleForm(httpServletRequest.getParameter("tableId"));
        if (temp != null && temp.size() > 0) {
          Object[] obj = temp.get(0);
          httpServletRequest.setAttribute("realTableName", obj[6]);
        } 
        httpServletRequest.setAttribute("formList", formBD.getFormBaseInfo(httpServletRequest.getSession(true).getAttribute("domainId").toString()));
        tag = "add";
        WorkFlowBD workFlowBD = new WorkFlowBD();
        httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
        httpServletRequest.setAttribute("tableId", tableId);
        httpServletRequest.setAttribute("processId", processId);
        ModuleBD moduleBD = new ModuleBD();
        ModuleVO moduleVO = moduleBD.getModule(moduleId);
        if (moduleVO.getActivityFormMethod() != null && 
          !moduleVO.getActivityFormMethod().equals("") && 
          !moduleVO.getActivityFormMethod().toUpperCase().equals("NULL")) {
          httpServletRequest.setAttribute("activityFormMethod", moduleVO.getActivityFormMethod());
        } else {
          httpServletRequest.setAttribute("activityFormMethod", "");
        } 
        httpServletRequest.setAttribute("chanActiType", new Boolean(moduleVO.isChanActiType()));
        httpServletRequest.setAttribute("actiType", (new StringBuilder(String.valueOf(moduleVO.getActiType()))).toString());
        httpServletRequest.setAttribute("chanActiClass", new Boolean(moduleVO.isChanActiClass()));
        httpServletRequest.setAttribute("actiClass", (new StringBuilder(String.valueOf(moduleVO.getActiClass()))).toString());
        if (moduleVO.isChanActiClass()) {
          HttpSession httpSession = httpServletRequest.getSession(true);
          String userId = httpSession.getAttribute("userId").toString();
          String orgIdString = httpSession.getAttribute("orgIdString").toString();
          httpServletRequest.setAttribute("userProc", (new ProcessBD()).getUserProcess(userId, orgIdString, (new StringBuilder(String.valueOf(moduleId))).toString()));
        } 
        httpServletRequest.setAttribute("chanTranType", new Boolean(moduleVO.isChanTranType()));
        if ("1".equals(httpServletRequest.getParameter("moduleId"))) {
          httpServletRequest.setAttribute("actiCommType", "1");
        } else {
          httpServletRequest.setAttribute("actiCommType", (new StringBuilder(String.valueOf(moduleVO.getActiCommType()))).toString());
        } 
        httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
        httpServletRequest.setAttribute("orgList", workFlowBD.getOrg(session.getAttribute("domainId").toString()));
        httpServletRequest.setAttribute("roleList", workFlowBD.getRole(session.getAttribute("domainId").toString()));
        try {
          List dutyList = (new ProcessStep()).listDutyLevel(
              httpServletRequest.getSession(true).getAttribute(
                "domainId").toString());
          httpServletRequest.setAttribute("dutyList", dutyList);
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
      String activityName = activityActionForm.getActivityName();
      String activityType = activityActionForm.getActivityType();
      String activityDescription = activityActionForm.getActivityDescription();
      String participantType = activityActionForm.getParticipantType();
      String[] transactTypes = httpServletRequest.getParameterValues("transactType");
      String transactType = "";
      for (int i = 0; i < transactTypes.length; i++)
        transactType = String.valueOf(transactType) + transactTypes[i]; 
      if (SystemCommon.getFormProgram() == 1 && moduleId == 1) {
        String duiying = httpServletRequest.getParameter("duiying");
        String caozuo = httpServletRequest.getParameter("toAddOrUpdate");
        DataSynchro ds = new DataSynchro();
        if ("1".equals(duiying)) {
          if ("add".equals(caozuo)) {
            ds.save(httpServletRequest);
          } else {
            ds.update(httpServletRequest);
          } 
        } else {
          String theProcessId = httpServletRequest.getParameter("processId");
          String dataNodeId = httpServletRequest.getParameter("activityId");
          ds.deleteSynchro(theProcessId, dataNodeId);
        } 
      } 
      String participantUser = "";
      String participantGroup = "";
      String participantUserName = "";
      String participantUserField = "";
      String partRole = "";
      String passRole = "";
      if (participantType.equals("2")) {
        String candidateId = httpServletRequest.getParameter("candidateId");
        char flagCode = '0';
        int nextPos = 0;
        String str = "";
        if (!candidateId.equals(""))
          for (int k = 0; k < candidateId.length(); k++) {
            flagCode = candidateId.charAt(k);
            nextPos = candidateId.indexOf((new StringBuilder(String.valueOf(flagCode))).toString(), k + 1);
            str = candidateId.substring(k, nextPos + 1);
            if (flagCode == '$') {
              participantUser = String.valueOf(participantUser) + str;
            } else {
              participantGroup = String.valueOf(participantGroup) + str;
            } 
            k = nextPos;
          }  
        participantUserName = httpServletRequest.getParameter("candidate");
      } else if (participantType.equals("3")) {
        String allUserId = httpServletRequest.getParameter("allUserId");
        char flagCode = '0';
        int nextPos = 0;
        String str = "";
        if (!allUserId.equals(""))
          for (int k = 0; k < allUserId.length(); k++) {
            flagCode = allUserId.charAt(k);
            nextPos = allUserId.indexOf((new StringBuilder(String.valueOf(flagCode))).toString(), k + 1);
            str = allUserId.substring(k, nextPos + 1);
            if (flagCode == '$') {
              participantUser = String.valueOf(participantUser) + str;
            } else {
              participantGroup = String.valueOf(participantGroup) + str;
            } 
            k = nextPos;
          }  
        participantUserName = httpServletRequest.getParameter("allUser");
      } else if (participantType.equals("4")) {
        participantUserField = httpServletRequest.getParameter("participantUserField");
      } else if (participantType.equals("6")) {
        if (httpServletRequest.getParameter("partRoleNexus").equals("0") || httpServletRequest.getParameter("partRoleOrg").equals("0")) {
          partRole = httpServletRequest.getParameter("partRole");
        } else {
          partRole = String.valueOf(httpServletRequest.getParameter("partRole")) + 
            httpServletRequest.getParameter("partRoleNexus") + 
            httpServletRequest.getParameter("partRoleOrg");
        } 
      } else if (participantType.equals("15")) {
        if (httpServletRequest.getParameter("partPositionNexus").equals("0") || httpServletRequest.getParameter("partPositionOrg").equals("0")) {
          partRole = httpServletRequest.getParameter("partPosition");
        } else {
          partRole = String.valueOf(httpServletRequest.getParameter("partPosition")) + 
            httpServletRequest.getParameter("partPositionNexus") + 
            httpServletRequest.getParameter("partPositionOrg");
        } 
      } else if (participantType.equals("14")) {
        partRole = String.valueOf(httpServletRequest.getParameter("dutyLevelOperate")) + "|" + httpServletRequest.getParameter("dutyLevel");
      } 
      String needPassRound = httpServletRequest.getParameter("needPassRound");
      int passRoundUserType = -1;
      String passRoundUser = "", passRoundUserGroup = "", passRoundUserName = "";
      String passRoundUserField = "", passRoundCommField = "";
      if (needPassRound.equals("1")) {
        String passRound_candidateId;
        char flagCode;
        int nextPos;
        String str, passRound_allUserId;
        passRoundUserType = Integer.parseInt(httpServletRequest.getParameter("passRoundUserType"));
        switch (passRoundUserType) {
          case 2:
            passRound_candidateId = httpServletRequest.getParameter("passRound_candidateId");
            flagCode = '0';
            nextPos = 0;
            str = "";
            if (!passRound_candidateId.equals(""))
              for (int k = 0; k < passRound_candidateId.length(); k++) {
                flagCode = passRound_candidateId.charAt(k);
                nextPos = passRound_candidateId.indexOf((new StringBuilder(String.valueOf(flagCode))).toString(), k + 1);
                str = passRound_candidateId.substring(k, nextPos + 1);
                if (flagCode == '$') {
                  passRoundUser = String.valueOf(passRoundUser) + str;
                } else {
                  passRoundUserGroup = String.valueOf(passRoundUserGroup) + str;
                } 
                k = nextPos;
              }  
            passRoundUserName = httpServletRequest.getParameter("passRound_candidate");
            break;
          case 3:
            passRound_allUserId = httpServletRequest.getParameter("passRound_allUserId");
            if (!passRound_allUserId.equals(""))
              for (int k = 0; k < passRound_allUserId.length(); k++) {
                flagCode = passRound_allUserId.charAt(k);
                nextPos = passRound_allUserId.indexOf((new StringBuilder(String.valueOf(flagCode))).toString(), k + 1);
                str = passRound_allUserId.substring(k, nextPos + 1);
                if (flagCode == '$') {
                  passRoundUser = String.valueOf(passRoundUser) + str;
                } else {
                  passRoundUserGroup = String.valueOf(passRoundUserGroup) + str;
                } 
                k = nextPos;
              }  
            passRoundUserName = httpServletRequest.getParameter("passRound_allUser");
            break;
          case 4:
            passRoundUserField = httpServletRequest.getParameter("passRoundUserField");
            break;
          case 6:
            if (httpServletRequest.getParameter("passRoleNexus").equals("0") || httpServletRequest.getParameter("passOrg").equals("0")) {
              passRole = httpServletRequest.getParameter("passRole");
              break;
            } 
            passRole = String.valueOf(httpServletRequest.getParameter("passRole")) + 
              httpServletRequest.getParameter("passRoleNexus") + 
              httpServletRequest.getParameter("passOrg");
            break;
          case 15:
            if (httpServletRequest.getParameter("passPositionNexus").equals("0") || httpServletRequest.getParameter("passPositionOrg").equals("0")) {
              passRole = httpServletRequest.getParameter("passPosition");
              break;
            } 
            passRole = String.valueOf(httpServletRequest.getParameter("passPosition")) + 
              httpServletRequest.getParameter("passPositionNexus") + 
              httpServletRequest.getParameter("passPositionOrg");
            break;
          case 14:
            passRole = String.valueOf(httpServletRequest.getParameter("passDutyLevelOperate")) + "|" + httpServletRequest.getParameter("passDutyLevel");
            break;
        } 
        String[] passRoundCommFieldArray = httpServletRequest
          .getParameterValues(
            "passRoundCommField");
        if (passRoundCommFieldArray == null || 
          passRoundCommFieldArray.length == 0) {
          passRoundCommField = "-1";
        } else if (passRoundCommFieldArray.length == 1) {
          passRoundCommField = passRoundCommFieldArray[0];
        } else {
          for (int k = 0; k < passRoundCommFieldArray.length; k++)
            passRoundCommField = String.valueOf(passRoundCommField) + "$" + passRoundCommFieldArray[k] + "$"; 
        } 
      } 
      if (action.equals("trueModify")) {
        String readFieldTemp = httpServletRequest.getParameter("canReadFieldIds");
        String writeFieldTemp = httpServletRequest.getParameter("canWriteFieldIds");
        if (!"".equals(readFieldTemp)) {
          readField = readFieldTemp.split(",");
        } else {
          readField = new String[0];
        } 
        if (!"".equals(writeFieldTemp)) {
          writeField = writeFieldTemp.split(",");
        } else {
          writeField = new String[0];
        } 
        String noDisplayFieldTemp = httpServletRequest.getParameter("noDisplayFieldIds");
        if (!"".equals(noDisplayFieldTemp)) {
          noDisplayField = noDisplayFieldTemp.split(",");
        } else {
          noDisplayField = new String[0];
        } 
      } else {
        readField = httpServletRequest.getParameterValues("readField");
        writeField = httpServletRequest.getParameterValues("writeField");
        noDisplayField = httpServletRequest.getParameterValues("noDisplayField");
      } 
      String[] protectField = httpServletRequest.getParameterValues("protectField");
      String allowStandFor = activityActionForm.getAllowStandFor();
      if (allowStandFor == null)
        allowStandFor = "0"; 
      String allowCancel = "0";
      if (httpServletRequest.getParameterValues("allowCancel") != null) {
        String[] tmp = httpServletRequest.getParameterValues("allowCancel");
        if (tmp.length > 1) {
          allowCancel = "2";
        } else {
          allowCancel = tmp[0];
        } 
      } 
      String allowTransition = activityActionForm.getAllowTransition();
      if (allowTransition == null)
        allowTransition = "0"; 
      String allowSmsRemind = activityActionForm.getAllowSmsRemind();
      if (allowSmsRemind == null)
        allowSmsRemind = "0"; 
      String pressType = activityActionForm.getPressType();
      String deadLineTime = "0";
      String pressMotionTime = "0";
      if (pressType.equals("1")) {
        deadLineTime = httpServletRequest.getParameter("pressLimit");
        if (httpServletRequest.getParameter("isMotion") != null)
          pressMotionTime = httpServletRequest.getParameter("pressMotionTime"); 
      } 
      String pressDealType = "-1";
      if (pressType.equals("1") || pressType.equals("2")) {
        String[] tmp = httpServletRequest.getParameterValues("pressDealType");
        if (tmp != null)
          if (tmp.length > 1) {
            pressDealType = "2";
          } else if (tmp[0].equals("1")) {
            pressDealType = "1";
          } else if (tmp[0].equals("0")) {
            pressDealType = "0";
          }  
      } 
      String actiCommType = httpServletRequest.getParameter("actiCommType");
      String actiCommField = "";
      String[] actiCommFieldArray = httpServletRequest.getParameterValues("actiCommField");
      if (actiCommFieldArray == null || actiCommFieldArray.length == 0) {
        actiCommField = "-1";
      } else if (actiCommFieldArray.length == 1) {
        actiCommField = actiCommFieldArray[0];
      } else {
        for (int k = 0; k < actiCommFieldArray.length; k++)
          actiCommField = String.valueOf(actiCommField) + "$" + actiCommFieldArray[k] + "$"; 
      } 
      String formClassMethod = httpServletRequest.getParameter("formClassMethod");
      String activityClass = httpServletRequest.getParameter("activityClass");
      String activitySubProc = "0";
      String subProcType = "0";
      String extendMainTable = "0";
      if (activityClass.equals("0")) {
        activitySubProc = httpServletRequest.getParameter("activitySubProc");
        subProcType = httpServletRequest.getParameter("subProcType");
        transactType = "0";
        extendMainTable = (httpServletRequest.getParameter("extendMainTable") == null) ? "0" : httpServletRequest.getParameter("extendMainTable");
      } 
      String participantGivenOrgName = httpServletRequest.getParameter("participantGivenOrgName");
      String participantGivenOrg = httpServletRequest.getParameter("participantGivenOrg");
      String passRoundGivenOrgName = httpServletRequest.getParameter("passRoundGivenOrgName");
      String passRoundGivenOrg = httpServletRequest.getParameter("passRoundGivenOrg");
      if (participantType.equals("13")) {
        participantGivenOrgName = httpServletRequest.getParameter("participantGivenGroupName");
        participantGivenOrg = httpServletRequest.getParameter("participantGivenGroup");
      } 
      if (passRoundUserType == 13) {
        passRoundGivenOrgName = httpServletRequest.getParameter("passRoundGivenGroupName");
        passRoundGivenOrg = httpServletRequest.getParameter("passRoundGivenGroup");
      } 
      String untreadMethod = httpServletRequest.getParameter("untreadMethod");
      String printFileSeeScope = httpServletRequest.getParameter("printFileSeeScope");
      String printFileSeeOrg = httpServletRequest.getParameter("printFileSeeOrg");
      String printFileSeeGroup = httpServletRequest.getParameter("printFileSeeGroup");
      String printFileSeePerson = httpServletRequest.getParameter("printFileSeePerson");
      String[] operButton = httpServletRequest.getParameterValues("operButton");
      String operButtonStr = "";
      for (int j = 0; operButton != null && j < operButton.length; j++)
        operButtonStr = String.valueOf(operButtonStr) + operButton[j]; 
      String tranType = httpServletRequest.getParameter("tranType");
      String tranCustomExtent = httpServletRequest.getParameter("tranCustomExtent");
      String tranCustomExtentId = httpServletRequest.getParameter("tranCustomExtentId");
      if (!tranType.endsWith("4")) {
        tranCustomExtent = "";
        tranCustomExtentId = "";
      } 
      String tranReadType = httpServletRequest.getParameter("tranReadType");
      String tranReadCustomExtent = httpServletRequest.getParameter("tranReadCustomExtent");
      String tranReadCustomExtentId = httpServletRequest.getParameter("tranReadCustomExtentId");
      if (!tranReadType.endsWith("4")) {
        tranReadCustomExtent = "";
        tranReadCustomExtentId = "";
      } 
      String handleType = httpServletRequest.getParameter("handleType");
      String handleClass = "";
      String handleMethod = "";
      String handleParam = "";
      String handleView = "";
      if ("4".equals(handleType)) {
        handleClass = httpServletRequest.getParameter("handleClass");
        handleMethod = httpServletRequest.getParameter("handleMethod");
        handleParam = httpServletRequest.getParameter("handleParam");
      } else {
        handleView = httpServletRequest.getParameter("handleView");
      } 
      String webServiceUrl = "", webServiceMethod = "", webServicePara = "", webServiceNameSpace = "";
      if (httpServletRequest.getParameter("ifUseWebService") != null) {
        webServiceUrl = httpServletRequest.getParameter("WebServiceUrl");
        webServiceMethod = httpServletRequest.getParameter("WebServiceMethod");
        webServicePara = httpServletRequest.getParameter("WebServicePara");
        webServiceNameSpace = httpServletRequest.getParameter("WebServiceNameSpace");
      } 
      String sendMsgToInitiator = (httpServletRequest.getParameter("sendMsgToInitiator") == null) ? "0" : 
        httpServletRequest.getParameter("sendMsgToInitiator");
      String opinionmust = (httpServletRequest.getParameter("opinionmust") == null) ? "0" : "1";
      String handSignType = (httpServletRequest.getParameter("handSignType") == null) ? "0" : httpServletRequest.getParameter("handSignType");
      String sendMsgToDealDone = (httpServletRequest.getParameter("sendMsgToDealDone") == null) ? "0" : 
        httpServletRequest.getParameter("sendMsgToDealDone");
      String actiCommFieldType = httpServletRequest.getParameter("actiCommFieldType");
      String passRoundCommFieldType = httpServletRequest.getParameter("passRoundCommFieldType");
      String exeScript = httpServletRequest.getParameter("exeScript");
      String beforeSubmit = httpServletRequest.getParameter("beforeSubmit");
      String opinionLengthSet = (httpServletRequest.getParameter("opinionLengthSet") == null) ? "0" : "1";
      String opinionLengthMin = (httpServletRequest.getParameter("opinionLengthMin") == null) ? "10" : httpServletRequest.getParameter("opinionLengthMin");
      String opinionLengthMax = (httpServletRequest.getParameter("opinionLengthMax") == null) ? "1000" : httpServletRequest.getParameter("opinionLengthMax");
      String allowAutoCheck = (httpServletRequest.getParameter("allowAutoCheck") == null) ? "0" : httpServletRequest.getParameter("allowAutoCheck");
      String isDivide = (httpServletRequest.getParameter("isDivide") == null) ? "0" : httpServletRequest.getParameter("isDivide");
      String isGather = (httpServletRequest.getParameter("isGather") == null) ? "0" : httpServletRequest.getParameter("isGather");
      String isBranch = (httpServletRequest.getParameter("isBranch") == null) ? "0" : httpServletRequest.getParameter("isBranch");
      String[] activityParameter = { 
          processId, activityName, activityDescription, activityType, 
          allowStandFor, allowCancel, allowTransition, participantType, 
          participantUser, participantUserName, 
          participantUserField, 
          pressType, deadLineTime, pressMotionTime, participantGroup, 
          transactType, actiCommType, actiCommField, needPassRound, (
          new StringBuilder(String.valueOf(passRoundUserType))).toString(), 
          passRoundUser, passRoundUserGroup, 
          passRoundUserName, passRoundUserField, passRoundCommField, 
          partRole, passRole, activityClass, activitySubProc, subProcType, 
          formClassMethod, participantGivenOrgName, participantGivenOrg, 
          passRoundGivenOrgName, passRoundGivenOrg, untreadMethod, 
          httpServletRequest.getParameter("commentRange"), 
          httpServletRequest.getParameter("commentRangeName"), printFileSeeScope, printFileSeeOrg, 
          printFileSeeGroup, printFileSeePerson, operButtonStr, actiCommFieldType, passRoundCommFieldType, 
          allowSmsRemind, tranType, tranCustomExtent, tranCustomExtentId, pressDealType, 
          extendMainTable, 
          tranReadType, tranReadCustomExtent, tranReadCustomExtentId, exeScript, 
          handleType, handleClass, handleMethod, handleView, handleParam, 
          webServiceUrl, webServiceMethod, webServicePara, webServiceNameSpace, 
          sendMsgToInitiator, opinionmust, handSignType, beforeSubmit, 
          opinionLengthSet, opinionLengthMin, 
          opinionLengthMax, allowAutoCheck, isDivide, isGather, isBranch, sendMsgToDealDone };
      ActivityBD newActivityBD = new ActivityBD();
      if (pressType.equals("0") || pressType.equals("1")) {
        if (action.equals("trueModify")) {
          String activityId = httpServletRequest.getParameter("activityId");
          newActivityBD.updateWithoutCondition(activityParameter, readField, writeField, noDisplayField, protectField, activityId, httpServletRequest.getParameter("formId"));
        } else {
          newActivityBD.addWithoutCondition(activityParameter, readField, writeField, protectField, httpServletRequest.getParameter("formId"));
        } 
      } else if (pressType.equals("2")) {
        String[] condition = httpServletRequest.getParameterValues("condition");
        String[] operate = httpServletRequest.getParameterValues("operate");
        String[] compareValue = httpServletRequest.getParameterValues("compareValue");
        String[] timeLimit = httpServletRequest.getParameterValues("timeLimit");
        String[] motionTime = httpServletRequest.getParameterValues("motionTime");
        if (action.equals("trueModify")) {
          String activityId = httpServletRequest.getParameter("activityId");
          newActivityBD.updateWithCondition(activityParameter, readField, writeField, noDisplayField, protectField, condition, operate, compareValue, timeLimit, motionTime, activityId, httpServletRequest.getParameter("formId"));
        } else {
          newActivityBD.addWithCondition(activityParameter, readField, writeField, protectField, condition, operate, compareValue, timeLimit, motionTime, httpServletRequest.getParameter("formId"));
        } 
      } 
      activityActionForm.setActivityName("");
      activityActionForm.setActivityDescription("");
    } else if (action.equals("singleDelete") || action.equals("batchDelete") || action.equals("deleteAll")) {
      ActivityBD activityBD = new ActivityBD();
      if (action.equals("singleDelete") || action.equals("batchDelete")) {
        String ids = "";
        if (action.equals("singleDelete")) {
          ids = httpServletRequest.getParameter("activityId");
        } else {
          String[] batchId = httpServletRequest.getParameterValues("batchId");
          for (int i = 0; i < batchId.length; i++)
            ids = String.valueOf(ids) + batchId[i] + ","; 
          ids = ids.substring(0, ids.length() - 1);
        } 
        activityBD.remove(ids);
      } else {
        activityBD.removeAll(processId);
      } 
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      tag = "view";
      list(httpServletRequest, processId);
    } else if (action.equals("relation")) {
      tag = "relation";
      List<Object[]> processList = (new ProcessBD()).getProcInfo(httpServletRequest.getParameter("processId"));
      if (processList != null && processList.size() > 0) {
        Object[] obj = processList.get(0);
        httpServletRequest.setAttribute("formType", obj[23]);
      } else {
        httpServletRequest.setAttribute("formType", "0");
      } 
      String activityId = httpServletRequest.getParameter("activityId");
      String activityType = httpServletRequest.getParameter("activityType");
      httpServletRequest.setAttribute("activityType", activityType);
      httpServletRequest.setAttribute("activityId", activityId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("activityName", httpServletRequest.getParameter("activityName"));
      httpServletRequest.setAttribute("activityClass", String.valueOf((new ActivityBD()).getActivityInfo(activityId).getActivityClass()));
      ActivityBD activityBD = new ActivityBD();
      httpServletRequest.setAttribute("fromActivityList", activityBD.getFromActivity(activityId));
      WorkFlowBD workFlowBD = new WorkFlowBD();
      httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
      httpServletRequest.setAttribute("activityList", activityBD.getActivity(processId));
      httpServletRequest.setAttribute("toActivityList", activityBD.getToActivity(activityId));
    } else if (action.equals("relationAdd")) {
      ActivityBD activityBD = new ActivityBD();
      String activityType = httpServletRequest.getParameter("activityType");
      String activityId = httpServletRequest.getParameter("activityId");
      processId = httpServletRequest.getParameter("processId");
      String defaultActivity = httpServletRequest.getParameter("defaultActivity");
      ArrayList<String> alist = new ArrayList();
      alist.add(" delete from jsf_transition where transitionFrom = " + activityId);
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.updateTable(alist);
      if (activityType != null && activityType.equals("-1")) {
        String tempActivityId = "0";
        Connection conn = null;
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("select wf_activity_id from jsf_activity where wf_workflowprocess_id=" + 
              processId + " and ActivityBeginEnd = 1");
          if (rs.next())
            tempActivityId = rs.getString(1); 
          rs.close();
          stmt.close();
          conn.close();
        } catch (Exception err) {
          if (conn != null)
            try {
              conn.close();
            } catch (Exception err2) {
              err2.printStackTrace();
            }  
          err.printStackTrace();
        } 
        alist.add(" delete from jsf_transition where transitionFrom = " + tempActivityId);
        workFlowBD.updateTable(alist);
        String startActivity = httpServletRequest.getParameter("startActivity");
        String[] toActivityIdValue = httpServletRequest.getParameterValues("toActivityIdValue");
        String[] toConditionValue = httpServletRequest.getParameterValues("toConditionValue");
        String[] toOperateValue = httpServletRequest.getParameterValues("toOperateValue");
        String[] toCompareValue = httpServletRequest.getParameterValues("toCompareValue");
        String[] expression = httpServletRequest.getParameterValues("expression");
        activityBD.setStartActivity(activityId, startActivity);
        activityBD.setActivity(toActivityIdValue, toConditionValue, toOperateValue, toCompareValue, activityId, expression, "to", defaultActivity);
      } else {
        String[] expression = httpServletRequest.getParameterValues("expression");
        String[] toActivityIdValue = httpServletRequest.getParameterValues("toActivityIdValue");
        String[] toConditionValue = httpServletRequest.getParameterValues("toConditionValue");
        String[] toOperateValue = httpServletRequest.getParameterValues("toOperateValue");
        String[] toCompareValue = httpServletRequest.getParameterValues("toCompareValue");
        activityBD.setActivity(toActivityIdValue, toConditionValue, toOperateValue, toCompareValue, activityId, expression, "to", defaultActivity);
      } 
      tag = "close";
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("processId", processId);
    } else if (action.equals("modify")) {
      tag = "modify";
      Date date = new Date();
      FormBD formBD = new FormBD();
      List<Object[]> temp = formBD.getSingleForm(httpServletRequest.getParameter("tableId"));
      if (temp != null && temp.size() > 0) {
        Object[] obj = temp.get(0);
        httpServletRequest.setAttribute("realTableName", obj[6]);
      } 
      if ("2".equals(httpServletRequest.getParameter("moduleId")) || "3".equals(httpServletRequest.getParameter("moduleId"))) {
        FormPageEJBBean bean = new FormPageEJBBean();
        try {
          httpServletRequest.setAttribute("formDocList", bean.getFormDocBaseInfo(httpServletRequest));
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
      httpServletRequest.setAttribute("formList", formBD.getFormBaseInfo(httpServletRequest.getSession(true).getAttribute("domainId").toString()));
      ActivityBD activityBD = new ActivityBD();
      String activityId = httpServletRequest.getParameter("activityId");
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("activityId", activityId);
      WFActivityPO activityPO = activityBD.getActivityInfo(activityId);
      httpServletRequest.setAttribute("handleType", Integer.valueOf(activityPO.getHandleType()));
      httpServletRequest.setAttribute("handleClass", activityPO.getHandleClass());
      httpServletRequest.setAttribute("handleMethod", activityPO.getHandleMethod());
      httpServletRequest.setAttribute("handleParam", activityPO.getHandleParam());
      httpServletRequest.setAttribute("handleView", activityPO.getHandleView());
      httpServletRequest.setAttribute("webServiceUrl", activityPO.getWebServiceUrl());
      httpServletRequest.setAttribute("webServiceMethod", activityPO.getWebServiceMethod());
      httpServletRequest.setAttribute("webServicePara", activityPO.getWebServicePara());
      httpServletRequest.setAttribute("webServiceNameSpace", activityPO.getWebServiceNameSpace());
      httpServletRequest.setAttribute("sendMsgToInitiator", activityPO.getSendMsgToInitiator());
      httpServletRequest.setAttribute("sendMsgToDealDone", activityPO.getSendMsgToDealDone());
      httpServletRequest.setAttribute("opinionmust", (new StringBuilder(String.valueOf(activityPO.getOpinionmust()))).toString());
      httpServletRequest.setAttribute("handSignType", activityPO.getHandSignType());
      httpServletRequest.setAttribute("opinionLengthSet", activityPO.getOpinionLengthSet());
      httpServletRequest.setAttribute("opinionLengthMin", activityPO.getOpinionLengthMin());
      httpServletRequest.setAttribute("opinionLengthMax", activityPO.getOpinionLengthMax());
      activityActionForm.setActivityName(activityPO.getActivityName());
      httpServletRequest.setAttribute("activityType", (new StringBuilder(String.valueOf(activityPO.getActivityType()))).toString());
      httpServletRequest.setAttribute("formId", (new StringBuilder(String.valueOf(activityPO.getFormId()))).toString());
      activityActionForm.setActivityDescription(activityPO.getActivityDescription());
      int pressType = activityPO.getPressType();
      httpServletRequest.setAttribute("pressType", (new StringBuilder(String.valueOf(pressType))).toString());
      if (pressType == 1) {
        httpServletRequest.setAttribute("pressLimit", (new StringBuilder(String.valueOf(activityPO.getDeadLineTime()))).toString());
        httpServletRequest.setAttribute("pressMotionTime", (new StringBuilder(String.valueOf(activityPO.getPressMotionTime()))).toString());
      } else if (pressType == 2) {
        Set pressSet = activityPO.getWfPress();
        httpServletRequest.setAttribute("pressSet", pressSet);
      } 
      httpServletRequest.setAttribute("pressDealType", (new StringBuilder(String.valueOf(activityPO.getPressDealType()))).toString());
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      httpServletRequest.setAttribute("chanActiType", new Boolean(moduleVO.isChanActiType()));
      httpServletRequest.setAttribute("actiType", (new StringBuilder(String.valueOf(moduleVO.getActiType()))).toString());
      httpServletRequest.setAttribute("chanActiClass", new Boolean(moduleVO.isChanActiClass()));
      httpServletRequest.setAttribute("actiClass", (new StringBuilder(String.valueOf(moduleVO.getActiClass()))).toString());
      if (moduleVO.isChanActiClass())
        httpServletRequest.setAttribute("userProc", (new ProcessBD()).getAllUserProcess(String.valueOf(moduleId))); 
      httpServletRequest.setAttribute("activityClass", (new StringBuilder(String.valueOf(activityPO.getActivityClass()))).toString());
      httpServletRequest.setAttribute("activitySubProc", activityPO.getActivitySubProc());
      httpServletRequest.setAttribute("subProcType", (new StringBuilder(String.valueOf(activityPO.getSubProcType()))).toString());
      httpServletRequest.setAttribute("chanTranType", new Boolean(moduleVO.isChanTranType()));
      httpServletRequest.setAttribute("actiCommFieldType", (new StringBuilder(String.valueOf(activityPO.getActiCommFieldType()))).toString());
      httpServletRequest.setAttribute("passRoundCommFieldType", (new StringBuilder(String.valueOf(activityPO.getPassRoundCommFieldType()))).toString());
      if ("1".equals(httpServletRequest.getParameter("moduleId"))) {
        httpServletRequest.setAttribute("actiCommType", "1");
      } else {
        httpServletRequest.setAttribute("actiCommType", (new StringBuilder(String.valueOf(moduleVO.getActiCommType()))).toString());
      } 
      httpServletRequest.setAttribute("actiCommField", activityPO.getActiCommField());
      WorkFlowBD workFlowBD = new WorkFlowBD();
      httpServletRequest.setAttribute("fieldList", workFlowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
      int participantType = activityPO.getParticipantType();
      httpServletRequest.setAttribute("participantType", (new StringBuilder(String.valueOf(participantType))).toString());
      String participantUser = activityPO.getParticipantUser();
      if (participantUser == null)
        participantUser = ""; 
      String participantGroup = activityPO.getParticipantGroup();
      if (participantGroup == null)
        participantGroup = ""; 
      httpServletRequest.setAttribute("participantUser", String.valueOf(participantUser) + participantGroup);
      String participantUserName = activityPO.getParticipantUserName();
      httpServletRequest.setAttribute("participantUserName", participantUserName);
      httpServletRequest.setAttribute("participantUserField", activityPO.getParticipantUserField());
      Set rwControlSet = activityPO.getWfReadWriteControl();
      httpServletRequest.setAttribute("rwControlSet", rwControlSet);
      if ((rwControlSet == null || rwControlSet.size() < 1) && "1".equals(httpServletRequest.getParameter("moduleId"))) {
        httpServletRequest.setAttribute("ALLFieldList", workFlowBD.getSimpleField(httpServletRequest.getParameter("moduleId"), httpServletRequest.getParameter("tableId")));
      } else {
        List tmpList = workFlowBD.getSimpleField(httpServletRequest.getParameter("moduleId"), httpServletRequest.getParameter("tableId"), rwControlSet);
        if (tmpList != null) {
          httpServletRequest.setAttribute("ALLFieldList", tmpList);
        } else {
          httpServletRequest.setAttribute("ALLFieldList", new ArrayList());
        } 
      } 
      Set ptControlSet = activityPO.getWfProtectControl();
      httpServletRequest.setAttribute("ptControlSet", ptControlSet);
      List notPtList = workFlowBD.getNotProtectField(httpServletRequest.getParameter("moduleId"), httpServletRequest.getParameter("tableId"), ptControlSet);
      if (notPtList != null) {
        httpServletRequest.setAttribute("NotPtFieldList", notPtList);
      } else {
        httpServletRequest.setAttribute("NotPtFieldList", new ArrayList());
      } 
      httpServletRequest.setAttribute("allowStandFor", (new StringBuilder(String.valueOf(activityPO.getAllowStandFor()))).toString());
      httpServletRequest.setAttribute("allowCancel", (new StringBuilder(String.valueOf(activityPO.getAllowCancel()))).toString());
      httpServletRequest.setAttribute("allowTransition", (new StringBuilder(String.valueOf(activityPO.getAllowTransition()))).toString());
      httpServletRequest.setAttribute("allowSmsRemind", (new StringBuilder(String.valueOf(activityPO.getAllowSmsRemind()))).toString());
      httpServletRequest.setAttribute("transactType", (new StringBuilder(String.valueOf(activityPO.getTransactType()))).toString());
      httpServletRequest.setAttribute("needPassRound", (new StringBuilder(String.valueOf(activityPO.getNeedPassRound()))).toString());
      httpServletRequest.setAttribute("passRoundUserType", (new StringBuilder(String.valueOf(activityPO.getPassRoundUserType()))).toString());
      httpServletRequest.setAttribute("passRoundUser", activityPO.getPassRoundUser());
      httpServletRequest.setAttribute("passRoundUserGroup", activityPO.getPassRoundUserGroup());
      httpServletRequest.setAttribute("passRoundUserName", activityPO.getPassRoundUserName());
      httpServletRequest.setAttribute("passRoundUserField", activityPO.getPassRoundUserField());
      httpServletRequest.setAttribute("passRoundCommField", activityPO.getPassRoundCommField());
      httpServletRequest.setAttribute("formClassMethod", (activityPO.getFormClassMethod() == null) ? moduleVO.getActivityFormMethod() : activityPO.getFormClassMethod());
      httpServletRequest.setAttribute("untreadMethod", (activityPO.getUntreadMethod() == null) ? moduleVO.getActivityFormMethod() : activityPO.getUntreadMethod());
      httpServletRequest.setAttribute("operButton", activityPO.getOperButton());
      httpServletRequest.setAttribute("tranType", activityPO.getTranType());
      httpServletRequest.setAttribute("tranCustomExtent", activityPO.getTranCustomExtent());
      httpServletRequest.setAttribute("tranCustomExtentId", activityPO.getTranCustomExtentId());
      httpServletRequest.setAttribute("tranReadType", activityPO.getTranReadType());
      httpServletRequest.setAttribute("tranReadCustomExtent", activityPO.getTranReadCustomExtent());
      httpServletRequest.setAttribute("tranReadCustomExtentId", activityPO.getTranReadCustomExtentId());
      httpServletRequest.setAttribute("extendMainTable", activityPO.getExtendMainTable());
      httpServletRequest.setAttribute("exeScript", activityPO.getExeScript());
      httpServletRequest.setAttribute("beforeSubmit", activityPO.getBeforeSubmit());
      try {
        List dutyList = (new ProcessStep()).listDutyLevel(httpServletRequest.getSession(true).getAttribute("domainId").toString());
        httpServletRequest.setAttribute("dutyList", dutyList);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      String partRole = "", partRoleNexus = "", partRoleOrg = "";
      if (activityPO.getParticipantRole() != null) {
        String tmp = activityPO.getParticipantRole();
        if (tmp.indexOf("and") > 0) {
          partRoleNexus = "and";
          partRole = tmp.split("and")[0];
          partRoleOrg = tmp.split("and")[1];
        } else {
          partRole = tmp;
        } 
      } 
      httpServletRequest.setAttribute("partRole", partRole);
      httpServletRequest.setAttribute("partRoleNexus", partRoleNexus);
      httpServletRequest.setAttribute("partRoleOrg", partRoleOrg);
      httpServletRequest.setAttribute("partPosition", partRole);
      httpServletRequest.setAttribute("partPositionNexus", partRoleNexus);
      httpServletRequest.setAttribute("partPositionOrg", partRoleOrg);
      String dutyLevelOperate = "", dutyLevel = "";
      if (activityPO.getParticipantRole() != null) {
        String tmp = activityPO.getParticipantRole();
        if (tmp.indexOf("|") > 0) {
          dutyLevelOperate = tmp.split("\\|")[0];
          dutyLevel = tmp.split("\\|")[1];
        } 
      } 
      httpServletRequest.setAttribute("dutyLevelOperate", dutyLevelOperate);
      httpServletRequest.setAttribute("dutyLevel", dutyLevel);
      String passRole = "", passRoleNexus = "", passOrg = "";
      if (activityPO.getPassRoundRole() != null) {
        String tmp = activityPO.getPassRoundRole();
        if (tmp.indexOf("and") > 0) {
          passRoleNexus = "and";
          passRole = tmp.split("and")[0];
          passOrg = tmp.split("and")[1];
        } else {
          passRole = tmp;
        } 
      } 
      httpServletRequest.setAttribute("passRole", passRole);
      httpServletRequest.setAttribute("passRoleNexus", passRoleNexus);
      httpServletRequest.setAttribute("passOrg", passOrg);
      httpServletRequest.setAttribute("passPosition", passRole);
      httpServletRequest.setAttribute("passPositionNexus", passRoleNexus);
      httpServletRequest.setAttribute("passPositionOrg", passOrg);
      String passDutyLevelOperate = "", passDutyLevel = "";
      if (activityPO.getPassRoundRole() != null) {
        String tmp = activityPO.getPassRoundRole();
        if (tmp.indexOf("|") > 0) {
          passDutyLevelOperate = tmp.split("\\|")[0];
          passDutyLevel = tmp.split("\\|")[1];
        } 
      } 
      httpServletRequest.setAttribute("passDutyLevelOperate", passDutyLevelOperate);
      httpServletRequest.setAttribute("passDutyLevel", passDutyLevel);
      httpServletRequest.setAttribute("orgList", workFlowBD.getOrg(session.getAttribute("domainId").toString()));
      httpServletRequest.setAttribute("roleList", workFlowBD.getRole(session.getAttribute("domainId").toString()));
      httpServletRequest.setAttribute("participantGivenOrgName", (activityPO.getParticipantGivenOrgName() == null) ? "" : activityPO.getParticipantGivenOrgName());
      httpServletRequest.setAttribute("participantGivenOrg", (activityPO.getParticipantGivenOrg() == null) ? "" : activityPO.getParticipantGivenOrg());
      httpServletRequest.setAttribute("passRoundGivenOrgName", (activityPO.getPassRoundGivenOrgName() == null) ? "" : activityPO.getPassRoundGivenOrgName());
      httpServletRequest.setAttribute("passRoundGivenOrg", (activityPO.getPassRoundGivenOrg() == null) ? "" : activityPO.getPassRoundGivenOrg());
      httpServletRequest.setAttribute("participantGivenGroupName", (participantType != 13 || activityPO.getParticipantGivenOrgName() == null) ? "" : activityPO.getParticipantGivenOrgName());
      httpServletRequest.setAttribute("participantGivenGroup", (participantType != 13 || activityPO.getParticipantGivenOrg() == null) ? "" : activityPO.getParticipantGivenOrg());
      httpServletRequest.setAttribute("passRoundGivenGroupName", (activityPO.getPassRoundUserType() != 13 || activityPO.getPassRoundGivenOrgName() == null) ? "" : activityPO.getPassRoundGivenOrgName());
      httpServletRequest.setAttribute("passRoundGivenGroup", (activityPO.getPassRoundUserType() != 13 || activityPO.getPassRoundGivenOrg() == null) ? "" : activityPO.getPassRoundGivenOrg());
      httpServletRequest.setAttribute("commentRange", (activityPO.getCommentRange() == null) ? "" : activityPO.getCommentRange());
      httpServletRequest.setAttribute("commentRangeName", (activityPO.getCommentRangeName() == null) ? "" : activityPO.getCommentRangeName());
      httpServletRequest.setAttribute("allowAutoCheck", (activityPO.getAllowAutoCheck() == null) ? "0" : activityPO.getAllowAutoCheck());
      httpServletRequest.setAttribute("isDivide", (activityPO.getIsDivide() == null) ? "0" : activityPO.getIsDivide());
      httpServletRequest.setAttribute("isGather", (activityPO.getIsGather() == null) ? "0" : activityPO.getIsGather());
      httpServletRequest.setAttribute("isBranch", (activityPO.getIsBranch() == null) ? "0" : activityPO.getIsBranch());
    } else if ("modifyfirst".equals(action)) {
      tag = "modifyfirst";
      WorkFlowBD workflowBD = new WorkFlowBD();
      httpServletRequest.setAttribute("fieldList", workflowBD.getSimpleField((new StringBuilder(String.valueOf(moduleId))).toString(), tableId));
      ProcessBD processBD = new ProcessBD();
      httpServletRequest.setAttribute("noWriteFieldList", processBD.getNoWriteField(processId));
      httpServletRequest.setAttribute("noDisplayFieldList", processBD.getNoDisplayField(processId));
      System.out.println("");
    } else if ("updatefirstend".equals(action)) {
      tag = "close";
      String activityName = httpServletRequest.getParameter("activityName");
      String nowriteField = httpServletRequest.getParameter("nowriteField");
      String noDisplayField = httpServletRequest.getParameter("noDisplayField");
      String activityId = httpServletRequest.getParameter("activityId");
      ProcessBD processBD = new ProcessBD();
      processBD.updateFirstActivity(processId, activityId, activityName, nowriteField, noDisplayField, httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } 
    Date endDate = new Date();
    if (action.equals("continue") || action.equals("close")) {
      oprType = "1";
      oprContent = "新增节点:" + httpServletRequest.getParameter("activityName").toString();
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("trueModify")) {
      oprType = "2";
      oprContent = "修改节点:" + httpServletRequest.getParameter("activityName").toString();
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("relationAdd")) {
      oprType = "1";
      oprContent = "节点关系设置";
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("singleDelete")) {
      oprType = "3";
      oprContent = "删除节点";
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("batchDelete")) {
      oprType = "3";
      oprContent = "批量删除节点";
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } else if (action.equals("deleteAll")) {
      oprType = "3";
      oprContent = "清空节点";
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), httpServletRequest.getSession(true).getAttribute("userName").toString(), httpServletRequest.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, httpServletRequest.getRemoteAddr(), httpServletRequest.getSession(true).getAttribute("domainId").toString());
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest httpServletRequest, String processId) {
    HttpSession session = httpServletRequest.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String viewSQL = " aaa.wfActivityId, aaa.activityName, aaa.activityDescription, aaa.activityType, aaa.activityClass ";
    String fromSQL = " com.js.oa.jsflow.po.WFActivityPO aaa join aaa.wfWorkFlowProcess bbb";
    String whereSQL = " where bbb.wfWorkFlowProcessId = " + processId + " and aaa.activityBeginEnd = 0 order by aaa.wfActivityId desc ";
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("activityList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,processId,tableId,moduleId");
  }
}
