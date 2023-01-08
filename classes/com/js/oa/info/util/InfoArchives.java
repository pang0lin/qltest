package com.js.oa.info.util;

import cn.zzy.service.MessageService;
import com.js.oa.archives.util.ArchivesUtil;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class InfoArchives extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    List<Object[]> list = channelBD.getCanIssue(where, domainId);
    List<Object[]> returnList = new ArrayList();
    if (list != null)
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        if (!"1".equals(obj[3]))
          returnList.add(obj); 
      }  
    return returnList;
  }
  
  public void saveInfo(List<E> fileList, String curUserId, String flag) {
    try {
      begin();
      InformationChannelPO channelPO = null;
      String toUserIds = "";
      Date now = new Date();
      String channelId = fileList.get(1).toString();
      String userName = fileList.get(2).toString();
      String userId = fileList.get(3).toString();
      String orgId = fileList.get(4).toString();
      String orgName = fileList.get(5).toString();
      String orgIdString = fileList.get(6).toString();
      String fileName = fileList.get(0).toString();
      if (!",GWGL-FWGL,GWGL-DOC,GWGL-SWGL,".contains("," + flag + ","))
        fileName = String.valueOf(userName) + "的" + fileName; 
      if ("GWGL-DOC".equals(flag))
        fileName = String.valueOf(fileName) + "(发文正文)"; 
      String id = String.valueOf(fileList.get(7).toString()) + "_" + flag;
      if (channelPO == null) {
        channelPO = (InformationChannelPO)this.session.load(InformationChannelPO.class, Long.valueOf(channelId));
        toUserIds = getChannelCanReadUserIds(userId, 
            channelPO.getChannelReader(), channelPO.getChannelReaderOrg(), 
            channelPO.getChannelReaderGroup());
      } 
      String[] arrayStrings = toUserIds.split(",");
      String userIds = ",";
      for (int j = 0; j < arrayStrings.length; j++) {
        if (!userIds.contains("," + arrayStrings[j] + ","))
          userIds = String.valueOf(userIds) + arrayStrings[j] + ","; 
      } 
      if (userIds.length() > 0)
        userIds = userIds.substring(1, userIds.length() - 1); 
      InformationPO po = new InformationPO();
      po.setInformationChannel(channelPO);
      po.setInformationTitle(fileName);
      po.setInformationSubTitle("");
      po.setInformationKey("");
      po.setInformationHead(0);
      po.setInformationType("6");
      po.setInformationSummary("");
      po.setInformationContent("");
      po.setInformationHeadFile(id);
      po.setInformationStatus(0);
      po.setInformationAuthor("");
      po.setInformationIssuerId(Long.valueOf(Long.parseLong(userId)));
      po.setInformationIssuer(userName);
      po.setInformationIssueTime(now);
      po.setInformationModifyTime(now);
      po.setInformationIssueOrg(orgName);
      po.setInformationIssueOrgId(orgId);
      po.setIssueOrgIdString(orgIdString);
      po.setInformationReaderName(channelPO.getChannelReaderName());
      po.setInformationReader(channelPO.getChannelReader());
      po.setInformationReaderOrg(channelPO.getChannelReaderOrg());
      po.setInformationReaderGroup(channelPO.getChannelReaderGroup());
      po.setDomainId(Long.valueOf("0"));
      po.setInformationVersion("1.0");
      po.setOrderCode("1000");
      po.setDisplayTitle(1);
      po.setOtherChannel(",0,");
      po.setTitleColor(Integer.valueOf(0));
      po.setModifyEmp("");
      po.setDossierStatus(Integer.valueOf(0));
      po.setMustRead(Integer.valueOf(0));
      po.setComeFrom("");
      po.setIsConf(Integer.valueOf(0));
      po.setDocumentNo("0");
      po.setDocumentEditor("");
      po.setDocumentType("0");
      po.setDisplayImage("1");
      po.setWordDisplayType("");
      po.setInformationOrISODoc("0");
      po.setIsoDocStatus("0");
      po.setIsoOldInfoId("");
      po.setIsoSecretStatus("0");
      po.setIsoDealCategory("");
      po.setIsoApplyName("");
      po.setIsoApplyId("");
      po.setIsoReceiveId("");
      po.setIsoReceiveName("");
      po.setIsoModifyReason("");
      po.setIsoAmendmentPage("");
      po.setIsoModifyVersion("");
      po.setInforModifyMen("");
      po.setInforModifyOrg("");
      po.setIsAllow("0");
      po.setOrderCodeTemp(Integer.valueOf(0));
      Long infoId = (Long)this.session.save(po);
      String url = "";
      url = ArchivesUtil.clickImg(flag, fileList.get(7).toString(), "2", (String)infoId);
      String title = String.valueOf(userName) + "发布了:" + fileName;
      RemindUtil.sendMessageToUsersWithType(title, url, userIds, "Info", 
          new Date(), new Date("2050/1/1"), 1);
      this.session.flush();
      this.session.close();
      if ("tjqnzyxy".equals(SystemCommon.getCustomerName()) && 
        ",227,228,230,231,".indexOf("," + channelId + ",") > -1)
        (new MessageService()).sendMessages(channelId, "Info", po.getInformationHeadFile(), infoId.longValue(), po.getInformationIssuer(), 
            po.getInformationIssueOrg(), "", "", "", po.getInformationTitle(), po.getInformationContent()); 
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
  }
  
  private String getChannelCanReadUserIds(String userId, String reader, String readOrg, String readGroup) {
    try {
      int i = 0;
      if (("".equals(reader) || reader == null) && ("".equals(readOrg) || readOrg == null) && ("".equals(readGroup) || readGroup == null)) {
        reader = "-1";
      } else {
        StringBuffer readerBuffer = new StringBuffer();
        if (!"".equals(reader) && reader != null) {
          reader = reader.substring(1, reader.length() - 1);
          reader = reader.replaceAll("\\$\\$", ",");
          String[] readerArr = reader.split(",");
          for (i = 0; i < readerArr.length; i++) {
            if (!userId.equals(readerArr[i]))
              readerBuffer.append(reader).append(","); 
          } 
        } 
        if (!"".equals(readOrg) && readOrg != null) {
          String[] orgIdArray = readOrg.substring(1, readOrg.length() - 1).split("\\*\\*");
          StringBuffer where = new StringBuffer();
          for (i = 0; i < orgIdArray.length; i++) {
            if (where.length() > 0) {
              where.append(" or orgIdString like '%$").append(orgIdArray[i]).append("$%'");
            } else {
              where.append(" where orgIdString like '%$").append(orgIdArray[i]).append("$%'");
            } 
          } 
          List<E> list = this.session.createQuery("select emp.empId from com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org where org.orgId in (select po.orgId from com.js.system.vo.organizationmanager.OrganizationVO po " + where.toString() + ")").list();
          if (list != null && list.size() > 0)
            for (i = 0; i < list.size(); i++) {
              String empId = list.get(i).toString();
              if (!userId.equals(empId))
                readerBuffer.append(empId).append(","); 
            }  
        } 
        if (!"".equals(readGroup) && readGroup != null) {
          List<E> list;
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like concat('%@',gr.groupId,'@%')").list();
          } else {
            list = this.session.createQuery("select emp.empId from com.js.system.vo.groupmanager.GroupVO gr join gr.employees emp where '" + readGroup + "' like '%@'||gr.groupId||'@%'").list();
          } 
          if (list != null && list.size() > 0)
            for (i = 0; i < list.size(); i++) {
              String empId = list.get(i).toString();
              if (!userId.equals(empId))
                readerBuffer.append(empId).append(","); 
            }  
        } 
        reader = readerBuffer.toString();
        if (reader.length() > 0)
          reader = reader.substring(0, reader.length() - 1); 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return reader;
  }
}
