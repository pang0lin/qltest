package com.js.oa.message.action;

import com.js.oa.message.po.MsDescribePO;
import com.js.oa.message.po.MsInfoListPO;
import com.js.oa.message.service.MessageBD;
import com.js.oa.message.service.MsHistoryBD;
import com.js.sms.corp.CorpSMSAbstractFactory;
import com.js.sms.corp.CorpSMSTool;
import com.js.sms.corp.ICorpSMSServies;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ConversionString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageAction extends Action {
  private int pageSize = 15;
  
  public void ListDecribe(HttpServletRequest httpServletRequest, int offsetCopy, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and describePO.sendId=" + userID);
    whereSql.append(" order by describePO.describeId desc");
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("describePO.describeId,describePO.receiveMan,describePO.msContent,describePO.sendTime,describePO.receiveCode", "com.js.oa.message.po.MsDescribePO describePO", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("describeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,taxisName");
  }
  
  public void ListDecribe(HttpServletRequest httpServletRequest, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and describePO.sendId=" + userID);
    whereSql.append(" order by describePO.describeId desc");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("describePO.describeId,describePO.receiveMan,describePO.msContent,describePO.sendTime,describePO.receiveCode", "com.js.oa.message.po.MsDescribePO describePO", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("describeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void countMsgView(HttpServletRequest httpServletRequest, int offsetCopy) {
    String databaseType = SystemCommon.getDatabaseType();
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int offset = offsetCopy;
    String name = httpServletRequest.getParameter("name");
    name = name.replaceAll("'", "''");
    String checkbox = httpServletRequest.getParameter("checkbox");
    String start_date = String.valueOf(httpServletRequest.getParameter("start_date")) + " 00:00:00";
    String end_date = String.valueOf(httpServletRequest.getParameter("end_date")) + " 23:59:59";
    StringBuffer whereSql = new StringBuffer("where 1=1 and msgcountPO.sendCountMan !='系统管理员' ");
    if (name != null && !"".equals(name))
      whereSql.append(" and msgcountPO.sendCountMan like '%" + name + "%'"); 
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page();
    if (checkbox != null && "checked".equals(checkbox)) {
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql.append("and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " and msgcountPO.countDate between '" + start_date + "' and '" + end_date + "' ");
      } else {
        whereSql.append("and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " and msgcountPO.countDate between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') ");
      } 
      whereSql.append("group by msgcountPO.sendCountMan,msgcountPO.modelName ");
      page = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp ", whereSql.toString());
    } else {
      whereSql.append("and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " group by msgcountPO.sendCountMan,msgcountPO.modelName ");
      page = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp  ", whereSql.toString());
    } 
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    StringBuffer where = new StringBuffer("where 1=1 and msgcountPO.sendCountMan ='系统管理员'");
    where.append(" and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " group by msgcountPO.sendCountMan,msgcountPO.modelName ");
    Page pageSys = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp  ", where.toString());
    List<Object[]> listSys = pageSys.getResultList();
    String sysMsgCount = "0";
    if (listSys.size() != 0) {
      Object[] obj = listSys.get(0);
      sysMsgCount = obj[2].toString();
    } 
    httpServletRequest.setAttribute("countSysMsg", sysMsgCount);
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("countMsgList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,checkbox,start_date,end_date,name");
  }
  
  public void countMsgView(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int offset = 0;
    String name = httpServletRequest.getParameter("name");
    name = name.replaceAll("'", "''");
    String checkbox = httpServletRequest.getParameter("checkbox");
    String start_date = String.valueOf(httpServletRequest.getParameter("start_date")) + " 00:00:00";
    String end_date = String.valueOf(httpServletRequest.getParameter("end_date")) + " 23:59:59";
    StringBuffer whereSql = new StringBuffer("where 1=1 and msgcountPO.sendCountMan !='系统管理员'");
    if (name != null && !"".equals(name))
      whereSql.append(" and msgcountPO.sendCountMan like '%" + name + "%'"); 
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page();
    if (checkbox != null && "checked".equals(checkbox)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql.append(" and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " and msgcountPO.countDate between '" + start_date + "' and '" + end_date + "' ");
      } else {
        whereSql.append(" and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " and msgcountPO.countDate between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  ");
      } 
      whereSql.append(" group by msgcountPO.sendCountMan,msgcountPO.modelName ");
      page = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp ", whereSql.toString());
    } else {
      whereSql.append(" and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " group by msgcountPO.sendCountMan,msgcountPO.modelName ");
      page = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp ", whereSql.toString());
    } 
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    StringBuffer where = new StringBuffer("where 1=1 and msgcountPO.sendCountMan ='系统管理员'");
    where.append(" and emp.empId=msgcountPO.sendCountId and emp.domainId=" + domainId + " group by msgcountPO.sendCountMan,msgcountPO.modelName ");
    Page pageSys = new Page("msgcountPO.sendCountMan,msgcountPO.modelName,sum(msgcountPO.sumCount)", "com.js.oa.message.po.MsCountPO msgcountPO,com.js.system.vo.usermanager.EmployeeVO emp ", where.toString());
    List<Object[]> listSys = pageSys.getResultList();
    String sysMsgCount = "0";
    if (listSys.size() != 0) {
      Object[] obj = listSys.get(0);
      sysMsgCount = obj[2].toString();
    } 
    httpServletRequest.setAttribute("countSysMsg", sysMsgCount);
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("countMsgList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,checkbox,start_date,end_date,name");
  }
  
  public void desertedView(HttpServletRequest httpServletRequest, int offsetCopy, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and ((recievePO.sendId=" + userID + " and recievePO.sendTime is not null ) or ( recievePO.receiveId=" + userID + " and recievePO.receiveTime is not null ))");
    whereSql.append(" and  recievePO.msSign=0 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.receiveMan,recievePO.msContent,recievePO.sendTime,recievePO.receiveTime,recievePO.sendMan,recievePO.modelSend", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("deletedAllList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,taxisName");
  }
  
  public void desertedView(HttpServletRequest httpServletRequest, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and ((recievePO.sendId=" + userID + " and recievePO.sendTime is not null ) or ( recievePO.receiveId=" + userID + " and recievePO.receiveTime is not null ))");
    whereSql.append(" and  recievePO.msSign=0 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.receiveMan,recievePO.msContent,recievePO.sendTime,recievePO.receiveTime,recievePO.sendMan,recievePO.modelSend", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("deletedAllList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    MessageForm messageForm = (MessageForm)actionForm;
    MessageBD messageBD = new MessageBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String userName = session.getAttribute("userName").toString();
    Long orgId = new Long(session.getAttribute("orgId").toString());
    if ("countBook".equals(action)) {
      httpServletRequest.setAttribute("countResult", (new MessageBD()).getMsAccountInfo((session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString()));
      return actionMapping.findForward("countBook");
    } 
    if ("listView".equals(action)) {
      HashMap<Object, Object> listInfo = new HashMap<Object, Object>(10);
      listInfo = messageBD.listView(userID);
      if (listInfo != null) {
        httpServletRequest.setAttribute("describe", listInfo.get("describe"));
        httpServletRequest.setAttribute("receivelist", listInfo.get("receivelist"));
        httpServletRequest.setAttribute("sendlist", listInfo.get("sendlist"));
        httpServletRequest.setAttribute("dellist", listInfo.get("dellist"));
      } 
      return actionMapping.findForward("listView");
    } 
    if ("messageCount".equals(action)) {
      httpServletRequest.setAttribute("recordCount", "0");
      httpServletRequest.setAttribute("maxPageItems", "15");
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("messageCountJSP");
    } 
    if ("countMsgView".equals(action)) {
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      if (offsetCopy != 0) {
        countMsgView(httpServletRequest, offsetCopy);
      } else {
        countMsgView(httpServletRequest);
      } 
      return actionMapping.findForward("messageCountJSP");
    } 
    if ("sendedView".equals(action)) {
      try {
        sendedAndFlowList(httpServletRequest, userID);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      return actionMapping.findForward("sendedListJSP");
    } 
    if ("clearAllSended".equals(action)) {
      boolean result = messageBD.sendedClearToDescribe(userID);
      if (result) {
        try {
          sendedAndFlowList(httpServletRequest, userID);
        } catch (Exception ex1) {
          ex1.printStackTrace();
        } 
        return actionMapping.findForward("sendedListJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("delQuiteSendedBox".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = true;
      if (ids == null) {
        result = true;
      } else {
        result = messageBD.delRealSendedBoxBatch(ids);
      } 
      if (result) {
        try {
          sendedAndFlowList(httpServletRequest, userID);
        } catch (Exception ex1) {
          ex1.printStackTrace();
        } 
        return actionMapping.findForward("sendedListJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("delSendedBox".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = true;
      if (ids == null) {
        result = true;
      } else {
        result = messageBD.sendedToDescribe(ids);
      } 
      if (result) {
        try {
          sendedAndFlowList(httpServletRequest, userID);
        } catch (Exception ex1) {
          ex1.printStackTrace();
        } 
        return actionMapping.findForward("sendedListJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("receiveView".equals(action)) {
      receiveView(httpServletRequest, userID);
      return actionMapping.findForward("receiveViewJSP");
    } 
    if ("clearAllReceived".equals(action)) {
      boolean result = messageBD.receivedClearToDescribe(userID);
      if (result) {
        receiveView(httpServletRequest, userID);
        return actionMapping.findForward("receiveViewJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("delRealReceiveBox".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = messageBD.delRealReceiveBoxBatch(ids);
      if (result) {
        if (offsetCopy != 0) {
          receiveView(httpServletRequest, offsetCopy, userID);
        } else {
          receiveView(httpServletRequest, userID);
        } 
        return actionMapping.findForward("receiveViewJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("delReceiveBox".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = messageBD.receiveToDescribe(ids);
      if (result) {
        if (offsetCopy != 0) {
          receiveView(httpServletRequest, offsetCopy, userID);
        } else {
          receiveView(httpServletRequest, userID);
        } 
        return actionMapping.findForward("receiveViewJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("desertedView".equals(action)) {
      desertedView(httpServletRequest, userID);
      return actionMapping.findForward("desertedViewJSP");
    } 
    if ("deletedAll".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = messageBD.deletedboxBatch(ids);
      if (result) {
        if (offsetCopy != 0) {
          desertedView(httpServletRequest, offsetCopy, userID);
        } else {
          desertedView(httpServletRequest, userID);
        } 
        return actionMapping.findForward("desertedViewJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("realClearDeleted".equals(action)) {
      boolean result = messageBD.realClearDeletedBox(userID);
      if (result) {
        desertedView(httpServletRequest, userID);
        return actionMapping.findForward("desertedViewJSP");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("sendView".equals(action)) {
      ListDecribe(httpServletRequest, userID);
      return actionMapping.findForward("describeView");
    } 
    if ("realClearDescribe".equals(action)) {
      boolean result = messageBD.realClearDescribeBox(userID);
      if (result) {
        ListDecribe(httpServletRequest, userID);
        return actionMapping.findForward("describeView");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("redayMsg".equals(action)) {
      boolean isresult = messageBD.isSendOutMsg(userID, userName);
      if (isresult)
        httpServletRequest.setAttribute("msgCount", "1"); 
      String describId = httpServletRequest.getParameter("describeId");
      MsDescribePO redayMsg = messageBD.listDescribe(describId);
      if (redayMsg != null) {
        if (redayMsg.getReceiveId() != null) {
          String selectMan = messageBD.selectManSign(redayMsg.getReceiveId(), redayMsg.getReceiveMan());
          if (selectMan.equals("个人联系人")) {
            messageForm.setMsgtoprivate(String.valueOf(redayMsg.getReceiveMan()) + ",");
            messageForm.setMsgtoidprivate("$" + redayMsg.getReceiveId().toString() + "$");
            messageForm.setMsgprivatephone(String.valueOf(redayMsg.getReceiveCode()) + ",");
          } 
          if (selectMan.equals("内部联系人")) {
            messageForm.setMsgto(String.valueOf(redayMsg.getReceiveMan()) + ",");
            messageForm.setMsgtoid("$" + redayMsg.getReceiveId().toString() + "$");
            messageForm.setMsgtophone(String.valueOf(redayMsg.getReceiveCode()) + ",");
          } 
          if (selectMan.equals("公共联系人")) {
            messageForm.setMsgtopublic(String.valueOf(redayMsg.getReceiveMan()) + ",");
            messageForm.setMsgtoidpublic("$" + redayMsg.getReceiveId().toString() + "$");
            messageForm.setMsgpublicphone(String.valueOf(redayMsg.getReceiveCode()) + ",");
          } 
        } else {
          messageForm.setReceiveCode(redayMsg.getReceiveCode());
        } 
        httpServletRequest.setAttribute("msgtitle", redayMsg.getMsContent().substring(0, redayMsg.getMsContent().indexOf(":") + 1));
        messageForm.setMsContent(redayMsg.getMsContent().substring(redayMsg.getMsContent().indexOf(":") + 1, redayMsg.getMsContent().length()));
      } 
      httpServletRequest.setAttribute("describId", describId);
      return actionMapping.findForward("redayMsgJsp");
    } 
    if ("delQuiteReceive".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      boolean result = true;
      if (ids == null) {
        result = true;
      } else {
        result = messageBD.delDescribeBatch(ids);
      } 
      if (result) {
        if (offsetCopy != 0) {
          ListDecribe(httpServletRequest, offsetCopy, userID);
        } else {
          ListDecribe(httpServletRequest, userID);
        } 
        return actionMapping.findForward("describeView");
      } 
      return actionMapping.findForward("failure");
    } 
    if ("redayAddDescribe".equals(action)) {
      String id = "";
      if (httpServletRequest.getParameter("describId") != null)
        id = httpServletRequest.getParameter("describId"); 
      String flagsign = messageForm.getSaveType();
      StringBuffer contents = new StringBuffer();
      contents.append(httpServletRequest.getParameter("msgtitle"));
      contents.append(messageForm.getMsContent());
      String content = contents.toString();
      String tmpMbgroup = "";
      String tmpMbgroupPrivate = "";
      String tmpMbgroupPublic = "";
      String tmpMassageMan = "";
      String tmpMobilePhone = "";
      String outmobilCode = messageForm.getReceiveCode();
      tmpMbgroup = (messageForm.getMsgtoid() != null && !messageForm.getMsgtoid().equals("")) ? messageForm.getMsgtoid() : "";
      tmpMbgroupPrivate = (messageForm.getMsgtoidprivate() != null && !messageForm.getMsgtoidprivate().equals("")) ? messageForm.getMsgtoidprivate() : "";
      tmpMbgroupPublic = (messageForm.getMsgtoidpublic() != null && !messageForm.getMsgtoidpublic().equals("")) ? messageForm.getMsgtoidpublic() : "";
      if (!tmpMbgroup.equals("") && tmpMbgroup != null) {
        ConversionString conversionString = new ConversionString(tmpMbgroup);
        tmpMbgroup = conversionString.getUserIdString();
      } 
      if (!tmpMbgroupPrivate.equals("") && tmpMbgroupPrivate != null) {
        ConversionString conversionString = new ConversionString(tmpMbgroupPrivate);
        tmpMbgroupPrivate = conversionString.getUserIdString();
      } 
      if (!tmpMbgroupPublic.equals("") && tmpMbgroupPublic != null) {
        ConversionString conversionString = new ConversionString(tmpMbgroupPublic);
        tmpMbgroupPublic = conversionString.getUserIdString();
      } 
      if (!tmpMbgroup.equals("")) {
        tmpMbgroup = "," + tmpMbgroup;
        tmpMassageMan = messageForm.getMsgto();
        tmpMobilePhone = messageForm.getMsgtophone();
      } 
      if (!tmpMbgroupPrivate.equals("")) {
        tmpMbgroup = String.valueOf(tmpMbgroup) + "," + tmpMbgroupPrivate;
        tmpMassageMan = String.valueOf(tmpMassageMan) + messageForm.getMsgtoprivate();
        tmpMobilePhone = String.valueOf(tmpMobilePhone) + messageForm.getMsgprivatephone();
      } 
      if (!tmpMbgroupPublic.equals("")) {
        tmpMbgroup = String.valueOf(tmpMbgroup) + "," + tmpMbgroupPublic;
        tmpMassageMan = String.valueOf(tmpMassageMan) + messageForm.getMsgtopublic();
        tmpMobilePhone = String.valueOf(tmpMobilePhone) + messageForm.getMsgpublicphone();
      } 
      if (!"".equals(tmpMbgroup)) {
        tmpMbgroup = tmpMbgroup.substring(1);
        tmpMassageMan = tmpMassageMan.substring(0, tmpMassageMan.length() - 1);
        tmpMobilePhone = tmpMobilePhone.substring(0, tmpMobilePhone.length() - 1);
      } 
      if (flagsign.equals("save") && !id.equals("")) {
        boolean result = messageBD.delDescribeBatch(id);
        if (result) {
          boolean resulter = messageBD.addDescribe(tmpMbgroup, tmpMassageMan, tmpMobilePhone, content, userID, userName, outmobilCode);
          if (resulter) {
            messageForm.setSaveType("save");
            return actionMapping.findForward("redayMsgJsp");
          } 
          return actionMapping.findForward("failure");
        } 
      } 
      String mbgroup = "";
      String massageMan = "";
      String mobilePhone = "";
      String[] mbgroupList = tmpMbgroup.split(",");
      String[] messageManList = tmpMassageMan.split(",");
      String[] mobilePhoneList = tmpMobilePhone.split(",");
      Set<String> mobilePhoneManSet = new HashSet<String>();
      if (mbgroupList.length != 0 && messageManList.length != 0 && mobilePhoneList.length != 0 && 
        mbgroupList.length == messageManList.length && mobilePhoneList.length == mbgroupList.length)
        for (int i = 0; i < mobilePhoneList.length; i++) {
          if (!mobilePhoneManSet.contains(mobilePhoneList[i])) {
            mobilePhoneManSet.add(mobilePhoneList[i]);
            mbgroup = String.valueOf(mbgroup) + mbgroupList[i] + ",";
            massageMan = String.valueOf(massageMan) + messageManList[i] + ",";
            mobilePhone = String.valueOf(mobilePhone) + mobilePhoneList[i] + ",";
          } 
        }  
      if (flagsign.equals("sendAndExit") && !id.equals("")) {
        boolean isresult = messageBD.isSendMsg(userID, userName, (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString());
        if (isresult) {
          boolean result;
          CorpSMSAbstractFactory corpSMSAbstractFactory = null;
          CorpSMSTool.getPropertise();
          switch (CorpSMSTool.CORPSTATE) {
            case 0:
              result = messageBD.addMsgFlow(mbgroup, massageMan, mobilePhone, content, userID, userName, outmobilCode);
              if (result) {
                boolean resulter = messageBD.delDescribeBatch(id);
                if (resulter) {
                  messageForm.setSaveType("sendAndExit");
                  return actionMapping.findForward("redayMsgJsp");
                } 
              } else {
                return actionMapping.findForward("failure");
              } 
            case 1:
              corpSMSAbstractFactory = CorpSMSAbstractFactory.CreateFactory();
              if (corpSMSAbstractFactory != null) {
                ICorpSMSServies iCorpSMSServies = corpSMSAbstractFactory.createCorpSMSService();
                iCorpSMSServies.sendMessage(mbgroup, massageMan, mobilePhone, content, userID, userName, outmobilCode);
                MsHistoryBD msHistoryBD = new MsHistoryBD();
              } 
              if (flagsign.equals("sendAndExit")) {
                messageForm.reset(actionMapping, httpServletRequest);
                messageForm.setSaveType("sendAndExit");
              } else {
                messageForm.reset(actionMapping, httpServletRequest);
                messageForm.setSaveType("sendAndContinue");
              } 
              return actionMapping.findForward("writeMessage");
          } 
        } else {
          httpServletRequest.setAttribute("msgCount", "0");
          return actionMapping.findForward("redayMsgJsp");
        } 
      } 
      return actionMapping.findForward("failure");
    } 
    if ("searchBoxView".equals(action)) {
      httpServletRequest.setAttribute("recordCount", "0");
      httpServletRequest.setAttribute("maxPageItems", "15");
      httpServletRequest.setAttribute("pageParameters", "action");
      return actionMapping.findForward("searchBoxViewJSP");
    } 
    if ("searchView".equals(action)) {
      searchMsgBox(httpServletRequest);
      if (httpServletRequest.getParameter("status").equals("4"))
        searchflowBox(httpServletRequest); 
      return actionMapping.findForward("searchBoxViewJSP");
    } 
    if ("delSearch".equals(action)) {
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      String ids = httpServletRequest.getParameter("ids");
      String status = httpServletRequest.getParameter("status");
      if (ids == null) {
        if (offsetCopy != 0) {
          searchMsgBox(httpServletRequest, offsetCopy);
        } else {
          searchMsgBox(httpServletRequest);
        } 
        return actionMapping.findForward("searchBoxViewJSP");
      } 
      boolean result = messageBD.delSearchMessage(ids, status);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        searchMsgBox(httpServletRequest, offsetCopy);
      } else {
        searchMsgBox(httpServletRequest);
      } 
      return actionMapping.findForward("searchBoxViewJSP");
    } 
    if ("delQuiteSearch".equals(action)) {
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter("pager.offset"));
      String ids = httpServletRequest.getParameter("ids");
      String status = httpServletRequest.getParameter("status");
      if (ids == null) {
        if (offsetCopy != 0) {
          searchMsgBox(httpServletRequest, offsetCopy);
        } else {
          searchMsgBox(httpServletRequest);
        } 
        return actionMapping.findForward("searchBoxViewJSP");
      } 
      boolean result = messageBD.delRealSearchMessage(ids, status);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        searchMsgBox(httpServletRequest, offsetCopy);
      } else {
        searchMsgBox(httpServletRequest);
      } 
      return actionMapping.findForward("searchBoxViewJSP");
    } 
    if ("openAddMsg".equals(action)) {
      httpServletRequest.setAttribute("usermod", httpServletRequest.getParameter("usermod"));
      boolean isresult = messageBD.isSendOutMsg(userID, userName);
      if (isresult)
        httpServletRequest.setAttribute("msgCount", "1"); 
      httpServletRequest.setAttribute("gotomsgCount", httpServletRequest.getParameter("gotomsgCount"));
      return actionMapping.findForward("writeMessage");
    } 
    if ("isViewed".equals(action)) {
      String id = httpServletRequest.getParameter("id");
      MsInfoListPO po = messageBD.updateViewMsg(id);
      httpServletRequest.setAttribute("po", po);
      return actionMapping.findForward("viewMsg");
    } 
    if ("add".equals(action)) {
      String checkbox = httpServletRequest.getParameter("checkbox");
      String flagsign = messageForm.getSaveType();
      StringBuffer contents = new StringBuffer();
      contents.append(messageForm.getMsContent());
      String content = contents.toString();
      String tmpMbgroup = (messageForm.getMsgtoid() != null) ? messageForm.getMsgtoid() : "";
      String tmpMbgroupPrivate = (messageForm.getMsgtoidprivate() != null) ? messageForm.getMsgtoidprivate().replaceAll("\\#", "\\$") : "";
      String tmpMbgroupPublic = (messageForm.getMsgtoidpublic() != null) ? messageForm.getMsgtoidpublic().replaceAll("\\#", "\\$") : "";
      String outmobilCode = messageForm.getReceiveCode();
      outmobilCode = String.valueOf(outmobilCode) + ((httpServletRequest.getParameter("msgclientphone") == null) ? "" : httpServletRequest.getParameter("msgclientphone"));
      String tmpMassageMan = "";
      String tmpMobilePhone = "";
      if (tmpMbgroup != null && !tmpMbgroup.equals("")) {
        ConversionString conversionString = new ConversionString(tmpMbgroup);
        tmpMbgroup = conversionString.getUserIdString();
      } 
      if (tmpMbgroupPrivate != null && !tmpMbgroupPrivate.equals("")) {
        ConversionString conversionString = new ConversionString(tmpMbgroupPrivate);
        tmpMbgroupPrivate = conversionString.getUserIdString();
      } 
      if (tmpMbgroupPublic != null && !tmpMbgroupPublic.equals("")) {
        ConversionString conversionString = new ConversionString(tmpMbgroupPublic);
        tmpMbgroupPublic = conversionString.getUserIdString();
      } 
      if (!tmpMbgroup.equals("")) {
        tmpMbgroup = "," + tmpMbgroup;
        tmpMassageMan = "," + messageForm.getMsgto();
        tmpMobilePhone = messageForm.getMsgtophone();
      } 
      if (!tmpMbgroupPrivate.equals("")) {
        tmpMbgroup = String.valueOf(tmpMbgroup) + "," + tmpMbgroupPrivate;
        tmpMassageMan = String.valueOf(tmpMassageMan) + "," + messageForm.getMsgtoprivate();
        tmpMobilePhone = String.valueOf(tmpMobilePhone) + messageForm.getMsgprivatephone();
      } 
      if (!tmpMbgroupPublic.equals("")) {
        tmpMbgroup = String.valueOf(tmpMbgroup) + "," + tmpMbgroupPublic;
        tmpMassageMan = String.valueOf(tmpMassageMan) + "," + messageForm.getMsgtopublic();
        tmpMobilePhone = String.valueOf(tmpMobilePhone) + messageForm.getMsgpublicphone();
      } 
      tmpMbgroup = !"".equals(tmpMbgroup) ? tmpMbgroup.substring(1) : tmpMbgroup;
      tmpMassageMan = !"".equals(tmpMassageMan) ? tmpMassageMan.substring(1) : tmpMassageMan;
      tmpMobilePhone = !"".equals(tmpMobilePhone) ? tmpMobilePhone.substring(0, tmpMobilePhone.length() - 1) : tmpMobilePhone;
      String mbgroup = "";
      String massageMan = "";
      String mobilePhone = "";
      String[] mbgroupList = tmpMbgroup.split(",");
      String[] messageManList = tmpMassageMan.split(",");
      String[] mobilePhoneList = tmpMobilePhone.split(",");
      Set<String> mobilePhoneManSet = new HashSet<String>();
      if (mbgroupList.length != 0 && messageManList.length != 0 && mobilePhoneList.length != 0 && 
        mbgroupList.length == messageManList.length && mobilePhoneList.length == mbgroupList.length)
        for (int i = 0; i < mobilePhoneList.length; i++) {
          if (!mobilePhoneManSet.contains(mobilePhoneList[i])) {
            mobilePhoneManSet.add(mobilePhoneList[i]);
            mbgroup = String.valueOf(mbgroup) + mbgroupList[i] + ",";
            massageMan = String.valueOf(massageMan) + messageManList[i] + ",";
            mobilePhone = String.valueOf(mobilePhone) + mobilePhoneList[i] + ",";
          } 
        }  
      if (flagsign.equals("save")) {
        boolean result = messageBD.addDescribe(mbgroup, massageMan, mobilePhone, content, userID, userName, outmobilCode);
        if (result) {
          messageForm.reset(actionMapping, httpServletRequest);
          messageForm.setSaveType("save");
          return actionMapping.findForward("writeMessage");
        } 
        return actionMapping.findForward("failure");
      } 
      if (flagsign.equals("sendAndExit") || flagsign.equals("sendAndContinue")) {
        boolean isresult = messageBD.isSendMsg(userID, userName, (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString());
        if (isresult) {
          boolean result;
          CorpSMSAbstractFactory corpSMSAbstractFactory = null;
          CorpSMSTool.getPropertise();
          switch (CorpSMSTool.CORPSTATE) {
            case 0:
              result = messageBD.addMsgFlow(mbgroup, massageMan, mobilePhone, content, userID, userName, outmobilCode);
              if (result) {
                if (flagsign.equals("sendAndExit")) {
                  messageForm.reset(actionMapping, httpServletRequest);
                  messageForm.setSaveType("sendAndExit");
                } else {
                  messageForm.reset(actionMapping, httpServletRequest);
                  messageForm.setSaveType("sendAndContinue");
                } 
                return actionMapping.findForward("writeMessage");
              } 
              return actionMapping.findForward("failure");
            case 1:
              corpSMSAbstractFactory = CorpSMSAbstractFactory.CreateFactory();
              if (corpSMSAbstractFactory != null) {
                ICorpSMSServies iCorpSMSServies = corpSMSAbstractFactory.createCorpSMSService();
                iCorpSMSServies.sendMessage(mbgroup, massageMan, mobilePhone, content, userID, userName, outmobilCode);
              } 
              if (flagsign.equals("sendAndExit")) {
                messageForm.reset(actionMapping, httpServletRequest);
                messageForm.setSaveType("sendAndExit");
              } else {
                messageForm.reset(actionMapping, httpServletRequest);
                messageForm.setSaveType("sendAndContinue");
              } 
              return actionMapping.findForward("writeMessage");
          } 
        } else {
          httpServletRequest.setAttribute("msgCount", "0");
          return actionMapping.findForward("writeMessage");
        } 
        return actionMapping.findForward("writeMessage");
      } 
      return actionMapping.findForward("failure");
    } 
    throw new UnsupportedOperationException("Method perform() not yet implemented.");
  }
  
  public List flowList(HttpServletRequest httpServletRequest, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and msflowPO.sendId=" + userID);
    whereSql.append(" order by msflowPO.messageId desc");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("msflowPO.messageId,msflowPO.receiveMan,msflowPO.msContent,msflowPO.sendTime,msflowPO.sendMan", "com.js.oa.message.po.MsInfoFlowPO msflowPO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("sendedflowList", list);
    return list;
  }
  
  public void receiveView(HttpServletRequest httpServletRequest, int offsetCopy, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and recievePO.receiveId=" + userID);
    whereSql.append(" and  recievePO.msSign=2 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.sendMan,recievePO.msContent,recievePO.receiveTime,recievePO.sendTime", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("receiveList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,taxisName");
  }
  
  public void receiveView(HttpServletRequest httpServletRequest, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    MessageBD bd = new MessageBD();
    String serial = bd.getSerialByUserId(String.valueOf(userID));
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and (recievePO.receiveId=" + userID);
    whereSql.append("  or receiveMan='" + serial + "')");
    whereSql.append(" and  recievePO.msSign=2 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.sendMan,recievePO.msContent,recievePO.receiveTime,recievePO.sendTime,recievePO.sendId,recievePO.isViewed", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("receiveList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void searchMsgBox(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    int offset = offsetCopy;
    String msgContent = httpServletRequest.getParameter("msgContent");
    String name = httpServletRequest.getParameter("name");
    name = name.replaceAll("'", "''");
    String status = httpServletRequest.getParameter("status");
    String checkbox = httpServletRequest.getParameter("checkbox");
    String start_date = String.valueOf(httpServletRequest.getParameter("start_date")) + " 00:00:00";
    String end_date = String.valueOf(httpServletRequest.getParameter("end_date")) + " 23:59:59";
    String myWhere = " 1=1 ";
    String databaseType = SystemCommon.getDatabaseType();
    if (status != null && !"".equals(status) && status.equals("4") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between '" + start_date + "' and '" + end_date + "' and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("4") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1   and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
    } else if (status != null && !"".equals(status) && status.equals("4") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between '" + start_date + "' and '" + end_date + "'  order by infoList.listId desc";
      } else {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("4") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1   order by infoList.listId desc";
    } 
    if (status != null && !"".equals(status) && status.equals("3") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between '" + start_date + "' and '" + end_date + "') or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "') ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
      } else {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("3") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null ) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  ) ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
    } else if (status != null && !"".equals(status) && status.equals("3") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between '" + start_date + "' and '" + end_date + "') or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "'))  and infoList.msSign=0 order by infoList.listId desc";
      } else {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')))  and infoList.msSign=0 " + "   order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("3") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null ) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null ) )  and infoList.msSign=0 " + "   order by infoList.listId desc";
    } 
    if (status != null && !"".equals(status) && status.equals("1") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "' and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "' and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2   order by infoList.listId desc";
    } 
    if (msgContent != null && !"".equals(msgContent))
      myWhere = " infoList.msContent like '%" + msgContent + "%'  and  " + myWhere; 
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("infoList.listId,infoList.msContent,infoList.sendTime,infoList.receiveTime,infoList.receiveMan,infoList.sendMan,infoList.modelSend,infoList.msSign", "com.js.oa.message.po.MsInfoListPO infoList ", "where" + myWhere);
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchBoxList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "msgContent,action,id,status,checkbox,start_date,end_date,name");
  }
  
  public void searchMsgBox(HttpServletRequest httpServletRequest) {
    String databaseType = SystemCommon.getDatabaseType();
    HttpSession session = httpServletRequest.getSession(true);
    int offset = 0;
    String msgContent = httpServletRequest.getParameter("msgContent");
    String name = httpServletRequest.getParameter("name");
    msgContent = msgContent.replaceAll("'", "''");
    name = name.replaceAll("'", "''");
    String status = httpServletRequest.getParameter("status");
    String checkbox = httpServletRequest.getParameter("checkbox");
    String start_date = String.valueOf(httpServletRequest.getParameter("start_date")) + " 00:00:00";
    String end_date = String.valueOf(httpServletRequest.getParameter("end_date")) + " 23:59:59";
    String myWhere = " 1=1 ";
    if (status != null && !"".equals(status) && status.equals("4") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between '" + start_date + "' and '" + end_date + "' and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("4") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1   and infoList.receiveMan like '%" + name + "%' order by infoList.listId desc";
    } else if (status != null && !"".equals(status) && status.equals("4") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between '" + start_date + "' and '" + end_date + "'  order by infoList.listId desc";
      } else {
        myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1  and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("4") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " infoList.sendId = " + session.getAttribute("userId") + " and infoList.msSign=1   order by infoList.listId desc";
    } 
    if (status != null && !"".equals(status) && status.equals("3") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between '" + start_date + "' and '" + end_date + "') or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "') ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
      } else {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("3") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null ) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  ) ) and infoList.msSign=0 " + "  and (infoList.receiveMan like '%" + name + "%' or infoList.sendMan like '%" + name + "%') order by infoList.listId desc";
    } else if (status != null && !"".equals(status) && status.equals("3") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between '" + start_date + "' and '" + end_date + "') or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "'))  and infoList.msSign=0 " + "   order by infoList.listId desc";
      } else {
        myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null and infoList.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')))  and infoList.msSign=0 " + "   order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("3") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " ((infoList.sendId = " + session.getAttribute("userId") + " and infoList.sendTime is not null ) or (infoList.receiveId = " + session.getAttribute("userId") + " and infoList.receiveTime is not null ) )  and infoList.msSign=0 " + "   order by infoList.listId desc";
    } 
    if (status != null && !"".equals(status) && status.equals("1") && checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "' and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && !"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "' and '" + end_date + "' and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and infoList.sendMan like '%" + name + "%' order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between '" + start_date + "'  and '" + end_date + "' order by infoList.listId desc";
      } else {
        myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2  and infoList.receiveTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  order by infoList.listId desc";
      } 
    } else if (status != null && !"".equals(status) && status.equals("1") && (checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " infoList.receiveId = " + session.getAttribute("userId") + " and infoList.msSign=2   order by infoList.listId desc";
    } 
    if (msgContent != null && !"".equals(msgContent))
      myWhere = " infoList.msContent like '%" + msgContent + "%'  and  " + myWhere; 
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("infoList.listId,infoList.msContent,infoList.sendTime,infoList.receiveTime,infoList.receiveMan,infoList.sendMan,infoList.modelSend,infoList.msSign", "com.js.oa.message.po.MsInfoListPO infoList ", "where" + myWhere);
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("searchBoxList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "msgContent,action,id,status,checkbox,start_date,end_date,name");
  }
  
  public void searchflowBox(HttpServletRequest httpServletRequest) {
    String databaseType = SystemCommon.getDatabaseType();
    HttpSession session = httpServletRequest.getSession(true);
    String msgContent = httpServletRequest.getParameter("msgContent");
    String name = httpServletRequest.getParameter("name");
    msgContent = msgContent.replaceAll("'", "''");
    name = name.replaceAll("'", "''");
    String checkbox = httpServletRequest.getParameter("checkbox");
    String start_date = String.valueOf(httpServletRequest.getParameter("start_date")) + " 00:00:00";
    String end_date = String.valueOf(httpServletRequest.getParameter("end_date")) + " 23:59:59";
    String myWhere = " 1=1 ";
    if (checkbox != null && "checked".equals(checkbox) && !"".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "  and msflowPO.sendTime between '" + start_date + "' and '" + end_date + "' and msflowPO.receiveMan like '%" + name + "%' order by msflowPO.messageId desc";
      } else {
        myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "  and msflowPO.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L') and msflowPO.receiveMan like '%" + name + "%' order by msflowPO.messageId desc";
      } 
    } else if (!"".equals(name) && (checkbox == null || "null".equals(checkbox))) {
      myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "   and msflowPO.receiveMan like '%" + name + "%' order by msflowPO.messageId desc";
    } else if (checkbox != null && "checked".equals(checkbox) && "".equals(name)) {
      if (databaseType.indexOf("mysql") >= 0) {
        myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "   and msflowPO.sendTime between '" + start_date + "' and '" + end_date + "'  order by msflowPO.messageId desc";
      } else {
        myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "   and msflowPO.sendTime between JSDB.FN_STRTODATE('" + start_date + "','L') and JSDB.FN_STRTODATE('" + end_date + "','L')  order by msflowPO.messageId desc";
      } 
    } else if ((checkbox == null || "null".equals(checkbox)) && "".equals(name)) {
      myWhere = " msflowPO.sendId = " + session.getAttribute("userId") + "   order by msflowPO.messageId desc";
    } 
    if (msgContent != null && !"".equals(msgContent))
      myWhere = " msflowPO.msContent like '%" + msgContent + "%'  and  " + myWhere; 
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("msflowPO.messageId,msflowPO.receiveMan,msflowPO.msContent,msflowPO.sendTime,msflowPO.sendMan", "com.js.oa.message.po.MsInfoFlowPO msflowPO ", "where" + myWhere);
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("searchSendedFlow", list);
    httpServletRequest.setAttribute("pageParameters", "action,msgContent,status,name,start_date,end_date");
  }
  
  public void sendedList(HttpServletRequest httpServletRequest, int offsetCopy, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and recievePO.sendId=" + userID);
    whereSql.append(" and  recievePO.msSign=1 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.receiveMan,recievePO.msContent,recievePO.sendTime,recievePO.msSign,recievePO.receiveCode", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("sendedList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,taxisName");
  }
  
  public List sendedList(HttpServletRequest httpServletRequest, Long userID) {
    HttpSession session = httpServletRequest.getSession(true);
    StringBuffer whereSql = new StringBuffer("where 1=1 ");
    whereSql.append(" and recievePO.sendId=" + userID);
    whereSql.append(" and  recievePO.msSign=1 ");
    whereSql.append(" order by recievePO.listId desc");
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    Page page = new Page("recievePO.listId,recievePO.receiveMan,recievePO.msContent,recievePO.sendTime,recievePO.msSign,recievePO.receiveCode", "com.js.oa.message.po.MsInfoListPO recievePO ", whereSql.toString());
    page.setPageSize(this.pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("sendedList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
    return list;
  }
  
  public void sendedAndFlowList(HttpServletRequest httpServletRequest, Long userID) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    int beginRow = 0;
    int endRow = 0;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / this.pageSize + 1;
    MessageBD bd = new MessageBD();
    List list1 = bd.getFlowAndList(userID);
    int recordCount = list1.size();
    int countPage = recordCount / this.pageSize + 1;
    beginRow = (currentPage - 1) * this.pageSize;
    endRow = currentPage * this.pageSize;
    if (currentPage == countPage)
      endRow = recordCount; 
    ArrayList list = new ArrayList();
    for (int i = beginRow; i < endRow; i++)
      list.add(list1.get(i)); 
    list.trimToSize();
    httpServletRequest.setAttribute("sendedflowList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(this.pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
