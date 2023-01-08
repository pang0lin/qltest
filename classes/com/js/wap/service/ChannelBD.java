package com.js.wap.service;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.wap.bean.ChannelBean;
import java.util.Map;
import org.apache.log4j.Logger;

public class ChannelBD {
  private static Logger logger = Logger.getLogger(ChannelBD.class.getName());
  
  private ChannelBean bean = new ChannelBean();
  
  public Map getChannelList(String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getChannelList(userId, orgId, orgIdString, domainId, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public InformationPO getInfoPOByID(String infoID) throws Exception {
    return this.bean.getInfoPOByID(infoID);
  }
}
