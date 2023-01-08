package com.js.oa.scheme.taskcenter.service;

import com.js.oa.scheme.taskcenter.bean.TaskPressEJBBean;
import com.js.oa.scheme.taskcenter.po.TaskPO;
import com.js.oa.scheme.taskcenter.po.TaskPressPO;
import java.util.ArrayList;
import java.util.List;

public class TaskPressBD {
  public TaskPO getTaskPO(Long taskId) {
    TaskPO po = null;
    try {
      po = (new TaskPressEJBBean()).getTaskPO(taskId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public Long saveTaskPressPO(TaskPressPO po) {
    Long id = Long.valueOf(0L);
    try {
      id = (new TaskPressEJBBean()).savePO(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return id;
  }
  
  public List<TaskPressPO> getTaskPressPOList(String taskId) {
    String hql = "from com.js.oa.scheme.taskcenter.po.TaskPressPO po where po.taskId=" + taskId + " order by po.pressDate";
    List<TaskPressPO> list = new ArrayList<TaskPressPO>();
    try {
      list = (new TaskPressEJBBean()).getTaskPressPOList(hql);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
