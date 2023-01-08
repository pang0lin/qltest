package com.js.oa.message.bean;

import com.js.oa.message.po.MsDescribePO;
import com.js.oa.message.po.MsInfoListPO;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface MessageEJB extends EJBObject {
  HashMap listView(Long paramLong) throws Exception, RemoteException;
  
  boolean addDescribe(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6) throws Exception, RemoteException;
  
  boolean addMsgFlow(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6) throws Exception, RemoteException;
  
  MsDescribePO listDescribe(String paramString) throws Exception, RemoteException;
  
  String selectManSign(Long paramLong, String paramString) throws Exception, RemoteException;
  
  boolean delDescribeBatch(String paramString) throws Exception, RemoteException;
  
  boolean deletedboxBatch(String paramString) throws Exception, RemoteException;
  
  boolean delRealReceiveBoxBatch(String paramString) throws Exception, RemoteException;
  
  boolean receiveToDescribe(String paramString) throws Exception, RemoteException;
  
  boolean delRealSendedBoxBatch(String paramString) throws Exception, RemoteException;
  
  boolean sendedToDescribe(String paramString) throws Exception, RemoteException;
  
  boolean receivedClearToDescribe(Long paramLong) throws Exception, RemoteException;
  
  boolean sendedClearToDescribe(Long paramLong) throws Exception, RemoteException;
  
  boolean realClearDeletedBox(Long paramLong) throws Exception, RemoteException;
  
  boolean realClearDescribeBox(Long paramLong) throws Exception, RemoteException;
  
  boolean delRealSearchMessage(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean delSearchMessage(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean isSendMsg(Long paramLong, String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean isSendOutMsg(Long paramLong, String paramString) throws Exception, RemoteException;
  
  boolean modelSendMsg(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  boolean modSysFileMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List selectGroupUser(String paramString) throws Exception, RemoteException;
  
  List selectPersonUser(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Map getSubOrgAndUsers(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getSubOrgs(Long paramLong) throws Exception, RemoteException;
  
  List getUserList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getMsAccountInfo(String paramString) throws Exception, RemoteException;
  
  boolean modelSendMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getSenderBySerial(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getSerialByUserId(String paramString) throws Exception, RemoteException;
  
  String checkSendMsCount(String paramString1, String paramString2) throws Exception, RemoteException;
  
  MsInfoListPO updateViewMsg(String paramString) throws Exception, RemoteException;
  
  List getNewMsglist(String paramString) throws Exception, RemoteException;
  
  List getFlowAndList(Long paramLong) throws Exception, RemoteException;
}
