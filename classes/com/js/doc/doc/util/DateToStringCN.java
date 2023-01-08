package com.js.doc.doc.util;

import java.util.Date;

public class DateToStringCN {
  public static String convertObjectToYearMonthDateCN(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date tmpDate = (Date)obj;
    int year = 1900 + tmpDate.getYear();
    int month = tmpDate.getMonth() + 1;
    int date = tmpDate.getDate();
    String tmpYearSTR = getDateStringCN(year, "year");
    String tmpMonthSTR = getDateStringCN(month, "month");
    String tmpDateSTR = getDateStringCN(date, "date");
    retString = String.valueOf(tmpYearSTR) + "年" + tmpMonthSTR + "月" + tmpDateSTR + "日";
    return retString;
  }
  
  private static String getDateStringCN(int para, String type) {
    StringBuffer dateString = new StringBuffer("");
    String tmpDateString = "";
    if (type.equals("year")) {
      char[] tmpChar = (char[])null;
      tmpDateString = String.valueOf(para);
      tmpChar = tmpDateString.toCharArray();
      for (int i = 0; i < tmpChar.length; i++) {
        char nowChar = tmpChar[i];
        switch (nowChar) {
          case '0':
            dateString.append("○");
            break;
          case '1':
            dateString.append("一");
            break;
          case '2':
            dateString.append("二");
            break;
          case '3':
            dateString.append("三");
            break;
          case '4':
            dateString.append("四");
            break;
          case '5':
            dateString.append("五");
            break;
          case '6':
            dateString.append("六");
            break;
          case '7':
            dateString.append("七");
            break;
          case '8':
            dateString.append("八");
            break;
          case '9':
            dateString.append("九");
            break;
        } 
      } 
    } else if (type.equals("date") || type.equals("month")) {
      char[] tmpChar = (char[])null;
      tmpDateString = String.valueOf(para);
      tmpChar = tmpDateString.toCharArray();
      for (int i = 0; i < tmpChar.length; i++) {
        char nowChar = tmpChar[i];
        switch (nowChar) {
          case '1':
            if (i == 0 && para >= 10) {
              dateString.append("十");
              break;
            } 
            dateString.append("一");
            break;
          case '2':
            if (i == 0 && para >= 10) {
              dateString.append("二十");
              break;
            } 
            dateString.append("二");
            break;
          case '3':
            if (i == 0 && para >= 10) {
              dateString.append("三十");
              break;
            } 
            dateString.append("三");
            break;
          case '4':
            dateString.append("四");
            break;
          case '5':
            dateString.append("五");
            break;
          case '6':
            dateString.append("六");
            break;
          case '7':
            dateString.append("七");
            break;
          case '8':
            dateString.append("八");
            break;
          case '9':
            dateString.append("九");
            break;
        } 
      } 
    } 
    return dateString.toString();
  }
}
