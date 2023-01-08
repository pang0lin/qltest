package com.js.oa.pressdeal.bean;

import com.js.oa.pressdeal.po.OaPersonoaFeedbackPO;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface PressDealDoEJBLocal extends EJBLocalObject {
  String AddPress(OaPersonoaPressPO paramOaPersonoaPressPO) throws HibernateException;
  
  String updatePress(OaPersonoaPressPO paramOaPersonoaPressPO) throws HibernateException;
  
  OaPersonoaPressPO getPressByIdLoad(String paramString) throws HibernateException;
  
  OaPersonoaPressPO getPressByIdHql(String paramString) throws HibernateException;
  
  String SendPress(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) throws HibernateException, ParseException, HibernateException;
  
  String SendPress_receiveUserNameStr(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws HibernateException, ParseException, HibernateException;
  
  String addPersonUserPressER(OaPersonoaUserPressRelatioPO paramOaPersonoaUserPressRelatioPO) throws HibernateException;
  
  String delPersonUserPressER(OaPersonoaUserPressRelatioPO paramOaPersonoaUserPressRelatioPO) throws HibernateException;
  
  OaPersonoaUserPressRelatioPO getPersonUserPressRelationByLoad(String paramString) throws HibernateException;
  
  OaPersonoaUserPressRelatioPO getPersonUserPressRelationByHql(String paramString) throws HibernateException;
  
  String addFeedback(OaPersonoaFeedbackPO paramOaPersonoaFeedbackPO) throws HibernateException;
  
  List getAllFeedbackList(String paramString) throws HibernateException;
  
  String getUserName(String paramString) throws HibernateException;
  
  String getOrgId_name(String paramString) throws HibernateException;
  
  String Del_Send_Press(String paramString) throws HibernateException;
  
  List getUserPressERList(String paramString) throws HibernateException;
  
  List getPressFeedbackList(String paramString) throws HibernateException;
  
  String getDatabaseTime() throws HibernateException, ParseException;
  
  String del_receive_press(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  List getUser_Press_List(String paramString1, String paramString2) throws HibernateException;
  
  String getUserAccountsByEmpId(String paramString) throws HibernateException;
  
  List getUserAccountsListByEmpIdStr(String paramString) throws HibernateException;
  
  String updateAllSendPress(String paramString1, String paramString2) throws Exception;
  
  String getUserIds(String paramString) throws Exception;
  
  String send_press_orgin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception;
  
  String send_press_orgin2(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception;
  
  String updateFeedbackStatus(Long paramLong) throws Exception;
  
  String updatePressStatus(Long paramLong) throws Exception;
  
  OaPersonoaFeedbackPO loadFeedback(Long paramLong) throws Exception;
}
