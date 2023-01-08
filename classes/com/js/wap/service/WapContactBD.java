package com.js.wap.service;

import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.wap.bean.WapContactBean;
import java.util.List;
import java.util.Map;

public class WapContactBD {
  WapContactBean wapContactBean = new WapContactBean();
  
  public Map getPersonalInner(String hql, int beginIndex, int limit) throws Exception {
    Map map = null;
    map = this.wapContactBean.getPersonal(hql, beginIndex, limit);
    return map;
  }
  
  public Map getCommonLinkMan(String hql, int beginIndex, int limit) throws Exception {
    Map map = null;
    map = this.wapContactBean.getPersonal(hql, beginIndex, limit);
    return map;
  }
  
  public PersonPO getPersonPO(String hql) throws Exception {
    PersonPO personPO = new PersonPO();
    personPO = this.wapContactBean.getPersonPO(hql);
    return personPO;
  }
  
  public Map getEmployee(String hql, String id) throws Exception {
    Map map = null;
    map = this.wapContactBean.getEmployee(hql, id);
    return map;
  }
  
  public List getSort(String type, String domainId, String userId) throws Exception {
    List list = null;
    list = this.wapContactBean.getSort(type, domainId, userId);
    return list;
  }
  
  public Map getClassList(String hql, int beginIndex, int limit) throws Exception {
    Map map = null;
    map = this.wapContactBean.getClassList(hql, beginIndex, limit);
    return map;
  }
  
  public String[] getClassMore(String id, String type) throws Exception {
    String[] more = (String[])null;
    more = this.wapContactBean.getClassMore(id, type);
    return more;
  }
  
  public void saveLinkMan(PersonPO po) throws Exception {
    this.wapContactBean.saveLinkMan(po);
  }
  
  public void updateLinkMan(PersonPO po) throws Exception {
    this.wapContactBean.updateLinkMan(po);
  }
  
  public PersonClassPO getClassPO(long id) throws Exception {
    return this.wapContactBean.getClassPO(Long.valueOf(id));
  }
  
  public String[] getRight(String sql) {
    return this.wapContactBean.getRight(sql);
  }
  
  public String getOrg(String sql) {
    return this.wapContactBean.getOrg(sql);
  }
}
