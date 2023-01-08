package com.js.oa.hr.subsidiarywork.action;

import com.js.oa.hr.subsidiarywork.bean.QuestionnaireEJBBean;
import com.js.oa.hr.subsidiarywork.po.AnswerSheetPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import com.js.oa.hr.subsidiarywork.service.QuestionnaireBD;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ConversionString;
import com.js.util.util.FillBean;
import com.js.util.util.StringSplit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class QuestionnaireAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = httpServletRequest.getParameter("action");
    QuestionnaireActionForm questionnaireActionForm = 
      (QuestionnaireActionForm)actionForm;
    QuestionnaireBD questionnaireBD = new QuestionnaireBD();
    ManagerService managerBD = new ManagerService();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String userName = session.getAttribute("userName").toString();
    Long orgID = new Long(session.getAttribute("orgId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    if ("viewMyQuestionare".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0)
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList); 
      List questhemeEssayList = (List)map.get("questhemeEssay");
      if (questhemeEssayList.size() != 0)
        httpServletRequest.setAttribute("questhemeEssayList", 
            questhemeEssayList); 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      String from = 
        "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO";
      String where = "answerSheetPO.ballotEmp ='" + userID + 
        "' and answerSheetPO.questionnaireId =" + 
        questionnaireId;
      List isMyAnswer = questionnaireBD.isMyAnswer(from, where);
      if (isMyAnswer != null && isMyAnswer.size() > 0) {
        questionnaireActionForm.setSaveType("isRepeatName");
        httpServletRequest.setAttribute("isMyAnswer", isMyAnswer);
      } 
      return actionMapping.findForward("viewMyQuestionare");
    } 
    if ("questionnaireListView".equals(action)) {
      String answerWhere = managerBD.getRightFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "问卷管理", "维护", 
          "questionnairePO.cratedOrg", 
          "questionnairePO.createdEmp");
      String userId = session.getAttribute("userId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = null;
      try {
        CustomDesktopBD customDesktopBD = new CustomDesktopBD();
        groupList = customDesktopBD.getGroupById(userId);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      buffer = new StringBuffer("( (");
      if (!groupList.isEmpty())
        for (int k = 0; k < groupList.size(); k++)
          buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(k)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(k)).append("%' ) or ");  
      for (int i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append("%' ) or "); 
      } 
      buffer.append(" ( questionnairePO.actorEmp like '%").append(userId).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userId).append("%' )");
      buffer.append(") or ");
      buffer.append(answerWhere);
      buffer.append(")");
      questionnaireList(httpServletRequest, buffer.toString());
      List list = new ArrayList();
      list = questionnaireBD.searchIdByVindicate(answerWhere.toString(), domainId);
      StringBuffer buffer1 = new StringBuffer("(");
      if (!groupList.isEmpty())
        for (int k = 0; k < groupList.size(); k++)
          buffer1.append(" questionnairePO.actorGroup like '%").append(groupList.get(k)).append("%'  or ");  
      for (int j = 0; j < orgIdArray.length; j++) {
        if (!"".equals(orgIdArray[j]))
          buffer1.append(" questionnairePO.actorOrg like '%").append(orgIdArray[j]).append("%' or "); 
      } 
      buffer1.append(" questionnairePO.actorEmp like '%").append(userId).append("%'");
      buffer1.append(")");
      List list1 = new ArrayList();
      list1 = questionnaireBD.searchIdByExamine(buffer1.toString(), domainId);
      httpServletRequest.setAttribute("vindicate", list);
      httpServletRequest.setAttribute("examineList", list1);
      boolean isAddRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isAddRight)
        httpServletRequest.setAttribute("isAddRight", "yes"); 
      boolean isModiRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isModiRight)
        httpServletRequest.setAttribute("isModiRight", "yes"); 
      return actionMapping.findForward("gotoQuestionnaireList");
    } 
    if ("addQuestionnaireView".equals(action))
      return actionMapping.findForward("gotoQuestionnaireAdd"); 
    if ("addQuestionnaire".equals(action)) {
      QuestionnairePO questionnairePO = 
        (QuestionnairePO)FillBean.transformOneToOne(
          questionnaireActionForm, QuestionnairePO.class);
      Date startDate = new Date(httpServletRequest.getParameter(
            "startDate"));
      questionnairePO.setStartDate(startDate);
      Date endDate = new Date(httpServletRequest.getParameter("endDate"));
      questionnairePO.setEndDate(endDate);
      ConversionString conversionActorIdString = new ConversionString(
          questionnaireActionForm.getActorId());
      String actorUserIds = conversionActorIdString.getUserString();
      questionnairePO.setActorEmp(actorUserIds);
      String actorOrgIds = conversionActorIdString.getOrgString();
      questionnairePO.setActorOrg(actorOrgIds);
      String actorGroupIds = conversionActorIdString.getGroupString();
      questionnairePO.setActorGroup(actorGroupIds);
      ConversionString conversionExamineString = new ConversionString(
          questionnaireActionForm.getExamineId());
      String examineUserIds = conversionExamineString.getUserString();
      questionnairePO.setExamineEmp(examineUserIds);
      String examineOrgIds = conversionExamineString.getOrgString();
      questionnairePO.setExamineOrg(examineOrgIds);
      String examineGroupIds = conversionExamineString.getGroupString();
      questionnairePO.setExamineGroup(examineGroupIds);
      questionnairePO.setCreatedEmp(userID);
      questionnairePO.setCratedOrg(orgID);
      questionnairePO.setDomainId(domainId);
      String from = 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO";
      String where = "questionnairePO.title ='" + 
        questionnairePO.getTitle() + 
        "' and questionnairePO.domainId=" + domainId;
      boolean isRepeatName = questionnaireBD.isRepeatName(from, where);
      if (isRepeatName) {
        questionnaireActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("gotoQuestionnaireAdd");
      } 
      boolean result = questionnaireBD.addQuestionnaire(
          questionnairePO);
      Long questionnaireId = questionnairePO.getQuestionnaireId();
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        questionnaireActionForm.reset(actionMapping, 
            httpServletRequest);
        questionnaireActionForm.setSaveType("saveAndContinue");
        if (questionnairePO.getStatus().intValue() == 1)
          sendMessages(questionnaireId, actorUserIds, actorOrgIds, examineUserIds, examineOrgIds, startDate, endDate, userName); 
        return actionMapping.findForward(
            "questionnaire_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        questionnaireActionForm.reset(actionMapping, 
            httpServletRequest);
        questionnaireActionForm.setSaveType("saveAndExit");
        if (questionnairePO.getStatus().intValue() == 1)
          sendMessages(questionnaireId, actorUserIds, actorOrgIds, examineUserIds, examineOrgIds, startDate, endDate, userName); 
        return actionMapping.findForward(
            "questionnaire_saveAndExit");
      } 
    } 
    if ("deleteQuestionnaire".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      String answerWhere = managerBD.getRightFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "问卷管理", "维护", 
          "questionnairePO.cratedOrg", 
          "questionnairePO.createdEmp");
      boolean isAddRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isAddRight)
        httpServletRequest.setAttribute("isAddRight", "yes"); 
      boolean isModiRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isModiRight)
        httpServletRequest.setAttribute("isModiRight", "yes"); 
      boolean result = questionnaireBD.deleteQuestionnaire(
          questionnaireId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        questionnaireList(httpServletRequest, offsetCopy, 
            answerWhere);
      } else {
        questionnaireList(httpServletRequest, answerWhere);
      } 
      questionnaireActionForm.reset(actionMapping, httpServletRequest);
      String url = "/jsoa/QuestionnaireAction.do?action=questionnaireListView";
      httpServletRequest.setAttribute("url", url);
      return actionMapping.findForward("forward");
    } 
    if ("deleteBatchQuestionnaire".equals(action)) {
      String questionnaireIds = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      String answerWhere = managerBD.getRightFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "问卷管理", "维护", 
          "questionnairePO.cratedOrg", 
          "questionnairePO.createdEmp");
      boolean isAddRight = managerBD.hasRightTypeName(session
          .getAttribute("userId").toString(), "问卷管理", "维护");
      if (isAddRight)
        httpServletRequest.setAttribute("isAddRight", "yes"); 
      boolean isModiRight = managerBD.hasRightTypeName(session
          .getAttribute("userId").toString(), "问卷管理", "维护");
      if (isModiRight)
        httpServletRequest.setAttribute("isModiRight", "yes"); 
      boolean result = questionnaireBD.deleteBatchQuestionnaire(
          questionnaireIds);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        questionnaireList(httpServletRequest, offsetCopy, 
            answerWhere);
      } else {
        questionnaireList(httpServletRequest, answerWhere);
      } 
      questionnaireActionForm.reset(actionMapping, httpServletRequest);
      String url = "/jsoa/QuestionnaireAction.do?action=questionnaireListView";
      httpServletRequest.setAttribute("url", url);
      return actionMapping.findForward("forward");
    } 
    if ("selectQuestionnaireSee".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      QuestionnairePO questionnairePO = questionnaireBD
        .selectQuestionnaireView(
          questionnaireId);
      questionnaireActionForm.setTitle(questionnairePO.getTitle());
      questionnaireActionForm.setActorName(questionnairePO.getActorName());
      questionnaireActionForm.setExamineName(questionnairePO
          .getExamineName());
      questionnaireActionForm.setGrade(questionnairePO.getGrade());
      questionnaireActionForm.setStatus(questionnairePO.getStatus());
      if (questionnairePO.getStartDate() != null)
        httpServletRequest.setAttribute("startDate", 
            questionnairePO.getStartDate()
            .toString()); 
      if (questionnairePO.getEndDate() != null)
        httpServletRequest.setAttribute("endDate", 
            questionnairePO.getEndDate()
            .toString()); 
      String actorUserIds = questionnairePO.getActorEmp();
      if (actorUserIds == null || "null".equals(actorUserIds))
        actorUserIds = ""; 
      String actorOrgIds = questionnairePO.getActorOrg();
      if (actorOrgIds == null || "null".equals(actorOrgIds))
        actorOrgIds = ""; 
      String actorGroupIds = questionnairePO.getActorGroup();
      if (actorGroupIds == null || "null".equals(actorGroupIds))
        actorGroupIds = ""; 
      questionnaireActionForm.setActorId(String.valueOf(actorUserIds) + actorOrgIds + 
          actorGroupIds);
      String examineUserIds = questionnairePO.getExamineEmp();
      if (examineUserIds == null || "null".equals(examineUserIds))
        examineUserIds = ""; 
      String examineOrgIds = questionnairePO.getExamineOrg();
      if (examineOrgIds == null || "null".equals(examineOrgIds))
        examineOrgIds = ""; 
      String examineGroupIds = questionnairePO.getExamineGroup();
      if (examineGroupIds == null || "null".equals(examineGroupIds))
        examineGroupIds = ""; 
      questionnaireActionForm.setExamineId(String.valueOf(examineUserIds) + examineOrgIds + 
          examineGroupIds);
      return actionMapping.findForward("gotoSeeQuestionnaire");
    } 
    if ("selectQuestionnaireView".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      QuestionnairePO questionnairePO = questionnaireBD
        .selectQuestionnaireView(
          questionnaireId);
      questionnaireActionForm.setTitle(questionnairePO.getTitle());
      questionnaireActionForm.setActorName(questionnairePO.getActorName());
      questionnaireActionForm.setExamineName(questionnairePO
          .getExamineName());
      questionnaireActionForm.setGrade(questionnairePO.getGrade());
      questionnaireActionForm.setStatus(questionnairePO.getStatus());
      if (questionnairePO.getStartDate() != null)
        httpServletRequest.setAttribute("startDate", 
            questionnairePO.getStartDate()
            .toString()); 
      if (questionnairePO.getEndDate() != null)
        httpServletRequest.setAttribute("endDate", 
            questionnairePO.getEndDate()
            .toString()); 
      String actorUserIds = questionnairePO.getActorEmp();
      if (actorUserIds == null || "null".equals(actorUserIds))
        actorUserIds = ""; 
      String actorOrgIds = questionnairePO.getActorOrg();
      if (actorOrgIds == null || "null".equals(actorOrgIds))
        actorOrgIds = ""; 
      String actorGroupIds = questionnairePO.getActorGroup();
      if (actorGroupIds == null || "null".equals(actorGroupIds))
        actorGroupIds = ""; 
      questionnaireActionForm.setActorId(String.valueOf(actorUserIds) + actorOrgIds + 
          actorGroupIds);
      String examineUserIds = questionnairePO.getExamineEmp();
      if (examineUserIds == null || "null".equals(examineUserIds))
        examineUserIds = ""; 
      String examineOrgIds = questionnairePO.getExamineOrg();
      if (examineOrgIds == null || "null".equals(examineOrgIds))
        examineOrgIds = ""; 
      String examineGroupIds = questionnairePO.getExamineGroup();
      if (examineGroupIds == null || "null".equals(examineGroupIds))
        examineGroupIds = ""; 
      questionnaireActionForm.setExamineId(String.valueOf(examineUserIds) + examineOrgIds + 
          examineGroupIds);
      httpServletRequest.setAttribute("questionnaireId", questionnaireId);
      return actionMapping.findForward("gotoModiQuestionnaire");
    } 
    if ("updateQuestionnaire".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      QuestionnairePO questionnairePO = 
        (QuestionnairePO)FillBean.transformOneToOne(
          questionnaireActionForm, QuestionnairePO.class);
      Date startDate = new Date(httpServletRequest.getParameter(
            "startDate"));
      questionnairePO.setStartDate(startDate);
      Date endDate = new Date(httpServletRequest.getParameter("endDate"));
      questionnairePO.setEndDate(endDate);
      ConversionString conversionActorIdString = new ConversionString(
          questionnaireActionForm.getActorId());
      String actorUserIds = conversionActorIdString.getUserString();
      questionnairePO.setActorEmp(actorUserIds);
      String actorOrgIds = conversionActorIdString.getOrgString();
      questionnairePO.setActorOrg(actorOrgIds);
      String actorGroupIds = conversionActorIdString.getGroupString();
      questionnairePO.setActorGroup(actorGroupIds);
      ConversionString conversionExamineString = new ConversionString(
          questionnaireActionForm.getExamineId());
      String examineUserIds = conversionExamineString.getUserString();
      questionnairePO.setExamineEmp(examineUserIds);
      String examineOrgIds = conversionExamineString.getOrgString();
      questionnairePO.setExamineOrg(examineOrgIds);
      String examineGroupIds = conversionExamineString.getGroupString();
      questionnairePO.setExamineGroup(examineGroupIds);
      questionnairePO.setCreatedEmp(userID);
      questionnairePO.setCratedOrg(orgID);
      questionnairePO.setQuestionnaireId(questionnaireId);
      String from = 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO";
      String where = "questionnairePO.title ='" + 
        questionnairePO.getTitle() + 
        "' and questionnairePO.questionnaireId <>" + 
        questionnaireId + " and questionnairePO.domainId=" + 
        domainId;
      boolean isRepeatName = questionnaireBD.isRepeatName(from, where);
      if (isRepeatName) {
        questionnaireActionForm.setSaveType("isRepeatName");
        return actionMapping.findForward("gotoQuestionnaireAdd");
      } 
      boolean result = questionnaireBD.updateQuestionnaire(
          questionnairePO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        questionnaireActionForm.reset(actionMapping, httpServletRequest);
        questionnaireActionForm.setSaveType("updateAndExit");
      } 
      if (questionnairePO.getStatus().intValue() == 1 && httpServletRequest.getParameter("initStatus").equals("close"))
        sendMessages(questionnaireId, actorUserIds, actorOrgIds, examineUserIds, examineOrgIds, startDate, endDate, userName); 
      return actionMapping.findForward(
          "questionnaire_updateAndExit");
    } 
    if ("questionnaireSearch".equals(action)) {
      String answerWhere = managerBD.getRightFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "问卷管理", "维护", 
          "questionnairePO.cratedOrg", 
          "questionnairePO.createdEmp");
      String userId = session.getAttribute("userId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = null;
      try {
        CustomDesktopBD customDesktopBD = new CustomDesktopBD();
        groupList = customDesktopBD.getGroupById(userId);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      buffer = new StringBuffer("( (");
      if (!groupList.isEmpty())
        for (int k = 0; k < groupList.size(); k++)
          buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(k)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(k)).append("%' ) or ");  
      for (int i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append("%' ) or "); 
      } 
      buffer.append(" ( questionnairePO.actorEmp like '%").append(userId).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userId).append("%' )");
      buffer.append(") or ");
      buffer.append(answerWhere);
      buffer.append(")");
      questionnaireSearchList(httpServletRequest, buffer.toString());
      List list = new ArrayList();
      list = questionnaireBD.searchIdByVindicate(answerWhere.toString(), domainId);
      StringBuffer buffer1 = new StringBuffer("(");
      if (!groupList.isEmpty())
        for (int k = 0; k < groupList.size(); k++)
          buffer1.append(" questionnairePO.actorGroup like '%").append(groupList.get(k)).append("%'  or ");  
      for (int j = 0; j < orgIdArray.length; j++) {
        if (!"".equals(orgIdArray[j]))
          buffer1.append(" questionnairePO.actorOrg like '%").append(orgIdArray[j]).append("%' or "); 
      } 
      buffer1.append(" questionnairePO.actorEmp like '%").append(userId).append("%'");
      buffer1.append(")");
      List list1 = new ArrayList();
      list1 = questionnaireBD.searchIdByExamine(buffer1.toString(), domainId);
      httpServletRequest.setAttribute("vindicate", list);
      httpServletRequest.setAttribute("examineList", list1);
      boolean isAddRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isAddRight)
        httpServletRequest.setAttribute("isAddRight", "yes"); 
      boolean isModiRight = managerBD.hasRight(session.getAttribute(
            "userId").toString(), "07*07*02");
      if (isModiRight)
        httpServletRequest.setAttribute("isModiRight", "yes"); 
      return actionMapping.findForward("gotoQuestionnaireList");
    } 
    if ("questhemeListView".equals(action)) {
      questhemeList(httpServletRequest);
      return actionMapping.findForward("gotoQuesthemeList");
    } 
    if ("addQuesthemeView".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      QuestionnairePO questionnairePO = questionnaireBD.selectQuestionnaireView(questionnaireId);
      Integer grade = questionnairePO.getGrade();
      httpServletRequest.setAttribute("grade", grade);
      return actionMapping.findForward("gotoQuesthemeAdd");
    } 
    if ("addQuestheme".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      String type = httpServletRequest.getParameter("type");
      String grade = httpServletRequest.getParameter("gradeHidden");
      String[] solutionTitle = (String[])null;
      String[] pitchon = (String[])null;
      String[] optionScore = (String[])null;
      QuesthemePO questhemePO = (QuesthemePO)FillBean.transformOneToOne(
          questionnaireActionForm, QuesthemePO.class);
      questhemePO.setDomainId(domainId);
      if (type != null && "0".equals(type)) {
        solutionTitle = httpServletRequest.getParameterValues(
            "solutionTitle");
        optionScore = httpServletRequest.getParameterValues(
            "optionScore");
        questhemePO.setOrderCode(new Float("10000"));
      } 
      if (type != null && "1".equals(type)) {
        solutionTitle = httpServletRequest.getParameterValues(
            "solutionTitle2");
        pitchon = httpServletRequest.getParameterValues("pitchon");
        questhemePO.setOrderCode(new Float("20000"));
      } 
      if (type != null && "2".equals(type))
        questhemePO.setOrderCode(new Float("30000")); 
      questhemePO.setQuestionnaireId(questionnaireId);
      questhemePO.setType(new Integer(type));
      String from = 
        "com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO";
      String where = "questhemePO.title ='" + questhemePO.getTitle() + 
        "' and questhemePO.questionnaire.questionnaireId=" + 
        questionnaireId;
      boolean isRepeatName = questionnaireBD.isRepeatName(from, where);
      if (isRepeatName) {
        questionnaireActionForm.setSaveType("isRepeatName");
        httpServletRequest.setAttribute("grade", grade);
        return actionMapping.findForward("gotoQuesthemeAdd");
      } 
      boolean result = questionnaireBD.addQuestheme(questhemePO, 
          solutionTitle, optionScore, pitchon);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        questionnaireActionForm.reset(actionMapping, 
            httpServletRequest);
        httpServletRequest.setAttribute("grade", grade);
        questionnaireActionForm.setSaveType("saveAndContinue");
        try {
          httpServletResponse.sendRedirect(
              "/jsoa/QuestionnaireAction.do?action=addQuesthemeView&questionnaireId=" + 
              questionnaireId);
        } catch (IOException ex) {
          ex.printStackTrace();
        } 
        return null;
      } 
      if ("saveAndExit".equals(saveType)) {
        questionnaireActionForm.reset(actionMapping, 
            httpServletRequest);
        questionnaireActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("questheme_saveAndExit");
      } 
    } 
    if ("deleteQuestheme".equals(action)) {
      Long questhemeId = new Long(0L);
      boolean result = true;
      if (httpServletRequest.getParameter(
          "questhemeId") != null)
        if (!"".equals(httpServletRequest.getParameter(
              "questhemeId"))) {
          questhemeId = Long.valueOf(httpServletRequest.getParameter(
                "questhemeId"));
          result = questionnaireBD.deleteQuestheme(questhemeId);
        }  
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (!result) {
        httpServletRequest.setAttribute("delResult", "noDelete");
        return actionMapping.findForward("gotoQuesthemeList");
      } 
      if (offsetCopy != 0) {
        questhemeList(httpServletRequest, offsetCopy);
      } else {
        questhemeList(httpServletRequest);
      } 
      questionnaireActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoQuesthemeList");
    } 
    if ("deleteBatchQuestheme".equals(action)) {
      String questhemeIds = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = questionnaireBD.deleteBatchQuestheme(questhemeIds);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        questhemeList(httpServletRequest, offsetCopy);
      } else {
        questhemeList(httpServletRequest);
      } 
      questionnaireActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoQuesthemeList");
    } 
    if ("selectQuesthemeSee".equals(action)) {
      Long questhemeId = Long.valueOf(httpServletRequest.getParameter(
            "questhemeId"));
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuesthemeView(questhemeId);
      QuesthemePO questhemePO = (QuesthemePO)map.get("questheme");
      List themeOptionList = (List)map.get("themeOption");
      questionnaireActionForm.setTitle(questhemePO.getTitle());
      questionnaireActionForm.setScore(questhemePO.getScore());
      QuestionnairePO questionnairePO = questionnaireBD
        .selectQuestionnaireView(
          questionnaireId);
      Integer grade = questionnairePO.getGrade();
      httpServletRequest.setAttribute("grade", grade);
      httpServletRequest.setAttribute("type", questhemePO.getType());
      httpServletRequest.setAttribute("themeOptionList", themeOptionList);
      return actionMapping.findForward("gotoSeeQuestheme");
    } 
    if ("selectQuesthemeView".equals(action)) {
      Long questhemeId = Long.valueOf(httpServletRequest.getParameter(
            "questhemeId"));
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuesthemeView(questhemeId);
      QuesthemePO questhemePO = (QuesthemePO)map.get("questheme");
      List themeOptionList = (List)map.get("themeOption");
      questionnaireActionForm.setTitle(questhemePO.getTitle());
      questionnaireActionForm.setScore(questhemePO.getScore());
      questionnaireActionForm.setOrderCode(questhemePO.getOrderCode());
      QuestionnairePO questionnairePO = questionnaireBD
        .selectQuestionnaireView(
          questionnaireId);
      Integer grade = questionnairePO.getGrade();
      httpServletRequest.setAttribute("grade", grade);
      httpServletRequest.setAttribute("type", questhemePO.getType());
      httpServletRequest.setAttribute("themeOptionList", themeOptionList);
      httpServletRequest.setAttribute("questhemeId", questhemeId);
      return actionMapping.findForward("gotoModiQuestheme");
    } 
    if ("updateQuestheme".equals(action)) {
      Long questhemeId = Long.valueOf(httpServletRequest.getParameter(
            "questhemeId"));
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      String type = httpServletRequest.getParameter("type");
      String[] solutionTitle = (String[])null;
      String[] pitchon = (String[])null;
      String[] optionScore = (String[])null;
      QuesthemePO questhemePO = (QuesthemePO)FillBean.transformOneToOne(
          questionnaireActionForm, QuesthemePO.class);
      if (type != null && "0".equals(type)) {
        solutionTitle = httpServletRequest.getParameterValues("solutionTitle");
        optionScore = httpServletRequest.getParameterValues("optionScore");
      } 
      if (type != null && "1".equals(type)) {
        solutionTitle = httpServletRequest.getParameterValues("solutionTitle2");
        pitchon = httpServletRequest.getParameterValues("pitchon");
      } 
      questhemePO.setQuesthemeId(questhemeId);
      questhemePO.setQuestionnaireId(questionnaireId);
      questhemePO.setType(new Integer(type));
      boolean result = questionnaireBD.updateQuestheme(questhemePO, solutionTitle, optionScore, pitchon);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        Map map = questionnaireBD.selectQuesthemeView(questhemeId);
        questhemePO = (QuesthemePO)map.get("questheme");
        List themeOptionList = (List)map.get("themeOption");
        questionnaireActionForm.setTitle(questhemePO.getTitle());
        questionnaireActionForm.setScore(questhemePO.getScore());
        QuestionnairePO questionnairePO = questionnaireBD
          .selectQuestionnaireView(
            questionnaireId);
        Integer grade = questionnairePO.getGrade();
        httpServletRequest.setAttribute("grade", grade);
        httpServletRequest.setAttribute("type", questhemePO.getType());
        httpServletRequest.setAttribute("themeOptionList", 
            themeOptionList);
        httpServletRequest.setAttribute("questhemeId", questhemeId);
        questionnaireActionForm.reset(actionMapping, httpServletRequest);
        questionnaireActionForm.setSaveType("updateAndExit");
        return actionMapping.findForward("questheme_updateAndExit");
      } 
    } 
    if ("questhemeSearch".equals(action)) {
      questhemeSearchList(httpServletRequest);
      return actionMapping.findForward("gotoQuesthemeList");
    } 
    if ("questionnairePreview".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0) {
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList);
      } else {
        httpServletRequest.setAttribute("questhemeCheckList", "NULL");
      } 
      List questhemeEssayList = (List)map.get("questhemeEssay");
      if (questhemeEssayList.size() != 0) {
        httpServletRequest.setAttribute("questhemeEssayList", 
            questhemeEssayList);
      } else {
        httpServletRequest.setAttribute("questhemeEssayList", "NULL");
      } 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      return actionMapping.findForward("gotoPreviewQuestionnaire");
    } 
    if ("questionnaireAnswer".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0)
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList); 
      List questhemeEssayList = (List)map.get("questhemeEssay");
      if (questhemeEssayList.size() != 0)
        httpServletRequest.setAttribute("questhemeEssayList", 
            questhemeEssayList); 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      String from = 
        "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO";
      String where = "answerSheetPO.ballotEmp ='" + userID + 
        "' and answerSheetPO.questionnaireId =" + 
        questionnaireId;
      boolean isRepeatName = questionnaireBD.isRepeatName(from, where);
      if (isRepeatName)
        questionnaireActionForm.setSaveType("isRepeatName"); 
      return actionMapping.findForward("gotoAnswerQuestionnaire");
    } 
    if ("addQuestionnaireAnswer".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      AnswerSheetPO answerSheetPO = new AnswerSheetPO();
      answerSheetPO.setBallotEmp(userID);
      answerSheetPO.setQuestionnaireId(questionnaireId);
      answerSheetPO.setBallotDate(new Date());
      answerSheetPO.setDomainId(domainId);
      List<String> list = new ArrayList();
      List<String> essayList = new ArrayList();
      String[] questhemeID = httpServletRequest.getParameterValues(
          "questhemeID");
      if (questhemeID != null)
        for (int i = 0; i < questhemeID.length; i++) {
          List<String> list2 = new ArrayList();
          list.add(questhemeID[i].toString());
          String[] questhemeOptionID = httpServletRequest
            .getParameterValues("Box_" + 
              questhemeID[i]);
          for (int j = 0; j < questhemeOptionID.length; j++)
            list2.add(questhemeOptionID[j].toString()); 
          list.add(list2);
        }  
      String[] essayQuesthemeID = httpServletRequest.getParameterValues(
          "essayQuesthemeID");
      if (essayQuesthemeID != null)
        for (int i = 0; i < essayQuesthemeID.length; i++) {
          essayList.add(essayQuesthemeID[i].toString());
          String content = httpServletRequest.getParameter(
              "Textarea_" + essayQuesthemeID[i]);
          essayList.add(content);
        }  
      boolean result = questionnaireBD.addQuestionnaireAnswer(
          answerSheetPO, list, essayList);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndExit".equals(saveType)) {
        Map map = questionnaireBD.selectQuestionnairePreview(
            questionnaireId);
        QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
            "questionnaire");
        List questhemeRadioList = (List)map.get("questhemeRadio");
        if (questhemeRadioList.size() != 0)
          httpServletRequest.setAttribute("questhemeRadioList", 
              questhemeRadioList); 
        List questhemeCheckList = (List)map.get("questhemeCheck");
        if (questhemeCheckList.size() != 0)
          httpServletRequest.setAttribute("questhemeCheckList", 
              questhemeCheckList); 
        List questhemeEssayList = (List)map.get("questhemeEssay");
        if (questhemeEssayList.size() != 0)
          httpServletRequest.setAttribute("questhemeEssayList", 
              questhemeEssayList); 
        httpServletRequest.setAttribute("title", 
            questionnairePO.getTitle());
        questionnaireActionForm.reset(actionMapping, httpServletRequest);
        questionnaireActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward(
            "QuestionnaireAnswer_saveAndExit");
      } 
    } 
    if ("addQuestionnaireAnswer1".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      String from = 
        "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO";
      String where = "answerSheetPO.ballotEmp ='" + userID + 
        "' and answerSheetPO.questionnaireId =" + 
        questionnaireId;
      boolean isRepeatName = questionnaireBD.isRepeatName(from, where);
      String retString = "";
      if (isRepeatName) {
        retString = "repeat";
      } else {
        AnswerSheetPO answerSheetPO = new AnswerSheetPO();
        answerSheetPO.setBallotEmp(userID);
        answerSheetPO.setQuestionnaireId(questionnaireId);
        answerSheetPO.setBallotDate(new Date());
        answerSheetPO.setDomainId(domainId);
        List<String> list = new ArrayList();
        List<String> essayList = new ArrayList();
        String[] questhemeID = httpServletRequest.getParameterValues(
            "questhemeID");
        if (questhemeID != null)
          for (int i = 0; i < questhemeID.length; i++) {
            List<String> list2 = new ArrayList();
            list.add(questhemeID[i].toString());
            String[] questhemeOptionID = httpServletRequest
              .getParameterValues("Box_" + questhemeID[i]);
            for (int j = 0; j < questhemeOptionID.length; j++)
              list2.add(questhemeOptionID[j].toString()); 
            list.add(list2);
          }  
        String[] essayQuesthemeID = httpServletRequest.getParameterValues(
            "essayQuesthemeID");
        if (essayQuesthemeID != null)
          for (int i = 0; i < essayQuesthemeID.length; i++) {
            essayList.add(essayQuesthemeID[i].toString());
            String content = httpServletRequest.getParameter(
                "Textarea_" + essayQuesthemeID[i]);
            essayList.add(content);
          }  
        boolean result = questionnaireBD.addQuestionnaireAnswer(
            answerSheetPO, list, essayList);
        if (result) {
          retString = "true";
        } else {
          retString = "提交失败";
        } 
      } 
      httpServletRequest.setAttribute("voteAdd", retString);
      return actionMapping.findForward("ifmvote");
    } 
    if ("answerListView".equals(action)) {
      String answerWhere = managerBD.getScopeFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), 
          "questionnairePO.examineEmp", 
          "questionnairePO.examineOrg", 
          "questionnairePO.examineGroup");
      answerList(httpServletRequest, answerWhere);
      return actionMapping.findForward("gotoAnswerList");
    } 
    if ("answerSearch".equals(action)) {
      String answerWhere = managerBD.getScopeFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), 
          "questionnairePO.examineEmp", 
          "questionnairePO.examineOrg", 
          "questionnairePO.examineGroup");
      answerSearchList(httpServletRequest, answerWhere);
      return actionMapping.findForward("gotoAnswerList");
    } 
    if ("actorNameListView".equals(action)) {
      String selectValue = "answerSheetPO.answerSheetId";
      String from = "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO";
      String where = "answerSheetPO.questionnaireId=questhemePO.questionnaire.questionnaireId and questhemePO.type=2 and answerSheetPO.domainId=" + 
        domainId;
      String gradeAnswerSheetIds = questionnaireBD.maintenance(
          selectValue, from, where);
      httpServletRequest.setAttribute("gradeAnswerSheetIds", 
          gradeAnswerSheetIds);
      actorNameList(httpServletRequest);
      return actionMapping.findForward("gotoAnswerActorNameList");
    } 
    if ("answerActorNameSearch".equals(action)) {
      String selectValue = "answerSheetPO.answerSheetId";
      String from = "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO";
      String where = "answerSheetPO.questionnaireId=questhemePO.questionnaire.questionnaireId and questhemePO.type=2 and answerSheetPO.domainId=" + 
        domainId;
      String gradeAnswerSheetIds = questionnaireBD.maintenance(
          selectValue, from, where);
      httpServletRequest.setAttribute("gradeAnswerSheetIds", 
          gradeAnswerSheetIds);
      actorNameSearchList(httpServletRequest);
      return actionMapping.findForward("gotoAnswerActorNameList");
    } 
    if ("answerPreview".equals(action)) {
      Long answerSheetId = Long.valueOf(httpServletRequest.getParameter(
            "answerSheetId"));
      questionnaireBD.setReadedUser(httpServletRequest.getSession(true)
          .getAttribute("userId").toString(), 
          answerSheetId);
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0)
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList); 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      Map map2 = questionnaireBD.selectAnswerPreview(answerSheetId);
      List<Object[]> answerSheetList = (List)map2.get("answerSheet");
      if (answerSheetList.size() != 0) {
        Object[] obj = answerSheetList.get(0);
        String ballotName = obj[0].toString();
        httpServletRequest.setAttribute("ballotName", ballotName);
        String ballotDate = obj[1].toString();
        httpServletRequest.setAttribute("ballotDate", ballotDate);
      } 
      String themeOptionIds = (String)map2.get("themeOptionIds");
      if (themeOptionIds != null && !"".equals(themeOptionIds))
        httpServletRequest.setAttribute("themeOptionIds", 
            themeOptionIds); 
      List answerSheetContentPOList = (List)map2.get(
          "answerSheetContentPO");
      if (answerSheetContentPOList != null && 
        answerSheetContentPOList.size() != 0)
        httpServletRequest.setAttribute("questhemeEssayList", 
            answerSheetContentPOList); 
      return actionMapping.findForward("gotoAnswerPreview");
    } 
    if ("answerGraded".equals(action)) {
      Long answerSheetId = Long.valueOf(httpServletRequest.getParameter(
            "answerSheetId"));
      questionnaireBD.setReadedUser(httpServletRequest.getSession(true)
          .getAttribute("userId").toString(), 
          answerSheetId);
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0)
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList); 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      Map map2 = questionnaireBD.selectAnswerPreview(answerSheetId);
      List<Object[]> answerSheetList = (List)map2.get("answerSheet");
      if (answerSheetList.size() != 0) {
        Object[] obj = answerSheetList.get(0);
        String ballotName = obj[0].toString();
        httpServletRequest.setAttribute("ballotName", ballotName);
        String ballotDate = obj[1].toString();
        httpServletRequest.setAttribute("ballotDate", ballotDate);
      } 
      String themeOptionIds = (String)map2.get("themeOptionIds");
      if (themeOptionIds != null && !"".equals(themeOptionIds))
        httpServletRequest.setAttribute("themeOptionIds", 
            themeOptionIds); 
      List answerSheetContentPOList = (List)map2.get(
          "answerSheetContentPO");
      if (answerSheetContentPOList != null && 
        answerSheetContentPOList.size() != 0)
        httpServletRequest.setAttribute("questhemeEssayList", 
            answerSheetContentPOList); 
      return actionMapping.findForward("gotoAnswerGraded");
    } 
    if ("addAnswerGraded".equals(action)) {
      Long answerSheetId = Long.valueOf(httpServletRequest.getParameter(
            "answerSheetId"));
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      List<String> essayList = new ArrayList();
      String[] essayQuesthemeID = httpServletRequest.getParameterValues(
          "essayQuesthemeID");
      if (essayQuesthemeID != null)
        for (int i = 0; i < essayQuesthemeID.length; i++) {
          essayList.add(essayQuesthemeID[i].toString());
          String score = httpServletRequest.getParameter("Text_" + 
              essayQuesthemeID[i]);
          essayList.add(score);
        }  
      Enumeration<String> parameters = httpServletRequest.getParameterNames();
      Map<Object, Object> multipleMap = new HashMap<Object, Object>();
      while (parameters.hasMoreElements()) {
        String parameter = parameters.nextElement();
        if (parameter.indexOf("multiple_") != -1) {
          String keyString = String.valueOf(parameter) + "_" + answerSheetId;
          String valueString = httpServletRequest.getParameter(parameter);
          if (valueString.trim().length() > 0)
            multipleMap.put(keyString, valueString); 
        } 
      } 
      QuestionnaireEJBBean ejbBean = new QuestionnaireEJBBean();
      ejbBean.updateMultiple(multipleMap);
      boolean result = questionnaireBD.addAnswerGraded(essayList);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndExit".equals(saveType)) {
        Map map = questionnaireBD.selectQuestionnairePreview(
            questionnaireId);
        QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
            "questionnaire");
        List questhemeRadioList = (List)map.get("questhemeRadio");
        if (questhemeRadioList.size() != 0)
          httpServletRequest.setAttribute("questhemeRadioList", 
              questhemeRadioList); 
        List questhemeCheckList = (List)map.get("questhemeCheck");
        if (questhemeCheckList.size() != 0)
          httpServletRequest.setAttribute("questhemeCheckList", 
              questhemeCheckList); 
        httpServletRequest.setAttribute("title", 
            questionnairePO.getTitle());
        Map map2 = questionnaireBD.selectAnswerPreview(answerSheetId);
        List<Object[]> answerSheetList = (List)map2.get("answerSheet");
        if (answerSheetList.size() != 0) {
          Object[] obj = answerSheetList.get(0);
          String ballotName = obj[0].toString();
          httpServletRequest.setAttribute("ballotName", ballotName);
          String ballotDate = obj[1].toString();
          httpServletRequest.setAttribute("ballotDate", ballotDate);
        } 
        String themeOptionIds = (String)map2.get("themeOptionIds");
        if (themeOptionIds != null && !"".equals(themeOptionIds))
          httpServletRequest.setAttribute("themeOptionIds", 
              themeOptionIds); 
        List answerSheetContentPOList = (List)map2.get(
            "answerSheetContentPO");
        if (answerSheetContentPOList != null && 
          answerSheetContentPOList.size() != 0)
          httpServletRequest.setAttribute("questhemeEssayList", 
              answerSheetContentPOList); 
        questionnaireActionForm.reset(actionMapping, httpServletRequest);
        questionnaireActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("AnswerGraded_saveAndExit");
      } 
    } 
    if ("answerStatisticView".equals(action)) {
      Long questionnaireId = Long.valueOf(httpServletRequest.getParameter(
            "questionnaireId"));
      Map map = questionnaireBD.selectQuestionnairePreview(
          questionnaireId);
      QuestionnairePO questionnairePO = (QuestionnairePO)map.get(
          "questionnaire");
      List questhemeRadioList = (List)map.get("questhemeRadio");
      if (questhemeRadioList.size() != 0)
        httpServletRequest.setAttribute("questhemeRadioList", 
            questhemeRadioList); 
      List questhemeCheckList = (List)map.get("questhemeCheck");
      if (questhemeCheckList.size() != 0)
        httpServletRequest.setAttribute("questhemeCheckList", 
            questhemeCheckList); 
      List statisticAnswerSheetOptionSumList = (List)map.get(
          "statisticAnswerSheetOptionSum");
      if (statisticAnswerSheetOptionSumList.size() != 0)
        httpServletRequest.setAttribute(
            "statisticAnswerSheetOptionSumList", 
            statisticAnswerSheetOptionSumList); 
      String statisticAnswerSheetSum = (String)map.get(
          "statisticAnswerSheetSum");
      if (statisticAnswerSheetSum != null)
        httpServletRequest.setAttribute("statisticAnswerSheetSum", 
            statisticAnswerSheetSum); 
      String voters = (String)map.get("voters");
      if (voters != null)
        httpServletRequest.setAttribute("voters", voters); 
      String notVoters = (String)map.get("notVoters");
      if (notVoters != null)
        httpServletRequest.setAttribute("notVoters", notVoters); 
      httpServletRequest.setAttribute("title", questionnairePO.getTitle());
      return actionMapping.findForward("gotoAnswerStatistic");
    } 
    if ("answerQuestionnaireListView".equals(action)) {
      String where = managerBD.getScopeFinalWhere(session.getAttribute(
            "userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "questionnairePO.actorEmp", 
          "questionnairePO.actorOrg", 
          "questionnairePO.actorGroup");
      answerQuestionnaireList(httpServletRequest, where);
      return actionMapping.findForward("gotoAnswerQuestionnaireList");
    } 
    if ("forPortal".equals(action)) {
      QuestionnaireBD qBD = new QuestionnaireBD();
      ManagerService mbd = new ManagerService();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString")
        .toString();
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = null;
      try {
        CustomDesktopBD customDesktopBD = new CustomDesktopBD();
        groupList = customDesktopBD.getGroupById(userID.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      buffer = new StringBuffer("(");
      if (!groupList.isEmpty())
        for (int j = 0; j < groupList.size(); j++)
          buffer.append(" ( questionnairePO.actorGroup like '%").append(groupList.get(j)).append("%'  or ").append("  questionnairePO.examineGroup like '%").append(groupList.get(j)).append("%' ) or ");  
      for (int i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" ( questionnairePO.actorOrg like '%").append(orgIdArray[i]).append("%' or ").append("  questionnairePO.examineOrg like '%").append(orgIdArray[i]).append("%' ) or "); 
      } 
      buffer.append(" ( questionnairePO.actorEmp like '%").append(userID.toString()).append("%' or ").append("  questionnairePO.examineEmp like '%").append(userID.toString()).append("%' )");
      buffer.append(")");
      List listInfo = qBD.answerQuestionnaireList(buffer.toString());
      httpServletRequest.setAttribute("questionnaireList", listInfo);
      return actionMapping.findForward("gotoAnswerQuestionnaireListForPortal");
    } 
    if ("viewbrowserUser".equals(action)) {
      String questionnaireId = httpServletRequest.getParameter("questionnaireId");
      httpServletRequest.setAttribute("questionnaireId", questionnaireId);
      return actionMapping.findForward("viewbrowserUser");
    } 
    if ("browser".equals(action)) {
      String questionnaireId = httpServletRequest.getParameter("questionnaireId");
      String orgId = httpServletRequest.getParameter("orgId");
      httpServletRequest.setAttribute("questionnaireId", questionnaireId);
      List browserlist = questionnaireBD.getBrowser1(orgId, Long.valueOf(Long.parseLong(questionnaireId)));
      List nobrowserlist = questionnaireBD.getNoBrowser1(orgId, Long.valueOf(Long.parseLong(questionnaireId)));
      httpServletRequest.setAttribute("browserlist", browserlist);
      httpServletRequest.setAttribute("nobrowserlist", nobrowserlist);
      return actionMapping.findForward("browser");
    } 
    if ("viewbrowserOption".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      String themeId = httpServletRequest.getParameter("themeId");
      String searchName = (httpServletRequest.getParameter("searchName") == null) ? 
        "" : 
        httpServletRequest.getParameter("searchName");
      List list = null;
      try {
        list = questionnaireBD.getBrowserOption(themeId, searchName, 
            pageSize, currentPage);
      } catch (Exception ex1) {
        ex1.printStackTrace();
      } 
      String recordCount = "0";
      try {
        recordCount = String.valueOf(questionnaireBD
            .getBrowserOptionCount(themeId, 
              searchName));
      } catch (Exception ex2) {
        ex2.printStackTrace();
      } 
      httpServletRequest.setAttribute("themeId", themeId);
      httpServletRequest.setAttribute("browserList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,themeId");
      return actionMapping.findForward("viewbrowserOption");
    } 
    System.out.println("=====================" + action);
    throw new UnsupportedOperationException(
        "Method perform() not yet implemented.");
  }
  
  public void questionnaireList(HttpServletRequest httpServletRequest, String answerWhere) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where " + answerWhere + 
      " and questionnairePO.domainId=" + domainId + 
      " order by questionnairePO.questionnaireId desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questionnairePO.questionnaireId,questionnairePO.title,questionnairePO.actorName,questionnairePO.status", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("questionnaireList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId");
  }
  
  public void questionnaireList(HttpServletRequest httpServletRequest, int offsetCopy, String answerWhere) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "  where " + answerWhere + 
      " and questionnairePO.domainId=" + domainId + 
      " order by questionnairePO.questionnaireId desc";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questionnairePO.questionnaireId,questionnairePO.title,questionnairePO.actorName,questionnairePO.status", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("questionnaireList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId");
  }
  
  public void questionnaireSearchList(HttpServletRequest httpServletRequest, String answerWhere) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String searchTitle = httpServletRequest.getParameter("searchTitle");
    String searchStatus = httpServletRequest.getParameter("searchStatus");
    String htrq = httpServletRequest.getParameter("htrq");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    String where = " 1=1 ";
    if ("2".equals(searchStatus)) {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + ((
          searchTitle != null && searchTitle.length() > 0) ? (
          " and questionnairePO.title like '%" + searchTitle + 
          "%' ") : 
          "") + ((
          htrq != null && "1".equals(htrq)) ? (
          " and (questionnairePO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + 
          "' or questionnairePO.endDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "')") : "") + 
          " and 1=1 order by questionnairePO.questionnaireId desc";
      } else {
        where = String.valueOf(where) + ((
          searchTitle != null && searchTitle.length() > 0) ? (
          " and questionnairePO.title like '%" + searchTitle + 
          "%' ") : 
          "") + ((
          htrq != null && "1".equals(htrq)) ? (
          " and (questionnairePO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + 
          "','L') or questionnairePO.endDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L'))") : "") + 
          " and 1=1 order by questionnairePO.questionnaireId desc";
      } 
    } else {
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + ((
          searchTitle != null && searchTitle.length() > 0) ? (
          " and questionnairePO.title like '%" + searchTitle + 
          "%' ") : 
          "") + 
          " and questionnairePO.status ='" + searchStatus + "' " + ((
          htrq != null && "1".equals(htrq)) ? (
          " and (questionnairePO.startDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + 
          "' or questionnairePO.endDate between '" + 
          searchStartDate + "' and '" + 
          searchEndDate + "')") : "") + 
          " and 1=1 order by questionnairePO.questionnaireId desc";
      } else {
        where = String.valueOf(where) + ((
          searchTitle != null && searchTitle.length() > 0) ? (
          " and questionnairePO.title like '%" + searchTitle + 
          "%' ") : 
          "") + 
          " and questionnairePO.status ='" + searchStatus + "' " + ((
          htrq != null && "1".equals(htrq)) ? (
          " and (questionnairePO.startDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + 
          "','L') or questionnairePO.endDate between JSDB.FN_STRTODATE('" + 
          searchStartDate + "','L') and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L'))") : "") + 
          " and 1=1 order by questionnairePO.questionnaireId desc";
      } 
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questionnairePO.questionnaireId,questionnairePO.title,questionnairePO.actorName,questionnairePO.status", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO", 
        "where questionnairePO.domainId=" + domainId + " and (" + 
        answerWhere + ") and " + where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchTitle", searchTitle);
    httpServletRequest.setAttribute("searchStatus", searchStatus);
    httpServletRequest.setAttribute("searchStartDate", searchStartDate);
    httpServletRequest.setAttribute("searchEndDate", searchEndDate);
    httpServletRequest.setAttribute("questionnaireList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,searchTitle,searchStatus,searchStartDate,searchEndDate");
  }
  
  public void questhemeList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String questionnaireId = httpServletRequest.getParameter(
        "questionnaireId");
    String where = "order by questhemePO.orderCode";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questhemePO.questhemeId,questhemePO.title,questhemePO.type,questhemePO.orderCode", 
        "com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO", 
        "where questhemePO.questionnaire.questionnaireId = '" + 
        questionnaireId + "' and questhemePO.domainId=" + domainId + 
        " " + where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("questhemeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId");
  }
  
  public void questhemeList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String questionnaireId = httpServletRequest.getParameter(
        "questionnaireId");
    String where = " order by questhemePO.orderCode";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questhemePO.questhemeId,questhemePO.title,questhemePO.type,questhemePO.orderCode", 
        "com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO", 
        "where questhemePO.questionnaire.questionnaireId = '" + 
        questionnaireId + "' " + " and questhemePO.domainId=" + 
        domainId + where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("questhemeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId");
  }
  
  public void questhemeSearchList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String questionnaireId = httpServletRequest.getParameter(
        "questionnaireId");
    String searchTitle = httpServletRequest.getParameter("searchTitle");
    String searchStatus = httpServletRequest.getParameter("searchStatus");
    String where = "";
    if ("3".equals(searchStatus)) {
      where = "questhemePO.questionnaire.questionnaireId = '" + 
        questionnaireId + "' and questhemePO.title like '%" + 
        searchTitle + "%' and questhemePO.domainId=" + domainId + 
        " order by questhemePO.orderCode";
    } else {
      where = "questhemePO.questionnaire.questionnaireId = '" + 
        questionnaireId + "' and questhemePO.title like '%" + 
        searchTitle + "%' and questhemePO.type='" + searchStatus + 
        "' and questhemePO.domainId=" + domainId + 
        " order by questhemePO.orderCode";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "questhemePO.questhemeId,questhemePO.title,questhemePO.type,questhemePO.orderCode", 
        "com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO", 
        "where " + where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchTitle", searchTitle);
    httpServletRequest.setAttribute("searchStatus", searchStatus);
    httpServletRequest.setAttribute("questhemeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId,searchTitle,searchStatus");
  }
  
  public void answerList(HttpServletRequest httpServletRequest, String answerWhere) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "distinct questionnairePO.questionnaireId,questionnairePO.title,questionnairePO.actorName,questionnairePO.status,questionnairePO.grade", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO,com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO", 
        "where " + answerWhere + " and questionnairePO.domainId=" + 
        domainId + " and questionnairePO.questionnaireId = answerSheetPO.questionnaireId order by questionnairePO.questionnaireId desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("answerList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void answerSearchList(HttpServletRequest httpServletRequest, String answerWhere) {
    HttpSession session = httpServletRequest.getSession(true);
    String searchTitle = httpServletRequest.getParameter("searchTitle");
    String searchStatus = httpServletRequest.getParameter("searchStatus");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "";
    if ("2".equals(searchStatus)) {
      where = "questionnairePO.title like '%" + searchTitle + 
        "%' and questionnairePO.domainId=" + domainId + 
        " order by questionnairePO.questionnaireId desc";
    } else {
      where = "questionnairePO.title like '%" + searchTitle + 
        "%' and questionnairePO.domainId=" + domainId + 
        " and questionnairePO.status ='" + searchStatus + 
        "' order by questionnairePO.questionnaireId desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "distinct questionnairePO.questionnaireId,questionnairePO.title,questionnairePO.actorName,questionnairePO.status,questionnairePO.grade", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO,com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO", 
        "where (" + answerWhere + ") and " + where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchTitle", searchTitle);
    httpServletRequest.setAttribute("searchStatus", searchStatus);
    httpServletRequest.setAttribute("answerList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,searchTitle,searchStatus");
  }
  
  public void actorNameList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String questionnaireId = httpServletRequest.getParameter(
        "questionnaireId");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String tempOrd = httpServletRequest.getParameter("ord");
    if (tempOrd == null) {
      tempOrd = "desc";
    } else if (tempOrd.equals("desc")) {
      tempOrd = "desc";
      httpServletRequest.setAttribute("ord", "asc");
    } else {
      tempOrd = "asc";
      httpServletRequest.setAttribute("ord", "desc");
    } 
    String _tempOrd = httpServletRequest.getParameter("ord1");
    if (_tempOrd == null) {
      _tempOrd = "desc";
    } else if (_tempOrd.equals("desc")) {
      _tempOrd = "desc";
      httpServletRequest.setAttribute("ord1", "desc");
    } else {
      _tempOrd = "asc";
      httpServletRequest.setAttribute("ord1", "desc");
    } 
    Page page = new Page(
        "answerSheetPO.answerSheetId,empPO.empName,orgPO.orgNameString,answerSheetPO.ballotDate,answerSheetPO.readedman", 
        "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO", 
        "where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.questionnaireId ='" + 
        questionnaireId + "' and orgPO.domainId=" + domainId + 
        " order by answerSheetPO.questionnaireId desc,answerSheetPO.ballotDate " + 
        tempOrd + ",orgPO.orgNameString " + _tempOrd);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("actorNameList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,questionnaireId,grade,name");
  }
  
  public void actorNameSearchList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String questionnaireId = httpServletRequest.getParameter(
        "questionnaireId");
    String searchActorId = httpServletRequest.getParameter("searchActorId");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String searchActorIdWhere = "";
    if (searchActorId != null && !"".equals(searchActorId))
      if (searchActorId.indexOf("$") >= 0) {
        searchActorIdWhere = "and answerSheetPO.ballotEmp = " + 
          searchActorId.substring(searchActorId
            .indexOf(
              "$") + 1, searchActorId.lastIndexOf("$"));
      } else {
        searchActorIdWhere = "and answerSheetPO.ballotEmp = " + 
          searchActorId;
      }  
    String searchActorName = httpServletRequest.getParameter(
        "searchActorName");
    String searchStartDate = 
      String.valueOf(httpServletRequest.getParameter("searchStartDate")) + " 00:00:00";
    String searchEndDate = String.valueOf(httpServletRequest.getParameter("searchEndDate")) + 
      " 23:59:59";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String dateDoamin = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      dateDoamin = 
        " and answerSheetPO.ballotDate between '" + 
        searchStartDate + "' and '" + 
        searchEndDate + "'";
    } else {
      dateDoamin = 
        " and answerSheetPO.ballotDate between JSDB.FN_STRTODATE('" + 
        searchStartDate + "','L') and JSDB.FN_STRTODATE('" + 
        searchEndDate + "','L')";
    } 
    String orderby = " order by answerSheetPO.questionnaireId desc";
    if (httpServletRequest.getParameter("sortFirst") != null)
      if (httpServletRequest.getParameter("sortFirst").equals("sszz")) {
        if (httpServletRequest.getParameter("sszzDesc").equals("1")) {
          orderby = " order by orgPO.orgNameString ";
        } else {
          orderby = " order by orgPO.orgNameString desc ";
        } 
      } else if (httpServletRequest.getParameter("sortFirst").equals(
          "tpsj")) {
        if (httpServletRequest.getParameter("tpsjDesc").equals("1")) {
          orderby = " order by answerSheetPO.ballotDate ";
        } else {
          orderby = " order by answerSheetPO.ballotDate desc ";
        } 
      }  
    Page page = new Page(
        "answerSheetPO.answerSheetId,empPO.empName,orgPO.orgNameString,answerSheetPO.ballotDate,answerSheetPO.readedman", 
        "com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO", 
        "where answerSheetPO.ballotEmp=empPO.empId " + 
        searchActorIdWhere + " and answerSheetPO.domainId=" + domainId + 
        " and answerSheetPO.questionnaireId =" + 
        questionnaireId + (
        "1".equals(httpServletRequest.getParameter("timecheck")) ? 
        dateDoamin : " ") + orderby);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchActorId", searchActorId);
    httpServletRequest.setAttribute("searchActorName", searchActorName);
    httpServletRequest.setAttribute("searchStartDate", searchStartDate);
    httpServletRequest.setAttribute("searchEndDate", searchEndDate);
    httpServletRequest.setAttribute("actorNameList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,questionnaireId,searchActorId,searchStartDate,searchEndDate,grade,timecheck,searchActorName,name,sortFirst,sszzDesc,tpsjDesc");
  }
  
  public void answerQuestionnaireList(HttpServletRequest httpServletRequest, String where) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    Calendar nowDate = Calendar.getInstance();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String tmpSql = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      tmpSql = "where (" + where + 
        ") and questionnairePO.status = 1 and questionnairePO.domainId=" + 
        domainId + " '" + 
        nowDate.get(1) + "-" + (
        nowDate.get(2) + 1) + "-" + 
        nowDate.get(5) + " 00:00:00' between questionnairePO.startDate and questionnairePO.endDate order by questionnairePO.questionnaireId desc";
    } else {
      tmpSql = "where (" + where + 
        ") and questionnairePO.status = 1 and questionnairePO.domainId=" + 
        domainId + " and JSDB.FN_STRTODATE('" + 
        nowDate.get(1) + "-" + (
        nowDate.get(2) + 1) + "-" + 
        nowDate.get(5) + " 00:00:00', 'L') between questionnairePO.startDate and questionnairePO.endDate order by questionnairePO.questionnaireId desc";
    } 
    Page page = new Page(
        "questionnairePO.questionnaireId,questionnairePO.title", 
        "com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO", 
        tmpSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("actorList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void sendMessages(Long questionnaireId, String actorEmps, String actorOrgs, String examineEmps, String examineOrgs, Date beginDate, Date endDate, String userName) throws Exception {
    String title = "问卷调查";
    String url = "QuestionnaireAction.do?action=questionnaireAnswer&questionnaireId=" + questionnaireId;
    UserBD userBD = new UserBD();
    Calendar beginCalendar = Calendar.getInstance();
    beginCalendar.setTime(beginDate);
    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTime(endDate);
    String userIdString = "";
    String actorString = StringSplit.splitWith(actorEmps, "$", "*@");
    if (actorString != null && !"".equals(actorString)) {
      actorString = actorString.substring(1, actorString.length() - 1);
      actorString = actorString.replace("$$", ",");
      actorString = String.valueOf(actorString) + ",";
    } 
    String examineString = StringSplit.splitWith(examineEmps, "$", "*@");
    if (examineString != null && !"".equals(examineString)) {
      examineString = examineString.substring(1, examineString.length() - 1);
      examineString = examineString.replace("$$", ",");
      examineString = String.valueOf(examineString) + ",";
    } 
    userIdString = String.valueOf(actorString) + examineString;
    String orgActorString = StringSplit.splitWith(actorOrgs, "*", "@$");
    if (orgActorString != null && !"".equals(orgActorString)) {
      orgActorString = orgActorString.substring(1, orgActorString.length() - 1);
      orgActorString = orgActorString.replace("**", ",");
      List<E> list = new ArrayList();
      list = userBD.selectEmpIdByOrgIds(orgActorString);
      if (!list.isEmpty() && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          userIdString = String.valueOf(userIdString) + list.get(i).toString() + ",";  
    } 
    String orgExamineString = StringSplit.splitWith(examineOrgs, "*", "@$");
    if (orgExamineString != null && !"".equals(orgExamineString)) {
      orgExamineString = orgExamineString.substring(1, orgExamineString.length() - 1);
      orgExamineString = orgExamineString.replace("**", ",");
      List<E> list = new ArrayList();
      list = userBD.selectEmpIdByOrgIds(orgExamineString);
      if (!list.isEmpty() && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          userIdString = String.valueOf(userIdString) + list.get(i).toString() + ",";  
    } 
    beginCalendar.set(11, 0);
    beginCalendar.set(12, 0);
    endCalendar.set(11, 23);
    endCalendar.set(12, 59);
    userIdString = userIdString.substring(0, userIdString.length() - 1);
    RemindUtil.sendMessageToUsers(title, url, userIdString, "Questionnaire", beginCalendar.getTime(), endCalendar.getTime(), userName, questionnaireId);
  }
}
