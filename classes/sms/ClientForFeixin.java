package sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ClientForFeixin {
  private String serviceURL = "http://202.100.211.104/WebService.asmx";
  
  public String jinpanfeixin_login(String username, String password) {
    this.serviceURL = "http://202.100.211.104:6666/WebService.asmx/Login?userid=" + username + "&password=" + password;
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      httpconn.setRequestMethod("GET");
      httpconn.setDoOutput(true);
      InputStream is = httpconn.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      while (br.read() != -1)
        sb.append(br.readLine()); 
      String result = new String(sb);
      result = new String(result.getBytes("GB2312"), "ISO-8859-1");
      br.close();
      if (result.contains("true")) {
        result = "true";
      } else {
        result = "false";
      } 
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public String jinpanfeixin_sendMessage(String receiver, String content, String sender) {
    if (!"".equals(content)) {
      content = content.replace(" ", "");
      try {
        content = URLEncoder.encode(content, "utf-8");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    this.serviceURL = "http://202.100.211.104:6666/SMSService.asmx/SendMsg?mobile=" + receiver + "&content=" + content;
    try {
      URL url = new URL(this.serviceURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpconn = (HttpURLConnection)connection;
      httpconn.setRequestMethod("GET");
      httpconn.setDoInput(true);
      httpconn.setDoOutput(true);
      InputStream is = httpconn.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      while (br.read() != -1)
        sb.append(br.readLine()); 
      String result = new String(sb);
      result = new String(result.getBytes("GB2312"), "ISO-8859-1");
      br.close();
      if (result.contains("true")) {
        result = "true";
      } else {
        result = "false";
      } 
      return result;
    } catch (IOException e) {
      return "false";
    } 
  }
}
