package com.js.oa.hr.officemanager.service;

import com.js.oa.hr.officemanager.bean.PostTitleEJBHome;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class PostTitleBD {
  private static Logger logger = Logger.getLogger(PostTitleBD.class.getName());
  
  public int add(PostTitlePO postTitlePO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("PostTitleEJB", 
          "PostTitleEJBLocal", PostTitleEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitlePO, PostTitlePO.class);
      addResult = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return addResult;
  }
  
  public boolean del(String[] id) {
    boolean delResult = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PostTitleEJB", "PostTitleEJBLocal", PostTitleEJBHome.class);
      pg.put(id, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("Error to del PostTitle information:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public List select() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PostTitleEJB", 
          "PostTitleEJBLocal", PostTitleEJBHome.class);
      list = (List)ejbProxy.invoke("select", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select PostTitle information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public List getPostTitle(String postTitleSeries) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PostTitleEJB", "PostTitleEJBLocal", PostTitleEJBHome.class);
      pg.put(postTitleSeries, String.class);
      list = (List)ejbProxy.invoke("getPostTitle", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select PostTitle information:" + 
          e.getMessage());
    } 
    return list;
  }
}
