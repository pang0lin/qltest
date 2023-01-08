package com.js.oa.routine.voiture.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDeal {
  public String dealTime() {
    String riqi = new String();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    riqi = formatter.format(date);
    return riqi;
  }
  
  public String dealTimeInput(String dealTime) throws Exception {
    String riqi = new String();
    SimpleDateFormat formatter1 = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");
    Date date = formatter1.parse(dealTime);
    riqi = formatter1.format(date);
    return riqi;
  }
  
  public String dealString(String str, String hourTime, String MinuterTime) throws Exception {
    String riqi = new String();
    StringBuffer sb = new StringBuffer();
    if (str != null)
      if (!str.trim().equals("")) {
        sb.append(str.replaceAll("\\/", "\\-"));
        sb.append(" ");
      }  
    if (hourTime != null)
      if (!hourTime.trim().equals("")) {
        if (Integer.parseInt(hourTime) > 0 && 
          Integer.parseInt(hourTime) < 10) {
          sb.append("0");
          sb.append(Integer.parseInt(hourTime));
          sb.append(":");
        } else {
          sb.append(String.valueOf(Integer.parseInt(hourTime)) + ":");
        } 
      } else {
        sb.append("00:");
      }  
    if (MinuterTime != null)
      if (!MinuterTime.trim().equals("")) {
        if (Integer.parseInt(MinuterTime) > 0 && 
          Integer.parseInt(MinuterTime) < 10) {
          sb.append("0");
          sb.append(Integer.parseInt(MinuterTime));
        } else {
          sb.append(Integer.parseInt(MinuterTime));
        } 
      } else {
        sb.append("00");
      }  
    riqi = sb.toString();
    return riqi;
  }
}
