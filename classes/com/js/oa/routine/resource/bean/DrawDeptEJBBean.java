package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.DrawDeptPO;
import com.js.oa.routine.resource.po.StockPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class DrawDeptEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(DrawDeptPO drawDeptPO, String stockId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where aaa.name='" + 
          drawDeptPO.getName() + "' and bbb.id=" + stockId);
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        success = Boolean.FALSE;
      } else {
        StockPO stockPO = (StockPO)this.session.load(StockPO.class, new Long(stockId));
        drawDeptPO.setStock(stockPO);
        this.session.save(drawDeptPO);
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean delete(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      this.session.delete("from com.js.oa.routine.resource.po.DrawDeptPO aaa where aaa.id in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String[] getSingleDept(String drawDeptId) throws Exception {
    String[] drawDept = { "", "", "" };
    begin();
    try {
      Query query = this.session.createQuery("select aaa.name,bbb.id,aaa.remark from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where aaa.id=" + 
          drawDeptId);
      Iterator<Object[]> iter = query.iterate();
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        drawDept[0] = obj[0].toString();
        drawDept[1] = obj[1].toString();
        drawDept[2] = obj[2].toString();
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return drawDept;
  }
  
  public Boolean update(DrawDeptPO drawDeptPO, String stockId) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where bbb.id=" + 
          stockId + " and aaa.name='" + drawDeptPO.getName() + "' and aaa.id<>" + 
          drawDeptPO.getId());
      Iterator iter = query.iterate();
      if (iter.hasNext()) {
        success = Boolean.FALSE;
      } else {
        StockPO stockPO = (StockPO)this.session.load(StockPO.class, new Long(stockId));
        DrawDeptPO needModifyPO = (DrawDeptPO)this.session.load(DrawDeptPO.class, drawDeptPO.getId());
        needModifyPO.setStock(stockPO);
        needModifyPO.setName(drawDeptPO.getName());
        needModifyPO.setRemark(drawDeptPO.getRemark());
        this.session.flush();
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public String getVindicate(String where) throws Exception {
    StringBuffer ids = new StringBuffer();
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id from com.js.oa.routine.resource.po.DrawDeptPO aaa where " + 
          where);
      Iterator<E> itor = query.iterate();
      while (itor.hasNext())
        ids.append("$" + itor.next().toString() + "$"); 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ids.toString();
  }
  
  public List getDeptInStock(String stockId) throws Exception {
    ArrayList deptList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select aaa.id,aaa.name from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where bbb.id=" + 
          stockId);
      deptList = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return deptList;
  }
  
  public List getUserManaDept(String userId) throws Exception {
    begin();
    ArrayList alist = new ArrayList();
    try {
      Query query = this.session.createQuery("select bbb.id,aaa.id,aaa.name from com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb where bbb.stockPut like '%$" + 
          userId + "$%'");
      alist = (ArrayList)query.list();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
}
