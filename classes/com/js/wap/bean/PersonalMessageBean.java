package com.js.wap.bean;

import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

public class PersonalMessageBean extends HibernateBase implements SessionBean {
  Logger log = Logger.getLogger(PersonalMessageBean.class);
  
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getMessageList(String userId, String orgId, String orgIdString, int beginIndex, int limited) throws Exception {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List<Object[]> messagelList = null;
    try {
      begin();
      String sql = (
        "select po.chatId, po.chatContent, po.chatTo, po.chatHasattach, po.chatAttachsize, po.senderId, po.senderName, po.chatTime, cu.empId, cu.chatStatus, cu.isRead " + 
        "from com.js.oa.chat.po.ChatUserPO cu left join cu.chat po " + 
        
        "where cu.empId=USER_ID order by po.chatTime desc")
        .replaceAll("USER_ID", userId);
      String sqlGroupBy = sql;
      this.log.debug("sqlGroupBy = " + sqlGroupBy);
      Query query = this.session.createQuery(sqlGroupBy);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      messagelList = query.list();
      Query queryCount = this.session.createQuery(sqlGroupBy);
      recordCount = queryCount.list().size();
      StringBuilder chatIdsRange = new StringBuilder();
      for (int i = 0; i < messagelList.size(); i++) {
        chatIdsRange.append(((Object[])messagelList.get(i))[0].toString());
        if (i != messagelList.size() - 1)
          chatIdsRange.append(","); 
      } 
      if (chatIdsRange.length() < 1)
        chatIdsRange.append("-1"); 
      String sqlRange = sql.replace("where", "where po.chatId in (" + chatIdsRange.toString() + ") and");
      this.log.debug("sqlRange = " + sqlRange);
      messagelList = this.session.createQuery(sqlRange).list();
      StringBuilder chatIdsTemp = new StringBuilder();
      for (int j = 0; j < messagelList.size(); j++) {
        Object[] obj = messagelList.get(j);
        if (Integer.parseInt((obj[9] == null) ? "0" : obj[9].toString()) == 0 && obj[0] != null)
          chatIdsTemp.append(String.valueOf(obj[0].toString()) + ","); 
      } 
      if (chatIdsTemp.toString().endsWith(",")) {
        String chatIds = chatIdsTemp.substring(0, chatIdsTemp.length() - 1);
        String sqlSender = sql.replace("cu.empId=", "po.senderId=")
          .replace("where", "where po.chatId in (" + chatIds + ") and cu.chatStatus='1' and");
        this.log.debug("sqlSender = " + sqlSender);
        List<Object[]> senderList = this.session.createQuery(sqlSender).list();
        for (int k = 0; k < senderList.size(); k++)
          messagelList.add(senderList.get(k)); 
      } 
      queryMap.put("QUERY_LIST", messagelList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return queryMap;
  }
  
  public String getUseAccounts(String chatId, String emp_id) throws Exception {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    String userAccounts = "";
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select DISTINCT c.userAccounts,a.emp_id from oa_chat_user a,org_employee c  where a.emp_id=c.emp_id and  a.chat_Id=" + chatId + "  order by a.emp_id asc ";
      rSet = stmt.executeQuery(sqlString);
      while (rSet.next())
        userAccounts = String.valueOf(userAccounts) + rSet.getString(1) + ","; 
      rSet.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return userAccounts;
  }
  
  public void setRead(String chatId, String userId) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String sql = "update oa_chat_user set isread ='1' where emp_id=" + userId + " and chat_id ='" + chatId + "'";
      dbopt.executeUpdate(sql);
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + chatId + " and message_type ='Chat'  and  message_toUserId=" + userId + " and message_date_begin <= now() and message_date_end >= now()");
      } else if (databaseType.indexOf("oracle") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + chatId + " and message_type ='Chat'  and  message_toUserId=" + userId + " and message_date_begin <= sysdate and message_date_end >= sysdate");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void replyMessage(String userId, String userName, String toID, String content, String userNames) throws Exception {
    UserBD userBD = new UserBD();
    ChatService chatDB = new ChatService();
    MessagesBD messagesBD = new MessagesBD();
    String sendID = userId;
    String sendName = userName;
    ChatPO chatPO = new ChatPO();
    if (toID == null)
      toID = ""; 
    String sendToid = "";
    if (toID.indexOf(",") > 0 && toID != null) {
      String[] userIds_ = toID.split(",");
      for (int i = 0; i < userIds_.length; i++)
        sendToid = String.valueOf(sendToid) + "$" + userIds_[i] + "$"; 
      toID = sendToid;
    } 
    String[] strTmp = toID.split("\\$");
    List<String> list = new ArrayList();
    for (int k = 0; k < strTmp.length; k++) {
      if (!strTmp[k].equals(""))
        list.add(strTmp[k]); 
    } 
    Calendar tmp = Calendar.getInstance();
    tmp.set(2050, 12, 12);
    String chatTime = getCurrentTime().substring(0, 16);
    String msgTitle = content;
    if (msgTitle.length() > 20) {
      msgTitle = msgTitle.substring(0, 20);
      msgTitle = String.valueOf(msgTitle) + "...";
    } 
    begin();
    try {
      chatPO.setChatTo(userNames);
      chatPO.setChatContent(content);
      chatPO.setSenderId(sendID);
      chatPO.setChatTime(new Date());
      chatPO.setChatHasattach("0");
      chatPO.setSenderName(userName);
      long dataId = ((Long)this.session.save(chatPO)).longValue();
      ChatUserPO chatUserPO = new ChatUserPO();
      chatUserPO.setChatStatus("0");
      chatUserPO.setEmpId(chatPO.getSenderId());
      chatUserPO.setIsRead("1");
      chatUserPO.setChat(chatPO);
      this.session.save(chatUserPO);
      for (int j = 0; j < list.size(); j++) {
        chatUserPO = new ChatUserPO();
        chatUserPO.setChatStatus("1");
        chatUserPO.setEmpId(list.get(j));
        chatUserPO.setIsRead("0");
        chatUserPO.setChat(chatPO);
        this.session.save(chatUserPO);
        MessagesVO messagesVO = new MessagesVO();
        messagesVO.setMessage_send_UserName(sendName);
        messagesVO.setMessage_type("Chat");
        messagesVO.setMessage_send_UserId(Long.parseLong(sendID));
        messagesVO.setMessage_show(1);
        messagesVO.setMessage_status(1);
        messagesVO.setMessage_time(new Date());
        messagesVO.setMessage_title(msgTitle);
        messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + dataId);
        messagesVO.setMessage_toUserId(Long.parseLong(list.get(j)));
        messagesVO.setMessage_date_begin(new Date());
        messagesVO.setData_id(dataId);
        messagesVO.setMessage_date_end(tmp.getTime());
        messagesBD.messageAdd(messagesVO);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private String getCurrentTime() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String dateString = formatter.format(currentTime);
    return dateString;
  }
}
