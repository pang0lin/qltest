package com.js.oa.hr.subsidiarywork.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface QuestionnaireEJBLocalHome extends EJBLocalHome {
  QuestionnaireEJBLocal create() throws CreateException;
}
