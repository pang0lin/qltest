package com.js.oa.message.action;

import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.message.po.MsManagePO;
import com.js.oa.message.service.MsManageBD;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MsManageAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    MsManageActionForm messageActionForm = (MsManageActionForm)actionForm;
    MsManageBD msManageBD = new MsManageBD();
    MsManageEJBBean msManageEJBBean = new MsManageEJBBean();
    HttpSession session = httpServletRequest.getSession(true);
    if ("listView".equals(action)) {
      list(httpServletRequest, "1=1");
      return actionMapping.findForward("listView");
    } 
    if ("load".equals(action)) {
      try {
        MsManagePO managePo = msManageBD.loadMs(httpServletRequest.getParameter("id"));
        List<Object[]> list = msManageBD.getMsManageInfoByMsId(managePo.getMsId().toString());
        String grantId = "";
        Object object = "";
        if (list != null && list.size() != 0)
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            if (i == 0) {
              if (obj[4].toString().equals("1")) {
                grantId = "$" + obj[2] + "$";
                object = obj[3];
              } else if (obj[4].toString().equals("2")) {
                grantId = "*" + obj[2] + "*";
                object = obj[3];
              } else if (obj[4].toString().equals("3")) {
                grantId = "@" + obj[2] + "@";
                object = obj[3];
              } 
            } else if (obj[4].toString().equals("1")) {
              grantId = String.valueOf(grantId) + "$" + obj[2].toString() + "$";
              object = String.valueOf(object) + "," + obj[3].toString();
            } else if (obj[4].toString().equals("2")) {
              grantId = String.valueOf(grantId) + "*" + obj[2].toString() + "*";
              object = String.valueOf(object) + "," + obj[3].toString();
            } else if (obj[4].toString().equals("3")) {
              grantId = String.valueOf(grantId) + "@" + obj[2].toString() + "@";
              object = String.valueOf(object) + "," + obj[3].toString();
            } 
          }  
        if (!"".equals(object) && object != null)
          object = String.valueOf(object) + ","; 
        managePo.setUserRange(grantId);
        managePo.setUserRangeCh((String)object);
        MsManageActionForm form = (MsManageActionForm)FillBean.transformOTO(managePo, MsManageActionForm.class);
        BeanUtils.copyProperties(messageActionForm, form);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("load");
    } 
    if ("modify".equals(action)) {
      try {
        MsManagePO managePo = (MsManagePO)FillBean.transformOTO(messageActionForm, MsManagePO.class);
        msManageEJBBean.updateMsMangeGrant(managePo);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("load");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    MsManageBD msManageBD = new MsManageBD();
    MsManageEJBBean msManageEJBBean = new MsManageEJBBean();
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where 1=1 ";
    where = String.valueOf(where) + " and ( " + wherePara + ") and ms.domainId=" + domainId + " ";
    Page page = new Page(" ms.msId,ms.msTitle,ms.msRemark,ms.domainId ", 
        " from com.js.oa.message.po.MsManagePO ms ", 
        String.valueOf(where) + " order by ms.msId ");
    page.setPageSize(pageSize);
    List<Object[]> list = new ArrayList();
    list = page.getResultList();
    try {
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          Object[] obj = list.get(i);
          String msId = obj[0].toString();
          String users = msManageEJBBean.getUserNameByMsId(msId);
          obj[2] = users;
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
}
