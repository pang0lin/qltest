package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqDutySetEJBBean;
import com.js.oa.hr.kq.po.KqDutySetPO;
import org.apache.log4j.Logger;

public class KqDutySetBD {
  private static Logger logger = Logger.getLogger(KqDutySetBD.class.getName());
  
  private KqDutySetEJBBean kqDutySetEJBBean = new KqDutySetEJBBean();
  
  public void add(KqDutySetPO kqDutySetPO) throws Exception {
    this.kqDutySetEJBBean.add(kqDutySetPO);
  }
  
  public void update(KqDutySetPO kqDutySetPO) throws Exception {
    this.kqDutySetEJBBean.update(kqDutySetPO);
  }
  
  public void del(long id) throws Exception {
    this.kqDutySetEJBBean.del(id);
  }
  
  public KqDutySetPO searchById(long kqDutySetId) throws Exception {
    KqDutySetPO kqDutySetPO = new KqDutySetPO();
    kqDutySetPO = this.kqDutySetEJBBean.searchById(kqDutySetId);
    return kqDutySetPO;
  }
  
  public KqDutySetPO searchByUserId(long userId) throws Exception {
    KqDutySetPO kqDutySetPO = new KqDutySetPO();
    kqDutySetPO = this.kqDutySetEJBBean.searchByUserId(userId);
    return kqDutySetPO;
  }
}
