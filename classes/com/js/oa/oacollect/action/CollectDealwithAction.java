package com.js.oa.oacollect.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.module.bean.ModuleMenuEJBBean;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.module.util.CommPagesGeneration;
import com.js.oa.module.util.CustomizeDataImport;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.oacollect.po.OaCollect;
import com.js.oa.oacollect.util.CollectUtil;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CollectDealwithAction extends Action {
  private static String SUB_LIST = "subList";
  
  private static String DEL_BATCH = "delBatch";
  
  private static String DEL_ALL = "delAll";
  
  private static String IMPORTDATA = "import";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = request.getSession(true);
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    String action = request.getParameter("action");
    String collectId = request.getParameter("collectId");
    ManagerService mBD = new ManagerService();
    request.setAttribute("collectId", collectId);
    String searchWithout = request.getParameter("searchWithout");
    Long domainId = (session.getAttribute("domainId") != null) ? 
      Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    if ("getPage".equals(action)) {
      String delFlag = request.getParameter("delflag");
      request.setAttribute("offset", request.getParameter("pager.offset"));
      if (delFlag != null && "1".equals(delFlag)) {
        ModuleMenuService bd = new ModuleMenuService();
        String delId = request.getParameter("ids");
        String tblId = request.getParameter("tblId");
        if (delId == null || tblId == null)
          throw new Exception("删除编号不能为空!"); 
        if (bd.deleteBizDatas(tblId, delId))
          loadDefListPageDatas(domainId.toString(), collectId, request, 
              searchWithout, curUserId, orgIdString, 
              "search", 
              session, mBD); 
      } else if (delFlag != null && "2".equals(delFlag)) {
        ModuleMenuService bd = new ModuleMenuService();
        String tblId = request.getParameter("tblId");
        if (tblId == null || tblId == "null")
          throw new Exception("表名称不能为空!"); 
        if (bd.deleteAllMasterAndSub(tblId))
          loadDefListPageDatas(domainId.toString(), collectId, request, 
              searchWithout, curUserId, orgIdString, 
              "search", 
              session, mBD); 
      } else {
        loadDefListPageDatas(domainId.toString(), collectId, request, 
            searchWithout, curUserId, orgIdString, 
            "init", 
            session, mBD);
      } 
      if ("export".equals(request.getParameter("flag"))) {
        if (request.getParameter("type") != null && "sub".equals(request.getParameter("type")))
          return actionMapping.findForward("sub"); 
        return actionMapping.findForward("export");
      } 
      return actionMapping.findForward("success");
    } 
    if ("subExport".equals(action)) {
      String tableId = request.getParameter("tableId");
      String processId = request.getParameter("processId");
      String[][] formTable = (new CustomFormBD()).getTableIDAndName(tableId);
      request.setAttribute("processId", processId);
      request.setAttribute("tableId", tableId);
      request.setAttribute("tableName", formTable[0][1]);
      String mainTableName = formTable[0][1];
      String subTableName = "";
      String[][] tableHead = (String[][])null;
      String[][] tableData = (String[][])null;
      String fields = request.getParameter("fields");
      String subfields = request.getParameter("subfields");
      if (formTable != null && formTable.length > 0) {
        String[][] tableHead1 = new String[0][0], tableHead2 = new String[0][0];
        if (fields == null) {
          tableHead1 = (new CustomDatabaseBD()).getListField(formTable[0][0]);
        } else {
          String fieldString = request.getParameter("fields");
          tableHead1 = (new CustomDatabaseBD()).getListField(formTable[0][0], fieldString);
        } 
        if (subfields == null || subfields.length() <= 0) {
          tableHead2 = new String[0][0];
        } else {
          subTableName = request.getParameter("sub_table").split("-")[0];
          String fieldString1 = request.getParameter("subfields");
          tableHead2 = (new CustomDatabaseBD()).getSubListField(formTable[0][0], fieldString1);
        } 
        if (tableHead2 != null && tableHead2.length >= 0)
          tableHead = unite(tableHead1, tableHead2); 
        StringBuffer sb = new StringBuffer("select ");
        int i;
        for (i = 0; i < tableHead1.length; i++) {
          if (i == 0) {
            sb.append("m." + tableHead1[i][2]);
          } else {
            sb.append(",m." + tableHead1[i][2]);
          } 
        } 
        for (i = 0; tableHead2 != null && tableHead2.length >= 0 && i < tableHead2.length; i++)
          sb.append(",s." + tableHead2[i][2]); 
        sb.append(" from " + mainTableName + " m ");
        if (!"".equals(subTableName)) {
          sb.append("," + subTableName + " s where m." + mainTableName + "_id=s." + subTableName + "_FOREIGNKEY ");
        } else {
          sb.append(" where 1=1 ");
        } 
        if (request.getParameter("ids") != null && !"".equals(request.getParameter("ids")))
          sb.append(" and m." + mainTableName + "_id in (" + request.getParameter("ids") + ") "); 
        sb.append(" and m." + mainTableName + "_relaByInde=" + collectId + " order by m." + mainTableName + "_id");
        tableData = (new CollectUtil()).collectData(sb.toString(), tableHead1.length + tableHead2.length);
        request.setAttribute("tableHead", tableHead);
        request.setAttribute("tableData", tableData);
      } else {
        tableData = new String[0][0];
        request.setAttribute("tableHead", new String[0][0]);
        request.setAttribute("tableData", tableData);
      } 
      return actionMapping.findForward("subExport");
    } 
    if ("listWithSearch".equals(action)) {
      loadDefListPageDatas(domainId.toString(), collectId, request, 
          searchWithout, curUserId, orgIdString, "search", 
          session, mBD);
      if ("export".equals(request.getParameter("flag")))
        return actionMapping.findForward("export"); 
      return actionMapping.findForward("success");
    } 
    if (SUB_LIST.equals(action)) {
      String tblId = request.getParameter("tblId");
      request.setAttribute("collectId", collectId);
      loadSubTableDatas(tblId, domainId.toString(), searchWithout, 
          request, 
          collectId, curUserId);
      return actionMapping.findForward("success");
    } 
    if (DEL_BATCH.equals(action)) {
      ModuleMenuService bd = new ModuleMenuService();
      String delId = request.getParameter("ids");
      request.setAttribute("offset", request.getParameter("pager.offset"));
      String tblId = request.getParameter("tblId");
      if (delId == null || tblId == null)
        throw new Exception("删除编号不能为空!"); 
      if (bd.deleteBizDatas(tblId, delId))
        loadDefListPageDatas(domainId.toString(), collectId, request, 
            searchWithout, curUserId, orgIdString, 
            "init", 
            session, mBD); 
      return actionMapping.findForward("success");
    } 
    if (DEL_ALL.equals(action)) {
      ModuleMenuService bd = new ModuleMenuService();
      String tblId = request.getParameter("tblId");
      request.setAttribute("offset", request.getParameter("pager.offset"));
      if (tblId == null || tblId == null)
        throw new Exception("表名称不能为空!"); 
      if (bd.deleteAllMasterAndSub(tblId))
        loadDefListPageDatas(domainId.toString(), collectId, request, 
            searchWithout, curUserId, orgIdString, 
            "init", 
            session, mBD); 
      return actionMapping.findForward("success");
    } 
    if (IMPORTDATA.equals(action)) {
      Object obj = request.getSession(true).getAttribute("fileServer");
      String fileServer = (obj != null) ? obj.toString() : null;
      String saveFileName = request.getParameter("dataSaveName");
      String srcFive = "0000";
      if (saveFileName != null && saveFileName.length() > 6 && saveFileName.substring(4, 5).equals("_")) {
        srcFive = saveFileName.substring(0, 4);
      } else {
        srcFive = "0000";
      } 
      String path = String.valueOf(request.getRealPath("/")) + "/upload/" + srcFive + "/datas/" + 
        saveFileName;
      String urlPath = String.valueOf(fileServer) + "/upload/" + srcFive + "/datas/" + saveFileName;
      Map sysMap = 
        SysSetupReader.getInstance().getSysSetupMap(session
          .getAttribute("domainId").toString());
      if (sysMap != null && sysMap.get("附件上传") != null && 
        sysMap.get("附件上传").toString().equals("0")) {
        int bytesum = 0;
        int byteread = 0;
        URL url = new URL(urlPath);
        URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();
        FileOutputStream fs = new FileOutputStream(path);
        byte[] buffer = new byte[3072];
        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread;
          fs.write(buffer, 0, byteread);
        } 
        fs.close();
      } 
      int retProcC = (new CustomizeDataImport()).importDataProcessor(
          path, session.getAttribute("userId").toString(), 
          orgId, collectId);
      if (retProcC > 0) {
        request.setAttribute("flag", "1");
        request.setAttribute("count", (new StringBuilder(String.valueOf(retProcC))).toString());
        return actionMapping.findForward("import");
      } 
      request.setAttribute("flag", "2");
      return actionMapping.findForward("import");
    } 
    return null;
  }
  
  private void loadSubTableDatas(String tblId, String domainId, String searchWithout, HttpServletRequest request, String collectId, String curUserId) {
    if (tblId != null && tblId.length() > 0) {
      ModuleMenuService bd = new ModuleMenuService();
      String[][] fNames = bd.getTableFields(domainId.toString(), 
          tblId);
      String[][] datas = bd.getSubTableDatas(domainId.toString(), 
          tblId, 
          new Integer(fNames.length));
      ModuleMenuPO po = 
        bd.loadMenuConfig(domainId, 
          collectId)
        .get(0);
      po.setMenuListTableName(tblId);
      String dispStr = "";
      if (fNames != null && fNames.length > 0 && datas != null && 
        datas.length > 0)
        dispStr = createDispHtml(searchWithout, fNames, datas, po, 
            collectId, 
            curUserId); 
      request.setAttribute("listPart", dispStr);
    } 
  }
  
  private void loadDefListPageDatas(String domainId, String collectId, HttpServletRequest request, String searchWithout, String curUserId, String orgIdString, String actDescribe, HttpSession session, ManagerService mBD) throws SQLException, SQLException, Exception {
    int thisPageCount = 0;
    boolean isRefFlow = false;
    OACollectEJBBean bd = new OACollectEJBBean();
    OaCollect po = 
      bd.loadOaTaskCollect(new Long(collectId));
    request.setAttribute("tblId", po.getCollectTTable());
    String fromTabFlag = (request.getParameter("fromTabFlag") == null) ? ((request.getAttribute("fromTabFlag") == null) ? "" : request.getAttribute("fromTabFlag").toString()) : request.getParameter("fromTabFlag");
    request.setAttribute("fromTabFlag", fromTabFlag);
    String fromFlag = (request.getParameter("fromFlag") == null) ? ((request.getAttribute("fromFlag") == null) ? "" : request.getAttribute("fromFlag").toString()) : request.getParameter("fromFlag");
    request.setAttribute("fromFlag", fromFlag);
    request.setAttribute("isMultiCollect", po.getIsMultiCollect());
    request.setAttribute("curUserId", curUserId);
    boolean rightView = true;
    boolean rightAdd = true;
    boolean rightUpdate = true;
    boolean rightExport = true;
    boolean rightDelete = true;
    if ("taskListDone".equals(fromTabFlag) || "collectListDone".equals(fromTabFlag)) {
      rightView = true;
      rightAdd = false;
      rightUpdate = false;
      rightExport = true;
      rightDelete = false;
    } 
    if ("task".equals(fromFlag)) {
      rightAdd = false;
    } else if (po.getCollectTProcess() != null && !"".equals(po.getCollectTProcess()) && !"-1".equals(po.getCollectTProcess())) {
      rightView = true;
      rightAdd = true;
      rightUpdate = false;
      rightExport = true;
      rightDelete = false;
    } 
    if (po.getCollectTProcess() != null && po.getCollectTProcess().length() > 0 && 
      !"-1".equals(po.getCollectTProcess()))
      isRefFlow = true; 
    int index = 0;
    String tableStr = po.getCollectTTableName();
    request.setAttribute("mainTableName", tableStr);
    ModuleMenuService modbd = new ModuleMenuService();
    CustomDatabaseBD dbBD = new CustomDatabaseBD();
    String[][] queryFields = (String[][])null;
    if (po.getCollectQueryelements() != null && 
      po.getCollectQueryelements().length() > 0) {
      queryFields = modbd.getQueryField(po.getCollectQueryelements().toString(), domainId.toString());
    } else {
      queryFields = dbBD.getQueryField(po.getCollectTTable()
          .toString());
    } 
    request.setAttribute("queryFields", queryFields);
    String[][] paras = (String[][])null;
    String databaseType = SystemCommon.getDatabaseType();
    String valideDateCondition = "";
    String[] validDateFields = (String[])null;
    String[] validDateExp = (String[])null;
    if (queryFields != null && queryFields.length > 0) {
      paras = new String[queryFields.length][4];
      validDateFields = new String[queryFields.length];
      validDateExp = new String[queryFields.length];
      for (int i = 0; i < queryFields.length; i++) {
        paras[i][0] = queryFields[i][2];
        paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + 
            "_type");
        paras[i][3] = "<input type='hidden' id='" + paras[i][0] + 
          "_hid' name='" + paras[i][0] + 
          "_hid' value='" + 
          request.getParameter(paras[i][0]) + 
          "'/>";
        if ("403".equals(queryFields[i][3])) {
          validDateFields[0] = queryFields[i][2];
          validDateExp[0] = queryFields[i][4];
        } else if ("varchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%' ";
        } else if ("raidonumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("raidovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("selectnumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("selectvarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
            request.getParameter(queryFields[i][2]) + 
            "'";
        } else if ("checkbox".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          String[] values = request.getParameterValues(queryFields[i][2]);
          paras[i][2] = " ( ";
          for (int k = 0; k < values.length; k++)
            paras[i][2] = String.valueOf(paras[i][2]) + paras[i][0] + "='" + values[k] + "' or " + paras[i][0] + " like '%" + 
              values[k] + ",%' or "; 
          paras[i][2] = String.valueOf(paras[i][2].substring(0, 
                paras[i][2].lastIndexOf("or"))) + 
            ") ";
        } else if ("radiovarchar".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + 
            request.getParameter(queryFields[i][2]) + 
            "%'";
        } else if ("radionumber".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]) != null && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("date".equals(paras[i][1]) || 
          "time".equals(paras[i][1]) || 
          "datetime".equals(paras[i][1])) {
          if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date"))) {
            String end = "";
            String start = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_start");
            end = request.getParameter(String.valueOf(queryFields[i][2]) + 
                "_end");
            paras[i][3] = "<input type='hidden' id='" + 
              paras[i][0] + 
              "_start_hid' name='" + 
              paras[i][0] + 
              "_start_hid' value='" + start + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "_end_hid' value='" + end + "'/>" + 
              "<input type='hidden' id='" + 
              paras[i][0] + 
              "dateHid' name='" + 
              paras[i][0] + 
              "dateHid' value='" + 
              request.getParameter(String.valueOf(queryFields[i][2]) + 
                "date") + "' />";
            if ("date".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD')" + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD') and to_date('" + 
                  end + 
                  "','YYYY-MM-DD') ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime)" + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + 
                  "' as datetime) ";
              } 
            } else if ("time".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'HH:MM:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','HH:MI:SS') and to_date('" + 
                  end + "','HH:MI:SS') ";
              } else {
                paras[i][2] = String.valueOf(paras[i][0]) + " > '" + 
                  start + "' and " + paras[i][0] + 
                  "< '" + 
                  end + "'";
              } 
            } else if ("datetime".equals(paras[i][1])) {
              if ("oracle".equals(databaseType)) {
                paras[i][2] = "to_date(" + paras[i][0] + 
                  ", 'YYYY-MM-DD HH:MI:SS') " + 
                  " between to_date('" + 
                  start + 
                  "','YYYY-MM-DD HH:MI:SS') and to_date('" + 
                  end + 
                  "',YYYY-MM-DD HH:MI:SS) ";
              } else if ("mysql".equals(databaseType)) {
                paras[i][2] = String.valueOf(paras[i][0]) + 
                  " between '" + start + "' and '" + 
                  end + "'";
              } else {
                paras[i][2] = " cast(" + paras[i][0] + 
                  " as datetime) " + 
                  " between cast('" + 
                  start + 
                  "' as datetime) and cast('" + 
                  end + "' as datetime) ";
              } 
            } 
          } 
        } else if ("number".equals(paras[i][1]) && 
          request.getParameter(queryFields[i][2]).length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " = " + 
            request.getParameter(queryFields[i][2]);
        } else if ("float".equals(paras[i][1])) {
          String js = "";
          if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
            paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
            paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
            js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
          } 
          request.setAttribute("js", js);
        } 
      } 
    } 
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    if (queryFields == null || queryFields.length <= 0) {
      request.setAttribute("searchPart", null);
    } else {
      for (int i = 0; i < queryFields.length; i++)
        map.put(queryFields[i][2], 
            dbBD.getQueryFieldHTML(queryFields[i][0])); 
    } 
    StringBuffer sbf = new StringBuffer("");
    StringBuffer limtWhere = new StringBuffer("");
    if (queryFields != null && queryFields.length > 0) {
      int pt = 0;
      int colms = 2;
      int rows = queryFields.length / colms + 1;
      HashMap<Object, Object> dateMap = new HashMap<Object, Object>();
      for (int i = 0; i < rows; i++) {
        sbf.append("<tr>");
        if (i == 0)
          sbf.append("<td width=20 rowspan='10'/>"); 
        for (int k = 0;; k++);
        if (i == 0)
          sbf.append("<td width=16 rowspan='10'/>"); 
        sbf.append("</tr>");
        if (pt >= queryFields.length)
          break; 
        continue;
      } 
      if (dateMap != null && dateMap.size() > 0) {
        Iterator<String> it = dateMap.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next();
          sbf.append("<tr><td>");
          sbf.append(String.valueOf(key) + "：");
          sbf.append("</td><td colspan=\"3\">").append(dateMap.get(key)).append("</td></tr>");
        } 
      } 
      sbf.append(
          "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='right'>").append(
          "<input type=\"button\" class=\"btnButton4font\" onClick=\"searchAction();\" value=\"查询\"/>").append(
          "<input type=\"button\" class=\"btnButton2font\" onClick=\"clearSearch();searchAction();\" value=\"重置\" />").append(
          "<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>").append(
          "").append("</td></tr>");
      request.setAttribute("searchPart", sbf.toString());
    } 
    String[] actType = tokenArray(po.getCollectOperationType(), "|");
    String[] actName = tokenArray(po.getCollectRecordOperation(), "|");
    String[] actImage = tokenArray(po.getCollectOperationimg(), "|");
    String[] actComp = tokenArray(po.getCollectOperationcomponert(), "|");
    sbf.delete(0, sbf.length());
    sbf.append("<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"inlineBottomLine\"><tr><td align=\"right\">");
    String[] old = (new OACollectEJBBean()).getOldEmp((String)po.getCollectId());
    if (old[0].length() > 0 && old[0].contains("$" + curUserId + "$")) {
      if (rightUpdate && !rightAdd)
        sbf.append("<input type=\"button\" class=\"btnButton2font\" title=\"提醒\" onclick=\"fenfa('" + po.getCollectTPage() + 
            "','" + po.getCollectTTableName() + "');\" value=\"提醒\" />"); 
      if (rightAdd)
        if (po.getCollectTProcess() != null && po.getCollectTProcess().length() > 0 && 
          !"-1".equals(po.getCollectTProcess())) {
          isRefFlow = true;
          String action = tokenFlowAction(po.getCollectTProcess(), (new Integer(po.getCollectOpenStyle())).intValue());
          sbf.append("<input type=\"button\" class=\"btnButton2font\" title=\"进入[ ").append(
              getFlowTokenArray(po.getCollectTProcess(), "$")[2]).append("]流程\" onclick=\"goBatchFlow('").append(
              String.valueOf(action) + "&addFlag=batchAdd").append("')\" value=\"批量新增\" />");
          sbf.append("<input type=\"button\" class=\"btnButton2font\" title=\"进入[ ").append(
              getFlowTokenArray(po.getCollectTProcess(), "$")[2]).append("]流程\" onclick=\"goFlow('").append(
              action).append("')\" value=\"新增\" />");
          sbf.append("<input type=\"button\" class=\"btnButton2font\" onClick=\"importData();\" value=\"导入\" />");
        } else {
          sbf.append("<input type=\"button\" class=\"btnButton2font\" onClick=\"batchAdd('").append(
              po.getCollectTPage()).append("');\" title=\"新增当前主表记录\" value=\"批量新增\" />");
          sbf.append("<input type=\"button\" class=\"btnButton2font\" onClick=\"add('").append(
              po.getCollectTPage()).append("');\" title=\"新增当前主表记录\" value=\"新增\" />");
          sbf.append("<input type=\"button\" class=\"btnButton2font\" onClick=\"importData();\" value=\"导入\" />");
        }  
    } 
    if (actName != null && actName.length > 0)
      for (int i = 0; i < actType.length; i++) {
        if (!"1".equals(actType[i]))
          if (actType[i] != null && actType[i].length() > 0 && 
            "0".equals(actType[i])) {
            String css = "btnButton4font";
            if (actName[i].length() < 3) {
              css = "btnButton2font";
            } else if (actName[i].length() > 4) {
              css = "btnButton5font";
            } 
            sbf.append(
                "<input type=\"button\" class=\"").append(css).append(
                "\" onClick=\"goBatch('").append(
                actComp[i]).append("');\" value=\"").append(actName[i]).append(
                "\" />");
          }  
      }  
    if (old[0].length() > 0 && old[0].contains("$" + curUserId + "$"))
      if (rightDelete)
        sbf.append(
            "<input type='button' class=\"btnButton4font\" onClick=\"delBatch('").append(
            po.getCollectTTableName()).append(
            "');\" title=\"\"  value='删除' />");  
    if (old[0].length() > 0 && old[0].contains("$" + curUserId + "$"))
      if (rightExport)
        sbf.append(
            "<input type='button' class=\"btnButton2font\" onClick=\"exportData();\" value='导出' />");  
    String moduleProcessId = "";
    if (po.getCollectTProcess() != null && !"".equals(po.getCollectTProcess()))
      moduleProcessId = getFlowTokenArray(po.getCollectTProcess(), "$")[0]; 
    String tableId = po.getCollectTTable();
    MsManageBD msBD = new MsManageBD();
    String sql = "select po.statsTitle,po.statsId,po.isExtendUrl,po.extendUrl,po.statsIndexName,po.statsChart from  com.js.oa.userdb.statistics.po.JsfStatisticsManage po where po.statsStatus=1 and po.statsTableId=" + tableId;
    List<Object[]> msList = msBD.getListByYourSQL(sql);
    if (msList != null && msList.size() > 0)
      for (int k = 0; k < msList.size(); k++) {
        Object[] obj = msList.get(k);
        if ("1".equals(obj[2].toString())) {
          sbf.append("<input type='button'  class=\"btnButton4Font\" onClick=\"MM_openBrWindow('" + 
              obj[3] + 
              "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=600')\" " + 
              " value='" + obj[0] + "' />");
        } else {
          String action = "";
          if (obj[5] != null && "5".equals(obj[5].toString())) {
            action = "statisticsOnlyData";
          } else {
            action = "statisticsDataChart";
          } 
          sbf.append("<input type='button'  class=\"btnButton4Font\" onClick=\"doStatic('/jsoa/StatisticsAction.do?action=" + 
              action + "&statsId=" + obj[1] + 
              "&statsTableId=" + tableId + "&rightType=" + po.getCollectTitle() + "&menuId=" + collectId + 
              "&moduleProcessId=" + moduleProcessId);
          if (rightUpdate && !rightAdd) {
            sbf.append("&isCollect=2");
          } else {
            sbf.append("&isCollect=1");
          } 
          sbf.append("','" + obj[1] + "')\" " + 
              " value='" + obj[0] + "' />");
        } 
      }  
    sbf.append("<input type='button' id=\"hiddenSearch\"  name=\"hiddenSearch\" class=\"btnButton4Font\" onClick=\"hidserch()\" value='查询' />");
    sbf.append("<input type='button' id=\"hiddenBackToCollectTask\"  name=\"hiddenSearch\" class=\"btnButton4Font\" onClick=\"hiddenBackToCollectTask()\" value='返回' />");
    sbf.append("</td></tr></table>");
    request.setAttribute("middlButton", sbf.toString());
    String[][] listFields = (String[][])null;
    if (po.getCollectDisplayelements() != null && 
      po.getCollectDisplayelements().length() > 0) {
      listFields = modbd.getListField(po.getCollectDisplayelements().toString(), domainId.toString());
    } else {
      listFields = dbBD.getListField(po.getCollectTTable().toString());
    } 
    if (listFields == null)
      listFields = new String[0][0]; 
    String validateFlag = "";
    String[] compFields = new String[10];
    String tok = "";
    String sysDef = "";
    String mathMark = "";
    String logicMark = "";
    if (validDateFields != null && validDateFields[0] != null && 
      validDateExp != null && validDateExp[0] != null) {
      validateFlag = request.getParameter(validDateFields[0]);
      StringTokenizer st = new StringTokenizer(validDateExp[0], " ");
      int i = 0;
      while (st.hasMoreTokens()) {
        tok = st.nextToken();
        if (tok.indexOf("f") >= 0) {
          compFields[i] = tok;
        } else if (tok.indexOf("+") >= 0 || tok.indexOf("-") >= 0 || 
          tok.indexOf("*") >= 0 || tok.indexOf("/") >= 0) {
          mathMark = tok;
        } else if (tok.indexOf(">") >= 0 || tok.indexOf("<") >= 0 || 
          tok.indexOf("<>") >= 0 || tok.indexOf(">=") >= 0 || 
          tok.indexOf("<=") >= 0 || tok.indexOf("=") >= 0) {
          logicMark = tok;
        } else if (tok.indexOf("date") >= 0) {
          sysDef = tok;
        } 
        i++;
      } 
    } 
    if ("0".equals(validateFlag) && sysDef.length() > 0) {
      if ("oracle".equals(databaseType)) {
        valideDateCondition = " (sysdate" + mathMark + "to_date('" + 
          compFields[2] + 
          "', 'yyyy-mm-dd'))/365 " + 
          logicMark + compFields[4];
      } else if ("mysql".equals(databaseType)) {
        valideDateCondition = " DATEDIFF('" + 
          compFields[2] + "', CURDATE())" + 
          logicMark + 
          compFields[4];
      } else {
        valideDateCondition = " DATEDIFF(year, convert(datetime," + 
          compFields[2] + "), getdate())" + 
          logicMark + 
          compFields[4];
      } 
    } else if ("1".equals(validateFlag)) {
      sysDef.length();
    } 
    String defOrder = po.getCollectDefqueryorder();
    String defConstrain = po.getCollectDefquerycondition();
    sbf.delete(0, sbf.length());
    String[][] datas = (String[][])null;
    int pageSize = 15;
    if ("export".equals(request.getParameter("flag")))
      pageSize = 100000; 
    int offset = 0;
    if (request.getParameter("pager.offset") != null && 
      !"null".equals(request.getParameter("pager.offset")))
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    if (request.getAttribute("pager.offset") != null && 
      !"null".equals(request.getAttribute("pager.offset")))
      offset = Integer.parseInt(request.getAttribute("pager.offset")
          .toString()); 
    int currentPage = offset / pageSize + 1;
    String parameters = "";
    String viewScopeWhere = "";
    if (fromFlag != null && "collect".equals(fromFlag)) {
      viewScopeWhere = "(" + po.getCollectTTableName() + "_OWNER = " + request.getSession(true).getAttribute("userId").toString() + ")";
    } else {
      viewScopeWhere = "1=1";
    } 
    viewScopeWhere = viewScopeWhere.trim();
    if (viewScopeWhere.equals("1=1")) {
      viewScopeWhere = " ( 1=1 ";
    } else {
      viewScopeWhere = viewScopeWhere.substring(0, 
          viewScopeWhere.length() - 1);
    } 
    viewScopeWhere = String.valueOf(viewScopeWhere) + " or " + po.getCollectTTableName() + 
      "_ORG is null or " + po.getCollectTTableName() + 
      "_org='' )";
    String countSql = "";
    StringBuffer sumWhereBuf = new StringBuffer();
    if ("oracle".equals(databaseType)) {
      if (paras != null && paras.length > 0) {
        if (defConstrain == null || defConstrain.length() <= 0)
          parameters = String.valueOf(parameters) + " "; 
        for (int i = 0; i < paras.length; i++) {
          if (paras[i][2] != null && 
            paras[i][2].length() > 0)
            parameters = String.valueOf(parameters) + " and " + paras[i][2]; 
        } 
      } 
      sbf.append(" select distinct * from ( ");
      if (listFields != null && listFields.length > 0) {
        String selStr = String.valueOf(po.getCollectTTableName()) + "_ID";
        sbf.append("select " + po.getCollectTTableName() + "_ID, ");
        for (int i = 0; i < listFields.length; i++) {
          sbf.append(listFields[i][2]).append(", ");
          selStr = String.valueOf(selStr) + "," + listFields[i][2];
        } 
        sbf.delete(sbf.toString().lastIndexOf(", "), 
            sbf.toString().length());
        sbf.append(", rownum r ");
        sbf.append(" from (select distinct ").append(selStr).append(" from ").append(
            tableStr).append(" where 1=1 ")
          .append(" and " + po.getCollectTTableName() + "_relaByInde=" + collectId);
        sumWhereBuf.append(" 1=1 ");
        if (defConstrain != null && defConstrain.length() > 0) {
          if (defConstrain.indexOf("&") > 0) {
            String pre = defConstrain.substring(0, 
                defConstrain.indexOf("&"));
            String suf = defConstrain.substring(defConstrain
                .indexOf("&") + 4, defConstrain.length());
            String mid = defConstrain.substring(defConstrain
                .indexOf("&") + 1, defConstrain
                .indexOf("&") + 4);
            if ("uid".equals(mid)) {
              sbf.append(" and ").append(String.valueOf(pre) + ";").append(curUserId).append(
                  suf);
              sumWhereBuf.append(" and ").append(pre).append(";" + curUserId).append(
                  suf);
            } 
            if ("oid".equals(mid))
              sbf.append(" and ").append(pre).append("'").append(
                  session.getAttribute("userAccount")).append("'" + suf); 
            if ("sun".equals(mid)) {
              sbf.append(" and ").append(String.valueOf(pre) + ";").append(
                  session.getAttribute("userSimpleName")).append(
                  suf);
              sumWhereBuf.append(" and ").append(pre).append(";").append(
                  session.getAttribute("userSimpleName")).append(
                  suf);
            } 
          } else {
            sbf.append("  and ").append(defConstrain);
            sumWhereBuf.append("  and ").append(defConstrain);
          } 
          if (valideDateCondition.length() > 0) {
            sbf.append(" and ").append(valideDateCondition);
            sumWhereBuf.append(" and ").append(valideDateCondition);
          } 
          sbf.append(" ").append(parameters);
          sumWhereBuf.append(" ").append(parameters);
        } else {
          if (valideDateCondition.length() > 0) {
            sbf.append(" and ").append(valideDateCondition);
            sumWhereBuf.append(" and ").append(valideDateCondition);
          } 
          if (parameters.length() > 0) {
            sbf.append(" ").append(parameters);
            sumWhereBuf.append(" ").append(parameters);
          } 
        } 
      } 
      if (viewScopeWhere != null && viewScopeWhere.length() > 0) {
        sbf.append(" and ").append(viewScopeWhere);
        sumWhereBuf.append(" and ").append(viewScopeWhere);
      } 
      if (po.getCollectDefqueryorder() != null && 
        po.getCollectDefqueryorder().length() > 0) {
        sbf.append(" ").append(po.getCollectDefqueryorder()).append(", ").append(
            po.getCollectTTableName()).append("_ID  desc ");
      } else {
        sbf.append(" order by ").append(po.getCollectTTableName()).append(
            "_ID  desc ");
      } 
      sbf.append(" ) A ) B ");
      limtWhere.append(" where   r <= ").append(pageSize * currentPage).append(" and r >" + (pageSize * currentPage - pageSize));
    } else if ("mysql".equals(databaseType)) {
      if (paras != null && paras.length > 0) {
        if (defConstrain == null || defConstrain.length() <= 0)
          parameters = String.valueOf(parameters) + " "; 
        for (int i = 0; i < paras.length; i++) {
          if (paras[i][2] != null && 
            paras[i][2].length() > 0)
            parameters = String.valueOf(parameters) + " and " + paras[i][2]; 
        } 
      } 
      sbf.append(" select * from ( ");
      if (listFields != null && listFields.length > 0) {
        String selStr = String.valueOf(po.getCollectTTableName()) + "_ID";
        sbf.append("select ").append(po.getCollectTTableName()).append("_ID, ");
        for (int i = 0; i < listFields.length; i++) {
          sbf.append(listFields[i][2]).append(", ");
          selStr = String.valueOf(selStr) + "," + listFields[i][2];
        } 
        sbf.delete(sbf.toString().lastIndexOf(", "), 
            sbf.toString().length());
        sbf.append(" from (select distinct " + selStr + " from " + 
            tableStr + " where 1=1 ")
          .append(" and " + po.getCollectTTableName() + "_relaByInde=" + collectId);
        sumWhereBuf.append(" 1=1 ");
        if (defConstrain != null && defConstrain.length() > 0) {
          if (defConstrain.indexOf("&") > 0) {
            String pre = defConstrain.substring(0, 
                defConstrain.indexOf("&"));
            String suf = defConstrain.substring(defConstrain
                .indexOf("&") + 4, defConstrain.length());
            String mid = defConstrain.substring(defConstrain
                .indexOf("&") + 1, defConstrain
                .indexOf("&") + 4);
            if ("uid".equals(mid)) {
              sbf.append(" and ").append(pre).append(";").append(curUserId).append(
                  suf);
              sumWhereBuf.append(" and ").append(pre).append(";").append(curUserId).append(
                  suf);
            } 
            if ("oid".equals(mid)) {
              sbf.append(" and ").append(pre).append("'").append(
                  session.getAttribute("userAccount")).append("'").append(suf);
              sumWhereBuf.append(" and ").append(pre).append("'").append(
                  session.getAttribute("userAccount")).append("'").append(suf);
            } 
            if ("sun".equals(mid)) {
              sbf.append(" and ").append(pre).append(";").append(
                  session.getAttribute("userSimpleName")).append(
                  suf);
              sumWhereBuf.append(" and ").append(pre).append(";").append(
                  session.getAttribute("userSimpleName")).append(
                  suf);
            } 
          } else {
            sbf.append("  and ").append(defConstrain);
            sumWhereBuf.append("  and ").append(defConstrain);
          } 
          if (valideDateCondition.length() > 0) {
            sbf.append(" and ").append(valideDateCondition);
            sumWhereBuf.append(" and ").append(valideDateCondition);
          } 
          sbf.append(" ").append(parameters);
          sumWhereBuf.append(" ").append(parameters);
        } else {
          if (valideDateCondition.length() > 0) {
            sbf.append(" and ").append(valideDateCondition);
            sumWhereBuf.append(" and ").append(valideDateCondition);
          } 
          if (parameters.length() > 0) {
            sbf.append("  ").append(parameters);
            sumWhereBuf.append("  ").append(parameters);
          } 
        } 
      } 
      if (viewScopeWhere != null && viewScopeWhere.length() > 0) {
        sbf.append(" and ").append(viewScopeWhere);
        sumWhereBuf.append(" and ").append(viewScopeWhere);
      } 
      if (po.getCollectDefqueryorder() != null && 
        po.getCollectDefqueryorder().length() > 0) {
        sbf.append(" ").append(po.getCollectDefqueryorder()).append(", ").append(
            po.getCollectTTableName()).append("_ID  desc ");
      } else {
        sbf.append(" order by ").append(po.getCollectTTableName()).append(
            "_ID  desc ");
      } 
      sbf.append(" )A").append(
          " ) B ");
      limtWhere.append(" limit ").append(pageSize * (currentPage - 1)).append(", ").append(
          pageSize);
    } else {
      StringBuffer selectBuf = new StringBuffer("select top " + pageSize + 
          " " + po.getCollectTTableName() + "_id, ");
      StringBuffer assTab = new StringBuffer();
      StringBuffer assTabFor = new StringBuffer();
      if (listFields != null && listFields.length > 0)
        for (int i = 0; i < listFields.length; i++)
          selectBuf.append(listFields[i][2]).append(", ");  
      selectBuf.delete(selectBuf.lastIndexOf(","), selectBuf.length());
      StringBuffer fromBuf = new StringBuffer(" from " + 
          po.getCollectTTableName() + assTab.toString());
      StringBuffer whereBuf = new StringBuffer(" where " + 
          assTabFor.toString() + " 1=1 ");
      sumWhereBuf.append(" 1=1 ");
      if (viewScopeWhere != null && viewScopeWhere.length() > 0) {
        whereBuf.append(" and ").append(viewScopeWhere);
        sumWhereBuf.append(" and ").append(viewScopeWhere);
      } 
      if (defConstrain != null && defConstrain.length() > 0)
        if (defConstrain.indexOf("&") > 0) {
          String pre = defConstrain.substring(0, 
              defConstrain.indexOf("&"));
          String midStr = "";
          String suf = "";
          if (defConstrain.indexOf("&oid") > 0 && 
            defConstrain.indexOf("&uid") > 0) {
            midStr = defConstrain.substring(defConstrain
                .indexOf("&uid") + 4, 
                defConstrain.indexOf("&oid"));
            suf = defConstrain.substring(defConstrain
                .indexOf("&oid") + 4, defConstrain.length());
          } else if (defConstrain.indexOf("&uid") > 0) {
            suf = defConstrain.substring(defConstrain
                .indexOf("&uid") + 4, defConstrain.length());
          } else if (defConstrain.indexOf("&oid") > 0) {
            suf = defConstrain.substring(defConstrain
                .indexOf("&oid") + 4, defConstrain.length());
          } 
          String mid = "";
          if (defConstrain.indexOf("&uid") > 0) {
            mid = defConstrain.substring(defConstrain
                .indexOf("&uid") + 1, defConstrain
                .indexOf("&uid") + 4);
          } else {
            mid = defConstrain.substring(defConstrain
                .indexOf("&oid") + 1, defConstrain
                .indexOf("&oid") + 4);
          } 
          if ("uid".equals(mid)) {
            if (midStr.length() > 0) {
              mid = defConstrain.substring(defConstrain
                  .indexOf("&oid") + 1, 
                  defConstrain
                  .indexOf("&oid") + 4);
              if ("oid".equals(mid)) {
                whereBuf.append(" and ").append(pre).append("").append(curUserId).append(
                    midStr).append("'").append(
                    session.getAttribute("userAccount")).append("'").append(
                    suf);
                sumWhereBuf.append(" and ").append(pre).append("").append(
                    curUserId).append(midStr).append("'").append(
                    session.getAttribute("userAccount")).append("'").append(suf);
              } 
            } else {
              whereBuf.append(" and ").append(pre).append("").append(curUserId).append(
                  suf);
              sumWhereBuf.append(" and ").append(pre).append("").append(curUserId).append(
                  suf);
            } 
          } else if ("oid".equals(mid)) {
            whereBuf.append(" and ").append(pre).append("'").append(
                session.getAttribute("userAccount")).append("'").append(suf);
            sumWhereBuf.append(" and ").append(pre).append("'").append(
                session.getAttribute("userAccount")).append("'").append(suf);
          } 
        } else {
          whereBuf.append(" and ").append(defConstrain);
          sumWhereBuf.append(" and ").append(defConstrain);
        }  
      if (paras != null && paras.length > 0)
        for (int i = 0; i < paras.length; i++) {
          if (paras[i][2] != null && paras[i][2].length() > 0)
            parameters = String.valueOf(parameters) + " and " + paras[i][2] + " "; 
        }  
      whereBuf.append(parameters);
      sumWhereBuf.append(parameters);
      if (valideDateCondition.length() > 0) {
        whereBuf.append(" and ").append(valideDateCondition);
        sumWhereBuf.append(" and ").append(valideDateCondition);
      } 
      sbf.append(selectBuf).append(fromBuf).append(whereBuf);
      sbf.append(" and ").append(po.getCollectTTableName()).append(
          "_ID not in (select top ").append(pageSize * (currentPage - 1)).append(
          " ").append(po.getCollectTTableName()).append("_ID ").append(
          fromBuf.toString()).append(" ").append(whereBuf.toString()).append(" ").append(
          "order by ").append(po.getCollectTTableName()).append("_ID desc)");
      if (po.getCollectDefqueryorder() != null && 
        po.getCollectDefqueryorder().length() > 0) {
        sbf.append(" ").append(po.getCollectDefqueryorder()).append(", ").append(
            po.getCollectTTableName()).append("_ID  desc ");
      } else {
        sbf.append(" order by ").append(po.getCollectTTableName()).append(
            "_ID  desc ");
      } 
      countSql = sbf.toString();
      countSql = countSql.substring(0, countSql.indexOf("not in"));
      countSql = countSql.substring(countSql.indexOf("from"), 
          countSql.lastIndexOf("and"));
      countSql = "select count(*) " + countSql;
    } 
    String condition = "";
    if (po.getCollectDefquerycondition() != null && 
      po.getCollectDefquerycondition().length() > 0) {
      if (parameters.length() > 0) {
        condition = 
          String.valueOf(pickupArablil(po.getCollectDefquerycondition(), curUserId, "'" + session.getAttribute("userAccount").toString())) + "'" + 
          " " + parameters;
      } else {
        condition = pickupArablil(po.getCollectDefquerycondition(), curUserId, "'" + session.getAttribute("userAccount").toString() + "'");
      } 
    } else if (parameters.length() > 0) {
      condition = " 1=1 " + parameters;
    } else {
      condition = "";
    } 
    if (condition.indexOf("and") > 0 && 
      valideDateCondition.length() > 0) {
      condition = String.valueOf(condition) + " and " + valideDateCondition;
    } else {
      condition = String.valueOf(condition) + valideDateCondition;
    } 
    if (viewScopeWhere != null && viewScopeWhere.length() > 0)
      if (condition.indexOf("and") >= 0) {
        condition = String.valueOf(condition) + " and " + viewScopeWhere;
      } else if (condition.trim().length() > 0) {
        condition = String.valueOf(condition) + " and " + viewScopeWhere;
      } else if ("".equals(condition)) {
        condition = String.valueOf(condition) + " and " + viewScopeWhere;
      }  
    String querySql = sbf.toString();
    querySql = querySql.replace("replaceMe", (condition == null) ? "" : condition);
    String tempStr1 = querySql;
    String quertyCountSql = "";
    if (rightView || rightAdd || rightUpdate || rightExport || rightDelete)
      if (isRefFlow && "oracle".equals(databaseType)) {
        querySql = "select distinct C.*,D.WORKCURSTEP,D.WORKSTATUS from (" + tempStr1 + limtWhere + ") C left join JSF_WORK D on C." + po.getCollectTTableName() + "_ID=D.WORKRECORD_ID where D.WORKSTATUS = 0 or D.WORKSTATUS = 100 ";
        quertyCountSql = "select distinct C.*,D.WORKCURSTEP,D.WORKSTATUS from (" + tempStr1 + ") C left join JSF_WORK D on C." + po.getCollectTTableName() + "_ID=D.WORKRECORD_ID where D.WORKSTATUS = 0 or D.WORKSTATUS = 100 ";
        datas = modbd.getDefaultLoadDatas(querySql, listFields.length + 4, "");
      } else if (isRefFlow) {
        querySql = "select distinct C.*,D.WORKCURSTEP,D.WORKSTATUS from (" + querySql + ") C left join JSF_WORK D on C." + po.getCollectTTableName() + "_ID=D.WORKRECORD_ID where D.WORKSTATUS = 0 or D.WORKSTATUS = 100 ";
        quertyCountSql = querySql;
        querySql = String.valueOf(querySql) + limtWhere;
        datas = modbd.getDefaultLoadDatas(querySql, listFields.length + 3, "");
      } else {
        quertyCountSql = querySql;
        querySql = String.valueOf(querySql) + limtWhere;
        datas = modbd.getDefaultLoadDatas(querySql, listFields.length + 1, "");
      }  
    String recordCount = "0";
    sumWhereBuf.append(" and " + po.getCollectTTableName() + "_relaByInde=" + po.getCollectId());
    request.setAttribute("totFieldValue", dbBD.getTableTotFieldValue(po.getCollectTTable()
          .toString(), sumWhereBuf.toString()));
    if (rightView || rightAdd || rightUpdate || rightExport || rightDelete)
      recordCount = (new ModuleMenuEJBBean()).getCountByYourSql(quertyCountSql); 
    request.setAttribute("recordCount", 
        (recordCount != null) ? recordCount : "0");
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    if (paras != null && paras.length > 0) {
      String attachParas = "action,collectId,searchWithout,";
      for (int i = 0; i < paras.length; i++) {
        if ("date".equals(paras[i][1]) || "time".equals(paras[i][1]) || 
          "datetime".equals(paras[i][1]) || "float".equals(paras[i][1])) {
          if (attachParas.indexOf(String.valueOf(paras[i][0]) + "_start,") < 0)
            attachParas = String.valueOf(attachParas) + paras[i][0] + "_start,"; 
          if (attachParas.indexOf(String.valueOf(paras[i][0]) + "_end,") < 0)
            attachParas = String.valueOf(attachParas) + paras[i][0] + "_end,"; 
        } else {
          if (attachParas.indexOf(String.valueOf(paras[i][0]) + ",") < 0)
            attachParas = String.valueOf(attachParas) + paras[i][0] + ","; 
          if (attachParas.indexOf(String.valueOf(paras[i][0]) + "_type,") < 0)
            attachParas = String.valueOf(attachParas) + paras[i][0] + "_type,"; 
          if (attachParas.indexOf(String.valueOf(paras[i][0]) + "_hid,") < 0)
            attachParas = String.valueOf(attachParas) + paras[i][0] + "_hid,"; 
        } 
        if (attachParas.indexOf(String.valueOf(queryFields[i][2]) + "_type,") < 0)
          attachParas = String.valueOf(attachParas) + queryFields[i][2] + "_type,"; 
      } 
      request.setAttribute("pageParameters", 
          attachParas.substring(0, 
            attachParas.lastIndexOf(",")));
    } else {
      request.setAttribute("pageParameters", 
          "action,collectId,searchWithout");
    } 
    sbf.delete(0, sbf.length());
    if ("export".equals(request.getParameter("flag")) && 
      listFields != null && listFields.length > 0 && datas != null && 
      datas.length > 0) {
      request.setAttribute("datas", datas);
      request.setAttribute("listFields", listFields);
      return;
    } 
    sbf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\n");
    sbf.append("<tr class=\"listTableHead\" align=\"center\">\n<td width=\"40px\" class=\"listTableHead\" nowrap><input type=\"checkbox\" name=\"chkSelect\" id=\"chkSelect\" style=\"cursor:'pointer'\" onclick=\"selectAll(this)\"></td>\n");
    int isLink = -1;
    for (int j = 0; j < listFields.length; j++) {
      sbf.append("<td class=\"listTableHead\" width='" + 
          listFields[j][3] + "%'>" + listFields[j][1] + 
          "</td>\n");
      if (po.getCollectMantblSubtblname() != null && 
        po.getCollectMantblSubtblname().length() > 0 && 
        po.getCollectMantblSubtblname().equals(listFields[j][0]))
        isLink = j; 
    } 
    if (!"taskListDone".equals(fromTabFlag) && !"collectListDone".equals(fromTabFlag) && (
      !"collect".equals(fromFlag) || 
      po.getCollectTProcess() == null || "-1".equals(po.getCollectTProcess()) || "".equals(po.getCollectTProcess()))) {
      if (isRefFlow)
        sbf.append("<td class=\"listTableHead\" width=\"10%\" nowrap>办理状态</td>\n"); 
      sbf.append("<td class=\"listTableHead\" width=\"8%\" nowrap>操作</td></tr>\n");
    } 
    if (datas != null && datas.length > 0)
      if (rightView)
        for (int i = 0; i < datas.length; i++) {
          String listClass;
          thisPageCount = i;
          if (i % 2 != 0) {
            listClass = "listTableLine2";
          } else {
            listClass = "listTableLine1";
          } 
          sbf.append("<tr>");
          sbf.append("<td class='").append(listClass)
            .append("'><input type=\"checkbox\" style=\"cursor:'pointer'\" name=\"batchDel\" id=\"batchDel\" value='")
            .append(datas[i][0]).append("'>")
            .append("</td>\n");
          for (int k = 1; k <= listFields.length; k++) {
            if ("103,104,105".indexOf(listFields[k - 1][4].toString()) >= 0 && 
              datas[i][k] != null && 
              datas[i][k].length() > 0) {
              CustomFormBD formBd = new CustomFormBD();
              if (isLink < 0) {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append(formBd.getFieldShowValue(listFields[k - 1][2], listFields[k - 1][4], datas[i][k], listFields[k - 1][0], request))
                  .append("&nbsp;");
              } else if (isLink == k - 1) {
                sbf.append("<td class='").append(listClass)
                  .append("'><a href='#' onclick=\"edit('")
                  .append(datas[i][0]).append("', '")
                  .append(po.getCollectTPage())
                  .append("','1')\" >")
                  .append(formBd.getFieldShowValue(listFields[k - 1][2], listFields[k - 1][4], datas[i][k], listFields[k - 1][0], request))
                  .append("&nbsp;");
              } else {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append(formBd.getFieldShowValue(listFields[k - 1][2], listFields[k - 1][4], datas[i][k], listFields[k - 1][0], request))
                  .append("&nbsp;");
              } 
              continue;
            } 
            if ("210,211,212,214,".indexOf(listFields[k - 1][4].toString()) >= 0 && 
              datas[i][k] != null && 
              datas[i][k].length() > 0) {
              if (isLink < 0) {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append((datas[i][k] == null || 
                    datas[i][k].length() <= 0 || 
                    datas[i][k].indexOf(";") <= 0) ? 
                    "&nbsp;" : (
                    
                    String.valueOf(datas[i][k].substring(0, datas[i][k].indexOf(";"))) + 
                    "&nbsp;"));
              } else if (isLink == k - 1) {
                sbf.append("<td class='").append(listClass)
                  .append("'><a href='#' onclick=\"edit('").append(datas[i][0])
                  .append("', '").append(po.getCollectTPage()).append("','1')\" >")
                  .append((
                    datas[i][k] == null || 
                    datas[i][k].length() <= 0 || 
                    datas[i][k].indexOf(";") <= 0) ? 
                    "&nbsp;" : (
                    
                    String.valueOf(datas[i][k].substring(0, datas[i][k].indexOf(";"))) + 
                    "&nbsp;"));
              } else {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append((
                    datas[i][k] == null || 
                    datas[i][k].length() <= 0 || 
                    datas[i][k].indexOf(";") <= 0) ? 
                    "&nbsp;" : (
                    
                    String.valueOf(datas[i][k].substring(0, datas[i][k].indexOf(";"))) + 
                    "&nbsp;"));
              } 
              continue;
            } 
            if ("115".indexOf(listFields[k - 1][4]
                .toString()) >= 0)
              if (datas[i][k] != null && 
                datas[i][k].length() > 0) {
                String tempStr = (datas[i][k] != null && datas[i][k].length() > 0 && datas[i][k].indexOf(";") > 0) ? datas[i][k].split(";")[1].replaceAll(",,", "") : "";
                while (tempStr.length() > 0 && tempStr.endsWith(","))
                  tempStr = tempStr.substring(0, tempStr.length() - 1); 
                tempStr = String.valueOf(tempStr) + "&nbsp;";
                if (isLink < 0) {
                  sbf.append("<td class='").append(listClass).append("'>").append(tempStr);
                } else if (isLink == k - 1) {
                  sbf.append("<td class='").append(listClass).append("'><a href='#' onclick=\"edit('").append(
                      datas[i][0]).append("', '").append(
                      po.getCollectTPage()).append(
                      "','1')\" >").append(tempStr);
                } else {
                  sbf.append("<td class='").append(listClass).append(
                      "'>").append(tempStr);
                } 
                continue;
              }  
            if ("450".indexOf(listFields[k - 1][4].toString()) >= 0 && 
              datas[i][k] != null && 
              datas[i][k].length() > 0) {
              String tempValue = datas[i][k];
              if (tempValue.indexOf("@@$@@") >= 0)
                tempValue = tempValue.substring(tempValue.indexOf("@@$@@") + 5); 
              if (isLink < 0) {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append(tempValue);
              } else if (isLink == k - 1) {
                sbf.append("<td class='").append(listClass)
                  .append("'><a href='#' onclick=\"edit('").append(datas[i][0])
                  .append("', '").append(po.getCollectTPage()).append("','1')\" >")
                  .append(tempValue);
              } else {
                sbf.append("<td class='").append(listClass).append("'>")
                  .append(tempValue);
              } 
            } else if ("199".indexOf(listFields[k - 1][4].toString()) >= 0 && 
              datas[i][k] != null && 
              datas[i][k].length() > 0) {
              String value = datas[i][k];
              String title = "&nbsp;";
              String URL = "";
              if (!"".equals(value)) {
                if (value.indexOf("`~`~`") >= 0) {
                  String[] strings = value.split("`~`~`");
                  if (strings != null && strings.length >= 2) {
                    title = strings[0];
                    URL = strings[1];
                  } 
                } 
                if ("#".equals(URL))
                  URL = ""; 
              } 
              sbf.append("<td class='").append(listClass).append("'>\n<a href='#' style='cursor:pointer;' onclick=JSMainWinOpen('" + URL + "','','');>")
                .append(title).append("</a></td>");
            } else {
              String value = (datas[i][k] != null && datas[i][k].length() > 0 && datas[i][k].indexOf(";") > 0) ? 
                datas[i][k].substring(0, datas[i][k].indexOf(";")) : datas[i][k];
              try {
                if ("1000001".equals(listFields[k - 1][6])) {
                  value = value.toLowerCase();
                  String value1 = value.substring(0, 
                      value.indexOf('e'));
                  String value2 = value.substring(value.indexOf('e') + 1, value.length());
                  double value11 = Double.parseDouble(value1);
                  double value22 = Double.parseDouble(value2);
                  double laterValue = value11 * Math.pow(10.0D, value22);
                  value = DecimalFormat.getInstance().format(laterValue).replaceAll(",", "");
                } 
              } catch (Exception exception) {}
              value = String.valueOf(value) + "&nbsp;";
              if (isLink < 0) {
                sbf.append("<td class='").append(listClass).append("'>").append(value);
              } else if (isLink == k - 1) {
                sbf.append("<td class='").append(listClass)
                  .append("'>").append("<a href='#' onclick=\"edit('")
                  .append(datas[i][0]).append("', '").append(po.getCollectTPage())
                  .append("','1')\" >").append(value);
              } else {
                sbf.append("<td class='").append(listClass).append("'>").append(value);
              } 
            } 
            continue;
          } 
          if ("taskListDone".equals(fromTabFlag) || "collectListDone".equals(fromTabFlag) || (
            "collect".equals(fromFlag) && 
            po.getCollectTProcess() != null && !"-1".equals(po.getCollectTProcess()) && !"".equals(po.getCollectTProcess()))) {
            sbf.append("&nbsp;</tr>\n");
          } else {
            if (isRefFlow && "oracle".equals(databaseType)) {
              sbf.append("<td class='").append(listClass).append("'>").append("100".equals(datas[i][listFields.length + 3]) ? "办理完毕" : datas[i][listFields.length + 2]).append("&nbsp;</td>\n");
            } else if (isRefFlow) {
              sbf.append("<td class='").append(listClass).append("'>").append("100".equals(datas[i][listFields.length + 2]) ? "办理完毕" : datas[i][listFields.length + 1]).append("&nbsp;</td>\n");
            } 
            sbf.append("<td class='").append(listClass).append(
                " listTableLineLastTD' >");
            if (old[0].length() > 0 && old[0].contains("$" + curUserId + "$")) {
              if (rightUpdate)
                sbf.append("<img  style=\"cursor:pointer\" border=\"0\" src=\"images/modi.gif\" title=\"修改\" onclick=\"edit('").append(
                    datas[i][0]).append("', '").append(
                    po.getCollectTPage()).append(
                    "', '0')\" >"); 
              if (rightDelete)
                sbf.append("<img  style=\"cursor:pointer\" border=\"0\" src=\"images/del.gif\" title=\"删除\" onclick=\"del('").append(
                    po.getCollectTTableName()).append("','").append(
                    datas[i][0]).append("')\">"); 
            } 
            if (searchWithout == null || 
              "null".equals(searchWithout) || 
              searchWithout.length() <= 0) {
              if (po.getCollectTProcess() != null && 
                po.getCollectTProcess().length() > 0)
                "-1".equals(po.getCollectTProcess()); 
              if (actName != null && actName.length > 0)
                for (int x = 0; x < actType.length; x++) {
                  if (!"0".equals(actType[x]))
                    if (actType[x] != null && 
                      actType[x].length() > 0 && 
                      "1".equals(actType[x]))
                      sbf.append(
                          "<img  style=\"cursor:pointer\" border=\"0\" src=\"images/copy.gif\" title=\"").append(
                          actName[x]).append(
                          "\" onclick=\"goBatch('").append(
                          actComp[x]).append("', '").append(
                          datas[i][0]).append(
                          "')\" >");  
                }  
            } 
            sbf.append("&nbsp;</td></tr>\n");
          } 
        }   
    request.setAttribute("listPart", sbf.toString());
    request.setAttribute("thisPageCount", thisPageCount);
  }
  
  public String[][] sameArrStruct(String[][] qlFields) throws SQLException, SQLException, Exception {
    if (qlFields != null) {
      String[][] retArr = new String[qlFields.length][5];
      DbOpt opt = new DbOpt();
      String[][] showAndExp = new String[1][2];
      for (int i = 0; i < qlFields.length; i++) {
        retArr[i][2] = qlFields[i][2];
        showAndExp = opt.executeQueryToStrArr2(" select field_id, field_desname, field_show, field_value from tField where field_name='" + 
            qlFields[i][1] + "'");
        if (showAndExp != null && showAndExp.length > 0) {
          retArr[i][0] = showAndExp[0][0];
          retArr[i][1] = showAndExp[0][1];
          retArr[i][3] = showAndExp[0][2];
          retArr[i][4] = showAndExp[0][3];
        } 
      } 
      opt.close();
      return retArr;
    } 
    return null;
  }
  
  private String pickupArablil(String defConstrain, String curUserId, String orgId) {
    if (defConstrain != null && defConstrain.length() > 0) {
      String pickUp = "";
      if (defConstrain.indexOf("&") > 0) {
        String pre = defConstrain.substring(0, 
            defConstrain.indexOf("&"));
        String midStr = "";
        String suf = "";
        if (defConstrain.indexOf("&oid") > 0 && 
          defConstrain.indexOf("&uid") > 0) {
          midStr = defConstrain.substring(defConstrain
              .indexOf("&uid") + 4, 
              defConstrain.indexOf("&oid"));
          suf = defConstrain.substring(defConstrain
              .indexOf("&oid") + 4, 
              defConstrain.length());
        } else if (defConstrain.indexOf("&uid") > 0) {
          suf = defConstrain.substring(defConstrain
              .indexOf("&uid") + 4, 
              defConstrain.length());
        } else if (defConstrain.indexOf("&oid") > 0) {
          suf = defConstrain.substring(defConstrain
              .indexOf("&oid") + 4, 
              defConstrain.length());
        } 
        String mid = "";
        if (defConstrain.indexOf("&uid") > 0) {
          mid = defConstrain.substring(defConstrain
              .indexOf("&uid") + 1, 
              defConstrain
              .indexOf("&uid") + 4);
        } else {
          mid = defConstrain.substring(defConstrain
              .indexOf("&oid") + 1, 
              defConstrain
              .indexOf("&oid") + 4);
        } 
        if ("uid".equals(mid)) {
          if (midStr.length() > 0) {
            mid = defConstrain.substring(defConstrain
                .indexOf("&oid") + 1, 
                defConstrain
                .indexOf("&oid") + 4);
            if ("oid".equals(mid))
              pickUp = " " + pre + curUserId + midStr + 
                orgId + suf; 
          } else {
            pickUp = " " + pre + curUserId + suf;
          } 
        } else if ("oid".equals(mid)) {
          pickUp = " " + pre + orgId + suf;
        } 
      } else {
        pickUp = defConstrain;
      } 
      return pickUp;
    } 
    return defConstrain;
  }
  
  private HashMap setFormQueryValue(HashMap map, String[][] paras, HttpServletRequest request) {
    HashMap<Object, Object> mapp = new HashMap<Object, Object>();
    if (paras != null && map.size() > 0) {
      Iterator<String> keys = map.keySet().iterator();
      String controlStr = "";
      while (keys.hasNext()) {
        String keyName = keys.next();
        for (int i = 0; i < paras.length; i++) {
          if (keyName.equals(paras[i][0]) && paras[i][2] != null && 
            paras[i][2].length() > 0 && 
            "varchar".equals(paras[i][1])) {
            controlStr = (String)map.get(keyName);
            int typeLocation = controlStr.indexOf("text");
            if (typeLocation > 0) {
              String app = " value='" + 
                request.getParameter(paras[i][0]) + 
                "'";
              String preControlStr = controlStr.substring(0, 
                  typeLocation + 6);
              String sufControlStr = controlStr.substring(
                  typeLocation + 6, controlStr.length());
              mapp.put(paras[i][0], 
                  String.valueOf(preControlStr) + app + " " + 
                  sufControlStr);
            } else {
              mapp.put(paras[i][0], controlStr);
            } 
          } else {
            mapp.put(paras[i][0], controlStr);
          } 
        } 
      } 
    } 
    return mapp;
  }
  
  private void loadDefaultListPageDatas(String domainId, String collectId, HttpServletRequest request, String searchWithout, String curUserId) throws Exception {
    ModuleMenuService bd = new ModuleMenuService();
    request.setAttribute("searchPart", 
        bd.getSearchHtmlPart(domainId, collectId));
    request.setAttribute("collectId", collectId);
    ModuleMenuPO po = 
      bd.loadMenuConfig(domainId, collectId)
      .get(0);
    request.setAttribute("tblId", po.getMenuListTableName());
    String[][] joinFields = bd.getBizTableJoins(po.getMenuListTableMap()
        .toString(), 
        po.getMenuSubTableMap()
        .toString());
    String joinClause = "";
    if (joinFields != null && joinFields.length > 0) {
      int i;
      for (i = 0; i < joinFields.length; i++) {
        if (joinFields[i][0].equals(po.getMenuListTableMap()
            .toString()))
          joinClause = String.valueOf(joinClause) + po.getMenuListTableName() + "." + 
            joinFields[i][2]; 
      } 
      joinClause = String.valueOf(joinClause) + " = ";
      for (i = 0; i < joinFields.length; i++) {
        if (joinFields[i][0].equals(po.getMenuSubTableMap()
            .toString()))
          joinClause = String.valueOf(joinClause) + po.getMenuSubTableName() + "." + 
            joinFields[i][2]; 
      } 
    } 
    String[][] dispFields = bd.getQueryFields(domainId, 
        po.getMenuSearchBound()
        .toString());
    String searchSql = "SELECT " + dispFields[0][0] + ", " + 
      po.getMenuListTableName() + "." + 
      po.getMenuListTableName() + "_id " + 
      " FROM " + 
      po.getMenuListTableName();
    if (po.getMenuSubTableName() != null && 
      po.getMenuSubTableName().length() > 0)
      searchSql = String.valueOf(searchSql) + " JOIN " + po.getMenuSubTableName() + 
        " ON " + 
        joinClause; 
    searchSql = String.valueOf(searchSql) + " WHERE " + 
      setWhereCondition(po.getMenuDefQueryCondition(), 
        domainId.toString(), 
        po.getMenuSearchBound().toString(), 
        request) + 
      " " + ((
      po.getMenuDefQueryOrder() != null && 
      po.getMenuDefQueryOrder().length() > 0) ? 
      po.getMenuDefQueryOrder() : 
      " ");
    StringTokenizer tk = new StringTokenizer(dispFields[0][0], ",");
    String fName = "";
    int clomns = 0;
    while (tk.hasMoreTokens()) {
      StringTokenizer tkInner = new StringTokenizer(tk.nextToken(), 
          ".");
      tkInner.nextToken();
      fName = String.valueOf(fName) + "'" + tkInner.nextToken() + "',";
      clomns++;
    } 
    clomns++;
    String[][] fNames = bd.getFieldsTypes(fName.substring(0, 
          fName.lastIndexOf(",")));
    String[][] datas = bd.getDefaultLoadDatas(searchSql, clomns, "");
    String dispStr = "";
    if (fNames != null && fNames.length > 0 && datas != null && 
      datas.length > 0)
      dispStr = createDispHtml(searchWithout, fNames, datas, po, 
          collectId, 
          curUserId); 
    request.setAttribute("listPart", dispStr);
  }
  
  private String createDispHtml(String searchFlag, String[][] names, String[][] datas, ModuleMenuPO po, String collectId, String curUserId) {
    String dispStr = "<tr bgcolor=\"#FFFFFF\"><td colspan=\"" + (
      names.length + 5) + 
      "\" align=\"center\"><div align=\"right\"> ";
    if (po.getMenuScope().indexOf(curUserId) > 0 && 
      po.getMenuAccess().indexOf("c") > 0)
      dispStr = String.valueOf(dispStr) + "<a  href=\"#\" onclick=\"alert('新增当前主表记录 [未开发]');add();\" title=\"新增当前主表记录 [未开发]\">新增&nbsp;&nbsp;</a>"; 
    if (po.getMenuScope().indexOf(curUserId) > 0 && 
      po.getMenuAccess().indexOf("u") > 0)
      dispStr = String.valueOf(dispStr) + "<a href=\"#\" ><span  style=\"cursor:pointer\" onclick=\"alert('删除请表与从表记录 [完成]');delBatch();\" title=\"\">批量删除&nbsp;&nbsp;</span></a>"; 
    if ((searchFlag == null || "null".equals(searchFlag) || 
      searchFlag.length() <= 0) && 
      po.getMenuRelationRefFlow() != null) {
      String[] relationFlow = getFlowTokenArray(po
          .getMenuRelationRefFlow(), 
          "|");
      if (relationFlow != null && relationFlow.length > 0)
        for (int z = 0; z < relationFlow.length; z++) {
          if (relationFlow[z] != null && 
            !"-1".equals(relationFlow[z]))
            dispStr = String.valueOf(dispStr) + 
              "<a href=\"#\" ><span  style=\"cursor:pointer\" onclick=\"goFlow('" + 
              tokenFlowAction(relationFlow[z], 
                po.getMenuOpenStyle()) + 
              "');\"><font color='red'>" + 
              getFlowTokenArray(relationFlow[z], "$")[
                2] + 
              "</font>&nbsp;&nbsp;</span></a>"; 
        }  
    } 
    if ("1".equals(searchFlag))
      dispStr = String.valueOf(dispStr) + "<a href=\"#\" ><span  style=\"cursor:pointer\" onclick=\"alert('返回主列表画面');doAction('" + 
        collectId + 
        "');\" title=\"返回主列表画面\">返回&nbsp;&nbsp;</span></a>"; 
    dispStr = String.valueOf(dispStr) + "</div></td></tr>";
    if (po.getMenuScope().indexOf(curUserId) > 0 && 
      po.getMenuAccess().indexOf("l") > 0) {
      dispStr = String.valueOf(dispStr) + "<tr align=\"center\"><td width=\"10%\" height=\"25\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\"><input type=\"checkbox\" name=\"chkSelect\" id=\"chkSelect\" style=\"cursor:'hand'\" onclick=\"selectAll(this)\">序号</font></td>";
      if (searchFlag != null && "1".equals(searchFlag)) {
        for (int j = 0; j < names.length; j++)
          dispStr = String.valueOf(dispStr) + "<td width=\"20%\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">" + 
            names[j][2] + "</font></td>"; 
      } else {
        for (int j = 0; j < names.length; j++)
          dispStr = String.valueOf(dispStr) + "<td width=\"20%\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">" + 
            names[j][2] + "</font></td>"; 
      } 
      dispStr = String.valueOf(dispStr) + "<td width=\"8%\" background=\"/jsoa/images/gwbg2.jpg\"><font color=\"#FFFFFF\">操作</font></td></tr><tr bgcolor=\"CFD5E4\"><td height=\"6\" colspan=\"6\"></td></tr>";
      for (int i = 0; i < datas.length; i++) {
        if (i % 2 == 0) {
          dispStr = String.valueOf(dispStr) + "<tr bgcolor=\"#FFFFFF\">";
        } else {
          dispStr = String.valueOf(dispStr) + "<tr bgcolor=\"#E8ECED\">";
        } 
        dispStr = String.valueOf(dispStr) + "<td align='center'><input type=\"checkbox\" style=\"cursor:pointer\" name=\"batchDel\" id=\"batchDel\" value='" + ((
          searchFlag != null && "1".equals(searchFlag)) ? 
          datas[i][0] : datas[i][names.length]) + "' />" + (
          i + 1) + "</td>";
        for (int j = 0; j < names.length; j++)
          dispStr = String.valueOf(dispStr) + "<td  height=\"25\"  align=\"left\">" + ((
            datas[i][j] != null && 
            datas[i][j].length() > 0) ? 
            datas[i][j] : 
            "&nbsp;"); 
        dispStr = String.valueOf(dispStr) + "<td align=\"left\">";
        if (po.getMenuScope().indexOf(curUserId) > 0 && 
          po.getMenuAccess().indexOf("u") > 0) {
          dispStr = String.valueOf(dispStr) + "<img  style=\"cursor:pointer\" border=\"0\" src=\"images/modi.gif\" title=\"修改当前主表记录 [未开发]\" onclick=\"edit('" + ((
            searchFlag != null && "1".equals(searchFlag)) ? 
            datas[i][0] : datas[i][names.length]) + 
            "')\" >&nbsp;";
          dispStr = String.valueOf(dispStr) + "<img  style=\"cursor:pointer\" border=\"0\" src=\"images/del.gif\" title=\"删除主表及从表记录 [完成]\" onclick=\"del('" + 
            po.getMenuListTableName() + "','" + ((
            searchFlag != null && "1".equals(searchFlag)) ? 
            datas[i][0] : datas[i][names.length]) + 
            "')\" >&nbsp;";
        } 
        if (searchFlag == null || "null".equals(searchFlag) || 
          searchFlag.length() <= 0) {
          if (po.getMenuRefFlow() != null && 
            po.getMenuRefFlow().length() > 0 && 
            !"-1".equals(po.getMenuRefFlow())) {
            String action = tokenFlowAction(po.getMenuRefFlow(), 
                po.getMenuOpenStyle());
            dispStr = String.valueOf(dispStr) + 
              "<img  style=\"cursor:pointer\" border=\"0\" src=\"images/stop.gif\" title=\"进入[ " + 
              getFlowTokenArray(po.getMenuRefFlow(), "$")[
                2] + 
              "]流程\" onclick=\"goFlow('" + 
              action + "')\" >&nbsp;";
          } 
          if (po.getMenuSubTableName() != null && 
            po.getMenuSubTableName().length() > 0)
            dispStr = String.valueOf(dispStr) + "<img  style=\"cursor:pointer\" border=\"0\" src=\"images/downsort.gif\" title=\"进入从表" + 
              po.getMenuSubTableName() + 
              "显示列表 [完成]\" onclick=\"enterSub('" + 
              po.getMenuSubTableName() + "')\" >&nbsp;"; 
        } 
        dispStr = String.valueOf(dispStr) + "&nbsp;</td></tr>";
      } 
    } 
    return dispStr;
  }
  
  private String tokenFlowAction(String flowData, int style) {
    String[] values = getFlowTokenArray(flowData, "$");
    String action = "";
    if (values != null) {
      String path = 
        "/jsoa/JsFlowAddAction.do?action=add&processId=" + 
        values[0] + 
        "&tableId=" + values[1] + "&processName=" + values[2] + 
        "&processType=" + 
        values[3] + "&remindField=" + ((
        values[4] != null && values[4].length() > 0) ? values[4] : 
        "") + 
        "&moduleId=null";
      action = (style == 0) ? path : (
        "javascript:window.open('" + 
        path + 
        "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=580');");
    } 
    return action;
  }
  
  private String[] getFlowTokenArray(String flowStr, String mark) {
    String[] values = new String[5];
    int m = 0;
    StringTokenizer tk = new StringTokenizer(flowStr, mark);
    while (tk.hasMoreTokens() && m < 5) {
      values[m] = tk.nextToken();
      for (; m == 4 && tk.hasMoreTokens(); values[m] = String.valueOf(values[m]) + mark + tk.nextToken());
      m++;
    } 
    return values;
  }
  
  private String setWhereCondition(String defaultCondition, String domainId, String mneuSearchBound, HttpServletRequest request) throws Exception {
    CommPagesGeneration cpGen = new CommPagesGeneration();
    String[][] list = cpGen.getCustomerSearchFields(domainId, 
        mneuSearchBound);
    String[][] fieldMap = cpGen.tokenFieldsForSearch(cpGen.getSearchSql(), true);
    request.setAttribute("searchFields", fieldMap);
    String pickStr = " 1=1 ";
    String searchValue = "";
    for (int i = 0; i < fieldMap.length; i++) {
      String value = request.getParameter(fieldMap[i][1]);
      if (value != null && value.length() > 0 && !"null".equals(value))
        searchValue = String.valueOf(searchValue) + fieldMap[i][2] + " like '%" + value + 
          "%' and "; 
    } 
    if (defaultCondition == null || defaultCondition.length() <= 0 || 
      defaultCondition.indexOf("null") < 0) {
      defaultCondition = (searchValue.length() > 1) ? 
        searchValue.substring(0, 
          searchValue.lastIndexOf("and")) : 
        "";
    } else {
      defaultCondition = String.valueOf(defaultCondition) + ((searchValue.length() > 1) ? (" and " + 
        searchValue.substring(0, searchValue.lastIndexOf("and"))) : 
        "");
    } 
    return (defaultCondition != null && defaultCondition.length() > 0 && 
      defaultCondition.indexOf("null") < 0) ? (
      String.valueOf(pickStr) + " and " + defaultCondition) : pickStr;
  }
  
  private String[] tokenArray(String tokenSrc, String exp) {
    String[] ret = (String[])null;
    if (tokenSrc != null && tokenSrc.length() >= 2 && exp != null) {
      StringTokenizer st = new StringTokenizer(tokenSrc, exp);
      ret = new String[20];
      int z = 0;
      while (st.hasMoreTokens()) {
        ret[z] = st.nextToken();
        z++;
      } 
    } 
    return ret;
  }
  
  public boolean notExistsGroup(String scope, String userId, String domainId) {
    boolean retFlag = true;
    if (scope != null && scope.length() > 0) {
      List<E> list = (new ModuleMenuService()).getAllGroupsByUserId(userId, 
          domainId);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          if (scope.indexOf(list.get(0).toString()) > 0) {
            retFlag = false;
            break;
          } 
        }  
    } 
    return retFlag;
  }
  
  public boolean notExistsOrg(String scope, String orgIdString) {
    boolean retFlag = true;
    if (scope != null && scope.length() > 0 && orgIdString != null) {
      StringTokenizer st = new StringTokenizer(orgIdString, "$");
      String id = "";
      while (st.hasMoreTokens()) {
        id = st.nextToken();
        if (scope.indexOf(id) > 0) {
          retFlag = false;
          break;
        } 
      } 
    } 
    return retFlag;
  }
  
  private String[][] getListField(String tableId) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2("select a.field_id,a.field_desname,a.field_name,a.field_width,a.field_show,a.field_value,field_type from tfield a where  a.field_table=" + 
          tableId + 
          " order by field_SEQUENCE asc", 
          7);
    } catch (Exception e) {
      System.out.println(
          "^^^^^^^^^^^^^^^^^^EJB:getListField wrong^^^^^^^^^^^^^^^^^^^^^^^^");
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
  
  public static String[][] unite(String[][] os1, String[][] os2) {
    if (os1 == null)
      os1 = new String[0][0]; 
    if (os2 == null)
      os2 = new String[0][0]; 
    List<String[]> list = (List)new ArrayList<String>(Arrays.asList((String[])os1));
    list.addAll(Arrays.asList((String[])os2));
    return list.<String[]>toArray(os1);
  }
}
