package com.js.thread;

import com.js.oa.userdb.util.DbOpt;
import com.js.oa.webservice.appservice.JPushUtils;
import com.js.util.config.SystemCommon;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

public class AppPushTask extends Thread {
  private void execute() {
    System.out.println("app消息push启动。。。");
    String databaseType = SystemCommon.getDatabaseType();
    String customName = SystemCommon.getCustomerName();
    String key = "30b1086471f9b62a0be3dda9";
    String secret = "1f06b389fd827952f4df7b34";
    List<String> users = null;
    String sql = "select message_id, message_type, message_title, message_send_username, (select max(useraccounts) from org_employee where emp_id= message.message_toUserId) userid, message_toUserId,message_Url,data_Id  from sys_messages message where (appPushFlag is null or appPushFlag = '' or appPushFlag = '0')";
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
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          db.close();
        } 
        if (result != null && result.length > 0) {
          String messageId = result[0][0];
          String toEmpId = result[0][5];
          String messageTitle = result[0][2];
          String messageUrl = result[0][6];
          users = new ArrayList<String>();
          users.add(String.valueOf(customName) + "_" + toEmpId);
          String mailPost = "";
          DbOpt db0 = new DbOpt();
          try {
            mailPost = db0.executeQueryToStr("select mailpost from org_employee where emp_id=" + toEmpId);
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            db0.close();
          } 
          if (messageUrl.indexOf("NetdiskAction.do?action=listshared") > -1)
            mailPost = null; 
          if (mailPost != null && "1".equals(mailPost)) {
            DbOpt db1 = new DbOpt();
            try {
              db1.executeUpdate("update sys_messages set appPushFlag = '2' where message_id = " + messageId);
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              db1.close();
            } 
            System.out.println("app推送消息地址：" + messageUrl);
            String res = JPushUtils.sendByAlias(secret, key, true, false, messageTitle, "移动办公", "{\"messageURL\":\"" + messageId + "\"}", users);
            System.out.println("Android消息推送：" + res);
            res = JPushUtils.sendByAlias(secret, key, true, true, messageTitle, messageTitle, "{\"messageURL\":\"" + messageId + "\"}", users);
            System.out.println("IOS消息推送：" + res);
            if (!"".equals(res)) {
              JSONObject json = JSONObject.fromObject(res);
              if (!"".equals(json.getString("msg_id"))) {
                DbOpt db2 = new DbOpt();
                try {
                  db2.executeUpdate("update sys_messages set appPushFlag = '1' where message_id = " + messageId);
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                  db2.close();
                } 
              } 
            } 
          } else {
            DbOpt db1 = new DbOpt();
            try {
              db1.executeUpdate("update sys_messages set appPushFlag = '1' where message_id = " + messageId);
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              db1.close();
            } 
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
