package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO;
import com.js.oa.hr.subsidiarywork.po.AnswerSheetOptionPO;
import com.js.oa.hr.subsidiarywork.po.AnswerSheetPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import com.js.oa.hr.subsidiarywork.po.ThemeOptionPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class QuestionnaireEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean addQuestionnaire(QuestionnairePO questionnairePO) throws Exception {
    boolean result = false;
    begin();
    try {
      this.session.save(questionnairePO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteQuestionnaire(Long questionnaireId) throws Exception {
    boolean result = false;
    begin();
    try {
      QuestionnairePO questionnairePO = (QuestionnairePO)this.session.load(
          QuestionnairePO.class, 
          questionnaireId);
      this.session.delete(questionnairePO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteBatchQuestionnaire(String questionnaireIds) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = questionnaireIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        QuestionnairePO questionnairePO = (QuestionnairePO)this.session
          .load(QuestionnairePO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(questionnairePO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public QuestionnairePO selectQuestionnaireView(Long questionnaireId) throws HibernateException {
    QuestionnairePO questionnairePO = null;
    try {
      begin();
      questionnairePO = (QuestionnairePO)this.session.load(QuestionnairePO.class, 
          questionnaireId);
    } catch (Exception e) {
      System.out.println("selectQuestionnaireViewEJB Exception:" + e.toString());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return questionnairePO;
  }
  
  public boolean updateQuestionnaire(QuestionnairePO questionnairePO) throws Exception {
    boolean result = false;
    begin();
    try {
      QuestionnairePO questionnaire = (QuestionnairePO)this.session.load(QuestionnairePO.class, 
          questionnairePO.getQuestionnaireId());
      questionnaire.setQuestionnaireId(questionnairePO.getQuestionnaireId());
      questionnaire.setTitle(questionnairePO.getTitle());
      questionnaire.setActorEmp(questionnairePO.getActorEmp());
      questionnaire.setActorGroup(questionnairePO.getActorGroup());
      questionnaire.setActorOrg(questionnairePO.getActorOrg());
      questionnaire.setActorName(questionnairePO.getActorName());
      questionnaire.setExamineEmp(questionnairePO.getExamineEmp());
      questionnaire.setExamineGroup(questionnairePO.getExamineGroup());
      questionnaire.setExamineOrg(questionnairePO.getExamineOrg());
      questionnaire.setExamineName(questionnairePO.getExamineName());
      questionnaire.setCreatedEmp(questionnairePO.getCreatedEmp());
      questionnaire.setCratedOrg(questionnairePO.getCratedOrg());
      questionnaire.setStartDate(questionnairePO.getStartDate());
      questionnaire.setEndDate(questionnairePO.getEndDate());
      questionnaire.setGrade(questionnairePO.getGrade());
      questionnaire.setStatus(questionnairePO.getStatus());
      this.session.update(questionnaire);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean addQuestheme(QuesthemePO questhemePO, String[] solutionTitle, String[] optionScore, String[] pitchon) throws Exception {
    boolean result = false;
    begin();
    try {
      QuestionnairePO questionnairePO = (QuestionnairePO)this.session.load(
          QuestionnairePO.class, questhemePO.getQuestionnaireId());
      questhemePO.setQuestionnaire(questionnairePO);
      this.session.save(questhemePO);
      if (solutionTitle != null && !"null".equals(solutionTitle))
        for (int i = 0; i < solutionTitle.length; i++) {
          ThemeOptionPO themeOptionPO = new ThemeOptionPO();
          themeOptionPO.setQuestheme(questhemePO);
          themeOptionPO.setTitle(solutionTitle[i]);
          if (pitchon != null && !"null".equals(pitchon))
            themeOptionPO.setPitchon(new Integer(pitchon[i])); 
          if (optionScore != null && !"null".equals(optionScore))
            if (optionScore[i] != null && !"".equals(optionScore[i])) {
              themeOptionPO.setOptionScore(new Float(optionScore[
                      i]));
            } else {
              themeOptionPO.setOptionScore(new Float(0.0F));
            }  
          this.session.save(themeOptionPO);
        }  
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteQuestheme(Long questhemeId) throws Exception {
    boolean result = false;
    begin();
    try {
      QuesthemePO questhemePO = (QuesthemePO)this.session.load(QuesthemePO.class, 
          questhemeId);
      this.session.delete(questhemePO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteBatchQuestheme(String questhemeIds) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = questhemeIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        QuesthemePO questhemePO = (QuesthemePO)this.session.load(
            QuesthemePO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(questhemePO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Map selectQuesthemeView(Long questhemeId) throws Exception {
    begin();
    Map<Object, Object> map = new HashMap<Object, Object>();
    QuesthemePO questhemePO = null;
    try {
      questhemePO = (QuesthemePO)this.session.load(QuesthemePO.class, questhemeId);
      map.put("questheme", questhemePO);
      Query query = this.session.createQuery("select themeOption.themeOptionId,themeOption.title,themeOption.pitchon,themeOption.optionScore from com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO join questhemePO.themeOption themeOption where questhemePO.questhemeId = " + 
          questhemeId + " order by themeOption.themeOptionId");
      map.put("themeOption", query.list());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return map;
  }
  
  public boolean updateQuestheme(QuesthemePO questhemePO, String[] solutionTitle, String[] optionScore, String[] pitchon) throws Exception {
    boolean result = false;
    begin();
    try {
      QuesthemePO questheme = (QuesthemePO)this.session.load(QuesthemePO.class, 
          questhemePO.getQuesthemeId());
      QuestionnairePO questionnairePO = (QuestionnairePO)this.session.load(
          QuestionnairePO.class, questhemePO.getQuestionnaireId());
      questheme.setQuesthemeId(questhemePO.getQuesthemeId());
      questheme.setQuestionnaire(questionnairePO);
      questheme.setTitle(questhemePO.getTitle());
      questheme.setScore(questhemePO.getScore());
      questheme.setOrderCode(questhemePO.getOrderCode());
      questheme.setType(questhemePO.getType());
      this.session.update(questheme);
      Iterator<ThemeOptionPO> it = this.session.createQuery("from com.js.oa.hr.subsidiarywork.po.ThemeOptionPO themeOptionPO where themeOptionPO.questheme.questhemeId=" + 
          questhemePO.getQuesthemeId())
        .iterate();
      while (it.hasNext()) {
        ThemeOptionPO themeOption = it.next();
        this.session.delete(themeOption);
      } 
      if (solutionTitle != null && !"null".equals(solutionTitle))
        for (int i = 0; i < solutionTitle.length; i++) {
          ThemeOptionPO themeOptionPO = new ThemeOptionPO();
          themeOptionPO.setQuestheme(questhemePO);
          themeOptionPO.setTitle(solutionTitle[i]);
          if (pitchon != null && !"null".equals(pitchon))
            themeOptionPO.setPitchon(new Integer(pitchon[i])); 
          if (optionScore != null && !"null".equals(optionScore))
            if (optionScore[i] != null && !"".equals(optionScore[i])) {
              themeOptionPO.setOptionScore(new Float(optionScore[i]));
            } else {
              themeOptionPO.setOptionScore(new Float(0.0F));
            }  
          this.session.save(themeOptionPO);
        }  
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Map selectQuestionnairePreview(Long questionnaireId) throws Exception {
    begin();
    Map<Object, Object> map = new HashMap<Object, Object>();
    QuestionnairePO questionnairePO = null;
    try {
      questionnairePO = (QuestionnairePO)this.session.load(QuestionnairePO.class, 
          questionnaireId);
      map.put("questionnaire", questionnairePO);
      Query query = this.session.createQuery("select questhemePO from com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO join questhemePO.questionnaire questionnairePO where questionnairePO.questionnaireId = " + 
          questionnaireId + 
          " and questhemePO.type=0 order by questhemePO.orderCode");
      map.put("questhemeRadio", query.list());
      query = this.session.createQuery("select questhemePO from com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO join questhemePO.questionnaire questionnairePO where questionnairePO.questionnaireId = " + 
          questionnaireId + 
          " and questhemePO.type=1 order by questhemePO.orderCode");
      map.put("questhemeCheck", query.list());
      query = this.session.createQuery("select questhemePO from com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO join questhemePO.questionnaire questionnairePO where questionnairePO.questionnaireId = " + 
          questionnaireId + 
          " and questhemePO.type=2 order by questhemePO.orderCode");
      map.put("questhemeEssay", query.list());
      query = this.session.createQuery("select answerSheetOptionPO.themeOptionId,COUNT(answerSheetOptionPO.themeOptionId) from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO answerSheetContentPO,com.js.oa.hr.subsidiarywork.po.AnswerSheetOptionPO answerSheetOptionPO where answerSheetPO.answerSheetId = answerSheetContentPO.answerSheet.answerSheetId and answerSheetContentPO.contentId = answerSheetOptionPO.answerSheetContent.contentId and answerSheetPO.questionnaireId = " + 
          questionnaireId + 
          " group by answerSheetOptionPO.themeOptionId");
      map.put("statisticAnswerSheetOptionSum", query.list());
      query = this.session.createQuery("select COUNT(distinct answerSheetPO.answerSheetId) from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO where answerSheetPO.questionnaireId = " + 
          questionnaireId);
      String statisticAnswerSheetSum = query.iterate().next().toString();
      map.put("statisticAnswerSheetSum", statisticAnswerSheetSum);
      String voters = "";
      query = this.session.createQuery("select distinct orgPO.orgName,empPO.empName from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.questionnaireId ='" + 
          questionnaireId + 
          "' order by orgPO.orgName desc");
      List<Object[]> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        Object[] arrayOfObject = list.get(i);
        String tmpVoters = "";
        for (int n = 0; n < arrayOfObject.length; n++)
          tmpVoters = String.valueOf(tmpVoters) + arrayOfObject[n] + "."; 
        tmpVoters = tmpVoters.substring(0, tmpVoters.length() - 1);
        voters = String.valueOf(voters) + tmpVoters + "，";
      } 
      if (!"".equals(voters))
        voters = voters.substring(0, voters.length() - 1); 
      map.put("voters", voters);
      query = this.session.createQuery("select questionnairePO.actorEmp,questionnairePO.actorOrg,questionnairePO.actorGroup from com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO where questionnairePO.questionnaireId ='" + 
          questionnaireId + "'");
      List<Object[]> list2 = query.list();
      Object[] o = list2.get(0);
      String userOrgGroupId = "";
      for (int j = 0; j < o.length; j++) {
        if (o[j] != null)
          userOrgGroupId = String.valueOf(userOrgGroupId) + o[j]; 
      } 
      ConversionString conversionString = new ConversionString(
          userOrgGroupId);
      String userIdString = conversionString.getUserIdString();
      if (!"".equals(userIdString))
        userIdString = String.valueOf(userIdString) + ","; 
      String orgIdString = conversionString.getOrgIdString();
      if (!"".equals(orgIdString)) {
        String[] arr_orgIds = orgIdString.split(",");
        String selectOrgIdstringWhere = "";
        for (int n = 0; n < arr_orgIds.length; n++) {
          if (n == 0) {
            selectOrgIdstringWhere = String.valueOf(selectOrgIdstringWhere) + "orgVO.orgIdString like '%$" + 
              arr_orgIds[n] + "$%'";
          } else {
            selectOrgIdstringWhere = String.valueOf(selectOrgIdstringWhere) + 
              " or orgVO.orgIdString like '%$" + 
              arr_orgIds[n] + "$%'";
          } 
        } 
        Query orgQuery = this.session.createQuery("select orgVO.orgId from com.js.system.vo.organizationmanager.OrganizationVO orgVO where " + 
            selectOrgIdstringWhere);
        List<E> orgList = orgQuery.list();
        orgIdString = "";
        for (int i1 = 0; i1 < orgList.size(); i1++)
          orgIdString = String.valueOf(orgIdString) + orgList.get(i1).toString() + ","; 
        orgIdString = orgIdString.substring(0, orgIdString.length() - 1);
      } 
      if (!"".equals(orgIdString)) {
        Query orgQuery = this.session.createQuery("select emp.empId from com.js.system.vo.organizationmanager.OrganizationVO orgVO join orgVO.employees emp where orgVO.orgId in (" + 
            orgIdString + ") and (emp.userIsActive = 1 and emp.userIsDeleted = 0) ");
        List<E> orgList = orgQuery.list();
        for (int n = 0; n < orgList.size(); n++)
          userIdString = String.valueOf(userIdString) + orgList.get(n).toString() + ","; 
      } 
      String groupIdString = conversionString.getGroupIdString();
      if (!"".equals(groupIdString)) {
        Query groupQuery = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO groupVO join groupVO.employees emp where groupVO.groupId in (" + 
            groupIdString + ") and (emp.userIsActive = 1 and emp.userIsDeleted = 0) ");
        List<E> groupList = groupQuery.list();
        for (int n = 0; n < groupList.size(); n++)
          userIdString = String.valueOf(userIdString) + groupList.get(n).toString() + ","; 
      } 
      Query votersQuery = this.session.createQuery("select empPO.empId from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.questionnaireId ='" + 
          questionnaireId + "'  and (empPO.userIsActive = 1 and empPO.userIsDeleted = 0) ");
      List<E> votersList = votersQuery.list();
      String votersIdString = "";
      for (int k = 0; k < votersList.size(); k++)
        votersIdString = String.valueOf(votersIdString) + votersList.get(k).toString() + ","; 
      if (!"".equals(votersIdString))
        votersIdString = votersIdString.substring(0, 
            votersIdString.length() - 1); 
      String notVoters = "";
      if (userIdString.length() > 0)
        userIdString = userIdString.substring(0, userIdString.length() - 1); 
      String sql = "";
      if (!"-1".equals(userIdString)) {
        sql = " ";
        String[] userArray = userIdString.split(",");
        StringBuffer userBufferTemp = new StringBuffer();
        StringBuffer whereCondition = new StringBuffer();
        boolean isCut = false;
        for (int n = 0; n < userArray.length; n++) {
          userBufferTemp.append(userArray[n]).append(",");
          if (n % 999 == 0) {
            String users = userBufferTemp.toString();
            users = users.substring(0, users.length() - 1);
            if (isCut) {
              whereCondition.append(" or emp.empId in(").append(users).append(")");
            } else {
              whereCondition.append(" emp.empId in(").append(users).append(")");
            } 
            if (!isCut)
              isCut = true; 
            userBufferTemp = new StringBuffer();
          } 
        } 
        if (isCut) {
          String users = userBufferTemp.toString();
          if (users.length() > 0) {
            users = users.substring(0, users.length() - 1);
            whereCondition.append(" or emp.empId in(").append(users).append(")");
          } 
        } else {
          String users = userBufferTemp.toString();
          if (users.length() > 0) {
            users = users.substring(0, users.length() - 1);
            whereCondition.append(" emp.empId in(").append(users).append(")");
          } 
        } 
        sql = String.valueOf(sql) + whereCondition.toString();
      } 
      if (!"".equals(userIdString) && !"".equals(votersIdString)) {
        query = this.session.createQuery("select orgVO.orgName,emp.empName from com.js.system.vo.organizationmanager.OrganizationVO orgVO join orgVO.employees emp where " + 
            sql + " and emp.empId not in (" + 
            votersIdString + ")  and (emp.userIsActive = 1 and emp.userIsDeleted = 0) ");
      } else {
        if (userIdString.equals(""))
          sql = " emp.empId in (-1)"; 
        query = this.session.createQuery("select orgVO.orgName,emp.empName from com.js.system.vo.organizationmanager.OrganizationVO orgVO join orgVO.employees emp where " + 
            sql + "  and (emp.userIsActive = 1 and emp.userIsDeleted = 0) ");
      } 
      List<Object[]> list3 = query.list();
      for (int m = 0; m < list3.size(); m++) {
        Object[] o1 = list3.get(m);
        String tmpVoters = "";
        for (int n = 0; n < o1.length; n++)
          tmpVoters = String.valueOf(tmpVoters) + o1[n] + "."; 
        tmpVoters = tmpVoters.substring(0, tmpVoters.length() - 1);
        notVoters = String.valueOf(notVoters) + tmpVoters + "，";
      } 
      if (notVoters.length() > 0)
        notVoters = notVoters.substring(0, notVoters.length() - 1); 
      map.put("notVoters", notVoters);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return map;
  }
  
  public boolean addQuestionnaireAnswer(AnswerSheetPO answerSheetPO, List<E> list, List<E> essayList) throws Exception {
    boolean result = false;
    begin();
    try {
      this.session.save(answerSheetPO);
      if (list != null)
        for (int i = 0; i < list.size(); i += 2) {
          AnswerSheetContentPO answerSheetContentPO = 
            new AnswerSheetContentPO();
          Long themeId = Long.valueOf(list.get(i).toString());
          answerSheetContentPO.setThemeId(themeId);
          answerSheetContentPO.setAnswerSheet(answerSheetPO);
          answerSheetContentPO.setDomainId(answerSheetPO.getDomainId());
          this.session.save(answerSheetContentPO);
          int j = i + 1;
          List<E> list2 = (List)list.get(j);
          for (int k = 0; k < list2.size(); k++) {
            AnswerSheetOptionPO answerSheetOptionPO = 
              new AnswerSheetOptionPO();
            Long themeOptionId = Long.valueOf(list2.get(k).toString());
            answerSheetOptionPO.setThemeOptionId(themeOptionId);
            answerSheetOptionPO.setAnswerSheetContent(
                answerSheetContentPO);
            answerSheetOptionPO.setDomainId(answerSheetPO
                .getDomainId());
            this.session.save(answerSheetOptionPO);
          } 
        }  
      if (essayList != null)
        for (int i = 0; i < essayList.size(); i += 2) {
          AnswerSheetContentPO answerSheetContentPO = 
            new AnswerSheetContentPO();
          Long themeId = Long.valueOf(essayList.get(i).toString());
          answerSheetContentPO.setThemeId(themeId);
          int j = i + 1;
          String content = essayList.get(j).toString();
          answerSheetContentPO.setContent(content);
          answerSheetContentPO.setAnswerSheet(answerSheetPO);
          answerSheetContentPO.setDomainId(answerSheetPO.getDomainId());
          this.session.save(answerSheetContentPO);
        }  
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Map selectAnswerPreview(Long answerSheetId) throws Exception {
    begin();
    Map<Object, Object> map = new HashMap<Object, Object>();
    String themeOptionIds = "";
    try {
      Query query = this.session.createQuery("select empPO.empName,answerSheetPO.ballotDate from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.answerSheetId = " + 
          answerSheetId);
      map.put("answerSheet", query.list());
      query = this.session.createQuery("select answerSheetContentPO from com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO answerSheetContentPO where answerSheetContentPO.answerSheet.answerSheetId = " + 
          answerSheetId);
      List<AnswerSheetContentPO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        AnswerSheetContentPO answerSheetContentPO = 
          list.get(i);
        Set answerSheetOption = answerSheetContentPO
          .getAnswerSheetOption();
        Iterator<AnswerSheetOptionPO> iterator = answerSheetOption.iterator();
        while (iterator.hasNext()) {
          AnswerSheetOptionPO answerSheetOptionPO = 
            iterator.next();
          themeOptionIds = String.valueOf(themeOptionIds) + 
            answerSheetOptionPO.getThemeOptionId() + 
            ",";
        } 
      } 
      map.put("themeOptionIds", themeOptionIds);
      query = this.session.createQuery("select questhemePO.questhemeId,questhemePO.title,questhemePO.score,answerSheetContentPO.contentId,answerSheetContentPO.content,answerSheetContentPO.score from com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO answerSheetContentPO,com.js.oa.hr.subsidiarywork.po.QuesthemePO questhemePO where answerSheetContentPO.themeId=questhemePO.questhemeId and questhemePO.type=2 and answerSheetContentPO.answerSheet.answerSheetId = " + 
          answerSheetId);
      map.put("answerSheetContentPO", query.list());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return map;
  }
  
  public boolean addAnswerGraded(List<E> essayList) throws Exception {
    boolean result = false;
    begin();
    try {
      if (essayList != null)
        for (int i = 0; i < essayList.size(); i += 2) {
          Long contentId = Long.valueOf(essayList.get(i).toString());
          AnswerSheetContentPO answerSheetContentPO = 
            (AnswerSheetContentPO)this.session.load(
              AnswerSheetContentPO.class, contentId);
          int j = i + 1;
          Float score = Float.valueOf(essayList.get(j).toString());
          answerSheetContentPO.setScore(score);
        }  
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List answerQuestionnaireList(String where) throws HibernateException {
    List actorList = new ArrayList();
    Calendar nowDate = Calendar.getInstance();
    try {
      begin();
      String databaseType = SystemCommon.getDatabaseType();
      String sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "select questionnairePO.questionnaireId,questionnairePO.title ,questionnairePO.startDate ,questionnairePO.endDate ,questionnairePO.actorName from com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO where (" + 
          where + 
          ") and questionnairePO.status = 1 and questionnairePO.startDate <= '" + nowDate.get(1) + "-" + (nowDate.get(2) + 1) + "-" + nowDate.get(5) + " 00:00:00' and questionnairePO.endDate >= '" + nowDate.get(1) + "-" + (nowDate.get(2) + 1) + "-" + nowDate.get(5) + " 00:00:00' order by questionnairePO.questionnaireId desc";
      } else {
        sql = "select questionnairePO.questionnaireId,questionnairePO.title ,questionnairePO.startDate ,questionnairePO.endDate ,questionnairePO.actorName from com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO where (" + 
          where + 
          ") and questionnairePO.status = 1 and JSDB.FN_STRTODATE('" + nowDate.get(1) + "-" + (nowDate.get(2) + 1) + "-" + nowDate.get(5) + " 00:00:00', 'L') between questionnairePO.startDate and questionnairePO.endDate order by questionnairePO.questionnaireId desc";
      } 
      Query answerQuestionnaireActorQuery = this.session.createQuery(sql);
      answerQuestionnaireActorQuery.setFirstResult(0);
      answerQuestionnaireActorQuery.setMaxResults(10);
      actorList = answerQuestionnaireActorQuery.list();
    } catch (Exception e) {
      System.out.println("answerQuestionnaireListEJB Exception:" + 
          e.toString());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return actorList;
  }
  
  public Boolean isRepeatName(String from, String where) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery("from " + from + " where " + 
          where);
      List list = query.list();
      if (list.size() >= 1)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String maintenance(String selectValue, String from, String where) throws HibernateException {
    String maintenanceIds = "";
    begin();
    try {
      Query query = this.session.createQuery("select " + selectValue + 
          " from " + from + " where " + 
          where);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        maintenanceIds = String.valueOf(maintenanceIds) + list.get(i) + ","; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return maintenanceIds;
  }
  
  public List isMyAnswer(String from, String where) throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("from " + from + " where " + 
          where).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getThemeOptionByOptionId(String themeOptionId, String domainId) {
    return "";
  }
  
  public String getThemeOptionByThemeId(String themeId, String domainId) {
    return 
      "";
  }
  
  public void setReadedUser(String userID, Long recordID) throws HibernateException {
    begin();
    try {
      AnswerSheetPO po = (AnswerSheetPO)this.session.load(AnswerSheetPO.class, recordID);
      if (po.getReadedman() != null && !po.getReadedman().equalsIgnoreCase("null")) {
        po.setReadedman(String.valueOf(po.getReadedman()) + "," + userID);
      } else {
        po.setReadedman(userID);
      } 
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public int getBrowserCount(String orgId, Long questionnaireId) throws Exception {
    Integer re = Integer.valueOf("0");
    begin();
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      st = conn.createStatement();
      rs = st.executeQuery("select count(ee.emp_Id) from org_employee ee where ee.userisdeleted=0 and ee.userisactive=1 and ee.emp_id in (select answersheet.BALLOTEMP from oa_answersheet answersheet where answersheet.QUESTIONNAIREID =" + questionnaireId + ") and ee.emp_id in(select ou.emp_id from org_organization_user ou where ou.org_id in(select org_id from org_organization where orgidstring like '%$" + orgId + "$%'))");
      while (rs.next())
        re = Integer.valueOf(rs.getInt(1)); 
      st.close();
      rs.close();
      conn.close();
    } catch (Exception ex) {
      st.close();
      rs.close();
      conn.close();
      throw ex;
    } 
    return re.intValue();
  }
  
  public List getBrowser1(String orgId, Long questionnaireId) throws Exception {
    List<Object[]> browserlist = new ArrayList();
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    begin();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      st = conn.createStatement();
      rs = st.executeQuery("select ee.emp_Id,ee.empName,ee.empMobilePhone,ee.userAccounts,ee.userOnline ,ee.empEmail from org_employee ee where ee.userisdeleted=0 and ee.userisactive=1 and ee.emp_id in (select answersheet.BALLOTEMP from oa_answersheet answersheet where answersheet.QUESTIONNAIREID =" + questionnaireId + ") and ee.emp_id in(select ou.emp_id from org_organization_user ou where ou.org_id in(select org_id from org_organization where orgidstring like '%$" + orgId + "$%'))");
      while (rs.next()) {
        Object[] obj = new Object[6];
        obj[0] = Long.valueOf(rs.getLong(1));
        obj[1] = rs.getString(2);
        if ("".equals(rs.getString(3))) {
          obj[2] = null;
        } else {
          obj[2] = rs.getString(3);
        } 
        obj[3] = rs.getString(4);
        obj[4] = Integer.valueOf(rs.getInt(5));
        obj[5] = rs.getString(6);
        browserlist.add(obj);
      } 
      st.close();
      rs.close();
      conn.close();
    } catch (Exception ex) {
      st.close();
      rs.close();
      conn.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public List getNoBrowser1(String orgId, Long questionnaireId) throws Exception {
    List<Object[]> browserlist = new ArrayList();
    begin();
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      st = conn.createStatement();
      rs = st.executeQuery("select ee.emp_Id,ee.empName,ee.empMobilePhone ,ee.userAccounts,ee.userOnline,ee.empEmail from org_employee ee where ee.userisdeleted=0 and ee.userisactive=1 and ee.emp_id not in (select answersheet.BALLOTEMP from oa_answersheet answersheet where answersheet.QUESTIONNAIREID =" + questionnaireId + ") and ee.emp_id in(select ou.emp_id from org_organization_user ou where ou.org_id in(select org_id from org_organization where orgidstring like '%$" + orgId + "$%'))");
      while (rs.next()) {
        Object[] obj = new Object[6];
        obj[0] = Long.valueOf(rs.getLong(1));
        obj[1] = rs.getString(2);
        if ("".equals(rs.getString(3))) {
          obj[2] = null;
        } else {
          obj[2] = rs.getString(3);
        } 
        obj[3] = rs.getString(4);
        obj[4] = Integer.valueOf(rs.getInt(5));
        obj[5] = rs.getString(6);
        browserlist.add(obj);
      } 
      st.close();
      rs.close();
      conn.close();
    } catch (Exception ex) {
      st.close();
      rs.close();
      conn.close();
      throw ex;
    } 
    return browserlist;
  }
  
  public long searchAnswerSheetId(long userid, long questionnaireId) throws Exception {
    Long answerSheetId = null;
    begin();
    try {
      List<Long> list = this.session.createQuery("select answerSheetPO.answerSheetId  from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO where answerSheetPO.ballotEmp =" + userid + " and answerSheetPO.questionnaireId =" + questionnaireId).list();
      if (!list.isEmpty())
        answerSheetId = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return answerSheetId.longValue();
  }
  
  public List searchIdByVindicate(String where, String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select questionnairePO.questionnaireId from com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO   where " + where + 
          " and questionnairePO.domainId=" + domainId + 
          " order by questionnairePO.questionnaireId desc").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List searchIdByExamine(String where, String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select questionnairePO.questionnaireId from com.js.oa.hr.subsidiarywork.po.QuestionnairePO questionnairePO   where " + where + 
          " and questionnairePO.domainId=" + domainId + 
          " order by questionnairePO.questionnaireId desc").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean updateMultiple(Map multipleMap) throws Exception {
    boolean flag = false;
    Map updateMap = multipleMap;
    begin();
    Set multipleNames = updateMap.keySet();
    Iterator<String> iterator = multipleNames.iterator();
    String[] parameters = new String[0];
    AnswerSheetContentPO answer = null;
    try {
      while (iterator.hasNext()) {
        String multipleName = iterator.next();
        parameters = multipleName.split("_");
        Long themeID = Long.valueOf(parameters[1]);
        Long answerSheetID = Long.valueOf(parameters[2]);
        Float multipleScore = Float.valueOf((String)updateMap.get(multipleName));
        Query query = this.session.createQuery("from AnswerSheetContentPO po where po.themeId=? and po.answerSheet.answerSheetId=?");
        query.setLong(0, themeID.longValue());
        query.setLong(1, answerSheetID.longValue());
        List<AnswerSheetContentPO> list = query.list();
        if (list.size() > 0)
          answer = list.get(0); 
        answer.setScore(multipleScore);
        this.session.save(answer);
      } 
      this.session.flush();
      flag = true;
    } catch (Exception e) {
      flag = false;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
  
  public Float getScoreValue(String themeIDString, String answerSheetIDString) throws Exception {
    Float scoreValue = null;
    begin();
    try {
      Long themeID = Long.valueOf(themeIDString);
      Long answerSheetID = Long.valueOf(answerSheetIDString);
      String hql = "from AnswerSheetContentPO po where po.themeId=? and po.answerSheet.answerSheetId=? ";
      Query query = this.session.createQuery(hql);
      query.setLong(0, themeID.longValue());
      query.setLong(1, answerSheetID.longValue());
      List list = query.list();
      Iterator<AnswerSheetContentPO> iterator = list.iterator();
      AnswerSheetContentPO answerSheetContent = null;
      while (iterator.hasNext())
        answerSheetContent = iterator.next(); 
      if (answerSheetContent != null) {
        Float datascoreValue = answerSheetContent.getScore();
        if (datascoreValue != null)
          scoreValue = datascoreValue; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return scoreValue;
  }
}
