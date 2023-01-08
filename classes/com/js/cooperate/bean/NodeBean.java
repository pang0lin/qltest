package com.js.cooperate.bean;

import com.js.cooperate.po.TemplatePO;
import com.js.util.hibernate.HibernateBase;

public class NodeBean extends HibernateBase {
  public Integer add(TemplatePO templatePO) throws Exception {
    int result = 0;
    begin();
    try {
      this.session.save(templatePO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      result = 2;
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
}
