package com.js.thread;

import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.manage.WeixinManageAction;
import com.js.util.config.SystemCommon;
import com.js.util.util.IO2File;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.pojo.MessageTypeAppBhMap;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import com.qq.weixin.mp.service.WeixinMessageUrlGetterFactory;
import com.qq.weixin.mp.util.GetUrlUtil;
import com.qq.weixin.mp.util.MessageUtil;
import com.qq.weixin.mp.util.SysMessageUtil;
import com.yunshipei.util.MessageUtil;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeixinTask extends Thread {
  private void execute() {
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "select message_id, message_type, message_title, message_send_username,   (select max(useraccounts) from org_employee where emp_id= message.message_toUserId) userid,    message_toUserId,message_Url,data_Id   from sys_messages message  where (weixinremindflag is null    or weixinremindflag = ''    or weixinremindflag = '0')";
    String sql1 = "select empmobilephone, empemail from org_employee where emp_id=";
    if (databaseType.indexOf("oracle") >= 0) {
      sql = String.valueOf(sql) + " and message_date_begin <= sysdate and rownum<=1  order by message_id";
    } else if (databaseType.indexOf("mysql") >= 0) {
      sql = String.valueOf(sql) + " and message_date_begin <= now() order by message_id limit 0,1 ";
    } 
    while (true) {
      long time1 = (new Date()).getTime();
      try {
        DbOpt db = new DbOpt();
        String[][] result = (String[][])null;
        String[][] result1 = (String[][])null;
        try {
          result = db.executeQueryToStrArr2(sql, 8);
          if (result != null && result.length > 0)
            result1 = db.executeQueryToStrArr2(String.valueOf(sql1) + result[0][5], 2); 
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
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
          String mobilePhone = result1[0][0];
          String email = result1[0][1];
          Date date = new Date();
          DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          String time = format.format(date);
          MessageTypeAppBhMap map = 
            SysMessageUtil.getMessageTypeAppBhMap(messageType);
          String appBh = "";
          String weixinPost = "";
          try {
            DbOpt db0 = new DbOpt();
            weixinPost = db0.executeQueryToStr("select weixinpost from org_employee where emp_id=" + toEmp_Id);
            db0.close();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          if (message_Url.indexOf("NetdiskAction.do?action=listshared") > -1)
            weixinPost = null; 
          if (weixinPost != null && "1".equals(weixinPost)) {
            try {
              DbOpt db1 = new DbOpt();
              db1.executeUpdate("update sys_messages set weixinremindflag = '2' where message_id = " + 
                  messageId);
              db1.close();
            } catch (Exception e) {
              e.printStackTrace();
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
              if (!"".equals(appBh) && AppRoom.isAppUse(appBh)) {
                if ("1".equals(map.getWithLink())) {
                  IO2File.printFile(String.valueOf(time1) + ":4.1取得链接", "微信消息", 3);
                  String url = 
                    GetUrlUtil.getMessageLinkURL(messageId);
                  IO2File.printFile(String.valueOf(time1) + ":4.1取得链接结束开始发送", "微信消息", 3);
                  MessageUtil.sendNewMessageToUser(messageToUser, 
                      appBh, remindTitle, remindDescription, url, 
                      remindPicURL);
                } else {
                  IO2File.printFile(String.valueOf(time1) + ":4.2无连接开始发送", "微信消息", 3);
                  MessageUtil.sendNewMessageToUser(messageToUser, 
                      appBh, remindTitle, remindDescription, "", 
                      remindPicURL);
                } 
                if (SystemCommon.getYunshipeiName().equals("guangmingwang")) {
                  String url = "http://42.159.29.165/cgi-bin/message/send";
                  String urlEnCode = URLEncoder.encode(message_Url, "UTF-8");
                  String params = "<xml><CompanyId>56010fe0c6efa194e7000005</CompanyId><CompanyCert>jhl@gmw.cn</CompanyCert><ToUser><tel>" + 
                    
                    mobilePhone + "</tel>" + 
                    "<email>" + email + "</email></ToUser>" + 
                    "<ToParty></ToParty>" + 
                    "<FromUser><tel></tel>" + 
                    "<email>guanliyuan@gmw.cn</email></FromUser>" + 
                    "<CreateTime>" + time + "</CreateTime>" + 
                    "<MsgType>text</MsgType>" + 
                    
                    "<Content><message>" + messageTitle + "</message>" + 
                    "<url>" + urlEnCode + "</url></Content ></xml>";
                  try {
                    String result2 = MessageUtil.doHttpsPost(url, params);
                    System.out.println("结果：" + result2);
                  } catch (Exception e) {
                    e.printStackTrace();
                  } 
                } 
              } 
            } 
          } 
          try {
            DbOpt db1 = new DbOpt();
            db1.executeUpdate("update sys_messages set weixinremindflag = '1' where message_id = " + 
                messageId);
            db1.close();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          } 
          continue;
        } 
        IO2File.printFile(String.valueOf(time1) + ":6没有发送消息 休息10秒钟:", "微信消息", 3);
        try {
          Thread.sleep(1000L);
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
