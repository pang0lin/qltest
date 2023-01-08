package com.js.oa.info.channelmanager.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ChannelEJBHome extends EJBHome {
  ChannelEJB create() throws CreateException, RemoteException;
}
