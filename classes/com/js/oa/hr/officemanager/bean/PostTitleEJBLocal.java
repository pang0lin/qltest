package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.PostTitlePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface PostTitleEJBLocal extends EJBLocalObject {
  Integer add(PostTitlePO paramPostTitlePO) throws Exception;
  
  Boolean del(String[] paramArrayOfString) throws Exception;
  
  List getPostTitle(String paramString) throws Exception;
}
