package com.js.system.service.rssmanager;

import com.js.system.bean.rssmanager.RssEJBHome;
import com.js.system.vo.rssmanager.ItemStateVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class RssItemBD {
  private static Logger logger = Logger.getLogger(RssItemBD.class.getName());
  
  public List getReadedItemList(String userId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getReadedItemList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void saveOrUpdateItem(ItemStateVO itemVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(itemVO, ItemStateVO.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("saveOrUpdateItem", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
}
