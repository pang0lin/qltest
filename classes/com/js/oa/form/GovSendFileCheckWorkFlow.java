package com.js.oa.form;

import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO;
import com.js.doc.doc.service.GovSendFileCheckWithWorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class GovSendFileCheckWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("resubmitWorkId") != null && 
      
      !httpServletRequest.getParameter("resubmitWorkId").toString().equals(""))
      delete(httpServletRequest); 
    GovSendFileCheckWithWorkFlowBD bd = new GovSendFileCheckWithWorkFlowBD();
    Long Id = new Long(-1L);
    Id = bd.save(setPOForSave(httpServletRequest), httpServletRequest.getParameterValues("displayName"), httpServletRequest.getParameterValues("saveName"));
    return Id;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    GovSendFileCheckWithWorkFlowBD bd = new GovSendFileCheckWithWorkFlowBD();
    Long editId = Long.valueOf(httpServletRequest.getParameter("editId"));
    GovSendFileCheckWithWorkFlowPO po = new GovSendFileCheckWithWorkFlowPO();
    po = setPOForUpdate(httpServletRequest);
    po.setId(editId.intValue());
    editId = bd.update(po, httpServletRequest.getParameterValues("displayName"), httpServletRequest.getParameterValues("saveName"));
    return editId;
  }
  
  private GovSendFileCheckWithWorkFlowPO setPOForSave(HttpServletRequest request) {
    GovSendFileCheckWithWorkFlowPO po = new GovSendFileCheckWithWorkFlowPO();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    Object object3 = request.getSession(true).getAttribute("userName");
    po.setCreatedEmp(Long.parseLong((String)object1));
    po.setCreatedOrg(Long.parseLong((String)object2));
    po.setSubmitPerson((String)object3);
    po.setSubmitTime(new Date());
    po.setSendFileCheckComeUnit(request.getParameter("sendFileCheckComeUnit"));
    po.setSendFileCheckTitle(request.getParameter("sendFileCheckTitle"));
    po.setField1((new StringBuilder(String.valueOf(request.getParameter("field1")))).toString());
    po.setSendFileCheckWriterComment((new StringBuilder(String.valueOf(request.getParameter(
              "sendFileCheckWriterComment")))).toString());
    po.setSendFileCheckFinishDate((new StringBuilder(String.valueOf(request.getParameter(
              "sendFileCheckFinishDate")))).toString());
    po.setSendFileCheckLeaderComment((new StringBuilder(String.valueOf(request.getParameter(
              "sendFileCheckLeaderComment")))).toString());
    po.setField2((new StringBuilder(String.valueOf(request.getParameter("field2")))).toString());
    po.setField3((new StringBuilder(String.valueOf(request.getParameter("field3")))).toString());
    po.setField4((new StringBuilder(String.valueOf(request.getParameter("field4")))).toString());
    po.setField5((new StringBuilder(String.valueOf(request.getParameter("field5")))).toString());
    po.setField6((new StringBuilder(String.valueOf(request.getParameter("field6")))).toString());
    po.setField7((new StringBuilder(String.valueOf(request.getParameter("field7")))).toString());
    po.setField8((new StringBuilder(String.valueOf(request.getParameter("field8")))).toString());
    po.setField9((new StringBuilder(String.valueOf(request.getParameter("field9")))).toString());
    po.setField10((new StringBuilder(String.valueOf(request.getParameter("field10")))).toString());
    if (request.getParameter("sendFileCheckReceiveDate") != null && !request.getParameter("sendFileCheckReceiveDate").toString().equals("")) {
      po.setSendFileCheckReceiveDate(new Date(request.getParameter("sendFileCheckReceiveDate")));
    } else {
      po.setSendFileCheckReceiveDate(new Date());
    } 
    if (request.getParameter("fromReceiveFileId") != null) {
      po.setReceiveFileId(Long.valueOf(request.getParameter(
              "fromReceiveFileId")));
      po.setReceiveFileLink(request.getParameter("fromReceiveFileLink"));
    } 
    po.setTransactStatus("0");
    if (request.getParameter("transactStatus") != null && request.getParameter("transactStatus").toString().equals(""))
      po.setTransactStatus(request.getParameter("transactStatus")); 
    return po;
  }
  
  private GovSendFileCheckWithWorkFlowPO setPOForUpdate(HttpServletRequest request) {
    GovSendFileCheckWithWorkFlowPO po = new GovSendFileCheckWithWorkFlowPO();
    po.setSendFileCheckComeUnit((new StringBuilder(String.valueOf(request.getParameter(
              "sendFileCheckComeUnit")))).toString());
    po.setSendFileCheckTitle((new StringBuilder(String.valueOf(request.getParameter("sendFileCheckTitle")))).toString());
    po.setField1((new StringBuilder(String.valueOf(request.getParameter("field1")))).toString());
    po.setField2((new StringBuilder(String.valueOf(request.getParameter("field2")))).toString());
    po.setField3((new StringBuilder(String.valueOf(request.getParameter("field3")))).toString());
    po.setField4((new StringBuilder(String.valueOf(request.getParameter("field4")))).toString());
    po.setField5((new StringBuilder(String.valueOf(request.getParameter("field5")))).toString());
    po.setField6((new StringBuilder(String.valueOf(request.getParameter("field6")))).toString());
    po.setField7((new StringBuilder(String.valueOf(request.getParameter("field7")))).toString());
    po.setField8((new StringBuilder(String.valueOf(request.getParameter("field8")))).toString());
    po.setField9((new StringBuilder(String.valueOf(request.getParameter("field9")))).toString());
    po.setField10((new StringBuilder(String.valueOf(request.getParameter("field10")))).toString());
    po.setTransactStatus("0");
    if (request.getParameter("transactStatus") != null && !request.getParameter("transactStatus").toString().equals(""))
      po.setTransactStatus(request.getParameter("transactStatus")); 
    String[] wf_dealwithcomment_id = request.getParameterValues(
        "wf_dealwithcomment_id");
    String[] rangeName = request.getParameterValues("rangeName");
    String[] rangeId = request.getParameterValues("rangeId");
    (new WorkFlowCommonBD())
      .updateCommentRange(wf_dealwithcomment_id, rangeName, rangeId);
    return po;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String sendFileCheckId = httpServletRequest.getParameter("editId");
    return (new GovSendFileCheckWithWorkFlowBD()).completeSendFileCheck(
        sendFileCheckId);
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    GovSendFileCheckWithWorkFlowPO po = new GovSendFileCheckWithWorkFlowPO();
    Long editId = Long.valueOf(httpServletRequest.getParameter("editId"));
    GovSendFileCheckWithWorkFlowBD sendFileCheckWithWorkFlowBD = new GovSendFileCheckWithWorkFlowBD();
    po = setPOForUpdate(httpServletRequest);
    String type = "1";
    if (httpServletRequest.getParameter("type") != null && !httpServletRequest.getParameter("type").toString().equals(""))
      type = httpServletRequest.getParameter("type").toString(); 
    if (type.equals("0")) {
      po.setTransactStatus("2");
    } else {
      po.setTransactStatus("0");
    } 
    po.setId(editId.intValue());
    editId = sendFileCheckWithWorkFlowBD.update(po, httpServletRequest.getParameterValues("displayName"), httpServletRequest.getParameterValues("saveName"));
    return editId;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String editId = httpServletRequest.getParameter("editId");
    GovSendFileCheckWithWorkFlowBD sendFileCheckWithWorkFlowBD = new GovSendFileCheckWithWorkFlowBD();
    sendFileCheckWithWorkFlowBD.deleteBatch(editId);
  }
}
