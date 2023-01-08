package com.js.oa.routine.voiture.bean;

import com.js.oa.routine.voiture.po.VoitureFeePO;
import com.js.oa.routine.voiture.po.VoiturePO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class VoitureSecondEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Integer voitureFeeAdd(VoitureFeePO po, Long voitureId) throws Exception {
    begin();
    Integer result = new Integer(1);
    try {
      VoiturePO po1 = (VoiturePO)this.session.load(VoiturePO.class, voitureId);
      po.setVoiturePO(po1);
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List listVoitureFeeDetail(Long voitureId) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String where = " where b.id=" + voitureId;
      list = this.session.createQuery("select a.id,a.maintainTime,b.name,b.num from com.js.oa.routine.voiture.po.VoitureFeePO a join a.voiturePO b  " + 
          
          where + " order by a.maintainTime desc").list();
    } catch (Exception e) {
      System.out.println("------------------------");
      e.printStackTrace();
      System.out.println("------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void delVoitureFeeBatch(String ids) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.routine.voiture.po.VoitureFeePO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void delVoitureFee(Long voitureFeeId) throws Exception {
    try {
      begin();
      this.session.delete(
          " from com.js.oa.routine.voiture.po.VoitureFeePO po where po.id=" + voitureFeeId);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("-----------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List listModifyVoitureFee(Long voitureFeeId) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String where = " where a.id=" + voitureFeeId;
      list = this.session.createQuery("select a.id,a.maintainTime,a.oilCost,a.oilFee,a.fixFee,a.washFee,a.taxFee,a.insureFee,a.roadFee,a.yearTicketFee,a.yearSensorFee,a.purchaseTax,a.brandFee,a.accidentFee,a.otherFee,a.remark,b.id,a.isMaintain,a.monthFee,a.jsy,a.fwld,a.sylc,a.bylc,a.xslc,a.yy,a.bglhy  from com.js.oa.routine.voiture.po.VoitureFeePO a join a.voiturePO b  " + 



          
          where).list();
    } catch (Exception e) {
      System.out.println("------------------------");
      e.printStackTrace();
      System.out.println("------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Integer updateVoitureFee(VoitureFeePO paraPO, Long voitureId) throws Exception {
    begin();
    Integer update_info = new Integer(1);
    try {
      VoiturePO po1 = (VoiturePO)this.session.load(VoiturePO.class, voitureId);
      VoitureFeePO po = (VoitureFeePO)this.session.load(VoitureFeePO.class, paraPO.getId());
      po.setAccidentFee(paraPO.getAccidentFee());
      po.setBrandFee(paraPO.getBrandFee());
      po.setFixFee(paraPO.getFixFee());
      po.setInsureFee(paraPO.getInsureFee());
      po.setMaintainTime(paraPO.getMaintainTime());
      po.setOilCost(paraPO.getOilCost());
      po.setOilFee(paraPO.getOilFee());
      po.setOtherFee(paraPO.getOtherFee());
      po.setPurchaseTax(paraPO.getPurchaseTax());
      po.setRemark(paraPO.getRemark());
      po.setRoadFee(paraPO.getRoadFee());
      po.setTaxFee(paraPO.getTaxFee());
      po.setVoiturePO(po1);
      po.setWashFee(paraPO.getWashFee());
      po.setYearSensorFee(paraPO.getYearSensorFee());
      po.setYearTicketFee(paraPO.getYearTicketFee());
      po.setMonthFee(paraPO.getMonthFee());
      po.setIsMaintain(paraPO.getIsMaintain());
      po.setJsy(paraPO.getJsy());
      po.setFwld(paraPO.getFwld());
      po.setSylc(paraPO.getSylc());
      po.setBylc(paraPO.getBylc());
      po.setXslc(paraPO.getXslc());
      po.setYy(paraPO.getYy());
      po.setBglhy(paraPO.getBglhy());
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return update_info;
  }
  
  public List listReportForms(String viewSQL, String fromSQL, String whereSQL) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select " + viewSQL + " from " + fromSQL + " " + whereSQL)
        .list();
    } catch (Exception e) {
      System.out.println("------------------------");
      e.printStackTrace();
      System.out.println("------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listVoitureInfo(String viewSQL, String fromSQL, String whereSQL) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select " + viewSQL + " from " + fromSQL + 
          " " + whereSQL)
        .list();
    } catch (Exception e) {
      System.out.println("------------------------");
      e.printStackTrace();
      System.out.println("------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List listVoitureSend(String viewSQL, String fromSQL, String whereSQL) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      list = this.session.createQuery("select " + viewSQL + " from " + fromSQL + 
          " " + whereSQL)
        .list();
    } catch (Exception e) {
      System.out.println("------------------------");
      e.printStackTrace();
      System.out.println("------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
}
