package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.AnswerSheetPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface QuestionnaireEJBLocal extends EJBLocalObject {
  void setReadedUser(String paramString, Long paramLong) throws HibernateException;
  
  boolean addQuestionnaire(QuestionnairePO paramQuestionnairePO) throws Exception;
  
  boolean deleteQuestionnaire(Long paramLong) throws Exception;
  
  boolean deleteBatchQuestionnaire(String paramString) throws Exception;
  
  QuestionnairePO selectQuestionnaireView(Long paramLong) throws HibernateException;
  
  boolean updateQuestionnaire(QuestionnairePO paramQuestionnairePO) throws Exception;
  
  boolean addQuestheme(QuesthemePO paramQuesthemePO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception;
  
  boolean deleteQuestheme(Long paramLong) throws Exception;
  
  boolean deleteBatchQuestheme(String paramString) throws Exception;
  
  Map selectQuesthemeView(Long paramLong) throws Exception;
  
  boolean updateQuestheme(QuesthemePO paramQuesthemePO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception;
  
  Map selectQuestionnairePreview(Long paramLong) throws Exception;
  
  boolean addQuestionnaireAnswer(AnswerSheetPO paramAnswerSheetPO, List paramList1, List paramList2) throws Exception;
  
  Map selectAnswerPreview(Long paramLong) throws Exception;
  
  boolean addAnswerGraded(List paramList) throws Exception;
  
  List answerQuestionnaireList(String paramString) throws HibernateException;
  
  List isMyAnswer(String paramString1, String paramString2) throws Exception;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws Exception;
  
  String maintenance(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  String getThemeOptionByOptionId(String paramString1, String paramString2);
  
  String getThemeOptionByThemeId(String paramString1, String paramString2);
}
