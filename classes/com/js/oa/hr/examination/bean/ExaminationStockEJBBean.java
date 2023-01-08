package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationItemPO;
import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.util.hibernate.HibernateBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Transaction;

public class ExaminationStockEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(ExaminationStockPO po, Object[] para) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      Long id = (Long)this.session.save(po);
      this.session.flush();
      if (!po.getExaminationStyle().equals("3")) {
        String result = ",";
        String[] itemOption = (String[])para[0];
        String[] isResult = (String[])para[1];
        char orderCode = 'A';
        for (int i = 0; i < itemOption.length; i++) {
          ExaminationItemPO itemPO = new ExaminationItemPO();
          itemPO.setExaminationStockPO(po);
          itemPO.setItemOption(itemOption[i]);
          orderCode = (char)(orderCode + 1);
          itemPO.setOrderCode(String.valueOf(orderCode));
          if (isResult[i] != null && isResult[i].equals("1")) {
            itemPO.setIsResult("1");
          } else {
            itemPO.setIsResult("0");
          } 
          Long itemID = (Long)this.session.save(itemPO);
          this.session.flush();
          if (isResult[i] != null && isResult[i].equals("1"))
            result = String.valueOf(result) + itemID + ","; 
        } 
        po.setResult(result);
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
  
  public ExaminationStockPO load(Long id) throws Exception {
    ExaminationStockPO po = null;
    try {
      begin();
      po = (ExaminationStockPO)this.session.load(ExaminationStockPO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean update(ExaminationStockPO po, Object[] para, Long examinationStockID) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      ExaminationStockPO oldPO = (ExaminationStockPO)this.session.load(
          ExaminationStockPO.class, examinationStockID);
      oldPO.setExaminationType(po.getExaminationType());
      oldPO.setSubject(po.getSubject());
      oldPO.setResult(po.getResult());
      this.session.flush();
      if (!oldPO.getExaminationStyle().equals("3")) {
        List<Long> list = this.session.createQuery("select po.itemID from com.js.oa.hr.examination.po.ExaminationItemPO po where po.examinationStockPO.examinationID=" + 



            
            examinationStockID).list();
        String oldItem = ",";
        if (list != null && list.size() > 0)
          for (int k = 0; k < list.size(); k++)
            oldItem = String.valueOf(oldItem) + (Long)list.get(k) + ",";  
        String newItem = ",";
        String result = ",";
        String[] itemOption = (String[])para[0];
        String[] isResult = (String[])para[1];
        String[] itemID = (String[])para[2];
        char orderCode = 'A';
        for (int i = 0; i < itemOption.length; i++) {
          if (itemID[i].equals("0")) {
            ExaminationItemPO itemPO = new ExaminationItemPO();
            itemPO.setExaminationStockPO(oldPO);
            itemPO.setItemOption(itemOption[i]);
            orderCode = (char)(orderCode + 1);
            itemPO.setOrderCode(String.valueOf(orderCode));
            if (isResult[i] != null && isResult[i].equals("1")) {
              itemPO.setIsResult("1");
            } else {
              itemPO.setIsResult("0");
            } 
            Long saveItemID = (Long)this.session.save(itemPO);
            this.session.flush();
            if (isResult[i] != null && isResult[i].equals("1"))
              result = String.valueOf(result) + saveItemID + ","; 
            newItem = String.valueOf(newItem) + saveItemID + ",";
          } else {
            ExaminationItemPO itemPO = (ExaminationItemPO)this.session
              .load(ExaminationItemPO.class, new Long(itemID[i]));
            itemPO.setItemOption(itemOption[i]);
            orderCode = (char)(orderCode + 1);
            itemPO.setOrderCode(String.valueOf(orderCode));
            if (isResult[i] != null && isResult[i].equals("1")) {
              itemPO.setIsResult("1");
            } else {
              itemPO.setIsResult("0");
            } 
            this.session.flush();
            if (isResult[i] != null && isResult[i].equals("1"))
              result = String.valueOf(result) + itemID[i] + ","; 
            newItem = String.valueOf(newItem) + itemID[i] + ",";
          } 
        } 
        oldPO.setResult(result);
        this.session.flush();
        oldItem = oldItem.substring(1, oldItem.length() - 1);
        String[] needDeleteItem = oldItem.split(",");
        for (int j = 0; j < needDeleteItem.length; j++) {
          if (newItem.indexOf("," + needDeleteItem[j] + ",") < 0) {
            ExaminationItemPO itemPO = (ExaminationItemPO)this.session
              .load(ExaminationItemPO.class, 
                new Long(needDeleteItem[j]));
            this.session.delete(itemPO);
            this.session.flush();
          } 
        } 
      } 
      ret = Boolean.TRUE;
      ExaminationStockPO newPO = (ExaminationStockPO)this.session.load(ExaminationStockPO.class, examinationStockID);
      Transaction tx = this.session.beginTransaction();
      Connection conn = this.session.connection();
      PreparedStatement stmtQuery = conn.prepareStatement("select examinationresult from oa_examination_answer_item where examinationid=" + examinationStockID);
      ResultSet rs = stmtQuery.executeQuery();
      String oldResult = "";
      PreparedStatement stmt = null;
      if (rs.next())
        oldResult = rs.getString(1); 
      if (!oldResult.equals(newPO.getResult())) {
        stmt = conn.prepareStatement("update oa_examination_answer_item set examinationresult='" + newPO.getResult() + "',isright=0 where examinationid=" + examinationStockID);
        stmt.executeUpdate();
      } 
      tx.commit();
      PreparedStatement stmtUpdate = conn.prepareStatement("update oa_examination_answer_item set mark=fullmark,isright='1' where  examinationid=" + examinationStockID + " and examinationresult=myresult");
      PreparedStatement stmtUpdate1 = conn.prepareStatement("update oa_examination_answer_item set mark=0,isright='0' where  examinationid=" + examinationStockID + " and examinationresult!=myresult");
      stmtUpdate.executeUpdate();
      stmtUpdate1.executeUpdate();
      stmtQuery.close();
      stmt.close();
      stmtUpdate.close();
      stmtUpdate1.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean delete(Long examinationID) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      List<Long> list = this.session.createQuery("select po from  com.js.oa.hr.examination.po.ExaminationManagePO po  where po.radioIds like '%," + 


          
          examinationID + ",%'" + 
          " or po.checkIds like '%," + 
          examinationID + ",%'" + 
          " or po.questionIds like '%," + 
          examinationID + ",%'").list();
      if (list != null && list.size() > 0)
        return ret; 
      ExaminationStockPO po = (ExaminationStockPO)this.session.get(
          ExaminationStockPO.class, examinationID);
      if (po != null) {
        list = this.session.createQuery("select po.itemID from com.js.oa.hr.examination.po.ExaminationItemPO po where po.examinationStockPO.examinationID=" + 



            
            examinationID).list();
        if (list != null && list.size() > 0)
          for (int i = 0; i < list.size(); i++) {
            ExaminationItemPO itemPO = (ExaminationItemPO)this.session
              .load(ExaminationItemPO.class, list.get(i));
            this.session.delete(itemPO);
            this.session.flush();
          }  
        this.session.delete(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return ret;
  }
  
  public Boolean deleteBatch(String ids) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        List<Long> list = this.session.createQuery("select po from  com.js.oa.hr.examination.po.ExaminationManagePO po  where po.radioIds like '%," + 


            
            idsArr[i] + ",%'" + 
            " or po.checkIds like '%," + 
            idsArr[i] + ",%'" + 
            " or po.questionIds like '%," + 
            idsArr[i] + ",%'").list();
        if (list != null && list.size() > 0) {
          ret = Boolean.FALSE;
        } else {
          ExaminationStockPO po = (ExaminationStockPO)this.session.get(
              ExaminationStockPO.class, new Long(idsArr[i]));
          if (po != null) {
            list = this.session.createQuery("select po.itemID from com.js.oa.hr.examination.po.ExaminationItemPO po where po.examinationStockPO.examinationID=" + 



                
                idsArr[i]).list();
            if (list != null && list.size() > 0)
              for (int j = 0; j < list.size(); j++) {
                ExaminationItemPO itemPO = 
                  (ExaminationItemPO)this.session
                  .load(ExaminationItemPO.class, 
                    list.get(j));
                this.session.delete(itemPO);
                this.session.flush();
              }  
            this.session.delete(po);
            this.session.flush();
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
  
  public Boolean move(String ids, String sign) throws Exception {
    Boolean ret = Boolean.TRUE;
    try {
      begin();
      String[] idsArr = ids.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        ExaminationStockPO po = (ExaminationStockPO)this.session.get(
            ExaminationStockPO.class, new Long(idsArr[i]));
        if (po != null) {
          po.setSign(sign.equals("0") ? "1" : "0");
          this.session.flush();
        } 
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
  
  public String[] getStockArr(String style, String type, String sign, Long domainId) throws Exception {
    String[] stockArr = (String[])null;
    try {
      begin();
      String whereSQL = "where po.domainId=" + domainId + 
        " and po.examinationStyle=" + style;
      if (!type.equals("0"))
        whereSQL = String.valueOf(whereSQL) + " and po.examinationType = " + type; 
      if (!sign.equals("-1"))
        whereSQL = String.valueOf(whereSQL) + " and po.sign= " + sign; 
      String stockIds = "";
      List<Long> list = this.session.createQuery("select po.examinationID from com.js.oa.hr.examination.po.ExaminationStockPO po " + 

          
          whereSQL).list();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++)
          stockIds = String.valueOf(stockIds) + (Long)list.get(i) + ",";  
      if (stockIds.length() > 0) {
        stockIds = stockIds.substring(0, stockIds.length() - 1);
        stockArr = stockIds.split(",");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return stockArr;
  }
}
