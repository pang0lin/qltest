package com.js.oa.info.channelmanager.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.channelmanager.service.DepartmentPageBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.util.page.Page;
import com.js.util.util.DeleteFile;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DepartmentStyleAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    DepartmentStyleActionForm departmentStyleActionForm = (DepartmentStyleActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String tag = "frame";
    String flag = httpServletRequest.getParameter("flag");
    if (flag == null)
      flag = "frame"; 
    String styleId = httpServletRequest.getParameter("styleId");
    httpServletRequest.setAttribute("styleId", styleId);
    String orgId = httpServletRequest.getParameter("orgId");
    httpServletRequest.setAttribute("orgId", orgId);
    String orgName = httpServletRequest.getParameter("orgName");
    httpServletRequest.setAttribute("orgName", orgName);
    String channelId = httpServletRequest.getParameter("channelId");
    httpServletRequest.setAttribute("channelId", channelId);
    if (flag.equals("frame")) {
      tag = "frame";
    } else if (flag.equals("styleSetup")) {
      tag = "styleSetup";
      (new DepartmentPageBD()).ModiDepaStyle(styleId, orgId);
    } else {
      HttpSession httpSession = httpServletRequest.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String userOrg = httpSession.getAttribute("orgId").toString();
      ChannelBD channelBD = new ChannelBD();
      DepartmentPageBD depaBD = new DepartmentPageBD();
      httpServletRequest.setAttribute("stylePO", channelBD.getDepaStyle(styleId));
      if (flag.equals("top")) {
        httpServletRequest.setAttribute("topChannel", depaBD.getTopChannel(userOrg, userId, orgId));
        tag = "top";
        if (channelBD.departPageRight(userId, userOrg, httpSession.getAttribute("orgIdString").toString(), httpServletRequest.getParameter("orgId"))) {
          httpServletRequest.setAttribute("canMana", "1");
        } else {
          httpServletRequest.setAttribute("canMana", "0");
        } 
        httpServletRequest.setAttribute("orgBanner", depaBD.getOrgBanner(orgId));
      } else if (flag.equals("left")) {
        tag = "left";
        if (channelId != null) {
          httpServletRequest.setAttribute("channelTree", depaBD.getLeftChTree(channelId, userOrg, userId));
          String corpId = session.getAttribute("corpId").toString();
          String sidelineCorpId = session.getAttribute("sidelineCorpId").toString();
          List<Object[]> list = channelBD.getUserViewCh(userId, userOrg, orgId, "0", domainId, corpId, sidelineCorpId);
          String canReadChannel = "";
          Object[] obj = (Object[])null;
          if (list != null && list.size() > 0 && list.get(0) != null)
            for (int i = 0; i < list.size(); i++) {
              obj = list.get(i);
              canReadChannel = String.valueOf(canReadChannel) + "$" + obj[0] + "$";
            }  
          httpServletRequest.setAttribute("canReadChannel", canReadChannel);
        } 
      } else if (flag.equals("right")) {
        tag = "right";
        StringBuffer sb = new StringBuffer();
        InformationBD infoBD = new InformationBD();
        String corpId = session.getAttribute("corpId").toString();
        String sidelineCorpId = session.getAttribute("sidelineCorpId").toString();
        String readerWhere = infoBD.getInfoReader(userId, userOrg, 
            httpSession.getAttribute("orgIdString").toString(), 
            "aaa");
        List list = channelBD.getUserViewCh(userId, userOrg, orgId, "0", domainId, corpId, sidelineCorpId);
        for (int i = 0; i < list.size(); i++)
          sb.append("$" + ((Object[])list.get(i))[0] + "$"); 
        httpServletRequest.setAttribute("depaDeskInfo", depaBD.departmentDeskop(sb.toString(), readerWhere));
        httpServletRequest.setAttribute("mostNewInfo", depaBD.getMostNewInfo(sb.toString(), httpSession.getAttribute("orgIdString").toString(), httpSession.getAttribute("userId").toString()));
        List mostNewInfo = new ArrayList();
        mostNewInfo = depaBD.getMostNewInfo(sb.toString(), httpSession.getAttribute("orgIdString").toString(), httpSession.getAttribute("userId").toString());
        DepartmentPageBD dpBD = new DepartmentPageBD();
        httpServletRequest.setAttribute("anno", dpBD.getNewAnno(orgId, readerWhere));
        httpServletRequest.setAttribute("photoInfo", dpBD.getPhotoInfo(orgId, readerWhere));
      } else if (flag.equals("setup")) {
        tag = "setup";
      } else if (flag.equals("defaultLeft")) {
        tag = "defaultLeft";
        httpServletRequest.setAttribute("canIssue", new Boolean(channelBD.canIssue(userId, userOrg, orgId)));
      } else if (flag.equals("ny") || flag.equals("commend") || flag.equals("transfer") || flag.equals("batchDelete") || flag.equals("deleteAll") || flag.equals("singleDelete")) {
        String returnValue = "";
        try {
          returnValue = (new ArchivesBD()).archivesPigeonholeSet("ZSGL", session.getAttribute("domainId").toString());
        } catch (Exception exception) {}
        if (returnValue.equals(""))
          returnValue = "0,1,0"; 
        Boolean dossier = Boolean.FALSE;
        String isSendMessage = "0";
        if (!returnValue.equals("-1")) {
          String[] tmp = returnValue.split(",");
          if (tmp[0].startsWith("0"))
            dossier = Boolean.TRUE; 
          isSendMessage = tmp[2];
        } 
        httpServletRequest.setAttribute("dossier", dossier);
        httpServletRequest.setAttribute("isSendMessage", isSendMessage);
        InformationBD informationBD = new InformationBD();
        List<String> list = null;
        if (flag.equals("commend") || flag.equals("transfer") || flag.equals("batchDelete") || flag.equals("deleteAll")) {
          String[] batchId = httpServletRequest.getParameterValues("batchId");
          if (flag.equals("commend")) {
            informationBD.commend(batchId);
          } else if (!flag.equals("transfer")) {
            if (flag.equals("batchDelete")) {
              list = informationBD.batchDelete(batchId);
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size(); i++)
                  deleteFile.delete("information", list.get(i)); 
              } 
            } else if (flag.equals("deleteAll")) {
              list = informationBD.allDelete(channelId);
              String path = httpServletRequest.getRealPath("/");
              if (list != null && list.size() > 0) {
                DeleteFile deleteFile = new DeleteFile();
                for (int i = 0; i < list.size(); i++)
                  deleteFile.delete(String.valueOf(path) + "/upload/information", list.get(i)); 
              } 
            } 
          } 
        } else if (flag.equals("singleDelete")) {
          String id = httpServletRequest.getParameter("id");
          list = informationBD.singleDelete(channelId, id);
          if (list != null && list.size() > 0) {
            String path = httpServletRequest.getRealPath("/");
            DeleteFile deleteFile = new DeleteFile();
            for (int i = 0; i < list.size(); i++)
              deleteFile.delete(String.valueOf(path) + "/upload/information", list.get(i)); 
          } 
        } 
        InformationBD infoBD = new InformationBD();
        String readerWhere = infoBD.getInfoReader(userId, userOrg, 
            httpSession.getAttribute("orgIdString").toString(), 
            "aaa");
        String viewSql = "";
        if (httpServletRequest.getParameter("channelShowType") != null && httpServletRequest.getParameter("channelShowType").equals("1")) {
          tag = "picList";
          viewSql = "aaa.informationId,aaa.informationTitle,aaa.informationHead, aaa.informationType,aaa.validBeginTime, aaa.validEndTime, aaa.informationKits, aaa.informationIssueTime,aaa.titleColor,aaa.dossierStatus,aaa.isConf,bbb.channelId,aaa.otherChannel,bbb.channelNeedCheckup ";
        } else {
          tag = "ny";
          viewSql = "aaa.informationId,aaa.informationTitle,aaa.informationKits,aaa.informationIssueTime,aaa.informationHead,aaa.informationType,aaa.orderCode,aaa.informationValidType,aaa.validBeginTime,aaa.validEndTime,aaa.titleColor,aaa.dossierStatus,aaa.isConf,bbb.channelId,aaa.otherChannel,bbb.channelNeedCheckup,aaa.informationIssuer,aaa.informationModifyTime,aaa.informationCommonNum ";
        } 
        httpServletRequest.setAttribute("channelShowType", httpServletRequest.getParameter("channelShowType"));
        String fromSql = "com.js.oa.info.infomanager.po.InformationPO aaa join aaa.informationChannel bbb ";
        String includeChild = "";
        List<Object[]> lists = new ArrayList();
        lists = channelBD.getSingleChannel(channelId);
        if (lists != null && lists.size() > 0) {
          Object[] obj = (Object[])null;
          obj = lists.get(0);
          includeChild = obj[22].toString();
        } 
        String whereSql = "";
        if (includeChild.equals("0")) {
          whereSql = "where (bbb.channelId = " + channelId + " or aaa.otherChannel like '%," + channelId + ",%') " + 
            "and aaa.informationStatus=0 and (" + readerWhere + ") order by aaa.orderCode desc, aaa.informationIssueTime desc";
        } else {
          whereSql = "where (bbb.channelIdString like '%$" + channelId + "$%' or aaa.otherChannel like '%," + channelId + ",%') " + 
            "and aaa.informationStatus=0 and (" + readerWhere + ") order by aaa.orderCode desc, aaa.informationIssueTime desc";
        } 
        list(httpServletRequest, viewSql, fromSql, whereSql);
        Boolean canVindicate = new Boolean(false);
        canVindicate = new Boolean(channelBD.departPageRight(userId, userOrg, 
              httpSession.getAttribute("orgIdString").toString(), 
              httpServletRequest.getParameter("orgId")));
        httpServletRequest.setAttribute("canVindicate", canVindicate);
        DepartmentPageBD dpBD = new DepartmentPageBD();
        httpServletRequest.setAttribute("anno", dpBD.getNewAnno(orgId, readerWhere));
        httpServletRequest.setAttribute("photoInfo", dpBD.getPhotoInfo(orgId, readerWhere));
        int listType = 0;
        if (httpServletRequest.getParameter("listType") != null)
          listType = Integer.parseInt(httpServletRequest.getParameter("listType")); 
        if (listType == 1)
          tag = "nyListType"; 
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int listType = 0;
    if (httpServletRequest.getParameter("listType") != null)
      listType = Integer.parseInt(httpServletRequest.getParameter("listType")); 
    if (listType == 1)
      pageSize = 30; 
    if (httpServletRequest.getParameter("channelShowType") != null && 
      httpServletRequest.getParameter("channelShowType").equals("1"))
      pageSize = 8; 
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    System.out.println("________________________________________________________________");
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    System.out.println("________________________________________________________________");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("informationList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,channelId,channelType,styleId,channelName,orgId,channelShowType,listType,orgName");
  }
}
