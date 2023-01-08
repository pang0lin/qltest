package com.js.oa.routine.officeroom.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface OfficeRoomEJBLocalHome extends EJBLocalHome {
  OfficeRoomEJBLocal create() throws CreateException;
}
