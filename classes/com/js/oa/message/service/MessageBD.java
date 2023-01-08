package com.js.oa.message.service;

import com.js.oa.message.bean.MessageEJBBean;
import com.js.oa.message.bean.MessageEJBHome;
import com.js.oa.message.po.MsDescribePO;
import com.js.oa.message.po.MsInfoListPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class MessageBD {
  private static Logger logger = Logger.getLogger(MessageBD.class.getName());
  
  public boolean modelSendMsg(String ids, String contents, String mobile, String domainId, Date sendTime, Long dataId) {
    boolean result = false;
    ParameterGenerator p = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(ids, String.class);
      p.put(contents, String.class);
      p.put(mobile, String.class);
      p.put(domainId, String.class);
      p.put(sendTime, Date.class);
      p.put(dataId, Long.class);
      result = ((Boolean)ejbProxy.invoke("modelSendMsg", p.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public boolean modSysFileMsg(String ids, String contents, String mobile, String fileSign, String domainId) {
    boolean result = false;
    ParameterGenerator p = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(ids, String.class);
      p.put(contents, String.class);
      p.put(mobile, String.class);
      p.put(fileSign, String.class);
      p.put(domainId, String.class);
      result = ((Boolean)ejbProxy.invoke("modSysFileMsg", p.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public HashMap listView(Long userID) {
    HashMap listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(userID, Long.class);
      listInfo = (HashMap)ejbProxy.invoke("listView", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public boolean isSendMsg(Long userID, String userName, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      pg.put(userName, String.class);
      pg.put(domainId, String.class);
      result = ((Boolean)ejbProxy.invoke("isSendMsg", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD isSendMsg bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean isSendOutMsg(Long userID, String userName) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      pg.put(userName, String.class);
      result = ((Boolean)ejbProxy.invoke("isSendOutMsg", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("Error from  MessageBD isSendOutMsg bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delRealSearchMessage(String ids, String status) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      pg.put(status, String.class);
      result = ((Boolean)ejbProxy.invoke("delRealSearchMessage", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD delRealSearchMessage bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delSearchMessage(String ids, String status) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      pg.put(status, String.class);
      result = ((Boolean)ejbProxy.invoke("delSearchMessage", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD delSearchMessage bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getFlowAndList(Long userID) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(userID, Long.class);
      listInfo = (List)ejbProxy.invoke("getFlowAndList", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public boolean delDescribeBatch(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("delDescribeBatch", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD delDescribeBatch bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean realClearDescribeBox(Long userID) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      ejbProxy.invoke("realClearDescribeBox", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD realClearDescribeBox bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean realClearDeletedBox(Long userID) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      ejbProxy.invoke("realClearDeletedBox", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD realClearDeletedBox bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deletedboxBatch(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("deletedboxBatch", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD deletedboxBatch bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delRealReceiveBoxBatch(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("delRealReceiveBoxBatch", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD delRealReceiveBoxBatch bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean receiveToDescribe(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("receiveToDescribe", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD receiveToDescribe bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean receivedClearToDescribe(Long userID) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      ejbProxy.invoke("receivedClearToDescribe", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD receivedClearToDescribe bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delRealSendedBoxBatch(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("delRealSendedBoxBatch", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD delRealSendedBoxBatch bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean sendedToDescribe(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(ids, String.class);
      ejbProxy.invoke("sendedToDescribe", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD sendedToDescribe bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean sendedClearToDescribe(Long userID) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(userID, Long.class);
      ejbProxy.invoke("sendedClearToDescribe", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD sendedClearToDescribe bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public MsDescribePO listDescribe(String describId) {
    MsDescribePO listDes = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(describId, String.class);
      listDes = (MsDescribePO)ejbProxy.invoke("listDescribe", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listDes;
  }
  
  public String selectManSign(Long manId, String manName) {
    String sign = null;
    ParameterGenerator p = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(manId, Long.class);
      p.put(manName, String.class);
      sign = (String)ejbProxy.invoke("selectManSign", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return sign;
  }
  
  public boolean addDescribe(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(tmpMbgroup, String.class);
      pg.put(tmpMassageMan, String.class);
      pg.put(tmpMobilePhone, String.class);
      pg.put(content, String.class);
      pg.put(userID, Long.class);
      pg.put(userName, String.class);
      pg.put(outmobilCode, String.class);
      ejbProxy.invoke("addDescribe", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD addDescribe bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addMsgFlow(String tmpMbgroup, String tmpMassageMan, String tmpMobilePhone, String content, Long userID, String userName, String outmobilCode) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      pg.put(tmpMbgroup, String.class);
      pg.put(tmpMassageMan, String.class);
      pg.put(tmpMobilePhone, String.class);
      pg.put(content, String.class);
      pg.put(userID, Long.class);
      pg.put(userName, String.class);
      pg.put(outmobilCode, String.class);
      ejbProxy.invoke("addMsgFlow", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD addMsgFlow bd information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List selectGroupUser(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    System.out.println("the group id is:" + id);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      list = (List)ejbProxy.invoke("selectGroupUser", pg.getParameters());
      System.out.println("The group list is :" + list);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD selectGroupUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List selectGroupUserEmail(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    System.out.println("the group id is:" + id);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      list = (List)ejbProxy.invoke("selectGroupUserEmail", pg.getParameters());
      System.out.println("The group list is :" + list);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD selectGroupUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List selectPersonUser(String id, String userId, String orgId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      pg.put(id, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      list = (List)ejbProxy.invoke("selectPersonUser", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD selectPersonUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List selectPersonUserEmail(String id, String userId, String orgId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      pg.put(id, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      list = (List)ejbProxy.invoke("selectPersonUserEmail", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD selectPersonUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Map getSubOrgAndUsers(String orgId, String currentOrg) {
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(orgId, "String");
    pg.put(currentOrg, "String");
    Map map = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      map = (Map)ejbProxy.invoke("getSubOrgAndUsers", pg.getParameters());
    } catch (Exception e) {
      logger.error("error from  MessageBD g getSubOrgAndUsers information :" + e.getMessage());
    } 
    return map;
  }
  
  public Map getSubOrgAndUsersEmail(String orgId, String currentOrg) {
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(orgId, "String");
    pg.put(currentOrg, "String");
    Map map = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      map = (Map)ejbProxy.invoke("getSubOrgAndUsersEmail", pg.getParameters());
    } catch (Exception e) {
      logger.error("error from  MessageBD g getSubOrgAndUsers information :" + e.getMessage());
    } 
    return map;
  }
  
  public List getSubOrgs(String orgId) {
    List subOrg = null;
    ParameterGenerator parameterGenerator = new ParameterGenerator(1);
    parameterGenerator.put(new Long(orgId), "Long");
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", 
          MessageEJBHome.class);
      subOrg = (List)ejbProxy.invoke("getSubOrgs", 
          parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("from  MessageBD Can not get organization's info:" + e.getMessage());
    } 
    return subOrg;
  }
  
  public List getUserList(String para, String vo, String where) {
    List userList = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(para, String.class);
    pg.put(vo, "String");
    pg.put(where, "String");
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", 
          MessageEJBHome.class);
      userList = (List)ejbProxy.invoke("getUserList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to get User information List:" + e.getMessage());
    } finally {}
    return userList;
  }
  
  public String getMsAccountInfo(String domainId) {
    String result = "";
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(domainId, String.class);
      result = ejbProxy.invoke("getMsAccountInfo", p.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public boolean modelSendMsg(String ids, String contents, String mobile, String domainId, String senderId, Date sendTime, Long dataId) {
    boolean result = false;
    ParameterGenerator p = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", "MessageEJBLocal", MessageEJBHome.class);
      p.put(ids, String.class);
      p.put(contents, String.class);
      p.put(mobile, String.class);
      p.put(domainId, String.class);
      p.put(senderId, String.class);
      p.put(sendTime, Date.class);
      p.put(dataId, Long.class);
      result = ((Boolean)ejbProxy.invoke("modelSendMsg", p.getParameters())).booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getSenderBySerial(String serial, String domainId) {
    List userList = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(serial, String.class);
    pg.put(domainId, String.class);
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", 
          MessageEJBHome.class);
      userList = (List)ejbProxy.invoke("getSenderBySerial", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to getSenderBySerial information List:" + 
          e.getMessage());
    } finally {}
    return userList;
  }
  
  public String getSerialByUserId(String userId) {
    String result = "";
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", MessageEJBHome.class);
      p.put(userId, String.class);
      result = ejbProxy.invoke("getSerialByUserId", p.getParameters())
        .toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String checkSendMsCount(String userId, String domainId) {
    String result = "";
    ParameterGenerator p = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", MessageEJBHome.class);
      p.put(userId, String.class);
      p.put(domainId, String.class);
      result = ejbProxy.invoke("checkSendMsCount", p.getParameters())
        .toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public MsInfoListPO updateViewMsg(String msgId) {
    MsInfoListPO result = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", MessageEJBHome.class);
      p.put(msgId, String.class);
      result = (MsInfoListPO)ejbProxy.invoke("updateViewMsg", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getNewMsglist(String userIds) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("MessageEJB", 
          "MessageEJBLocal", MessageEJBHome.class);
      list = (List)ejbProxy.invoke("getNewMsglist", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error from  MessageBD getNewMsglist information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public String getUserPhone(String userId) {
    MessageEJBBean bean = new MessageEJBBean();
    return bean.getUserPhone(userId);
  }
}
