package com.js.oa.zky.bean;

import com.js.oa.zky.po.ZkyMangersPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceUtil;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ZkyMangerEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List list(String mode, Long domainID) throws Exception {
    String sql = "select a.id,b.menu_name||'.'||a.menu_name from MENU_EXT a join MENU_EXT b on b.Id=a.menu_blone where a.menu_zkytype=" + mode + " order by a.menu_level";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    return list;
  }
  
  public Boolean save(ZkyMangersPO po) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public ZkyMangersPO moudleMangerLoad(Long id) throws Exception {
    ZkyMangersPO po = new ZkyMangersPO();
    try {
      begin();
      po = (ZkyMangersPO)this.session.load(ZkyMangersPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean moudleMangerUpdate(ZkyMangersPO po, Long id) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      ZkyMangersPO updatePO = (ZkyMangersPO)this.session.load(ZkyMangersPO.class, id);
      updatePO.setMangerMenu(po.getMangerMenu());
      updatePO.setMangerMenuName(po.getMangerMenuName());
      updatePO.setMangerUsername(po.getMangerUsername());
      updatePO.setMangerUsers(po.getMangerUsers());
      updatePO.setMangerType(po.getMangerType());
      this.session.update(updatePO);
      this.session.flush();
    } catch (Exception e) {
      ret = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public boolean deleteMoudleManger(String id) throws Exception {
    boolean result = false;
    try {
      begin();
      this.session.delete("from com.js.oa.zky.po.ZkyMangersPO po where po.mangerId in (" + id + ")");
      this.session.flush();
      this.session.close();
      result = true;
    } catch (Exception err) {
      this.session.close();
      throw err;
    } 
    return result;
  }
}
