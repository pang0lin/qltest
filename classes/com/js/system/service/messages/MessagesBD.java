package com.js.system.service.messages;

import com.js.system.bean.messages.MessagesBean;
import com.js.system.vo.messages.MessagesVO;
import java.util.ArrayList;
import java.util.List;

public class MessagesBD {
  MessagesBean messagesBean = new MessagesBean();
  
  public void messageAdd(MessagesVO message) throws Exception {
    List<MessagesVO> msgList = new ArrayList();
    msgList.add(message);
    RemindUtil.sendMessageToUsersWithShowType(msgList);
  }
  
  public void messageArrayAdd(List messages) throws Exception {
    RemindUtil.sendMessageToUsersWithShowType(messages);
  }
  
  public void messageUpdate(long messageID) {
    this.messagesBean.messageUpdate(messageID);
  }
  
  public List selectByUserID(String userId) throws Exception {
    List list = new ArrayList();
    list = this.messagesBean.selectByUserID(userId);
    return list;
  }
  
  public int selectCountByUserID(String userId) throws Exception {
    Integer cum = Integer.valueOf(0);
    cum = Integer.valueOf(this.messagesBean.selectCountByUserID(userId));
    return cum.intValue();
  }
  
  public void delBatch(String ids, String type, String timeScan, String userId) throws Exception {
    this.messagesBean.delBatch(ids, type, timeScan, userId);
  }
  
  public void setMessageStatus(String emp_id, String status) throws Exception {
    this.messagesBean.setMessageStatus(emp_id, status);
  }
  
  public String serchMessageStatus(String emp_id) throws Exception {
    String re = "N";
    re = this.messagesBean.serchMessageStatus(emp_id);
    return re;
  }
  
  public void clearMessageStatus() throws Exception {
    this.messagesBean.clearMessageStatus();
  }
  
  public void changeMessageStatus(String data_id, String userId, String type, String be) throws Exception {
    this.messagesBean.changeMessageStatus(data_id, userId, type, be);
  }
  
  public void delMessage(String data_id, String type, String be) throws Exception {
    this.messagesBean.delMessage(data_id, type, be);
  }
  
  public void changeMessageStatusForBbs(String data_id, String type, String be) throws Exception {
    this.messagesBean.changeMessageStatusForBbs(data_id, type, be);
  }
  
  public void changeTaskStatus(String data_id, String userId) throws Exception {
    this.messagesBean.changeTaskStatus(data_id, userId);
  }
  
  public void readMessage(String data_id, String userId, String message_type) throws Exception {
    this.messagesBean.readMessage(data_id, userId, message_type);
  }
}
