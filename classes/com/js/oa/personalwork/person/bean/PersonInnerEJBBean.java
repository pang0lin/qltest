package com.js.oa.personalwork.person.bean;

import com.js.system.vo.organizationmanager.SyncRTXVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class PersonInnerEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Vector list(String browseRange, String orgId, String userId, String queryItem, String queryText, String order, String desc, String offset) throws Exception {
    return null;
  }
  
  public Map load(String editId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(editId));
      result.put("employeeVO", po);
      List list = this.session.createQuery("select poo.orgNameString from com.js.system.vo.usermanager.EmployeeVO po join po.organizations poo where po.empId=" + editId).list();
      if (list.size() > 0)
        result.put("userOrganization", list.get(0)); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public void update(EmployeeVO paraPO, String userAccount) throws Exception {
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(paraPO.getEmpId()));
      po.setEmpAddress(paraPO.getEmpAddress());
      po.setEmpBusinessFax(paraPO.getEmpBusinessFax());
      po.setEmpBusinessPhone(paraPO.getEmpBusinessPhone());
      po.setEmpCountry(paraPO.getEmpCountry());
      po.setEmpCounty(paraPO.getEmpCounty());
      po.setEmpDescribe(paraPO.getEmpDescribe());
      po.setEmpEmail(paraPO.getEmpEmail());
      po.setEmpEmail2(paraPO.getEmpEmail2());
      po.setEmpEmail3(paraPO.getEmpEmail3());
      po.setEmpEnglishName(paraPO.getEmpEnglishName());
      po.setEmpMobilePhone(paraPO.getEmpMobilePhone());
      po.setEmpName(paraPO.getEmpName());
      po.setEmpPhone(paraPO.getEmpPhone());
      po.setEmpSex(paraPO.getEmpSex());
      po.setEmpState(paraPO.getEmpState());
      po.setEmpWebAddress(paraPO.getEmpWebAddress());
      po.setEmpBirth(paraPO.getEmpBirth());
      this.session.update(po);
      SyncRTXVO rtxVO = new SyncRTXVO();
      rtxVO.setUserAccount(userAccount);
      rtxVO.setDataOpr(Byte.valueOf("1"));
      rtxVO.setDataType(Byte.valueOf("1"));
      this.session.save(rtxVO);
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
  
  private List listCountrys() throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.districtName from com.js.system.basedata.po.DistrictPO po where po.parentId = 0  order by po.id")
        .list();
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
  
  public List see() throws Exception {
    List lsit = new ArrayList();
    try {
      lsit = listCountrys();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return lsit;
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
            " order by po.districtName").list();
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
    List<String> listCity = new ArrayList();
    begin();
    List listCounty = new ArrayList();
    try {
      String countryId = "";
      List<E> listCountry = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where  po.parentId=0 and po.districtName='" + 
          country + "'").list();
      if (listCountry.size() > 0) {
        countryId = listCountry.get(0).toString();
        listCity = this.session.createQuery("select po.id from com.js.system.basedata.po.DistrictPO po where po.districtName='" + city + "' and  po.parentId=" + countryId).list();
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
  
  public List setValidOrgs(Long domainId) throws Exception {
    List validOrgs = new ArrayList();
    begin();
    try {
      String sql = "SELECT org.orgId,org.orgName,org.orgParentOrgId,org.orgLevel,org.orgHasJunior,org.orgIdString,org.orgStatus FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgStatus=0 " + (



        
        (domainId != null) ? ("  AND org.domainId=" + domainId.longValue()) : "") + 
        
        " ORDER BY org.orgIdString";
      validOrgs = this.session.createQuery(sql).list();
      this.session.close();
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
      throw e;
    } 
    return validOrgs;
  }
  
  private String getOrgBrowseScope(String browseRange) throws Exception {
    StringBuffer sb = new StringBuffer(" 1<>1 ");
    try {
      String orgIdString = "*" + browseRange + "*";
      String[] arr = orgIdString.split("\\*\\*");
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] != null && !"".equals(arr[i]))
          sb.append(" or org.orgIdString like '%$").append(arr[i]).append("$%'"); 
      } 
    } catch (Exception e) {
      sb = new StringBuffer(" 1<>1 ");
      e.printStackTrace();
    } 
    return sb.toString();
  }
  
  private String getBrowseScope(String browseRange) throws Exception {
    StringBuffer sb = new StringBuffer(" 1=1 ");
    return sb.toString();
  }
}
