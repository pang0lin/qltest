package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAttendancePO;
import com.js.util.hibernate.HibernateBase;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WorkAttendanceEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void testMethod() {}
  
  public Boolean save(WorkAttendancePO workAttendancePO) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.hr.personnelmanager.po.WorkAttendancePO aaa where aaa.emp=" + 
          workAttendancePO.getEmp() + " and aaa.year=" + 
          workAttendancePO.getYear() + " and aaa.month=" + workAttendancePO.getMonth());
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        success = Boolean.FALSE;
      } else {
        this.session.save(workAttendancePO);
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public List stat(String orgId, String year, String month) throws Exception {
    ArrayList<Object[]> alist = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select e.empName,wa.record,wa.emp from com.js.oa.hr.personnelmanager.po.WorkAttendancePO wa,com.js.system.vo.usermanager.EmployeeVO e where wa.emp=e.empId and wa.org=" + 
          orgId + 
          " and wa.year=" + year + " and wa.month=" + month);
      Iterator<Object[]> iter = query.iterate();
      Object[] waObj = (Object[])null;
      int absentCount = 0;
      int sickCount = 0;
      int privateCount = 0;
      int compoCount = 0;
      int marryFuneral = 0;
      int subtotal = 0;
      float monthRate = 0.0F;
      int workingDay = 0;
      float yearRate = 0.0F;
      int yearWorkingDay = 0;
      int yearHols = 0;
      char[] record = (char[])null;
      char[] yearRec = (char[])null;
      DecimalFormat df = new DecimalFormat("#.0");
      Iterator<E> yearIter = null;
      StringBuffer yearRecord = new StringBuffer();
      while (iter.hasNext()) {
        absentCount = 0;
        sickCount = 0;
        privateCount = 0;
        compoCount = 0;
        marryFuneral = 0;
        subtotal = 0;
        monthRate = 0.0F;
        yearRate = 0.0F;
        workingDay = 0;
        yearWorkingDay = 0;
        yearHols = 0;
        yearRecord.delete(0, yearRecord.length());
        waObj = iter.next();
        Object[] statObj = { "", "", "", "", "", "", "", "", "", "" };
        statObj[0] = waObj[0];
        statObj[1] = waObj[1];
        query = this.session.createQuery("select wa.record from com.js.oa.hr.personnelmanager.po.WorkAttendancePO wa where wa.emp=" + 
            waObj[2] + " and wa.year=" + year);
        yearIter = query.iterate();
        while (yearIter.hasNext())
          yearRecord.append(yearIter.next().toString()); 
        yearRec = yearRecord.toString().toCharArray();
        int i;
        for (i = 0; i < yearRec.length; i++) {
          if (yearRec[i] == 'd' || yearRec[i] == 'b' || yearRec[i] == 'a' || 
            yearRec[i] == 'c' || yearRec[i] == 'f' || yearRec[i] == 'i')
            yearHols++; 
          if (yearRec[i] != 'm' && yearRec[i] != 'n' && yearRec[i] != 'l' && 
            yearRec[i] != 'j' && yearRec[i] != 'o')
            yearWorkingDay++; 
        } 
        yearRate = Float.parseFloat((new StringBuilder(String.valueOf(yearWorkingDay - yearHols))).toString()) / Float.parseFloat((new StringBuilder(String.valueOf(yearWorkingDay))).toString()) * 100.0F;
        record = waObj[1].toString().toCharArray();
        for (i = 0; i < record.length; i++) {
          switch (record[i]) {
            case 'd':
              absentCount++;
              break;
            case 'b':
              sickCount++;
              break;
            case 'a':
              privateCount++;
              break;
            case 'c':
              compoCount++;
              break;
            case 'f':
              marryFuneral++;
              break;
            case 'i':
              marryFuneral++;
              break;
          } 
          subtotal = absentCount + sickCount + privateCount + compoCount + marryFuneral;
          if (record[i] != 'm' && record[i] != 'n' && record[i] != 'l' && 
            record[i] != 'j' && record[i] != 'o')
            workingDay++; 
        } 
        monthRate = Float.parseFloat((new StringBuilder(String.valueOf(workingDay - subtotal))).toString()) / Float.parseFloat((new StringBuilder(String.valueOf(workingDay))).toString()) * 100.0F;
        statObj[2] = (new StringBuilder(String.valueOf(absentCount))).toString();
        statObj[3] = (new StringBuilder(String.valueOf(sickCount))).toString();
        statObj[4] = (new StringBuilder(String.valueOf(privateCount))).toString();
        statObj[5] = (new StringBuilder(String.valueOf(compoCount))).toString();
        statObj[6] = (new StringBuilder(String.valueOf(marryFuneral))).toString();
        statObj[7] = (new StringBuilder(String.valueOf(subtotal))).toString();
        statObj[8] = df.format(monthRate);
        statObj[9] = df.format(yearRate);
        alist.add(statObj);
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public Boolean delete(String ids) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      this.session.delete("from com.js.oa.hr.personnelmanager.po.WorkAttendancePO aaa where aaa.id in (" + 
          ids + ")");
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Object[] getSingle(String id) throws Exception {
    begin();
    Object[] single = (Object[])null;
    try {
      Iterator<Object[]> iter = this.session.createQuery("select wa.org,o.orgName,e.empName,wa.emp,wa.record,wa.fillDate,wa.year,wa.month from com.js.oa.hr.personnelmanager.po.WorkAttendancePO wa,com.js.system.vo.usermanager.EmployeeVO e,com.js.system.vo.organizationmanager.OrganizationVO o where wa.emp=e.empId and wa.org=o.orgId and wa.id=" + 

          
          id).iterate();
      if (iter.hasNext())
        single = iter.next(); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return single;
  }
  
  public Boolean update(WorkAttendancePO workAttendancePO) throws Exception {
    begin();
    Boolean success = new Boolean(true);
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.hr.personnelmanager.po.WorkAttendancePO aaa where aaa.emp=" + 
          workAttendancePO.getEmp() + " and aaa.year=" + 
          workAttendancePO.getYear() + " and aaa.month=" + workAttendancePO.getMonth() + 
          " and aaa.id<>" + workAttendancePO.getId());
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        success = Boolean.FALSE;
      } else {
        WorkAttendancePO modifyPO = (WorkAttendancePO)this.session.load(WorkAttendancePO.class, workAttendancePO.getId());
        modifyPO.setEmp(workAttendancePO.getEmp());
        modifyPO.setFillDate(workAttendancePO.getFillDate());
        modifyPO.setMonth(workAttendancePO.getMonth());
        modifyPO.setOrg(workAttendancePO.getOrg());
        modifyPO.setRecord(workAttendancePO.getRecord());
        modifyPO.setYear(workAttendancePO.getYear());
        this.session.flush();
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
}
