package com.js.oa.hr.officemanager.bean;

import javax.ejb.EJBObject;

public interface SqlQueryEJB extends EJBObject {
  String[][] query(String paramString);
}
