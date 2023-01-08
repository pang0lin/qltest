package com.js.oa.routine.boardroom.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface EquipmentEJBLocalHome extends EJBLocalHome {
  EquipmentEJBLocal create() throws CreateException;
}
