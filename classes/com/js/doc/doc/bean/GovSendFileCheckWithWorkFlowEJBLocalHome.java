package com.js.doc.doc.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface GovSendFileCheckWithWorkFlowEJBLocalHome extends EJBLocalHome {
  GovSendFileCheckWithWorkFlowEJBLocal create() throws CreateException;
}
