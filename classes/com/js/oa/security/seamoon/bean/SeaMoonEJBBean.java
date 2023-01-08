package com.js.oa.security.seamoon.bean;

import com.js.oa.security.seamoon.po.SecSeaMoon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class SeaMoonEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String saveSeaMoon(SecSeaMoon secSeaMoon) throws Exception {
    String id = null;
    begin();
    try {
      id = (String)this.session.save(secSeaMoon);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return id;
  }
  
  public boolean updateSeaMoon(SecSeaMoon secSeaMoon) throws Exception {
    begin();
    boolean re = true;
    try {
      this.session.update(secSeaMoon);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean deleteSeaMoon(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      ids = "'" + ids.replace(",", "','") + "'";
      this.session.delete("from SecSeaMoon po where po.sn in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public boolean deleteSeaMoonByUserId(String userIds) throws Exception {
    begin();
    boolean re = true;
    try {
      if (userIds.endsWith(","))
        userIds = userIds.substring(0, userIds.length() - 1); 
      String[] idArr = userIds.split(",");
      for (int i = 0; i < idArr.length; i++) {
        if (!"".equals(idArr[i])) {
          Iterator<SecSeaMoon> it = this.session.createQuery("select po from SecSeaMoon po where po.userId='" + idArr[i] + "'").iterate();
          if (it.hasNext()) {
            SecSeaMoon po = it.next();
            po.setUserId("");
            po.setUserName("");
            this.session.flush();
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public SecSeaMoon loadSeaMoon(String sn) throws Exception {
    begin();
    SecSeaMoon osSysRemindPO = null;
    try {
      osSysRemindPO = (SecSeaMoon)this.session.get(SecSeaMoon.class, sn);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return osSysRemindPO;
  }
  
  public boolean updateYourSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stat = conn.createStatement();
      stat.execute(sql);
      stat.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    List list = new ArrayList();
    try {
      begin();
      Query query1 = this.session.createQuery(sql);
      list = query1.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getSeeMoonSNByUserId(String userId) throws Exception {
    String sn = "";
    try {
      begin();
      Iterator<E> it = this.session.createQuery("select po.sn from SecSeaMoon po where po.userId='" + userId + "'").iterate();
      if (it.hasNext())
        sn = it.next().toString(); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return sn;
  }
  
  public String checkUserAndSN(String userId, String sn) throws Exception {
    String re = "-1";
    try {
      begin();
      Iterator it = this.session.createQuery("select po.sn from SecSeaMoon po where po.sn='" + sn + "'").iterate();
      if (!it.hasNext()) {
        re = "-1";
      } else {
        if (userId == null || "null".equals(userId) || "".equals(userId)) {
          it = this.session.createQuery("select po.sn from SecSeaMoon po where po.sn='" + sn + "' and po.userId is not null").iterate();
        } else {
          it = this.session.createQuery("select po.sn from SecSeaMoon po where po.sn='" + sn + "' and po.userId<>'" + userId + "'").iterate();
        } 
        if (it.hasNext()) {
          re = "1";
        } else {
          re = "0";
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public String checkUserAndUkey(String userId, String keySerial) throws Exception {
    String re = "-1";
    try {
      begin();
      Iterator it = this.session.createQuery("select po.keySerial from com.js.system.vo.usermanager.EmployeeVO po where po.keySerial='" + keySerial + "'").iterate();
      if (!it.hasNext()) {
        re = "-1";
      } else {
        if (userId == null || "null".equals(userId) || "".equals(userId)) {
          it = this.session.createQuery("select po.keySerial from com.js.system.vo.usermanager.EmployeeVO po where po.sn='" + keySerial + "' and po.userId is not null").iterate();
        } else {
          it = this.session.createQuery("select po.keySerial from com.js.system.vo.usermanager.EmployeeVO po where po.sn='" + keySerial + "' and po.userId<>'" + userId + "'").iterate();
        } 
        if (it.hasNext()) {
          re = "1";
        } else {
          re = "0";
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return re;
  }
}
