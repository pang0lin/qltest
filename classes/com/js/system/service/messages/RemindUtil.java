package com.js.system.service.messages;

import com.js.oa.message.service.MsManageBD;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.messages.disrupter.DisrupterContext;
import com.js.system.service.messages.disrupter.MessagesBean;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.messages.MessagesVO;
import com.js.system.vo.messages.Remind;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import org.apache.commons.httpclient.HttpException;

public class RemindUtil {
  public static void addRemind(long userID, long dataid, String remind_type) throws Exception {
    Remind forumRemind = new Remind();
    forumRemind.setEmp_id(userID);
    forumRemind.setData_id(dataid);
    forumRemind.setRemind_type(remind_type);
    (new RemindBD()).addRemind(forumRemind);
  }
  
  public static void delRemind(long userID, long dataid, String remind_type) throws Exception {
    (new RemindBD()).delRemind(userID, dataid, remind_type);
  }
  
  public static String searchYesOrNo(long userId, long dataId, String remind_type) throws Exception {
    String re = "N";
    re = (new RemindBD()).searchYesOrNo(userId, dataId, remind_type);
    return re;
  }
  
  public static String getUseEmailFlag() {
    SysSetupReader ssr = new SysSetupReader();
    String str = ssr.getEmail("0");
    String useEmailFlag = str.split(";")[0];
    return useEmailFlag;
  }
  
  public static Boolean getSysControlConfig() {
    Boolean flag = Boolean.valueOf(false);
    SysSetupReader sysRed = SysSetupReader.getInstance();
    String options = sysRed.getSystemOption("0");
    if (options.charAt(2) == '1')
      flag = Boolean.valueOf(true); 
    return flag;
  }
  
  public static Boolean judgeUserReceiveMs(Boolean receiveMs, String userId) {
    Boolean flag = Boolean.valueOf(false);
    try {
      if (receiveMs.booleanValue()) {
        OrganizationBD orgBD = new OrganizationBD();
        String orgId = orgBD.getOrgIdByUserID(userId);
        String sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=2 and po.msManage.msId=1";
        MsManageBD msBD = new MsManageBD();
        List<Object[]> msList = msBD.getListByYourSQL(sql);
        if (msList != null && msList.size() != 0)
          for (int i = 0; i < msList.size(); i++) {
            Object[] obj = msList.get(i);
            sql = "select vo.orgId,vo.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO vo where vo.orgIdString like '%$" + 
              orgId + "$%' and orgIdString like '%$" + obj[1] + "$%'";
            List orgIdStingList = msBD.getListByYourSQL(sql);
            if (orgIdStingList != null && orgIdStingList.size() > 0)
              flag = Boolean.valueOf(true); 
          }  
        if (!flag.booleanValue()) {
          GroupBD groupBD = new GroupBD();
          List<E> userGroupList = groupBD.searchByUserid(Long.valueOf(userId).longValue());
          if (userGroupList != null && userGroupList.size() != 0) {
            String userGroupId = userGroupList.get(0).toString();
            sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=3 and po.msManage.msId=1";
            msList = msBD.getListByYourSQL(sql);
            if (msList != null && msList.size() != 0)
              for (int i = 0; i < msList.size(); i++) {
                Object[] obj = msList.get(i);
                if (userGroupId.equals(obj[1]))
                  flag = Boolean.valueOf(true); 
              }  
          } 
        } 
        if (!flag.booleanValue()) {
          sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=1 and po.msManage.msId=1";
          msList = msBD.getListByYourSQL(sql);
          if (msList != null && msList.size() != 0)
            for (int i = 0; i < msList.size(); i++) {
              Object[] obj = msList.get(i);
              if (userId.equals(obj[1]))
                flag = Boolean.valueOf(true); 
            }  
        } 
      } else {
        flag = Boolean.valueOf(true);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public static void sendMessages(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, hasURL, 1);
  }
  
  public static void sendMessages(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL, int sendSMS) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, hasURL, sendSMS, 1);
  }
  
