package com.js.oa.message.bean;

import com.js.oa.message.po.MsDescribePO;
import com.js.oa.message.po.MsInfoListPO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface MessageEJBLocal extends EJBLocalObject {
  HashMap listView(Long paramLong) throws Exception;
  
  boolean addDescribe(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6) throws Exception;
  
  boolean addMsgFlow(String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, String paramString5, String paramString6) throws Exception;
  
  MsDescribePO listDescribe(String paramString) throws Exception;
  
  String selectManSign(Long paramLong, String paramString) throws Exception;
  
  boolean delDescribeBatch(String paramString) throws Exception;
  
  boolean deletedboxBatch(String paramString) throws Exception;
  
  boolean delRealReceiveBoxBatch(String paramString) throws Exception;
  
  boolean receiveToDescribe(String paramString) throws Exception;
  
  boolean delRealSendedBoxBatch(String paramString) throws Exception;
  
  boolean sendedToDescribe(String paramString) throws Exception;
  
  boolean receivedClearToDescribe(Long paramLong) throws Exception;
  
  boolean sendedClearToDescribe(Long paramLong) throws Exception;
  
  boolean realClearDeletedBox(Long paramLong) throws Exception;
  
  boolean realClearDescribeBox(Long paramLong) throws Exception;
  
  boolean delRealSearchMessage(String paramString1, String paramString2) throws Exception;
  
  boolean delSearchMessage(String paramString1, String paramString2) throws Exception;
  
  boolean isSendMsg(Long paramLong, String paramString1, String paramString2) throws Exception;
  
  boolean isSendOutMsg(Long paramLong, String paramString) throws Exception;
  
  boolean modelSendMsg(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  boolean modSysFileMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List selectGroupUser(String paramString) throws Exception;
  
  List selectPersonUser(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Map getSubOrgAndUsers(String paramString1, String paramString2) throws Exception;
  
  List getSubOrgs(Long paramLong) throws Exception;
  
  List getUserList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getMsAccountInfo(String paramString) throws Exception;
  
  boolean modelSendMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getSenderBySerial(String paramString1, String paramString2) throws Exception;
  
  String getSerialByUserId(String paramString) throws Exception;
  
  String checkSendMsCount(String paramString1, String paramString2) throws Exception;
  
  MsInfoListPO updateViewMsg(String paramString) throws Exception;
  
  List getNewMsglist(String paramString) throws Exception;
  
  List getFlowAndList(Long paramLong) throws Exception;
}
