package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqOutEJBBean;
import com.js.oa.hr.kq.po.KqOutPO;
import org.apache.log4j.Logger;

public class KqOutBD {
  private static Logger logger = Logger.getLogger(KqOutBD.class.getName());
  
  private KqOutEJBBean kqOutEJBBean = new KqOutEJBBean();
  
  public Long add(KqOutPO kqOutPO) throws Exception {
    return this.kqOutEJBBean.add(kqOutPO);
  }
  
  public void update(KqOutPO kqOutPO) throws Exception {
    this.kqOutEJBBean.update(kqOutPO);
  }
  
  public void del(long kqOutId) throws Exception {
    this.kqOutEJBBean.del(kqOutId);
  }
  
  public KqOutPO searchById(long kqOutId) throws Exception {
    KqOutPO kqOutPO = new KqOutPO();
    kqOutPO = this.kqOutEJBBean.searchById(kqOutId);
    return kqOutPO;
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqOutEJBBean.searchStat(begindate, enddate, userId));
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqOutEJBBean.searchStatOrg(begindate, enddate, orgId));
    return sta.intValue();
  }
}
