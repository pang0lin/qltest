package com.js.oa.form;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.info.infomanager.bean.InfoProFileEJBBean;
import com.js.oa.info.infomanager.po.InfoFileReviewFlowPO;
import com.js.oa.info.infomanager.po.InfoProFilePO;
import com.js.oa.info.infomanager.po.InfoProFileReviewPO;
import com.js.oa.jsflow.util.DataToOtherBase;
import com.js.system.vo.usermanager.EmployeeVO;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class InfoProFileWorkFlow extends Workflow {
  public String complete(HttpServletRequest request) {
    try {
      String processeId = request.getParameter("processId");
      String recorded = request.getParameter("recordId");
      String tableId = request.getParameter("tableId");
      String work = request.getParameter("work");
      DataToOtherBase dob = new DataToOtherBase(processeId, "-1", recorded);
      dob.dataSynchro();
      InfoProFileEJBBean infoProFileEJBBean = new InfoProFileEJBBean();
      List<InfoFileReviewFlowPO> ifrfList = infoProFileEJBBean.getInfoFileReviewFlowPOById(recorded);
      for (int i = 0; i < ifrfList.size(); i++) {
        InfoFileReviewFlowPO ifrf = ifrfList.get(i);
        if ("1".equals(ifrf.getResult())) {
          Object[] obj = infoProFileEJBBean.getInfoProFileReviewPOByFileNum(ifrf.getFileNum()).get(0);
          InfoProFileReviewPO iprp = new InfoProFileReviewPO();
          iprp.setReviewId(Long.valueOf(obj[0].toString()));
          iprp.setFileId(Long.valueOf(obj[1].toString()));
          iprp.setFileName(obj[2].toString());
          iprp.setVersion(obj[7].toString());
          iprp.setFileNum(obj[8].toString());
          String[] reviewDate = obj[6].toString().split(" ");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          try {
            iprp.setReviewDate(sdf.parse(reviewDate[0]));
          } catch (ParseException e) {
            e.printStackTrace();
          } 
          iprp.setResult("1");
          infoProFileEJBBean.updateInfoProFileReviewPO(iprp);
          Long fileId = iprp.getFileId();
          InfoProFilePO preInfoProFile = infoProFileEJBBean.getInfoProFilePOById(fileId);
          String date = sdf.format(new Date());
          preInfoProFile.setFileDate(sdf.parse(date));
          Date reviewDate2 = preInfoProFile.getReviewDate();
          Calendar rightNow = Calendar.getInstance();
          rightNow.setTime(reviewDate2);
          rightNow.add(1, 3);
          preInfoProFile.setReviewDate(rightNow.getTime());
          preInfoProFile.setReviewRecordId(String.valueOf(processeId) + ";" + tableId + ";" + recorded);
          infoProFileEJBBean.updateInfoProFilePO(preInfoProFile);
        } else if ("2".equals(ifrf.getResult())) {
          Object[] obj = infoProFileEJBBean.getInfoProFileReviewPOByFileNum(ifrf.getFileNum()).get(0);
          InfoProFileReviewPO iprp = new InfoProFileReviewPO();
          iprp.setReviewId(Long.valueOf(obj[0].toString()));
          iprp.setFileId(Long.valueOf(obj[1].toString()));
          iprp.setFileName(obj[2].toString());
          iprp.setVersion(obj[7].toString());
          iprp.setFileNum(obj[8].toString());
          String[] reviewDate = obj[6].toString().split(" ");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          try {
            iprp.setReviewDate(sdf.parse(reviewDate[0]));
          } catch (ParseException e) {
            e.printStackTrace();
          } 
          iprp.setResult("2");
          String editName = ifrf.getEditName().split(";")[0];
          String editId = ifrf.getEditName().split(";")[1];
          iprp.setEditName(editName);
          iprp.setEditId(Long.valueOf(editId));
          infoProFileEJBBean.updateInfoProFileReviewPO(iprp);
          String userNameID = request.getSession().getAttribute("userId").toString();
          EmployeeVO userEmployee = infoProFileEJBBean.getEmployeeVOById(userNameID);
          EmployeeVO receiveEmployee = infoProFileEJBBean.getEmployeeVOById(editId);
          String submitName = userEmployee.getUserAccounts();
          String receiveName = receiveEmployee.getUserAccounts();
          Long fileId = iprp.getFileId();
          InfoProFilePO preInfoProFile = infoProFileEJBBean.getInfoProFilePOById(fileId);
          preInfoProFile.setReviewRecordId(String.valueOf(processeId) + ";" + tableId + ";" + recorded);
          infoProFileEJBBean.updateInfoProFilePO(preInfoProFile);
          ResourceBundle resource = ResourceBundle.getBundle("info");
          String FileReviseProcessId = resource.getString("FileReviseProcessId");
          String FileReviseTableId = resource.getString("FileReviseTableId");
          StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
          xml.append("<WorkFlow>");
          xml.append("<Process processId=\"").append(FileReviseProcessId).append("\"/>");
          xml.append("<UserName  submitName=\"").append(submitName).append("\"  receiveName=\"").append(receiveName).append("\"/>");
          xml.append("<Data>");
          xml.append("<Table tableId=\"").append(FileReviseTableId).append("\" tableName=\"HAIER_FILEREVISE").append("\">");
          xml.append("<Column>");
          xml.append("<field name=\"FILE_NAME\" type=\"varchar\">").append(preInfoProFile.getFileName()).append("</field>");
          xml.append("<field name=\"FILE_NUM\" type=\"varchar\">").append(preInfoProFile.getFileNum()).append("</field>");
          xml.append("<field name=\"CHARACTER\" type=\"varchar\">").append(preInfoProFile.getCharacter()).append("</field>");
          xml.append("<field name=\"VERSION\" type=\"varchar\">").append(preInfoProFile.getVersion()).append("</field>");
          xml.append("<field name=\"FILE_DATE\" type=\"varchar\">").append(preInfoProFile.getFileDate()).append("</field>");
          xml.append("<field name=\"REVIEW_DATE\" type=\"varchar\">").append(preInfoProFile.getReviewDate()).append("</field>");
          xml.append("<field name=\"DEPARTMENT\" type=\"varchar\">").append(preInfoProFile.getDepartment()).append("</field>");
          xml.append("<field name=\"AUTHOR\" type=\"varchar\">").append(preInfoProFile.getAuthor()).append("</field>");
          xml.append("<field name=\"FILE_ID\" type=\"varchar\">").append(preInfoProFile.getFileId()).append("</field>");
          xml.append("</Column>");
          xml.append("</Table>");
          xml.append("</Data>");
          xml.append("</WorkFlow>");
          ClientInfoFromWeb webservice = new ClientInfoFromWeb();
          webservice.createNewProcess(xml.toString());
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return "success";
  }
}
