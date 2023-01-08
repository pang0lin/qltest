package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeContractEJBHome;
import com.js.system.vo.usermanager.ContractVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeContractBD {
  public boolean delete(Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeContractEJB", "EmployeeContractEJBLocal", EmployeeContractEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("delete", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("删除一条记录时出错:" + e);
    } 
    return retflag;
  }
  
  public ContractVO load(Long id) {
    ContractVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeContractEJB", "EmployeeContractEJBLocal", EmployeeContractEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (ContractVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取ContractVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean save(ContractVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeContractEJB", "EmployeeContractEJBLocal", EmployeeContractEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ContractVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("保存培训记录时出错:" + e);
    } 
    return retflag;
  }
  
  public boolean update(ContractVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeContractEJB", "EmployeeContractEJBLocal", EmployeeContractEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, ContractVO.class);
      pg.put(empID, Long.class);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("update", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("更新一条合同信息时出错:" + e);
    } 
    return retflag;
  }
  
  private static Logger logger = Logger.getLogger(EmployeeContractBD.class.getName());
}
