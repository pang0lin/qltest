package com.js.system.service.messages.disrupter;

import com.lmax.disruptor.EventFactory;

class MessagesEvent {
  public static final EventFactory<MessagesEvent> FACTORY = new EventFactory<MessagesEvent>() {
      public MessagesEvent newInstance() {
        return new MessagesEvent();
      }
    };
  
  private MessagesBean messagesBean;
  
  public MessagesBean getMessagesBean() {
    return this.messagesBean;
  }
  
  public void setMessagesBean(MessagesBean messagesBean) {
    this.messagesBean = messagesBean;
  }
}
