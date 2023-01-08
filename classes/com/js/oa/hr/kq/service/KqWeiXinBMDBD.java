package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqWeiXinBMDEJBBean;
import com.js.oa.hr.kq.po.KqWeiXinBMDPO;
import org.apache.log4j.Logger;

public class KqWeiXinBMDBD {
  private static Logger logger = Logger.getLogger(KqSigntimeBD.class.getName());
  
  private KqWeiXinBMDEJBBean kqWeiXinBMDEJBBean = new KqWeiXinBMDEJBBean();
  
  public Long add(KqWeiXinBMDPO kqWeiXinBMDPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    eveId = this.kqWeiXinBMDEJBBean.add(kqWeiXinBMDPO);
    return eveId;
  }
  
  public void update(KqWeiXinBMDPO kqWeiXinBMDPO) throws Exception {
    this.kqWeiXinBMDEJBBean.update(kqWeiXinBMDPO);
  }
  
  public void del(long id) throws Exception {
    this.kqWeiXinBMDEJBBean.del(id);
  }
  
  public KqWeiXinBMDPO searchById(long KqWeiXinBMDId) throws Exception {
    KqWeiXinBMDPO kqWeiXinBMDPO = new KqWeiXinBMDPO();
    kqWeiXinBMDPO = this.kqWeiXinBMDEJBBean.searchById(KqWeiXinBMDId);
    return kqWeiXinBMDPO;
  }
  
  public boolean searchByDate(String date) throws Exception {
    boolean re = false;
    re = this.kqWeiXinBMDEJBBean.searchByDate(date);
    return re;
  }
}
