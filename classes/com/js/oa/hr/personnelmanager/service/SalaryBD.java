package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.SalaryEJBBean;
import com.js.oa.hr.personnelmanager.po.SalaryPO;
import java.util.Date;
import org.apache.log4j.Logger;

public class SalaryBD {
  private static Logger logger = null;
  
  public void save(SalaryPO salaryPO) throws Exception {
    SalaryEJBBean salaryEJBBean = new SalaryEJBBean();
    salaryEJBBean.save(salaryPO);
  }
  
  public String yanzhengDate(Date date, long userid) throws Exception {
    String yanz = "N";
    SalaryEJBBean salaryEJBBean = new SalaryEJBBean();
    yanz = salaryEJBBean.yanzhengDate(date, userid);
    return yanz;
  }
  
  public SalaryPO searchById(Long salaryId) throws Exception {
    SalaryPO salaryPO = new SalaryPO();
    SalaryEJBBean salaryEJBBean = new SalaryEJBBean();
    salaryPO = salaryEJBBean.searchById(salaryId);
    return salaryPO;
  }
}
