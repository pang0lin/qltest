package com.js.oa.personalwork.person.action;

import com.js.oa.personalwork.person.po.PersonPO;
import com.js.oa.personalwork.person.service.PersonOwnBD;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.simple.Page;
import com.js.util.util.FillBean;
import com.js.util.util.MD5;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PersonOwnAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
    PersonOwnActionForm personOwnActionForm = (PersonOwnActionForm)actionForm;
    PersonOwnBD bd = new PersonOwnBD();
    String action = request.getParameter("action");
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String moduleName = request.getParameter("moduleName");
    request.setAttribute("moduleName", moduleName);
    if ("see".equals(action)) {
      String userType = request.getParameter("userType");
      Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), userType, session.getAttribute("domainId").toString());
      request.setAttribute("countries", vec.get(0));
      request.setAttribute("classes", vec.get(1));
      return actionMapping.findForward("add");
    } 
    if ("add".equals(action)) {
      personOwnActionForm.setTmpLinkManBirth(personOwnActionForm
          .getTmpLinkManBirth().replaceAll("-", "/"));
      String userName = String.valueOf(request.getSession(true)
          .getAttribute("userName"));
      long orgId = Long.parseLong(String.valueOf(request.getSession(true)
            .getAttribute("orgId")));
      String userType = request.getParameter("userType");
      PersonPO po = (PersonPO)FillBean.transformOneToOne(personOwnActionForm, PersonPO.class);
      po.setDomainId(session.getAttribute("domainId").toString());
      po.setViewScope(personOwnActionForm.getViewScope());
      bd.add(po, (new StringBuilder(String.valueOf(userId))).toString(), userName, (new StringBuilder(String.valueOf(orgId))).toString(), userType, 
          personOwnActionForm.getTmpLinkManBirth(), 
          personOwnActionForm.getClassId());
      personOwnActionForm.reset(actionMapping, request);
      Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), userType, session.getAttribute("domainId").toString());
      request.setAttribute("countries", vec.get(0));
      request.setAttribute("classes", vec.get(1));
      return actionMapping.findForward("add");
    } 
    if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null) {
        bd.delBatch(request.getParameter("ids"), (new StringBuilder(String.valueOf(userId))).toString());
        bd.ejbMethod(request, "delBatch");
      } 
      action = "list";
    } 
    if ("delAll".equals(action)) {
      String userType = request.getParameter("userType");
      String pclassType = "";
      if (request.getParameter("pclassType") != null && !request.getParameter("pclassType").toString().equals(""))
        pclassType = request.getParameter("pclassType").toString(); 
      bd.delAll((new StringBuilder(String.valueOf(userId))).toString(), userType, pclassType);
      action = "list";
    } 
    if ("list".equals(action)) {
      ManagerService mbd = new ManagerService();
      String userType = request.getParameter("userType");
      String queryItem = request.getParameter("queryItem");
      String queryText = request.getParameter("queryText");
      String linkManName = request.getParameter("linkManName");
      String linkManSex = request.getParameter("linkManSex");
      String linkManUnit = request.getParameter("linkManUnit");
      String email = request.getParameter("email");
      String bussinessPhone = request.getParameter("bussinessPhone");
      String mobilePhone = request.getParameter("mobilePhone");
      String linkManAddress = request.getParameter("linkManAddress");
      String createdEmpName = request.getParameter("createdEmpName");
      String order = request.getParameter("order");
      String desc = request.getParameter("desc");
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int pageSize = 15;
      int currentPage = offset / pageSize + 1;
      StringBuffer where = new StringBuffer(" where 1=1 ");
      if (queryItem != null && queryText != null && !"".equals(queryItem) && !"".equals(queryText)) {
        if (queryItem != null && !"".equals(queryItem) && !"".equals(queryText)) {
          where.append(" and (");
          if (queryItem.equals("email")) {
            where.append(" po.linkManEmail like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManEmail2 like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManEmail3 like '%")
              .append(queryText)
              .append("%'");
          } else if (queryItem.equals("linkManAddress")) {
            where.append(" po.linkManCountry like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManState like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManCounty like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManAddress like '%")
              .append(queryText)
              .append("%' ");
          } else if (queryItem.equals("orgName")) {
            where.append(" po.linkManUnit like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManDepart like '%")
              .append(queryText)
              .append("%'");
          } else {
            where.append("po.").append(queryItem).append(" like '%")
              .append(queryText).append("%'");
          } 
          where.append(")");
        } else if (queryText != null && !"".equals(queryText)) {
          where.append(" and (");
          where.append(" po.linkManEmail like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManEmail2 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManEmail3 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManCountry like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManState like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManCounty like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManAddress like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManUnit like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManDepart like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManName like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.bussinessPhone like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.mobilePhone like '%")
            .append(queryText)
            .append("%' or ")
            .append(" poo.className like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.createdEmpName like '%")
            .append(queryText)
            .append("%'");
          where.append(")");
        } 
      } else {
        if (email != null && !"null".equals(email) && !"".equals(email))
          where.append(" and (po.linkManEmail like '%")
            .append(email)
            .append("%' or ")
            .append(" po.linkManEmail2 like '%")
            .append(email)
            .append("%' or ")
            .append(" po.linkManEmail3 like '%")
            .append(email)
            .append("%')"); 
        if (linkManAddress != null && !"null".equals(linkManAddress) && !"".equals(linkManAddress))
          where.append(" and (po.linkManCountry like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManState like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManCounty like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManAddress like '%")
            .append(linkManAddress)
            .append("%') "); 
        if (linkManUnit != null && !"null".equals(linkManUnit) && !"".equals(linkManUnit))
          where.append(" and (po.linkManUnit like '%")
            .append(linkManUnit)
            .append("%' or ")
            .append(" po.linkManDepart like '%")
            .append(linkManUnit)
            .append("%')"); 
        if (linkManName != null && !"null".equals(linkManName) && !"".equals(linkManName))
          where.append(" and po.linkManName").append(" like '%").append(linkManName).append("%'"); 
        if (linkManSex != null && !"null".equals(linkManSex) && !"".equals(linkManSex))
          where.append(" and po.linkManSex").append(" like '%").append(linkManSex).append("%'"); 
        if (bussinessPhone != null && !"null".equals(bussinessPhone) && !"".equals(bussinessPhone))
          where.append(" and po.bussinessPhone").append(" like '%").append(bussinessPhone).append("%'"); 
        if (mobilePhone != null && !"null".equals(mobilePhone) && !"".equals(mobilePhone))
          where.append(" and po.mobilePhone").append(" like '%").append(mobilePhone).append("%'"); 
        if (createdEmpName != null && !"null".equals(createdEmpName) && !"".equals(createdEmpName))
          where.append(" and po.createdEmpName").append(" like '%").append(createdEmpName).append("%'"); 
      } 
      if (request.getParameter("tclassType") != null && !"".equals(request.getParameter("tclassType").toString())) {
        if ("1".equals(userType)) {
          where.append(" and (");
          where.append("poo.id=").append(request.getParameter("tclassType").toString());
          where.append(")");
        } 
        request.setAttribute("tclassType", request.getParameter("tclassType"));
      } 
      if (request.getParameter("pclassType") != null && !"".equals(request.getParameter("pclassType").toString())) {
        request.setAttribute("pclassType", request.getParameter("pclassType").toString());
        if ("0".equals(userType)) {
          where.append(" and (");
          where.append("poo.id=").append(request.getParameter("pclassType").toString());
          where.append(")");
        } 
      } 
      if ("0".equals(userType)) {
        where.append(" and po.linkManType=0 and  po.createdEmpId = ")
          .append(userId);
      } else {
        where.append(" and po.linkManType=1 ");
      } 
      String orgIdSring = session.getAttribute("orgIdString").toString();
      String orgId = session.getAttribute("orgId").toString();
      orgIdSring = orgIdSring.replace('$', ',');
      if (orgIdSring.startsWith(","))
        orgIdSring = orgIdSring.substring(1, orgIdSring.length()); 
      if (orgIdSring.endsWith(","))
        orgIdSring = orgIdSring.substring(0, orgIdSring.length() - 1); 
      String[] orgIdStringArr = { orgIdSring };
      String _orgIdSring = "";
      if (orgIdSring.indexOf(",,") >= 0) {
        orgIdStringArr = orgIdSring.split(",,");
        for (int i = 0; i < orgIdStringArr.length; i++)
          _orgIdSring = String.valueOf(_orgIdSring) + "or po.viewScope like '%*" + orgIdStringArr[i] + "*%' "; 
      } 
      String rightWhere = "";
      if (userType.equals("1")) {
        rightWhere = (new ManagerService()).getRightFinalWhere(String.valueOf(userId), orgId, "08*01*02", "po.createdOrg", "po.createdEmpId");
        where.append(" and (((po.viewScope = '0' or po.viewScope like '%$" + curUserId + "$%' or po.createdEmpId = " + curUserId + ") " + _orgIdSring + ") or (").append(rightWhere).append(")) ");
      } 
      if (userType.equals("0"))
        where.append(" and ((po.viewScope like '%$" + curUserId + "$%' or po.createdEmpId = " + curUserId + ") " + _orgIdSring + ") "); 
      where.append(" and po.domainId=" + session.getAttribute("domainId") + " ");
      ManagerEJBBean meb = new ManagerEJBBean();
      String whereGroupString = "or 1<>1";
      try {
        whereGroupString = "or " + meb.whereGroup("po.viewScope", curUserId);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      where.append(whereGroupString);
      String sqlOrder = " order by po.linkManName";
      if (order != null && !"null".equals(order))
        sqlOrder = order; 
      String sqlOrderDisc = "";
      if (desc != null && !"null".equals(desc))
        sqlOrderDisc = desc; 
      if ("2".equals(sqlOrder))
        sqlOrder = " order by po.linkManCounty"; 
      if ("3".equals(sqlOrder))
        if ("2".equals(sqlOrderDisc)) {
          sqlOrder = 
            " order by po.linkManUnit desc ,po.linkManDepart ";
        } else {
          sqlOrder = " order by po.linkManUnit , po.linkManDepart  ";
        }  
      if ("1".equals(sqlOrder)) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          sqlOrder = " order by convert( po.linkManName using gbk )";
        } else {
          sqlOrder = " order by po.linkManName";
        } 
      } 
      if ("4".equals(sqlOrder))
        sqlOrder = " order by poo.className"; 
      if ("5".equals(sqlOrder))
        sqlOrder = " order by po.id"; 
      if ("2".equals(sqlOrderDisc)) {
        sqlOrderDisc = " desc";
      } else {
        sqlOrderDisc = "";
      } 
      sqlOrder = String.valueOf(sqlOrder) + sqlOrderDisc;
      Page page = new Page(
          " po.id,po.linkManName,po.linkManSex,po.linkManEmail,po.linkManEmail2,po.linkManEmail3,po.bussinessPhone,po.mobilePhone,po.linkManCounty,po.linkManUnit,po.linkManDepart,poo.className,po.linkManType,po.createdEmpName,po.createdEmpId,po.createdOrg,po.linkManState,po.viewScope,po.fixedPhone,po.linkManAddress ", 
          " com.js.oa.personalwork.person.po.PersonPO po join po.linkManClass poo ", 
          String.valueOf(where.toString()) + sqlOrder);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      boolean modiRight = mbd.hasRight(curUserId, "08*01*02");
      if (modiRight) {
        request.setAttribute("add", "1");
        request.setAttribute("modi", "1");
      } 
      if (mbd.hasRight(curUserId, "08*02"))
        request.setAttribute("manager", "1"); 
      if (modiRight) {
        List rightList = mbd.getRightScope(curUserId, "08*01*02");
        request.setAttribute("rightList", rightList);
      } 
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,order,desc,queryItem,queryText,color,userType,tclassType,pclassType,moduleName,linkManName,linkManSex,linkManUnit,email,bussinessPhone,mobilePhone,linkManAddress,createdEmpName");
    } 
    if ("load".equals(action)) {
      String key = request.getParameter("key");
      String toMD5 = (new MD5()).toMD5("key-md5" + request.getParameter("editId"));
      if (!toMD5.equals(key))
        return actionMapping.findForward("error"); 
      PersonPO po = bd.load(request.getParameter("editId"));
      PersonOwnActionForm form = (PersonOwnActionForm)FillBean.transformOTO(po, PersonOwnActionForm.class);
      form.setLinkManBirth(po.getLinkManBirth());
      form.setEditId(request.getParameter("editId"));
      form.setClassId((new StringBuilder(String.valueOf(po.getLinkManClass().getId()))).toString());
      personOwnActionForm.setBussinessFax(form.getBussinessFax());
      personOwnActionForm.setBussinessPhone(form.getBussinessPhone());
      personOwnActionForm.setClassId(form.getClassId());
      personOwnActionForm.setEditId(form.getEditId());
      personOwnActionForm.setFixedPhone(form.getFixedPhone());
      personOwnActionForm.setLinkManAddress(form.getLinkManAddress());
      personOwnActionForm.setLinkManCountry(form.getLinkManCountry());
      personOwnActionForm.setLinkManCounty(form.getLinkManCounty());
      personOwnActionForm.setLinkManDepart(form.getLinkManDepart());
      personOwnActionForm.setLinkManDuty(form.getLinkManDuty());
      personOwnActionForm.setLinkManEmail(form.getLinkManEmail());
      personOwnActionForm.setLinkManEmail2(form.getLinkManEmail2());
      personOwnActionForm.setLinkManEmail3(form.getLinkManEmail3());
      personOwnActionForm.setLinkManEnName(form.getLinkManEnName());
      personOwnActionForm.setLinkManDescribe(form.getLinkManDescribe());
      personOwnActionForm.setLinkManName(form.getLinkManName());
      personOwnActionForm.setLinkManPostZip(form.getLinkManPostZip());
      personOwnActionForm.setLinkManProfession(form.getLinkManProfession());
      personOwnActionForm.setLinkManSex(form.getLinkManSex());
      personOwnActionForm.setLinkManState(form.getLinkManState());
      personOwnActionForm.setLinkManUnit(form.getLinkManUnit());
      personOwnActionForm.setLinkManWebUrl(form.getLinkManWebUrl());
      personOwnActionForm.setMobilePhone(form.getMobilePhone());
      personOwnActionForm.setViewScope(form.getViewScope());
      request.setAttribute("birthday", form.getLinkManBirth());
      request.setAttribute("createdEmpName", form.getCreatedEmpName());
      String userType = request.getParameter("userType");
      Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), userType, session.getAttribute("domainId").toString());
      request.setAttribute("userType", userType);
      request.setAttribute("countries", vec.get(0));
      request.setAttribute("classes", vec.get(1));
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      personOwnActionForm.setTmpLinkManBirth(personOwnActionForm.getTmpLinkManBirth().replaceAll("-", "/"));
      PersonPO paraPO = new PersonPO();
      paraPO.setId(Long.parseLong(personOwnActionForm.getEditId()));
      paraPO.setLinkManBirth(new Date(personOwnActionForm.getTmpLinkManBirth()));
      paraPO.setBussinessFax(personOwnActionForm.getBussinessFax());
      paraPO.setBussinessPhone(personOwnActionForm.getBussinessPhone());
      paraPO.setFixedPhone(personOwnActionForm.getFixedPhone());
      paraPO.setLinkManAddress(personOwnActionForm.getLinkManAddress());
      paraPO.setLinkManCountry(personOwnActionForm.getLinkManCountry());
      paraPO.setLinkManCounty(personOwnActionForm.getLinkManCounty());
      paraPO.setLinkManDepart(personOwnActionForm.getLinkManDepart());
      paraPO.setLinkManDuty(personOwnActionForm.getLinkManDuty());
      paraPO.setLinkManEmail(personOwnActionForm.getLinkManEmail());
      paraPO.setLinkManEmail2(personOwnActionForm.getLinkManEmail2());
      paraPO.setLinkManEmail3(personOwnActionForm.getLinkManEmail3());
      paraPO.setLinkManEnName(personOwnActionForm.getLinkManEnName());
      paraPO.setLinkManDescribe(personOwnActionForm.getLinkManDescribe());
      paraPO.setLinkManName(personOwnActionForm.getLinkManName());
      paraPO.setLinkManPostZip(personOwnActionForm.getLinkManPostZip());
      paraPO.setLinkManProfession(personOwnActionForm.getLinkManProfession());
      paraPO.setLinkManSex(personOwnActionForm.getLinkManSex());
      paraPO.setLinkManState(personOwnActionForm.getLinkManState());
      paraPO.setLinkManUnit(personOwnActionForm.getLinkManUnit());
      paraPO.setLinkManWebUrl(personOwnActionForm.getLinkManWebUrl());
      paraPO.setMobilePhone(personOwnActionForm.getMobilePhone());
      paraPO.setViewScope(personOwnActionForm.getViewScope());
      bd.update(paraPO, personOwnActionForm.getClassId());
      String userType = request.getParameter("userType");
      Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), userType, session.getAttribute("domainId").toString());
      request.setAttribute("countries", vec.get(0));
      request.setAttribute("classes", vec.get(1));
      return actionMapping.findForward("modi");
    } 
    if ("city".equals(action)) {
      request.setAttribute("cities", bd.city(request.getParameter("country")));
      return actionMapping.findForward("ifmcity");
    } 
    if ("county".equals(action)) {
      String country = request.getParameter("country");
      String city = request.getParameter("city");
      request.setAttribute("counties", bd.county(country, city));
      return actionMapping.findForward("ifmcounty");
    } 
    if ("export".equals(action)) {
      ManagerService mbd = new ManagerService();
      String userType = request.getParameter("userType");
      String personids = request.getParameter("personids");
      if (userType != null) {
        session.setAttribute("linkExport", userType);
      } else {
        userType = session.getAttribute("linkExport").toString();
      } 
      String queryItem = request.getParameter("queryItem");
      String queryText = request.getParameter("queryText");
      String linkManName = request.getParameter("linkManName");
      String linkManSex = request.getParameter("linkManSex");
      String linkManUnit = request.getParameter("linkManUnit");
      String email = request.getParameter("email");
      String bussinessPhone = request.getParameter("bussinessPhone");
      String mobilePhone = request.getParameter("mobilePhone");
      String linkManAddress = request.getParameter("linkManAddress");
      String createdEmpName = request.getParameter("createdEmpName");
      String order = request.getParameter("order");
      String desc = request.getParameter("desc");
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int pageSize = 1000000;
      int currentPage = offset / pageSize + 1;
      StringBuffer where = new StringBuffer(" where 1=1 ");
      if (queryItem != null && queryText != null && !"".equals(queryItem) && !"".equals(queryText)) {
        if (queryItem != null && !"".equals(queryItem) && !"".equals(queryText)) {
          where.append(" and (");
          if (queryItem.equals("email")) {
            where.append(" po.linkManEmail like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManEmail2 like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManEmail3 like '%")
              .append(queryText)
              .append("%'");
          } else if (queryItem.equals("linkManAddress")) {
            where.append(" po.linkManCountry like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManState like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManCounty like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManAddress like '%")
              .append(queryText)
              .append("%' ");
          } else if (queryItem.equals("orgName")) {
            where.append(" po.linkManUnit like '%")
              .append(queryText)
              .append("%' or ")
              .append(" po.linkManDepart like '%")
              .append(queryText)
              .append("%'");
          } else {
            where.append("po.").append(queryItem).append(" like '%")
              .append(queryText).append("%'");
          } 
          where.append(")");
        } else if (queryText != null && !"".equals(queryText)) {
          where.append(" and (");
          where.append(" po.linkManEmail like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManEmail2 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManEmail3 like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManCountry like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManState like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManCounty like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManAddress like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManUnit like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManDepart like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.linkManName like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.bussinessPhone like '%")
            .append(queryText)
            .append("%' or ")
            .append(" po.mobilePhone like '%")
            .append(queryText)
            .append("%' or ")
            .append(" poo.className like '%")
            .append(queryText)
            .append("%'");
          where.append(")");
        } 
      } else {
        if (email != null && !"null".equals(email) && !"".equals(email))
          where.append(" and (po.linkManEmail like '%")
            .append(email)
            .append("%' or ")
            .append(" po.linkManEmail2 like '%")
            .append(email)
            .append("%' or ")
            .append(" po.linkManEmail3 like '%")
            .append(email)
            .append("%')"); 
        if (linkManAddress != null && !"null".equals(linkManAddress) && !"".equals(linkManAddress))
          where.append(" and (po.linkManCountry like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManState like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManCounty like '%")
            .append(linkManAddress)
            .append("%' or ")
            .append(" po.linkManAddress like '%")
            .append(linkManAddress)
            .append("%') "); 
        if (linkManUnit != null && !"null".equals(linkManUnit) && !"".equals(linkManUnit))
          where.append(" and (po.linkManUnit like '%")
            .append(linkManUnit)
            .append("%' or ")
            .append(" po.linkManDepart like '%")
            .append(linkManUnit)
            .append("%')"); 
        if (linkManName != null && !"null".equals(linkManName) && !"".equals(linkManName))
          where.append(" and po.linkManName").append(" like '%").append(linkManName).append("%'"); 
        if (linkManSex != null && !"null".equals(linkManSex) && !"".equals(linkManSex))
          where.append(" and po.linkManSex").append(" like '%").append(linkManSex).append("%'"); 
        if (bussinessPhone != null && !"null".equals(bussinessPhone) && !"".equals(bussinessPhone))
          where.append(" and po.bussinessPhone").append(" like '%").append(bussinessPhone).append("%'"); 
        if (mobilePhone != null && !"null".equals(mobilePhone) && !"".equals(mobilePhone))
          where.append(" and po.mobilePhone").append(" like '%").append(mobilePhone).append("%'"); 
        if (createdEmpName != null && !"null".equals(createdEmpName) && !"".equals(createdEmpName))
          where.append(" and po.createdEmpName").append(" like '%").append(createdEmpName).append("%'"); 
      } 
      if (request.getParameter("tclassType") != null && !"".equals(request.getParameter("tclassType").toString())) {
        if ("1".equals(userType)) {
          where.append(" and (");
          where.append("poo.id=").append(request.getParameter("tclassType").toString());
          where.append(")");
        } 
        request.setAttribute("tclassType", request.getParameter("tclassType"));
      } 
      if (request.getParameter("pclassType") != null && !"".equals(request.getParameter("pclassType").toString())) {
        request.setAttribute("pclassType", request.getParameter("pclassType").toString());
        if ("0".equals(userType)) {
          where.append(" and (");
          where.append("poo.id=").append(request.getParameter("pclassType").toString());
          where.append(")");
        } 
      } 
      if ("0".equals(userType)) {
        where.append(" and po.linkManType=0 and  po.createdEmpId = ")
          .append(userId);
      } else {
        where.append(" and po.linkManType=1 ");
      } 
      String orgIdSring = session.getAttribute("orgIdString").toString();
      String orgId = session.getAttribute("orgId").toString();
      orgIdSring = orgIdSring.replace('$', ',');
      if (orgIdSring.startsWith(","))
        orgIdSring = orgIdSring.substring(1, orgIdSring.length()); 
      if (orgIdSring.endsWith(","))
        orgIdSring = orgIdSring.substring(0, orgIdSring.length() - 1); 
      String[] orgIdStringArr = { orgIdSring };
      String _orgIdSring = "";
      if (orgIdSring.indexOf(",,") >= 0) {
        orgIdStringArr = orgIdSring.split(",,");
        for (int i = 0; i < orgIdStringArr.length; i++)
          _orgIdSring = String.valueOf(_orgIdSring) + "or po.viewScope like '%*" + orgIdStringArr[i] + "*%' "; 
      } 
      String rightWhere = "";
      if (userType.equals("1")) {
        rightWhere = (new ManagerService()).getRightFinalWhere(String.valueOf(userId), 
            orgId, "08*01*02", "po.createdOrg", "po.createdEmpId");
        where.append(" and ((po.viewScope = '0' or po.viewScope like '%$" + curUserId + "$%' or po.createdEmpId = " + curUserId + ") " + _orgIdSring + ") ");
      } 
      if (userType.equals("0"))
        where.append(" and ((po.viewScope like '%$" + curUserId + "$%' or po.createdEmpId = " + curUserId + ") " + _orgIdSring + ") "); 
      where.append(" and po.domainId=" + session.getAttribute("domainId") + " ");
      String sqlOrder = " order by po.linkManName";
      if (order != null && !"null".equals(order))
        sqlOrder = order; 
      String sqlOrderDisc = "";
      if (desc != null && !"null".equals(desc))
        sqlOrderDisc = desc; 
      if ("2".equals(sqlOrder))
        sqlOrder = " order by po.linkManCounty"; 
      if ("3".equals(sqlOrder))
        if ("2".equals(sqlOrderDisc)) {
          sqlOrder = 
            " order by po.linkManUnit desc ,po.linkManDepart ";
        } else {
          sqlOrder = " order by po.linkManUnit , po.linkManDepart  ";
        }  
      if ("1".equals(sqlOrder))
        sqlOrder = " order by po.linkManName"; 
      if ("4".equals(sqlOrder))
        sqlOrder = " order by poo.className"; 
      if ("5".equals(sqlOrder))
        sqlOrder = " order by po.id"; 
      if ("2".equals(sqlOrderDisc)) {
        sqlOrderDisc = " desc";
      } else {
        sqlOrderDisc = "";
      } 
      sqlOrder = String.valueOf(sqlOrder) + sqlOrderDisc;
      String personIdWhere = " ";
      if (!StringUtils.isEmpty(personids))
        personIdWhere = " and po.id in (" + personids + ")"; 
      Page page = new Page(
          " po.id,po.linkManName,po.linkManSex,po.linkManEmail,po.linkManEmail2,po.linkManEmail3,po.bussinessPhone,po.mobilePhone,po.linkManCounty,po.linkManUnit,po.linkManDepart,poo.className,po.linkManType,po.createdEmpName,po.createdEmpId,po.createdOrg,po.linkManState,po.viewScope,po.fixedPhone,po.linkManAddress ", 
          " com.js.oa.personalwork.person.po.PersonPO po join po.linkManClass poo ", 
          String.valueOf(where.toString()) + personIdWhere + sqlOrder);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      boolean modiRight = mbd.hasRight(curUserId, "08*01*02");
      if (modiRight) {
        request.setAttribute("add", "1");
        request.setAttribute("modi", "1");
      } 
      if (mbd.hasRight(curUserId, "08*02"))
        request.setAttribute("manager", "1"); 
      if (modiRight) {
        List rightList = mbd.getRightScope(curUserId, "08*01*02");
        request.setAttribute("rightList", rightList);
      } 
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("userType", userType);
      return actionMapping.findForward("export");
    } 
    List unitlist = null;
    PersonOwnBD pob = new PersonOwnBD();
    try {
      Byte linkManType = Byte.valueOf(Byte.parseByte(request.getParameter("userType")));
      if (linkManType.byteValue() == 0) {
        int userId2 = Integer.parseInt(session.getAttribute("userId").toString());
        unitlist = pob.getUnitList(linkManType, userId2);
      } else {
        unitlist = pob.getPublicUnitList(linkManType);
      } 
      request.setAttribute("unitlist", unitlist);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return actionMapping.findForward("list");
  }
}
