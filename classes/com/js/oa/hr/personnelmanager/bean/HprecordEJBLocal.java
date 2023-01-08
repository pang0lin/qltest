package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HprecordPO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface HprecordEJBLocal extends EJBLocalObject {
  boolean addHprecord(HprecordPO paramHprecordPO) throws Exception;
  
  boolean deleteHprecord(Long paramLong) throws Exception;
  
  boolean deleteBatchHprecord(String paramString) throws Exception;
  
  HprecordPO selectHprecordView(Long paramLong) throws HibernateException;
  
  boolean updateHprecord(HprecordPO paramHprecordPO) throws Exception;
  
  List selectHpName(String paramString) throws HibernateException;
}
