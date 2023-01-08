package com.js.oa.info.channelmanager.bean;

import com.js.oa.info.channelmanager.po.DepartmentStylePO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.po.UserChannelPO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface ChannelEJBLocal extends EJBLocalObject {
  List getAllChannel(String paramString1, String paramString2) throws Exception;
  
  Long add(InformationChannelPO paramInformationChannelPO, String paramString1, String paramString2) throws Exception;
  
  List getSingleChannel(String paramString) throws Exception;
  
  void update(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, Date paramDate) throws Exception;
  
  void modify(InformationChannelPO paramInformationChannelPO, String paramString1, String paramString2) throws Exception;
  
  List getBrotherCh(String paramString1, String paramString2) throws Exception;
  
  Object[] getAccessory(String paramString) throws Exception;
  
  List getPublicChannel(String paramString) throws Exception;
  
  InformationChannelPO loadChannel(String paramString) throws Exception;
  
  List getUserViewCh(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getCanIssue(String paramString) throws Exception;
  
  Boolean canIssue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean canVindicate(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getOrgChannel(String paramString) throws Exception;
  
  void addUserChannel(UserChannelPO paramUserChannelPO) throws Exception;
  
  Boolean deleteUserChannel(String paramString) throws Exception;
  
  String getUserChannelName(String paramString) throws Exception;
  
  void updateUserChannel(String paramString1, String paramString2) throws Exception;
  
  List getUserChannel(String paramString1, String paramString2) throws Exception;
  
  List getChannelMenu(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String[] getSortChannel(String paramString1, String paramString2) throws Exception;
  
  void modifyByArray(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception;
  
  Boolean canOnDesktop() throws Exception;
  
  List getChannelPosition() throws Exception;
  
  Boolean departPageRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  DepartmentStylePO getDepaStyle(String paramString) throws Exception;
  
  Boolean canOnDepaDesk(String paramString) throws Exception;
  
  List getOtherPositionCh(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserAllViewCh(String paramString1, String paramString2) throws Exception;
  
  Long getChannelProcessId(String paramString) throws Exception;
  
  List getAllCanIssueWithoutCheck(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Boolean canVindicate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getCanIssue(String paramString1, String paramString2) throws Exception;
  
  List getAllCanIssueWithoutCheck(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserViewCh(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  void addUserChannel(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void updateUserChannel(UserChannelPO paramUserChannelPO) throws Exception;
  
  UserChannelPO getUserChannel(String paramString) throws Exception;
  
  List getSingleChannelName(String paramString) throws Exception;
  
  String hasChannelViewRight(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  List getAfficheCanIssue(String paramString1, String paramString2) throws Exception;
  
  List getAfficheCanIssue(String paramString) throws Exception;
  
  Boolean isChannelManager(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getUserChannel(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getInformationProcessId(String paramString) throws Exception;
  
  List getUserManageList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  List getChannelMenu_ByType(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getAllChannel_ByType(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getISoCanIssue(String paramString) throws Exception;
  
  List getIsoCanIssue(String paramString1, String paramString2) throws Exception;
  
  List getAllIsoChannel(String paramString) throws Exception;
  
  List getBrotherCh_ByChannelStatusType(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Object[] deleteInformation(String paramString1, String paramString2) throws Exception;
  
  String getChannelProcessInfo(String paramString) throws Exception;
  
  String channelNeedFlow(String paramString) throws Exception;
}
