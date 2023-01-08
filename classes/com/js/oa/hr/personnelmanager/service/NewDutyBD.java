package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.bean.NewDutyEJBBean;
import com.js.oa.hr.personnelmanager.bean.NewDutyEJBHome;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewDutyBD {
  private static Logger logger = Logger.getLogger(NewDutyBD.class.getName());
  
  public int add(DutyPO dutyPO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return delResult;
  }
  
  public List getList(String domainId) {
    return getList(domainId, "");
  }
  
  public List getList(String domainId, String corpId) {
    List alist = new ArrayList();
    try {
      alist = (new NewDutyEJBBean()).getList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return alist;
  }
  
  public DutyPO getSingle(String dutyId) {
    DutyPO dutyPO = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitlePO, PostTitlePO.class);
      addResult = ((Integer)ejbProxy.invoke("updatePost", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return addResult;
  }
  
  public String saveStation(StationPO stationPO) {
    String result = "success";
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stationPO, StationPO.class);
      result = (String)ejbProxy.invoke("saveStation", pg.getParameters());
      (new EmployeeBD()).resetPositionMap();
    } catch (Exception e) {
      logger.info(e);
    } 
    return result;
  }
  
  public Boolean deleteStation(String ids) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      success = (Boolean)ejbProxy.invoke("deleteStation", pg.getParameters());
      (new EmployeeBD()).resetPositionMap();
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public String[] getSingleStation(String stationId) {
    String[] station = { "", "" };
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stationId, String.class);
      station = (String[])ejbProxy.invoke("getSingleStation", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return station;
  }
  
  public String[] getSingleStationByName(String stationName) {
    String[] station = { "", "" };
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stationName, String.class);
      station = (String[])ejbProxy.invoke("getSingleStationByName", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return station;
  }
  
  public String updateStation(StationPO stationPO) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stationPO, StationPO.class);
      result = (String)ejbProxy.invoke("updateStation", pg.getParameters());
      (new EmployeeBD()).resetPositionMap();
    } catch (Exception e) {
      logger.info(e);
    } 
    return result;
  }
  
  public List getStationList(String domainId) {
    return getStationList(domainId, "");
  }
  
  public List getStationList(String domainId, String corpId) {
    List alist = new ArrayList();
    try {
      alist = (new NewDutyEJBBean()).getStationList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return alist;
  }
  
  public Long getDutyID(String dutyName, Long domainId) {
    Long id = new Long(0L);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(dutyName, String.class);
      pg.put(domainId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      id = (Long)ejbProxy.invoke("getDutyID", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return id;
  }
  
  public OrganizationVO getOrgPO(Long id) {
    OrganizationVO vo = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("NewDutyEJB", "NewDutyEJBLocal", NewDutyEJBHome.class);
      vo = (OrganizationVO)ejbProxy.invoke("getOrgPO", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return vo;
  }
}
