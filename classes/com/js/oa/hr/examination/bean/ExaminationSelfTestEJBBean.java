package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationSelfTestItemPO;
import com.js.oa.hr.examination.po.ExaminationSelfTestPO;
import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ExaminationSelfTestEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(ExaminationSelfTestPO po, Object[] para) throws Exception {
    Long ret = new Long(0L);
    try {
      begin();
      Long id = (Long)this.session.save(po);
      for (int i = 0; i < para.length; i++) {
        String[] itemArr = (String[])para[i];
        ExaminationSelfTestItemPO itempo = 
          new ExaminationSelfTestItemPO();
        itempo.setExaminationSelfTestPO(po);
        itempo.setExaminationID(new Long(itemArr[0]));
        itempo.setExaminationStyle(itemArr[1]);
        itempo.setExaminationNO(itemArr[2]);
        itempo.setExaminationNO2(itemArr[3]);
        itempo.setMyResult(itemArr[4]);
        ExaminationStockPO stockPO = (ExaminationStockPO)this.session.load(
            ExaminationStockPO.class, new Long(itemArr[0]));
        itempo.setExaminationResult(stockPO.getResult());
        if (itemArr[4].equals(stockPO.getResult())) {
          itempo.setIsRight("1");
        } else {
          itempo.setIsRight("0");
        } 
        this.session.save(itempo);
        this.session.flush();
      } 
      ret = id;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public String[] result(Long id) throws Exception {
    String[] ret = new String[3];
    try {
      begin();
      List<Integer> list = this.session.createQuery("select count(*) from com.js.oa.hr.examination.po.ExaminationSelfTestItemPO po  where po.examinationSelfTestPO.selfTestID=" + 



          
          id).list();
      ret[0] = (String)list.get(0);
      list = this.session.createQuery("select count(*) from com.js.oa.hr.examination.po.ExaminationSelfTestItemPO po  where po.examinationSelfTestPO.selfTestID=" + 



          
          id + 
          " and po.examinationStyle in(1,2) ")
        .list();
      ret[1] = (String)list.get(0);
      list = this.session.createQuery("select count(*) from com.js.oa.hr.examination.po.ExaminationSelfTestItemPO po  where po.examinationSelfTestPO.selfTestID=" + 



          
          id + 
          " and po.examinationStyle in(1,2) and po.isRight='1' ")
        .list();
      ret[2] = (String)list.get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public ExaminationSelfTestPO load(Long id) throws Exception {
    ExaminationSelfTestPO po = null;
    try {
      begin();
      po = (ExaminationSelfTestPO)this.session.load(ExaminationSelfTestPO.class, 
          id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean delete(Long id) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      this.session.delete(
          "from com.js.oa.hr.examination.po.ExaminationSelfTestItemPO po  where po.examinationSelfTestPO.selfTestID=" + 
          id);
      this.session.delete(
          "from com.js.oa.hr.examination.po.ExaminationSelfTestPO po  where po.selfTestID=" + 
          id);
      this.session.flush();
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean deleteBatch(String ids) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        this.session.delete(
            "from com.js.oa.hr.examination.po.ExaminationSelfTestItemPO po  where po.examinationSelfTestPO.selfTestID=" + 
            idsArr[i]);
        this.session.delete(
            "from com.js.oa.hr.examination.po.ExaminationSelfTestPO po  where po.selfTestID=" + 
            idsArr[i]);
        this.session.flush();
      } 
      ret = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
}
