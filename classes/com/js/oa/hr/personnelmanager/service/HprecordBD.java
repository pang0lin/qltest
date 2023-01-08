package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.HprecordEJBHome;
import com.js.oa.hr.personnelmanager.po.HprecordPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class HprecordBD {
  private static Logger logger = Logger.getLogger(HprecordBD.class.getName());
  
  public boolean addHprecord(HprecordPO hprecordPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      pg.put(hprecordPO, HprecordPO.class);
      ejbProxy.invoke("addHprecord", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addHprecordBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteHprecord(Long hprecordId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      pg.put(hprecordId, Long.class);
      ejbProxy.invoke("deleteHprecord", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteHprecordBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchHprecord(String hprecordIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      pg.put(hprecordIds, String.class);
      ejbProxy.invoke("deleteBatchHprecord", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchHprecordBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public HprecordPO selectHprecordView(Long hprecordId) {
    HprecordPO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      pg.put(hprecordId, Long.class);
      result = (HprecordPO)ejbProxy.invoke(
          "selectHprecordView", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectHprecordViewBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateHprecord(HprecordPO hprecordPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      pg.put(hprecordPO, HprecordPO.class);
      ejbProxy.invoke("updateHprecord", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("updateHprecordBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectHpName(String domain) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domain, String.class);
      EJBProxy ejbProxy = new EJBProxy("HprecordEJB", 
          "HprecordEJBLocal", HprecordEJBHome.class);
      result = (ArrayList)ejbProxy.invoke("selectHpName", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectHpNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
}
