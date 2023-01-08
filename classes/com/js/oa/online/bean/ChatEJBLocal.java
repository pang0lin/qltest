package com.js.oa.online.bean;

import com.js.oa.online.po.ChatAccessoryPO;
import com.js.oa.online.po.ChatPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ChatEJBLocal extends EJBLocalObject {
  void addChat(ChatPO paramChatPO) throws Exception;
  
  List get(String paramString) throws Exception;
  
  String getName(String paramString) throws Exception;
  
  void addChatAccessory(ChatAccessoryPO paramChatAccessoryPO) throws Exception;
  
  List getChatAccessory(String paramString) throws Exception;
  
  ChatPO getByChatID(String paramString) throws Exception;
}
