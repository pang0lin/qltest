package com.js.oa.hr.officemanager.bean;

import com.js.oa.hr.officemanager.po.PostTitlePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface PostTitleEJB extends EJBObject {
  Integer add(PostTitlePO paramPostTitlePO) throws Exception, RemoteException;
  
  Boolean del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getPostTitle(String paramString) throws Exception, RemoteException;
}
