package com.js.oa.archives.bean;

import com.js.oa.archives.po.ArchivesBorrowPO;
import com.js.oa.archives.po.ArchivesClassPO;
import com.js.oa.archives.po.ArchivesDossierAccessoryPO;
import com.js.oa.archives.po.ArchivesDossierPO;
import com.js.oa.archives.po.ArchivesFileAccessoryPO;
import com.js.oa.archives.po.ArchivesFilePO;
import com.js.oa.archives.po.ArchivesPigeonholeSetPO;
import com.js.oa.archives.po.ArchivesWaitPigeonholePO;
import com.js.oa.archives.vo.ArchivesClassVO;
import com.js.oa.archives.vo.ArchivesDossierVO;
import com.js.oa.archives.vo.ArchivesFileVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.TransformObject;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

public class ArchivesEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  Logger logger = (Logger)Logger.getInstance(ArchivesEJBBean.class.getName());
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List selectArchivesClass(Long userId, Long orgId, String where) throws HibernateException {
    List<ArchivesClassVO> list = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery(
          "from com.js.oa.archives.po.ArchivesClassPO archivesClass where " + 
          where + " order by archivesClass.classIdString");
      List queryList = archivesClassQuery.list();
      Iterator<ArchivesClassPO> iterator = queryList.iterator();
      ArchivesClassPO archivesClassPO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesClassPO = iterator.next();
        ArchivesClassVO archivesClassVO = 
          (ArchivesClassVO)TransformObject.getInstance()
          .transformObject(
            archivesClassPO, ArchivesClassVO.class);
        archivesClassVO.setMaintenance(Boolean.TRUE);
        list.add(archivesClassVO);
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select archivesClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public List selectArchivesClass(Long userId, Long orgId, String where, Integer currentPage, Integer volume) throws HibernateException {
    List<ArchivesClassVO> list = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery(
          "from com.js.oa.archives.po.ArchivesClassPO archivesClass where " + 
          where + " ORDER BY archivesClass.classIdString");
      archivesClassQuery.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      archivesClassQuery.setMaxResults(volume.intValue());
      List queryList = archivesClassQuery.list();
      Iterator<ArchivesClassPO> iterator = queryList.iterator();
      ArchivesClassPO archivesClassPO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesClassPO = iterator.next();
        ArchivesClassVO archivesClassVO = 
          (ArchivesClassVO)TransformObject.getInstance()
          .transformObject(
            archivesClassPO, ArchivesClassVO.class);
        archivesClassVO.setMaintenance(Boolean.TRUE);
        list.add(archivesClassVO);
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select archivesClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Integer getArchivesClassRecordCount(Long userId, Long orgId, String where) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesClass) from com.js.oa.archives.po.ArchivesClassPO archivesClass where " + 
          where)
        .next();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesClassCount Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean addArchivesClass(ArchivesClassPO archivesClassPO, String taxis, String state) throws HibernateException {
    boolean result = false;
    List<Integer> list = null;
    try {
      begin();
      Long classId = (Long)this.session.save(archivesClassPO);
      int orderCode = 500000;
      int orderCode2 = 10000;
      String orgIdString = "";
      Query query = this.session.createQuery("select MAX(archivesClass.classOrderCode) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classParentId =" + 
          archivesClassPO.getClassParentId());
      list = query.list();
      if (list == null || (list.size() == 1 && list.get(0) == null)) {
        archivesClassPO.setClassOrderCode(new Integer(orderCode));
        if (!"0".equals(archivesClassPO.getClassLevel().toString())) {
          archivesClassPO.setClassIdString(
              String.valueOf(archivesClassPO.getClassIdString()) + "_" + orderCode + "$" + 
              classId.toString() + "$");
        } else {
          archivesClassPO.setClassIdString("_" + orderCode + "$" + 
              classId.toString() + "$");
        } 
      } else {
        List<E> list4 = null;
        if (archivesClassPO.getClassParentId() != null && 
          !"0".equals(archivesClassPO.getClassParentId())) {
          Query query4 = this.session.createQuery("select MAX(archivesClass.classIdString) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classId = " + 
              archivesClassPO
              .getClassParentId());
          list4 = query4.list();
        } 
        if (taxis != null && "1".equals(taxis)) {
          if ("0".equals(state)) {
            Query query2 = this.session.createQuery("SELECT MAX(archivesClass.classOrderCode) FROM com.js.oa.archives.po.ArchivesClassPO archivesClass WHERE archivesClass.classParentId=" + 
                archivesClassPO.getClassParentId() + 
                " AND archivesClass.classOrderCode<" + 
                archivesClassPO.getClassOrderCode());
            List<Integer> list2 = query2.list();
            if (list2 == null || (list2.size() == 1 && list2.get(0) == null)) {
              archivesClassPO.setClassOrderCode(new Integer(
                    archivesClassPO.getClassOrderCode()
                    .intValue() - 5000));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else {
              int intValue = ((Integer)list2.get(0)).intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer((
                    intValue + 
                    archivesClassPO
                    .getClassOrderCode()
                    .intValue()) / 2));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } 
          } 
          if ("1".equals(state)) {
            Query query3 = this.session.createQuery("SELECT MIN(archivesClass.classOrderCode) FROM com.js.oa.archives.po.ArchivesClassPO archivesClass WHERE archivesClass.classParentId=" + 
                archivesClassPO.getClassParentId() + 
                " AND archivesClass.classOrderCode>" + 
                archivesClassPO.getClassOrderCode());
            List<Integer> list3 = query3.list();
            if (list3 == null || (list3.size() == 1 && list3.get(0) == null)) {
              archivesClassPO.setClassOrderCode(new Integer(
                    archivesClassPO.getClassOrderCode()
                    .intValue() + 5000));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else {
              int intValue = ((Integer)list3.get(0)).intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer((
                    intValue + 
                    archivesClassPO
                    .getClassOrderCode()
                    .intValue()) / 2));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } 
          } 
        } else {
          archivesClassPO.setClassOrderCode(new Integer((
                (Integer)list.get(0)).intValue() + orderCode2));
          if (!"0".equals(archivesClassPO.getClassLevel().toString())) {
            archivesClassPO.setClassIdString(
                String.valueOf(archivesClassPO.getClassIdString()) + "_" + ((
                (Integer)list.get(0)).intValue() + 
                orderCode2) + "$" + classId.toString() + "$");
          } else {
            archivesClassPO.setClassIdString("_" + ((
                (Integer)list.get(0)).intValue() + 
                orderCode2) + "$" + classId.toString() + "$");
          } 
        } 
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesClass Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer selectIfSubordinateClass(Long classId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesClass) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classParentId = " + 
          classId).next();
    } catch (HibernateException ex) {
      System.out.println("selectClassLevel Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public ArchivesClassVO selectArchivesClass(Long classId) throws HibernateException {
    ArchivesClassVO archivesClassVO = null;
    try {
      begin();
      ArchivesClassPO archivesClassPO = null;
      archivesClassPO = (ArchivesClassPO)this.session.load(ArchivesClassPO.class, 
          classId);
      archivesClassVO = (ArchivesClassVO)TransformObject.getInstance()
        .transformObject(archivesClassPO, ArchivesClassVO.class);
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select archivesClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return archivesClassVO;
  }
  
  public Boolean deleteArchivesClass(Long classId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      result = deleteArchivesClassRecord(classId);
      if (result.booleanValue())
        this.session.flush(); 
    } catch (HibernateException ex) {
      System.out.println("deleteArchivesClass Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Boolean deleteArchivesClassRecord(Long classId) throws HibernateException {
    Boolean result = new Boolean(false);
    Integer SCN = this.session.iterate("select count(archivesDossier) from com.js.oa.archives.po.ArchivesDossierPO archivesDossier where archivesDossier.archivesClass.classId = " + 
        classId).next();
    Integer SCN2 = this.session.iterate("select count(archivesClass) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classParentId = " + 
        classId).next();
    int subordinateClassNumber = SCN.intValue() + 
      SCN2.intValue();
    if (subordinateClassNumber > 0) {
      result = Boolean.FALSE;
    } else {
      ArchivesClassPO archivesClassPO = (ArchivesClassPO)this.session.load(
          ArchivesClassPO.class, classId);
      this.session.delete(archivesClassPO);
      result = Boolean.TRUE;
    } 
    return result;
  }
  
  public Boolean deleteBatchArchivesClass(String classIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] idsArr = classIds.split(",");
      boolean deleteFlag = false;
      for (int i = 0; i < idsArr.length; i++) {
        deleteFlag = deleteArchivesClassRecord(Long.valueOf(idsArr[i]))
          .booleanValue();
        if (!deleteFlag)
          break; 
      } 
      if (deleteFlag) {
        this.session.flush();
        result = Boolean.TRUE;
      } else {
        result = Boolean.FALSE;
      } 
    } catch (HibernateException ex) {
      System.out.println("deleteBatchArchivesClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean modiArchivesClass(ArchivesClassPO archivesClassPO, String taxis, String state, String classOrderCode) throws HibernateException {
    boolean result = false;
    List<E> list = null;
    try {
      begin();
      Long classId = archivesClassPO.getClassId();
      String selfOLDClassIdString = "";
      Integer selfOLDClassLevel = null;
      Query query6 = this.session.createQuery("select archivesClass.classIdString,archivesClass.classLevel from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classId=" + 
          classId);
      Iterator<Object[]> iterator = query6.iterate();
      Object[] obj = (Object[])null;
      while (iterator.hasNext()) {
        obj = iterator.next();
        selfOLDClassIdString = obj[0].toString();
        selfOLDClassLevel = (Integer)obj[1];
      } 
      int orderCode = 500000;
      int orderCode2 = 10000;
      String orgIdString = "";
      Query query = this.session.createQuery("select MAX(archivesClass.classOrderCode) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classParentId =" + 
          archivesClassPO.getClassParentId());
      list = query.list();
      if (list == null || (list.size() == 1 && list.get(0) == null)) {
        archivesClassPO.setClassOrderCode(new Integer(orderCode));
        if (!"0".equals(archivesClassPO.getClassLevel().toString())) {
          archivesClassPO.setClassIdString(
              String.valueOf(archivesClassPO.getClassIdString()) + "_" + orderCode + "$" + 
              classId.toString() + "$");
        } else {
          archivesClassPO.setClassIdString("_" + orderCode + "$" + 
              classId.toString() + "$");
        } 
      } else {
        List<E> list4 = null;
        if (archivesClassPO.getClassParentId() != null && 
          !"0".equals(archivesClassPO.getClassParentId())) {
          Query query4 = this.session.createQuery("select MAX(archivesClass.classIdString) from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classId = " + 
              archivesClassPO
              .getClassParentId());
          list4 = query4.list();
        } 
        if (taxis != null && "1".equals(taxis)) {
          if ("0".equals(state)) {
            Query query2 = this.session.createQuery("SELECT MAX(archivesClass.classOrderCode) FROM com.js.oa.archives.po.ArchivesClassPO archivesClass WHERE archivesClass.classParentId=" + 
                archivesClassPO.getClassParentId() + 
                " AND archivesClass.classOrderCode<" + 
                archivesClassPO.getClassOrderCode());
            List<E> list2 = query2.list();
            if (list2 == null || (list2.size() == 1 && list2.get(0) == null)) {
              archivesClassPO.setClassOrderCode(new Integer(
                    archivesClassPO.getClassOrderCode()
                    .intValue() - 5000));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else if (!list2.get(0).toString().equals(
                classOrderCode)) {
              int intValue = ((Integer)list2.get(0))
                .intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer((
                    intValue + 
                    archivesClassPO
                    .getClassOrderCode()
                    .intValue()) / 2));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else {
              int intValue = ((Integer)list2.get(0))
                .intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer(
                    classOrderCode));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } 
          } 
          if ("1".equals(state)) {
            Query query3 = this.session.createQuery("SELECT MIN(archivesClass.classOrderCode) FROM com.js.oa.archives.po.ArchivesClassPO archivesClass WHERE archivesClass.classParentId=" + 
                archivesClassPO.getClassParentId() + 
                " AND archivesClass.classOrderCode>" + 
                archivesClassPO.getClassOrderCode());
            List<E> list3 = query3.list();
            if (list3 == null || (list3.size() == 1 && list3.get(0) == null)) {
              archivesClassPO.setClassOrderCode(new Integer(
                    archivesClassPO.getClassOrderCode()
                    .intValue() + 5000));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else if (!list3.get(0).toString().equals(
                classOrderCode)) {
              int intValue = ((Integer)list3.get(0))
                .intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer((
                    intValue + 
                    archivesClassPO
                    .getClassOrderCode()
                    .intValue()) / 2));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } else {
              int intValue = ((Integer)list3.get(0))
                .intValue();
              if (intValue == 0)
                intValue = 100000; 
              archivesClassPO.setClassOrderCode(new Integer(
                    classOrderCode));
              if (!"0".equals(archivesClassPO.getClassLevel()
                  .toString())) {
                archivesClassPO.setClassIdString(
                    String.valueOf(list4.get(0).toString()) + "_" + 
                    archivesClassPO.getClassOrderCode() + 
                    "$" + classId.toString() + "$");
              } else {
                archivesClassPO.setClassIdString("_" + 
                    archivesClassPO.getClassOrderCode()
                    .toString() + "$" + classId.toString() + 
                    "$");
              } 
            } 
          } 
        } else if (!list.get(0).toString().equals(classOrderCode)) {
          archivesClassPO.setClassOrderCode(new Integer((
                (Integer)list.get(0)).intValue() + orderCode2));
          if (!"0".equals(archivesClassPO.getClassLevel()
              .toString())) {
            archivesClassPO.setClassIdString(
                String.valueOf(archivesClassPO.getClassIdString()) + "_" + ((
                (Integer)list.get(0)).intValue() + 
                orderCode2) + "$" + classId.toString() + 
                "$");
          } else {
            archivesClassPO.setClassIdString("_" + ((
                (Integer)list.get(0)).intValue() + 
                orderCode2) + "$" + classId.toString() + 
                "$");
          } 
        } else {
          archivesClassPO.setClassOrderCode(new Integer(
                classOrderCode));
          if (!"0".equals(archivesClassPO.getClassLevel()
              .toString())) {
            archivesClassPO.setClassIdString(
                String.valueOf(archivesClassPO.getClassIdString()) + "_" + (
                (Integer)list.get(0)).intValue() + "$" + 
                classId.toString() + "$");
          } else {
            archivesClassPO.setClassIdString("_" + (
                (Integer)list.get(0)).intValue() + "$" + 
                classId.toString() + "$");
          } 
        } 
      } 
      this.session.update(archivesClassPO, classId);
      Query query7 = this.session.createQuery("select archivesClass from com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesClass.classIdString LIKE '%" + 
          classId + 
          "%' AND archivesClass<>" + 
          classId);
      List<ArchivesClassPO> list7 = query7.list();
      int selfOLDClassIdStringLenght = selfOLDClassIdString.length();
      int levelDiff = archivesClassPO.getClassLevel().intValue() - 
        selfOLDClassLevel.intValue();
      for (int i = 0; i < list7.size(); i++) {
        ArchivesClassPO archivesClassPO2 = list7.get(
            i);
        String modiClassIdString = archivesClassPO2.getClassIdString();
        archivesClassPO2.setClassLevel(new Integer(archivesClassPO2
              .getClassLevel().intValue() + levelDiff));
        archivesClassPO2.setClassIdString(
            String.valueOf(archivesClassPO.getClassIdString().toString()) + 
            modiClassIdString.substring(
              selfOLDClassIdStringLenght));
        this.session.update(archivesClassPO2);
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesClass Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean addArchivesDossier(ArchivesDossierPO archivesDossierPO, Long classId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesClassPO archivesClassPO = (ArchivesClassPO)this.session.load(
          ArchivesClassPO.class, classId);
      archivesDossierPO.setArchivesClass(archivesClassPO);
      this.session.save(archivesDossierPO);
      Set set = archivesDossierPO.getArchivesDossierAccessory();
      HashSet hs = new HashSet();
      archivesDossierPO.setArchivesDossierAccessory(hs);
      Iterator<ArchivesDossierAccessoryPO> iterator = set.iterator();
      ArchivesDossierAccessoryPO archivesDossierAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesDossierAccessoryPO = 
          iterator
          .next();
        archivesDossierAccessoryPO.setArchivesDossier(archivesDossierPO);
        archivesDossierPO.getArchivesDossierAccessory().add(
            archivesDossierAccessoryPO);
        this.session.save(archivesDossierAccessoryPO);
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesDossierEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getArchivesDossierRecordCount(Long userId, Long orgId, String where) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesDossier) from com.js.oa.archives.po.ArchivesDossierPO archivesDossier where (" + 
          where + 
          ") and archivesDossier.dossierStatus=0")
        .next();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierCountEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectArchivesDossier(Long userId, Long orgId, String where, Integer currentPage, Integer volume) throws HibernateException {
    List<ArchivesDossierVO> list = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery("select archivesDossier,archivesClass.className from com.js.oa.archives.po.ArchivesDossierPO archivesDossier,com.js.oa.archives.po.ArchivesClassPO archivesClass where archivesDossier.archivesClass.classId = archivesClass.classId and  archivesDossier.dossierStatus=0 and (" + 

          
          where + ") ORDER BY archivesDossier.dossierId DESC");
      archivesClassQuery.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      archivesClassQuery.setMaxResults(volume.intValue());
      List<Object[]> queryList = archivesClassQuery.list();
      ArchivesDossierPO archivesDossierPO = null;
      ArchivesDossierVO archivesDossierVO = null;
      for (int i = 0; i < queryList.size(); i++) {
        Object[] obj = queryList.get(i);
        for (int j = 0; j < obj.length; j++) {
          switch (j) {
            case 0:
              archivesDossierPO = (ArchivesDossierPO)obj[j];
              archivesDossierVO = 
                (ArchivesDossierVO)TransformObject.getInstance().transformObject(
                  archivesDossierPO, ArchivesDossierVO.class);
              archivesDossierVO.setMaintenance(Boolean.TRUE);
              break;
            case 1:
              archivesDossierVO.setClassName(obj[j].toString());
              list.add(archivesDossierVO);
              break;
          } 
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Map selectArchivesDossier(Long dossierId) throws HibernateException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    ArchivesDossierVO archivesDossierVO = null;
    try {
      begin();
      ArchivesDossierPO archivesDossierPO = null;
      archivesDossierPO = (ArchivesDossierPO)this.session.load(
          ArchivesDossierPO.class, dossierId);
      archivesDossierVO = (ArchivesDossierVO)TransformObject.getInstance()
        .transformObject(archivesDossierPO, 
          ArchivesDossierVO.class);
      archivesDossierVO.setClassNo(archivesDossierPO.getArchivesClass()
          .getClassId().toString());
      map.put("archivesDossierVO", archivesDossierVO);
      Query query = this.session.createQuery("select accessory.accessoryname,accessory.accessorysavename from com.js.oa.archives.po.ArchivesDossierPO archivesDossierPO join archivesDossierPO.archivesDossierAccessory accessory where archivesDossierPO.dossierId = " + 
          dossierId);
      map.put("accessory", query.list());
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select archivesDossier Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return map;
  }
  
  public boolean modiArchivesDossier(ArchivesDossierPO archivesDossierPO, Long classId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesDossierPO archivesDossier = (ArchivesDossierPO)this.session.load(
          ArchivesDossierPO.class, 
          archivesDossierPO.getDossierId());
      ArchivesClassPO archivesClassPO = (ArchivesClassPO)this.session.load(
          ArchivesClassPO.class, classId);
      archivesDossier.setArchivesClass(archivesClassPO);
      Iterator<ArchivesDossierAccessoryPO> it = this.session.createQuery("from com.js.oa.archives.po.ArchivesDossierAccessoryPO accessory where accessory.archivesDossier.dossierId=" + 
          archivesDossierPO.getDossierId())
        .iterate();
      while (it.hasNext()) {
        ArchivesDossierAccessoryPO archivesDossierAccessory = it.next();
        this.session.delete(archivesDossierAccessory);
      } 
      Set<ArchivesDossierAccessoryPO> accessorySet = archivesDossierPO.getArchivesDossierAccessory();
      Set<ArchivesDossierAccessoryPO> setTemp = new HashSet();
      it = accessorySet.iterator();
      while (it.hasNext()) {
        ArchivesDossierAccessoryPO archivesDossierAccessory = it.next();
        this.session.save(archivesDossierAccessory);
        setTemp.add(archivesDossierAccessory);
      } 
      archivesDossier.setArchivesDossierAccessory(setTemp);
      archivesDossier.setCatalogNo(archivesDossierPO.getCatalogNo());
      archivesDossier.setClassNo(archivesDossierPO.getClassNo());
      archivesDossier.setCopyCount(archivesDossierPO.getCopyCount());
      archivesDossier.setDossierKey(archivesDossierPO.getDossierKey());
      archivesDossier.setDossierName(archivesDossierPO.getDossierName());
      archivesDossier.setDossierNo(archivesDossierPO.getDossierNo());
      archivesDossier.setDossierStatus(archivesDossierPO.getDossierStatus());
      archivesDossier.setGeneralNo(archivesDossierPO.getGeneralNo());
      archivesDossier.setPageCount(archivesDossierPO.getPageCount());
      archivesDossier.setPigeonholeTime(archivesDossierPO
          .getPigeonholeTime());
      archivesDossier.setPrincipal(archivesDossierPO.getPrincipal());
      archivesDossier.setPrincipalName(archivesDossierPO.getPrincipalName());
      archivesDossier.setSaveBeginTime(archivesDossierPO.getSaveBeginTime());
      archivesDossier.setSaveEndTime(archivesDossierPO.getSaveEndTime());
      archivesDossier.setSaveStyle(archivesDossierPO.getSaveStyle());
      archivesDossier.setSecretLevel(archivesDossierPO.getSecretLevel());
      archivesDossier.setDossierId(archivesDossierPO.getDossierId());
      archivesDossier.setClassReader(archivesDossierPO.getClassReader());
      archivesDossier.setClassReadGroup(archivesDossierPO
          .getClassReadGroup());
      archivesDossier.setClassReadOrg(archivesDossierPO.getClassReadOrg());
      archivesDossier.setClassReadName(archivesDossierPO.getClassReadName());
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("modiArchivesDossierEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean logoutArchivesDossier(Long dossierId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesDossierPO archivesDossierPO = (ArchivesDossierPO)this.session
        .load(ArchivesDossierPO.class, 
          dossierId);
      archivesDossierPO.setDossierStatus(new Integer(1));
      result = Boolean.TRUE;
      result = logoutArchivesDossierRecord(dossierId);
      if (result.booleanValue())
        this.session.flush(); 
    } catch (HibernateException ex) {
      System.out.println("logoutArchivesDossierEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean logoutBatchArchivesDossier(String dossierIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] idsArr = dossierIds.split(",");
      boolean deleteFlag = false;
      for (int i = 0; i < idsArr.length; i++) {
        deleteFlag = logoutArchivesDossierRecord(Long.valueOf(idsArr[i]))
          .booleanValue();
        if (!deleteFlag)
          break; 
      } 
      if (deleteFlag) {
        this.session.flush();
        result = Boolean.TRUE;
      } else {
        result = Boolean.FALSE;
      } 
    } catch (HibernateException ex) {
      System.out.println("logoutBatchArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean cleanLogoutArchivesDossier() throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.archives.po.ArchivesDossierPO archivesDossier where archivesDossier.dossierStatus=0");
      List list = query.list();
      Iterator<ArchivesDossierPO> iterator = list.iterator();
      ArchivesDossierPO archivesDossierPO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesDossierPO = iterator.next();
        archivesDossierPO.setDossierStatus(new Integer(1));
        this.session.save(archivesDossierPO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("cleanLogoutArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Boolean logoutArchivesDossierRecord(Long dossierId) throws HibernateException {
    Boolean result = new Boolean(false);
    Integer SCN = this.session.iterate("select count(archivesFile) from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.archivesDossier.dossierId = " + 
        dossierId).next();
    int subordinateClassNumber = SCN.intValue();
    if (subordinateClassNumber > 0) {
      result = Boolean.FALSE;
    } else {
      ArchivesDossierPO archivesDossierPO = (ArchivesDossierPO)this.session
        .load(
          ArchivesDossierPO.class, dossierId);
      archivesDossierPO.setDossierStatus(new Integer(1));
      this.session.update(archivesDossierPO);
      result = Boolean.TRUE;
    } 
    return result;
  }
  
  public List selectLogoutArchivesDossier(Long userId, Long orgId, String where, Integer currentPage, Integer volume) throws HibernateException {
    List<ArchivesDossierVO> list = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery("select archivesDossier,archivesClass.className from com.js.oa.archives.po.ArchivesDossierPO archivesDossier join archivesDossier.archivesClass archivesClass where (" + 
          where + 
          ") and archivesDossier.dossierStatus=1 ORDER BY archivesDossier.dossierId DESC");
      archivesClassQuery.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      archivesClassQuery.setMaxResults(volume.intValue());
      List<Object[]> queryList = archivesClassQuery.list();
      ArchivesDossierPO archivesDossierPO = null;
      ArchivesDossierVO archivesDossierVO = null;
      for (int i = 0; i < queryList.size(); i++) {
        Object[] obj = queryList.get(i);
        for (int j = 0; j < obj.length; j++) {
          switch (j) {
            case 0:
              archivesDossierPO = (ArchivesDossierPO)obj[j];
              archivesDossierVO = 
                (ArchivesDossierVO)TransformObject.getInstance().transformObject(
                  archivesDossierPO, ArchivesDossierVO.class);
              archivesDossierVO.setMaintenance(Boolean.TRUE);
              break;
            case 1:
              archivesDossierVO.setClassName(obj[j].toString());
              list.add(archivesDossierVO);
              break;
          } 
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectLogoutArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Integer getLogoutArchivesDossierRecordCount(Long userId, Long orgId, String where) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesDossier) from com.js.oa.archives.po.ArchivesDossierPO archivesDossier where (" + 
          where + 
          ") and archivesDossier.dossierStatus=1")
        .next();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println(
          "selectLogoutArchivesDossierCountEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean resumeArchivesDossier(String dossierId) throws Exception {
    Boolean result = new Boolean(false);
    DbOpt opt = new DbOpt();
    try {
      String sql = 
        "update OA_ARCHIVESDOSSIER set DOSSIERSTATUS = 0 WHERE DOSSIER_ID IN (" + 
        dossierId + ")";
      opt.executeUpdate(sql);
      opt.close();
      result = Boolean.TRUE;
    } catch (Exception ex) {
      try {
        opt.close();
      } catch (Exception err) {
        ex.printStackTrace();
      } 
      System.out.println("resumeArchivesDossierEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } 
    return result;
  }
  
  public Boolean delArchivesDossier(Long dossierId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesDossierPO archivesDossierPO = (ArchivesDossierPO)this.session
        .load(ArchivesDossierPO.class, 
          dossierId);
      this.session.delete(archivesDossierPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delArchivesDossierEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean delBatchArchivesDossier(String dossierIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] idsArr = dossierIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        ArchivesDossierPO archivesDossierPO = 
          (ArchivesDossierPO)this.session.load(ArchivesDossierPO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(archivesDossierPO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delBatchArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean cleanDelArchivesDossier() throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.delete("from com.js.oa.archives.po.ArchivesDossierPO archivesDossier where archivesDossier.dossierStatus=1");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("cleanDelArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectArchivesFile(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, String where, Integer currentPage, Integer volume) throws HibernateException {
    List<ArchivesFileVO> list = new ArrayList();
    Query archivesFileQuery = null;
    try {
      begin();
      archivesFileQuery = this.session.createQuery("select archivesFile from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.fileStatus='" + 
          fileStatus + "' and archivesFile.pigeonholeStatus='" + 
          pigeonholeStatus + "' and (" + where + 
          ") ORDER BY archivesFile.fileId DESC");
      archivesFileQuery.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      archivesFileQuery.setMaxResults(volume.intValue());
      List queryList = archivesFileQuery.list();
      Iterator<ArchivesFilePO> iterator = queryList.iterator();
      ArchivesFilePO archivesFilePO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesFilePO = iterator.next();
        ArchivesFileVO archivesFileVO = 
          (ArchivesFileVO)TransformObject.getInstance()
          .transformObject(
            archivesFilePO, ArchivesFileVO.class);
        archivesFileVO.setMaintenance(Boolean.TRUE);
        list.add(archivesFileVO);
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Integer getArchivesFileRecordCount(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, String where) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesFile) from com.js.oa.archives.po.ArchivesFilePO archivesFile join archivesFile.archivesDossier archivesDossier where archivesFile.fileStatus='" + 
          fileStatus + 
          "' and archivesFile.pigeonholeStatus='" + 
          pigeonholeStatus + 
          "' and (" + where + ")")
        .next();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesFileCountEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectArchivesDossier(String where) throws HibernateException {
    List<ArchivesDossierVO> result = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery("select archivesDossier,archivesClass.className from com.js.oa.archives.po.ArchivesDossierPO archivesDossier left join archivesDossier.archivesClass archivesClass where (" + 
          where + 
          ") and archivesDossier.dossierStatus=0 ORDER BY archivesDossier.dossierId DESC");
      List<Object[]> queryList = archivesClassQuery.list();
      ArchivesDossierPO archivesDossierPO = null;
      ArchivesDossierVO archivesDossierVO = null;
      for (int i = 0; i < queryList.size(); i++) {
        Object[] o = queryList.get(i);
        for (int j = 0; j < o.length; j++) {
          if (j == 0) {
            archivesDossierPO = (ArchivesDossierPO)o[j];
            archivesDossierVO = 
              (ArchivesDossierVO)TransformObject.getInstance()
              .transformObject(archivesDossierPO, 
                ArchivesDossierVO.class);
          } else if (j == 1) {
            archivesDossierVO.setClassName(o[j].toString());
            result.add(archivesDossierVO);
          } 
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectArchivesDossierType(String where, String type) throws HibernateException {
    List<ArchivesDossierVO> result = new ArrayList();
    try {
      begin();
      Query archivesClassQuery = this.session.createQuery("select archivesDossier,archivesClass.className from com.js.oa.archives.po.ArchivesDossierPO archivesDossier left join archivesDossier.archivesClass archivesClass where (" + 
          where + 
          ") and archivesDossier.dossierStatus=0 and archivesDossier.archivesClass.classId=" + type + " ORDER BY archivesDossier.dossierId DESC");
      List<Object[]> queryList = archivesClassQuery.list();
      ArchivesDossierPO archivesDossierPO = null;
      ArchivesDossierVO archivesDossierVO = null;
      for (int i = 0; i < queryList.size(); i++) {
        Object[] o = queryList.get(i);
        for (int j = 0; j < o.length; j++) {
          if (j == 0) {
            archivesDossierPO = (ArchivesDossierPO)o[j];
            archivesDossierVO = 
              (ArchivesDossierVO)TransformObject.getInstance()
              .transformObject(archivesDossierPO, 
                ArchivesDossierVO.class);
          } else if (j == 1) {
            archivesDossierVO.setClassName(o[j].toString());
            result.add(archivesDossierVO);
          } 
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean addArchivesFile(ArchivesFilePO archivesFilePO, Long dossierId, Long waitPigeonholeId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      if (dossierId != null && !dossierId.equals(new Long(0L))) {
        ArchivesDossierPO archivesDossierPO = 
          (ArchivesDossierPO)this.session.load(ArchivesDossierPO.class, dossierId);
        archivesFilePO.setArchivesDossier(archivesDossierPO);
      } else {
        archivesFilePO.setDossierId(new Long(0L));
      } 
      Long id = (Long)this.session.save(archivesFilePO);
      if (!waitPigeonholeId.equals(new Long(0L))) {
        ArchivesWaitPigeonholePO archivesWaitPigeonholePO = 
          (ArchivesWaitPigeonholePO)this.session.load(
            ArchivesWaitPigeonholePO.class, waitPigeonholeId);
        archivesWaitPigeonholePO.setPigeonholeState(new Integer(1));
        archivesWaitPigeonholePO.setPigeonholeManageDate(new Date());
        archivesWaitPigeonholePO.setFileID(id);
        this.session.update(archivesWaitPigeonholePO);
      } 
      Set set = archivesFilePO.getArchivesFileAccessory();
      HashSet hs = new HashSet();
      archivesFilePO.setArchivesFileAccessory(hs);
      Iterator<ArchivesFileAccessoryPO> iterator = set.iterator();
      ArchivesFileAccessoryPO archivesFileAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesFileAccessoryPO = iterator
          .next();
        archivesFileAccessoryPO.setArchivesFile(archivesFilePO);
        archivesFilePO.getArchivesFileAccessory().add(
            archivesFileAccessoryPO);
        this.session.save(archivesFileAccessoryPO);
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesFileEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean logoutArchivesFile(Long fileId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session
        .load(ArchivesFilePO.class, 
          fileId);
      boolean deleteFlag = false;
      deleteFlag = logoutArchivesFileRecord(fileId).booleanValue();
      if (deleteFlag) {
        archivesFilePO.setFileStatus(new Integer(1));
        this.session.flush();
        result = Boolean.TRUE;
      } else {
        result = Boolean.FALSE;
      } 
    } catch (HibernateException ex) {
      System.out.println("logoutArchivesFileEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean logoutBatchArchivesFile(String fileIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] idsArr = fileIds.split(",");
      boolean deleteFlag = false;
      int i;
      for (i = 0; i < idsArr.length; i++) {
        deleteFlag = logoutArchivesFileRecord(Long.valueOf(idsArr[i]))
          .booleanValue();
        if (!deleteFlag)
          break; 
      } 
      if (deleteFlag) {
        for (i = 0; i < idsArr.length; i++) {
          ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session
            .load(ArchivesFilePO.class, Long.valueOf(idsArr[i]));
          archivesFilePO.setFileStatus(new Integer(1));
        } 
        this.session.flush();
        result = Boolean.TRUE;
      } else {
        result = Boolean.FALSE;
      } 
    } catch (HibernateException ex) {
      System.out.println("logoutBatchArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Boolean logoutArchivesFileRecord(Long fileId) throws HibernateException {
    Boolean result = new Boolean(false);
    Integer SCN = this.session.iterate("select count(archivesBorrow) from com.js.oa.archives.po.ArchivesBorrowPO archivesBorrow left join archivesBorrow.archivesFile archivesFile where archivesBorrow.isReturned = 0 and archivesFile.fileId = " + 
        fileId).next();
    int subordinateClassNumber = SCN.intValue();
    if (subordinateClassNumber > 0) {
      result = Boolean.FALSE;
    } else {
      result = Boolean.TRUE;
    } 
    return result;
  }
  
  public Boolean cleanLogoutArchivesFile() throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.fileStatus=0");
      List list = query.list();
      Iterator<ArchivesFilePO> iterator = list.iterator();
      ArchivesFilePO archivesFilePO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesFilePO = iterator.next();
        archivesFilePO.setFileStatus(new Integer(1));
        this.session.save(archivesFilePO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("cleanLogoutArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean resumeArchivesFile(Long fileId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session
        .load(ArchivesFilePO.class, 
          fileId);
      archivesFilePO.setFileStatus(new Integer(0));
      this.session.update(archivesFilePO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("resumeArchivesFileEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean delArchivesFile(Long fileId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session
        .load(ArchivesFilePO.class, 
          fileId);
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(
          " update oa_waitpigeonhole set pigeonholestate=0 where file_id=" + 
          fileId);
      this.session.delete(archivesFilePO);
      this.session.flush();
      result = Boolean.TRUE;
      stmt.close();
      conn.close();
    } catch (HibernateException ex) {
      System.out.println("delArchivesFileEJB Exception:" + 
          ex.getMessage());
      throw ex;
    } catch (Exception exception) {
    
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean delBatchArchivesFile(String fileIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] idsArr = fileIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        ArchivesFilePO archivesFilePO = 
          (ArchivesFilePO)this.session.load(ArchivesFilePO.class, 
            Long.valueOf(idsArr[i]));
        stmt.executeUpdate(
            " update oa_waitpigeonhole set pigeonholestate=0 where file_id=" + 
            idsArr[i]);
        this.session.delete(archivesFilePO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
      stmt.close();
      conn.close();
    } catch (HibernateException ex) {
      this.session.flush();
      System.out.println("delBatchArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } catch (Exception exception) {
    
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean cleanDelArchivesFile(String fileStat) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      if ("logout".equals(fileStat))
        this.session.delete("from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.fileStatus=1"); 
      if ("nopigeonhole".equals(fileStat))
        this.session.delete("from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.pigeonholeStatus=1"); 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("cleanDelArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Map selectArchivesFile(Long fileId) throws HibernateException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    ArchivesFileVO archivesFileVO = null;
    try {
      begin();
      ArchivesFilePO archivesFilePO = null;
      archivesFilePO = (ArchivesFilePO)this.session.load(ArchivesFilePO.class, 
          fileId);
      archivesFileVO = (ArchivesFileVO)TransformObject.getInstance()
        .transformObject(archivesFilePO, ArchivesFileVO.class);
      if (archivesFilePO.getArchivesDossier() != null && 
        !"".equals(archivesFilePO.getArchivesDossier()))
        archivesFileVO.setDossierId(archivesFilePO.getArchivesDossier()
            .getDossierId()); 
      map.put("archivesFileVO", archivesFileVO);
      Query query = this.session.createQuery("select accessory.accessoryname,accessory.accessorysavename from com.js.oa.archives.po.ArchivesFilePO archivesFilePO join archivesFilePO.archivesFileAccessory accessory where archivesFilePO.fileId = " + 
          fileId);
      map.put("accessory", query.list());
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesFile Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return map;
  }
  
  public boolean modiArchivesFile(ArchivesFilePO archivesFilePO, Long dossierId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesFilePO archivesFile = (ArchivesFilePO)this.session.load(ArchivesFilePO.class, 
          archivesFilePO.getFileId());
      if (dossierId != null && !dossierId.equals(new Long(0L))) {
        ArchivesDossierPO archivesDossierPO = 
          (ArchivesDossierPO)this.session.load(ArchivesDossierPO.class, dossierId);
        archivesFile.setArchivesDossier(archivesDossierPO);
      } else {
        archivesFile.setDossierId(new Long(0L));
      } 
      Iterator<ArchivesFileAccessoryPO> it = this.session.createQuery("from com.js.oa.archives.po.ArchivesFileAccessoryPO accessory where accessory.archivesFile.fileId=" + 
          archivesFilePO.getFileId())
        .iterate();
      while (it.hasNext()) {
        ArchivesFileAccessoryPO archivesFileAccessory = it.next();
        this.session.delete(archivesFileAccessory);
      } 
      Set<ArchivesFileAccessoryPO> accessorySet = archivesFilePO.getArchivesFileAccessory();
      Set<ArchivesFileAccessoryPO> setTemp = new HashSet();
      it = accessorySet.iterator();
      while (it.hasNext()) {
        ArchivesFileAccessoryPO archivesFileAccessory = it.next();
        this.session.save(archivesFileAccessory);
        setTemp.add(archivesFileAccessory);
      } 
      archivesFile.setArchivesFileAccessory(setTemp);
      archivesFile.setResidualCount(Integer.valueOf(String.valueOf(
              Integer.parseInt(archivesFilePO.getCopyCount().toString()) - 
              Integer.parseInt(archivesFile.getCopyCount().toString()) + 
              Integer.parseInt(archivesFile.getResidualCount().toString()))));
      archivesFile.setCopyCount(archivesFilePO.getCopyCount());
      archivesFile.setFileId(archivesFilePO.getFileId());
      archivesFile.setFileKey(archivesFilePO.getFileKey());
      archivesFile.setFileName(archivesFilePO.getFileName());
      archivesFile.setFileNo(archivesFilePO.getFileNo());
      archivesFile.setFileRemark(archivesFilePO.getFileRemark());
      archivesFile.setFileStatus(archivesFilePO.getFileStatus());
      archivesFile.setPageCount(archivesFilePO.getPageCount());
      if (archivesFile.getPigeonholeStatus().intValue() == 1 && 
        archivesFilePO.getPigeonholeStatus().intValue() == 0)
        archivesFile.setPigeonholeDate(archivesFilePO.getPigeonholeDate()); 
      archivesFile.setPigeonholeStatus(archivesFilePO.getPigeonholeStatus());
      archivesFile.setPlaceNo(archivesFilePO.getPlaceNo());
      archivesFile.setPrincipal(archivesFilePO.getPrincipal());
      archivesFile.setPrincipalName(archivesFilePO.getPrincipalName());
      archivesFile.setRoomNo(archivesFilePO.getRoomNo());
      archivesFile.setSaveBeginTime(archivesFilePO.getSaveBeginTime());
      archivesFile.setSaveEndTime(archivesFilePO.getSaveEndTime());
      archivesFile.setSaveStyle(archivesFilePO.getSaveStyle());
      archivesFile.setSecretLevel(archivesFilePO.getSecretLevel());
      archivesFile.setDossierNO(archivesFilePO.getDossierNO());
      archivesFile.setClassReader(archivesFilePO.getClassReader());
      archivesFile.setClassReadGroup(archivesFilePO.getClassReadGroup());
      archivesFile.setClassReadName(archivesFilePO.getClassReadName());
      archivesFile.setClassReadOrg(archivesFilePO.getClassReadOrg());
      archivesFile.setSerialNO(archivesFilePO.getSerialNO());
      archivesFile.setRegistrNO(archivesFilePO.getRegistrNO());
      archivesFile.setClassNO(archivesFilePO.getClassNO());
      archivesFile.setMicroNO(archivesFilePO.getMicroNO());
      archivesFile.setDossierNO(archivesFilePO.getDossierNO());
      archivesFile.setModel(archivesFilePO.getModel());
      archivesFile.setArchiveCode(archivesFilePO.getArchiveCode());
      archivesFile.setDuty(archivesFilePO.getDuty());
      archivesFile.setAttendEmp(archivesFilePO.getAttendEmp());
      archivesFile.setAttendEmpName(archivesFilePO
          .getAttendEmpName());
      archivesFile.setPigeonholeOrg(archivesFilePO
          .getPigeonholeOrg());
      archivesFile.setPigeonholeOrgName(archivesFilePO
          .getPigeonholeOrgName());
      archivesFile.setAchievePhase(archivesFilePO
          .getAchievePhase());
      archivesFile.setItemClass(archivesFilePO.getItemClass());
      archivesFile.setVolume(archivesFilePO.getVolume());
      archivesFile.setTotalLength(archivesFilePO.getTotalLength());
      archivesFile.setDrawingNO(archivesFilePO.getDrawingNO());
      archivesFile.setSpecPage(archivesFilePO.getSpecPage());
      archivesFile.setCooperateUnits(archivesFilePO
          .getCooperateUnits());
      archivesFile.setAppraisalUnit(archivesFilePO
          .getAppraisalUnit());
      archivesFile.setPatentNO(archivesFilePO.getPatentNO());
      archivesFile.setAwardUnit(archivesFilePO.getAwardUnit());
      archivesFile.setHortationLevel(archivesFilePO
          .getHortationLevel());
      archivesFile.setMerit(archivesFilePO.getMerit());
      archivesFile.setTechnicData(archivesFilePO.getTechnicData());
      archivesFile.setReachLevel(archivesFilePO.getReachLevel());
      archivesFile.setAppraisalDate(archivesFilePO.getAppraisalDate());
      archivesFile.setAppraisalDate(archivesFilePO.getApproveDate());
      archivesFile.setAwardDate(archivesFilePO.getAwardDate());
      this.session.update(archivesFile);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("modiArchivesFileEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Long addArchivesBorrowFile(ArchivesBorrowPO archivesBorrowPO, Long fileId) throws HibernateException {
    Long result = new Long(-1L);
    try {
      begin();
      ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session.load(
          ArchivesFilePO.class, fileId);
      if (archivesFilePO.getResidualCount().intValue() - 
        archivesBorrowPO.getBorrowCount().intValue() > 0) {
        archivesFilePO.setIsBorrow(new Integer(0));
        archivesFilePO.setResidualCount(Integer.valueOf(String.valueOf(
                archivesFilePO.getResidualCount().intValue() - 
                archivesBorrowPO.getBorrowCount().intValue())));
      } 
      if (archivesFilePO.getResidualCount().intValue() - 
        archivesBorrowPO.getBorrowCount().intValue() == 0) {
        archivesFilePO.setIsBorrow(new Integer(1));
        archivesFilePO.setResidualCount(new Integer(0));
      } 
      archivesBorrowPO.setArchivesFile(archivesFilePO);
      result = (Long)this.session.save(archivesBorrowPO);
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("addArchivesBorrowFileEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean restituteArchivesFile(Long borrowId, Long fileId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      ArchivesFilePO archivesFilePO = (ArchivesFilePO)this.session.load(
          ArchivesFilePO.class, fileId);
      ArchivesBorrowPO archivesBorrowPO = (ArchivesBorrowPO)this.session.load(
          ArchivesBorrowPO.class, borrowId);
      archivesFilePO.setResidualCount(Integer.valueOf(String.valueOf(
              Integer.parseInt(archivesBorrowPO.getBorrowCount().toString()) + 
              Integer.parseInt(archivesFilePO.getResidualCount().toString()))));
      archivesFilePO.setIsBorrow(new Integer(0));
      this.session.update(archivesFilePO);
      archivesBorrowPO.setIsReturned(new Integer(1));
      archivesBorrowPO.setStatus(new Integer(4));
      archivesBorrowPO.setReturnDate(new Date());
      this.session.update(archivesBorrowPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("restituteArchivesFileEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectArchivesFileSearch(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, Integer currentPage, String searchRoomNo, String searchPlaceNo, String searchFileNo, String searchFileName, String searchFileKey, String searchFileRemark, Integer volume, String where) throws HibernateException {
    List<ArchivesFileVO> list = new ArrayList();
    try {
      begin();
      Query archivesFileQuery = this.session.createQuery("select archivesFile from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.fileStatus='" + 
          fileStatus + "' and archivesFile.pigeonholeStatus='" + 
          pigeonholeStatus + "' and archivesFile.placeNo like '%" + 
          searchPlaceNo + "%' and archivesFile.roomNo like '%" + 
          searchRoomNo + "%' and archivesFile.fileNo like '%" + 
          searchFileNo + "%' and archivesFile.fileName like '%" + 
          searchFileName + "%' and archivesFile.fileKey like '%" + 
          searchFileKey + "%' and archivesFile.fileRemark like '%" + 
          searchFileRemark + "%' and (" + where + 
          ") ORDER BY archivesFile.fileId DESC");
      archivesFileQuery.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      archivesFileQuery.setMaxResults(volume.intValue());
      List queryList = archivesFileQuery.list();
      Iterator<ArchivesFilePO> iterator = queryList.iterator();
      ArchivesFilePO archivesFilePO = null;
      while (iterator != null && iterator.hasNext()) {
        archivesFilePO = iterator.next();
        ArchivesFileVO archivesFileVO = 
          (ArchivesFileVO)TransformObject.getInstance()
          .transformObject(
            archivesFilePO, ArchivesFileVO.class);
        archivesFileVO.setMaintenance(Boolean.TRUE);
        list.add(archivesFileVO);
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public Integer getArchivesFileRecordSearchCount(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, String searchRoomNo, String searchPlaceNo, String searchFileNo, String searchFileName, String searchFileKey, String searchFileRemark, String where) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select count(archivesFile) from com.js.oa.archives.po.ArchivesFilePO archivesFile where (" + 
          where + 
          ") and archivesFile.fileStatus='" + 
          fileStatus + 
          "' and archivesFile.pigeonholeStatus='" + 
          pigeonholeStatus + 
          "' and archivesFile.placeNo like '%" + 
          searchPlaceNo + 
          "%' and archivesFile.roomNo like '%" + 
          searchRoomNo + 
          "%' and archivesFile.fileNo like '%" + 
          searchFileNo + 
          "%' and archivesFile.fileName like '%" + 
          searchFileName + 
          "%' and archivesFile.fileKey like '%" + 
          searchFileKey + 
          "%' and archivesFile.fileRemark like '%" + 
          searchFileRemark + "%'")
        .next();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesFileCountEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public ArchivesBorrowPO selectBorrowAuditingFile(Long borrowId) throws HibernateException {
    ArchivesBorrowPO archivesBorrowPO = null;
    begin();
    try {
      archivesBorrowPO = (ArchivesBorrowPO)this.session.load(ArchivesBorrowPO.class, 
          borrowId);
    } catch (HibernateException e) {
      System.out.println("selectBorrowAuditingFile EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return archivesBorrowPO;
  }
  
  public String selectResidualCount(Long fileId) throws HibernateException {
    String result = "";
    try {
      begin();
      Query query = this.session.createQuery("select archivesFile.residualCount from com.js.oa.archives.po.ArchivesFilePO archivesFile where archivesFile.fileId=" + 
          fileId);
      List<E> list = query.list();
      result = list.get(0).toString();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public ArchivesFilePO loadArchivesFilePO(Long fileId) throws HibernateException {
    ArchivesFilePO po = null;
    try {
      begin();
      po = (ArchivesFilePO)this.session.load(ArchivesFilePO.class, fileId);
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public boolean modiBorrowAuditingFile(ArchivesBorrowPO archivesBorrowPO, Long borrowId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesBorrowPO archivesBorrow = (ArchivesBorrowPO)this.session.load(ArchivesBorrowPO.class, 
          borrowId);
      archivesBorrow.setBorrowCount(archivesBorrowPO.getBorrowCount());
      archivesBorrow.setBorrowDate(archivesBorrowPO.getBorrowDate());
      archivesBorrow.setBorrowIntent(archivesBorrowPO.getBorrowIntent());
      archivesBorrow.setEmpId(archivesBorrowPO.getEmpId());
      archivesBorrow.setEmpName(archivesBorrowPO.getEmpName());
      archivesBorrow.setOrgId(archivesBorrowPO.getOrgId());
      archivesBorrow.setOrgName(archivesBorrowPO.getOrgName());
      archivesBorrow.setStatus(archivesBorrowPO.getStatus());
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("modiBorrowAuditingFile EJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean delBorrowAuditingFile(Long borrowId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesBorrowPO archivesBorrow = (ArchivesBorrowPO)this.session.load(
          ArchivesBorrowPO.class, borrowId);
      ArchivesFilePO archivesFile = archivesBorrow.getArchivesFile();
      archivesFile.setIsBorrow(new Integer(0));
      archivesFile.setResidualCount(Integer.valueOf(String.valueOf(
              Integer.parseInt(archivesBorrow.getBorrowCount().toString()) + 
              Integer.parseInt(archivesFile.getResidualCount().toString()))));
      this.session.delete(archivesBorrow);
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("deleteArchivesClass Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean untreadBorrowAuditingFile(Long borrowId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesBorrowPO archivesBorrow = (ArchivesBorrowPO)this.session.load(
          ArchivesBorrowPO.class, borrowId);
      ArchivesFilePO archivesFile = archivesBorrow.getArchivesFile();
      archivesFile.setIsBorrow(new Integer(0));
      archivesFile.setResidualCount(Integer.valueOf(String.valueOf(
              Integer.parseInt(archivesBorrow.getBorrowCount().toString()) + 
              Integer.parseInt(archivesFile.getResidualCount().toString()))));
      archivesBorrow.setStatus(new Integer(3));
      this.session.update(archivesBorrow);
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("deleteArchivesClass Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String maintenance(String selectValue, String from, String scopeWhere) throws HibernateException {
    String maintenanceIds = "";
    begin();
    try {
      Query query = this.session.createQuery("select " + selectValue + 
          " from " + from + " where " + 
          scopeWhere);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        maintenanceIds = String.valueOf(maintenanceIds) + list.get(i) + ","; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return maintenanceIds;
  }
  
  public Boolean isRepeatName(String from, String where) throws HibernateException {
    Boolean result = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery("from " + from + " where " + 
          where);
      List list = query.list();
      if (list.size() >= 1)
        result = Boolean.TRUE; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List selectArchivesPigeonholeSet(String domainId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query archivesPigeonholeSetQuery = this.session.createQuery("select archivesPigeonholeSetPO.pigeonholeSetId,archivesPigeonholeSetPO.pigeonholeSetType,archivesPigeonholeSetPO.pigeonholeSetPlace,archivesPigeonholeSetPO.isHold,archivesPigeonholeSetPO.isSendMessage from com.js.oa.archives.po.ArchivesPigeonholeSetPO archivesPigeonholeSetPO where archivesPigeonholeSetPO.domainId = " + 
          domainId);
      list = archivesPigeonholeSetQuery.list();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesPigeonholeSetEJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public boolean addArchivesPigeonholeSet(ArchivesPigeonholeSetPO archivesPigeonholeSetPO) throws HibernateException {
    boolean result = false;
    try {
      begin();
      this.session.save(archivesPigeonholeSetPO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesPigeonholeSetEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean updateArchivesPigeonholeSet(ArchivesPigeonholeSetPO archivesPigeonholeSetPO) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesPigeonholeSetPO archivesPigeonholeSet = (ArchivesPigeonholeSetPO)this.session.load(
          ArchivesPigeonholeSetPO.class, 
          archivesPigeonholeSetPO.getPigeonholeSetId());
      archivesPigeonholeSet.setPigeonholeSetId(archivesPigeonholeSetPO
          .getPigeonholeSetId());
      archivesPigeonholeSet.setPigeonholeSetType(archivesPigeonholeSetPO
          .getPigeonholeSetType());
      archivesPigeonholeSet.setPigeonholeSetPlace(archivesPigeonholeSetPO
          .getPigeonholeSetPlace());
      archivesPigeonholeSet.setIsHold(archivesPigeonholeSetPO.getIsHold());
      archivesPigeonholeSet.setIsSendMessage(archivesPigeonholeSetPO
          .getIsSendMessage());
      this.session.update(archivesPigeonholeSet);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("updateArchivesPigeonholeSetEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String archivesPigeonholeSet(String where, String domainId) throws HibernateException {
    String setValue = "";
    try {
      begin();
      Query query = this.session.createQuery("select archivesPigeonholeSetPO.pigeonholeSetId,archivesPigeonholeSetPO.pigeonholeSetType,archivesPigeonholeSetPO.pigeonholeSetPlace,archivesPigeonholeSetPO.isHold,archivesPigeonholeSetPO.isSendMessage from com.js.oa.archives.po.ArchivesPigeonholeSetPO archivesPigeonholeSetPO where archivesPigeonholeSetPO.pigeonholeSetType like '%" + 
          where + 
          "%' and archivesPigeonholeSetPO.domainId = " + 
          domainId);
      List<Object[]> list = query.list();
      if (list.size() > 0) {
        Object[] obj = list.get(0);
        String isSendMessage = "";
        if ("ZSGL".equals(where)) {
          String pigeonholeSetPlace = obj[2].toString();
          String isHold = obj[3].toString();
          isSendMessage = obj[4].toString();
          setValue = String.valueOf(pigeonholeSetPlace) + "," + isHold + "," + 
            isSendMessage;
        } else {
          isSendMessage = obj[4].toString();
          setValue = isSendMessage;
        } 
      } else {
        setValue = "-1";
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("archivesPigeonholeSet EJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return setValue;
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String createdEmp, String createdOrg, String domainId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesWaitPigeonholePO archivesWaitPigeonholePO = 
        new ArchivesWaitPigeonholePO();
      archivesWaitPigeonholePO.setPigeonholeCaption(pigeonholeCaption);
      archivesWaitPigeonholePO.setPigeonholeFileId(pigeonholeFileId);
      archivesWaitPigeonholePO.setPigeonholeFileName(pigeonholeFileName);
      archivesWaitPigeonholePO.setPigeonholeTypeName(pigeonholeTypeName);
      archivesWaitPigeonholePO.setPigeonholePromulgator(
          pigeonholePromulgator);
      archivesWaitPigeonholePO.setPigeonholeDate(pigeonholeDate);
      archivesWaitPigeonholePO.setPigeonholeState(new Integer(0));
      archivesWaitPigeonholePO.setCreatedEmp(createdEmp);
      archivesWaitPigeonholePO.setCreatedOrg(createdOrg);
      archivesWaitPigeonholePO.setDomainId(domainId);
      this.session.save(archivesWaitPigeonholePO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesWaitPigeonhole Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String domainId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesWaitPigeonholePO archivesWaitPigeonholePO = 
        new ArchivesWaitPigeonholePO();
      archivesWaitPigeonholePO.setPigeonholeCaption(pigeonholeCaption);
      archivesWaitPigeonholePO.setPigeonholeFileId(pigeonholeFileId);
      archivesWaitPigeonholePO.setPigeonholeFileName(pigeonholeFileName);
      archivesWaitPigeonholePO.setPigeonholeTypeName(pigeonholeTypeName);
      archivesWaitPigeonholePO.setPigeonholePromulgator(
          pigeonholePromulgator);
      archivesWaitPigeonholePO.setPigeonholeDate(pigeonholeDate);
      archivesWaitPigeonholePO.setPigeonholeState(new Integer(0));
      archivesWaitPigeonholePO.setDomainId(domainId);
      this.session.save(archivesWaitPigeonholePO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("addArchivesWaitPigeonhole Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean delArchivesWaitPigeonhole(Long waitPigeonholeId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ArchivesWaitPigeonholePO archivesWaitPigeonholePO = 
        (ArchivesWaitPigeonholePO)this.session.load(
          ArchivesWaitPigeonholePO.class, waitPigeonholeId);
      Long infomationID = archivesWaitPigeonholePO.getPigeonholeFileId();
      this.session.delete(archivesWaitPigeonholePO);
      Connection conn = null;
      Statement stmt = null;
      if (archivesWaitPigeonholePO.getPigeonholeTypeName().equals("ZSGL")) {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        stmt.executeUpdate(
            " update oa_information set DOSSIERSTATUS=0 where information_id=" + 
            infomationID);
      } 
      this.session.flush();
      result = true;
      if (conn != null) {
        stmt.close();
        conn.close();
      } 
    } catch (HibernateException ex) {
      System.out.println("delArchivesWaitPigeonholeEJB Exception:" + 
          ex.getMessage());
    } catch (Exception e) {
      System.out.println("error==");
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (Exception ex2) {
        System.out.println("ex2 error");
        ex2.printStackTrace();
      } 
    } 
    return result;
  }
  
  public boolean delArchivesWaitPigeonholeAll(String waitPigeonholeId) throws Exception {
    boolean result = false;
    try {
      begin();
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] _waitPigeonholeId = waitPigeonholeId.split(",");
      for (int i = 0; i < _waitPigeonholeId.length; i++) {
        ArchivesWaitPigeonholePO archivesWaitPigeonholePO = 
          (ArchivesWaitPigeonholePO)this.session.load(
            ArchivesWaitPigeonholePO.class, 
            new Long(_waitPigeonholeId[i]));
        if (archivesWaitPigeonholePO.getPigeonholeState().intValue() != 
          1) {
          if (archivesWaitPigeonholePO.getPigeonholeTypeName().equals(
              "ZSGL"))
            try {
              stmt.executeUpdate(
                  " update oa_information set DOSSIERSTATUS=0 where information_id=" + 
                  archivesWaitPigeonholePO
                  .getPigeonholeFileId());
              this.session.flush();
            } catch (Exception exception) {} 
          this.session.delete(archivesWaitPigeonholePO);
        } 
      } 
      this.session.flush();
      result = true;
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      System.out.println("delArchivesWaitPigeonholeEJB Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String selectRightUserIds(String righttype, String rightName, String domainId) throws HibernateException {
    String userIds = "";
    try {
      begin();
      Query query = this.session.createQuery("select distinct rightScope.employee.empId from com.js.system.vo.rolemanager.RightScopeVO rightScope left join rightScope.right right2 where right2.rightType ='" + 
          righttype + 
          "' and right2.rightName='" + 
          rightName + 
          "' and rightScope.domainId=" + 
          domainId);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        userIds = String.valueOf(userIds) + list.get(i) + ","; 
      userIds = userIds.substring(0, userIds.length() - 1);
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectRightUserIds EJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return userIds;
  }
  
  public ArchivesFilePO selectFileByBorrowApplyId(String recordId) throws HibernateException {
    ArchivesFilePO archivesFilePO = null;
    try {
      begin();
      List<ArchivesFilePO> list = this.session.createQuery(" from com.js.oa.archives.po.ArchivesFilePO po where po.fileId = (select bpo.fileId from com.js.oa.archives.po.ArchivesBorrowPO bpo where bpo.borrowId=" + 
          recordId + ")").list();
      if (list != null && list.size() > 0)
        archivesFilePO = list.get(0); 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectRightUserIds EJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return archivesFilePO;
  }
  
  public Integer havePigeholeSetInDomain(String domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      List list = this.session.createQuery(" from com.js.oa.archives.po.ArchivesPigeonholeSetPO po where po.domainId = " + 
          domainId).list();
      if (list != null && list.size() > 0)
        result = new Integer(1); 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectRightUserIds EJB Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String getClassNOByDossierNO(String DossierNO) {
    String sql = "SELECT classNO FROM oa_archivesdossier WHERE dossier_id=" + DossierNO;
    DataSourceUtil util = new DataSourceUtil();
    return ((String[])util.getListQuery(sql, "").get(0))[0];
  }
}
