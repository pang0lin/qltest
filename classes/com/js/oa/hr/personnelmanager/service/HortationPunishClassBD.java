package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.personnelmanager.bean.HortationPunishEJBHome;
import com.js.oa.hr.personnelmanager.po.HortationPunishPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class HortationPunishClassBD {
  private static Logger logger = Logger.getLogger(HortationPunishClassBD.class.getName());
  
  public int add(DutyPO dutyPO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(dutyPO, DutyPO.class);
      addResult = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return addResult;
  }
  
  public boolean del(String[] id) {
    boolean delResult = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return delResult;
  }
  
  public List getList(String domainId) {
    ArrayList alist = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getList", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return alist;
  }
  
  public DutyPO getSingle(String dutyId) {
    DutyPO dutyPO = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(dutyId, String.class);
      dutyPO = (DutyPO)ejbProxy.invoke("getSingle", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return dutyPO;
  }
  
  public int update(DutyPO dutyPO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(dutyPO, DutyPO.class);
      addResult = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return addResult;
  }
  
  public PostTitlePO getSinglePost(String postTitleId) {
    PostTitlePO postPO = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitleId, String.class);
      postPO = (PostTitlePO)ejbProxy.invoke("getSinglePost", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return postPO;
  }
  
  public int updatePost(PostTitlePO postTitlePO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitlePO, PostTitlePO.class);
      addResult = ((Integer)ejbProxy.invoke("updatePost", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return addResult;
  }
  
  public Boolean saveHortationPunish(HortationPunishPO hortationPunishnPO) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(hortationPunishnPO, HortationPunishPO.class);
      success = (Boolean)ejbProxy.invoke("saveHortationPunish", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public Boolean deleteHortationPunish(String ids) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      success = (Boolean)ejbProxy.invoke("deleteHortationPunish", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public HortationPunishPO getSingleHortationPunish(Long hortationPunishId) {
    HortationPunishPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(hortationPunishId, Long.class);
      po = (HortationPunishPO)ejbProxy.invoke("getSingleHortationPunish", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return po;
  }
  
  public Boolean updateHortationPunish(HortationPunishPO hortationPunishPO, Long id) {
    Boolean success = new Boolean(true);
    try {
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(hortationPunishPO, HortationPunishPO.class);
      pg.put(id, Long.class);
      success = (Boolean)ejbProxy.invoke("updateHortationPunish", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public List getHortationPunishList(String domain) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domain, String.class);
      EJBProxy ejbProxy = new EJBProxy("HortationPunishEJB", "HortationPunishEJBLocal", HortationPunishEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getHortationPunishList", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return alist;
  }
}
