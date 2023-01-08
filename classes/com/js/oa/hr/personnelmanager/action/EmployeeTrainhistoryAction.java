package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeTrainhistoryBD;
import com.js.system.vo.usermanager.TrainhistoryVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeTrainhistoryAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeTrainhistoryActionForm employeeTrainhistoryActionForm = 
      (EmployeeTrainhistoryActionForm)actionForm;
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String tag = "list";
    String userId = httpServletRequest.getSession().getAttribute("userId").toString();
    String empID = httpServletRequest.getParameter("empID");
    String open = httpServletRequest.getParameter("open");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    httpServletRequest.setAttribute("open", open);
    httpServletRequest.setAttribute("empID", empID);
    if (action.equals("list")) {
      tag = "list";
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
      employeeTrainhistoryActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeTrainhistoryActionForm, httpServletRequest)) {
        if (action.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("modify")) {
      tag = "modify";
      Long id = new Long((httpServletRequest.getParameter("trainhistoryId") == null) ? 
          "0" : 
          httpServletRequest.getParameter("trainhistoryId"));
      TrainhistoryVO vo = (new EmployeeTrainhistoryBD()).load(id);
      employeeTrainhistoryActionForm.setTrainName(vo.getTrainName());
      employeeTrainhistoryActionForm.setTrainUnit(vo.getTrainUnit());
      employeeTrainhistoryActionForm.setTrainMemo(vo.getTrainMemo());
      employeeTrainhistoryActionForm.setId(id);
      employeeTrainhistoryActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("beginDate", vo.getBeginDate());
      httpServletRequest.setAttribute("endDate", vo.getEndDate());
    } else if (action.equals("update")) {
      tag = "modify";
      TrainhistoryVO vo = new TrainhistoryVO();
      vo.setBeginDate(new Date(httpServletRequest.getParameter(
              "beginDate")));
      vo.setEndDate(new Date(httpServletRequest.getParameter("endDate")));
      vo.setTrainName(employeeTrainhistoryActionForm.getTrainName());
      vo.setTrainUnit(employeeTrainhistoryActionForm.getTrainUnit());
      vo.setTrainMemo(employeeTrainhistoryActionForm.getTrainMemo());
      if ((new EmployeeTrainhistoryBD()).update(vo, 
          employeeTrainhistoryActionForm.getEmpID(), 
          employeeTrainhistoryActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("trainhistoryId") != null && 
        !"0".equals(httpServletRequest.getParameter("trainhistoryId")))
        (new EmployeeTrainhistoryBD()).delete(new Long(httpServletRequest.getParameter("trainhistoryId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = 
      "po.id,po.trainName,po.trainUnit,po.beginDate,po.endDate,po.trainMemo";
    String fromSQL = 
      "com.js.system.vo.usermanager.TrainhistoryVO po join po.employeeVO ppo";
    String whereSQL = "where ppo.empId=" + new Long(empID) + 
      " order by po.beginDate desc ";
    int pageSize = 999999;
    int offset = 0;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    int currentPage = offset / pageSize + 1;
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("trainHistoryList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeTrainhistoryActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    TrainhistoryVO vo = new TrainhistoryVO();
    vo.setBeginDate(new Date(request.getParameter("beginDate")));
    vo.setEndDate(new Date(request.getParameter("endDate")));
    vo.setTrainMemo(form.getTrainMemo());
    vo.setTrainName(form.getTrainName());
    vo.setTrainUnit(form.getTrainUnit());
    retflag = (new EmployeeTrainhistoryBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
