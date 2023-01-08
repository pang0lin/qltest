package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.StockPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface StockEJBLocal extends EJBLocalObject {
  Boolean save(StockPO paramStockPO) throws Exception;
  
  List getStockIDName(String paramString) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  String[] getSingleStock(String paramString) throws Exception;
  
  Boolean update(StockPO paramStockPO) throws Exception;
  
  String getVindicate(String paramString) throws Exception;
  
  List getUserManaStock(String paramString1, String paramString2) throws Exception;
  
  List getUserManaStock(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getStockIDName(String paramString1, String paramString2) throws Exception;
  
  List getWorkFlowStock(Long paramLong) throws Exception;
  
  List getAllStock(String paramString1, String paramString2) throws Exception;
}
