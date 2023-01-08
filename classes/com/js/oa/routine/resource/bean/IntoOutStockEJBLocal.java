package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.CsMasterPO;
import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.po.SsMasterPO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface IntoOutStockEJBLocal extends EJBLocalObject {
  Map saveIntoStock(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Map getIntoStock(String paramString) throws Exception;
  
  Boolean updateIntoStock(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Boolean unCheckIntoStock(String paramString) throws Exception;
  
  Boolean deleteIntoStock(String paramString) throws Exception;
  
  Long saveOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Map getOutStock(String paramString) throws Exception;
  
  Boolean updateOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Boolean uncheckOutStock(String paramString) throws Exception;
  
  Boolean deleteOutStock(String paramString1, String paramString2) throws Exception;
  
  List getStockGoods(String paramString) throws Exception;
  
  Boolean saveStockCheck(CsMasterPO paramCsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Map getStockCheck(String paramString) throws Exception;
  
  Boolean updateStockCheck(CsMasterPO paramCsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Boolean uncheckStockCheck(String paramString) throws Exception;
  
  Boolean deleteStockCheck(String paramString) throws Exception;
  
  String getGoodsAmount(String paramString1, String paramString2) throws Exception;
  
  List getWFProcessInfoByStockId(String paramString) throws Exception;
  
  String getStockCharger(String paramString) throws HibernateException;
  
  Long saveDirectOutStock(SsMasterPO paramSsMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Integer updateOutStockCheck(String paramString1, String paramString2) throws Exception;
  
  Map updateOutFlag(String paramString) throws Exception;
  
  Boolean updateIntoStockMoney(PtMasterPO paramPtMasterPO, Object[] paramArrayOfObject) throws Exception;
  
  Integer updateIntoStockCheck(String paramString1, String paramString2) throws Exception;
  
  List getWFProcessInfoByStockId(String paramString1, String paramString2) throws Exception;
}
