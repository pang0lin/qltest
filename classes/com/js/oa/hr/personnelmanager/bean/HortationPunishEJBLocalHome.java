package com.js.oa.hr.personnelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface HortationPunishEJBLocalHome extends EJBLocalHome {
  HortationPunishEJBLocal create() throws CreateException;
}
