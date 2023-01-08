package com.js.oa.workplan.service;

import com.js.oa.workplan.bean.WorkplanBean;
import com.js.oa.workplan.po.WorkplanPO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class WorkplanService {
  private static Logger logger = Logger.getLogger(WorkplanService.class.getName());
  
  public boolean saveWorkplan(List planList) {
    boolean result = false;
    WorkplanBean bean = new WorkplanBean();
    try {
      result = bean.saveWorkplan(planList);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean updateWorkplan(WorkplanPO po) {
    boolean result = false;
    WorkplanBean bean = new WorkplanBean();
    try {
      result = bean.updateWorkplan(po);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public WorkplanPO loadWorkplan(String workplanId) {
    WorkplanPO po = new WorkplanPO();
    try {
      po = (new WorkplanBean()).loadWorkplan(workplanId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return po;
  }
  
  public boolean deleteWorkplan(String workplanId) {
    boolean result = false;
    WorkplanBean bean = new WorkplanBean();
    try {
      result = bean.deleteWorkplan(workplanId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String checkData(String cdate, String leaderId) {
    String result = "";
    WorkplanBean bean = new WorkplanBean();
    try {
      result = bean.checkData(cdate, leaderId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String[] getLeaderInfoById(String leaderId) {
    return (new WorkplanBean()).getLeaderInfoById(leaderId);
  }
  
  public List searchWorkplanList(String groupId, String workYearWeek, List flist) {
    List list = new ArrayList();
    try {
      list = (new WorkplanBean()).searchWorkplanList(groupId, workYearWeek, flist);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
