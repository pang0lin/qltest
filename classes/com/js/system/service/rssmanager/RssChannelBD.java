package com.js.system.service.rssmanager;

import com.js.system.bean.rssmanager.RssEJBHome;
import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.ChannelInfoVO;
import com.js.system.vo.rssmanager.ChannelOrderVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

public class RssChannelBD {
  private static Logger logger = Logger.getLogger(RssChannelBD.class.getName());
  
  public void delChannel(String channelId, String[] channelIds) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      pg.put(channelId, String.class);
      pg.put(channelIds, String[].class);
      ejbProxy.invoke("delChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to del Mail information:" + e.getMessage());
    } 
  }
  
  public void delAllChannel() {
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("delAllChannel", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to delAll Mail information:" + e.getMessage());
    } 
  }
  
  public CategoryChannelVO getSingleRssChannel(String channelId) {
    CategoryChannelVO rssChannelVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      rssChannelVO = (CategoryChannelVO)ejbProxy.invoke("getSingleRssChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return rssChannelVO;
  }
  
  public List getSingleRssChannelByName(String channelName) {
    return getSingleRssChannelByName(channelName, "");
  }
  
  public List getSingleRssChannelByName(String channelName, String channelId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      pg.put(channelName, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleRssChannelByName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getRssChannelList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getRssChannelList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void saveOrUpdateRssChannel(CategoryChannelVO rssChannelVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(rssChannelVO, CategoryChannelVO.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("saveOrUpdateRssChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void saveChannelInfo(ChannelInfoVO channelInfoVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(channelInfoVO, ChannelInfoVO.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("saveChannelInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void delChannelInfo() {
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("delChannelInfo", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void saveChannelItem(Collection entities) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(entities, Collection.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("saveChannelItem", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void delChannelItem(String channelId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("delChannelItem", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void orderRss(ChannelOrderVO cov) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(cov, ChannelOrderVO.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("orderRss", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public List getMyRssList(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = null;
    try {
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getMyRssList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public List getChannelItemList(String channelId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List list = null;
    try {
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getChannelItemList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public void updateItemReadState(String itemId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(itemId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("updateItemReadState", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void removeRssOrder(String channelId, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      pg.put(channelId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("removeRssOrder", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
}
