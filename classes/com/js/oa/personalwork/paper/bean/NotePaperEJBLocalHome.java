package com.js.oa.personalwork.paper.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface NotePaperEJBLocalHome extends EJBLocalHome {
  NotePaperEJBLocal create() throws CreateException;
}
