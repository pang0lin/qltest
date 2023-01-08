package com.js.oa.jsflow.util;

import com.js.util.util.DataSourceBase;
import com.js.util.util.ZipOrUnZip;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ExportFlow {
  public String exportFlowInfo(HttpServletRequest request) {
    String fileInfo = "";
    try {
      HttpSession session = request.getSession();
      String flowIds = request.getParameter("flowIds");
      Calendar cal = Calendar.getInstance();
      String yearStr = String.valueOf(cal.get(1));
      String path = session.getServletContext().getRealPath("/upload/" + yearStr + "/flowFile/");
      File myFilePath = new File(path);
      if (myFilePath.exists()) {
        File flowFile = new File(String.valueOf(path) + "/flow/");
        if (!flowFile.exists())
          flowFile.mkdir(); 
      } else {
        myFilePath.mkdir();
        File flowFile = new File(String.valueOf(path) + "/flow/");
        flowFile.mkdir();
      } 
      String[] Ids = flowIds.split(",");
      String dirName = "";
      for (int i = 0; i < Ids.length; i++) {
        String pageString = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<pageInfos>";
        String flowString = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<flowInfos>";
        dirName = getFlowName(Ids[i]);
        pageString = String.valueOf(pageString) + exportPage(Ids[i], request, String.valueOf(path) + "/flow/" + dirName + "/");
        getFile(String.valueOf(pageString) + "\n</pageInfos>", String.valueOf(path) + "/flow/" + dirName + "/", "flowPage.xml");
        flowString = String.valueOf(flowString) + exportFlow(Ids[i], request, String.valueOf(path) + "/flow/" + dirName + "/");
        getFile(String.valueOf(flowString) + "\n</flowInfos>", String.valueOf(path) + "/flow/" + dirName + "/", "flowFlow.xml");
      } 
      ZipOrUnZip.ZIP(String.valueOf(path) + "/flow/", String.valueOf(path) + "/flowExport.zip");
      deletefile(String.valueOf(path) + "/flow/", 0);
      fileInfo = "flowExport.zip";
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return fileInfo;
  }
  
  public String exportPage(String flowId, HttpServletRequest request, String path) {
    String sql = "SELECT tp.* FROM tpage tp,jsf_workflowprocess jw WHERE jw.ACCESSDATABASEID=tp.page_id AND jw.WF_WORKFLOWPROCESS_ID=" + flowId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String tPageField = "PAGE_ID,PAGE_NAME,PAGE_DES,PAGE_LIST,PAGE_REF,PAGE_PATH,PAGE_FILENAME,PAGE_OWNER,PAGE_DATE,PAGE_SESSION,PAGE_TYPE,DOMAIN_ID,PAGE_CONTENT,PRINT_PAGE_ID,CREATEDEMP,CREATEDORG,JS_ONLOAD,JS_BEFORECOMMIT,FORMCLASSNAME,FORMCLASSSAVEMETHOD,FORMCLASSUPDATEMETHOD";
    String[] fields = tPageField.split(",");
    String printId = "";
    String showId = "";
    StringBuffer xmlString = new StringBuffer("");
    xmlString.append("\n\t<pageInfo>");
    try {
      base.begin();
      Tools tools = new Tools();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        showId = rs.getString("page_id");
        xmlString.append("\n\t\t<showPage>\n");
        for (int i = 0; i < fields.length; i++) {
          if ("PAGE_NAME".equalsIgnoreCase(fields[i]) || "PAGE_DES".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
            xmlString.append(rs.getString(fields[i]));
            xmlString.append("]]></" + fields[i].toLowerCase() + ">\n");
          } else if ("PRINT_PAGE_ID".equals(fields[i])) {
            printId = rs.getString(fields[i]);
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">" + rs.getString(fields[i]) + "</" + fields[i].toLowerCase() + ">\n");
          } else if ("PAGE_CONTENT".equals(fields[i])) {
            getFile(rs.getString(fields[i]), path, "showPage.txt");
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">showPage.txt</" + fields[i].toLowerCase() + "><!-- 显示表单生成txt文件 -->\n");
          } else if ("JS_ONLOAD".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
            xmlString.append(tools.escapFlowSymbol(rs.getString(fields[i])));
            xmlString.append("]]></" + fields[i].toLowerCase() + "><!-- 表单加载时，执行的js代码 -->\n");
          } else if ("JS_BEFORECOMMIT".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">" + tools.escapFlowSymbol(rs.getString(fields[i])) + "</" + fields[i].toLowerCase() + ">\n");
          } else {
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">" + rs.getString(fields[i]) + "</" + fields[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\t\t</showPage>\n");
      } 
      rs.close();
      if (printId != null && !printId.equals("null") && !printId.equals("")) {
        sql = "SELECT * FROM tpage WHERE page_id=" + printId;
        rs = base.executeQuery(sql);
        if (rs.next()) {
          xmlString.append("\t\t<printPage>\n");
          for (int i = 0; i < fields.length; i++) {
            if ("PAGE_NAME".equalsIgnoreCase(fields[i]) || "PAGE_DES".equalsIgnoreCase(fields[i])) {
              xmlString.append("\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
              xmlString.append(rs.getString(fields[i]));
              xmlString.append("]]></" + fields[i].toLowerCase() + ">\n");
            } else if ("PAGE_CONTENT".equals(fields[i])) {
              getFile(rs.getString(fields[i]), path, "printPage.txt");
              xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">printPage.txt</" + fields[i].toLowerCase() + "><!-- 打印表单生成txt文件 -->\n");
            } else if ("JS_ONLOAD".equalsIgnoreCase(fields[i])) {
              xmlString.append("\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
              xmlString.append(rs.getString(fields[i]));
              xmlString.append("]]></" + fields[i].toLowerCase() + "><!-- 表单加载时，执行的js代码 -->\n");
            } else {
              xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">" + rs.getString(fields[i]) + "</" + fields[i].toLowerCase() + ">\n");
            } 
          } 
          xmlString.append("\t\t</printPage>");
        } 
      } 
      rs.close();
      String areaFields = "area_id,area_areatype,area_areatmc,area_page,area_name,area_table,area_show,area_sql,area_action,area_altercolor,area_pagecount,area_sfxs,area_ref,area_sqlevent,area_des,area_html,area_code,area_columns,AREATYPE_ID,PAGE_ID";
      String areaSql = "SELECT * FROM tarea WHERE (page_id=" + showId + " or page_id=" + printId + ") and area_name='form1' order by area_name";
      rs = base.executeQuery(areaSql);
      String[] areafield = areaFields.split(",");
      xmlString.append("\n\t\t<areaInfos>\n");
      while (rs.next()) {
        xmlString.append("\n\t\t\t<areaInfo>\n");
        for (int i = 0; i < areafield.length; i++) {
          if (",area_html,area_code,area_areatype,".indexOf("," + areafield[i] + ",") >= 0) {
            xmlString.append("\t\t\t\t<" + areafield[i].toLowerCase() + "><![CDATA[");
            xmlString.append("null");
            xmlString.append("]]></" + areafield[i].toLowerCase() + ">\n");
          } else {
            xmlString.append("\t\t\t\t<" + areafield[i].toLowerCase() + ">" + rs.getString(areafield[i]) + "</" + areafield[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\n\t\t\t</areaInfo>");
      } 
      xmlString.append("\n\t\t</areaInfos>\n");
      xmlString.append("\n\t</pageInfo>");
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return xmlString.toString();
  }
  
  public String exportFlow(String flowId, HttpServletRequest request, String path) {
    StringBuffer xmlString = new StringBuffer("");
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String field = "wf_workflowprocess_id,accessdatabaseid,workflowprocessname,processcreateddate,processdescription,createusername,createdorg,createdemp,userscope,useorg,usegroup,useperson,dossierfileseescope,dossierfileseeorg,dossierfileseegroup,dossierfileseeperson,wf_package_id,processtype,remindfield,ispublish,isdossier,cancancel,formclassname,formclassmethod,formclasscompmethod,domain_id,printfileseescope,printfileseeorg,printfileseegroup,printfileseeperson,formtype,startjsp,optjsp,dossierfileoperscope,dossierfileoperorg,dossierfileopergroup,dossierfileoperperson,processadminscope,processadminorg,processadmingroup,processadminperson,ordercode,process_status,ownername,ownerid,ownerorgid,lastupdatetime,processusetime,main_formid";
    String sql = "SELECT * FROM JSF_WORKFLOWPROCESS WHERE WF_WORKFLOWPROCESS_ID=" + flowId;
    try {
      base.begin();
      Tools tools = new Tools();
      xmlString.append("\n\t<flowInfo>\n");
      rs = base.executeQuery(sql);
      String[] fields = field.split(",");
      if (rs.next()) {
        xmlString.append("\t\t<flowProcess>\n");
        for (int i = 0; i < fields.length; i++) {
          if ("processdescription".equals(fields[i])) {
            getFile(rs.getString(fields[i]), path, "process.txt");
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">process.txt</" + fields[i].toLowerCase() + "><!-- 流程的说明生成txt文件 -->\n");
          } else {
            xmlString.append("\t\t\t<" + fields[i].toLowerCase() + ">" + tools.escapFlowSymbol(rs.getString(fields[i])) + "</" + fields[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\t\t</flowProcess>\n");
      } 
      rs.close();
      String activityIds = "-1";
      sql = "SELECT * FROM jsf_activity WHERE WF_WORKFLOWPROCESS_ID=" + flowId;
      String activity = "wf_activity_id,activityname,activitydescription,activitytype,allowstandfor,allowcancel,allowtransition,participanttype,participantuser,participantgroup,participantusername,participantuserfield,presstype,deadlinetime,pressmotiontime,activitydocumentation,activityicon,activitybeginend,wf_workflowprocess_id,transacttype,acticommtype,acticommfield,needpassround,passroundusertype,passrounduser,passroundusergroup,passroundusername,passrounduserfield,passroundcommfield,participantrole,passroundrole,activityclass,activitysubproc,subproctype,formclassmethod,untreadmethod,participantgivenorgname,participantgivenorg,passroundgivenorgname,passroundgivenorg,commentrange,commentrangename,domain_id,form_id,printnum,operbutton,acticommfieldtype,passroundcommfieldtype,printfileseescope,printfileseeorg,printfileseegroup,printfileseeperson,allowsmsremind,trantype,trancustomextent,trancustomextentid,pressdealtype,tranreadtype,tranreadcustomextent,tranreadcustomextentid,extendmaintable,positionleft,positiontop,nicknum,main_formid,exe_script";
      rs = base.executeQuery(sql);
      fields = activity.split(",");
      xmlString.append("\t\t<flowActivitys><!-- 节点信息 -->\n");
      while (rs.next()) {
        xmlString.append("\t\t\t<flowActivity>\n");
        activityIds = String.valueOf(activityIds) + "," + rs.getString("wf_activity_id");
        for (int i = 0; i < fields.length; i++) {
          if ("exe_script".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[" + tools.escapFlowSymbol(rs.getString(fields[i])) + 
                "]]></" + fields[i].toLowerCase() + "><!-- js代码 -->\n");
          } else if ("printnum".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + ">0</" + fields[i].toLowerCase() + ">\n");
          } else {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + ">" + tools.escapFlowSymbol(rs.getString(fields[i])) + "</" + fields[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\t\t\t</flowActivity>\n");
      } 
      xmlString.append("\t\t</flowActivitys>\n");
      rs.close();
      String control = "wf_readwritecontrol_id,controltype,controlfield,wf_activity_id,domain_id";
      sql = "SELECT * FROM JSF_READWRITECONTROL WHERE WF_ACTIVITY_ID IN (" + activityIds + ")";
      rs = base.executeQuery(sql);
      fields = control.split(",");
      xmlString.append("\t\t<flowControls><!-- 节点的读写控制 -->\n");
      while (rs.next()) {
        xmlString.append("\t\t\t<flowControl>\n");
        for (int i = 0; i < fields.length; i++)
          xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + ">" + tools.escapFlowSymbol(rs.getString(fields[i])) + "</" + fields[i].toLowerCase() + ">\n"); 
        xmlString.append("\t\t\t</flowControl>\n");
      } 
      xmlString.append("\t\t</flowControls>\n");
      rs.close();
      sql = "SELECT * FROM jsf_transition WHERE TRANSITIONFROM IN (" + activityIds + ")";
      String transitionId = "-1";
      String transition = "wf_transition_id,transitionfrom,transitionname,transitionto,transitiondescription,domain_id,expression,defaultactivity,linetype,nicknum";
      rs = base.executeQuery(sql);
      fields = transition.split(",");
      xmlString.append("\t\t<flowTransitions><!-- 节点的前后关系 -->\n");
      while (rs.next()) {
        xmlString.append("\t\t\t<flowTransition>\n");
        transitionId = String.valueOf(transitionId) + "," + rs.getString("wf_transition_id");
        for (int i = 0; i < fields.length; i++) {
          if ("transitiondescription".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
            xmlString.append(tools.escapFlowSymbol(rs.getString(fields[i])));
            xmlString.append("]]></" + fields[i].toLowerCase() + "><!-- 操作符会导致xml格式错误 -->\n");
          } else if ("expression".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[");
            xmlString.append(tools.escapFlowSymbol(rs.getString(fields[i])));
            xmlString.append("]]></" + fields[i].toLowerCase() + "><!-- 操作符会导致xml格式错误 -->\n");
          } else {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + ">" + tools.escapFlowSymbol(rs.getString(fields[i])) + "</" + fields[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\t\t\t</flowTransition>\n");
      } 
      xmlString.append("\t\t</flowTransitions>\n");
      rs.close();
      sql = "SELECT * FROM jsf_transitionrestriction WHERE wf_transition_id in (" + transitionId + ")";
      String restriction = "wf_transitionrestriction_id,conditionfield,comparevalue,relation,wf_transition_id,domain_id";
      rs = base.executeQuery(sql);
      fields = restriction.split(",");
      xmlString.append("\t\t<flowRestrictions><!-- 限定条件，节点间流转的条件 -->\n");
      while (rs.next()) {
        xmlString.append("\t\t\t<flowRestriction>\n");
        for (int i = 0; i < fields.length; i++) {
          if ("relation".equalsIgnoreCase(fields[i])) {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + "><![CDATA[" + tools.escapFlowSymbol(rs.getString(fields[i])) + 
                "]]></" + fields[i].toLowerCase() + "><!-- 操作符会导致xml格式错误 -->\n");
          } else {
            xmlString.append("\t\t\t\t<" + fields[i].toLowerCase() + ">" + rs.getString(fields[i]) + "</" + fields[i].toLowerCase() + ">\n");
          } 
        } 
        xmlString.append("\t\t\t</flowRestriction>\n");
      } 
      xmlString.append("\t\t</flowRestrictions>\n");
      rs.close();
      xmlString.append("\t</flowInfo>");
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return xmlString.toString();
  }
  
  public void getFile(String xmlString, String path, String fileName) {
    File myFilePath = new File(path);
    try {
      if (!myFilePath.isDirectory())
        myFilePath.mkdir(); 
      File fp = new File(String.valueOf(path) + "/" + fileName);
      PrintWriter pfp = new PrintWriter(fp);
      pfp.print(xmlString);
      pfp.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean deletefile(String delpath, int flag) {
    boolean isDelete = false;
    try {
      File file = new File(delpath);
      if (!file.exists()) {
        System.out.println("路径" + delpath + "不存在！");
      } else {
        if (!file.isDirectory()) {
          System.out.println("路径" + delpath + "不是文件夹！");
          file.delete();
        } else if (file.isDirectory()) {
          String[] filelist = file.list();
          for (int i = 0; i < filelist.length; i++) {
            File delfile = new File(String.valueOf(delpath) + "/" + filelist[i]);
            if (!delfile.isDirectory()) {
              delfile.delete();
            } else if (delfile.isDirectory()) {
              deletefile(String.valueOf(delpath) + "/" + filelist[i], 1);
            } 
          } 
          if (flag > 0)
            file.delete(); 
        } 
        isDelete = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return isDelete;
  }
  
  public String getFlowName(String flowId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String flowName = "";
    try {
      base.begin();
      String sql = "SELECT WORKFLOWPROCESSNAME FROM JSF_WORKFLOWPROCESS WHERE WF_WORKFLOWPROCESS_ID=" + flowId;
      rs = base.executeQuery(sql);
      if (rs.next())
        flowName = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return flowName;
  }
}
