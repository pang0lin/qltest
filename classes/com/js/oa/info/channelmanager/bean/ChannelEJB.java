package com.js.oa.info.channelmanager.bean;

import com.js.oa.info.channelmanager.po.DepartmentStylePO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.po.UserChannelPO;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBObject;

public interface ChannelEJB extends EJBObject {
  List getAllChannel(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long add(InformationChannelPO paramInformationChannelPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getSingleChannel(String paramString) throws Exception, RemoteException;
  
  void update(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, Date paramDate) throws Exception, RemoteException;
  
  void modify(InformationChannelPO paramInformationChannelPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getBrotherCh(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Object[] getAccessory(String paramString) throws Exception, RemoteException;
  
  List getPublicChannel(String paramString) throws Exception, RemoteException;
  
  InformationChannelPO loadChannel(String paramString) throws Exception, RemoteException;
  
  List getUserViewCh(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getCanIssue(String paramString) throws Exception, RemoteException;
  
  Boolean canIssue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean canVindicate(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getOrgChannel(String paramString) throws Exception, RemoteException;
  
  void addUserChannel(UserChannelPO paramUserChannelPO) throws Exception, RemoteException;
  
  Boolean deleteUserChannel(String paramString) throws Exception, RemoteException;
  
  String getUserChannelName(String paramString) throws Exception, RemoteException;
  
  void updateUserChannel(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getUserChannel(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getChannelMenu(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String[] getSortChannel(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void modifyByArray(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean canOnDesktop() throws Exception, RemoteException;
  
  List getChannelPosition() throws Exception, RemoteException;
  
  Boolean departPageRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  DepartmentStylePO getDepaStyle(String paramString) throws Exception, RemoteException;
  
  Boolean canOnDepaDesk(String paramString) throws Exception, RemoteException;
  
  List getOtherPositionCh(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserAllViewCh(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long getChannelProcessId(String paramString) throws Exception, RemoteException;
  
  List getAllCanIssueWithoutCheck(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Boolean canVindicate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getCanIssue(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllCanIssueWithoutCheck(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserViewCh(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  void addUserChannel(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void updateUserChannel(UserChannelPO paramUserChannelPO) throws Exception, RemoteException;
  
  UserChannelPO getUserChannel(String paramString) throws Exception, RemoteException;
  
  List getSingleChannelName(String paramString) throws Exception, RemoteException;
  
  String hasChannelViewRight(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  List getAfficheCanIssue(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAfficheCanIssue(String paramString) throws Exception, RemoteException;
  
  Boolean isChannelManager(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getUserChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getInformationProcessId(String paramString) throws Exception;
  
  List getUserManageList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  List getChannelMenu_ByType(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getAllChannel_ByType(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getISoCanIssue(String paramString) throws Exception, RemoteException;
  
  List getIsoCanIssue(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllIsoChannel(String paramString) throws Exception, RemoteException;
  
  List getBrotherCh_ByChannelStatusType(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Object[] deleteInformation(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getChannelProcessInfo(String paramString) throws Exception, RemoteException;
  
  String channelNeedFlow(String paramString) throws Exception;
}
