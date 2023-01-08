package com.js.wap.bean;

import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WapNotePaperBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getNotePaperList(String hql, int beginIndex, int limit) throws Exception {
    List list = null;
    Map<Object, Object> map = new HashMap<Object, Object>();
    int size = 0;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      size = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      map.put("list", list);
      map.put("size", Integer.valueOf(size));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public String getNoteContent(String noteId) throws Exception {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String content = "";
    try {
      dataSourceBase.begin();
      long id = Long.valueOf(noteId).longValue();
      String sql = "select notepapercontent from oa_notepaper where notepaper_id=" + id;
      ResultSet rs = dataSourceBase.executeQuery(sql);
      if (rs.next())
        content = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return content;
  }
  
  public void delete(String noteId) throws Exception {
    DataSourceBase dataSourceBase = new DataSourceBase();
    try {
      dataSourceBase.begin();
      long id = Long.valueOf(noteId).longValue();
      String sql = "delete from oa_notepaper where notepaper_id=" + id;
      dataSourceBase.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
  }
}
