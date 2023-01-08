package com.js.oa.userdb.newCode.service;

import com.js.oa.userdb.newCode.bean.NewCodeEJBBean;
import com.js.oa.userdb.newCode.po.NewCodePO;
import org.apache.log4j.Logger;

public class NewCodeBD {
  private static Logger logger = Logger.getLogger(NewCodeBD.class
      .getName());
  
  public Long addNewCode(NewCodePO po) {
    try {
      return (new NewCodeEJBBean()).save(po);
    } catch (Exception e) {
      e.printStackTrace();
      return Long.valueOf(0L);
    } 
  }
  
  public NewCodePO load(String codeId) {
    try {
      return (new NewCodeEJBBean()).load(Long.valueOf(codeId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void modi(NewCodePO po) {
    try {
      (new NewCodeEJBBean()).modi(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void del(String codeId) {
    try {
      (new NewCodeEJBBean()).del(Long.valueOf(codeId));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
