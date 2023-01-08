package com.js.oa.hr.examination.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ExaminationManageEJBLocalHome extends EJBLocalHome {
  ExaminationManageEJBLocal create() throws CreateException;
}
