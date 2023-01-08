package com.js.oa.routine.voiture.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface VoitureReportEJBLocalHome extends EJBLocalHome {
  VoitureReportEJBLocal create() throws CreateException;
}