  public static void sendMessages(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL, int sendSMS, int sendEmail) {
    if (!userIds.equals("")) {
      MessagesBean messagesBean = new MessagesBean();
      messagesBean.setMethodName("sendMessages");
      messagesBean.setTitle(title);
      messagesBean.setUrl(url);
      messagesBean.setUserIds(userIds);
      messagesBean.setModuleType(moduleType);
      messagesBean.setStartTime(startTime);
      messagesBean.setEndTime(endTime);
      messagesBean.setHasURL(hasURL);
      messagesBean.setSendSMS(sendSMS);
      messagesBean.setSendEmail(sendEmail);
      DisrupterContext.getInstance().sendMessages(messagesBean);
      getUserInfoByUserName(messagesBean.getUserIds(), title, url, moduleType);
    } 
  }
  
  public static void sendMessageToUsers(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id) {
    sendMessageToUsers(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, 1);
  }
  
  public static void sendMessageToUsers(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int sendSMS) {
    sendMessageToUsers(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, sendSMS, 1);
  }
  
  public static void sendMessageToUsers(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int sendSMS, int sendEmail) {
    if (!userIds.equals("")) {
      MessagesBean messagesBean = new MessagesBean();
      messagesBean.setMethodName("sendMessageToUsers");
      messagesBean.setTitle(title);
      messagesBean.setUrl(url);
      messagesBean.setUserIds(userIds);
      messagesBean.setModuleType(moduleType);
      messagesBean.setStartTime(startTime);
      messagesBean.setEndTime(endTime);
      messagesBean.setSend_UserName(send_UserName);
      messagesBean.setData_id(data_id);
      messagesBean.setSendSMS(sendSMS);
      messagesBean.setSendEmail(sendEmail);
      DisrupterContext.getInstance().sendMessages(messagesBean);
      getUserInfoByUserName(messagesBean.getUserIds(), title, url, moduleType);
    } 
  }
  
  public static void sendMessageToUsers1(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id) {
    sendMessageToUsers1(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, 1);
  }
  
  public static void sendMessageToUsers1(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int sendSMS) {
    sendMessageToUsers1(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, sendSMS, 1);
  }
  
  public static void sendMessageToUsers1(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int sendSMS, int sendEmail) {
    if (!userIds.equals("")) {
      MessagesBean messagesBean = new MessagesBean();
      messagesBean.setMethodName("sendMessageToUsers1");
      messagesBean.setTitle(title);
      messagesBean.setUrl(url);
      messagesBean.setUserIds(userIds);
      messagesBean.setModuleType(moduleType);
      messagesBean.setStartTime(startTime);
      messagesBean.setEndTime(endTime);
      messagesBean.setSend_UserName(send_UserName);
      messagesBean.setData_id(data_id);
      messagesBean.setSendSMS(sendSMS);
      messagesBean.setSendEmail(sendEmail);
      DisrupterContext.getInstance().sendMessages(messagesBean);
      getUserInfoByUserName(messagesBean.getUserIds(), title, url, moduleType);
    } 
  }
  
  public static void sendMessageToUsers2(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int showType) {
    sendMessageToUsers2(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, showType, 1);
  }
  
  public static void sendMessageToUsers2(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int showType, int sendSMS) {
    sendMessageToUsers2(title, url, userIds, moduleType, startTime, endTime, send_UserName, data_id, showType, sendSMS, 1);
  }
  
  public static void sendMessageToUsers2(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String send_UserName, Long data_id, int showType, int sendSMS, int sendEmail) {
    if (!userIds.equals("")) {
      MessagesBean messagesBean = new MessagesBean();
      messagesBean.setMethodName("sendMessageToUsers2");
      messagesBean.setTitle(title);
      messagesBean.setUrl(url);
      messagesBean.setUserIds(userIds);
      messagesBean.setModuleType(moduleType);
      messagesBean.setStartTime(startTime);
      messagesBean.setEndTime(endTime);
      messagesBean.setSend_UserName(send_UserName);
      messagesBean.setData_id(data_id);
      messagesBean.setShowType(showType);
      messagesBean.setSendSMS(sendSMS);
      messagesBean.setSendEmail(sendEmail);
      DisrupterContext.getInstance().sendMessages(messagesBean);
      getUserInfoByUserName(messagesBean.getUserIds(), title, url, moduleType);
    } 
  }
  
