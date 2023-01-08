package com.js.net;

import com.js.util.util.MD5;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetReader {
  private String hexByte(byte b) {
    String s = "000000" + Integer.toHexString(b);
    return s.substring(s.length() - 2);
  }
  
  public static void main(String[] args) throws Exception {
    NetReader reader = new NetReader();
    System.out.println(reader.getmac());
    System.out.println(reader.getMac());
    reader.getMacOnWindow();
  }
  
  public List getMac() {
    List<String> macList = new ArrayList();
    try {
      Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
      while (el.hasMoreElements()) {
        StringBuilder builder = new StringBuilder();
        byte[] mac = ((NetworkInterface)el.nextElement()).getHardwareAddress();
        if (mac == null || mac.length < 1)
          continue; 
        byte b;
        int i;
        byte[] arrayOfByte1;
        for (i = (arrayOfByte1 = mac).length, b = 0; b < i; ) {
          byte b1 = arrayOfByte1[b];
          builder.append(hexByte(b1));
          builder.append("-");
          b++;
        } 
        builder.deleteCharAt(builder.length() - 1);
        if (builder.length() > 1)
          macList.add(builder.toString()); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return macList;
  }
  
  public String getMacOnWindow() {
    String mac = "";
    try {
      Process process = Runtime.getRuntime().exec("ipconfig /all");
      BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
      for (String line = buffer.readLine(); line != null; ) {
        int index = line.indexOf("Physical Address");
        if (index <= 0) {
          line = buffer.readLine();
          continue;
        } 
        mac = line.substring(index + 36);
        break;
      } 
      buffer.close();
      process.waitFor();
      mac = mac.toLowerCase();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return mac;
  }
  
  public String getmac() {
    List<E> macList = getMac();
    StringBuffer buffer = new StringBuffer();
    if (macList != null) {
      MD5 md5 = new MD5();
      for (int i = 0; i < macList.size(); i++) {
        String mac = macList.get(i).toString();
        mac = mac.toLowerCase();
        buffer.append(md5.toMD5(mac)).append(",");
      } 
    } 
    return buffer.toString();
  }
}
