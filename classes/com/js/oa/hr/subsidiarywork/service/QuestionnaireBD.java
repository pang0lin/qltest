package com.js.oa.hr.subsidiarywork.service;

import com.js.oa.hr.subsidiarywork.bean.QuestionnaireEJBBean;
import com.js.oa.hr.subsidiarywork.bean.QuestionnaireEJBHome;
import com.js.oa.hr.subsidiarywork.po.AnswerSheetPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.apache.log4j.Logger;

public class QuestionnaireBD {
  private static Logger logger = Logger.getLogger(QuestionnaireBD.class
      .getName());
  
  public boolean addQuestionnaire(QuestionnairePO questionnairePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnairePO, QuestionnairePO.class);
      ejbProxy.invoke("addQuestionnaire", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addQuestionnaireBD Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteQuestionnaire(Long questionnaireId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnaireId, Long.class);
      ejbProxy.invoke("deleteQuestionnaire", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteQuestionnaireBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchQuestionnaire(String questionnaireIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnaireIds, String.class);
      ejbProxy.invoke("deleteBatchQuestionnaire", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchQuestionnaireBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public QuestionnairePO selectQuestionnaireView(Long questionnaireId) {
    QuestionnairePO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnaireId, Long.class);
      result = (QuestionnairePO)ejbProxy.invoke(
          "selectQuestionnaireView", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectQuestionnaireViewBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateQuestionnaire(QuestionnairePO questionnairePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnairePO, QuestionnairePO.class);
      ejbProxy.invoke("updateQuestionnaire", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("updateQuestionnaireBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addQuestheme(QuesthemePO questhemePO, String[] solutionTitle, String[] optionScore, String[] pitchon) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questhemePO, QuesthemePO.class);
      pg.put(solutionTitle, String[].class);
      pg.put(optionScore, String[].class);
      pg.put(pitchon, String[].class);
      ejbProxy.invoke("addQuestheme", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addQuesthemeBD Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteQuestheme(Long questhemeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questhemeId, Long.class);
      ejbProxy.invoke("deleteQuestheme", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteQuesthemeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchQuestheme(String questhemeIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questhemeIds, String.class);
      ejbProxy.invoke("deleteBatchQuestheme", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchQuesthemeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Map selectQuesthemeView(Long questhemeId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questhemeId, Long.class);
      result = (Map)ejbProxy.invoke("selectQuesthemeView", pg.getParameters());
    } catch (Exception e) {
      logger.error("selectQuesthemeViewBD:" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean updateQuestheme(QuesthemePO questhemePO, String[] solutionTitle, String[] optionScore, String[] pitchon) {
    boolean result = false;
    try {
      result = (new QuestionnaireEJBBean()).updateQuestheme(questhemePO, solutionTitle, optionScore, pitchon);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public Map selectQuestionnairePreview(Long questionnaireId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(questionnaireId, Long.class);
      result = (Map)ejbProxy.invoke("selectQuestionnairePreview", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("selectQuestionnairePreviewBD:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getBrowser(String questionnaireId, String searchName, String read, int volume, int currentPage, String domainId) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      if ("1".equals(read)) {
        query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts,answerSheetPO.ballotDate from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.questionnaireId ='" + 
            questionnaireId + "' " + sWhere + 
            " order by orgPO.orgName desc");
      } else {
        query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.ballotEmp={b}.empId  and {a}.questionnaireId ='" + 
            questionnaireId + "') " + sWhere + 
            " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
            domainId);
      } 
      query.setFirstResult((currentPage - 1) * volume);
      query.setMaxResults(volume);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list;
  }
  
  public int getBrowserCount(String questionnaireId, String searchName, String read, String domainId) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and empPO.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      if ("1".equals(read)) {
        query = session.createQuery("select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO answerSheetPO,com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where answerSheetPO.ballotEmp=empPO.empId and answerSheetPO.questionnaireId ='" + 
            questionnaireId + "' " + sWhere + 
            " order by orgPO.orgName desc");
      } else {
        query = session.createQuery(" select distinct empPO.empName,orgPO.orgName,empPO.userAccounts from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations orgPO where empPO.empId not in ( select distinct {b}.empId from com.js.oa.hr.subsidiarywork.po.AnswerSheetPO {a},com.js.system.vo.usermanager.EmployeeVO {b} where {a}.ballotEmp={b}.empId and {a}.questionnaireId ='" + 
            questionnaireId + "') " + sWhere + 
            " and empPO.userIsActive=1 and empPO.userIsDeleted=0 and empPO.domainId=" + 
            domainId);
      } 
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list.size();
  }
  
  public List getBrowserOption(String themeId, String searchName, int volume, int currentPage) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and {d}.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      query = session.createQuery(" select distinct {d}.empName,{e}.orgName,{d}.userAccounts from com.js.oa.hr.subsidiarywork.po.AnswerSheetOptionPO {a},com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO {b} join {b}.answerSheet {c},com.js.system.vo.usermanager.EmployeeVO {d} join {d}.organizations {e} where {a}.answerSheetContent={b}.contentId and {c}.ballotEmp={d}.empId and  {a}.themeOptionId='" + 
          themeId + "' " + sWhere);
      query.setFirstResult((currentPage - 1) * volume);
      query.setMaxResults(volume);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list;
  }
  
  public int getBrowserOptionCount(String themeId, String searchName) throws Exception {
    String sWhere = "";
    if (!"".equals(searchName))
      sWhere = " and {d}.empName like '%" + searchName + "%'"; 
    List list = null;
    HibernateBase hb = new HibernateBase();
    hb.begin();
    Session session = hb.getSession();
    Query query = null;
    try {
      query = session.createQuery(" select distinct {d}.empName,{e}.orgName,{d}.userAccounts from com.js.oa.hr.subsidiarywork.po.AnswerSheetOptionPO {a},com.js.oa.hr.subsidiarywork.po.AnswerSheetContentPO {b} join {b}.answerSheet {c},com.js.system.vo.usermanager.EmployeeVO {d} join {d}.organizations {e} where {a}.answerSheetContent={b}.contentId and {c}.ballotEmp={d}.empId and  {a}.themeOptionId='" + 
          themeId + "' " + sWhere);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    session.close();
    session = null;
    return list.size();
  }
  
  public boolean addQuestionnaireAnswer(AnswerSheetPO answerSheetPO, List list, List essayList) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(answerSheetPO, AnswerSheetPO.class);
      pg.put(list, List.class);
      pg.put(essayList, List.class);
      ejbProxy.invoke("addQuestionnaireAnswer", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addQuestionnaireAnswerBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Map selectAnswerPreview(Long answerSheetId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(answerSheetId, Long.class);
      result = (Map)ejbProxy.invoke("selectAnswerPreview", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("selectAnswerPreviewBD:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public boolean addAnswerGraded(List essayList) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(essayList, List.class);
      ejbProxy.invoke("addAnswerGraded", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addAnswerGradedBD Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List answerQuestionnaireList(String where) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(where, String.class);
      result = (List)ejbProxy.invoke("answerQuestionnaireList", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("answerQuestionnaireListBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean isRepeatName(String from, String where) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(from, String.class);
      pg.put(where, String.class);
      Boolean b = (Boolean)ejbProxy.invoke("isRepeatName", 
          pg.getParameters());
      result = b.booleanValue();
    } catch (Exception ex) {
      System.out.println("isRepeatNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List isMyAnswer(String from, String where) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(from, String.class);
      pg.put(where, String.class);
      list = (List)ejbProxy.invoke("isMyAnswer", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("isRepeatNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return list;
  }
  
  public String maintenance(String selectValue, String from, String where) {
    String maintenanceIds = "";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("QuestionnaireEJB", 
          "QuestionnaireEJBLocal", QuestionnaireEJBHome.class);
      pg.put(selectValue, String.class);
      pg.put(from, String.class);
      pg.put(where, String.class);
      maintenanceIds = (String)ejbProxy.invoke("maintenance", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("maintenance BD Exception:" + 
          ex.getMessage());
    } finally {}
    return maintenanceIds;
  }
  
  public String getThemeOptionByOptionId(String themeOptionId, String domainId) {
    if (themeOptionId != null && themeOptionId.length() > 0) {
      String id = "";
      ParameterGenerator pg = new ParameterGenerator(2);
      try {
        EJBProxy ejbProxy = new EJBProxy(
            "QuestionnaireEJB", "QuestionnaireEJBLocal", 
            QuestionnaireEJBHome.class);
        pg.put(themeOptionId, String.class);
        pg.put(domainId, String.class);
        id = (String)ejbProxy.invoke("getThemeOptionByOptionId", 
            pg.getParameters());
      } catch (Exception ex) {
        System.out.println("getThemeOptionByOptionId Exception:" + 
            ex.getMessage());
      } finally {}
      return id;
    } 
    return "";
  }
  
  public String getThemeOptionByThemeId(String themeId, String domainId) {
    if (themeId != null && themeId.length() > 0) {
      String id = "";
      ParameterGenerator pg = new ParameterGenerator(2);
      try {
        EJBProxy ejbProxy = new EJBProxy(
            "QuestionnaireEJB", "QuestionnaireEJBLocal", 
            QuestionnaireEJBHome.class);
        pg.put(themeId, String.class);
        pg.put(domainId, String.class);
        id = (String)ejbProxy.invoke("getThemeOptionByThemeId", 
            pg.getParameters());
      } catch (Exception ex) {
        System.out.println("getThemeOptionByThemeId Exception:" + 
            ex.getMessage());
      } finally {}
      return id;
    } 
    return "";
  }
  
  public void setReadedUser(String userID, Long recordID) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "QuestionnaireEJB", "QuestionnaireEJBLocal", 
          QuestionnaireEJBHome.class);
      pg.put(userID, String.class);
      pg.put(recordID, Long.class);
      ejbProxy.invoke("setReadedUser", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("setReadedUser Exception:" + 
          ex.getMessage());
    } 
  }
  
  public int getBrowserCount(String orgId, Long questionnaireId) throws Exception {
    Integer re = Integer.valueOf("0");
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    re = Integer.valueOf(questionnaireEJBBean.getBrowserCount(orgId, questionnaireId));
    return re.intValue();
  }
  
  public List getBrowser1(String orgId, Long questionnaireId) throws Exception {
    List browserlist = new ArrayList();
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    browserlist = questionnaireEJBBean.getBrowser1(orgId, questionnaireId);
    return browserlist;
  }
  
  public List getNoBrowser1(String orgId, Long questionnaireId) throws Exception {
    List browserlist = new ArrayList();
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    browserlist = questionnaireEJBBean.getNoBrowser1(orgId, questionnaireId);
    return browserlist;
  }
  
  public long searchAnswerSheetId(long userid, long questionnaireId) throws Exception {
    Long answerSheetId = null;
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    answerSheetId = Long.valueOf(questionnaireEJBBean.searchAnswerSheetId(userid, questionnaireId));
    return answerSheetId.longValue();
  }
  
  public List searchIdByVindicate(String where, String domainId) throws Exception {
    List list = new ArrayList();
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    list = questionnaireEJBBean.searchIdByVindicate(where, domainId);
    return list;
  }
  
  public List searchIdByExamine(String where, String domainId) throws Exception {
    List list = new ArrayList();
    QuestionnaireEJBBean questionnaireEJBBean = new QuestionnaireEJBBean();
    list = questionnaireEJBBean.searchIdByExamine(where, domainId);
    return list;
  }
}
