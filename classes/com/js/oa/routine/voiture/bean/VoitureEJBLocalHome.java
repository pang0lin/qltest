package com.js.oa.routine.voiture.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface VoitureEJBLocalHome extends EJBLocalHome {
  VoitureEJBLocal create() throws CreateException;
}
