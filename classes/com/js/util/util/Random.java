package com.js.util.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Random {
  public String getFileRandomName() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(System.currentTimeMillis());
    String random = String.valueOf(Math.random());
    random = random.substring(random.length() - 6, random.length());
    buffer.append(random);
    String temp = buffer.toString();
    if (temp.indexOf("E-") > 0)
      temp = temp.substring(0, temp.indexOf("E-")); 
    return temp;
  }
  
  public String getRandom() {
    Calendar calendar = new GregorianCalendar();
    StringBuffer buffer = new StringBuffer();
    buffer.append(calendar.get(1));
    int month = calendar.get(2) + 1;
    int date = calendar.get(5);
    int hour = calendar.get(11);
    int min = calendar.get(12);
    int sec = calendar.get(13);
    int millsec = calendar.get(14);
    if (month < 10)
      buffer.append(0); 
    buffer.append(month);
    if (date < 10)
      buffer.append(0); 
    buffer.append(date);
    if (hour < 10)
      buffer.append(0); 
    buffer.append(hour);
    if (min < 10)
      buffer.append(0); 
    buffer.append(min);
    if (sec < 10)
      buffer.append(0); 
    buffer.append(sec);
    if (millsec < 100) {
      if (millsec < 10)
        buffer.append(0); 
      buffer.append(0);
    } 
    buffer.append(millsec);
    String random = String.valueOf(Math.random());
    random = random.substring(random.length() - 8, random.length());
    buffer.append(random);
    return buffer.toString();
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 10000; i++) {
      String xx = (new Random()).getFileRandomName();
      if (xx.indexOf("E-") > 0) {
        System.out.println("xx:" + xx);
      } else if (i == 1) {
        System.out.println("xx:" + xx);
      } 
    } 
  }
}
