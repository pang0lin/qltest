package com.js.oa.hr.examination.service;

import com.js.oa.hr.examination.bean.ExaminationManageEJBHome;
import com.js.oa.hr.examination.po.ExaminationAnswerItemPO;
import com.js.oa.hr.examination.po.ExaminationAnswerPO;
import com.js.oa.hr.examination.po.ExaminationManagePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class ExaminationManageBD {
  private static Logger logger = Logger.getLogger(ExaminationManageBD.class
      .getName());
  
  public Long save(ExaminationManagePO po) {
    Long ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, ExaminationManagePO.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.save :" + e.getMessage());
    } 
    return ret;
  }
  
  public ExaminationManagePO load(Long id) {
    ExaminationManagePO ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = (ExaminationManagePO)ejbProxy.invoke("load", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.save :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean update(ExaminationManagePO po, Long id) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ExaminationManagePO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("update", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.update :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean savePaper(ExaminationAnswerPO po, Object[] para) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ExaminationAnswerPO.class);
      pg.put(para, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("savePaper", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.savePaper :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public ExaminationAnswerItemPO loadPaper(Long examinationID, Long empID, String stockID) {
    ExaminationAnswerItemPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(examinationID, Long.class);
      pg.put(empID, Long.class);
      pg.put(stockID, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      po = (ExaminationAnswerItemPO)ejbProxy.invoke("loadPaper", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.loadPaper :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public boolean grade(Long answerID, Object[] para) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(answerID, Long.class);
      pg.put(para, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("grade", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.grade :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Long getScore(String manageID, String empID, String style) {
    Long ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(manageID, String.class);
      pg.put(empID, String.class);
      pg.put(style, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = (Long)ejbProxy.invoke("getScore", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.getScore :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public boolean delete(Long id) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("delete", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.delete :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public boolean deleteBatch(String ids) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("deleteBatch", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.deleteBatch :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public List voteList(String manageID, String stockID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(manageID, String.class);
      pg.put(stockID, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationManageEJB", 
          "ExaminationManageEJBLocal", ExaminationManageEJBHome.class);
      list = (List)ejbProxy.invoke("voteList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationManageBD.voteList :" + 
          e.getMessage());
    } 
    return list;
  }
}
