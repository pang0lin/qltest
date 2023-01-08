package com.js.oa.personalwork.netemp.service;

import com.js.oa.personalwork.netemp.bean.NetEmpEJBBean;
import com.js.oa.personalwork.netemp.po.NetEmpPO;
import java.util.Map;
import org.apache.log4j.Logger;

public class NetEmpBD {
  private static Logger logger = Logger.getLogger(NetEmpBD.class.getName());
  
  NetEmpEJBBean netEmpEJBBean = new NetEmpEJBBean();
  
  public void update(NetEmpPO netEmpPO) throws Exception {
    this.netEmpEJBBean.update(netEmpPO);
  }
  
  public NetEmpPO load(long userId) throws Exception {
    NetEmpPO netEmpPO = new NetEmpPO();
    netEmpPO = this.netEmpEJBBean.load(userId);
    return netEmpPO;
  }
  
  public Map getAllRelationEmp(String userId) {
    return this.netEmpEJBBean.getAllRelationEmp(userId);
  }
}
