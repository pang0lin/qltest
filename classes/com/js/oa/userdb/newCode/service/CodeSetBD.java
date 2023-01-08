package com.js.oa.userdb.newCode.service;

import com.js.oa.userdb.newCode.bean.CodeSetEJBBean;
import com.js.oa.userdb.newCode.po.CodeSetPO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class CodeSetBD {
  private static Logger logger = Logger.getLogger(CodeSetBD.class
      .getName());
  
  public Long addCodeSet(CodeSetPO po) {
    try {
      return (new CodeSetEJBBean()).save(po);
    } catch (Exception e) {
      e.printStackTrace();
      return Long.valueOf(0L);
    } 
  }
  
  public CodeSetPO load(String codeSetId) {
    try {
      return (new CodeSetEJBBean()).load(Long.valueOf(codeSetId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void modi(CodeSetPO po) {
    try {
      (new CodeSetEJBBean()).modi(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void del(String codeSetId) {
    try {
      (new CodeSetEJBBean()).del(Long.valueOf(codeSetId).longValue());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public Map<String, String> getRelyDate(String codeId) {
    try {
      return (new CodeSetEJBBean()).getRelyDate(Long.valueOf(codeId).longValue());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public int getMaxOrder(String codeId) {
    try {
      int cnt = (new CodeSetEJBBean()).getMaxOrder(Long.valueOf(codeId).longValue());
      return cnt + 1;
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    } 
  }
  
  public List<CodeSetPO> getCodeSetList(String codeId) {
    try {
      return (new CodeSetEJBBean()).getCodeSetList(Long.valueOf(codeId).longValue());
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<CodeSetPO>();
    } 
  }
}
