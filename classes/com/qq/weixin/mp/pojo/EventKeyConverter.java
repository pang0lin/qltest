package com.qq.weixin.mp.pojo;

import org.apache.log4j.Logger;

public class EventKeyConverter {
  private static Logger log = Logger.getLogger(EventKeyConverter.class);
  
  private static String home;
  
  private static String[][] matrix;
  
  private static void initMatrix() {
    if (home == null || home.trim().length() == 0)
      home = String.valueOf(EventKey.HOME) + "?/mark="; 
    if (matrix == null || matrix.length == 0)
      matrix = new String[][] { { String.valueOf(home) + "1", EventKey.DEAL_WITH }, { String.valueOf(home) + "2", EventKey.ONLINE_CHECKIN }, { String.valueOf(home) + "3", EventKey.COOPERATE } }; 
  }
  
  public static String link2Mark(String link) {
    String mark = "";
    log.debug("link = " + link);
    initMatrix();
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i][1].equals(link)) {
        mark = matrix[i][0];
        break;
      } 
    } 
    return mark;
  }
  
  public static String mark2Link(String mark) {
    String link = "";
    log.debug("mark = " + mark);
    initMatrix();
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i][0].equals(mark)) {
        link = matrix[i][1];
        break;
      } 
    } 
    return link;
  }
}
