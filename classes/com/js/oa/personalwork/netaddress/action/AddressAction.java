package com.js.oa.personalwork.netaddress.action;

import com.js.oa.personalwork.netaddress.bean.AddressEJBBean;
import com.js.oa.personalwork.netaddress.po.AddressPO;
import com.js.oa.personalwork.netaddress.service.AddressBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.BASE64;
import com.js.util.util.FillBean;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddressAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    AddressActionForm addressActionForm = (AddressActionForm)actionForm;
    AddressBD bd = new AddressBD();
    String action = request.getParameter("action");
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String curUserName = session.getAttribute("userName").toString();
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    if ("addresses".equals(action)) {
      request.setAttribute("addressClasses", bd.see(curUserId));
      return actionMapping.findForward("add");
    } 
    if ("showDesktop".equals(action)) {
      if (request.getParameter("editId") != null)
        bd.showDesktop((new StringBuilder(String.valueOf(curUserId))).toString(), request.getParameter("isShow"), request.getParameter("editId")); 
      action = "list";
    } else if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null)
        bd.delBatch(request.getParameter("ids"), (new StringBuilder(String.valueOf(curUserId))).toString()); 
      action = "list";
    } else if ("forbid".equals(action)) {
      if (request.getParameter("ids") != null) {
        String sql = "";
        String operate = request.getParameter("operate");
        DbOpt dbopt = null;
        try {
          dbopt = new DbOpt();
          if (operate.equals("1")) {
            sql = "update oa_netaddress set operate='0' where NETADDRESS_ID='" + request.getParameter("ids") + "'";
            dbopt.executeUpdate(sql);
            sql = "insert oa_netaddressshow (EMP_ID,NETADDRESS_ID,NETADDRESS,NETADDRESSURL,DOMAIN_ID)values('" + curUserId + "','" + request.getParameter("ids") + "','','','" + domainId + "');";
            dbopt.executeUpdate(sql);
          } else {
            sql = "update oa_netaddress set operate='1' where NETADDRESS_ID='" + request.getParameter("ids") + "'";
            dbopt.executeUpdate(sql);
            sql = "delete from  oa_netaddressshow where NETADDRESS_ID='" + request.getParameter("ids") + "'";
            dbopt.executeUpdate(sql);
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            dbopt.close();
          } catch (SQLException sQLException) {}
        } 
      } 
      action = "list";
    } else if ("delShare".equals(action)) {
      if (request.getParameter("ids") != null)
        bd.delShare(request.getParameter("ids"), (new StringBuilder(String.valueOf(curUserId))).toString()); 
      action = "list";
    } else if ("delAll".equals(action)) {
      bd.delAll((new StringBuilder(String.valueOf(curUserId))).toString());
      action = "list";
    } else {
      if ("add".equals(action)) {
        AddressPO po = (AddressPO)FillBean.transformOneToOne(addressActionForm, AddressPO.class);
        String fieldNameValue = "";
        String fieldValues = "";
        String isImmoField = "";
        String[] fieldName = request.getParameterValues("fieldName");
        String[] fieldValue = request.getParameterValues("fieldValue");
        String[] isImmobilefield = request.getParameterValues("isImmobilefield");
        if (fieldName != null)
          for (int i = 0; i < fieldName.length; i++) {
            if (i == fieldName.length - 1) {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i];
            } else {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i] + "#&#";
            } 
          }  
        if (fieldValue != null)
          for (int i = 0; i < fieldValue.length; i++) {
            if (i == fieldValue.length - 1) {
              fieldValues = String.valueOf(fieldValues) + fieldValue[i];
            } else {
              fieldValues = String.valueOf(fieldValues) + fieldValue[i] + "#&#";
            } 
          }  
        if (isImmobilefield != null)
          for (int i = 0; i < isImmobilefield.length; i++) {
            if (i == isImmobilefield.length - 1) {
              isImmoField = String.valueOf(isImmoField) + isImmobilefield[i];
            } else {
              isImmoField = String.valueOf(isImmoField) + isImmobilefield[i] + "#&#";
            } 
          }  
        String ordernumber = (request.getParameter("ordernumber") == null) ? "100" : request.getParameter("ordernumber");
        po.setOrdernumber(Integer.valueOf(ordernumber).intValue());
        po.setFormelseparamType(isImmoField);
        po.setFormelseparamValue(fieldValues);
        po.setFormelseparam(fieldNameValue);
        po.setDomainId(domainId);
        po.setNetAddressName(addressActionForm.getNetAddressName().trim());
        bd.add(po, (new StringBuilder(String.valueOf(curUserId))).toString(), curUserName, 
            request.getParameter("myAddressClass"), 
            new Byte(addressActionForm.getIsShare()), 
            addressActionForm.getShareScope());
        addressActionForm.setNetAddressName("");
        addressActionForm.setMyAddressClass("");
        addressActionForm.setNetAddressUrl("");
        addressActionForm.setShareToName("");
        addressActionForm.setShareScope("");
        addressActionForm.setSynopsis("");
        addressActionForm.setSaveImg("");
        addressActionForm.setOperate(Byte.parseByte("0"));
        addressActionForm.setSso(Byte.parseByte("0"));
        addressActionForm.setFormaction("");
        addressActionForm.setFormusername("");
        addressActionForm.setFormuserpassword("");
        addressActionForm.setSsologin(Byte.parseByte("0"));
        addressActionForm.setUsername("");
        addressActionForm.setPassword("");
        addressActionForm.setIsShare(Byte.parseByte("0"));
        addressActionForm.setIsShow(Byte.parseByte("0"));
        addressActionForm.setFormelseparam(fieldNameValue);
        request.setAttribute("addressClasses", bd.see((new StringBuilder(String.valueOf(curUserId))).toString()));
        return actionMapping.findForward("add");
      } 
      if ("load".equals(action)) {
        AddressPO po = bd.load(request.getParameter("editId"));
        BASE64 bASE64 = new BASE64();
        addressActionForm.setSynopsis(po.getSynopsis());
        addressActionForm.setSaveImg(po.getSaveImg());
        addressActionForm.setOperate(po.getOperate());
        addressActionForm.setSso(po.getSso());
        addressActionForm.setFormaction(po.getFormaction());
        addressActionForm.setFormusername(po.getFormusername());
        addressActionForm.setFormuserpassword(po.getFormuserpassword());
        addressActionForm.setSsologin(po.getSsologin());
        if (po.getUsername() == null || "".equals(po.getUsername())) {
          addressActionForm.setUsername("");
        } else {
          addressActionForm.setUsername(bASE64.BASE64Decoder(po.getUsername()));
        } 
        if (po.getPassword() == null || "".equals(po.getPassword())) {
          addressActionForm.setUsername("");
        } else {
          addressActionForm.setPassword(bASE64.BASE64Decoder(po.getPassword()));
        } 
        if (po.getFormelseparam() == null || "".equals(po.getFormelseparam())) {
          request.setAttribute("formelseparam", "");
        } else {
          request.setAttribute("formelseparam", po.getFormelseparam());
        } 
        if (po.getFormelseparamType() == null || "".equals(po.getFormelseparamType())) {
          request.setAttribute("formelseparamType", "");
        } else {
          request.setAttribute("formelseparamType", po.getFormelseparamType());
        } 
        if (po.getFormelseparamValue() == null || "".equals(po.getFormelseparamValue())) {
          request.setAttribute("formelseparamValue", "");
        } else {
          request.setAttribute("formelseparamValue", po.getFormelseparamValue());
        } 
        addressActionForm.setNetAddressName(po.getNetAddressName());
        addressActionForm.setMyAddressClass(String.valueOf(po.getAddressClass().getId()));
        AddressEJBBean ab = new AddressEJBBean();
        String urlNameString = String.valueOf(po.getNetAddressUrl());
        try {
          String urlName = ab.getNetAddressURL(request.getParameter("editId").toString(), curUserId);
          if (!"".equals(urlName) && urlName != null)
            urlNameString = urlName; 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        addressActionForm.setNetAddressUrl(urlNameString);
        addressActionForm.setIsShare(po.getIsShare());
        addressActionForm.setShareScope(String.valueOf(po.getShareToGroup()) + po.getShareToEmp() + po.getShareToOrg());
        addressActionForm.setShareToName(po.getShareToName());
        request.setAttribute("ordernumber", Integer.valueOf(po.getOrdernumber()));
        request.setAttribute("isUpdate", request.getParameter("isUpdate"));
        request.setAttribute("NetAddressUrl", po.getNetAddressUrl());
        request.setAttribute("addressClasses", bd.see((new StringBuilder(String.valueOf(curUserId))).toString()));
        return actionMapping.findForward("modi");
      } 
      if ("update".equals(action)) {
        String fieldNameValue = "";
        String[] fieldName = request.getParameterValues("fieldName");
        if (fieldName != null)
          for (int i = 0; i < fieldName.length; i++) {
            if (i == fieldName.length - 1) {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i];
            } else {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i] + "#&#";
            } 
          }  
        String fieldValues = "";
        String isImmoField = "";
        String[] fieldValue = request.getParameterValues("fieldValue");
        String[] isImmobilefield = request.getParameterValues("isImmobilefield");
        if (fieldValue != null)
          for (int i = 0; i < fieldValue.length; i++) {
            if (i == fieldValue.length - 1) {
              fieldValues = String.valueOf(fieldValues) + fieldValue[i];
            } else {
              fieldValues = String.valueOf(fieldValues) + fieldValue[i] + "#&#";
            } 
          }  
        if (isImmobilefield != null)
          for (int i = 0; i < isImmobilefield.length; i++) {
            if (i == isImmobilefield.length - 1) {
              isImmoField = String.valueOf(isImmoField) + isImmobilefield[i];
            } else {
              isImmoField = String.valueOf(isImmoField) + isImmobilefield[i] + "#&#";
            } 
          }  
        String oldPath = request.getParameter("oldPath");
        String isUpdate = request.getParameter("isUpdate");
        String userId = session.getAttribute("userId").toString();
        if (!"".equals(isUpdate) && isUpdate.equals(userId))
          oldPath = addressActionForm.getNetAddressUrl().toString(); 
        request.setAttribute("isUpdate", isUpdate);
        String addressId = addressActionForm.getEditId();
        String username = "";
        String password = "";
        String[] account = bd.getAccount(addressId, userId);
        if (account != null && account.length > 0) {
          username = account[0];
          password = account[1];
        } 
        addressActionForm.setDone("done");
        int Ssologin = addressActionForm.getSsologin();
        if (addressActionForm.getSso() == 0)
          Ssologin = 1; 
        String ordernumber = (request.getParameter("ordernumber") == null) ? "100" : request.getParameter("ordernumber");
        bd.update(new Byte(addressActionForm.getIsShare()), 
            addressActionForm.getSynopsis(), 
            addressActionForm.getSaveImg(), 
            new Integer(addressActionForm.getOperate()), 
            new Integer(addressActionForm.getSso()), 
            addressActionForm.getFormaction(), 
            addressActionForm.getFormusername(), 
            addressActionForm.getFormuserpassword(), 
            new Integer(Ssologin), 

            
            username, 
            password, 
            addressActionForm.getNetAddressName().trim(), 
            addressActionForm.getNetAddressUrl(), 
            addressActionForm.getEditId(), 
            addressActionForm.getShareScope(), 
            addressActionForm.getShareToName(), 
            request.getParameter("myAddressClass"), 
            fieldNameValue, 
            oldPath, 
            curUserName, (
            new StringBuilder(String.valueOf(curUserId))).toString(), domainId, fieldValues, isImmoField, Integer.valueOf(ordernumber).intValue());
        if (isImmoField == null || "".equals(isImmoField)) {
          request.setAttribute("formelseparamType", "");
        } else {
          request.setAttribute("formelseparamType", isImmoField);
        } 
        if (fieldValues == null || "".equals(fieldValues)) {
          request.setAttribute("formelseparamValue", "");
        } else {
          request.setAttribute("formelseparamValue", fieldValues);
        } 
        request.setAttribute("NetAddressUrl", addressActionForm.getNetAddressUrl());
        request.setAttribute("addressClasses", bd.see((new StringBuilder(String.valueOf(curUserId))).toString()));
        return actionMapping.findForward("modi");
      } 
      if ("setaccount".equals(action)) {
        String userId = session.getAttribute("userId").toString();
        String addressId = request.getParameter("addressId");
        String formelseparam = bd.getFormelseParam(addressId);
        String elseparam = bd.getElseParam(addressId, userId);
        elseparam = (new BASE64()).BASE64Decoder(elseparam);
        request.setAttribute("formelseparam", formelseparam);
        request.setAttribute("elseparam", elseparam);
        if (request.getParameter("userName") == null)
          return actionMapping.findForward("setaccount"); 
        String fieldNameValue = "";
        String[] fieldName = request.getParameterValues("fieldName");
        if (fieldName != null)
          for (int i = 0; i < fieldName.length; i++) {
            if (i == fieldName.length - 1) {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i];
            } else {
              fieldNameValue = String.valueOf(fieldNameValue) + fieldName[i] + "#&#";
            } 
          }  
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        String id = request.getParameter("addressId");
        fieldNameValue = (new BASE64()).BASE64Encoder(fieldNameValue);
        bd.setAccount(id, curUserId, userName, userPass, fieldNameValue);
        request.setAttribute("oprResult", "1");
        return actionMapping.findForward("setaccount");
      } 
    } 
    if ("list".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeFinalWhere(curUserId, curOrgId, 
          orgIdString, "po.shareToEmp", "po.shareToOrg", 
          "po.shareToGroup");
      list(request, wherePara);
      request.setAttribute("addressClasses", bd.see((new StringBuilder(String.valueOf(curUserId))).toString()));
    } else if ("show".equals(action)) {
      ManagerService mbd = new ManagerService();
      List list = bd.getBox(curUserId, curOrgId, orgIdString);
      request.setAttribute("showlist", list);
      return actionMapping.findForward("show");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    long userId = Long.parseLong(String.valueOf(request.getSession(true)
          .getAttribute("userId")));
    String netAddressName = request.getParameter("netAddressName");
    netAddressName = (netAddressName == null) ? "" : netAddressName;
    String addressClass = request.getParameter("myAddressClass");
    StringBuffer where = new StringBuffer("where ");
    where.append("po.netAddressName like '%").append(netAddressName).append(
        "%'");
    where.append(" and (");
    if ("none".equals(addressClass)) {
      where.append("po.createdEmpId=").append(userId).append(" or (")
        .append(wherePara).append(" and po.isShare=1)");
    } else if ("share".equals(addressClass)) {
      where.append("(").append(wherePara).append("or po.createdEmpId=")
        .append(userId).append(")").append(" and po.isShare=1 ");
    } else {
      where.append(" po.addressClass.id=").append(addressClass).append(
          " and po.createdEmpId =").append(userId);
    } 
    where.append(")");
    where.append(" and (po.shareDelUserId not like '%*").append(userId).append("*%' or po.shareDelUserId is null or po.shareDelUserId='')");
    where.append(" and po.domainId=" + domainId + " order by po.ordernumber,po.id desc");
    Page page = new Page(
        " po.id,po.netAddressName,po.netAddressUrl,po.createdEmpName,po.isShare,po.deskShowUser,po.addressClass.className,po.createdEmpId ,po.shareToEmp,po.shareToOrg,po.shareToGroup,po.addressClass.id,po.synopsis,po.sso,po.ssologin,po.saveImg,po.formusername,po.formuserpassword,po.formelseparam,po.ordernumber", 
        " com.js.oa.personalwork.netaddress.po.AddressPO po", 
        where.toString());
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", 
        "action,netAddressName,myAddressClass");
  }
}
