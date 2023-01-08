package com.js.message.lava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RtxGK {
  private String type = "";
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public RtxGK() {
    try {
      Properties prop = new Properties();
      FileInputStream fil = new FileInputStream("type.properties");
      prop.load(fil);
      this.type = prop.getProperty("type");
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public boolean Sync() {
    boolean flag = false;
    if (!this.type.equals("rtx"))
      if (this.type.equals("lava-lava")) {
        GKSync gkSync = new GKSync();
        flag = gkSync.Sync();
      }  
    return flag;
  }
  
  public static void main(String[] args) {}
}
