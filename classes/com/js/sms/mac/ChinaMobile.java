package com.js.sms.mac;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public final class ChinaMobile {
  private static final String sendMSM = "http://218.246.34.171/daredo/pushsms/mingxun_mt_rpt.jsp?CP_ID=2306&CP_PASSWORD=zmsw1128";
  
  private static final String acceptSMS = "http://218.246.34.171/daredo/pushsms/mingxun_mo.jsp?CP_ID=2306&CP_PASSWORD=zmsw1128";
  
  public static String sendShortMes(String phone, String msg) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      String pare = "&PHONE=" + phone + "&DELIVEROID=0" + "&MSG=" + msg.replaceAll("\r\n", "%0D%0A").replaceAll(" ", "%20") + "&SPNUMBER=100301" + "&SVCTYPE=TEST" + "&LINKID=TEST" + "&AUTHCODE=TEST";
      StringBuffer sendBuffer = new StringBuffer("http://218.246.34.171/daredo/pushsms/mingxun_mt_rpt.jsp?CP_ID=2306&CP_PASSWORD=zmsw1128");
      sendBuffer.append(pare);
      URL url = new URL(sendBuffer.toString());
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
      out.close();
      InputStream in = connection.getInputStream();
      byte[] buf = new byte[20];
      if (in.read(buf) > 0)
        stringBuffer.append(new String(buf)); 
      in.close();
      in = null;
      url = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return stringBuffer.toString();
  }
  
  public static String acceptShortMes() {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      URL url = new URL("http://218.246.34.171/daredo/pushsms/mingxun_mo.jsp?CP_ID=2306&CP_PASSWORD=zmsw1128");
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
      out.close();
      InputStream in = connection.getInputStream();
      byte[] buf = new byte[1000];
      if (in.read(buf) > 0)
        stringBuffer.append(new String(buf)); 
      in.close();
      in = null;
      url = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return stringBuffer.toString();
  }
  
  public static void main(String[] args) {
    System.out.println(sendShortMes("13466558736", "海淀网络检测中心提示您，由于检测到您浏览非法网页，特此警告一次！"));
  }
}
