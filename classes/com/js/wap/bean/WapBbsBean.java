package com.js.wap.bean;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.oa.bbs.po.PersonalStatPO;
import com.js.util.config.SystemCommon;
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

public class WapBbsBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getBbsMap(String hql, int beginIndex, int limit) throws Exception {
    Map<Object, Object> bbsMap = new HashMap<Object, Object>();
    int size = 0;
    List list = null;
    try {
      begin();
      Query query = this.session.createQuery(hql);
      size = query.list().size();
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      Query querySize = this.session.createQuery(hql);
      bbsMap.put("list", list);
      bbsMap.put("size", Integer.valueOf(size));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return bbsMap;
  }
  
  public ForumPO getForumPO(String hql) throws Exception {
    ForumPO forumPO = new ForumPO();
    try {
      begin();
      List<ForumPO> list = this.session.createQuery(hql).list();
      forumPO = list.get(0);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return forumPO;
  }
  
  public Map getForumList(String hql, int beginIndex, int limit) throws Exception {
    Map<Object, Object> bbsMap = new HashMap<Object, Object>();
    int size = 0;
    List<ForumPO> list = null;
    String title = "";
    try {
      begin();
      Query query = this.session.createQuery(hql);
      query.setFirstResult(beginIndex);
      query.setMaxResults(limit);
      list = query.list();
      Query querySize = this.session.createQuery(hql);
      size = querySize.list().size();
      ForumPO forumPO = querySize.list().get(0);
      title = forumPO.getForumTitle();
      Long classId = Long.valueOf(forumPO.getForumClass().getId());
      bbsMap.put("list", list);
      bbsMap.put("size", Integer.valueOf(size));
      bbsMap.put("title", title);
      bbsMap.put("classId", classId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return bbsMap;
  }
  
  public ForumClassPO getClassPO(String hql) throws Exception {
    ForumClassPO forumClassPO = new ForumClassPO();
    try {
      begin();
      Query query = this.session.createQuery(hql);
      forumClassPO = query.list().get(0);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return forumClassPO;
  }
  
  public String save(ForumPO forumPO) throws Exception {
    String retString = "";
    try {
      begin();
      Long nowId = (Long)this.session.save(forumPO);
      retString = nowId.toString();
      ForumPO topicPO = (ForumPO)this.session.load(ForumPO.class, new Long(forumPO.getForumTopicId()));
      topicPO.setForumRevertNum(topicPO.getForumRevertNum() + 1);
      long userId = forumPO.getForumAuthorId();
      List<PersonalStatPO> listStat = this.session.createQuery("select po from com.js.oa.bbs.po.PersonalStatPO po where po.empId=" + userId).list();
      if (listStat.size() <= 0) {
        PersonalStatPO stat = new PersonalStatPO();
        stat.setEmpId(userId);
        stat.setForumNum(1);
        stat.setDomainId(forumPO.getDomainId());
        this.session.save(stat);
      } else {
        PersonalStatPO stat = listStat.get(0);
        stat.setForumNum(1 + stat.getForumNum());
        this.session.update(stat);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return retString;
  }
  
  public int updateReTime(String forumId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    int num = 0;
    try {
      dataSourceBase.begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "update oa_forum set newretime=NOW() where forum_id=" + forumId;
      } else {
        sql = "update oa_forum set newretime=sysdate where forum_id=" + forumId;
      } 
      num = dataSourceBase.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
    return num;
  }
  
  public void forumKit(String id) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    try {
      dataSourceBase.begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "update oa_forum set FORUMKITS=IFNULL(FORUMKITS,0) +1 where forum_id=" + id;
      } else {
        sql = "update oa_forum set FORUMKITS = nvl(FORUMKITS,0)+1 where forum_id= " + id;
      } 
      dataSourceBase.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataSourceBase.end();
    } 
  }
  
  public String getTitle(String id) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String title = "";
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      String databaseType = SystemCommon.getDatabaseType();
      sql = "select forumtitle from OA_FORUM where forum_id=" + id;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next())
        title = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      dataSourceBase.end();
    } 
    return title;
  }
}
