package com.js.thread;

import SmgwClient.DeliverRespEX;
import SmgwClient.ErrorCode;
import SmgwClient.UserInterface;
import com.js.util.util.DataSourceBase;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

public class TelecomReceiveThread extends Thread {
  public void run() {
    UserInterface ui = new UserInterface();
    ErrorCode err = new ErrorCode();
    int ret = ui.InitSMGPAPI(err);
    System.out.println("兰大-电信开始接收短信。。。。");
    while (true) {
      DeliverRespEX deliverresp = new DeliverRespEX();
      ret = ui.SMGPDeliverEX(20, deliverresp, err);
      if (ret == 0) {
        byte[] zijie = deliverresp.GetMsgContent();
        String msg = "";
        try {
          msg = new String(zijie, "Unicode");
        } catch (UnsupportedEncodingException e1) {
          e1.printStackTrace();
        } 
        String phone = deliverresp.GetSrcTermID();
        String extendCode = deliverresp.GetDestTermID().substring(9);
        try {
          String sql = "insert into ms_telreceive(telreceive_id,telreceivenum,telcontent,telextend) values(hibernate_sequence.nextval,?,?,?)";
          DataSource ds = (new DataSourceBase()).getDataSource();
          Connection conn = ds.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, phone);
          pstmt.setString(2, msg);
          pstmt.setString(3, extendCode);
          pstmt.execute();
          pstmt.close();
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        continue;
      } 
      try {
        Thread.sleep(10000L);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
}
