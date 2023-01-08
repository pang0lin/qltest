package com.js.oa.info.infomanager.bean;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.po.IsoCommentPO;
import com.js.oa.info.isodoc.po.IsoDeallogPO;
import com.js.oa.info.isodoc.po.IsoPaperPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface InformationEJB extends EJBObject {
  List getSingleInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[] getSingleEditor(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getOrgName(String paramString) throws Exception, RemoteException;
  
  List getAllOrgName(String paramString) throws Exception, RemoteException;
  
  List getinformation(String paramString) throws Exception, RemoteException;
  
  void setBrowser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getBrowser(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getHistoryVersion(String paramString) throws Exception, RemoteException;
  
  List getComment(String paramString) throws Exception, RemoteException;
  
  List getinformationID(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getUserViewCh(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  void setComment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  void setKits(String paramString) throws Exception, RemoteException;
  
  void saveHistory(String paramString) throws Exception, RemoteException;
  
  void update(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6) throws Exception, RemoteException;
  
  void deleteAccessory(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean informationStatus(String paramString) throws Exception, RemoteException;
  
  List getchannleinfo(String paramString) throws Exception, RemoteException;
  
  List getSingleHistInfo(String paramString) throws Exception, RemoteException;
  
  void commend(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List batchDelete(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List allDelete(String paramString) throws Exception, RemoteException;
  
  List singleDelete(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void transfer(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getNew(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getContent(String paramString) throws Exception, RemoteException;
  
  String getInfoReader(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getAssociateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  void updateProcInfo(String paramString, List paramList) throws Exception, RemoteException;
  
  String getAccessoryType(String paramString) throws Exception, RemoteException;
  
  Long save(InformationPO paramInformationPO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getNotBrowser(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer getUserIssueInfoCount(String paramString) throws Exception, RemoteException;
  
  Integer setDossierStatus(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean vindicateInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getInfoUserdefine(String paramString) throws Exception, RemoteException;
  
  Map getMustReadCount(String paramString) throws Exception, RemoteException;
  
  List getMustReadInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer setOrderCode(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  Boolean channelCanView(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  Object[] getAllBrowser(String paramString) throws Exception, RemoteException;
  
  Integer delComment(String paramString) throws Exception, RemoteException;
  
  void removeCommend(String paramString) throws Exception, RemoteException;
  
  List getUserViewCh2(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getInformationModiIds(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, List paramList) throws Exception;
  
  String deleteHistory(String paramString1, String paramString2) throws Exception;
  
  List getAfficheList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  boolean setInformationStatus(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getManagedChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getAllGroupByUserId(String paramString) throws Exception, RemoteException;
  
  Boolean channelCanView2(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getUserViewCh3(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getAllInfoChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Long saveIsoPaperPO(IsoPaperPO paramIsoPaperPO) throws Exception, RemoteException;
  
  String setPaperPOStatus(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String deletePaperPO(String paramString) throws Exception, RemoteException;
  
  Long saveBorrowPO(IsoBorrowUserPO paramIsoBorrowUserPO) throws Exception, RemoteException;
  
  Long updateBorrowPO(IsoBorrowUserPO paramIsoBorrowUserPO) throws Exception, RemoteException;
  
  String setBorrowStatus(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String deleteBorrow(String paramString) throws Exception, RemoteException;
  
  IsoBorrowUserPO loadBorrowUserPO(String paramString) throws Exception, RemoteException;
  
  List findIdsFromBorrow(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  boolean setInformationStatus(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void updateBigVersion(String paramString) throws Exception, RemoteException;
  
  List getInforByVersion(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getHisModiNum(String paramString) throws Exception, RemoteException;
  
  List getAssociateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  List getCanVindicate_ISO(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String updatePaperPO(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  IsoPaperPO loadIsoPaperPO(String paramString) throws Exception, RemoteException;
  
  List getBrowByEmpAndIfoId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long saveIsoCommentPO(IsoCommentPO paramIsoCommentPO) throws Exception, RemoteException;
  
  List getIsoCommentList(String paramString) throws Exception, RemoteException;
  
  List getCommentList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void TransferUserId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getIsoDeallogList(String paramString) throws Exception, RemoteException;
  
  Long saveIsoDeallogPO(IsoDeallogPO paramIsoDeallogPO) throws Exception, RemoteException;
  
  boolean setInformationStatus(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  Integer getIssueNumOrg(String paramString) throws Exception, RemoteException;
  
  Integer getIssueNumPerson(String paramString) throws Exception, RemoteException;
  
  void updateComment(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void setBrowserKits(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
}
