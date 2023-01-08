package com.js.util.hibernate;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;

public class HibernateBase {
  protected Session session = null;
  
  protected Transaction transaction = null;
  
  private static SessionFactory sf = null;
  
  public void begin() throws HibernateException {
    try {
      if (sf == null) {
        Context ctx = new InitialContext();
        sf = (SessionFactory)ctx.lookup("hibernate");
      } 
      this.session = sf.openSession();
    } catch (HibernateException e) {
      throw e;
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("未发现hibernate");
    } 
  }
  
  public Session getSession() throws Exception {
    if (sf == null) {
      Context ctx = new InitialContext();
      sf = (SessionFactory)ctx.lookup("hibernate");
    } 
    this.session = sf.openSession();
    return this.session;
  }
  
  public Long getTableId() throws HibernateException {
    Long seq = null;
    if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
      Connection conn = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select HIBERNATE_SEQUENCE.Nextval From dual");
        if (rs.next())
          seq = Long.valueOf(rs.getString(1)); 
        rs.close();
        stmt.close();
        conn.close();
      } catch (Exception err) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception eerr) {
            eerr.printStackTrace();
          }  
        err.printStackTrace();
      } 
    } else {
      try {
        Iterator<SeqPO> iter = this.session.createQuery("select po from com.js.util.hibernate.SeqPO po").iterate();
        if (iter.hasNext()) {
          SeqPO seqPO = iter.next();
          seq = Long.valueOf(seqPO.getSeq().longValue() + 1L);
          seqPO.setSeq(seq);
          this.session.update(seqPO);
          this.session.flush();
        } else {
          seq = Long.valueOf("1000");
          SeqPO seqPO = new SeqPO();
          seqPO.setId(Long.valueOf("1"));
          seqPO.setSeq(seq);
          this.session.save(seqPO);
          this.session.flush();
        } 
      } catch (HibernateException e) {
        System.out.println("---------------------------------------------");
        e.printStackTrace();
        System.out.println("---------------------------------------------");
        throw e;
      } 
    } 
    return seq;
  }
  
  public Long getTableId(int scan) throws HibernateException {
    Long seq = null;
    if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
      Connection conn = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        synchronized (this) {
          ResultSet rs = stmt.executeQuery("Select HIBERNATE_SEQUENCE.Nextval From dual");
          if (rs.next())
            seq = Long.valueOf(rs.getString(1)); 
          rs.close();
          for (int i = 0; i < scan; i++)
            stmt.executeQuery("Select HIBERNATE_SEQUENCE.Nextval From dual"); 
        } 
        stmt.close();
        conn.close();
      } catch (Exception err) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception eerr) {
            eerr.printStackTrace();
          }  
        err.printStackTrace();
      } 
    } else {
      try {
        synchronized (this) {
          Iterator<SeqPO> iter = this.session.createQuery("select po from com.js.util.hibernate.SeqPO po").iterate();
          if (iter.hasNext()) {
            SeqPO seqPO = iter.next();
            seq = Long.valueOf(seqPO.getSeq().longValue() + 1L);
            seqPO.setSeq(Long.valueOf(seq.longValue() + scan));
            this.session.update(seqPO);
            this.session.flush();
          } else {
            seq = Long.valueOf("1000");
            SeqPO seqPO = new SeqPO();
            seqPO.setId(Long.valueOf("1"));
            seqPO.setSeq(Long.valueOf(seq.longValue() + scan));
            this.session.save(seqPO);
            this.session.flush();
          } 
        } 
      } catch (HibernateException e) {
        System.out.println("---------------------------------------------");
        e.printStackTrace();
        System.out.println("---------------------------------------------");
        throw e;
      } 
    } 
    return seq;
  }
  
  public void createAll(Collection entities) throws Exception {
    try {
      int i = 0;
      if (entities != null) {
        Iterator it = entities.iterator();
        while (it.hasNext()) {
          Object object = it.next();
          this.session.save(object);
          i++;
          if (i % 20 == 0)
            this.session.clear(); 
          this.session.flush();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getCanReadUserIdsImpl(String userIdString, String orgIdString, String groupIdString) throws Exception {
    String reader = "";
    StringBuffer readerBuffer = new StringBuffer();
    begin();
    try {
      if (!"".equals(userIdString)) {
        reader = userIdString.substring(1, userIdString.length() - 1);
        reader = reader.replaceAll("\\$\\$", ",");
        readerBuffer.append(reader).append(",");
      } 
      if (!"".equals(orgIdString)) {
        String[] orgIdArray = orgIdString.substring(1, orgIdString.length() - 1).split("\\*\\*");
        StringBuffer where = new StringBuffer();
        for (int i = 0; i < orgIdArray.length; i++) {
          if (where.length() > 0) {
            where.append(" or orgIdString like '%$").append(orgIdArray[i]).append("$%'");
          } else {
            where.append(" where orgIdString like '%$").append(orgIdArray[i]).append("$%'");
          } 
        } 
        List<E> list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (select po.orgId from com.js.system.vo.organizationmanager.OrganizationVO po " + where.toString() + ")").list();
        if (list != null && list.size() > 0)
          for (int j = 0; j < list.size(); j++) {
            String empId = list.get(j).toString();
            readerBuffer.append(empId).append(",");
          }  
      } 
      if (!"".equals(groupIdString)) {
        List<E> list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + groupIdString + "' like concat('%@',gr.groupId,'@%')").list();
        if (list != null && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            String empId = list.get(i).toString();
            readerBuffer.append(empId).append(",");
          }  
      } 
      if (readerBuffer.toString().length() > 0)
        reader = readerBuffer.toString().substring(0, readerBuffer.toString().length() - 1); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
    } 
    return reader;
  }
}
