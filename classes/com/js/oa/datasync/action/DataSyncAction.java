package com.js.oa.datasync.action;

import com.js.oa.datasync.po.DataSyncFieldPO;
import com.js.oa.datasync.po.DataSyncPO;
import com.js.oa.datasync.po.DataSyncSetPO;
import com.js.oa.datasync.service.DataSyncBD;
import com.js.util.page.simple.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DataSyncAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    DataSyncActionForm dataSyncActionForm = (DataSyncActionForm)actionForm;
    String action = request.getParameter("action");
    DataSyncBD dataSyncDB = new DataSyncBD();
    String tag = "";
    if ("syncList".equals(action)) {
      tag = "syncList";
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request
            .getParameter("pager.offset")); 
      String viewSQL = " aaa.id,aaa.syncTitle,aaa.memo,aaa.processId,aaa.activityId";
      String fromSQL = " from com.js.oa.datasync.po.DataSyncPO aaa";
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSQL, fromSQL, "");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,channelType,userChannelName,orderBy,depart,styleId,sortType,userId");
      request.setAttribute("dataSyncList", list);
    } else if ("toAddSync".equals(action)) {
      tag = "toAddSync";
      List processList = dataSyncDB.getAllProcess();
      request.setAttribute("processList", processList);
    } else if ("addSync".equals(action)) {
      DataSyncPO savePo = setDataSyncPO(dataSyncActionForm);
      dataSyncDB.saveDataSync(savePo);
    } else if ("toUpdateSync".equals(action)) {
      tag = "toUpdateSync";
      String id = request.getParameter("id");
      List processList = dataSyncDB.getAllProcess();
      request.setAttribute("processList", processList);
      DataSyncPO dataSyncPOBForEdit = dataSyncDB.getDataSyncPOById(Long.valueOf(id));
      dataSyncActionForm.setId(id);
      dataSyncActionForm.setSyncTitle(dataSyncPOBForEdit.getSyncTitle());
      dataSyncActionForm.setMemo(dataSyncPOBForEdit.getMemo());
      dataSyncActionForm.setProcessId(dataSyncPOBForEdit.getProcessId());
      dataSyncActionForm.setActivityId(dataSyncPOBForEdit.getActivityId());
      request.setAttribute("activityList", dataSyncDB.getProcessActivity(dataSyncPOBForEdit.getProcessId()));
      request.setAttribute("activityId", dataSyncPOBForEdit.getActivityId());
    } else if ("updateSync".equals(action)) {
      DataSyncPO updatePo = setDataSyncPO(dataSyncActionForm);
      updatePo.setId(Long.valueOf(dataSyncActionForm.getId()));
      dataSyncDB.updateDataSync(updatePo);
    } else {
      if ("deleteSync".equals(action)) {
        String ids = request.getParameter("ids");
        dataSyncDB.deleteDataSync(ids);
        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/DataSyncAction.do?action=syncList");
        actionForward.setRedirect(true);
        return actionForward;
      } 
      if ("detailList".equals(action)) {
        tag = "detailList";
        String syncId = request.getParameter("syncId");
        List dataSyncSetPOList = dataSyncDB.getAllDataSyncSetPO(Long.valueOf(syncId));
        request.setAttribute("dataSyncSetPOList", dataSyncSetPOList);
      } else if ("toAddDetail".equals(action)) {
        tag = "toAddDetail";
        String id = request.getParameter("id");
        DataSyncPO dataSyncPOById = dataSyncDB.getDataSyncPOById(Long.valueOf(id));
        List<String[]> tableList = dataSyncDB.getTableList(Long.valueOf(dataSyncPOById.getProcessId()));
        for (String[] tableInfo : tableList) {
          if ("form1".equals(tableInfo[0])) {
            request.setAttribute("mainTableName", tableInfo[1]);
            request.setAttribute("mainTableField", dataSyncDB.getTableFieldList(tableInfo[1]));
            continue;
          } 
          request.setAttribute("subTableField_" + tableInfo[0], dataSyncDB.getTableFieldList(tableInfo[1]));
        } 
        request.setAttribute("tableList", tableList);
      } else if ("addDetail".equals(action)) {
        String syncId = request.getParameter("id");
        DataSyncSetPO savePO = setDataSyncSetPO(dataSyncActionForm, request);
        savePO.setSyncId(syncId);
        Long syncSetId = dataSyncDB.saveDataSyncSetPO(savePO);
        String[] toField = request.getParameterValues("toField");
        String[] fromField = request.getParameterValues("fromField");
        String[] fromValueSet = request.getParameterValues("fromValueSet");
        for (int i = 0; i < toField.length; i++) {
          DataSyncFieldPO saveFieldPO = new DataSyncFieldPO();
          saveFieldPO.setSyncSetId(syncSetId);
          if ("".equals(fromField[i])) {
            saveFieldPO.setFieldIsMainForm("0");
            saveFieldPO.setFromField("");
            saveFieldPO.setFromType("0");
          } else {
            String[] fromFieldTemp = fromField[i].split(";");
            saveFieldPO.setFieldIsMainForm(fromFieldTemp[0]);
            saveFieldPO.setFromField(fromFieldTemp[1]);
            saveFieldPO.setFromType(fromFieldTemp[2]);
          } 
          saveFieldPO.setToField(toField[i]);
          saveFieldPO.setFromValueSet(fromValueSet[i]);
          dataSyncDB.saveDataSyncFieldPO(saveFieldPO);
        } 
      } else if ("toUpdateDetail".equals(action)) {
        tag = "toUpdateDetail";
        String syncSetId = request.getParameter("syncSetId");
        request.setAttribute("syncSetId", syncSetId);
        DataSyncSetPO dataSyncSetPO = dataSyncDB.getDataSyncSetPOById(Long.valueOf(syncSetId));
        dataSyncActionForm.setSetTitle(dataSyncSetPO.getSetTitle());
        dataSyncActionForm.setSetType(dataSyncSetPO.getSetType());
        dataSyncActionForm.setDataSourceType(dataSyncSetPO.getDataSourceType());
        dataSyncActionForm.setDataSourceName(dataSyncSetPO.getDataSourceName());
        dataSyncActionForm.setOperateTable(dataSyncSetPO.getOperateTable());
        dataSyncActionForm.setOperateType(dataSyncSetPO.getOperateType());
        dataSyncActionForm.setExecuteOrder(dataSyncSetPO.getExecuteOrder());
        dataSyncActionForm.setDescription(dataSyncSetPO.getDescription());
        dataSyncActionForm.setExeCondition(dataSyncSetPO.getExeCondition());
        request.setAttribute("setType", dataSyncSetPO.getSetType());
        request.setAttribute("mainTableName", dataSyncSetPO.getMainTableName());
        request.setAttribute("subTableName", dataSyncSetPO.getSubTableName());
        request.setAttribute("dataSourceName", dataSyncSetPO.getDataSourceName());
        request.setAttribute("syncCondition", dataSyncSetPO.getSyncCondition());
        List<String[]> fieldList = dataSyncDB.getSyncSetFieldList(syncSetId);
        request.setAttribute("syncSetField", fieldList);
        String syncId = request.getParameter("syncId");
        DataSyncPO dataSyncPOById = dataSyncDB.getDataSyncPOById(Long.valueOf(syncId));
        List<String[]> tableList = dataSyncDB.getTableList(Long.valueOf(dataSyncPOById.getProcessId()));
        request.setAttribute("tableList", tableList);
        for (String[] tableInfo : tableList) {
          if ("form1".equals(tableInfo[0])) {
            request.setAttribute("mainTableName", tableInfo[1]);
            request.setAttribute("mainTableField", dataSyncDB.getTableFieldList(tableInfo[1]));
            continue;
          } 
          request.setAttribute("subTableField_" + tableInfo[0], dataSyncDB.getTableFieldList(tableInfo[1]));
        } 
      } else if ("updateDetail".equals(action)) {
        String syncSetId = request.getParameter("syncSetId");
        String syncId = request.getParameter("syncId");
        DataSyncSetPO savePO = setDataSyncSetPO(dataSyncActionForm, request);
        savePO.setId(Long.valueOf(syncSetId));
        savePO.setSyncId(syncId);
        dataSyncDB.updateDataSyncSetPO(savePO);
        String[] toField = request.getParameterValues("toField");
        String[] fromField = request.getParameterValues("fromField");
        String[] fromValueSet = request.getParameterValues("fromValueSet");
        dataSyncDB.deleteDataSyncSetFields(syncSetId);
        for (int i = 0; i < toField.length; i++) {
          DataSyncFieldPO saveFieldPO = new DataSyncFieldPO();
          saveFieldPO.setSyncSetId(Long.valueOf(syncSetId));
          if ("".equals(fromField[i])) {
            saveFieldPO.setFieldIsMainForm("0");
            saveFieldPO.setFromField("");
            saveFieldPO.setFromType("0");
          } else {
            String[] fromFieldTemp = fromField[i].split(";");
            saveFieldPO.setFieldIsMainForm(fromFieldTemp[0]);
            saveFieldPO.setFromField(fromFieldTemp[1]);
            saveFieldPO.setFromType(fromFieldTemp[2]);
          } 
          saveFieldPO.setToField(toField[i]);
          saveFieldPO.setFromValueSet(fromValueSet[i]);
          dataSyncDB.saveDataSyncFieldPO(saveFieldPO);
        } 
      } else if ("deleteDetail".equals(action)) {
        tag = "detailList";
        String ids = request.getParameter("ids");
        dataSyncDB.deleteDataSyncSetPO(ids);
        String syncId = request.getParameter("syncId");
        List dataSyncSetPOList = dataSyncDB.getAllDataSyncSetPO(Long.valueOf(syncId));
        request.setAttribute("dataSyncSetPOList", dataSyncSetPOList);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public DataSyncPO setDataSyncPO(DataSyncActionForm dataSyncActionForm) {
    DataSyncPO savePo = new DataSyncPO();
    savePo.setSyncTitle(dataSyncActionForm.getSyncTitle());
    savePo.setMemo(dataSyncActionForm.getMemo());
    savePo.setProcessId(dataSyncActionForm.getProcessId());
    savePo.setActivityId(dataSyncActionForm.getActivityId());
    return savePo;
  }
  
  public DataSyncSetPO setDataSyncSetPO(DataSyncActionForm dataSyncActionForm, HttpServletRequest request) {
    DataSyncSetPO savePO = new DataSyncSetPO();
    savePO.setSetTitle(dataSyncActionForm.getSetTitle());
    savePO.setDescription(dataSyncActionForm.getDescription());
    savePO.setSetType(request.getParameter("setType"));
    savePO.setDataSourceType(dataSyncActionForm.getDataSourceType());
    if ("0".equals(savePO.getDataSourceType())) {
      savePO.setDataSourceName(request.getParameter("dataSourceNameSys"));
    } else {
      savePO.setDataSourceName(request.getParameter("dataSourceNameField"));
    } 
    savePO.setOperateType(dataSyncActionForm.getOperateType());
    savePO.setOperateTable(dataSyncActionForm.getOperateTable());
    savePO.setExeCondition(dataSyncActionForm.getExeCondition());
    savePO.setExecuteOrder(dataSyncActionForm.getExecuteOrder());
    savePO.setMainTableName(request.getParameter("mainTableName"));
    savePO.setSubTableName(request.getParameter("subTableName"));
    savePO.setSyncCondition(request.getParameter("syncCondition"));
    return savePO;
  }
}
