package com.buguniao;

import com.js.util.util.BASE64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class SysMessageSend {
  public void sendMessage(String userNO, String title) {
    String fileName = null;
    String uri = null;
    String up = null;
    Date date = new Date();
    fileName = String.valueOf(userNO) + date.getTime() + ".sisenmsg";
    String messagePath = (new ServerConn()).getMessagePath();
    messagePath = String.valueOf(messagePath) + "/" + fileName;
    try {
      File file = new File(messagePath);
      file.createNewFile();
      Writer wr = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
      wr.write("False");
      wr.write("\r\n");
      wr.write("\r\n");
      wr.write(userNO);
      wr.write("\r\n");
      wr.write("False");
      wr.write("\r\n");
      wr.write(title);
      wr.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void sendMessage(String userNO, String title, String url) {
    String oaServerUrl = (new ServerConn()).getOaAServerPath();
    if ("1".equals(ServerConn.getMessageHasURL()) && url != null && !"".equals(url) && !"null".equals(url)) {
      String[] strs = url.split("\\?");
      String[] strs1 = strs[0].split("\\.");
      String rtxMsgAction = strs1[0];
      String suffix = strs1[1];
      url = "rtxMsgAction=" + rtxMsgAction + "&suffix=" + suffix + "&bgnUserNO=" + userNO + "&" + strs[1] + "&date=" + Calendar.getInstance().getTimeInMillis();
      url = BASE64.BASE64EncoderNoBR(url);
      title = String.valueOf(title) + "\n" + oaServerUrl + "rtxFlag=bgnLogin&rtxMsg=yes&bgnLoginCode=" + url;
    } 
    sendMessage(userNO, title);
  }
  
  public static void main(String[] args) {
    try {
      (new SysMessageSend()).sendMessage("zhangsan111", "测试测试测试测试。。。。。。。。");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
