package com.js.system.action.organizationmanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadOrgTemplateAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
    String type = httpServletRequest.getParameter("type");
    String filename = "";
    String name = "";
    if ("1".equals(type)) {
      filename = "orgtemplate.xls";
      name = "组织模版.xls";
    } else if ("2".equals(type)) {
      filename = "usertemplate.xls";
      name = "用户模版.xls";
    } else if ("3".equals(type)) {
      filename = "linkmantemplate.xls";
      name = "联系人模版.xls";
    } else if ("5".equals(type)) {
      filename = "usersnkey.xls";
      name = "用户SN密钥模板.xls";
    } else if ("6".equals(type)) {
      filename = "fsalary.xls";
      name = "用户工资记录模板.xls";
    } else if ("7".equals(type)) {
      filename = "fpayable.xls";
      name = "用户其他应付模板.xls";
    } else if ("8".equals(type)) {
      filename = "fexpense.xls";
      name = "用户报销模板.xls";
    } else if ("9".equals(type)) {
      filename = "ftax.xls";
      name = "用户报税模板.xls";
    } else if ("10".equals(type)) {
      filename = "duty.xls";
      name = "职务模板.xls";
    } else if ("11".equals(type)) {
      filename = "station.xls";
      name = "岗位模板.xls";
    } else if ("daka".equals(type)) {
      filename = "daka.xls";
      name = "考勤模板.xls";
    } else if ("12".equals(type)) {
      filename = "office.xls";
      name = "办公室模板.xls";
    } else if ("13".equals(type)) {
      filename = "equipment.xls";
      name = "设备信息模板.xls";
    } else if ("14".equals(type)) {
      filename = "goods.xls";
      name = "物品信息模板.xls";
    } else if ("15".equals(type)) {
      filename = "employeetemplate.xls";
      name = "人事信息模板.xls";
    } else if ("17".equals(type)) {
      filename = "archivestemplate.xls";
      name = "档案模板.xls";
    } else if ("rsxc".equals(type)) {
      filename = "rsxc.xls";
      name = "薪酬导入模板.xls";
    } else if ("lddbmonth".equals(type)) {
      filename = "supervisemonthtemplate.xls";
      name = "兰州大学督办月度重点工作导入模板.xls";
    } else if ("lddbyear".equals(type)) {
      filename = "superviseyeartemplate.xls";
      name = "兰州大学督办年度重点工作导入模板.xls";
    } else if ("lddbtask".equals(type)) {
      filename = "supervisetasktemplate.xls";
      name = "兰州大学督查督办事项任务分解表导入模板.xls";
    } else {
      filename = "salary.xls";
      name = "工资条模版.xls";
    } 
    String realPath = httpServletRequest.getRealPath("/template/" + filename);
    httpServletResponse.setContentType("application/msword");
    httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"), "iso8859-1"));
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(realPath));
      bos = new BufferedOutputStream((OutputStream)httpServletResponse.getOutputStream());
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        bos.write(buff, 0, bytesRead); 
    } catch (IOException e) {
      System.out.println("出现IOException." + e);
    } finally {
      if (bis != null)
        bis.close(); 
      if (bos != null)
        bos.close(); 
    } 
    return null;
  }
}
