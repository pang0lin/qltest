package com.js.oa.messageWall.service;

import com.js.oa.messageWall.bean.MessageWallGrantBean;
import com.js.oa.messageWall.po.MessageWallGrantPO;

public class MessageWallGrantService {
  public MessageWallGrantPO getMWGrant() {
    return (new MessageWallGrantBean()).getMWGrant();
  }
  
  public void updateMWGrant(MessageWallGrantPO mg) {
    (new MessageWallGrantBean()).updateMWGrant(mg);
  }
}
