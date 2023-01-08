package com.js.oa.archives.service;

import com.js.oa.archives.bean.ArchivesEJBBean;
import com.js.oa.archives.bean.ArchivesEJBHome;
import com.js.oa.archives.po.ArchivesBorrowPO;
import com.js.oa.archives.po.ArchivesClassPO;
import com.js.oa.archives.po.ArchivesDossierPO;
import com.js.oa.archives.po.ArchivesFilePO;
import com.js.oa.archives.po.ArchivesPigeonholeSetPO;
import com.js.oa.archives.vo.ArchivesClassVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ArchivesBD {
  private static Logger logger = (Logger)Logger.getInstance(ArchivesBD.class
      .getName());
  
  private Integer volume = new Integer(15);
  
  private int recordCount = 0;
  
  public List selectArchivesClass(Long userId, Long orgId, String where, Integer currentPage) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      list = (List)ejbProxy.invoke("selectArchivesClass", pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      ret = (Integer)ejbProxy.invoke("getArchivesClassRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All ArchivesClass Exception:" + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
  
  public List selectArchivesClass(Long userId, Long orgId, String where) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      list = (List)ejbProxy.invoke("selectArchivesClass", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All ArchivesClass Exception:" + ex.getMessage());
    } finally {}
    return list;
  }
  
  public boolean addArchivesClass(ArchivesClassPO archivesClassPO, String taxis, String state) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesClassPO, ArchivesClassPO.class);
      pg.put(taxis, "String");
      pg.put(state, "String");
      ejbProxy.invoke("addArchivesClass", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("AddArchivesClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public ArchivesClassVO selectArchivesClass(Long classId) {
    ArchivesClassVO archivesClassVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(classId, "Long");
      archivesClassVO = (ArchivesClassVO)ejbProxy.invoke(
          "selectArchivesClass", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectArchivesClass Exception:" + 
          ex.getMessage());
    } finally {}
    return archivesClassVO;
  }
  
  public boolean deleteArchivesClass(Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(classId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteArchivesClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("");
    } finally {}
    return result;
  }
  
  public boolean deleteBatchArchivesClass(String classIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(classIds, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchArchivesClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("deleteBatchArchivesClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modiArchivesClass(ArchivesClassPO archivesClassPO, String taxis, String state, String classOrderCode) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesClassPO, ArchivesClassPO.class);
      pg.put(taxis, "String");
      pg.put(state, "String");
      pg.put(classOrderCode, "String");
      ejbProxy.invoke("modiArchivesClass", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("ModiArchivesClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addArchivesDossier(ArchivesDossierPO archivesDossierPO, Long classNo) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesDossierPO, ArchivesDossierPO.class);
      pg.put(classNo, "Long");
      ejbProxy.invoke("addArchivesDossier", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("AddArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectArchivesDossier(Long userId, Long orgId, String where, Integer currentPage) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      list = (List)ejbProxy.invoke("selectArchivesDossier", 
          pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      ret = (Integer)ejbProxy.invoke("getArchivesDossierRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
  
  public Map selectArchivesDossier(Long dossierId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierId, "Long");
      result = (Map)ejbProxy.invoke("selectArchivesDossier", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectArchivesDossier Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modiArchivesDossier(ArchivesDossierPO archivesDossierPO, Long classNo) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesDossierPO, ArchivesDossierPO.class);
      pg.put(classNo, "Long");
      ejbProxy.invoke("modiArchivesDossier", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("ModiArchivesDossier Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean logoutArchivesDossier(Long dossierId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierId, "Long");
      result = ((Boolean)ejbProxy.invoke("logoutArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("logoutArchivesDossierBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean logoutBatchArchivesDossier(String dossierIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierIds, "String");
      result = ((Boolean)ejbProxy.invoke("logoutBatchArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("logoutBatchArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean cleanLogoutArchivesDossier() {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("cleanLogoutArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("cleanLogoutArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectLogoutArchivesDossier(Long userId, Long orgId, String where, Integer currentPage) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      list = (List)ejbProxy.invoke("selectLogoutArchivesDossier", 
          pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(where, "String");
      ret = (Integer)ejbProxy.invoke(
          "getLogoutArchivesDossierRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectLogoutArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
  
  public boolean resumeArchivesDossier(String dossierId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierId, "String");
      result = ((Boolean)ejbProxy.invoke("resumeArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("resumeArchivesDossierBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean delArchivesDossier(Long dossierId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierId, "Long");
      result = ((Boolean)ejbProxy.invoke("delArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delArchivesDossierBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean delBatchArchivesDossier(String dossierIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(dossierIds, "String");
      result = ((Boolean)ejbProxy.invoke("delBatchArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delBatchArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean cleanDelArchivesDossier() {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("cleanDelArchivesDossier", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("cleanDelArchivesDossierBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectArchivesFile(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, String where, Integer currentPage) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(fileStatus, "String");
      pg.put(pigeonholeStatus, "String");
      pg.put(where, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      list = (List)ejbProxy.invoke("selectArchivesFile", 
          pg.getParameters());
      pg = new ParameterGenerator(5);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(fileStatus, "String");
      pg.put(pigeonholeStatus, "String");
      pg.put(where, "String");
      ret = (Integer)ejbProxy.invoke("getArchivesFileRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
  
  public List selectArchivesDossier(String where) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(where, "String");
      list = (List)ejbProxy.invoke("selectArchivesDossier", 
          pg.getParameters());
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {}
    return list;
  }
  
  public List selectArchivesDossierType(String where, String Type) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(where, "String");
      pg.put(Type, "String");
      list = (List)ejbProxy.invoke("selectArchivesDossierType", 
          pg.getParameters());
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("selectArchivesDossierType Exception:" + ex.getMessage());
    } finally {}
    return list;
  }
  
  public boolean addArchivesFile(ArchivesFilePO archivesFilePO, Long dossierId, Long waitPigeonholeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesFilePO, ArchivesFilePO.class);
      pg.put(dossierId, "Long");
      pg.put(waitPigeonholeId, "Long");
      ejbProxy.invoke("addArchivesFile", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("AddArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean logoutArchivesFile(Long fileId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      result = ((Boolean)ejbProxy.invoke("logoutArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("logoutArchivesFileBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean logoutBatchArchivesFile(String fileIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileIds, "String");
      result = ((Boolean)ejbProxy.invoke("logoutBatchArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("logoutBatchArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean cleanLogoutArchivesFile() {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(0);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("cleanLogoutArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("cleanLogoutArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean resumeArchivesFile(Long fileId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      result = ((Boolean)ejbProxy.invoke("resumeArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("resumeArchivesFileBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean delArchivesFile(Long fileId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      result = ((Boolean)ejbProxy.invoke("delArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delArchivesFileBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public boolean delBatchArchivesFile(String fileIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileIds, "String");
      result = ((Boolean)ejbProxy.invoke("delBatchArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delBatchArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean cleanDelArchivesFile(String fileState) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileState, "String");
      result = ((Boolean)ejbProxy.invoke("cleanDelArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("cleanDelArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Map selectArchivesFile(Long fileId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      result = (Map)ejbProxy.invoke("selectArchivesFile", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectArchivesFile Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modiArchivesFile(ArchivesFilePO archivesFilePO, Long dossierId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesFilePO, ArchivesFilePO.class);
      pg.put(dossierId, "Long");
      ejbProxy.invoke("modiArchivesFile", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("ModiArchivesFile Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Long addArchivesBorrowFile(ArchivesBorrowPO archivesBorrowPO, Long fileId) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesBorrowPO, ArchivesBorrowPO.class);
      pg.put(fileId, "Long");
      result = (Long)ejbProxy.invoke("addArchivesBorrowFile", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("addArchivesBorrowFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean restituteArchivesFile(Long borrowId, Long fileId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(borrowId, "Long");
      pg.put(fileId, "Long");
      result = ((Boolean)ejbProxy.invoke("restituteArchivesFile", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("restituteArchivesFileBD Exception:" + ex);
    } finally {}
    return result;
  }
  
  public List selectArchivesFileSearch(Long userId, Long orgId, String fileStatus, String pigeonholeStatus, String searchRoomNo, String searchPlaceNo, String searchFileNo, String searchFileName, String searchFileKey, String searchFileRemark, Integer currentPage, String where) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(13);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(fileStatus, "String");
      pg.put(pigeonholeStatus, "String");
      pg.put(currentPage, "Integer");
      pg.put(searchRoomNo, "String");
      pg.put(searchPlaceNo, "String");
      pg.put(searchFileNo, "String");
      pg.put(searchFileName, "String");
      pg.put(searchFileKey, "String");
      pg.put(searchFileRemark, "String");
      pg.put(this.volume, "Integer");
      pg.put(where, "String");
      list = (List)ejbProxy.invoke("selectArchivesFileSearch", 
          pg.getParameters());
      pg = new ParameterGenerator(11);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(fileStatus, "String");
      pg.put(pigeonholeStatus, "String");
      pg.put(searchRoomNo, "String");
      pg.put(searchPlaceNo, "String");
      pg.put(searchFileNo, "String");
      pg.put(searchFileName, "String");
      pg.put(searchFileKey, "String");
      pg.put(searchFileRemark, "String");
      pg.put(where, "String");
      ret = (Integer)ejbProxy.invoke("getArchivesFileRecordSearchCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("SelectArchivesFileBD Exception:" + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
  
  public ArchivesBorrowPO selectBorrowAuditingFile(Long borrowId) {
    ArchivesBorrowPO archivesBorrowPO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(borrowId, Long.class);
      archivesBorrowPO = (ArchivesBorrowPO)ejbProxy.invoke(
          "selectBorrowAuditingFile", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectBorrowAuditingFile BD Exception:" + 
          ex.getMessage());
    } finally {}
    return archivesBorrowPO;
  }
  
  public boolean modiBorrowAuditingFile(ArchivesBorrowPO archivesBorrowPO, Long borrowId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesBorrowPO, ArchivesBorrowPO.class);
      pg.put(borrowId, "Long");
      ejbProxy.invoke("modiBorrowAuditingFile", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modiBorrowAuditingFile Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delBorrowAuditingFile(Long borrowId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(borrowId, "Long");
      ejbProxy.invoke("delBorrowAuditingFile", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delBorrowAuditingFile Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String selectResidualCount(Long fileId) {
    String residualCount = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      residualCount = (String)ejbProxy.invoke("selectResidualCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All selectResidualCount Exception:" + 
          ex.getMessage());
    } finally {}
    return residualCount;
  }
  
  public ArchivesFilePO loadArchivesFilePO(Long fileId) {
    ArchivesFilePO po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(fileId, "Long");
      po = (ArchivesFilePO)ejbProxy.invoke("loadArchivesFilePO", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("loadArchivesFilePO Exception:" + ex.getMessage());
    } finally {}
    return po;
  }
  
  public boolean untreadBorrowAuditingFile(Long borrowId) {
    boolean residualCount = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(borrowId, "Long");
      ejbProxy.invoke("untreadBorrowAuditingFile", pg.getParameters());
      residualCount = true;
    } catch (Exception ex) {
      System.out.println("Select All selectResidualCount Exception:" + 
          ex.getMessage());
    } 
    return residualCount;
  }
  
  public String maintenance(String selectValue, String from, String scopeWhere) {
    String maintenanceIds = "";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(selectValue, String.class);
      pg.put(from, String.class);
      pg.put(scopeWhere, String.class);
      maintenanceIds = (String)ejbProxy.invoke("maintenance", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("maintenance BD Exception:" + 
          ex.getMessage());
    } finally {}
    return maintenanceIds;
  }
  
  public boolean isRepeatName(String from, String where) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(from, String.class);
      pg.put(where, String.class);
      Boolean b = (Boolean)ejbProxy.invoke("isRepeatName", 
          pg.getParameters());
      result = b.booleanValue();
    } catch (Exception ex) {
      System.out.println("isRepeatNameBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectArchivesPigeonholeSet(String domainId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      list = (List)ejbProxy.invoke("selectArchivesPigeonholeSet", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectArchivesPigeonholeSet Exception:" + 
          ex.getMessage());
    } finally {}
    return list;
  }
  
  public boolean addArchivesPigeonholeSet(ArchivesPigeonholeSetPO archivesPigeonholeSetPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesPigeonholeSetPO, ArchivesPigeonholeSetPO.class);
      ejbProxy.invoke("addArchivesPigeonholeSet", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addArchivesPigeonholeSetBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public int havePigeholeSetInDomain(String domainId) {
    int result = 0;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(domainId, String.class);
      result = ((Integer)ejbProxy.invoke("havePigeholeSetInDomain", 
          pg.getParameters())).intValue();
    } catch (Exception ex) {
      System.out.println("havePigeholeSetInDomain Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateArchivesPigeonholeSet(ArchivesPigeonholeSetPO archivesPigeonholeSetPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(archivesPigeonholeSetPO, ArchivesPigeonholeSetPO.class);
      ejbProxy.invoke("updateArchivesPigeonholeSet", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("updateArchivesPigeonholeSetBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String archivesPigeonholeSet(String where, String domainId) {
    String setValue = "";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(where, "String");
      pg.put(domainId, "String");
      setValue = (String)ejbProxy.invoke("archivesPigeonholeSet", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All selectResidualCount Exception:" + 
          ex.getMessage());
    } finally {}
    return setValue;
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String createdEmp, String createdOrg, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(9);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(pigeonholeCaption, String.class);
      pg.put(pigeonholeFileName, String.class);
      pg.put(pigeonholeFileId, Long.class);
      pg.put(pigeonholeTypeName, String.class);
      pg.put(pigeonholePromulgator, String.class);
      pg.put(pigeonholeDate, "Date");
      pg.put(createdEmp, String.class);
      pg.put(createdOrg, String.class);
      pg.put(domainId, String.class);
      ejbProxy.invoke("addArchivesWaitPigeonhole", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("AddArchivesWaitPigeonhole Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addArchivesWaitPigeonhole(String pigeonholeCaption, String pigeonholeFileName, Long pigeonholeFileId, String pigeonholeTypeName, String pigeonholePromulgator, Date pigeonholeDate, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(pigeonholeCaption, String.class);
      pg.put(pigeonholeFileName, String.class);
      pg.put(pigeonholeFileId, Long.class);
      pg.put(pigeonholeTypeName, String.class);
      pg.put(pigeonholePromulgator, String.class);
      pg.put(pigeonholeDate, "Date");
      pg.put(domainId, String.class);
      ejbProxy.invoke("addArchivesWaitPigeonhole", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("AddArchivesWaitPigeonhole Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delArchivesWaitPigeonhole(Long waitPigeonholeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(waitPigeonholeId, "Long");
      ejbProxy.invoke("delArchivesWaitPigeonhole", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delArchivesWaitPigeonholeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delArchivesWaitPigeonholeAll(String waitPigeonholeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(waitPigeonholeId, "String");
      ejbProxy.invoke("delArchivesWaitPigeonholeAll", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delArchivesWaitPigeonholeBD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String selectRightUserIds(String righttype, String rightName, String domainId) {
    String userIds = "";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(righttype, String.class);
      pg.put(rightName, String.class);
      pg.put(domainId, String.class);
      userIds = (String)ejbProxy.invoke("selectRightUserIds", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All selectRightUserIds Exception:" + 
          ex.getMessage());
    } finally {}
    return userIds;
  }
  
  public void restoreBorrowCountAfterCancel(String recordId) throws InvocationTargetException, IllegalAccessException {
    ArchivesBorrowPO archivesBorrowPO = selectBorrowAuditingFile(
        Long.valueOf(recordId));
    ArchivesFilePO archivesFilePO = selectFileByBorrowApplyId(recordId);
    archivesFilePO.setResidualCount(new Integer(archivesBorrowPO
          .getBorrowCount()
          .intValue() + 
          archivesFilePO.getResidualCount()
          .intValue()));
    modiArchivesFile(archivesFilePO, archivesFilePO.getDossierId());
  }
  
  private ArchivesFilePO selectFileByBorrowApplyId(String recordId) {
    ArchivesFilePO archivesFilePO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ArchivesEJB", 
          "ArchivesEJBLocal", ArchivesEJBHome.class);
      pg.put(recordId, String.class);
      archivesFilePO = (ArchivesFilePO)ejbProxy.invoke(
          "selectFileByBorrowApplyId", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectFileByBorrowApplyId Exception:" + 
          ex.getMessage());
    } finally {}
    return archivesFilePO;
  }
  
  public String getClassNOByDossierNO(String DossierNO) {
    return (new ArchivesEJBBean()).getClassNOByDossierNO(DossierNO);
  }
  
  public int getVolume() {
    return this.volume.intValue();
  }
  
  public void setVolume(int volume) {
    this.volume = new Integer(volume);
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int value) {
    this.recordCount = value;
  }
}
