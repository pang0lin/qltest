package com.js.oa.routine.voiture.service;

import com.js.oa.routine.voiture.bean.VoitureSecondEJBHome;
import com.js.oa.routine.voiture.po.VoitureFeePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class VoitureManagerSecondBD {
  private static Logger logger = Logger.getLogger(VoitureManagerSecondBD.class
      .getName());
  
  public Integer voitureFeeAdd(VoitureFeePO po, Long voitureId) {
    Integer result = new Integer(1);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, VoitureFeePO.class);
      pg.put(voitureId, "Long");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      result = (Integer)ejbProxy.invoke("voitureFeeAdd", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveVoitureType information :" + e.getMessage());
      System.out.println("-------------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------------");
    } 
    return result;
  }
  
  public List listVoitureFeeDetail(Long voitureId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureId, "Long");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      list = (List)ejbProxy.invoke("listVoitureFeeDetail", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
  
  public void delVoitureFeeBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("delVoitureFeeBatch", pg.getParameters());
    } catch (Exception e) {
      System.out.println("----------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------");
    } 
  }
  
  public void delVoitureFee(Long voitureFeeId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      pg.put(voitureFeeId, "Long");
      ejbProxy.invoke("delVoitureFee", pg.getParameters());
    } catch (Exception e) {
      System.out.println("-------------------------------");
      e.printStackTrace();
      System.out.println("-------------------------------");
    } 
  }
  
  public List listModifyVoitureFee(Long voitureId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(voitureId, "Long");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      list = (List)ejbProxy.invoke("listModifyVoitureFee", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
  
  public Integer updateVoitureFee(VoitureFeePO paraPO, Long voitureId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Integer update_info = new Integer(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      pg.put(paraPO, VoitureFeePO.class);
      pg.put(voitureId, "Long");
      update_info = (Integer)ejbProxy.invoke("updateVoitureFee", pg.getParameters());
    } catch (Exception e) {
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } finally {}
    return update_info;
  }
}
