package com.js.oa.hr.examination.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ExaminationTypeEJBLocalHome extends EJBLocalHome {
  ExaminationTypeEJBLocal create() throws CreateException;
}
