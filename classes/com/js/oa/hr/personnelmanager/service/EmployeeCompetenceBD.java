package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmployeeCompetenceEJBHome;
import com.js.system.vo.usermanager.CompetenceVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmployeeCompetenceBD {
  public boolean delete(Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeCompetenceEJB", "EmployeeCompetenceEJBLocal", EmployeeCompetenceEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("delete", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("删除一条记录时出错:" + e);
    } 
    return retflag;
  }
  
  public CompetenceVO load(Long id) {
    CompetenceVO vo = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeCompetenceEJB", "EmployeeCompetenceEJBLocal", EmployeeCompetenceEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      vo = (CompetenceVO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取CompetenceVO对象时出错:" + e);
    } 
    return vo;
  }
  
  public boolean save(CompetenceVO po, Long empID) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeCompetenceEJB", "EmployeeCompetenceEJBLocal", EmployeeCompetenceEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, CompetenceVO.class);
      pg.put(empID, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("save", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("保存资格证书时出错:" + e);
    } 
    return retflag;
  }
  
  public boolean update(CompetenceVO vo, Long empID, Long id) {
    boolean retflag = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmployeeCompetenceEJB", "EmployeeCompetenceEJBLocal", EmployeeCompetenceEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(vo, CompetenceVO.class);
      pg.put(empID, Long.class);
      pg.put(id, Long.class);
      retflag = ((Boolean)ejbProxy.invoke("update", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("更新一条资格证书时出错:" + e);
    } 
    return retflag;
  }
  
  private static Logger logger = Logger.getLogger(EmployeeCompetenceBD.class.getName());
}
