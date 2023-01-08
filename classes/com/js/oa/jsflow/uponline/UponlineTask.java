package com.js.oa.jsflow.uponline;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class UponlineTask extends TimerTask {
  private int helpNum = 0;
  
  public void run() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      FileInputStream configFileInputStream = new FileInputStream(new File(filePath));
      Document doc = builder.build(configFileInputStream);
      Element node = doc.getRootElement().getChild("OAonlineuser");
      if (node != null) {
        String setTime = node.getChild("setTime").getAttribute("value").getValue();
        String OAonlineuserSwitch = node.getChild("OAonlineuserSwitch").getAttribute("value").getValue();
        String timeString = node.getChild("doTime").getAttribute("value").getValue();
        if ("1".equals(OAonlineuserSwitch)) {
          if (!"".equals(setTime))
            setTime = "1"; 
          if (!"".equals(setTime) && setTime != null) {
            int timeValue = Integer.parseInt(setTime);
            this.helpNum++;
            if ("".equals(timeString))
              timeString = "2"; 
            if (this.helpNum == timeValue) {
              updateOnline(timeString);
              this.helpNum = 0;
            } 
          } 
        } 
      } else {
        int timeValue = Integer.parseInt("1");
        this.helpNum++;
        if (this.helpNum == timeValue) {
          updateOnline("15");
          this.helpNum = 0;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void updateOnline(String timeString) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      long doTime = Long.parseLong(timeString);
      if (doTime < 2L)
        doTime = 2L; 
      Long datelong = Long.valueOf(System.currentTimeMillis() - doTime * 60000L);
      String dataType = SystemCommon.getDatabaseType();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String timeDate = dateFormat.format(new Date(datelong.longValue()));
      String sql = "";
      if (dataType.indexOf("mysql") >= 0) {
        sql = "delete from jsf_onlineuser where onlinebegintime<'" + timeDate + "'";
      } else {
        sql = "delete from jsf_onlineuser where onlinebegintime<to_date('" + timeDate + "','yyyy-mm-dd hh24:mi:ss')";
      } 
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
}
