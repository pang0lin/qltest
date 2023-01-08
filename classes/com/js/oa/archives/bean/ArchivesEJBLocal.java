package com.js.oa.archives.bean;

import com.js.oa.archives.po.ArchivesBorrowPO;
import com.js.oa.archives.po.ArchivesClassPO;
import com.js.oa.archives.po.ArchivesDossierPO;
import com.js.oa.archives.po.ArchivesFilePO;
import com.js.oa.archives.po.ArchivesPigeonholeSetPO;
import com.js.oa.archives.vo.ArchivesClassVO;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface ArchivesEJBLocal extends EJBLocalObject {
  boolean delArchivesWaitPigeonholeAll(String paramString) throws HibernateException;
  
  Integer havePigeholeSetInDomain(String paramString) throws HibernateException;
  
  List selectArchivesClass(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  List selectArchivesClass(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException;
  
  Integer getArchivesClassRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  boolean addArchivesClass(ArchivesClassPO paramArchivesClassPO, String paramString1, String paramString2) throws HibernateException;
  
  Integer selectIfSubordinateClass(Long paramLong) throws HibernateException;
  
  ArchivesClassVO selectArchivesClass(Long paramLong) throws HibernateException;
  
  Boolean deleteArchivesClass(Long paramLong) throws HibernateException;
  
  Boolean deleteBatchArchivesClass(String paramString) throws HibernateException;
  
  boolean modiArchivesClass(ArchivesClassPO paramArchivesClassPO, String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  boolean addArchivesDossier(ArchivesDossierPO paramArchivesDossierPO, Long paramLong) throws HibernateException;
  
  Integer getArchivesDossierRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  List selectArchivesDossier(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException;
  
  List selectArchivesDossierType(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Map selectArchivesDossier(Long paramLong) throws HibernateException;
  
  boolean modiArchivesDossier(ArchivesDossierPO paramArchivesDossierPO, Long paramLong) throws HibernateException;
  
  Boolean logoutArchivesDossier(Long paramLong) throws HibernateException;
  
  Boolean logoutBatchArchivesDossier(String paramString) throws HibernateException;
  
  Boolean cleanLogoutArchivesDossier() throws HibernateException;
  
  List selectLogoutArchivesDossier(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException;
  
  Integer getLogoutArchivesDossierRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  Boolean resumeArchivesDossier(String paramString) throws Exception;
  
  Boolean delArchivesDossier(Long paramLong) throws HibernateException;
  
  Boolean delBatchArchivesDossier(String paramString) throws HibernateException;
  
  Boolean cleanDelArchivesDossier() throws HibernateException;
  
  List selectArchivesFile(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Integer paramInteger1, Integer paramInteger2) throws HibernateException;
  
  Integer getArchivesFileRecordCount(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  List selectArchivesDossier(String paramString) throws HibernateException;
  
  boolean addArchivesFile(ArchivesFilePO paramArchivesFilePO, Long paramLong1, Long paramLong2) throws HibernateException;
  
  Boolean logoutArchivesFile(Long paramLong) throws HibernateException;
  
  Boolean logoutBatchArchivesFile(String paramString) throws HibernateException;
  
  Boolean cleanLogoutArchivesFile() throws HibernateException;
  
  Boolean resumeArchivesFile(Long paramLong) throws HibernateException;
  
  Boolean delArchivesFile(Long paramLong) throws HibernateException;
  
  Boolean delBatchArchivesFile(String paramString) throws HibernateException;
  
  Boolean cleanDelArchivesFile(String paramString) throws HibernateException;
  
  Map selectArchivesFile(Long paramLong) throws HibernateException;
  
  boolean modiArchivesFile(ArchivesFilePO paramArchivesFilePO, Long paramLong) throws HibernateException;
  
  Long addArchivesBorrowFile(ArchivesBorrowPO paramArchivesBorrowPO, Long paramLong) throws HibernateException;
  
  Boolean restituteArchivesFile(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectArchivesFileSearch(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Integer paramInteger1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, Integer paramInteger2, String paramString9) throws HibernateException;
  
  Integer getArchivesFileRecordSearchCount(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) throws HibernateException;
  
  ArchivesBorrowPO selectBorrowAuditingFile(Long paramLong) throws HibernateException;
  
  String selectResidualCount(Long paramLong) throws HibernateException;
  
  boolean modiBorrowAuditingFile(ArchivesBorrowPO paramArchivesBorrowPO, Long paramLong) throws HibernateException;
  
  boolean delBorrowAuditingFile(Long paramLong) throws HibernateException;
  
  boolean untreadBorrowAuditingFile(Long paramLong) throws HibernateException;
  
  String maintenance(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws HibernateException;
  
  List selectArchivesPigeonholeSet(String paramString) throws HibernateException;
  
  boolean addArchivesPigeonholeSet(ArchivesPigeonholeSetPO paramArchivesPigeonholeSetPO) throws HibernateException;
  
  boolean updateArchivesPigeonholeSet(ArchivesPigeonholeSetPO paramArchivesPigeonholeSetPO) throws HibernateException;
  
  String archivesPigeonholeSet(String paramString1, String paramString2) throws HibernateException;
  
  boolean addArchivesWaitPigeonhole(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, Date paramDate, String paramString5, String paramString6, String paramString7) throws HibernateException;
  
  boolean addArchivesWaitPigeonhole(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, Date paramDate, String paramString5) throws HibernateException;
  
  boolean delArchivesWaitPigeonhole(Long paramLong) throws HibernateException;
  
  String selectRightUserIds(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  ArchivesFilePO selectFileByBorrowApplyId(String paramString) throws HibernateException;
}
