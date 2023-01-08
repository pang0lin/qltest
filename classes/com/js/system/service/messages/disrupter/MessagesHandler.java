package com.js.system.service.messages.disrupter;

import com.active.e_uc.user.po.TblSysmsg;
import com.active.e_uc.user.service.TblSysmsgBD;
import com.buguniao.SysMessageSend;
import com.js.message.RealTimeUtil;
import com.js.oa.message.service.MessageBD;
import com.js.oa.message.service.MsManageBD;
import com.js.sms.corp.CorpSMSAbstractFactory;
import com.js.sms.corp.CorpSMSTool;
import com.js.sms.corp.ICorpSMSServies;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ReadActiveXml;
import com.lmax.disruptor.EventHandler;
import com.saitong.SaiTongUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rtx.RTXSvrApi;

public class MessagesHandler implements EventHandler<MessagesEvent> {
  public void onEvent(MessagesEvent messagesEvent, long sequence, boolean endOfBatch) throws Exception {
    String emailRemindPrefix = SystemCommon.getEmailRemindPrefix();
    String title = messagesEvent.getMessagesBean().getTitle();
    if (title.length() >= 200)
      title = title.substring(0, 99); 
    String url = messagesEvent.getMessagesBean().getUrl();
    String userIds = messagesEvent.getMessagesBean().getUserIds();
    String moduleType = messagesEvent.getMessagesBean().getModuleType();
    Date startTime = messagesEvent.getMessagesBean().getStartTime();
    Date endTime = messagesEvent.getMessagesBean().getEndTime();
    String send_UserName = messagesEvent.getMessagesBean().getSend_UserName();
    Long data_id = messagesEvent.getMessagesBean().getData_id();
    int showType = messagesEvent.getMessagesBean().getShowType();
    String methodName = messagesEvent.getMessagesBean().getMethodName();
    int hasURL = messagesEvent.getMessagesBean().getHasURL();
    String myId = messagesEvent.getMessagesBean().getMyId();
    int sendSMS = messagesEvent.getMessagesBean().getSendSMS();
    int sendEmail = messagesEvent.getMessagesBean().getSendEmail();
    Connection conn = null;
    String databaseType = SystemCommon.getDatabaseType();
    if (methodName != null && "sendMessageToUsers2".equals(methodName)) {
      try {
        String psql;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        if (databaseType.indexOf("mysql") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id,message_id) values(?,?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
        } else {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } 
        PreparedStatement pstmt = conn.prepareStatement(psql);
        String sql = "";
        if (!"-1".equals(userIds)) {
          sql = " where ";
          if (userIds != null && userIds.startsWith(","))
            userIds = userIds.substring(1); 
          String[] userArray = userIds.split(",");
          StringBuffer userBufferTemp = new StringBuffer();
          StringBuffer whereCondition = new StringBuffer();
          boolean isCut = false;
          for (int i = 0; i < userArray.length; i++) {
            userBufferTemp.append(userArray[i]).append(",");
            if (i % 999 == 0) {
              String users = userBufferTemp.toString();
              users = users.substring(0, users.length() - 1);
              if (isCut) {
                whereCondition.append(" or emp.emp_id in(").append(users).append(")");
              } else {
                whereCondition.append(" emp.emp_id in(").append(users).append(")");
              } 
              if (!isCut)
                isCut = true; 
              userBufferTemp = new StringBuffer();
            } 
          } 
          if (isCut) {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" or emp.emp_id in(").append(users).append(")");
            } 
          } else {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" emp.emp_id in(").append(users).append(")");
            } 
          } 
          sql = String.valueOf(sql) + whereCondition.toString();
        } 
        Date now = new Date();
        Timestamp sqlNow = new Timestamp(now.getTime());
        Timestamp sqlStart = new Timestamp(startTime.getTime());
        Timestamp sqlEnd = new Timestamp(endTime.getTime());
        String usd = ReadActiveXml.getReadActive().getUse();
        String sqlt = "select emp.emp_Id,emp.empName,kk.status from org_employee emp left join (select srs.emp_id, srs.status from sys_remind_type tt left join sys_remind_set srs on tt.type=srs.type where tt.type='" + moduleType + "')  kk  on ( kk.emp_id=emp.emp_id or kk.emp_id=null) " + sql;
        ResultSet rs = stmt.executeQuery(sqlt);
        Boolean receiveMs = getSysControlConfig();
        StringBuffer emailBuffer = new StringBuffer(",");
        while (rs.next()) {
          String empId = rs.getString(1);
          String empName = rs.getString(2);
          String remindType = rs.getString(3);
          if (remindType == null || remindType.indexOf("$01$") >= 0) {
            pstmt.setLong(1, Long.parseLong(empId));
            pstmt.setString(2, moduleType);
            pstmt.setString(3, title);
            pstmt.setString(4, url);
            pstmt.setTimestamp(5, sqlNow);
            pstmt.setLong(6, 0L);
            pstmt.setString(7, send_UserName);
            pstmt.setInt(8, 1);
            pstmt.setInt(9, showType);
            pstmt.setTimestamp(10, sqlStart);
            pstmt.setTimestamp(11, sqlEnd);
            pstmt.setLong(12, data_id.longValue());
            pstmt.executeUpdate();
          } 
          if (remindType != null && remindType.indexOf("$02$") >= 0 && sendSMS == 1) {
            if ("jsflow".equals(messagesEvent.getMessagesBean().getModuleType()) && "1".equals(SystemCommon.getMessageForDocShow()))
              title = changeMessageTitle(messagesEvent.getMessagesBean()); 
            if (judgeUserReceiveMs(receiveMs, empId).booleanValue()) {
              SysSetupReader ssReader = SysSetupReader.getInstance();
              String noteYesOrNo = ssReader.noteYesOrNo();
              if ("1".equals(noteYesOrNo)) {
                MessageBD messageSend = null;
                CorpSMSAbstractFactory corpSMSAbstractFactory = null;
                CorpSMSTool.getPropertise();
                switch (CorpSMSTool.CORPSTATE) {
                  case 0:
                    messageSend = new MessageBD();
                    messageSend.modelSendMsg((new Long(empId)).toString(), title, "", "0", "0", sqlStart, data_id);
                    break;
                  case 1:
                    corpSMSAbstractFactory = CorpSMSAbstractFactory.CreateFactory();
                    if (corpSMSAbstractFactory != null) {
                      ICorpSMSServies iCorpSMSServies = corpSMSAbstractFactory.createCorpSMSService();
                      iCorpSMSServies.modelSendMsg(empId, title, "", "0", "0");
                    } 
                    break;
                } 
              } 
            } 
          } 
          if (getUseEmailFlag().equals("1") && remindType != null && remindType.indexOf("$03$") >= 0 && sendEmail == 1) {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO = (new UserBD()).getEmpByid(new Long(empId));
            String emailUrl = employeeVO.getEmpEmail();
            if (emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
              if (emailBuffer.toString().indexOf("," + emailUrl + ",") >= 0)
                continue; 
              Mail wm = new Mail();
              wm.setSendTo(emailUrl);
              wm.setBoby(title);
              wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
              MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
              if ("haier".equals(SystemCommon.getCustomerName()))
                emailBuffer.append(emailUrl).append(","); 
            } 
          } 
          if (remindType != null && remindType.indexOf("$04$") >= 0 && 
            "iactive".equals(usd)) {
            TblSysmsg tblSysmsg = new TblSysmsg();
            TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
            String username = (new UserBD()).getUserName(new Long(empId));
            List<Integer> useridsList = tblSysmsgBD.findUserId("'" + username + "'");
            String userids = "";
            String userid = "";
            for (int j = 0; j < useridsList.size(); j++) {
              userid = ((Integer)useridsList.get(j)).toString();
              userids = String.valueOf(userids) + userid + ",";
            } 
            userids = "," + userids;
            tblSysmsg.setUsageType(1);
            tblSysmsg.setContentType(0);
            tblSysmsg.setDisplayMode(0);
            tblSysmsg.setContent(title);
            tblSysmsg.setReceiverType(24);
            tblSysmsg.setReceiver(userids);
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tblSysmsg.setStartTime(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblSysmsg.setEndTime(si.format(calendar.getTime()));
            tblSysmsg.setApp(3);
            tblSysmsg.setCreatorId(3);
            tblSysmsg.setCreatorType(3);
            tblSysmsg.setOrgId(1);
            tblSysmsg.setAid(1);
            tblSysmsg.setDeptId(0);
            tblSysmsg.setOrderNum(0);
            tblSysmsgBD.addTblSysmsg(tblSysmsg);
          } 
          if (remindType != null && remindType.indexOf("$05$") >= 0) {
            if ("rtx".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RTXSvrApi rtxSvr = new RTXSvrApi();
              rtxSvr.sendNotify(username, send_UserName, title, url, "0", "0");
            } 
            if ("gk".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RealTimeUtil util = new RealTimeUtil();
              util.sendNotify(username, title, title, "1", "0");
            } 
            if ("jsim".equals(usd))
              sendMessageToJSIM(Long.parseLong(empId), moduleType, title, url, sqlNow, 0L, send_UserName, 1, 0, sqlStart, sqlEnd, data_id); 
          } 
          if (remindType != null && remindType.indexOf("$06$") >= 0) {
            System.out.println("校园通信息：");
            (new SaiTongUtil()).saiTongExc(title, empId, url);
          } 
          if (remindType != null && remindType.indexOf("$07$") >= 0) {
            String username = (new UserBD()).getUserName(new Long(empId));
            (new SysMessageSend()).sendMessage(username, title, url);
          } 
        } 
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } else if (methodName != null && "sendMessageToUsers1".equals(methodName)) {
      try {
        String psql;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        if (databaseType.indexOf("mysql") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id,message_id) values(?,?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
        } else {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } 
        PreparedStatement pstmt = conn.prepareStatement(psql);
        String sql = "";
        if (!"-1".equals(userIds)) {
          sql = " where ";
          String[] userArray = userIds.split(",");
          StringBuffer userBufferTemp = new StringBuffer();
          StringBuffer whereCondition = new StringBuffer();
          boolean isCut = false;
          for (int i = 0; i < userArray.length; i++) {
            userBufferTemp.append(userArray[i]).append(",");
            if (i % 999 == 0) {
              String users = userBufferTemp.toString();
              users = users.substring(0, users.length() - 1);
              if (isCut) {
                whereCondition.append(" or emp.emp_id in(").append(users).append(")");
              } else {
                whereCondition.append(" emp.emp_id in(").append(users).append(")");
              } 
              if (!isCut)
                isCut = true; 
              userBufferTemp = new StringBuffer();
            } 
          } 
          if (isCut) {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" or emp.emp_id in(").append(users).append(")");
            } 
          } else {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" emp.emp_id in(").append(users).append(")");
            } 
          } 
          sql = String.valueOf(sql) + whereCondition.toString();
        } 
        Date now = new Date();
        Timestamp sqlNow = new Timestamp(now.getTime());
        Timestamp sqlStart = new Timestamp(startTime.getTime());
        Timestamp sqlEnd = new Timestamp(endTime.getTime());
        String usd = ReadActiveXml.getReadActive().getUse();
        ResultSet rs = stmt.executeQuery("select emp.emp_Id,emp.empName,kk.status from org_employee emp left join (select srs.emp_id, srs.status from sys_remind_type tt left join sys_remind_set srs on tt.type=srs.type where tt.type='" + moduleType + "')  kk  on ( kk.emp_id=emp.emp_id or kk.emp_id=null) " + sql);
        Boolean receiveMs = getSysControlConfig();
        StringBuffer emailBuffer = new StringBuffer(",");
        while (rs.next()) {
          String empId = rs.getString(1);
          String empName = rs.getString(2);
          String remindType = rs.getString(3);
          if (remindType == null || remindType.indexOf("$01$") >= 0) {
            pstmt.setLong(1, Long.parseLong(empId));
            pstmt.setString(2, moduleType);
            pstmt.setString(3, title);
            pstmt.setString(4, url);
            pstmt.setTimestamp(5, sqlNow);
            pstmt.setLong(6, 0L);
            pstmt.setString(7, send_UserName);
            pstmt.setInt(8, 1);
            pstmt.setInt(9, 0);
            pstmt.setTimestamp(10, sqlStart);
            pstmt.setTimestamp(11, sqlEnd);
            pstmt.setLong(12, data_id.longValue());
            pstmt.executeUpdate();
          } 
          if (remindType != null && remindType.indexOf("$02$") >= 0 && sendSMS == 1) {
            if ("jsflow".equals(messagesEvent.getMessagesBean().getModuleType()) && "1".equals(SystemCommon.getMessageForDocShow()))
              title = changeMessageTitle(messagesEvent.getMessagesBean()); 
            if (judgeUserReceiveMs(receiveMs, empId).booleanValue()) {
              SysSetupReader ssReader = SysSetupReader.getInstance();
              String noteYesOrNo = ssReader.noteYesOrNo();
              if ("1".equals(noteYesOrNo)) {
                MessageBD messageSend = new MessageBD();
                messageSend.modelSendMsg((
                    new Long(empId)).toString(), 
                    title, 
                    "", "0", "0", sqlStart, data_id);
              } 
            } 
          } 
          if (getUseEmailFlag().equals("1") && remindType != null && remindType.indexOf("$03$") >= 0 && sendEmail == 1) {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO = (new UserBD()).getEmpByid(new Long(empId));
            String emailUrl = employeeVO.getEmpEmail();
            if (emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
              if (emailBuffer.toString().indexOf("," + emailUrl + ",") >= 0)
                continue; 
              Mail wm = new Mail();
              wm.setSendTo(emailUrl);
              wm.setBoby(title);
              wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
              MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
              if ("haier".equals(SystemCommon.getCustomerName()))
                emailBuffer.append(emailUrl).append(","); 
            } 
          } 
          if (remindType != null && remindType.indexOf("$04$") >= 0 && 
            "iactive".equals(usd)) {
            TblSysmsg tblSysmsg = new TblSysmsg();
            TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
            String username = (new UserBD()).getUserName(new Long(empId));
            List<Integer> useridsList = tblSysmsgBD.findUserId("'" + username + "'");
            String userids = "";
            String userid = "";
            for (int j = 0; j < useridsList.size(); j++) {
              userid = ((Integer)useridsList.get(j)).toString();
              userids = String.valueOf(userids) + userid + ",";
            } 
            userids = "," + userids;
            tblSysmsg.setUsageType(1);
            tblSysmsg.setContentType(0);
            tblSysmsg.setDisplayMode(0);
            tblSysmsg.setContent(title);
            tblSysmsg.setReceiverType(24);
            tblSysmsg.setReceiver(userids);
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tblSysmsg.setStartTime(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblSysmsg.setEndTime(si.format(calendar.getTime()));
            tblSysmsg.setApp(3);
            tblSysmsg.setCreatorId(3);
            tblSysmsg.setCreatorType(3);
            tblSysmsg.setOrgId(1);
            tblSysmsg.setAid(1);
            tblSysmsg.setDeptId(0);
            tblSysmsg.setOrderNum(0);
            tblSysmsgBD.addTblSysmsg(tblSysmsg);
          } 
          if (remindType != null && remindType.indexOf("$05$") >= 0) {
            if ("rtx".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RTXSvrApi rtxSvr = new RTXSvrApi();
              rtxSvr.sendNotify(username, send_UserName, title, url, "0", "0");
            } 
            if ("gk".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RealTimeUtil util = new RealTimeUtil();
              util.sendNotify(username, title, title, "1", "0");
            } 
            if ("jsim".equals(usd))
              sendMessageToJSIM(Long.parseLong(empId), moduleType, title, url, sqlNow, 0L, send_UserName, 1, 0, sqlStart, sqlEnd, data_id); 
          } 
          if (remindType != null && remindType.indexOf("$06$") >= 0)
            (new SaiTongUtil()).saiTongExc(title, empId, url); 
          if (remindType != null && remindType.indexOf("$07$") >= 0) {
            String username = (new UserBD()).getUserName(new Long(empId));
            (new SysMessageSend()).sendMessage(username, title, url);
          } 
        } 
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } else if (methodName != null && "sendMessageToUsers".equals(methodName)) {
      try {
        String psql;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        if (databaseType.indexOf("mysql") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id,message_id) values(?,?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
        } else {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        } 
        PreparedStatement pstmt = conn.prepareStatement(psql);
        String sql = "";
        if (!"-1".equals(userIds)) {
          sql = " where ";
          String[] userArray = userIds.split(",");
          StringBuffer userBufferTemp = new StringBuffer();
          StringBuffer whereCondition = new StringBuffer();
          boolean isCut = false;
          for (int i = 0; i < userArray.length; i++) {
            userBufferTemp.append(userArray[i]).append(",");
            if (i % 999 == 0) {
              String users = userBufferTemp.toString();
              users = users.substring(0, users.length() - 1);
              if (isCut) {
                whereCondition.append(" or emp.emp_id in(").append(users).append(")");
              } else {
                whereCondition.append(" emp.emp_id in(").append(users).append(")");
              } 
              if (!isCut)
                isCut = true; 
              userBufferTemp = new StringBuffer();
            } 
          } 
          if (isCut) {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" or emp.emp_id in(").append(users).append(")");
            } 
          } else {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" emp.emp_id in(").append(users).append(")");
            } 
          } 
          sql = String.valueOf(sql) + whereCondition.toString();
        } 
        Date now = new Date();
        Timestamp sqlNow = new Timestamp(now.getTime());
        Timestamp sqlStart = new Timestamp(startTime.getTime());
        Timestamp sqlEnd = new Timestamp(endTime.getTime());
        String usd = ReadActiveXml.getReadActive().getUse();
        ResultSet rs = stmt.executeQuery("select emp.emp_Id,emp.empName,kk.status from org_employee emp left join (select srs.emp_id, srs.status from sys_remind_type tt left join sys_remind_set srs on tt.type=srs.type where tt.type='" + moduleType + "')  kk  on ( kk.emp_id=emp.emp_id or kk.emp_id=null) " + sql);
        Boolean receiveMs = getSysControlConfig();
        StringBuffer emailBuffer = new StringBuffer(",");
        while (rs.next()) {
          String empId = rs.getString(1);
          String empName = rs.getString(2);
          String remindType = rs.getString(3);
          if (remindType == null || remindType.indexOf("$01$") >= 0) {
            pstmt.setLong(1, Long.parseLong(empId));
            pstmt.setString(2, moduleType);
            pstmt.setString(3, title);
            pstmt.setString(4, url);
            pstmt.setTimestamp(5, sqlNow);
            pstmt.setLong(6, 0L);
            pstmt.setString(7, send_UserName);
            pstmt.setInt(8, 1);
            pstmt.setInt(9, 1);
            pstmt.setTimestamp(10, sqlStart);
            pstmt.setTimestamp(11, sqlEnd);
            pstmt.setLong(12, data_id.longValue());
            pstmt.executeUpdate();
          } 
          if (remindType != null && remindType.indexOf("$02$") >= 0 && sendSMS == 1) {
            if ("jsflow".equals(messagesEvent.getMessagesBean().getModuleType()) && "1".equals(SystemCommon.getMessageForDocShow()))
              title = changeMessageTitle(messagesEvent.getMessagesBean()); 
            if (judgeUserReceiveMs(receiveMs, empId).booleanValue()) {
              SysSetupReader ssReader = SysSetupReader.getInstance();
              String noteYesOrNo = ssReader.noteYesOrNo();
              if ("1".equals(noteYesOrNo)) {
                MessageBD messageSend = new MessageBD();
                messageSend.modelSendMsg((
                    new Long(empId)).toString(), 
                    title, 
                    "", "0", "0", sqlStart, data_id);
              } 
            } 
          } 
          if (getUseEmailFlag().equals("1") && remindType != null && remindType.indexOf("$03$") >= 0 && sendEmail == 1) {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO = (new UserBD()).getEmpByid(new Long(empId));
            String emailUrl = employeeVO.getEmpEmail();
            if (emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
              if (emailBuffer.toString().indexOf("," + emailUrl + ",") >= 0)
                continue; 
              Mail wm = new Mail();
              wm.setSendTo(emailUrl);
              wm.setBoby(title);
              wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
              MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
              if ("haier".equals(SystemCommon.getCustomerName()))
                emailBuffer.append(emailUrl).append(","); 
            } 
          } 
          if (remindType != null && remindType.indexOf("$04$") >= 0 && 
            "iactive".equals(usd)) {
            TblSysmsg tblSysmsg = new TblSysmsg();
            TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
            String username = (new UserBD()).getUserName(new Long(empId));
            List<Integer> useridsList = tblSysmsgBD.findUserId("'" + username + "'");
            String userids = "";
            String userid = "";
            for (int j = 0; j < useridsList.size(); j++) {
              userid = ((Integer)useridsList.get(j)).toString();
              userids = String.valueOf(userids) + userid + ",";
            } 
            userids = "," + userids;
            tblSysmsg.setUsageType(1);
            tblSysmsg.setContentType(0);
            tblSysmsg.setDisplayMode(0);
            tblSysmsg.setContent(title);
            tblSysmsg.setReceiverType(24);
            tblSysmsg.setReceiver(userids);
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tblSysmsg.setStartTime(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblSysmsg.setEndTime(si.format(calendar.getTime()));
            tblSysmsg.setApp(3);
            tblSysmsg.setCreatorId(3);
            tblSysmsg.setCreatorType(3);
            tblSysmsg.setOrgId(1);
            tblSysmsg.setAid(1);
            tblSysmsg.setDeptId(0);
            tblSysmsg.setOrderNum(0);
            tblSysmsgBD.addTblSysmsg(tblSysmsg);
          } 
          if (remindType != null && remindType.indexOf("$05$") >= 0) {
            if ("rtx".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RTXSvrApi rtxSvr = new RTXSvrApi();
              rtxSvr.sendNotify(username, send_UserName, title, url, "0", "0");
            } 
            if ("gk".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RealTimeUtil util = new RealTimeUtil();
              util.sendNotify(username, title, title, "1", "0");
            } 
            if ("jsim".equals(usd))
              sendMessageToJSIM(Long.parseLong(empId), moduleType, title, url, sqlNow, 0L, send_UserName, 1, 0, sqlStart, sqlEnd, data_id); 
          } 
          if (remindType != null && remindType.indexOf("$06$") >= 0)
            (new SaiTongUtil()).saiTongExc(title, empId, url); 
          if (remindType != null && remindType.indexOf("$07$") >= 0) {
            String username = (new UserBD()).getUserName(new Long(empId));
            (new SysMessageSend()).sendMessage(username, title, url);
          } 
        } 
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } else if (methodName != null && "sendMessages".equals(methodName)) {
      try {
        String psql;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        if (databaseType.indexOf("mysql") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end) values(?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,message_id) values(?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
        } else {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end) values(?,?,?,?,?,?,?,?,?,?,?)";
        } 
        Statement stmt = conn.createStatement();
        PreparedStatement pstmt = conn.prepareStatement(psql);
        String sql = "";
        if (!"-1".equals(userIds)) {
          sql = " where ";
          String[] userArray = userIds.split(",");
          StringBuffer userBufferTemp = new StringBuffer();
          StringBuffer whereCondition = new StringBuffer();
          boolean isCut = false;
          for (int i = 0; i < userArray.length; i++) {
            userBufferTemp.append(userArray[i]).append(",");
            if (i % 999 == 0) {
              String users = userBufferTemp.toString();
              users = users.substring(0, users.length() - 1);
              if (isCut) {
                whereCondition.append(" or emp.emp_id in(").append(users).append(")");
              } else {
                whereCondition.append(" emp.emp_id in(").append(users).append(")");
              } 
              if (!isCut)
                isCut = true; 
              userBufferTemp = new StringBuffer();
            } 
          } 
          if (isCut) {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" or emp.emp_id in(").append(users).append(")");
            } 
          } else {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" emp.emp_id in(").append(users).append(")");
            } 
          } 
          sql = String.valueOf(sql) + whereCondition.toString();
        } 
        Date now = new Date();
        Timestamp sqlNow = new Timestamp(now.getTime());
        Timestamp sqlStart = new Timestamp(startTime.getTime());
        Timestamp sqlEnd = new Timestamp(endTime.getTime());
        String usd = ReadActiveXml.getReadActive().getUse();
        ResultSet rs = stmt.executeQuery("select emp.emp_Id,emp.empName,kk.status from org_employee emp left join (select srs.emp_id, srs.status from sys_remind_type tt left join sys_remind_set srs on tt.type=srs.type where tt.type='" + moduleType + "')  kk  on ( kk.emp_id=emp.emp_id or kk.emp_id=null) " + sql);
        Boolean receiveMs = getSysControlConfig();
        StringBuffer emailBuffer = new StringBuffer(",");
        while (rs.next()) {
          String empId = rs.getString(1);
          String empName = rs.getString(2);
          String remindType = rs.getString(3);
          if (remindType == null || remindType.indexOf("$01$") >= 0) {
            pstmt.setLong(1, Long.parseLong(empId));
            pstmt.setString(2, moduleType);
            pstmt.setString(3, title);
            pstmt.setString(4, url);
            pstmt.setTimestamp(5, sqlNow);
            pstmt.setLong(6, 0L);
            pstmt.setString(7, "系统提醒");
            pstmt.setInt(8, 1);
            pstmt.setInt(9, hasURL);
            pstmt.setTimestamp(10, sqlStart);
            pstmt.setTimestamp(11, sqlEnd);
            pstmt.executeUpdate();
          } 
          if (remindType != null && remindType.indexOf("$02$") >= 0 && sendSMS == 1) {
            if ("jsflow".equals(messagesEvent.getMessagesBean().getModuleType()) && "1".equals(SystemCommon.getMessageForDocShow()))
              title = changeMessageTitle(messagesEvent.getMessagesBean()); 
            if (judgeUserReceiveMs(receiveMs, empId).booleanValue()) {
              SysSetupReader ssReader = SysSetupReader.getInstance();
              String noteYesOrNo = ssReader.noteYesOrNo();
              if ("1".equals(noteYesOrNo)) {
                MessageBD messageSend = new MessageBD();
                messageSend.modelSendMsg((new Long(empId)).toString(), title, "", "0", "0", sqlStart, data_id);
              } 
            } 
          } 
          if (getUseEmailFlag().equals("1") && remindType != null && remindType.indexOf("$03$") >= 0 && sendEmail == 1) {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO = (new UserBD()).getEmpByid(new Long(empId));
            String emailUrl = employeeVO.getEmpEmail();
            if (emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
              if (emailBuffer.toString().indexOf("," + emailUrl + ",") >= 0)
                continue; 
              Mail wm = new Mail();
              wm.setSendTo(emailUrl);
              wm.setBoby(title);
              wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
              MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
              if ("haier".equals(SystemCommon.getCustomerName()))
                emailBuffer.append(emailUrl).append(","); 
            } 
          } 
          if (remindType != null && remindType.indexOf("$04$") >= 0 && 
            "iactive".equals(usd)) {
            TblSysmsg tblSysmsg = new TblSysmsg();
            TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
            String username = (new UserBD()).getUserName(new Long(empId));
            List<Integer> useridsList = tblSysmsgBD.findUserId("'" + username + "'");
            String userids = "";
            String userid = "";
            for (int j = 0; j < useridsList.size(); j++) {
              userid = ((Integer)useridsList.get(j)).toString();
              userids = String.valueOf(userids) + userid + ",";
            } 
            userids = "," + userids;
            tblSysmsg.setUsageType(1);
            tblSysmsg.setContentType(0);
            tblSysmsg.setDisplayMode(0);
            tblSysmsg.setContent(title);
            tblSysmsg.setReceiverType(24);
            tblSysmsg.setReceiver(userids);
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tblSysmsg.setStartTime(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblSysmsg.setEndTime(si.format(calendar.getTime()));
            tblSysmsg.setApp(3);
            tblSysmsg.setCreatorId(3);
            tblSysmsg.setCreatorType(3);
            tblSysmsg.setOrgId(1);
            tblSysmsg.setAid(1);
            tblSysmsg.setDeptId(0);
            tblSysmsg.setOrderNum(0);
            tblSysmsgBD.addTblSysmsg(tblSysmsg);
          } 
          if (remindType != null && remindType.indexOf("$05$") >= 0) {
            if ("rtx".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RTXSvrApi rtxSvr = new RTXSvrApi();
              rtxSvr.sendNotify(username, "系统消息", title, url, "0", "0");
            } 
            if ("gk".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RealTimeUtil util = new RealTimeUtil();
              util.sendNotify(username, title, title, "1", "0");
            } 
            if ("jsim".equals(usd))
              sendMessageToJSIM(Long.parseLong(empId), moduleType, title, url, sqlNow, 0L, "系统提醒", 1, 0, sqlStart, sqlEnd, Long.valueOf(0L)); 
          } 
          if (remindType != null && remindType.indexOf("$06$") >= 0)
            (new SaiTongUtil()).saiTongExc(title, empId, url); 
          if (remindType != null && remindType.indexOf("$07$") >= 0) {
            String username = (new UserBD()).getUserName(new Long(empId));
            (new SysMessageSend()).sendMessage(username, title, url);
          } 
        } 
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } else if (methodName != null && "sendMessagesExceptMe".equals(methodName)) {
      try {
        String psql;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        if (databaseType.indexOf("mysql") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end) values(?,?,?,?,?,?,?,?,?,?,?)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,message_id) values(?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
        } else {
          psql = "insert into sys_messages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end) values(?,?,?,?,?,?,?,?,?,?,?)";
        } 
        Statement stmt = conn.createStatement();
        PreparedStatement pstmt = conn.prepareStatement(psql);
        String sql = "";
        if (!"-1".equals(userIds)) {
          sql = " where ";
          String[] userArray = userIds.split(",");
          StringBuffer userBufferTemp = new StringBuffer();
          StringBuffer whereCondition = new StringBuffer();
          boolean isCut = false;
          for (int i = 0; i < userArray.length; i++) {
            userBufferTemp.append(userArray[i]).append(",");
            if (i % 999 == 0) {
              String users = userBufferTemp.toString();
              users = users.substring(0, users.length() - 1);
              if (isCut) {
                whereCondition.append(" or emp.emp_id in(").append(users).append(")");
              } else {
                whereCondition.append(" emp.emp_id in(").append(users).append(")");
              } 
              if (!isCut)
                isCut = true; 
              userBufferTemp = new StringBuffer();
            } 
          } 
          if (isCut) {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" or emp.emp_id in(").append(users).append(")");
            } 
          } else {
            String users = userBufferTemp.toString();
            if (users.length() > 0) {
              users = users.substring(0, users.length() - 1);
              whereCondition.append(" emp.emp_id in(").append(users).append(")");
            } 
          } 
          sql = String.valueOf(sql) + whereCondition.toString();
        } 
        Date now = new Date();
        Timestamp sqlNow = new Timestamp(now.getTime());
        Timestamp sqlStart = new Timestamp(startTime.getTime());
        Timestamp sqlEnd = new Timestamp(endTime.getTime());
        String usd = ReadActiveXml.getReadActive().getUse();
        Boolean receiveMs = getSysControlConfig();
        StringBuffer emailBuffer = new StringBuffer(",");
        ResultSet rs = stmt.executeQuery("select emp.emp_Id,emp.empName,kk.status from org_employee emp left join (select srs.emp_id, srs.status from sys_remind_type tt left join sys_remind_set srs on tt.type=srs.type where tt.type='" + moduleType + "')  kk  on ( kk.emp_id=emp.emp_id or kk.emp_id=null) " + sql);
        while (rs.next()) {
          String empId = rs.getString(1);
          String empName = rs.getString(2);
          String remindType = rs.getString(3);
          if (empId.equals(myId))
            continue; 
          if (remindType == null || remindType.indexOf("$01$") >= 0) {
            pstmt.setLong(1, Long.parseLong(empId));
            pstmt.setString(2, moduleType);
            pstmt.setString(3, title);
            pstmt.setString(4, url);
            pstmt.setTimestamp(5, sqlNow);
            pstmt.setLong(6, 0L);
            pstmt.setString(7, "系统提醒");
            pstmt.setInt(8, 1);
            pstmt.setInt(9, hasURL);
            pstmt.setTimestamp(10, sqlStart);
            pstmt.setTimestamp(11, sqlEnd);
            pstmt.executeUpdate();
          } 
          if (remindType != null && remindType.indexOf("$02$") >= 0 && sendSMS == 1) {
            if ("jsflow".equals(messagesEvent.getMessagesBean().getModuleType()) && "1".equals(SystemCommon.getMessageForDocShow()))
              title = changeMessageTitle(messagesEvent.getMessagesBean()); 
            if (judgeUserReceiveMs(receiveMs, empId).booleanValue()) {
              SysSetupReader ssReader = SysSetupReader.getInstance();
              String noteYesOrNo = ssReader.noteYesOrNo();
              if ("1".equals(noteYesOrNo)) {
                MessageBD messageSend = new MessageBD();
                messageSend.modelSendMsg((new Long(empId)).toString(), title, "", "0", "0", sqlStart, data_id);
              } 
            } 
          } 
          if (getUseEmailFlag().equals("1") && remindType != null && remindType.indexOf("$03$") >= 0 && sendEmail == 1) {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO = (new UserBD()).getEmpByid(new Long(empId));
            String emailUrl = employeeVO.getEmpEmail();
            if (emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
              if (emailBuffer.toString().indexOf("," + emailUrl + ",") >= 0)
                continue; 
              Mail wm = new Mail();
              wm.setSendTo(emailUrl);
              wm.setBoby(title);
              wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
              MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
              if ("haier".equals(SystemCommon.getCustomerName()))
                emailBuffer.append(emailUrl).append(","); 
            } 
          } 
          if (remindType != null && remindType.indexOf("$04$") >= 0 && 
            "iactive".equals(usd)) {
            TblSysmsg tblSysmsg = new TblSysmsg();
            TblSysmsgBD tblSysmsgBD = new TblSysmsgBD();
            String username = (new UserBD()).getUserName(new Long(empId));
            List<Integer> useridsList = tblSysmsgBD.findUserId("'" + username + "'");
            String userids = "";
            String userid = "";
            for (int j = 0; j < useridsList.size(); j++) {
              userid = ((Integer)useridsList.get(j)).toString();
              userids = String.valueOf(userids) + userid + ",";
            } 
            userids = "," + userids;
            tblSysmsg.setUsageType(1);
            tblSysmsg.setContentType(0);
            tblSysmsg.setDisplayMode(0);
            tblSysmsg.setContent(title);
            tblSysmsg.setReceiverType(24);
            tblSysmsg.setReceiver(userids);
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tblSysmsg.setStartTime(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblSysmsg.setEndTime(si.format(calendar.getTime()));
            tblSysmsg.setApp(3);
            tblSysmsg.setCreatorId(3);
            tblSysmsg.setCreatorType(3);
            tblSysmsg.setOrgId(1);
            tblSysmsg.setAid(1);
            tblSysmsg.setDeptId(0);
            tblSysmsg.setOrderNum(0);
            tblSysmsgBD.addTblSysmsg(tblSysmsg);
          } 
          if (remindType != null && remindType.indexOf("$05$") >= 0) {
            if ("rtx".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RTXSvrApi rtxSvr = new RTXSvrApi();
              rtxSvr.sendNotify(username, "系统消息", title, url, "0", "0");
            } 
            if ("gk".equals(usd)) {
              String username = (new UserBD()).getUserName(new Long(empId));
              RealTimeUtil util = new RealTimeUtil();
              util.sendNotify(username, title, title, "1", "0");
            } 
            if ("jsim".equals(usd))
              sendMessageToJSIM(Long.parseLong(empId), moduleType, title, url, sqlNow, 0L, "系统提醒", 1, 0, sqlStart, sqlEnd, Long.valueOf(0L)); 
          } 
          if (remindType != null && remindType.indexOf("$06$") >= 0)
            (new SaiTongUtil()).saiTongExc(title, empId, url); 
          if (remindType != null && remindType.indexOf("$07$") >= 0) {
            String username = (new UserBD()).getUserName(new Long(empId));
            (new SysMessageSend()).sendMessage(username, title, url);
          } 
        } 
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
  }
  
  private void sendMessageToJSIM(long empId, String moduleType, String title, String url, Timestamp sqlNow, long send_UserId, String send_UserName, int message_status, int message_show, Timestamp sqlStart, Timestamp sqlEnd, Long data_id) {
    String databaseType = SystemCommon.getDatabaseType();
    Connection conn = null;
    try {
      String psql;
      conn = (new DataSourceBase())
        .getDataSource().getConnection();
      if (databaseType.indexOf("mysql") >= 0) {
        psql = "insert into ofmessages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
      } else if (databaseType.indexOf("oracle") >= 0) {
        psql = "insert into ofmessages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id,message_id) values(?,?,?,?,?,?,?,?,?,?,?,?,hibernate_sequence.nextval)";
      } else {
        psql = "insert into ofmessages(message_touserid,message_type,message_title,message_url,message_time,message_send_userId,message_send_username,message_status,message_show,message_date_begin,message_date_end,data_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
      } 
      PreparedStatement pstmt = conn.prepareStatement(psql);
      pstmt.setLong(1, empId);
      pstmt.setString(2, moduleType);
      pstmt.setString(3, title);
      pstmt.setString(4, url);
      pstmt.setTimestamp(5, sqlNow);
      pstmt.setLong(6, 0L);
      pstmt.setString(7, send_UserName);
      pstmt.setInt(8, 1);
      pstmt.setInt(9, message_show);
      pstmt.setTimestamp(10, sqlStart);
      pstmt.setTimestamp(11, sqlEnd);
      pstmt.setLong(12, data_id.longValue());
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public static String getUseEmailFlag() {
    SysSetupReader ssr = new SysSetupReader();
    String str = ssr.getEmail("0");
    String useEmailFlag = str.split(";")[0];
    return useEmailFlag;
  }
  
  public static Boolean getSysControlConfig() {
    Boolean flag = Boolean.valueOf(false);
    SysSetupReader sysRed = SysSetupReader.getInstance();
    String options = sysRed.getSystemOption("0");
    if (options.charAt(2) == '1')
      flag = Boolean.valueOf(true); 
    return flag;
  }
  
  public static Boolean judgeUserReceiveMs(Boolean receiveMs, String userId) {
    Boolean flag = Boolean.valueOf(false);
    try {
      if (receiveMs.booleanValue()) {
        OrganizationBD orgBD = new OrganizationBD();
        String orgId = orgBD.getOrgIdByUserID(userId);
        String sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=2 and po.msManage.msId=1";
        MsManageBD msBD = new MsManageBD();
        List<Object[]> msList = msBD.getListByYourSQL(sql);
        if (msList != null && msList.size() != 0)
          for (int i = 0; i < msList.size(); i++) {
            Object[] obj = msList.get(i);
            sql = "select vo.orgId,vo.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO vo where vo.orgIdString like '%$" + 
              orgId + "$%' and orgIdString like '%$" + obj[1] + "$%'";
            List orgIdStingList = msBD.getListByYourSQL(sql);
            if (orgIdStingList != null && orgIdStingList.size() > 0)
              flag = Boolean.valueOf(true); 
          }  
        if (!flag.booleanValue()) {
          GroupBD groupBD = new GroupBD();
          List<E> userGroupList = groupBD.searchByUserid(Long.valueOf(userId).longValue());
          if (userGroupList != null && userGroupList.size() != 0) {
            String userGroupId = userGroupList.get(0).toString();
            sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=3 and po.msManage.msId=1";
            msList = msBD.getListByYourSQL(sql);
            if (msList != null && msList.size() != 0)
              for (int i = 0; i < msList.size(); i++) {
                Object[] obj = msList.get(i);
                if (userGroupId.equals(obj[1]))
                  flag = Boolean.valueOf(true); 
              }  
          } 
        } 
        if (!flag.booleanValue()) {
          sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=1 and po.msManage.msId=1";
          msList = msBD.getListByYourSQL(sql);
          if (msList != null && msList.size() != 0)
            for (int i = 0; i < msList.size(); i++) {
              Object[] obj = msList.get(i);
              if (userId.equals(obj[1]))
                flag = Boolean.valueOf(true); 
            }  
        } 
      } else {
        flag = Boolean.valueOf(true);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  private String changeMessageTitle(MessagesBean messagesBean) {
    String getFlowSql = "SELECT workstatus,workfiletype,worktitle,workmainlinkfile from jsf_work WHERE wf_work_id= " + messagesBean.getData_id();
    String workStatus = "", workfiletype = "", worktitle = "", workmainlinkfile = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(getFlowSql);
      while (rs.next()) {
        workStatus = rs.getString("workstatus");
        workfiletype = rs.getString("workfiletype");
        worktitle = rs.getString("worktitle");
        workmainlinkfile = rs.getString("workmainlinkfile");
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
      return messagesBean.getTitle();
    } 
    if (workmainlinkfile.indexOf("/GovSendFileLoadAction") > 0 || workmainlinkfile.indexOf("/GovReceiveFileLoadAction") > 0)
      if ("1".equals(workStatus)) {
        worktitle = "【" + workfiletype + "】" + worktitle + "正在办理中！";
      } else if ("0".equals(workStatus)) {
        worktitle = "【" + workfiletype + "】" + worktitle + "等待您处理！";
      } else if ("101".equals(workStatus)) {
        worktitle = "【" + workfiletype + "】" + worktitle + "您已办理完毕！";
      } else if ("100".equals(workStatus)) {
        worktitle = "【" + workfiletype + "】" + worktitle + "已办理完毕！";
      } else if ("102".equals(workStatus)) {
        worktitle = "【" + workfiletype + "】" + worktitle + "审阅完毕！";
      } else {
        worktitle = "【" + workfiletype + "】" + worktitle;
      }  
    return worktitle;
  }
}
