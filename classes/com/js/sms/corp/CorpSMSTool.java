package com.js.sms.corp;

import java.util.ResourceBundle;

public class CorpSMSTool {
  public static int CORPSTATE;
  
  public static int CORPMARKER;
  
  public static void getPropertise() {
    ResourceBundle rb = ResourceBundle.getBundle("corpsms");
    CORPSTATE = Integer.parseInt(rb.getString("corpstate"));
    CORPMARKER = Integer.parseInt(rb.getString("corpmarker"));
  }
}
