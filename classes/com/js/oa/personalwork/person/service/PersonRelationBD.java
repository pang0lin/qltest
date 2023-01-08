package com.js.oa.personalwork.person.service;

import com.js.oa.personalwork.person.bean.PersonRelationEJBBean;
import java.util.List;
import java.util.Map;

public class PersonRelationBD {
  public Map getRelationEmpMap(String userId, String empId, String orgId, String orgIdString, String domainId, List underEmpList, String whereDoc) throws Exception {
    Map map = null;
    try {
      PersonRelationEJBBean personRelationBean = new PersonRelationEJBBean();
      map = personRelationBean.getRelationList(userId, empId, orgId, orgIdString, domainId, underEmpList, whereDoc);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
}
