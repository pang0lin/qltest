package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsPO;
import javax.ejb.EJBLocalObject;

public interface GoodsEJBLocal extends EJBLocalObject {
  Boolean save(GoodsPO paramGoodsPO, String paramString) throws Exception;
  
  String[] getSingleGoods(String paramString) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  Boolean update(GoodsPO paramGoodsPO, String paramString) throws Exception;
  
  String getVindicate(String paramString) throws Exception;
}
