package com.js.wap.service;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.wap.bean.WapBbsBean;
import java.util.Map;

public class WapBbsBD {
  public Map getBbsMap(String hql, int beginIndex, int limit) throws Exception {
    return (new WapBbsBean()).getBbsMap(hql, beginIndex, limit);
  }
  
  public ForumPO getForumPO(String hql) throws Exception {
    return (new WapBbsBean()).getForumPO(hql);
  }
  
  public Map getForumMap(String hql, int beginIndex, int limit) throws Exception {
    return (new WapBbsBean()).getForumList(hql, beginIndex, limit);
  }
  
  public ForumClassPO getClassPO(String hql) throws Exception {
    return (new WapBbsBean()).getClassPO(hql);
  }
  
  public String save(ForumPO forumPO) throws Exception {
    return (new WapBbsBean()).save(forumPO);
  }
  
  public int updateReTime(String forumId) {
    return (new WapBbsBean()).updateReTime(forumId);
  }
  
  public void forumKit(String id) {
    (new WapBbsBean()).forumKit(id);
  }
}
