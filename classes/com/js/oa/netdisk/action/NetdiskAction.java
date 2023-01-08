package com.js.oa.netdisk.action;

import com.js.oa.netdisk.po.NetDiskPO;
import com.js.oa.netdisk.service.NetdiskBD;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NetdiskAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String tag = "";
    String databaseType = SystemCommon.getDatabaseType();
    String nowStr = "now()";
    if (databaseType.indexOf("oracle") >= 0 || databaseType.indexOf("sqlserver") >= 0)
      nowStr = "sysdate"; 
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String userAccount = session.getAttribute("userAccount").toString();
    String domainId = session.getAttribute("domainId").toString();
    NetdiskBD netdiskBD = new NetdiskBD();
    String action = "";
    if (!"".equals(httpServletRequest.getParameter(action)))
      action = httpServletRequest.getParameter("action"); 
    String fileSaveName = "";
    String choicetime = "";
    String startTaskBeginTime = "";
    String endTaskBeginTime = "";
    String choicesize = "";
    String filesizeBegin = "";
    String filesizeEnd = "";
    String fileType = "";
    String currentid = "";
    if ("checkFolderName".equals(action)) {
      String name = httpServletRequest.getParameter("foldername");
      String id = httpServletRequest.getParameter("currenid");
      String idString = httpServletRequest.getParameter("idString");
      String type = httpServletRequest.getParameter("type");
      StringBuffer xml = new StringBuffer(1024);
      boolean flag = netdiskBD.checkFolderName(name.trim(), id, idString, type, userId);
      httpServletResponse.setContentType("text/xml;charset=GBK");
      PrintWriter out = httpServletResponse.getWriter();
      xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
      xml.append("<result>\n");
      xml.append("  <name>" + flag + "</name>\n");
      xml.append("</result>\n");
      out.print(xml.toString());
      out.close();
      return null;
    } 
    if ("list".equals(action)) {
      currentid = "0";
      String copyitem = httpServletRequest.getParameter("copyitem");
      String flaggs = "";
      if (httpServletRequest.getParameter("flaggs") != null)
        flaggs = httpServletRequest.getParameter("flaggs"); 
      httpServletRequest.setAttribute("flags", flaggs);
      httpServletRequest.setAttribute("copyitem", copyitem);
      String copyorcut = httpServletRequest.getParameter("copyorcut");
      httpServletRequest.setAttribute("copyorcut", copyorcut);
      if (httpServletRequest.getParameter("currentid") != null && !"null".equals(httpServletRequest.getParameter("currentid")))
        currentid = httpServletRequest.getParameter("currentid"); 
      if ("0".equals(currentid)) {
        httpServletRequest.setAttribute("fileidstring", "0");
      } else {
        List<NetDiskPO> list = netdiskBD.getinfodetail(currentid);
        NetDiskPO netPO = list.get(0);
        httpServletRequest.setAttribute("fileidstring", netPO.getFileIdString());
      } 
      String result = netdiskBD.checksharetype(currentid);
      httpServletRequest.setAttribute("currentid", currentid);
      String viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote,netdisk.fileIdString";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = " where netdisk.fileFatherId=" + currentid + " order by netdisk.fileCreatedTime desc,netdisk.fileSize";
      if ("0".equals(currentid))
        whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId=" + currentid + " and netdisk.fileOwnId=" + userId + 
          " order by netdisk.fileCreatedTime desc,netdisk.fileType"; 
      lists(httpServletRequest, viewSQL, fromSQL, whereSQL, currentid);
      tag = "list";
    } 
    if ("listshared".equals(action)) {
      String flaggs = "";
      if (httpServletRequest.getParameter("flaggs") != null)
        flaggs = httpServletRequest.getParameter("flaggs"); 
      httpServletRequest.setAttribute("flags", flaggs);
      String ishared = "";
      if (httpServletRequest.getParameter("toUserId") != null)
        httpServletRequest.setAttribute("toUserId", httpServletRequest.getParameter("toUserId")); 
      httpServletRequest.setAttribute("cancopy", "1");
      currentid = "0";
      String copyitem = httpServletRequest.getParameter("copyitem");
      httpServletRequest.setAttribute("copyitem", copyitem);
      String copyorcut = httpServletRequest.getParameter("copyorcut");
      httpServletRequest.setAttribute("copyorcut", copyorcut);
      if (httpServletRequest.getParameter("currentid") != null && !"null".equals(httpServletRequest.getParameter("currentid")))
        currentid = httpServletRequest.getParameter("currentid"); 
      httpServletRequest.setAttribute("currentid", currentid);
      String viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote,netdisk.fileShareToNameWrite,netdisk.fileShareToOrgWrite,netdisk.fileShareToGroupWrite,netdisk.fileShareToEmpWrite ";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId=" + currentid + " order by netdisk.fileSize,netdisk.fileCreatedTime desc";
      lists(httpServletRequest, viewSQL, fromSQL, whereSQL, currentid);
      List list = netdiskBD.getWriteAccess(userId, currentid, orgId, session.getAttribute("orgIdString").toString());
      if (list.size() > 0) {
        httpServletRequest.setAttribute("ishared", "2");
        httpServletRequest.setAttribute("cando", "2");
      } else if (httpServletRequest.getParameter("cando") != null && "2".equals(httpServletRequest.getParameter("cando"))) {
        httpServletRequest.setAttribute("ishared", "2");
        httpServletRequest.setAttribute("cando", "2");
      } 
      tag = "listshared";
    } 
    if ("sharedlist".equals(action)) {
      currentid = "0";
      String ishared = "";
      if (httpServletRequest.getParameter("ishared") != null)
        ishared = httpServletRequest.getParameter("ishared"); 
      httpServletRequest.setAttribute("ishared", ishared);
      httpServletRequest.setAttribute("cando", ishared);
      httpServletRequest.setAttribute("cancopy", "1");
      String copyitem = httpServletRequest.getParameter("copyitem");
      httpServletRequest.setAttribute("copyitem", copyitem);
      String copyorcut = httpServletRequest.getParameter("copyorcut");
      httpServletRequest.setAttribute("copyorcut", copyorcut);
      if (httpServletRequest.getParameter("currentid") != null && 
        !"null".equals(httpServletRequest.getParameter("currentid")))
        currentid = httpServletRequest.getParameter("currentid"); 
      httpServletRequest.setAttribute("currentid", currentid);
      String readerWhere = netdiskBD.getInfoReader(session.getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString").toString(), 
          "netdisk");
      String viewSQL = " netdisk.fileId,netdisk.fileIdString ";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = "";
      if (httpServletRequest.getParameter("toUserId") != null) {
        String toUserId = httpServletRequest.getParameter("toUserId");
        if (readerWhere.equals(""))
          readerWhere = " 1=1 "; 
        whereSQL = String.valueOf(whereSQL) + " where netdisk.domainId=" + domainId + " and netdisk.fileOwnId=" + toUserId + "  and netdisk.fileExtName='DIR' and (" + readerWhere + ")";
        whereSQL = String.valueOf(whereSQL) + " and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (";
        whereSQL = String.valueOf(whereSQL) + nowStr + " between netdisk.readTimeFrom and netdisk.readTimeTo))";
        whereSQL = String.valueOf(whereSQL) + " or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (" + nowStr + " between netdisk.writeTimeFrom and netdisk.writeTimeTo)))";
        whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileFatherId";
        httpServletRequest.setAttribute("toUserId", toUserId);
        tag = "usersharedlist";
      } else {
        if (!readerWhere.equals("")) {
          whereSQL = " where netdisk.domainId=" + domainId + "  and netdisk.fileExtName='DIR' and (" + readerWhere + ")";
          whereSQL = String.valueOf(whereSQL) + " and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (" + nowStr + "  between netdisk.readTimeFrom and netdisk.readTimeTo ))";
          whereSQL = String.valueOf(whereSQL) + " or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (" + nowStr + "  between netdisk.writeTimeFrom and netdisk.writeTimeTo )))";
          whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileFatherId";
        } else {
          readerWhere = " 1=1 ";
          whereSQL = " where netdisk.domainId=" + domainId + "  and netdisk.fileExtName='DIR' and (" + readerWhere + ")";
          whereSQL = String.valueOf(whereSQL) + " and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (" + nowStr + "  between netdisk.readTimeFrom and netdisk.readTimeTo))";
          whereSQL = String.valueOf(whereSQL) + " or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (" + nowStr + " between netdisk.writeTimeFrom and netdisk.writeTimeTo)))";
          whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileFatherId";
        } 
        tag = "sharedlist";
      } 
      Page page = new Page(viewSQL, fromSQL, whereSQL);
      page.setPageSize(100000);
      page.setcurrentPage(1);
      List<Object[]> list = page.getResultList();
      String idString = "-1,";
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String[] idStrings = obj[1].replace("null", "$0$").replace("$$", ",").replace("$", "").split(",");
        boolean f = true;
        byte b;
        int j;
        String[] arrayOfString1;
        for (j = (arrayOfString1 = idStrings).length, b = 0; b < j; ) {
          String s = arrayOfString1[b];
          if (!s.equals("0") && idString.contains("," + s + ",")) {
            f = false;
            break;
          } 
          b++;
        } 
        if (f)
          idString = String.valueOf(idString) + obj[0] + ","; 
      } 
      idString = String.valueOf(idString) + "-11";
      viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote,netdisk.fileShareToNameWrite,netdisk.fileShareToOrgWrite,netdisk.fileShareToGroupWrite,netdisk.fileShareToEmpWrite ";
      whereSQL = " where netdisk.fileId in (" + idString + ") order by netdisk.fileSize,netdisk.fileCreatedTime desc";
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    } 
    if ("uplist".equals(action)) {
      currentid = "0";
      if (httpServletRequest.getParameter("currentid") != null && !"null".equals(httpServletRequest.getParameter("currentid")))
        currentid = httpServletRequest.getParameter("currentid"); 
      String res = netdiskBD.getupuse(currentid);
      httpServletRequest.setAttribute("currentid", res);
      String viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote,netdisk.fileShareToNameWrite,netdisk.fileShareToOrgWrite,netdisk.fileShareToGroupWrite,netdisk.fileShareToEmpWrite ";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = "";
      if ("0".equals(currentid) || "0".equals(res)) {
        if ("0".equals(currentid))
          whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId=" + currentid + " and netdisk.fileOwnId=" + userId + " order by netdisk.fileType,netdisk.fileCreatedTime desc"; 
        if ("0".equals(res))
          whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId=" + res + " and netdisk.fileOwnId=" + userId + " order by netdisk.fileType,netdisk.fileCreatedTime desc"; 
      } else {
        whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId in(select aaa.fileFatherId from com.js.oa.netdisk.po.NetDiskPO aaa where aaa.fileId=" + currentid + ") order by netdisk.fileType,netdisk.fileCreatedTime desc";
      } 
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
      tag = "list";
    } 
    if ("addfolder".equals(action)) {
      String foldername = httpServletRequest.getParameter("foldername");
      String currenid = httpServletRequest.getParameter("currenid");
      String flags = httpServletRequest.getParameter("flags");
      String fileidstring = httpServletRequest.getParameter("fileidstring");
      boolean result = false;
      result = netdiskBD.addfolder(userId, userName, foldername, currenid, userAccount, fileidstring, domainId);
      if ("exit".equals(flags)) {
        httpServletRequest.setAttribute("flag", "reload");
      } else {
        httpServletRequest.setAttribute("flag", "conn");
      } 
      tag = "successfolder";
    } 
    if ("deletefile".equals(action)) {
      String fileid = httpServletRequest.getParameter("fileid");
      String currenid = httpServletRequest.getParameter("currenid");
      String realpath = httpServletRequest.getRealPath("/");
      boolean result = false;
      result = netdiskBD.deletemydisk(fileid, userAccount, realpath);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=list&currentid=" + currenid);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("copy".equals(action)) {
      String currenid = httpServletRequest.getParameter("currenid");
      String copyitem = httpServletRequest.getParameter("copyitem");
      String copyorcut = httpServletRequest.getParameter("copyorcut");
      session.setAttribute("copyitem", copyitem);
      session.setAttribute("copyorcut", copyorcut);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=list&currentid=" + currenid + "&copyitem=" + copyitem + "&copyorcut=" + copyorcut);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("copyshare".equals(action)) {
      String copyitem = httpServletRequest.getParameter("copyitem");
      String copyorcut = httpServletRequest.getParameter("copyorcut");
      String currenid = httpServletRequest.getParameter("currenid");
      String ishared = httpServletRequest.getParameter("ishared");
      session.setAttribute("copyitem", copyitem);
      session.setAttribute("copyorcut", copyorcut);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=sharedlist&currentid=" + currenid + "&ishared=" + ishared);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("parse".equals(action)) {
      String currenid = httpServletRequest.getParameter("currenid");
      String copyitem = session.getAttribute("copyitem").toString();
      String copyorcut = session.getAttribute("copyorcut").toString();
      session.removeAttribute("copyitem");
      session.removeAttribute("copyorcut");
      boolean result = false;
      result = netdiskBD.copyorcut(currenid, copyitem, copyorcut, userAccount, domainId);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=list&currentid=" + currenid + "&copyitem=" + copyitem + "&copyorcut=" + copyorcut);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("parseshare".equals(action)) {
      String currenid = httpServletRequest.getParameter("currenid");
      String copyitem = session.getAttribute("copyitem").toString();
      String copyorcut = session.getAttribute("copyorcut").toString();
      String ishared = httpServletRequest.getParameter("ishared");
      session.removeAttribute("copyitem");
      session.removeAttribute("copyorcut");
      boolean result = false;
      result = netdiskBD.copyorcutshare(currenid, copyitem, copyorcut, userId, userName, userAccount, domainId);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=listshared&currentid=" + currenid + "&ishared=" + ishared);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("shared".equals(action)) {
      String shareds = "1";
      String copyitem = httpServletRequest.getParameter("copyitem");
      String sharetype = "0";
      String ReaderName = "";
      String ReaderId = httpServletRequest.getParameter("informationReaderId");
      String ReaderWriterId = httpServletRequest.getParameter("informationReaderWriterId");
      String ReaderWriterName = "";
      String readStartDate = httpServletRequest.getParameter("read_start_date").replaceAll("/", "-");
      String readHourStart = httpServletRequest.getParameter("readHourStart");
      String readMiniStart = httpServletRequest.getParameter("readMiniStart");
      String readTimeStart = String.valueOf(readStartDate) + " " + readHourStart + ":" + readMiniStart + ":00";
      String readEndDate = httpServletRequest.getParameter("read_end_date").replaceAll("/", "-");
      String readHourEnd = httpServletRequest.getParameter("readHourEnd");
      String readMiniEnd = httpServletRequest.getParameter("readMiniEnd");
      String readTimeEnd = String.valueOf(readEndDate) + " " + readHourEnd + ":" + readMiniEnd + ":00";
      String writeStartDate = httpServletRequest.getParameter("write_start_date").replaceAll("/", "-");
      String writeHourStart = httpServletRequest.getParameter("writeHourStart");
      String writeMiniStart = httpServletRequest.getParameter("writeMiniStart");
      String writeTimeStart = String.valueOf(writeStartDate) + " " + writeHourStart + ":" + writeMiniStart + ":00";
      String writeEndDate = httpServletRequest.getParameter("write_end_date").replaceAll("/", "-");
      String writeHourEnd = httpServletRequest.getParameter("writeHourEnd");
      String writeMiniEnd = httpServletRequest.getParameter("writeMiniEnd");
      String writeTimeEnd = String.valueOf(writeEndDate) + " " + writeHourEnd + ":" + writeMiniEnd + ":00";
      String Reader = "";
      String ReaderOrg = "";
      String ReaderGroup = "";
      String ReaderWriter = "";
      String ReaderWriterOrg = "";
      String ReaderWriterGroup = "";
      if (ReaderId != null && !ReaderId.equals("")) {
        sharetype = "1";
        ReaderName = httpServletRequest.getParameter("informationReaderName");
        if (ReaderId.indexOf("$") >= 0)
          Reader = ReaderId.substring(ReaderId.indexOf("$"), ReaderId.lastIndexOf("$") + 1); 
        if (ReaderId.indexOf("*") >= 0)
          ReaderOrg = ReaderId.substring(ReaderId.indexOf("*"), ReaderId.lastIndexOf("*") + 1); 
        if (ReaderId.indexOf("@") >= 0)
          ReaderGroup = ReaderId.substring(ReaderId.indexOf("@"), ReaderId.lastIndexOf("@") + 1); 
        netdiskBD.shared(copyitem, sharetype, Reader, ReaderOrg, ReaderGroup, ReaderName, shareds, readTimeStart, readTimeEnd, writeTimeStart, writeTimeEnd);
      } else {
        sharetype = "3";
        netdiskBD.shared(copyitem, sharetype, Reader, ReaderOrg, ReaderGroup, ReaderName, shareds, readTimeStart, readTimeEnd, writeTimeStart, writeTimeEnd);
      } 
      if (ReaderWriterId != null && !ReaderWriterId.equals("")) {
        sharetype = "2";
        ReaderWriterName = httpServletRequest.getParameter("informationReaderWriterName");
        if (ReaderWriterId.indexOf("$") >= 0)
          ReaderWriter = ReaderWriterId.substring(ReaderWriterId.indexOf("$"), ReaderWriterId.lastIndexOf("$") + 1); 
        if (ReaderWriterId.indexOf("*") >= 0)
          ReaderWriterOrg = ReaderWriterId.substring(ReaderWriterId.indexOf("*"), ReaderWriterId.lastIndexOf("*") + 1); 
        if (ReaderWriterId.indexOf("@") >= 0)
          ReaderWriterGroup = ReaderWriterId.substring(ReaderWriterId.indexOf("@"), ReaderWriterId.lastIndexOf("@") + 1); 
        netdiskBD.shared(copyitem, sharetype, ReaderWriter, ReaderWriterOrg, ReaderWriterGroup, ReaderWriterName, shareds, readTimeStart, readTimeEnd, writeTimeStart, writeTimeEnd);
      } else {
        sharetype = "4";
        netdiskBD.shared(copyitem, sharetype, ReaderWriter, ReaderWriterOrg, ReaderWriterGroup, ReaderWriterName, shareds, readTimeStart, readTimeEnd, writeTimeStart, writeTimeEnd);
      } 
      if ((ReaderId == null || ReaderId.equals("")) && (ReaderWriterId == null || ReaderWriterId.equals("")))
        netdiskBD.unshared(copyitem); 
      String folder = httpServletRequest.getParameter("folder");
      String title = String.valueOf(userName) + "给您共享了:" + folder + "文件夹";
      String url = "/jsoa/NetdiskAction.do?action=listshared&currentid=" + copyitem + "&copyitem=null&copyorcut=null&ishared=";
      String user = String.valueOf(Reader) + ReaderWriter;
      String org = String.valueOf(ReaderOrg) + ReaderWriterOrg;
      String group = String.valueOf(ReaderGroup) + ReaderWriterGroup;
      String userTemp = getMoveRepid(user, org, group);
      String userIdString = "";
      if (userTemp.indexOf("$") >= 0)
        userIdString = userTemp.substring(userTemp.indexOf("$"), userTemp.lastIndexOf("$") + 1); 
      String orgIdString = "";
      if (userTemp.indexOf("*") >= 0)
        orgIdString = userTemp.substring(userTemp.indexOf("*"), userTemp.lastIndexOf("*") + 1); 
      String groupIdString = "";
      if (userTemp.indexOf("@") >= 0)
        groupIdString = userTemp.substring(userTemp.indexOf("@"), userTemp.lastIndexOf("@") + 1); 
      String toUserIds = netdiskBD.getCanReadUserIds(userIdString, orgIdString, groupIdString).replaceAll(String.valueOf(userId) + ",", "").replaceAll(userId, "");
      RemindUtil.sendMessageToUsers(title, url, toUserIds, "Info", new Date(), new Date("2050/1/1"));
      httpServletRequest.setAttribute("flag", "reload");
      tag = "successshared";
    } 
    if ("rename".equals(action)) {
      String netdiskid = httpServletRequest.getParameter("netdiskid");
      String rename = httpServletRequest.getParameter("rename");
      boolean result = false;
      result = netdiskBD.rename(rename, netdiskid);
      httpServletRequest.setAttribute("flag", "reload");
      tag = "successrename";
    } 
    if ("searchthings".equals(action)) {
      fileSaveName = httpServletRequest.getParameter("fileSaveName");
      choicetime = httpServletRequest.getParameter("choicetime");
      startTaskBeginTime = httpServletRequest.getParameter("startTaskBeginTime");
      endTaskBeginTime = httpServletRequest.getParameter("endTaskBeginTime");
      choicesize = httpServletRequest.getParameter("choicesize");
      filesizeBegin = httpServletRequest.getParameter("filesizeBegin");
      filesizeEnd = httpServletRequest.getParameter("filesizeEnd");
      fileType = httpServletRequest.getParameter("fileType");
      String viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote,netdisk.fileShareToNameWrite,netdisk.fileShareToOrgWrite,netdisk.fileShareToGroupWrite,netdisk.fileShareToEmpWrite ";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileOwnId=" + userId;
      if (!"".equals(fileSaveName))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileSaveName like '%" + fileSaveName + "%'"; 
      if (!"".equals(choicetime) && "1".equals(choicetime))
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + " and netdisk.fileCreatedTime between '" + startTaskBeginTime + " 00:00:00'  and '" + endTaskBeginTime + " 23:59:59'";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and netdisk.fileCreatedTime between JSDB.FN_STRTODATE('" + startTaskBeginTime + " 00:00:00','')  and JSDB.FN_STRTODATE('" + endTaskBeginTime + " 23:59:59','')";
        }  
      if (!"".equals(choicesize) && "1".equals(choicesize) && 
        !"".equals(filesizeBegin) && !"".equals(filesizeEnd))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileSize between " + filesizeBegin + " and " + filesizeEnd; 
      if (!"".equals(fileType))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileExtName like '%" + fileType + "%'"; 
      whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileSize,netdisk.fileCreatedTime desc";
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
      httpServletRequest.setAttribute("flags", "open");
      tag = "list";
    } 
    if ("searchsharedthings".equals(action)) {
      String cancopy = httpServletRequest.getParameter("cancopy");
      httpServletRequest.setAttribute("cancopy", cancopy);
      currentid = "0";
      if (httpServletRequest.getParameter("currentid") != null && !"null".equals(httpServletRequest.getParameter("currentid")))
        currentid = httpServletRequest.getParameter("currentid"); 
      fileSaveName = httpServletRequest.getParameter("fileSaveName");
      choicetime = httpServletRequest.getParameter("choicetime");
      startTaskBeginTime = httpServletRequest.getParameter("startTaskBeginTime");
      endTaskBeginTime = httpServletRequest.getParameter("endTaskBeginTime");
      choicesize = httpServletRequest.getParameter("choicesize");
      filesizeBegin = httpServletRequest.getParameter("filesizeBegin");
      filesizeEnd = httpServletRequest.getParameter("filesizeEnd");
      fileType = httpServletRequest.getParameter("fileType");
      String viewSQL = " netdisk.fileId,netdisk.fileName,netdisk.fileSaveName,netdisk.fileSaveNameMin,netdisk.fileExtName,netdisk.fileType,netdisk.fileOwn,netdisk.fileOwnId,netdisk.fileFatherId,netdisk.filePath,netdisk.fileIsShare,netdisk.fileShareToName,netdisk.fileShareToOrg,netdisk.fileShareToGroup,netdisk.fileShareToEmp,netdisk.fileCreatedTime,netdisk.fileSize,netdisk.fileNote";
      String fromSQL = "com.js.oa.netdisk.po.NetDiskPO netdisk";
      String whereSQL = " where netdisk.domainId=" + domainId + " and netdisk.fileFatherId=" + currentid;
      if (httpServletRequest.getParameter("toUserId") != null)
        httpServletRequest.setAttribute("toUser", httpServletRequest.getParameter("toUserId")); 
      if (!"".equals(fileSaveName))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileSaveName like '%" + fileSaveName + "%'"; 
      if (!"".equals(choicetime) && "1".equals(choicetime))
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = String.valueOf(whereSQL) + " and netdisk.fileCreatedTime between '" + startTaskBeginTime + " 00:00:00'  and '" + endTaskBeginTime + " 23:59:59'";
        } else {
          whereSQL = String.valueOf(whereSQL) + " and netdisk.fileCreatedTime between JSDB.FN_STRTODATE('" + startTaskBeginTime + " 00:00:00','')  and JSDB.FN_STRTODATE('" + endTaskBeginTime + " 23:59:59','')";
        }  
      if (!"".equals(choicesize) && "1".equals(choicesize) && 
        !"".equals(filesizeBegin) && !"".equals(filesizeEnd))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileSize between " + filesizeBegin + " and " + filesizeEnd; 
      if (!"".equals(fileType))
        whereSQL = String.valueOf(whereSQL) + " and netdisk.fileExtName like '%" + fileType + "%'"; 
      String readerWhere = netdiskBD.getInfoReader(session.getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString").toString(), 
          "netdisk");
      if ("0".equals(currentid))
        if (httpServletRequest.getParameter("toUserId") != null) {
          StringBuffer hql = new StringBuffer("");
          String toUserId = httpServletRequest.getParameter("toUserId");
          if (readerWhere.equals(""))
            readerWhere = " 1=1 "; 
          hql.append(" and netdisk.fileOwnId=" + toUserId + "  and (" + readerWhere + ")");
          hql.append(" and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (").append(nowStr).append(" between netdisk.readTimeFrom and netdisk.readTimeTo))");
          hql.append(" or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (").append(nowStr).append(" between netdisk.writeTimeFrom and netdisk.writeTimeTo)))");
          hql.append(" order by netdisk.fileSize,netdisk.fileCreatedTime desc");
          whereSQL = String.valueOf(whereSQL) + hql.toString();
        } else if (!readerWhere.equals("")) {
          whereSQL = String.valueOf(whereSQL) + " and (" + readerWhere + ")";
          whereSQL = String.valueOf(whereSQL) + " and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (" + nowStr + "  between netdisk.readTimeFrom and netdisk.readTimeTo ))";
          whereSQL = String.valueOf(whereSQL) + " or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (" + nowStr + "  between netdisk.writeTimeFrom and netdisk.writeTimeTo )))";
          whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileSize,netdisk.fileCreatedTime desc";
        } else {
          readerWhere = " 1=1 ";
          whereSQL = String.valueOf(whereSQL) + " and (" + readerWhere + ")";
          whereSQL = String.valueOf(whereSQL) + " and (((netdisk.readTimeFrom is not null and netdisk.readTimeTo is not null) and (" + nowStr + "  between netdisk.readTimeFrom and netdisk.readTimeTo))";
          whereSQL = String.valueOf(whereSQL) + " or ((netdisk.writeTimeFrom is not null and netdisk.writeTimeTo is not null) and (" + nowStr + " between netdisk.writeTimeFrom and netdisk.writeTimeTo)))";
          whereSQL = String.valueOf(whereSQL) + " order by netdisk.fileSize,netdisk.fileCreatedTime desc";
        }  
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
      httpServletRequest.setAttribute("flags", "open");
      tag = "sharedlist";
    } 
    if ("deleteall".equals(action)) {
      currentid = httpServletRequest.getParameter("currentid");
      String copyitem = httpServletRequest.getParameter("copyitem");
      String realpath = httpServletRequest.getRealPath("/");
      boolean result = false;
      result = netdiskBD.deleteall(realpath, userAccount, copyitem);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=list&currentid=" + currentid);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("unshared".equals(action)) {
      currentid = httpServletRequest.getParameter("currenid");
      String id = httpServletRequest.getParameter("id");
      boolean result = false;
      result = netdiskBD.unshared(id);
      try {
        httpServletResponse.sendRedirect("/jsoa/NetdiskAction.do?action=list&currentid=" + currentid);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if ("modishared".equals(action)) {
      String id = httpServletRequest.getParameter("id");
      List<NetDiskPO> list = netdiskBD.getinfodetail(id);
      String sharedType = "";
      String shareName = "", shareNameRR = "";
      String emp = "", empRR = "";
      String org = "", orgRR = "";
      String group = "", groupRR = "";
      NetDiskPO netpo = list.get(0);
      if (netpo != null) {
        sharedType = netpo.getFileIsShare().toString();
        if (netpo.getFileShareToName() != null && !"".equals(netpo.getFileShareToName()))
          shareName = netpo.getFileShareToName(); 
        if (netpo.getFileShareToEmp() != null && !"".equals(netpo.getFileShareToEmp()))
          emp = netpo.getFileShareToEmp(); 
        if (netpo.getFileShareToOrg() != null && !"".equals(netpo.getFileShareToOrg()))
          org = netpo.getFileShareToOrg(); 
        if (netpo.getFileShareToGroup() != null && !"".equals(netpo.getFileShareToGroup()))
          group = netpo.getFileShareToGroup(); 
        if (netpo.getFileShareToOrgWrite() != null && !netpo.getFileShareToOrgWrite().equals(""))
          orgRR = netpo.getFileShareToOrgWrite(); 
        if (netpo.getFileShareToGroupWrite() != null && !netpo.getFileShareToGroupWrite().equals(""))
          groupRR = netpo.getFileShareToGroupWrite(); 
        if (netpo.getFileShareToEmpWrite() != null && !netpo.getFileShareToEmpWrite().equals(""))
          empRR = netpo.getFileShareToEmpWrite(); 
        if (netpo.getFileShareToNameWrite() != null && !netpo.getFileShareToNameWrite().equals(""))
          shareNameRR = netpo.getFileShareToNameWrite(); 
        if (netpo.getReadTimeFrom() != null && !netpo.getReadTimeFrom().equals("null"))
          httpServletRequest.setAttribute("readTimeFrom", netpo.getReadTimeFrom()); 
        if (netpo.getReadTimeTo() != null && !netpo.getReadTimeTo().equals("null"))
          httpServletRequest.setAttribute("readTimeTo", netpo.getReadTimeTo()); 
        if (netpo.getWriteTimeFrom() != null && !netpo.getWriteTimeTo().equals("null"))
          httpServletRequest.setAttribute("writeTimeFrom", netpo.getWriteTimeFrom()); 
        if (netpo.getWriteTimeTo() != null && !netpo.getWriteTimeTo().equals("null"))
          httpServletRequest.setAttribute("writeTimeTo", netpo.getWriteTimeTo()); 
      } 
      httpServletRequest.setAttribute("sharedType", sharedType);
      httpServletRequest.setAttribute("shareName", shareName);
      httpServletRequest.setAttribute("emp", emp);
      httpServletRequest.setAttribute("org", org);
      httpServletRequest.setAttribute("group", group);
      httpServletRequest.setAttribute("id", id);
      httpServletRequest.setAttribute("shareNameRR", shareNameRR);
      httpServletRequest.setAttribute("shareRRID", String.valueOf(orgRR) + groupRR + empRR);
      tag = "modishared";
    } 
    return actionMapping.findForward(tag);
  }
  
  private int list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String pageCount = String.valueOf(page.getPageCount());
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("mydisklist", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (httpServletRequest.getParameter("toUserId") != null) {
      httpServletRequest.setAttribute("toUserId", httpServletRequest.getParameter("toUserId"));
      httpServletRequest.setAttribute("pageParameters", "action,fileSaveName,choicetime,startTaskBeginTime,endTaskBeginTime,choicesize,filesizeBegin,filesizeEnd,fileType,toUserId");
    } else {
      httpServletRequest.setAttribute("pageParameters", "action,fileSaveName,choicetime,startTaskBeginTime,endTaskBeginTime,choicesize,filesizeBegin,filesizeEnd,fileType");
    } 
    return (list == null) ? 0 : list.size();
  }
  
  private void lists(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL, String currentid) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String pageCount = String.valueOf(page.getPageCount());
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("mydisklist", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,fileSaveName,choicetime,startTaskBeginTime,endTaskBeginTime,choicesize,filesizeBegin,filesizeEnd,fileType,currentid");
  }
  
  private String getMoveRepid(String userIdString, String orgIdString, String groupIdString) {
    StringBuffer readerTemp = new StringBuffer("");
    if (orgIdString.indexOf("*") >= 0) {
      String[] readerArray = orgIdString.substring(1, orgIdString.length() - 1).split("\\*\\*");
      for (int i = 0; i < readerArray.length; i++) {
        if (readerTemp.indexOf(readerArray[i]) == -1)
          readerTemp.append("*" + readerArray[i] + "*"); 
      } 
    } 
    if (groupIdString.indexOf("@") >= 0) {
      String[] readerArray = groupIdString.substring(1, groupIdString.length() - 1).split("\\@\\@");
      for (int i = 0; i < readerArray.length; i++) {
        if (readerTemp.indexOf(readerArray[i]) == -1)
          readerTemp.append("@" + readerArray[i] + "@"); 
      } 
    } 
    if (userIdString.indexOf("$") >= 0) {
      String[] readerArray = userIdString.substring(1, userIdString.length() - 1).split("\\$\\$");
      for (int i = 0; i < readerArray.length; i++) {
        if (readerTemp.indexOf(readerArray[i]) == -1)
          readerTemp.append("$" + readerArray[i] + "$"); 
      } 
    } 
    return readerTemp.toString();
  }
}
