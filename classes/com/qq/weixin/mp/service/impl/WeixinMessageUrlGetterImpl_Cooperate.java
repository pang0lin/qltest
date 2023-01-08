package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import java.sql.SQLException;

public class WeixinMessageUrlGetterImpl_Cooperate implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    System.out.println("messageUrl:" + messageUrl);
    String sql1 = "select id,status from CO_NODEMEMBER where  body_id= " + dataId + " and emp_id= " + currentUserId + " and isposter=1 ORDER BY id DESC";
    String sql2 = "select id,status from CO_NODEMEMBER where  body_id= " + dataId + " and emp_id= " + currentUserId + " and tracker=1 ORDER BY id DESC";
    String sql3 = "select id,status from CO_NODEMEMBER where  body_id= " + dataId + " and emp_id= " + currentUserId + " ORDER BY id DESC";
    String sqlWork = "";
    if (messageUrl.indexOf("&workId=") > 0) {
      String workId = messageUrl.substring(messageUrl.indexOf("&workId=") + 8, messageUrl.length());
      sqlWork = "select wf_work_Id,workstatus,WF_CUREMPLOYEE_ID from jsf_work where wf_work_id=" + workId;
    } 
    String[][] result = (String[][])null;
    int lengthtemp = 0;
    DbOpt db = null;
    try {
      db = new DbOpt();
      result = db.executeQueryToStrArr2(sql1);
      lengthtemp = result.length;
      db.close();
    } catch (Exception e) {
      if (db != null)
        try {
          db.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    if (result == null || result.length == 0) {
      DbOpt db1 = null;
      try {
        db1 = new DbOpt();
        result = db1.executeQueryToStrArr2(sql2);
        db1.close();
      } catch (Exception e) {
        if (db1 != null)
          try {
            db1.close();
          } catch (SQLException e1) {
            e1.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } 
    if (result == null || result.length == 0) {
      DbOpt db2 = null;
      try {
        db2 = new DbOpt();
        result = db2.executeQueryToStrArr2(sql3);
        db2.close();
      } catch (Exception e) {
        if (db2 != null)
          try {
            db2.close();
          } catch (SQLException e1) {
            e1.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } 
    if (lengthtemp > 0) {
      DbOpt db3 = null;
      try {
        db3 = new DbOpt();
        result = db3.executeQueryToStrArr2(sql3);
        db3.close();
      } catch (Exception e) {
        if (db3 != null)
          try {
            db3.close();
          } catch (SQLException e1) {
            e1.printStackTrace();
          }  
        e.printStackTrace();
      } 
      if (result.length > 1) {
        String sql4 = "select id,status from CO_NODEMEMBER where  body_id= " + dataId + " and emp_id= " + currentUserId + " and isposter=0 ORDER BY id DESC";
        DbOpt db4 = null;
        try {
          db4 = new DbOpt();
          result = db4.executeQueryToStrArr2(sql4);
          db4.close();
        } catch (Exception e) {
          if (db4 != null)
            try {
              db4.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
            }  
          e.printStackTrace();
        } 
      } 
    } 
    if (result != null && result.length > 0) {
      String bodyId = dataId;
      String memberId = result[0][0];
      String status = result[0][1];
      if (messageUrl.indexOf("&workId=") > 0) {
        String[][] resultWork = (String[][])null;
        DbOpt dbwork = null;
        try {
          dbwork = new DbOpt();
          resultWork = dbwork.executeQueryToStrArr2(sqlWork);
          dbwork.close();
        } catch (Exception e) {
          if (dbwork != null)
            try {
              dbwork.close();
            } catch (Exception err) {
              err.printStackTrace();
            }  
          e.printStackTrace();
        } 
        if (resultWork != null && resultWork.length > 0 && "101".equals((new StringBuilder(String.valueOf(resultWork[0][1]))).toString())) {
          status = "20";
        } else {
          status = "10";
        } 
      } else if (messageUrl.indexOf("isPoster=1&") > 0) {
        if ("10".equals(status))
          status = "20"; 
        if (currentUserId.equals(result[0][1])) {
          status = String.valueOf(status) + "2";
        } else {
          status = String.valueOf(status) + "1";
        } 
      } 
      url = "/weiXinCoopAction.do?action=toDealwith&from=message&bodyId=" + bodyId + "&memberId=" + memberId + "&status=" + status;
    } 
    return url;
  }
  
  public String[] getRemindInfo(String title, String messageUrl, String dataid, String emp_Id) {
    String[] remindInfo = new String[3];
    remindInfo[0] = title;
    if (messageUrl.indexOf("jump_dealwith.jsp") > 0) {
      remindInfo[1] = "您有一条标题为【" + title + "】的协同工作，请及时处理！";
    } else if (messageUrl.indexOf("jump_body.jsp") > 0) {
      remindInfo[1] = title;
    } 
    remindInfo[2] = "";
    return remindInfo;
  }
}
