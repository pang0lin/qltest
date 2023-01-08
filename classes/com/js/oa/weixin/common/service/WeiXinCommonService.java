package com.js.oa.weixin.common.service;

import com.js.oa.weixin.common.bean.WeiXinCommonBean;
import com.js.system.manager.service.ManagerService;
import java.util.List;
import org.apache.log4j.Logger;

public class WeiXinCommonService {
  private static Logger logger = Logger.getLogger(ManagerService.class.getName());
  
  private WeiXinCommonBean mbean = null;
  
  public WeiXinCommonService() {
    this.mbean = new WeiXinCommonBean();
  }
  
  public List getOrgList(String domainId, String range, String sele, String pId) throws Exception {
    List orgList = null;
    WeiXinCommonBean bean = new WeiXinCommonBean();
    try {
      orgList = bean.getOrgList(domainId, range, sele, pId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getPrivateList(String userId, String domainId) {
    List orgList = null;
    try {
      orgList = this.mbean.getPrivateList(userId, domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getPublicList(String userId, String domainId) throws Exception {
    List orgList = null;
    try {
      orgList = this.mbean.getPublicList(userId, domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
}
