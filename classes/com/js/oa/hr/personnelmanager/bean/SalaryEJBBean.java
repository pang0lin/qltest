package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.SalaryPO;
import com.js.util.hibernate.HibernateBase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SalaryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void save(SalaryPO salaryPO) throws Exception {
    try {
      begin();
      this.session.save(salaryPO);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public String yanzhengDate(Date date, long userid) throws Exception {
    begin();
    String yanz = "N";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
    Date salaryDate = null;
    String salaryDateString = null;
    String newSalaryDateString = null;
    try {
      List<SalaryPO> list = this.session.createQuery("select salary FROM com.js.oa.hr.personnelmanager.po.SalaryPO salary where salary.employeeVO.empId =" + userid).list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          salaryDate = ((SalaryPO)list.get(i)).getSendTime();
          salaryDateString = dateFormat.format(salaryDate);
          newSalaryDateString = dateFormat.format(date);
          if (salaryDateString.equals(newSalaryDateString))
            yanz = "Y"; 
        }  
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return yanz;
  }
  
  public SalaryPO searchById(Long salaryId) throws Exception {
    SalaryPO salaryPO = new SalaryPO();
    try {
      begin();
      salaryPO = (SalaryPO)this.session.get(SalaryPO.class, salaryId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return salaryPO;
  }
}
