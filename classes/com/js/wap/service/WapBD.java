package com.js.wap.service;

import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.wap.bean.WapBean;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class WapBD {
  private static Logger logger = Logger.getLogger(WapBD.class.getName());
  
  private WapBean bean = new WapBean();
  
  public Map getCoopListByEmpId(String empId, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getCoopListByEmpId(empId, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getCoopInfoByEmpId(String empId) {
    Map map = null;
    try {
      map = this.bean.getCoopInfoByEmpId(empId);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getEventListByEmpId(String empId, String domainId, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getEventListByEmpId(empId, domainId, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getUnderEventList(String empsStr, String domainId, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getUnderEventList(empsStr, domainId, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public List getProjectListByRangeParam(String para, String from, String where) {
    List list = null;
    try {
      list = this.bean.getProjectListByRangeParam(para, from, where);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return list;
  }
  
  public Map getMeetListByRangeParam(String para, String from, String where, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getMeetListByRangeParam(para, from, where, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public List getMeetInfoById(String para) {
    List list = null;
    try {
      list = this.bean.getMeetInfoById(para);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return list;
  }
  
  public Map getReportByDay(String hql, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getReportByDay(hql, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getReportByWeek(String hql, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getReportByWeek(hql, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getReportByMonth(String hql, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getReportByMonth(hql, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getNewsList(String channel, String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getNewsList(channel, userId, orgId, orgIdString, domainId, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map getReportContentByWeek(String userId, String orgId, String orgIdString, String domainId, String reportType, String id) {
    Map map = null;
    try {
      map = this.bean.getReportContentByWeek(userId, orgId, orgIdString, domainId, reportType, id);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public WorkReportPO getReportById(String userId, Long domainId, String id) {
    WorkReportPO wrpo = null;
    try {
      wrpo = this.bean.getReportById(userId, domainId, id);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return wrpo;
  }
}
