package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WorkAddressEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(WorkAddressPO po) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Long id = (Long)this.session.save(po);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement pstmt = conn.createStatement();
      String userIds = null;
      String orgIds = null;
      String groupIds = null;
      String ids = "";
      if (po.getAddrUserId() != null) {
        userIds = "$" + po.getAddrUserId() + "$";
        ids = "-1" + userIds.replaceAll("\\$\\$", ",") + "-1";
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + ids + ")");
      } 
      if (po.getAddrUserOrg() != null) {
        orgIds = "*" + po.getAddrUserOrg() + "*";
        String tmpOrgs = orgIds.replaceAll("\\*\\*", ",");
        String[] tmps = tmpOrgs.split(",");
        String org0 = "-1";
        for (int k0 = 0; k0 < tmps.length; k0++) {
          if (!"".equals(tmps[k0])) {
            ResultSet rs0 = pstmt.executeQuery(
                "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
                tmps[k0]);
            String orgCode = "-1";
            if (rs0 != null) {
              if (rs0.next())
                orgCode = rs0.getString(1); 
              rs0.close();
            } 
            ResultSet rs1 = pstmt.executeQuery("select distinct EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
                orgCode + "%')");
            if (rs1 != null) {
              while (rs1.next()) {
                Object empId = rs1.getObject(1);
                org0 = String.valueOf(org0) + "," + empId.toString();
              } 
              rs1.close();
            } 
          } 
        } 
        String _ids = "-1";
        _ids = String.valueOf(_ids) + "," + org0;
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + _ids + ")");
      } 
      if (po.getAddrUserGroup() != null) {
        groupIds = "@" + po.getAddrUserGroup() + "@";
        groupIds = "-1" + groupIds.replaceAll("\\@\\@", ",") + "-1";
        ResultSet rs = pstmt.executeQuery("select distinct a.emp_id from org_employee a, org_user_group b, org_group c where a.emp_id = b.emp_id and b.group_id = c.group_id and c.group_id in (" + 
            groupIds + ")");
        String _ids = "-1";
        while (rs.next())
          _ids = String.valueOf(_ids) + "," + rs.getLong(1); 
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + _ids + ")");
        rs.close();
      } 
      pstmt.close();
      conn.close();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public WorkAddressPO load(Long id) throws Exception {
    WorkAddressPO po = null;
    try {
      begin();
      po = (WorkAddressPO)this.session.get(WorkAddressPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public WorkAddressPO loadByName(String name) throws Exception {
    WorkAddressPO po = null;
    try {
      begin();
      List<WorkAddressPO> list = this.session.createQuery("from com.js.oa.hr.personnelmanager.po.WorkAddressPO po  where po.workName='" + 
          
          name + "'").list();
      if (list != null && list.size() > 0)
        return list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(WorkAddressPO po, Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      WorkAddressPO needModifyPO = (WorkAddressPO)this.session.load(WorkAddressPO.class, id);
      needModifyPO.setDomain(po.getDomain());
      needModifyPO.setWorkAddress(po.getWorkAddress());
      needModifyPO.setWorkCity(po.getWorkCity());
      needModifyPO.setWorkCountry(po.getWorkCountry());
      needModifyPO.setWorkFax(po.getWorkFax());
      needModifyPO.setWorkName(po.getWorkName());
      needModifyPO.setWorkState(po.getWorkState());
      needModifyPO.setWorkTelephone(po.getWorkTelephone());
      needModifyPO.setAddrUserName(po.getAddrUserName());
      needModifyPO.setAddrUserId(po.getAddrUserId());
      needModifyPO.setAddrUserOrg(po.getAddrUserOrg());
      needModifyPO.setAddrUserGroup(po.getAddrUserGroup());
      this.session.update(needModifyPO);
      this.session.flush();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement pstmt = conn.createStatement();
      pstmt.executeUpdate(
          "update org_employee set workaddress = 0 where workaddress in (" + 
          id + ")");
      String userIds = null;
      String orgIds = null;
      String groupIds = null;
      String ids = "";
      if (po.getAddrUserId() != null) {
        userIds = "$" + po.getAddrUserId() + "$";
        ids = "-1" + userIds.replaceAll("\\$\\$", ",") + "-1";
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + ids + ")");
      } 
      if (po.getAddrUserOrg() != null) {
        orgIds = "*" + po.getAddrUserOrg() + "*";
        String tmpOrgs = orgIds.replaceAll("\\*\\*", ",");
        String[] tmps = tmpOrgs.split(",");
        String org0 = "-1";
        for (int k0 = 0; k0 < tmps.length; k0++) {
          if (!"".equals(tmps[k0])) {
            ResultSet rs0 = pstmt.executeQuery(
                "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
                tmps[k0]);
            String orgCode = "-1";
            if (rs0 != null) {
              if (rs0.next())
                orgCode = rs0.getString(1); 
              rs0.close();
            } 
            ResultSet rs1 = pstmt.executeQuery("select distinct EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
                orgCode + "%')");
            if (rs1 != null) {
              while (rs1.next()) {
                Object empId = rs1.getObject(1);
                org0 = String.valueOf(org0) + "," + empId.toString();
              } 
              rs1.close();
            } 
          } 
        } 
        String _ids = "-1";
        _ids = String.valueOf(_ids) + "," + org0;
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + _ids + ")");
      } 
      if (po.getAddrUserGroup() != null) {
        groupIds = "@" + po.getAddrUserGroup() + "@";
        groupIds = "-1" + groupIds.replaceAll("\\@\\@", ",") + "-1";
        ResultSet rs = pstmt.executeQuery("select distinct a.emp_id from org_employee a, org_user_group b, org_group c where a.emp_id = b.emp_id and b.group_id = c.group_id and c.group_id in (" + 
            groupIds + ")");
        String _ids = "-1";
        while (rs.next())
          _ids = String.valueOf(_ids) + "," + rs.getLong(1); 
        pstmt.executeUpdate("update org_employee set workaddress = " + 
            id + " where emp_id in (" + _ids + ")");
        rs.close();
      } 
      pstmt.close();
      conn.close();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean delete(String ids) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      this.session.delete("from com.js.oa.hr.personnelmanager.po.WorkAddressPO po  where po.id in(" + 
          ids + ")");
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement pstmt = conn.createStatement();
      pstmt.executeUpdate("update org_employee set workaddress = 0 where workaddress in (" + ids + ")");
      this.session.flush();
      ret = Boolean.TRUE;
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public List list(Long domainID) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select po.id,po.workName from com.js.oa.hr.personnelmanager.po.WorkAddressPO po  where po.domain=" + 
          
          domainID).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
}
