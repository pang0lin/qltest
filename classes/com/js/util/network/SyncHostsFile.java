package com.js.util.network;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class SyncHostsFile {
  public static synchronized boolean updateHostName(String hostName, String ip) throws Exception {
    if (StringUtils.isEmpty(hostName) || StringUtils.isEmpty(ip))
      throw new Exception("HostName or Ip can't be null."); 
    if (StringUtils.isEmpty(hostName.trim()) || StringUtils.isEmpty(ip.trim()))
      throw new Exception("HostName or Ip can't be null."); 
    String splitter = " ";
    String fileName = null;
    boolean isLinux = false;
    if ("linux".equalsIgnoreCase(System.getProperty("os.name"))) {
      isLinux = true;
      fileName = "/etc/hosts";
    } else {
      fileName = "C:/WINDOWS/system32/drivers/etc/hosts";
    } 
    Security.setProperty("networkaddress.cache.ttl", "0");
    Security.setProperty("networkaddress.cache.negative.ttl", "0");
    if (isLinux) {
      Process processTest = Runtime.getRuntime().exec("/etc/init.d/nscd restart");
      processTest.destroy();
    } 
    List<?> lines = FileUtils.readLines(new File(fileName));
    List<String> newLines = new ArrayList<String>();
    boolean findFlag = false;
    boolean updateFlag = false;
    for (Object line : lines) {
      String strLine = (String)line;
      if (StringUtils.isNotEmpty(strLine) && !strLine.startsWith("#")) {
        strLine = strLine.replaceAll("/t", splitter);
        int index = strLine.toLowerCase().indexOf(hostName.toLowerCase());
        if (index != -1) {
          String[] array = strLine.trim().split(splitter);
          byte b;
          int i;
          String[] arrayOfString1;
          for (i = (arrayOfString1 = array).length, b = 0; b < i; ) {
            String name = arrayOfString1[b];
            if (hostName.equalsIgnoreCase(name)) {
              findFlag = true;
              if (array[0].equals(ip)) {
                newLines.add(strLine);
                break;
              } 
              StringBuilder sb = new StringBuilder();
              sb.append(ip);
              for (int j = 1; j < array.length; j++)
                sb.append(splitter).append(array[j]); 
              newLines.add(sb.toString());
              updateFlag = true;
              break;
            } 
            b++;
          } 
          if (findFlag)
            break; 
        } 
      } 
      newLines.add(strLine);
    } 
    if (!findFlag)
      newLines.add(ip + splitter + hostName); 
    if (updateFlag || !findFlag) {
      FileUtils.writeLines(new File(fileName), newLines);
      String formatIp = formatIpv6IP(ip);
      for (int i = 0; i < 20; i++) {
        try {
          boolean breakFlg = false;
          InetAddress[] addressArr = InetAddress.getAllByName(hostName);
          byte b;
          int j;
          InetAddress[] arrayOfInetAddress1;
          for (j = (arrayOfInetAddress1 = addressArr).length, b = 0; b < j; ) {
            InetAddress address = arrayOfInetAddress1[b];
            if (formatIp.equals(address.getHostAddress())) {
              breakFlg = true;
              break;
            } 
            b++;
          } 
          if (breakFlg)
            break; 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        Thread.sleep(3000L);
      } 
    } 
    if (isLinux) {
      Process processTest = Runtime.getRuntime().exec("/etc/init.d/nscd restart");
      processTest.destroy();
    } 
    return updateFlag;
  }
  
  private static String formatIpv6IP(String ipV6Addr) {
    String strRet = ipV6Addr;
    int iCount = 0;
    char ch = ':';
    if (StringUtils.isNotEmpty(ipV6Addr) && ipV6Addr.indexOf("::") > -1) {
      StringBuffer replaceStr;
      int i;
      for (i = 0; i < ipV6Addr.length(); i++) {
        if (ch == ipV6Addr.charAt(i))
          iCount++; 
      } 
      if (ipV6Addr.startsWith("::")) {
        replaceStr = new StringBuffer("0:0:");
        for (i = iCount; i < 7; i++)
          replaceStr.append("0:"); 
      } else if (ipV6Addr.endsWith("::")) {
        replaceStr = new StringBuffer(":0:0");
        for (i = iCount; i < 7; i++)
          replaceStr.append(":0"); 
      } else {
        replaceStr = new StringBuffer(":0:");
        for (i = iCount; i < 7; i++)
          replaceStr.append("0:"); 
      } 
      strRet = ipV6Addr.trim().replaceAll("::", replaceStr.toString());
    } 
    return strRet;
  }
  
  public static String getIP(String name) {
    InetAddress address = null;
    try {
      address = InetAddress.getByName(name);
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.out.println("获取失败");
    } 
    return address.getHostAddress().toString();
  }
  
  public static void main(String[] args) {
    try {
      updateHostName("www.abcd.com", "127.0.1.1");
      System.out.println(getIP("qyapi.weixin.qq.com"));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
