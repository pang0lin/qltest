package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.AnswerSheetPO;
import com.js.oa.hr.subsidiarywork.po.QuesthemePO;
import com.js.oa.hr.subsidiarywork.po.QuestionnairePO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface QuestionnaireEJB extends EJBObject {
  void setReadedUser(String paramString, Long paramLong) throws HibernateException, RemoteException;
  
  boolean addQuestionnaire(QuestionnairePO paramQuestionnairePO) throws Exception, RemoteException;
  
  boolean deleteQuestionnaire(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteBatchQuestionnaire(String paramString) throws Exception, RemoteException;
  
  QuestionnairePO selectQuestionnaireView(Long paramLong) throws HibernateException, RemoteException;
  
  boolean updateQuestionnaire(QuestionnairePO paramQuestionnairePO) throws Exception, RemoteException;
  
  boolean addQuestheme(QuesthemePO paramQuesthemePO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  boolean deleteQuestheme(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteBatchQuestheme(String paramString) throws Exception, RemoteException;
  
  Map selectQuesthemeView(Long paramLong) throws Exception, RemoteException;
  
  boolean updateQuestheme(QuesthemePO paramQuesthemePO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  Map selectQuestionnairePreview(Long paramLong) throws Exception, RemoteException;
  
  boolean addQuestionnaireAnswer(AnswerSheetPO paramAnswerSheetPO, List paramList1, List paramList2) throws Exception, RemoteException;
  
  Map selectAnswerPreview(Long paramLong) throws Exception, RemoteException;
  
  boolean addAnswerGraded(List paramList) throws Exception, RemoteException;
  
  List answerQuestionnaireList(String paramString) throws HibernateException, RemoteException;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List isMyAnswer(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String maintenance(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  String getThemeOptionByOptionId(String paramString1, String paramString2) throws RemoteException;
  
  String getThemeOptionByThemeId(String paramString1, String paramString2) throws RemoteException;
}
