package com.js.system.bean.organizationmanager;

import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface OrganizationEJB extends EJBObject {
  void add(OrganizationVO paramOrganizationVO, String paramString1, String paramString2, Integer paramInteger) throws Exception, RemoteException;
  
  void update(OrganizationVO paramOrganizationVO, String paramString1, String paramString2, Integer paramInteger, String paramString3) throws Exception, RemoteException;
  
  String delete(Long paramLong) throws Exception, RemoteException;
  
  List getAllOrgs() throws HibernateException, RemoteException;
  
  List getSubOrgs(Long paramLong, String paramString) throws Exception, RemoteException;
  
  List getValidOrgs() throws HibernateException, RemoteException;
  
  Set getSubUsers(Long paramLong) throws Exception, RemoteException;
  
  List getHasChannel() throws Exception, RemoteException;
  
  String getOrgName(String paramString) throws Exception, RemoteException;
  
  List getSons(String paramString) throws Exception, RemoteException;
  
  List getSuperior(String paramString) throws Exception, RemoteException;
  
  List getNameAndId(String paramString) throws Exception, RemoteException;
  
  List getValidOrgsByRange(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getSimpleOrg(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllChannelList(String paramString) throws Exception, RemoteException;
  
  Integer checkOrganizationSerial(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer checkOrgSerialAndOrgParentorgid(String paramString1, String paramString2) throws Exception, RemoteException;
  
  DomainVO loadDomain(String paramString) throws Exception;
  
  boolean updateRootDept(OrganizationVO paramOrganizationVO) throws Exception, RemoteException;
  
  boolean updateRelationDept(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getOrgIdByUserID(String paramString) throws Exception;
  
  String getAllSubOrgIdByOrgId(String paramString) throws Exception;
  
  String findStationName(String paramString) throws Exception;
}
