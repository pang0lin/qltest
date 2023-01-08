package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpChangeEJBHome;
import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class EmpChangeBD {
  private static Logger logger = Logger.getLogger(EmpChangeBD.class.getName());
  
  public boolean addEmpChange(EmployeeChangePO empChangePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangePO, EmployeeChangePO.class);
      ejbProxy.invoke("addEmpChange", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addEmpChangeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteEmpChange(Long empChangeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeId, Long.class);
      ejbProxy.invoke("deleteEmpChange", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteEmpChangeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchEmpChange(String empChangeIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeIds, String.class);
      ejbProxy.invoke("deleteBatchEmpChange", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchEmpChangeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public EmployeeChangePO selectEmpChangeView(Long empChangeId) {
    EmployeeChangePO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeId, Long.class);
      result = (EmployeeChangePO)ejbProxy.invoke(
          "selectEmpChangeView", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEmpChangeViewBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateEmpChange(EmployeeChangePO empChangePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangePO, EmployeeChangePO.class);
      ejbProxy.invoke("updateEmpChange", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("updateEmpChangeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String selectEmpName(Long empChangeEmpId) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeEmpId, Long.class);
      result = (String)ejbProxy.invoke("selectEmpName", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEmpNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String selectOrgName(Long empChangeOrgId) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeOrgId, Long.class);
      result = (String)ejbProxy.invoke("selectOrgName", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectOrgNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectEmpDuty(String domainId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      result = (List)ejbProxy.invoke("selectEmpDuty", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEmpDutyBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectEmpType(String domainId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      result = (List)ejbProxy.invoke("selectEmpType", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEmpTypeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public int addEmpChangeType(EmployeeChangeTypePO empChangeTypePO) {
    int addResult = 2;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeTypePO, EmployeeChangeTypePO.class);
      addResult = ((Integer)ejbProxy.invoke("addEmpChangeType", 
          pg.getParameters())).intValue();
    } catch (Exception ex) {
      System.out.println("addEmpChangeTypeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return addResult;
  }
  
  public boolean deleteEmpChangeType(Long empChangeTypeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeTypeId, Long.class);
      result = ((Boolean)ejbProxy.invoke("deleteEmpChangeType", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("deleteEmpChangeTypeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchEmpChangeType(String empChangeTypeIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeTypeIds, String.class);
      result = ((Boolean)ejbProxy.invoke("deleteBatchEmpChangeType", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("deleteBatchEmpChangeTypeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public EmployeeChangeTypePO selectEmpChangeTypeView(Long empChangeTypeId) {
    EmployeeChangeTypePO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeTypeId, Long.class);
      result = (EmployeeChangeTypePO)ejbProxy.invoke(
          "selectEmpChangeTypeView", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectEmpChangeTypeViewBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public int updateEmpChangeType(EmployeeChangeTypePO empChangeTypePO) {
    int addResult = 2;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(empChangeTypePO, EmployeeChangeTypePO.class);
      addResult = ((Integer)ejbProxy.invoke("updateEmpChangeType", 
          pg.getParameters())).intValue();
    } catch (Exception ex) {
      System.out.println("updateEmpChangeTypeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return addResult;
  }
  
  public boolean hasSameNameExists(String typeName, String domainId) {
    boolean retFlg = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(typeName, String.class);
      pg.put(domainId, String.class);
      retFlg = ((Boolean)ejbProxy.invoke("hasSameNameExists", 
          pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("hasSameNameExists Exception:" + 
          ex.getMessage());
    } finally {}
    return retFlg;
  }
  
  public boolean hasSameNameExists(String typeName, String domainId, Long id) {
    boolean retFlg = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpChangeEJB", 
          "EmpChangeEJBLoca", EmpChangeEJBHome.class);
      pg.put(typeName, String.class);
      pg.put(domainId, String.class);
      pg.put(id, Long.class);
      retFlg = ((Boolean)ejbProxy.invoke("hasSameNameExists", 
          pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("hasSameNameExists Exception:" + 
          ex.getMessage());
    } finally {}
    return retFlg;
  }
}
