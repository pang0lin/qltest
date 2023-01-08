package com.js.oa.jsflow.util.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HtmlToPdfInterceptor extends Thread {
  private InputStream is = null;
  
  public HtmlToPdfInterceptor(InputStream is) {
    this.is = is;
  }
  
  public void run() {
    try {
      InputStreamReader isr = new InputStreamReader(this.is, "GBK");
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null)
        System.out.println(line.toString()); 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
