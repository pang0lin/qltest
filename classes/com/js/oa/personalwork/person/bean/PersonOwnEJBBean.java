package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PersonOwnEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getUnitList(Byte linkManType, int userId) throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select distinct po.linkManUnit from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType =" + linkManType + " and po.createdEmpName=" + 
          "(select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId=" + userId + ")")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getPublicUnitList(Byte linkManType) throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select distinct po.linkManUnit from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType =" + linkManType)
        .list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Vector list(String userType, String queryItem, String queryText, String order, String desc, String offset, String userId) throws Exception {
    return null;
  }
  
  public PersonPO load(String editId) throws Exception {
    PersonPO po = null;
    try {
      begin();
      po = (PersonPO)this.session.load(PersonPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public void add(PersonPO po, String userId, String userName, String orgId, String userType, String tmpLinkManBirth, String classId) throws Exception {
    try {
      begin();
      po.setLinkManBirth(new Date(tmpLinkManBirth));
      if (classId != null)
        po.setLinkManClass((PersonClassPO)this.session.load(PersonClassPO.class, new Long(classId))); 
      po.setLinkManType(Byte.parseByte(userType));
      po.setCreatedEmpId(Long.parseLong(userId));
      po.setCreatedEmpName(userName);
      po.setCreatedOrg(Long.parseLong(orgId));
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delBatch(String ids, String userId) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.personalwork.person.po.PersonPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String userId, String userType) throws Exception {
    begin();
    try {
      if (userType.equals("0"))
        this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=0 and  po.createdEmpId=" + 
            userId); 
      if (userType.equals("1"))
        this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=1 and   po.createdEmpId=" + 
            userId); 
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
  
  public void delAll(String userId, String userType, String classId) throws Exception {
    begin();
    try {
      if (userType.equals("0"))
        if (classId != null && !classId.equals("")) {
          this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=0 and  po.createdEmpId=" + 
              userId + " and po.linkManClass.id = " + classId);
        } else {
          this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=0 and  po.createdEmpId=" + 
              userId);
        }  
      if (userType.equals("1"))
        if (classId != null && !classId.equals("")) {
          this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=1 and   po.createdEmpId=" + 
              userId + " and po.linkManClass.id = " + classId);
        } else {
          this.session.delete("from com.js.oa.personalwork.person.po.PersonPO po where po.linkManType=1 and   po.createdEmpId=" + 
              userId);
        }  
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
  
  public void update(PersonPO paraPO, String classId) throws Exception {
    try {
      begin();
      PersonPO po = (PersonPO)this.session.load(PersonPO.class, 
          new Long(paraPO.getId()));
      po.setLinkManClass((PersonClassPO)this.session.load(PersonClassPO.class, new Long(classId)));
      po.setLinkManBirth(paraPO.getLinkManBirth());
      po.setBussinessFax(paraPO.getBussinessFax());
      po.setBussinessPhone(paraPO.getBussinessPhone());
      po.setFixedPhone(paraPO.getFixedPhone());
      po.setLinkManAddress(paraPO.getLinkManAddress());
      po.setLinkManCountry(paraPO.getLinkManCountry());
      po.setLinkManCounty(paraPO.getLinkManCounty());
      po.setLinkManDepart(paraPO.getLinkManDepart());
      po.setLinkManDuty(paraPO.getLinkManDuty());
      po.setLinkManEmail(paraPO.getLinkManEmail());
      po.setLinkManEmail2(paraPO.getLinkManEmail2());
      po.setLinkManEmail3(paraPO.getLinkManEmail3());
      po.setLinkManEnName(paraPO.getLinkManEnName());
      po.setLinkManDescribe(paraPO.getLinkManDescribe());
      po.setLinkManName(paraPO.getLinkManName());
      po.setLinkManPostZip(paraPO.getLinkManPostZip());
      po.setLinkManProfession(paraPO.getLinkManProfession());
      po.setLinkManSex(paraPO.getLinkManSex());
      po.setLinkManState(paraPO.getLinkManState());
      po.setLinkManUnit(paraPO.getLinkManUnit());
      po.setLinkManWebUrl(paraPO.getLinkManWebUrl());
      po.setMobilePhone(paraPO.getMobilePhone());
      po.setViewScope(paraPO.getViewScope());
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private List listCountrys() throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where po.parentId = 0  order by po.id")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("列表的时候" + e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  private List listClasses(String userId, String userType, String domainId) throws Exception {
    List list = null;
    try {
      begin();
      if ("1".equals(userType))
        list = this.session.createQuery("select po.id,po.className from com.js.oa.personalwork.person.po.PersonClassPO po where po.classType=1 and po.domainId=" + domainId + " order by po.className").list(); 
      if ("0".equals(userType))
        list = this.session.createQuery("select po.id,po.className from com.js.oa.personalwork.person.po.PersonClassPO po  where po.classType=0 and (po.empId=" + userId + " or po.empId=0) and po.domainId=" + domainId + " order by po.className").list(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("列表的时候" + e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Vector see(String userId, String userType, String domainId) throws Exception {
    Vector<List> vec = new Vector();
    try {
      vec.add(listCountrys());
      vec.add(listClasses(userId, userType, domainId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return vec;
  }
  
  public List city(String country) throws Exception {
    List listCity = new ArrayList();
    begin();
    try {
      String countryId = "";
      List<E> listCountry = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where  po.parentId=0 and po.districtName='" + 
          country + "'").list();
      if (listCountry.size() > 0) {
        countryId = listCountry.get(0).toString();
        listCity = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where  po.parentId=" + 
            countryId + 
            " order by po.districtName")
          .list();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return listCity;
  }
  
  public List county(String country, String city) throws Exception {
    begin();
    List listCounty = new ArrayList();
    try {
      String countryId = "";
      List<E> listCountry = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where  po.parentId=0 and po.districtName='" + 
          country + "'").list();
      if (listCountry.size() > 0) {
        countryId = listCountry.get(0).toString();
        List<String> listCity = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where po.districtName='" + 
            city + "' and  po.parentId=" + countryId).list();
        if (listCity.size() > 0)
          listCounty = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where  po.parentId=" + 
              listCity.get(0) + " order by po.districtName").list(); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return listCounty;
  }
  
  public void addInput(PersonPO po) throws Exception {
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
}
