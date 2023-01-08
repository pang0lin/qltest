package com.js.system.service.messages;

import com.js.system.bean.messages.RemindBean;
import com.js.system.vo.messages.Remind;
import java.util.ArrayList;
import java.util.List;

public class RemindBD {
  RemindBean remindBean = new RemindBean();
  
  public void addRemind(Remind forumRemind) throws Exception {
    this.remindBean.addRemind(forumRemind);
  }
  
  public List searchReminduser(long forumid, String remind_type) throws Exception {
    List list = new ArrayList();
    list = this.remindBean.searchReminduser(forumid, remind_type);
    return list;
  }
  
  public String searchYesOrNo(long userId, long dataId, String remind_type) throws Exception {
    String re = "N";
    re = this.remindBean.searchYesOrNo(userId, dataId, remind_type);
    return re;
  }
  
  public void delRemind(long userId, long dataId, String remind_type) throws Exception {
    this.remindBean.delRemind(userId, dataId, remind_type);
  }
}
