package com.js.oa.hr.officemanager.action;

import com.active.e_uc.user.po.TblJilu;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.po.TblUserApp;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.oa.hr.officemanager.bean.EmployeeEJBBean;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.hr.personnelmanager.service.PersonalKindBD;
import com.js.oa.hr.personnelmanager.service.WorkAddressBD;
import com.js.oa.hr.personnelmanager.service.WorkAttendanceBD;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.jsflow.service.PackageBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.MD5;
import com.js.util.util.ReadActiveXml;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeActionForm employeeActionForm = (EmployeeActionForm)actionForm;
    EmployeeBD employeeBD = new EmployeeBD();
    String action = httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession(true);
    TblUserBD tblUserBD = new TblUserBD();
    TblUser tblUser = new TblUser();
    UserBD userBD = new UserBD();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String tag = "";
    String searchEmpName = (httpServletRequest.getParameter("searchEmpName") == null) ? 
      "" : 
      httpServletRequest.getParameter("searchEmpName");
    String searchEmpEnglishName = (httpServletRequest.getParameter(
        "searchEmpEnglishName") == null) ? "" : 
      httpServletRequest
      .getParameter("searchEmpEnglishName");
    String searchOrgName = (httpServletRequest.getParameter("searchOrgName") == null) ? 
      "" : 
      httpServletRequest.getParameter("searchOrgName");
    String searchEmpBusinessFax = (httpServletRequest.getParameter(
        "searchEmpBusinessFax") == null) ? "" : 
      httpServletRequest
      .getParameter("searchEmpBusinessFax");
    String searchEmpPosition = (httpServletRequest.getParameter(
        "searchEmpPosition") == null) ? "" : 
      httpServletRequest
      .getParameter("searchEmpPosition");
    String searchStartAge = (httpServletRequest.getParameter(
        "searchStartAge") == null) ? "" : 
      httpServletRequest
      .getParameter("searchStartAge");
    String searchEndAge = (httpServletRequest.getParameter("searchEndAge") == null) ? 
      "" : 
      httpServletRequest.getParameter("searchEndAge");
    String searchEmpDuty = httpServletRequest.getParameter("searchEmpDuty");
    String qrz_zgxl = httpServletRequest.getParameter("qrz_zgxl");
    String zzjy_zgxl = httpServletRequest.getParameter("zzjy_zgxl");
    String databaseType = 
      SystemCommon.getDatabaseType();
    String search = "&searchEmpName=" + searchEmpName + 
      "&searchEmpEnglishName=" + 
      searchEmpEnglishName + "&searchOrgName=" + 
      searchOrgName + "&searchOrgName=" + 
      searchOrgName + "&dignity=" + searchEmpBusinessFax + 
      "&searchEmpPosition=" + searchEmpPosition + 
      "&searchStartAge=" + searchStartAge + 
      "&searchEndAge=" + searchEndAge + 
      "&qrz_zgxl=" + qrz_zgxl + 
      "&zzjy_zgxl=" + zzjy_zgxl;
    if (action.equals("menu")) {
      tag = "menu";
      ManagerService mbd = new ManagerService();
      if (mbd.hasRight(curUserId, "07*02*01"))
        httpServletRequest.setAttribute("assert", "1"); 
      if (mbd.hasRight(curUserId, "07*01"))
        httpServletRequest.setAttribute("peopleinfo", "1"); 
    } else if (action.equals("workCount")) {
      EmployeeEJBBean emp = new EmployeeEJBBean();
      String para = "distinct bpo.id,bpo.title";
      String from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo";
      String where = "where bpo.id=mpo.bodyId";
      String empId = httpServletRequest.getParameter("empId");
      List listWaitFlow = getList(httpServletRequest, "po.id,po.processId,po.tableId,po.recordId,po.workTitle,po.saveTime,po.processName", "com.js.oa.jsflow.po.JSFPDraftPO po", "where po.userId=" + empId + " order by po.saveTime desc");
      httpServletRequest.setAttribute("listWaitFlow", Integer.valueOf(listWaitFlow.size()));
      String whereSqlWait = " select count(*) from JSF_WORK  where WORKSTATUS = 101 and WORKDONEWITHDATE is null and WF_CUREMPLOYEE_ID = " + empId + 
        " and WORKLISTCONTROL = 1 and WORKDELETE = 0 and WORKTABLE_ID!=1";
      try {
        int listWorkWait = emp.getWorkflow(whereSqlWait);
        httpServletRequest.setAttribute("listWorkWait", Integer.valueOf(listWorkWait));
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      String whereSqlCom = " select count(*) from JSF_WORK  where WORKSTATUS = 101 and WORKDONEWITHDATE is not null and WF_CUREMPLOYEE_ID = " + empId + 
        " and WORKLISTCONTROL = 1 and WORKDELETE = 0  and WORKTABLE_ID!=1";
      try {
        int listWorkCom = emp.getWorkflow(whereSqlCom);
        httpServletRequest.setAttribute("listWorkCom", Integer.valueOf(listWorkCom));
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      para = "bpo.id,bpo.title,bpo.postTime,bpo.level";
      from = "com.js.cooperate.po.BodyPO bpo";
      String whereSql = "where bpo.status=1 and bpo.posterId=" + empId + " order by bpo.postTime desc";
      List list_daifa = getListCoprate(httpServletRequest, para, from, whereSql);
      httpServletRequest.setAttribute("list_daifa", Integer.valueOf(list_daifa.size()));
      para = "distinct bpo.id,bpo.title";
      from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo";
      where = String.valueOf(where) + " and mpo.status=10";
      List list_daiban = getListCoprate(httpServletRequest, para, from, where);
      httpServletRequest.setAttribute("list_daiban", Integer.valueOf(list_daiban.size()));
      para = "distinct bpo.id,bpo.title";
      from = "com.js.cooperate.po.BodyPO bpo,com.js.cooperate.po.NodeMemberPO mpo";
      where = String.valueOf(where) + " and bpo.status=10 and mpo.isPoster=1 and bpo.posterId=" + empId;
      List list_yifa = getListCoprate(httpServletRequest, para, from, where);
      httpServletRequest.setAttribute("list_yifa", Integer.valueOf(list_yifa.size()));
      where = String.valueOf(where) + " and bpo.status=10 and mpo.status<>10 and mpo.status<>0 and mpo.isPoster=0";
      List list_yiban = getListCoprate(httpServletRequest, para, from, where);
      httpServletRequest.setAttribute("list_yiban", Integer.valueOf(list_yiban.size()));
      List listFawen = listFawen(httpServletRequest);
      httpServletRequest.setAttribute("listFawen", Integer.valueOf(listFawen.size()));
      List listYIfa_gw = listGw(httpServletRequest);
      httpServletRequest.setAttribute("listYIfa_gw", Integer.valueOf(listYIfa_gw.size()));
      String orgId = null;
      try {
        orgId = emp.getOrgId(empId);
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      List processList = null;
      ProcessBD procbd = new ProcessBD();
      Object tmp = procbd.getUserProcess(empId, orgId, "3");
      if (tmp != null)
        processList = (List)tmp; 
      httpServletRequest.setAttribute("processList", Integer.valueOf(processList.size()));
      List listGWCY = listGWCY(httpServletRequest);
      httpServletRequest.setAttribute("listGWCY", Integer.valueOf(listGWCY.size()));
      String[][] info = { { "100", "新闻" }, { "101", "公告" } };
      String corpId = session.getAttribute("corpId").toString();
      ChannelBD bd = new ChannelBD();
      info = bd.getChannelSimpleInfoByCorpId(corpId, 0);
      int news = 0;
      try {
        news = emp.getPublic(empId, info[0][0]);
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("news", Integer.valueOf(news));
      int newsCheck = 0;
      try {
        newsCheck = emp.getCheckPublic(empId, info[0][0].toString());
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("newsCheck", Integer.valueOf(newsCheck));
      int notices = 0;
      try {
        notices = emp.getPublic(empId, info[1][0].toString());
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("notices", Integer.valueOf(notices));
      int noticesCheck = 0;
      try {
        noticesCheck = emp.getCheckPublic(empId, info[1][0]);
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("noticesCheck", Integer.valueOf(noticesCheck));
      int knowledge = 0;
      try {
        knowledge = emp.getKnowlege(empId, info[1][0].toString());
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("knowledge", Integer.valueOf(knowledge));
      int knowledgeCheck = 0;
      try {
        knowledgeCheck = emp.getCheckKnowlege(empId, info[0][0]);
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("knowledgeCheck", Integer.valueOf(knowledgeCheck));
      tag = "workCount";
    } else if (action.equals("add")) {
      tag = "add";
      String managerScope = "*" + session.getAttribute("orgId") + "*";
      if ("1".equals(session.getAttribute("sysManager").toString())) {
        managerScope = "*0*";
      } else {
        Object[] obj = (new ManagerService()).getRightScope(
            session.getAttribute("userId").toString(), "07*01*03")
          .get(0);
        String type = obj[0].toString();
        if ("0".equals(type)) {
          managerScope = "*0*";
        } else if ("4".equals(type) && 
          obj[1] != null) {
          managerScope = obj[1].toString();
        } 
      } 
      DbOpt db = null;
      String[][] rszfw = (String[][])null;
      String[][] zw = (String[][])null;
      try {
        db = new DbOpt();
        String sql = "select id,name from temp_rsotherinfo where type='rszfw'";
        rszfw = db.executeQueryToStrArr2(sql);
        sql = "select id,name from temp_rsotherinfo where type='zw'";
        zw = db.executeQueryToStrArr2(sql);
        db.close();
      } catch (Exception e) {
        e.printStackTrace();
        if (db != null)
          try {
            db.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
      } 
      httpServletRequest.setAttribute("rszfw", rszfw);
      httpServletRequest.setAttribute("zw", zw);
      httpServletRequest.setAttribute("managerScope", managerScope);
      httpServletRequest.setAttribute("listDuty", (new NewDutyBD()).getList(domainId, session.getAttribute("corpId").toString()));
      httpServletRequest.setAttribute("listStation", (new NewDutyBD()).getStationList(domainId));
      httpServletRequest.setAttribute("listWorkAddress", (new WorkAddressBD()).list(new Long(domainId)));
      httpServletRequest.setAttribute("listPersonalKind", (new PersonalKindBD()).list());
      httpServletRequest.setAttribute("countries", employeeBD.listCountry());
    } else if (action.equals("exportgbrmspb")) {
      tag = "exportgbrmspb";
      String empId = httpServletRequest.getParameter("empId");
      List<Object[]> list = (new NewEmployeeBD()).selectSingle(Long.valueOf(empId));
      if (list != null && list.size() == 2) {
        Object[] object = list.get(0);
        EmployeeVO employeeVO = (EmployeeVO)object[0];
        EmployeeOtherInfoVO employeeOtherInfoVO = (EmployeeOtherInfoVO)list.get(1);
        httpServletRequest.setAttribute("employeeVO", employeeVO);
        httpServletRequest.setAttribute("employeeOtherInfoVO", employeeOtherInfoVO);
        String viewSQL = "po.id,po.hjsj,po.hjmc,po.hjjb,po.hjsx,po.cfsj,po.cfmc";
        String fromSQL = "com.js.system.vo.usermanager.JcxxVO po join po.employeeVO ppo";
        String whereSQL = "where ppo.empId=" + new Long(empId) + 
          " order by po.id desc ";
        int pageSize = 999999;
        int offset = 0;
        Page page = new Page(viewSQL, fromSQL, whereSQL);
        int currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List<Object[]> jcxxlist = page.getResultList();
        String jcxx = "";
        if (jcxxlist != null && jcxxlist.size() > 0)
          for (int i = 0; i < jcxxlist.size(); i++) {
            Object[] jcitem = jcxxlist.get(i);
            jcxx = String.valueOf(jcxx) + (String)jcitem[1] + "&nbsp;&nbsp;&nbsp;&nbsp;";
            jcxx = String.valueOf(jcxx) + (String)jcitem[2] + "&nbsp;&nbsp;&nbsp;&nbsp;";
            jcxx = String.valueOf(jcxx) + (String)jcitem[3] + "&nbsp;&nbsp;&nbsp;&nbsp;";
            jcxx = String.valueOf(jcxx) + (String)jcitem[4] + "&nbsp;&nbsp;&nbsp;&nbsp;";
            jcxx = String.valueOf(jcxx) + (String)jcitem[5] + "&nbsp;&nbsp;&nbsp;&nbsp;";
            jcxx = String.valueOf(jcxx) + (String)jcitem[6] + "<br>";
          }  
        httpServletRequest.setAttribute("jcxx", jcxx);
        viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz,po.sfzhm";
        fromSQL = "com.js.system.vo.usermanager.ChildrenVO po join po.employeeVO ppo";
        whereSQL = "where ppo.empId=" + new Long(empId) + 
          " order by po.id desc ";
        pageSize = 999999;
        offset = 0;
        page = new Page(viewSQL, fromSQL, whereSQL);
        page.setPageSize(pageSize);
        page.setcurrentPage(1);
        List znxxlist = page.getResultList();
        httpServletRequest.setAttribute("znxx", znxxlist);
        viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz,po.rsrz,po.ftjrz";
        fromSQL = "com.js.system.vo.usermanager.QtqsqkVO po join po.employeeVO ppo";
        whereSQL = "where ppo.empId=" + new Long(empId) + 
          " order by po.id desc ";
        page = new Page(viewSQL, fromSQL, whereSQL);
        currentPage = 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List qtqs = page.getResultList();
        httpServletRequest.setAttribute("qtqs", qtqs);
        viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz";
        fromSQL = "com.js.system.vo.usermanager.GnwgxVO po join po.employeeVO ppo";
        whereSQL = "where ppo.empId=" + new Long(empId) + 
          " order by po.id desc ";
        page = new Page(viewSQL, fromSQL, whereSQL);
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List gnwgx = page.getResultList();
        httpServletRequest.setAttribute("gnwgx", gnwgx);
      } 
    } else if (action.equals("view")) {
      tag = "view";
      (new WorkAttendanceBD()).testMethod();
      employeeActionForm.setSearchEmpEnglishName(searchEmpEnglishName);
      employeeActionForm.setSearchEmpName(searchEmpName);
      employeeActionForm.setSearchOrgName(searchOrgName);
      employeeActionForm.setQrz_zgxl(qrz_zgxl);
      employeeActionForm.setZzjy_zgxl(zzjy_zgxl);
      list(httpServletRequest);
      String username = (String)httpServletRequest.getSession().getAttribute("userAccount");
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
        String status = "false";
        try {
          tblUser = tblUserBD.findTblUser(username);
          status = tblUserStatusBD.findstatus(tblUser.getId());
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
        httpServletRequest.setAttribute("iactive", status);
        try {
          List userOnlinList = tblUserStatusBD.findUserOnline();
          httpServletRequest.setAttribute("userOnlinList", userOnlinList);
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
      httpServletRequest.setAttribute("listStation", (
          new NewDutyBD())
          .getStationList(domainId));
      httpServletRequest.setAttribute("listPersonalKind", (
          
          new PersonalKindBD()).list());
    } else if (action.equals("selectSingle") || action.equals("myCard") || 
      action.equals("personCard")) {
      String userIsDeleted = httpServletRequest.getParameter("userIsDeleted");
      httpServletRequest.setAttribute("userIsDeleted", userIsDeleted);
      if (action.equals("selectSingle")) {
        String key = httpServletRequest.getParameter("key");
        String toMD5 = (new MD5()).toMD5("key-md5" + 
            httpServletRequest.getParameter("empId"));
        if (!toMD5.equals(key))
          return actionMapping.findForward("error"); 
      } else if (httpServletRequest.getParameter("key") != null && 
        !"".equals(httpServletRequest.getParameter("key")) && 
        !"null".equals(httpServletRequest.getParameter("key")) && 
        action.equals("myCard")) {
        String key = httpServletRequest.getParameter("key");
        String toMD5 = (new MD5()).toMD5("key-md5" + httpServletRequest.getParameter("empId"));
        if (!toMD5.equals(key))
          return actionMapping.findForward("error"); 
      } 
      underlingEmp(httpServletRequest, false);
      httpServletRequest.setAttribute("listStation", (
          new NewDutyBD())
          .getStationList(domainId));
      Long empId = new Long((httpServletRequest.getParameter("empId") == null) ? 
          "0" : httpServletRequest.getParameter("empId"));
      tag = "modi";
      if (action.equals("myCard")) {
        empId = new Long(curUserId);
        tag = "myCard";
        if (session.getAttribute("userAccount").toString().toLowerCase()
          .equals("admin"))
          return new ActionForward(
              "/EmployeeAction.do?action=myUnderling"); 
      } 
      if (action.equals("personCard"))
        tag = "personCard"; 
      List<Object[]> list = (new NewEmployeeBD()).selectSingle(empId);
      EmployeeVO employeeVO = null;
      EmployeeOtherInfoVO employeeOtherInfoVO = null;
      String orgId = "";
      String orgName = "";
      if (list.size() > 0) {
        Object[] object = list.get(0);
        employeeVO = (EmployeeVO)object[0];
        employeeOtherInfoVO = (EmployeeOtherInfoVO)list.get(1);
        orgId = (object[1] == null) ? "" : object[1].toString();
        orgName = (object[2] == null) ? "" : object[2].toString();
      } 
      DbOpt db = new DbOpt();
      String sql = "select id,name from temp_rsotherinfo where type='rszfw'";
      String[][] rszfw = (String[][])null;
      try {
        rszfw = db.executeQueryToStrArr2(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      sql = "select id,name from temp_rsotherinfo where type='zw'";
      String[][] zw = (String[][])null;
      try {
        zw = db.executeQueryToStrArr2(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      try {
        db.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      httpServletRequest.setAttribute("rszfw", rszfw);
      httpServletRequest.setAttribute("zw", zw);
      httpServletRequest.setAttribute("empPositionId", 
          (employeeVO.getEmpPositionId() == null) ? "-1" : employeeVO.getEmpPositionId());
      String nowYear = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
      String nowMonth = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
      String nowDay = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getRxzsj() == null || "".equals(employeeOtherInfoVO.getRxzsj())) {
        httpServletRequest.setAttribute("rxzsj1", nowYear);
        httpServletRequest.setAttribute("rxzsj2", nowMonth);
        httpServletRequest.setAttribute("rxzsj3", nowDay);
      } else {
        String[] rxzsj = employeeOtherInfoVO.getRxzsj().split("/");
        httpServletRequest.setAttribute("rxzsj1", rxzsj[0]);
        httpServletRequest.setAttribute("rxzsj2", rxzsj[1]);
        httpServletRequest.setAttribute("rxzsj3", rxzsj[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getRxjsj() == null || "".equals(employeeOtherInfoVO.getRxjsj())) {
        httpServletRequest.setAttribute("rxjsj1", nowYear);
        httpServletRequest.setAttribute("rxjsj2", nowMonth);
        httpServletRequest.setAttribute("rxjsj3", nowDay);
      } else {
        String[] rxjsj = employeeOtherInfoVO.getRxjsj().split("/");
        httpServletRequest.setAttribute("rxjsj1", rxjsj[0]);
        httpServletRequest.setAttribute("rxjsj2", rxjsj[1]);
        httpServletRequest.setAttribute("rxjsj3", rxjsj[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getSyqksrq() == null || "".equals(employeeOtherInfoVO.getSyqksrq())) {
        httpServletRequest.setAttribute("syqkssj1", nowYear);
        httpServletRequest.setAttribute("syqkssj2", nowMonth);
        httpServletRequest.setAttribute("syqkssj3", nowDay);
      } else {
        String[] syqkssj = employeeOtherInfoVO.getSyqksrq().split("/");
        httpServletRequest.setAttribute("syqkssj1", syqkssj[0]);
        httpServletRequest.setAttribute("syqkssj2", syqkssj[1]);
        httpServletRequest.setAttribute("syqkssj3", syqkssj[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getSyqjsrq() == null || "".equals(employeeOtherInfoVO.getSyqjsrq())) {
        httpServletRequest.setAttribute("syqjssj1", nowYear);
        httpServletRequest.setAttribute("syqjssj2", nowMonth);
        httpServletRequest.setAttribute("syqjssj3", nowDay);
      } else {
        String[] syqjssj = employeeOtherInfoVO.getSyqjsrq().split("/");
        httpServletRequest.setAttribute("syqjssj1", syqjssj[0]);
        httpServletRequest.setAttribute("syqjssj2", syqjssj[1]);
        httpServletRequest.setAttribute("syqjssj3", syqjssj[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getPo_csny() == null || "".equals(employeeOtherInfoVO.getPo_csny())) {
        httpServletRequest.setAttribute("po_csny1", nowYear);
        httpServletRequest.setAttribute("po_csny2", nowMonth);
        httpServletRequest.setAttribute("po_csny3", nowDay);
      } else {
        String[] po_csny = employeeOtherInfoVO.getPo_csny().split("/");
        httpServletRequest.setAttribute("po_csny1", po_csny[0]);
        httpServletRequest.setAttribute("po_csny2", po_csny[1]);
        httpServletRequest.setAttribute("po_csny3", po_csny[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getPo_cjgzsj() == null || "".equals(employeeOtherInfoVO.getPo_cjgzsj())) {
        httpServletRequest.setAttribute("po_cjgzsj1", nowYear);
        httpServletRequest.setAttribute("po_cjgzsj2", nowMonth);
        httpServletRequest.setAttribute("po_cjgzsj3", nowDay);
      } else {
        String[] po_cjgzsj = employeeOtherInfoVO.getPo_cjgzsj().split("/");
        httpServletRequest.setAttribute("po_cjgzsj1", po_cjgzsj[0]);
        httpServletRequest.setAttribute("po_cjgzsj2", po_cjgzsj[1]);
        httpServletRequest.setAttribute("po_cjgzsj3", po_cjgzsj[2]);
      } 
      if (employeeOtherInfoVO == null || employeeOtherInfoVO.getDt_rdsj() == null || "".equals(employeeOtherInfoVO.getDt_rdsj())) {
        httpServletRequest.setAttribute("dt_rdsj1", nowYear);
        httpServletRequest.setAttribute("dt_rdsj2", nowMonth);
        httpServletRequest.setAttribute("dt_rdsj3", nowDay);
      } else {
        String[] dt_rdsj = employeeOtherInfoVO.getDt_rdsj().split("/");
        httpServletRequest.setAttribute("dt_rdsj1", dt_rdsj[0]);
        httpServletRequest.setAttribute("dt_rdsj2", dt_rdsj[1]);
        httpServletRequest.setAttribute("dt_rdsj3", dt_rdsj[2]);
      } 
      httpServletRequest.setAttribute("dutyName", 
          (employeeVO.getEmpDuty() == null) ? 
          "" : 
          employeeVO.getEmpDuty());
      try {
        BeanUtils.copyProperties(employeeActionForm, employeeVO);
        BeanUtils.copyProperties(employeeActionForm, employeeOtherInfoVO);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      } 
      employeeActionForm.setJobStatus(employeeVO.getJobStatus());
      httpServletRequest.setAttribute("jobStatus", employeeVO.getJobStatus());
      if (employeeVO.getUserOrderCode() == null)
        employeeActionForm.setUserOrderCode(""); 
      employeeActionForm.setEmpId(empId);
      employeeActionForm.setOrgId(orgId);
      employeeActionForm.setOrgName(orgName);
      httpServletRequest.setAttribute("empLivingPhoto", 
          employeeActionForm
          .getEmpLivingPhoto());
      String managerScope = "*" + session.getAttribute("orgId") + "*";
      httpServletRequest.setAttribute("managerScope", managerScope);
      if ("view".equals(httpServletRequest.getParameter("flag")))
        tag = "newView"; 
      httpServletRequest.setAttribute("empBirth", employeeVO.getEmpBirth());
      httpServletRequest.setAttribute("empHireDate", 
          employeeVO.getEmpHireDate());
      httpServletRequest.setAttribute("empFireDate", 
          employeeVO.getEmpFireDate());
      httpServletRequest.setAttribute("userSuperBegin", 
          employeeVO.getUserSuperBegin());
      httpServletRequest.setAttribute("userSuperEnd", 
          employeeVO.getUserSuperEnd());
      httpServletRequest.setAttribute("userIsSuper", 
          String.valueOf(employeeVO
            .getUserIsSuper()));
      httpServletRequest.setAttribute("listDuty", (
          new NewDutyBD()).getList(domainId));
      httpServletRequest.setAttribute("listWorkAddress", (
          
          new WorkAddressBD()).list(new Long(
              domainId)));
      httpServletRequest.setAttribute("graduateDate", 
          employeeVO.getGraduateDate());
      httpServletRequest.setAttribute("intoCompanyDate", 
          employeeVO.getIntoCompanyDate());
      httpServletRequest.setAttribute("listDuty", 
          employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
      httpServletRequest.setAttribute("countries", employeeBD.listCountry());
      httpServletRequest.setAttribute("userIsFormalUser", 
          employeeVO.getUserIsFormalUser());
      httpServletRequest.setAttribute("listPersonalKind", (
          
          new PersonalKindBD()).list());
      httpServletRequest.setAttribute("lizhiDate", 
          employeeVO.getLizhiDate());
      httpServletRequest.setAttribute("zhuanzhengDate", 
          employeeVO.getZhuanzhengDate());
    } else if (action.equals("saveclose") || action.equals("savecontinue")) {
      EmployeeVO employeeVO = new EmployeeVO();
      EmployeeOtherInfoVO employeeOtherInfoVO = new EmployeeOtherInfoVO();
      Date empBirth = new Date(httpServletRequest.getParameter(
            "empBirth").replaceAll("-", "/"));
      Date empFireDate = new Date(httpServletRequest
          .getParameter("empFireDate")
          .replaceAll("-", "/"));
      Date intoCompanyDate = new Date(httpServletRequest
          .getParameter("intoCompanyDate").replaceAll("-", "/"));
      String orgId = httpServletRequest.getParameter("orgId");
      try {
        BeanUtils.copyProperties(
            employeeVO, employeeActionForm);
        BeanUtils.copyProperties(
            employeeOtherInfoVO, employeeActionForm);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      } 
      employeeVO.setEmpLivingPhoto(httpServletRequest.getParameter(
            "empLivingPhoto"));
      employeeVO.setUserOrderCode("10000");
      employeeVO.setEmpBirth(empBirth);
      employeeVO.setEmpFireDate(empFireDate);
      employeeVO.setIntoCompanyDate(intoCompanyDate);
      employeeVO.setMedicare(employeeActionForm.getMedicare());
      employeeVO.setPostCompetence(httpServletRequest.getParameter(
            "postCompetenceSel"));
      employeeVO.setCurrentPostTitle(httpServletRequest.getParameter(
            "currentPostTitleSel"));
      if (employeeVO.getUserAccounts() != null && 
        !"".equals(employeeVO.getUserAccounts())) {
        employeeVO.setUserIsFormalUser(Integer.valueOf("0"));
        byte userIsActive = 1;
        employeeVO.setUserIsActive(userIsActive);
      } else {
        byte userIsActive = 0;
        employeeVO.setUserIsActive(userIsActive);
        employeeVO.setUserIsFormalUser(Integer.valueOf("2"));
      } 
      Date userSuperBegin = new Date(httpServletRequest.getParameter(
            "userSuperBegin"));
      Date userSuperEnd = new Date(httpServletRequest.getParameter(
            "userSuperEnd"));
      if (employeeVO.getUserIsSuper() == 1) {
        employeeVO.setUserSuperBegin(userSuperBegin);
        employeeVO.setUserSuperEnd(userSuperEnd);
      } 
      if ("离职".equals(employeeActionForm.getJobStatus())) {
        employeeVO.setZhuanzhengDate(new Date(httpServletRequest
              .getParameter("zhuanzhengDate")
              .replaceAll("-", "/")));
        employeeVO.setLizhiDate(new Date(httpServletRequest
              .getParameter("lizhiDate")
              .replaceAll("-", "/")));
        employeeVO.setEmpFireType(employeeActionForm.getEmpFireType());
        employeeVO.setFireReason(employeeActionForm.getFireReason());
        Byte userIsDeleted = new Byte("1");
        employeeVO.setUserIsDeleted(userIsDeleted.byteValue());
      } else if ("临时".equals(employeeActionForm.getJobStatus())) {
        employeeVO.setZhuanzhengDate(null);
        employeeVO.setLizhiDate(null);
        employeeVO.setEmpFireType(null);
        employeeVO.setFireReason(null);
      } else {
        employeeVO.setZhuanzhengDate(new Date(httpServletRequest
              .getParameter("zhuanzhengDate")
              .replaceAll("-", "/")));
        employeeVO.setLizhiDate(null);
        employeeVO.setEmpFireType(null);
        employeeVO.setFireReason(null);
      } 
      employeeVO.setDomainId(domainId);
      employeeVO.setSkin("blue");
      employeeVO.setEmpPositionId(Long.valueOf(httpServletRequest.getParameter("empPositionId")));
      String[] station = (new NewDutyBD()).getSingleStation(httpServletRequest.getParameter("empPositionId"));
      employeeVO.setEmpPosition(station[0]);
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse()))
        if (employeeVO.getUserAccounts() != null && !"".equals(employeeVO.getUserAccounts())) {
          tblUser.setUserName(employeeVO.getUserAccounts());
          tblUser.setPassWord(employeeActionForm.getUserPassword());
          tblUser.setType(4);
          tblUser.setIsPrimaryAdmin((byte)0);
          tblUser.setOrgId(1);
          tblUser.setAid(1);
          tblUser.setIsValid((byte)1);
          SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          tblUser.setStartValidDate(si.format(new Date()));
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(1, 5);
          tblUser.setEndValidDate(si.format(calendar.getTime()));
          tblUser.setNickName(employeeVO.getEmpName());
          tblUser.setSex(employeeVO.getEmpSex());
          tblUser.setMailaddr("");
          tblUser.setTelephone("");
          tblUser.setMphone("");
          tblUser.setProtocolRcv((byte)0);
          tblUser.setProtocolSend((byte)0);
          tblUser.setVerifyHid((byte)0);
          tblUser.setTruename(employeeVO.getEmpName());
          tblUser.setOccupy(0);
          tblUser.setInterest(Double.valueOf(0.0D));
          tblUser.setSafeinfo(0);
          tblUser.setShengxiao((byte)0);
          tblUser.setBloodtype((byte)0);
          tblUser.setStar((byte)0);
          tblUser.setImageindex((short)1);
          OrganizationBD organizationBD = new OrganizationBD();
          TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
          String sa = organizationBD.findOrgSerial(Integer.parseInt(employeeActionForm.getOrgId()));
          int did = tblDepartmentBD.findID(sa);
          tblUser.setDeptId(did);
          tblUser.setGrade(0);
          tblUser.setAccountId(0);
          tblUser.setContinueService((byte)0);
          tblUser.setRole(0);
          tblUser.setDicOrder(0);
          tblUser.setTrolServerId(Integer.valueOf(0));
          tblUser.setTrolState(0);
          tblUser.setTrolIsOnline((byte)0);
          tblUser.setUserid(31914);
          try {
            tblUserBD.addTblUser(tblUser);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        }  
      int result = (new NewEmployeeBD()).add(employeeVO, employeeOtherInfoVO, orgId);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
        action = "savefailure";
        tag = "savefailure";
        httpServletRequest.setAttribute("listDuty", 
            employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
        httpServletRequest.setAttribute("countries", 
            employeeBD.listCountry());
        httpServletRequest.setAttribute("listStation", (
            new NewDutyBD())
            .getStationList(domainId));
        httpServletRequest.setAttribute("listWorkAddress", (
            
            new WorkAddressBD()).list(
              new Long(domainId)));
        httpServletRequest.setAttribute("listPersonalKind", (
            
            new PersonalKindBD()).list());
      } 
      if (action.equals("saveclose")) {
        tag = "saveclose";
        httpServletRequest.setAttribute("parentUrl", 
            "/jsoa/EmployeeAction.do?action=view" + 
            search + 
            "&pager.offset=" + 
            httpServletRequest.getParameter(
              "pager.offset"));
      } else if (action.equals("savecontinue")) {
        tag = "savecontinue";
        employeeActionForm.reset();
        httpServletRequest.setAttribute("listDuty", 
            employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
        httpServletRequest.setAttribute("countries", 
            employeeBD.listCountry());
        httpServletRequest.setAttribute("listStation", (
            new NewDutyBD())
            .getStationList(domainId));
        httpServletRequest.setAttribute("listWorkAddress", (
            
            new WorkAddressBD()).list(
              new Long(domainId)));
        httpServletRequest.setAttribute("listPersonalKind", (
            
            new PersonalKindBD()).list());
      } 
    } else if (action.equals("update") || action.equals("mycardupdate")) {
      tag = "update";
      if (action.equals("mycardupdate")) {
        tag = "myCard";
        httpServletRequest.setAttribute("listWorkAddress", (
            
            new WorkAddressBD()).list(new Long(
                domainId)));
        httpServletRequest.setAttribute("empLivingPhoto", 
            employeeActionForm
            .getEmpLivingPhoto());
      } 
      httpServletRequest.setAttribute("parentUrl", 
          "/jsoa/EmployeeAction.do?action=search&pager.offset=" + 
          httpServletRequest.getParameter(
            "pager.offset"));
      EmployeeVO employeeVO = new EmployeeVO();
      employeeVO.setSection(employeeActionForm.getSection());
      employeeVO.setEmpBusinessPhone(employeeActionForm
          .getEmpBusinessPhone());
      Date empFireDate = new Date(httpServletRequest
          .getParameter("empFireDate")
          .replaceAll("-", "/"));
      Date empBirth = new Date(httpServletRequest.getParameter(
            "empBirth").replaceAll("-", "/"));
      Date intoCompanyDate = new Date(httpServletRequest
          .getParameter("intoCompanyDate").replaceAll("-", "/"));
      String orgId = httpServletRequest.getParameter("orgId");
      Long empId = new Long(httpServletRequest.getParameter("empId"));
      EmployeeOtherInfoVO employeeOtherInfoVO = new EmployeeOtherInfoVO();
      try {
        BeanUtils.copyProperties(
            employeeVO, employeeActionForm);
        BeanUtils.copyProperties(employeeOtherInfoVO, employeeActionForm);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      } 
      String userisdelete = httpServletRequest.getParameter("userIsDeleted");
      if (userisdelete != null && userisdelete.equals("1"))
        employeeVO.setUserIsDeleted((byte)1); 
      employeeVO.setEmpFireDate(empFireDate);
      employeeVO.setEmpBirth(empBirth);
      employeeVO.setEmpLivingPhoto(httpServletRequest.getParameter(
            "empLivingPhoto"));
      employeeVO.setEmpDuty(employeeActionForm.getEmpDuty());
      employeeVO.setIntoCompanyDate(intoCompanyDate);
      if (employeeVO.getUserAccounts() != null && 
        !"".equals(employeeVO.getUserAccounts())) {
        employeeVO.setUserIsFormalUser(Integer.valueOf("0"));
        byte userIsActive = 1;
        employeeVO.setUserIsActive(userIsActive);
      } else {
        byte userIsActive = 0;
        employeeVO.setUserIsActive(userIsActive);
        employeeVO.setUserIsFormalUser(Integer.valueOf("2"));
      } 
      Date userSuperBegin = new Date(httpServletRequest.getParameter(
            "userSuperBegin"));
      Date userSuperEnd = new Date(httpServletRequest.getParameter(
            "userSuperEnd"));
      if (httpServletRequest.getParameter("userIsSuper") != null && 
        "1".equals(httpServletRequest.getParameter("userIsSuper"))) {
        employeeVO.setUserIsSuper(Byte.parseByte("1"));
      } else {
        employeeVO.setUserIsSuper(Byte.parseByte("0"));
      } 
      employeeVO.setUserSuperBegin(userSuperBegin);
      employeeVO.setUserSuperEnd(userSuperEnd);
      employeeVO.setDomainId(domainId);
      employeeVO.setEmpPositionId(Long.valueOf(httpServletRequest.getParameter("empPositionId")));
      String[] station = (new NewDutyBD()).getSingleStation(httpServletRequest.getParameter("empPositionId"));
      employeeVO.setEmpPosition(station[0]);
      if ("离职".equals(employeeActionForm.getJobStatus())) {
        employeeVO.setZhuanzhengDate(new Date(httpServletRequest
              .getParameter("zhuanzhengDate")
              .replaceAll("-", "/")));
        httpServletRequest.setAttribute("zhuanzhengDate", new Date(httpServletRequest.getParameter("zhuanzhengDate").replaceAll("-", "/")));
        employeeVO.setLizhiDate(new Date(httpServletRequest
              .getParameter("lizhiDate")
              .replaceAll("-", "/")));
        employeeVO.setEmpFireType(employeeActionForm.getEmpFireType());
        employeeVO.setFireReason(employeeActionForm.getFireReason());
        Byte userIsDeleted = new Byte("1");
        employeeVO.setUserIsDeleted(userIsDeleted.byteValue());
      } else if ("临时".equals(employeeActionForm.getJobStatus())) {
        employeeVO.setZhuanzhengDate(null);
        employeeVO.setLizhiDate(null);
        employeeVO.setEmpFireType(null);
        employeeVO.setFireReason(null);
      } else {
        employeeVO.setZhuanzhengDate(new Date(httpServletRequest
              .getParameter("zhuanzhengDate")
              .replaceAll("-", "/")));
        httpServletRequest.setAttribute("zhuanzhengDate", new Date(httpServletRequest.getParameter("zhuanzhengDate").replaceAll("-", "/")));
        employeeVO.setLizhiDate(null);
        employeeVO.setEmpFireType(null);
        employeeVO.setFireReason(null);
      } 
      int result = 0;
      boolean flag = true;
      try {
        EmployeeVO employee = userBD.getEmployeeVO(empId);
        if (!employeeActionForm.getUserAccounts().equals((employee.getUserAccounts() == null) ? "" : employee.getUserAccounts()) || 
          !employeeActionForm.getUserPassword().equals("") || 
          !orgId.equals(StaticParam.getOrgIdByEmpId((String)empId)) || 
          !employeeActionForm.getSidelineOrg().equals((employee.getSidelineOrg() == null) ? "" : employee.getSidelineOrg()) || 
          employeeActionForm.getEmpSex() != employee.getEmpSex() || 
          employeeVO.getEmpPositionId().longValue() != ((employee.getEmpPositionId() == null) ? 0L : employee.getEmpPositionId().longValue()) || 
          !employeeVO.getEmpDuty().equals((employee.getEmpDuty() == null) ? "" : employee.getEmpDuty()) || 
          !employeeVO.getEmpLeaderId().equals((employee.getEmpLeaderId() == null) ? "" : employee.getEmpLeaderId()) || 
          !employeeVO.getEmpName().equals((employee.getEmpName() == null) ? "" : employee.getEmpName()))
          flag = false; 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (SystemCommon.getAudit() == 0 || employeeActionForm.getUserAccounts() == null || 
        employeeActionForm.getUserAccounts().equals("") || flag) {
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse()))
          try {
            String tblUserName = userBD.getUserName(Long.valueOf(employeeVO.getEmpId()));
            tblUser = tblUserBD.findTblUser(tblUserName);
            if (employeeActionForm.getUserPassword() != null && !"".equals(employeeActionForm.getUserPassword()))
              tblUser.setPassWord(employeeActionForm.getUserPassword()); 
            tblUser.setUserName(employeeActionForm.getUserAccounts());
            tblUser.setTruename(employeeActionForm.getEmpName());
            tblUser.setNickName(employeeActionForm.getEmpName());
            tblUser.setSex(employeeActionForm.getEmpSex());
            OrganizationBD organizationBD = new OrganizationBD();
            TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
            String sa = organizationBD.findOrgSerial(Integer.parseInt(employeeActionForm.getOrgId()));
            int did = tblDepartmentBD.findID(sa);
            tblUser.setDeptId(did);
            tblUserBD.updateTblUser(tblUser);
          } catch (Exception e) {
            e.printStackTrace();
          }  
        result = (new NewEmployeeBD()).update(employeeVO, employeeOtherInfoVO, orgId, empId);
      } else {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "6" };
        result = (new NewEmployeeBD()).update(employeeVO, employeeOtherInfoVO, orgId, empId, log);
        httpServletRequest.setAttribute("shenhe", "shenhe");
      } 
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
        tag = "updatefailure";
        String nowYear = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
        String nowMonth = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
        String nowDay = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getRxzsj() == null || "".equals(employeeOtherInfoVO.getRxzsj())) {
          httpServletRequest.setAttribute("rxzsj1", nowYear);
          httpServletRequest.setAttribute("rxzsj2", nowMonth);
          httpServletRequest.setAttribute("rxzsj3", nowDay);
        } else {
          String[] rxzsj = employeeOtherInfoVO.getRxzsj().split("/");
          httpServletRequest.setAttribute("rxzsj1", rxzsj[0]);
          httpServletRequest.setAttribute("rxzsj2", rxzsj[1]);
          httpServletRequest.setAttribute("rxzsj3", rxzsj[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getRxjsj() == null || "".equals(employeeOtherInfoVO.getRxjsj())) {
          httpServletRequest.setAttribute("rxjsj1", nowYear);
          httpServletRequest.setAttribute("rxjsj2", nowMonth);
          httpServletRequest.setAttribute("rxjsj3", nowDay);
        } else {
          String[] rxjsj = employeeOtherInfoVO.getRxjsj().split("/");
          httpServletRequest.setAttribute("rxjsj1", rxjsj[0]);
          httpServletRequest.setAttribute("rxjsj2", rxjsj[1]);
          httpServletRequest.setAttribute("rxjsj3", rxjsj[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getSyqksrq() == null || "".equals(employeeOtherInfoVO.getSyqksrq())) {
          httpServletRequest.setAttribute("syqkssj1", nowYear);
          httpServletRequest.setAttribute("syqkssj2", nowMonth);
          httpServletRequest.setAttribute("syqkssj3", nowDay);
        } else {
          String[] syqkssj = employeeOtherInfoVO.getSyqksrq().split("/");
          httpServletRequest.setAttribute("syqkssj1", syqkssj[0]);
          httpServletRequest.setAttribute("syqkssj2", syqkssj[1]);
          httpServletRequest.setAttribute("syqkssj3", syqkssj[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getSyqjsrq() == null || "".equals(employeeOtherInfoVO.getSyqjsrq())) {
          httpServletRequest.setAttribute("syqjssj1", nowYear);
          httpServletRequest.setAttribute("syqjssj2", nowMonth);
          httpServletRequest.setAttribute("syqjssj3", nowDay);
        } else {
          String[] syqjssj = employeeOtherInfoVO.getSyqjsrq().split("/");
          httpServletRequest.setAttribute("syqjssj1", syqjssj[0]);
          httpServletRequest.setAttribute("syqjssj2", syqjssj[1]);
          httpServletRequest.setAttribute("syqjssj3", syqjssj[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getPo_csny() == null || "".equals(employeeOtherInfoVO.getPo_csny())) {
          httpServletRequest.setAttribute("po_csny1", nowYear);
          httpServletRequest.setAttribute("po_csny2", nowMonth);
          httpServletRequest.setAttribute("po_csny3", nowDay);
        } else {
          String[] po_csny = employeeOtherInfoVO.getPo_csny().split("/");
          httpServletRequest.setAttribute("po_csny1", po_csny[0]);
          httpServletRequest.setAttribute("po_csny2", po_csny[1]);
          httpServletRequest.setAttribute("po_csny3", po_csny[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getPo_cjgzsj() == null || "".equals(employeeOtherInfoVO.getPo_cjgzsj())) {
          httpServletRequest.setAttribute("po_cjgzsj1", nowYear);
          httpServletRequest.setAttribute("po_cjgzsj2", nowMonth);
          httpServletRequest.setAttribute("po_cjgzsj3", nowDay);
        } else {
          String[] po_cjgzsj = employeeOtherInfoVO.getPo_cjgzsj().split("/");
          httpServletRequest.setAttribute("po_cjgzsj1", po_cjgzsj[0]);
          httpServletRequest.setAttribute("po_cjgzsj2", po_cjgzsj[1]);
          httpServletRequest.setAttribute("po_cjgzsj3", po_cjgzsj[2]);
        } 
        if (employeeOtherInfoVO == null || employeeOtherInfoVO.getDt_rdsj() == null || "".equals(employeeOtherInfoVO.getDt_rdsj())) {
          httpServletRequest.setAttribute("dt_rdsj1", nowYear);
          httpServletRequest.setAttribute("dt_rdsj2", nowMonth);
          httpServletRequest.setAttribute("dt_rdsj3", nowDay);
        } else {
          String[] dt_rdsj = employeeOtherInfoVO.getDt_rdsj().split("/");
          httpServletRequest.setAttribute("dt_rdsj1", dt_rdsj[0]);
          httpServletRequest.setAttribute("dt_rdsj2", dt_rdsj[1]);
          httpServletRequest.setAttribute("dt_rdsj3", dt_rdsj[2]);
        } 
        httpServletRequest.setAttribute("dutyName", 
            (employeeVO.getEmpDuty() == null) ? 
            "" : 
            employeeVO.getEmpDuty());
        try {
          BeanUtils.copyProperties(employeeActionForm, employeeVO);
          BeanUtils.copyProperties(employeeActionForm, employeeOtherInfoVO);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        } 
        employeeActionForm.setJobStatus(employeeVO.getJobStatus());
        httpServletRequest.setAttribute("jobStatus", employeeVO.getJobStatus());
        if (employeeVO.getUserOrderCode() == null)
          employeeActionForm.setUserOrderCode(""); 
        employeeActionForm.setEmpId(empId);
        employeeActionForm.setOrgId(orgId);
        employeeActionForm.setOrgName(employeeActionForm.getOrgName());
        httpServletRequest.setAttribute("empLivingPhoto", 
            employeeVO.getEmpLivingPhoto());
        httpServletRequest.setAttribute("empPositionId", employeeVO.getEmpPositionId());
        String managerScope = "*" + session.getAttribute("orgId") + "*";
        httpServletRequest.setAttribute("managerScope", managerScope);
        if ("view".equals(httpServletRequest.getParameter("flag")))
          tag = "newView"; 
        httpServletRequest.setAttribute("empBirth", employeeVO.getEmpBirth());
        httpServletRequest.setAttribute("empHireDate", 
            employeeVO.getEmpHireDate());
        httpServletRequest.setAttribute("empFireDate", 
            employeeVO.getEmpFireDate());
        httpServletRequest.setAttribute("userSuperBegin", 
            employeeVO.getUserSuperBegin());
        httpServletRequest.setAttribute("userSuperEnd", 
            employeeVO.getUserSuperEnd());
        httpServletRequest.setAttribute("userIsSuper", 
            String.valueOf(employeeVO
              .getUserIsSuper()));
        httpServletRequest.setAttribute("listDuty", (
            new NewDutyBD()).getList(domainId));
        httpServletRequest.setAttribute("listWorkAddress", (
            
            new WorkAddressBD()).list(new Long(
                domainId)));
        httpServletRequest.setAttribute("graduateDate", 
            employeeVO.getGraduateDate());
        httpServletRequest.setAttribute("intoCompanyDate", 
            employeeVO.getIntoCompanyDate());
        httpServletRequest.setAttribute("listDuty", 
            employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
        httpServletRequest.setAttribute("countries", employeeBD.listCountry());
        httpServletRequest.setAttribute("userIsFormalUser", 
            employeeVO.getUserIsFormalUser());
        httpServletRequest.setAttribute("listPersonalKind", (
            
            new PersonalKindBD()).list());
        httpServletRequest.setAttribute("lizhiDate", 
            employeeVO.getLizhiDate());
        httpServletRequest.setAttribute("zhuanzhengDate", 
            employeeVO.getZhuanzhengDate());
      } else {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
      } 
      httpServletRequest.setAttribute("empBirth", 
          employeeVO.getEmpBirth());
      httpServletRequest.setAttribute("empHireDate", 
          employeeVO.getEmpHireDate());
      httpServletRequest.setAttribute("empFireDate", 
          employeeVO.getEmpFireDate());
      httpServletRequest.setAttribute("userSuperBegin", 
          employeeVO.getUserSuperBegin());
      httpServletRequest.setAttribute("userSuperEnd", 
          employeeVO.getUserSuperEnd());
      httpServletRequest.setAttribute("userIsSuper", 
          String.valueOf(employeeVO
            .getUserIsSuper()));
      httpServletRequest.setAttribute("intoCompanyDate", 
          employeeVO.getIntoCompanyDate());
      httpServletRequest.setAttribute("listStation", (
          new NewDutyBD())
          .getStationList(domainId));
      httpServletRequest.setAttribute("listDuty", 
          employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
      httpServletRequest.setAttribute("listWorkAddress", (
          
          new WorkAddressBD()).list(
            new Long(domainId)));
      httpServletRequest.setAttribute("listPersonalKind", (
          
          new PersonalKindBD()).list());
    } else if (action.equals("del")) {
      String[] ids = { "" };
      if (httpServletRequest.getParameterValues("batchDel") != null) {
        ids = httpServletRequest.getParameterValues("batchDel");
      } else {
        ids[0] = httpServletRequest.getParameter("id");
      } 
      String acc = "";
      String not = "";
      try {
        for (int t = 0; t < ids.length; t++) {
          EmployeeVO employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(ids[t])));
          if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
            employeeVO.getUserAccounts().equals("")) {
            not = String.valueOf(not) + ids[t] + ",";
          } else {
            acc = String.valueOf(acc) + ids[t] + ",";
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (not.length() > 0) {
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          String tblUserName = "";
          for (int i = 0; i < (not.split(",")).length; i++) {
            try {
              tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(ids[i])));
              tblUser = tblUserBD.findTblUser(tblUserName);
              TblJilu tblJilu = new TblJilu();
              tblJilu.setUserName(tblUser.getUserName());
              tblJilu.setPassWord(tblUser.getPassWord());
              userBD.addTblJilu(tblJilu);
              tblUserBD.delTblUser(tblUser);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } 
        employeeBD.del(not.split(","));
      } 
      if (acc.length() > 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "6" };
        employeeBD.del(acc.split(","), log);
        httpServletRequest.setAttribute("shenhe", "shenhe");
      } 
      tag = "view";
      action = "search";
    } else if (action.equals("rehis")) {
      String id = httpServletRequest.getParameter("id");
      EmployeeVO employeeVO = new EmployeeVO();
      try {
        employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(id)));
        List<Object[]> list = (new NewEmployeeBD()).selectSingle(Long.valueOf(employeeVO.getEmpId()));
        Object object = "";
        if (list.size() > 0) {
          Object[] arrayOfObject = list.get(0);
          object = arrayOfObject[1];
        } 
        if ((SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
          employeeVO.getUserAccounts().equals("")) && 
          "iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          tblUser.setUserName(employeeVO.getUserAccounts());
          TblJilu tblJilu = new TblJilu();
          tblJilu = userBD.findTblJiluByUsername(employeeVO.getUserAccounts());
          tblUser.setPassWord(tblJilu.getPassWord());
          userBD.delTblJilu(tblJilu);
          tblUser.setType(4);
          tblUser.setIsPrimaryAdmin((byte)0);
          tblUser.setOrgId(1);
          tblUser.setAid(1);
          tblUser.setIsValid((byte)1);
          SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          tblUser.setStartValidDate(si.format(new Date()));
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(1, 5);
          tblUser.setEndValidDate(si.format(calendar.getTime()));
          tblUser.setNickName(employeeVO.getEmpName());
          tblUser.setSex(employeeVO.getEmpSex());
          tblUser.setMailaddr("");
          tblUser.setTelephone("");
          tblUser.setMphone("");
          tblUser.setProtocolRcv((byte)0);
          tblUser.setProtocolSend((byte)0);
          tblUser.setVerifyHid((byte)0);
          tblUser.setTruename(employeeVO.getEmpName());
          tblUser.setOccupy(0);
          tblUser.setInterest(Double.valueOf(0.0D));
          tblUser.setSafeinfo(0);
          tblUser.setShengxiao((byte)0);
          tblUser.setBloodtype((byte)0);
          tblUser.setStar((byte)0);
          tblUser.setImageindex((short)1);
          OrganizationBD organizationBD = new OrganizationBD();
          TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
          String sa = organizationBD.findOrgSerial(Integer.parseInt((String)object));
          int did = tblDepartmentBD.findID(sa);
          tblUser.setDeptId(did);
          tblUser.setGrade(0);
          tblUser.setAccountId(0);
          tblUser.setContinueService((byte)0);
          tblUser.setRole(0);
          tblUser.setDicOrder(0);
          tblUser.setTrolServerId(Integer.valueOf(0));
          tblUser.setTrolState(0);
          tblUser.setTrolIsOnline((byte)0);
          tblUser.setUserid(31914);
          tblUserBD.addTblUser(tblUser);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      boolean flag = true;
      if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
        employeeVO.getUserAccounts().equals("")) {
        flag = employeeBD.rehis(id);
      } else {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "6" };
        flag = employeeBD.rehis(id, log);
      } 
      if (!flag)
        httpServletRequest.setAttribute("success", "1"); 
      String hisSearchEmpName = "";
      String hisSearchEmpEnglishName = "";
      String hisSearchOrgName = "";
      hisSearchEmpName = (httpServletRequest.getParameter(
          "hisSearchEmpName") == null) ? "" : 
        httpServletRequest
        .getParameter("hisSearchEmpName");
      hisSearchEmpEnglishName = (httpServletRequest.getParameter(
          "hisSearchEmpEnglishName") == null) ? "" : 
        httpServletRequest.getParameter(
          "hisSearchEmpEnglishName");
      hisSearchOrgName = (httpServletRequest.getParameter(
          "hisSearchOrgName") == null) ? "" : 
        httpServletRequest
        .getParameter("hisSearchOrgName");
      String where = getSearchWhere(hisSearchEmpName, 
          hisSearchEmpEnglishName, 
          hisSearchOrgName, 
          searchEmpBusinessFax, 
          searchEmpPosition, searchStartAge, 
          searchEndAge, searchEmpDuty);
      tag = "viewhistory";
      historylist(httpServletRequest, where);
    } else if (action.equals("forbid")) {
      if (httpServletRequest.getParameter("id") != null) {
        String id = httpServletRequest.getParameter("id");
        try {
          EmployeeVO employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(id)));
          if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
            employeeVO.getUserAccounts().equals("")) {
            String tblUserName = "";
            TblUserApp tblUserApp = new TblUserApp();
            List<TblUserApp> userAppList = new ArrayList();
            try {
              tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(id)));
              tblUser = tblUserBD.findTblUser(tblUserName);
              userAppList = tblUserBD.findTblUserApp(tblUser.getId());
              for (int k = 0; k < userAppList.size(); k++) {
                tblUserApp = userAppList.get(k);
                tblUserBD.delTblUserApp(tblUserApp);
              } 
            } catch (Exception e) {
              e.printStackTrace();
            } 
            employeeBD.forbid(id);
          } else {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "6" };
            employeeBD.forbid(id, log);
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      tag = "view";
      action = "search";
    } else if (action.equals("regain")) {
      if (httpServletRequest.getParameter("id") != null) {
        String id = httpServletRequest.getParameter("id");
        try {
          EmployeeVO employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(id)));
          if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
            employeeVO.getUserAccounts().equals("")) {
            String tblUserName = "";
            try {
              tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(id)));
              tblUser = tblUserBD.findTblUser(tblUserName);
              tblUserBD.addTblUserApp(tblUser);
            } catch (Exception e) {
              e.printStackTrace();
            } 
            employeeBD.regain(id);
          } else {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "6" };
            employeeBD.regain(id, log);
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      tag = "view";
      action = "search";
    } else if (action.equals("batchResume")) {
      String[] ids = httpServletRequest.getParameterValues("batchDel");
      String account = "";
      String notacoun = "";
      EmployeeVO employeeVO = new EmployeeVO();
      if (httpServletRequest.getParameter("flag") != null && httpServletRequest.getParameter("flag").equals("enable")) {
        String tblUserName = "";
        for (int i = 0; i < ids.length; i++) {
          try {
            employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(ids[i])));
            if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
              employeeVO.getUserAccounts().equals("")) {
              notacoun = String.valueOf(notacoun) + ids[i] + ",";
              tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(ids[i])));
              tblUser = tblUserBD.findTblUser(tblUserName);
              tblUserBD.addTblUserApp(tblUser);
            } else {
              account = String.valueOf(account) + ids[i] + ",";
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } else {
        for (int i = 0; i < ids.length; i++) {
          try {
            employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(ids[i])));
            if (SystemCommon.getAudit() == 0 || employeeVO.getUserAccounts() == null || 
              employeeVO.getUserAccounts().equals("")) {
              notacoun = String.valueOf(notacoun) + ids[i] + ",";
              List<Object[]> list = (new NewEmployeeBD()).selectSingle(Long.valueOf(employeeVO.getEmpId()));
              Object object = "";
              if (list.size() > 0) {
                Object[] arrayOfObject = list.get(0);
                object = arrayOfObject[1];
              } 
              tblUser.setUserName(employeeVO.getUserAccounts());
              TblJilu tblJilu = new TblJilu();
              tblJilu = userBD.findTblJiluByUsername(employeeVO.getUserAccounts());
              tblUser.setPassWord(tblJilu.getPassWord());
              userBD.delTblJilu(tblJilu);
              tblUser.setType(4);
              tblUser.setIsPrimaryAdmin((byte)0);
              tblUser.setOrgId(1);
              tblUser.setAid(1);
              tblUser.setIsValid((byte)1);
              SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              tblUser.setStartValidDate(si.format(new Date()));
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(new Date());
              calendar.add(1, 5);
              tblUser.setEndValidDate(si.format(calendar.getTime()));
              tblUser.setNickName(employeeVO.getEmpName());
              tblUser.setSex(employeeVO.getEmpSex());
              tblUser.setMailaddr("");
              tblUser.setTelephone("");
              tblUser.setMphone("");
              tblUser.setProtocolRcv((byte)0);
              tblUser.setProtocolSend((byte)0);
              tblUser.setVerifyHid((byte)0);
              tblUser.setTruename(employeeVO.getEmpName());
              tblUser.setOccupy(0);
              tblUser.setInterest(Double.valueOf(0.0D));
              tblUser.setSafeinfo(0);
              tblUser.setShengxiao((byte)0);
              tblUser.setBloodtype((byte)0);
              tblUser.setStar((byte)0);
              tblUser.setImageindex((short)1);
              OrganizationBD organizationBD = new OrganizationBD();
              TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
              String sa = organizationBD.findOrgSerial(Integer.parseInt((String)object));
              int did = tblDepartmentBD.findID(sa);
              tblUser.setDeptId(did);
              tblUser.setGrade(0);
              tblUser.setAccountId(0);
              tblUser.setContinueService((byte)0);
              tblUser.setRole(0);
              tblUser.setDicOrder(0);
              tblUser.setTrolServerId(Integer.valueOf(0));
              tblUser.setTrolState(0);
              tblUser.setTrolIsOnline((byte)0);
              tblUser.setUserid(31914);
              tblUserBD.addTblUser(tblUser);
            } else {
              account = String.valueOf(account) + ids[i] + ",";
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } 
      if (account.length() > 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "6" };
        String[] empIds = account.split(",");
        employeeBD.batchResume(empIds, log);
      } 
      String reEmp = "";
      if (notacoun.length() > 0)
        reEmp = employeeBD.batchResume(notacoun.split(",")); 
      if (reEmp.length() > 0)
        httpServletRequest.setAttribute("success", "1"); 
      if (httpServletRequest.getParameter("flag") != null && 
        httpServletRequest.getParameter("flag").equals("history")) {
        tag = "viewhistory";
        historylist(httpServletRequest, "");
      } else {
        tag = "view";
        action = "search";
      } 
      httpServletRequest.setAttribute("listStation", (
          new NewDutyBD())
          .getStationList(domainId));
    } else if (action.equals("city")) {
      tag = "ifmcity";
      String country = httpServletRequest.getParameter("country");
      httpServletRequest.setAttribute("cities", employeeBD.city(country));
    } else if (action.equals("county")) {
      tag = "ifmcounty";
      String country = httpServletRequest.getParameter("country");
      String city = httpServletRequest.getParameter("city");
      httpServletRequest.setAttribute("counties", 
          employeeBD.county(country, city));
    } else if (action.equals("postTitle")) {
      tag = "ifmPostCompetence";
      String postTitleSeries = httpServletRequest.getParameter(
          "postTitleSeries");
      httpServletRequest.setAttribute("postCompetences", (
          
          new NewEmployeeBD()).postTitle(
            postTitleSeries));
    } else if (action.equals("history")) {
      tag = "viewhistory";
      historylist(httpServletRequest, "");
    } else if (action.equals("hSearch")) {
      tag = "viewhistory";
      String hisSearchEmpName = "";
      String hisSearchEmpEnglishName = "";
      String hisSearchOrgName = "";
      String where = "";
      if (employeeActionForm.getSearchEmpName() != null) {
        hisSearchEmpName = employeeActionForm.getHisSearchEmpName();
        hisSearchEmpEnglishName = employeeActionForm
          .getHisSearchEmpEnglishName();
        hisSearchOrgName = employeeActionForm.getHisSearchEmpName();
      } else {
        hisSearchEmpName = httpServletRequest.getParameter(
            "hisSearchEmpName");
        hisSearchEmpEnglishName = httpServletRequest.getParameter(
            "hisSearchEmpEnglishName");
        hisSearchOrgName = httpServletRequest.getParameter(
            "hisSearchOrgName");
      } 
      where = getSearchWhere(hisSearchEmpName, hisSearchEmpEnglishName, 
          hisSearchOrgName, searchEmpBusinessFax, 
          searchEmpPosition, searchStartAge, 
          searchEndAge, searchEmpDuty);
      historylist(httpServletRequest, where);
      httpServletRequest.setAttribute("pageParameters", "action,hisSearchEmpName,hisSearchEmpEnglishName,hisSearchOrgName,searchEmpBusinessFax,searchEmpPosition,searchStartAge,searchEndAge");
    } 
    if (action.equals("search")) {
      httpServletRequest.setAttribute("listStation", (new NewDutyBD()).getStationList(domainId));
      httpServletRequest.setAttribute("listPersonalKind", (new PersonalKindBD()).list());
      if (employeeActionForm.getSearchEmpName() != null) {
        searchEmpName = employeeActionForm.getSearchEmpName();
        searchEmpEnglishName = employeeActionForm.getSearchEmpEnglishName();
        searchOrgName = employeeActionForm.getSearchOrgName();
        searchEmpBusinessFax = employeeActionForm.getSearchEmpBusinessFax();
        searchEmpPosition = employeeActionForm.getSearchEmpPosition();
        searchStartAge = employeeActionForm.getSearchStartAge();
        searchEndAge = employeeActionForm.getSearchEndAge();
        qrz_zgxl = employeeActionForm.getQrz_zgxl();
        zzjy_zgxl = employeeActionForm.getZzjy_zgxl();
      } else {
        searchEmpName = httpServletRequest.getParameter("searchEmpName");
        searchEmpEnglishName = httpServletRequest.getParameter(
            "searchEmpEnglishName");
        searchOrgName = httpServletRequest.getParameter("searchOrgName");
        searchEmpBusinessFax = httpServletRequest.getParameter(
            "searchEmpBusinessFax");
        searchEmpPosition = httpServletRequest.getParameter(
            "searchEmpPosition");
        searchStartAge = httpServletRequest.getParameter(
            "searchStartAge");
        searchEndAge = httpServletRequest.getParameter("searchEndAge");
        qrz_zgxl = httpServletRequest.getParameter("qrz_zgxl");
        zzjy_zgxl = httpServletRequest.getParameter("zzjy_zgxl");
      } 
      if (httpServletRequest.getParameter("searchOrgName") != null)
        searchOrgName = httpServletRequest.getParameter("searchOrgName"); 
      String searchEmpStudyExperience = (httpServletRequest.getParameter(
          "searchEmpStudyExperience") == null) ? "" : 
        httpServletRequest.getParameter(
          "searchEmpStudyExperience");
      String searchPostLevel = (httpServletRequest.getParameter(
          "searchPostLevel") == null) ? "" : 
        httpServletRequest
        .getParameter("searchPostLevel");
      String searchEmpNation = (httpServletRequest.getParameter(
          "searchEmpNation") == null) ? "" : 
        httpServletRequest
        .getParameter("searchEmpNation");
      String searchEmpCountry = (httpServletRequest.getParameter(
          "searchEmpCountry") == null) ? "" : 
        httpServletRequest
        .getParameter("searchEmpCountry");
      String searchIntoCompanyDate = (httpServletRequest.getParameter(
          "searchIntoCompanyDate") == null) ? "" : 
        httpServletRequest
        .getParameter("searchIntoCompanyDate");
      String searchWorkPackStartDate = (httpServletRequest.getParameter(
          "searchWorkPackStartDate") == null) ? "" : 
        httpServletRequest.getParameter(
          "searchWorkPackStartDate");
      String searchWorkPackEndDate = (httpServletRequest.getParameter(
          "searchWorkPackEndDate") == null) ? "" : 
        httpServletRequest
        .getParameter("searchWorkPackEndDate");
      String speciality1 = employeeActionForm.getSpeciality1();
      String month1 = (httpServletRequest.getParameter(
          "month1") == null) ? "" : 
        httpServletRequest
        .getParameter("month1");
      String searchIntoCompanyDate2 = (httpServletRequest.getParameter(
          "searchIntoCompanyDate2") == null) ? "" : 
        httpServletRequest
        .getParameter("searchIntoCompanyDate2");
      String month2 = (httpServletRequest.getParameter(
          "month2") == null) ? "" : 
        httpServletRequest
        .getParameter("month2");
      String personalKind = (httpServletRequest.getParameter(
          "personalKind") == null) ? "" : 
        httpServletRequest
        .getParameter("personalKind");
      String where = getSearchWhere(searchEmpName, searchEmpEnglishName, 
          searchOrgName, 
          searchEmpBusinessFax, 
          searchEmpPosition, searchStartAge, 
          searchEndAge, searchEmpDuty);
      if (qrz_zgxl != null && !"".equals(qrz_zgxl) && !"null".equalsIgnoreCase(qrz_zgxl))
        where = String.valueOf(where) + " and po.empId in (select a.empId from EmployeeOtherInfoVO a where qrz_zgxl like '%" + qrz_zgxl + "%')"; 
      if (zzjy_zgxl != null && !"".equals(zzjy_zgxl) && !"null".equalsIgnoreCase(zzjy_zgxl))
        where = String.valueOf(where) + " and po.empId in (select b.empId from EmployeeOtherInfoVO b where zzjy_zgxl like '%" + zzjy_zgxl + "%')"; 
      where = where.trim();
      if (!"0".equals(searchEmpStudyExperience) && 
        !"".equals(searchEmpStudyExperience))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.empStudyExperience='" + 
            searchEmpStudyExperience + "' ";
        } else {
          where = String.valueOf(where) + " and po.empStudyExperience='" + 
            searchEmpStudyExperience + "' ";
        }  
      if (!"0".equals(searchPostLevel) && !"".equals(searchPostLevel))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.postLevel='" + searchPostLevel + "'";
        } else {
          where = String.valueOf(where) + " and po.postLevel='" + searchPostLevel + "'";
        }  
      if (!"0".equals(searchEmpNation) && !"".equals(searchEmpNation))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.empNation='" + searchEmpNation + "'";
        } else {
          where = String.valueOf(where) + " and po.empNation='" + searchEmpNation + "'";
        }  
      if (!"0".equals(personalKind) && !"".equals(personalKind))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.personalKind='" + personalKind + "'";
        } else {
          where = String.valueOf(where) + " and po.personalKind='" + personalKind + "'";
        }  
      if (!"".equals(searchEmpCountry) && !"".equals(searchEmpCountry))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.speciality like '%" + searchEmpCountry + "%'";
        } else {
          where = String.valueOf(where) + " and po.speciality like '%" + searchEmpCountry + 
            "%'";
        }  
      if (speciality1 != null && !"".equals(speciality1) && !speciality1.equals("null"))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " (po.speciality1 like '%" + speciality1 + "%' or po.speciality2 like '%" + speciality1 + "%')";
        } else {
          where = String.valueOf(where) + " and (po.speciality1 like '%" + speciality1 + 
            "%' or po.speciality2 like '%" + speciality1 + "%')";
        }  
      if (!"0".equals(searchIntoCompanyDate) && 
        !"".equals(searchIntoCompanyDate))
        if (where.endsWith("and")) {
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + " po.intoCompanyDate between '" + 
              searchIntoCompanyDate + "-" + month1 + "-1' and '" + 
              searchIntoCompanyDate2 + "-" + month2 + "-" + getDays(searchIntoCompanyDate2, month2) + "'";
          } else {
            where = String.valueOf(where) + 
              " po.intoCompanyDate between JSDB.FN_STRTODATE('" + 
              searchIntoCompanyDate + 
              "-" + month1 + "-1','S') and JSDB.FN_STRTODATE('" + 
              searchIntoCompanyDate2 + "-" + month2 + "-" + getDays(searchIntoCompanyDate2, month2) + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and po.intoCompanyDate between '" + 
            searchIntoCompanyDate + "-" + month1 + "-1' and '" + 
            searchIntoCompanyDate2 + "-" + month2 + "-" + getDays(searchIntoCompanyDate2, month2) + "'";
        } else {
          where = String.valueOf(where) + 
            " and po.intoCompanyDate between JSDB.FN_STRTODATE('" + 
            searchIntoCompanyDate + 
            "-" + month1 + "-1','S') and JSDB.FN_STRTODATE('" + 
            searchIntoCompanyDate2 + "-" + month2 + "-" + getDays(searchIntoCompanyDate2, month2) + "','S')";
        }  
      if (httpServletRequest.getParameter("htrq") != null && 
        !"".equals(httpServletRequest.getParameter("htrq"))) {
        if (where.endsWith("and")) {
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + " po.workPackStartDate>='" + 
              searchWorkPackStartDate + "' and " + 
              "po.workPackEndDate<='" + searchWorkPackEndDate + 
              "'";
          } else {
            where = String.valueOf(where) + 
              " po.workPackStartDate>=JSDB.FN_STRTODATE('" + 
              searchWorkPackStartDate + "','S') and " + 
              "po.workPackEndDate<=JSDB.FN_STRTODATE('" + 
              searchWorkPackEndDate + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and po.workPackStartDate>='" + 
            searchWorkPackStartDate + "' and " + 
            "po.workPackEndDate<='" + searchWorkPackEndDate + 
            "'";
        } else {
          where = String.valueOf(where) + 
            " and po.workPackStartDate>=JSDB.FN_STRTODATE('" + 
            searchWorkPackStartDate + "','S') and " + 
            "po.workPackEndDate<=JSDB.FN_STRTODATE('" + 
            searchWorkPackEndDate + "','S')";
        } 
        httpServletRequest.setAttribute("htrq", "1");
      } 
      String searchBirthday = httpServletRequest.getParameter(
          "searchBirthday");
      String searchEndBirthday = httpServletRequest.getParameter(
          "searchEndBirthday");
      if (httpServletRequest.getParameter("chkBirth") != null && 
        !"".equals(httpServletRequest.getParameter("chkBirth")) && 
        !"".equals(searchBirthday)) {
        if (where.endsWith("and")) {
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + " po.empBirth>='" + searchBirthday + 
              "' and  po.empBirth<='" + searchEndBirthday + 
              "'";
          } else {
            where = String.valueOf(where) + " po.empBirth>=JSDB.FN_STRTODATE('" + 
              searchBirthday + 
              "','S') and  po.empBirth<=JSDB.FN_STRTODATE('" + 
              searchEndBirthday + "','S') ";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and po.empBirth>='" + searchBirthday + 
            "' and  po.empBirth<='" + searchEndBirthday + 
            "'";
        } else {
          where = String.valueOf(where) + 
            " and po.empBirth>=JSDB.FN_STRTODATE('" + 
            searchBirthday + 
            "','S') and  po.empBirth<=JSDB.FN_STRTODATE('" + 
            searchEndBirthday + "','S') ";
        } 
        httpServletRequest.setAttribute("chkBirth", "1");
      } 
      if (httpServletRequest.getParameter("empSex") != null && 
        !"".equals(httpServletRequest.getParameter("empSex")))
        if (where.endsWith("and")) {
          where = String.valueOf(where) + " po.empSex='" + 
            httpServletRequest.getParameter("empSex") + "'";
        } else {
          where = String.valueOf(where) + " and po.empSex='" + 
            httpServletRequest.getParameter("empSex") + "'";
        }  
      where = String.valueOf(where) + " ";
      if ("export".equals(httpServletRequest.getParameter("flag"))) {
        tag = "export";
        String exportType = httpServletRequest.getParameter("exportType");
        if ("1".equals(exportType)) {
          tag = "export_contract";
          export_contract(httpServletRequest, where);
        } else if ("2".equals(exportType)) {
          tag = "export_edusty";
          export_edusty(httpServletRequest, where);
        } else if ("3".equals(exportType)) {
          tag = "export_work";
          export_work(httpServletRequest, where);
        } else if ("4".equals(exportType)) {
          tag = "export_trainhistory";
          export_trainhistory(httpServletRequest, where);
        } else if ("5".equals(exportType)) {
          tag = "export_competence";
          export_competence(httpServletRequest, where);
        } else {
          export(httpServletRequest, where);
        } 
      } else {
        tag = "view";
        list(httpServletRequest, where);
        httpServletRequest.setAttribute("pageParameters", "action,searchEmpName,qrz_zgxl,zzjy_zgxl,searchEmpEnglishName,searchOrgName,searchEmpBusinessFax,searchEmpPosition,searchStartAge,searchEndAge,searchEmpStudyExperience,searchPostLevel,searchEmpNation,searchEmpCountry,searchIntoCompanyDate,searchWorkPackStartDate,searchWorkPackEndDate,htrq,empSex,searchEndBirthday,personalKind,speciality1,month1,searchIntoCompanyDate2,month2");
      } 
    } else if (action.equals("myUnderling")) {
      tag = "myUnderling";
      String viewSQL = "po.empId,po.empName,orgpo.orgNameString,po.empDuty,po.workAddress,po.empBusinessPhone,po.empLeaderId,po.empLeaderName,po.userAccounts,po.empMobilePhone,po.userOnline,po.empEmail";
      String fromSQL = 
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo";
      String whereSQL = "where po.empLeaderId like '%$" + curUserId + 
        "$%' and po.userIsDeleted=0 and po.userIsActive=1 order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName";
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        String username = (String)httpServletRequest.getSession().getAttribute("userAccount");
        TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
        String status = "false";
        try {
          tblUser = tblUserBD.findTblUser(username);
          status = tblUserStatusBD.findstatus(tblUser.getId());
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
        httpServletRequest.setAttribute("iactive", status);
        try {
          List userOnlinList = tblUserStatusBD.findUserOnline();
          httpServletRequest.setAttribute("userOnlinList", userOnlinList);
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    } 
    if (action.equals("contractFellin") || "contractFellin_tip".equals(action)) {
      tag = action;
      getContractFellinList(httpServletRequest, "contractFellin_tip".equals(action) ? 5 : 15);
    } 
    if (action.equals("birthDayCome") || "birthDayCome_tip".equals(action)) {
      tag = action;
      getBirthDayList(httpServletRequest, "birthDayCome_tip".equals(action) ? 5 : 15);
    } 
    if ("orgView".equals(action)) {
      tag = action;
      String orgId = httpServletRequest.getParameter("orgId");
      Page page = new Page(
          "po", 
          "com.js.system.vo.organizationmanager.OrganizationVO po", 
          "where po.domainId = " + domainId + " and po.orgId = " + orgId);
      page.setPageSize(1);
      page.setcurrentPage(1);
      List<OrganizationVO> list = page.getResultList();
      if (list != null && list.size() > 0) {
        OrganizationVO vo = list.get(0);
        Page page2 = new Page(
            "po", 
            "com.js.system.vo.organizationmanager.OrganizationVO po", 
            "where po.domainId = " + domainId + " and po.orgId = " + 
            vo.getOrgParentOrgId());
        page2.setPageSize(1);
        page2.setcurrentPage(1);
        List<OrganizationVO> list2 = page2.getResultList();
        if (list2 != null && list2.size() > 0) {
          OrganizationVO vo2 = list2.get(0);
          httpServletRequest.setAttribute("orgParentName", vo2.getOrgName());
        } 
      } 
      httpServletRequest.setAttribute("orgList", list);
    } 
    if (action.equals("underlingEmp")) {
      tag = "underlingEmp";
      underlingEmp(httpServletRequest, true);
    } 
    if ("InhabitancyCome".equals(action) || "InhabitancyCome_tip".equals(action)) {
      tag = action;
      InhabitancyComeList(httpServletRequest, "InhabitancyCome_tip".equals(action) ? 5 : 15);
    } 
    if ("maturityAlertSettingsSave".equals(action)) {
      String[] code = httpServletRequest.getParameterValues("code");
      if (code != null && code.length > 0) {
        String[][] ret = new String[code.length][2];
        for (int i = 0; i < code.length; i++) {
          ret[i][0] = code[i];
          ret[i][1] = httpServletRequest.getParameter("val_" + code[i]);
        } 
        NewEmployeeBD nbd = new NewEmployeeBD();
        if (nbd.saveMaturityAlertSettings("maturity_alert_settings", 
            ret, domainId).booleanValue())
          httpServletRequest.setAttribute("settingSuccess", "true"); 
      } 
      action = "maturityAlertSettings";
    } 
    if ("maturityAlertSettings".equals(action)) {
      tag = "maturityAlertSettings";
      NewEmployeeBD nbd = new NewEmployeeBD();
      List list = nbd.getMaturityAlertSettings("maturity_alert_settings", domainId);
      httpServletRequest.setAttribute("list", list);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String where) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    if (mbd.hasRight(curUserId, "07*01*03"))
      httpServletRequest.setAttribute("pepoleadd", "1"); 
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*01", "orgpo.id", "po.empId");
    String tmp = "";
    tmp = listRetStr(httpServletRequest);
    httpServletRequest.setAttribute("vindicate", tmp);
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    Page page = new Page(
        "po.empId,po.empName,po.empEnglishName,po.empSex,po.empBirth,po.empDuty,po.empNativePlace,po.userAccounts,orgpo.orgNameString,po.userIsActive,po.empLeaderName,orgpo.id,po.empMobilePhone,po.userAccounts,po.userOnline,po.empEmail", 
        
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("employeeList", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchEmpName,qrz_zgxl,zzjy_zgxl,searchEmpEnglishName,searchOrgName,searchEmpBusinessFax,searchEmpPosition,searchStartAge,searchEndAge,searchEmpStudyExperience,searchPostLevel,searchEmpNation,searchEmpCountry,searchIntoCompanyDate,searchWorkPackStartDate,searchWorkPackEndDate,htrq");
  }
  
  private String listRetStr(HttpServletRequest httpServletRequest) {
    String where = "";
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 99999;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    if (mbd.hasRight(curUserId, "07*01*03"))
      httpServletRequest.setAttribute("pepoleadd", "1"); 
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", "po.empId");
    String tmp = mbd.getRightFinalWhere(curUserId, curOrgId, "07*01*03", 
        "orgpo.id", "po.empId");
    httpServletRequest.setAttribute("vindicate", tmp);
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    Page page = new Page(
        "po.empId", 
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<String> list = page.getResultList();
    String retStr = ",";
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        retStr = String.valueOf(retStr) + list.get(i) + ",";  
    return retStr;
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    list(httpServletRequest, "");
  }
  
  private void historylist(HttpServletRequest httpServletRequest, String where) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        orgIdString, "人事管理-人事信息", 
        "维护", "orgpo.id", "po.empId");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = "where po.userIsDeleted=1 and " + where + 
        " and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } else {
      where = "where po.userIsDeleted=1  and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    Page page = new Page(
        "po.empId,po.empName,po.empEnglishName,po.empSex,po.empBirth,po.empNation,po.empNativePlace,po.userAccounts,orgpo.orgNameString,po.userIsActive,po.empStudyExperience", 
        "com.js.system.vo.usermanager.EmployeeVO po   join  po.organizations  orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("employeeList", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,flag");
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
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
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("myUnderlingList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private String getSearchWhere(String hisSearchEmpName, String hisSearchEmpEnglishName, String hisSearchOrgName, String searchEmpBusinessFax, String searchEmpPosition, String searchStartAge, String searchEndAge, String searchEmpDuty) {
    String where = "";
    if (hisSearchEmpName != null && hisSearchEmpName.length() > 0)
      where = " (po.empName like '%" + hisSearchEmpName + 
        "%' "; 
    if (hisSearchOrgName != null && hisSearchOrgName.length() > 0)
      where = String.valueOf(where) + (where.equals("") ? (
        " (orgpo.orgNameString like '%" + hisSearchOrgName + 
        "%'") : (
        " and orgpo.orgNameString like '%" + hisSearchOrgName + 
        "%'")); 
    if (hisSearchEmpEnglishName != null && 
      hisSearchEmpEnglishName.length() > 0)
      where = String.valueOf(where) + (where.equals("") ? (
        " (po.empEnglishName like '%" + hisSearchEmpEnglishName + 
        "%'") : (
        " and po.empEnglishName like '%" + hisSearchEmpEnglishName + 
        "%'")); 
    if (searchEmpBusinessFax != null && searchEmpBusinessFax.length() > 0)
      where = String.valueOf(where) + (where.equals("") ? (
        " (po.dignity='" + searchEmpBusinessFax + "'") : (
        " and po.dignity='" + searchEmpBusinessFax + "'")); 
    if (searchEmpPosition != null && searchEmpPosition.length() > 0)
      where = String.valueOf(where) + (where.equals("") ? (
        " (po.empPosition='" + searchEmpPosition + "'") : (
        " and po.empPosition='" + searchEmpPosition + "'")); 
    if (searchEmpDuty != null && searchEmpDuty.length() > 0)
      where = String.valueOf(where) + (where.equals("") ? (
        " (po.empDuty='" + searchEmpDuty + "'") : (
        " and po.empDuty='" + searchEmpDuty + "'")); 
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (searchStartAge != null && searchStartAge.length() > 0 && 
      searchEndAge != null && searchEndAge.length() > 0) {
      Date now = new Date();
      if (!searchStartAge.equals("") && !searchEndAge.equals(""))
        if ((searchStartAge.equals("") && !searchEndAge.equals("")) || (
          !searchStartAge.equals("") && searchEndAge.equals(""))) {
          int year = searchStartAge.equals("") ? 
            Integer.parseInt(searchEndAge) : 
            Integer.parseInt(searchStartAge);
          if (databaseType.indexOf("mysql") >= 0) {
            where = String.valueOf(where) + (where.equals("") ? (
              " (po.empBirth between '" + (
              now.getYear() + 1900 - year) + "-1-1' and '" + (
              now.getYear() + 1900 - year) + "-12-31'") : (
              " and po.empBirth between '" + (
              now.getYear() + 1900 - year) + "-1-1' and '" + (
              now.getYear() + 1900 - year) + "-12-31'"));
          } else {
            where = String.valueOf(where) + (where.equals("") ? (
              " (po.empBirth between JSDB.FN_STRTODATE('" + (
              now.getYear() + 1900 - year) + 
              "-1-1', 'S') and JSDB.FN_STRTODATE('" + (
              now.getYear() + 1900 - year) + "-12-31', 'S')") : (
              " and po.empBirth between JSDB.FN_STRTODATE('" + (
              now.getYear() + 1900 - year) + 
              "-1-1', 'S') and JSDB.FN_STRTODATE('" + (
              now.getYear() + 1900 - year) + "-12-31', 'S')"));
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + (where.equals("") ? (
            " (po.empBirth between '" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchEndAge)) + 
            "-1-1' and '" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchStartAge)) + "-12-31'") : (
            " and po.empBirth between '" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchEndAge)) + 
            "-1-1' and '" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchStartAge)) + "-12-31'"));
        } else {
          where = String.valueOf(where) + (where.equals("") ? (
            " (po.empBirth between JSDB.FN_STRTODATE('" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchEndAge)) + 
            "-1-1', 'S') and JSDB.FN_STRTODATE('" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchStartAge)) + 
            "-12-31', 'S')") : (
            " and po.empBirth between JSDB.FN_STRTODATE('" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchEndAge)) + 
            "-1-1', 'S') and JSDB.FN_STRTODATE('" + (
            now.getYear() + 1900 - 
            Integer.parseInt(searchStartAge)) + 
            "-12-31', 'S')"));
        }  
    } 
    if (!where.equals(""))
      where = String.valueOf(where) + ")"; 
    return where;
  }
  
  private void export(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.org_id", 
        "po.emp_id");
    String idValue = (httpServletRequest.getParameter(
        "idValue") == null) ? "" : 
      httpServletRequest
      .getParameter("idValue");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!"".equals(idValue)) {
      idValue = idValue.substring(0, idValue.length() - 1).replace("on,", "");
      where = String.valueOf(where) + " and  po.emp_id in(" + idValue + ")";
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export(where));
  }
  
  private void getContractFellinList(HttpServletRequest httpServletRequest, int pageSize) {
    String where = "";
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*01", "orgpo.id", "po.empId");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    String databaseType = 
      SystemCommon.getDatabaseType();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String searchWorkPackStartDate = format.format(new Date());
    String c_where = "where 1=1 and aaa.employeeVO.userIsDeleted=0 and aaa.employeeVO.userIsActive=1 ";
    c_where = String.valueOf(c_where) + " group by aaa.employeeVO.empId";
    Page cpage = new Page("distinct aaa.employeeVO.empId, max(aaa.endDate)", "com.js.system.vo.usermanager.ContractVO aaa", c_where);
    cpage.setPageSize(999999);
    cpage.setcurrentPage(1);
    List<Object[]> clist = cpage.getResultList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String nowDate = sdf.format(new Date());
    NewEmployeeBD nbd = new NewEmployeeBD();
    String days = nbd.getMaturityAlertSettingsValue("maturity_alert_settings", "10001", domainId);
    if (days == null || days.equals(""))
      days = "30"; 
    nowDate = getNextDay(nowDate, days);
    String empIds = "-1";
    if (clist != null && clist.size() > 0)
      for (int i = 0; i < clist.size(); i++) {
        Object[] obj = clist.get(i);
        Date endDate = (Date)obj[1];
        String e = sdf.format(endDate);
        if (nowDate.compareTo(e) >= 0)
          empIds = String.valueOf(empIds) + "," + obj[0].toString(); 
      }  
    httpServletRequest.setAttribute("clist", clist);
    where = String.valueOf(where) + " and (po.empId in (" + empIds + ")) ";
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 " + 
          where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.workPackEndDate";
      } else {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 and " + 
          where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.workPackEndDate";
      } 
    } else {
      where = 
        "where po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + 
        domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.workPackEndDate";
    } 
    Page page = new Page(
        "po.empId,po.empName,po.empEnglishName,po.empSex,po.empBirth,po.empDuty,po.empNativePlace,po.userAccounts,orgpo.orgNameString,po.userIsActive,po.empLeaderName,orgpo.id,po.workPackEndDate", 
        
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("contractFellin", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void getBirthDayList(HttpServletRequest httpServletRequest, int pageSize) {
    String where = "";
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*01", "orgpo.id", "po.empId");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    String databaseType = 
      SystemCommon.getDatabaseType();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String searchWorkPackStartDate = format.format(new Date());
    NewEmployeeBD nbd = new NewEmployeeBD();
    String days = nbd.getMaturityAlertSettingsValue("maturity_alert_settings", "10002", domainId);
    if (days == null || days.equals(""))
      days = "7"; 
    String searchWorkPackEndDate = getNextDay(searchWorkPackStartDate, days);
    searchWorkPackStartDate = searchWorkPackStartDate.substring(5, 10);
    searchWorkPackEndDate = searchWorkPackEndDate.substring(5, 10);
    if (databaseType.indexOf("oracle") >= 0) {
      where = String.valueOf(where) + " and to_char(po.empBirth,'mm-dd')>='" + 
        searchWorkPackStartDate + "' and " + 
        "to_char(po.empBirth,'mm-dd')<='" + searchWorkPackEndDate + 
        "'";
    } else if (databaseType.indexOf("mysql") >= 0) {
      where = String.valueOf(where) + " and substr(concat(po.empBirth),6,5)>='" + 
        searchWorkPackStartDate + "' and " + 
        "substr(concat(po.empBirth),6,5)<='" + searchWorkPackEndDate + 
        "'";
    } else {
      where = String.valueOf(where) + 
        " and substring(convert(varchar,po.empBirth,110),1,5)>='" + 
        searchWorkPackStartDate + "' and " + 
        "substring(convert(varchar,po.empBirth,110),1,5)<='" + 
        searchWorkPackEndDate + "'";
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 " + 
          where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.empBirth";
      } else {
        where = "where po.userIsDeleted=0 and po.userIsActive=1 and " + 
          where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.empBirth";
      } 
    } else {
      where = 
        "where po.userIsDeleted=0 and po.userIsActive=1 and po.domainId=" + 
        domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName, po.empBirth";
    } 
    Page page = new Page(
        "po.empId,po.empName,po.empEnglishName,po.empSex,po.empBirth,po.empDuty,po.empNativePlace,po.userAccounts,orgpo.orgNameString,po.userIsActive,po.empLeaderName,orgpo.id,po.workPackEndDate", 
        
        "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("contractFellin", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void export_contract(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", 
        "po.createdEmp");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export_contract(where));
  }
  
  private void export_edusty(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", 
        "po.createdEmp");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export_edusty(where));
  }
  
  private void export_work(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", 
        "po.createdEmp");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export_work(where));
  }
  
  private void export_trainhistory(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", 
        "po.createdEmp");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export_trainhistory(where));
  }
  
  private void export_competence(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, 
        "07*01*03", "orgpo.id", 
        "po.createdEmp");
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsDeleted=0 " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } else {
        where = "where po.userIsDeleted=0 and " + where + 
          " and po.domainId=" + domainId + 
          " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
      } 
    } else {
      where = "where po.userIsDeleted=0 and po.domainId=" + domainId + 
        " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
    } 
    httpServletRequest.setAttribute("empList", (
        new EmployeeBD()).export_competence(where));
  }
  
  private void underlingEmp(HttpServletRequest httpServletRequest, boolean flag) {
    String empId = httpServletRequest.getParameter("empId");
    if ("myCard".equals(httpServletRequest.getParameter("action")))
      empId = httpServletRequest.getSession(true).getAttribute("userId").toString(); 
    String viewSQL = "po.empId,po.empName,orgpo.orgNameString,po.empDuty,po.workAddress,po.empBusinessPhone,po.empLeaderId,po.empLeaderName";
    String fromSQL = 
      "com.js.system.vo.usermanager.EmployeeVO po join po.organizations orgpo";
    String whereSQL = "where po.empLeaderId like '%$" + empId + 
      "$%' and po.userIsDeleted=0 and po.userIsActive=1 order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName ";
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
    String recordCount = String.valueOf(page.getRecordCount());
    if (flag) {
      String pageCount = String.valueOf(page.getPageCount());
      httpServletRequest.setAttribute("myUnderlingList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,empId");
    } 
    if (list != null && list.size() > 0)
      httpServletRequest.setAttribute("underlingEmpCount", recordCount); 
  }
  
  private void InhabitancyComeList(HttpServletRequest request, int pageSize) {
    HttpSession session = request.getSession(true);
    Long userId = new Long(session.getAttribute("userId").toString());
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String domainId = session.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    String scopeSQL = mbd.getRightFinalWhere(String.valueOf(userId), 
        String.valueOf(orgId), 
        "07*55*03", 
        "po.empOrg", 
        "po.emp.empId");
    String viewSQL = 
      "po.id,po.beginDate,po.continueDate,po.yearLimit,po.memos,ppo.empName";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO po join po.emp ppo";
    String whereSQL = " where 1=1 ";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String nowDate = format.format(new Date());
    NewEmployeeBD nbd = new NewEmployeeBD();
    String days = nbd.getMaturityAlertSettingsValue(
        "maturity_alert_settings", "10003", domainId);
    if (days == null || days.equals(""))
      days = "7"; 
    String endDate = getNextDay(nowDate, days);
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") > -1) {
      whereSQL = String.valueOf(whereSQL) + " and to_char(po.continueDate, 'yyyy-MM-dd')<= '" + endDate + "'";
    } else if (databaseType.indexOf("mysql") > -1) {
      whereSQL = String.valueOf(whereSQL) + " and substr(concat(po.continueDate),1,10)<= '" + endDate + "'";
    } else {
      whereSQL = String.valueOf(whereSQL) + " and convert(char(10),po.continueDate,20) <= '" + endDate + "'";
    } 
    whereSQL = String.valueOf(whereSQL) + " and (" + scopeSQL + ")";
    whereSQL = String.valueOf(whereSQL) + " order by po.beginDate desc ";
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
  
  private static String getNextDay(String nowdate, String delay) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String mdate = "";
      Date d = strToDate(nowdate);
      long myTime = d.getTime() / 1000L + (
        Integer.parseInt(delay) * 24 * 60 * 60);
      d.setTime(myTime * 1000L);
      mdate = format.format(d);
      return mdate;
    } catch (Exception e) {
      return "";
    } 
  }
  
  private boolean isLeapYear(int year) {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
      return true; 
    return false;
  }
  
  private int getDays(String year, String month) {
    Calendar c = Calendar.getInstance();
    c.set(1, Integer.parseInt(year));
    c.set(2, Integer.parseInt(month) - 1);
    return c.getActualMaximum(5);
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
  
  public List getList(HttpServletRequest request, String para, String from, String where) {
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
    return list;
  }
  
  public List getListCoprate(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    HttpSession httpSession = request.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
  
  private List listFawen(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String empId = httpServletRequest.getParameter("empId");
    int pageSize = 1500000;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page("sendfile.id, sendfile.documentSendFileByteNumber, sendfile.documentSendFileTitle, sendfile.documentSendFileWriteOrg, sendfile.processId, sendfile.processName, sendfile.processType, sendfile.tableId, sendfile.remindField,sendfile.createdTime ", 
        "com.js.doc.doc.po.GovDocumentSendFilePO sendfile", 
        "where sendfile.createdEmp=" + empId + " and sendfile.isDraft=1 and sendfile.domainId=" + domainId + " order by sendfile.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
  
  private List listGw(HttpServletRequest request) {
    EmployeeEJBBean emp = new EmployeeEJBBean();
    String empId = request.getParameter("empId");
    String orgId = null;
    try {
      orgId = emp.getOrgId(empId);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    ManagerService managerBD = new ManagerService();
    String wherePara = " po.createdEmp=" + empId + 
      " or (" + 
      managerBD.getRightFinalWhere(empId, 
        orgId, 
        "03*01*02", "po.createdOrg", 
        "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + 
      managerBD.getRightFinalWhere(empId, 
        orgId, 
        "03*01*01", "po.createdOrg", 
        "po.createdEmp") + ")";
    PackageBD pbd = new PackageBD();
    Object[] ooo = pbd.getModuleProc("2").get(0);
    Object object = ooo[3];
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryItem = request.getParameter("queryItem");
    String queryBeginDate = request.getParameter("queryBeginDate");
    String queryEndDate = request.getParameter("queryEndDate");
    String joinwhere = "1=1";
    String fromwhere = 
      "com.js.doc.doc.po.GovDocumentSendFilePO po ";
    if ("1".equals(queryItem)) {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.createdTime between '")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("' and '").append(
            queryEndDate).append(" 23:59:59")
          .append("'").append(
            " or  po.createdTime between '")
          .append(queryEndDate).append(" 00:00:00").append(
            "' and '")
          .append(queryBeginDate).append(" 23:59:59").append(
            "' )");
      } else {
        sb.append(" and ( po.createdTime between JSDB.FN_STRTODATE('")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('").append(
            queryEndDate).append(" 23:59:59")
          .append("','L')").append(
            " or  po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryEndDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('")
          .append(queryBeginDate).append(" 23:59:59").append(
            "','L') )");
      } 
    } 
    sb.append(" and (").append(wherePara).append(")");
    if (request.getParameter("redHeadId") != null && !request.getParameter("redHeadId").toUpperCase().trim().equals("NULL"))
      sb.append(" and ( po.documentSendFileHead='" + request.getParameter("redHeadId") + "') "); 
    if (request.getParameter("numId") != null && !request.getParameter("numId").toString().equals("")) {
      sb.append(" and ( po.sendFilePoNumId= " + request.getParameter("numId") + " )");
    } else if (request.getParameter("numType") != null && !request.getParameter("numType").toString().equals("")) {
      List<SendDocumentNumPO> numList = (new SenddocumentBD()).getSendNumByNumClass(request.getParameter("numType"));
      String sqlStr = "";
      if (numList != null && numList.size() > 0)
        for (int i = 0; i < numList.size(); i++) {
          SendDocumentNumPO po = numList.get(i);
          sqlStr = String.valueOf(sqlStr) + po.getId() + ",";
        }  
      if (sqlStr != null && sqlStr.length() > 1) {
        sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        sb.append(" and ( po.sendFilePoNumId in (" + sqlStr + " ) )");
      } 
    } 
    sb.append(" and (po.isDraft is null or po.isDraft<1) ");
    Page page = new Page(" po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime, po.createdEmp, po.createdOrg, po.sendFileLink, po.thirdDossier,po.transactStatus,po.sendFileText,po.sendFileDraft,po.accessoryName,po.accessorySaveName,po.tableId ", 
        fromwhere, 
        " where " + joinwhere + sb + " and po.domainId=" + domainId + 
        "  order by po.id desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
  
  private List listGWCY(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    EmployeeEJBBean emp = new EmployeeEJBBean();
    String empId = request.getParameter("empId");
    String orgId = null;
    try {
      orgId = emp.getOrgId(empId);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    ManagerService mbd = new ManagerService();
    String wherePara = " po.createdEmp=" + empId + " or (" + mbd.getRightFinalWhere(empId, orgId, "03*05*03", "po.createdOrg", "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + mbd.getRightFinalWhere(empId, orgId, "03*05*02", "po.createdOrg", "po.createdEmp") + ")";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String querySecret = request.getParameter("querySecret");
    String queryTransPersonName = request.getParameter("queryTransPersonName");
    String queryItem1 = request.getParameter("queryItem1");
    String queryItem2 = request.getParameter("queryItem2");
    String queryItem3 = "0";
    if (request.getParameter("queryItem3") != null)
      queryItem3 = request.getParameter("queryItem3").toString(); 
    String queryBeginDate1 = request.getParameter("queryBeginDate1");
    String queryEndDate1 = request.getParameter("queryEndDate1");
    String queryBeginDate2 = request.getParameter("queryBeginDate2");
    String queryEndDate2 = request.getParameter("queryEndDate2");
    String queryNumberCountBegin = request.getParameter("queryNumberCountBegin");
    String queryNumberCountEnd = request.getParameter("queryNumberCountEnd");
    String queryComeFileUnit = request.getParameter("queryComeFileUnit");
    String queryStatus = request.getParameter("queryStatus");
    String queryOrgName = request.getParameter("queryOrgName");
    String joinwhere = "1=1";
    String fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ";
    int ii = 0;
    String databaseType = SystemCommon.getDatabaseType();
    if ("1".equals(queryItem1))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.receiveFileReceiveDate between '").append(queryBeginDate1).append(" 00:00:00").append("' and '").append(queryEndDate1).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate1).append(" 00:00:00").append("' and '").append(queryBeginDate1).append(" 23:59:59").append("' )");
      } else {
        sb.append(" and ( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate1).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate1).append(" 23:59:59").append("','L') )");
      }  
    if ("1".equals(queryItem2)) {
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and (( po.receiveFileEndDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileEndDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("' )");
      } else {
        sb.append(" and (( po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileEndDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L') )");
      } 
      if (ii == 1)
        if (databaseType.indexOf("mysql") >= 0) {
          sb.append(" or ((( po.receiveFileReceiveDate between '").append(queryBeginDate2).append(" 00:00:00").append("' and '").append(queryEndDate2).append(" 23:59:59").append("'").append(" or  po.receiveFileReceiveDate between '").append(queryEndDate2).append(" 00:00:00").append("' and '").append(queryBeginDate2).append(" 23:59:59").append("') and po.receiveFileEndDate is null ))");
        } else {
          sb.append(" or ((( po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 23:59:59").append("','L')").append(" or  po.receiveFileReceiveDate between JSDB.FN_STRTODATE('").append(queryEndDate2).append(" 00:00:00").append("','L') and JSDB.FN_STRTODATE('").append(queryBeginDate2).append(" 23:59:59").append("','L')) and po.receiveFileEndDate is null ))");
        }  
      sb.append(")");
    } 
    sb.append(" and (").append(wherePara).append(")");
    if (request.getParameter("receiveType") != null && 
      !request.getParameter("receiveType").toString().equals("")) {
      String receiveType = request.getParameter("receiveType").toString();
      if (receiveType.equals("end")) {
        sb.append(" and ( ").append(" po.receiveFileStatus= '1' ").append(
            " )");
        request.setAttribute("receiveType", "end");
      } else {
        sb.append(" and ( ").append(" po.receiveFileStatus <> '1' ").append(
            " )");
        request.setAttribute("receiveType", "noend");
      } 
    } 
    Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate,po.thirdDossier,po.field5,po.zjkySeq,po.tableId  ", 
        fromwhere, 
        " where " + joinwhere + sb + " and po.domainId=" + domainId + " order by po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    return list;
  }
}
