package com.js.oa.info.channelmanager.action;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ISOChannelAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ChannelActionForm channelActionForm = (ChannelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String tag = "isoDocView";
    String action = httpServletRequest.getParameter("action");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String channelType = httpServletRequest.getParameter("channelType");
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    if (action.equals("isoDocView")) {
      String userDefine = (httpServletRequest.getParameter("userDefine") == null) ? "0" : httpServletRequest.getParameter("userDefine");
      ManagerService managerBD = new ManagerService();
      tag = "isoDocView";
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(50);
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (new WorkFlowBD()).getAccessTable(moduleVO).get(0));
      view(httpServletRequest, channelType, 0, "", "", domainId);
    } else if (action.equals("search")) {
      tag = "isoDocView";
      String searchChannelType = httpServletRequest.getParameter("searchChannelType");
      String searchChannelName = httpServletRequest.getParameter("searchChannelName").trim();
      String searchOnDesktop = httpServletRequest.getParameter("searchOnDesktop");
      String viewSql = "aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,aaa.channelNeedCheckup,aaa.createdOrg,aaa.createdEmp,aaa.createdEmpName,aaa.onDesktop,aaa.isRollOnDesktop,aaa.onDepaDesk,bbb.processId,aaa.channelIssuerName";
      String fromSql = "com.js.oa.info.channelmanager.po.InformationChannelPO aaa left outer join aaa.channelProcess bbb";
      String whereSql = "where aaa.channelType = " + channelType;
      whereSql = String.valueOf(whereSql) + " and aaa.channelName like '%" + searchChannelName + "%'";
      if (searchOnDesktop != null && !searchOnDesktop.equals("-1"))
        whereSql = String.valueOf(whereSql) + " and aaa.onDesktop = " + searchOnDesktop; 
      whereSql = String.valueOf(whereSql) + " and aaa.afficheChannelStatus = '2' ";
      whereSql = String.valueOf(whereSql) + " and aaa.domainId=" + domainId + " order by aaa.channelIdString ";
      afficeList(httpServletRequest, viewSql, fromSql, whereSql, channelType, 0, "", "");
      channelActionForm.setSearchChannelName(searchChannelName);
      channelActionForm.setSearchChannelType(searchChannelType);
      httpServletRequest.setAttribute("channelType", searchChannelType);
      String userDefine = (httpServletRequest.getParameter("userDefine") == null) ? "0" : httpServletRequest.getParameter("userDefine");
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(50);
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (new WorkFlowBD()).getAccessTable(moduleVO).get(0));
    } else if (action.equals("add") || action.equals("modify")) {
      httpServletRequest.setAttribute("channelType", channelType);
      ChannelBD channelBD = new ChannelBD();
      List<Object[]> list = channelBD.getAllChannel_ByType(channelType, domainId, "2");
      httpServletRequest.setAttribute("allChannelList", list);
      int onDesktop = 0;
      if ((Long.parseLong(channelType) == 0L || "1".equals(httpServletRequest.getParameter("userDefine"))) && 
        channelBD.canOnDesktop())
        onDesktop = 1; 
      httpServletRequest.setAttribute("canOnDesktop", (new StringBuilder(String.valueOf(onDesktop))).toString());
      if (Long.parseLong(channelType) > 0L && "0".equals(httpServletRequest.getParameter("userDefine")))
        httpServletRequest.setAttribute("canOnDepaDesk", new Boolean(true)); 
      httpServletRequest.setAttribute("channelPositionList", channelBD.getChannelPosition());
      if (action.equals("add")) {
        tag = "isoDocadd";
        httpServletRequest.setAttribute("allSortList", list);
      } else {
        tag = "isoDocmodify";
        String channelId = httpServletRequest.getParameter("channelId");
        httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
        list = channelBD.getBrotherCh_ByChannelStatusType(channelId, session.getAttribute("domainId").toString(), "2");
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
      } 
    } else if (action.equals("continue") || action.equals("close")) {
      String includeChild = channelActionForm.getIncludeChild();
      String afficheChannelStauts = "2";
      InformationChannelPO informationChannelPO = new InformationChannelPO();
      String channelName = channelActionForm.getChannelName();
      channelType = channelActionForm.getChannelType();
      String channelParentId = channelActionForm.getChannelParentId();
      String channelNeedCheckup = httpServletRequest.getParameter("channelNeedCheckup");
      String channelOrderId = httpServletRequest.getParameter("channelOrderId");
      String radiobutton = httpServletRequest.getParameter("radiobutton");
      if (channelNeedCheckup == null)
        channelNeedCheckup = "0"; 
      informationChannelPO.setAfficheChannelStatus(afficheChannelStauts);
      informationChannelPO.setChannelName(channelName);
      informationChannelPO.setChannelType(Integer.parseInt(channelType));
      informationChannelPO.setChannelParentId(new Long(channelParentId));
      informationChannelPO.setChannelNeedCheckup(Integer.parseInt(channelNeedCheckup));
      informationChannelPO.setChannelIssuer(channelActionForm.getChannelIssuer());
      informationChannelPO.setChannelIssuerGroup(channelActionForm.getChannelIssuerGroup());
      informationChannelPO.setChannelIssuerOrg(channelActionForm.getChannelIssuerOrg());
      informationChannelPO.setChannelReader(channelActionForm.getChannelReader());
      informationChannelPO.setChannelReaderGroup(channelActionForm.getChannelReaderGroup());
      informationChannelPO.setChannelReaderOrg(channelActionForm.getChannelReaderOrg());
      informationChannelPO.setChannelIssuerName(channelActionForm.getChannelIssuerName());
      informationChannelPO.setChannelReaderName(channelActionForm.getChannelReaderName());
      informationChannelPO.setChannelManager(channelActionForm.getChannelManager());
      informationChannelPO.setChannelManagerOrg(channelActionForm.getChannelManagerOrg());
      informationChannelPO.setChannelManagerGroup(channelActionForm.getChannelManagerGroup());
      informationChannelPO.setChannelManagerName(channelActionForm.getChannelManagerName());
      informationChannelPO.setIncludeChild(Integer.parseInt(includeChild));
      informationChannelPO.setCreatedOrg(new Long(orgId));
      informationChannelPO.setCreatedEmp(new Long(userId));
      informationChannelPO.setCreatedEmpName(userName);
      informationChannelPO.setUserDefine(httpServletRequest.getParameter("userDefine"));
      informationChannelPO.setChannelShowType(Integer.parseInt(channelActionForm.getChannelShowType()));
      if (httpServletRequest.getParameter("onDesktop") != null) {
        informationChannelPO.setOnDesktop(Integer.parseInt(httpServletRequest.getParameter("onDesktop")));
        if (httpServletRequest.getParameter("onDesktop").equals("1")) {
          informationChannelPO.setInfoNum(Integer.valueOf(httpServletRequest.getParameter("infoNum")));
          informationChannelPO.setDesktopType(Integer.valueOf(httpServletRequest.getParameter("desktopType")));
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
        informationChannelPO.setIsRollOnDesktop(Integer.parseInt(httpServletRequest.getParameter("isRollOnDesktop")));
      } else {
        informationChannelPO.setIsRollOnDesktop(0);
      } 
      if (httpServletRequest.getParameter("onDepaDesk") != null) {
        informationChannelPO.setOnDepaDesk(Integer.parseInt(httpServletRequest.getParameter("onDepaDesk")));
      } else {
        informationChannelPO.setOnDepaDesk(0);
      } 
      informationChannelPO.setChannelPosition(Integer.parseInt(httpServletRequest.getParameter("channelPosition")));
      informationChannelPO.setPositionUpDown(Integer.parseInt(httpServletRequest.getParameter("positionUpDown")));
      informationChannelPO.setDomainId(Long.valueOf(domainId));
      ChannelBD channelBD = new ChannelBD();
      Long long_ = channelBD.add(informationChannelPO, channelOrderId, radiobutton);
      httpServletRequest.setAttribute("result", long_);
      httpServletRequest.setAttribute("channelType", channelType);
      if (action.equals("continue")) {
        channelBD = new ChannelBD();
        List list = channelBD.getAllChannel_ByType(channelType, domainId, "2");
        httpServletRequest.setAttribute("allChannelList", list);
        channelActionForm.setChannelName("");
        channelActionForm.setChannelIssuerName("");
        channelActionForm.setChannelReaderName("");
        channelActionForm.setIssuerId("");
        channelActionForm.setReaderId("");
        int onDesktop = 0;
        if ((Long.parseLong(channelType) == 0L || "1".equals(httpServletRequest.getParameter("userDefine"))) && 
          channelBD.canOnDesktop())
          onDesktop = 1; 
        httpServletRequest.setAttribute("canOnDesktop", (new StringBuilder(String.valueOf(onDesktop))).toString());
        if (Long.parseLong(channelType) > 0L && "0".equals(httpServletRequest.getParameter("userDefine")))
          httpServletRequest.setAttribute("canOnDepaDesk", new Boolean(true)); 
        httpServletRequest.setAttribute("channelPositionList", channelBD.getChannelPosition());
        tag = "aisoDoccontinue";
      } else {
        tag = "close";
      } 
    } else if (action.equals("trueModify")) {
      String channelOrderId = httpServletRequest.getParameter("channelOrderId");
      String radiobutton = httpServletRequest.getParameter("radiobutton");
      String channelNeedCheckup = httpServletRequest.getParameter("channelNeedCheckup");
      if (channelNeedCheckup == null)
        channelNeedCheckup = "0"; 
      String onDesktop = httpServletRequest.getParameter("onDesktop");
      if (onDesktop == null)
        onDesktop = "0"; 
      String isRollOnDesktop = httpServletRequest.getParameter("isRollOnDesktop");
      if (isRollOnDesktop == null)
        isRollOnDesktop = "0"; 
      String channelNameU = channelActionForm.getChannelName();
      String afficheChannelStauts = "2";
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
          afficheChannelStauts };
      ChannelBD channelBD = new ChannelBD();
      channelBD.modifyByArray(para, channelOrderId, radiobutton);
      httpServletRequest.setAttribute("channelType", channelActionForm.getChannelType());
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      tag = "close";
    } else if (action.equals("delete")) {
      tag = "isoDocdelete";
      String channelId = httpServletRequest.getParameter("channelId");
      ChannelBD channelBD = new ChannelBD();
      Object[] obj = channelBD.deleteInformation(channelId, "50");
      DeleteFile df = new DeleteFile();
      for (int i = 0; i < obj.length; i++)
        df.delete("information", obj[i].toString()); 
      OrganizationBD organizationBD = new OrganizationBD();
      List list = organizationBD.getHasChannel();
      httpServletRequest.setAttribute("hasChannelList", list);
      ManagerService managerBD = new ManagerService();
      int pager = 0;
      if (httpServletRequest.getParameter("pager.offset") != null && 
        httpServletRequest.getParameter("pager.offset").equals(""))
        pager = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setId(50);
      moduleVO.setFormType(0);
      httpServletRequest.setAttribute("tableVO", (new WorkFlowBD()).getAccessTable(moduleVO).get(0));
      view(httpServletRequest, channelType, pager, "", "", domainId);
    } 
    Integer result = Integer.valueOf("0");
    httpServletRequest.setAttribute("result", result);
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest request, String channelType, int pager, String depart, String styleId, String domainId) {
    String viewSql = 
      "aaa.channelId,aaa.channelName,aaa.channelType,aaa.channelLevel,aaa.channelNeedCheckup,aaa.createdOrg,aaa.createdEmp,aaa.createdEmpName,aaa.onDesktop,aaa.isRollOnDesktop, aaa.onDepaDesk, bbb.processId,aaa.channelIssuerName";
    String fromSql = 
      "com.js.oa.info.channelmanager.po.InformationChannelPO aaa left outer join aaa.channelProcess bbb ";
    String whereSql = "where aaa.channelType = " + channelType;
    whereSql = String.valueOf(whereSql) + " and aaa.afficheChannelStatus='2' ";
    String searchChannelName = request.getParameter("searchChannelName");
    if (searchChannelName != null && !"null".equals(searchChannelName)) {
      searchChannelName = searchChannelName.trim();
      whereSql = String.valueOf(whereSql) + " and aaa.channelName like '%" + 
        searchChannelName + "%'";
    } 
    whereSql = String.valueOf(whereSql) + " and aaa.domainId=" + domainId + 
      " order by aaa.channelIdString ";
    afficeList(request, viewSql, fromSql, whereSql, channelType, pager, depart, styleId);
  }
  
  private void afficeList(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, String channelType, int pager, String depart, String styleId) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    int pageSize = 15;
    int offset = pager;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
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
    httpServletRequest.setAttribute("channelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("currentPage", (new StringBuilder(String.valueOf(currentPage))).toString());
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,channelType,userChannelName,searchChannelType,searchChannelName,searchOnDesktop,userDefine,depart,styleId,isAffiche");
  }
}
