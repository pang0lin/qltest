package net.jiusi.jsoa.service;

import net.jiusi.jsoa.service.pojo.OrganizationPojo;

public interface IOrgBiz {
  String addOrganization(OrganizationPojo paramOrganizationPojo, String paramString1, String paramString2);
  
  String deleteOrgByOrgSerial(String paramString);
  
  String disableOrgByOrgSerial(String paramString);
  
  String recoverOrgByOrgSerial(String paramString);
  
  String modifyOrg(OrganizationPojo paramOrganizationPojo, String paramString1, String paramString2);
}
