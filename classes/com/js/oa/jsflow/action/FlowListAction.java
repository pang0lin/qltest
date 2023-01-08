package com.js.oa.jsflow.action;

import com.js.oa.jsflow.bean.ProcessQuery;
import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.CreateFlowReportData;
import com.js.oa.jsflow.vo.ZSFHTaizhangVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterFilter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

public class FlowListAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FlowListActionForm myFlowActionForm = (FlowListActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String action = httpServletRequest.getParameter("action");
    String workflowContent = httpServletRequest.getParameter("workflowContent");
    if (action == null)
      action = "newFlow"; 
    String tag = "";
    String moduleId = httpServletRequest.getParameter("moduleId");
    httpServletRequest.setAttribute("moduleId", moduleId);
    if (!ParameterFilter.isNumber(moduleId))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    if (action.equals("newFlow")) {
      ProcessBD processBD = new ProcessBD();
      List<Object[]> processList = processBD.getUserProcessList(userId, orgIdString, moduleId, workflowContent);
      ArrayList<Object[]> packageList = new ArrayList();
      if (processList != null) {
        String upPackageId = "";
        for (int i = 0; i < processList.size(); i++) {
          Object[] processObj = processList.get(i);
          if (!upPackageId.equals(processObj[0].toString())) {
            upPackageId = processObj[0].toString();
            Object[] packageObj = { processObj[0], processObj[1] };
            packageList.add(packageObj);
          } 
        } 
      } 
      httpServletRequest.setAttribute("packageList", packageList);
      httpServletRequest.setAttribute("processList", processList);
      tag = "newFlow";
    } else if ("delete".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      WorkFlowCommonBD wfc = new WorkFlowCommonBD();
      if (ids != null)
        wfc.deleteDraftRecord(ids); 
      action = "draft";
    } else if ("oftenList".equals(action)) {
      ProcessBD processBD = new ProcessBD();
      if (httpServletRequest.getParameter("processId") != null) {
        String processId = httpServletRequest.getParameter("processId");
        String type = httpServletRequest.getParameter("type");
        String[] procIds = processId.split(",");
        for (int i = 0; i < procIds.length; i++)
          processBD.setProcessOnDeskTop(userId, procIds[i], type); 
      } 
      String whereSql = processBD.getProcWhereSql(userId, orgIdString, "new");
      String para = " bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP";
      String from = "com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb";
      String where = "where (" + whereSql + ")  and bbb.moduleId = " + moduleId + " and aaa.isPublish=1 order by bbb.orderCode,bbb.wfPackageId desc,aaa.wfWorkFlowProcessId ";
      list(httpServletRequest, para, from, where);
      httpServletRequest.setAttribute("pageParameters", "action,moduleId");
      httpServletRequest.setAttribute("desktopID", processBD.getDeskTopFlowId(userId));
      tag = "oftenList";
    } 
    if ("draft".equals(action)) {
      list(httpServletRequest, 
          "po.id,po.processId,po.tableId,po.recordId,po.workTitle,po.saveTime,po.processName,po.relProjectId", 
          "com.js.oa.jsflow.po.JSFPDraftPO po", 
          "where po.userId=" + userId + " order by po.saveTime desc");
      httpServletRequest.setAttribute("pageParameters", "action");
      tag = "draftList";
    } 
    if ("reportPage".equals(action)) {
      Object object = httpServletRequest.getSession().getAttribute("domainId");
      ProcessBD procBD = new ProcessBD();
      List processList = procBD.getAllProcess((String)object);
      httpServletRequest.setAttribute("processList", processList);
      return actionMapping.findForward(action);
    } 
    if ("flowReport".equals(action)) {
      String beginDate = httpServletRequest.getParameter("beginDate").replace("/", "-");
      String endDate = httpServletRequest.getParameter("endDate").replace("/", "-");
      String flowId = (httpServletRequest.getParameter("flowId") == null) ? "" : httpServletRequest.getParameter("flowId");
      String orgIds = (httpServletRequest.getParameter("orgIds") == null) ? "" : httpServletRequest.getParameter("orgIds");
      String flowSubmitId = (httpServletRequest.getParameter("flowSubmitId") == null) ? "" : httpServletRequest.getParameter("flowSubmitId");
      String dealwithOrgIds = (httpServletRequest.getParameter("dealwithOrgIds") == null) ? "" : httpServletRequest.getParameter("dealwithOrgIds");
      String dealwithId = (httpServletRequest.getParameter("dealwithId") == null) ? "" : httpServletRequest.getParameter("dealwithId");
      String flowStatus = (httpServletRequest.getParameter("flowStatus") == null) ? "" : httpServletRequest.getParameter("flowStatus");
      String[] exportCond = { beginDate, endDate, flowId, orgIds, flowSubmitId, dealwithOrgIds, dealwithId, flowStatus };
      String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/upload/" + ((new Date()).getYear() + 1900) + "/flowFile/");
      boolean flag = (new CreateFlowReportData()).flowReport(String.valueOf(filePath) + "/流程用时统计报表.xls", exportCond);
      if (flag) {
        String fileUrl = String.valueOf(filePath) + "/流程用时统计报表.xls";
        httpServletRequest.setAttribute("fromWorkflowReport", "1");
        httpServletRequest.setAttribute("fileSaveName", fileUrl);
        httpServletRequest.setAttribute("fileName", "流程用时统计报表.xls");
        return actionMapping.findForward(action);
      } 
      return actionMapping.findForward("exportClose");
    } 
    if ("flowquery".equals(action)) {
      String orgId = session.getAttribute("orgId").toString();
      ProcessQuery p = new ProcessQuery();
      p.query(httpServletRequest, orgId);
      return actionMapping.findForward("flowquery");
    } 
    if ("myFlow".equals(action)) {
      String workFileType = (httpServletRequest.getParameter("workFileType") == null) ? "" : httpServletRequest.getParameter("workFileType");
      String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup  ,aaa.workDeadlineDate,aaa.stickie,aaa.workReadMarker,  aaa.workStatus";
      String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
      String whereSql = "";
      whereSql = " where (aaa.activityDelaySend IS NULL OR aaa.activityDelaySend = '' OR aaa.activityDelaySend <= date_format(now(), '%Y-%c-%d %H:%i:%s')) AND aaa.workStatus IN (0, 2, 101, 102,- 1 ,- 2, 1, 100) AND (aaa.wfCurEmployeeId =" + 

        
        userId + " or aaa.workSubmitPerson = " + userId + ")" + 
        " and aaa.workFileType = '" + workFileType + "'" + 
        " AND aaa.workListControl = 1 AND aaa.workDelete = 0" + 
        " ORDER BY aaa.workSubmitTime DESC, aaa.wfWorkId DESC";
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      httpServletRequest.setAttribute("workFileType", workFileType);
      httpServletRequest.setAttribute("workList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,workFileType");
      return actionMapping.findForward("myFlow");
    } 
    if ("export".equals(action))
      return actionMapping.findForward("export"); 
    if ("toexcel_taizhang".equals(action)) {
      String tableTitle = "项目任务单,项目任务单-合约阶段,项目任务单-投标阶段,发票申请单,项目盖章申请,项目退付款单";
      String headTitle = "立项编号,立项时间,申请人,项目名称,项目专业,项目渠道,渠道联系人,项目服务内容,经营方式,项目类型,甲方名称,甲方联系人和方式,渠道联系方式,甲方公司性质,报名时间,协作单位,信息费,立项-经营委员会品类负责人,投标实施人,项目开标时间,招标类型,上传中标通知书,投标-企业发展部,投标-企业发展部印章,生产委员会批示,生产人员,计划完成时间,生产人员上传报告,生产-总工办上传,生产-总工办批示意见,生产-企业部印章管理,经营人员,部门,所属公司,项目总结,选择标书审核人,标书审核,项目重要程度,生产组长或生产人,建设规模,总投资,专家姓名,专家联系方式,是否需要评审,评审层级,评审层级填写,企发部负责人,投标结果,开标人,人员证书使用,业务证书使用,项目证书使用,产值,项目重要备注,立项时间,申请人,项目专业,项目渠道,渠道联系人,渠道联系方式,项目服务内容,经营方式,项目类型,甲方名称,甲方联系人,甲方公司性质,报名时间,协作单位,信息费,合同金额,付款方式,分包商名称,分包账户信息,备注,合同有效金额,合同电子档上传,合约-企业发展部法务组,合约-企业发展部印章,分包金额,合同编号,实签甲方单位名称,合约-甲方联系人,合约-甲方联系方式,甲方地址,甲方账户信息,产值,经营委员会品类负责人,返回合同编号,合同扫描件上传,付款约定1,付款约定2,付款约定3,付款约定4,付款约定5,付款约定6,付款约定7,付款约定8,预计毛利率,部门,项目重要性,评审层级,评审层级填写,所属公司,项目是否需要评审,项目重要备注,合约阶段-信息费,立项时间,申请人,项目专业,项目渠道,渠道联系人,项目服务内容,经营方式,项目类型,甲方名称,甲方联系人和方式,甲方公司性质,报名时间,协作单位,信息费,经营委员会品类负责人,企业发展部负责人,投标实施人,投标结果,中标通知书,项目开标时间,招标类型,开标人,企业发展部,选择审核人,标书审核,企业发展部印章,项目重要性,部门,产值,渠道联系方式,项目是否需要评审,评审层级,评审层级填写,所属公司,项目重要备注,财务主管,开票人,经办人姓名,经营委员会品类负责人,发票申请编号,预计时间,领取人,已开票累计,已回款累计,已付款累计,合同金额,预计毛利率,实际毛利,毛利率,开票合计,申请日期,申请人,盖章页书,盖章类型,部门经理,公司领导,文件名称,报送单位,上传附件,项目收款合计,项目付款合计,单据上传,经营委员会,财务主管,出纳,项目阶段,已开票累积,已付款累计,合同额,预计毛利率,实际毛利,毛利率,已累计回款";
      String viewSql = "t1.zsfh_3021_f3312,t1.zsfh_3021_f3313,t1.zsfh_3021_f3314,t1.zsfh_3021_f3315,t1.zsfh_3021_f3316,t1.zsfh_3021_f3317,t1.zsfh_3021_f3318,t1.zsfh_3021_f3320,t1.zsfh_3021_f3321,t1.zsfh_3021_f3322,t1.zsfh_3021_f3323,t1.zsfh_3021_f3324,t1.zsfh_3021_f3319,t1.zsfh_3021_f3325,t1.zsfh_3021_f3326,t1.zsfh_3021_f3327,t1.zsfh_3021_f3328,t1.zsfh_3021_f3329,t1.zsfh_3021_f3330,t1.zsfh_3021_f3331,t1.zsfh_3021_f3332,t1.zsfh_3021_f3333,t1.zsfh_3021_f3334,t1.zsfh_3021_f3335,t1.zsfh_3021_f3336,t1.zsfh_3021_f3337,t1.zsfh_3021_f3338,t1.zsfh_3021_f3339,t1.zsfh_3021_f3340,t1.zsfh_3021_f3341,t1.zsfh_3021_f3342,t1.zsfh_3021_f3343,t1.zsfh_3021_f3344,t1.zsfh_3021_f3345,t1.zsfh_3021_f3346,t1.zsfh_3021_f3347,t1.zsfh_3021_f3348,t1.zsfh_3021_f3349,t1.zsfh_3021_f3350,t1.zsfh_3021_f3351,t1.zsfh_3021_f3352,t1.zsfh_3021_f3353,t1.zsfh_3021_f3354,t1.zsfh_3021_f3355,t1.zsfh_3021_f3356,t1.zsfh_3021_f3357,t1.zsfh_3021_f3358,t1.zsfh_3021_f3359,t1.zsfh_3021_f3360,t1.zsfh_3021_f3361,t1.zsfh_3021_f3362,t1.zsfh_3021_f3363,t1.zsfh_3021_f3364,t1.zsfh_3021_f3378,t4.zsfh_3013_f3175,t4.zsfh_3013_f3176,t4.zsfh_3013_f3178,t4.zsfh_3013_f3179,t4.zsfh_3013_f3180,t4.zsfh_3013_f3181,t4.zsfh_3013_f3182,t4.zsfh_3013_f3183,t4.zsfh_3013_f3184,t4.zsfh_3013_f3185,t4.zsfh_3013_f3186,t4.zsfh_3013_f3187,t4.zsfh_3013_f3188,t4.zsfh_3013_f3189,t4.zsfh_3013_f3190,t4.zsfh_3013_f3191,t4.zsfh_3013_f3192,t4.zsfh_3013_f3194,t4.zsfh_3013_f3195,t4.zsfh_3013_f3203,t4.zsfh_3013_f3204,t4.zsfh_3013_f3205,t4.zsfh_3013_f3206,t4.zsfh_3013_f3207,t4.zsfh_3013_f3196,t4.zsfh_3013_f3197,t4.zsfh_3013_f3198,t4.zsfh_3013_f3199,t4.zsfh_3013_f3200,t4.zsfh_3013_f3201,t4.zsfh_3013_f3202,t4.zsfh_3013_f3208,t4.zsfh_3013_f3209,t4.zsfh_3013_f3218,t4.zsfh_3013_f3219,t4.zsfh_3013_f3220,t4.zsfh_3013_f3221,t4.zsfh_3013_f3222,t4.zsfh_3013_f3223,t4.zsfh_3013_f3224,t4.zsfh_3013_f3225,t4.zsfh_3013_f3226,t4.zsfh_3013_f3227,t4.zsfh_3013_f3248,t4.zsfh_3013_f3272,t4.zsfh_3013_f3273,t4.zsfh_3013_f3274,t4.zsfh_3013_f3275,t4.zsfh_3013_f3276,t4.zsfh_3013_f3277,t4.zsfh_3013_f3294,t4.zsfh_3013_f3298,t5.zsfh_3017_f3277,t5.zsfh_3017_f3278,t5.zsfh_3017_f3280,t5.zsfh_3017_f3281,t5.zsfh_3017_f3282,t5.zsfh_3017_f3283,t5.zsfh_3017_f3284,t5.zsfh_3017_f3285,t5.zsfh_3017_f3286,t5.zsfh_3017_f3287,t5.zsfh_3017_f3288,t5.zsfh_3017_f3289,t5.zsfh_3017_f3290,t5.zsfh_3017_f3291,t5.zsfh_3017_f3292,t5.zsfh_3017_f3293,t5.zsfh_3017_f3294,t5.zsfh_3017_f3295,t5.zsfh_3017_f3296,t5.zsfh_3017_f3297,t5.zsfh_3017_f3298,t5.zsfh_3017_f3299,t5.zsfh_3017_f3300,t5.zsfh_3017_f3301,t5.zsfh_3017_f3302,t5.zsfh_3017_f3303,t5.zsfh_3017_f3304,t5.zsfh_3017_f3305,t5.zsfh_3017_f3306,t5.zsfh_3017_f3307,t5.zsfh_3017_f3308,t5.zsfh_3017_f3309,t5.zsfh_3017_f3310,t5.zsfh_3017_f3311,t5.zsfh_3017_f3393,t1.zsfh_3021_id";
      String viewSql1 = "t2.zsfh_3016_f3255,t2.zsfh_3016_f3256,t2.zsfh_3016_f3257,t2.zsfh_3016_f3258,t2.zsfh_3016_f3259,t2.zsfh_3016_f3260,t2.zsfh_3016_f3261,t2.zsfh_3016_f3262,t2.zsfh_3016_f3263,t2.zsfh_3016_f3264,t2.zsfh_3016_f3265,t2.zsfh_3016_f3266,t2.zsfh_3016_f3267,t2.zsfh_3016_f3268,t2.zsfh_3016_f3269";
      String viewSql2 = "t3.zsfh_3024_f3385,t3.zsfh_3024_f3386,t3.zsfh_3024_f3387,t3.zsfh_3024_f3388,t3.zsfh_3024_f3389,t3.zsfh_3024_f3390,t3.zsfh_3024_f3391,t3.zsfh_3024_f3392,t3.zsfh_3024_f3395";
      String viewSql3 = "t6.zsfh_3010_f3149,t6.zsfh_3010_f3150,t6.zsfh_3010_f3151,t6.zsfh_3010_f3152,t6.zsfh_3010_f3153,t6.zsfh_3010_f3154,t6.zsfh_3010_f3216,t6.zsfh_3010_f3231,t6.zsfh_3010_f3233,t6.zsfh_3010_f3234,t6.zsfh_3010_f3235,t6.zsfh_3010_f3236,t6.zsfh_3010_f3237,t6.zsfh_3010_f3232";
      String fromSql = "zsfh_3021 t1 LEFT JOIN zsfh_3013 t4 ON t4.zsfh_3013_f3174 = CONCAT(t1.zsfh_3021_id,'@@$@@',t1.zsfh_3021_f3312) LEFT JOIN zsfh_3017 t5 ON t5.zsfh_3017_f3276 = CONCAT(t1.zsfh_3021_id,'@@$@@',t1.zsfh_3021_f3312)";
      String whereSql = "";
      String orderBy = "t1.zsfh_3021_f3313";
      String projectName = httpServletRequest.getParameter("projectName");
      String projector = httpServletRequest.getParameter("projector");
      String projectNo = httpServletRequest.getParameter("projectNo");
      String firstPartyInfo = httpServletRequest.getParameter("firstPartyInfo");
      String profession = httpServletRequest.getParameter("profession");
      String serviceContent = httpServletRequest.getParameter("serviceContent");
      if ("1".equals(httpServletRequest.getParameter("searchDate"))) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          whereSql = " and t1.zsfh_3021_f3313 between '" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00' and '" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59' ";
        } else {
          whereSql = " and t1.zsfh_3021_f3313 between JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59','L') ";
        } 
      } 
      if (projectName != null && !"".equals(projectName))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3315 like '%" + projectName + "%' "; 
      if (projector != null && !"".equals(projector))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3314 like '%" + projector + "%' "; 
      if (projectNo != null && !"".equals(projectNo))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3312 like '%" + projectNo + "%' "; 
      if (firstPartyInfo != null && !"".equals(firstPartyInfo))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3323 like '%" + firstPartyInfo + "%' "; 
      if (profession != null && !"".equals(profession))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3316 like '%" + profession + "%' "; 
      if (serviceContent != null && !"".equals(serviceContent))
        whereSql = String.valueOf(whereSql) + " and t1.zsfh_3021_f3315 like '%" + serviceContent + "%' "; 
      List<ZSFHTaizhangVO> dataList = new ArrayList<ZSFHTaizhangVO>();
      try {
        DataSourceBase base = new DataSourceBase();
        DataSourceBase base2 = new DataSourceBase();
        try {
          base.begin();
          base2.begin();
          String sql = "select " + viewSql + " from " + fromSql + " where 1=1 " + whereSql + ("".equals(orderBy) ? "" : (" order by " + orderBy));
          ResultSet rs = base.executeQuery(sql);
          ResultSetMetaData rsmd = rs.getMetaData();
          int columnCount = rsmd.getColumnCount() - 1;
          while (rs.next()) {
            ZSFHTaizhangVO taizhang = new ZSFHTaizhangVO();
            List<Object[]> invoiceTable = new ArrayList();
            List<Object[]> sealApplyTable = new ArrayList();
            List<Object[]> paymentTable = new ArrayList();
            String[] arrayOfString = new String[columnCount];
            for (int i = 0; i < columnCount; i++)
              arrayOfString[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1); 
            String id = (rs.getString(columnCount + 1) == null) ? "" : rs.getString(columnCount + 1);
            String sql1 = "select " + viewSql1 + " from zsfh_3016 t2 where t2.zsfh_3016_f3251 = CONCAT('" + id + "','@@$@@','" + arrayOfString[0] + "')";
            String sql2 = "select " + viewSql2 + " from zsfh_3024 t3 where t3.zsfh_3024_f3383 = CONCAT('" + id + "','@@$@@','" + arrayOfString[0] + "')";
            String sql3 = "select " + viewSql3 + " from zsfh_3010 t6 where t6.zsfh_3010_f3145 = CONCAT('" + id + "','@@$@@','" + arrayOfString[0] + "')";
            ResultSet rs1 = base2.executeQuery(sql1);
            ResultSetMetaData rsmd1 = rs1.getMetaData();
            int columnCount1 = rsmd1.getColumnCount();
            while (rs1.next()) {
              String[] arrayOfString1 = new String[columnCount1];
              for (int j = 0; j < columnCount1; j++)
                arrayOfString1[j] = (rs1.getString(j + 1) == null) ? "" : rs1.getString(j + 1); 
              arrayOfString1[1] = arrayOfString1[1].equals("") ? "" : arrayOfString1[1].toString().split(";")[0];
              invoiceTable.add(arrayOfString1);
            } 
            rs1.close();
            ResultSet rs2 = base2.executeQuery(sql2);
            ResultSetMetaData rsmd2 = rs2.getMetaData();
            int columnCount2 = rsmd2.getColumnCount();
            while (rs2.next()) {
              String[] arrayOfString1 = new String[columnCount2];
              for (int j = 0; j < columnCount2; j++)
                arrayOfString1[j] = (rs2.getString(j + 1) == null) ? "" : rs2.getString(j + 1); 
              arrayOfString1[8] = arrayOfString1[8].equals("") ? "" : arrayOfString1[8].toString().split(";")[1];
              sealApplyTable.add(arrayOfString1);
            } 
            rs2.close();
            ResultSet rs3 = base2.executeQuery(sql3);
            ResultSetMetaData rsmd3 = rs3.getMetaData();
            int columnCount3 = rsmd3.getColumnCount();
            while (rs3.next()) {
              String[] arrayOfString1 = new String[columnCount3];
              for (int j = 0; j < columnCount3; j++)
                arrayOfString1[j] = (rs3.getString(j + 1) == null) ? "" : rs3.getString(j + 1); 
              arrayOfString1[2] = arrayOfString1[2].equals("") ? "" : arrayOfString1[2].toString().split(";")[1];
              arrayOfString1[5] = arrayOfString1[5].equals("") ? "" : arrayOfString1[5].toString().split(";")[0];
              paymentTable.add(arrayOfString1);
            } 
            rs3.close();
            arrayOfString[18] = arrayOfString[18].equals("") ? "" : arrayOfString[18].toString().split(";")[0];
            arrayOfString[21] = arrayOfString[21].equals("") ? "" : arrayOfString[21].toString().split(";")[1];
            arrayOfString[25] = arrayOfString[25].equals("") ? "" : arrayOfString[25].toString().split(";")[0];
            arrayOfString[27] = arrayOfString[27].equals("") ? "" : arrayOfString[27].toString().split(";")[1];
            arrayOfString[28] = arrayOfString[28].equals("") ? "" : arrayOfString[28].toString().split(";")[1];
            arrayOfString[31] = arrayOfString[31].equals("") ? "" : arrayOfString[31].toString().split(";")[1];
            arrayOfString[35] = arrayOfString[35].equals("") ? "" : arrayOfString[35].toString().split(";")[0];
            arrayOfString[38] = arrayOfString[38].equals("") ? "" : arrayOfString[38].toString().split(";")[0];
            arrayOfString[48] = arrayOfString[48].equals("") ? "" : arrayOfString[48].toString().split(";")[0];
            arrayOfString[75] = arrayOfString[75].equals("") ? "" : arrayOfString[75].toString().split(";")[1];
            arrayOfString[88] = arrayOfString[88].equals("") ? "" : arrayOfString[88].toString().split(";")[1];
            arrayOfString[122] = arrayOfString[122].equals("") ? "" : arrayOfString[122].toString().split(";")[0];
            arrayOfString[124] = arrayOfString[124].equals("") ? "" : arrayOfString[124].toString().split(";")[1];
            arrayOfString[127] = arrayOfString[127].equals("") ? "" : arrayOfString[127].toString().split(";")[0];
            arrayOfString[129] = arrayOfString[129].equals("") ? "" : arrayOfString[129].toString().split(";")[0];
            taizhang.setMainTable((Object[])arrayOfString);
            taizhang.setInvoiceTable(invoiceTable);
            taizhang.setSealApplyTable(sealApplyTable);
            taizhang.setPaymentTable(paymentTable);
            taizhang.setMaxlen(taizhang.maxLen());
            dataList.add(taizhang);
          } 
          base2.end();
          rs.close();
          base.end();
        } catch (Exception e) {
          base.end();
          base2.end();
          e.printStackTrace();
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("dataList", dataList);
      httpServletRequest.setAttribute("tableTitle", tableTitle);
      httpServletRequest.setAttribute("headTitle", headTitle);
      return actionMapping.findForward("toexcel_taizhang");
    } 
    if (!"".equals(workflowContent) && workflowContent != null) {
      String databaseType = SystemCommon.getDatabaseType();
      WFProcessEJBBean bean = new WFProcessEJBBean();
      String whereSql = "";
      try {
        whereSql = bean.getProcWhereSql(userId, orgIdString, "new");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String para = " bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId,";
      para = String.valueOf(para) + " aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,";
      para = String.valueOf(para) + " aaa.remindField,aaa.formType,aaa.startJSP";
      String from = " from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb";
      String where = " where (" + whereSql + ") and bbb.moduleId = " + moduleId + " and aaa.isPublish=1";
      where = String.valueOf(where) + " and aaa.processStatus=1";
      where = String.valueOf(where) + " and (aaa.workFlowProcessName like '%" + workflowContent + "%' or aaa.processDescription like '%" + workflowContent + "%') ";
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by bbb.orderCode,bbb.wfPackageId desc,convert(aaa.workFlowProcessName using gbk ),aaa.wfWorkFlowProcessId ";
      } else {
        where = String.valueOf(where) + " order by bbb.orderCode,bbb.wfPackageId desc,aaa.workFlowProcessName,aaa.wfWorkFlowProcessId ";
      } 
      list(httpServletRequest, para, from, where);
      tag = "workflowContent";
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request, String para, String from, String where) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    String action = request.getParameter("action");
    String workflowContent = request.getParameter("workflowContent");
    String moduleId = request.getParameter("moduleId");
    request.setAttribute("workList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,workflowContent,moduleId");
  }
}
