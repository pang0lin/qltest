package com.js.oa.chat.bean;

import com.js.oa.chat.po.ChatAttachPO;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ChatBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean sendChat(ChatPO chatPO, String[] fileName, String[] saveName, String chatToId) {
    boolean result = false;
    try {
      begin();
      long chatId = ((Long)this.session.save(chatPO)).longValue();
      HashSet chatAttach = new HashSet();
      if (fileName != null) {
        chatPO.setChatHasattach("1");
        for (int j = 0; j < fileName.length; j++) {
          if (!"".equals(fileName[j])) {
            ChatAttachPO chatAttachPO = new ChatAttachPO();
            chatAttachPO.setFileName(fileName[j]);
            chatAttachPO.setFileSaveName(saveName[j]);
            chatAttachPO.setChat(chatPO);
            this.session.save(chatAttachPO);
          } 
        } 
      } else {
        chatPO.setChatHasattach("0");
      } 
      ChatUserPO chatUserPO = new ChatUserPO();
      chatUserPO.setChatStatus("0");
      chatUserPO.setEmpId(chatPO.getSenderId());
      chatUserPO.setIsRead("1");
      chatUserPO.setChat(chatPO);
      this.session.save(chatUserPO);
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      String msgTitle = chatPO.getChatContent();
      if (msgTitle.length() > 50) {
        msgTitle = msgTitle.substring(0, 45);
        msgTitle = String.valueOf(msgTitle) + "...";
      } 
      List<MessagesVO> msgList = new ArrayList();
      String[] toIds = chatToId.split("\\$");
      for (int i = 0; i < toIds.length; i++) {
        if (!toIds[i].equals("")) {
          chatUserPO = new ChatUserPO();
          chatUserPO.setChatStatus("1");
          chatUserPO.setEmpId(toIds[i]);
          chatUserPO.setIsRead("0");
          chatUserPO.setChat(chatPO);
          this.session.save(chatUserPO);
          MessagesVO messagesVO = new MessagesVO();
          messagesVO.setMessage_send_UserName(chatPO.getSenderName());
          messagesVO.setMessage_type("Chat");
          messagesVO.setMessage_send_UserId(Long.parseLong(chatPO.getSenderId()));
          messagesVO.setMessage_show(1);
          messagesVO.setMessage_status(1);
          messagesVO.setMessage_time(new Date());
          messagesVO.setMessage_title(msgTitle);
          messagesVO.setMessage_url("/jsoa/chat/showChat.jsp?id=" + chatId + "&isRead=0");
          messagesVO.setMessage_toUserId(Long.parseLong(toIds[i]));
          messagesVO.setMessage_date_begin(new Date());
          messagesVO.setData_id(chatId);
          messagesVO.setMessage_date_end(tmp.getTime());
          msgList.add(messagesVO);
        } 
      } 
      if (msgList.size() > 0) {
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.messageArrayAdd(msgList);
      } 
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean deleteChat(String chatUserIds, String userId, String timeScan) {
    boolean result = false;
    String where = " where cu.emp_id=" + userId;
    String databaseType = SystemCommon.getDatabaseType();
    if (!"".equals(timeScan))
      if (!"all".equals(timeScan)) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if ("new".equals(timeScan)) {
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
          calendar.add(6, -7);
          where = String.valueOf(where) + " and chat.chat_time > " + getDateString(databaseType, calendar);
        } else if ("week".equals(timeScan)) {
          calendar.add(6, -7);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("month".equals(timeScan)) {
          calendar.add(2, -1);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("tmonth".equals(timeScan)) {
          calendar.add(2, -3);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("hyear".equals(timeScan)) {
          calendar.add(2, -6);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("year".equals(timeScan)) {
          calendar.add(2, -12);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } 
      }  
    if (!"".equals(chatUserIds)) {
      chatUserIds = chatUserIds.substring(0, chatUserIds.trim().length() - 1);
      where = String.valueOf(where) + " and cu.id in (" + chatUserIds + ")";
    } 
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      List<String> chatList = new ArrayList();
      String sql = "select distinct chat.chat_id from oa_chat_user cu left join oa_chat chat on cu.chat_id=chat.chat_id " + where;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        chatList.add(rs.getString(1)); 
      rs.close();
      rs = stmt.executeQuery("select cu.id from oa_chat_user cu left join oa_chat chat on cu.chat_id=chat.chat_id " + where);
      String ids = "";
      while (rs.next())
        ids = String.valueOf(ids) + rs.getString(1) + ","; 
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      sql = "delete from oa_chat_user  where id in(" + ids + ")";
      stmt.executeUpdate(sql);
      rs.close();
      int sum = 0;
      for (int i = 0; i < chatList.size(); i++) {
        sum = 1;
        rs = stmt.executeQuery("select count(id) from oa_chat_user where chat_id=" + chatList.get(i).toString());
        if (rs.next())
          sum = rs.getInt(1); 
        rs.close();
        if (sum == 0) {
          stmt.executeUpdate("delete from oa_chat_attach where chat_id=" + chatList.get(i).toString());
          stmt.executeUpdate("delete from oa_chat where chat_id=" + chatList.get(i).toString());
        } 
      } 
      stmt.close();
      conn.close();
      result = true;
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean markAsRead(String chatUserIds, String userId, String timeScan) {
    boolean result = false;
    String where = " where cu.emp_id=" + userId;
    String databaseType = SystemCommon.getDatabaseType();
    if (!"".equals(timeScan))
      if (!"all".equals(timeScan)) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if ("new".equals(timeScan)) {
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
          calendar.add(6, -7);
          where = String.valueOf(where) + " and chat.chat_time > " + getDateString(databaseType, calendar);
        } else if ("week".equals(timeScan)) {
          calendar.add(6, -7);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("month".equals(timeScan)) {
          calendar.add(2, -1);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("tmonth".equals(timeScan)) {
          calendar.add(2, -3);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("hyear".equals(timeScan)) {
          calendar.add(2, -6);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } else if ("year".equals(timeScan)) {
          calendar.add(2, -12);
          where = String.valueOf(where) + " and chat.chat_time < " + getDateString(databaseType, calendar);
        } 
      }  
    if (!"".equals(chatUserIds)) {
      chatUserIds = chatUserIds.substring(0, chatUserIds.trim().length() - 1);
      where = String.valueOf(where) + " and cu.id in (" + chatUserIds + ")";
    } 
    Connection conn = null;
    ResultSet rs = null;
    String chatid = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery("select cu.id,cu.chat_id from oa_chat_user cu left join oa_chat chat on cu.chat_id=chat.chat_id " + where);
      String ids = "";
      while (rs.next()) {
        ids = String.valueOf(ids) + rs.getString(1) + ",";
        chatid = String.valueOf(chatid) + rs.getString(2) + ",";
      } 
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      if (chatid.endsWith(","))
        chatid = chatid.substring(0, chatid.length() - 1); 
      String sql = "update oa_chat_user chatUser set chatUser.isread=1 where chatUser.id in(" + ids + ")";
      stmt.executeUpdate(sql);
      sql = "UPDATE sys_messages SET message_status=0 WHERE data_id IN(" + chatid + ")";
      int count = stmt.executeUpdate(sql);
      rs.close();
      stmt.close();
      conn.close();
      result = true;
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public ChatPO getSingleChat(String chatId) {
    ChatPO chatPO = null;
    try {
      begin();
      chatPO = (ChatPO)this.session.load(ChatPO.class, Long.valueOf(chatId));
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return chatPO;
  }
  
  public String getCountByHQL(String sql) {
    String sum = "0";
    try {
      begin();
      sum = this.session.createQuery(sql).iterate().next().toString();
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return sum;
  }
  
  public List getChatAttach(String chatId) {
    List list = new ArrayList();
    try {
      begin();
      list = this.session.createQuery("select po from com.js.oa.chat.po.ChatAttachPO po left join po.chat chat where chat.chatId=" + chatId).list();
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String getDateString(String databaseType, Calendar cal) {
    String result = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (databaseType.indexOf("oracle") >= 0) {
      result = "JSDB.FN_STRTODATE('" + dateFormat.format(cal.getTime()) + "','')";
    } else {
      result = "'" + dateFormat.format(cal.getTime()) + "'";
    } 
    return result;
  }
  
  public String getName(String sendId) throws Exception {
    String username = "";
    try {
      begin();
      List list = this.session.createQuery("select user.empName from com.js.system.vo.usermanager.EmployeeVO user where user.empId='" + 
          sendId + "'").list();
      if (list.size() > 0)
        username = String.valueOf(list.get(0)); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return username;
  }
  
  public ChatPO getByChatID(String chatId) throws Exception {
    ChatPO chatPO = null;
    try {
      begin();
      List<ChatPO> list = this.session.createQuery("from  ChatPO chatPO where chatPO.chatId='" + chatId + "'").list();
      if (list.size() > 0)
        chatPO = list.get(0); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return chatPO;
  }
  
  public long addChat(ChatPO chatPO) throws Exception {
    Long chatId = null;
    try {
      begin();
      chatId = (Long)this.session.save(chatPO);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return chatId.longValue();
  }
  
  public long addChatUser(ChatUserPO chatUserPO) throws Exception {
    Long chatUserId = null;
    try {
      begin();
      chatUserId = (Long)this.session.save(chatUserPO);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return chatUserId.longValue();
  }
}
