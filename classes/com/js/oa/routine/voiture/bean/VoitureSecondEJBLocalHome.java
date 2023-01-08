package com.js.oa.routine.voiture.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface VoitureSecondEJBLocalHome extends EJBLocalHome {
  VoitureSecondEJBLocal create() throws CreateException;
}
