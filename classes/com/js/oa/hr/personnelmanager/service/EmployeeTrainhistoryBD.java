package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeTrainhistoryEJBHome;
import com.js.system.vo.usermanager.TrainhistoryVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeTrainhistoryBD {
  private static Logger logger = Logger.getLogger(EmployeeTrainhistoryBD.class
      .getName());
  
  public boolean save(TrainhistoryVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeTrainhistoryEJB", 
          "EmployeeTrainhistoryEJBLocal", 
          EmployeeTrainhistoryEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, TrainhistoryVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存入职前培训经历时出错:" + e);
    } 
    return retflag;
  }
  
  public TrainhistoryVO load(Long id) {
    TrainhistoryVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeTrainhistoryEJB", 
          "EmployeeTrainhistoryEJBLocal", 
          EmployeeTrainhistoryEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (TrainhistoryVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取TrainhistoryVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean update(TrainhistoryVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeTrainhistoryEJB", 
          "EmployeeTrainhistoryEJBLocal", 
          EmployeeTrainhistoryEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, TrainhistoryVO.class);
      pg.put(empID, Long.class);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("更新一条入职前培训经历时出错:" + e);
    } 
    return retflag;
  }
  
  public boolean delete(Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeTrainhistoryEJB", 
          "EmployeeTrainhistoryEJBLocal", 
          EmployeeTrainhistoryEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("delete", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("删除一条记录时出错:" + e);
    } 
    return retflag;
  }
}
