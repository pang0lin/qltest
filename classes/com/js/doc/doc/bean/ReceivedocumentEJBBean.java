package com.js.doc.doc.bean;

import com.js.doc.doc.po.ReceiveBaseInfoPO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ReceivedocumentEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Object[] getReceivedocumentBaseInfo(String redHeadId) throws Exception {
    Object[] result = (Object[])null;
    begin();
    try {
      Iterator<Object[]> iter = this.session.iterate("select po.id,po.receiveFileType,po.pigeonholeType, po.decumentKind,po.receiveSecretLevel,  po.urgencyLevel,po.keepTerm,po.seqType,po.receiveDropDownSelect1 ,po.receiveDropDownSelect2  from com.js.doc.doc.po.ReceiveBaseInfoPO  po");
      if (iter.hasNext())
        result = iter.next(); 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveReceiveBaseInfo(ReceiveBaseInfoPO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String updateReceiveBaseInfo(ReceiveBaseInfoPO po) throws Exception {
    begin();
    String result = "0";
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      result = "1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long saveRecSeqInfo(ReceiveFileSeqPO po) throws Exception {
    begin();
    Long result = null;
    try {
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      result = Long.valueOf("-2");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public ReceiveFileSeqPO loadRecSeqPO(String editId) throws Exception {
    begin();
    ReceiveFileSeqPO po = new ReceiveFileSeqPO();
    try {
      po = (ReceiveFileSeqPO)this.session.load(ReceiveFileSeqPO.class, 
          Long.valueOf(editId));
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String updateRecSeqPO(ReceiveFileSeqPO po) throws Exception {
    begin();
    String result = "0";
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      result = "1";
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String deleteRecSeqPO(String Sqlstr) throws Exception {
    begin();
    String result = "0";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "DELETE from JSDB.Doc_ReceiveFileSeq WHERE id in (" + 
          Sqlstr + ")");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      result = "-1";
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public List getRecSeqListByProceId(String provcessId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.find("from com.js.doc.doc.po.ReceiveFileSeqPO po where po.seqProceId= " + 
          provcessId);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getRecSeqListByProceId(String provcessId, String where) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.find("from com.js.doc.doc.po.ReceiveFileSeqPO po where po.seqProceId= " + provcessId + " " + where);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getSeqPoListBySeqClass(String numClass) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      result = this.session.find("from com.js.doc.doc.po.ReceiveFileSeqPO po where po.seqType= '" + 
          numClass + "'");
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
}
