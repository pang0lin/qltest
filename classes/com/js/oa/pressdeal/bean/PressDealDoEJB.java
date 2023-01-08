package com.js.oa.pressdeal.bean;

import com.js.oa.pressdeal.po.OaPersonoaFeedbackPO;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface PressDealDoEJB extends EJBObject {
  String AddPress(OaPersonoaPressPO paramOaPersonoaPressPO) throws HibernateException, RemoteException;
  
  String updatePress(OaPersonoaPressPO paramOaPersonoaPressPO) throws HibernateException, RemoteException;
  
  OaPersonoaPressPO getPressByIdLoad(String paramString) throws HibernateException, RemoteException;
  
  OaPersonoaPressPO getPressByIdHql(String paramString) throws HibernateException, RemoteException;
  
  String SendPress(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) throws HibernateException, ParseException, HibernateException, RemoteException;
  
  String SendPress_receiveUserNameStr(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws HibernateException, ParseException, HibernateException, RemoteException;
  
  String addPersonUserPressER(OaPersonoaUserPressRelatioPO paramOaPersonoaUserPressRelatioPO) throws HibernateException, RemoteException;
  
  String delPersonUserPressER(OaPersonoaUserPressRelatioPO paramOaPersonoaUserPressRelatioPO) throws HibernateException, RemoteException;
  
  OaPersonoaUserPressRelatioPO getPersonUserPressRelationByLoad(String paramString) throws HibernateException, RemoteException;
  
  OaPersonoaUserPressRelatioPO getPersonUserPressRelationByHql(String paramString) throws HibernateException, RemoteException;
  
  String addFeedback(OaPersonoaFeedbackPO paramOaPersonoaFeedbackPO) throws HibernateException, RemoteException;
  
  List getAllFeedbackList(String paramString) throws HibernateException, RemoteException;
  
  String getUserName(String paramString) throws HibernateException, RemoteException;
  
  String getOrgId_name(String paramString) throws HibernateException, RemoteException;
  
  String Del_Send_Press(String paramString) throws HibernateException, RemoteException;
  
  List getUserPressERList(String paramString) throws HibernateException, RemoteException;
  
  List getPressFeedbackList(String paramString) throws HibernateException, RemoteException;
  
  String getDatabaseTime() throws HibernateException, ParseException, RemoteException;
  
  String del_receive_press(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  List getUser_Press_List(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String getUserAccountsByEmpId(String paramString) throws HibernateException, RemoteException;
  
  List getUserAccountsListByEmpIdStr(String paramString) throws HibernateException, RemoteException;
  
  String updateAllSendPress(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getUserIds(String paramString) throws Exception, RemoteException;
  
  String send_press_orgin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception, RemoteException;
  
  String send_press_orgin2(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception, RemoteException;
  
  String updateFeedbackStatus(Long paramLong) throws Exception, RemoteException;
  
  String updatePressStatus(Long paramLong) throws Exception, RemoteException;
  
  OaPersonoaFeedbackPO loadFeedback(Long paramLong) throws Exception, RemoteException;
}
