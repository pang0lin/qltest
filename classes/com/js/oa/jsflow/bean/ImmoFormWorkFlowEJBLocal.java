package com.js.oa.jsflow.bean;

import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface ImmoFormWorkFlowEJBLocal extends EJBLocalObject {
  String[] getCommPO(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Map getComment(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getImmoFormRealName(String paramString) throws Exception;
}
