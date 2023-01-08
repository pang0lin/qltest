package com.js.thread;

import com.ibm.icu.util.Calendar;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class MeetingRemindSendEmail {
  private static int EXECUTE_FLAG = 0;
  
  public void execute() {
    SysSetupReader ssr = new SysSetupReader();
    String str = ssr.getEmail("0");
    String useEmailFlag = str.split(";")[0];
    if (EXECUTE_FLAG == 0 && "1".equals(useEmailFlag)) {
      EXECUTE_FLAG = 1;
      String databaseType = SystemCommon.getDatabaseType();
      Connection conn = null;
      Statement stmt = null;
      String sql = "";
      try {
        String emailRemindPrefix = SystemCommon.getEmailRemindPrefix();
        Calendar cal = Calendar.getInstance();
        cal.add(6, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(cal.getTime());
        cal.add(6, 1);
        String nextDay = format.format(cal.getTime());
        DataSourceBase ds = new DataSourceBase();
        conn = ds.getDataSource().getConnection();
        if ("oracle".equals(databaseType)) {
          sql = "SELECT sm.message_title,e.empemail,sm.data_id,kk.status FROM sys_messages sm JOIN org_employee e ON  sm.message_touserid=e.emp_id  LEFT JOIN (SELECT srs.emp_id, srs.status FROM sys_remind_type tt LEFT JOIN sys_remind_set srs ON tt.type=srs.type WHERE tt.type='Meeting')  kk  ON ( kk.emp_id=e.emp_id OR kk.emp_id=NULL) WHERE  e.userisdeleted=0 AND e.userisactive=1 AND  sm.message_type='Meeting' AND e.empemail IS NOT NULL AND e.empemail<>'' AND  sm.message_date_begin<=to_date('" + 


            
            nextDay + "','yyyy-MM-dd')  AND sm.message_date_begin>to_date('" + today + "','yyyy-MM-dd')" + 
            " AND sm.message_url LIKE '/jsoa/BoardRoomAction.do?action=selectBoardroomApply%'";
        } else {
          sql = "SELECT sm.message_title,e.empemail,sm.data_id,kk.status FROM sys_messages sm JOIN org_employee e ON  sm.message_touserid=e.emp_id  LEFT JOIN (SELECT srs.emp_id, srs.status FROM sys_remind_type tt LEFT JOIN sys_remind_set srs ON tt.type=srs.type WHERE tt.type='Meeting')  kk  ON ( kk.emp_id=e.emp_id OR kk.emp_id=NULL) WHERE e.userisdeleted=0 AND e.userisactive=1 AND sm.message_type='Meeting' AND e.empemail IS NOT NULL AND e.empemail<>'' AND  sm.message_date_begin<='" + 


            
            nextDay + "'  AND  sm.message_date_begin>'" + today + "'" + 
            " AND sm.message_url LIKE '/jsoa/BoardRoomAction.do?action=selectBoardroomApply%'";
        } 
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String title = rs.getString(1);
          String emailUrl = rs.getString(2);
          String remindType = rs.getString(3);
          if (remindType != null && remindType.indexOf("$03$") >= 0 && 
            emailUrl != null && !"".equals(emailUrl) && !"null".equals(emailUrl)) {
            Mail wm = new Mail();
            wm.setSendTo(emailUrl);
            wm.setBoby(title);
            wm.setSubjectTitle(String.valueOf(emailRemindPrefix) + title);
            MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
          } 
        } 
        stmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception er) {
            er.printStackTrace();
          }  
        ex.printStackTrace();
      } 
      EXECUTE_FLAG = 0;
    } 
  }
}
