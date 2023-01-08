package com.js.oa.tjgzw.service;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.oa.tjgzw.bean.ReceiveFileGzwEJBLocalHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class ReceiveFileDrafGzwBD {
  private static Logger logger = Logger.getLogger(ReceiveFileDrafGzwBD.class.getName());
  
  public Long saveDraf(HttpServletRequest httpServletRequest) {
    String str1, fromSendFile = (httpServletRequest.getParameter("fromSendFile") != null) ? httpServletRequest.getParameter("fromSendFile").toString() : "";
    if (httpServletRequest.getParameter("editId") != null && 
      !httpServletRequest.getParameter("editId").toString().equals("") && !"1".equals(fromSendFile))
      delete(httpServletRequest); 
    Long result = save(setPO(httpServletRequest));
    if (result.longValue() > 0L)
      if (result != null && !result.toString().equals("") && Integer.parseInt((String)result) > 0)
        changeSeqfig(httpServletRequest);  
    Long long_1 = result;
    if (httpServletRequest.getParameter("editId") != null && !httpServletRequest.getParameter("editId").equals("") && !"null".equals(httpServletRequest.getParameter("editId")))
      str1 = httpServletRequest.getParameter("editId"); 
    httpServletRequest.setAttribute("editId", str1);
    return result;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String editId = httpServletRequest.getParameter("editId");
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    receiveFileBD.delete2(editId);
  }
  
  public void changeSeqfig(HttpServletRequest request) {
    ReceivedocumentBD bd = new ReceivedocumentBD();
    if (request.getParameter("zjkySeq") != null && !request.getParameter("zjkySeq").toString().equals("") && 
      request.getParameter("seqId") != null && !request.getParameter("seqId").toString().equals("")) {
      ReceiveFileSeqPO po = bd.loadRecSeqPO(request.getParameter("seqId"));
      if (po != null && po.getId() != null) {
        int seqfig = po.getSeqfigR().intValue();
        seqfig++;
        po.setSeqfigR(new Long(seqfig));
        bd.updateRecSeqPO(po);
      } 
    } 
  }
  
  public Long save(GovReceiveFilePO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ReceiveFileGzwEJB", "ReceiveFileGzwEJBLocal", ReceiveFileGzwEJBLocalHome.class);
      pg.put(po, GovReceiveFilePO.class);
      result = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return result;
  }
  
  private GovReceiveFilePO setPO(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    GovReceiveFilePO po = new GovReceiveFilePO();
    if (httpServletRequest.getParameter("sendFileId") != null && !httpServletRequest.getParameter("sendFileId").toString().equals("")) {
      po.setSendFileId(Long.valueOf(httpServletRequest.getParameter("sendFileId")));
      po.setSendFileLink(httpServletRequest.getParameter("sendFileLink"));
      po.setSendFileTitle(httpServletRequest.getParameter("sendFileTitle"));
    } 
    if (httpServletRequest.getParameter("exchangeFileLink") != null) {
      po.setExchangeFileId(httpServletRequest.getParameter("exchangeFileId"));
      po.setExchangeFileLink(httpServletRequest.getParameter("exchangeFileLink"));
      po.setExchangeFileTitle(httpServletRequest.getParameter("exchangeFileTitle"));
    } 
    StringBuffer receiveFileFileNo = new StringBuffer();
    receiveFileFileNo.append(httpServletRequest.getParameter("field1"));
    receiveFileFileNo.append("[" + httpServletRequest.getParameter("field2") + "]");
    receiveFileFileNo.append(httpServletRequest.getParameter("field3"));
    po.setReceiveFileFileNo(receiveFileFileNo.toString());
    if (httpServletRequest.getParameter("field3") != null && !"".equals(httpServletRequest.getParameter("field3"))) {
      po.setReceiveFileFileNoCount(Long.parseLong(httpServletRequest.getParameter("field3")));
    } else {
      po.setReceiveFileFileNoCount(0L);
    } 
    if (httpServletRequest.getParameter("field2") != null && !"".equals(httpServletRequest.getParameter("field2"))) {
      po.setCreatedTimeYear(Long.parseLong(httpServletRequest.getParameter("field2")));
    } else {
      po.setCreatedTimeYear(0L);
    } 
    po.setReceiveFileType(httpServletRequest.getParameter("receiveFileType"));
    po.setCreatedEmp(Long.parseLong(httpSession.getAttribute("userId").toString()));
    po.setCreatedOrg(Long.parseLong(httpSession.getAttribute("orgId").toString()));
    po.setDomainId(httpSession.getAttribute("domainId").toString());
    String attachName1 = "";
    String[] attachNameArr1 = httpServletRequest.getParameterValues("accessoryName1");
    String attachSaveName1 = "";
    String[] attachSaveNameArr1 = httpServletRequest.getParameterValues("accessorySaveName1");
    String attachName2 = "";
    String[] attachNameArr2 = httpServletRequest.getParameterValues("accessoryName2");
    String attachSaveName2 = "";
    String[] attachSaveNameArr2 = httpServletRequest.getParameterValues("accessorySaveName2");
    int k = 0;
    int i;
    for (i = 0; attachSaveNameArr1 != null && i < attachSaveNameArr1.length; i++) {
      if (attachSaveNameArr1[i] != null && !"".equals(attachSaveNameArr1[i])) {
        k++;
        if (k != 1) {
          attachName1 = String.valueOf(attachName1) + "|";
          attachSaveName1 = String.valueOf(attachSaveName1) + "|";
        } 
        attachName1 = String.valueOf(attachName1) + attachNameArr1[i];
        attachSaveName1 = String.valueOf(attachSaveName1) + attachSaveNameArr1[i];
      } 
    } 
    po.setAccessoryName(attachName1);
    po.setAccessorySaveName(attachSaveName1);
    k = 0;
    for (i = 0; attachSaveNameArr2 != null && i < attachSaveNameArr2.length; i++) {
      if (attachSaveNameArr2[i] != null || !"".equals(attachSaveNameArr2[i])) {
        k++;
        if (k != 1) {
          attachName2 = String.valueOf(attachName2) + "|";
          attachSaveName2 = String.valueOf(attachSaveName2) + "|";
        } 
        attachName2 = String.valueOf(attachName2) + attachNameArr2[i];
        attachSaveName2 = String.valueOf(attachSaveName2) + attachSaveNameArr2[i];
      } 
    } 
    po.setAccessoryNameFile(attachName2);
    po.setAccessorySaveNameFile(attachSaveName2);
    if (httpServletRequest.getParameter("receiveFileReceiveDate") != null && !"2008/12/12".equals((new StringBuilder(String.valueOf(httpServletRequest.getParameter("receiveFileReceiveDate")))).toString())) {
      po.setReceiveFileReceiveDate(new Date(httpServletRequest.getParameter("receiveFileReceiveDate")));
    } else {
      po.setReceiveFileReceiveDate(new Date());
    } 
    if (httpServletRequest.getParameter("createdDate") != null && !"2008/12/12".equals((new StringBuilder(String.valueOf(httpServletRequest.getParameter("receiveFileReceiveDate")))).toString())) {
      po.setCreatedTime(new Date(httpServletRequest.getParameter("createdDate")));
    } else {
      po.setCreatedTime(new Date());
    } 
    po.setReceiveFieldSelectMoreEmp((httpServletRequest.getParameter("receiveFieldSelectMoreEmp") == null) ? 
        "" : httpServletRequest.getParameter("receiveFieldSelectMoreEmp"));
    po.setField1(httpServletRequest.getParameter("field1"));
    po.setField2(httpServletRequest.getParameter("field2"));
    po.setField3(httpServletRequest.getParameter("field3"));
    po.setField5(httpServletRequest.getParameter("field5"));
    po.setField6(httpServletRequest.getParameter("field6"));
    po.setField7(httpServletRequest.getParameter("field7"));
    po.setField8(httpServletRequest.getParameter("field8"));
    po.setField16(httpServletRequest.getParameter("field16"));
    po.setField17(httpServletRequest.getParameter("field17"));
    po.setField18(httpServletRequest.getParameter("field18"));
    po.setField19(httpServletRequest.getParameter("field19"));
    po.setField20(httpServletRequest.getParameter("field20"));
    po.setField21(httpServletRequest.getParameter("field21"));
    po.setField22(httpServletRequest.getParameter("field22"));
    po.setField23(httpServletRequest.getParameter("field23"));
    po.setField24(httpServletRequest.getParameter("field24"));
    po.setField25(httpServletRequest.getParameter("field25"));
    po.setField26(httpServletRequest.getParameter("field26"));
    po.setReceiveFileSendFileUnit(httpServletRequest.getParameter("receiveFileSendFileUnit"));
    po.setReceiveFileFileNumber(httpServletRequest.getParameter("receiveFileFileNumber"));
    po.setReceiveFileTitle(httpServletRequest.getParameter("receiveFileTitle"));
    po.setReceiveFileSafetyGrade(httpServletRequest.getParameter("receiveFileSafetyGrade"));
    if (httpServletRequest.getParameter("receiveFileQuantity") != null && !"".equals(httpServletRequest.getParameter("receiveFileQuantity")))
      po.setReceiveFileQuantity(Short.parseShort(httpServletRequest.getParameter("receiveFileQuantity"))); 
    po.setField4(httpServletRequest.getParameter("field4"));
    po.setReceiveFileDoComment(httpServletRequest.getParameter("receiveFileDoComment"));
    po.setField10(httpServletRequest.getParameter("field10"));
    if (httpServletRequest.getParameter("fromFileSendCheckId") != null) {
      po.setFileSendCheckId(httpServletRequest.getParameter("fromFileSendCheckId"));
      po.setFileSendCheckLink(httpServletRequest.getParameter("fromFileSendCheckLink"));
    } 
    if (httpServletRequest.getParameter("zjkySeq") != null)
      po.setZjkySeq(httpServletRequest.getParameter("zjkySeq")); 
    if (httpServletRequest.getParameter("zjkyType") != null)
      po.setZjkyType(httpServletRequest.getParameter("zjkyType")); 
    if (httpServletRequest.getParameter("zjkykeepTerm") != null)
      po.setZjkykeepTerm(httpServletRequest.getParameter("zjkykeepTerm")); 
    if (httpServletRequest.getParameter("seqId") != null && !httpServletRequest.getParameter("seqId").toString().equals(""))
      po.setSeqId(new Long(httpServletRequest.getParameter("seqId"))); 
    if (httpServletRequest.getParameter("receiveStatus") != null && !httpServletRequest.getParameter("receiveStatus").toString().equals(""))
      po.setReceiveFileStatus(Byte.parseByte(httpServletRequest.getParameter("receiveStatus"))); 
    if (httpServletRequest.getParameter("tableId") != null && !httpServletRequest.getParameter("tableId").toString().equals(""))
      po.setTableId(new Long(httpServletRequest.getParameter("tableId"))); 
    if (httpServletRequest.getParameter("receiveTextField1") != null)
      po.setReceiveTextField1(httpServletRequest.getParameter("receiveTextField1")); 
    if (httpServletRequest.getParameter("receiveTextField2") != null)
      po.setReceiveTextField2(httpServletRequest.getParameter("receiveTextField2")); 
    if (httpServletRequest.getParameter("receiveDropDownSelect1") != null)
      po.setReceiveDropDownSelect1(httpServletRequest.getParameter("receiveDropDownSelect1")); 
    if (httpServletRequest.getParameter("receiveDropDownSelect2") != null)
      po.setReceiveDropDownSelect2(httpServletRequest.getParameter("receiveDropDownSelect2")); 
    if (httpServletRequest.getParameter("receiveMutliTextField1") != null)
      po.setReceiveMutliTextField1(httpServletRequest.getParameter("receiveMutliTextField1")); 
    if (httpServletRequest.getParameter("processId") != null)
      po.setProcessId(new Long(httpServletRequest.getParameter("processId"))); 
    String fromSendFile = (httpServletRequest.getParameter("fromSendFile") != null) ? httpServletRequest.getParameter("fromSendFile").toString() : "";
    if (httpServletRequest.getParameter("editId") != null && !httpServletRequest.getParameter("editId").equals("")) {
      long id = 0L;
      if (!"1".equals(fromSendFile)) {
        id = Long.parseLong(httpServletRequest.getParameter("editId"));
      } else {
        id = Long.parseLong("0");
      } 
      po.setId(id);
    } 
    return po;
  }
}
