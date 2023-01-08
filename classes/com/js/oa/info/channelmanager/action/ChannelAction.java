package com.js.oa.info.channelmanager.action;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.util.ChannelCache;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChannelAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ChannelActionForm channelActionForm = (ChannelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String tag = "view";
    String action = httpServletRequest.getParameter("action");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String corpId = httpSession.getAttribute("corpId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String channelType = httpServletRequest.getParameter("channelType");
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    String isAffiche = (httpServletRequest.getParameter("isAffiche") == null) ? 
      "" : 
      httpServletRequest.getParameter("isAffiche")
      .toString();
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    String depart = httpServletRequest.getParameter("depart");
    httpServletRequest.setAttribute("depart", depart);
    httpServletRequest.setAttribute("defaultDepa", 
        httpServletRequest
        .getParameter("defaultDepa"));
    String styleId = "";
    if (depart != null) {
      styleId = httpServletRequest.getParameter("styleId");
      httpServletRequest.setAttribute("styleId", styleId);
      httpServletRequest.setAttribute("stylePO", (
          new ChannelBD())
          .getDepaStyle(styleId));
    } 
    if (action.equals("view")) {
      if (SystemCommon.getMultiDepart() == 1)
        (new ChannelBD()).getChannelSimpleInfoByCorpId(httpSession.getAttribute("corpId").toString(), 1); 
      String userDefine = (httpServletRequest.getParameter("userDefine") == null) ? "0" : httpServletRequest.getParameter("userDefine");
      ManagerService managerBD = new ManagerService();
      if (channelType.equals("0") || userDefine.equals("1")) {
        if (managerBD.hasRight(userId, "01*02*01")) {
          httpServletRequest.setAttribute("addRight", "1");
        } else {
          httpServletRequest.setAttribute("addRight", "0");
        } 
      } else if (managerBD.hasRight(userId, "01*01*02")) {
        httpServletRequest.setAttribute("addRight", "1");
      } else {
        httpServletRequest.setAttribute("addRight", "0");
      } 
      if (depart != null) {
        tag = "viewdepart";
      } else if (httpServletRequest.getParameter("defaultDepa") != null) {
        tag = "defaultDepa";
      } else {
        tag = "view";
      } 
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(4);
      if (httpServletRequest.getParameter("isAffiche") != null && httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
        tag = "afficheLiu";
        moduleVO.setId(51);
      } 
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (new WorkFlowBD()).getAccessTable(moduleVO).get(0));
      httpServletRequest.setAttribute("flag", httpServletRequest.getParameter("flag"));
      view(httpServletRequest, channelType, 0, depart, styleId, domainId);
    } else if (action.equals("search")) {
      httpServletRequest.setAttribute("flag", httpServletRequest.getParameter("flag"));
      if (depart != null) {
        tag = "viewdepart";
      } else if (httpServletRequest.getParameter("defaultDepa") != null) {
        tag = "defaultDepa";
      } else {
        tag = "view";
      } 
      String searchChannelType = httpServletRequest.getParameter(
          "searchChannelType");
      String searchChannelName = httpServletRequest.getParameter(
          "searchChannelName").trim();
      String searchOnDesktop = httpServletRequest.getParameter(
          "searchOnDesktop");
      String viewSql = 
        "aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,aaa.channelNeedCheckup,aaa.createdOrg,aaa.createdEmp,aaa.createdEmpName,aaa.onDesktop,aaa.isRollOnDesktop,aaa.onDepaDesk,bbb.processId,aaa.channelIssuerName,aaa.isAllowReview";
      String fromSql = 
        "com.js.oa.info.channelmanager.po.InformationChannelPO aaa left outer join aaa.channelProcess bbb";
      String whereSql = "where aaa.channelType = " + channelType;
      whereSql = String.valueOf(whereSql) + " and aaa.channelName like '%" + 
        searchChannelName + "%'";
      if (searchOnDesktop != null && !searchOnDesktop.equals("-1"))
        whereSql = String.valueOf(whereSql) + " and aaa.onDesktop = " + searchOnDesktop; 
      if (httpServletRequest.getParameter("isAffiche") != null && 
        httpServletRequest.getParameter("isAffiche").toString().equals(
          "1")) {
        whereSql = String.valueOf(whereSql) + " and aaa.afficheChannelStatus = '1' ";
      } else {
        whereSql = String.valueOf(whereSql) + 
          " and ( aaa.afficheChannelStatus is null or aaa.afficheChannelStatus='0' )";
      } 
      if (SystemCommon.getMultiDepart() == 1)
        whereSql = String.valueOf(whereSql) + " and aaa.corpId=" + corpId; 
      whereSql = String.valueOf(whereSql) + " and aaa.domainId=" + domainId + 
        " order by aaa.channelIdString ";
      list(httpServletRequest, viewSql, fromSql, whereSql, channelType, 0, 
          depart, styleId);
      channelActionForm.setSearchChannelName(searchChannelName);
      channelActionForm.setSearchChannelType(searchChannelType);
      httpServletRequest.setAttribute("channelType", searchChannelType);
      String userDefine = (httpServletRequest.getParameter("userDefine") == null) ? 
        "0" : 
        httpServletRequest.getParameter("userDefine");
      ManagerService managerBD = new ManagerService();
      if (channelType.equals("0") || userDefine.equals("1")) {
        if (managerBD.hasRight(userId, "01*02*01")) {
          httpServletRequest.setAttribute("addRight", "1");
        } else {
          httpServletRequest.setAttribute("addRight", "0");
        } 
      } else if (managerBD.hasRight(userId, "01*01*02")) {
        httpServletRequest.setAttribute("addRight", "1");
      } else {
        httpServletRequest.setAttribute("addRight", "0");
      } 
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(4);
      if (isAffiche.equals("1"))
        moduleVO.setId(51); 
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (
          new WorkFlowBD())
          .getAccessTable(moduleVO).get(0));
    } else if (action.equals("add") || action.equals("modify")) {
      List<Object[]> list;
      httpServletRequest.setAttribute("channelType", channelType);
      ChannelBD channelBD = new ChannelBD();
      if (SystemCommon.getMultiDepart() == 1) {
        list = channelBD.getAllChannel(channelType, domainId, corpId);
      } else {
        list = channelBD.getAllChannel(channelType, domainId, "");
      } 
      httpServletRequest.setAttribute("allChannelList", list);
      int onDesktop = 0;
      if ((Long.parseLong(channelType) == 0L || 
        "1".equals(httpServletRequest.getParameter("userDefine"))) && 
        channelBD.canOnDesktop())
        onDesktop = 1; 
      httpServletRequest.setAttribute("canOnDesktop", (new StringBuilder(String.valueOf(onDesktop))).toString());
      if (Long.parseLong(channelType) > 0L && 
        "0".equals(httpServletRequest.getParameter("userDefine")))
        httpServletRequest.setAttribute("canOnDepaDesk", new Boolean(true)); 
      httpServletRequest.setAttribute("channelPositionList", 
          channelBD.getChannelPosition());
      if (action.equals("add")) {
        tag = "add";
        if (httpServletRequest.getParameter("isAffiche") != null && 
          httpServletRequest.getParameter("isAffiche").toString()
          .equals("1"))
          tag = "afficheadd"; 
        httpServletRequest.setAttribute("allSortList", list);
      } else {
        tag = "modify";
        if (httpServletRequest.getParameter("isAffiche") != null && 
          httpServletRequest.getParameter("isAffiche").toString()
          .equals("1"))
          tag = "affichemodify"; 
        String channelId = httpServletRequest.getParameter("channelId");
        httpServletRequest.setAttribute("pager.offset", 
            httpServletRequest.getParameter(
              "pager.offset"));
        list = channelBD.getBrotherCh(channelId, 
            session.getAttribute("domainId")
            .toString());
        httpServletRequest.setAttribute("sortList", list);
        String[] sort = channelBD.getSortChannel(channelId, channelType);
        httpServletRequest.setAttribute("sortType", sort[0]);
        httpServletRequest.setAttribute("sortChannelId", sort[1]);
        list = channelBD.getSingleChannel(channelId);
        Object[] obj = list.get(0);
        httpServletRequest.setAttribute("channelId", channelId);
        channelActionForm.setChannelName(obj[0].toString());
        httpServletRequest.setAttribute("channelName", obj[0]);
        String channelIssuerName = "";
        if (obj[10] != null)
          channelIssuerName = obj[10].toString(); 
        channelActionForm.setChannelIssuerName(channelIssuerName);
        String channelReaderName = "";
        if (obj[11] != null)
          channelReaderName = obj[11].toString(); 
        channelActionForm.setChannelReaderName(channelReaderName);
        httpServletRequest.setAttribute("channelType", obj[1]);
        httpServletRequest.setAttribute("channelParentId", obj[2]);
        httpServletRequest.setAttribute("channelNeedCheckup", obj[3]);
        httpServletRequest.setAttribute("channelSort", obj[12]);
        httpServletRequest.setAttribute("channelIdString", obj[13]);
        httpServletRequest.setAttribute("includeChild", obj[22]);
        String managerId = "";
        if (obj[23] != null)
          managerId = String.valueOf(managerId) + obj[23].toString(); 
        if (obj[24] != null)
          managerId = String.valueOf(managerId) + obj[24].toString(); 
        if (obj[25] != null)
          managerId = String.valueOf(managerId) + obj[25].toString(); 
        httpServletRequest.setAttribute("managerId", managerId);
        if (obj[26] != null) {
          channelActionForm.setChannelManagerName(obj[26].toString());
        } else {
          channelActionForm.setChannelManagerName("");
        } 
        String readerId = "";
        if (obj[4] != null)
          readerId = String.valueOf(readerId) + obj[4].toString(); 
        if (obj[5] != null)
          readerId = String.valueOf(readerId) + obj[5].toString(); 
        if (obj[6] != null)
          readerId = String.valueOf(readerId) + obj[6].toString(); 
        httpServletRequest.setAttribute("readerId", readerId);
        String issuerId = "";
        if (obj[7] != null)
          issuerId = String.valueOf(issuerId) + obj[7].toString(); 
        if (obj[8] != null)
          issuerId = String.valueOf(issuerId) + obj[8].toString(); 
        if (obj[9] != null)
          issuerId = String.valueOf(issuerId) + obj[9].toString(); 
        httpServletRequest.setAttribute("issuerId", issuerId);
        httpServletRequest.setAttribute("channelShowType", obj[14]);
        httpServletRequest.setAttribute("onDesktop", obj[15]);
        httpServletRequest.setAttribute("isRollOnDesktop", obj[16]);
        httpServletRequest.setAttribute("channelPoistion", obj[17]);
        httpServletRequest.setAttribute("positionUpDown", obj[18]);
        httpServletRequest.setAttribute("onDepaDesk", obj[19]);
        httpServletRequest.setAttribute("infoNum", obj[20]);
        httpServletRequest.setAttribute("desktopType", obj[21]);
        httpServletRequest.setAttribute("isAllowReview", obj[28]);
      } 
    } else if (action.equals("continue") || action.equals("close")) {
      Date beginDate = new Date();
      String includeChild = channelActionForm.getIncludeChild();
      String afficheChannelStauts = "0";
      InformationChannelPO informationChannelPO = new InformationChannelPO();
      String channelName = channelActionForm.getChannelName();
      if (httpServletRequest.getParameter("isAffiche") != null && 
        httpServletRequest.getParameter("isAffiche").toString().equals(
          "1"))
        afficheChannelStauts = "1"; 
      channelType = channelActionForm.getChannelType();
      String channelParentId = channelActionForm.getChannelParentId();
      String channelNeedCheckup = httpServletRequest.getParameter(
          "channelNeedCheckup");
      String channelOrderId = httpServletRequest.getParameter(
          "channelOrderId");
      String radiobutton = httpServletRequest.getParameter("radiobutton");
      if (channelNeedCheckup == null)
        channelNeedCheckup = "0"; 
      String isAllowReview = httpServletRequest.getParameter("isAllowReview");
      if (isAllowReview == null)
        isAllowReview = "0"; 
      informationChannelPO.setIsAllowReview(isAllowReview);
      informationChannelPO.setAfficheChannelStatus(afficheChannelStauts);
      informationChannelPO.setChannelName(channelName);
      informationChannelPO.setChannelType(Integer.parseInt(channelType));
      informationChannelPO.setChannelParentId(new Long(channelParentId));
      informationChannelPO.setChannelNeedCheckup(Integer.parseInt(
            channelNeedCheckup));
      informationChannelPO.setChannelIssuer(channelActionForm
          .getChannelIssuer());
      informationChannelPO.setChannelIssuerGroup(channelActionForm
          .getChannelIssuerGroup());
      informationChannelPO.setChannelIssuerOrg(channelActionForm
          .getChannelIssuerOrg());
      informationChannelPO.setChannelReader(channelActionForm
          .getChannelReader());
      informationChannelPO.setChannelReaderGroup(channelActionForm
          .getChannelReaderGroup());
      informationChannelPO.setChannelReaderOrg(channelActionForm
          .getChannelReaderOrg());
      informationChannelPO.setChannelIssuerName(channelActionForm
          .getChannelIssuerName());
      informationChannelPO.setChannelReaderName(channelActionForm
          .getChannelReaderName());
      informationChannelPO.setChannelManager(channelActionForm
          .getChannelManager());
      informationChannelPO.setChannelManagerOrg(channelActionForm
          .getChannelManagerOrg());
      informationChannelPO.setChannelManagerGroup(channelActionForm
          .getChannelManagerGroup());
      informationChannelPO.setChannelManagerName(channelActionForm
          .getChannelManagerName());
      informationChannelPO.setIncludeChild(Integer.parseInt(includeChild));
      informationChannelPO.setCreatedOrg(new Long(orgId));
      informationChannelPO.setCreatedEmp(new Long(userId));
      informationChannelPO.setCreatedEmpName(userName);
      informationChannelPO.setUserDefine(httpServletRequest.getParameter(
            "userDefine"));
      informationChannelPO.setChannelShowType(Integer.parseInt(
            channelActionForm.getChannelShowType()));
      if (httpServletRequest.getParameter("onDesktop") != null) {
        informationChannelPO.setOnDesktop(Integer.parseInt(
              httpServletRequest.getParameter("onDesktop")));
        if (httpServletRequest.getParameter("onDesktop").equals("1")) {
          informationChannelPO.setInfoNum(Integer.valueOf(
                httpServletRequest.getParameter("infoNum")));
          informationChannelPO.setDesktopType(Integer.valueOf(
                httpServletRequest.getParameter("desktopType")));
        } else {
          informationChannelPO.setInfoNum(Integer.valueOf("10"));
          informationChannelPO.setDesktopType(Integer.valueOf("0"));
        } 
      } else {
        informationChannelPO.setOnDesktop(0);
        informationChannelPO.setInfoNum(Integer.valueOf("10"));
        informationChannelPO.setDesktopType(Integer.valueOf("0"));
      } 
      if (httpServletRequest.getParameter("isRollOnDesktop") != null) {
        informationChannelPO.setIsRollOnDesktop(Integer.parseInt(
              httpServletRequest.getParameter("isRollOnDesktop")));
      } else {
        informationChannelPO.setIsRollOnDesktop(0);
      } 
      if (httpServletRequest.getParameter("onDepaDesk") != null) {
        informationChannelPO.setOnDepaDesk(Integer.parseInt(
              httpServletRequest.getParameter("onDepaDesk")));
      } else {
        informationChannelPO.setOnDepaDesk(0);
      } 
      informationChannelPO.setChannelPosition(Integer.parseInt(
            httpServletRequest.getParameter("channelPosition")));
      informationChannelPO.setPositionUpDown(Integer.parseInt(
            httpServletRequest.getParameter("positionUpDown")));
      informationChannelPO.setDomainId(Long.valueOf(domainId));
      informationChannelPO.setPublicType(new Integer(-1));
      informationChannelPO.setCorpId(Long.valueOf(session.getAttribute("corpId").toString()));
      ChannelBD channelBD = new ChannelBD();
      Long long_ = channelBD.add(informationChannelPO, channelOrderId, radiobutton);
      httpServletRequest.setAttribute("result", long_);
      httpServletRequest.setAttribute("channelType", channelType);
      if (long_ != null && long_.longValue() > 0L) {
        CustomDesktopBD cdbd = new CustomDesktopBD();
        String moduleType = httpServletRequest.getParameter("moduleType");
        String[] objectId = httpServletRequest.getParameterValues("objectId");
        cdbd.saveRelationModule(moduleType, long_.toString(), objectId, domainId);
      } 
      if (action.equals("continue")) {
        List list = null;
        channelBD = new ChannelBD();
        if (SystemCommon.getMultiDepart() == 1) {
          list = channelBD.getAllChannel(channelType, domainId, corpId);
        } else {
          list = channelBD.getAllChannel(channelType, domainId, "");
        } 
        httpServletRequest.setAttribute("allChannelList", list);
        channelActionForm.setChannelName("");
        channelActionForm.setChannelIssuerName("");
        channelActionForm.setChannelReaderName("");
        channelActionForm.setIssuerId("");
        channelActionForm.setReaderId("");
        int onDesktop = 0;
        if ((Long.parseLong(channelType) == 0L || 
          "1".equals(httpServletRequest.getParameter("userDefine"))) && 
          channelBD.canOnDesktop())
          onDesktop = 1; 
        httpServletRequest.setAttribute("canOnDesktop", (new StringBuilder(String.valueOf(onDesktop))).toString());
        if (Long.parseLong(channelType) > 0L && 
          "0".equals(httpServletRequest.getParameter("userDefine")))
          httpServletRequest.setAttribute("canOnDepaDesk", 
              new Boolean(true)); 
        httpServletRequest.setAttribute("channelPositionList", 
            channelBD.getChannelPosition());
        tag = "continue";
        if (httpServletRequest.getParameter("isAffiche") != null && 
          httpServletRequest.getParameter("isAffiche").toString()
          .equals("1")) {
          tag = "affichecontinue";
          httpServletRequest.setAttribute("isAffiche", "1");
        } 
      } else {
        if (httpServletRequest.getParameter("isAffiche") != null && 
          httpServletRequest.getParameter("isAffiche").toString()
          .equals("1"))
          httpServletRequest.setAttribute("isAffiche", "1"); 
        tag = "close";
      } 
      ChannelCache.initChannel(long_.toString());
      LogBD logBD = new LogBD();
      logBD.log(session.getAttribute("userId").toString(), 
          session.getAttribute("userName").toString(), 
          session.getAttribute("orgName").toString(), 
          "oa_information_channel", "", beginDate, new Date(), "1", 
          "新增栏目", 
          session.getAttribute("userIP").toString(), domainId);
    } else if (action.equals("trueModify")) {
      Date beginDate = new Date();
      String channelOrderId = httpServletRequest.getParameter(
          "channelOrderId");
      String radiobutton = httpServletRequest.getParameter("radiobutton");
      String isAllowReview = httpServletRequest.getParameter("isAllowReview");
      String channelNeedCheckup = httpServletRequest.getParameter(
          "channelNeedCheckup");
      if (channelNeedCheckup == null)
        channelNeedCheckup = "0"; 
      String onDesktop = httpServletRequest.getParameter("onDesktop");
      if (onDesktop == null)
        onDesktop = "0"; 
      String isRollOnDesktop = httpServletRequest.getParameter(
          "isRollOnDesktop");
      if (isRollOnDesktop == null)
        isRollOnDesktop = "0"; 
      String channelNameU = channelActionForm.getChannelName();
      String afficheChannelStauts = "0";
      if (httpServletRequest.getParameter("isAffiche") != null && 
        httpServletRequest.getParameter("isAffiche").toString().equals(
          "1"))
        afficheChannelStauts = "1"; 
      String[] para = { 
          channelActionForm.getChannelId(), 
          channelNameU, 
          channelActionForm.getChannelType(), 
          channelActionForm.getChannelParentId(), 
          channelNeedCheckup, 
          channelActionForm.getChannelIssuer(), 
          channelActionForm.getChannelIssuerGroup(), 
          channelActionForm.getChannelIssuerOrg(), 
          channelActionForm.getChannelReader(), 
          channelActionForm.getChannelReaderGroup(), 
          channelActionForm.getChannelReaderOrg(), 
          channelActionForm.getChannelIssuerName(), 
          channelActionForm.getChannelReaderName(), 
          channelActionForm.getChannelShowType(), 
          onDesktop, 
          isRollOnDesktop, 
          httpServletRequest.getParameter("channelPosition"), 
          httpServletRequest.getParameter("positionUpDown"), 
          httpServletRequest.getParameter("onDepaDesk"), 
          httpServletRequest.getParameter("infoNum"), 
          httpServletRequest.getParameter("desktopType"), 
          httpServletRequest.getParameter("includeChild"), 
          
          channelActionForm.getChannelManager(), 
          channelActionForm.getChannelManagerOrg(), 
          channelActionForm.getChannelManagerGroup(), 
          channelActionForm.getChannelManagerName(), 
          afficheChannelStauts, 
          (isAllowReview == null) ? "0" : isAllowReview };
      ChannelBD channelBD = new ChannelBD();
      channelBD.modifyByArray(para, channelOrderId, radiobutton);
      CustomDesktopBD cdbd = new CustomDesktopBD();
      String moduleType = httpServletRequest.getParameter("moduleType");
      String moduleSubId = httpServletRequest.getParameter("moduleSubId");
      String[] objectId = httpServletRequest.getParameterValues("objectId");
      cdbd.saveRelationModule(moduleType, moduleSubId, objectId, domainId);
      httpServletRequest.setAttribute("channelType", 
          channelActionForm.getChannelType());
      httpServletRequest.setAttribute("channelType", 
          channelActionForm.getChannelType());
      httpServletRequest.setAttribute("pager.offset", 
          httpServletRequest
          .getParameter("pager.offset"));
      tag = "close";
      if (httpServletRequest.getParameter("isAffiche") != null && 
        httpServletRequest.getParameter("isAffiche").toString().equals(
          "1"))
        httpServletRequest.setAttribute("isAffiche", "1"); 
      ChannelCache.initChannel(channelActionForm.getChannelId());
      LogBD logBD = new LogBD();
      logBD.log(session.getAttribute("userId").toString(), 
          session.getAttribute("userName").toString(), 
          session.getAttribute("orgName").toString(), 
          "oa_information_channel", "", beginDate, new Date(), "2", "修改栏目", 
          session.getAttribute("userIP").toString(), domainId);
    } else if (action.equals("delete")) {
      if (httpServletRequest.getParameter("flag") != null && !"null".equals(httpServletRequest.getParameter("flag")))
        httpServletRequest.setAttribute("flag", httpServletRequest.getParameter("flag")); 
      Date beginDate = new Date();
      if (depart != null) {
        tag = "deletedepart";
      } else if (httpServletRequest.getParameter("defaultDepa") != null) {
        tag = "defaultDepa";
      } else {
        tag = "delete";
        if (httpServletRequest.getParameter("isAffiche") != null && 
          httpServletRequest.getParameter("isAffiche").toString()
          .equals("1"))
          tag = "affichedelete"; 
      } 
      String channelId = httpServletRequest.getParameter("channelId");
      ChannelBD channelBD = new ChannelBD();
      Object[] obj = channelBD.getAccessory(channelId);
      DeleteFile df = new DeleteFile();
      if (obj != null && obj.length > 0)
        for (int i = 0; i < obj.length; i++)
          df.delete("information", obj[i].toString());  
      OrganizationBD organizationBD = new OrganizationBD();
      List list = organizationBD.getHasChannel();
      httpServletRequest.setAttribute("hasChannelList", list);
      ManagerService managerBD = new ManagerService();
      if (channelType.equals("0") || 
        "1".equals(httpServletRequest.getParameter("userDefine"))) {
        if (managerBD.hasRight(userId, "01*02*01")) {
          httpServletRequest.setAttribute("addRight", "1");
        } else {
          httpServletRequest.setAttribute("addRight", "0");
        } 
      } else if (managerBD.hasRight(userId, "01*01*02")) {
        httpServletRequest.setAttribute("addRight", "1");
      } else {
        httpServletRequest.setAttribute("addRight", "0");
      } 
      int pager = 0;
      if (httpServletRequest.getParameter("pager.offset") != null && 
        httpServletRequest.getParameter("pager.offset").equals(""))
        pager = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(4);
      if (isAffiche.equals("1"))
        moduleVO.setId(51); 
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (
          new WorkFlowBD())
          .getAccessTable(moduleVO).get(0));
      view(httpServletRequest, channelType, pager, depart, styleId, 
          domainId);
      LogBD logBD = new LogBD();
      logBD.log(session.getAttribute("userId").toString(), 
          session.getAttribute("userName").toString(), 
          session.getAttribute("orgName").toString(), 
          "oa_information_channel", "", beginDate, new Date(), "3", 
          "删除栏目", 
          session.getAttribute("userIP").toString(), domainId);
      CustomDesktopBD cdbd = new CustomDesktopBD();
      String moduleType = httpServletRequest.getParameter("moduleType");
      String[] objectId = httpServletRequest.getParameterValues("objectId");
      cdbd.delRelationModule(moduleType, channelId, domainId);
    } 
    Integer result = Integer.valueOf("0");
    httpServletRequest.setAttribute("result", result);
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest request, String channelType, int pager, String depart, String styleId, String domainId) {
    String viewSql = 
      "aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,aaa.channelNeedCheckup,aaa.createdOrg,aaa.createdEmp,aaa.createdEmpName,aaa.onDesktop,aaa.isRollOnDesktop, aaa.onDepaDesk, bbb.processId,aaa.channelIssuerName,aaa.isAllowReview";
    String fromSql = 
      "com.js.oa.info.channelmanager.po.InformationChannelPO aaa left outer join aaa.channelProcess bbb ";
    String whereSql = "where aaa.channelType = " + channelType;
    if ("1".equals(channelType))
      if (SystemCommon.getMultiDepart() != 1) {
        whereSql = String.valueOf(whereSql) + " and (aaa.channelIdString like '%$100$%' or aaa.channelIdString like '%$101$%')";
      } else {
        whereSql = String.valueOf(whereSql) + " and (aaa.channelIdString not like '%$100$%' and aaa.channelIdString not like '%$101$%') ";
      }  
    if (request.getParameter("isAffiche") != null && request.getParameter("isAffiche").toString().equals("1")) {
      whereSql = String.valueOf(whereSql) + " and aaa.afficheChannelStatus='1' ";
    } else {
      whereSql = String.valueOf(whereSql) + " and ( aaa.afficheChannelStatus is null or  aaa.afficheChannelStatus='0' )";
    } 
    String searchChannelName = request.getParameter("searchChannelName");
    if (searchChannelName != null && !"null".equals(searchChannelName)) {
      searchChannelName = searchChannelName.trim();
      whereSql = String.valueOf(whereSql) + " and aaa.channelName like '%" + searchChannelName + "%'";
    } 
    if (SystemCommon.getMultiDepart() == 1) {
      String corpId = request.getSession().getAttribute("corpId").toString();
      whereSql = String.valueOf(whereSql) + " and aaa.corpId=" + corpId;
    } 
    whereSql = String.valueOf(whereSql) + " and aaa.domainId=" + domainId + 
      " order by aaa.channelIdString ";
    if (request.getParameter("isAffiche") != null && request.getParameter("isAffiche").toString().equals("1")) {
      afficeList(request, viewSql, fromSql, whereSql, channelType, pager, depart, styleId);
    } else {
      list(request, viewSql, fromSql, whereSql, channelType, pager, depart, styleId);
    } 
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, String channelType, int pager, String depart, String styleId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    String rightCode = "01*02*01";
    String userDefine = httpServletRequest.getParameter("userDefine");
    if ("0".equals(userDefine) && Integer.parseInt(channelType) >= 1)
      rightCode = "01*01*02"; 
    List<Object[]> rightScopeList = managerBD.getRightScope(userId, rightCode);
    String selfSonOrgStr = "";
    String scopeSonStr = "";
    boolean flag = false;
    int rightScopeType = 0;
    String rightScopeScope = "";
    if (rightScopeList != null && rightScopeList.size() > 0) {
      flag = true;
      Object[] tmp = rightScopeList.get(0);
      rightScopeType = Integer.parseInt(tmp[0].toString());
      if (tmp[1] != null && !tmp[1].toString().equals(""))
        rightScopeScope = tmp[1].toString().replace('*', 's'); 
      OrganizationBD organizationBD1 = new OrganizationBD();
      List<String> selfSon = organizationBD1.getSons(orgId);
      if (selfSon != null)
        for (int j = 0; j < selfSon.size(); j++)
          selfSonOrgStr = String.valueOf(selfSonOrgStr) + "$" + selfSon.get(j) + "$";  
      if (rightScopeType == 4 && 
        !rightScopeScope.equals("")) {
        String[] rightTmp = ("s" + rightScopeScope + 
          "s").split("ss");
        for (int k = 0; k < rightTmp.length; k++) {
          if (!rightTmp[k].equals("")) {
            List<String> scopeSon = organizationBD1.getSons(
                rightTmp[k]);
            scopeSonStr = String.valueOf(scopeSonStr) + "$" + rightTmp[k] + "$";
            if (scopeSon != null)
              for (int j = 0; j < scopeSon.size(); j++)
                scopeSonStr = String.valueOf(scopeSonStr) + "$" + 
                  scopeSon.get(j) + "$";  
          } 
        } 
      } 
    } 
    int pageSize = 15;
    int offset = pager;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    OrganizationBD organizationBD = new OrganizationBD();
    ChannelBD channelBD = new ChannelBD();
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      String channelType2 = obj[2].toString();
      if (channelType2.equals("0")) {
        obj[2] = "公共频道";
      } else if ("1".equals(userDefine)) {
        obj[2] = channelBD.getUserChannelName(channelType2);
      } else {
        obj[2] = organizationBD.getOrgName((String)obj[2]);
      } 
      String createdOrg = obj[5].toString();
      String createdEmp = obj[6].toString();
      if (!flag) {
        obj[5] = "0";
      } else if (rightScopeType == 0) {
        obj[5] = "1";
      } else if (rightScopeType == 1) {
        if (userId.equals(createdEmp)) {
          obj[5] = "1";
        } else {
          obj[5] = "0";
        } 
      } else if (rightScopeType == 2) {
        if (createdOrg.equals(orgId) || 
          selfSonOrgStr.indexOf("$" + createdOrg + "$") >= 0) {
          obj[5] = "1";
        } else {
          obj[5] = "0";
        } 
      } else if (rightScopeType == 3) {
        if (createdOrg.equals(orgId)) {
          obj[5] = "1";
        } else {
          obj[5] = "0";
        } 
      } else if (rightScopeType == 4) {
        if (scopeSonStr.indexOf("$" + createdOrg + "$") >= 0 || 
          rightScopeScope.equals((new StringBuilder(String.valueOf(createdOrg))).toString())) {
          obj[5] = "1";
        } else {
          obj[5] = "0";
        } 
      } 
      list.set(i, obj);
    } 
    httpServletRequest.setAttribute("channelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("currentPage", (new StringBuilder(String.valueOf(currentPage))).toString());
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,channelType,userChannelName,searchChannelType,searchChannelName,searchOnDesktop,userDefine,depart,styleId,isAffiche");
  }
  
  private void afficeList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, String channelType, int pager, String depart, String styleId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    int pageSize = 15;
    int offset = pager;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    OrganizationBD organizationBD = new OrganizationBD();
    ChannelBD channelBD = new ChannelBD();
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      obj[5] = "1";
      list.set(i, obj);
    } 
    httpServletRequest.setAttribute("channelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("currentPage", (new StringBuilder(String.valueOf(currentPage))).toString());
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,channelType,userChannelName,searchChannelType,searchChannelName,searchOnDesktop,userDefine,depart,styleId,isAffiche");
  }
}
