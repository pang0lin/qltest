package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqNosignEJBBean;
import com.js.oa.hr.kq.po.KqNosignPO;
import org.apache.log4j.Logger;

public class KqNosignBD {
  private static Logger logger = Logger.getLogger(KqSigntimeBD.class.getName());
  
  private KqNosignEJBBean kqNosignEJBBean = new KqNosignEJBBean();
  
  public Long add(KqNosignPO kqNosignPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    eveId = this.kqNosignEJBBean.add(kqNosignPO);
    return eveId;
  }
  
  public void update(KqNosignPO kqNosignPO) throws Exception {
    this.kqNosignEJBBean.update(kqNosignPO);
  }
  
  public void del(long id) throws Exception {
    this.kqNosignEJBBean.del(id);
  }
  
  public KqNosignPO searchById(long kqNosignId) throws Exception {
    KqNosignPO kqNosignPO = new KqNosignPO();
    kqNosignPO = this.kqNosignEJBBean.searchById(kqNosignId);
    return kqNosignPO;
  }
  
  public boolean isNosignByUser(long userId, int seq, String beginDate, String endDate) throws Exception {
    boolean re = false;
    re = this.kqNosignEJBBean.isNosignByUser(userId, seq, beginDate, endDate);
    return re;
  }
}
