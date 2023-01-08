package com.js.wap.bean;

import com.js.oa.jsflow.po.WFWorkPO;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.math.PolishNotation;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import com.js.wap.util.WorkDealWith;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkDealWithBean extends HibernateBase {
  public Map getWorkDealWithSimpleInfo(String workId) {
    Map<Object, Object> itemInfo = new HashMap<Object, Object>();
    Connection conn = null;
    try {
      begin();
      String hql = "select po.wfCurEmployeeId, po.workFileType, po.workCurStep, po.workTitle, po.workLeftLinkFile, po.workMainLinkFile, po.workListControl, po.workActivity, po.workSubmitPerson, po.workSubmitTime, po.wfSubmitEmployeeId, po.workReadMarker, po.workType,po.workProcessId,po.workTableId, po.workRecordId, po.workDeadLine,po.workPressTime, po.workCreateDate, po.workStartFlag,po.workDoneWithDate, po.workAllowCancel, po.workCancelReason, po.workDelete,po.workIsTran, po.workUser,po.workStepCount,po.creatorCancelLink,po.isStandForWork,po.standForUserId,po.standForUserName,po.initActivity,po.initStepCount, po.submitOrg, po.printNum,po.emergence,po.initActivityName,po.workDeadlineDate,po.workDeadlinePressDate,po.tranType,po.tranFromPersonId,po.processDeadlineDate,po.wfCurEmployeeOrgId,po.relProjectId,po.transActType,po.workStatus from com.js.oa.jsflow.po.WFWorkPO po where po.wfWorkId=" + 










        
        workId;
      WFWorkPO po = new WFWorkPO();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Object[] obj = this.session.createQuery(hql).iterate().next();
      if (obj != null) {
        po.setWfCurEmployeeId(Long.valueOf(obj[0].toString()));
        po.setWorkFileType((String)obj[1]);
        po.setWorkCurStep((String)obj[2]);
        po.setWorkTitle((String)obj[3]);
        po.setWorkMainLinkFile((String)obj[5]);
        po.setWorkListControl(Integer.valueOf(Integer.parseInt(obj[6].toString())));
        po.setWorkActivity(Long.valueOf(obj[7].toString()));
        po.setWorkSubmitPerson(obj[8].toString());
        po.setWorkSubmitTime(dateFormat.parse(obj[9].toString()));
        po.setWfSubmitEmployeeId(Long.valueOf(obj[10].toString()));
        po.setWorkReadMarker(Integer.valueOf(Integer.parseInt(obj[11].toString())));
        po.setWorkType(Integer.valueOf(Integer.parseInt(obj[12].toString())));
        po.setWorkProcessId(Long.valueOf(obj[13].toString()));
        po.setWorkTableId(Long.valueOf(obj[14].toString()));
        po.setWorkRecordId(Long.valueOf(obj[15].toString()));
        po.setWorkDeadLine(Long.valueOf(obj[16].toString()));
        po.setWorkPressTime(Long.valueOf(obj[17].toString()));
        po.setWorkCreateDate(dateFormat.parse(obj[18].toString()));
        if (obj[19] != null && !"".equals(obj[19].toString()))
          po.setWorkStartFlag(Integer.valueOf(Integer.parseInt(obj[19].toString()))); 
        if (obj[21] != null && !"".equals(obj[21].toString()))
          po.setWorkAllowCancel(Integer.valueOf(Integer.parseInt(obj[21].toString()))); 
        po.setWorkDelete(Integer.valueOf(Integer.parseInt(obj[23].toString())));
        if (obj[26] != null && !"".equals(obj[26].toString()))
          po.setWorkStepCount(Integer.valueOf(Integer.parseInt(obj[26].toString()))); 
        po.setIsStandForWork((obj[28] != null && !"null".equals(obj[28].toString())) ? obj[28].toString() : "");
        if (obj[29] != null && !"".equals(obj[29]))
          po.setStandForUserId(Long.valueOf(obj[29].toString())); 
        if (obj[30] != null && !"".equals(obj[30]))
          po.setStandForUserName(obj[30].toString()); 
        po.setInitActivity(Long.valueOf((obj[31] != null && !"null".equals(obj[31].toString())) ? obj[31].toString() : "0"));
        po.setInitStepCount(Integer.valueOf(Integer.parseInt((obj[32] != null && !"null".equals(obj[32].toString())) ? obj[32].toString() : "0")));
        po.setSubmitOrg(obj[33].toString());
        po.setEmergence((String)obj[35]);
        po.setInitActivityName((String)obj[36]);
        po.setWorkDeadlineDate((obj[37] != null) ? dateFormat.parse(obj[37].toString()) : null);
        po.setWorkDeadlinePressDate((obj[38] != null) ? dateFormat.parse(obj[38].toString()) : null);
        if (obj[39] == null) {
          po.setTranType("0");
        } else {
          po.setTranType((String)obj[39]);
        } 
        if (obj[43] == null || obj[43].toString().equals("null") || obj[43].toString().equals("")) {
          po.setRelProjectId(Long.valueOf(-1L));
        } else {
          po.setRelProjectId(Long.valueOf(obj[43].toString()));
        } 
        po.setTransActType((String)obj[44]);
        if (obj[44] == null) {
          po.setTransActType("0");
        } else {
          po.setTransActType(obj[44].toString());
        } 
        if (obj[45] != null)
          po.setWorkStatus(Integer.valueOf(Integer.parseInt(obj[45].toString()))); 
      } 
      itemInfo.put("workPO", po);
      this.session.close();
      WorkVO workVO = new WorkVO();
      workVO.setId(Long.valueOf(workId));
      workVO.setTableId(po.getWorkTableId());
      workVO.setRecordId(po.getWorkRecordId());
      workVO.setStepCount(String.valueOf(po.getWorkStepCount()));
      WorkFlowButtonBD wfBD = new WorkFlowButtonBD();
      WorkFlowBD workFlowBD = new WorkFlowBD();
      StringBuffer cmdButtons = new StringBuffer(",");
      boolean hasMore = wfBD.hasMoreDealFile(workVO);
      if (!"1".equals(po.getIsStandForWork()) && hasMore) {
        cmdButtons.append("End,");
      } else if ("1".equals(po.getIsStandForWork()) && hasMore && ("3".equals(po.getTransActType()) || "1".equals(po.getTransActType()))) {
        cmdButtons.append("End,");
      } 
      if ("1".equals(po.getTranType()))
        cmdButtons.append("EndAutoReturn,"); 
      List<ActivityVO> activityList = workFlowBD.getAllNextActivity(po.getWorkTableId().toString(), po.getWorkRecordId().toString(), po.getWorkActivity().toString());
      if (activityList.size() == 1 && ((ActivityVO)activityList.get(0)).getBeginEnd() == 2)
        if (cmdButtons.length() < 2 && po.getWorkStatus().intValue() != 100)
          cmdButtons.append("EndOnlyComp,");  
      if (cmdButtons.length() < 2 && po.getWorkStatus().intValue() != 100)
        cmdButtons.append("Send,"); 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select activityClass,acticommfield,acticommfieldtype,passroundcommfield from JSDB.jsf_p_activity where ttable_id=" + 
          po.getWorkTableId().toString() + " and trecord_id=" + po.getWorkRecordId().toString() + " and wf_activity_id=" + po.getWorkActivity().toString());
      if (rs.next()) {
        String actiClass = rs.getString(1);
        if ("3".equals(actiClass))
          cmdButtons.append("EndAutoReturn,"); 
        itemInfo.put("activityClass", actiClass);
        itemInfo.put("curCommField", rs.getString(2));
        itemInfo.put("actiCommFieldType", rs.getString(3));
        itemInfo.put("curPassRoundCommField", rs.getString(4));
      } 
      rs.close();
      String oprButtonTmp = "";
      rs = stmt.executeQuery("select operButton from jsf_p_activity where wf_activity_id=" + po.getWorkActivity() + " and ttable_id=" + po.getWorkTableId() + 
          " and trecord_id=" + po.getWorkRecordId());
      if (rs.next())
        oprButtonTmp = rs.getString("operButton"); 
      rs.close();
      rs = stmt.executeQuery("select opinionmust from jsf_activity where wf_activity_id=" + po.getWorkActivity());
      if (rs.next()) {
        String opinionMust = rs.getString(1);
        if ("1".equals(opinionMust))
          itemInfo.put("opinionMust", "1"); 
      } 
      rs.close();
      if (oprButtonTmp.indexOf("Tran") >= 0)
        cmdButtons.append("Tran,"); 
      if (oprButtonTmp.indexOf("Back") >= 0)
        cmdButtons.append("Back,"); 
      if (oprButtonTmp.indexOf("Feedback") >= 0)
        cmdButtons.append("Feedback,"); 
      if (oprButtonTmp.indexOf("TranRead") != -1)
        cmdButtons.append("TranRead"); 
      WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
      String moduleId = workFlowCommonBD.getModuleId((String)po.getWorkTableId());
      if ("2".equals(moduleId) || "3".equals(moduleId))
        cmdButtons.append(",Viewtext,Viewacc,Printcomm,Printtext"); 
      if ("34".equals(moduleId))
        cmdButtons.append(",Viewtext,Viewacc"); 
      if (oprButtonTmp.indexOf("Delete") >= 0)
        cmdButtons.append("Delete,"); 
      itemInfo.put("operButton", cmdButtons.toString());
      String mainFileURL = (po.getWorkMainLinkFile() == null) ? "" : po.getWorkMainLinkFile();
      if (mainFileURL.indexOf("WorkFlowProcAction.do") >= 0 || mainFileURL.indexOf("WorkFlowReSubmitAction.do") > 0) {
        List<String[]> areaList = (List)new ArrayList<String>();
        rs = stmt.executeQuery("select area_id,area_table,areatype_id,tt.table_desname,tt.table_id from tarea ta left join ttable tt on ta.area_table=tt.table_name where ta.page_id=" + po.getWorkTableId() + " order by areatype_id");
        while (rs.next()) {
          String areaId = rs.getString(1);
          String areaTable = rs.getString(2);
          String areaType = rs.getString(3);
          String tableName = rs.getString(4);
          String tableId = rs.getString(5);
          String[] areaArray = new String[8];
          areaArray[0] = areaId;
          areaArray[1] = areaTable;
          areaArray[2] = areaType;
          areaArray[3] = tableName;
          areaArray[4] = tableId;
          areaArray[5] = (String)po.getWorkActivity();
          areaArray[6] = (String)po.getWorkRecordId();
          areaArray[7] = (String)po.getWorkTableId();
          areaList.add(areaArray);
        } 
        rs.close();
        Map<Object, Object> subTable = new HashMap<Object, Object>();
        for (int i = 0; i < areaList.size(); i++) {
          String[] areaArray = areaList.get(i);
          if (areaArray[2].equals("102")) {
            List<String[]> tableFieldInfo = getMainTableFieldValue(areaArray, stmt, po.getWorkRecordId().toString(), po.getWorkTableId().toString());
            itemInfo.put("mainTableInfo", tableFieldInfo);
            String[] comment = getComment(po.getWorkTableId().toString(), po.getWorkRecordId().toString(), stmt);
            if (!"".equals(comment[6]))
              itemInfo.put("mainCommentInfo", comment); 
          } else if (areaArray[2].equals("105")) {
            subTable.put(areaArray[3], getSubTableFieldValue(areaArray, stmt, po.getWorkRecordId().toString()));
          } 
        } 
        itemInfo.put("subTableInfo", subTable);
      } else if (mainFileURL.indexOf("InformationAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getInfoList(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("GovSendFileLoadAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getSendFile(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("GovReceiveFileLoadAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getReceiveList(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("BoardRoomAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getBoradRoomApply(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("VoitureApplyAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getVehApply(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("EquipmentAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getEquipApply(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("IntoStockAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getIntoStockList(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("OutStockAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getOutStockList(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } else if (mainFileURL.indexOf("archivesAction.do") >= 0) {
        List<String[]> list = (new WorkDealWith()).getArchivesList(po.getWorkRecordId().toString(), po.getWorkTableId().toString());
        itemInfo.put("mainTableInfo", list);
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (this.session != null)
          this.session.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return itemInfo;
  }
  
  public List<String[]> getMainTableFieldValue(String[] areaArray, Statement stmt, String recordId, String tableId) {
    List<String[]> tableFieldInfo = (List)new ArrayList<String>();
    try {
      StringBuffer sql = new StringBuffer();
      int i = 0;
      ResultSet rs = stmt.executeQuery("select tf.field_id,tf.field_desname,tf.field_name,tf.field_type,tf.field_show,field_value from telt te left join tfield tf on te.elt_table=tf.field_name where te.area_id=" + areaArray[0] + " and tf.field_table=" + areaArray[4] + " order by tf.field_sequence");
      while (rs.next()) {
        String[] temp = new String[7];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        temp[4] = rs.getString(5);
        temp[5] = rs.getString(6);
        tableFieldInfo.add(temp);
        if (i == 0) {
          sql.append(temp[2]);
        } else {
          sql.append(",").append(temp[2]);
        } 
        i++;
      } 
      rs.close();
      String writeField = ",";
      rs = stmt.executeQuery("SELECT r.CONTROLFIELD FROM jsf_p_readwritecontrol r JOIN jsf_p_activity a ON r.WF_PROCEEDACTIVITY_ID=a.WF_PROCEEDACTIVITY_ID WHERE r.CONTROLTYPE=1 AND r.WF_ACTIVITY_ID=" + 
          areaArray[5] + " AND a.TTABLE_ID=" + areaArray[7] + " AND a.TRECORD_ID=" + areaArray[6]);
      while (rs.next())
        writeField = String.valueOf(writeField) + rs.getString(1) + ","; 
      rs.close();
      rs = stmt.executeQuery("select " + sql.toString() + " from " + areaArray[1] + " where " + areaArray[1] + "_id=" + recordId);
      if (rs.next())
        for (i = 0; i < tableFieldInfo.size(); i++) {
          String[] temp = tableFieldInfo.get(i);
          temp[6] = rs.getString(i + 1);
        }  
      rs.close();
      for (i = 0; i < tableFieldInfo.size(); i++) {
        String[] temp = tableFieldInfo.get(i);
        String tempValue = temp[6];
        int fieldType = Integer.parseInt((temp[4] == null) ? "0" : temp[4]);
        if ((tempValue == null || "null".equals(tempValue)) && fieldType != 401 && 
          !",121,122,123,".contains((new StringBuilder(String.valueOf(fieldType))).toString())) {
          tempValue = "";
        } else {
          String srcThree = "0000";
          if (tempValue != null && tempValue.length() > 6 && tempValue.substring(4, 5).equals("_")) {
            srcThree = tempValue.substring(0, 4);
          } else {
            srcThree = "0000";
          } 
          switch (fieldType) {
            case 102:
              tempValue = "********";
              break;
            case 103:
              tempValue = getSelectOrRadioValue(tempValue, temp[5], stmt);
              break;
            case 104:
              tempValue = getCheckboxValue(tempValue, temp[5], stmt);
              break;
            case 105:
              tempValue = getSelectOrRadioValue(tempValue, temp[5], stmt);
              break;
            case 115:
              tempValue = getAttachValue(tempValue);
              break;
            case 116:
              tempValue = "<div><a href=\"/jsoa/upload/" + srcThree + "/information/" + tempValue + ".doc\">正文</a></div>";
              break;
            case 117:
              tempValue = "<div><a href=\"/jsoa/upload/" + srcThree + "/information/" + tempValue + ".xls\">正文</a></div>";
              break;
            case 121:
              if (writeField.contains("," + temp[0] + ","))
                tempValue = "<span>@@curUser@@</span><input type='hidden' name='" + 
                  temp[2] + "' id='" + temp[2] + "' value='@@curUser@@' /> "; 
              break;
            case 122:
              if (writeField.contains("," + temp[0] + ","))
                tempValue = "<span>@@curOrg@@</span><input type='hidden' name='" + 
                  temp[2] + "' id='" + temp[2] + "' value='@@curOrg@@' /> "; 
              break;
            case 123:
              if (writeField.contains("," + temp[0] + ",")) {
                String curTime = (new SimpleDateFormat(temp[5])).format(new Date());
                tempValue = "<span>" + curTime + "</span>" + 
                  "<input type='hidden' name='" + temp[2] + "' id='" + temp[2] + "' value='" + curTime + "' /> ";
              } 
              break;
            case 210:
              if (tempValue.indexOf(";") > 0)
                tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
              break;
            case 211:
              if (tempValue.indexOf(";") > 0)
                tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
              break;
            case 212:
              if (tempValue.indexOf(";") > 0)
                tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
              break;
            case 214:
              if (tempValue.indexOf(";") > 0)
                tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
              break;
            case 401:
              tempValue = getCommentByField(tableId, recordId, temp[2].toString(), stmt);
              break;
            case 450:
              if (tempValue.indexOf("@@$@@") >= 0)
                tempValue = tempValue.split("\\@\\@\\$\\@\\@")[1]; 
              break;
          } 
        } 
        temp[6] = tempValue;
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return tableFieldInfo;
  }
  
  public Map getSubTableFieldValue(String[] areaArray, Statement stmt, String recordId) {
    Map<String, List<String[]>> subTableMap = new HashMap<String, List<String[]>>();
    List<String[]> tableFieldInfo = (List)new ArrayList<String>();
    try {
      StringBuffer sql = new StringBuffer();
      int i = 0;
      ResultSet rs = stmt.executeQuery("select tf.field_id,tf.field_desname,tf.field_name,tf.field_type,tf.field_show,field_value,field_index from telt te left join tfield tf on te.elt_table=tf.field_name where te.area_id=" + areaArray[0] + " and tf.field_table=" + areaArray[4] + " order by tf.field_id");
      while (rs.next()) {
        String[] temp = new String[8];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        temp[4] = rs.getString(5);
        temp[5] = rs.getString(6);
        temp[6] = rs.getString(7);
        temp[7] = "0";
        tableFieldInfo.add(temp);
        if (i == 0) {
          sql.append(temp[2]);
        } else {
          sql.append(",").append(temp[2]);
        } 
        i++;
      } 
      rs.close();
      subTableMap.put("fieldInfo", tableFieldInfo);
      List<String[]> subTableContentList = (List)new ArrayList<String>();
      IO2File.printFile("select " + sql.toString() + " from " + areaArray[1] + " where " + areaArray[1] + "_foreignkey=" + recordId + " order by " + areaArray[1] + "_id");
      rs = stmt.executeQuery("select " + sql.toString() + " from " + areaArray[1] + " where " + areaArray[1] + "_foreignkey=" + recordId + " order by " + areaArray[1] + "_id");
      while (rs.next()) {
        String[] subTableContent = new String[tableFieldInfo.size()];
        for (i = 0; i < tableFieldInfo.size(); i++)
          subTableContent[i] = rs.getString(i + 1); 
        subTableContentList.add(subTableContent);
      } 
      rs.close();
      for (i = 0; i < tableFieldInfo.size(); i++) {
        String[] temp = tableFieldInfo.get(i);
        int fieldType = Integer.parseInt((temp[4] == null) ? "0" : temp[4]);
        for (int j = 0; j < subTableContentList.size(); j++) {
          String tempValue = ((String[])subTableContentList.get(j))[i];
          if (tempValue == null || "null".equals(tempValue)) {
            tempValue = "";
          } else {
            String srcOne = "0000";
            if (tempValue != null && tempValue.length() > 6 && tempValue.substring(4, 5).equals("_")) {
              srcOne = tempValue.substring(0, 4);
            } else {
              srcOne = "0000";
            } 
            switch (fieldType) {
              case 102:
                tempValue = "********";
                break;
              case 103:
                tempValue = getSelectOrRadioValue(tempValue, temp[5], stmt);
                break;
              case 104:
                tempValue = getCheckboxValue(tempValue, temp[5], stmt);
                break;
              case 105:
                tempValue = getSelectOrRadioValue(tempValue, temp[5], stmt);
                break;
              case 115:
                tempValue = getAttachValue(tempValue);
                break;
              case 116:
                tempValue = "<div><a href=\"/jsoa/upload/" + srcOne + "/information/" + tempValue + ".doc\">正文</a></div>";
                break;
              case 117:
                tempValue = "<div><a href=\"/jsoa/upload/" + srcOne + "/information/" + tempValue + ".xls\">正文</a></div>";
                break;
              case 210:
                if (tempValue.indexOf(";") > 0)
                  tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
                break;
              case 211:
                if (tempValue.indexOf(";") > 0)
                  tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
                break;
              case 212:
                if (tempValue.indexOf(";") > 0)
                  tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
                break;
              case 214:
                if (tempValue.indexOf(";") > 0)
                  tempValue = tempValue.substring(0, tempValue.indexOf(";")); 
                break;
              case 302:
                tempValue = String.valueOf(j + 1);
                break;
              case 401:
              case 450:
                if (tempValue.indexOf("@@$@@") >= 0)
                  tempValue = tempValue.split("\\@\\@\\$\\@\\@")[1]; 
                break;
            } 
          } 
          ((String[])subTableContentList.get(j))[i] = tempValue;
          try {
            if ("1".equals(temp[6])) {
              double sumTemp = Double.valueOf(temp[7]).doubleValue() + Double.valueOf("".equals(tempValue) ? "0" : tempValue).doubleValue();
              temp[7] = String.valueOf(sumTemp);
              tableFieldInfo.set(i, temp);
            } 
          } catch (Exception err) {
            err.printStackTrace();
          } 
        } 
      } 
      subTableMap.put("subTableFieldValue", subTableContentList);
      try {
        String sumStr = "";
        for (i = 0; i < tableFieldInfo.size(); i++) {
          String[] temp = tableFieldInfo.get(i);
          if (temp != null && "1".equals(temp[6])) {
            double sumTemp = Double.parseDouble(temp[7]);
            if (Math.round(sumTemp) - sumTemp == 0.0D) {
              sumStr = String.valueOf(sumStr) + temp[1] + ":" + String.valueOf((long)sumTemp) + "&nbsp;&nbsp;";
            } else {
              sumStr = String.valueOf(sumStr) + temp[1] + ":" + temp[7] + "&nbsp;&nbsp;";
            } 
          } 
        } 
        if (!"".equals(sumStr)) {
          String[] sumTmp = new String[1];
          sumTmp[0] = sumStr;
          List<String[]> sumList = (List)new ArrayList<String>();
          sumList.add(sumTmp);
          subTableMap.put("subTableSumValue", sumList);
        } 
      } catch (Exception err) {
        err.printStackTrace();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return subTableMap;
  }
  
  public String getSelectOrRadioValue(String tempValue, String fieldValue, Statement stmt) {
    if (tempValue.contains("@#@")) {
      tempValue = tempValue.split("@#@")[1];
    } else if (fieldValue.startsWith("@")) {
      String table = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
      String[][] data = (String[][])null;
      try {
        ResultSet rs = stmt.executeQuery("select " + table.substring(0, table.indexOf(".")) + "_id," + 
            table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")) + " where " + table.substring(0, table.indexOf(".")) + "_id=" + tempValue);
        if (rs.next())
          tempValue = rs.getString(2); 
      } catch (Exception e3) {
        e3.printStackTrace();
      } 
    } else if (fieldValue.startsWith("$")) {
      String sqlStr, dataSourceName = "system";
      if (fieldValue.indexOf("].$[") > 0) {
        dataSourceName = fieldValue.substring(2, fieldValue.indexOf("].$["));
        sqlStr = fieldValue.substring(fieldValue.indexOf("].$[") + 4, fieldValue.length() - 1);
      } else {
        sqlStr = fieldValue.substring(2, fieldValue.length() - 1);
      } 
      String[][] data = (String[][])null;
      DbOpt dbo = null;
      try {
        if (!"system".equals(dataSourceName)) {
          dbo = new DbOpt(dataSourceName);
          String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
          if (dbType.indexOf("oracle") >= 0) {
            String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
            if (!"".equals(lang))
              dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
          } 
          data = dbo.executeQueryToStrArr2(sqlStr);
          dbo.close();
        } else {
          dbo = new DbOpt();
          data = dbo.executeQueryToStrArr2(sqlStr);
          dbo.close();
        } 
      } catch (Exception e3) {
        if (dbo != null)
          try {
            dbo.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e3.printStackTrace();
      } 
      if (data != null)
        for (int j = 0; j < data.length; j++) {
          if (tempValue.equals(data[j][0])) {
            tempValue = data[j][1];
            break;
          } 
        }  
    } else {
      if (fieldValue.startsWith("*")) {
        String parentId = fieldValue.substring(fieldValue.indexOf(".*[") + 3, fieldValue.length() - 1);
        fieldValue = (new BaseSetEJBBean()).getValue(parentId);
      } else if (fieldValue.startsWith("#[interface:")) {
        fieldValue = InitWorkFlowData.getShowContext(fieldValue, tempValue);
      } 
      String[] fieldValueArr = fieldValue.split(";");
      for (int j = 0; j < fieldValueArr.length; j++) {
        if (fieldValueArr[j] != null && 
          fieldValueArr[j].trim().length() > 0 && 
          fieldValueArr[j].indexOf("/") > 0 && 
          fieldValueArr[j].indexOf("/") < fieldValueArr[j].length() - 1 && 
          fieldValueArr[j].split("/")[0].equals(tempValue))
          tempValue = fieldValueArr[j].split("/")[1]; 
      } 
    } 
    return tempValue;
  }
  
  public String getCheckboxValue(String tempValue, String fieldValue, Statement stmt) {
    if (fieldValue.startsWith("@")) {
      String table = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
      String[][] data = (String[][])null;
      try {
        String dbType = SystemCommon.getDatabaseType();
        String tableName = table.substring(0, table.indexOf("."));
        String sql = "select " + tableName + "_id," + 
          table.substring(table.indexOf(".") + 1, table.length()) + 
          " from " + tableName;
        if (dbType.indexOf("oracle") >= 0) {
          sql = String.valueOf(sql) + " where '," + tempValue + "' like '%,'||" + tableName + "_id||',%'";
        } else if (dbType.indexOf("mysql") >= 0) {
          sql = String.valueOf(sql) + " where '," + tempValue + "' like concat('%,'," + tableName + "_id,',%')";
        } else {
          sql = String.valueOf(sql) + " where '," + tempValue + "' like concat('%,'," + tableName + "_id,',%')";
        } 
        ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) {
          if (i == 0) {
            tempValue = rs.getString(2);
          } else {
            tempValue = String.valueOf(tempValue) + "," + rs.getString(2);
          } 
          i++;
        } 
      } catch (Exception e3) {
        e3.printStackTrace();
      } 
    } else if (fieldValue.startsWith("$")) {
      String sqlStr, dataSourceName = "system";
      if (fieldValue.indexOf("].$[") > 0) {
        dataSourceName = fieldValue.substring(2, fieldValue.indexOf("].$["));
        sqlStr = fieldValue.substring(fieldValue.indexOf("].$[") + 4, fieldValue.length() - 1);
      } else {
        sqlStr = fieldValue.substring(2, fieldValue.length() - 1);
      } 
      String[][] data = (String[][])null;
      DbOpt dbo = null;
      try {
        if (!"system".equals(dataSourceName)) {
          dbo = new DbOpt(dataSourceName);
          String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
          if (dbType.indexOf("oracle") >= 0) {
            String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
            if (!"".equals(lang))
              dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
          } 
          data = dbo.executeQueryToStrArr2(sqlStr);
          dbo.close();
        } else {
          dbo = new DbOpt();
          data = dbo.executeQueryToStrArr2(sqlStr);
          dbo.close();
        } 
      } catch (Exception e3) {
        if (dbo != null)
          try {
            dbo.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e3.printStackTrace();
      } 
      if (data != null) {
        String tempValue1 = "," + tempValue;
        tempValue = "";
        for (int j = 0; j < data.length; j++) {
          if (tempValue1.indexOf("," + data[j][0] + ",") >= 0)
            tempValue = String.valueOf(tempValue) + data[j][1] + ","; 
        } 
        if (tempValue.endsWith(","))
          tempValue = tempValue.substring(0, tempValue.length() - 1); 
      } 
    } else {
      if (fieldValue.startsWith("*")) {
        String parentId = fieldValue.substring(fieldValue.indexOf(".*[") + 3, fieldValue.length() - 1);
        fieldValue = (new BaseSetEJBBean()).getValue(parentId);
      } else if (fieldValue.startsWith("#[interface:")) {
        fieldValue = InitWorkFlowData.getShowContext(fieldValue, tempValue);
      } 
      String[] fieldValueArr = fieldValue.split(";");
      String tempValue1 = "," + tempValue;
      tempValue = "";
      for (int j = 0; j < fieldValueArr.length; j++) {
        if (fieldValueArr[j] != null && 
          fieldValueArr[j].trim().length() > 0 && 
          fieldValueArr[j].indexOf("/") > 0 && 
          fieldValueArr[j].indexOf("/") < fieldValueArr[j].length() - 1 && 
          tempValue1.indexOf("," + fieldValueArr[j].split("/")[0] + ",") >= 0)
          tempValue = String.valueOf(tempValue) + fieldValueArr[j].split("/")[1] + ","; 
      } 
      if (tempValue.endsWith(","))
        tempValue = tempValue.substring(0, tempValue.length() - 1); 
    } 
    return tempValue;
  }
  
  public String getAttachValue(String tempValue) {
    String filePath = "/jsoa";
    if (SystemCommon.getUseClusterServer() == 1)
      filePath = SystemCommon.getClusterServerUrl(); 
    if (tempValue != null && tempValue.length() > 0) {
      String[] tempValueArr = tempValue.split(";");
      if (tempValueArr.length > 1) {
        String[] attachSaveName = tempValueArr[0].split(",");
        String[] attachFileName = tempValueArr[1].split(",");
        tempValue = "";
        for (int j = 0; j < attachSaveName.length; j++) {
          if (!"".equals(attachSaveName[j])) {
            String srcLove = "0000";
            if (attachSaveName[j] != null && attachSaveName[j].length() > 6 && attachSaveName[j].substring(4, 5).equals("_")) {
              srcLove = attachSaveName[j].substring(0, 4);
            } else {
              srcLove = "0000";
            } 
            tempValue = String.valueOf(tempValue) + "<div><a href=\"" + filePath + "/upload/" + srcLove + "/customform/" + attachSaveName[j] + "\">" + attachFileName[j] + "</a></div>";
          } 
        } 
      } 
    } 
    return tempValue;
  }
  
  public String[] getComment(String tableId, String recordId, Statement stmt) {
    String sql = "";
    StringBuffer commentBuffer = new StringBuffer("");
    String[] comment = { "", "批示意见", "", "", "", "", "" };
    try {
      List<String[]> list = new ArrayList();
      sql = "select a.activityname,b.dealwithemployeecomment,b.dealwithtime,c.empname,b.wf_dealwith_id,a.activity_id from jsf_dealwith a,jsf_dealwithcomment b,org_employee c where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id=c.emp_id and a.databaserecord_id=" + 

        
        recordId + 
        " and databasetable_id=" + tableId + " and (b.commentfield is null or b.commentfield='-1' or b.commentfield='' or b.commentfield='null') ";
      if (SystemCommon.getShowBackComment() != 1)
        sql = String.valueOf(sql) + " and (b.commentisback<>1) "; 
      sql = String.valueOf(sql) + " order by a.wf_dealwith_id,b.dealwithtime";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = String.valueOf(rs.getDate(3).toString()) + " " + rs.getTime(3).toString();
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        list.add(tmp);
      } 
      int i;
      for (i = 0; i < list.size(); i++) {
        String[] tmp = list.get(i);
        rs = stmt.executeQuery("SELECT distinct actiCommFieldType FROM JSDB.JSF_ACTIVITY WHERE WF_ACTIVITY_ID=" + tmp[5]);
        if (rs.next()) {
          tmp[6] = rs.getString(1);
        } else {
          tmp[6] = "";
        } 
        rs.close();
        list.set(i, tmp);
      } 
      for (i = 0; i < list.size(); i++) {
        String[] tmp = list.get(i);
        if (!"-1".equals(tmp[6]))
          commentBuffer.append("<h4>" + tmp[0] + "</h4>")
            .append(String.valueOf(tmp[1]) + "<br/>")
            .append("<div align=\"right\">" + tmp[3] + " ")
            .append(String.valueOf(tmp[2]) + "</div><br/>"); 
      } 
      if (!"".equals(commentBuffer.toString()))
        comment[6] = commentBuffer.toString(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return comment;
  }
  
  public String getCommentByField(String tableId, String recordId, String fieldName, Statement stmt) {
    StringBuffer comment = new StringBuffer("");
    try {
      String sql = "";
      sql = "select a.activityname,b.dealwithemployeecomment,b.dealwithtime,c.empname,b.wf_dealwith_id from jsf_dealwith a,jsf_dealwithcomment b,org_employee c where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id=c.emp_id and a.databaserecord_id=" + 

        
        recordId + 
        " and databasetable_id=" + tableId + " and b.commentfield='" + fieldName + "'";
      if (SystemCommon.getShowBackComment() != 1)
        sql = String.valueOf(sql) + " and (b.commentisback<>1) "; 
      sql = String.valueOf(sql) + " order by a.wf_dealwith_id,b.dealwithtime";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        comment.append(String.valueOf(rs.getString(2)) + "<br/>")
          .append("<div align=\"right\">" + rs.getString(4) + " ")
          .append(String.valueOf(rs.getDate(3).toString()) + " " + rs.getTime(3).toString() + "</div>"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return comment.toString();
  }
  
  public String getCalculateValue(String[][] tableFieldValue, String tempValue) {
    byte b;
    int i;
    String[][] arrayOfString;
    for (i = (arrayOfString = tableFieldValue).length, b = 0; b < i; ) {
      String[] temp = arrayOfString[b];
      if (tempValue.indexOf(temp[2]) >= 0)
        tempValue = tempValue.replaceAll(temp[2], temp[2]); 
      b++;
    } 
    PolishNotation pn = new PolishNotation();
    tempValue = pn.calculate(tempValue);
    return tempValue;
  }
  
  public List searchUserList(String queryName) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select a.empId,a.empName from com.js.system.vo.usermanager.EmployeeVO a where a.userIsActive=1 and a.userIsDeleted=0 and (a.empId<>0 and a.empId<>-99) and (a.empName like '%" + 
          
          queryName + "%' or a.userAccounts like '%" + queryName + "%')").list();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String docFlowIds() {
    String ids = "-1";
    String sql = "SELECT w.WF_WORKFLOWPROCESS_ID FROM jsf_package p JOIN jsf_workflowprocess w ON p.WF_PACKAGE_ID=w.WF_PACKAGE_ID WHERE (p.WF_MODULE_ID=2 OR p.WF_MODULE_ID=3)";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        ids = String.valueOf(ids) + "," + rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return ids;
  }
  
  public String docFlowIdsByType(String type) {
    int modId = 2;
    if ("receive".equals(type))
      modId = 3; 
    String ids = "-1";
    String sql = "SELECT w.WF_WORKFLOWPROCESS_ID FROM jsf_package p JOIN jsf_workflowprocess w ON p.WF_PACKAGE_ID=w.WF_PACKAGE_ID WHERE p.WF_MODULE_ID=" + 
      modId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        ids = String.valueOf(ids) + "," + rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return ids;
  }
}
