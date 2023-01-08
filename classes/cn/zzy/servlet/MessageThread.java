package cn.zzy.servlet;

import cn.zzy.action.Communicator;
import cn.zzy.action.NotifyRequest;
import cn.zzy.action.QResponse;
import com.js.oa.userdb.util.DbOpt;

public class MessageThread extends Thread {
  public void run() {
    startRun();
  }
  
  private void startRun() {
    Communicator communicator = Communicator.getInstance();
    String sql = "select message_id, message_toUserId, message_type, message_title, message_Url, data_id, message_send_username, (select max(useraccounts) from org_employee where emp_id= message.message_toUserId) userid  from sys_messages message where tjqzFlag='0' limit 0,1";
    while (true) {
      DbOpt db = null;
      boolean sleep = false;
      try {
        db = new DbOpt();
        String[][] result = (String[][])null;
        try {
          result = db.executeQueryToStrArr2(sql, 8);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (result != null && result.length > 0) {
          String messageId = result[0][0];
          String messageType = result[0][2];
          String messageTitle = result[0][3];
          String messageUrl = result[0][4];
          String messageSendUserName = result[0][6];
          String messageSendOrg = "协同办公";
          String messageToUserAccount = result[0][7];
          String informodifymen = "";
          String informodifyorg = "";
          System.out.println("天津青职消息推送轮循。。" + messageId);
          long informationId = 0L;
          db.executeUpdate("update sys_messages set tjqzFlag='2' where message_id=" + messageId);
          QResponse q = null;
          System.out.println("messageType===" + messageType);
          if ("Info".equals(messageType)) {
            String infoType = "";
            String channelId = "";
            String infoId = messageUrl.substring(messageUrl.indexOf("informationId=") + 14);
            String tempSql = "select informationtitle,informationheadfile,informationtype,informationcontent,channel_id,information_id,informationissuer,informationissueorg,informodifymen,informodifyorg from oa_information where information_id=" + infoId;
            result = db.executeQueryToStrArr2(tempSql, 10);
            if (result != null && result.length > 0) {
              messageTitle = result[0][0];
              infoType = result[0][2];
              channelId = result[0][4];
              informationId = Long.parseLong(result[0][5]);
              messageSendUserName = result[0][6];
              messageSendOrg = result[0][7];
              informodifymen = result[0][8];
              informodifyorg = result[0][9];
              if (!"".equals(informodifyorg) && !"".equals(informodifymen)) {
                messageSendOrg = informodifyorg;
                messageSendUserName = informodifymen;
              } 
              System.out.println("informationId==" + informationId);
              if ("6".equals(infoType) || (
                !"227".equals(channelId) && !"228".equals(channelId) && !"230".equals(channelId) && !"231".equals(channelId)))
                q = oaMessage(communicator, messageId, messageSendUserName, messageSendOrg, new String[] { messageToUserAccount }, messageTitle); 
            } 
          } else {
            q = oaMessage(communicator, messageId, messageSendUserName, messageSendOrg, new String[] { messageToUserAccount }, messageTitle);
          } 
          System.out.println("消息推送结果：" + q.getCode());
          db.executeUpdate("update sys_messages set tjqzFlag='1' where message_id=" + messageId);
        } else {
          sleep = true;
        } 
      } catch (Exception e) {
        e.getStackTrace();
        continue;
      } finally {
        try {
          if (db != null)
            db.close(); 
          if (sleep)
            Thread.sleep(3000L); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private QResponse oaMessage(Communicator communicator, String messageId, String senderName, String messageSendOrg, String[] toUsers, String messageTitle) {
    NotifyRequest data = new NotifyRequest();
    data.setTransid(Long.parseLong(messageId));
    data.setOaname(messageSendOrg);
    data.setAccount(communicator.getAccount());
    data.setPassword(communicator.getPassword());
    data.setSender("0");
    data.setSendername(senderName);
    data.setRecvlist(toUsers);
    data.setSubject(messageTitle);
    data.setContent(messageTitle);
    return communicator.sendRequest(data);
  }
}
