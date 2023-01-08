package com.js.oa.messageWall.service;

import com.js.oa.messageWall.bean.MessageWallBean;
import com.js.oa.messageWall.po.MessageWallPO;
import java.util.List;

public class MessageWallService {
  public long addMessage(MessageWallPO mw) {
    return (new MessageWallBean()).addMessage(mw);
  }
  
  public int updateMessage(MessageWallPO mw) {
    return (new MessageWallBean()).updateMessage(mw);
  }
  
  public int deleteMessage(long messageWallId) {
    return (new MessageWallBean()).deleteMessage(messageWallId);
  }
  
  public List<MessageWallPO> showAll(String whereSQL) {
    return (new MessageWallBean()).showAll(whereSQL);
  }
  
  public int getCurNum(long userId) {
    return (new MessageWallBean()).getCurNum(userId);
  }
  
  public int getMaxNum() {
    return (new MessageWallBean()).getMaxNum();
  }
  
  public boolean getRightByUserId(long userId) {
    return (new MessageWallBean()).getRightByUserId(userId);
  }
  
  public boolean isAdmin(long userId) {
    return (new MessageWallBean()).isAdmin(userId);
  }
}
