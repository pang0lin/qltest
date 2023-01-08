package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.service.ReportFormsBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportFormsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String viewSQL = "";
    String fromSQL = "";
    String whereSQL = "";
    String voitureViewSQL = "po.id,po.num";
    String voitureFromSQL = "com.js.oa.routine.voiture.po.VoiturePO po";
    String voitureWhereSQL = " where po.domainId=" + domainId + " order by po.id";
    String voitureSendViewSQL = "b.id,sum(po.keepingFee),sum(po.travelFee)";
    String voitureSendFromSQL = 
      "com.js.oa.routine.voiture.po.VoitureSendPO po join po.voiturePO b";
    String voitureSendWhereSQL = "";
    ReportFormsBD bd = new ReportFormsBD();
    String tag = "";
    String action = (httpServletRequest.getParameter("action") == null) ? "list" : 
      httpServletRequest.getParameter("action");
    if ("list".equals(action)) {
      tag = "list";
    } else if ("report".equals(action)) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
      String tmpbeginDate = null;
      String tmpendDate = null;
      Date beginDate = null;
      Date endDate = null;
      if (httpServletRequest.getParameter("beginDate") != null) {
        tmpbeginDate = httpServletRequest.getParameter("beginDate").toString();
        try {
          beginDate = formatter.parse(tmpbeginDate);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (beginDate != null)
          tmpbeginDate = String.valueOf(beginDate.getYear() + 1900) + "-" + (
            beginDate.getMonth() + 1) + "-" + beginDate.getDate(); 
      } 
      if (httpServletRequest.getParameter("endDate") != null) {
        tmpendDate = httpServletRequest.getParameter("endDate")
          .toString();
        try {
          endDate = formatter.parse(tmpendDate);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (endDate != null)
          tmpendDate = String.valueOf(endDate.getYear() + 1900) + "-" + (
            endDate.getMonth() + 1) + "-" + endDate.getDate(); 
      } 
      String whichReport = httpServletRequest.getParameter("whichReport");
      String tmpList = "";
      if ("CMRF".equals(whichReport)) {
        tag = "CMRF";
        List<Object[]> totalList = new ArrayList();
        viewSQL = 
          "b.id,sum(a.oilCost),sum(a.oilFee),sum(a.fixFee),sum(a.washFee),sum(a.taxFee),sum(a.insureFee),sum(a.roadFee),sum(a.yearTicketFee),sum(a.yearSensorFee),sum(a.purchaseTax),sum(a.brandFee),sum(a.otherFee),sum(a.accidentFee),sum(a.monthFee)";
        fromSQL = "com.js.oa.routine.voiture.po.VoitureFeePO a join a.voiturePO b ";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = "where a.maintainTime between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "'" + 
            " and b.domainId=" + domainId + " group by b.id order by b.id ";
        } else {
          whereSQL = "where a.maintainTime between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S')" + 
            " and b.domainId=" + domainId + " group by b.id order by b.id ";
        } 
        List<Object[]> companyMonthReportFormsList = bd.listReportForms(viewSQL, fromSQL, 
            whereSQL);
        if (databaseType.indexOf("mysql") >= 0) {
          voitureSendWhereSQL = 
            "where po.sendStartDate between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "'" + 
            " and po.domainId=" + domainId + " group by b.id order by b.id ";
        } else {
          voitureSendWhereSQL = 
            "where po.sendStartDate between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S')" + 
            " and po.domainId=" + domainId + " group by b.id order by b.id ";
        } 
        List<Object[]> voitueSendList = bd.listVoitureSend(voitureSendViewSQL, 
            voitureSendFromSQL, 
            voitureSendWhereSQL);
        List<Object[]> voitueInfoList = bd.listVoitureInfo(voitureViewSQL, voitureFromSQL, 
            voitureWhereSQL);
        if (!voitueInfoList.isEmpty())
          for (int i = 0; i < voitueInfoList.size(); i++) {
            int len2 = 0;
            int len3 = 0;
            Object[] obj2 = (Object[])null;
            Object[] obj3 = (Object[])null;
            Object[] obj1 = voitueInfoList.get(i);
            if (!companyMonthReportFormsList.isEmpty())
              for (int j = 0; j < companyMonthReportFormsList.size(); j++) {
                obj2 = companyMonthReportFormsList.get(j);
                if (obj1[0].toString().equals(obj2[0].toString())) {
                  len2 = obj2.length;
                  len2 = 15;
                  break;
                } 
                if (!obj1[0].toString().equals(obj2[0].toString()))
                  obj2 = new Object[15]; 
              }  
            if (!voitueSendList.isEmpty())
              for (int k = 0; k < voitueSendList.size(); k++) {
                obj3 = voitueSendList.get(k);
                if (obj1[0].toString().equals(obj3[0].toString())) {
                  len3 = 3;
                  break;
                } 
                if (!obj1[0].toString().equals(obj3[0].toString()))
                  obj3 = new Object[3]; 
              }  
            Object[] totalOBJ = new Object[20];
            int d;
            for (d = 0; d < obj1.length; d++)
              totalOBJ[d] = obj1[d]; 
            if (len2 > 0) {
              for (d = 0; d < len2; d++)
                totalOBJ[obj1.length + d] = obj2[d]; 
            } else {
              for (d = 0; d < 15; d++)
                totalOBJ[obj1.length + d] = null; 
            } 
            if (len3 > 0) {
              for (d = 0; d < len3; d++)
                totalOBJ[obj1.length + 15 + d] = obj3[d]; 
            } else {
              for (d = 0; d < 3; d++)
                totalOBJ[obj1.length + 15 + d] = null; 
            } 
            totalList.add(i, totalOBJ);
          }  
        httpServletRequest.setAttribute("totalList", totalList);
      } else if ("OVUS".equals(whichReport)) {
        tag = "OVUS";
        viewSQL = "select b.orgName,b.empName,sum(a.sendStartKilo),sum(a.sendEndKilo),sum((a.sendEndKilo-a.sendStartKilo)*a.kiloPrice),sum(a.sendStartTimeTotal),sum(a.sendEndTimeTotal),sum(a.sendCount)";
        fromSQL = " from JSDB.VEH_Send a, JSDB.VEH_Apply b , JSDB.VEH_INFO c ";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = " where a.applyid=b.applyid and a.voitureid=c.voitureid and  a.sendStartDate between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "' and b.domain_Id=" + domainId + " group by b.orgName,b.empName  ";
        } else {
          whereSQL = " where a.applyid=b.applyid and a.voitureid=c.voitureid and  a.sendStartDate between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S') and b.domain_Id=" + domainId + " group by b.orgName,b.empName  ";
        } 
        List organizationVoitureUseStationList = bd.listReportForms2(viewSQL, 
            fromSQL, whereSQL);
        httpServletRequest.setAttribute("organizationVoitureUseStationList", 
            organizationVoitureUseStationList);
      } else if ("LAOVUS".equals(whichReport)) {
        tag = "LAOVUS";
        viewSQL = "select b.orgName,sum(a.sendStartKilo),sum(a.sendEndKilo),sum((a.sendEndKilo-a.sendStartKilo)*a.kiloPrice),sum(a.sendStartTimeTotal),sum(a.sendEndTimeTotal),sum(a.sendCount),count(*) ";
        fromSQL = " from JSDB.VEH_Send a, JSDB.VEH_Apply b , JSDB.VEH_INFO c ";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = " where  a.applyid=b.applyid and a.voitureid=c.voitureid and a.sendStartDate between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "' and b.domain_Id=" + domainId + " group by b.orgName";
        } else {
          whereSQL = " where  a.applyid=b.applyid and a.voitureid=c.voitureid and a.sendStartDate between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S') and b.domain_Id=" + domainId + " group by b.orgName";
        } 
        List leaderAndOrganizationVoitureUseStationList = bd.listReportForms2(
            viewSQL, fromSQL, whereSQL);
        httpServletRequest.setAttribute(
            "leaderAndOrganizationVoitureUseStationList", 
            leaderAndOrganizationVoitureUseStationList);
      } else if ("CHYRF".equals(whichReport)) {
        tag = "CHYRF";
        List<Object[]> totalList = new ArrayList();
        viewSQL = "b.id,sum(a.oilCost),sum(a.oilFee),sum(a.fixFee),sum(a.washFee),sum(a.taxFee),sum(a.insureFee),sum(a.roadFee),sum(a.yearTicketFee),sum(a.yearSensorFee),sum(a.purchaseTax),sum(a.brandFee),sum(a.otherFee),sum(a.accidentFee),sum(a.monthFee)";
        fromSQL = 
          "com.js.oa.routine.voiture.po.VoitureFeePO a join a.voiturePO b ";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = "where a.maintainTime between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "'" + 
            " and a.domainId=" + domainId + " group by b.id order by b.id ";
        } else {
          whereSQL = "where a.maintainTime between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S')" + 
            " and a.domainId=" + domainId + " group by b.id order by b.id ";
        } 
        List<Object[]> companyMonthReportFormsList = bd.listReportForms(viewSQL, fromSQL, whereSQL);
        if (databaseType.indexOf("mysql") >= 0) {
          voitureSendWhereSQL = 
            "where po.sendStartDate between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "'" + 
            " and po.domainId=" + domainId + " group by b.id order by b.id ";
        } else {
          voitureSendWhereSQL = 
            "where po.sendStartDate between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S')" + 
            " and po.domainId=" + domainId + " group by b.id order by b.id ";
        } 
        List<Object[]> voitueSendList = bd.listVoitureSend(voitureSendViewSQL, 
            voitureSendFromSQL, 
            voitureSendWhereSQL);
        List<Object[]> voitueInfoList = bd.listVoitureInfo(voitureViewSQL, voitureFromSQL, 
            voitureWhereSQL);
        if (!voitueInfoList.isEmpty()) {
          int len2 = 0;
          int len3 = 0;
          for (int i = 0; i < voitueInfoList.size(); i++) {
            Object[] obj2 = (Object[])null;
            Object[] obj3 = (Object[])null;
            Object[] obj1 = voitueInfoList.get(i);
            if (!companyMonthReportFormsList.isEmpty()) {
              int j = 0;
              for (; j < companyMonthReportFormsList.size(); 
                j++) {
                obj2 = companyMonthReportFormsList.get(j);
                if (obj1[0].toString().equals(obj2[0].toString())) {
                  len2 = obj2.length;
                  break;
                } 
                if (!obj1[0].toString().equals(obj2[0].toString()))
                  obj2 = new Object[15]; 
              } 
            } 
            if (!voitueSendList.isEmpty()) {
              int k = 0;
              for (; k < voitueSendList.size(); 
                k++) {
                obj3 = voitueSendList.get(k);
                if (obj1[0].toString().equals(obj3[0].toString())) {
                  len3 = obj3.length;
                  break;
                } 
                if (!obj1[0].toString().equals(obj3[0].toString()))
                  obj3 = new Object[3]; 
              } 
            } 
            Object[] totalOBJ = new Object[20];
            int d;
            for (d = 0; d < obj1.length; d++)
              totalOBJ[d] = obj1[d]; 
            if (len2 > 0) {
              for (d = 0; d < len2; d++)
                totalOBJ[obj1.length + d] = obj2[d]; 
            } else {
              for (d = 0; d < 15; d++)
                totalOBJ[obj1.length + d] = null; 
            } 
            if (len3 > 0) {
              for (d = 0; d < len3; d++)
                totalOBJ[obj1.length + 15 + d] = obj3[d]; 
            } else {
              for (d = 0; d < 3; d++)
                totalOBJ[obj1.length + 15 + d] = null; 
            } 
            totalList.add(i, totalOBJ);
          } 
        } 
        httpServletRequest.setAttribute("totalList", totalList);
      } else if ("MWRF".equals(whichReport)) {
        tag = "MWRF";
        viewSQL = "b.motorMan,sum(a.sendStartKilo),sum(a.sendEndKilo),sum(a.sendStartTimeTotal),sum(a.sendEndTimeTotal),sum(a.sendCount),sum(a.sendHolidayCount),sum(a.overTimeHoliDay),sum(a.misMealFee),sum(a.overTimeWeekend),sum(a.overTime),sum(a.otherAllowance)";
        fromSQL = 
          "com.js.oa.routine.voiture.po.VoitureSendPO a  join a.voitureApplyPO b";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = "where a.sendStartDate between '" + 
            tmpbeginDate + "' and '" + tmpendDate + 
            "' and b.domainId=" + domainId + " group by b.motorMan ";
        } else {
          whereSQL = "where a.sendStartDate between JSDB.FN_STRTODATE('" + 
            tmpbeginDate + "','S') and JSDB.FN_STRTODATE('" + tmpendDate + 
            "','S') and b.domainId=" + domainId + " group by b.motorMan ";
        } 
        List motormanWorkReportFormsList = bd.listReportForms(viewSQL, fromSQL, 
            whereSQL);
        httpServletRequest.setAttribute("motormanWorkReportFormsList", 
            motormanWorkReportFormsList);
      } else if ("YH".equals(whichReport)) {
        tag = "YH";
        viewSQL = "SELECT i.num,f.jsy,f.fwld,f.sylc,f.bylc,f.xslc,f.yy,f.bglhy,f.REMARK FROM veh_fee f JOIN veh_info i ON f.VOITUREID=i.voitureid";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          whereSQL = " WHERE f.MAINTAINTIME BETWEEN '" + tmpbeginDate.replace("/", "-") + 
            "' AND '" + tmpendDate.replace("/", "-") + "' and f.domain_Id=" + domainId + 
            " order by f.MAINTAINTIME ";
        } else {
          whereSQL = " where f.maintainTime between JSDB.FN_STRTODATE('" + tmpbeginDate + 
            "','S') and JSDB.FN_STRTODATE('" + tmpendDate + "','S')" + 
            " and f.domain_Id=" + domainId + " order by i.num,f.MAINTAINTIME ";
        } 
        httpServletRequest.setAttribute("data", (new DataSourceUtil()).getListQuery(String.valueOf(viewSQL) + whereSQL, ""));
      } 
    } 
    return actionMapping.findForward(tag);
  }
}
