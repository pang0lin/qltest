package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.po.PostTitlePO;
import com.js.oa.hr.personnelmanager.po.StationPO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface NewDutyEJBLocal extends EJBLocalObject {
  Integer add(DutyPO paramDutyPO) throws Exception;
  
  Boolean del(String[] paramArrayOfString) throws Exception;
  
  List getList(String paramString) throws Exception;
  
  DutyPO getSingle(String paramString) throws Exception;
  
  Integer update(DutyPO paramDutyPO) throws Exception;
  
  PostTitlePO getSinglePost(String paramString) throws Exception;
  
  Integer updatePost(PostTitlePO paramPostTitlePO) throws Exception;
  
  String saveStation(StationPO paramStationPO) throws Exception;
  
  Boolean deleteStation(String paramString) throws Exception;
  
  String[] getSingleStation(String paramString) throws Exception;
  
  String updateStation(StationPO paramStationPO) throws Exception;
  
  List getStationList(String paramString) throws Exception;
  
  Long getDutyID(String paramString, Long paramLong) throws Exception;
  
  OrganizationVO getOrgPO(Long paramLong) throws Exception;
}
