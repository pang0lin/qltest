package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqOvertimeEJBBean;
import com.js.oa.hr.kq.po.KqOvertimePO;
import com.js.oa.hr.kq.po.KqOvertimeUserPO;
import org.apache.log4j.Logger;

public class KqOvertimeBD {
  private static Logger logger = Logger.getLogger(KqOvertimeBD.class.getName());
  
  private KqOvertimeEJBBean kqOvertimeEJBBean = new KqOvertimeEJBBean();
  
  public Long add(KqOvertimePO kqOvertimePO) throws Exception {
    return this.kqOvertimeEJBBean.add(kqOvertimePO);
  }
  
  public void update(KqOvertimePO kqOvertimePO) throws Exception {
    this.kqOvertimeEJBBean.update(kqOvertimePO);
  }
  
  public void del(long kqOvertimeId) throws Exception {
    this.kqOvertimeEJBBean.del(kqOvertimeId);
  }
  
  public KqOvertimePO searchById(long kqOvertimeId) throws Exception {
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    kqOvertimePO = this.kqOvertimeEJBBean.searchById(kqOvertimeId);
    return kqOvertimePO;
  }
  
  public int searchStat(String begindate, String enddate, long userId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqOvertimeEJBBean.searchStat(begindate, enddate, userId));
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqOvertimeEJBBean.searchStatOrg(begindate, enddate, orgId));
    return sta.intValue();
  }
  
  public Long addKqOvertimeUser(KqOvertimeUserPO kqOvertimeUserPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    eveId = this.kqOvertimeEJBBean.addKqOvertimeUser(kqOvertimeUserPO);
    return eveId;
  }
}
