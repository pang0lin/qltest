package com.js.oa.hr.kq.service;

import com.js.oa.hr.kq.bean.KqCheckinInfoBean;
import com.js.oa.hr.kq.po.KqCheckinInfoPO;
import java.util.List;

public class KqCheckinInfoBD {
  private KqCheckinInfoBean KqCheckinInfoBean = new KqCheckinInfoBean();
  
  public List<KqCheckinInfoPO> showInfoByQuery(String sql, int pageSize, int firstRow) {
    return this.KqCheckinInfoBean.showInfoByQuery(sql, pageSize, firstRow);
  }
}
