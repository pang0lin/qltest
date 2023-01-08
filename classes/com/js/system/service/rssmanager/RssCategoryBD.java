package com.js.system.service.rssmanager;

import com.js.system.bean.rssmanager.RssEJBHome;
import com.js.system.vo.rssmanager.CategoryVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class RssCategoryBD {
  private static Logger logger = Logger.getLogger(RssCategoryBD.class.getName());
  
  public void delCategory(String categoryId, String[] categoryIds) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      pg.put(categoryId, String.class);
      pg.put(categoryIds, String[].class);
      ejbProxy.invoke("delCategory", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to del Mail information:" + e.getMessage());
    } 
  }
  
  public void delAllCategory() {
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("delAllCategory", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to delAll Mail information:" + e.getMessage());
    } 
  }
  
  public CategoryVO getSingleRssCategory(String categoryId) {
    CategoryVO rssVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(categoryId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      rssVO = (CategoryVO)ejbProxy.invoke("getSingleRssCategory", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return rssVO;
  }
  
  public List getSingleRssCategoryByName(String name) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(name, String.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleRssCategoryByName", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getRssCategoryList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      list = (List)ejbProxy.invoke("getRssCategoryList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void saveOrUpdateRssCategory(CategoryVO rssVO) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(rssVO, CategoryVO.class);
      EJBProxy ejbProxy = new EJBProxy("RssEJB", "RssEJBLocal", RssEJBHome.class);
      ejbProxy.invoke("saveOrUpdateRssCategory", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
}
