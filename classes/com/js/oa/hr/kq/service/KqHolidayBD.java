package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqHolidayEJBBean;
import com.js.oa.hr.kq.po.KqHolidayPO;
import java.util.List;
import org.apache.log4j.Logger;

public class KqHolidayBD {
  private static Logger logger = Logger.getLogger(KqSigntimeBD.class.getName());
  
  private KqHolidayEJBBean kqHolidayEJBBean = new KqHolidayEJBBean();
  
  public Long add(KqHolidayPO kqHolidayPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    eveId = this.kqHolidayEJBBean.add(kqHolidayPO);
    return eveId;
  }
  
  public void update(KqHolidayPO kqHolidayPO) throws Exception {
    this.kqHolidayEJBBean.update(kqHolidayPO);
  }
  
  public void del(long id) throws Exception {
    this.kqHolidayEJBBean.del(id);
  }
  
  public KqHolidayPO searchById(long KqHolidayId) throws Exception {
    KqHolidayPO kqHolidayPO = new KqHolidayPO();
    kqHolidayPO = this.kqHolidayEJBBean.searchById(KqHolidayId);
    return kqHolidayPO;
  }
  
  public boolean searchByDate(String date) throws Exception {
    boolean re = false;
    re = this.kqHolidayEJBBean.searchByDate(date);
    return re;
  }
  
  public List getPaibanList() {
    try {
      return this.kqHolidayEJBBean.getPaibanList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
