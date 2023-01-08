package com.js.oa.info.channelmanager.bean;

import com.js.oa.info.channelmanager.po.DepartmentStylePO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.po.UserChannelPO;
import com.js.oa.info.infomanager.po.AssociateInfoPO;
import com.js.oa.info.infomanager.po.InforHistoryAccessoryPO;
import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import com.js.oa.info.infomanager.po.InformationHistoryPO;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.util.ChannelCache;
import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.po.WorkFlowChannelPO;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.InfoUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

public class ChannelEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getAllChannel(String channelType, String domainId, String corpId) throws Exception {
    List list = null;
    begin();
    try {
      String sql = " select aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,  aaa.channelParentId,aaa.channelSort,aaa.channelIdString,aaa.isAllowReview ,aaa.includeChild from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 

        
        channelType;
      if (SystemCommon.getMultiDepart() == 1) {
        if (!"".equals(corpId) && !"0".equals(corpId))
          sql = String.valueOf(sql) + " and aaa.corpId=" + corpId; 
        sql = String.valueOf(sql) + " and (aaa.channelIdString not like '%$100$%' and aaa.channelIdString not like '%$101$%') ";
      } else if ("1".equals(channelType)) {
        sql = String.valueOf(sql) + " and (aaa.channelIdString like '%$100$%' or aaa.channelIdString like '%$101$%')";
      } 
      sql = String.valueOf(sql) + " and aaa.domainId=" + domainId + " and ( aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0' ) " + 
        " order by aaa.channelIdString ";
      Query query = this.session.createQuery(sql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Long add(InformationChannelPO informationChannelPO, String channelOrderId, String radiobutton) throws Exception {
    begin();
    Long result = Long.valueOf("0");
    Long channelId = null;
    try {
      Query query = null;
      List<Object[]> list = null;
      int sort = 0;
      if (channelOrderId.equals("-1")) {
        sort = 500000;
      } else {
        query = this.session.createQuery("select aaa.channelSort,aaa.channelParentId,aaa.channelType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
            channelOrderId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String orderChannelSort = tmpObj[0].toString();
        String orderChannelParentId = tmpObj[1].toString();
        String orderChannelType = tmpObj[2].toString();
        if (radiobutton.equals("up")) {
          query = this.session.createQuery("select max(aaa.channelSort) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort < " + 
              orderChannelSort);
          list = query.list();
          String maxChannelSort = "";
          if (list.get(0) != null && list.get(0) != "") {
            maxChannelSort = list.get(0).toString();
          } else {
            maxChannelSort = "0";
          } 
          if (maxChannelSort.equals("0")) {
            sort = Integer.parseInt(orderChannelSort) - 10000;
            if (sort < 0) {
              result = Long.valueOf("-1");
              return result;
            } 
          } else {
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(maxChannelSort)) / 2;
          } 
        } else {
          query = this.session.createQuery("select min(aaa.channelSort) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort > " + 
              orderChannelSort);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            sort = Integer.parseInt(orderChannelSort) + 10000;
            if (sort > 1000000) {
              result = Long.valueOf("-1");
              return result;
            } 
          } else {
            String minChannelSort = list.get(0).toString();
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(minChannelSort)) / 2;
          } 
        } 
      } 
      channelId = (Long)this.session.save(informationChannelPO);
      int modelId = 4;
      if (informationChannelPO.getAfficheChannelStatus().equals("1"))
        modelId = 51; 
      if (informationChannelPO.getAfficheChannelStatus().equals("2"))
        modelId = 50; 
      if (informationChannelPO.getChannelNeedCheckup() == 1) {
        String domainId = informationChannelPO.getDomainId().toString();
        query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId=" + modelId + " and aaa.domainId=" + domainId);
        Iterator iter = query.iterate();
        WFPackagePO packagePO = new WFPackagePO();
        if (iter.hasNext())
          packagePO = query.iterate().next(); 
        query = this.session.createQuery("select aaa.immoFormId from com.js.oa.jsflow.po.WorkFlowImmoFormPO aaa where aaa.moduleId=" + modelId);
        iter = query.iterate();
        Long formId = new Long(0L);
        if (iter.hasNext())
          formId = query.iterate().next(); 
        WFWorkFlowProcessPO processPO = new WFWorkFlowProcessPO();
        processPO.setAccessDatabaseId(formId);
        processPO.setWorkFlowProcessName(
            String.valueOf(informationChannelPO.getChannelName()) + "发布流程");
        processPO.setProcessCreatedDate(new Date());
        processPO.setProcessDescription(
            String.valueOf(informationChannelPO.getChannelName()) + "发布流程");
        processPO.setProcessType(1);
        processPO.setFormClassName("InformationWorkFlow");
        processPO.setFormClassMethod("save");
        processPO.setFormClassCompMethod("complete");
        processPO.setWfPackage(packagePO);
        processPO.setDomainId(domainId);
        Long processId = (Long)this.session.save(processPO);
        WFActivityPO startActivityPO = new WFActivityPO();
        startActivityPO.setActivityBeginEnd(1);
        startActivityPO.setWfWorkFlowProcess(processPO);
        startActivityPO.setDomainId(domainId);
        this.session.save(startActivityPO);
        WFActivityPO endActivityPO = new WFActivityPO();
        endActivityPO.setActivityBeginEnd(2);
        endActivityPO.setWfWorkFlowProcess(processPO);
        endActivityPO.setDomainId(domainId);
        this.session.save(endActivityPO);
        WorkFlowChannelPO wfcPO = new WorkFlowChannelPO();
        wfcPO.setProcessId(processId);
        wfcPO.setChannel(informationChannelPO);
        this.session.save(wfcPO);
      } 
      String channelLevel = "0";
      String channelIdString = "";
      Long long_ = informationChannelPO.getChannelParentId();
      InformationChannelPO parentPO = null;
      if (!long_.equals("0")) {
        query = this.session.createQuery("select aaa.channelLevel,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
            long_);
        list = query.list();
        Object[] obj = list.get(0);
        channelLevel = obj[0].toString();
        channelIdString = obj[1].toString();
      } 
      channelIdString = String.valueOf(channelIdString) + sort + "$" + channelId + "$_";
      informationChannelPO.setChannelSort(sort);
      informationChannelPO.setChannelLevel(Integer.parseInt(channelLevel) + 
          1);
      informationChannelPO.setChannelIdString(channelIdString);
    } catch (Exception e) {
      result = Long.valueOf("0");
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    result = channelId;
    return result;
  }
  
  public List getSingleChannel(String channelId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.channelName, aaa.channelType, aaa.channelParentId, aaa.channelNeedCheckup, aaa.channelReader, aaa.channelReaderOrg, aaa.channelReaderGroup, aaa.channelIssuer, aaa.channelIssuerOrg, aaa.channelIssuerGroup, aaa.channelIssuerName, aaa.channelReaderName, aaa.channelSort, aaa.channelIdString, aaa.channelShowType, aaa.onDesktop,aaa.isRollOnDesktop,aaa.channelPosition, aaa.positionUpDown, aaa.onDepaDesk,aaa.infoNum, aaa.desktopType,aaa.includeChild,aaa.channelManager,aaa.channelManagerOrg,aaa.channelManagerGroup,aaa.channelManagerName ,aaa.afficheChannelStatus,aaa.isAllowReview,aaa.relProjectId from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 







          
          channelId);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void modify(InformationChannelPO informationChannelPO, String channelOrderId, String radiobutton) throws Exception {
    begin();
    try {
      Query query = null;
      List<Object[]> list = null;
      int sort = 0;
      if (channelOrderId.equals("-1")) {
        sort = 500000;
      } else {
        query = this.session.createQuery("select aaa.channelSort,aaa.channelParentId,aaa.channelType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
            channelOrderId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String orderChannelSort = tmpObj[0].toString();
        String orderChannelParentId = tmpObj[1].toString();
        String orderChannelType = tmpObj[2].toString();
        if (radiobutton.equals("up")) {
          query = this.session.createQuery("select max(aaa.channelSort) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort < " + 
              orderChannelSort);
          list = query.list();
          String maxChannelSort = list.get(0).toString();
          if (maxChannelSort.equals("0")) {
            sort = Integer.parseInt(orderChannelSort) - 10000;
          } else {
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(maxChannelSort)) / 2;
          } 
        } else {
          query = this.session.createQuery("select min(aaa.channelSort) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort > " + 
              orderChannelSort);
          list = query.list();
          if (list != null && list.size() > 0 && list.get(0) != null) {
            String minChannelSort = list.get(0).toString();
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(minChannelSort)) / 2;
          } else {
            sort = Integer.parseInt(orderChannelSort) + 10000;
          } 
        } 
      } 
      Long modifyChId = informationChannelPO.getChannelId();
      query = this.session.createQuery("select aaa.channelIdString,aaa.channelParentId,aaa.channelSort,aaa.channelLevel from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
          modifyChId);
      list = query.list();
      Object[] obj = list.get(0);
      String beforeModiChIdStr = obj[0].toString();
      String beforeModiParentId = obj[1].toString();
      String beforeModiChSort = obj[2].toString();
      String beforeModiChLevel = obj[3].toString();
      String modifyedChPaId = informationChannelPO.getChannelParentId()
        .toString();
      String modifyedChIdStr = "";
      int modifyedChLevel = 0;
      if (beforeModiParentId.equals(modifyedChPaId)) {
        int i = beforeModiChIdStr.indexOf(String.valueOf(beforeModiChSort) + "$" + 
            modifyChId + "$_");
        modifyedChIdStr = String.valueOf(beforeModiChIdStr.substring(0, i)) + sort + 
          "$" + modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId <>" + 
            modifyChId + 
            " and aaa.channelIdString like '%$" + 
            modifyChId + "$%' ");
        list = query.list();
        InformationChannelPO tmpPO = null;
        String tmpIdString = "";
        for (int j = 0; j < list.size(); j++) {
          tmpPO = (InformationChannelPO)list.get(j);
          tmpIdString = tmpPO.getChannelIdString();
          tmpPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr.length(), 
                tmpIdString.length()));
        } 
        informationChannelPO.setChannelSort(sort);
        informationChannelPO.setChannelIdString(modifyedChIdStr);
        informationChannelPO.setChannelLevel(Integer.parseInt(
              beforeModiChLevel));
        this.session.update(informationChannelPO);
      } else if (modifyedChPaId.equals("0")) {
        modifyedChLevel = 1;
        modifyedChIdStr = String.valueOf(sort) + "$" + modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelIdString like  '%" + 
            beforeModiChIdStr + 
            "%' and aaa.channelId <> " + 
            modifyChId);
        list = query.list();
        InformationChannelPO tmpPO = null;
        String tmpIdString = "";
        int tmpLevel = 0;
        for (int i = 0; i < list.size(); i++) {
          tmpPO = (InformationChannelPO)list.get(i);
          tmpIdString = tmpPO.getChannelIdString();
          tmpLevel = tmpPO.getChannelLevel();
          tmpPO.setChannelLevel(tmpLevel - 1);
          tmpPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr
                .length()));
        } 
        informationChannelPO.setChannelSort(sort);
        informationChannelPO.setChannelIdString(modifyedChIdStr);
        informationChannelPO.setChannelLevel(1);
        this.session.update(informationChannelPO);
      } else {
        query = this.session.createQuery("select aaa.channelIdString,aaa.channelLevel from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
            modifyedChPaId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String modifyedPaChIdString = tmpObj[0].toString();
        String modifyedPaChLevel = tmpObj[1].toString();
        modifyedChIdStr = String.valueOf(modifyedPaChIdString) + sort + "$" + 
          modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelIdString like  '%" + 
            beforeModiChIdStr + 
            "%' and aaa.channelId <> " + 
            modifyChId);
        list = query.list();
        InformationChannelPO tmpPO = null;
        String tmpIdString = "";
        int tmpLevel = 0;
        for (int i = 0; i < list.size(); i++) {
          tmpPO = (InformationChannelPO)list.get(i);
          tmpIdString = tmpPO.getChannelIdString();
          tmpLevel = tmpPO.getChannelLevel();
          tmpPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr
                .length()));
          if (Integer.parseInt(modifyedPaChLevel) + 1 > 
            Integer.parseInt(beforeModiChLevel)) {
            tmpPO.setChannelLevel(tmpLevel + 1);
          } else if (Integer.parseInt(modifyedPaChLevel) + 1 < 
            Integer.parseInt(beforeModiChLevel)) {
            tmpPO.setChannelLevel(tmpLevel - 1);
          } else {
            tmpPO.setChannelLevel(tmpLevel);
          } 
        } 
        informationChannelPO.setChannelSort(sort);
        informationChannelPO.setChannelIdString(modifyedChIdStr);
        informationChannelPO.setChannelLevel(Integer.parseInt(modifyedPaChLevel) + 1);
        this.session.update(informationChannelPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void update(String informationId, String[] para, String[] assoInfo, String[] infoPicName, String[] infoPicSaveName, String[] infoAppendName, String[] infoAppendSaveName, Date historydate) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(
          InformationPO.class, new Long(informationId));
      String isISO = informationPO.getInformationOrISODoc();
      if (isISO != null && isISO.equals("1") && para.length > 56 && para[56].toString().equals("1")) {
        String version = informationPO.getInformationVersion();
        String middle = "0";
        int lastInt = 1;
        int a = Integer.parseInt(version.substring(0, version.indexOf(".")));
        String b = version.substring(version.indexOf(".") + 1, 
            version.length());
        lastInt = Integer.parseInt(b);
        lastInt++;
        version = a + "." + lastInt;
        informationPO.setInformationVersion(version);
      } 
      informationPO.setInformationIssueTime(informationPO
          .getInformationIssueTime());
      informationPO.setInformationTitle(para[0]);
      informationPO.setInformationSubTitle(para[1]);
      informationPO.setInformationSummary(para[2]);
      informationPO.setInformationKey(para[3]);
      informationPO.setInformationModifyTime(new Date());
      informationPO.setInformationReaderName(para[8]);
      informationPO.setInformationReader(para[9]);
      informationPO.setInformationReaderOrg(para[10]);
      informationPO.setInformationReaderGroup(para[11]);
      informationPO.setInformationValidType(Integer.parseInt(para[12]));
      informationPO.setValidBeginTime(new Date(para[13]));
      informationPO.setValidEndTime(new Date(para[14]));
      if (para[15] != null && !para[15].equals(""))
        informationPO.setInformationHead(Integer.parseInt(para[15])); 
      informationPO.setInformationHeadFile(para[16]);
      informationPO.setInformationSeal(para[17]);
      informationPO.setInformationMark(para[18]);
      informationPO.setInfoRedIssueOrg(para[19]);
      informationPO.setInfoRedIssueTime(para[20]);
      informationPO.setInformationHeadId(new Long(para[21]));
      informationPO.setInformationSealId(new Long(para[22]));
      informationPO.setTransmitToWebsite(Integer.parseInt(para[23]));
      informationPO.setForbidCopy(Integer.parseInt(para[24]));
      informationPO.setInformationAuthor(para[28]);
      informationPO.setTitleColor(new Integer(para[30]));
      informationPO.setReprocess(para[58]);
      if (para[25].equals("")) {
        informationPO.setOrderCode("1000");
      } else {
        informationPO.setOrderCode(para[25]);
      } 
      informationPO.setDisplayTitle(Integer.parseInt(para[26]));
      if (para[27] != null && !"".equals(para[27]) && !"null".equals(para[27]))
        informationPO.setOtherChannel(para[27]); 
      informationPO.setInformationContent(para[4]);
      informationPO.setMustRead(Integer.valueOf(para[32]));
      informationPO.setComeFrom(para[33]);
      if (para.length > 34)
        if (para[34] != null) {
          informationPO.setIsConf(Integer.valueOf("1"));
        } else {
          informationPO.setIsConf(Integer.valueOf("0"));
        }  
      if (para.length > 35)
        if (para[35] != null) {
          informationPO.setDocumentNo(para[35]);
        } else {
          informationPO.setDocumentNo("0");
        }  
      if (para.length > 36)
        informationPO.setDocumentEditor(para[36]); 
      if (para.length > 37)
        informationPO.setDocumentType(para[37]); 
      if (para.length > 38 && "1".equals(para[38]))
        informationPO.setInformationStatus(1); 
      if (para.length > 39)
        informationPO.setAfficeHistoryDate(new Long(para[39])); 
      informationPO.setAfficheHiTime(historydate);
      if (para.length > 40) {
        InformationChannelPO channelPO = (InformationChannelPO)this.session.load(InformationChannelPO.class, Long.valueOf(para[40]));
        informationPO.setInformationChannel(channelPO);
      } 
      if (para.length > 41)
        informationPO.setDisplayImage(para[41]); 
      if (para.length > 42)
        informationPO.setInformationOrISODoc(para[42]); 
      if (para.length > 43)
        informationPO.setIsoDocStatus(para[43]); 
      if (para.length > 44)
        informationPO.setIsoOldInfoId(para[44]); 
      if (para.length > 45)
        informationPO.setIsoSecretStatus(para[45]); 
      if (para.length > 46)
        informationPO.setIsoDealCategory(para[46]); 
      if (para.length > 47)
        informationPO.setIsoApplyName(para[47]); 
      if (para.length > 48)
        informationPO.setIsoApplyId(para[48]); 
      if (para.length > 49)
        informationPO.setIsoReceiveName(para[49]); 
      if (para.length > 50)
        informationPO.setIsoReceiveId(para[50]); 
      if (para.length > 51)
        informationPO.setIsoModifyReason(para[51]); 
      if (para.length > 52)
        if (para[52] != null && !para[52].toString().equals("")) {
          informationPO.setIsoAmendmentPage(para[52]);
        } else {
          informationPO.setIsoAmendmentPage("");
        }  
      if (para.length > 53)
        informationPO.setIsoModifyVersion(para[53]); 
      if (para.length > 55 && !para[55].toString().equals("") && !para[54].toString().equals(""))
        if (isISO != null && isISO.equals("1") && para[45].toString().equals("0")) {
          informationPO.setModifyEmp("");
          informationPO.setInforModifyMen("");
          informationPO.setInforModifyOrg("");
        } else {
          informationPO.setModifyEmp(String.valueOf(para[55]) + "." + para[54]);
          informationPO.setInforModifyOrg(para[55]);
          informationPO.setInforModifyMen(para[54]);
        }  
      if (para.length > 57)
        informationPO.setIsAllow(para[57]); 
      Set accessory = informationPO.getInformationAccessory();
      informationPO.setInformationAccessory(null);
      Iterator<InformationAccessoryPO> iter = accessory.iterator();
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      HashSet<InformationAccessoryPO> acceSet = new HashSet();
      if (infoAppendName != null)
        for (int i = 0; i < infoAppendName.length; i++) {
          InformationAccessoryPO accePO = new InformationAccessoryPO();
          accePO.setAccessoryIsImage(0);
          accePO.setAccessoryName(infoAppendName[i]);
          accePO.setAccessorySaveName(infoAppendSaveName[i]);
          if (infoAppendSaveName[i].indexOf(".") > 0) {
            accePO.setAccessoryType(infoAppendSaveName[i].substring(
                  infoAppendSaveName[i].indexOf(".") + 1));
          } else {
            accePO.setAccessoryType("");
          } 
          accePO.setInformation(informationPO);
          acceSet.add(accePO);
          this.session.save(accePO);
        }  
      if (infoPicName != null)
        for (int i = 0; i < infoPicName.length; i++) {
          InformationAccessoryPO accePO = new InformationAccessoryPO();
          accePO.setAccessoryIsImage(1);
          accePO.setAccessoryName(infoPicName[i]);
          accePO.setAccessorySaveName(infoPicSaveName[i]);
          if (infoPicSaveName[i].indexOf(".") > 0) {
            accePO.setAccessoryType(infoPicSaveName[i].substring(
                  infoPicSaveName[i].indexOf(".") + 1));
          } else {
            accePO.setAccessoryType("");
          } 
          accePO.setInformation(informationPO);
          acceSet.add(accePO);
          this.session.save(accePO);
        }  
      informationPO.setInformationAccessory(acceSet);
      this.session.update(informationPO);
      this.session.flush();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        InfoUtil.insertInfoContent_oracle(new Long(informationId), para[4]); 
      this.session.delete(
          "from com.js.oa.info.infomanager.po.AssociateInfoPO aaa  where aaa.masterInfo = " + 
          informationId);
      if (assoInfo != null)
        for (int j = 0; j < assoInfo.length; j++) {
          AssociateInfoPO assoPO = new AssociateInfoPO();
          assoPO.setAssociateInfo(new Long(assoInfo[j]));
          assoPO.setMasterInfo(new Long(informationId));
          this.session.save(assoPO);
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getBrotherCh(String channelId, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.channelParentId,aaa.channelType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
          channelId);
      List<Object[]> tmpList = query.list();
      Object[] obj = tmpList.get(0);
      String channelParentId = obj[0].toString();
      String channelType = obj[1].toString();
      query = this.session.createQuery("select aaa.channelId,aaa.channelName,aaa.channelSort from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
          channelType + 
          " and aaa.channelParentId = " + 
          channelParentId + 
          " and aaa.domainId=" + domainId + " and ( aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0' ) " + 
          " order by aaa.channelSort  ");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Object[] getAccessory(String channelId) throws Exception {
    ArrayList<String> arrayList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select aaa from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelIdString like '%$" + 
          
          channelId + "$%'");
      List<InformationChannelPO> channelList = query.list();
      InformationAccessoryPO informationAccessoryPO = 
        new InformationAccessoryPO();
      InforHistoryAccessoryPO inforHistoryAccessoryPO = 
        new InforHistoryAccessoryPO();
      for (int i = 0; i < channelList.size(); i++) {
        InformationChannelPO channelPO = 
          channelList.get(i);
        Set infoSet = channelPO.getInformation();
        Iterator<InformationPO> iterator = infoSet.iterator();
        channelPO.setInformation(null);
        while (iterator.hasNext()) {
          InformationPO infoPO = iterator.next();
          Set infoAcceSet = infoPO.getInformationAccessory();
          Set infoHistSet = infoPO.getInformationHistory();
          infoPO.setInformationAccessory(null);
          infoPO.setInformationHistory(null);
          Iterator<InformationAccessoryPO> infoAcceIter = infoAcceSet.iterator();
          while (infoAcceIter.hasNext()) {
            informationAccessoryPO = 
              infoAcceIter.next();
            arrayList.add(informationAccessoryPO
                .getAccessorySaveName());
          } 
          Iterator<InformationHistoryPO> infoHistIter = infoHistSet.iterator();
          while (infoHistIter.hasNext()) {
            InformationHistoryPO infoHistPO = 
              infoHistIter.next();
            Set infoHistAcceSet = infoHistPO
              .getInforHistoryAccessory();
            infoHistPO.setInforHistoryAccessory(null);
            Iterator<InforHistoryAccessoryPO> infoHistAcceIter = infoHistAcceSet.iterator();
            while (infoHistAcceIter.hasNext()) {
              inforHistoryAccessoryPO = 
                infoHistAcceIter.next();
              arrayList.add(inforHistoryAccessoryPO
                  .getAccessorySaveName());
              this.session.delete(inforHistoryAccessoryPO);
            } 
          } 
        } 
      } 
      Long processId = Long.valueOf(-1L);
      query = this.session.createQuery("select aaa.processId from com.js.oa.jsflow.po.WorkFlowChannelPO aaa join aaa.channel bbb where bbb.channelId=" + 
          channelId);
      Iterator<Long> iter = query.iterate();
      if (iter != null && iter.hasNext())
        processId = iter.next(); 
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      try {
        stmt.executeUpdate("DELETE FROM JSF_WORKFLOWPROCESS WHERE wf_workflowprocess_id=" + processId);
        stmt.executeUpdate("DELETE FROM JSF_WORKFLOWCHANNEL WHERE processId=" + processId);
        ResultSet rs = null;
        String tableId = "";
        rs = stmt.executeQuery(
            "select wf_immoform_id from jsf_immobilityform where wf_module_id=4");
        if (rs.next())
          tableId = rs.getString(1); 
        rs.close();
        stmt.execute("delete from JSF_WORK where worktable_id=" + 
            tableId + " and workrecord_id in(select info.information_id from oa_information info,oa_informationchannel channel" + 
            " where info.channel_id=channel.channel_id and channel.channelneedcheckup=1 and" + 
            " CHANNELIDSTRING LIKE '%$" + channelId + "$%')");
        stmt.execute("delete from oa_information where channel_id in (select ch.channel_id from oa_informationchannel ch where ch.channelidstring like '%$" + 
            channelId + "$%')");
        stmt.execute(
            "DELETE from OA_INFORMATIONCHANNEL WHERE CHANNELIDSTRING LIKE '%$" + 
            channelId + "$%'");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return arrayList.toArray();
  }
  
  public List getPublicChannel(String channelType) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelParentId  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 
          
          channelType + 
          " order by aaa.channelIdString ");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public InformationChannelPO loadChannel(String channelId) throws Exception {
    InformationChannelPO informationChannelPO = null;
    begin();
    try {
      informationChannelPO = (InformationChannelPO)this.session.load(
          InformationChannelPO.class, new Long(channelId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return informationChannelPO;
  }
  
  public List getUserViewCh(String userId, String orgId, String channelType) throws Exception {
    List list = null;
    begin();
    try {
      String rightCode = "01*01*03";
      if (Integer.parseInt(channelType) < 500000000 && 
        Integer.parseInt(channelType) >= 1)
        rightCode = "部门主页"; 
      String hSql = 
        " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString,aaa.afficheChannelStatus   from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      String databaseType = 
        SystemCommon.getDatabaseType();
      String tmpSql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like concat('%$', '" + 
          orgId + "', '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
          orgId + "$%' ";
      } else {
        tmpSql = "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 
          rightCode + "'  and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals(""))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        if (channelType.equals("-1")) {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
            groupString + " aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')) ) and (aaa.channelType > 0)  and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by aaa.channelIdString ";
        } else {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
            groupString + 
            " aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')) ) and aaa.channelType = " + 
            channelType + " and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0')  order by aaa.channelIdString ";
        } 
      } else if (channelType.equals("-1")) {
        hSql = String.valueOf(hSql) + 
          " where aaa.channelType > 0 and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " where aaa.channelType = " + channelType + 
          "  and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List getCanIssue(String where) throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.isAllowReview from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where " + 
        
        where + " and ( aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0' ) " + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + 
            "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
            
            obj[2] + "' )>0  order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + 
            "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        list.set(i, obj);
        continue;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String channelCanIssue(String where, String channelId) throws Exception {
    String res = "0";
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        
        where + ") and aaa.channelId=" + channelId;
      Query query = this.session.createQuery(hSql);
      List list = query.list();
      if (list != null && list.size() > 0)
        res = "1"; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return res;
  }
  
  public String getIsAllowView(String type, String channelId) throws Exception {
    String res = "0";
    begin();
    try {
      String sql = "select po.isAllowReview from InformationChannelPO po where po.channelId=" + channelId + " and po.channelType=" + type;
      Query query = this.session.createQuery(sql);
      List<String> list = query.list();
      if (list != null && list.size() > 0)
        res = list.get(0); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return res;
  }
  
  public List getAfficheCanIssue(String where) throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where " + 
        
        where + " and aaa.afficheChannelStatus='1' " + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
              channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + 
            "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
            
            obj[2] + "' )>0  order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + 
            "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        list.set(i, obj);
        continue;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Boolean canIssue(String userId, String orgId, String channelType) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String hSql = "select count(aaa.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where ";
      Query query = this.session.createQuery(
          " select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
          userId);
      List<String> tmpList = query.list();
      String canIssueGroupStr = "";
      for (int i = 0; i < tmpList.size(); i++)
        canIssueGroupStr = String.valueOf(canIssueGroupStr) + 
          " aaa.channelIssuerGroup like '%@" + 
          tmpList.get(i) + "@%' or "; 
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ")) >0 ";
      } else {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      tmpList = query.list();
      String canIssueOrgStr = "";
      for (int j = 0; j < tmpList.size(); j++)
        canIssueOrgStr = String.valueOf(canIssueOrgStr) + 
          " aaa.channelIssuerOrg like '%*" + 
          tmpList.get(j) + "*%' or "; 
      query = this.session.createQuery(String.valueOf(hSql) + "(" + canIssueGroupStr + 
          canIssueOrgStr + 
          " aaa.channelIssuer like '%$" + userId + 
          "$%') and channelType = " + channelType);
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String canIssueChannel(String userId, String orgId, String channelId) throws Exception {
    String result = "-1";
    begin();
    try {
      String hSql = "select count(aaa.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where ";
      InformationChannelPO po = (InformationChannelPO)this.session.load(InformationChannelPO.class, new Long(channelId));
      String channelIssuer = po.getChannelIssuer();
      String channelIssuerOrg = po.getChannelIssuerOrg();
      String channelIssuerGroup = po.getChannelIssuerGroup();
      if ((channelIssuer != null && channelIssuer.indexOf("$" + userId + "$") >= 0) || (
        channelIssuerOrg != null && channelIssuerOrg.indexOf("*-1*") >= 0))
        result = String.valueOf(po.getChannelType()); 
      if ("-1".equals(result) && channelIssuerOrg != null && !"".equals(channelIssuerOrg)) {
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = 
            " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
            
            orgId + ") like concat('%$', aaa.orgId, '$%') ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = 
            " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
            
            orgId + ")) >0 ";
        } else {
          tmpSql = 
            " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
            
            orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
        } 
        Query query = this.session.createQuery(tmpSql);
        List<String> tmpList = query.list();
        for (int i = 0; i < tmpList.size(); i++) {
          if (channelIssuerOrg.indexOf("*" + tmpList.get(i) + "*") >= 0) {
            result = String.valueOf(po.getChannelType());
            break;
          } 
        } 
      } 
      if ("-1".equals(result) && channelIssuerGroup != null && !"".equals(channelIssuerGroup)) {
        Query query = null;
        String tmpSql = "";
        query = this.session.createQuery(
            " select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
            userId);
        List<String> tmpList = query.list();
        for (int i = 0; i < tmpList.size(); i++) {
          if (channelIssuerGroup.indexOf("@" + tmpList.get(i) + "@") >= 0) {
            result = String.valueOf(po.getChannelType());
            break;
          } 
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean canVindicate(String userId, String orgId, String channelId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String rightCode = "";
      Iterator<Object[]> iter = this.session.iterate("select a.channelType, a.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO a where a.channelId = " + 
          channelId);
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[0].toString().equals("0") || 
          obj[1].toString().equals("1")) {
          rightCode = "01*02*01";
        } else {
          rightCode = "01*01*02";
        } 
      } 
      Query query = this.session.createQuery(
          " select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa join  aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 

          
          rightCode + "' and ccc.empId = " + userId);
      List<Object[]> tmpList = query.list();
      boolean hasAllScope = false;
      String scopeWhereSql = "";
      if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString(); 
        if (scopeType.equals("0")) {
          hasAllScope = true;
        } else if (scopeType.equals("1")) {
          scopeWhereSql = " aaa.createdEmp = " + userId + " or ";
        } else if (scopeType.equals("2")) {
          query = this.session.createQuery(
              " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
              orgId + "$%'");
          tmpList = query.list();
          for (int i = 0; i < tmpList.size(); i++)
            scopeWhereSql = String.valueOf(scopeWhereSql) + " aaa.createdOrg = " + 
              tmpList.get(i) + " or "; 
        } else if (scopeType.equals("3")) {
          scopeWhereSql = "aaa.createdOrg = " + orgId + " or ";
        } else if (scopeType.equals("4") && 
          scopeScope != null && !scopeScope.equals("")) {
          scopeScope = scopeScope.substring(1, 
              scopeScope.length() - 1);
          String[] tmp = { "" };
          if (scopeScope.indexOf("**") > 0) {
            tmp = scopeScope.split("\\*\\*");
          } else {
            tmp[0] = scopeScope;
          } 
          StringBuffer sb1 = new StringBuffer();
          for (int j = 0; j < tmp.length; j++)
            sb1.append(" aaa.orgIdString like '%$" + tmp[j] + 
                "$%' or "); 
          sb1.append(" 1 <> 1");
          query = this.session.createQuery(
              " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where " + 
              sb1.toString());
          tmpList = query.list();
          for (int i = 0; i < tmpList.size(); i++)
            scopeWhereSql = String.valueOf(scopeWhereSql) + 
              " aaa.createdOrg = " + 
              tmpList.get(i) + " or "; 
        } 
      } 
      if (hasAllScope) {
        result = Boolean.TRUE;
      } else {
        if (scopeWhereSql.endsWith("or "))
          scopeWhereSql = scopeWhereSql.substring(0, 
              scopeWhereSql.length() - 3); 
        if (scopeWhereSql == "")
          scopeWhereSql = "1=2"; 
        query = this.session.createQuery(
            " select count(aaa.channelId) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
            
            scopeWhereSql + ") and aaa.channelId=" + channelId);
        int count = ((Integer)query.iterate().next()).intValue();
        if (count > 0)
          result = Boolean.TRUE; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getCanVindicate(String userId, String orgId, String channelType) throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      String rightCode = "";
      if (Long.parseLong(channelType) == 0L || 
        Long.parseLong(channelType) >= 500000000L) {
        rightCode = "01*03*03";
      } else {
        rightCode = "部门主页维护";
      } 
      Query query = this.session.createQuery(
          " select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa join  aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 
          
          rightCode + "' and ccc.empId = " + userId);
      List<Object[]> tmpList = query.list();
      boolean hasAllScope = false;
      String scopeWhereSql = "";
      if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString(); 
        if (scopeType.equals("0")) {
          hasAllScope = true;
        } else if (scopeType.equals("1")) {
          scopeWhereSql = " aaa.createdEmp = " + userId + " or ";
        } else if (scopeType.equals("2")) {
          query = this.session.createQuery(
              " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
              orgId + "$%'");
          tmpList = query.list();
          for (int j = 0; j < tmpList.size(); j++)
            scopeWhereSql = String.valueOf(scopeWhereSql) + " aaa.createdOrg = " + 
              tmpList.get(j) + " or "; 
        } else if (scopeType.equals("3")) {
          scopeWhereSql = "aaa.createdOrg = " + orgId + " or ";
        } else if (scopeType.equals("4") && 
          scopeScope != null && !scopeScope.equals("")) {
          scopeScope = scopeScope.substring(1, 
              scopeScope.length() - 1);
          scopeScope = scopeScope.replace('*', ',');
          String[] tmp = scopeScope.split(",,");
          for (int k = 0; k < tmp.length; k++) {
            query = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                tmp[k] + 
                "$%'");
            tmpList = query.list();
            for (int j = 0; j < tmpList.size(); j++)
              scopeWhereSql = String.valueOf(scopeWhereSql) + 
                " aaa.createdOrg = " + 
                tmpList.get(j) + " or "; 
          } 
        } 
        String hSql = 
          " select aaa.channelId,aaa.channelName,aaa.channelIdString from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
        if (!hasAllScope) {
          if (scopeWhereSql.endsWith("or "))
            scopeWhereSql = scopeWhereSql.substring(0, 
                scopeWhereSql.length() - 3); 
          hSql = String.valueOf(hSql) + " where (" + scopeWhereSql + 
            ") and aaa.channelType = " + channelType + 
            " order by aaa.channelIdString ";
        } else {
          hSql = String.valueOf(hSql) + " where aaa.channelType = " + channelType + 
            " order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(hSql);
        list = query.list();
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        for (int i = 0; i < list.size(); i++) {
          String channelNameString = "";
          Object[] obj2 = list.get(i);
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = " select aaa.channelName from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where '" + 
              
              obj2[2] + 
              "' like concat('%$',aaa.channelId, '$%') order by aaa.channelIdString ";
          } else if (databaseType.indexOf("db2") >= 0) {
            tmpSql = " select aaa.channelName from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
              
              obj2[2] + "')>0  order by aaa.channelIdString ";
          } else {
            tmpSql = " select aaa.channelName from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where '" + 
              
              obj2[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
          } 
          query = this.session.createQuery(tmpSql);
          tmpList = query.list();
          for (int j = 0; j < tmpList.size(); j++)
            channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + 
              "."; 
          obj2[2] = channelNameString.substring(0, 
              channelNameString.length() - 1);
          list.set(i, obj2);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getOrgChannel(String orgId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelParentId  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 
          
          orgId + 
          " order by aaa.channelIdString ");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public void addUserChannel(UserChannelPO po) throws Exception {
    begin();
    try {
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public Boolean deleteUserChannel(String userChannelId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery(
          " select count(aaa.channelId) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 
          
          userChannelId);
      int count = Integer.parseInt(query.iterate().next().toString());
      if (count == 0) {
        result = Boolean.TRUE;
        this.session.delete(
            " from com.js.oa.info.channelmanager.po.UserChannelPO  aaa where aaa.userChannelId = " + 
            userChannelId);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public String getUserChannelName(String userChannelId) throws Exception {
    String userChannelName = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.userChannelName from  com.js.oa.info.channelmanager.po.UserChannelPO aaa  where aaa.userChannelId = " + 
          
          userChannelId);
      userChannelName = query.iterate().next().toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userChannelName;
  }
  
  public String getChannelName(String ChannelId) throws Exception {
    String channelName = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.channelName from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
          
          ChannelId);
      channelName = query.iterate().next().toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return channelName;
  }
  
  public void updateUserChannel(String userChannelId, String userChannelName) throws Exception {
    begin();
    try {
      UserChannelPO userChannelPO = (UserChannelPO)this.session.load(
          UserChannelPO.class, new Long(userChannelId));
      userChannelPO.setUserChannelName(userChannelName);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List getUserChannel(String domainId, String flag) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery(
          " select aaa.userChannelId, aaa.userChannelName from  com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.domainId=" + 
          
          domainId + " order by aaa.userChannelOrder");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getUserChannel(String domainId, String userId, String orgId, String orgIdString) throws Exception {
    begin();
    List list = null;
    int i = 0;
    try {
      StringBuffer where = new StringBuffer();
      where.append(" aaa.channelReader like '%$").append(userId).append("$%'");
      if (orgIdString.length() > 0) {
        orgIdString = orgIdString.substring(1, orgIdString.length() - 1);
        String[] orgIds = orgIdString.split("\\$\\$");
        for (i = 0; i < orgIds.length; i++)
          where.append(" or aaa.channelReadOrg like '%*").append(orgIds[i]).append("*%'"); 
      } 
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      for (i = 0; groupList != null && i < groupList.size(); i++)
        where.append(" or aaa.channelReadGroup like '%@").append(groupList.get(i)).append("@%'"); 
      list = this.session.createQuery(
          " select aaa.userChannelId, aaa.userChannelName from  com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.domainId=" + 
          
          domainId + " and (" + where.toString() + ") order by aaa.userChannelOrder").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getChannelMenu(String rightWhere, String scopeWhere, String channelType, String domainId, String corpId, String sidelineCorpId) throws Exception {
    List list = null;
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1 where ";
      if (SystemCommon.getMultiDepart() == 1 && 
        !"".equals(corpId) && !"0".equals(corpId)) {
        tmpSql = String.valueOf(tmpSql) + " (po1.corpId=" + corpId;
        if (!"".equals(sidelineCorpId) && !"0".equals(sidelineCorpId))
          tmpSql = String.valueOf(tmpSql) + " or po1.corpId=" + sidelineCorpId; 
        tmpSql = String.valueOf(tmpSql) + ") and ";
      } 
      tmpSql = String.valueOf(tmpSql) + "po1.channelType=" + 
        channelType + " and po1.domainId=" + domainId + 
        " order by po1.channelIdString";
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getChannelMenu(String scopeWhere, String channelType, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where po2.channelIdString like concat('%$', po1.channelId, '$%') and po1.channelType=" + 


          
          channelType + 
          " and (" + 
          scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup='')))  and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
          " and po1.domainId=" + domainId + " and po2.domainId=" + domainId + 
          " order by po1.channelIdString";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(po1.channelId)), '$'),po2.channelIdString)>0  and po1.channelType=" + 


          
          channelType + 
          " and (" + 
          scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup='')))  and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
          " and po1.domainId=" + domainId + " and po2.domainId=" + domainId + 
          " order by po1.channelIdString";
      } else {
        tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where po2.channelIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(po1.channelId)), '$%') and po1.channelType=" + 


          
          channelType + 
          " and (" + 
          scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup='')))  and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
          " and po1.domainId=" + domainId + " and po2.domainId=" + domainId + 
          " order by po1.channelIdString";
      } 
      Query query = this.session.createQuery(tmpSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String[] getSortChannel(String channelId, String channelType) throws Exception {
    begin();
    String[] result = { "", "" };
    try {
      Query query = this.session.createQuery(
          " select aaa.channelParentId, aaa.channelSort  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
          
          channelId);
      List<Object[]> list = query.list();
      Object[] tmp = list.get(0);
      query = this.session.createQuery(" select aaa.channelId from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelParentId = " + 
          
          tmp[0] + 
          " and aaa.channelSort < " + tmp[1] + 
          " and aaa.channelType = " + channelType + 
          " order by aaa.channelSort desc ");
      query.setFirstResult(0);
      query.setMaxResults(1);
      list = query.list();
      if (list != null && list.size() > 0) {
        result[0] = "1";
        result[1] = list.iterator().next().toString();
      } else {
        query = this.session.createQuery(" select aaa.channelId from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelParentId = " + 

            
            tmp[0] + " and aaa.channelSort > " + 
            tmp[1] + 
            " and aaa.channelType = " + 
            channelType + 
            " order by aaa.channelSort");
        query.setFirstResult(0);
        query.setMaxResults(1);
        list = query.list();
        if (list != null && list.size() > 0) {
          result[0] = "0";
          result[1] = list.iterator().next().toString();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public void modifyByArray(String[] para, String channelOrderId, String radiobutton) throws Exception {
    begin();
    try {
      Query query = null;
      List<Object[]> list = null;
      query = this.session.createQuery(" select aaa from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
          
          para[0]);
      InformationChannelPO needModiChannelPO = 
        query.list().get(0);
      String channelId = para[0];
      if (para[27].equals("0"))
        StaticParam.updateInformationByChannelId(channelId); 
      int oldNeedCheck = needModiChannelPO.getChannelNeedCheckup();
      int sort = 0;
      if (channelOrderId.equals("-1")) {
        sort = 500000;
      } else {
        query = this.session.createQuery(
            " select aaa.channelSort, aaa.channelParentId, aaa.channelType from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
            
            channelOrderId);
        list = query.list();
        Object[] arrayOfObject = list.get(0);
        String orderChannelSort = arrayOfObject[0].toString();
        String orderChannelParentId = arrayOfObject[1].toString();
        String orderChannelType = arrayOfObject[2].toString();
        if (radiobutton.equals("up")) {
          query = this.session.createQuery(
              " select max(aaa.channelSort) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 
              
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort < " + orderChannelSort);
          Object maxChannelSort = query.list().get(0);
          if (maxChannelSort == null) {
            sort = Integer.parseInt(orderChannelSort) - 10000;
          } else {
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(maxChannelSort.toString())) / 
              2;
          } 
        } else {
          query = this.session.createQuery(
              " select min(aaa.channelSort) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 
              
              orderChannelType + 
              " and aaa.channelParentId = " + 
              orderChannelParentId + 
              " and aaa.channelSort > " + orderChannelSort);
          list = query.list();
          String minChannelSort = "0";
          if (list != null && list.size() > 0 && list.get(0) != null)
            minChannelSort = list.get(0).toString(); 
          if (!minChannelSort.equals("0")) {
            sort = (Integer.parseInt(orderChannelSort) + 
              Integer.parseInt(minChannelSort)) / 2;
          } else {
            sort = Integer.parseInt(orderChannelSort) + 10000;
          } 
        } 
      } 
      String modifyChId = para[0];
      query = this.session.createQuery(" select aaa.channelIdString,aaa.channelParentId,aaa.channelSort,aaa.channelLevel  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
          
          modifyChId);
      Object[] tmpObj = query.list().get(0);
      String beforeModiChIdStr = tmpObj[0].toString();
      String beforeModiParentId = tmpObj[1].toString();
      String beforeModiChSort = tmpObj[2].toString();
      String beforeModiChLevel = tmpObj[3].toString();
      String modifyedChPaId = para[3];
      String modifyedChIdStr = "";
      int modifyedChLevel = 0;
      if (beforeModiParentId.equals(modifyedChPaId)) {
        int i = beforeModiChIdStr.indexOf(String.valueOf(beforeModiChSort) + "$" + 
            modifyChId + "$_");
        modifyedChIdStr = String.valueOf(beforeModiChIdStr.substring(0, i)) + sort + 
          "$" + modifyChId + "$_";
        query = this.session.createQuery(" select aaa from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId <>" + 

            
            modifyChId + 
            " and aaa.channelIdString like '%$" + 
            modifyChId + "$%' ");
        list = query.list();
        for (int j = 0; j < list.size(); j++) {
          InformationChannelPO sonPO = (InformationChannelPO)list
            .get(j);
          String sonIdStr = sonPO.getChannelIdString();
          sonPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              sonIdStr
              .substring(beforeModiChIdStr.length(), 
                sonIdStr.length()));
        } 
        needModiChannelPO.setChannelSort(sort);
        needModiChannelPO.setChannelIdString(modifyedChIdStr);
        needModiChannelPO.setChannelLevel(Integer.parseInt(
              beforeModiChLevel));
      } else if (modifyedChPaId.equals("0")) {
        modifyedChLevel = 1;
        modifyedChIdStr = String.valueOf(sort) + "$" + modifyChId + "$_";
        query = this.session.createQuery(" select aaa from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelIdString like '%$" + 

            
            modifyChId + "$%'");
        list = query.list();
        String sonIdString = "";
        int sonLevel = 0;
        for (int j = 0; j < list.size(); j++) {
          InformationChannelPO sonPO = 
            (InformationChannelPO)list.get(j);
          sonIdString = sonPO.getChannelIdString();
          sonLevel = sonPO.getChannelLevel();
          sonPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              sonIdString
              .substring(beforeModiChIdStr
                .length()));
          sonPO.setChannelLevel(sonLevel - 1);
        } 
        needModiChannelPO.setChannelSort(sort);
        needModiChannelPO.setChannelIdString(modifyedChIdStr);
        needModiChannelPO.setChannelLevel(1);
      } else {
        query = this.session.createQuery(
            " select aaa.channelIdString,aaa.channelLevel from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelId = " + 
            
            modifyedChPaId);
        tmpObj = query.list().get(0);
        String modifyedPaChIdString = tmpObj[0].toString();
        String modifyedPaChLevel = tmpObj[1].toString();
        modifyedChIdStr = String.valueOf(modifyedPaChIdString) + sort + "$" + 
          modifyChId + "$_";
        modifyedChLevel = Integer.parseInt(modifyedPaChLevel) + 1;
        query = this.session.createQuery(" select aaa from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelIdString like '%$" + 

            
            modifyChId + "$%'");
        list = query.list();
        String sonIdString = "";
        int sonLevel = 0;
        for (int j = 0; j < list.size(); j++) {
          InformationChannelPO sonPO = 
            (InformationChannelPO)list.get(j);
          sonIdString = sonPO.getChannelIdString();
          sonLevel = sonPO.getChannelLevel();
          sonPO.setChannelIdString(String.valueOf(modifyedChIdStr) + 
              sonIdString
              .substring(beforeModiChIdStr
                .length()));
          int m = modifyedChLevel - 
            Integer.parseInt(beforeModiChLevel);
          sonPO.setChannelLevel(sonLevel + m);
        } 
        needModiChannelPO.setChannelSort(sort);
        needModiChannelPO.setChannelIdString(modifyedChIdStr);
        needModiChannelPO.setChannelLevel(modifyedChLevel);
      } 
      needModiChannelPO.setChannelName(para[1]);
      needModiChannelPO.setChannelType(Integer.parseInt(para[2]));
      needModiChannelPO.setChannelParentId(new Long(para[3]));
      needModiChannelPO.setChannelNeedCheckup(Integer.parseInt(para[4]));
      needModiChannelPO.setChannelIssuer(para[5]);
      needModiChannelPO.setChannelIssuerGroup(para[6]);
      needModiChannelPO.setChannelIssuerOrg(para[7]);
      needModiChannelPO.setChannelReader(para[8]);
      needModiChannelPO.setChannelReaderGroup(para[9]);
      needModiChannelPO.setChannelReaderOrg(para[10]);
      needModiChannelPO.setChannelIssuerName(para[11]);
      needModiChannelPO.setChannelReaderName(para[12]);
      needModiChannelPO.setChannelShowType(Integer.parseInt(para[13]));
      needModiChannelPO.setOnDesktop(Integer.parseInt(para[14]));
      needModiChannelPO.setIncludeChild(Integer.parseInt(para[21]));
      needModiChannelPO.setChannelManager(para[22]);
      needModiChannelPO.setChannelManagerOrg(para[23]);
      needModiChannelPO.setChannelManagerGroup(para[24]);
      needModiChannelPO.setChannelManagerName(para[25]);
      int moduleId = 4;
      needModiChannelPO.setAfficheChannelStatus(para[26]);
      if (para.length > 27 && "2".equals(para[26]))
        moduleId = 50; 
      if (para.length > 27) {
        needModiChannelPO.setIsAllowReview(para[27]);
      } else {
        needModiChannelPO.setIsAllowReview("0");
      } 
      if (para[18] == null) {
        needModiChannelPO.setOnDepaDesk(0);
      } else {
        needModiChannelPO.setOnDepaDesk(Integer.parseInt(para[18]));
      } 
      if (para[4].equals("0") && oldNeedCheck == 1) {
        query = this.session.createQuery("select aaa.processId from com.js.oa.jsflow.po.WorkFlowChannelPO aaa join aaa.channel bbb where bbb.channelId=" + 
            para[0]);
        Iterator<Long> iter = query.iterate();
        Long processId = iter.next();
        this.session.delete("from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa where aaa.wfWorkFlowProcessId=" + 
            processId.toString());
        this.session.delete(
            "from com.js.oa.jsflow.po.WorkFlowChannelPO aaa where aaa.processId=" + 
            processId.toString());
      } 
      if (para[4].equals("1") && oldNeedCheck == 0) {
        String domainId = needModiChannelPO.getDomainId().toString();
        query = this.session.createQuery("select aaa from com.js.oa.jsflow.po.WFPackagePO aaa where aaa.moduleId=" + moduleId + " and aaa.domainId=" + domainId);
        Iterator iter = query.iterate();
        WFPackagePO packagePO = new WFPackagePO();
        if (iter.hasNext())
          packagePO = query.iterate().next(); 
        query = this.session.createQuery("select aaa.immoFormId from com.js.oa.jsflow.po.WorkFlowImmoFormPO aaa where aaa.moduleId=" + moduleId);
        iter = query.iterate();
        Long formId = new Long(0L);
        if (iter.hasNext())
          formId = query.iterate().next(); 
        WFWorkFlowProcessPO processPO = new WFWorkFlowProcessPO();
        processPO.setAccessDatabaseId(formId);
        processPO.setWorkFlowProcessName(
            String.valueOf(needModiChannelPO.getChannelName()) + "发布流程");
        processPO.setProcessCreatedDate(new Date());
        processPO.setProcessDescription(
            String.valueOf(needModiChannelPO.getChannelName()) + "发布流程");
        processPO.setProcessType(1);
        processPO.setFormClassName("InformationWorkFlow");
        processPO.setFormClassMethod("save");
        processPO.setFormClassCompMethod("complete");
        processPO.setWfPackage(packagePO);
        processPO.setDomainId(domainId);
        Long processId = (Long)this.session.save(processPO);
        WFActivityPO startActivityPO = new WFActivityPO();
        startActivityPO.setActivityBeginEnd(1);
        startActivityPO.setWfWorkFlowProcess(processPO);
        startActivityPO.setDomainId(domainId);
        this.session.save(startActivityPO);
        WFActivityPO endActivityPO = new WFActivityPO();
        endActivityPO.setActivityBeginEnd(2);
        endActivityPO.setWfWorkFlowProcess(processPO);
        endActivityPO.setDomainId(domainId);
        this.session.save(endActivityPO);
        WorkFlowChannelPO wfcPO = new WorkFlowChannelPO();
        wfcPO.setProcessId(processId);
        wfcPO.setChannel(needModiChannelPO);
        this.session.save(wfcPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public Boolean canOnDesktop() throws Exception {
    Boolean canOnDesktop = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery(
          "select count(aaa.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.onDesktop = 1");
      if (Integer.parseInt(query.list().iterator().next().toString()) < 4)
        canOnDesktop = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return canOnDesktop;
  }
  
  public List getChannelPosition() throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.positionId, aaa.positionName from com.js.oa.info.channelmanager.po.ChannelPositionPO aaa");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Boolean departPageRight(String userId, String userOrg, String userOrgString, String orgId) throws Exception {
    Boolean flag = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.rightScopeType, aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where ccc.empId = " + 

          
          userId + " and bbb.rightCode = '01*01*02'");
      List<Object[]> list = query.list();
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        int rightScopeType = Integer.parseInt(obj[0].toString());
        String rightScopeScope = "";
        if (obj[1] != null)
          rightScopeScope = obj[1].toString(); 
        if (rightScopeType == 0) {
          flag = Boolean.TRUE;
        } else if (rightScopeType == 2) {
          query = this.session.createQuery(
              "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
              userOrg + "$%'");
          list = query.list();
          for (int j = 0; j < list.size(); j++) {
            if (orgId.equals(list.get(j).toString())) {
              flag = Boolean.TRUE;
              break;
            } 
          } 
        } else if (rightScopeType == 3) {
          if (userOrg.equals(orgId))
            flag = Boolean.TRUE; 
        } else if (rightScopeType == 4) {
          if (!rightScopeScope.equals(""))
            if (rightScopeScope.indexOf("*" + orgId + "*") >= 0) {
              flag = Boolean.TRUE;
            } else {
              String[] tmp = ("*" + rightScopeScope + 
                "*").split("**");
              StringBuffer sb = new StringBuffer();
              int k;
              for (k = 0; k < tmp.length; k++) {
                if (!tmp[k].equals(""))
                  sb.append(" aaa.orgIdString like '%$" + 
                      tmp[k] + "$%' and "); 
              } 
              if (!sb.toString().equals("")) {
                query = this.session.createQuery(
                    "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where " + 
                    sb.toString() + " 1 = 1");
                list = query.list();
                if (list != null && list.size() > 0)
                  for (k = 0; k < list.size(); k++) {
                    if (list.get(k).toString().equals(orgId)) {
                      flag = Boolean.TRUE;
                      break;
                    } 
                  }  
              } 
            }  
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return flag;
  }
  
  public DepartmentStylePO getDepaStyle(String styleId) throws Exception {
    DepartmentStylePO stylePO = new DepartmentStylePO();
    begin();
    try {
      stylePO = (DepartmentStylePO)this.session.load(DepartmentStylePO.class, 
          new Integer(styleId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return stylePO;
  }
  
  public Boolean canOnDepaDesk(String channelType) throws Exception {
    Boolean canOnDepaDesk = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery(
          "select count(aaa.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.onDepaDesk = 1 and aaa.channelType = " + 

          
          channelType);
      if (Integer.parseInt(query.list().iterator().next().toString()) < 2)
        canOnDepaDesk = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return canOnDepaDesk;
  }
  
  public List getOtherPositionCh(String positionId, String userId, String orgId) throws Exception {
    List<Object[]> userViewCh = getUserAllViewCh(userId, orgId);
    StringBuffer channelIdString = new StringBuffer("0");
    Object[] chIdObj = (Object[])null;
    if (userViewCh != null && userViewCh.size() > 0) {
      channelIdString = new StringBuffer();
      for (int i = 0; i < userViewCh.size(); i++) {
        chIdObj = userViewCh.get(i);
        if (!chIdObj[0].equals("")) {
          channelIdString.append(chIdObj[0]);
          if (i != userViewCh.size())
            channelIdString.append(","); 
        } 
      } 
    } 
    begin();
    ArrayList chList = new ArrayList();
    try {
      String tmp = channelIdString.toString().trim();
      if (tmp.endsWith(","))
        tmp = tmp.substring(0, tmp.length() - 1); 
      Query query = this.session.createQuery("select aaa.channelId, aaa.channelName, aaa.channelType, aaa.channelShowType, aaa.positionUpDown from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelPosition = " + 
          
          positionId + 
          " and aaa.channelId in (" + tmp + 
          ") order by aaa.channelIdString");
      chList = (ArrayList)query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return chList;
  }
  
  public List getUserAllViewCh(String userId, String orgId) throws Exception {
    begin();
    List list = null;
    try {
      String hSql = 
        " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode ='01*03*03'  and ccc.empId = " + 
          
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + " aaa.channelReader like '%$" + 
          userId + "$%' or (aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) " + 
          "and (aaa.channelType = 0 or aaa.channelType > 500000000)" + 
          " order by aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list = query.list();
      hSql = 
        "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '部门主页维护' and ccc.empId = " + 
          
          userId);
      tmpList = query.list();
      allScope = false;
      scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + " aaa.channelReader like '%$" + 
          userId + "$%' or (aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) " + 
          "and (aaa.channelType > 0 and aaa.channelType <= 500000000)" + 
          " order by aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list.addAll(query.list());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getUserAllViewCh(String userId, String orgId, String channelId) throws Exception {
    begin();
    List list = null;
    try {
      String hSql = 
        " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode ='01*03*03'  and ccc.empId = " + 
          
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + " aaa.channelReader like '%$" + 
          userId + "$%' or (aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) " + 
          "and (aaa.channelType = 0 or aaa.channelType > 500000000)" + 
          " and aaa.channelIdString like '%" + channelId + "%' order by aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " where aaa.channelIdString like '%" + channelId + "%'" + " order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list = query.list();
      hSql = 
        "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      query = this.session.createQuery("select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '部门主页维护' and ccc.empId = " + 
          
          userId);
      tmpList = query.list();
      allScope = false;
      scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + " aaa.channelReader like '%$" + 
          userId + "$%' or (aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) " + 
          "and (aaa.channelType > 0 and aaa.channelType <= 500000000)" + 
          " and aaa.channelIdString like '%" + channelId + "%' order by aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " and aaa.channelIdString like '%" + channelId + "%' order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list.addAll(query.list());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String getUserViewChildCh(String userId, String orgId, String channelId) throws Exception {
    StringBuffer sb = new StringBuffer("0,");
    StringBuffer subsb = new StringBuffer();
    sb.append(channelId).append(",");
    begin();
    List<Object[]> list = null;
    try {
      String hSql = 
        " select aaa.channelId,aaa.channelLevel,aaa.channelIdString,aaa.includeChild,aaa.channelParentId  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      orgList.add("-1");
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode ='01*03*03'  and ccc.empId = " + 
          
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals("null") && obj[1].toString().length() > 2)
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        for (int i = 0; i < orgList.size(); i++)
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or "; 
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + 
            tmpList.get(j) + "@%' or "; 
        hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + 
          groupString + " aaa.channelReader like '%$" + 
          userId + "$%' or ";
        if (databaseType.indexOf("mysql") >= 0) {
          hSql = String.valueOf(hSql) + "(aaa.channelReader='' and aaa.channelReaderOrg ='' and aaa.channelReaderGroup ='') ) ";
        } else {
          hSql = String.valueOf(hSql) + "(aaa.channelReader is null and aaa.channelReaderOrg is null and aaa.channelReaderGroup is null) ) ";
        } 
        hSql = String.valueOf(hSql) + 
          " and aaa.channelIdString like '%" + channelId + "%' order by aaa.channelLevel,aaa.channelIdString ";
      } else {
        hSql = String.valueOf(hSql) + " where aaa.channelIdString like '%" + channelId + "%'" + " order by aaa.channelLevel,aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list = query.list();
      if (list != null)
        for (int i = 1; i < list.size(); i++) {
          Object[] obj = list.get(i);
          String curChannelId = obj[0].toString();
          String parentId = obj[4].toString();
          if ("1".equals(obj[3].toString())) {
            if (sb.indexOf("," + parentId + ",") >= 0) {
              sb.append(curChannelId).append(",");
              subsb.append(curChannelId).append(",");
            } 
          } else if (sb.indexOf("," + parentId + ",") >= 0) {
            subsb.append(curChannelId).append(",");
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return sb.append(subsb.toString()).append("0").toString();
  }
  
  public Long getChannelProcessId(String channelId) throws Exception {
    begin();
    Long processId = new Long(0L);
    try {
      Query query = this.session.createQuery(
          "select aaa.processId from com.js.oa.jsflow.po.WorkFlowChannelPO aaa join aaa.channel bbb where bbb.channelId=" + 
          channelId);
      Iterator<Long> iter = query.iterate();
      if (iter.hasNext())
        processId = iter.next(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return processId;
  }
  
  public String getChannelProcessInfo(String channelId) throws Exception {
    begin();
    String info = null;
    try {
      Long processId = new Long(0L);
      Query query = this.session.createQuery(
          "select aaa.processId from com.js.oa.jsflow.po.WorkFlowChannelPO aaa join aaa.channel bbb where bbb.channelId=" + 
          channelId);
      Iterator<Long> iter = query.iterate();
      if (iter.hasNext()) {
        processId = iter.next();
        iter = this.session.createQuery("select po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=" + channelId).iterate();
        if (iter.hasNext())
          info = processId + "," + iter.next().toString(); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return info;
  }
  
  public List getAllCanIssueWithoutCheck(String userId, String orgId, String domainId, String test) throws Exception {
    List<Object[]> list = null;
    ArrayList<Object[]> resultList = new ArrayList();
    begin();
    try {
      String hSql = " select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa where ";
      hSql = String.valueOf(hSql) + "  aaa.afficheChannelStatus <> '2'   and  ";
      Query query = this.session.createQuery(
          " select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
          userId);
      List<String> tmpList = query.list();
      String canIssueGroupStr = "";
      for (int i = 0; i < tmpList.size(); i++)
        canIssueGroupStr = String.valueOf(canIssueGroupStr) + 
          " aaa.channelIssuerGroup like '%@" + 
          tmpList.get(i) + "@%' or "; 
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0  ";
      } else {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      tmpList = query.list();
      String canIssueOrgStr = "";
      for (int j = 0; j < tmpList.size(); j++)
        canIssueOrgStr = String.valueOf(canIssueOrgStr) + 
          " aaa.channelIssuerOrg like '%*" + tmpList.get(j) + "*%' or "; 
      canIssueOrgStr = String.valueOf(canIssueOrgStr) + " aaa.channelIssuerOrg like '%*-1*%' or ";
      query = this.session.createQuery(String.valueOf(hSql) + "(" + canIssueGroupStr + 
          canIssueOrgStr + 
          " aaa.channelIssuer like '%$" + userId + 
          "$%' " + 
          ") and aaa.channelNeedCheckup = 0 and aaa.domainId=" + 
          domainId + 
          " order by aaa.channelType,aaa.channelIdString ");
      list = query.list();
      Iterator<E> orgIter = null;
      for (int k = 0; k < list.size(); k++) {
        String channelNameString = "";
        Object[] obj = list.get(k);
        if (obj[2] != null) {
          String channelIdString = obj[2].toString();
          obj[2] = ChannelCache.getChannelNameString(obj[0].toString());
          if (obj[2] == null || obj[2].equals("null") || "".equals(obj[2])) {
            if (obj[3].toString().equals("0")) {
              channelNameString = "知识管理.";
            } else if (obj[4] != null && "1".equals(obj[4].toString())) {
              query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                  obj[3]);
              orgIter = query.iterate();
              if (orgIter.hasNext()) {
                channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
              } else {
                continue;
              } 
            } else {
              query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
                  obj[3]);
              orgIter = query.iterate();
              if (orgIter.hasNext()) {
                channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
                  channelNameString;
              } else {
                continue;
              } 
            } 
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
                
                channelIdString + 
                "' like concat('%$', aaa.channelId, '$%') and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } else if (databaseType.indexOf("db2") >= 0) {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
                
                channelIdString + "')>0 and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } else {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
                
                channelIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } 
            query = this.session.createQuery(tmpSql);
            tmpList = query.list();
            for (int m = 0; m < tmpList.size(); m++)
              channelNameString = String.valueOf(channelNameString) + tmpList.get(m) + 
                "."; 
            obj[2] = channelNameString.substring(0, channelNameString.length() - 1);
          } 
          Object[] resultObj = { obj[0], obj[1], obj[2], obj[3] };
          resultList.add(resultObj);
        } 
        continue;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return resultList;
  }
  
  public Boolean canVindicate(String userId, String orgId, String channelType, String channelId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      String rightCode = "";
      Iterator<Object[]> iter = this.session.iterate("select a.channelType, a.userDefine from com.js.oa.info.channelmanager.po.InformationChannelPO a where a.channelId = " + 
          channelId);
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        if (obj[0].toString().equals("0") || 
          obj[1].toString().equals("1")) {
          rightCode = "01*02*01";
        } else {
          rightCode = "01*01*02";
        } 
      } 
      Query query = this.session.createQuery(
          " select aaa.rightScopeType,aaa.rightScopeScope from  com.js.system.vo.rolemanager.RightScopeVO aaa join  aaa.right bbb join aaa.employee ccc where bbb.rightCode='" + 

          
          rightCode + "' and ccc.empId = " + userId);
      List<Object[]> tmpList = query.list();
      boolean hasAllScope = false;
      String scopeWhereSql = "";
      if (tmpList != null && tmpList.size() > 0 && tmpList.get(0) != null) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals("null") && obj[1].toString().length() > 2)
          scopeScope = obj[1].toString(); 
        if (scopeType.equals("0")) {
          hasAllScope = true;
        } else if (scopeType.equals("1")) {
          scopeWhereSql = " aaa.createdEmp = " + userId + " or ";
        } else if (scopeType.equals("2")) {
          query = this.session.createQuery(
              " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
              orgId + "$%'");
          tmpList = query.list();
          for (int i = 0; i < tmpList.size(); i++)
            scopeWhereSql = String.valueOf(scopeWhereSql) + " aaa.createdOrg = " + 
              tmpList.get(i) + " or "; 
        } else if (scopeType.equals("3")) {
          scopeWhereSql = "aaa.createdOrg = " + orgId + " or ";
        } else if (scopeType.equals("4") && 
          scopeScope != null && !scopeScope.equals("")) {
          scopeScope = scopeScope.substring(1, 
              scopeScope.length() - 1);
          query = this.session.createQuery(
              " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
              scopeScope + 
              "$%'");
          tmpList = query.list();
          for (int i = 0; i < tmpList.size(); i++)
            scopeWhereSql = String.valueOf(scopeWhereSql) + 
              " aaa.createdOrg = " + 
              tmpList.get(i) + " or "; 
        } 
      } 
      if (hasAllScope) {
        result = Boolean.TRUE;
      } else {
        if (scopeWhereSql.endsWith("or "))
          scopeWhereSql = scopeWhereSql.substring(0, 
              scopeWhereSql.length() - 3); 
        query = this.session.createQuery(
            " select count(aaa.channelId) from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
            
            scopeWhereSql + ") and aaa.channelId=" + channelId);
        int count = ((Integer)query.iterate().next()).intValue();
        if (count > 0)
          result = Boolean.TRUE; 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getCanIssue(String where, String domainId) throws Exception {
    List<Object[]> rList = new ArrayList();
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.isAllowReview from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        
        where + ") and aaa.domainId=" + 
        domainId + " and ( aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0' )" + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[2] != null && !obj[2].equals("null") && !"".equals(obj[2])) {
          String channelIdString = obj[2].toString();
          obj[2] = ChannelCache.getChannelNameString(obj[0].toString());
          if (obj[2] == null || obj[2].equals("null") || "".equals(obj[2])) {
            if (obj[3].toString().equals("0")) {
              channelNameString = "知识管理.";
            } else if (obj[4] != null && "1".equals(obj[4].toString())) {
              query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
                  obj[3]);
              orgIter = query.iterate();
              if (orgIter.hasNext()) {
                channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
              } else {
                continue;
              } 
            } else {
              query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
                  obj[3]);
              orgIter = query.iterate();
              if (orgIter.hasNext()) {
                channelNameString = String.valueOf(orgIter.next().toString()) + "." + 
                  channelNameString;
              } else {
                continue;
              } 
            } 
            String tmpSql = "";
            String databaseType = 
              SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
                
                channelIdString + 
                "' like concat('%$', aaa.channelId, '$%') and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } else if (databaseType.indexOf("db2") >= 0) {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
                
                channelIdString + "')>0  and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } else {
              tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
                
                channelIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.domainId=" + 
                domainId + 
                " order by aaa.channelIdString ";
            } 
            query = this.session.createQuery(tmpSql);
            tmpList = query.list();
            for (int j = 0; j < tmpList.size(); j++)
              channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + "."; 
            obj[2] = channelNameString.substring(0, channelNameString.length() - 1);
          } 
          rList.add(obj);
        } 
        continue;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return rList;
  }
  
  public List getAfficheCanIssue(String where, String domainId) throws Exception {
    List<Object[]> rList = new ArrayList();
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.isAllowReview from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        
        where + ") and aaa.domainId=" + 
        domainId + " and aaa.afficheChannelStatus='1' " + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator<E> orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0")) {
          channelNameString = "知识管理.";
        } else if (obj[4] != null && "1".equals(obj[4].toString())) {
          query = this.session.createQuery("select aaa.userChannelName from com.js.oa.info.channelmanager.po.UserChannelPO aaa where aaa.userChannelId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
          } else {
            continue;
          } 
        } else {
          query = this.session.createQuery("select aaa.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgId=" + 
              obj[3]);
          orgIter = query.iterate();
          if (orgIter.hasNext()) {
            channelNameString = String.valueOf(orgIter.next().toString()) + "." + channelNameString;
          } else {
            continue;
          } 
        } 
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + 
            "' like concat('%$', aaa.channelId, '$%') and aaa.domainId=" + 
            domainId + 
            " order by aaa.channelIdString ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
            
            obj[2] + "' )>0  and aaa.domainId=" + 
            domainId + 
            " order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.domainId=" + 
            domainId + 
            " order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + "."; 
        obj[2] = channelNameString.substring(0, channelNameString.length() - 1);
        rList.add(obj);
        continue;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return rList;
  }
  
  public List getAllCanIssueWithoutCheck(String userId, String orgId, String userDefine) throws Exception {
    List<Object[]> list = null;
    ArrayList<Object[]> resultList = new ArrayList();
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String hSql = " select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.isAllowReview from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa where ";
      Query query = this.session.createQuery(
          " select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa  join aaa.employees bbb where bbb.empId = " + 
          userId);
      List<String> tmpList = query.list();
      String canIssueGroupStr = "";
      for (int i = 0; i < tmpList.size(); i++)
        canIssueGroupStr = String.valueOf(canIssueGroupStr) + 
          " aaa.channelIssuerGroup like '%@" + 
          tmpList.get(i) + "@%' or "; 
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") )>0 ";
      } else {
        tmpSql = 
          " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      tmpList = query.list();
      String canIssueOrgStr = "";
      for (int j = 0; j < tmpList.size(); j++)
        canIssueOrgStr = String.valueOf(canIssueOrgStr) + 
          " aaa.channelIssuerOrg like '%*" + 
          tmpList.get(j) + "*%' or "; 
      query = this.session.createQuery(String.valueOf(hSql) + "(" + canIssueGroupStr + 
          canIssueOrgStr + 
          " aaa.channelIssuer like '%$" + userId + 
          "$%' " + 
          ") and aaa.channelNeedCheckup = 0 order by aaa.channelType,aaa.channelIdString ");
      list = query.list();
      Iterator orgIter = null;
      for (int k = 0; k < list.size(); k++) {
        String channelNameString = "";
        Object[] obj = list.get(k);
        obj[2] = ChannelCache.getChannelNameString(obj[0].toString());
        Object[] resultObj = { obj[0], obj[1], obj[2], obj[3] };
        resultList.add(resultObj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return resultList;
  }
  
  public List getUserViewCh(String userId, String orgId, String channelType, String userDefine, String domainId, String corpId, String sidelineCorpId) throws Exception {
    List list = null;
    begin();
    try {
      userDefine = (userDefine == null) ? "0" : userDefine;
      String rightCode = "";
      if ("0".equals(channelType) || "1".equals(userDefine)) {
        rightCode = "01*02*01";
      } else {
        rightCode = "01*01*02";
      } 
      String hSql = 
        " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString,aaa.afficheChannelStatus  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      String corpSQL = "";
      if (SystemCommon.getMultiDepart() == 1 && 
        !"".equals(corpId) && !"0".equals(corpId)) {
        corpSQL = " and (aaa.corpId=" + corpId;
        if (!"".equals(sidelineCorpId) && !"0".equals(sidelineCorpId))
          corpSQL = String.valueOf(corpSQL) + " or aaa.corpId=" + sidelineCorpId; 
        corpSQL = String.valueOf(corpSQL) + ") ";
      } 
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like concat('%$', aaa.orgId, '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.orgId)), '$'),(select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + "))>0 ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where (select bbb.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO bbb where bbb.orgId = " + 
          
          orgId + ") like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.orgId)), '$%') ";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<String> orgList = query.list();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like concat('%$', " + 
          orgId + 
          ", '$%') ";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
          
          orgId + "$%' ";
      } else {
        tmpSql = 
          "select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(" + 
          
          orgId + ")), '$%') ";
      } 
      query = this.session.createQuery(tmpSql);
      List<String> orgChildList = query.list();
      query = this.session.createQuery(" select aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa  join aaa.right bbb join aaa.employee ccc where bbb.rightCode = '" + 
          
          rightCode + "' and ccc.empId = " + 
          userId);
      List<Object[]> tmpList = query.list();
      boolean allScope = false;
      String scopeString = "";
      if (tmpList != null && tmpList.size() > 0) {
        Object[] obj = tmpList.get(0);
        String scopeType = obj[0].toString();
        String scopeScope = "";
        if (obj[1] != null && !obj[1].toString().equals("") && !obj[1].toString().equals("null"))
          scopeScope = obj[1].toString().substring(1, 
              obj[1].toString().length() - 1); 
        if (!scopeType.equals("0")) {
          if (scopeType.equals("1")) {
            scopeString = " aaa.createdEmp = " + userId + " or ";
          } else if (scopeType.equals("2")) {
            for (int i = 0; i < orgChildList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                orgChildList.get(i) + " or "; 
          } else if (scopeType.equals("3")) {
            scopeString = "aaa.createdOrg = " + orgId + " or ";
          } else if (scopeType.equals("4") && 
            !scopeScope.equals("")) {
            Query tmpQuery = this.session.createQuery(
                " select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgIdString like '%$" + 
                
                scopeScope + "$%'");
            List<String> tmpOrgList = tmpQuery.list();
            for (int i = 0; i < tmpOrgList.size(); i++)
              scopeString = String.valueOf(scopeString) + "aaa.createdOrg = " + 
                tmpOrgList.get(i) + " or "; 
          } 
        } else {
          allScope = true;
        } 
      } 
      if (!allScope) {
        String orgString = "";
        String whOrgString = "";
        String whGroupString = "";
        for (int i = 0; i < orgList.size(); i++) {
          orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*" + 
            orgList.get(i) + "*%' or ";
          whOrgString = String.valueOf(whOrgString) + " aaa.channelManagerOrg like '%*" + 
            orgList.get(i) + "*%' or ";
        } 
        orgString = String.valueOf(orgString) + " aaa.channelReaderOrg like '%*-1*%' or ";
        whOrgString = String.valueOf(whOrgString) + " aaa.channelManagerOrg like '%*-1*%' or ";
        String groupString = "";
        query = this.session.createQuery("select aaa.groupId from com.js.system.vo.groupmanager.GroupVO aaa join aaa.employees bbb where bbb.empId = " + 
            userId);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++) {
          groupString = String.valueOf(groupString) + 
            " aaa.channelReaderGroup like '%@" + tmpList.get(j) + "@%' or ";
          whGroupString = String.valueOf(whGroupString) + 
            " aaa.channelManagerGroup like '%@" + tmpList.get(j) + "@%' or ";
        } 
        if ("-1".equals(channelType)) {
          hSql = "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString,org.orgNameString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ,com.js.system.vo.organizationmanager.OrganizationVO org where (" + 

            
            scopeString + orgString + groupString + whOrgString + whGroupString + 
            " aaa.channelManager like '%$" + userId + "$%' or aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')))  and (aaa.channelType > 0) and (aaa.userDefine=0) and (org.orgId=aaa.channelType) and (aaa.domainId=" + 
            domainId + corpSQL + 
            ")  and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by org.orgIdString,aaa.channelIdString";
        } else {
          hSql = String.valueOf(hSql) + " where (" + scopeString + orgString + groupString + whOrgString + whGroupString + 
            " aaa.channelManager like '%$" + userId + "$%' or aaa.channelReader like '%$" + userId + "$%' or ((aaa.channelReader is null or aaa.channelReader='') and (aaa.channelReaderOrg is null or aaa.channelReaderOrg='') and (aaa.channelReaderGroup is null or aaa.channelReaderGroup='')) ) and aaa.channelType = " + 
            channelType + " and aaa.domainId=" + domainId + corpSQL + 
            "  and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by aaa.channelIdString ";
        } 
      } else if ("-1".equals(channelType)) {
        hSql = "select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString,org.orgNameString from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ,com.js.system.vo.organizationmanager.OrganizationVO org where aaa.channelType >0 and aaa.userDefine=0 and (org.orgId=aaa.channelType) and (aaa.domainId=" + 


          
          domainId + corpSQL + 
          ")   and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by org.orgIdString,aaa.channelIdString";
      } else {
        hSql = String.valueOf(hSql) + " where aaa.channelType = " + channelType + 
          "  and aaa.domainId=" + domainId + corpSQL + 
          "  and (aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0') order by aaa.channelIdString ";
      } 
      query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public void addUserChannel(String userChannelName, String userChannelOrder, String domainId) throws Exception {
    begin();
    try {
      UserChannelPO userChannelPO = new UserChannelPO();
      userChannelPO.setUserChannelName(userChannelName);
      userChannelPO.setDomainId(Long.valueOf(domainId));
      if (userChannelOrder == null || userChannelOrder.equals("")) {
        userChannelOrder = "99";
      } else if (userChannelOrder.length() == 1) {
        userChannelOrder = "0" + userChannelOrder;
      } 
      userChannelPO.setUserChannelOrder(userChannelOrder);
      this.session.save(userChannelPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void updateUserChannel(UserChannelPO po) throws Exception {
    begin();
    try {
      UserChannelPO userChannelPO = (UserChannelPO)this.session.load(
          UserChannelPO.class, po.getUserChannelId());
      userChannelPO.setUserChannelName(po.getUserChannelName());
      userChannelPO.setUserChannelOrder(po.getUserChannelOrder());
      userChannelPO.setChannelReader(po.getChannelReader());
      userChannelPO.setChannelReadGroup(po.getChannelReadGroup());
      userChannelPO.setChannelReadOrg(po.getChannelReadOrg());
      userChannelPO.setChannelReadName(po.getChannelReadName());
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public UserChannelPO getUserChannel(String userChannelId) throws Exception {
    UserChannelPO userChannelPO = null;
    begin();
    try {
      userChannelPO = (UserChannelPO)this.session.load(UserChannelPO.class, 
          new Long(userChannelId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return userChannelPO;
  }
  
  public List getSingleChannelName(String channelIdString) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.channelName ,aaa.afficheChannelStatus  from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelIdString = " + 
          channelIdString);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String hasChannelViewRight(String userId, String orgId, String channelType, String userDefine, String channelId, String domainId, String corpId, String sidelineCorpId) throws Exception {
    String right = "0";
    List<Object[]> list = getUserViewCh(userId, orgId, channelType, userDefine, 
        domainId, corpId, sidelineCorpId);
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      if (obj[0].toString().trim().equals(channelId.trim())) {
        right = "1";
        break;
      } 
    } 
    return right;
  }
  
  public Boolean isChannelManager(String channelId, String userId, String orgId, String orgIdString) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$")
        .toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + 
          userId).list();
      buffer = new StringBuffer(" where po.channelId=");
      buffer.append(channelId).append(" and (");
      int i;
      for (i = 0; groupList != null && i < groupList.size(); i++)
        buffer.append(" po.channelManagerGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.channelManagerOrg like '%").append(orgIdArray[i]).append("%' or "); 
      } 
      buffer.append(" po.channelManager like '%").append(userId).append("%')");
      int num = ((Integer)this.session.createQuery("select count(po.channelId) from com.js.oa.info.channelmanager.po.InformationChannelPO po " + buffer.toString()).iterate().next()).intValue();
      if (num > 0)
        result = Boolean.TRUE; 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      ex.printStackTrace();
      throw ex;
    } finally {}
    return result;
  }
  
  public String getInformationProcessId(String informationId) throws Exception {
    begin();
    String processId = "0";
    try {
      Query query = this.session.createQuery("select bbb.channelId from com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb where aaa.informationId=" + informationId);
      Iterator<E> iter = query.iterate();
      if (iter.hasNext())
        informationId = iter.next().toString(); 
      query = this.session.createQuery(
          "select aaa.processId from com.js.oa.jsflow.po.WorkFlowChannelPO aaa join aaa.channel bbb where bbb.channelId=" + 
          informationId);
      iter = query.iterate();
      if (iter.hasNext())
        processId = iter.next().toString(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return processId;
  }
  
  public List getUserManageList(String userId, String orgId, String orgIdString, String channelType, String userDefine, String domainId) throws Exception {
    List list = null;
    begin();
    try {
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      List groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      buffer = new StringBuffer("select po.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO po where ");
      int i;
      for (i = 0; groupList != null && i < groupList.size(); i++)
        buffer.append(" po.channelManagerGroup like '%").append(groupList.get(i)).append("%' or "); 
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.channelManagerOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      buffer.append(" po.channelManagerOrg like '%*-1*%' or po.channelManager like '%").append(userId).append("%'");
      list = this.session.createQuery(buffer.toString()).list();
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
      ex.printStackTrace();
      throw ex;
    } finally {}
    return list;
  }
  
  public List getChannelMenu_ByType(String rightWhere, String scopeWhere, String channelType, String domainId, String type) throws Exception {
    List list = null;
    begin();
    try {
      if (type.equals("2")) {
        String sqlStr = "select  po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1,  where po1.afficheChannelStatus ='2' and po1.domainId=" + 
          
          domainId + " order by po1.channelIdString";
        Query query1 = this.session.createQuery(sqlStr);
        list = query1.list();
      } else {
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where po2.channelIdString like concat('%$', po1.channelId, '$%') and po1.channelType=" + 


            
            channelType + 
            " and (" + rightWhere + " or " + 
            scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup=''))) and po1.domainId=" + 
            domainId + " and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
            " order by po1.channelIdString";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(po1.channelId)), '$'),po2.channelIdString )>0  and po1.channelType=" + 


            
            channelType + 
            " and (" + rightWhere + " or " + 
            scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup=''))) and po1.domainId=" + 
            domainId + " and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
            " order by po1.channelIdString";
        } else {
          tmpSql = "select distinct po1.channelId, po1.channelName, po1.channelLevel, po1.channelParentId, po1.channelShowType,po1.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO po1, com.js.oa.info.channelmanager.po.InformationChannelPO po2 where po2.channelIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(po1.channelId)), '$%') and po1.channelType=" + 


            
            channelType + 
            " and (" + rightWhere + " or " + 
            scopeWhere + " or ((po2.channelReader is null or po2.channelReader='') and (po2.channelReaderOrg is null or po2.channelReaderOrg='') and (po2.channelReaderGroup is null or po2.channelReaderGroup=''))) and po1.domainId=" + 
            domainId + " and ( po1.afficheChannelStatus is null or po1.afficheChannelStatus='0' )" + 
            " order by po1.channelIdString";
        } 
        Query query = this.session.createQuery(tmpSql);
        list = query.list();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getAllChannel_ByType(String channelType, String domainId, String type) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,  aaa.channelParentId,aaa.channelSort,aaa.channelIdString from  com.js.oa.info.channelmanager.po.InformationChannelPO aaa  where aaa.channelType = " + 

          
          channelType + 
          " and aaa.domainId=" + domainId + " and aaa.afficheChannelStatus='" + type + "'" + 
          " order by aaa.channelIdString ");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getISoCanIssue(String where) throws Exception {
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.isAllowReview from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where " + 
        
        where + " and (  aaa.afficheChannelStatus='2' ) " + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0"))
          channelNameString = "文档管理."; 
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + 
            "' like concat('%$', aaa.channelId, '$%') order by aaa.channelIdString ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
            
            obj[2] + "')>0 order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        list.set(i, obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getIsoCanIssue(String where, String domainId) throws Exception {
    List<Object[]> rList = new ArrayList();
    List<Object[]> list = null;
    begin();
    try {
      String hSql = "select aaa.channelId,aaa.channelName,aaa.channelIdString,aaa.channelType,aaa.userDefine,aaa.isAllowReview from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where (" + 
        
        where + ") and aaa.domainId=" + 
        domainId + " and ( aaa.afficheChannelStatus='2' )" + 
        " order by aaa.channelType,aaa.channelIdString";
      Query query = this.session.createQuery(hSql);
      list = query.list();
      List<String> tmpList = null;
      Iterator orgIter = null;
      for (int i = 0; i < list.size(); i++) {
        String channelNameString = "";
        Object[] obj = list.get(i);
        if (obj[3].toString().equals("0"))
          channelNameString = "文档管理."; 
        String tmpSql = "";
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + 
            "' like concat('%$', aaa.channelId, '$%') and aaa.domainId=" + 
            domainId + " order by aaa.channelIdString ";
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(aaa.channelId)), '$'),'" + 
            
            obj[2] + "')>0  and aaa.domainId=" + 
            domainId + " order by aaa.channelIdString ";
        } else {
          tmpSql = "select aaa.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where '" + 
            
            obj[2] + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(aaa.channelId)), '$%') and aaa.domainId=" + 
            domainId + " order by aaa.channelIdString ";
        } 
        query = this.session.createQuery(tmpSql);
        tmpList = query.list();
        for (int j = 0; j < tmpList.size(); j++)
          channelNameString = String.valueOf(channelNameString) + tmpList.get(j) + "."; 
        obj[2] = channelNameString.substring(0, 
            channelNameString.length() - 1);
        rList.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return rList;
  }
  
  public List getAllIsoChannel(String type) throws Exception {
    List list = null;
    begin();
    try {
      String hSql = 
        " select aaa.channelId,aaa.channelName,aaa.channelLevel,aaa.channelIdString,aaa.afficheChannelStatus   from com.js.oa.info.channelmanager.po.InformationChannelPO aaa ";
      hSql = String.valueOf(hSql) + " where aaa.afficheChannelStatus='2'";
      Query query = this.session.createQuery(hSql);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getBrotherCh_ByChannelStatusType(String channelId, String domainId, String channelStatusType) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.channelParentId,aaa.channelType from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelId = " + 
          channelId);
      List<Object[]> tmpList = query.list();
      Object[] obj = tmpList.get(0);
      String channelParentId = obj[0].toString();
      String channelType = obj[1].toString();
      query = this.session.createQuery("select aaa.channelId,aaa.channelName,aaa.channelSort from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelType = " + 
          channelType + 
          " and aaa.channelParentId = " + 
          channelParentId + 
          " and aaa.domainId=" + domainId + " and ( aaa.afficheChannelStatus='" + channelStatusType + "' ) " + 
          " order by aaa.channelSort  ");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Object[] deleteInformation(String channelId, String type) throws Exception {
    ArrayList<String> arrayList = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select aaa from com.js.oa.info.channelmanager.po.InformationChannelPO aaa where aaa.channelIdString like '%$" + 
          
          channelId + "$%'");
      List<InformationChannelPO> channelList = query.list();
      InformationAccessoryPO informationAccessoryPO = 
        new InformationAccessoryPO();
      InforHistoryAccessoryPO inforHistoryAccessoryPO = 
        new InforHistoryAccessoryPO();
      for (int i = 0; i < channelList.size(); i++) {
        InformationChannelPO channelPO = 
          channelList.get(i);
        Set infoSet = channelPO.getInformation();
        Iterator<InformationPO> iter = infoSet.iterator();
        channelPO.setInformation(null);
        while (iter.hasNext()) {
          InformationPO infoPO = iter.next();
          Set infoAcceSet = infoPO.getInformationAccessory();
          Set infoHistSet = infoPO.getInformationHistory();
          infoPO.setInformationAccessory(null);
          infoPO.setInformationHistory(null);
          Iterator<InformationAccessoryPO> infoAcceIter = infoAcceSet.iterator();
          while (infoAcceIter.hasNext()) {
            informationAccessoryPO = 
              infoAcceIter.next();
            arrayList.add(informationAccessoryPO
                .getAccessorySaveName());
          } 
          Iterator<InformationHistoryPO> infoHistIter = infoHistSet.iterator();
          while (infoHistIter.hasNext()) {
            InformationHistoryPO infoHistPO = 
              infoHistIter.next();
            Set infoHistAcceSet = infoHistPO
              .getInforHistoryAccessory();
            infoHistPO.setInforHistoryAccessory(null);
            Iterator<InforHistoryAccessoryPO> infoHistAcceIter = infoHistAcceSet.iterator();
            while (infoHistAcceIter.hasNext()) {
              inforHistoryAccessoryPO = 
                infoHistAcceIter.next();
              arrayList.add(inforHistoryAccessoryPO
                  .getAccessorySaveName());
              this.session.delete(inforHistoryAccessoryPO);
            } 
          } 
        } 
      } 
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      try {
        ResultSet rs = null;
        String tableId = "0";
        rs = stmt.executeQuery(
            "select wf_immoform_id from jsf_immobilityform where wf_module_id=50");
        if (rs.next())
          tableId = rs.getString(1); 
        rs.close();
        String sql1 = "delete from JSF_WORK where worktable_id=" + 
          tableId + " and workrecord_id in(select info.information_id from oa_information info,oa_informationchannel channel" + 
          " where info.channel_id=channel.channel_id and channel.channelneedcheckup=1 and" + 
          " CHANNELIDSTRING LIKE '%$" + channelId + "$%')";
        String sql2 = "delete from oa_information where channel_id in (select ch.channel_id from oa_informationchannel ch where ch.channelidstring like '%$" + 
          channelId + "$%')";
        String sql3 = 
          "DELETE from OA_INFORMATIONCHANNEL WHERE CHANNELIDSTRING LIKE '%$" + 
          channelId + "$%'";
        String deSql1 = "delete  from jsf_p_readwritecontrol where wf_proceedactivity_id  in (select wf_proceedactivity_id  from  jsf_p_activity  where  ttable_id=" + tableId + ")";
        String deSql2 = " delete   from  jsf_p_activity  where  ttable_id=" + tableId;
        stmt.execute("delete from jsf_work where worktable_id=" + 
            tableId + " and workrecord_id in(select info.information_id from oa_information info,oa_informationchannel channel" + 
            " where info.channel_id=channel.channel_id and channel.channelneedcheckup=1 and" + 
            " CHANNELIDSTRING LIKE '%$" + channelId + "$%')");
        stmt.execute(deSql1);
        stmt.execute(deSql2);
        stmt.execute("delete from oa_information where channel_id in (select ch.channel_id from oa_informationchannel ch where ch.channelidstring like '%$" + 
            channelId + "$%')");
        stmt.execute(
            "DELETE from OA_INFORMATIONCHANNEL WHERE CHANNELIDSTRING LIKE '%$" + 
            channelId + "$%'");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return arrayList.toArray();
  }
  
  public String channelNeedFlow(String channelId) throws Exception {
    String needFlow = "0";
    try {
      begin();
      needFlow = this.session.createQuery("select po.channelNeedCheckup from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelId=" + channelId).iterate().next().toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return needFlow;
  }
  
  public String getParticipationProId(String userId) throws Exception {
    String returnValueString = "";
    StringBuffer sBuffer = new StringBuffer();
    begin();
    try {
      Query query = this.session.createQuery("select distinct po.project.id from com.js.oa.relproject.po.RelProActorPO po where po.actorId=" + userId);
      Iterator<E> iterator = query.iterate();
      while (iterator.hasNext())
        sBuffer.append(iterator.next().toString()).append(","); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      returnValueString = sBuffer.toString();
      if (returnValueString.indexOf(",") > 0)
        returnValueString = returnValueString.substring(0, returnValueString.length() - 1); 
      this.session.close();
    } 
    return returnValueString;
  }
  
  public List getProBindListByUserId(String userId) {
    List bindList = new ArrayList();
    String hql = "select po.channelId,po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.relProjectId is not null and po.relProjectId <> '0' and po.channelReader like '%$" + userId + "$%'";
    try {
      begin();
      Query query = this.session.createQuery(hql);
      Iterator iterator = query.iterate();
      while (iterator.hasNext())
        bindList.add(iterator.next()); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (this.session != null)
          this.session.close(); 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
    return bindList;
  }
  
  public String[][] getChannelSimpleInfoByCorpId(String corpId, int flag) {
    String[][] info = { { "", "" }, { "", "" } };
    try {
      begin();
      List<Object[]> list = this.session.createQuery("select po.channelId,po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.corpId=" + corpId + " and po.publicType=0 and po.channelParentId=0").list();
      if (list != null && list.size() > 0) {
        Object[] temp = list.get(0);
        info[0][0] = temp[0].toString();
        info[0][1] = temp[1].toString();
      } else {
        String channelId = addCorpPublicChannel(corpId, 0);
        this.session.flush();
        info[0][0] = channelId;
        info[0][1] = "新闻";
      } 
      list = this.session.createQuery("select po.channelId,po.channelName from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.corpId=" + corpId + " and po.publicType=1 and po.channelParentId=0").list();
      if (list != null && list.size() > 0) {
        Object[] temp = list.get(0);
        info[1][0] = temp[0].toString();
        info[1][1] = temp[1].toString();
      } else {
        String channelId = addCorpPublicChannel(corpId, 1);
        info[1][0] = channelId;
        info[1][1] = "公告";
      } 
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return info;
  }
  
  private static synchronized String addCorpPublicChannel(String corpId, int flag) {
    String channelId = null;
    Session hsession = null;
    try {
      hsession = (new HibernateBase()).getSession();
      Iterator<E> it = hsession.createQuery("select po.channelId from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.corpId=" + corpId + " and po.publicType=" + flag).iterate();
      if (it.hasNext()) {
        channelId = it.next().toString();
      } else {
        String channelName;
        if (flag == 0) {
          channelName = "新闻";
        } else {
          channelName = "公告";
        } 
        InformationChannelPO po = new InformationChannelPO();
        po.setChannelName(channelName);
        po.setChannelType(1);
        po.setChannelParentId(new Long(0L));
        po.setChannelLevel(1);
        po.setChannelNeedCheckup(0);
        po.setChannelShowType(0);
        po.setChannelReader("");
        po.setChannelReaderGroup("");
        po.setChannelReaderName("");
        po.setChannelReaderOrg("");
        po.setChannelIssuer("");
        po.setChannelIssuerGroup("");
        po.setChannelIssuerName("");
        po.setChannelIssuerOrg("");
        po.setCreatedEmp(new Long(0L));
        po.setCreatedEmpName("admin");
        po.setCreatedOrg(new Long(0L));
        po.setOnDepaDesk(0);
        po.setIsRollOnDesktop(0);
        po.setChannelPosition(0);
        po.setPositionUpDown(1);
        po.setOnDepaDesk(0);
        po.setUserDefine("1");
        po.setInfoNum(Integer.valueOf(10));
        po.setDesktopType(new Integer(0));
        po.setIncludeChild(0);
        po.setDomainId(new Long(0L));
        po.setChannelManager("");
        po.setChannelManagerGroup("");
        po.setChannelManagerName("");
        po.setChannelManagerOrg("");
        po.setAfficheChannelStatus("0");
        po.setIsAllowReview("0");
        po.setRelProjectId(new Long(0L));
        po.setPublicType(new Integer(flag));
        po.setCorpId(new Long(corpId));
        channelId = ((Long)hsession.save(po)).toString();
        po.setChannelSort(500000);
        po.setChannelIdString("500000$" + channelId.toString() + "$_");
        hsession.flush();
      } 
      hsession.close();
    } catch (Exception ex) {
      if (hsession != null)
        try {
          hsession.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
    return channelId;
  }
  
  public List getChannelInfoList(String channelId, String curUserId) {
    String orgId = StaticParam.getOrgIdByEmpId(curUserId);
    String[] groupId = StaticParam.getGroupIdByEmpId(curUserId).split(",");
    String[] orgIds = StaticParam.getOrgIdsByOrgId(orgId).split(",");
    List list = null;
    String userIds = "po.channelReader like '%$" + curUserId + "$%' or po.channelManager like '%$" + curUserId + "$%' or po.channelIssuer like '%$" + curUserId + "$%'";
    String channelReaderOrg = " channelReaderOrg like '%*-1*%' ";
    String channelManagerOrg = " or channelManagerOrg like '%*-1*%' ";
    String channelIssuerOrg = " or channelIssuerOrg like '%*-1*%' ";
    for (int i = 1; i < orgIds.length; i++) {
      channelIssuerOrg = String.valueOf(channelIssuerOrg) + " or channelReaderOrg like '%*" + orgIds[i] + "*%' ";
      channelManagerOrg = String.valueOf(channelManagerOrg) + " or channelManagerOrg like '%*" + orgIds[i] + "*%' ";
      channelIssuerOrg = String.valueOf(channelIssuerOrg) + " or channelIssuerOrg like '%*" + orgIds[i] + "*%' ";
    } 
    String channelReaderGroup = "";
    String channelManagerGroup = "";
    String channelIssuerGroup = "";
    for (int j = 0; j < groupId.length; j++) {
      channelReaderGroup = String.valueOf(channelReaderGroup) + " or channelReaderGroup like '%@" + groupId[j] + "@%' ";
      channelManagerGroup = String.valueOf(channelManagerGroup) + " or channelManagerGroup like '%@" + groupId[j] + "@%' ";
      channelIssuerGroup = String.valueOf(channelIssuerGroup) + " or channelIssuerGroup like '%@" + groupId[j] + "@%' ";
    } 
    String hql = "select po.channelId,po.channelName,po.channelParentId from com.js.oa.info.channelmanager.po.InformationChannelPO po  where po.channelId=" + 
      channelId + " or (po.channelIdString like '%$" + channelId + "$%' and (" + userIds + " or " + channelReaderOrg + channelManagerOrg + 
      channelIssuerOrg + channelReaderGroup + channelManagerGroup + channelIssuerGroup + " or CONCAT(CONCAT(channelReader,channelReaderOrg),channelReaderGroup)='' " + 
      "or CONCAT(CONCAT(channelReader,channelReaderOrg),channelReaderGroup) is null)) order by po.channelIdString ";
    try {
      begin();
      list = this.session.createQuery(hql).list();
      this.session.close();
    } catch (Exception ex) {
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
      ex.printStackTrace();
    } 
    return list;
  }
}
