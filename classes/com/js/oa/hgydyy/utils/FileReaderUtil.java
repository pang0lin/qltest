package com.js.oa.hgydyy.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileReaderUtil {
  public static void main(String[] args) {
    System.out.println(readFileContentToString(new File("c:/OA-backup - D.bat")));
  }
  
  public static String readFileContentToString(File file) {
    String result = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String s = null;
      while ((s = br.readLine()) != null)
        result = String.valueOf(result) + "\n" + s; 
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
