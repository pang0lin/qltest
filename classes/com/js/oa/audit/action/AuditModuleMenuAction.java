package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditModuleMenuPO;
import com.js.oa.audit.service.AuditModuleMenuBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.module.po.SystemMenuPO;
import com.js.oa.module.service.ModuleMenuAllService;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.userdb.util.DbOpt;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditModuleMenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditModuleMenuActionForm form = (AuditModuleMenuActionForm)actionForm;
    HttpSession session = request.getSession(true);
    String tag = "";
    String domainId = session.getAttribute("domainId").toString();
    ModuleMenuService bd = new ModuleMenuService();
    if (action.equals("forshenji"))
      try {
        tag = "shenji";
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        MsManageBD msBD = new MsManageBD();
        List<Object[]> msList = null;
        String sql = "select po.logId,po.ischecked from com.js.oa.audit.po.AuditLog po where po.logId=" + id;
        msList = msBD.getListByYourSQL(sql);
        if (msList != null && msList.size() != 0)
          for (int i = 0; i < msList.size(); i++) {
            Object[] obj = msList.get(i);
            String ischecked = obj[1].toString();
            if ("1".equals(ischecked))
              tag = "shenjiDetail"; 
          }  
        if ("detail".equals(flag))
          tag = "shenjiDetail"; 
        request.setAttribute("flag", flag);
        sql = "select po.auditMenuExtId,po.menuExtId from com.js.oa.audit.po.AuditModuleMenuPO po where po.auditLogId=" + id;
        msList = msBD.getListByYourSQL(sql);
        if (msList != null && msList.size() != 0)
          for (int i = 0; i < msList.size(); i++) {
            Object[] obj = msList.get(i);
            id = obj[0].toString();
          }  
        AuditModuleMenuBD auditBD = new AuditModuleMenuBD();
        AuditModuleMenuPO po = auditBD.loadAuditModuleMenu(Long.valueOf(id).longValue());
        if (po != null) {
          String zorderLocation = "";
          ModuleMenuAllService oBd = new ModuleMenuAllService();
          String menuViewUser = (po.getMenuViewUser() == null || po.getMenuViewUser().equals("null")) ? "" : po.getMenuViewUser();
          String menuViewGroup = (po.getMenuViewGroup() == null || po.getMenuViewGroup().equals("null")) ? "" : po.getMenuViewGroup();
          request.setAttribute("menuView", po.getMenuScope());
          request.setAttribute("menuViewId", String.valueOf(menuViewUser) + po.getMenuViewOrg() + menuViewGroup);
          SystemMenuPO ppo = oBd.loadMenuSet(po.getMenuBlone().toString());
          if (ppo != null && ppo.getMenuURL() != null)
            if (ppo.getMenuURL().indexOf("?") > 0) {
              form.setDefHref(ppo.getMenuURL().substring(ppo
                    .getMenuURL()
                    .lastIndexOf("&") + 3, ppo.getMenuURL().length()));
              form.setDefHrefOld(form.getDefHref());
            } else {
              form.setDefHref(ppo.getMenuURL().substring(ppo
                    .getMenuURL()
                    .lastIndexOf("?") + 3, ppo.getMenuURL().length()));
              form.setDefHrefOld(form.getDefHref());
            }  
          zorderLocation = po.getMenuBlone().toString();
          injectForm(po, form);
          String[][] filed = getQueryFiled(po.getMenuListTableName());
          String filtstr = (po.getMenuDefQueryCondition() == null) ? "" : po.getMenuDefQueryCondition();
          String orderstr = (po.getMenuDefQueryOrder() == null) ? "" : po.getMenuDefQueryOrder();
          String mainTableNameTemp = "";
          if (filed != null && filed.length > 0) {
            for (int i = 0; i < filed.length; i++) {
              filtstr = replaceAll(filtstr, filed[i][2], 
                  String.valueOf(filed[i][1]) + "." + 
                  filed[i][3]);
              orderstr = replaceAll(orderstr, filed[i][2], filed[i][3]);
              mainTableNameTemp = filed[i][1];
            } 
            orderstr = replaceAll(orderstr, po.getMenuListTableName(), mainTableNameTemp);
          } 
          request.setAttribute("orderstr", orderstr);
          request.setAttribute("filtstr", filtstr);
          po.getMenuCount();
          if (po.getMenuAction() != null && 
            po.getMenuAction().length() > 0) {
            request.setAttribute("span", "1");
          } else if (po.getMenuListTableMap() != null && 
            po.getMenuListTableMap().longValue() > 0L) {
            request.setAttribute("span", "2");
          } else if (po.getMenuStartFlow() != null && 
            po.getMenuStartFlow().length() > 0 && 
            !"-1".equals(po.getMenuStartFlow())) {
            request.setAttribute("span", "4");
          } else if (po.getMenuFileLink() != null && 
            po.getMenuFileLink().length() > 0 && 
            !"null".equals(po.getMenuFileLink())) {
            request.setAttribute("span", "5");
          } 
          if (po.getMenuListQueryConditionElements() != null && 
            po.getMenuListQueryConditionElements().length() > 0) {
            List queryCaseList = bd.getAllQueryCaseByTblId(po
                .getMenuListTableMap().toString(), domainId.toString(), 
                "0");
            request.setAttribute("queryCaseList", queryCaseList);
            List queryFieldList = bd.getQueryShowFieldsByCase(po
                .getMenuListQueryConditionElements(), 
                domainId.toString(), "0");
            request.setAttribute("queryFieldList", queryFieldList);
          } 
          if (po.getMenuListDisplayElements() != null && 
            po.getMenuListDisplayElements().length() > 0) {
            List listCaseList = bd.getAllQueryCaseByTblId(po
                .getMenuListTableMap().toString(), domainId.toString(), 
                "1");
            request.setAttribute("listCaseList", listCaseList);
            List listFieldList = bd.getQueryShowFieldsByCase(po
                .getMenuListDisplayElements(), domainId.toString(), 
                "1");
            request.setAttribute("listFieldList", listFieldList);
          } 
          request.setAttribute("real", (
              po.getMenuFileLink() != null && 
              po.getMenuFileLink().length() > 0) ? 
              po.getMenuFileLink() : null);
          request.setAttribute("save", (
              po.getMenuFileLink() != null && 
              po.getMenuFileLink().length() > 0) ? 
              po.getMenuFileLink() : null);
          request.setAttribute("allcusttables", 
              bd.getAllCustTables(domainId.toString()));
          request.setAttribute("alllistfields", 
              bd.getTableFieldsById(domainId.toString(), 
                po.getMenuListTableMap().toString()));
          request.setAttribute("alllistorderfields", 
              bd.getTableFieldsById(domainId.toString(), 
                po.getMenuListTableMap().toString()));
          request.setAttribute("searchbound", 
              bd.getQueryFormByTblId(domainId.toString(), 
                po.getMenuListTableMap().toString()));
          request.setAttribute("workflows", 
              bd.getAllWFProcesses("", "", 
                domainId.toString(), 
                ""));
          request.setAttribute("defListFields", 
              bd.getDefListFields(po.getMenuListTableMap()
                .toString()));
          if (po.getMenuCount() > 7)
            request.setAttribute("subFlag", "1"); 
          request.setAttribute("allmenus", 
              bd.getAllCustomMenu(domainId.toString(), null, true, true, request.getParameter("validate")));
          request.setAttribute("zorderlist", 
              bd.getAllSubMenusForOrder(domainId
                .toString(), zorderLocation));
          String operationTypeCh = po.getAuditOperationType();
          if ("insert".equals(operationTypeCh))
            operationTypeCh = "新增"; 
          if ("update".equals(operationTypeCh))
            operationTypeCh = "修改"; 
          if ("delete".equals(operationTypeCh))
            operationTypeCh = "删除"; 
          if ("hideMenu".equals(operationTypeCh))
            operationTypeCh = "隐藏"; 
          if ("showMenu".equals(operationTypeCh))
            operationTypeCh = "显示"; 
          request.setAttribute("operationTypeCh", operationTypeCh);
          request.setAttribute("operationType", po.getAuditOperationType());
          request.setAttribute("menuExtId", po.getMenuExtId());
          form.setAuditMenuExtId(po.getAuditMenuExtId());
          form.setId(po.getMenuExtId());
          form.setZorder((po.getZorder() == null || "".equals(po.getZorder())) ? "1" : po.getZorder());
          form.setMenuLocationSelValue(po.getMenuLocationSelValue());
          form.setAuditLogId(po.getAuditLogId());
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if (action.equals("shenji")) {
      String auditMenuExtId = request.getParameter("auditMenuExtId");
      String checked = request.getParameter("checked");
      String auditLogId = request.getParameter("auditLogId");
      String operationType = request.getParameter("operationType");
      AuditModuleMenuBD auditModuleMenuBD = new AuditModuleMenuBD();
      auditModuleMenuBD.auditModuleMenu(auditMenuExtId, auditLogId, checked, operationType, request);
      tag = "close";
      request.setAttribute("done", "done");
      String flag = request.getParameter("flag");
      if (flag != null && "fromRemind".equals(flag))
        flag = "fromRemind2"; 
      request.setAttribute("flag", flag);
    } 
    return actionMapping.findForward(tag);
  }
  
  private String[][] getQueryFiled(String tablename) throws SQLException, Exception {
    DbOpt dbopt = new DbOpt();
    String sql = "select t.table_id,t.table_desname,v.field_name,v.field_desname from ttable t,tfield v  where v.field_table=t.table_id and t.table_name='" + 
      tablename + "'";
    String[][] result = dbopt.executeQueryToStrArr2(sql, 4);
    dbopt.close();
    return result;
  }
  
  public static String replaceAll(String str, String befor, String after) {
    StringBuffer buffer = new StringBuffer();
    int i = 0, preIndex = 0, lastIndex = 0;
    int strLen = str.length();
    int litLen = befor.length();
    if (str.endsWith(befor))
      str = String.valueOf(str.substring(0, strLen - litLen)) + after; 
    if (str.startsWith(befor))
      str = String.valueOf(after) + str.substring(litLen); 
    preIndex = str.indexOf(befor);
    String tmp = "";
    while (preIndex >= 0) {
      if (preIndex == 0) {
        if (str.length() > 1) {
          str = str.substring(befor.length(), str.length());
          preIndex = str.indexOf(befor);
        } else {
          preIndex = -1;
        } 
        buffer.append(after);
      } else {
        if (i == 0) {
          buffer.append(str.substring(0, preIndex));
        } else {
          buffer.append(after).append(str.substring(0, preIndex));
        } 
        str = str.substring(preIndex + befor.length());
        preIndex = str.indexOf(befor);
      } 
      i++;
    } 
    if (!"".equals(str)) {
      if (i > 0)
        buffer.append(after); 
      buffer.append(str);
    } 
    return buffer.toString();
  }
  
  private void injectForm(AuditModuleMenuPO po, AuditModuleMenuActionForm form) {
    form.setMenuExtId(po.getMenuExtId());
    form.setMenuName(po.getMenuName());
    form.setMenuBlone(po.getMenuBlone());
    form.setMenuBloneHId(po.getMenuBlone());
    form.setMenuLocation(po.getMenuLocation());
    form.setMenuCount(po.getMenuCount());
    form.setMenuScope(po.getMenuScope());
    form.setMenuAction(po.getMenuAction());
    form.setMenuActionParams1(po.getMenuActionParams1());
    form.setMenuActionParams2(po.getMenuActionParams2());
    form.setMenuActionParams3(po.getMenuActionParams3());
    form.setMenuActionParams4(po.getMenuActionParams4());
    form.setMenuActionParams4Value(po.getMenuActionParams4Value());
    form.setMenuListTableMap(po.getMenuListTableMap());
    form.setMenuSubTableMap(po.getMenuSubTableMap());
    form.setMenuMaintenanceTableMap(po.getMenuMaintenanceTableMap());
    form.setMenuMaintenanceSubTableMap(po.getMenuMaintenanceSubTableMap());
    form.setMenuMaintenanceSubTableName(po.getMenuMaintenanceSubTableName());
    form.setMenuListDisplayElements(po.getMenuListDisplayElements());
    form.setMenuListQueryConditionElements(po
        .getMenuListQueryConditionElements());
    form.setMenuDefQueryCondition(po.getMenuDefQueryCondition());
    form.setMenuDefQueryOrder(po.getMenuDefQueryOrder());
    form.setMenuSearchBound(po.getMenuSearchBound());
    form.setMenuAccesss(po.getMenuAccess());
    form.setMenuMessageSend(po.getMenuMessageSend());
    form.setMenuIsValide(po.getMenuIsValide());
    form.setMenuOpenStyle(po.getMenuOpenStyle());
    form.setMenuFileLink(po.getMenuFileLink());
    form.setMenuHtmlLink(po.getMenuHtmlLink());
    form.setMenuRefFlow(po.getMenuRefFlow());
    form.setMenuStartFlow(po.getMenuStartFlow());
    if (po.getMenuRelationRefFlow() != null && 
      po.getMenuRelationRefFlow().length() > 0) {
      form.setMenuRelationRefFlow(po.getMenuRelationRefFlow().substring(0, 
            po.getMenuRelationRefFlow().lastIndexOf("|")));
    } else {
      po.setMenuRelationRefFlow("");
    } 
    form.setMenuRelationRefFlow2(po.getMenuRelationRefFlow().substring(po
          .getMenuRelationRefFlow().lastIndexOf("|") + 1, 
          po.getMenuRelationRefFlow().length()));
    form.setMenuLevel(po.getMenuLevel());
    form.setMenuBlone(po.getMenuBlone());
    form.setRecordOperattion(po.getRecordOperattion());
    form.setOperationType(po.getOperationType());
    form.setOperationImg(po.getOperationImg());
    form.setOperationComponert(po.getOperationComponert());
    form.setZorderLocation(po.getMenuBlone().toString());
    form.setZorderLacationOld(po.getMenuBlone().toString());
    form.setParentOrder(po.getParentOrder());
    form.setMenuAdd(po.getMenuAdd());
    form.setMenuDel(po.getMenuDel());
    form.setMenuImp(po.getMenuImp());
    form.setMenuExp(po.getMenuExp());
    form.setMenuQuery(po.getMenuQuery());
  }
}
