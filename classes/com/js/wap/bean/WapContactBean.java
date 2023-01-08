package com.js.wap.bean;

import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WapContactBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getPersonal(String hql, int beginIndex, int limit) throws Exception {
    Map<Object, Object> innerMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List list = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      Query querySize = this.session.createQuery(hql);
      recordCount = querySize.list().size();
      innerMap.put("list", list);
      innerMap.put("size", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return innerMap;
  }
  
  public void saveClass(PersonClassPO po) throws Exception {
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public Map getClassList(String hql, int beginIndex, int limit) throws Exception {
    Map<Object, Object> innerMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List list = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      Query querySize = this.session.createQuery(hql);
      recordCount = querySize.list().size();
      innerMap.put("list", list);
      innerMap.put("size", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return innerMap;
  }
  
  public PersonPO getPersonPO(String hql) throws Exception {
    PersonPO personPO = new PersonPO();
    List<PersonPO> list = null;
    try {
      begin();
      list = this.session.createQuery(hql).list();
      personPO = list.get(0);
    } catch (Exception e) {
      System.out.println("----------数据库中无此人信息！");
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return personPO;
  }
  
  public Map getEmployee(String hql, String id) throws Exception {
    Map<Object, Object> user = new HashMap<Object, Object>();
    EmployeeVO employeeVO = new EmployeeVO();
    List list = null;
    try {
      begin();
      list = this.session.createQuery(hql).list();
      user.put("person", list);
      List orglist = this.session.createQuery("select poo.orgNameString from com.js.system.vo.usermanager.EmployeeVO po join po.organizations poo where po.empId=" + id).list();
      if (list.size() > 0)
        user.put("userOrganization", orglist.get(0)); 
    } catch (Exception e) {
      System.out.println("----------数据库中无此人信息！");
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return user;
  }
  
  public List getSort(String type, String domainId, String userId) {
    List<String[]> list = new ArrayList();
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    try {
      dataSourceBase.begin();
      ResultSet rs = null;
      if ("0".equals(type)) {
        sql = "select class_id,classname from oa_linkmanclass where classtype=" + type + " and domain_id=" + domainId + " and emp_id=" + userId;
      } else {
        sql = "select class_id,classname from oa_linkmanclass where classtype=" + type + " and domain_id=" + domainId;
      } 
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String[] sort = new String[2];
        sort[0] = rs.getString(1);
        sort[1] = rs.getString(2);
        list.add(sort);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return list;
  }
  
  public String[] getClassMore(String id, String type) {
    String[] sort = new String[2];
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    try {
      dataSourceBase.begin();
      ResultSet rs = null;
      if ("0".equals(type)) {
        sql = "select classname,classdescribe from oa_linkmanclass where classtype=" + type + " and class_id=" + id;
      } else {
        sql = "select classname,classdescribe from oa_linkmanclass where classtype=" + type + " and class_id=" + id;
      } 
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        sort[0] = rs.getString(1);
        sort[1] = rs.getString(2);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return sort;
  }
  
  public void saveLinkMan(PersonPO po) throws Exception {
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public void updateLinkMan(PersonPO po) throws Exception {
    StringBuffer sql = new StringBuffer("update oa_linkman set ");
    sql.append(" linkmanname='" + po.getLinkManName());
    sql.append("',linkmansex=" + po.getLinkManSex());
    sql.append(",class_id=" + po.getLinkManClass().getId());
    sql.append(",mobilephone='" + po.getMobilePhone());
    sql.append("',bussinessphone='" + po.getBussinessPhone());
    sql.append("',bussinessfax='" + po.getBussinessFax());
    sql.append("',linkmanemail='" + po.getLinkManEmail());
    sql.append("',linkmanunit='" + po.getLinkManUnit());
    sql.append("',linkmandepart='" + po.getLinkManDepart());
    sql.append("',linkmanduty='" + po.getLinkManDuty());
    sql.append("',linkmanemail2='" + po.getLinkManEmail2());
    sql.append("',linkmanemail3='" + po.getLinkManEmail3());
    sql.append("',fixedphone='" + po.getFixedPhone());
    sql.append("',linkmanaddress='" + po.getLinkManAddress());
    sql.append("',linkmanpostzip='" + po.getLinkManPostZip());
    sql.append("' where linkman_id=" + po.getId());
    DataSourceBase dataSourceBase = new DataSourceBase();
    try {
      dataSourceBase.begin();
      dataSourceBase.executeUpdate(sql.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
  }
  
  public PersonClassPO getClassPO(Long id) throws Exception {
    PersonClassPO personClassPO = new PersonClassPO();
    try {
      begin();
      List<PersonClassPO> list = this.session.createQuery("select po from com.js.oa.personalwork.person.po.PersonClassPO po where po.id=" + id).list();
      personClassPO = list.get(0);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return personClassPO;
  }
  
  public String[] getRight(String sql) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String[] right = { "", "" };
    try {
      dataSourceBase.begin();
      ResultSet rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        right[0] = rs.getString(1);
        right[1] = rs.getString(2);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return right;
  }
  
  public String getOrg(String sql) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String org = "";
    try {
      dataSourceBase.begin();
      ResultSet rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        org = String.valueOf(org) + rs.getString(1) + ","; 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return org;
  }
}
