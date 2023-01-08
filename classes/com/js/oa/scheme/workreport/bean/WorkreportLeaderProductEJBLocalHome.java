package com.js.oa.scheme.workreport.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface WorkreportLeaderProductEJBLocalHome extends EJBLocalHome {
  WorkreportLeaderProductEJBLocal create() throws CreateException;
}
