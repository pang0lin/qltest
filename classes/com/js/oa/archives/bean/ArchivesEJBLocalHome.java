package com.js.oa.archives.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ArchivesEJBLocalHome extends EJBLocalHome {
  ArchivesEJBLocal create() throws CreateException;
}
