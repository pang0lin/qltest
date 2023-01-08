package com.js.oa.weixin.common.service;

import com.js.oa.bbs.po.ForumPO;
import com.js.oa.weixin.common.bean.WeiXinBean;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class WeiXinBD {
  private static Logger logger = Logger.getLogger(WeiXinBD.class.getName());
  
  private WeiXinBean bean = new WeiXinBean();
  
  public Map getNewsList(String channel, String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited, String keyword) {
    Map map = null;
    try {
      map = this.bean.getNewsList(channel, userId, orgId, orgIdString, 
          domainId, beginIndex, limited, keyword);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
      e.printStackTrace();
    } 
    return map;
  }
  
  public Map getEventListByEmpId(String empId, String domainId, int beginIndex, int limited, String keyword) {
    Map map = null;
    try {
      map = this.bean.getEventListByEmpId(empId, domainId, beginIndex, limited, keyword);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getUnderEventList(String empsStr, String domainId, int beginIndex, int limited, String keyword) {
    Map map = null;
    try {
      map = this.bean.getUnderEventList(empsStr, domainId, beginIndex, limited, keyword);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getChannelList(String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited, String keyword) {
    Map map = null;
    try {
      map = this.bean.getChannelList(userId, orgId, orgIdString, domainId, beginIndex, limited, keyword);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getReportByDay(String hql, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getReportByDay(hql, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getReportByWeek(String hql, int beginIndex, int limited) {
    Map map = null;
    try {
      map = this.bean.getReportByWeek(hql, beginIndex, limited);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getMessageList(String userId, String orgId, String orgIdString, int beginIndex, int limited, String keyword, String from) throws Exception {
    Map map = null;
    try {
      map = this.bean.getMessageList(userId, orgId, orgIdString, beginIndex, limited, keyword, from);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
      throw e;
    } 
    return map;
  }
  
  public Map personalCuibanList(String userId, int beginIndex, int limited, String keyword, String pressStatus) throws Exception {
    Map map = null;
    try {
      map = this.bean.personalCuibanList(userId, beginIndex, limited, keyword, pressStatus);
    } catch (Exception e) {
      logger.error("Error  information:" + e.getMessage());
      throw e;
    } 
    return map;
  }
  
  public List getForumClassList(String whereScopePara, String userId, String domainId) throws Exception {
    return this.bean.getForumClassList(whereScopePara, userId, domainId);
  }
  
  public Map searchAttentionByUserid(String userId, String keyword, int beginIndex) throws Exception {
    return this.bean.searchAttentionByUserid(userId, keyword, beginIndex);
  }
  
  public List<ForumPO> searchByClassId(String fornmId) throws Exception {
    return this.bean.searchByClassId(fornmId);
  }
  
  public int updateReTime(String forumId) {
    return this.bean.updateReTime(forumId);
  }
  
  public Map getForumMap(String hql, int beginIndex, int limit) throws Exception {
    return this.bean.getForumList(hql, beginIndex, limit);
  }
  
  public void replyMessage(String userId, String userName, String toID, String content, String userNames) throws Exception {
    this.bean.replyMessage(userId, userName, toID, content, userNames);
  }
}
