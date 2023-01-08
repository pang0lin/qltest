package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.GoodsTypePO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface GoodsTypeEJBLocal extends EJBLocalObject {
  Boolean save(GoodsTypePO paramGoodsTypePO, String paramString) throws Exception;
  
  String[] getSingleGoodsType(String paramString) throws Exception;
  
  Boolean update(GoodsTypePO paramGoodsTypePO, String paramString) throws Exception;
  
  Boolean delete(String paramString) throws Exception;
  
  String getVindicate(String paramString) throws Exception;
  
  List getUserManaGoodsType(String paramString) throws Exception;
  
  List getUserManaGoodsTypeParent(String paramString) throws Exception;
  
  List getUserManaGoodsTypeSub(String paramString1, String paramString2) throws Exception;
}
