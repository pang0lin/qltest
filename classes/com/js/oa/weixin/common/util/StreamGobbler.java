package com.js.oa.weixin.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamGobbler extends Thread {
  InputStream is = null;
  
  String type;
  
  OutputStream os = null;
  
  public StreamGobbler(InputStream is, String type) {
    this(is, type, null);
  }
  
  public StreamGobbler(InputStream is, String type, OutputStream os) {
    this.is = is;
    this.type = type;
    this.os = os;
  }
  
  public void run() {
    InputStreamReader isr = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      if (this.os != null)
        pw = new PrintWriter(this.os); 
      isr = new InputStreamReader(this.is);
      br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        if (pw != null)
          pw.println(line); 
      } 
      if (pw != null)
        pw.flush(); 
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (pw != null)
          pw.close(); 
        if (br != null)
          br.close(); 
        if (isr != null)
          isr.close(); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
}
