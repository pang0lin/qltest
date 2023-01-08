package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeQtqsqkEJBHome;
import com.js.system.vo.usermanager.QtqsqkVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeQtqsqkBD {
  private static Logger logger = Logger.getLogger(EmployeeQtqsqkBD.class
      .getName());
  
  public boolean save(QtqsqkVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeQtqsqkEJB", 
          "EmployeeQtqsqkEJBLocal", 
          EmployeeQtqsqkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, QtqsqkVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存教育经历时出错:" + e);
    } 
    return retflag;
  }
  
  public QtqsqkVO load(Long id) {
    QtqsqkVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeQtqsqkEJB", 
          "EmployeeQtqsqkEJBLocal", 
          EmployeeQtqsqkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (QtqsqkVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取QtqsqkVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean update(QtqsqkVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeQtqsqkEJB", 
          "EmployeeQtqsqkEJBLocal", 
          EmployeeQtqsqkEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, QtqsqkVO.class);
      pg.put(empID, Long.class);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("更新一条教育经历时出错:" + e);
    } 
    return retflag;
  }
  
  public boolean delete(Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeQtqsqkEJB", 
          "EmployeeQtqsqkEJBLocal", 
          EmployeeQtqsqkEJBHome.class);
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
