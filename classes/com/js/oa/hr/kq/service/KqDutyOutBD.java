package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqDutyOutEJBBean;
import com.js.oa.hr.kq.po.KqDutyOutPO;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class KqDutyOutBD {
  private static Logger logger = Logger.getLogger(KqDutyOutBD.class.getName());
  
  private KqDutyOutEJBBean kqDutySetEJBBean = new KqDutyOutEJBBean();
  
  public void add(KqDutyOutPO outPO) throws Exception {
    this.kqDutySetEJBBean.add(outPO);
  }
  
  public float[] getHour(String beginDate, String endDate, String userId) {
    float[] value = (float[])null;
    try {
      value = this.kqDutySetEJBBean.getHour(beginDate, endDate, userId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return value;
  }
  
  public float getHourByOrg(String beginDate, String endDate, String orgId, String flag) {
    float value = 0.0F;
    try {
      value = this.kqDutySetEJBBean.getHourByOrg(beginDate, endDate, orgId, flag);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    value = Math.round(value * 100.0F) / 100.0F;
    return value;
  }
  
  public int getDutyByOrg(String beginDate, String endDate, String orgId, String flag) {
    int value = 0;
    try {
      value = this.kqDutySetEJBBean.getDutyByOrg(beginDate, endDate, orgId, flag);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return value;
  }
  
  public KqDutyOutPO searchById(long kqDutySetId) throws Exception {
    KqDutyOutPO kqDutySetPO = new KqDutyOutPO();
    kqDutySetPO = this.kqDutySetEJBBean.searchById(kqDutySetId);
    return kqDutySetPO;
  }
  
  public List searchByEmpId(String beginDate, String endDate, String userId) {
    return this.kqDutySetEJBBean.searchByEmpId(beginDate, endDate, userId);
  }
  
  public int[] getDutyInfo(String searchBegindate, String searchEnddate, String empId) {
    return this.kqDutySetEJBBean.getDutyInfo(searchBegindate, searchEnddate, empId);
  }
  
  public Map getDutyByEmpId(String beginDate, String endDate, String userId) {
    return this.kqDutySetEJBBean.getDutyByEmpId(beginDate, endDate, userId);
  }
  
  public Map<String, String> getDutyMap(String beginDate, String endDate, String userId) {
    return this.kqDutySetEJBBean.getDutyMap(beginDate, endDate, userId);
  }
  
  public Map<String, String> getDutyShow(String beginDate, String endDate, String userId) {
    return this.kqDutySetEJBBean.getDutyShow(beginDate, endDate, userId);
  }
  
  public String dateShow(String sql) {
    return this.kqDutySetEJBBean.dateShow(sql);
  }
}
