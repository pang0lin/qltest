package com.js.oa.form;

import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.po.SsMasterPO;
import com.js.oa.routine.resource.service.IntoOutStockBD;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ResourceWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    Long Id = new Long(-1L);
    IntoOutStockBD bd = new IntoOutStockBD();
    Object[] para = setPara(httpServletRequest);
    if (httpServletRequest.getParameter("mode").equals("returnStock") || 
      httpServletRequest.getParameter("mode").equals("outStock")) {
      Id = bd.saveOutStock(setPOForSave(httpServletRequest), para);
    } else {
      Map result = (new IntoOutStockBD()).save(setIntoPOForSaveUpdate(
            httpServletRequest), para);
      Id = (Long)result.get("id");
    } 
    return Id;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    IntoOutStockBD bd = new IntoOutStockBD();
    Long editId = new Long(-1L);
    if (httpServletRequest.getParameter("mode").equals("returnStock") || 
      httpServletRequest.getParameter("mode").equals("outStock")) {
      editId = Long.valueOf(httpServletRequest.getParameter("ssMasterId"));
      SsMasterPO po = new SsMasterPO();
      po = setPOForUpdate(httpServletRequest);
      po.setId(editId);
      Object[] para = setPara(httpServletRequest);
      bd.updateOutStock(po, para);
    } else {
      PtMasterPO ptMasterPO = new PtMasterPO();
      ptMasterPO = setIntoPOForSaveUpdate(httpServletRequest);
      ptMasterPO.setId(new Long(httpServletRequest.getParameter(
              "ptMasterId")));
      if (bd.updateIntoStock(ptMasterPO, setPara(httpServletRequest))
        .booleanValue())
        editId = new Long(1L); 
    } 
    return editId;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    IntoOutStockBD bd = new IntoOutStockBD();
    Long editId = new Long(-1L);
    if (httpServletRequest.getParameter("mode").equals("returnStock") || 
      httpServletRequest.getParameter("mode").equals("outStock")) {
      editId = Long.valueOf(httpServletRequest.getParameter(
            "ssMasterId"));
      SsMasterPO po = new SsMasterPO();
      po = setPOForComplete(httpServletRequest);
      po.setId(editId);
      Object[] para = setPara(httpServletRequest);
      bd.updateOutStock(po, para);
      bd.updateOutStockAmnout(po, para);
    } else {
      PtMasterPO ptMasterPO = new PtMasterPO();
      ptMasterPO = setIntoPOForSaveUpdate(httpServletRequest);
      ptMasterPO.setId(new Long(httpServletRequest.getParameter(
              "ptMasterId")));
      ptMasterPO.setCheckFlag("Y");
      ptMasterPO.setCheckMan(new Long(httpSession.getAttribute("userId")
            .toString()));
      ptMasterPO.setCheckManName(httpSession.getAttribute("userName")
          .toString());
      ptMasterPO.setCheckDate(new Date());
      if (bd.updateIntoStock(ptMasterPO, setPara(httpServletRequest))
        .booleanValue())
        editId = new Long(1L); 
    } 
    return editId;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    Long editId = new Long(-1L);
    IntoOutStockBD bd = new IntoOutStockBD();
    if (httpServletRequest.getParameter("mode").equals("returnStock") || 
      httpServletRequest.getParameter("mode").equals("outStock")) {
      editId = Long.valueOf(httpServletRequest.getParameter("ssMasterId"));
      if (httpServletRequest.getParameter("backToStep") == null) {
        bd.updateOutStockCheck(httpServletRequest.getParameter(
              "ssMasterId"), 
            "B");
      } else if (httpServletRequest.getParameter("backToStep").equals("0")) {
        bd.updateOutStockCheck(httpServletRequest.getParameter(
              "ssMasterId"), 
            "B");
      } 
    } else {
      editId = Long.valueOf(httpServletRequest.getParameter("ptMasterId"));
      if (httpServletRequest.getParameter("backToStep") == null) {
        bd.updateIntoStockCheck(httpServletRequest.getParameter(
              "ptMasterId"), 
            "B");
      } else if (httpServletRequest.getParameter("backToStep").equals("0")) {
        bd.updateIntoStockCheck(httpServletRequest.getParameter(
              "ptMasterId"), 
            "B");
      } 
    } 
    return editId;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    Long editId = new Long(-1L);
    IntoOutStockBD bd = new IntoOutStockBD();
    if (httpServletRequest.getParameter("mode").equals("returnStock") || 
      httpServletRequest.getParameter("mode").equals("outStock")) {
      editId = Long.valueOf(httpServletRequest.getParameter("ssMasterId"));
      if (httpServletRequest.getParameter("backToStep") == null) {
        bd.updateOutStockCheck(httpServletRequest.getParameter("ssMasterId"), "B");
        SsMasterPO po = new SsMasterPO();
        po = setPOForUpdate(httpServletRequest);
        po.setId(editId);
        Object[] para = setPara(httpServletRequest);
        bd.updateBackOutStockAmnout(po, para);
      } else if (httpServletRequest.getParameter("backToStep").equals("0")) {
        bd.updateOutStockCheck(httpServletRequest.getParameter("ssMasterId"), "B");
      } 
    } else {
      editId = Long.valueOf(httpServletRequest.getParameter("ptMasterId"));
      if (httpServletRequest.getParameter("backToStep") == null) {
        bd.updateIntoStockCheck(httpServletRequest.getParameter(
              "ptMasterId"), 
            "B");
      } else if (httpServletRequest.getParameter("backToStep").equals("0")) {
        bd.updateIntoStockCheck(httpServletRequest.getParameter(
              "ptMasterId"), 
            "B");
      } 
    } 
    return editId;
  }
  
  private SsMasterPO setPOForSave(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    SsMasterPO po = new SsMasterPO();
    po.setSsStock(new Long(request.getParameter("stock")));
    po.setSsMan(request.getParameter("ssMan"));
    po.setSsMoney(new Float(request.getParameter("ptMoney")));
    po.setRemark(request.getParameter("remark"));
    po.setMakeMan(new Long(httpSession.getAttribute("userId").toString()));
    po.setMakeDate(new Date());
    po.setCheckFlag("N");
    po.setSsDate(new Date(request.getParameter("ssDate")));
    po.setSsDept(new Long((request.getParameter("ssDept") == null) ? request.getParameter("ssOrg") : request.getParameter("ssDept")));
    po.setSsHaveWorkFlow(new Long(1L));
    po.setDomainid(Integer.parseInt(httpSession.getAttribute("domainId")
          .toString()));
    po.setSsOrg(request.getParameter("ssOrg"));
    po.setSsOrgName(request.getParameter("ssOrgName"));
    po.setSsTypeDefine(request.getParameter("ssTypeDefine"));
    po.setSsOutFlag("0");
    po.setSsMode("2");
    po.setSsUseManID(request.getParameter("ssUseManID"));
    po.setSsUseMan(request.getParameter("ssUseMan"));
    if (request.getParameter("mode").equals("returnStock")) {
      po.setSsMode("3");
      po.setSsMoney(new Float("-" + request.getParameter("ptMoney")));
    } 
    return po;
  }
  
  private PtMasterPO setIntoPOForSaveUpdate(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    PtMasterPO ptMasterPO = new PtMasterPO();
    ptMasterPO.setPtStock(new Long(request.getParameter("stock")));
    ptMasterPO.setPtSupp(request.getParameter("supp"));
    ptMasterPO.setPtMan(request.getParameter("man"));
    ptMasterPO.setPtMoney(new Float(request.getParameter("ptMoney")));
    ptMasterPO.setRemark(request.getParameter("remark"));
    ptMasterPO.setMakeMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    ptMasterPO.setMakeDate(new Date());
    ptMasterPO.setCheckFlag("N");
    ptMasterPO.setPtDate(new Date(request.getParameter("ptDate")));
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      ptMasterPO.setDomainid(Integer.parseInt(domainId)); 
    ptMasterPO.setPtOrg(request.getParameter("ptOrg"));
    ptMasterPO.setPtOrgName(request.getParameter("ptOrgName"));
    ptMasterPO.setPtMode("1");
    ptMasterPO.setPtTypeDefine(request.getParameter("ptTypeDefine"));
    ptMasterPO.setPtHandleName(request.getParameter("ptHandleName"));
    ptMasterPO.setInvoiceNO(request.getParameter("invoiceNO"));
    ptMasterPO.setPtHaveWorkFlow("1");
    if (request.getParameter("mode").equals("salesreturn")) {
      ptMasterPO.setPtMode("4");
      ptMasterPO.setPtMoney(new Float("-" + 
            request.getParameter("ptMoney")));
    } 
    return ptMasterPO;
  }
  
  private SsMasterPO setPOForUpdate(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    SsMasterPO po = new SsMasterPO();
    po.setSsMan(request.getParameter("ssMan"));
    po.setSsMoney(new Float(request.getParameter("ptMoney")));
    po.setRemark(request.getParameter("remark"));
    if ("check".equals(request.getParameter("myflag"))) {
      po.setCheckFlag("Y");
      po.setCheckMan(new Long(httpSession.getAttribute("userId")
            .toString()));
      po.setCheckManName(httpSession.getAttribute("userName")
          .toString());
      po.setCheckDate(new Date());
    } else {
      po.setCheckFlag("N");
    } 
    po.setSsDate(new Date(request.getParameter("ssDate")));
    po.setSsDept(new Long((request.getParameter("ssDept") == null) ? request.getParameter("ssOrg") : request.getParameter("ssDept")));
    po.setSsHaveWorkFlow(new Long(1L));
    po.setDomainid(Integer.parseInt(httpSession.getAttribute("domainId")
          .toString()));
    po.setSsOrg(request.getParameter("ssOrg"));
    po.setSsOrgName(request.getParameter("ssOrgName"));
    po.setSsTypeDefine(request.getParameter("ssTypeDefine"));
    po.setSsMode("2");
    if (request.getParameter("mode").equals("returnStock")) {
      po.setSsMode("3");
      po.setSsMoney(new Float("-" + request.getParameter("ptMoney")));
    } 
    return po;
  }
  
  private SsMasterPO setPOForComplete(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    SsMasterPO po = new SsMasterPO();
    po.setSsDate(new Date(request.getParameter(
            "ssDate")));
    po.setSsDept(new Long((request.getParameter(
            "ssDept") == null) ? request.getParameter("ssOrg") : request.getParameter(
            "ssDept")));
    po.setSsMan(request.getParameter("ssMan"));
    po.setSsMoney(new Float(request.getParameter(
            "ptMoney")));
    po.setRemark(request.getParameter("remark"));
    po.setCheckFlag("Y");
    po.setCheckMan(new Long(httpSession.getAttribute("userId")
          .toString()));
    po.setCheckManName(httpSession.getAttribute("userName")
        .toString());
    po.setCheckDate(new Date());
    po.setSsOrg(request.getParameter("ssOrg"));
    po.setSsOrgName(request.getParameter("ssOrgName"));
    po.setSsTypeDefine(request.getParameter("ssTypeDefine"));
    po.setSsMode("2");
    if (request.getParameter("mode").equals("returnStock")) {
      po.setSsMode("3");
      po.setSsMoney(new Float("-" + request.getParameter("ptMoney")));
    } 
    return po;
  }
  
  private Object[] setPara(HttpServletRequest request) {
    String[] goodsId = request.getParameterValues("goodsId");
    String[] amount = request.getParameterValues("amount");
    String[] price = request.getParameterValues("price");
    String[] money = request.getParameterValues("money");
    String[] goodsName = request.getParameterValues("goodsName");
    String[] goodsUnit = request.getParameterValues("goodsUnit");
    String[] goodsSpecs = request.getParameterValues("goodsSpecs");
    String[] returnReason = (request.getParameterValues("returnReason") == null) ? 
      new String[goodsId.length] : request.getParameterValues("returnReason");
    String stockId = request.getParameter("stock");
    String[] stockIdS = { stockId };
    if (request.getParameter("mode").equals("returnStock") || 
      request.getParameter("mode").equals("salesreturn"))
      for (int i = 0; i < amount.length; i++) {
        amount[i] = "-" + amount[i];
        money[i] = "-" + money[i];
      }  
    Object[] para = { goodsId, amount, price, money, goodsName, goodsUnit, goodsSpecs, returnReason, stockIdS };
    return para;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("ssMasterId") != null && 
      !"".equals(httpServletRequest.getParameter("ssMasterId"))) {
      String editId = httpServletRequest.getParameter("ssMasterId");
      IntoOutStockBD bd = new IntoOutStockBD();
      bd.deleteOutStock(editId, "9");
    } 
    if (httpServletRequest.getParameter("ptMasterId") != null && 
      !"".equals(httpServletRequest.getParameter("ptMasterId"))) {
      String editId = httpServletRequest.getParameter("ptMasterId");
      IntoOutStockBD bd = new IntoOutStockBD();
      bd.deleteIntoStock(editId);
    } 
  }
}
