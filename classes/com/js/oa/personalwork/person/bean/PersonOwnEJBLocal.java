package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonPO;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJBLocalObject;

public interface PersonOwnEJBLocal extends EJBLocalObject {
  Vector list(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  PersonPO load(String paramString) throws Exception;
  
  void add(PersonPO paramPersonPO, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  void delBatch(String paramString1, String paramString2) throws Exception;
  
  void delAll(String paramString1, String paramString2) throws Exception;
  
  void update(PersonPO paramPersonPO, String paramString) throws Exception;
  
  Vector see(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List city(String paramString) throws Exception;
  
  List county(String paramString1, String paramString2) throws Exception;
  
  void delAll(String paramString1, String paramString2, String paramString3) throws Exception;
}
