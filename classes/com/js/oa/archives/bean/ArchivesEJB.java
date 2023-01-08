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
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface ArchivesEJB extends EJBObject {
  boolean delArchivesWaitPigeonholeAll(String paramString) throws HibernateException, RemoteException;
  
  Integer havePigeholeSetInDomain(String paramString) throws HibernateException, RemoteException;
  
  List selectArchivesClass(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  List selectArchivesClass(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException, RemoteException;
  
  Integer getArchivesClassRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  boolean addArchivesClass(ArchivesClassPO paramArchivesClassPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer selectIfSubordinateClass(Long paramLong) throws HibernateException, RemoteException;
  
  ArchivesClassVO selectArchivesClass(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteArchivesClass(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteBatchArchivesClass(String paramString) throws HibernateException, RemoteException;
  
  boolean modiArchivesClass(ArchivesClassPO paramArchivesClassPO, String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  boolean addArchivesDossier(ArchivesDossierPO paramArchivesDossierPO, Long paramLong) throws HibernateException, RemoteException;
  
  Integer getArchivesDossierRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  Map selectArchivesDossier(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiArchivesDossier(ArchivesDossierPO paramArchivesDossierPO, Long paramLong) throws HibernateException, RemoteException;
  
  Boolean logoutArchivesDossier(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean logoutBatchArchivesDossier(String paramString) throws HibernateException, RemoteException;
  
  Boolean cleanLogoutArchivesDossier() throws HibernateException, RemoteException;
  
  List selectLogoutArchivesDossier(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException, RemoteException;
  
  Integer getLogoutArchivesDossierRecordCount(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  Boolean resumeArchivesDossier(String paramString) throws Exception, RemoteException;
  
  Boolean delArchivesDossier(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean delBatchArchivesDossier(String paramString) throws HibernateException, RemoteException;
  
  Boolean cleanDelArchivesDossier() throws HibernateException, RemoteException;
  
  List selectArchivesFile(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Integer paramInteger1, Integer paramInteger2) throws HibernateException, RemoteException;
  
  Integer getArchivesFileRecordCount(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  List selectArchivesDossier(Long paramLong1, Long paramLong2, String paramString, Integer paramInteger1, Integer paramInteger2) throws HibernateException, RemoteException;
  
  List selectArchivesDossier(String paramString) throws HibernateException, RemoteException;
  
  List selectArchivesDossierType(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  boolean addArchivesFile(ArchivesFilePO paramArchivesFilePO, Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Boolean logoutArchivesFile(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean logoutBatchArchivesFile(String paramString) throws HibernateException, RemoteException;
  
  Boolean cleanLogoutArchivesFile() throws HibernateException, RemoteException;
  
  Boolean resumeArchivesFile(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean delArchivesFile(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean delBatchArchivesFile(String paramString) throws HibernateException, RemoteException;
  
  Boolean cleanDelArchivesFile(String paramString) throws HibernateException, RemoteException;
  
  Map selectArchivesFile(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiArchivesFile(ArchivesFilePO paramArchivesFilePO, Long paramLong) throws HibernateException, RemoteException;
  
  Long addArchivesBorrowFile(ArchivesBorrowPO paramArchivesBorrowPO, Long paramLong) throws HibernateException, RemoteException;
  
  Boolean restituteArchivesFile(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectArchivesFileSearch(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Integer paramInteger1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, Integer paramInteger2, String paramString9) throws HibernateException, RemoteException;
  
  Integer getArchivesFileRecordSearchCount(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) throws HibernateException, RemoteException;
  
  ArchivesBorrowPO selectBorrowAuditingFile(Long paramLong) throws HibernateException, RemoteException;
  
  String selectResidualCount(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiBorrowAuditingFile(ArchivesBorrowPO paramArchivesBorrowPO, Long paramLong) throws HibernateException, RemoteException;
  
  boolean delBorrowAuditingFile(Long paramLong) throws HibernateException, RemoteException;
  
  boolean untreadBorrowAuditingFile(Long paramLong) throws HibernateException, RemoteException;
  
  String maintenance(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List selectArchivesPigeonholeSet(String paramString) throws HibernateException, RemoteException;
  
  boolean addArchivesPigeonholeSet(ArchivesPigeonholeSetPO paramArchivesPigeonholeSetPO) throws HibernateException, RemoteException;
  
  boolean updateArchivesPigeonholeSet(ArchivesPigeonholeSetPO paramArchivesPigeonholeSetPO) throws HibernateException, RemoteException;
  
  String archivesPigeonholeSet(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  boolean addArchivesWaitPigeonhole(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, Date paramDate, String paramString5, String paramString6, String paramString7) throws HibernateException, RemoteException;
  
  boolean addArchivesWaitPigeonhole(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, Date paramDate, String paramString5) throws HibernateException, RemoteException;
  
  boolean delArchivesWaitPigeonhole(Long paramLong) throws HibernateException, RemoteException;
  
  String selectRightUserIds(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  ArchivesFilePO selectFileByBorrowApplyId(String paramString) throws RemoteException, HibernateException;
}
