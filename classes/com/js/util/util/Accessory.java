package com.js.util.util;

import java.io.File;

public class Accessory {
  public static void delAccessory(String path, String files) {
    try {
      String[] fileArray = files.split("\\|");
      for (int i = 0; i < fileArray.length; i++) {
        if (!"".equals(fileArray[i])) {
          String srcTr = "0000";
          if (fileArray[i] != null && fileArray[i].length() > 6 && fileArray[i].substring(4, 5).equals("_")) {
            srcTr = fileArray[i].substring(0, 4);
          } else {
            srcTr = "0000";
          } 
          path = path.replace("/upload/", "/upload/" + srcTr + "/");
          File theFile = new File(String.valueOf(path) + "\\" + fileArray[i]);
          if (theFile.exists())
            theFile.delete(); 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } 
  }
}
