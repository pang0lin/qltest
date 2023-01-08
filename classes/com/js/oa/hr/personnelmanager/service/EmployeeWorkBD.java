package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeWorkEJBHome;
import com.js.system.vo.usermanager.WorkVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeWorkBD {
  private static Logger logger = Logger.getLogger(EmployeeWorkBD.class
      .getName());
  
  public boolean save(WorkVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeWorkEJB", 
          "EmployeeWorkEJBLocal", 
          EmployeeWorkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, WorkVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存工作经历时出错:" + e);
    } 
    return retflag;
  }
  
  public WorkVO load(Long id) {
    WorkVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeWorkEJB", 
          "EmployeeWorkEJBLocal", 
          EmployeeWorkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (WorkVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取WorkVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean update(WorkVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeWorkEJB", 
          "EmployeeWorkEJBLocal", 
          EmployeeWorkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, WorkVO.class);
      pg.put(empID, Long.class);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("更新一条工作经历时出错:" + e);
    } 
    return retflag;
  }
  
  public boolean delete(Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeWorkEJB", 
          "EmployeeWorkEJBLocal", 
          EmployeeWorkEJBHome.class);
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
