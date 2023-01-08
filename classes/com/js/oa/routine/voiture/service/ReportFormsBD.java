package com.js.oa.routine.voiture.service;

import com.js.oa.routine.voiture.bean.VoitureReportEJBHome;
import com.js.oa.routine.voiture.bean.VoitureSecondEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReportFormsBD {
  private static Logger logger = Logger.getLogger(ReportFormsBD.class
      .getName());
  
  public List listReportForms(String viewSQL, String fromSQL, String whereSQL) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewSQL, "String");
      pg.put(fromSQL, "String");
      pg.put(whereSQL, "String");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      list = (List)ejbProxy.invoke("listReportForms", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
  
  public List listVoitureInfo(String viewSQL, String fromSQL, String whereSQL) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewSQL, "String");
      pg.put(fromSQL, "String");
      pg.put(whereSQL, "String");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      list = (List)ejbProxy.invoke("listVoitureInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
  
  public List listVoitureSend(String viewSQL, String fromSQL, String whereSQL) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewSQL, "String");
      pg.put(fromSQL, "String");
      pg.put(whereSQL, "String");
      EJBProxy ejbProxy = new EJBProxy("VoitureSecondEJB", 
          "VoitureSecondEJBLocal", VoitureSecondEJBHome.class);
      list = (List)ejbProxy.invoke("listVoitureSend", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
  
  public List listReportForms2(String viewSQL, String fromSQL, String whereSQL) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewSQL, "String");
      pg.put(fromSQL, "String");
      pg.put(whereSQL, "String");
      EJBProxy ejbProxy = new EJBProxy("VoitureReportEJB", 
          "VoitureReportEJBLocal", VoitureReportEJBHome.class);
      list = (List)ejbProxy.invoke("listReportForms", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("------------------------------");
      e.printStackTrace();
      System.out.println("------------------------------");
    } 
    return list;
  }
}
