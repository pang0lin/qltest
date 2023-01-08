package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqSigntimeEJBBean;
import com.js.oa.hr.kq.po.KqSigntimePO;
import org.apache.log4j.Logger;

public class KqSigntimeBD {
  private static Logger logger = Logger.getLogger(KqSigntimeBD.class.getName());
  
  private KqSigntimeEJBBean kqSigntimeEJBBean = new KqSigntimeEJBBean();
  
  public KqSigntimePO load(long domain_id) throws Exception {
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    kqSigntimePO = this.kqSigntimeEJBBean.load(domain_id);
    return kqSigntimePO;
  }
  
  public void set(long id, int offset, long domain_id) throws Exception {
    this.kqSigntimeEJBBean.set(id, offset, domain_id);
  }
}
