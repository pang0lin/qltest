package com.js.oa.zky.bean;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ZkyMangerEJBLocal extends EJBLocalObject {
  List list(String paramString, Long paramLong) throws Exception;
}
