package com.js.system.bean.rssmanager;

import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.CategoryVO;
import com.js.system.vo.rssmanager.ChannelInfoVO;
import com.js.system.vo.rssmanager.ChannelItemVO;
import com.js.system.vo.rssmanager.ChannelOrderVO;
import com.js.system.vo.rssmanager.ItemStateVO;
import com.js.util.hibernate.HibernateBase;
import java.util.Collection;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class RssEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delCategory(String categoryId, String[] categoryIds) throws Exception {
    begin();
    try {
      if (categoryId != null && !categoryId.equals("")) {
        this.session.delete("from CategoryVO rss where rss.categoryId=" + categoryId);
        this.session.delete("from CategoryChannelVO rss where rss.categoryId=" + categoryId);
      } 
      if (categoryIds != null && categoryIds.length > 0) {
        StringBuffer hql = new StringBuffer();
        for (int i = 0; i < categoryIds.length; i++)
          hql.append(String.valueOf(categoryIds[i]) + ","); 
        this.session.delete("from CategoryVO rss where rss.categoryId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
        this.session.delete("from CategoryChannelVO rss where rss.categoryId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void delChannel(String channelId, String[] channelIds) throws Exception {
    begin();
    try {
      if (channelId != null && !channelId.equals("")) {
        this.session.delete("from CategoryChannelVO rss where rss.channelId=" + channelId);
        this.session.delete("from ChannelItemVO cio where cio.channelId=" + channelId);
        this.session.delete("from ChannelOrderVO cio where cio.channelId=" + channelId);
      } 
      if (channelIds != null && channelIds.length > 0) {
        StringBuffer hql = new StringBuffer();
        for (int i = 0; i < channelIds.length; i++)
          hql.append(String.valueOf(channelIds[i]) + ","); 
        this.session.delete("from CategoryChannelVO rss where rss.channelId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
        this.session.delete("from ChannelItemVO cio where cio.channelId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
        this.session.delete("from ChannelOrderVO cio where cio.channelId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void delAllCategory() throws Exception {
    begin();
    try {
      this.session.delete("from CategoryVO");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAllChannel() throws Exception {
    begin();
    try {
      this.session.delete("from CategoryChannelVO");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getRssCategoryList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from CategoryVO");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getRssChannelList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from CategoryChannelVO");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public CategoryVO getSingleRssCategory(String categoryId) throws Exception {
    begin();
    CategoryVO rssVO = null;
    try {
      Query query = this.session.createQuery("from CategoryVO rss where rss.categoryId=" + categoryId);
      rssVO = (CategoryVO)query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return rssVO;
  }
  
  public List getSingleRssCategoryByName(String name) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from CategoryVO rss where rss.categoryName='" + name + "'");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public CategoryChannelVO getSingleRssChannel(String channelId) throws Exception {
    begin();
    CategoryChannelVO rssChannelVO = null;
    try {
      Query query = this.session.createQuery("from CategoryChannelVO rss where rss.channelId=" + channelId);
      rssChannelVO = (CategoryChannelVO)query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return rssChannelVO;
  }
  
  public List getSingleRssChannelByName(String channelName) throws Exception {
    return getSingleRssChannelByName(channelName, "");
  }
  
  public List getSingleRssChannelByName(String channelName, String channelId) throws Exception {
    begin();
    List list = null;
    try {
      String hql = "";
      if ("".equals(channelId)) {
        hql = "from CategoryChannelVO rss where rss.channelName='" + channelName + "'";
      } else {
        hql = "from CategoryChannelVO rss where rss.channelName='" + channelName + "' and rss.channelId<>" + channelId;
      } 
      Query query = this.session.createQuery(hql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void saveOrUpdateRssCategory(CategoryVO rssVO) throws Exception {
    begin();
    try {
      this.session.saveOrUpdate(rssVO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void saveOrUpdateRssChannel(CategoryChannelVO rssChannelVO) throws Exception {
    begin();
    try {
      this.session.saveOrUpdate(rssChannelVO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void saveChannelInfo(ChannelInfoVO channelInfoVO) throws Exception {
    begin();
    try {
      this.session.save(channelInfoVO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void delChannelInfo() throws Exception {
    begin();
    try {
      this.session.delete("from ChannelInfoVO");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void saveChannelItem(Collection entities) throws Exception {
    begin();
    try {
      if (entities != null && entities.size() > 0)
        createAll(entities); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void delChannelItem(String channelId) throws Exception {
    begin();
    try {
      this.session.delete("from ChannelItemVO cio where cio.channelId=" + channelId);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void orderRss(ChannelOrderVO cov) throws Exception {
    begin();
    try {
      this.session.save(cov);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List getMyRssList(String userId) throws Exception {
    begin();
    List list = null;
    try {
      list = this.session.createQuery("from ChannelOrderVO po where po.userId=" + userId).list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getChannelItemList(String channelId) throws Exception {
    begin();
    List list = null;
    try {
      list = this.session.createQuery("from ChannelItemVO po where po.channelId=" + channelId).list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void updateItemReadState(String itemId) throws Exception {
    begin();
    try {
      ChannelItemVO cc = (ChannelItemVO)this.session.load(ChannelItemVO.class, Long.valueOf(itemId));
      if (cc.getReadState().equals("0")) {
        cc.setItemId(Long.valueOf(itemId));
        cc.setReadState("1");
        this.session.saveOrUpdate(cc);
        this.session.flush();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void removeRssOrder(String channelId, String userId) throws Exception {
    begin();
    try {
      this.session.delete("from ChannelOrderVO cio where cio.userId=" + userId + " and cio.channelId=" + channelId);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List getReadedItemList(String userId) throws Exception {
    begin();
    List list = null;
    try {
      list = this.session.createQuery("from ItemStateVO cio where cio.userId=" + userId).list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void saveOrUpdateItem(ItemStateVO cio) throws Exception {
    begin();
    try {
      List list = this.session.createQuery("from ItemStateVO cio where cio.itemId=" + cio.getItemId() + " and cio.userId=" + cio.getUserId()).list();
      if (list == null || list.size() <= 0) {
        this.session.saveOrUpdate(cio);
        this.session.flush();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
}
