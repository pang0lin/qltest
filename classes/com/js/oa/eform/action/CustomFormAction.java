package com.js.oa.eform.action;

import com.js.oa.eform.po.TAreaPO;
import com.js.oa.eform.po.TEltPO;
import com.js.oa.eform.po.TPagePO;
import com.js.oa.eform.service.AreaBD;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.eform.service.DataBaseInfoBD;
import com.js.oa.eform.service.EltBD;
import com.js.oa.eform.service.FormBD;
import com.js.oa.jsflow.service.ModuleBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import com.js.util.util.ParameterFilter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CustomFormAction extends Action {
  private static Logger logger = Logger.getLogger(CustomFormAction.class.getName());
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customform";
    String moduleName = "系统管理-应用管理-表单管理";
    String oprType = "";
    String oprContent = "";
    if (!ParameterFilter.isNumber(httpServletRequest.getParameter("id")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("sname")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("status")) || 
      !ParameterFilter.checkParameter(httpServletRequest.getParameter("userDefine")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    CustomFormActionForm customFormActionForm = (CustomFormActionForm)actionForm;
    String flag = (customFormActionForm.getOperate() == null || 
      customFormActionForm.getOperate().length() < 1 || customFormActionForm
      .getOperate().equals("null")) ? "list" : customFormActionForm
      .getOperate();
    String formname = customFormActionForm.getFormname();
    String content = customFormActionForm.getContent();
    String code = customFormActionForm.getCode();
    String fieldelt = customFormActionForm.getFieldelt();
    String sname = customFormActionForm.getSname();
    String[] id = httpServletRequest.getParameterValues("id");
    String pageid = customFormActionForm.getPageid();
    String pageName = "";
    String jsOnload = httpServletRequest.getParameter("jsOnload");
    String jsBeforeCommit = httpServletRequest.getParameter("jsBeforeCommit");
    String formClassName = httpServletRequest.getParameter("formClassName");
    String formClassSaveMethod = httpServletRequest.getParameter("formClassSaveMethod");
    String formClassUpdateMethod = httpServletRequest.getParameter("formClassUpdateMethod");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    String userName = session.getAttribute("userName").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    DataBaseInfoBD dbInfo = new DataBaseInfoBD();
    FormBD page = new FormBD();
    AreaBD area = new AreaBD();
    EltBD elt = new EltBD();
    TPagePO pagePO = new TPagePO();
    TAreaPO areaPO = new TAreaPO();
    TEltPO eltPO = new TEltPO();
    if (flag.equals("list")) {
      formList(httpServletRequest, sname);
      httpServletRequest.setAttribute("sname", sname);
      String mark = httpServletRequest.getParameter("mark");
      httpServletRequest.setAttribute("mark", mark);
      httpServletRequest.setAttribute("pageParameters", "operate,sname,mark");
    } else if (flag.equals("add")) {
      String[][] tableInfo;
      if (SystemCommon.getMultiDepart() == 1) {
        tableInfo = dbInfo.getTableInfoByRange(domainId, userId, orgId);
      } else {
        tableInfo = dbInfo.getTableInfo(domainId);
      } 
      if (tableInfo == null)
        tableInfo = new String[0][0]; 
      httpServletRequest.setAttribute("tableInfo", tableInfo);
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(16);
      if (moduleVO.getFormClassName() != null && !moduleVO.getFormClassName().equals("") && 
        !moduleVO.getFormClassName().toUpperCase().equals("NULL")) {
        httpServletRequest.setAttribute("formClassName", moduleVO.getFormClassName());
      } else {
        httpServletRequest.setAttribute("formClassName", "WorkForm.class");
      } 
      if (moduleVO.getNewFormMethod() != null && 
        !moduleVO.getNewFormMethod().equals("") && 
        !moduleVO.getNewFormMethod().toUpperCase().equals("NULL")) {
        httpServletRequest.setAttribute("newFormMethod", moduleVO.getNewFormMethod());
      } else {
        httpServletRequest.setAttribute("newFormMethod", "save");
      } 
      if (moduleVO.getActivityFormMethod() != null && 
        !moduleVO.getActivityFormMethod().equals("") && 
        !moduleVO.getActivityFormMethod().toUpperCase().equals("NULL")) {
        httpServletRequest.setAttribute("activityMethod", moduleVO.getActivityFormMethod());
      } else {
        httpServletRequest.setAttribute("activityMethod", "update");
      } 
    } else if (flag.equals("continue") || flag.equals("addquit")) {
      String defineInitData = httpServletRequest.getParameter("defineInitData");
      if ("on".equals(defineInitData)) {
        defineInitData = "1";
      } else {
        defineInitData = "0";
      } 
      pagePO.setInitdata(defineInitData);
      String defineDataType = httpServletRequest.getParameter("defineDataType");
      pagePO.setInitdatatype(defineDataType);
      String datasourceName = httpServletRequest.getParameter("datasourceName");
      pagePO.setDatasourcename(datasourceName);
      String defineSql = httpServletRequest.getParameter("defineSql");
      pagePO.setFetchsql(defineSql);
      String interfaceName = httpServletRequest.getParameter("interfaceName");
      pagePO.setInterfacename(interfaceName);
      String interfaceMethodName = httpServletRequest.getParameter("interfaceMethodName");
      pagePO.setInterfacemethod(interfaceMethodName);
      String interfaceMethodPara = httpServletRequest.getParameter("interfaceMethodPara");
      pagePO.setInterfacemethodpara(interfaceMethodPara);
      String[] interfaceFieldTable = httpServletRequest.getParameterValues("interfaceFieldName");
      String[] interfaceDisplayName = httpServletRequest.getParameterValues("interfaceDisplayName");
      String fString = "";
      if (interfaceFieldTable != null)
        for (int z = 0; z < interfaceFieldTable.length; z++) {
          if (z == interfaceFieldTable.length - 1) {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z];
          } else {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z] + ";;;";
          } 
        }  
      if (!"".equals(fString))
        pagePO.setMappingfields(fString); 
      pagePO.setDomainId(Integer.parseInt(domainId));
      pagePO.setPageName(formname);
      pagePO.setPageFileName(content.split("~")[2]);
      pagePO.setPageType(0);
      while (fieldelt.indexOf(";;") >= 0)
        fieldelt = fieldelt.replaceAll(";;", ";"); 
      fieldelt = fieldelt.replaceAll(" ", "");
      pagePO.setPageRef(fieldelt);
      pagePO.setPageContent(code);
      pagePO.setPageOwner(userName);
      pagePO.setJsOnload(jsOnload);
      pagePO.setJsBeforeCommit(jsBeforeCommit);
      pagePO.setFormClassName(formClassName);
      pagePO.setFormClassSaveMethod(formClassSaveMethod);
      pagePO.setFormClassUpdateMethod(formClassUpdateMethod);
      pagePO.setCreatedEmp(Long.valueOf(userId));
      pagePO.setCreatedOrg(Long.valueOf(orgId));
      Long page_id = page.save(pagePO);
      Long area_id = null;
      if (!content.equals("1") && page_id != null) {
        areaPO.setAreaTable(content.split("~")[1]);
        areaPO.setAreaAction("#");
        areaPO.setAreaName("form1");
        area_id = area.save(areaPO, page_id.toString(), "102");
      } 
      if (!content.equals("1") && page_id != null && area_id != null && 
        fieldelt != null) {
        String[] field = fieldelt.split(";");
        for (int i = 0; i < field.length; i++) {
          if (field[i] != null && field[i].trim().length() >= 5) {
            eltPO.setEltName(field[i]);
            eltPO.setEltTable(field[i].split("-")[1]);
            elt.save(eltPO, page_id.toString(), area_id.toString());
          } 
        } 
      } 
      if (page_id != null) {
        saveForeignTable(httpServletRequest, page_id.toString());
        String formId = page_id.toString();
        String formName = pagePO.getPageName();
        pagePO.setPageName(String.valueOf(formName) + "打印表单");
        pagePO.setPageType(1);
        pagePO.setPageContent(code);
        page_id = null;
        page_id = page.save(pagePO);
        area_id = null;
        if (!content.equals("1") && page_id != null) {
          areaPO.setAreaTable(content.split("~")[1]);
          areaPO.setAreaAction("#");
          areaPO.setAreaName("form1");
          area_id = area.save(areaPO, page_id.toString(), "102");
        } 
        if (!content.equals("1") && page_id != null && area_id != null && 
          fieldelt != null) {
          String[] field = fieldelt.split(";");
          for (int i = 0; i < field.length; i++) {
            if (field[i] != null && 
              field[i].trim().length() >= 5) {
              eltPO.setEltName(field[i]);
              eltPO.setEltTable(field[i].split("-")[1]);
              elt.save(eltPO, page_id.toString(), area_id
                  .toString());
            } 
          } 
        } 
        saveForeignTable(httpServletRequest, page_id.toString());
        pagePO.setPageName(String.valueOf(formName) + "移动表单");
        pagePO.setPageType(2);
        pagePO.setPageContent(code);
        Long mobileId = page.save(pagePO);
        area_id = null;
        if (!content.equals("1") && mobileId != null) {
          areaPO.setAreaTable(content.split("~")[1]);
          areaPO.setAreaAction("#");
          areaPO.setAreaName("form1");
          area_id = area.save(areaPO, mobileId.toString(), "102");
        } 
        if (!content.equals("1") && mobileId != null && area_id != null && 
          fieldelt != null) {
          String[] field = fieldelt.split(";");
          for (int i = 0; i < field.length; i++) {
            if (field[i] != null && 
              field[i].trim().length() >= 5) {
              eltPO.setEltName(field[i]);
              eltPO.setEltTable(field[i].split("-")[1]);
              elt.save(eltPO, mobileId.toString(), area_id
                  .toString());
            } 
          } 
        } 
        saveForeignTable(httpServletRequest, mobileId.toString());
        DbOpt dbopt = new DbOpt();
        try {
          dbopt.executeUpdate("update tpage set print_page_id=" + 
              page_id.toString() + ",mobile_page_id=" + mobileId.toString() + " where page_id=" + formId);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            dbopt.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          } 
        } 
      } 
      if (flag.equals("continue") || page_id == null) {
        String[][] tableInfo = dbInfo.getTableInfo(domainId);
        httpServletRequest.setAttribute("tableInfo", tableInfo);
        if (page_id == null)
          httpServletRequest.setAttribute("stat", "1"); 
        flag = "add";
      } 
    } else if (flag.equals("edit")) {
      String[][] tableInfo;
      httpServletRequest.setAttribute("selectedSubField", page.getSelectedSubField(pageid));
      List forminfo = page.getSingleForm(pageid);
      List fieList = page.getFeildsList(pageid);
      String sname_old = httpServletRequest.getParameter("sname");
      httpServletRequest.setAttribute("fieList", fieList);
      httpServletRequest.setAttribute("forminfo", forminfo);
      httpServletRequest.setAttribute("sname", sname_old);
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      httpServletRequest.setAttribute("pageParameters", "operate,sname");
      if (SystemCommon.getMultiDepart() == 1) {
        tableInfo = dbInfo.getTableInfoByRange(domainId, userId, orgId);
      } else {
        tableInfo = dbInfo.getTableInfo(domainId);
      } 
      httpServletRequest.setAttribute("tableInfo", tableInfo);
      httpServletRequest.setAttribute("pageid", pageid);
      CustomFormBD bd = new CustomFormBD();
      String foreignFldHTML = bd.getForeignTableHTML(pageid);
      httpServletRequest.setAttribute("empId", page.getPageEmp(pageid));
      httpServletRequest.setAttribute("foreignFldHTML", foreignFldHTML);
    } else if (flag.equals("toMobileEdit")) {
      String[][] tableInfo;
      flag = "edit";
      String mobileId = httpServletRequest.getParameter("mobileId");
      if (mobileId == null || "null".equals(mobileId) || "".equals(mobileId))
        mobileId = pageid; 
      httpServletRequest.setAttribute("selectedSubField", page.getSelectedSubField(mobileId));
      List forminfo = page.getSingleForm(mobileId);
      List fieList = page.getFeildsList(mobileId);
      String sname_old = httpServletRequest.getParameter("sname");
      httpServletRequest.setAttribute("sname", sname_old);
      httpServletRequest.setAttribute("fieList", fieList);
      httpServletRequest.setAttribute("forminfo", forminfo);
      httpServletRequest.setAttribute("sname", sname);
      httpServletRequest.setAttribute("pageParameters", "operate,sname");
      if (SystemCommon.getMultiDepart() == 1) {
        tableInfo = dbInfo.getTableInfoByRange(domainId, userId, orgId);
      } else {
        tableInfo = dbInfo.getTableInfo(domainId);
      } 
      httpServletRequest.setAttribute("tableInfo", tableInfo);
      httpServletRequest.setAttribute("pageid", pageid);
      httpServletRequest.setAttribute("mobileId", mobileId);
      CustomFormBD bd = new CustomFormBD();
      String foreignFldHTML = bd.getForeignTableHTML(mobileId);
      httpServletRequest.setAttribute("empId", page.getPageEmp(mobileId));
      httpServletRequest.setAttribute("foreignFldHTML", foreignFldHTML);
    } else if (flag.equals("signledelete")) {
      CustomFormBD bd = new CustomFormBD();
      pageName = bd.getPageName(new String[] { pageid });
      Boolean success = page.delete(pageid);
      formList(httpServletRequest, sname);
      String mark = httpServletRequest.getParameter("mark");
      httpServletRequest.setAttribute("sname", sname);
      httpServletRequest.setAttribute("pageParameters", "operate,sname,mark,");
      flag = "list";
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, 
          new Date(), "3", pageName, httpServletRequest
          .getRemoteAddr(), domainId);
    } else if (flag.equals("batchdelete")) {
      CustomFormBD bd = new CustomFormBD();
      pageName = bd.getPageName(id);
      if (id != null)
        for (int i = 0; i < id.length; i++)
          page.delete(id[i]);  
      formList(httpServletRequest, sname);
      String mark = httpServletRequest.getParameter("mark");
      httpServletRequest.setAttribute("sname", sname);
      httpServletRequest.setAttribute("pageParameters", "operate,sname,mark,");
      flag = "list";
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, 
          new Date(), "3", pageName, httpServletRequest
          .getRemoteAddr(), domainId);
    } else if (flag.equals("editquit")) {
      String mobileId = httpServletRequest.getParameter("mobileId");
      if (mobileId != null && !"".equals(mobileId) && !"null".equals(mobileId))
        pageid = mobileId; 
      String sname_old = httpServletRequest.getParameter("sname");
      httpServletRequest.setAttribute("sname", sname_old);
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      pagePO = new TPagePO();
      String defineInitData = httpServletRequest.getParameter("defineInitData");
      if ("on".equals(defineInitData)) {
        defineInitData = "1";
      } else {
        defineInitData = "0";
      } 
      pagePO.setInitdata(defineInitData);
      String defineDataType = httpServletRequest.getParameter("defineDataType");
      pagePO.setInitdatatype(defineDataType);
      String datasourceName = httpServletRequest.getParameter("datasourceName");
      pagePO.setDatasourcename(datasourceName);
      String defineSql = httpServletRequest.getParameter("defineSql");
      pagePO.setFetchsql(defineSql);
      String interfaceName = httpServletRequest.getParameter("interfaceName");
      pagePO.setInterfacename(interfaceName);
      String interfaceMethodName = httpServletRequest.getParameter("interfaceMethodName");
      pagePO.setInterfacemethod(interfaceMethodName);
      String interfaceMethodPara = httpServletRequest.getParameter("interfaceMethodPara");
      pagePO.setInterfacemethodpara(interfaceMethodPara);
      String[] interfaceFieldTable = httpServletRequest.getParameterValues("interfaceFieldName");
      String[] interfaceDisplayName = httpServletRequest.getParameterValues("interfaceDisplayName");
      String fString = "";
      if (interfaceFieldTable != null)
        for (int z = 0; z < interfaceFieldTable.length; z++) {
          if (z == interfaceFieldTable.length - 1) {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z];
          } else {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z] + ";;;";
          } 
        }  
      if (!"".equals(fString))
        pagePO.setMappingfields(fString); 
      pagePO.setId(new Long(pageid));
      pagePO.setPageName(formname);
      pagePO.setDomainId(Integer.parseInt(domainId));
      pagePO.setPageFileName(content.split("~")[2]);
      pagePO.setPageRef(fieldelt);
      pagePO.setPageContent(code);
      pagePO.setJsBeforeCommit(jsBeforeCommit);
      pagePO.setJsOnload(jsOnload);
      pagePO.setFormClassName(formClassName);
      pagePO.setFormClassSaveMethod(formClassSaveMethod);
      pagePO.setFormClassUpdateMethod(formClassUpdateMethod);
      String empId = httpServletRequest.getParameter("emp_Id");
      String printId = "0";
      String mobilePageId = "0";
      String isUpdatePrint = "0";
      String isUpdateMobile = "0";
      if (page.update(pagePO).booleanValue()) {
        isUpdatePrint = (httpServletRequest.getParameter("isUpdatePrint") == null) ? "0" : httpServletRequest.getParameter("isUpdatePrint");
        if (isUpdatePrint != null && "1".equals(isUpdatePrint))
          try {
            TPagePO formPagePOinfo = page.getPageFromPageId(Long.valueOf(Long.parseLong(pageid)));
            if (formPagePOinfo != null && formPagePOinfo.getPrintPageId() != null) {
              Long long_ = formPagePOinfo.getPrintPageId();
              TPagePO tPagePrintPO = page.getPageFromPageId(formPagePOinfo.getPrintPageId());
              if (tPagePrintPO != null) {
                tPagePrintPO.setPageName(String.valueOf(pagePO.getPageName()) + "打印表单");
                tPagePrintPO.setPageContent(pagePO.getPageContent());
                tPagePrintPO.setPageRef(pagePO.getPageRef());
                tPagePrintPO.setCreatedEmp(pagePO.getCreatedEmp());
                tPagePrintPO.setCreatedOrg(pagePO.getCreatedOrg());
                tPagePrintPO.setPageFileName(pagePO.getPageFileName());
                tPagePrintPO.setInitdata(defineInitData);
                tPagePrintPO.setInitdatatype(defineDataType);
                tPagePrintPO.setDatasourcename(datasourceName);
                tPagePrintPO.setFetchsql(defineSql);
                tPagePrintPO.setInterfacename(interfaceName);
                tPagePrintPO.setInterfacemethod(interfaceMethodName);
                tPagePrintPO.setInterfacemethodpara(interfaceMethodPara);
                if (!"".equals(fString))
                  tPagePrintPO.setMappingfields(fString); 
                page.update(tPagePrintPO);
              } 
            } else {
              TPagePO newPagePrintPO = new TPagePO();
              newPagePrintPO.setInitdata(defineInitData);
              newPagePrintPO.setInitdatatype(defineDataType);
              newPagePrintPO.setDatasourcename(datasourceName);
              newPagePrintPO.setFetchsql(defineSql);
              newPagePrintPO.setInterfacename(interfaceName);
              newPagePrintPO.setInterfacemethod(interfaceMethodName);
              newPagePrintPO.setInterfacemethodpara(interfaceMethodPara);
              if (!"".equals(fString))
                newPagePrintPO.setMappingfields(fString); 
              newPagePrintPO.setDomainId(Integer.parseInt(domainId));
              newPagePrintPO.setPageName(String.valueOf(formname) + "打印表单");
              newPagePrintPO.setPageFileName(pagePO.getPageFileName());
              newPagePrintPO.setPageType(1);
              newPagePrintPO.setPageRef(pagePO.getPageRef());
              newPagePrintPO.setPageContent(pagePO.getPageContent());
              newPagePrintPO.setPageOwner(userName);
              newPagePrintPO.setJsOnload(jsOnload);
              newPagePrintPO.setJsBeforeCommit(jsBeforeCommit);
              newPagePrintPO.setFormClassName(formClassName);
              newPagePrintPO.setFormClassSaveMethod(formClassSaveMethod);
              newPagePrintPO.setFormClassUpdateMethod(formClassUpdateMethod);
              newPagePrintPO.setCreatedEmp(pagePO.getCreatedEmp());
              newPagePrintPO.setCreatedOrg(pagePO.getCreatedOrg());
              Long newPrintPageId = page.save(newPagePrintPO);
              printId = newPrintPageId.toString();
              DbOpt dbopt = new DbOpt();
              try {
                dbopt.executeUpdate("update tpage set print_page_id=" + printId + " where page_id=" + pageid);
              } catch (Exception e) {
                e.printStackTrace();
              } finally {
                try {
                  dbopt.close();
                } catch (SQLException ex) {
                  ex.printStackTrace();
                } 
              } 
            } 
          } catch (Exception e) {
            e.printStackTrace();
          }  
        isUpdateMobile = (httpServletRequest.getParameter("isUpdateMobile") == null) ? "0" : httpServletRequest.getParameter("isUpdateMobile");
        if (isUpdateMobile != null && "1".equals(isUpdateMobile))
          try {
            TPagePO formPagePOinfo = page.getPageFromPageId(Long.valueOf(Long.parseLong(pageid)));
            if (formPagePOinfo != null && formPagePOinfo.getMobilePageId() != null) {
              Long long_ = formPagePOinfo.getMobilePageId();
              TPagePO tPageMobilePO = page.getPageFromPageId(formPagePOinfo.getMobilePageId());
              if (tPageMobilePO != null) {
                tPageMobilePO.setPageName(String.valueOf(pagePO.getPageName()) + "移动表单");
                tPageMobilePO.setPageContent(pagePO.getPageContent());
                tPageMobilePO.setPageRef(pagePO.getPageRef());
                tPageMobilePO.setCreatedEmp(pagePO.getCreatedEmp());
                tPageMobilePO.setCreatedOrg(pagePO.getCreatedOrg());
                tPageMobilePO.setPageFileName(pagePO.getPageFileName());
                tPageMobilePO.setInitdata(defineInitData);
                tPageMobilePO.setInitdatatype(defineDataType);
                tPageMobilePO.setDatasourcename(datasourceName);
                tPageMobilePO.setFetchsql(defineSql);
                tPageMobilePO.setInterfacename(interfaceName);
                tPageMobilePO.setInterfacemethod(interfaceMethodName);
                tPageMobilePO.setInterfacemethodpara(interfaceMethodPara);
                if (!"".equals(fString))
                  tPageMobilePO.setMappingfields(fString); 
                page.update(tPageMobilePO);
              } 
            } else {
              TPagePO newPageMobilePO = new TPagePO();
              newPageMobilePO.setInitdata(defineInitData);
              newPageMobilePO.setInitdatatype(defineDataType);
              newPageMobilePO.setDatasourcename(datasourceName);
              newPageMobilePO.setFetchsql(defineSql);
              newPageMobilePO.setInterfacename(interfaceName);
              newPageMobilePO.setInterfacemethod(interfaceMethodName);
              newPageMobilePO.setInterfacemethodpara(interfaceMethodPara);
              if (!"".equals(fString))
                newPageMobilePO.setMappingfields(fString); 
              newPageMobilePO.setDomainId(Integer.parseInt(domainId));
              newPageMobilePO.setPageName(String.valueOf(formname) + "移动表单");
              newPageMobilePO.setPageFileName(pagePO.getPageFileName());
              newPageMobilePO.setPageType(2);
              newPageMobilePO.setPageRef(pagePO.getPageRef());
              newPageMobilePO.setPageContent(pagePO.getPageContent());
              newPageMobilePO.setPageOwner(userName);
              newPageMobilePO.setJsOnload(jsOnload);
              newPageMobilePO.setJsBeforeCommit(jsBeforeCommit);
              newPageMobilePO.setFormClassName(formClassName);
              newPageMobilePO.setFormClassSaveMethod(formClassSaveMethod);
              newPageMobilePO.setFormClassUpdateMethod(formClassUpdateMethod);
              newPageMobilePO.setCreatedEmp(pagePO.getCreatedEmp());
              newPageMobilePO.setCreatedOrg(pagePO.getCreatedOrg());
              Long newMobilePageId = page.save(newPageMobilePO);
              mobilePageId = newMobilePageId.toString();
              DbOpt dbopt = new DbOpt();
              try {
                dbopt.executeUpdate("update tpage set mobile_page_id=" + mobilePageId + " where page_id=" + pageid);
              } catch (Exception e) {
                e.printStackTrace();
              } finally {
                try {
                  dbopt.close();
                } catch (SQLException ex) {
                  ex.printStackTrace();
                } 
              } 
            } 
          } catch (Exception e) {
            e.printStackTrace();
          }  
        page.updatePageEmp(pageid, empId);
        elt.delete(pageid);
        area.delete(pageid);
        if (isUpdatePrint != null && "1".equals(isUpdatePrint)) {
          page.updatePageEmp(printId, empId);
          elt.delete(printId);
          area.delete(printId);
        } 
        if (isUpdateMobile != null && "1".equals(isUpdateMobile)) {
          page.updatePageEmp(mobilePageId, empId);
          elt.delete(mobilePageId);
          area.delete(mobilePageId);
        } 
        Long area_id = null;
        Long area_idP = null;
        Long area_idM = null;
        if (!content.equals("1") && pageid != null) {
          areaPO.setAreaTable(content.split("~")[1]);
          areaPO.setAreaAction("#");
          areaPO.setAreaName("form1");
          area_id = area.save(areaPO, pageid.toString(), "102");
          if (isUpdatePrint != null && "1".equals(isUpdatePrint))
            area_idP = area.save(areaPO, printId, "102"); 
          if (isUpdateMobile != null && "1".equals(isUpdateMobile))
            area_idM = area.save(areaPO, mobilePageId, "102"); 
        } 
        if (!content.equals("1") && pageid != null && area_id != null && 
          fieldelt != null && fieldelt.length() > 1) {
          String[] field = fieldelt.split(";");
          for (int i = 0; i < field.length; i++) {
            if (field[i] != null && field[i].trim().length() >= 5) {
              eltPO.setEltName(field[i]);
              eltPO.setEltTable(field[i].split("-")[1]);
              elt.save(eltPO, pageid.toString(), area_id.toString());
              if (isUpdatePrint != null && "1".equals(isUpdatePrint))
                elt.save(eltPO, printId.toString(), area_idP.toString()); 
              if (isUpdateMobile != null && "1".equals(isUpdateMobile))
                elt.save(eltPO, mobilePageId.toString(), area_idM.toString()); 
            } 
          } 
        } 
        saveForeignTable(httpServletRequest, pageid.toString(), isUpdatePrint, printId);
        saveForeignTable(httpServletRequest, mobilePageId);
      } else {
        String[][] tableInfo;
        List forminfo = page.getSingleForm(pageid);
        httpServletRequest.setAttribute("forminfo", forminfo);
        httpServletRequest.setAttribute("sname", sname);
        httpServletRequest.setAttribute("pageParameters", "operate,sname,pager.offset");
        if (SystemCommon.getMultiDepart() == 1) {
          tableInfo = dbInfo.getTableInfoByRange(domainId, userId, orgId);
        } else {
          tableInfo = dbInfo.getTableInfo(domainId);
        } 
        httpServletRequest.setAttribute("tableInfo", tableInfo);
        httpServletRequest.setAttribute("pageid", pageid);
        httpServletRequest.setAttribute("stat", "1");
        flag = "edit";
      } 
    } else if (flag.equals("addMobilePage")) {
      String defineInitData = httpServletRequest.getParameter("defineInitData");
      if ("on".equals(defineInitData)) {
        defineInitData = "1";
      } else {
        defineInitData = "0";
      } 
      pagePO.setInitdata(defineInitData);
      String defineDataType = httpServletRequest.getParameter("defineDataType");
      pagePO.setInitdatatype(defineDataType);
      String datasourceName = httpServletRequest.getParameter("datasourceName");
      pagePO.setDatasourcename(datasourceName);
      String defineSql = httpServletRequest.getParameter("defineSql");
      pagePO.setFetchsql(defineSql);
      String interfaceName = httpServletRequest.getParameter("interfaceName");
      pagePO.setInterfacename(interfaceName);
      String interfaceMethodName = httpServletRequest.getParameter("interfaceMethodName");
      pagePO.setInterfacemethod(interfaceMethodName);
      String interfaceMethodPara = httpServletRequest.getParameter("interfaceMethodPara");
      pagePO.setInterfacemethodpara(interfaceMethodPara);
      String[] interfaceFieldTable = httpServletRequest.getParameterValues("interfaceFieldName");
      String[] interfaceDisplayName = httpServletRequest.getParameterValues("interfaceDisplayName");
      String fString = "";
      if (interfaceFieldTable != null)
        for (int z = 0; z < interfaceFieldTable.length; z++) {
          if (z == interfaceFieldTable.length - 1) {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z];
          } else {
            fString = String.valueOf(fString) + interfaceFieldTable[z] + "=:=" + interfaceDisplayName[z] + ";;;";
          } 
        }  
      if (!"".equals(fString))
        pagePO.setMappingfields(fString); 
      pagePO.setDomainId(Integer.parseInt(domainId));
      pagePO.setPageName(formname);
      pagePO.setPageFileName(content.split("~")[2]);
      pagePO.setPageType(2);
      while (fieldelt.indexOf(";;") >= 0)
        fieldelt = fieldelt.replaceAll(";;", ";"); 
      fieldelt = fieldelt.replaceAll(" ", "");
      pagePO.setPageRef(fieldelt);
      pagePO.setPageContent(code);
      pagePO.setPageOwner(userName);
      pagePO.setJsOnload(jsOnload);
      pagePO.setJsBeforeCommit(jsBeforeCommit);
      pagePO.setFormClassName(formClassName);
      pagePO.setFormClassSaveMethod(formClassSaveMethod);
      pagePO.setFormClassUpdateMethod(formClassUpdateMethod);
      pagePO.setCreatedEmp(Long.valueOf(userId));
      pagePO.setCreatedOrg(Long.valueOf(orgId));
      Long mobilePageId = page.save(pagePO);
      Long area_id = null;
      if (!content.equals("1") && mobilePageId != null) {
        areaPO.setAreaTable(content.split("~")[1]);
        areaPO.setAreaAction("#");
        areaPO.setAreaName("form1");
        area_id = area.save(areaPO, mobilePageId.toString(), "102");
      } 
      if (!content.equals("1") && mobilePageId != null && area_id != null && 
        fieldelt != null) {
        String[] field = fieldelt.split(";");
        for (int i = 0; i < field.length; i++) {
          if (field[i] != null && field[i].trim().length() >= 5) {
            eltPO.setEltName(field[i]);
            eltPO.setEltTable(field[i].split("-")[1]);
            elt.save(eltPO, mobilePageId.toString(), area_id.toString());
          } 
        } 
      } 
      if (mobilePageId != null) {
        saveForeignTable(httpServletRequest, mobilePageId.toString());
        DbOpt dbopt = new DbOpt();
        try {
          dbopt.executeUpdate("update tpage set mobile_page_id=" + mobilePageId.toString() + " where page_id=" + pageid);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            dbopt.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          } 
        } 
        flag = "editquit";
      } else {
        String[][] tableInfo;
        List forminfo = page.getSingleForm(pageid);
        httpServletRequest.setAttribute("forminfo", forminfo);
        httpServletRequest.setAttribute("sname", sname);
        httpServletRequest.setAttribute("pageParameters", "operate,sname,pager.offset");
        if (SystemCommon.getMultiDepart() == 1) {
          tableInfo = dbInfo.getTableInfoByRange(domainId, userId, orgId);
        } else {
          tableInfo = dbInfo.getTableInfo(domainId);
        } 
        httpServletRequest.setAttribute("tableInfo", tableInfo);
        httpServletRequest.setAttribute("pageid", pageid);
        httpServletRequest.setAttribute("stat", "1");
        flag = "edit";
      } 
    } 
    Date endDate = new Date();
    if (flag.equals("continue") || flag.equals("addquit")) {
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, endDate, 
          "1", customFormActionForm.getFormname(), httpServletRequest
          .getRemoteAddr(), domainId);
    } else if (flag.equals("editquit")) {
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, endDate, 
          "2", "保存表单：" + customFormActionForm.getFormname(), httpServletRequest
          .getRemoteAddr(), domainId);
    } 
    String action = httpServletRequest.getParameter("oprFlag");
    if ("copyData".equals(action)) {
      String formId = httpServletRequest.getParameter("formId");
      CustomFormBD bd = new CustomFormBD();
      bd.pasteForm(formId, domainId, userId, orgId);
      flag = "copyData";
    } else if ("pasteData".equals(action)) {
      String formId = (session.getAttribute("copyedForm") == null) ? "" : session.getAttribute("copyedForm").toString();
      CustomFormBD bd = new CustomFormBD();
      bd.pasteForm(formId, domainId, userId, orgId);
      flag = "list";
      formList(httpServletRequest, sname);
      httpServletRequest.setAttribute("sname", sname);
      String mark = httpServletRequest.getParameter("mark");
      httpServletRequest.setAttribute("pageParameters", "operate,sname,mark,");
      session.setAttribute("copyedForm", "");
    } 
    return actionMapping.findForward(flag);
  }
  
  private void formList(HttpServletRequest request, String sname) {
    HttpSession session = request.getSession();
    String domainId = session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String para = "page_id,page_Name,page_FileName,print_page_id,mobile_page_id";
    String from = "TPage";
    String where = " where page_type=0 and domain_Id=" + domainId;
    if (sname != null && !"".equals(sname) && !"null".equals(sname))
      where = String.valueOf(where) + " and page_Name like '%" + sname + "%'"; 
    ManagerService managerBD = new ManagerService();
    String managerWhere = managerBD.getRightFinalWhere(userId, orgId, "02*02*02", 
        "createdOrg", "createdEmp");
    where = String.valueOf(where) + " and " + managerWhere;
    String mark = request.getParameter("mark");
    request.setAttribute("mark", mark);
    String desc = request.getParameter("desc");
    request.setAttribute("desc", desc);
    String sqlOrderDisc = "";
    if ("2".equals(desc)) {
      sqlOrderDisc = " desc";
    } else {
      sqlOrderDisc = " asc";
    } 
    if (mark != null && "1".equals(mark)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by convert(page_Name using gbk )" + sqlOrderDisc;
      } else {
        where = String.valueOf(where) + " ORDER BY NLSSORT(page_Name, 'NLS_SORT=SCHINESE_PINYIN_M') " + sqlOrderDisc;
      } 
    } else {
      where = String.valueOf(where) + " order by page_id desc";
    } 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("formList", list);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "mark,desc");
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
  }
  
  private void saveForeignTable(HttpServletRequest httpServletRequest, String pageid) {
    saveForeignTable(httpServletRequest, pageid, "0", "0");
  }
  
  private void saveForeignTable(HttpServletRequest httpServletRequest, String pageid, String isUpdatePrint, String printId) {
    AreaBD area = new AreaBD();
    EltBD elt = new EltBD();
    TAreaPO areaPO = new TAreaPO();
    TEltPO eltPO = new TEltPO();
    String forTbls = httpServletRequest.getParameter("forTable");
    String[] forTblArr = (String[])null;
    if (forTbls != null && forTbls.length() > 0 && !"null".equalsIgnoreCase(forTbls)) {
      forTblArr = forTbls.split(";");
      for (int i = 0; i < forTblArr.length; i++) {
        String fieldelt = httpServletRequest.getParameter(forTblArr[i]);
        if (fieldelt != null && fieldelt.length() >= 1) {
          Long area_id = null;
          Long area_idP = null;
          if (pageid != null) {
            areaPO.setAreaTable(forTblArr[i]);
            areaPO.setAreaAction("#");
            areaPO.setAreaName(forTblArr[i]);
            area_id = area.save(areaPO, pageid, "105");
            if (isUpdatePrint != null && "1".equals(isUpdatePrint))
              area_idP = area.save(areaPO, printId, "105"); 
          } 
          if (pageid != null && area_id != null)
            if (fieldelt != null && fieldelt.length() > 1) {
              String[] field = fieldelt.split(";");
              for (int j = 0; j < field.length; j++) {
                if (field[j] != null && field[j].trim().length() >= 5) {
                  eltPO.setEltName(field[j]);
                  eltPO.setEltTable(field[j].split("-")[1]);
                  elt.save(eltPO, pageid, area_id.toString());
                  if (isUpdatePrint != null && "1".equals(isUpdatePrint))
                    elt.save(eltPO, printId, area_idP.toString()); 
                } 
              } 
            }  
        } 
      } 
    } 
  }
  
  public void formlog(HttpServletRequest httpServletRequest, String moduleCode, String moduleName, String oprType, String oprContent) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    Date endDate = new Date();
    HttpSession sess = httpServletRequest.getSession(true);
    logBD.log(sess.getAttribute("userId").toString(), sess.getAttribute("userName").toString(), 
        sess.getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, 
        oprType, oprContent, httpServletRequest.getRemoteAddr(), sess.getAttribute("domainId").toString());
  }
}
