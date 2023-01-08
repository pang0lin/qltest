package com.js.oa.info.infomanager.service;

import com.js.oa.info.infomanager.bean.InfoProFileEJBBean;
import com.js.oa.info.infomanager.po.InfoProFilePO;
import com.js.oa.info.infomanager.po.InfoProFileReviewPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.portal.service.CustomDesktopBD;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class InfoProFileBD {
  private InfoProFileEJBBean infoProFileEJBBean = new InfoProFileEJBBean();
  
  private static Logger logger = Logger.getLogger(CustomDesktopBD.class
      .getName());
  
  public void save(InfoProFilePO infoProFilePO) {
    try {
      Map<String, String> paraMap = new HashMap<String, String>();
      String fileName = infoProFilePO.getFileName();
      paraMap.put("fileName", fileName);
      List<Object[]> list = this.infoProFileEJBBean.listAll(paraMap);
      if (list != null && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          Date pageDate = infoProFilePO.getFileDate();
          Date fileDate = (Date)obj[5];
          Long diff = Long.valueOf(pageDate.getTime() - fileDate.getTime());
          if (diff.longValue() > 0L) {
            infoProFilePO.setIsNew("1");
            infoProFilePO.setFilePreId(Long.valueOf(obj[10].toString()));
            update(Long.valueOf(obj[0].toString()));
          } else {
            infoProFilePO.setIsNew("0");
            infoProFilePO.setFilePreId(Long.valueOf(obj[10].toString()));
          } 
        } 
      } else {
        infoProFilePO.setIsNew("1");
        infoProFilePO.setFilePreId(infoProFilePO.getFileId());
      } 
      Long id = this.infoProFileEJBBean.save(infoProFilePO);
      setFilePreId(id);
    } catch (Exception ex) {
      logger.error("error to save profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public List ListAllProFile(Map<String, String> paraMap) {
    List list = new ArrayList();
    try {
      list = this.infoProFileEJBBean.listAll(paraMap);
    } catch (Exception ex) {
      logger.error("error to list profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listAllToReview(Map<String, String> paraMap) {
    List list = new ArrayList();
    try {
      list = this.infoProFileEJBBean.listAllToReview(paraMap);
    } catch (Exception ex) {
      logger.error("error to list profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List listReview(Map paraMap) {
    List list = new ArrayList();
    try {
      list = this.infoProFileEJBBean.listReview(null);
    } catch (Exception ex) {
      logger.error("error to list profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public InfoProFilePO getInfoProFilePOById(Long id) {
    InfoProFilePO infoProFilePO = null;
    try {
      infoProFilePO = this.infoProFileEJBBean.getInfoProFilePOById(id);
    } catch (Exception ex) {
      logger.error("error to list profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return infoProFilePO;
  }
  
  public List getAllVersion(Long filePreId) {
    List list = new ArrayList();
    try {
      list = this.infoProFileEJBBean.getAllVersion(filePreId);
    } catch (Exception ex) {
      logger.error("error to getAllVersion :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return list;
  }
  
  public void saveReview(InfoProFileReviewPO infoProFileReviewPO) {
    try {
      this.infoProFileEJBBean.saveReview(infoProFileReviewPO);
    } catch (Exception ex) {
      logger.error("error to save listReview :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public boolean delete(String ids) {
    boolean bl = false;
    try {
      bl = this.infoProFileEJBBean.delele(ids);
    } catch (Exception ex) {
      logger.error("error to ddelete profile :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return bl;
  }
  
  public void update(Long id) {
    InfoProFilePO po = new InfoProFilePO();
    try {
      po = this.infoProFileEJBBean.getInfoProFilePOById(id);
      po.setIsNew("0");
      this.infoProFileEJBBean.updateInfoProFilePO(po);
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void setFilePreId(Long id) {
    InfoProFilePO po = new InfoProFilePO();
    try {
      po = this.infoProFileEJBBean.getInfoProFilePOById(id);
      po.setFilePreId(po.getFileId());
      this.infoProFileEJBBean.updateInfoProFilePO(po);
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void updateInfoProFilePO(InfoProFilePO po) {
    try {
      this.infoProFileEJBBean.updateInfoProFilePO(po);
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void updateInfoProFileReviewPO(InfoProFileReviewPO infoProFileReviewPO) {
    try {
      this.infoProFileEJBBean.updateInfoProFileReviewPO(infoProFileReviewPO);
    } catch (HibernateException ex) {
      logger.error("error to update infoProFileReviewPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
  }
  
  public WFWorkFlowProcessPO getWFWorkFlowProcessPOById(Long id) throws HibernateException {
    WFWorkFlowProcessPO wwfp = new WFWorkFlowProcessPO();
    try {
      wwfp = this.infoProFileEJBBean.getWFWorkFlowProcessPOById(id);
    } catch (HibernateException ex) {
      logger.error("error to get WFWorkFlowProcessPO :" + ex.getMessage());
      ex.printStackTrace();
    } 
    return wwfp;
  }
}
