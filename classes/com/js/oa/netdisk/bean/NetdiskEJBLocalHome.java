package com.js.oa.netdisk.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface NetdiskEJBLocalHome extends EJBLocalHome {
  NetdiskEJBLocal create() throws CreateException;
}
