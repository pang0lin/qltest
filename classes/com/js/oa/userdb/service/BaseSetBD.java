package com.js.oa.userdb.service;

import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.po.BaseSetPO;
import java.util.List;
import org.apache.log4j.Logger;

public class BaseSetBD {
  private static Logger logger = Logger.getLogger(BaseSetBD.class
      .getName());
  
  public Long addBaseType(BaseSetPO po) {
    try {
      return (new BaseSetEJBBean()).save(po);
    } catch (Exception e) {
      e.printStackTrace();
      return Long.valueOf(0L);
    } 
  }
  
  public BaseSetPO load(String baseId) {
    try {
      return (new BaseSetEJBBean()).load(Long.valueOf(baseId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public List loadBaseSet(String baseId) {
    try {
      return (new BaseSetEJBBean()).loadBaseSet(Long.valueOf(baseId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void modi(BaseSetPO po) {
    try {
      (new BaseSetEJBBean()).modi(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void del(String baseId) {
    try {
      (new BaseSetEJBBean()).del(Long.valueOf(baseId));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getOption(String parentId) {
    return (new BaseSetEJBBean()).getOption(parentId);
  }
}
