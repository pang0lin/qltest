package cn.zzy.service;

import cn.zzy.action.AfficheDelRequest;
import cn.zzy.action.AfficheRequest;
import cn.zzy.action.AttachmentRequest;
import cn.zzy.action.Communicator;
import cn.zzy.action.FileRequest;
import cn.zzy.action.QResponse;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.IO2File;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageService {
  public void deleteMessage(String infoId) {
    DbOpt db = new DbOpt();
    String strSql = "select sid,infoId from sys_message_record where infoId in (" + infoId + ")";
    try {
      String[][] result = db.executeQueryToStrArr2(strSql, 2);
      if (result != null && result.length > 0)
        for (int i = 0; i < result.length; i++) {
          Communicator communicator = Communicator.getInstance();
          AfficheDelRequest afficheDelRequest = new AfficheDelRequest();
          afficheDelRequest.setTransid(Long.parseLong(result[i][1]));
          afficheDelRequest.setOaname(communicator.getOAName());
          afficheDelRequest.setAccount(communicator.getAccount());
          afficheDelRequest.setPassword(communicator.getPassword());
          afficheDelRequest.setSid(Long.parseLong(result[i][0]));
          QResponse q = Communicator.getInstance().sendRequest(afficheDelRequest);
          System.out.println("测试删除电子公告：返回码:" + q.getCode() + " 描述:" + q.getError_des());
          if ("0".equals(q.getCode()))
            db.executeUpdate("delete from sys_message_record where sid=" + result[i][0]); 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void sendMessages(String channelId, String infoType, String infoHeadfile, long infoId, String senderName, String senderOrgName, String informodifymen, String informodifyorg, String toUsers, String messageTitle, String infoContent) {
    IO2File.printFile(infoContent, "电子公告推送内容");
    Communicator communicator = Communicator.getInstance();
    DbOpt db = new DbOpt();
    QResponse q = null;
    try {
      String[][] result = (String[][])null;
      String querySQL = "select ifnull(max(sid),0) from sys_message_record where infoId=" + infoId;
      String sid = db.executeQueryToStr(querySQL);
      System.out.println("sid==" + sid);
      String[] toUserIds = (String[])null;
      if (toUsers != null && !"".equals(toUsers)) {
        querySQL = "select useraccounts from org_employee where emp_id in (" + toUsers + ")";
        toUserIds = db.executeQueryToStrArr1(querySQL);
      } 
      AfficheRequest afficheRequest = new AfficheRequest();
      afficheRequest.setSid(Long.parseLong(sid));
      afficheRequest.setTransid(infoId);
      afficheRequest.setAccount(communicator.getAccount());
      afficheRequest.setPassword(communicator.getPassword());
      afficheRequest.setSender("0");
      if (!"0".equals(sid) && !"".equals(informodifyorg) && !"".equals(informodifymen)) {
        senderOrgName = informodifyorg;
        senderName = informodifymen;
      } 
      afficheRequest.setOaname(senderOrgName);
      afficheRequest.setSendername(senderName);
      if (toUserIds != null && toUserIds.length > 0)
        afficheRequest.setRecvlist(toUserIds); 
      afficheRequest.setSubject(messageTitle);
      afficheRequest.setHtml(infoContent);
      afficheRequest.setContent(messageTitle);
      if ("227".equals(channelId)) {
        afficheRequest.setType((short)3);
      } else if ("228".equals(channelId)) {
        afficheRequest.setType((short)0);
      } else if ("230".equals(channelId)) {
        afficheRequest.setType((short)2);
      } else if ("231".equals(channelId)) {
        afficheRequest.setType((short)1);
      } 
      System.out.println("type==" + afficheRequest.getType());
      File localFile = null;
      ArrayList<FileRequest> files = new ArrayList<FileRequest>();
      AttachmentRequest attachment = new AttachmentRequest();
      ArrayList<AttachmentRequest> attachments = new ArrayList<AttachmentRequest>();
      if ("3".equals(infoType)) {
        localFile = new File("upload/" + infoHeadfile.split("_")[0] + "/information/" + infoHeadfile);
        attachment.setId(infoId);
        attachment.setPath(String.valueOf(communicator.getOAServerPath()) + localFile.getPath());
        attachment.setSize(localFile.length());
        attachments.add(attachment);
      } else if ("1".equals(infoType)) {
        result = db.executeQueryToStrArr2("SELECT accessory_id,information_id,accessoryname,accessorysavename FROM oa_informationaccessory WHERE information_id=" + infoId, 4);
        if (result != null && result.length > 0)
          for (int i = 0; i < result.length; i++) {
            localFile = new File(String.valueOf(communicator.getOAServerPath()) + "/jsoa/upload/" + result[i][3].split("_")[0] + "/information/" + result[i][3]);
            FileRequest file = new FileRequest();
            file.setId(Long.parseLong(result[i][0]));
            file.setPath(localFile.getPath());
            file.setSize(localFile.length());
            file.setName(result[i][2]);
            files.add(file);
          }  
        afficheRequest.setHtml(changeImg(afficheRequest.getHtml(), attachments, communicator));
      } 
      afficheRequest.setAttachments(attachments);
      afficheRequest.setFiles(files);
      q = communicator.sendRequest(afficheRequest);
      if ("0".equals(q.getCode()) && q.getSid() != 0L) {
        String recordSQL = "";
        if ("0".equals(sid)) {
          recordSQL = "insert into sys_message_record values(" + q.getSid() + ",'','" + infoType + "'," + infoId + ")";
        } else {
          recordSQL = "update sys_message_record set sid=" + q.getSid() + " where messageType='" + infoType + "' and infoId=" + infoId;
        } 
        System.out.println("消息推送记录：" + recordSQL);
        db.executeUpdate(recordSQL);
      } 
    } catch (Exception e) {
      e.getStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static String changeImg(String content, ArrayList<AttachmentRequest> files, Communicator communicator) {
    AttachmentRequest file = null;
    File localFile = null;
    int s = -1, e = -1;
    if (content == null || "".equals(content))
      return content; 
    String temp = content.toLowerCase(), img = "", src = "";
    s = temp.indexOf("<img");
    DbOpt db = new DbOpt();
    try {
      while (s > -1) {
        temp = temp.substring(s);
        e = temp.indexOf(">") + 1;
        img = temp.substring(0, e);
        src = img.substring(img.indexOf("src=\"") + 5);
        src = src.substring(0, src.indexOf("\""));
        System.out.println("src=" + src);
        if (!"".equals(src) && src.startsWith("/jsoa")) {
          localFile = new File(String.valueOf(communicator.getOAServerPath()) + src);
          String fileName = localFile.getName();
          String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
          db.executeUpdate("INSERT INTO oa_informationaccessory(information_id,accessoryisimage,accessorytype,accessoryname,accessorysavename,domain_id) SELECT 0,1,'" + 
              ext + "','" + fileName + "','" + fileName + 
              "',0 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM oa_informationaccessory WHERE accessorysavename='" + fileName + "')");
          String fileId = db.executeQueryToStr("SELECT IFNULL(MAX(accessory_id),0) FROM oa_informationaccessory where accessorysavename='" + fileName + "'");
          if (!"0".equals(fileId)) {
            file = new AttachmentRequest();
            file.setId(Long.parseLong(fileId));
            file.setPath(localFile.getPath());
            file.setSize(localFile.length());
            files.add(file);
            content = content.replace(src, "cid:" + fileId);
          } 
        } 
        temp = temp.substring(e);
        s = temp.indexOf("<img");
      } 
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
    return content;
  }
}
