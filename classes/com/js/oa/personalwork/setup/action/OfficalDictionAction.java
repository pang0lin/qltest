package com.js.oa.personalwork.setup.action;

import com.js.oa.personalwork.setup.po.OfficalDictionPO;
import com.js.oa.personalwork.setup.service.OfficalDictionBD;
import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OfficalDictionAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    OfficalDictionActionForm officalDictionActionForm = (OfficalDictionActionForm)actionForm;
    String action = request.getParameter("action");
    OfficalDictionBD bd = new OfficalDictionBD();
    Long curUserId = new Long((String)session.getAttribute("userId"));
    Object object = session.getAttribute("userName");
    BeanUtils bb = new BeanUtils();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    if ("add".equals(action)) {
      OfficalDictionPO po = new OfficalDictionPO();
      try {
        BeanUtils.copyProperties(po, officalDictionActionForm);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      po.setDomainId(domainId);
      po.setDictionContent(po.getDictionContent().trim().replace('\'', '‘'));
      String retString = bd.add(po, curUserId, (String)object);
      if (!"repeat".equals(retString))
        officalDictionActionForm.reset(actionMapping, request); 
      request.setAttribute("offcontent", request.getParameter("dictionContent"));
      request.setAttribute("message", retString);
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      String retString = bd.update(officalDictionActionForm.getDictionContent().trim(), 
          Byte.valueOf(officalDictionActionForm.getDictionIsShare()), 
          new Long(request.getParameter("editId")), 
          curUserId, 
          (String)object);
      request.setAttribute("message", retString);
      return actionMapping.findForward("modi");
    } 
    if ("delAll".equals(action)) {
      bd.delAll(curUserId);
      action = "list";
    } 
    if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null)
        bd.delBatch(request.getParameter("ids")); 
      action = "list";
    } 
    if ("load".equals(action)) {
      Long editId = new Long(request.getParameter("editId"));
      OfficalDictionPO po = bd.load(editId);
      try {
        BeanUtils.copyProperties(officalDictionActionForm, po);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action)) {
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      Vector<String> vec = list(curUserId, new Integer(offset), domainId);
      String recordCount = vec.get(0);
      String pageSize = vec.get(1);
      List list = (List)vec.get(2);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", pageSize);
      request.setAttribute("pageParameters", "action");
    } 
    return actionMapping.findForward("list");
  }
  
  private Vector list(Long curUserId, Integer offset, String domainId) {
    Vector<String> vec = new Vector();
    List list = new ArrayList();
    int pageSize = 15;
    int currentPage = offset.intValue() / pageSize + 1;
    Page page = new Page(
        " po.id,po.dictionContent,po.empName ,po.dictionIsShare,po.empId", 
        " com.js.oa.personalwork.setup.po.OfficalDictionPO po where (po.empId=" + 
        curUserId.longValue() + 
        " or po.dictionIsShare=1) and po.domainId=" + domainId, 
        " order by po.dictionContent ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    vec.add(recordCount);
    vec.add((new StringBuilder(String.valueOf(pageSize))).toString());
    vec.add(list);
    return vec;
  }
}
