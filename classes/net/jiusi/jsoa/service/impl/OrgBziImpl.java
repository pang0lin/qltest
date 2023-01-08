package net.jiusi.jsoa.service.impl;

import net.jiusi.jsoa.service.IOrgBiz;
import net.jiusi.jsoa.service.dao.OrgDao;
import net.jiusi.jsoa.service.pojo.OrganizationPojo;

public class OrgBziImpl implements IOrgBiz {
  private OrgDao orgDao = new OrgDao();
  
  public String deleteOrgByOrgSerial(String orgSerial) {
    return this.orgDao.modifyOrgStatus(orgSerial, 4);
  }
  
  public String recoverOrgByOrgSerial(String orgSerial) {
    return this.orgDao.modifyOrgStatus(orgSerial, 0);
  }
  
  public String disableOrgByOrgSerial(String orgSerial) {
    return this.orgDao.modifyOrgStatus(orgSerial, 1);
  }
  
  public String modifyOrg(OrganizationPojo org, String orderOrgSerial, String tag) {
    return this.orgDao.modifyOrg(org, orderOrgSerial, tag);
  }
  
  public String addOrganization(OrganizationPojo org, String orderOrgSerial, String tag) {
    return this.orgDao.addOrg(org, orderOrgSerial, tag);
  }
}
