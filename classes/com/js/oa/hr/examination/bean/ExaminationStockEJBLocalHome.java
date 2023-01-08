package com.js.oa.hr.examination.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ExaminationStockEJBLocalHome extends EJBLocalHome {
  ExaminationStockEJBLocal create() throws CreateException;
}
