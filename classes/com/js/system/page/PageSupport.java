package com.js.system.page;

import com.js.util.hibernate.HibernateBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.Query;

public class PageSupport extends HibernateBase {
  public Map getResult(String para, String PO, String where, Integer PageSize, Integer CurrentPage) throws Exception {
    List list = null;
    int pageSize = PageSize.intValue();
    int currentPage = CurrentPage.intValue();
    Map<Object, Object> map = new HashMap<Object, Object>();
    int beginRecord = pageSize * (currentPage - 1);
    StringBuffer queryBuffer = new StringBuffer();
    StringBuffer queryCount = new StringBuffer();
    queryCount.append("SELECT COUNT(*) FROM ");
    queryCount.append(PO);
    queryCount.append(" ");
    int p = where.toUpperCase().indexOf("ORDER BY");
    if (p >= 0) {
      queryCount.append(where.substring(0, p));
    } else {
      queryCount.append(where);
    } 
    queryBuffer.append("SELECT ");
    queryBuffer.append(para);
    queryBuffer.append(" FROM ");
    queryBuffer.append(PO);
    queryBuffer.append(" ");
    queryBuffer.append(where);
    begin();
    try {
      int recordCount;
      Query query = null;
      if (queryCount.toString().toUpperCase().indexOf(" GROUP BY ") > 0) {
        recordCount = this.session.createQuery(queryCount.toString()).list().size();
      } else if (para.toUpperCase().indexOf("DISTINCT") >= 0) {
        recordCount = this.session.createQuery(queryBuffer.toString()).list().size();
      } else {
        recordCount = ((Integer)this.session.iterate(queryCount.toString())
          .next()).intValue();
      } 
      map.put("recordCount", new Integer(recordCount));
      query = this.session.createQuery(queryBuffer.toString());
      query.setFirstResult(beginRecord);
      query.setMaxResults(pageSize);
      list = query.list();
      map.put("list", list);
    } catch (Exception e) {
      this.session.close();
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return map;
  }
}
