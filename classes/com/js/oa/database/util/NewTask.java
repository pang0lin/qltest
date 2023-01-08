package com.js.oa.database.util;

import com.js.oa.hr.subsidiarywork.service.DesktopBD;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.dom4j.DocumentException;

public class NewTask extends TimerTask {
  private static boolean isRunning = false;
  
  private static int t = 3;
  
  private CustomTask ct = TaskFactory.getTask();
  
  public void run() {
    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    DesktopBD desktopBD = new DesktopBD();
    desktopBD.executeWishRemind();
    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    if (!isRunning) {
      if (this.ct != null) {
        isRunning = true;
        XmlUtil xmlUtil = new XmlUtil();
        String type = "";
        String maxdate = "";
        try {
          type = xmlUtil.searchBackupType();
        } catch (DocumentException documentException) {}
        try {
          maxdate = xmlUtil.searchLastBackupDate();
        } catch (DocumentException e) {
          e.printStackTrace();
        } 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();
        if (maxdate.equals("")) {
          try {
            this.ct.execute();
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } else if (type.equals("0")) {
          try {
            Date parse1 = formatter.parse(maxdate);
            Date parse3 = formatter.parse(date);
            if (parse1.before(parse3))
              this.ct.execute(); 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } else if (type.equals("1")) {
          try {
            Date parse1 = formatter.parse(maxdate);
            calendar.setTime(parse1);
            calendar.add(3, 1);
            Date parse2 = calendar.getTime();
            Date parse3 = formatter.parse(date);
            if (parse2.before(parse3) || parse2.equals(parse3))
              this.ct.execute(); 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } else {
          try {
            Date parse1 = formatter.parse(maxdate);
            calendar.setTime(parse1);
            calendar.add(2, 1);
            Date parse2 = calendar.getTime();
            Date parse3 = formatter.parse(date);
            if (parse2.before(parse3) || parse2.equals(parse3))
              this.ct.execute(); 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
        isRunning = false;
      } else {
        if (t == 0)
          return; 
        t--;
        System.out.println();
        System.out.println("[Error] [com.xxh.autoTask.NewTask]  The   task   is   null.");
        System.out.println("[Error] [com.xxh.autoTask.NewTask]  The   task   is   null.");
        System.out.println("[Error] [com.xxh.autoTask.NewTask]  The   task   is   null.");
      } 
    } else {
      System.out.println("The   task   is   running.");
    } 
  }
}
