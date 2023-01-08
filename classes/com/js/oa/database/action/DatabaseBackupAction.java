package com.js.oa.database.action;

import com.js.oa.database.po.DatabaseBackupSetPO;
import com.js.oa.database.util.BackupAndRecover;
import com.js.oa.database.util.BackupAndRecoverForOracle;
import com.js.oa.database.util.XmlUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.DocumentException;

public class DatabaseBackupAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws DocumentException, IOException {
    HttpSession session = request.getSession(true);
    XmlUtil xmlUtil = new XmlUtil();
    String action = request.getParameter("action");
    if ("list".equals(action)) {
      List list = xmlUtil.getNodeList();
      request.setAttribute("list", list);
      return actionMapping.findForward("list");
    } 
    if ("load".equals(action)) {
      String datename = request.getParameter("date");
      String type = xmlUtil.searchBackupType();
      if ("oracle".equals(type))
        BackupAndRecoverForOracle.imp(datename); 
      if ("mysql".equals(type))
        BackupAndRecover.load(datename); 
    } 
    if ("setinput".equals(action)) {
      DatabaseBackupSetPO databaseBackupSetPO = xmlUtil.getSet();
      request.setAttribute("databaseBackupSetPO", databaseBackupSetPO);
      return actionMapping.findForward("set");
    } 
    if ("set".equals(action)) {
      String type = request.getParameter("type");
      String amount = request.getParameter("amount");
      xmlUtil.set(type, amount);
    } 
    return null;
  }
}
