package com.js.oa.info.templatemanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TemplateEJBLocalHome extends EJBLocalHome {
  TemplateEJBLocal create() throws CreateException;
}
