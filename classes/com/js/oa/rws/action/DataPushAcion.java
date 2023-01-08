package com.js.oa.rws.action;

import com.js.oa.rws.bean.DatapushBean;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DataPushAcion extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws HibernateException, SQLException {
    String action = request.getParameter("action");
    DatapushBean db = new DatapushBean();
    String flag = "list";
    if ("list".equals(action)) {
      List<String[]> list = db.getDataList();
      request.setAttribute("list", list);
    } 
    if ("send".equals(action)) {
      String id = request.getParameter("id");
      String success = db.DataSend(id);
      request.setAttribute("success", success);
    } 
    if ("view".equals(action)) {
      String id = request.getParameter("id");
      List<String[]> data = db.view(id);
      request.setAttribute("data", data);
      flag = "view";
    } 
    return actionMapping.findForward(flag);
  }
}
