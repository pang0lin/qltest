package com.js.oa.hr.officemanager.service;

import com.js.oa.hr.officemanager.bean.DutyEJBBean;
import com.js.oa.hr.officemanager.bean.DutyEJBHome;
import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class DutyBD {
  private static Logger logger = Logger.getLogger(DutyBD.class.getName());
  
  public int add(DutyPO dutyPO) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("DutyEJB", 
          "DutyEJBLocal", DutyEJBHome.class);
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
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("DutyEJB", "DutyEJBLocal", DutyEJBHome.class);
      pg.put(id, String[].class);
      delResult = ((Boolean)ejbProxy.invoke("del", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("Error to del Duty information:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public List select(String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DutyEJB", 
          "DutyEJBLocal", DutyEJBHome.class);
      list = (List)ejbProxy.invoke("select", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Duty information:" + 
          e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean validateByName(String name) throws Exception {
    boolean result = false;
    DutyEJBBean dutyEJBBean = new DutyEJBBean();
    result = dutyEJBBean.validateByName(name);
    return result;
  }
  
  public List getDuteList(String domainId) throws Exception {
    return getDuteList(domainId, "");
  }
  
  public List getDuteList(String domainId, String corpId) throws Exception {
    List list = null;
    try {
      list = (new DutyEJBBean()).getDuteList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
}
