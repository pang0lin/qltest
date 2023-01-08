package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqOffsetEJBBean;
import com.js.oa.hr.kq.po.KqOffsetPO;
import org.apache.log4j.Logger;

public class KqOffsetBD {
  private static Logger logger = Logger.getLogger(KqSigntimeBD.class.getName());
  
  private KqOffsetEJBBean kqOffsetEJBBean = new KqOffsetEJBBean();
  
  public KqOffsetPO load(long domain_id) throws Exception {
    KqOffsetPO kqOffsetPO = new KqOffsetPO();
    kqOffsetPO = this.kqOffsetEJBBean.load(domain_id);
    return kqOffsetPO;
  }
  
  public void set(long id, int offsetTime, long domain_id) throws Exception {
    this.kqOffsetEJBBean.set(id, offsetTime, domain_id);
  }
}
