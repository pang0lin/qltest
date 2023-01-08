package com.js.oa.info.infomanager.action;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.info.infomanager.bean.InfoProFileEJBBean;
import com.js.oa.info.infomanager.po.InfoProFilePO;
import com.js.oa.info.infomanager.po.InfoProFileReviewPO;
import com.js.oa.info.infomanager.service.InfoProFileBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.util.page.Page;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoProFileAction extends Action {
  private InformationBD informationBD = new InformationBD();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    InfoProFileActionForm infoProFileActionForm = (InfoProFileActionForm)actionForm;
    HttpSession session = request.getSession(true);
    request.setCharacterEncoding("GBK");
    String domainId = session.getAttribute("domainId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    String orgString = session.getAttribute("orgIdString").toString();
    String action = request.getParameter("action");
    InfoProFileBD infoProFileBD = new InfoProFileBD();
    String tag = "";
    if ("list".equals(action)) {
      tag = "list";
      Map<Object, Object> paraMap = new HashMap<Object, Object>();
      String fileNum = request.getParameter("fileNum");
      if (fileNum != null && !"".equals(fileNum))
        paraMap.put("fileNum", fileNum); 
      String fileSaveName = request.getParameter("fileSaveName");
      if (fileSaveName != null && !"".equals(fileSaveName))
        paraMap.put("fileSaveName", fileSaveName); 
      String reviewbeginDate = request.getParameter("reviewbeginDate");
      if (reviewbeginDate != null && !"".equals(reviewbeginDate))
        paraMap.put("reviewbeginDate", reviewbeginDate); 
      String reviewendDate = request.getParameter("reviewendDate");
      if (reviewendDate != null && !"".equals(reviewendDate))
        paraMap.put("reviewendDate", reviewendDate); 
      List proFileList = infoProFileBD.ListAllProFile((Map)paraMap);
      request.setAttribute("proFileList", proFileList);
    } else if ("add".equals(action)) {
      tag = "add";
      infoProFileActionForm.setAuthor(userName);
      infoProFileActionForm.setAuthorId(userId);
      infoProFileActionForm.setDepartment(orgName);
      infoProFileActionForm.setDepartmentId(orgId);
      request.setAttribute("userName", userName);
      request.setAttribute("orgName", orgName);
    } else if ("saveAndclose".equals(action)) {
      InfoProFilePO infoProFilePO = setPO(infoProFileActionForm, request);
      infoProFilePO.setIsAppend("0");
      infoProFileBD.save(infoProFilePO);
      String flag = request.getParameter("flag");
      if ("continue".equals(flag)) {
        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/InfoProFileAction.do?action=add");
        actionForward.setRedirect(true);
        return actionForward;
      } 
    } else {
      if ("deleteall".equals(action)) {
        String ids = request.getParameter("copyitem");
        infoProFileBD.delete(ids);
        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/InfoProFileAction.do?action=userList&isNew=1&isExport=no");
        actionForward.setRedirect(true);
        return actionForward;
      } 
      if ("userList".equals(action)) {
        String isExport = request.getParameter("isExport");
        if ("yes".equals(isExport)) {
          tag = "export";
          Map<String, String> paraMap = new HashMap<String, String>();
          paraMap.put("isNew", "1");
          List userFileList = infoProFileBD.ListAllProFile(paraMap);
          request.setAttribute("userFileList", userFileList);
        } else {
          tag = "userList";
          InfoProFileEJBBean iffb = new InfoProFileEJBBean();
          boolean b = iffb.hasRoleId(userId);
          int pageSize = 15;
          int offset = 0;
          if (request.getParameter("pager.offset") != null)
            offset = Integer.parseInt(request
                .getParameter("pager.offset")); 
          String fileNum = request.getParameter("fileNum");
          String fileSaveName = request.getParameter("fileSaveName");
          String viewSQL = "select aaa.fileId,aaa.proFile,aaa.fileName,aaa.fileNum,aaa.character,aaa.fileDate,aaa.reviewDate,aaa.department,aaa.author,aaa.viewMan,aaa.filePreId,aaa.version,aaa.isNew,aaa.filePath,aaa.appendPath,aaa.fileViewName,aaa.fileAppendName";
          String fromSQL = "from com.js.oa.info.infomanager.po.InfoProFilePO aaa";
          String whereSQL = "where aaa.isNew=1 and (aaa.viewMan like '%" + userId + "%' or aaa.viewMan like '%" + orgId + "%')";
          if (b)
            whereSQL = "where 1=1 and aaa.isNew=1"; 
          if (fileNum != null && !"".equals(fileNum))
            whereSQL = String.valueOf(whereSQL) + " and aaa.fileNum like '%" + fileNum + "%'"; 
          if (fileSaveName != null && !"".equals(fileSaveName))
            whereSQL = String.valueOf(whereSQL) + " and aaa.fileName like '%" + fileSaveName + "%'"; 
          whereSQL = String.valueOf(whereSQL) + " order by aaa.fileName";
          int currentPage = offset / pageSize + 1;
          Page page = new Page(viewSQL, fromSQL, whereSQL);
          page.setPageSize(pageSize);
          page.setcurrentPage(currentPage);
          List list = page.getResultList();
          String recordCount = String.valueOf(page.getRecordCount());
          request.setAttribute("recordCount", recordCount);
          request.setAttribute("maxPageItems", String.valueOf(pageSize));
          request.setAttribute("pageParameters", "action,channelType,userChannelName,orderBy,depart,styleId,sortType,userId");
          request.setAttribute("userFileList", list);
        } 
      } else if ("openInfo".equals(action)) {
        tag = "openInfo";
        Long id = Long.valueOf(request.getParameter("fileId"));
        InfoProFilePO infoForView = infoProFileBD.getInfoProFilePOById(id);
        request.setAttribute("infoForView", infoForView);
        Long filePreId = infoForView.getFilePreId();
        List allVersionList = infoProFileBD.getAllVersion(filePreId);
        request.setAttribute("allVersionList", allVersionList);
        String reviewId = infoForView.getReviewRecordId();
        String reviseId = infoForView.getReviseRecordId();
        if (reviewId != null && !"".equals(reviewId)) {
          List reviewRecordList = (new WorkFlowButtonBD()).getAllDealWithLog(reviewId.split(";")[0], reviewId.split(";")[1], reviewId.split(";")[2]);
          request.setAttribute("reviewRecordList", reviewRecordList);
          WFWorkFlowProcessPO wfWorkFlowProcess = infoProFileBD.getWFWorkFlowProcessPOById(Long.valueOf(reviewId.split(";")[0]));
          request.setAttribute("reviewProcessName", wfWorkFlowProcess.getWorkFlowProcessName());
        } 
        if (reviseId != null && !"".equals(reviseId)) {
          List reviseRecordList = (new WorkFlowButtonBD()).getAllDealWithLog(reviseId.split(";")[0], reviseId.split(";")[1], reviseId.split(";")[2]);
          request.setAttribute("reviseRecordList", reviseRecordList);
          WFWorkFlowProcessPO wfWorkFlowProcess = infoProFileBD.getWFWorkFlowProcessPOById(Long.valueOf(reviseId.split(";")[0]));
          request.setAttribute("reviseProcessName", wfWorkFlowProcess.getWorkFlowProcessName());
        } 
      } else if ("reviewList".equals(action)) {
        tag = "reviewList";
        InfoProFileEJBBean iffb = new InfoProFileEJBBean();
        boolean b = iffb.hasRoleId(userId);
        String fileNum = request.getParameter("fileNum");
        String fileSaveName = request.getParameter("fileSaveName");
        int pageSize = 15;
        int offset = 0;
        if (request.getParameter("pager.offset") != null)
          offset = Integer.parseInt(request
              .getParameter("pager.offset")); 
        String viewSQL = "select aaa.fileId,aaa.proFile,aaa.fileName,aaa.fileNum,aaa.character,aaa.fileDate,aaa.reviewDate,aaa.department,aaa.author,aaa.viewMan,aaa.filePreId,aaa.version,aaa.isNew,aaa.filePath,aaa.appendPath,aaa.fileViewName,aaa.fileAppendName";
        String fromSQL = "from com.js.oa.info.infomanager.po.InfoProFilePO aaa";
        String whereSQL = "where (aaa.viewMan like '%" + userId + "%' or aaa.viewMan like '%" + orgId + "%')";
        if (b)
          whereSQL = "where 1=1"; 
        if (fileNum != null && !"".equals(fileNum))
          whereSQL = String.valueOf(whereSQL) + " and aaa.fileNum like '%" + fileNum + "%'"; 
        if (fileSaveName != null && !"".equals(fileSaveName))
          whereSQL = String.valueOf(whereSQL) + " and aaa.fileName like '%" + fileSaveName + "%'"; 
        whereSQL = String.valueOf(whereSQL) + " order by aaa.fileName,aaa.fileDate";
        int currentPage = offset / pageSize + 1;
        Page page = new Page(viewSQL, fromSQL, whereSQL);
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List list = page.getResultList();
        String recordCount = String.valueOf(page.getRecordCount());
        request.setAttribute("recordCount", recordCount);
        request.setAttribute("maxPageItems", String.valueOf(pageSize));
        request.setAttribute("pageParameters", "action,channelType,userChannelName,orderBy,depart,styleId,sortType,userId");
        request.setAttribute("proFileList", list);
      } else if ("modify".equals(action)) {
        tag = "modify";
        Long id = Long.valueOf(request.getParameter("fileId"));
        request.setAttribute("fileId", id);
        InfoProFileEJBBean iffb = new InfoProFileEJBBean();
        InfoProFilePO infoToModify = infoProFileBD.getInfoProFilePOById(id);
        infoProFileActionForm.setFileName(infoToModify.getFileName());
        infoProFileActionForm.setFileNum(infoToModify.getFileNum());
        infoProFileActionForm.setCharacter(infoToModify.getCharacter());
        infoProFileActionForm.setVersion(infoToModify.getVersion());
        infoProFileActionForm.setDepartment(infoToModify.getDepartment().split(";")[0]);
        infoProFileActionForm.setDepartmentId(infoToModify.getDepartment().split(";")[1]);
        infoProFileActionForm.setAuthor(infoToModify.getAuthor().split(";")[0]);
        infoProFileActionForm.setAuthorId(infoToModify.getAuthor().split(";")[1]);
        String[] viewManArr = (String[])null;
        String viewMan = "";
        if (infoToModify.getViewMan().startsWith("$")) {
          viewManArr = infoToModify.getViewMan().replace("$$", ";").replace("$", "").split(";");
          for (int i = 0; i < viewManArr.length; i++) {
            if (i == viewManArr.length - 1) {
              viewMan = String.valueOf(viewMan) + iffb.getEmployeeVOById(viewManArr[i]).getEmpName();
            } else {
              viewMan = String.valueOf(viewMan) + iffb.getEmployeeVOById(viewManArr[i]).getEmpName() + ",";
            } 
          } 
          request.setAttribute("viewMan", viewMan);
          request.setAttribute("viewManId", infoToModify.getViewMan());
        } else if (infoToModify.getViewMan().startsWith("*")) {
          viewManArr = infoToModify.getViewMan().replace("**", ";").replace("*", "").split(";");
          for (int i = 0; i < viewManArr.length; i++) {
            if (i == viewManArr.length - 1) {
              viewMan = String.valueOf(viewMan) + iffb.getOrgNameById(viewManArr[i]);
            } else {
              viewMan = String.valueOf(viewMan) + iffb.getOrgNameById(viewManArr[i]) + ",";
            } 
          } 
          request.setAttribute("viewMan", viewMan);
          request.setAttribute("viewManId", infoToModify.getViewMan());
        } 
        String fileDate = infoToModify.getFileDate().toString();
        fileDate = fileDate.split(" ")[0];
        String reviewDate = infoToModify.getReviewDate().toString();
        reviewDate = reviewDate.split(" ")[0];
        request.setAttribute("fileDate", fileDate);
        request.setAttribute("reviewDate", reviewDate);
        String fileSaveName = infoToModify.getFilePath();
        String fileRealName = infoToModify.getFileViewName();
        request.setAttribute("fileSaveName", fileSaveName);
        request.setAttribute("fileRealName", fileRealName);
        String appendPath = infoToModify.getAppendPath();
        if (appendPath != null) {
          String fileAppendName = infoToModify.getFileAppendName();
          request.setAttribute("appendPath", appendPath);
          request.setAttribute("fileAppendName", fileAppendName);
        } 
      } else {
        if ("trueModify".equals(action)) {
          String fileId = request.getParameter("fileId");
          InfoProFilePO infoFromDB = infoProFileBD.getInfoProFilePOById(Long.valueOf(fileId));
          InfoProFilePO infoForUpdate = setPO(infoProFileActionForm, request);
          infoForUpdate.setFileId(Long.valueOf(fileId));
          infoForUpdate.setIsNew(infoFromDB.getIsNew());
          infoForUpdate.setFilePreId(infoFromDB.getFilePreId());
          infoForUpdate.setIsAppend("0");
          infoProFileBD.updateInfoProFilePO(infoForUpdate);
          ActionForward actionForward = new ActionForward();
          actionForward.setPath("/InfoProFileAction.do?action=userList&isNew=1&isExport=no");
          actionForward.setRedirect(true);
          return actionForward;
        } 
        if ("getReviewAjax".equals(action)) {
          String id = request.getParameter("id");
          InfoProFilePO po = infoProFileBD.getInfoProFilePOById(Long.valueOf(id));
          po.setEditDate(po.getFileDate().toString());
          JSONArray jsonArray = JSONArray.fromObject(po);
          httpServletResponse.setContentType("text/html;charset=utf-8");
          PrintWriter out = httpServletResponse.getWriter();
          out.print(jsonArray.toString());
          System.out.println(jsonArray.toString());
        } 
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public InfoProFilePO setPO(InfoProFileActionForm infoProFileActionForm, HttpServletRequest request) {
    String viewManid = request.getParameter("viewManId");
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    InfoProFilePO infoProFilePO = new InfoProFilePO();
    infoProFilePO.setProFile(infoProFileActionForm.getProFile());
    infoProFilePO.setFileName(infoProFileActionForm.getFileName());
    infoProFilePO.setFileNum(infoProFileActionForm.getFileNum());
    infoProFilePO.setCharacter(infoProFileActionForm.getCharacter());
    infoProFilePO.setVersion(infoProFileActionForm.getVersion());
    infoProFilePO.setFileDate(new Date(infoProFileActionForm.getFileDate()));
    infoProFilePO.setReviewDate(new Date(infoProFileActionForm.getReviewDate()));
    infoProFilePO.setDepartment(String.valueOf(infoProFileActionForm.getDepartment()) + ";" + infoProFileActionForm.getDepartmentId());
    infoProFilePO.setAuthor(String.valueOf(infoProFileActionForm.getAuthor()) + ";" + infoProFileActionForm.getAuthorId());
    infoProFilePO.setViewMan(viewManid);
    String[] fileViewName = request.getParameterValues(
        "fileViewName");
    String[] fileAppendName = request.getParameterValues(
        "fileAppendName");
    String[] fileViewSaveName = request.getParameterValues(
        "fileViewSaveName");
    String[] fileAppendSaveName = request.getParameterValues(
        "fileAppendSaveName");
    if (fileViewSaveName != null)
      infoProFilePO.setFilePath(fileViewSaveName[0]); 
    if (fileAppendSaveName != null) {
      String appendPath = "";
      for (int i = 0; i < fileAppendSaveName.length; i++)
        appendPath = String.valueOf(appendPath) + fileAppendSaveName[i] + ","; 
      appendPath = appendPath.substring(0, appendPath.length() - 1);
      infoProFilePO.setAppendPath(appendPath);
    } 
    if (fileViewName != null)
      infoProFilePO.setFileViewName(fileViewName[0]); 
    if (fileAppendName != null) {
      String fileAppendNameStr = "";
      for (int i = 0; i < fileAppendName.length; i++)
        fileAppendNameStr = String.valueOf(fileAppendNameStr) + fileAppendName[i] + ","; 
      fileAppendNameStr = fileAppendNameStr.substring(0, fileAppendNameStr.length() - 1);
      infoProFilePO.setFileAppendName(fileAppendNameStr);
    } 
    return infoProFilePO;
  }
  
  public InfoProFileReviewPO setInfoProFileReviewPO(Object[] obj) {
    InfoProFileReviewPO iprp = new InfoProFileReviewPO();
    iprp.setFileId(Long.valueOf(obj[0].toString()));
    iprp.setFileName(obj[2].toString());
    iprp.setVersion(obj[11].toString());
    iprp.setFileNum(obj[3].toString());
    String[] reviewDate = obj[6].toString().split(" ");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      iprp.setReviewDate(sdf.parse(reviewDate[0]));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return iprp;
  }
}
