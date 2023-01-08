package com.js.thread;

import com.ali.dingding.util.DingMessageUtil;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.manage.DingdingManageAction;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.util.config.SystemCommon;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMap;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import com.qq.weixin.mp.service.WeixinMessageUrlGetterFactory;
import com.qq.weixin.mp.util.SysMessageUtil;

public class DingdingTask extends Thread {
  private void execute() {
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "select message_id, message_type, message_title, message_send_username,   (select max(useraccounts) from org_employee where emp_id= message.message_toUserId) userid,    message_toUserId,message_Url,data_Id   from sys_messages message  where (dingdingremindflag is null    or dingdingremindflag = ''    or dingdingremindflag = '0')";
    if (databaseType.indexOf("oracle") >= 0) {
      sql = String.valueOf(sql) + " and rownum<=1";
    } else if (databaseType.indexOf("mysql") >= 0) {
      sql = String.valueOf(sql) + " limit 0,1 ";
    } 
    while (true) {
      try {
        DbOpt db = new DbOpt();
        String[][] result = (String[][])null;
        try {
          result = db.executeQueryToStrArr2(sql, 8);
          db.close();
        } catch (Exception e) {
          e.printStackTrace();
          if (db != null)
            db.close(); 
        } 
        if (result != null && result.length > 0) {
          String messageId = result[0][0];
          String messageType = result[0][1];
          String messageTitle = result[0][2];
          String messageToUser = result[0][4];
          String toEmp_Id = result[0][5];
          String message_Url = result[0][6];
          String data_id = result[0][7];
          MessageTypeAppBhMap map = 
            SysMessageUtil.getMessageTypeAppBhMap(messageType);
          String appBh = "";
          String weixinPost = "";
          DbOpt db0 = null;
          try {
            db0 = new DbOpt();
            weixinPost = db0.executeQueryToStr("select dingdingpost from org_employee where emp_id=" + toEmp_Id);
            db0.close();
          } catch (Exception e) {
            e.printStackTrace();
            if (db0 != null)
              db0.close(); 
          } 
          if (weixinPost != null && "1".equals(weixinPost)) {
            DbOpt dbOpt = null;
            try {
              dbOpt = new DbOpt();
              dbOpt.executeUpdate("update sys_messages set dingdingremindflag = '2' where message_id = " + 
                  messageId);
              dbOpt.close();
            } catch (Exception e) {
              e.printStackTrace();
              if (dbOpt != null)
                dbOpt.close(); 
            } 
            if (map != null) {
              WeixinMessageUrlGetter getter = 
                WeixinMessageUrlGetterFactory.getWeixinMessageUrlGetter(messageType);
              if (getter == null)
                continue; 
              String[] remindInfo = getter.getRemindInfo(messageTitle, 
                  message_Url, data_id, toEmp_Id);
              if (remindInfo == null)
                continue; 
              String remindTitle = remindInfo[0];
              String remindDescription = remindInfo[1];
              String remindPicURL = remindInfo[2];
              if (remindPicURL != null && !"".equals(remindPicURL)) {
                if (remindPicURL.startsWith("/jsoa")) {
                  remindPicURL = remindPicURL.substring("/jsoa".length());
                } else if (remindPicURL.startsWith("jsoa")) {
                  remindPicURL = remindPicURL.substring("jsoa".length());
                } else if (!remindPicURL.startsWith("/")) {
                  remindPicURL = "/" + remindPicURL;
                } 
                remindPicURL = String.valueOf(WeixinManageAction.getPropValue("serverUrl")) + remindPicURL;
              } 
              appBh = map.getAppBh();
              if (!"".equals(appBh) && AppRoom.isAppUse(appBh))
                if ("1".equals(map.getWithLink())) {
                  String url = DingdingManageAction.getPropValue("serverUrl");
                  url = String.valueOf(url) + "/dingding?id=" + messageId + "&dd_share=false&bh=" + appBh;
                  DingMessageUtil.sendDingMessageToUser(messageToUser, 
                      appBh, remindTitle, remindDescription, url, 
                      remindPicURL);
                } else {
                  DingMessageUtil.sendDingMessageToUser(messageToUser, 
                      appBh, remindTitle, remindDescription, "", 
                      remindPicURL);
                }  
            } 
          } 
          DbOpt db1 = null;
          try {
            db1 = new DbOpt();
            db1.executeUpdate("update sys_messages set dingdingremindflag = '1' where message_id = " + 
                messageId);
            db1.close();
          } catch (Exception e) {
            e.printStackTrace();
            if (db1 != null)
              db1.close(); 
          } 
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          } 
          continue;
        } 
        try {
          Thread.sleep(2000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } catch (Exception exception) {}
    } 
  }
  
  public void run() {
    execute();
  }
}
