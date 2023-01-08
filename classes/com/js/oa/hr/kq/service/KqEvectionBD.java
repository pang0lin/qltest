package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqEvectionEJBBean;
import com.js.oa.hr.kq.po.KqEvectionPO;
import org.apache.log4j.Logger;

public class KqEvectionBD {
  private static Logger logger = Logger.getLogger(KqEvectionBD.class.getName());
  
  private KqEvectionEJBBean kqEvectionEJBBean = new KqEvectionEJBBean();
  
  public Long add(KqEvectionPO kqEvectionPO) throws Exception {
    return this.kqEvectionEJBBean.add(kqEvectionPO);
  }
  
  public void update(KqEvectionPO kqEvectionPO) throws Exception {
    this.kqEvectionEJBBean.update(kqEvectionPO);
  }
  
  public void del(long kqEvectionId) throws Exception {
    this.kqEvectionEJBBean.del(kqEvectionId);
  }
  
  public KqEvectionPO searchById(long kqEvectionId) throws Exception {
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    kqEvectionPO = this.kqEvectionEJBBean.searchById(kqEvectionId);
    return kqEvectionPO;
  }
  
  public void isreturn(long kqEvectionId) throws Exception {
    this.kqEvectionEJBBean.isreturn(kqEvectionId);
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqEvectionEJBBean.searchStat(begindate, enddate, userId));
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqEvectionEJBBean.searchStatOrg(begindate, enddate, orgId));
    return sta.intValue();
  }
}
