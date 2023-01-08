package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqLeaveEJBBean;
import com.js.oa.hr.kq.po.KqLeavePO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class KqLeaveBD {
  private static Logger logger = Logger.getLogger(KqLeaveBD.class.getName());
  
  private KqLeaveEJBBean kqLeaveEJBBean = new KqLeaveEJBBean();
  
  public Long add(KqLeavePO kqLeavePO) throws Exception {
    return this.kqLeaveEJBBean.add(kqLeavePO);
  }
  
  public void update(KqLeavePO kqLeavePO) throws Exception {
    this.kqLeaveEJBBean.update(kqLeavePO);
  }
  
  public void del(long kqLeaveId) throws Exception {
    this.kqLeaveEJBBean.del(kqLeaveId);
  }
  
  public KqLeavePO searchById(long kqLeaveId) throws Exception {
    KqLeavePO kqLeavePO = new KqLeavePO();
    kqLeavePO = this.kqLeaveEJBBean.searchById(kqLeaveId);
    return kqLeavePO;
  }
  
  public List searchLeaveType(long domainId) throws Exception {
    List list = new ArrayList();
    list = this.kqLeaveEJBBean.searchLeaveType(domainId);
    return list;
  }
  
  public Map<Integer, String> searchLeaveTypeMap() throws Exception {
    Map<Integer, String> leaveTypeMap = new HashMap<Integer, String>();
    leaveTypeMap = this.kqLeaveEJBBean.searchLeaveTypeMap();
    return leaveTypeMap;
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqLeaveEJBBean.searchStat(begindate, enddate, userId));
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqLeaveEJBBean.searchStatOrg(begindate, enddate, orgId));
    return sta.intValue();
  }
}
