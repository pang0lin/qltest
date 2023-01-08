package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TrainClassEJBLocalHome extends EJBLocalHome {
  TrainClassEJBLocal create() throws CreateException;
}
