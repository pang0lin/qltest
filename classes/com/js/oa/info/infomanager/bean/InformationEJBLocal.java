package com.js.oa.info.infomanager.bean;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.po.IsoCommentPO;
import com.js.oa.info.isodoc.po.IsoDeallogPO;
import com.js.oa.info.isodoc.po.IsoPaperPO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface InformationEJBLocal extends EJBLocalObject {
  List getSingleInfo(String paramString1, String paramString2) throws Exception;
  
  String[] getSingleEditor(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getOrgName(String paramString) throws Exception;
  
  List getAllOrgName(String paramString) throws Exception;
  
  List getinformation(String paramString) throws Exception;
  
  void setBrowser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  boolean informationStatus(String paramString) throws Exception;
  
  List getchannleinfo(String paramString) throws Exception;
  
  List getBrowser(String paramString1, String paramString2) throws Exception;
  
  List getHistoryVersion(String paramString) throws Exception;
  
  List getComment(String paramString) throws Exception;
  
  List getOrderedComment(String paramString) throws Exception;
  
  List getinformationID(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void setComment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  void setComment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  void setKits(String paramString) throws Exception;
  
  void saveHistory(String paramString) throws Exception;
  
  void update(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6) throws Exception;
  
  String getUserViewCh(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  void deleteAccessory(String paramString1, String paramString2) throws Exception;
  
  List getSingleHistInfo(String paramString) throws Exception;
  
  void commend(String[] paramArrayOfString) throws Exception;
  
  List batchDelete(String[] paramArrayOfString) throws Exception;
  
  List allDelete(String paramString) throws Exception;
  
  List singleDelete(String paramString1, String paramString2) throws Exception;
  
  void transfer(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception;
  
  List getNew(String paramString1, String paramString2) throws Exception;
  
  String getContent(String paramString) throws Exception;
  
  String getInfoReader(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getAssociateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  void updateProcInfo(String paramString, List paramList) throws Exception;
  
  String getAccessoryType(String paramString) throws Exception;
  
  Long save(InformationPO paramInformationPO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, String paramString1, String paramString2) throws Exception;
  
  List getNotBrowser(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer getUserIssueInfoCount(String paramString) throws Exception;
  
  Integer setDossierStatus(String paramString1, String paramString2) throws Exception;
  
  Boolean vindicateInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getInfoUserdefine(String paramString) throws Exception;
  
  Map getMustReadCount(String paramString) throws Exception;
  
  List getMustReadInfo(String paramString1, String paramString2) throws Exception;
  
  Integer setOrderCode(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  Boolean channelCanView(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  Object[] getAllBrowser(String paramString) throws Exception;
  
  Integer delComment(String paramString) throws Exception;
  
  void removeCommend(String paramString) throws Exception;
  
  List getUserViewCh2(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getInformationModiIds(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, List paramList) throws Exception;
  
  String deleteHistory(String paramString1, String paramString2) throws Exception;
  
  List getAfficheList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  boolean setInformationStatus(String paramString1, String paramString2) throws Exception;
  
  String getManagedChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getAllGroupByUserId(String paramString) throws Exception;
  
  Boolean channelCanView2(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getUserViewCh3(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getAllInfoChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Long saveIsoPaperPO(IsoPaperPO paramIsoPaperPO) throws Exception;
  
  String setPaperPOStatus(String paramString1, String paramString2) throws Exception;
  
  String deletePaperPO(String paramString) throws Exception;
  
  Long saveBorrowPO(IsoBorrowUserPO paramIsoBorrowUserPO) throws Exception;
  
  Long updateBorrowPO(IsoBorrowUserPO paramIsoBorrowUserPO) throws Exception;
  
  String setBorrowStatus(String paramString1, String paramString2) throws Exception;
  
  String deleteBorrow(String paramString) throws Exception;
  
  IsoBorrowUserPO loadBorrowUserPO(String paramString) throws Exception;
  
  List findIdsFromBorrow(String paramString1, String paramString2, String paramString3) throws Exception;
  
  boolean setInformationStatus(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void updateBigVersion(String paramString) throws Exception;
  
  List getInforByVersion(String paramString1, String paramString2) throws Exception;
  
  String getHisModiNum(String paramString) throws Exception;
  
  List getAssociateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  List getCanVindicate_ISO(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String updatePaperPO(String paramString, String[] paramArrayOfString) throws Exception;
  
  IsoPaperPO loadIsoPaperPO(String paramString) throws Exception;
  
  List getBrowByEmpAndIfoId(String paramString1, String paramString2) throws Exception;
  
  Long saveIsoCommentPO(IsoCommentPO paramIsoCommentPO) throws Exception;
  
  List getIsoCommentList(String paramString) throws Exception;
  
  List getCommentList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void TransferUserId(String paramString1, String paramString2) throws Exception;
  
  List getIsoDeallogList(String paramString) throws Exception;
  
  Long saveIsoDeallogPO(IsoDeallogPO paramIsoDeallogPO) throws Exception;
  
  boolean setInformationStatus(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  Integer getIssueNumOrg(String paramString) throws Exception;
  
  Integer getIssueNumPerson(String paramString) throws Exception;
  
  void updateComment(String paramString1, String paramString2) throws Exception;
  
  void setBrowserKits(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
}
