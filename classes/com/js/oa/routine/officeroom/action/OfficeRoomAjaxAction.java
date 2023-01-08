package com.js.oa.routine.officeroom.action;

import com.js.oa.oasysremind.bean.JsonData;
import com.js.oa.routine.officeroom.service.OfficeRoomBD;
import com.js.system.manager.service.ManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OfficeRoomAjaxAction extends Action {
  private static final long serialVersionUID = 1L;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    HttpSession session = req.getSession(true);
    resp.setContentType("text/xml; charset=GBK");
    String type = (req.getParameter("type") == null) ? "" : req.getParameter("type");
    String action = (req.getParameter("action") == null) ? "officeadd" : req.getParameter("action");
    PrintWriter out = resp.getWriter();
    List list = new ArrayList();
    OfficeRoomBD officeRoomBD = new OfficeRoomBD();
    if (type.equals("getBuildCount")) {
      String code = null;
      code = req.getParameter("buildId");
      List<JsonData> listJson = new ArrayList<JsonData>();
      JsonData jsonDate = null;
      List<Object[]> listCoun = null;
      try {
        listCoun = officeRoomBD.getListBuild(code.toString());
        if (listCoun != null && listCoun.size() != 0)
          for (int i = 0; i < listCoun.size(); i++) {
            jsonDate = new JsonData();
            Object[] obj = listCoun.get(i);
            jsonDate.setOther(obj[0].toString());
            jsonDate.setId(obj[1].toString());
            jsonDate.setName(obj[2].toString());
            listJson.add(jsonDate);
          }  
        JSONArray jsonArray = JSONArray.fromObject(listJson);
        out.print(jsonArray.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return null;
    } 
    if ("getBuildNumber".equals(type)) {
      String buildId = req.getParameter("applayBuildId");
      String buildNumber = req.getParameter("buildNumber");
      HttpSession httpSession = req.getSession();
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      ManagerService managerBD = new ManagerService();
      String buildClassWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
          "po.officereader", 
          "po.officereadorg", 
          "po.officereadgroup");
      List<JsonData> listJson = new ArrayList<JsonData>();
      JsonData jsonDate = null;
      List<Object[]> listCoun = null;
      try {
        listCoun = officeRoomBD.getListBuildNumber(buildId.toString(), buildNumber, buildClassWhere);
        if (listCoun != null && listCoun.size() != 0)
          for (int i = 0; i < listCoun.size(); i++) {
            jsonDate = new JsonData();
            Object[] obj = listCoun.get(i);
            jsonDate.setOther(obj[0].toString());
            jsonDate.setId(obj[2].toString());
            jsonDate.setName(obj[1].toString());
            listJson.add(jsonDate);
          }  
        JSONArray jsonArray = JSONArray.fromObject(listJson);
        out.print(jsonArray.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return null;
    } 
    return actionMapping.findForward(action);
  }
}
