package com.js.oa.info.channelmanager.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ChannelEJBLocalHome extends EJBLocalHome {
  ChannelEJBLocal create() throws CreateException;
}
