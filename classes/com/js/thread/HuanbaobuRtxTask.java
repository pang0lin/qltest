package com.js.thread;

import com.js.oa.userdb.util.DbOpt;
import rtx.RTXSvrApi;

public class HuanbaobuRtxTask extends Thread {
  private void execute() {
    String sql = "select message_id,message_receiver,message_sender,message_title,message_url from rtx_message where send_flag=0  and rownum<10 order by message_id";
    while (true) {
      DbOpt db = null;
      try {
        db = new DbOpt();
        String[][] result = (String[][])null;
        try {
          result = db.executeQueryToStrArr2(sql, 5);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (result != null && result.length > 0)
          for (int i = 0; i < result.length; i++) {
            RTXSvrApi rtxSvr = new RTXSvrApi();
            rtxSvr.sendNotify(result[i][1], result[i][2], result[i][3], result[i][4], "0", "0");
            db.executeUpdate("delete from rtx_message where message_id=" + result[i][0]);
          }  
        db.close();
        if (result == null || result.length < 0)
          Thread.sleep(5000L); 
      } catch (Exception e) {
        if (db != null)
          try {
            db.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        try {
          Thread.sleep(3000L);
        } catch (Exception err) {
          err.printStackTrace();
        } 
      } 
    } 
  }
  
  public void run() {
    execute();
  }
}
