package com.js.thread;

import com.js.message.GuoTouSendMessage;
import com.js.message.MASSendMessage;
import com.js.oa.message.service.MsHistoryBD;
import com.js.sms.TelecomSendEX;
import com.js.sms.WXGSMModem;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.PropertiesTrans;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rtx.RTXSvrApi;

public class SMSTask {
  private static int EXECUTE_FLAG = 0;
  
  public void execute() {
    if (EXECUTE_FLAG == 0 && SystemCommon.getSMS_CAT()) {
      EXECUTE_FLAG = 1;
      String databaseType = SystemCommon.getDatabaseType();
      Connection conn = null;
      Statement stmt = null;
      String sql = "";
      String smsAPI = PropertiesTrans.getValueByKey("smsAPI");
      try {
        DataSourceBase ds = new DataSourceBase();
        conn = ds.getDataSource().getConnection();
        stmt = conn.createStatement();
        if (smsAPI.trim().equals("0")) {
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where  (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<15 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<15 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          boolean result = false;
          Date date1 = new Date();
          if (message.length > 0) {
            WXGSMModem modem = new WXGSMModem();
            modem.modemInit();
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              result = modem.doSendSinge(message[num][1], message[num][2], message[num][3]);
              Date date2 = new Date();
              System.out.println("发送第" + num + "条短信,发送结果:" + result + " 耗时：" + (date2.getTime() - date1.getTime()));
              if (result) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(800L);
            } 
            modem.release();
          } 
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("5")) {
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<15 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<15 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          boolean result = false;
          WXGSMModem modem = new WXGSMModem();
          Date date1 = new Date();
          if (message.length > 0) {
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              result = modem.sendSinge(message[num][1], message[num][2], message[num][3]);
              Date date2 = new Date();
              if (result) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(800L);
            } 
            modem.release();
          } 
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("6")) {
          int allNum = 20;
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where (resend is null or resend<>1)  and (sendtime <= now()) order by messageid limit 0," + allNum; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<" + allNum + " order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          System.out.println("待发短信数：" + num + "条（时间：" + (new Date()).getHours() + ":" + (new Date()).getMinutes() + "）");
          message = new String[num][7];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime,receiveman from ms_infoflow t where (resend is null or resend<>1)  and (sendtime <= now()) order by messageid limit 0," + allNum; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime,receiveman from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<" + allNum + " order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            message[num][6] = (rs.getString(7) == null || "null".equalsIgnoreCase(rs.getString(7)) || "".equals(rs.getString(7))) ? 
              rs.getString(2) : rs.getString(7);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          WXGSMModem modem = new WXGSMModem();
          List<String[]> list = (List)new ArrayList<String>();
          List<String[]> teleList = (List)new ArrayList<String>();
          MsHistoryBD msDb = new MsHistoryBD();
          String extendCode = msDb.genExtendCode();
          String telecode = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString().substring(7);
          if (message.length > 0)
            for (num = 0; num < message.length; num++) {
              String telecomNum = message[num][1].substring(0, 3);
              if (telecomNum.equals("133") || telecomNum.equals("153") || telecomNum.equals("180") || telecomNum.equals("181") || telecomNum.equals("189") || telecomNum.equals("180")) {
                String teleresult = TelecomSendEX.telecomSend(message[num][1], message[num][2], telecode);
                String[] teleInfo = { message[num][0], teleresult, "0", message[num][1], message[num][4], message[num][5], message[num][6], message[num][2] };
                teleList.add(teleInfo);
              } else {
                String[] masInfo = { message[num][0], modem.royaMas(message[num][1], message[num][2], extendCode), "0", 
                    message[num][1], message[num][4], message[num][5], message[num][6], message[num][2] };
                list.add(masInfo);
              } 
              Thread.sleep(600L);
            }  
          boolean result = false;
          int masNum = 4;
          int i;
          for (i = 0; i < teleList.size(); i++) {
            String[] info = teleList.get(i);
            if (info[1].equals("0")) {
              msDb.saveMsHistory(info[4], "", info[6], info[5], info[7], info[1], info[3], telecode);
              stmt.addBatch("delete from ms_infoflow where messageid=" + info[0]);
            } else {
              System.out.println("电信发送失败：手机号" + info[3]);
              stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + info[0]);
            } 
            Thread.sleep(200L);
          } 
          for (i = 0; i < list.size(); i++) {
            masNum = 4;
            String[] masInfo = list.get(i);
            String resultStr = "4000";
            modem.royoSendFlag(masInfo[1]);
            if (resultStr.equals("4000")) {
              result = true;
            } else if (",3002,1007,2002,".contains(resultStr)) {
              result = false;
            } else {
              masNum = Integer.valueOf(masInfo[2]).intValue();
              if (masNum < 3) {
                masNum++;
                masInfo[2] = (new StringBuilder(String.valueOf(masNum))).toString();
                list.add(masInfo);
              } else {
                result = false;
              } 
            } 
            if ("".equals(masInfo[1]))
              result = false; 
            if (masNum >= 3)
              if (result) {
                msDb.saveMsHistory(masInfo[4], "", masInfo[6], masInfo[5], masInfo[7], masInfo[1], masInfo[3], extendCode);
                stmt.addBatch("delete from ms_infoflow where messageid=" + masInfo[0]);
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + masInfo[0]);
              }  
            Thread.sleep(200L);
          } 
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("7")) {
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<15 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<15 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          boolean result = false;
          WXGSMModem modem = new WXGSMModem();
          Date date1 = new Date();
          if (message.length > 0) {
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              result = modem.sendSinge(message[num][1], message[num][2], message[num][3]);
              Date date2 = new Date();
              if (result) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(800L);
            } 
            modem.release();
          } 
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("8")) {
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<15 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<15 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          int result = 0;
          Date date1 = new Date();
          if (message.length > 0)
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              result = (new RTXSvrApi()).sendSms(message[num][1], message[num][2]);
              System.out.println("result:" + result);
              Date date2 = new Date();
              System.out.println("发送第" + num + "条短信,发送结果:" + ((result > 0) ? "true" : "false") + " 耗时：" + (date2.getTime() - date1.getTime()));
              if (result > 0) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(800L);
            }  
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("9")) {
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and  rownum<15 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid limit 0,15"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and (sendtime <= sysdate) and rownum<15 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          int result = 0;
          Date date1 = new Date();
          if (message.length > 0)
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              result = (new GuoTouSendMessage()).sendMessProxy(message[num][1], message[num][2]);
              System.out.println("result:" + result);
              Date date2 = new Date();
              System.out.println("发送第" + num + "条短信,发送结果:" + ((result > 0) ? "true" : "false") + " 耗时：" + (date2.getTime() - date1.getTime()));
              if (result > 0) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(800L);
            }  
          stmt.executeBatch();
        } else if (smsAPI.trim().equals("10")) {
          System.out.println("调用移动云MAS平台发送短信！");
          String[][] message = (String[][])null;
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid from ms_infoflow t where  resend is null or resend<>1  order by messageid limit 0,30"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid from ms_infoflow t where ( resend is null or resend<>1 ) and  rownum<30 order by messageid"; 
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next())
            num++; 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where resend is null or resend<>1 order by messageid limit 0,30"; 
          if (databaseType.indexOf("oracle") >= 0)
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where ( resend is null or resend<>1 ) and rownum<30 order by messageid"; 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          int result = 0;
          Date date1 = new Date();
          if (message.length > 0) {
            MASSendMessage masSendMessage = new MASSendMessage();
            for (num = 0; num < message.length; num++) {
              date1 = new Date();
              System.out.println(String.valueOf(message[num][1]) + "----" + message[num][2]);
              result = masSendMessage.getResult();
              System.out.println("result:" + result);
              Date date2 = new Date();
              System.out.println("发送第" + num + "条短信,发送结果:" + ((result > 0) ? "true" : "false") + " 耗时：" + (date2.getTime() - date1.getTime()));
              if (result > 0) {
                stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
                MsHistoryBD msDb = new MsHistoryBD();
                msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              } else {
                stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
              } 
              Thread.sleep(500L);
            } 
          } 
          stmt.executeBatch();
        } else {
          String[][] message = (String[][])null;
          MsHistoryBD msDb = new MsHistoryBD();
          int num = 0;
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select count(*) from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now())";
          } else {
            sql = "select count(*) from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= sysdate)";
          } 
          ResultSet rs = stmt.executeQuery(sql);
          if (rs.next())
            num = rs.getInt(1); 
          rs.close();
          message = new String[num][6];
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= now()) order by messageid";
          } else {
            sql = "select messageid,receivecode,mscontent,sendman,sendid,sendtime from ms_infoflow t where (resend is null or resend<>1) and (sendtime <= sysdate) order by messageid";
          } 
          rs = stmt.executeQuery(sql);
          num = 0;
          while (rs.next()) {
            message[num][0] = rs.getString(1);
            message[num][1] = rs.getString(2);
            message[num][2] = rs.getString(3);
            message[num][3] = rs.getString(4);
            message[num][4] = rs.getString(5);
            message[num][5] = rs.getString(6);
            stmt.addBatch("update ms_infoflow set resend=1 where messageid=" + message[num][0]);
            num++;
          } 
          rs.close();
          stmt.executeBatch();
          stmt.clearBatch();
          boolean result = false;
          WXGSMModem modem = new WXGSMModem();
          for (num = 0; num < message.length; num++) {
            result = modem.sendSinge(message[num][1], message[num][2], message[num][3]);
            if (result) {
              msDb.saveMsHistory(message[num][4], "", message[num][1], message[num][5], message[num][2], "1");
              stmt.addBatch("delete from ms_infoflow where messageid=" + message[num][0]);
            } else {
              stmt.addBatch("update ms_infoflow set resend=0 where messageid=" + message[num][0]);
            } 
            Thread.sleep(800L);
          } 
          stmt.executeBatch();
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
