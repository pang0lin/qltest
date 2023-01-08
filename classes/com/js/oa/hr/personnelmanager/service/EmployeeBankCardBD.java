package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeBankCardEJBHome;
import com.js.system.vo.usermanager.BankCardVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeBankCardBD {
  private static Logger logger = Logger.getLogger(EmployeeBankCardBD.class
      .getName());
  
  public boolean save(BankCardVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeBankCardEJB", 
          "EmployeeBankCardEJBLocal", 
          EmployeeBankCardEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, BankCardVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存教育经历时出错:" + e);
    } 
    return retflag;
  }
  
  public BankCardVO load(Long id) {
    BankCardVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeBankCardEJB", 
          "EmployeeBankCardEJBLocal", 
          EmployeeBankCardEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (BankCardVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取EdustoryVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean update(BankCardVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeBankCardEJB", 
          "EmployeeBankCardEJBLocal", 
          EmployeeBankCardEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, BankCardVO.class);
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
      EJBProxy ejbProxy = new EJBProxy("EmployeeBankCardEJB", 
          "EmployeeBankCardEJBLocal", 
          EmployeeBankCardEJBHome.class);
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