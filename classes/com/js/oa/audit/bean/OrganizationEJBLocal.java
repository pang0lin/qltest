package com.js.oa.audit.bean;

import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface OrganizationEJBLocal extends EJBLocalObject {
  void add(OrganizationVO paramOrganizationVO, String paramString1, String paramString2, Integer paramInteger) throws Exception;
  
  void update(OrganizationVO paramOrganizationVO, String paramString1, String paramString2, Integer paramInteger, String paramString3) throws Exception;
  
  String delete(Long paramLong) throws Exception;
  
  List getAllOrgs() throws HibernateException;
  
  List getSubOrgs(Long paramLong, String paramString) throws Exception;
  
  List getValidOrgs() throws HibernateException;
  
  Set getSubUsers(Long paramLong) throws Exception;
  
  List getHasChannel() throws Exception;
  
  String getOrgName(String paramString) throws Exception;
  
  List getSons(String paramString) throws Exception;
  
  List getSuperior(String paramString) throws Exception;
  
  List getNameAndId(String paramString) throws Exception;
  
  List getValidOrgsByRange(String paramString1, String paramString2) throws Exception;
  
  List getSimpleOrg(String paramString1, String paramString2) throws Exception;
  
  List getAllChannelList(String paramString) throws Exception;
  
  Integer checkOrganizationSerial(String paramString1, String paramString2) throws Exception;
  
  Integer checkOrgSerialAndOrgParentorgid(String paramString1, String paramString2) throws Exception;
  
  DomainVO loadDomain(String paramString) throws Exception;
  
  boolean updateRootDept(OrganizationVO paramOrganizationVO) throws Exception;
  
  boolean updateRelationDept(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getOrgIdByUserID(String paramString) throws Exception;
  
  String getAllSubOrgIdByOrgId(String paramString) throws Exception;
}
