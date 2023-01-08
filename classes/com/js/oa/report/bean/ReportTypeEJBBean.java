package com.js.oa.report.bean;

import com.js.oa.report.po.ReportTypePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

public class ReportTypeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
    this.sessionContext = sessionContext;
  }
  
  public Long saveReportType(ReportTypePO po, String insertSite) throws Exception {
    Long id = null;
    begin();
    try {
      if (0L == po.getParentId().longValue()) {
        String sortCode1 = countSortCode(po.getParentId().toString(), (String)po.getSortCode(), insertSite);
        po.setSortCode(Integer.valueOf(sortCode1));
        po.setParentId(new Long(0L));
        id = (Long)this.session.save(po);
        String classSort = "_" + sortCode1 + "$" + id.toString() + "$";
        po.setOrderCode(classSort);
        this.session.flush();
      } else {
        ReportTypePO poParent = (ReportTypePO)this.session.get(ReportTypePO.class, po.getParentId());
        String sortCode1 = countSortCode(po.getParentId().toString(), (String)po.getSortCode(), insertSite);
        po.setSortCode(Integer.valueOf(sortCode1));
        po.setParentId(poParent.getTypeId());
        id = (Long)this.session.save(po);
        String classSort = String.valueOf(poParent.getOrderCode()) + "_" + sortCode1 + "$" + id.toString() + "$";
        po.setOrderCode(classSort);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return id;
  }
  
  public ReportTypePO loadReportType(Long typeId) throws Exception {
    ReportTypePO po = new ReportTypePO();
    try {
      begin();
      po = (ReportTypePO)this.session.load(ReportTypePO.class, typeId);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public boolean updateReportType(ReportTypePO po, String insertSite) throws Exception {
    boolean flag = false;
    String currentId = String.valueOf(po.getTypeId());
    String oldSortString = po.getOrderCode();
    begin();
    try {
      if (po.getParentId().longValue() == 0L) {
        String sortCode1 = countSortCode(po.getParentId().toString(), (String)po.getSortCode(), insertSite);
        po.setParentId(new Long(0L));
        po.setSortCode(Integer.valueOf(sortCode1));
        String classSort = "_" + sortCode1 + "$" + po.getTypeId() + "$";
        String newSortString = classSort;
        po.setOrderCode(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(po);
        this.session.flush();
      } else {
        ReportTypePO parentPo = (ReportTypePO)this.session.get(ReportTypePO.class, Long.valueOf(po.getParentId().longValue()));
        String sortCode1 = countSortCode(po.getParentId().toString(), (String)po.getSortCode(), insertSite);
        po.setSortCode(Integer.valueOf(sortCode1));
        po.setParentId(parentPo.getTypeId());
        String classSort = String.valueOf(parentPo.getOrderCode()) + "_" + sortCode1 + "$" + po.getTypeId() + "$";
        String newSortString = classSort;
        po.setOrderCode(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
  
  public boolean deleteReportType(String typeId) throws Exception {
    typeId = typeId.endsWith(",") ? typeId.substring(0, typeId.length() - 1) : typeId;
    boolean flag = false;
    try {
      begin();
      this.session.delete("from com.js.oa.report.po.ReportTypePO po where po.typeId in (" + typeId + ") or po.parentId in (" + typeId + ")");
      this.session.delete("from com.js.oa.report.po.ReportPO po where po.reportType in (" + typeId + ")");
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return flag;
  }
  
  public List queryHql(String hql) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery(hql).list();
      this.session.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public int executeUpdate(String sql) {
    DataSourceBase base = new DataSourceBase();
    int num = 0;
    try {
      base.begin();
      num = base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return num;
  }
  
  private String countSortCode(String parentId, String currentSortCode, String insertSite) throws Exception {
    int sortCode = 500000;
    List<E> list = null;
    Query query = null;
    if ("-1".equals(currentSortCode)) {
      sortCode = 500000;
    } else if ("0".equals(insertSite)) {
      query = this.session.createQuery("SELECT MAX(po.sortCode) FROM com.js.oa.report.po.ReportTypePO po WHERE po.parentId=" + 
          parentId + " AND po.sortCode<" + currentSortCode);
      list = query.list();
      if (list == null || (
        list.size() == 1 && (
        "0".equals(list.get(0)) || list.get(0) == null))) {
        sortCode = Integer.parseInt(currentSortCode) - 10000;
      } else {
        sortCode = (Integer.valueOf(list.get(0).toString()).intValue() + Integer.valueOf(currentSortCode).intValue()) / 2;
      } 
    } else {
      query = this.session.createQuery("SELECT MIN(po.sortCode) FROM com.js.oa.report.po.ReportTypePO po WHERE po.parentId=" + 
          parentId + " AND po.sortCode>" + currentSortCode);
      list = query.list();
      if (list == null || (list.size() == 1 && list.get(0) == null)) {
        sortCode = Integer.parseInt(currentSortCode) + 10000;
      } else {
        sortCode = (((Integer)list.get(0)).intValue() + 
          Integer.parseInt(currentSortCode)) / 2;
      } 
    } 
    return sortCode;
  }
  
  public void updateSubClassSort(Session session, String parentId, String oldParentSort, String newParentSort) {
    if (oldParentSort.equals(newParentSort))
      return; 
    try {
      begin();
      Query query = session.createQuery("from com.js.oa.report.po.ReportTypePO po where po.orderCode like '%$" + parentId + "$%'");
      List<ReportTypePO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        ReportTypePO po = list.get(i);
        String classSort = po.getOrderCode();
        classSort = classSort.replace(oldParentSort, newParentSort);
        po.setOrderCode(classSort);
        session.update(po);
        session.beginTransaction().commit();
        session.flush();
      } 
    } catch (HibernateException e) {
      if (session != null)
        try {
          session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public Object[] searchByParentId(Long parentId, Integer sortCode, Long id) {
    Object[] c = { "0", "0" };
    Query query = null;
    List<E> list = null;
    try {
      begin();
      query = this.session.createQuery("SELECT po.sortCode FROM com.js.oa.report.po.ReportTypePO po WHERE po.parentId=" + 
          parentId + " AND po.typeId <>" + id + " order by po.sortCode");
      list = query.list();
      if (list.isEmpty()) {
        c[0] = "0";
        c[1] = "0";
      } else {
        if (Integer.valueOf(list.get(0).toString()).intValue() > sortCode.intValue()) {
          c[0] = "1";
          c[1] = list.get(0).toString();
        } 
        if (Integer.valueOf(list.get(list.size() - 1).toString()).intValue() < sortCode.intValue()) {
          c[0] = "2";
          c[1] = list.get(list.size() - 1).toString();
        } 
        if (Integer.valueOf(list.get(0).toString()).intValue() < sortCode.intValue() && Integer.valueOf(list.get(list.size() - 1).toString()).intValue() > sortCode.intValue())
          for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(list.get(i).toString()).intValue() > sortCode.intValue()) {
              c[0] = "3";
              c[1] = list.get(i).toString();
              break;
            } 
          }  
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return c;
  }
}
