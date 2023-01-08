package com.js.util.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class PdfToHtmlUtil extends Thread {
  InputStream is = null;
  
  String type;
  
  OutputStream os = null;
  
  PdfToHtmlUtil(InputStream is, String type) {
    this(is, type, null);
  }
  
  PdfToHtmlUtil(InputStream is, String type, OutputStream redirect) {
    this.is = is;
    this.type = type;
    this.os = redirect;
  }
  
  public void run() {
    InputStreamReader isr = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    String tmp = "";
    String[] tmps = (String[])null;
    try {
      if (this.os != null)
        pw = new PrintWriter(this.os); 
      isr = new InputStreamReader(this.is);
      br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        if (pw != null)
          pw.println(line); 
        if (line.indexOf("Preprocessing:") > -1) {
          tmp = line.substring(15).trim();
          if (tmp.indexOf("/") > -1) {
            tmps = tmp.split("/");
            if (tmps.length == 2 && !tmps[0].equals("0"))
              System.out.println("正在预转换准备中：" + String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tmps[0]) * 100.0D / Double.parseDouble(tmps[1])) }) + "%"); 
            continue;
          } 
          System.out.println("正在预转换准备中");
          continue;
        } 
        if (line.indexOf("Working:") > -1) {
          tmp = line.substring(8).trim();
          if (tmp.indexOf("/") > -1) {
            tmps = tmp.split("/");
            if (tmps.length == 2 && !tmps[0].equals("0"))
              System.out.println("正在格式转换中:" + String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tmps[0]) * 100.0D / Double.parseDouble(tmps[1])) }) + "%"); 
            continue;
          } 
          System.out.println("正在格式转换中");
        } 
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
