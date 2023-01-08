package com.js.oa.oacollect.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.oacollect.bean.OACollectCategoryEJBBean;
import com.js.oa.oacollect.po.OaCollectCategory;
import com.js.oa.oasysremind.bean.JsonData;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CollectCategoryAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    CollectCategoryActionForm form = (CollectCategoryActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    OACollectCategoryEJBBean bean = new OACollectCategoryEJBBean();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    if ("list".equals(action)) {
      try {
        collectCategoryList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("forCollectCategoryAdd".equals(action)) {
      List<Object> list = bean.searchAll(Long.valueOf(-1L), userId);
      request.setAttribute("list", list);
      return actionMapping.findForward(action);
    } 
    if ("collectCategoryAdd".equals(action))
      try {
        OaCollectCategory po = (OaCollectCategory)FillBean.transformOTO(form, OaCollectCategory.class);
        String readerId = form.getReaderId();
        po.setCreatedDate(new Date());
        po.setCreateEmp(Long.valueOf(session.getAttribute("userId").toString()));
        po.setCreateOrg(Long.valueOf(session.getAttribute("orgId").toString()));
        po.setCreateEmpName(session.getAttribute("userName").toString());
        po.setCreateOrgName(session.getAttribute("orgName").toString());
        Long id = bean.saveOACollectCategory(po, request);
        String flag = null;
        if (id != null) {
          flag = "addsuccess";
        } else {
          flag = "adderror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectCategoryAddAndContinue".equals(action))
      try {
        OaCollectCategory po = (OaCollectCategory)FillBean.transformOTO(form, OaCollectCategory.class);
        po.setCreatedDate(new Date());
        po.setCreateEmp(Long.valueOf(session.getAttribute("userId").toString()));
        po.setCreateOrg(Long.valueOf(session.getAttribute("orgId").toString()));
        po.setCreateEmpName(session.getAttribute("userName").toString());
        po.setCreateOrgName(session.getAttribute("orgName").toString());
        Long id = bean.saveOACollectCategory(po, request);
        String flag = null;
        if (id != null) {
          flag = "addAndContinueSuccess";
        } else {
          flag = "addAndContinueError";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("forCollectCategoryModi".equals(action))
      try {
        String collectId = request.getParameter("id");
        if (collectId != null && !"".equals(collectId)) {
          OaCollectCategory po = bean.loadOaCollectCategory(new Long(collectId));
          poToForm(form, po);
          List<Object> list = bean.searchAll(Long.valueOf(-1L), userId);
          request.setAttribute("list", list);
          String sql = "select po.categorySortCode,po.categoryName from com.js.oa.oacollect.po.OaCollectCategory po where po.parentId=" + po.getParentId() + 
            " and po.categoryId!=" + po.getCategoryId() + " order by po.categorySort";
          list = bean.getListByYourSQL(sql);
          request.setAttribute("categorySortCodeList", list);
          Object[] obj = bean.searchByParentId(form.getParentId(), form.getCategorySortCode(), form.getCategoryId());
          if (obj != null && obj.length >= 2)
            if ("0".equals(obj[0].toString())) {
              form.setCategorySortCode(Integer.valueOf(-1));
            } else if ("1".equals(obj[0].toString())) {
              form.setCategorySortCode(Integer.valueOf(obj[1].toString()));
              form.setInsertSite(Integer.valueOf(0));
            } else if ("2".equals(obj[0].toString())) {
              form.setCategorySortCode(Integer.valueOf(obj[1].toString()));
              form.setInsertSite(Integer.valueOf(1));
            } else if ("3".equals(obj[0].toString())) {
              form.setCategorySortCode(Integer.valueOf(obj[1].toString()));
              form.setInsertSite(Integer.valueOf(0));
            }  
          return actionMapping.findForward(action);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectCategoryModi".equals(action))
      try {
        OaCollectCategory po = (OaCollectCategory)FillBean.transformOTO(form, OaCollectCategory.class);
        po.setCreatedDate(new Date());
        po.setCreateEmp(Long.valueOf(session.getAttribute("userId").toString()));
        po.setCreateOrg(Long.valueOf(session.getAttribute("orgId").toString()));
        po.setCreateEmpName(session.getAttribute("userName").toString());
        po.setCreateOrgName(session.getAttribute("orgName").toString());
        boolean b = bean.updateOACollectCategory(po, request);
        String flag = null;
        if (b) {
          flag = "updatesuccess";
        } else {
          flag = "updateerror";
        } 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("close");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if ("collectCategoryDelete".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String fromTabFlag = request.getParameter("fromTabFlag");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          try {
            b = bean.deleteOACollectCategory(ids);
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String flag = null;
        if (b) {
          flag = "deletesuccess";
        } else {
          flag = "deleteerror";
        } 
        request.setAttribute("flag", flag);
        collectCategoryList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("list");
    } 
    if ("copyCollectCategory".equals(action)) {
      String ids = request.getParameter("ids");
      String[] idStrings = ids.split(",");
      OACollectCategoryEJBBean cBean = new OACollectCategoryEJBBean();
      Map<String, String> idMap = new HashMap<String, String>();
      for (int i = 0; i < idStrings.length; i++) {
        try {
          OaCollectCategory po = cBean.loadOaCollectCategory(Long.valueOf(idStrings[i]));
          if (po.getCategoryLevel().intValue() == 1) {
            List<String> poList = cBean.getChildrenPO(idStrings[i]);
            for (int j = 0; j < poList.size(); j++) {
              String poIdc = poList.get(j);
              if (idMap.get(poIdc) == null) {
                OaCollectCategory copyPo = cBean.loadOaCollectCategory(Long.valueOf(poIdc));
                copyPo.setCategoryName((j == 0) ? ("复制-" + copyPo.getCategoryName()) : copyPo.getCategoryName());
                Long poId = cBean.saveCategoryCopy(copyPo, idMap);
                idMap.put(poIdc, poId);
              } 
            } 
          } else {
            String sortCode = po.getCategorySort();
            String[] sort = sortCode.split("\\$");
            for (int j = 1; j < sort.length; j += 2) {
              if (idMap.get(sort[j]) == null) {
                OaCollectCategory copyPo = cBean.loadOaCollectCategory(Long.valueOf(sort[j]));
                copyPo.setCategoryName((j == 1) ? ("复制-" + copyPo.getCategoryName()) : copyPo.getCategoryName());
                Long poId = cBean.saveCategoryCopy(copyPo, idMap);
                idMap.put(sort[j], poId);
              } 
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      request.setAttribute("copyC", "1");
      try {
        collectCategoryList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      if ("getCategoryClass".equals(action)) {
        PrintWriter out = null;
        try {
          response.setContentType("text/xml; charset=GBK");
          out = response.getWriter();
          long parentId = Long.parseLong(request.getParameter("parentId"));
          String sql = "select po.categorySortCode,po.categoryName from com.js.oa.oacollect.po.OaCollectCategory po where po.parentId=" + parentId + " order by po.categorySort";
          JsonData jsonDate = null;
          List<JsonData> listJson = new ArrayList<JsonData>();
          if (sql != null && !"".equals(sql)) {
            MsManageBD msBD = new MsManageBD();
            List<Object[]> ttableList = msBD.getListByYourSQL(sql);
            if (ttableList != null && ttableList.size() != 0)
              for (int i = 0; i < ttableList.size(); i++) {
                jsonDate = new JsonData();
                Object[] obj = ttableList.get(i);
                jsonDate.setId(obj[0].toString());
                jsonDate.setName(obj[1].toString());
                if (obj.length > 2)
                  jsonDate.setTableName(obj[2].toString()); 
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
        return null;
      } 
      if ("getCollectTableList".equals(action)) {
        PrintWriter out = null;
        try {
          response.setContentType("text/xml; charset=GBK");
          out = response.getWriter();
          long tableId = Long.parseLong(request.getParameter("tableId"));
          String sql = "select tarea.tpage.id,tarea.tpage.pageName,ttable.tableName from  com.js.oa.jsflow.po.TTablePO ttable,com.js.oa.eform.po.TAreaPO tarea  where tarea.areaTable=ttable.tableName and tarea.areaName='form1' and tarea.tpage.pageType=0 and ttable.tableId='" + 
            
            tableId + "'";
          JsonData jsonDate = null;
          List<JsonData> listJson = new ArrayList<JsonData>();
          if (sql != null && !"".equals(sql)) {
            MsManageBD msBD = new MsManageBD();
            List<Object[]> ttableList = msBD.getListByYourSQL(sql);
            if (ttableList != null && ttableList.size() != 0)
              for (int i = 0; i < ttableList.size(); i++) {
                jsonDate = new JsonData();
                Object[] obj = ttableList.get(i);
                jsonDate.setId(obj[0].toString());
                jsonDate.setName(obj[1].toString());
                if (obj.length > 2)
                  jsonDate.setTableName(obj[2].toString()); 
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
        return null;
      } 
    } 
    return actionMapping.findForward("list");
  }
  
  public void poToForm(CollectCategoryActionForm form, OaCollectCategory po) {
    form.setCategoryId(po.getCategoryId());
    form.setCategoryName(po.getCategoryName());
    form.setCategoryNote(po.getCategoryNote());
    form.setCreateEmp(po.getCreateEmp());
    form.setCreateEmpName(po.getCreateEmpName());
    form.setCreateOrg(po.getCreateOrg());
    form.setCreateOrgName(po.getCreateOrgName());
    form.setCategorySortCode(po.getCategorySortCode());
    form.setCategorySort(po.getCategorySort());
    form.setParentId(po.getParentId());
    form.setCategoryLevel(po.getCategoryLevel());
    form.setCreatedDate(po.getCreatedDate());
    form.setReaderId(po.getReaderId());
    form.setReaderName(po.getReaderName());
  }
  
  public void collectCategoryList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String collectId = request.getParameter("collectId");
    int offset_page = 0;
    String test = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String para = " po.categoryId,po.categoryName,po.categorySortCode,po.parentId,po.createEmpName,po.categoryLevel,po.readerName";
    String from = " com.js.oa.oacollect.po.OaCollectCategory po";
    String where = " where  ";
    ManagerService managerBD = new ManagerService();
    String rightName = "09*01*02";
    String whereTmp = managerBD.getRightWhere(userId, 
        orgId, 
        rightName, 
        "po.createOrg", 
        "po.createEmp");
    if (whereTmp.equals(""))
      where = String.valueOf(where) + "1<1"; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + whereTmp; 
    String orderBy = " order by po.categorySort";
    Page page = new Page(para, from, String.valueOf(where) + orderBy);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
  
  public Date timeFormat(String timeStr) throws ParseException {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    Date datef = bartDateFormat.parse(timeStr);
    timeStr = format.format(datef);
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format2.parse(timeStr);
    return date;
  }
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
