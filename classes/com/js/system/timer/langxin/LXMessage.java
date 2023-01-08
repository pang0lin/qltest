package com.js.system.timer.langxin;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LXMessage {
  public static void getMessages() {
    String databaseName = SystemCommon.getLxDatabaseName();
    String databaseType = SystemCommon.getDatabaseType();
    DataSourceBase base = new DataSourceBase();
    Connection connSoruce = null;
    PreparedStatement psSource = null;
    ResultSet rsSource = null;
    Connection connTarget = null;
    PreparedStatement psTarget = null;
    ResultSet rsTarget = null;
    if (databaseName != null && !"".equals(databaseName)) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        long beginTime = date.getTime();
        connSoruce = base.getDataSource(databaseName).getConnection();
        connTarget = base.getDataSource().getConnection();
        psSource = connSoruce.prepareStatement("select empId,msg_type,msg_desc,msg_url,deal_flag from TI_MSG_INFO where deal_flag='N'");
        rsSource = psSource.executeQuery();
        String sql = "", empId = "", msgEmpId = "", msgType = "", msgDesc = "", msgURL = "", msgFlag = "", errMsg = "";
        int num = 0, errNum = 0;
        while (rsSource.next()) {
          try {
            msgEmpId = rsSource.getString("empId");
            msgType = rsSource.getString("msg_type");
            msgDesc = rsSource.getString("msg_desc");
            msgURL = rsSource.getString("msg_url");
            msgFlag = rsSource.getString("deal_flag");
            sql = "select emp_id from org_employee where wm_code='" + msgEmpId + "'";
            psTarget = connTarget.prepareStatement(sql);
            rsTarget = psTarget.executeQuery();
            if (rsTarget.next()) {
              empId = rsTarget.getString(1);
              rsTarget.close();
              sql = "select 1 from sys_messages where message_toUserId=" + empId + " and message_type='LX_" + msgType + 
                "' and message_title='" + msgDesc + "' and message_url='" + msgURL + "' and message_status=1";
              psTarget = connTarget.prepareStatement(sql);
              rsTarget = psTarget.executeQuery();
              if (rsTarget.next()) {
                errNum++;
                errMsg = "已存在未读的相同消息。";
              } else {
                if (databaseType.indexOf("oracle") > -1) {
                  sql = "insert into sys_messages(message_id,message_toUserId,message_type,message_title,message_url,message_time,message_send_userId,message_send_userName,message_status,message_show,message_date_begin,message_date_end,data_id,weixinremindflag) values (hibernate_sequence.nextval," + 

                    
                    empId + ",'LX_" + msgType + "','" + msgDesc + "','" + msgURL + "',sysdate,0,'完美系统',1,1,sysdate," + 
                    "to_date('2050-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),0,1)";
                } else {
                  sql = "insert into sys_messages(message_toUserId,message_type,message_title,message_url,message_time,message_send_userId,message_send_userName,message_status,message_show,message_date_begin,message_date_end,data_id,weixinremindflag) values (" + 

                    
                    empId + ",'LX_" + msgType + "','" + msgDesc + "','" + msgURL + "',now(),0,'完美系统',1,1,now()," + 
                    "'2050-01-01 00:00:00',0,1)";
                } 
                psTarget = connTarget.prepareStatement(sql);
                psTarget.executeUpdate();
                num++;
              } 
            } else {
              errNum++;
              errMsg = "消息接收人不存在。";
            } 
            if (rsTarget != null)
              rsTarget.close(); 
            if (psTarget != null)
              psTarget.close(); 
          } catch (Exception e) {
            errNum++;
            e.printStackTrace();
            errMsg = e.getMessage();
            continue;
          } finally {
            sql = "update TI_MSG_INFO set err_msg='" + errMsg + "',deal_flag='Y',deal_dt=getDate() where EMPID='" + msgEmpId + "' and MSG_TYPE='" + 
              msgType + "' and MSG_DESC='" + msgDesc + "' and DEAL_FLAG='" + msgFlag + "'";
            psSource = connSoruce.prepareStatement(sql);
            psSource.executeUpdate();
          } 
        } 
        System.out.println(String.valueOf(sdf.format(date)) + " 同步用户提醒信息用时" + ((new Date()).getTime() - beginTime) + "ms。成功：" + num + "条，失败：" + errNum + "条");
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        try {
          if (rsTarget != null)
            rsTarget.close(); 
          if (psTarget != null)
            psTarget.close(); 
          if (connTarget != null)
            connTarget.close(); 
          if (rsSource != null)
            rsSource.close(); 
          if (psSource != null)
            psSource.close(); 
          if (connSoruce != null)
            connSoruce.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } else {
      System.out.println("同步信息数据源信息未配置...");
    } 
  }
}
