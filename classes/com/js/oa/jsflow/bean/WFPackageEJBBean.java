package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.PackageVO;
import com.js.system.manager.service.ManagerService;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WFPackageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public PackageVO getPackage(Long packageId) throws Exception {
    PackageVO packageVO = new PackageVO();
    begin();
    try {
      WFPackagePO packagePO = (WFPackagePO)this.session.load(WFPackagePO.class, packageId);
      packageVO.setId(packagePO.getWfPackageId().longValue());
      packageVO.setName(packagePO.getPackageName());
      packageVO.setCreatedDate(packagePO.getPackageCreatedDate());
      packageVO.setCreatedEmp(packagePO.getCreatedEmp().longValue());
      packageVO.setCreatedOrg(packagePO.getCreatedOrg().longValue());
      packageVO.setModuleId(packagePO.getModuleId());
      packageVO.setDescription(packagePO.getPackageDescription());
      packageVO.setOrderCode(packagePO.getOrderCode());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return packageVO;
  }
  
  public Boolean updatePackage(PackageVO packageVO) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery("select count(*) from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.wfPackageId <> " + 
          packageVO.getId() + " and aaa.packageName='" + packageVO.getName() + "' and aaa.domainId=(select b.domainId from com.js.oa.jsflow.po.WFPackagePO b where b.id=" + packageVO.getId() + ") ");
      String count = query.iterate().next().toString();
      if (count.equals("0")) {
        WFPackagePO packagePO = (WFPackagePO)this.session.load(WFPackagePO.class, new Long(packageVO.getId()));
        packagePO.setPackageName(packageVO.getName());
        packagePO.setPackageDescription(packageVO.getDescription());
        packagePO.setOrderCode(packageVO.getOrderCode());
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean addPackage(PackageVO packageVO) throws Exception {
    begin();
    Boolean success = new Boolean(false);
    try {
      Query query = this.session.createQuery("select count(*) from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.packageName='" + 
          packageVO.getName() + "' and aaa.domainId=" + packageVO.getDomainId());
      String count = query.iterate().next().toString();
      if (count.equals("0")) {
        WFPackagePO packagePO = new WFPackagePO();
        packagePO.setPackageName(packageVO.getName());
        packagePO.setCreatedEmp(new Long(packageVO.getCreatedEmp()));
        packagePO.setCreatedOrg(new Long(packageVO.getCreatedOrg()));
        packagePO.setPackageCreatedDate(packageVO.getCreatedDate());
        packagePO.setPackageDescription(packageVO.getDescription());
        packagePO.setModuleId(packageVO.getModuleId());
        packagePO.setDomainId(packageVO.getDomainId());
        packagePO.setOrderCode(packageVO.getOrderCode());
        this.session.save(packagePO);
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return success;
  }
  
  public void removePackage(String packageIdString) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.wfPackageId in (" + 
          packageIdString + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getModulePackage(String moduleId, String domainId) throws Exception {
    begin();
    ArrayList<PackageVO> alist = new ArrayList();
    try {
      String sql = "select aaa.wfPackageId, aaa.packageName from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId = " + moduleId + " and aaa.domainId=" + domainId + " order by aaa.orderCode , aaa.wfPackageId desc";
      Query query = this.session.createQuery(sql);
      List<Object[]> list = query.list();
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        PackageVO packageVO = new PackageVO();
        packageVO.setId(Long.parseLong(obj[0].toString()));
        packageVO.setName(obj[1].toString());
        alist.add(packageVO);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List getModulePackageByRight(ModuleVO moduleVO, String domainId, String userId, String orgId) throws Exception {
    begin();
    ArrayList<PackageVO> alist = new ArrayList();
    int moduleId = moduleVO.getId();
    try {
      String sql = "select aaa.wfPackageId, aaa.packageName from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId = " + moduleId + " and aaa.domainId=" + domainId;
      if (moduleVO.isPackRight()) {
        ManagerService service = new ManagerService();
        sql = String.valueOf(sql) + " and " + service.getRightFinalWhere(userId, orgId, 
            moduleVO.getPackRightType(), 
            "aaa.createdOrg", "aaa.createdEmp");
      } 
      sql = String.valueOf(sql) + " order by aaa.orderCode , aaa.wfPackageId desc";
      Query query = this.session.createQuery(sql);
      List<Object[]> list = query.list();
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        PackageVO packageVO = new PackageVO();
        packageVO.setId(Long.parseLong(obj[0].toString()));
        packageVO.setName(obj[1].toString());
        alist.add(packageVO);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
  
  public List getModuleProc(String moduleId) throws Exception {
    ArrayList<String[]> list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.processType, aaa.accessDatabaseId, aaa.remindField from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb where bbb.moduleId = " + moduleId);
      list = (ArrayList)query.list();
      if (list.size() == 0) {
        list = new ArrayList();
        String[] tmp = { "", "", "", "", "" };
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT WF_IMMOFORM_ID FROM JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + moduleId);
        if (rs.next())
          tmp[3] = rs.getString(1); 
        rs.close();
        stmt.close();
        conn.close();
        list.add(tmp);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
}
