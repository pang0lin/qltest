package com.js.oa.message.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface messageSettingLocalHome extends EJBLocalHome {
  messageSettingLocal create() throws CreateException;
}
