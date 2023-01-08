package com.js.wap.service;

import com.js.system.manager.service.ManagerService;
import com.js.wap.bean.WapCommonBean;
import java.util.List;
import org.apache.log4j.Logger;

public class WapCommonService {
  private static Logger logger = Logger.getLogger(ManagerService.class.getName());
  
  private WapCommonBean mbean = null;
  
  public WapCommonService() {
    this.mbean = new WapCommonBean();
  }
  
  public List getOrgList(String domainId, String pId) throws Exception {
    List orgList = null;
    try {
      orgList = this.mbean.getOrgList(domainId, pId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
}
