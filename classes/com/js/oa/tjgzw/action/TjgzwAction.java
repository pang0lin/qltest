package com.js.oa.tjgzw.action;

import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.service.GovReceiveFileTypeBD;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.jsflow.vo.AccessTableVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.search.client.SearchService;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.tjgzw.bean.TjgzwBean;
import com.js.oa.tjgzw.utils.DateTime;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.page.sql.Page;
import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TjgzwAction extends Action {
  private static Logger log = Logger.getLogger(TjgzwAction.class);
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    httpServletResponse.setContentType("text/html;charset=UTF-8");
    Long curUserId = new Long(httpServletRequest.getSession(true).getAttribute("userId").toString());
    String type = httpServletRequest.getParameter("type");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    if (StringUtils.isNotEmpty(type) && "saveOrUpdateLinkEmail".equals(type)) {
      String email = httpServletRequest.getParameter("email");
      String pwd = httpServletRequest.getParameter("pwd");
      String pwdcon = httpServletRequest.getParameter("pwdcon");
      String updateSql = "update org_employee set empemail2=?,empemail3=? where emp_id=?";
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        pstmt = conn.prepareStatement(updateSql);
        pstmt.setString(1, email);
        pstmt.setString(2, pwd);
        pstmt.setLong(3, curUserId.longValue());
        pstmt.executeUpdate();
        httpServletResponse.getWriter().write("<script>alert('鎻愪氦鎴愬姛锛�);window.location.href='personal_work/setup/tjgzw/personalwork_email_link_tjgzw.jsp';</script>");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          if (pstmt != null)
            pstmt.close(); 
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
      return null;
    } 
    if (StringUtils.isNotEmpty(type) && "addlunch".equals(type)) {
      addLunch(httpServletRequest, httpServletResponse);
    } else {
      if (StringUtils.isNotEmpty(type) && "comonwordsshow".equals(type)) {
        try {
          String sql = "select id,words from common_words order by id";
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
          List<String[]> list = (List)new ArrayList<String>();
          while (rs.next()) {
            String id = rs.getString(1);
            String word = rs.getString(2);
            String[] obj = new String[2];
            obj[0] = id;
            obj[1] = word;
            list.add(obj);
          } 
          String str1 = "{\"rs\":[";
          for (int i = 0; i < list.size(); i++) {
            String[] obj = list.get(i);
            if (i != list.size() - 1) {
              str1 = String.valueOf(str1) + "{\"id\":\"" + obj[0] + "\",\"word\":\"" + obj[1] + "\"},";
            } else {
              str1 = String.valueOf(str1) + "{\"id\":\"" + obj[0] + "\",\"word\":\"" + obj[1] + "\"}";
            } 
          } 
          str1 = String.valueOf(str1) + "]}";
          httpServletResponse.getWriter().write(str1);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "comonwordsadd".equals(type)) {
        try {
          String v = httpServletRequest.getParameter("words");
          String sql = "insert into common_words values (HIBERNATE_SEQUENCE.Nextval,'" + URLDecoder.decode(v, "UTF8") + "')";
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          int r = pstmt.executeUpdate();
          String str1 = "{\"rs\":\"success\"}";
          httpServletResponse.getWriter().write(str1);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "comonwordsmodify".equals(type)) {
        try {
          String v = httpServletRequest.getParameter("words");
          String id = httpServletRequest.getParameter("id");
          String sql = "update common_words set words='" + URLDecoder.decode(v, "UTF8") + "' where id=" + id;
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          int r = pstmt.executeUpdate();
          String str1 = "{\"rs\":\"success\"}";
          httpServletResponse.getWriter().write(str1);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "comonwordsdel".equals(type)) {
        try {
          String id = httpServletRequest.getParameter("id");
          String sql = "delete from common_words where id=?";
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, Integer.parseInt(id));
          int r = pstmt.executeUpdate();
          String str1 = "{\"rs\":\"success\"}";
          httpServletResponse.getWriter().write(str1);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "goodsquery".equals(type)) {
        try {
          String query_str = httpServletRequest.getParameter("query_str");
          if (query_str == null)
            query_str = ""; 
          query_str = URLDecoder.decode(query_str, "UTF8");
          String sql = "select goods_id,GOODS_name,GOODS_UNIT from st_goods where GOODS_name like ?";
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, "%" + query_str + "%");
          rs = pstmt.executeQuery();
          List<String[]> objList = (List)new ArrayList<String>();
          while (rs.next()) {
            String id = rs.getString(1);
            String goods_name = rs.getString(2);
            String goods_unit = rs.getString(3);
            String[] obj = new String[3];
            obj[0] = id;
            obj[1] = goods_name;
            obj[2] = goods_unit;
            objList.add(obj);
          } 
          String str1 = "{\"rs\":[";
          for (int i = 0; i < objList.size(); i++) {
            String[] obj = objList.get(i);
            if (i != objList.size() - 1) {
              str1 = String.valueOf(str1) + "{\"id\":\"" + obj[0] + "\",\"goods_name\":\"" + obj[1] + "\",\"goods_unit\":\"" + obj[2] + "\"},";
            } else {
              str1 = String.valueOf(str1) + "{\"id\":\"" + obj[0] + "\",\"goods_name\":\"" + obj[1] + "\",\"goods_unit\":\"" + obj[2] + "\"}";
            } 
          } 
          str1 = String.valueOf(str1) + "]}";
          httpServletResponse.getWriter().write(str1);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "shumu".equals(type)) {
        String str1 = (httpServletRequest.getParameter("employeeId") == null) ? httpServletRequest.getSession().getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
        String um = null;
        try {
          String userId = (String)httpServletRequest.getSession().getAttribute("userId");
          String sql = 
            "select  count(1) from JSF_WORK a   where a.workstatus='0' and  a.workListControl = 1 and a.workDelete = 0 and a.wf_curemployee_id=? ";
          conn = (new DataSourceBase()).getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, Integer.parseInt(str1));
          rs = pstmt.executeQuery();
          while (rs.next())
            um = rs.getString(1); 
          String str2 = "{'rs':\"" + um + "\"}";
          String Str = str2.replaceAll("\\\\", "");
          String fstr = Str.substring(7, Str.lastIndexOf("}") - 1);
          httpServletResponse.getWriter().write(fstr);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null)
              rs.close(); 
            if (pstmt != null)
              pstmt.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
        return null;
      } 
      if (StringUtils.isNotEmpty(type) && "save_gzw_receivfile".equals(type)) {
        String result = "0";
        HttpSession httpSession1 = httpServletRequest.getSession(true);
        WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
        WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
        String formClassName = "ReceiveFileDrafGzwBD";
        String formClassMethod = "saveDraf";
        FormReflection formReflection = new FormReflection();
        Object obj = formReflection.execute("com.js.oa.tjgzw.service." + formClassName, formClassMethod, httpServletRequest);
        String processId = httpServletRequest.getParameter("processId");
        String tableId = httpServletRequest.getParameter("tableId");
        httpServletRequest.setAttribute("editId", obj);
        httpServletRequest.setAttribute("processId", processId);
        httpServletRequest.setAttribute("tableId", tableId);
        return actionMapping.findForward("loadreceivcc");
      } 
      if (StringUtils.isNotEmpty(type) && "list_gzw_receivfile_draf".equals(type)) {
        list(httpServletRequest);
        return actionMapping.findForward("list");
      } 
      if (StringUtils.isNotEmpty(type) && "listGoods".equals(type)) {
        listGoods(httpServletRequest);
        String export = httpServletRequest.getParameter("export");
        String tag = "listGoods";
        if (export != null && "1".equals(export))
          tag = "export"; 
        return actionMapping.findForward(tag);
      } 
      if ("delete".equals(type)) {
        Date beginDate = new Date();
        HttpSession session = httpServletRequest.getSession(true);
        Object object = session.getAttribute("domainId");
        String deleTitle = httpServletRequest.getParameter("deleTitle");
        delete(httpServletRequest);
        Date endDate = new Date();
        LogBD bd = new LogBD();
        bd.log(session.getAttribute("userId").toString(), 
            session.getAttribute("userName").toString(), 
            session.getAttribute("orgName").toString(), "oa_gw_sw", "", 
            beginDate, endDate, "3", deleTitle, 
            session.getAttribute("userIP").toString(), (String)object);
        return actionMapping.findForward("list");
      } 
      if ("getEmpIdByApplyId".equals(type)) {
        String applyId = httpServletRequest.getParameter("applyId");
        TjgzwBean tjgzwBean = new TjgzwBean();
        String empId = "";
        String result = "";
        if (!StringUtils.isEmpty(applyId))
          empId = tjgzwBean.getEmpIdByApplyId(applyId); 
        empId = empId.replace("$", "");
        result = "{\"empId\":\"" + empId + "\"}";
        try {
          httpServletResponse.getWriter().write(result);
        } catch (IOException e) {
          e.printStackTrace();
        } finally {}
        return null;
      } 
      String pageNum = httpServletRequest.getParameter("pageNum");
      String jsonStr = "";
      List<Map<String, String>> requestList = new ArrayList<Map<String, String>>();
      int totalRec = 0;
      int totalPage = 0;
      int preNum = 0;
      int nextNum = 0;
      int pageSize = 10;
      int curPageNum = 1;
      if (StringUtils.isNotEmpty(pageNum))
        curPageNum = Integer.parseInt(pageNum); 
      HttpSession httpSession = httpServletRequest.getSession(true);
      String tempSort = "";
      String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
      String viewSql = "select * from(select rownum as rn,b.* from (select a.workFileType,a.workCurStep,a.workTitle,a.workDeadLine,a.workSubmitPerson,a.workSubmitTime,a.workType,a.workActivity,a.WORKTABLE_ID,a.WORKRECORD_ID, a.WF_WORK_ID,a.workSubmitPerson as workSubmitPerson_,a.WF_SUBMITEMPLOYEE_ID,a.workAllowCancel,a.WORKPROCESS_ID,a.workStepCount,a.workMainLinkFile,a.workSubmitTime as workSubmitTime_, a.workCurStep as workCurStep_,a.creatorCancelLink,a.isStandForWork,a.standForUserId,  a.standForUserName,a.workCreateDate,a.submitOrg,a.workDoneWithDate, a.emergence,a.initActivity,a.initActivityName,a.tranType, a.tranFromPersonId, a.processDeadlineDate ,a.WF_CUREMPLOYEE_ID,a.relproject_id,a.work_hangup,a.workDeadlineDate, a.stickie,a.workreadmarker  from JSF_WORK a where a.workStatus='0' and a.workListControl=1 and a.workDelete=0 and a.wf_curemployee_id=?  order by  a.emergence desc, a.WF_WORK_ID desc ";
      viewSql = String.valueOf(viewSql) + ")b ) c where c.rn<? and c.rn>=? ";
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        pstmt = conn.prepareStatement(viewSql);
        pstmt.setInt(1, Integer.parseInt(wfCurEmployeeId));
        pstmt.setInt(2, pageSize * curPageNum + 1);
        pstmt.setInt(3, pageSize * (curPageNum - 1) + 1);
        rs = pstmt.executeQuery();
        while (rs.next()) {
          Map<String, String> tempMap = new HashMap<String, String>();
          tempMap.put("workFileType", rs.getString(2));
          tempMap.put("workCurStep", rs.getString(3));
          tempMap.put("workTitle", rs.getString(4));
          tempMap.put("workDeadLine", rs.getString(5));
          tempMap.put("workSubmitPerson", rs.getString(6));
          tempMap.put("workSubmitTime", (new SimpleDateFormat("yyyy/MM/dd")).format(rs.getDate(7)));
          tempMap.put("workType", rs.getString(8));
          tempMap.put("workActivity", rs.getString(9));
          tempMap.put("WORKTABLE_ID", rs.getString(10));
          tempMap.put("WORKRECORD_ID", rs.getString(11));
          tempMap.put("WF_WORK_ID", rs.getString(12));
          tempMap.put("workSubmitPerson", rs.getString(13));
          tempMap.put("WF_SUBMITEMPLOYEE_ID", rs.getString(14));
          tempMap.put("workAllowCancel", rs.getString(15));
          tempMap.put("WORKPROCESS_ID", rs.getString(16));
          tempMap.put("workStepCount", rs.getString(17));
          tempMap.put("workMainLinkFile", rs.getString(18));
          tempMap.put("workSubmitTime", rs.getString(19));
          tempMap.put("workCurStep", rs.getString(20));
          tempMap.put("creatorCancelLink", rs.getString(21));
          tempMap.put("isStandForWork", rs.getString(22));
          tempMap.put("standForUserId", rs.getString(23));
          tempMap.put("standForUserName", rs.getString(24));
          tempMap.put("workCreateDate", rs.getString(25));
          tempMap.put("submitOrg", rs.getString(26));
          tempMap.put("workDoneWithDate", rs.getString(27));
          tempMap.put("emergence", rs.getString(28));
          tempMap.put("initActivity", rs.getString(29));
          tempMap.put("initActivityName", rs.getString(30));
          tempMap.put("tranType", rs.getString(31));
          tempMap.put("tranFromPersonId", rs.getString(32));
          tempMap.put("processDeadlineDate", rs.getString(33));
          tempMap.put("WF_CUREMPLOYEE_ID", rs.getString(34));
          tempMap.put("relproject_id", rs.getString(35));
          tempMap.put("work_hangup", rs.getString(36));
          tempMap.put("workDeadlineDate", rs.getString(37));
          tempMap.put("stickie", rs.getString(38));
          tempMap.put("workreadmarker", rs.getString(39));
          requestList.add(tempMap);
        } 
        String sqlCount = 
          "select  count(1) from JSF_WORK a   where a.workstatus='0' and  a.workListControl = 1 and a.workDelete = 0 and a.wf_curemployee_id=? ";
        pstmt = conn.prepareStatement(sqlCount);
        pstmt.setInt(1, Integer.parseInt(wfCurEmployeeId));
        rs = pstmt.executeQuery();
        while (rs.next())
          totalRec = rs.getInt(1); 
        totalPage = (totalRec + pageSize - 1) / pageSize;
        if (curPageNum == 1) {
          preNum = 1;
        } else {
          preNum = curPageNum - 1;
        } 
        if (curPageNum == totalPage) {
          nextNum = totalPage;
        } else {
          nextNum = curPageNum + 1;
        } 
        jsonStr = String.valueOf(jsonStr) + "{ \"totalPage\" : \"" + totalPage + "\",\"preNum\":\"" + preNum + "\",\"nextNum\":\"" + nextNum + "\",\"curPageNum\":\"" + curPageNum + "\",";
        jsonStr = String.valueOf(jsonStr) + "\"rs\":";
        Gson gson = new Gson();
        jsonStr = String.valueOf(jsonStr) + gson.toJson(requestList);
        jsonStr = String.valueOf(jsonStr) + "}";
        System.out.println("------------------------------" + jsonStr + "----------------------------------");
        httpServletResponse.getWriter().write(jsonStr);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close(); 
          if (pstmt != null)
            pstmt.close(); 
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    return null;
  }
  
  public ActionForward addLunch(HttpServletRequest request, HttpServletResponse response) {
    String weekstartdate = request.getParameter("weekstartdate");
    String weeksenddate = request.getParameter("weeksenddate");
    String mondaylch = request.getParameter("mondaylch");
    if (mondaylch == null || mondaylch.equals(""))
      mondaylch = "鏆傛棤鍗堥淇℃伅"; 
    String tuesdaylch = request.getParameter("tuesdaylch");
    if (tuesdaylch == null || tuesdaylch.equals(""))
      tuesdaylch = "鏆傛棤鍗堥淇℃伅"; 
    String wednesdaylch = request.getParameter("wednesdaylch");
    if (wednesdaylch == null || wednesdaylch.equals(""))
      wednesdaylch = "鏆傛棤鍗堥淇℃伅"; 
    String thursdaylch = request.getParameter("thursdaylch");
    if (thursdaylch == null || thursdaylch.equals(""))
      thursdaylch = "鏆傛棤鍗堥淇℃伅"; 
    String fridaylch = request.getParameter("fridaylch");
    if (fridaylch == null || fridaylch.equals(""))
      fridaylch = "鏆傛棤鍗堥淇℃伅"; 
    String saterdaylch = request.getParameter("saterdaylch");
    if (saterdaylch == null || saterdaylch.equals(""))
      saterdaylch = "鏆傛棤鍗堥淇℃伅"; 
    String sundaylch = request.getParameter("sundaylch");
    if (sundaylch == null || sundaylch.equals(""))
      sundaylch = "鏆傛棤鍗堥淇℃伅"; 
    String id = request.getParameter("id");
    Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
    String curUserName = request.getSession(true).getAttribute("userName").toString();
    Long curOrgId = new Long(request.getSession(true).getAttribute("orgId").toString());
    String curOrgName = request.getSession(true).getAttribute("orgName").toString();
    int fushfalg = 1;
    DateTime dt = new DateTime();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "";
    if (id == null || id.equals("")) {
      sql = 
        "insert into gzw_week_lunch (YEAR, WEEKNUM, WEEKSTARTDATE, WEEKENDDATE, MONDAYLUNCH, TUESDAYLUNCH, WEDNESDAYLUNCH, THURSDAYLUNCH, FRIDAYLUNCH, SATERDAYLUNCH, SUNDAYLUNCH, CREATERID, CREATERNAME, CREATERADPID, CREATERADPNAME, FUSHFALG) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    } else {
      sql = 
        "update gzw_week_lunch set YEAR=?,WEEKNUM=?,WEEKSTARTDATE=?,WEEKENDDATE=?, MONDAYLUNCH=?,  TUESDAYLUNCH=?,WEDNESDAYLUNCH=?,THURSDAYLUNCH=?,FRIDAYLUNCH=?,SATERDAYLUNCH=?,SUNDAYLUNCH=?,CREATERID=?, CREATERNAME=?,CREATERADPID=?, CREATERADPNAME=?, FUSHFALG=? where id=? ";
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      String[] weeksDate = dt.getAllWeeksByTime(weekstartdate);
      pstmt.setInt(1, Integer.parseInt(weeksenddate.substring(0, 4)));
      pstmt.setInt(2, Integer.parseInt(dt.getZhouNumByTime2(weeksenddate)));
      pstmt.setString(3, weekstartdate);
      pstmt.setString(4, weeksenddate);
      pstmt.setString(5, String.valueOf(weeksDate[0]) + "&" + mondaylch);
      pstmt.setString(6, String.valueOf(weeksDate[1]) + "&" + tuesdaylch);
      pstmt.setString(7, String.valueOf(weeksDate[2]) + "&" + wednesdaylch);
      pstmt.setString(8, String.valueOf(weeksDate[3]) + "&" + thursdaylch);
      pstmt.setString(9, String.valueOf(weeksDate[4]) + "&" + fridaylch);
      pstmt.setString(10, String.valueOf(weeksDate[5]) + "&" + saterdaylch);
      pstmt.setString(11, String.valueOf(weeksDate[6]) + "&" + sundaylch);
      pstmt.setString(12, (String)curUserId);
      pstmt.setString(13, curUserName);
      pstmt.setString(14, (String)curOrgId);
      pstmt.setString(15, curOrgName);
      pstmt.setInt(16, 1);
      if (id != null && !id.equals(""))
        pstmt.setString(17, (new StringBuilder(String.valueOf(id))).toString()); 
      pstmt.executeUpdate();
      response.getWriter().write("<script>alert('鎻愪氦鎴愬姛锛�);window.location.href='/jsoa/tjgzw/week_lunch_add.jsp';</script>");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return null;
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    getReceiveFileType(request);
    String wherePara = " po.createdEmp=" + httpSesison.getAttribute("userId") + " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*03", "po.createdOrg", "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + mbd.getRightFinalWhere(httpSesison.getAttribute("userId").toString(), httpSesison.getAttribute("orgId").toString(), "03*05*02", "po.createdOrg", "po.createdEmp") + ")";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryName = request.getParameter("queryName");
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
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.receiveFileFileNumber like '%").append(queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.receiveFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (queryStatus != null && !"none".equals(queryStatus))
      sb.append(" and po.receiveFileStatus =").append(queryStatus); 
    if (querySecret != null && !"none".equals(querySecret))
      sb.append(" and po.receiveFileSafetyGrade = '").append(querySecret).append("'"); 
    if (queryTransPersonName != null && !"".equals(queryTransPersonName)) {
      ModuleVO moduleVO = new ModuleVO();
      moduleVO.setFormType(1);
      moduleVO.setId(3);
      String tableId = "0";
      List<AccessTableVO> list = (new WorkFlowBD()).getAccessTable(moduleVO);
      if (list != null && list.size() > 0)
        tableId = (new StringBuilder(String.valueOf(((AccessTableVO)list.get(0)).getId()))).toString(); 
      sb.append(" and emp.empName like '%").append(queryTransPersonName).append("%'");
      fromwhere = "com.js.doc.doc.po.GovReceiveFilePO po ,com.js.system.vo.usermanager.EmployeeVO emp , com.js.goa.workflow.po.wfDealWithCommentPO wfcomm join wfcomm.dealWith wfdw ";
      joinwhere = String.valueOf(joinwhere) + " and wfcomm.dealWithEmployeeId=emp.empId and wfdw.databaseRecordId=po.id and wfdw.databaseTableId=" + tableId;
    } 
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
    if (queryNumberCountBegin != null && !"".equals(queryNumberCountBegin))
      sb.append(" and po.receiveFileFileNoCount >=" + queryNumberCountBegin); 
    if (queryNumberCountEnd != null && !"".equals(queryNumberCountEnd))
      sb.append(" and po.receiveFileFileNoCount <= " + queryNumberCountEnd); 
    if (queryComeFileUnit != null && !"".equals(queryComeFileUnit))
      sb.append(" and po.receiveFileSendFileUnit  like '%" + queryComeFileUnit + "%'"); 
    if (request.getParameter("zjkySeq") != null && 
      !request.getParameter("zjkySeq").toString().equals(""))
      sb.append(" and po.zjkySeq  like '%" + request.getParameter("zjkySeq") + "%'"); 
    if (request.getParameter("seqId") != null && 
      !request.getParameter("seqId").toString().equals("")) {
      sb.append(" and ( po.seqId= " + request.getParameter("seqId") + " )");
    } else if (request.getParameter("seqType") != null && 
      !request.getParameter("seqType").toString().equals("")) {
      List<ReceiveFileSeqPO> seqList = (new ReceivedocumentBD()).getSeqPoListBySeqClass(
          request.getParameter("seqType"));
      String sqlStr = "";
      if (seqList != null && seqList.size() > 0)
        for (int i = 0; i < seqList.size(); i++) {
          ReceiveFileSeqPO po = seqList.get(i);
          sqlStr = String.valueOf(sqlStr) + po.getId() + ",";
        }  
      if (sqlStr != null && sqlStr.length() > 1) {
        sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        sb.append(" and ( po.seqId in (" + sqlStr + " ) )");
      } 
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
    if (queryName != null && !"".equals(queryName))
      sb.append(" and poo.workFlowProcessName like '%" + queryName + "%' "); 
    try {
      Page page = new Page(" po.id,po.receiveFileFileNumber,po.receiveFileTitle,po.receiveFileIsEnd,po.receiveFileEndDate,po.receiveFileLink,po.receiveFileDoDepartNm,po.createdEmp,po.createdOrg,po.receiveFileFileNo,po.receiveFileSendFileUnit,po.receiveFileStatus,po.receiveFileReceiveDate,po.thirdDossier,po.field5,po.zjkySeq,po.tableId,po.field19,po.field20 ,poo.workFlowProcessName", 


          
          String.valueOf(fromwhere) + ",com.js.oa.jsflow.po.WFWorkFlowProcessPO poo", 
          " where " + joinwhere + sb + " and poo.wfWorkFlowProcessId=po.processId and po.isDraf='1' and po.tableId = poo.accessDatabaseId and po.domainId=" + domainId + " order by po.createdTime desc ,po.receiveFileFileNoCount desc,po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "type,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm,seqId,seqType,zjkySeq,receiveType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    List<Object[]> dRight = mbd.getRightScope(httpSesison.getAttribute("userId").toString(), "03*16*01");
    String dRightScopeType = "", dRightScopeScope = "", dOrgRange = "";
    if (dRight != null && dRight.size() > 0) {
      Object[] dObj = dRight.get(0);
      dRightScopeType = dObj[0].toString();
      dRightScopeScope = (dObj[1] == null) ? "" : dObj[1].toString();
      if ("2".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange("*" + httpSesison.getAttribute("orgId") + "*"); 
      if ("4".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange(dRightScopeScope); 
    } 
    request.setAttribute("dRightScopeType", dRightScopeType);
    request.setAttribute("dOrgRange", dOrgRange);
    getDefendRight(request);
  }
  
  private void getReceiveFileType(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String scopeWhere = (new ManagerService()).getScopeFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.userId", "po.orgId", "po.groupId");
    scopeWhere = String.valueOf(scopeWhere) + "and po.domainId=" + domainId;
    GovReceiveFileTypeBD govReceiveFileTypeBD = new GovReceiveFileTypeBD();
    List grftList = govReceiveFileTypeBD.govReceiveFileTypeList(scopeWhere);
    httpServletRequest.setAttribute("grftList", grftList);
  }
  
  private void getDefendRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "03*05*02");
    if (rightList != null && rightList.size() > 0) {
      Object[] objRight = rightList.get(0);
      String scopeType = objRight[0].toString();
      String orgRange = "";
      if ("3".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange("*" + orgId + "*");
      } else if ("4".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange((String)objRight[1]);
      } 
      httpServletRequest.setAttribute("defendScopeType", scopeType);
      httpServletRequest.setAttribute("defendOrgRange", "," + orgRange + ",");
    } 
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    String id = httpServletRequest.getParameter("id");
    (new ReceiveFileBD()).delete(id);
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.deleteIndex(id.toString(), "doc_receivefile");
    } 
    list(httpServletRequest);
  }
  
  private void listGoods(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    String queryLb = request.getParameter("queryLb");
    String queryTitle = request.getParameter("queryTitle");
    String queryComeFileUnit = request.getParameter("queryComeFileUnit");
    String queryItem1 = request.getParameter("queryItem1");
    String queryBeginDate1 = request.getParameter("queryBeginDate1");
    String queryEndDate1 = request.getParameter("queryEndDate1");
    String niandu = request.getParameter("niandu");
    String export = request.getParameter("export");
    request.setAttribute("queryLb", queryLb);
    request.setAttribute("niandu", niandu);
    request.setAttribute("queryTitle", queryTitle);
    request.setAttribute("queryComeFileUnit", queryComeFileUnit);
    ManagerService mbd = new ManagerService();
    getReceiveFileType(request);
    int pageSize = 15;
    if (export != null && export.equals("1"))
      pageSize = 1000000000; 
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = null;
    try {
      if ("xxx" != queryLb)
        if (queryLb != null && "001".equals(queryLb)) {
          String wherePara = "1=1";
          StringBuffer sb = new StringBuffer("");
          String queryName = request.getParameter("queryName");
          String queryNumber = request.getParameter("queryNumber");
          String querySecret = request.getParameter("querySecret");
          String queryTransPersonName = request.getParameter("queryTransPersonName");
          String fromwhere = 
            
            "(select d.orgname, (b.jst_3002_f3014 * e.price) as sum_price           from jst_3001         a,                jst_3002         b,                org_employee     c,                org_organization d,                st_goods         e           where            a.jst_3001_id = b.jst_3002_foreignkey        and a.jst_3001_owner = c.emp_id        and a.jst_3001_org = d.org_id        and b.jst_3002_f3009 = e.goods_name        and b.jst_3002_f3014 > 0  ";
          String databaseType = SystemCommon.getDatabaseType();
          if ("1".equals(queryItem1))
            if (databaseType.indexOf("mysql") < 0) {
              fromwhere = String.valueOf(fromwhere) + " and (to_date(a.jst_3001_date,'yyyy-mm-dd') >= to_date('" + queryBeginDate1 + "','yyyy-mm-dd') and to_date(a.jst_3001_date,'yyyy-mm-dd') <= to_date('" + queryEndDate1 + "','yyyy-mm-dd')) ";
              System.out.println(wherePara);
            }  
          if (StringUtils.isNotEmpty(queryTitle))
            fromwhere = String.valueOf(fromwhere) + " and e.goods_name like '%" + queryTitle + "%' "; 
          if (StringUtils.isNotEmpty(queryComeFileUnit))
            fromwhere = String.valueOf(fromwhere) + " and d.orgname like '%" + queryComeFileUnit + "%' "; 
          if (StringUtils.isNotEmpty(niandu))
            fromwhere = String.valueOf(fromwhere) + " and a.jst_3001_date like '%" + niandu + "%' "; 
          fromwhere = String.valueOf(fromwhere) + ")";
          String queryItem = " orgname, sum(sum_price)  ";
          page = new Page(queryItem, 
              fromwhere, 
              " where " + wherePara + "  group by orgname ");
        } else if (queryLb != null && "002".equals(queryLb)) {
          String wherePara = "1=1";
          String fromwhere = 

            
            "(select b.jst_3002_f3009,               b.jst_3002_f3014,               (b.jst_3002_f3014 * e.price) as sum_price          from jst_3001         a,               jst_3002         b,               org_employee     c,               org_organization d,               st_goods         e         where a.jst_3001_id = b.jst_3002_foreignkey           and a.jst_3001_owner = c.emp_id           and a.jst_3001_org = d.org_id           and b.jst_3002_f3009 = e.goods_name           and b.jst_3002_f3014 > 0";
          String databaseType = SystemCommon.getDatabaseType();
          if ("1".equals(queryItem1) && 
            databaseType.indexOf("mysql") < 0) {
            fromwhere = String.valueOf(fromwhere) + " and (to_date(a.jst_3001_date,'yyyy-mm-dd') >= to_date('" + queryBeginDate1 + "','yyyy-mm-dd') and to_date(a.jst_3001_date,'yyyy-mm-dd') <= to_date('" + queryEndDate1 + "','yyyy-mm-dd')) ";
            System.out.println(wherePara);
          } 
          if (StringUtils.isNotEmpty(queryTitle))
            fromwhere = String.valueOf(fromwhere) + " and e.goods_name like '%" + queryTitle + "%' "; 
          if (StringUtils.isNotEmpty(queryComeFileUnit))
            fromwhere = String.valueOf(fromwhere) + " and d.orgname like '%" + queryComeFileUnit + "%' "; 
          if (StringUtils.isNotEmpty(niandu))
            fromwhere = String.valueOf(fromwhere) + " and a.jst_3001_date like '%" + niandu + "%' "; 
          fromwhere = String.valueOf(fromwhere) + ")";
          String queryItem = " jst_3002_f3009, sum(jst_3002_f3014), sum(sum_price) ";
          page = new Page(queryItem, 
              fromwhere, 
              " where " + wherePara + "  group by jst_3002_f3009 ");
        } else if (queryLb != null && "003".equals(queryLb)) {
          String wherePara = " a.jst_3001_id = b.jst_3002_foreignkey and a.jst_3001_owner = c.emp_id and a.jst_3001_org = d.org_id and b.jst_3002_f3009=e.goods_name and b.jst_3002_f3014>0  ";
          StringBuffer sb = new StringBuffer("");
          String queryName = request.getParameter("queryName");
          String queryNumber = request.getParameter("queryNumber");
          String querySecret = request.getParameter("querySecret");
          String queryTransPersonName = request.getParameter("queryTransPersonName");
          String fromwhere = "  jst_3001 a, jst_3002 b,org_employee c, org_organization d,st_goods e ";
          String queryItem = "  d.orgname  ";
          page = new Page(queryItem, 
              fromwhere, 
              " where " + wherePara + " group by d.orgname ");
        } else if (queryLb != null && "004".equals(queryLb)) {
          String wherePara = " a.jst_3001_id = b.jst_3002_foreignkey and a.jst_3001_owner = c.emp_id and a.jst_3001_org = d.org_id and b.jst_3002_f3009=e.goods_name and b.jst_3002_f3014>0  ";
          StringBuffer sb = new StringBuffer("");
          String queryName = request.getParameter("queryName");
          String queryNumber = request.getParameter("queryNumber");
          String querySecret = request.getParameter("querySecret");
          String queryTransPersonName = request.getParameter("queryTransPersonName");
          String fromwhere = "  jst_3001 a, jst_3002 b,org_employee c, org_organization d,st_goods e ";
          String queryItem = "  b.jst_3002_f3009  ";
          page = new Page(queryItem, 
              fromwhere, 
              " where " + wherePara + " group by b.jst_3002_f3009 ");
        } else {
          String wherePara = " a.jst_3001_id = b.jst_3002_foreignkey and a.jst_3001_owner = c.emp_id and a.jst_3001_org = d.org_id and b.jst_3002_f3009=e.goods_name and b.jst_3002_f3014>0  ";
          StringBuffer sb = new StringBuffer("");
          String queryName = request.getParameter("queryName");
          String queryNumber = request.getParameter("queryNumber");
          String querySecret = request.getParameter("querySecret");
          String queryTransPersonName = request.getParameter("queryTransPersonName");
          String fromwhere = "  jst_3001 a, jst_3002 b,org_employee c, org_organization d,st_goods e ";
          String databaseType = SystemCommon.getDatabaseType();
          if ("1".equals(queryItem1))
            if (databaseType.indexOf("mysql") < 0) {
              wherePara = String.valueOf(wherePara) + " and (to_date(a.jst_3001_date,'yyyy-mm-dd') >= to_date('" + queryBeginDate1 + "','yyyy-mm-dd') and to_date(a.jst_3001_date,'yyyy-mm-dd') <= to_date('" + queryEndDate1 + "','yyyy-mm-dd')) ";
              System.out.println(wherePara);
            }  
          if (StringUtils.isNotEmpty(queryTitle))
            wherePara = String.valueOf(wherePara) + " and e.goods_name like '%" + queryTitle + "%' "; 
          if (StringUtils.isNotEmpty(queryComeFileUnit))
            wherePara = String.valueOf(wherePara) + " and d.orgname like '%" + queryComeFileUnit + "%' "; 
          String queryItem = " b.jst_3002_f3009,c.empname,d.orgname,a.jst_3001_date,b.jst_3002_f3014,e.price,e.price as sum_price  ";
          page = new Page(queryItem, 
              fromwhere, 
              " where " + wherePara);
        }  
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "type,issearch,queryItem2,queryItem3,queryBeginDate2,queryEndDate2,queryItem1,queryBeginDate1,queryEndDate1,queryNumber,querySecret,queryTransPersonName,queryTitle,queryStatus,queryOrgName,queryNumberCountBegin,queryNumberCountEnd,queryComeFileUnit,zbstatus,receiveFileTogetherDoDepartNm,receiveFileSendLeaderCheckNm,receiveFileSendLeaderReaderNm,seqId,seqType,zjkySeq,receiveType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    List<Object[]> dRight = mbd.getRightScope(httpSesison.getAttribute("userId").toString(), "03*16*01");
    String dRightScopeType = "", dRightScopeScope = "", dOrgRange = "";
    if (dRight != null && dRight.size() > 0) {
      Object[] dObj = dRight.get(0);
      dRightScopeType = dObj[0].toString();
      dRightScopeScope = (dObj[1] == null) ? "" : dObj[1].toString();
      if ("2".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange("*" + httpSesison.getAttribute("orgId") + "*"); 
      if ("4".equals(dRightScopeType))
        dOrgRange = mbd.getAllJuniorOrgIdByRange(dRightScopeScope); 
    } 
    request.setAttribute("dRightScopeType", dRightScopeType);
    request.setAttribute("dOrgRange", dOrgRange);
    getDefendRight(request);
  }
}
