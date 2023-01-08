package com.js.oa.hr.examination.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ExaminationSelfTestEJBLocalHome extends EJBLocalHome {
  ExaminationSelfTestEJBLocal create() throws CreateException;
}
