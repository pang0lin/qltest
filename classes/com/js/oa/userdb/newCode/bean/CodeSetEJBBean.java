package com.js.oa.userdb.newCode.bean;

import com.js.oa.userdb.newCode.po.CodeSetPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class CodeSetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public CodeSetPO load(Long id) throws Exception {
    CodeSetPO po = null;
    try {
      begin();
      po = (CodeSetPO)this.session.load(CodeSetPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Long save(CodeSetPO po) throws Exception {
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
  
  public Boolean modi(CodeSetPO po) throws Exception {
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
  
  public Map<String, String> getRelyDate(long codeId) throws Exception {
    Map<String, String> result = new HashMap<String, String>();
    try {
      begin();
      List<Object[]> list = this.session.createQuery("select re.codeSetId,re.codeSetOrder from com.js.oa.userdb.newCode.po.CodeSetPO re where re.codeSetType='d' and re.codeId='" + codeId + "' order by re.codeSetOrder asc").list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          result.put(obj[0].toString(), "第" + obj[1] + "分段");
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public int getMaxOrder(long codeId) throws Exception {
    int cnt = 0;
    try {
      begin();
      List<E> list = this.session.createQuery("select re.codeSetOrder from com.js.oa.userdb.newCode.po.CodeSetPO re where  re.codeId='" + codeId + "' order by re.codeSetOrder desc").list();
      if (list != null && list.size() > 0)
        return Integer.parseInt(list.get(0).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return cnt;
  }
  
  public List<CodeSetPO> getCodeSetList(long codeId) throws Exception {
    List<CodeSetPO> resultList = new ArrayList<CodeSetPO>();
    try {
      begin();
      List<CodeSetPO> list = this.session.createQuery("select re from com.js.oa.userdb.newCode.po.CodeSetPO re where  re.codeId='" + codeId + "' order by re.codeSetOrder asc").list();
      if (list != null && list.size() > 0)
        resultList = list; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return resultList;
  }
  
  public boolean del(long codeSetId) {
    boolean result = false;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery("select code_id,codeSet_type,codeSet_order from jsf_newcode_record where codeSet_id='" + codeSetId + "'");
      rs.next();
      Long codeId = Long.valueOf(rs.getLong("code_id"));
      String codeSetType = rs.getString("codeSet_type");
      int codeSetOrder = rs.getInt("codeSet_order");
      rs.close();
      if (codeSetOrder < getMaxOrder(codeId.longValue()))
        base.executeUpdate("update jsf_newcode_record set codeSet_order=codeSet_order-1 where code_id='" + codeId + "' and codeSet_order > '" + codeSetOrder + "'"); 
      if ("d".equals(codeSetType))
        base.executeUpdate("update jsf_newcode_record set codeSet_isRelyDate='0',codeSet_relySetId='0' where code_id='" + codeId + "' and codeSet_relySetId = '" + codeSetId + "'"); 
      base.executeSQL("delete from jsf_newcode_record where codeSet_id='" + codeSetId + "'");
      base.end();
      result = true;
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
}
