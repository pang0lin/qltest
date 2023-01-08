package com.js.oa.userdb.bean;

import com.js.oa.userdb.po.BaseSetPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class BaseSetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public BaseSetPO load(Long id) throws Exception {
    BaseSetPO po = null;
    try {
      begin();
      po = (BaseSetPO)this.session.load(BaseSetPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public List loadBaseSet(Long id) throws Exception {
    String sql = "select base from com.js.oa.userdb.po.BaseSetPO base where base.baseParent='" + id + "' order by " + 
      "base.baseOrder ,base.baseId";
    List list = null;
    try {
      begin();
      list = this.session.find(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Long save(BaseSetPO po) throws Exception {
    Long result = new Long(-1L);
    try {
      begin();
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean del(Long id) throws Exception {
    Boolean result = new Boolean(false);
    BaseSetPO po = null;
    String sql = "select base from com.js.oa.userdb.po.BaseSetPO base where base.baseParent='" + id + "'";
    try {
      begin();
      po = (BaseSetPO)this.session.load(BaseSetPO.class, id);
      List<BaseSetPO> list = this.session.find(sql);
      if (po != null)
        this.session.delete(po); 
      for (int i = 0; i < list.size(); i++) {
        BaseSetPO bpo = list.get(i);
        this.session.delete(bpo);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean modi(BaseSetPO po) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String getOption(String parentId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String result = "";
    try {
      base.begin();
      String sql = "SELECT base_id,base_name FROM tbase WHERE base_parent='0'";
      rs = base.executeQuery(sql);
      while (rs.next())
        result = String.valueOf(result) + "<option value='" + rs.getString(1) + "' " + (!parentId.equals(rs.getString(1)) ? "" : "selected") + ">" + rs.getString(2) + "</option>"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getValue(String parentId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String result = "";
    try {
      base.begin();
      String sql = "SELECT base_key,base_value FROM tbase WHERE base_parent=" + parentId + " ORDER BY base_order";
      rs = base.executeQuery(sql);
      while (rs.next())
        result = String.valueOf(result) + rs.getString(2) + "/" + rs.getString(1) + ";"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return result;
  }
}
