package com.js.oa.online.bean;

import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.online.po.ChatAccessoryPO;
import com.js.oa.online.po.ChatPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class ChatEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
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
  
  public List get(String userID) throws Exception {
    List chatList = null;
    try {
      begin();
      String hql = "from ChatPO as chatPO where chatPO.toUserId='" + 
        userID + "' and chatPO.isRead='" + '\001' + 
        "' order by chatPO.chatId desc";
      Query query = this.session.createQuery(hql);
      if (!query.list().isEmpty())
        chatList = query.list(); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return chatList;
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
  
  public void addChatAccessory(ChatAccessoryPO chatAccessoryPO) throws Exception {
    try {
      begin();
      this.session.save(chatAccessoryPO);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
  }
  
  public List getChatAccessory(String chatId) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery(
          "from  ChatAccessoryPO chatAccessoryPO where chatAccessoryPO.chatPO.chatId='" + 
          chatId + "'").list();
      if (list.size() <= 0)
        list = new ArrayList(); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return list;
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
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return listInfo;
  }
  
  public int getCountChat(String hql) {
    int returnInt = 0;
    try {
      begin();
      List list = this.session.createQuery(hql).list();
      returnInt = list.size();
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return returnInt;
  }
}
