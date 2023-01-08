package com.js.sms.mac;

import com.jasson.im.api.APIClient;
import com.jasson.im.api.MOItem;
import com.jasson.im.api.RPTItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class ApiTestDemo {
  private String cmd = "11";
  
  private String mobileStr = "13600136666,13800138888";
  
  private String content = "短信发送测试内容 via  IM JAVA API ";
  
  private long smId = 1L;
  
  private int smType = 0;
  
  private String url = "wap.sohu.com";
  
  private String host = "localhost";
  
  private String dbName = "im";
  
  private String apiId = "11";
  
  private String name = "11";
  
  private String pwd = "11";
  
  private APIClient handler = new APIClient();
  
  BufferedReader in = null;
  
  public ApiTestDemo(String[] args) {
    parseCmd(args);
    init();
    this.in = new BufferedReader(new InputStreamReader(System.in), 512);
  }
  
  public void init() {
    int connectRe = this.handler.init(this.host, this.name, this.pwd, this.apiId, this.dbName);
    if (connectRe == 0) {
      info("初始化成功");
    } else if (connectRe == -1) {
      info("连接失败");
    } else if (connectRe == -7) {
      info("apiID不存在");
    } 
    if (connectRe != 0) {
      usage();
      System.exit(-1);
    } 
  }
  
  public void release() {
    this.handler.release();
    Thread.currentThread().interrupt();
  }
  
  public void testSend() {
    SendTask task = new SendTask();
    task.start();
  }
  
  public void sendSM() {
    long l;
    String tmpMobile = null;
    String tmpContent = null;
    String tmpTypeStr = null;
    String tmpSrcID = null;
    int tmpType = 0;
    String tmpUrl = null;
    info("请输入srcID，按回车结束：");
    tmpSrcID = readLine();
    if (tmpSrcID == null || tmpSrcID.trim().length() == 0 || getInt(tmpSrcID.trim()) == Integer.MIN_VALUE)
      l = this.smId; 
    info("请输入手机号码，多个号码用英文逗号隔开，按回车结束：");
    tmpMobile = readLine();
    if (tmpMobile == null || tmpMobile.trim().length() == 0)
      tmpMobile = this.mobileStr; 
    info("请输入短信内容，按回车结束：");
    tmpContent = readLine();
    if (tmpContent == null || tmpContent.trim().length() == 0)
      tmpContent = this.content; 
    info("请输入短信类型是否为WAP PUSH消息，按回车结束：Y/N[N]");
    tmpTypeStr = readLine();
    if (tmpTypeStr != null && tmpTypeStr.trim().equalsIgnoreCase("Y")) {
      tmpType = 1;
      info("请输入短信类型是否为WAP PUSH消息，按回车结束：Y/N[N]");
      tmpUrl = readLine();
      if (tmpUrl == null || tmpUrl.trim().length() == 0)
        tmpUrl = this.url; 
    } else {
      tmpType = this.smType;
    } 
    Vector<String> mobileList = new Vector();
    StringTokenizer st = new StringTokenizer(tmpMobile, ",");
    while (st.hasMoreElements()) {
      String tmp = (String)st.nextElement();
      if (tmp.indexOf("-") != -1) {
        long min = Long.parseLong(tmp.substring(0, tmp.indexOf("-")));
        long max = Long.parseLong(tmp.substring(tmp.indexOf("-") + 1));
        long l1 = min;
        while (l1 <= max) {
          mobileList.addElement(Long.toString(l1));
          l1++;
        } 
        continue;
      } 
      mobileList.addElement(tmp);
    } 
    int len = mobileList.size();
    String[] mobiles = new String[len];
    for (int i = 0; i < len; i++)
      mobiles[i] = mobileList.elementAt(i); 
    int result = 0;
    if (tmpType == 1) {
      if (this.url == null || this.url.length() == 0) {
        info("请输入Wap Push的链接地址！");
        return;
      } 
      result = this.handler.sendSM(mobiles, tmpContent, this.smId, Long.parseLong(l), this.url);
    } else {
      result = this.handler.sendSM(mobiles, tmpContent, this.smId, Long.parseLong(l));
    } 
    if (result == 0) {
      info("发送成功\n");
    } else if (result == -9) {
      info("未初始化");
    } else if (result == -1) {
      info("数据库连接失败");
    } else if (result == -6) {
      info("参数错误");
    } else if (result == -8) {
      info("消息内容太长");
    } else if (result == -3) {
      info("数据库插入错误");
    } else {
      info("出现其他错误");
    } 
  }
  
  public void recvSM() {
    MOItem[] mos = this.handler.receiveSM();
    int len = 0, i = 0;
    StringBuffer sb = new StringBuffer("");
    if (mos == null) {
      info("未初始化或接收失败");
      return;
    } 
    if (mos.length == 0) {
      info("没有MO短信");
    } else {
      len = mos.length;
      while (i < len) {
        sb.append("手机号码: ");
        sb.append(String.valueOf(mos[i].getMobile()) + " ");
        sb.append("短信内容: ");
        sb.append(mos[i].getContent());
        sb.append("MO时间: ");
        sb.append(mos[i].getMoTime());
        sb.append("\n");
        i++;
      } 
      info(sb.toString());
    } 
  }
  
  public void recvRPT() {
    RPTItem[] rpts = this.handler.receiveRPT();
    int len = 0, i = 0;
    StringBuffer sb = new StringBuffer("");
    if (rpts == null) {
      info("未初始化或接收失败");
      return;
    } 
    if (rpts.length == 0) {
      info("没有回执");
    } else {
      len = rpts.length;
      while (i < len) {
        sb.append("手机: ");
        sb.append(String.valueOf(rpts[i].getMobile()) + " ");
        sb.append("回执编码: ");
        sb.append(String.valueOf(rpts[i].getCode()) + " ");
        sb.append("回执描述: ");
        sb.append(String.valueOf(rpts[i].getDesc()) + " ");
        sb.append("回执时间: ");
        sb.append(String.valueOf(rpts[i].getRptTime()) + " ");
        sb.append("\n");
        i++;
      } 
      info(sb.toString());
    } 
  }
  
  public void error(Object obj, Throwable thr) {
    info(obj);
    thr.printStackTrace();
  }
  
  public void info(Object obj) {
    System.out.println(obj);
  }
  
  public String readLine() {
    String line = null;
    try {
      line = this.in.readLine();
    } catch (IOException e) {
      error("", e);
    } 
    return line;
  }
  
  public int getInt(String str) {
    int ret = Integer.MIN_VALUE;
    try {
      ret = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      ret = Integer.MIN_VALUE;
    } 
    return ret;
  }
  
  public void usage() {
    info("Usage : java ApiTestDemo [-h host] [-n name] [-p password] [-i apiCode]");
    info("\t-h host        信息机地址");
    info("\t-n name        API登陆名");
    info("\t-p password    API登陆密码");
    info("\t-i apiCode     API编码");
  }
  
  public void menu() {
    info("\n------------------------------");
    info("1\t 发送短信");
    info("2\t 发送短信(srcID测试)");
    info("3\t 接收短信");
    info("4\t 接收回执");
    info("5\t 查看帮助");
    info("6\t 退出");
    info("------------------------------");
    info("请输入你要进行操作的数字:");
  }
  
  public void quit() {
    release();
    System.exit(0);
  }
  
  public void run() {
    while (true) {
      menu();
      int menu = 0;
      menu = parseMenu(readLine());
      if (menu < 1 || menu > 6)
        continue; 
      switch (menu) {
        case 1:
          sendSM();
        case 2:
          testSend();
        case 3:
          recvSM();
        case 4:
          recvRPT();
        case 5:
          usage();
        case 6:
          quit();
      } 
    } 
  }
  
  public int parseMenu(String menu) {
    int cmd = 0;
    try {
      cmd = Integer.parseInt(menu);
    } catch (Exception e) {
      cmd = -1;
    } 
    return cmd;
  }
  
  public void parseCmd(String[] args) {
    String tmp = "";
    int index = 0;
    int len = args.length;
    if (args.length > 0) {
      info("parse argements....");
      while (index < len) {
        tmp = args[index++].trim();
        if (tmp.equalsIgnoreCase("-h")) {
          this.host = args[index++];
          info(" host  = " + this.host);
          continue;
        } 
        if (tmp.equalsIgnoreCase("-n")) {
          this.name = args[index++];
          info(" name  = " + this.name);
          continue;
        } 
        if (tmp.equalsIgnoreCase("-p")) {
          this.pwd = args[index++];
          info(" pwd   = " + this.pwd);
          continue;
        } 
        if (tmp.equalsIgnoreCase("-i")) {
          this.apiId = args[index++];
          info(" apiId = " + this.apiId);
          continue;
        } 
        index += 2;
      } 
    } 
  }
  
  class SendTask extends Thread {
    int loop = 10;
    
    long interval = 2000L;
    
    public void run() {
      Random random = new Random();
      long tmpSmId = 0L;
      long tmpSrcId = 0L;
      while (this.loop > 0) {
        try {
          Thread.sleep(this.interval);
        } catch (InterruptedException interruptedException) {}
        tmpSmId = random.nextInt(1000);
        tmpSrcId = random.nextInt(100);
        System.out.println("TestSend:" + this.loop + "[smId:" + tmpSmId + ";srcId:" + tmpSrcId + "]");
        ApiTestDemo.this.handler.sendSM(ApiTestDemo.this.mobileStr.split(","), ApiTestDemo.this.content, tmpSmId, tmpSrcId);
        this.loop--;
      } 
      System.out.println("Test completed.");
    }
  }
  
  public static void main(String[] args) {
    ApiTestDemo demo = new ApiTestDemo(args);
    demo.run();
  }
}
