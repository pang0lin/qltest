package com.js.oa.pressdeal.service;

import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.pressdeal.bean.PressDealDoEJBHome;
import com.js.oa.pressdeal.po.OaPersonoaFeedbackPO;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.oa.pressdeal.po.OaPersonoaUserPressRelatioPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PersonalOAPressManageBD {
  private static Logger logger = Logger.getLogger(PersonalOAPressManageBD.class);
  
  public String addPress(String title, String date, String content, String sendUserName, String sendUserDep, String receiveUserNameStr, String category, String subcategory, String domainId, String isNew) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    OaPersonoaPressPO press = new OaPersonoaPressPO();
    press.setCatorgry(category);
    press.setContent(content);
    press.setDispatchTime(Timestamp.valueOf(date));
    press.setDomainId(new Long(domainId));
    press.setIsNew(new Short(isNew));
    press.setReceiveUsernameStr(receiveUserNameStr);
    press.setSendUserDep(sendUserDep);
    press.setSendUsername(sendUserName);
    press.setSubcatorgryName(subcategory);
    press.setTitle(title);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(press, OaPersonoaPressPO.class);
    rslt = (String)EJBProxy.invoke("AddPress", pg.getParameters());
    return rslt;
  }
  
  public String getDataBaseDatetime() throws Exception {
    String date = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    date = (String)EJBProxy.invoke("getDatabaseTime", (Object[][])null);
    return date;
  }
  
  public String getUserOrg_id_Name(String userid) throws Exception {
    String rslt = "";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(userid, String.class);
    rslt = (String)EJBProxy.invoke("getOrgId_name", pg.getParameters());
    return rslt;
  }
  
  public String delPressUserRelationByLoad(String entityId) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(entityId, String.class);
    OaPersonoaUserPressRelatioPO press_user_ER = null;
    press_user_ER = (OaPersonoaUserPressRelatioPO)EJBProxy.invoke("getPersonUserPressRelationByLoad", pg.getParameters());
    if (press_user_ER == null) {
      rslt = "false";
    } else {
      ParameterGenerator pg2 = new ParameterGenerator(1);
      pg2.put(press_user_ER, OaPersonoaUserPressRelatioPO.class);
      rslt = (String)EJBProxy.invoke("delPersonUserPressER", pg2.getParameters());
    } 
    return rslt;
  }
  
  public String delPressUserRelationByHql(String entityId) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(entityId, String.class);
    OaPersonoaUserPressRelatioPO press_user_ER = null;
    press_user_ER = (OaPersonoaUserPressRelatioPO)EJBProxy.invoke("getPersonUserPressRelationByHql", pg.getParameters());
    if (press_user_ER == null) {
      rslt = "false";
    } else {
      ParameterGenerator pg2 = new ParameterGenerator(1);
      pg2.put(press_user_ER, OaPersonoaUserPressRelatioPO.class);
      rslt = (String)EJBProxy.invoke("delPersonUserPressER", pg2.getParameters());
    } 
    return rslt;
  }
  
  public String addFeedback(String feedback_time, String userName, String pressId, String content, String domainId) throws Exception {
    String rslt = "false";
    OaPersonoaFeedbackPO feedback = new OaPersonoaFeedbackPO();
    feedback.setContent(content);
    feedback.setDomainId(new Long(domainId));
    feedback.setFeedbackTime(Timestamp.valueOf(feedback_time));
    feedback.setPressId(new Long(pressId));
    feedback.setUserName(userName);
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(feedback, OaPersonoaFeedbackPO.class);
    rslt = (String)EJBProxy.invoke("addFeedback", pg.getParameters());
    return rslt;
  }
  
  public String addFeedback_RTXMail(String feedback_time, String userName, String pressId, String content, String domainId, boolean sendMail) throws Exception {
    String rslt = "false";
    if (!sendMail)
      rslt = addFeedback(feedback_time, userName, pressId, content, domainId); 
    if (sendMail)
      rslt = addFeedback(feedback_time, userName, pressId, content, domainId); 
    return rslt;
  }
  
  public List getAllFeedbackList(String pressId) throws Exception {
    List list = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressId, String.class);
    list = (List)EJBProxy.invoke("getAllFeedbackList", pg.getParameters());
    return list;
  }
  
  public String updatePressIsNew(String pressid) throws Exception {
    String rslt = "false";
    OaPersonoaPressPO press = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressid, String.class);
    press = (OaPersonoaPressPO)EJBProxy.invoke("getPressByIdLoad", pg.getParameters());
    if (press == null) {
      rslt = "false";
    } else {
      press.setIsNew(new Short("0"));
      rslt = updatePress(press);
    } 
    return rslt;
  }
  
  public String updatePress(OaPersonoaPressPO press) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(press, OaPersonoaPressPO.class);
    rslt = (String)EJBProxy.invoke("updatePress", pg.getParameters());
    return rslt;
  }
  
  public String addPerssUserRelation(OaPersonoaUserPressRelatioPO opupr) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(opupr, OaPersonoaUserPressRelatioPO.class);
    rslt = (String)EJBProxy.invoke("addPersonUserPressER", pg.getParameters());
    return rslt;
  }
  
  public String addPerssUserRelation(String pressId, String pressStatus, String userName, String domainId, String userId, String orgId, String orgName) throws Exception {
    String rslt = "false";
    OaPersonoaUserPressRelatioPO opupr = new OaPersonoaUserPressRelatioPO();
    opupr.setDomainId(new Long(domainId));
    opupr.setOrgId(new Long(orgId));
    opupr.setOrgName(orgName);
    opupr.setPressId(new Long(pressId));
    opupr.setPressStatus(new Byte(pressStatus));
    opupr.setUserId(new Long(userId));
    opupr.setUserName(userName);
    rslt = addPerssUserRelation(opupr);
    return rslt;
  }
  
  public List getUserPressERArray(String pressId) throws Exception {
    List rslt = null;
    List list = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressId, String.class);
    list = (List)EJBProxy.invoke("getUserPressERList", pg.getParameters());
    return rslt;
  }
  
  public List getPressFeedbackArray(String pressId) throws Exception {
    List rslt = null;
    List list = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressId, String.class);
    list = (List)EJBProxy.invoke("getPressFeedbackList", pg.getParameters());
    return rslt;
  }
  
  public OaPersonoaPressPO getPressByIdLoad(String pressid) throws Exception {
    OaPersonoaPressPO rslt = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressid, String.class);
    rslt = (OaPersonoaPressPO)EJBProxy.invoke("getPressByIdLoad", pg.getParameters());
    return rslt;
  }
  
  public String del_send_press(String pressId) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(pressId, String.class);
    rslt = (String)EJBProxy.invoke("Del_Send_Press", pg.getParameters());
    return rslt;
  }
  
  public String Del_Receive_Press(String pressId, String userId, String press_status) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(pressId, String.class);
    pg.put(userId, String.class);
    pg.put(press_status, String.class);
    rslt = (String)EJBProxy.invoke("del_receive_press", pg.getParameters());
    return rslt;
  }
  
  public List getUser_Press_List(String pressId, String press_status) throws Exception {
    List list = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(pressId, String.class);
    pg.put(press_status, String.class);
    list = (List)EJBProxy.invoke("getUser_Press_List", pg.getParameters());
    return list;
  }
  
  public String getPress_sender_userId(String pressId) throws Exception {
    String rslt = "";
    List<OaPersonoaUserPressRelatioPO> list = null;
    list = getUser_Press_List(pressId, "0");
    if (list != null && list.size() > 0) {
      OaPersonoaUserPressRelatioPO opupr = list.get(0);
      rslt = String.valueOf(opupr.getUserId());
    } 
    return rslt;
  }
  
  public String getPress_title(String pressId) throws Exception {
    String rslt = "";
    OaPersonoaPressPO press = getPressByIdLoad(pressId);
    rslt = press.getTitle();
    return rslt;
  }
  
  public String mySplit(String str, char c) {
    String rslt = "";
    char[] ch = str.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      if (ch[i] == c)
        if (i == 0) {
          ch[i] = '~';
        } else if (i < ch.length - 1) {
          if (ch[i + 1] != c) {
            ch[i] = '-';
          } else {
            ch[i] = '~';
          } 
        } else {
          ch[i] = '~';
        }  
    } 
    rslt = new String(ch);
    rslt = rslt.replaceAll("~", "");
    return rslt;
  }
  
  public String getUserAccountsByEmpId(String EmpId) throws Exception {
    String rslt = "";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(EmpId, String.class);
    rslt = (String)EJBProxy.invoke("getUserAccountsByEmpId", pg.getParameters());
    return rslt;
  }
  
  public String getUserAccounts(String userIdStr) throws Exception {
    String rslt = "";
    userIdStr = mySplit(userIdStr, '$');
    String[] tmpArray = userIdStr.split("-");
    for (int i = 0; i < tmpArray.length; i++)
      rslt = String.valueOf(rslt) + getUserAccountsByEmpId(tmpArray[i]) + ","; 
    rslt = rslt.substring(0, rslt.length() - 1);
    return rslt;
  }
  
  public String getUserAccountsListByEmpIdStr(String empIdstr) throws Exception {
    String rslt = "";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(empIdstr, String.class);
    List list = (List)EJBProxy.invoke("getUserAccountsListByEmpIdStr", pg.getParameters());
    if (list == null)
      list = new ArrayList(); 
    for (int i = 0; i < list.size(); i++)
      rslt = String.valueOf(rslt) + String.valueOf(list.get(i)); 
    rslt = rslt.substring(0, rslt.length() - 1);
    return rslt;
  }
  
  public String updateAllSendPress_Status(String userId, String domainId) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(userId, String.class);
    pg.put(domainId, String.class);
    rslt = (String)EJBProxy.invoke("updateAllSendPress", pg.getParameters());
    return rslt;
  }
  
  public String getUserIds(String orginIds) throws Exception {
    String rslt = "";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(orginIds, String.class);
    rslt = (String)EJBProxy.invoke("getUserIds", pg.getParameters());
    return rslt;
  }
  
  public String sendNewPress(String orginIds, String receiveNameStr, String sendUserId, String sendUserName, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId, boolean sendTelMail, HttpServletRequest httpServletRequest) throws Exception {
    String rslt = "false";
    String tmpstr = "";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(11);
    pg.put(orginIds, String.class);
    pg.put(receiveNameStr, String.class);
    pg.put(sendUserId, String.class);
    pg.put(sendUserName, String.class);
    pg.put(sendUserDep, String.class);
    pg.put(press_title, String.class);
    pg.put(press_content, String.class);
    pg.put(press_category, String.class);
    pg.put(press_subcategory, String.class);
    pg.put(domainId, String.class);
    pg.put(httpServletRequest, HttpServletRequest.class);
    tmpstr = (String)EJBProxy.invoke("send_press_orgin", pg.getParameters());
    if (!"false".equals(tmpstr) && tmpstr.length() > 0) {
      if (sendTelMail) {
        ModelSendMsg sendMsg = new ModelSendMsg();
        sendMsg.sendSystemMessage("催办管理", press_title, tmpstr, "", httpServletRequest);
      } 
      rslt = "true";
    } else {
      rslt = "false";
    } 
    return rslt;
  }
  
  public String sendNewPressBySystem(String orginIds, String receiveNameStr, String sendUserId, String sendUserName, String sendUserDep, String press_title, String press_content, String press_category, String press_subcategory, String domainId) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(10);
    pg.put(orginIds, String.class);
    pg.put(receiveNameStr, String.class);
    pg.put(sendUserId, String.class);
    pg.put(sendUserName, String.class);
    pg.put(sendUserDep, String.class);
    pg.put(press_title, String.class);
    pg.put(press_content, String.class);
    pg.put(press_category, String.class);
    pg.put(press_subcategory, String.class);
    pg.put(domainId, String.class);
    rslt = (String)EJBProxy.invoke("send_press_orgin2", pg.getParameters());
    return rslt;
  }
  
  public String updateFeedbackStatus(Long feedback_Id) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(feedback_Id, Long.class);
    rslt = (String)EJBProxy.invoke("updateFeedbackStatus", pg.getParameters());
    return rslt;
  }
  
  public String updatePressStatus(Long press_Id) throws Exception {
    String rslt = "false";
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(press_Id, Long.class);
    rslt = (String)EJBProxy.invoke("updatePressStatus", pg.getParameters());
    return rslt;
  }
  
  public OaPersonoaFeedbackPO loadFeedback(Long feedback_Id) throws Exception {
    OaPersonoaFeedbackPO po = null;
    EJBProxy EJBProxy = new EJBProxy("PressDealDoEJB", "PressDealDoEJBLocal", PressDealDoEJBHome.class);
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(feedback_Id, Long.class);
    po = (OaPersonoaFeedbackPO)EJBProxy.invoke("loadFeedback", pg.getParameters());
    return po;
  }
}
