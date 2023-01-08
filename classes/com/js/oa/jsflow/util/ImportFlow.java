package com.js.oa.jsflow.util;

import com.js.oa.eform.po.TAreaPO;
import com.js.oa.eform.po.TAreaTypePO;
import com.js.oa.eform.po.TPagePO;
import com.js.oa.eform.service.DataBaseInfoBD;
import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.po.WFPackagePO;
import com.js.oa.jsflow.po.WFReadWriteControlPO;
import com.js.oa.jsflow.po.WFTransitionPO;
import com.js.oa.jsflow.po.WFTransitionRestrictionPO;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ZipOrUnZip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ImportFlow extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
    this.sessionContext = ctx;
  }
  
  String path = "";
  
  String fileName = "";
  
  public boolean importInfo(HttpServletRequest request) {
    boolean flag = true;
    String file = request.getParameter("fileName");
    HttpSession session = request.getSession();
    this.fileName = file.substring(0, file.indexOf("."));
    Calendar cal = Calendar.getInstance();
    String yearStr = String.valueOf(cal.get(1));
    this.path = session.getServletContext().getRealPath("/upload/" + yearStr + "/temp/");
    File myFilePath = new File(this.path);
    if (!myFilePath.exists())
      myFilePath.mkdir(); 
    try {
      ZipOrUnZip.UnZIP(String.valueOf(this.path) + "/" + request.getParameter("fileName"), String.valueOf(this.path) + "/" + this.fileName);
      File flowInfoFile = new File(String.valueOf(this.path) + "/" + this.fileName + "flow/");
      String[] filelist = flowInfoFile.list();
      for (int i = 0; i < filelist.length; i++) {
        List pageInfo = readPageFile(request, filelist[i], String.valueOf(this.path) + "/" + this.fileName + "flow/");
        List flowInfo = readFlowFile(request, filelist[i], String.valueOf(this.path) + "/" + this.fileName + "flow/");
        String[] pageIds = importPage(pageInfo, filelist[i]);
        importFlowInfo(flowInfo, pageIds, filelist[i]);
      } 
    } catch (Exception e) {
      flag = false;
      e.printStackTrace();
    } 
    deletefile(this.path, 0);
    return flag;
  }
  
  public List readPageFile(HttpServletRequest request, String fileName, String path) {
    List<List<String[]>> readPage = new ArrayList();
    FileInputStream configFileInputStream = null;
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curUserName = session.getAttribute("userName").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String domainId = session.getAttribute("domainId").toString();
    try {
      Tools tools = new Tools();
      String configFile = String.valueOf(path) + fileName + "/flowPage.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> node = root.getChildren("pageInfo");
      for (int j = 0; j < node.size(); j++) {
        List<String[]> pageInfoList = new ArrayList();
        Element pageInfo = node.get(j);
        String tPageField = "PAGE_ID,PAGE_NAME,PAGE_DES,PAGE_LIST,PAGE_REF,PAGE_PATH,PAGE_FILENAME,PAGE_OWNER,PAGE_DATE,PAGE_SESSION,PAGE_TYPE,DOMAIN_ID,PAGE_CONTENT,PRINT_PAGE_ID,CREATEDEMP,CREATEDORG,JS_ONLOAD,JS_BEFORECOMMIT,FORMCLASSNAME,FORMCLASSSAVEMETHOD,FORMCLASSUPDATEMETHOD";
        String[] fields = tPageField.split(",");
        Element showPage = pageInfo.getChild("showPage");
        String[] showPageStrings = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
          Element fieldNode = showPage.getChild(fields[i].toLowerCase());
          if ("page_owner".equalsIgnoreCase(fields[i].toLowerCase())) {
            showPageStrings[i] = curUserName;
          } else if ("createdemp".equalsIgnoreCase(fields[i].toLowerCase())) {
            showPageStrings[i] = curUserId;
          } else if ("createdorg".equalsIgnoreCase(fields[i].toLowerCase())) {
            showPageStrings[i] = curOrgId;
          } else if ("js_onload".equalsIgnoreCase(fields[i].toLowerCase()) || 
            "js_beforecommit".equalsIgnoreCase(fields[i].toLowerCase())) {
            showPageStrings[i] = tools.retrieveFlowSymbol(fieldNode.getText());
          } else {
            showPageStrings[i] = fieldNode.getText();
          } 
        } 
        pageInfoList.add(showPageStrings);
        Element printPage = pageInfo.getChild("printPage");
        String[] printPageStrings = new String[fields.length];
        for (int k = 0; k < fields.length; k++) {
          Element fieldNode = printPage.getChild(fields[k].toLowerCase());
          if ("page_owner".equalsIgnoreCase(fields[k].toLowerCase())) {
            printPageStrings[k] = curUserName;
          } else if ("createdemp".equalsIgnoreCase(fields[k].toLowerCase())) {
            printPageStrings[k] = curUserId;
          } else if ("createdorg".equalsIgnoreCase(fields[k].toLowerCase())) {
            printPageStrings[k] = curOrgId;
          } else {
            printPageStrings[k] = fieldNode.getText();
          } 
        } 
        pageInfoList.add(printPageStrings);
        String areaFields = "area_id,area_areatype,area_areatmc,area_page,area_name,area_table,area_show,area_sql,area_action,area_altercolor,area_pagecount,area_sfxs,area_ref,area_sqlevent,area_des,area_html,area_code,area_columns,AREATYPE_ID,PAGE_ID";
        String[] areafield = areaFields.split(",");
        Element areaInfos = pageInfo.getChild("areaInfos");
        List<Element> areaNode = areaInfos.getChildren("areaInfo");
        String[][] areaInfoStrings = new String[areaNode.size()][areafield.length];
        for (int x = 0; x < areaNode.size(); x++) {
          Element areaInfo = areaNode.get(x);
          for (int m = 0; m < areafield.length; m++) {
            if ("area_table".equalsIgnoreCase(areafield[m])) {
              String[][] tableInfo;
              DataBaseInfoBD dbInfo = new DataBaseInfoBD();
              if (SystemCommon.getMultiDepart() == 1) {
                tableInfo = dbInfo.getTableInfoByRange(domainId, curUserId, curOrgId);
              } else {
                tableInfo = dbInfo.getTableInfo(domainId);
              } 
              String areaTable = "", tempAreaTable = areaInfo.getChildText(areafield[m].toLowerCase());
              for (int n = 0; n < tableInfo.length; n++) {
                if (tableInfo[n][2].equals(tempAreaTable)) {
                  areaTable = tempAreaTable;
                  break;
                } 
              } 
              if ("".equals(areaTable))
                areaTable = tableInfo[0][2]; 
              areaInfoStrings[x][m] = areaTable;
            } else {
              areaInfoStrings[x][m] = areaInfo.getChildText(areafield[m].toLowerCase());
            } 
          } 
        } 
        pageInfoList.add((String[])areaInfoStrings);
        readPage.add(pageInfoList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return readPage;
  }
  
  public String[] importPage(List<List> pageInfo, String fileDir) throws Exception {
    String[] pageIds = new String[pageInfo.size()];
    DataSourceBase base = new DataSourceBase();
    for (int i = 0; i < pageInfo.size(); i++) {
      begin();
      List<String[]> page = pageInfo.get(i);
      long pageIdShow = 0L;
      long pageId = 0L;
      try {
        Map<Object, Object> idMap = new HashMap<Object, Object>();
        String[] showPage = page.get(0);
        TPagePO tpoShow = getPagePO(showPage, fileDir);
        pageIdShow = ((Long)this.session.save(tpoShow)).longValue();
        pageIds[i] = pageIdShow;
        idMap.put(showPage[0], Long.valueOf(pageIdShow));
        String[] printPage = page.get(1);
        TPagePO tpo = getPagePO(printPage, fileDir);
        pageId = ((Long)this.session.save(tpo)).longValue();
        idMap.put(printPage[0], Long.valueOf(pageId));
        this.session.flush();
        String[][] areaStrings = (String[][])page.get(2);
        for (int z = 0; z < areaStrings.length; z++) {
          TAreaPO areaPo = new TAreaPO();
          areaPo.setAreaTMC(tranString(areaStrings[z][2]));
          areaPo.setAreaName(tranString(areaStrings[z][4]));
          areaPo.setAreaTable(tranString(areaStrings[z][5]));
          areaPo.setAreaShow(tranString(areaStrings[z][6]));
          areaPo.setAreaSQL(tranString(areaStrings[z][7]));
          areaPo.setAreaAction(tranString(areaStrings[z][8]));
          areaPo.setAreaAlterColor(tranString(areaStrings[z][9]));
          areaPo.setAreaPageCount(("null".equalsIgnoreCase(areaStrings[z][10]) || "".equals(areaStrings[z][10])) ? 
              null : Long.valueOf(areaStrings[z][10]));
          areaPo.setAreaSFXS(("null".equalsIgnoreCase(areaStrings[z][11]) || "".equals(areaStrings[z][11])) ? 
              0 : Integer.valueOf(areaStrings[z][11]).intValue());
          areaPo.setAreaRef(tranString(areaStrings[z][12]));
          areaPo.setAreaSQLEvent(tranString(areaStrings[z][13]));
          areaPo.setAreaDes(tranString(areaStrings[z][14]));
          areaPo.setAreaColumns(("null".equalsIgnoreCase(areaStrings[z][17]) || "".equals(areaStrings[z][17])) ? 
              null : Long.valueOf(areaStrings[z][17]));
          TAreaTypePO areaType = (TAreaTypePO)this.session.load(TAreaTypePO.class, Long.valueOf(areaStrings[z][18]));
          areaPo.setAreatype(areaType);
          TPagePO tPage = (TPagePO)this.session.load(TPagePO.class, Long.valueOf(idMap.get(areaStrings[z][19]).toString()));
          areaPo.setTpage(tPage);
          this.session.save(areaPo);
        } 
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        this.session.close();
      } 
      try {
        String sql = "update tpage set PRINT_PAGE_ID=" + pageId + " where page_id=" + pageIdShow;
        base.begin();
        base.executeUpdate(sql);
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return pageIds;
  }
  
  public TPagePO getPagePO(String[] printPage, String fileDir) {
    TPagePO tpo = new TPagePO();
    tpo.setPageName("（导入流程）" + printPage[1]);
    tpo.setPageDes(tranString(printPage[2]));
    tpo.setPageList(tranString(printPage[3]));
    tpo.setPageRef(tranString(printPage[4]));
    tpo.setPagePath(tranString(printPage[5]));
    tpo.setPageFileName(tranString(printPage[6]));
    tpo.setPageOwner(tranString(printPage[7]));
    tpo.setPageDate(new Date());
    tpo.setPageSession(Integer.valueOf(printPage[9]).intValue());
    tpo.setPageType(Integer.valueOf(printPage[10]).intValue());
    tpo.setDomainId(0);
    String content = readTxtFile(String.valueOf(this.path) + "/" + this.fileName + "flow/" + fileDir + "/" + tranString(printPage[12]));
    tpo.setPageContent(content);
    tpo.setCreatedEmp(Long.valueOf(printPage[14]));
    tpo.setCreatedOrg(Long.valueOf(printPage[15]));
    tpo.setJsOnload(tranString(printPage[16]));
    tpo.setJsBeforeCommit(tranString(printPage[17]));
    tpo.setFormClassName(tranString(printPage[18]));
    tpo.setFormClassSaveMethod(tranString(printPage[19]));
    tpo.setFormClassUpdateMethod(tranString(printPage[20]));
    return tpo;
  }
  
  public List readFlowFile(HttpServletRequest request, String fileName, String path) {
    List<List> readFlow = new ArrayList<List>();
    FileInputStream configFileInputStream = null;
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curUserName = session.getAttribute("userName").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String rootName = StaticParam.getDeptName();
    String configFile = String.valueOf(path) + fileName + "/flowFlow.xml";
    try {
      Tools tools = new Tools();
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> node = root.getChildren("flowInfo");
      for (int j = 0; j < node.size(); j++) {
        List<Object> infoList = new ArrayList();
        Element flowInfo = node.get(j);
        Element flowProcess = flowInfo.getChild("flowProcess");
        String field = "wf_workflowprocess_id,accessdatabaseid,workflowprocessname,processcreateddate,processdescription,createusername,createdorg,createdemp,userscope,useorg,usegroup,useperson,dossierfileseescope,dossierfileseeorg,dossierfileseegroup,dossierfileseeperson,wf_package_id,processtype,remindfield,ispublish,isdossier,cancancel,formclassname,formclassmethod,formclasscompmethod,domain_id,printfileseescope,printfileseeorg,printfileseegroup,printfileseeperson,formtype,startjsp,optjsp,dossierfileoperscope,dossierfileoperorg,dossierfileopergroup,dossierfileoperperson,processadminscope,processadminorg,processadmingroup,processadminperson,ordercode,process_status,ownername,ownerid,ownerorgid,lastupdatetime,processusetime,main_formid";
        String[] fields = field.split(",");
        String[] processInfo = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
          if ("createusername".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserName;
          } else if ("createdorg".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curOrgId;
          } else if ("createdemp".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserId;
          } else if ("userscope".equalsIgnoreCase(fields[i])) {
            processInfo[i] = rootName;
          } else if ("useorg".equalsIgnoreCase(fields[i])) {
            processInfo[i] = "*-1*";
          } else if ("processadminscope".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserName;
          } else if ("processadminperson".equalsIgnoreCase(fields[i])) {
            processInfo[i] = "$" + curUserId + "$";
          } else if ("dossierfileseescope".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserName;
          } else if ("dossierfileseeperson".equalsIgnoreCase(fields[i])) {
            processInfo[i] = "$" + curUserId + "$";
          } else if ("dossierfileoperscope".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserName;
          } else if ("dossierfileoperperson".equalsIgnoreCase(fields[i])) {
            processInfo[i] = "$" + curUserId + "$";
          } else if ("ownername".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserName;
          } else if ("ownerid".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curUserId;
          } else if ("ownerorgid".equalsIgnoreCase(fields[i])) {
            processInfo[i] = curOrgId;
          } else if ("wf_package_id".equalsIgnoreCase(fields[i])) {
            processInfo[i] = getPackageId();
          } else {
            processInfo[i] = tools.retrieveFlowSymbol(flowProcess.getChildText(fields[i]));
          } 
        } 
        infoList.add(processInfo);
        Element flowActivitys = flowInfo.getChild("flowActivitys");
        List<Element> activityNode = flowActivitys.getChildren("flowActivity");
        String activity = "wf_activity_id,activityname,activitydescription,activitytype,allowstandfor,allowcancel,allowtransition,participanttype,participantuser,participantgroup,participantusername,participantuserfield,presstype,deadlinetime,pressmotiontime,activitydocumentation,activityicon,activitybeginend,wf_workflowprocess_id,transacttype,acticommtype,acticommfield,needpassround,passroundusertype,passrounduser,passroundusergroup,passroundusername,passrounduserfield,passroundcommfield,participantrole,passroundrole,activityclass,activitysubproc,subproctype,formclassmethod,untreadmethod,participantgivenorgname,participantgivenorg,passroundgivenorgname,passroundgivenorg,commentrange,commentrangename,domain_id,form_id,printnum,operbutton,acticommfieldtype,passroundcommfieldtype,printfileseescope,printfileseeorg,printfileseegroup,printfileseeperson,allowsmsremind,trantype,trancustomextent,trancustomextentid,pressdealtype,tranreadtype,tranreadcustomextent,tranreadcustomextentid,extendmaintable,positionleft,positiontop,nicknum,main_formid,exe_script";
        fields = activity.split(",");
        String[][] activityInfo = new String[activityNode.size()][fields.length];
        for (int x = 0; x < activityNode.size(); x++) {
          Element flowActivity = activityNode.get(x);
          for (int y = 0; y < fields.length; y++) {
            if ("participantgivenorgname".equalsIgnoreCase(fields[y]) || "passroundgivenorgname".equalsIgnoreCase(fields[y]) || 
              "commentrangename".equalsIgnoreCase(fields[y])) {
              activityInfo[x][y] = "请点击选择";
            } else if ("participantgivenorg".equalsIgnoreCase(fields[y]) || "passroundgivenorg".equalsIgnoreCase(fields[y]) || 
              "commentrange".equalsIgnoreCase(fields[y])) {
              activityInfo[x][y] = "";
            } else {
              activityInfo[x][y] = tools.retrieveFlowSymbol(flowActivity.getChildText(fields[y]));
            } 
          } 
        } 
        infoList.add(activityInfo);
        Element flowControls = flowInfo.getChild("flowControls");
        List<Element> controlNode = flowControls.getChildren("flowControl");
        String control = "wf_readwritecontrol_id,controltype,controlfield,wf_activity_id,domain_id";
        fields = control.split(",");
        String[][] controlInfo = new String[controlNode.size()][fields.length];
        for (int k = 0; k < controlNode.size(); k++) {
          Element flowControl = controlNode.get(k);
          for (int y = 0; y < fields.length; y++)
            controlInfo[k][y] = tools.retrieveFlowSymbol(flowControl.getChildText(fields[y])); 
        } 
        infoList.add(controlInfo);
        Element flowTransitions = flowInfo.getChild("flowTransitions");
        List<Element> transitionNode = flowTransitions.getChildren("flowTransition");
        String transition = "wf_transition_id,transitionfrom,transitionname,transitionto,transitiondescription,domain_id,expression,defaultactivity,linetype,nicknum";
        fields = transition.split(",");
        String[][] transitionInfo = new String[transitionNode.size()][fields.length];
        for (int m = 0; m < transitionNode.size(); m++) {
          Element flowTransition = transitionNode.get(m);
          for (int y = 0; y < fields.length; y++)
            transitionInfo[m][y] = tools.retrieveFlowSymbol(flowTransition.getChildText(fields[y])); 
        } 
        infoList.add(transitionInfo);
        Element flowRestrictions = flowInfo.getChild("flowRestrictions");
        List<Element> restrictionNode = flowRestrictions.getChildren("flowRestriction");
        String restriction = "wf_transitionrestriction_id,conditionfield,comparevalue,relation,wf_transition_id,domain_id";
        fields = restriction.split(",");
        String[][] restrictionInfo = new String[restrictionNode.size()][fields.length];
        for (int n = 0; n < restrictionNode.size(); n++) {
          Element flowRestriction = restrictionNode.get(n);
          for (int y = 0; y < fields.length; y++)
            restrictionInfo[n][y] = tools.retrieveFlowSymbol(flowRestriction.getChildText(fields[y])); 
        } 
        infoList.add(restrictionInfo);
        readFlow.add(infoList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return readFlow;
  }
  
  public void importFlowInfo(List<List> flow, String[] pageIds, String fileDir) throws Exception {
    Map<Object, Object> idMap = new HashMap<Object, Object>();
    String[][] position = (String[][])null;
    for (int i = 0; i < flow.size(); i++) {
      List<String[]> flowList = flow.get(i);
      begin();
      try {
        String[] processInfo = flowList.get(0);
        WFWorkFlowProcessPO wfp = new WFWorkFlowProcessPO();
        wfp.setAccessDatabaseId(Long.valueOf(pageIds[i]));
        wfp.setWorkFlowProcessName("(导入流程)" + tranString(processInfo[2]));
        wfp.setProcessCreatedDate(new Date());
        String content = readTxtFile(String.valueOf(this.path) + "/" + this.fileName + "flow/" + fileDir + "/" + tranString(processInfo[4]));
        wfp.setProcessDescription(content);
        wfp.setCreateUserName(tranString(processInfo[5]));
        wfp.setCreatedOrg(Long.valueOf(processInfo[6]));
        wfp.setCreatedEmp(Long.valueOf(processInfo[7]));
        wfp.setUserScope(tranString(processInfo[8]));
        wfp.setUseOrg(tranString(processInfo[9]));
        wfp.setUseGroup(tranString(processInfo[10]));
        wfp.setUsePerson(tranString(processInfo[11]));
        wfp.setDossierFileSeeScope(tranString(processInfo[12]));
        wfp.setDossierFileSeeOrg(tranString(processInfo[13]));
        wfp.setDossierFileSeeGroup(tranString(processInfo[14]));
        wfp.setDossierFileSeePerson(tranString(processInfo[15]));
        WFPackagePO wfPackage = (WFPackagePO)this.session.load(WFPackagePO.class, Long.valueOf(processInfo[16]));
        wfp.setWfPackage(wfPackage);
        wfp.setProcessType(Integer.valueOf(processInfo[17]).intValue());
        wfp.setRemindField(tranString(processInfo[18]));
        wfp.setIsPublish("null".equalsIgnoreCase(processInfo[19]) ? null : Integer.valueOf(processInfo[19]));
        wfp.setIsDossier("null".equalsIgnoreCase(processInfo[20]) ? null : Integer.valueOf(processInfo[20]));
        wfp.setCanCancel("null".equalsIgnoreCase(processInfo[21]) ? null : Integer.valueOf(processInfo[21]));
        wfp.setFormClassName(tranString(processInfo[22]));
        wfp.setFormClassMethod(tranString(processInfo[23]));
        wfp.setFormClassCompMethod(tranString(processInfo[24]));
        wfp.setDomainId("0");
        wfp.setPrintFileSeeScope(tranString(processInfo[26]));
        wfp.setPrintFileSeeOrg(tranString(processInfo[27]));
        wfp.setPrintFileSeeGroup(tranString(processInfo[28]));
        wfp.setPrintFileSeePerson(tranString(processInfo[29]));
        wfp.setFormType(Integer.valueOf(processInfo[30]).intValue());
        wfp.setStartJSP(tranString(processInfo[31]));
        wfp.setOptJSP(tranString(processInfo[32]));
        wfp.setDossierFileOperScope(tranString(processInfo[33]));
        wfp.setDossierFileOperOrg(tranString(processInfo[34]));
        wfp.setDossierFileOperGroup(tranString(processInfo[35]));
        wfp.setDossierFileOperPerson(tranString(processInfo[36]));
        wfp.setProcessAdminScope(tranString(processInfo[37]));
        wfp.setProcessAdminOrg(tranString(processInfo[38]));
        wfp.setProcessAdminGroup(tranString(processInfo[38]));
        wfp.setProcessAdminPerson(tranString(processInfo[40]));
        wfp.setOrderCode(Integer.valueOf(processInfo[41]).intValue());
        wfp.setProcessStatus(Integer.valueOf(processInfo[42]).intValue());
        wfp.setOwnerName(tranString(processInfo[43]));
        wfp.setOwnerId(Long.valueOf(processInfo[44]));
        wfp.setOwnerOrgId(Long.valueOf(processInfo[45]));
        wfp.setLastUpdateTime(new Date());
        wfp.setProcessUseTime(new Date());
        if (processInfo[1].equals(processInfo[48])) {
          wfp.setMainFormId(Long.valueOf(pageIds[i]));
        } else {
          wfp.setMainFormId("null".equalsIgnoreCase(processInfo[48]) ? null : Long.valueOf(0L));
        } 
        long processId = ((Long)this.session.save(wfp)).longValue();
        this.session.flush();
        String[][] actInfo = (String[][])flowList.get(1);
        position = new String[actInfo.length][3];
        for (int x = 0; x < actInfo.length; x++) {
          WFActivityPO wf = new WFActivityPO();
          wf.setActivityName(tranString(actInfo[x][1]));
          wf.setActivityDescription(tranString(actInfo[x][2]));
          wf.setActivityType(Integer.valueOf(actInfo[x][3]).intValue());
          wf.setAllowStandFor(Integer.valueOf(actInfo[x][4]).intValue());
          wf.setAllowCancel(Integer.valueOf(actInfo[x][5]).intValue());
          wf.setAllowTransition(Integer.valueOf(actInfo[x][6]).intValue());
          wf.setParticipantType(Integer.valueOf(actInfo[x][7]).intValue());
          wf.setParticipantUser(tranString(actInfo[x][8]));
          wf.setParticipantGroup(tranString(actInfo[x][9]));
          wf.setParticipantUserName(tranString(actInfo[x][10]));
          wf.setParticipantUserField(tranString(actInfo[x][11]));
          wf.setPressType(Integer.valueOf(actInfo[x][12]).intValue());
          wf.setDeadLineTime(Integer.valueOf(actInfo[x][13]).intValue());
          wf.setPressMotionTime(Integer.valueOf(actInfo[x][14]).intValue());
          wf.setActivityDocumentionation(tranString(actInfo[x][15]));
          wf.setActivityIcon(tranString(actInfo[x][16]));
          wf.setActivityBeginEnd(Integer.valueOf(actInfo[x][17]).intValue());
          WFWorkFlowProcessPO wfWorkFlowProcess = (WFWorkFlowProcessPO)this.session.load(WFWorkFlowProcessPO.class, Long.valueOf(processId));
          wf.setWfWorkFlowProcess(wfWorkFlowProcess);
          wf.setTransactType(tranString(actInfo[x][19]));
          wf.setActiCommType(Integer.valueOf(actInfo[x][20]).intValue());
          wf.setActiCommField(tranString(actInfo[x][21]));
          wf.setNeedPassRound(Integer.valueOf(actInfo[x][22]).intValue());
          wf.setPassRoundUserType(Integer.valueOf(actInfo[x][23]).intValue());
          wf.setPassRoundUser(tranString(actInfo[x][24]));
          wf.setPassRoundUserGroup(tranString(actInfo[x][25]));
          wf.setPassRoundUserName(tranString(actInfo[x][26]));
          wf.setPassRoundUserField(tranString(actInfo[x][27]));
          wf.setPassRoundCommField(tranString(actInfo[x][28]));
          wf.setParticipantRole(tranString(actInfo[x][29]));
          wf.setPassRoundRole(tranString(actInfo[x][30]));
          wf.setActivityClass(Integer.valueOf(actInfo[x][31]).intValue());
          wf.setActivitySubProc((tranString(actInfo[x][32]) == null || "".equals(tranString(actInfo[x][32])) || "null".equalsIgnoreCase(tranString(actInfo[x][32]))) ? "0" : tranString(actInfo[x][32]));
          wf.setSubProcType(Integer.valueOf(actInfo[x][33]).intValue());
          wf.setFormClassMethod(tranString(actInfo[x][34]));
          wf.setUntreadMethod(tranString(actInfo[x][35]));
          wf.setParticipantGivenOrgName(tranString(actInfo[x][36]));
          wf.setParticipantGivenOrg(tranString(actInfo[x][37]));
          wf.setPassRoundGivenOrgName(tranString(actInfo[x][38]));
          wf.setPassRoundGivenOrg(tranString(actInfo[x][39]));
          wf.setCommentRange(tranString(actInfo[x][40]));
          wf.setCommentRangeName(tranString(actInfo[x][41]));
          wf.setDomainId("0");
          wf.setFormId(pageIds[i]);
          wf.setOperButton(tranString(actInfo[x][45]));
          wf.setActiCommFieldType(tranString(actInfo[x][46]));
          wf.setPassRoundCommFieldType(tranString(actInfo[x][48]));
          wf.setAllowSmsRemind((tranString(actInfo[x][52]) == null || "".equals(tranString(actInfo[x][52])) || "null".equalsIgnoreCase(tranString(actInfo[x][52]))) ? "0" : tranString(actInfo[x][52]));
          wf.setTranType((tranString(actInfo[x][53]) == null || "".equals(tranString(actInfo[x][53])) || "null".equalsIgnoreCase(tranString(actInfo[x][53]))) ? "0" : tranString(actInfo[x][53]));
          wf.setTranCustomExtent(tranString(actInfo[x][54]));
          wf.setTranCustomExtentId(tranString(actInfo[x][55]));
          wf.setPressDealType((tranString(actInfo[x][56]) == null || "".equals(tranString(actInfo[x][56])) || "null".equalsIgnoreCase(tranString(actInfo[x][56]))) ? "0" : tranString(actInfo[x][56]));
          wf.setTranReadType((tranString(actInfo[x][57]) == null || "".equals(tranString(actInfo[x][57])) || "null".equalsIgnoreCase(tranString(actInfo[x][57]))) ? "0" : tranString(actInfo[x][57]));
          wf.setTranReadCustomExtent(tranString(actInfo[x][58]));
          wf.setTranReadCustomExtentId(tranString(actInfo[x][59]));
          wf.setExtendMainTable((tranString(actInfo[x][60]) == null || "".equals(tranString(actInfo[x][60])) || "null".equalsIgnoreCase(tranString(actInfo[x][60]))) ? "0" : tranString(actInfo[x][60]));
          wf.setMainFormId((actInfo[x][64] == null || "".equals(actInfo[x][64]) || "null".equalsIgnoreCase(actInfo[x][64])) ? null : Long.valueOf(actInfo[x][64]));
          wf.setExeScript(tranString(actInfo[x][65]));
          position[x][1] = actInfo[x][61];
          position[x][2] = actInfo[x][62];
          long actId = ((Long)this.session.save(wf)).longValue();
          position[x][0] = (new StringBuilder(String.valueOf(actId))).toString();
          idMap.put(actInfo[x][0], (new StringBuilder(String.valueOf(actId))).toString());
        } 
        this.session.flush();
        String[][] flowControl = (String[][])flowList.get(2);
        for (int t = 0; t < flowControl.length; t++) {
          WFReadWriteControlPO wfrw = new WFReadWriteControlPO();
          wfrw.setControlType(Integer.valueOf(flowControl[t][1]).intValue());
          wfrw.setControlField(Long.valueOf(flowControl[t][2]));
          WFActivityPO wfActivity = (WFActivityPO)this.session.load(WFActivityPO.class, Long.valueOf(idMap.get(flowControl[t][3]).toString()));
          wfrw.setWfActivity(wfActivity);
          wfrw.setDomainId("0");
          this.session.save(wfrw);
        } 
        this.session.flush();
        Map<Object, Object> tranIdMap = new HashMap<Object, Object>();
        String[][] tranString = (String[][])flowList.get(3);
        for (int j = 0; j < tranString.length; j++) {
          WFTransitionPO wftp = new WFTransitionPO();
          WFActivityPO wffrom = (WFActivityPO)this.session.load(WFActivityPO.class, Long.valueOf(idMap.get(tranString[j][1]).toString()));
          wftp.setTransitionFrom(wffrom);
          wftp.setTransitionName(tranString(tranString[j][2]));
          WFActivityPO wfto = (WFActivityPO)this.session.load(WFActivityPO.class, Long.valueOf(idMap.get(tranString[j][3]).toString()));
          wftp.setTransitionTo(wfto);
          wftp.setTransitionDescription(tranString(tranString[j][4]));
          wftp.setDomainId("0");
          wftp.setExpression(tranString(tranString[j][6]));
          wftp.setDefaultActivity(("null".equalsIgnoreCase(tranString[j][7]) || idMap.get(tranString[j][7]) == null) ? null : 
              idMap.get(tranString[j][7]).toString());
          long tranId = ((Long)this.session.save(wftp)).longValue();
          tranIdMap.put(tranString[j][0], (new StringBuilder(String.valueOf(tranId))).toString());
        } 
        this.session.flush();
        String[][] flowRes = (String[][])flowList.get(4);
        for (int k = 0; k < flowRes.length; k++) {
          WFTransitionRestrictionPO wfr = new WFTransitionRestrictionPO();
          wfr.setConditionField("null".equalsIgnoreCase(flowRes[k][1]) ? null : Long.valueOf(flowRes[k][1]));
          wfr.setCompareValue(tranString(flowRes[k][2]));
          wfr.setRelation(tranString(flowRes[k][3]));
          WFTransitionPO wfTransition = (WFTransitionPO)this.session.load(WFTransitionPO.class, Long.valueOf(tranIdMap.get(flowRes[k][4]).toString()));
          wfr.setWfTransition(wfTransition);
          wfr.setDomainId("0");
          this.session.save(wfr);
        } 
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        this.session.close();
      } 
      DataSourceBase base = new DataSourceBase();
      try {
        String sql = "";
        base.begin();
        for (int z = 0; z < position.length; z++) {
          sql = "update JSF_ACTIVITY set positionleft=" + position[z][1] + ",positiontop=" + position[z][2] + 
            " where wf_activity_id=" + position[z][0];
          base.addBatch(sql);
        } 
        base.executeBatch();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
  }
  
  public String tranString(String tran) {
    if ("null".equalsIgnoreCase(tran))
      return null; 
    return tran;
  }
  
  public String getPackageId() {
    String id = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "SELECT WF_PACKAGE_ID FROM jsf_package WHERE WF_MODULE_ID=1";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next())
        id = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return id;
  }
  
  public String readTxtFile(String filePath) {
    String lineTxt = "";
    String txt = "";
    try {
      String encoding = "GBK";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(
            new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        while ((lineTxt = bufferedReader.readLine()) != null)
          txt = String.valueOf(txt) + lineTxt + "\n"; 
        read.close();
      } else {
        System.out.println("找不到指定的文件" + filePath);
      } 
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    } 
    return txt;
  }
  
  public boolean deletefile(String delpath, int flag) {
    boolean isDelete = false;
    try {
      File file = new File(delpath);
      if (!file.exists()) {
        System.out.println("路径" + delpath + "不存在！");
      } else {
        if (!file.isDirectory()) {
          System.out.println("路径" + delpath + "不是文件夹！");
          file.delete();
        } else if (file.isDirectory()) {
          String[] filelist = file.list();
          for (int i = 0; i < filelist.length; i++) {
            File delfile = new File(String.valueOf(delpath) + "/" + filelist[i]);
            if (!delfile.isDirectory()) {
              delfile.delete();
            } else if (delfile.isDirectory()) {
              deletefile(String.valueOf(delpath) + "/" + filelist[i], 1);
            } 
          } 
          if (flag > 0)
            file.delete(); 
        } 
        isDelete = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return isDelete;
  }
}
