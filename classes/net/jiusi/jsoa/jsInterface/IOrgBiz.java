package net.jiusi.jsoa.jsInterface;

import OrganizationPojo;

public interface IOrgBiz {
  static {
    throw new Error("Unresolved compilation problems: \n\tThe declared package \"net.jiusi.jsoa.jsinterface\" does not match the expected package \"net.jiusi.jsoa.jsInterface\"\n\tThe import net.jiusi.jsoa.jsinterface cannot be resolved\n\tOrganizationPojo cannot be resolved to a type\n\tOrganizationPojo cannot be resolved to a type\n");
  }
  
  String addOrganization(OrganizationPojo paramOrganizationPojo, String paramString1, String paramString2);
  
  String deleteOrgByOrgSerial(String paramString);
  
  String disableOrgByOrgSerial(String paramString);
  
  String recoverOrgByOrgSerial(String paramString);
  
  String modifyOrg(OrganizationPojo paramOrganizationPojo, String paramString1, String paramString2);
}
