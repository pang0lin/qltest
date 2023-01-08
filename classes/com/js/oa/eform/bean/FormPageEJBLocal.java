package com.js.oa.eform.bean;

import com.js.oa.eform.po.TPagePO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface FormPageEJBLocal extends EJBLocalObject {
  Long save(TPagePO paramTPagePO) throws HibernateException;
  
  List search(String paramString1, String paramString2) throws HibernateException;
  
  Boolean delete(String paramString) throws Exception;
  
  Boolean update(TPagePO paramTPagePO) throws Exception;
  
  List getSingleForm(String paramString) throws HibernateException;
  
  List getFormBaseInfo(String paramString) throws HibernateException;
  
  String getSelectedSubField(String paramString) throws Exception;
  
  TPagePO getPageFromPageId(String paramString) throws Exception;
}
