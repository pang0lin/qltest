package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqRecordEJBBean;
import com.js.oa.hr.kq.po.KqRecordPO;
import org.apache.log4j.Logger;

public class KqRecordBD {
  private static Logger logger = Logger.getLogger(KqRecordBD.class.getName());
  
  private KqRecordEJBBean kqRecordEJBBean = new KqRecordEJBBean();
  
  public void add(KqRecordPO kqRecordPO) throws Exception {
    this.kqRecordEJBBean.add(kqRecordPO);
  }
  
  public boolean seachByDate(long userId, String beginDate, String endDate) throws Exception {
    boolean re = false;
    re = this.kqRecordEJBBean.seachByDate(userId, beginDate, endDate);
    return re;
  }
  
  public KqRecordPO seachKqRecordByDate(long userId, String beginDate, String endDate) throws Exception {
    KqRecordPO kqRecordPO = new KqRecordPO();
    kqRecordPO = this.kqRecordEJBBean.seachKqRecordByDate(userId, beginDate, endDate);
    return kqRecordPO;
  }
  
  public KqRecordPO getKqRecordById(long kqRecordId) throws Exception {
    KqRecordPO kqRecordPO = new KqRecordPO();
    kqRecordPO = this.kqRecordEJBBean.getKqRecordById(kqRecordId);
    return kqRecordPO;
  }
  
  public void update(KqRecordPO kqRecordPO) throws Exception {
    this.kqRecordEJBBean.update(kqRecordPO);
  }
  
  public int searchStat(String begindate, String enddate, long userId, int type, long domain_id) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqRecordEJBBean.searchStat(begindate, enddate, userId, type, domain_id));
    return sta.intValue();
  }
  
  public int searchStatOrg(String begindate, String enddate, long orgId, int type, long domain_id) throws Exception {
    Integer sta = Integer.valueOf(0);
    sta = Integer.valueOf(this.kqRecordEJBBean.searchStatOrg(begindate, enddate, orgId, type, domain_id));
    return sta.intValue();
  }
}
