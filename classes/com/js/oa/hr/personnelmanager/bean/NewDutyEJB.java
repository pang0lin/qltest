package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import javax.servlet.http.HttpServletRequest;

public interface NewDutyEJB extends EJBObject {
  Integer add(DutyPO paramDutyPO) throws Exception, RemoteException;
  
  Boolean del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getList(String paramString) throws Exception, RemoteException;
  
  DutyPO getSingle(String paramString) throws Exception, RemoteException;
  
  Integer update(DutyPO paramDutyPO) throws Exception, RemoteException;
  
  PostTitlePO getSinglePost(String paramString) throws Exception, RemoteException;
  
  Integer updatePost(PostTitlePO paramPostTitlePO) throws Exception, RemoteException;
  
  String saveStation(StationPO paramStationPO) throws Exception, RemoteException;
  
  Boolean deleteStation(String paramString) throws Exception, RemoteException;
  
  String[] getSingleStation(String paramString) throws Exception, RemoteException;
  
  String updateStation(StationPO paramStationPO) throws Exception, RemoteException;
  
  List getStationList(String paramString) throws Exception, RemoteException;
  
  Long getDutyID(String paramString, Long paramLong) throws Exception, RemoteException;
  
  OrganizationVO getOrgPO(Long paramLong) throws Exception, RemoteException;
  
  Map ImportDuty(HttpServletRequest paramHttpServletRequest) throws FileNotFoundException;
  
  boolean updateByDutySql(String paramString) throws Exception;
  
  boolean getStationName(String paramString) throws Exception;
}
