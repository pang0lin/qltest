package com.js.util.job;

import java.util.Date;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class JobManager {
  public void task(String jobClass, Date date, int cycle) {
    StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
    Scheduler scheduler = null;
    try {
      scheduler = stdSchedulerFactory.getScheduler();
    } catch (SchedulerException ex) {
      ex.printStackTrace();
    } 
    long ctime = System.currentTimeMillis();
    Class<?> cls = null;
    try {
      cls = Class.forName(jobClass);
    } catch (ClassNotFoundException ex1) {
      ex1.printStackTrace();
    } 
    JobDetail jobDetail = new JobDetail("jobDetail2", "jobDetailGroup2", cls);
    CronTrigger cronTrigger = new CronTrigger("cronTrigger", "triggerGroup2");
    try {
      int year = date.getYear() + 1900;
      int month = date.getMonth() + 1;
      int day = date.getDate();
      int hour = date.getHours();
      int minute = date.getMinutes();
      int second = date.getSeconds();
      System.out.println(String.valueOf(year) + " " + month + " " + day + " " + hour + " " + minute + " " + second);
      CronExpression cexp = new CronExpression(String.valueOf(second) + "-" + cycle + " " + minute + " " + hour + " " + day + " " + month + " ? " + year);
      cronTrigger.setCronExpression(cexp);
      scheduler.scheduleJob(jobDetail, (Trigger)cronTrigger);
      scheduler.start();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void task(String jobClass, int cycle) {
    StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
    Scheduler scheduler = null;
    try {
      scheduler = stdSchedulerFactory.getScheduler();
    } catch (SchedulerException ex) {
      ex.printStackTrace();
    } 
    long ctime = System.currentTimeMillis();
    Class<?> cls = null;
    try {
      cls = Class.forName(jobClass);
    } catch (ClassNotFoundException ex1) {
      ex1.printStackTrace();
    } 
    JobDetail jobDetail = new JobDetail("jobDetail2", "jobDetailGroup2", cls);
    CronTrigger cronTrigger = new CronTrigger("cronTrigger", "triggerGroup2");
    try {
      CronExpression cexp = new CronExpression("0/" + (cycle - 1) + " * * * * ?");
      cronTrigger.setCronExpression(cexp);
      scheduler.scheduleJob(jobDetail, (Trigger)cronTrigger);
      scheduler.start();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void main(String[] args) {
    JobManager jb = new JobManager();
    jb.task("com.js.util.job.PressDealJob", 10);
  }
}
