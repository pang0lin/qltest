package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TrainRecordEJBLocalHome extends EJBLocalHome {
  TrainRecordEJBLocal create() throws CreateException;
}
