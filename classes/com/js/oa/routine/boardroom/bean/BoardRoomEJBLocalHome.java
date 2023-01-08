package com.js.oa.routine.boardroom.bean;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface BoardRoomEJBLocalHome extends EJBLocalHome {
  BoardRoomEJBLocal create() throws CreateException;
}