  private static boolean getUserInfoByUserName(String userIdstr, String title, String url, String moduleType) {
    boolean result = false;
    if (SystemCommon.getAppUse() == 1 && !userIdstr.equals("")) {
      int type = 3;
      try {
        result = (new MessageAppDaXing()).getUserInfoByUserName(userIdstr, type, title, url);
      } catch (HttpException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public static void sendMessageToUsers(String title, String url, String userIds, String moduleType, Date startTime, Date endTime) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, 1);
  }
  
  public static void sendMessageToUsers(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, String sendSMS) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, 1, Integer.valueOf(sendSMS).intValue());
  }
  
  public static void sendMessageToUsersWithType(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int type) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, type);
  }
  
  public static void sendMessageToUsersNoURL(String title, String url, String userIds, String moduleType, Date startTime, Date endTime) {
    sendMessages(title, url, userIds, moduleType, startTime, endTime, 2);
  }
  
  public static void sendMessageToUsers(List<MessagesVO> msgList) {
    String title = "", url = "", userIds = "", moduleType = "", senUserName = "";
    if (msgList != null && msgList.size() > 0)
      for (int i = 0; i < msgList.size(); i++) {
        MessagesVO msg = msgList.get(i);
        title = msg.getMessage_title();
        url = msg.getMessage_url();
        userIds = String.valueOf(msg.getMessage_toUserId());
        moduleType = msg.getMessage_type();
        senUserName = msg.getMessage_send_UserName();
        Long dataId = Long.valueOf(msg.getData_id());
        sendMessageToUsers1(title, url, userIds, moduleType, msg.getMessage_date_begin(), 
            msg.getMessage_date_end(), senUserName, dataId, msg.getSendSMS());
      }  
  }
  
  public static void sendMessageToUsersWithShowType(List<MessagesVO> msgList) {
    String title = "", url = "", userIds = "", moduleType = "", senUserName = "";
    if (msgList != null && msgList.size() > 0)
      for (int i = 0; i < msgList.size(); i++) {
        MessagesVO msg = msgList.get(i);
        title = msg.getMessage_title();
        url = msg.getMessage_url();
        userIds = String.valueOf(msg.getMessage_toUserId());
        moduleType = msg.getMessage_type();
        senUserName = msg.getMessage_send_UserName();
        Long dataId = Long.valueOf(msg.getData_id());
        int showType = msg.getMessage_show();
        sendMessageToUsers2(title, url, userIds, moduleType, msg.getMessage_date_begin(), 
            msg.getMessage_date_end(), senUserName, dataId, showType, msg.getSendSMS());
      }  
  }
  
  public static Boolean deleteMsg(String dataId, String msgType) throws Exception {
    Boolean flag = Boolean.FALSE;
    Connection conn = (new DataSourceBase()).getDataSource().getConnection();
    Statement stmt = conn.createStatement();
    String sqlString = "delete from sys_messages where data_id='" + dataId + "' and message_type='" + msgType + "'";
    try {
      stmt.executeUpdate(sqlString);
      flag = Boolean.TRUE;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return flag;
  }
  
  public static void sendMessagesExceptMe(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL, String myId) {
    sendMessagesExceptMe(title, url, userIds, moduleType, startTime, endTime, hasURL, myId, 1);
  }
  
  public static void sendMessagesExceptMe(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL, String myId, int sendSMS) {
    sendMessagesExceptMe(title, url, userIds, moduleType, startTime, endTime, hasURL, myId, sendSMS, 1);
  }
  
  public static void sendMessagesExceptMe(String title, String url, String userIds, String moduleType, Date startTime, Date endTime, int hasURL, String myId, int sendSMS, int sendEmail) {
    if (!userIds.equals("")) {
      MessagesBean messagesBean = new MessagesBean();
      messagesBean.setMethodName("sendMessagesExceptMe");
      messagesBean.setTitle(title);
      messagesBean.setUrl(url);
      messagesBean.setUserIds(userIds);
      messagesBean.setModuleType(moduleType);
      messagesBean.setStartTime(startTime);
      messagesBean.setEndTime(endTime);
      messagesBean.setHasURL(hasURL);
      messagesBean.setMyId(myId);
      messagesBean.setSendSMS(sendSMS);
      messagesBean.setSendEmail(sendEmail);
      DisrupterContext.getInstance().sendMessages(messagesBean);
      getUserInfoByUserName(messagesBean.getUserIds(), title, url, moduleType);
    } 
  }
}
