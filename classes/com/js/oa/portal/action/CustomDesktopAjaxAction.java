package com.js.oa.portal.action;

import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.oasysremind.bean.JsonData;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class CustomDesktopAjaxAction extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(true);
    resp.setContentType("text/xml; charset=GBK");
    String type = (req.getParameter("type") == null) ? "" : req.getParameter("type");
    PrintWriter out = resp.getWriter();
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    List<Object[]> list = new ArrayList();
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    if (type.equals("matterCategoryList")) {
      list = customDesktopBD.listMatterCategoryList();
      Element rss = new Element("rss");
      rss.setAttribute(new Attribute("version", "2.00"));
      Element channel = new Element("channel");
      rss.addContent((Content)channel);
      Element xietong = new Element("item");
      xietong.addContent((Content)(new Element("id")).addContent("11"));
      xietong.addContent((Content)(new Element("category")).addContent("协同工作"));
      channel.addContent((Content)xietong);
      Document myDocument = new Document(rss);
      if (list == null || list.size() == 0) {
        Element fisrt = new Element("item");
        fisrt.addContent((Content)(new Element("id")).addContent("<font color='#333'>未查询到数据！</font>"));
        fisrt.addContent((Content)(new Element("category")).addContent("<font color='#333'>未查询到数据！</font>"));
        channel.addContent((Content)fisrt);
      } else {
        for (int i = 0; i < list.size(); i++) {
          Object[] ob = list.get(i);
          Element fisrt = new Element("item");
          fisrt.addContent((Content)(new Element("id")).addContent((ob[0] == null) ? "" : ob[0].toString()));
          fisrt.addContent((Content)(new Element("category")).addContent((ob[1] == null) ? "" : ob[1].toString()));
          channel.addContent((Content)fisrt);
        } 
      } 
      if (SystemCommon.getModules().indexOf(",doc,") >= 0) {
        Element fisrt = new Element("item");
        fisrt.addContent((Content)(new Element("id")).addContent("-100"));
        fisrt.addContent((Content)(new Element("category")).addContent("全部发文流程"));
        channel.addContent((Content)fisrt);
        fisrt = new Element("item");
        fisrt.addContent((Content)(new Element("id")).addContent("-101"));
        fisrt.addContent((Content)(new Element("category")).addContent("全部收文流程"));
        channel.addContent((Content)fisrt);
        fisrt = new Element("item");
        fisrt.addContent((Content)(new Element("id")).addContent("-102"));
        fisrt.addContent((Content)(new Element("category")).addContent("全部收发文流程"));
        channel.addContent((Content)fisrt);
      } 
      Format format = Format.getCompactFormat();
      format.setEncoding("GBK");
      format.setIndent("    ");
      XMLOutputter outputter = new XMLOutputter(format);
      try {
        outputter.output(myDocument, out);
      } catch (IOException ex) {
        ex.printStackTrace();
      } 
    } 
    if (type.equals("checkMessageSend")) {
      String reString = "0";
      try {
        String domainId = session.getAttribute("domainId").toString();
        SysSetupReader sysRed = SysSetupReader.getInstance();
        String options = sysRed.getSystemOption(domainId);
        if (options.charAt(8) == '1') {
          OrganizationBD orgBD = new OrganizationBD();
          String orgId = orgBD.getOrgIdByUserID(userId);
          String sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=2 and po.msManage.msId=2";
          MsManageBD msBD = new MsManageBD();
          List<Object[]> msList = msBD.getListByYourSQL(sql);
          if (msList != null && msList.size() != 0)
            for (int i = 0; i < msList.size(); i++) {
              Object[] obj = msList.get(i);
              sql = "select vo.orgId,vo.orgIdString from com.js.system.vo.organizationmanager.OrganizationVO vo where vo.orgIdString like '%$" + 
                orgId + "$%' and orgIdString like '%$" + obj[1] + "$%'";
              List orgIdStingList = msBD.getListByYourSQL(sql);
              if (orgIdStingList != null && orgIdStingList.size() > 0)
                reString = "1"; 
            }  
          if (!"1".equals(reString)) {
            GroupBD groupBD = new GroupBD();
            List<E> userGroupList = groupBD.searchByUserid(Long.valueOf(userId).longValue());
            if (userGroupList != null && userGroupList.size() != 0) {
              String userGroupId = userGroupList.get(0).toString();
              sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=3 and po.msManage.msId=2";
              msList = msBD.getListByYourSQL(sql);
              if (msList != null && msList.size() != 0)
                for (int i = 0; i < msList.size(); i++) {
                  Object[] obj = msList.get(i);
                  if (userGroupId.equals(obj[1]))
                    reString = "1"; 
                }  
            } 
          } 
          if (!"1".equals(reString)) {
            sql = "select po.msInfoId,po.grantId from com.js.oa.message.po.MsManageInfoPO po where po.grantType=1 and po.msManage.msId=2";
            msList = msBD.getListByYourSQL(sql);
            if (msList != null && msList.size() != 0)
              for (int i = 0; i < msList.size(); i++) {
                Object[] obj = msList.get(i);
                if (userId.equals(obj[1]))
                  reString = "1"; 
              }  
          } 
        } else {
          reString = "1";
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reString);
      out.close();
    } 
    if (type.equals("checkAuditManagerExist")) {
      String reStr = "yes";
      String reEmpIdStr = "用户";
      try {
        MsManageBD msBD = new MsManageBD();
        UserBD userBD = new UserBD();
        String empIds = req.getParameter("empIds");
        String sql = "";
        empIds = empIds.substring(1, empIds.length() - 1);
        String[] empIdsArr = empIds.split("\\$\\$");
        if (empIdsArr != null)
          for (int i = 0; i < empIdsArr.length; i++) {
            sql = "select po.managerId,po.empId from com.js.oa.audit.po.AuditManager po where po.empId=" + Long.parseLong(empIdsArr[i]);
            List reList = msBD.getListByYourSQL(sql);
            if (reList != null && reList.size() != 0) {
              reEmpIdStr = String.valueOf(reEmpIdStr) + userBD.getUserNameById(empIdsArr[i]);
              reStr = "no";
            } 
          }  
        if ("no".equals(reStr))
          reStr = String.valueOf(reEmpIdStr.substring(0, reEmpIdStr.length() - 1)) + "已存在"; 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
    } 
    if (type.equals("getRemindIndexType")) {
      String reStr = "";
      try {
        String remindIndex = req.getParameter("remindIndex");
        String sql = "select po.fieldId,po.show.showId from com.js.oa.jsflow.po.TFieldPO po where po.fieldName='" + remindIndex + "'";
        MsManageBD msBD = new MsManageBD();
        List<Object[]> reList = msBD.getListByYourSQL(sql);
        if (reList != null && reList.size() > 0) {
          Object[] obj = reList.get(0);
          reStr = obj[1].toString();
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      out.print(reStr);
      out.close();
    } 
    if (type.equals("getTfieldIndexByTtable"))
      try {
        List<JsonData> listJson = new ArrayList<JsonData>();
        JsonData jsonDate = null;
        if (req.getParameter("tableId") != null) {
          long code = Long.parseLong(req.getParameter("tableId"));
          MsManageBD msBD = new MsManageBD();
          List<Object[]> tmplist = null;
          String sql = "select po.tableId,po.tableName from TTablePO po where po.tableId=" + code;
          String tableName = "";
          tmplist = msBD.getListByYourSQL(sql);
          if (tmplist != null && tmplist.size() != 0)
            for (int i = 0; i < tmplist.size(); i++) {
              Object[] obj = tmplist.get(i);
              tableName = obj[1].toString();
            }  
          sql = "select po.fieldId,po.fieldName,po.fieldDesName from TFieldPO po where po.table.tableId =" + code;
          tmplist = msBD.getListByYourSQL(sql);
          if (tmplist != null && tmplist.size() != 0)
            for (int i = 0; i < tmplist.size(); i++) {
              jsonDate = new JsonData();
              Object[] obj = tmplist.get(i);
              jsonDate.setId(obj[1].toString());
              jsonDate.setName(obj[2].toString());
              jsonDate.setTableName(tableName);
              jsonDate.setOther(obj[0].toString());
              listJson.add(jsonDate);
            }  
          JSONArray jsonArray = JSONArray.fromObject(listJson);
          out.print(jsonArray.toString());
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        out.flush();
        out.close();
      }  
    if (type.equals("getTfieldCounByTtable")) {
      MsManageBD msBD = new MsManageBD();
      String tableString = "";
      try {
        long code = Long.parseLong(req.getParameter("tableId"));
        tableString = msBD.getListTtable(String.valueOf(code));
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      List<JsonData> listJson = new ArrayList<JsonData>();
      JsonData jsonDate = null;
      List<Object[]> listCoun = null;
      try {
        long code = Long.parseLong(req.getParameter("tableId"));
        listCoun = msBD.getListType(String.valueOf(code));
        if (listCoun != null && listCoun.size() != 0)
          for (int i = 0; i < listCoun.size(); i++) {
            jsonDate = new JsonData();
            Object[] obj = listCoun.get(i);
            jsonDate.setOther(obj[0].toString());
            jsonDate.setId(obj[2].toString());
            jsonDate.setName(obj[3].toString());
            listJson.add(jsonDate);
          }  
        JSONArray jsonArray = JSONArray.fromObject(listJson);
        out.print(jsonArray.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if (type.equals("getRemindSourceByType"))
      try {
        String sourceType = null;
        sourceType = req.getParameter("sourceType");
        List<JsonData> listJson = new ArrayList<JsonData>();
        JsonData jsonDate = null;
        String sql = null;
        if ("1".equals(sourceType)) {
          sql = "SELECT aaa.wfWorkFlowProcessId,aaa.workFlowProcessName,ttable.tableId FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1'";
          WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
          String where = processEjbBean.getProcWhereSql(userId, orgIdString, "1");
          sql = String.valueOf(sql) + " and ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
          MsManageBD msBD = new MsManageBD();
          List<Object[]> ttableList = msBD.getListByYourSQL(sql);
          if (ttableList != null && ttableList.size() != 0)
            for (int i = 0; i < ttableList.size(); i++) {
              jsonDate = new JsonData();
              Object[] obj = ttableList.get(i);
              jsonDate.setId(String.valueOf(obj[0].toString()) + "," + obj[2].toString());
              jsonDate.setName(obj[1].toString());
              listJson.add(jsonDate);
            }  
          JSONArray jsonArray = JSONArray.fromObject(listJson);
          out.print(jsonArray.toString());
        } 
        if ("2".equals(sourceType)) {
          sql = "select po.id,po.menuListTableMap,po.menuName from com.js.oa.module.po.ModuleMenuPO po where po.menuListTableMap!=0";
          MsManageBD msBD = new MsManageBD();
          List<Object[]> ttableList = msBD.getListByYourSQL(sql);
          if (ttableList != null && ttableList.size() != 0)
            for (int i = 0; i < ttableList.size(); i++) {
              jsonDate = new JsonData();
              Object[] obj = ttableList.get(i);
              jsonDate.setId(obj[0] + "," + obj[1]);
              jsonDate.setName(obj[2].toString());
              listJson.add(jsonDate);
            }  
          JSONArray jsonArray = JSONArray.fromObject(listJson);
          out.print(jsonArray.toString());
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        out.flush();
        out.close();
      }  
    if (type.equals("getProcessByTtableIdWithUserRight"))
      try {
        List<JsonData> listJson = new ArrayList<JsonData>();
        JsonData jsonDate = null;
        String tableId = Long.valueOf(req.getParameter("tableId")).toString();
        String sql = null;
        sql = "SELECT aaa.wfWorkFlowProcessId,aaa.workFlowProcessName,ttable.tableId FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa,com.js.oa.eform.po.TAreaPO tarea,com.js.oa.jsflow.po.TTablePO ttable WHERE aaa.accessDatabaseId =tarea.tpage.id AND ttable.tableName=tarea.areaTable AND aaa.wfPackage.moduleId=1 AND tarea.areaName='form1' and ttable.tableId='" + 



          
          tableId + "'";
        WFProcessEJBBean processEjbBean = new WFProcessEJBBean();
        String where = processEjbBean.getProcWhereSql(userId, orgIdString, "1");
        sql = String.valueOf(sql) + " and ((" + where + ") or (aaa.createdEmp = " + userId + ")) ";
        MsManageBD msBD = new MsManageBD();
        List<Object[]> ttableList = msBD.getListByYourSQL(sql);
        if (ttableList != null && ttableList.size() != 0)
          for (int i = 0; i < ttableList.size(); i++) {
            jsonDate = new JsonData();
            Object[] obj = ttableList.get(i);
            jsonDate.setId(obj[0].toString());
            jsonDate.setName(obj[1].toString());
            listJson.add(jsonDate);
          }  
        JSONArray jsonArray = JSONArray.fromObject(listJson);
        out.print(jsonArray.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        out.flush();
        out.close();
      }  
  }
}
