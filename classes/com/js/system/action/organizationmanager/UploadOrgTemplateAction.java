package com.js.system.action.organizationmanager;

import com.js.oa.archives.po.ArchivesFilePO;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.hr.finance.bean.FSalaryRsNewEJBBean;
import com.js.oa.hr.finance.service.FExpenseService;
import com.js.oa.hr.finance.service.FPayableService;
import com.js.oa.hr.finance.service.FSalaryService;
import com.js.oa.hr.finance.service.FTaxService;
import com.js.oa.hr.officemanager.bean.DutyEJBBean;
import com.js.oa.hr.officemanager.service.DutyBD;
import com.js.oa.hr.personnelmanager.bean.NewDutyEJBBean;
import com.js.oa.hr.personnelmanager.po.SalaryPO;
import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.hr.personnelmanager.service.PersonalKindBD;
import com.js.oa.hr.personnelmanager.service.SalaryBD;
import com.js.oa.hr.personnelmanager.service.WorkAddressBD;
import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.personalwork.person.po.PersonPO;
import com.js.oa.personalwork.person.service.PersonClassBD;
import com.js.oa.personalwork.person.service.PersonOwnBD;
import com.js.oa.routine.boardroom.bean.EquipmentEJBBean;
import com.js.oa.routine.officeroom.bean.OfficeRoomEJBBean;
import com.js.oa.routine.resource.bean.GoodsEJBBean;
import com.js.oa.security.seamoon.bean.SeaMoonEJBBean;
import com.js.oa.security.seamoon.po.SecSeaMoon;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.MD5;
import com.js.util.util.ParameterFilter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.hibernate.Session;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadOrgTemplateAction extends Action {
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    UploadOrgTemplateForm uploadForm = (UploadOrgTemplateForm)actionForm;
    String domainId = httpServletRequest.getSession().getAttribute("domainId").toString();
    String curOrgId = httpServletRequest.getSession().getAttribute("orgId").toString();
    String type = httpServletRequest.getParameter("type");
    String savetype = httpServletRequest.getParameter("savetype");
    String userType = httpServletRequest.getParameter("userType");
    if (!ParameterFilter.checkParameter(type) || !ParameterFilter.checkParameter(savetype) || 
      !ParameterFilter.checkParameter(userType))
      httpServletResponse.sendRedirect(String.valueOf(httpServletRequest.getContextPath()) + "/public/jsp/inputerror.jsp"); 
    httpServletRequest.setAttribute("type", type);
    httpServletRequest.setAttribute("userType", userType);
    String realPath = "";
    String message = "";
    String succeed = "0";
    String messageString = "";
    List<PersonPO> exlList = new ArrayList();
    SysSetupReader sysRed = SysSetupReader.getInstance();
    if ("1".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/orgtemplate.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      int newInputNum = 0;
      int oldInputNum = 0;
      int badInputNum = 0;
      if (this.sheet != null) {
        OrganizationBD organizationBD = new OrganizationBD();
        int rows = this.sheet.getRows();
        String name = "";
        String bianma = "";
        String sbianma = "";
        String parentIdString = "";
        String currentOrderCode = "";
        Long orgid = null;
        List orgidList = new ArrayList();
        try {
          String orgtext = this.sheet.getCell(0, 2).getContents().trim();
          if ("组织名称".equals(orgtext)) {
            for (int i = 4; i < rows; i++) {
              succeed = "0";
              OrganizationVO oldOrganizationVO = null;
              OrganizationVO organizationVO = new OrganizationVO();
              List<String> organizationOrgSerialList = organizationBD.selectAllOrgSerial();
              name = this.sheet.getCell(0, i).getContents().trim();
              bianma = this.sheet.getCell(1, i).getContents().trim();
              sbianma = this.sheet.getCell(2, i).getContents().trim();
              if (name == null || "".equals(name)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行组织名称不能为空！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              if (bianma == null || "".equals(bianma)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行组织编码不能为空！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              if (sbianma != null && !"".equals(sbianma)) {
                oldOrganizationVO = organizationBD.getOrgBySerial(sbianma);
                if (oldOrganizationVO == null) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行所属组织不存在！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
              } 
              if (!"1".equals(succeed))
                if (organizationOrgSerialList.contains(bianma)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行组织编码已存在！<br>";
                  if (!"1".equals(succeed)) {
                    if (savetype != null && "2".equals(savetype)) {
                      oldInputNum++;
                      organizationVO = organizationBD.getOrgBySerial(bianma);
                      organizationVO.setOrgName(name);
                      organizationVO.setOrgSimpleName(this.sheet.getCell(3, i).getContents().trim());
                      organizationVO.setOrgDescripte(this.sheet.getCell(4, i).getContents().trim());
                      if (oldOrganizationVO == null) {
                        organizationVO.setOrgParentOrgId(0L);
                        organizationVO.setOrgType("0");
                      } else {
                        organizationVO.setOrgParentOrgId(oldOrganizationVO.getOrgId().longValue());
                        parentIdString = oldOrganizationVO.getOrgIdString();
                        organizationVO.setOrgType("1");
                      } 
                      boolean b = false;
                      if (SystemCommon.getAudit() == 0) {
                        b = organizationBD.update(organizationVO, currentOrderCode, 
                            parentIdString, Integer.valueOf(1), "0");
                      } else {
                        HttpSession session = httpServletRequest.getSession(true);
                        String[] log = { session.getAttribute("userId").toString(), 
                            session.getAttribute("userName").toString(), 
                            session.getAttribute("orgId").toString(), "3" };
                        b = organizationBD.update(organizationVO, currentOrderCode, 
                            parentIdString, Integer.valueOf(1), "1", log);
                      } 
                      succeed = "1";
                    } 
                    succeed = "1";
                  } 
                }  
              if (!"1".equals(succeed)) {
                newInputNum++;
                if (oldOrganizationVO == null) {
                  organizationVO.setOrgParentOrgId(0L);
                  organizationVO.setOrgType("0");
                  parentIdString = "";
                } else {
                  organizationVO.setOrgParentOrgId(oldOrganizationVO.getOrgId().longValue());
                  parentIdString = oldOrganizationVO.getOrgIdString();
                  organizationVO.setOrgType("1");
                } 
                organizationVO.setOrgName(name);
                currentOrderCode = organizationBD
                  .getMaxOrgOrderCode(organizationVO.getOrgParentOrgId());
                organizationVO.setDomainId("0");
                organizationVO.setOrgSerial(bianma);
                organizationVO.setOrgSimpleName(this.sheet.getCell(3, i).getContents().trim());
                organizationVO.setOrgDescripte(this.sheet.getCell(4, i).getContents().trim());
                if (SystemCommon.getAudit() == 0) {
                  orgid = 
                    Long.valueOf(organizationBD.exselAdd(organizationVO, currentOrderCode, parentIdString, Integer.valueOf(1)));
                } else {
                  HttpSession session = httpServletRequest.getSession(true);
                  String[] log = { session.getAttribute("userId").toString(), 
                      session.getAttribute("userName").toString(), 
                      session.getAttribute("orgId").toString(), "3" };
                  orgid = organizationBD.exselAdd(organizationVO, currentOrderCode, parentIdString, 
                      Integer.valueOf(1), log);
                } 
              } 
            } 
            badInputNum = rows - newInputNum - oldInputNum - 3;
          } else {
            if (!"1".equals(succeed))
              succeed = "1"; 
            message = String.valueOf(message) + "选择的模版不正确！<br>";
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
      if (newInputNum != 0)
        httpServletRequest.setAttribute("newInputNum", "有" + newInputNum + "个组织新增<br/>"); 
      if (oldInputNum != 0)
        httpServletRequest.setAttribute("oldInputNum", "有" + oldInputNum + "个组织覆盖<br/>"); 
      if (badInputNum != 0)
        httpServletRequest.setAttribute("badInputNum", "有" + badInputNum + "条无效记录<br/>"); 
      httpServletRequest.setAttribute("message", message);
      httpServletRequest.setAttribute("newType", type);
      httpServletRequest.setAttribute("succeed", succeed);
      return actionMapping.findForward("result");
    } 
    if ("2".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/usertemplate.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      int newInputNum = 0;
      int oldInputNum = 0;
      int badInputNum = 0;
      if (this.sheet != null) {
        UserBD userBD = new UserBD();
        OrganizationBD organizationBD = new OrganizationBD();
        int rows = this.sheet.getRows();
        String name = "";
        String orgNo = "";
        String username = "";
        Long userid = null;
        RoleBD roleBD = new RoleBD();
        String dutyName = "";
        DutyBD dutyBD = new DutyBD();
        try {
          String usertext = this.sheet.getCell(0, 2).getContents().trim();
          if ("真实姓名".equals(usertext)) {
            int canImport = userBD.canImportUserNum("0").intValue();
            if ("0".equals(SystemCommon.getLicType()) && canImport < rows - 4) {
              if (!"1".equals(succeed))
                succeed = "1"; 
              message = String.valueOf(message) + "授权用户数不够，您最多只能导入" + canImport + "个用户！<br>";
            } else {
              String defaultBrowseRange = SystemCommon.getDefaultBrowseRange();
              for (int i = 4; i < rows; i++) {
                succeed = "0";
                EmployeeVO employeeVO = new EmployeeVO();
                OrganizationVO oldOrganizationVO = null;
                List<String> UserAccountsList = userBD.selectAllUserAccounts();
                name = this.sheet.getCell(0, i).getContents().trim();
                orgNo = this.sheet.getCell(6, i).getContents().trim();
                username = this.sheet.getCell(1, i).getContents().trim();
                if (name == null || "".equals(name)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行用户姓名不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
                if (!"1".equals(succeed) && (
                  orgNo == null || "".equals(orgNo))) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行组织编码不能为空！<br>";
                  succeed = "1";
                } 
                try {
                  oldOrganizationVO = organizationBD.getOrgBySerial(orgNo);
                  if (oldOrganizationVO == null && 
                    !"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行所属组织不存在！<br>";
                    succeed = "1";
                  } 
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行所属组织不存在！<br>";
                    succeed = "1";
                  } 
                } 
                if (!"1".equals(succeed)) {
                  String sex = this.sheet.getCell(4, i).getContents().trim();
                  if ("女".equals(sex)) {
                    if (!"1".equals(succeed))
                      employeeVO.setEmpSex((byte)1); 
                  } else if ("男".equals(sex)) {
                    if (!"1".equals(succeed))
                      employeeVO.setEmpSex((byte)0); 
                  } else {
                    message = String.valueOf(message) + "第" + (i + 1) + "行用户性别填写错误！<br>";
                    succeed = "1";
                  } 
                } 
                List<Long> roleIdList = new ArrayList();
                try {
                  String roleName = "";
                  roleName = this.sheet.getCell(7, i).getContents().trim();
                  if (roleName != null && !"".equals(roleName)) {
                    String newroleName = roleName.replace("，", ",");
                    String[] newroleNames = newroleName.split(",");
                    for (int k = 0; k < newroleNames.length; k++) {
                      List<String> validateRoleIdList = roleBD.searchIdByName(newroleNames[k]);
                      if (validateRoleIdList.size() < 2) {
                        String nerolename = validateRoleIdList.get(0);
                        if (!"1".equals(succeed)) {
                          message = String.valueOf(message) + "第" + (i + 1) + "行" + nerolename + "角色不存在！<br>";
                          succeed = "1";
                        } 
                      } 
                      if (!"1".equals(succeed))
                        roleIdList.add((Long)validateRoleIdList.get(1)); 
                    } 
                  } 
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行" + "角色不存在！<br>";
                    succeed = "1";
                  } 
                } 
                String empPositionName = this.sheet.getCell(19, i).getContents().trim();
                try {
                  if (empPositionName != null && !"".equals(empPositionName)) {
                    Long empPositionId = roleBD.searchEmpPositionIdByName(empPositionName);
                    if (empPositionId == null) {
                      if (!"1".equals(succeed)) {
                        message = String.valueOf(message) + "第" + (i + 1) + "行" + empPositionName + "岗位不存在！<br>";
                        succeed = "1";
                      } 
                    } else {
                      employeeVO.setEmpPositionId(empPositionId);
                      employeeVO.setEmpPosition(empPositionName);
                    } 
                  } 
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行" + "岗位不存在！<br>";
                    succeed = "1";
                  } 
                } 
                String empPositionOtherName = this.sheet.getCell(20, i).getContents().trim();
                try {
                  if (empPositionOtherName != null && !"".equals(empPositionOtherName)) {
                    Long empPositionOtherId = roleBD
                      .searchEmpPositionIdByName(empPositionOtherName);
                    if (empPositionOtherId == null) {
                      if (!"1".equals(succeed)) {
                        message = String.valueOf(message) + "第" + (i + 1) + "行" + empPositionOtherName + "副岗不存在！<br>";
                        succeed = "1";
                      } 
                    } else {
                      employeeVO.setEmpPositionOtherId(empPositionOtherId.toString());
                    } 
                  } 
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行" + empPositionOtherName + "副岗不存在！<br>";
                    succeed = "1";
                  } 
                } 
                dutyName = this.sheet.getCell(8, i).getContents().trim();
                try {
                  if (dutyName != null && !"".equals(dutyName))
                    if (dutyBD.validateByName(dutyName)) {
                      "1".equals(succeed);
                    } else if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行" + dutyName + "职务不存在！<br>";
                      succeed = "1";
                    }  
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行" + dutyName + "职务不存在！<br>";
                    succeed = "1";
                  } 
                } 
                String birthday = this.sheet.getCell(15, i).getContents().trim();
                try {
                  if (birthday != null && !"".equals(birthday))
                    if (this.sheet.getCell(15, i).getType() == CellType.DATE) {
                      DateCell nc = (DateCell)this.sheet.getCell(15, i);
                      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                      Date userbirth = new Date(dateFormat.format(nc.getDate()));
                      employeeVO.setEmpBirth(userbirth);
                    } else {
                      message = String.valueOf(message) + "第" + (i + 1) + "行生日时间格式不正确！<br>";
                      if (!"1".equals(succeed))
                        succeed = "1"; 
                    }  
                } catch (Exception e) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行生日时间格式不正确！<br>";
                    succeed = "1";
                  } 
                } 
                if (username != null && !"".equals(username)) {
                  if (!"1".equals(succeed) && 
                    UserAccountsList.contains(username)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行用户帐号重复！<br>";
                    if (!"1".equals(succeed)) {
                      if (savetype != null && "2".equals(savetype)) {
                        employeeVO = userBD.getEmpByid(
                            Long.valueOf(userBD.findUserIdByUserAccount(username)));
                        employeeVO.setEmpMobilePhone(this.sheet.getCell(9, i).getContents()
                            .trim());
                        employeeVO.setEmpBusinessPhone(this.sheet.getCell(10, i).getContents()
                            .trim());
                        employeeVO.setSerial(this.sheet.getCell(11, i).getContents().trim());
                        employeeVO.setEmpEmail(this.sheet.getCell(12, i).getContents().trim());
                        employeeVO.setEmpBusinessFax(this.sheet.getCell(13, i).getContents()
                            .trim());
                        employeeVO.setEmpPhone(this.sheet.getCell(14, i).getContents().trim());
                        String password = "111111";
                        employeeVO
                          .setUserAccounts(this.sheet.getCell(1, i).getContents().trim());
                        employeeVO.setUserSimpleName(this.sheet.getCell(3, i).getContents()
                            .trim());
                        if (this.sheet.getCell(2, i).getContents() != null && 
                          !"".equals(this.sheet.getCell(2, i).getContents().trim()))
                          password = this.sheet.getCell(2, i).getContents().trim(); 
                        employeeVO.setUserPassword((new MD5()).getMD5Code(password));
                        employeeVO.setEmpName(name);
                        employeeVO.setEmpNumber(this.sheet.getCell(5, i).getContents().trim());
                        employeeVO.setEmpNativePlace(this.sheet.getCell(16, i).getContents()
                            .trim());
                        employeeVO
                          .setHujiAddress(this.sheet.getCell(17, i).getContents().trim());
                        employeeVO.setEmpIdCard(this.sheet.getCell(18, i).getContents().trim());
                        employeeVO.setEmpIsMarriage((byte)0);
                        employeeVO.setEmpHeight((short)0);
                        employeeVO.setEmpWeight((short)0);
                        employeeVO.setEmpStatus((byte)0);
                        employeeVO.setUserIsActive((byte)1);
                        employeeVO.setUserIsDeleted((byte)0);
                        employeeVO.setUserIsFormalUser(Integer.valueOf(1));
                        employeeVO.setUserIsSuper((byte)1);
                        Date userSuperBegin = new Date("2009/10/21");
                        Date userSuperEnd = new Date("2018/10/21");
                        employeeVO.setUserSuperBegin(userSuperBegin);
                        employeeVO.setUserSuperEnd(userSuperEnd);
                        employeeVO.setUserOrderCode("10000");
                        employeeVO.setCreatedOrg(0L);
                        employeeVO.setKeyValidate("0");
                        employeeVO.setDomainId("0");
                        employeeVO.setSkin("blue");
                        employeeVO.setMailboxSize("100");
                        employeeVO.setNetDiskSize(sysRed.getNetDiskSize(domainId));
                        employeeVO.setEmpDuty(dutyName);
                        employeeVO.setEmpDutyLevel(getDutyLevel(dutyName, domainId));
                        employeeVO.setBrowseRange(defaultBrowseRange);
                        String leaders = "";
                        String leaderIds = "";
                        if (this.sheet.getCell(21, i).getContents() != null && 
                          !"".equals(this.sheet.getCell(21, i).getContents().trim())) {
                          String leadersNumberTemp = this.sheet.getCell(21, i).getContents();
                          String[] leadersNumber = leadersNumberTemp.split(",");
                          for (int j = 0; j < leadersNumber.length; j++) {
                            String leadNumber = leadersNumber[j];
                            List<Object[]> leader = userBD
                              .getUserIdAndNameByEmpNumber(leadNumber);
                            if (leader != null && leader.size() > 0) {
                              Object[] leaderUser = leader.get(0);
                              leaderIds = String.valueOf(leaderIds) + "$" + leaderUser[0] + "$";
                              leaders = String.valueOf(leaders) + leaderUser[1] + ",";
                            } 
                          } 
                          employeeVO.setEmpLeaderId(leaderIds);
                          employeeVO.setEmpLeaderName(leaders);
                        } 
                        userBD.update(employeeVO);
                        oldInputNum++;
                        succeed = "1";
                      } 
                      succeed = "1";
                    } 
                  } 
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行用户帐号不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
                if (!"1".equals(succeed)) {
                  newInputNum++;
                  employeeVO.setEmpMobilePhone(this.sheet.getCell(9, i).getContents().trim());
                  employeeVO.setEmpBusinessPhone(this.sheet.getCell(10, i).getContents().trim());
                  employeeVO.setSerial(this.sheet.getCell(11, i).getContents().trim());
                  employeeVO.setEmpEmail(this.sheet.getCell(12, i).getContents().trim());
                  employeeVO.setEmpBusinessFax(this.sheet.getCell(13, i).getContents().trim());
                  employeeVO.setEmpPhone(this.sheet.getCell(14, i).getContents().trim());
                  String password = "111111";
                  employeeVO.setUserAccounts(this.sheet.getCell(1, i).getContents().trim());
                  employeeVO.setUserSimpleName(this.sheet.getCell(3, i).getContents().trim());
                  if (this.sheet.getCell(2, i).getContents() != null && 
                    !"".equals(this.sheet.getCell(2, i).getContents().trim()))
                    password = this.sheet.getCell(2, i).getContents().trim(); 
                  employeeVO.setUserPassword((new MD5()).getMD5Code(password));
                  employeeVO.setEmpName(name);
                  employeeVO.setEmpNumber(this.sheet.getCell(5, i).getContents().trim());
                  employeeVO.setEmpNativePlace(this.sheet.getCell(16, i).getContents().trim());
                  employeeVO.setHujiAddress(this.sheet.getCell(17, i).getContents().trim());
                  employeeVO.setEmpIdCard(this.sheet.getCell(18, i).getContents().trim());
                  employeeVO.setEmpIsMarriage((byte)0);
                  employeeVO.setEmpHeight((short)0);
                  employeeVO.setEmpWeight((short)0);
                  employeeVO.setEmpStatus((byte)0);
                  employeeVO.setUserIsActive((byte)1);
                  employeeVO.setUserIsDeleted((byte)0);
                  employeeVO.setUserIsFormalUser(Integer.valueOf(1));
                  employeeVO.setUserIsSuper((byte)1);
                  Date userSuperBegin = new Date("2009/10/21");
                  Date userSuperEnd = new Date("2018/10/21");
                  employeeVO.setUserSuperBegin(userSuperBegin);
                  employeeVO.setUserSuperEnd(userSuperEnd);
                  employeeVO.setUserOrderCode("10000");
                  employeeVO.setCreatedOrg(0L);
                  employeeVO.setKeyValidate("0");
                  employeeVO.setDomainId("0");
                  employeeVO.setSkin("blue");
                  employeeVO.setMailboxSize("100");
                  employeeVO.setNetDiskSize(sysRed.getNetDiskSize(domainId));
                  employeeVO.setEmpDuty(dutyName);
                  employeeVO.setEmpDutyLevel(getDutyLevel(dutyName, domainId));
                  employeeVO.setBrowseRange(defaultBrowseRange);
                  String leaders = "";
                  String leaderIds = "";
                  if (this.sheet.getCell(21, i).getContents() != null && 
                    !"".equals(this.sheet.getCell(21, i).getContents().trim())) {
                    String leadersNumberTemp = this.sheet.getCell(21, i).getContents();
                    String[] leadersNumber = leadersNumberTemp.split(",");
                    for (int j = 0; j < leadersNumber.length; j++) {
                      String leadNumber = leadersNumber[j];
                      List<Object[]> leader = userBD.getUserIdAndNameByEmpNumber(leadNumber);
                      if (leader != null && leader.size() > 0) {
                        Object[] leaderUser = leader.get(0);
                        leaderIds = String.valueOf(leaderIds) + "$" + leaderUser[0] + "$";
                        leaders = String.valueOf(leaders) + leaderUser[1] + ",";
                      } 
                    } 
                    employeeVO.setEmpLeaderId(leaderIds);
                    employeeVO.setEmpLeaderName(leaders);
                  } 
                  if (SystemCommon.getAudit() == 0) {
                    userid = Long.valueOf(userBD.exselAdd(employeeVO, oldOrganizationVO.getOrgId(), roleIdList));
                  } else {
                    employeeVO.setUserPassword(password);
                    HttpSession session = httpServletRequest.getSession(true);
                    String[] log = { session.getAttribute("userId").toString(), 
                        session.getAttribute("userName").toString(), 
                        session.getAttribute("orgId").toString(), "6" };
                    userid = Long.valueOf(userBD.exselAdd(employeeVO, oldOrganizationVO.getOrgId(), roleIdList, 
                          log));
                  } 
                } 
              } 
              badInputNum = rows - newInputNum - oldInputNum - 3;
            } 
          } else {
            if (!"1".equals(succeed))
              succeed = "1"; 
            message = String.valueOf(message) + "选择的模版不正确！<br>";
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
      if (newInputNum != 0)
        httpServletRequest.setAttribute("newInputNum", "有" + newInputNum + "个用户新增<br/>"); 
      if (oldInputNum != 0)
        httpServletRequest.setAttribute("oldInputNum", "有" + oldInputNum + "个用户覆盖<br/>"); 
      if (badInputNum != 0)
        httpServletRequest.setAttribute("badInputNum", "有" + badInputNum + "条无效记录<br/>"); 
      httpServletRequest.setAttribute("message", message);
      httpServletRequest.setAttribute("newType", type);
      httpServletRequest.setAttribute("succeed", succeed);
      return actionMapping.findForward("result");
    } 
    if ("3".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/linkmantemplate.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      if (this.sheet != null) {
        PersonOwnBD bd = new PersonOwnBD();
        PersonClassBD personClassBD = new PersonClassBD();
        int rows = this.sheet.getRows();
        String linkManName = "";
        String sex = "";
        String className = "";
        long userId = 
          Long.parseLong(String.valueOf(httpServletRequest.getSession(true).getAttribute("userId")));
        try {
          String linktext = this.sheet.getCell(0, 2).getContents().trim();
          if ("联系人姓名".equals(linktext)) {
            for (int i = 4; i < rows; i++) {
              PersonPO personPO = new PersonPO();
              linkManName = this.sheet.getCell(0, i).getContents().trim();
              if (linkManName == null || "".equals(linkManName)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行联系人姓名不能为空！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              PersonClassPO personClassPO = null;
              int re = Integer.parseInt(userType);
              personPO.setLinkManType((byte)re);
              personPO.setDomainId("0");
              personPO.setViewScope("0");
              className = this.sheet.getCell(5, i).getContents().trim();
              personClassPO = personClassBD.findPersonClassPO(Long.valueOf(userId), className, (byte)re, curOrgId);
              if (personClassPO == null) {
                if (re == 0) {
                  PersonClassPO personClassPO1 = new PersonClassPO();
                  personClassPO1.setEmpId(userId);
                  personClassPO1.setClassType((byte)0);
                  personClassPO1.setClassName(className);
                  personClassPO1.setDomainId("0");
                  personClassPO1.setClassDescribe("");
                  long orgid = ((Long)httpServletRequest.getSession().getAttribute("orgId")).longValue();
                  personClassPO1.setCreatedOrg(orgid);
                  Long pid = Long.valueOf(personClassBD.addper(personClassPO1));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行联系人分类不存在！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
              } else {
                personPO.setLinkManClass(personClassPO);
              } 
              personPO.setLinkManName(this.sheet.getCell(0, i).getContents().trim());
              sex = this.sheet.getCell(1, i).getContents().trim();
              if ("女".equals(sex)) {
                personPO.setLinkManSex("1");
              } else if ("男".equals(sex)) {
                personPO.setLinkManSex("0");
              } else {
                message = String.valueOf(message) + "第" + (i + 1) + "行用户性别填写错误！<br>";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              if (!"1".equals(succeed)) {
                personPO.setLinkManUnit(this.sheet.getCell(2, i).getContents().trim());
                personPO.setMobilePhone(this.sheet.getCell(3, i).getContents().trim());
                personPO.setLinkManEmail(this.sheet.getCell(4, i).getContents().trim());
                personPO.setLinkManAddress(this.sheet.getCell(6, i).getContents().trim());
                personPO.setBussinessPhone(this.sheet.getCell(7, i).getContents().trim());
                personPO.setFixedPhone(this.sheet.getCell(8, i).getContents().trim());
                personPO.setLinkManDuty(this.sheet.getCell(9, i).getContents().trim());
                personPO.setLinkManProfession(this.sheet.getCell(10, i).getContents().trim());
                personPO.setLinkManDepart(this.sheet.getCell(11, i).getContents().trim());
                personPO.setLinkManEnName(this.sheet.getCell(12, i).getContents().trim());
                personPO.setBussinessFax(this.sheet.getCell(13, i).getContents().trim());
                personPO.setLinkManWebUrl(this.sheet.getCell(14, i).getContents().trim());
                personPO.setLinkManPostZip(this.sheet.getCell(15, i).getContents().trim());
                personPO.setLinkManEmail2(this.sheet.getCell(16, i).getContents().trim());
                personPO.setLinkManEmail3(this.sheet.getCell(17, i).getContents().trim());
                personPO.setCreatedEmpId(userId);
                personPO.setLinkManBirth(new Date("2009/10/21"));
                String userName = String.valueOf(httpServletRequest.getSession(true).getAttribute(
                      "userName"));
                personPO.setCreatedEmpName(userName);
                long orgId = Long.parseLong(String.valueOf(httpServletRequest.getSession(true)
                      .getAttribute("orgId")));
                personPO.setCreatedOrg(orgId);
                exlList.add(personPO);
              } 
            } 
            if ("0".equals(succeed))
              for (int j = 0; j < exlList.size(); j++) {
                PersonPO personPO = new PersonPO();
                personPO = exlList.get(j);
                bd.addInput(personPO);
              }  
          } else {
            message = String.valueOf(message) + "选择的模版不正确！";
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
    } else if ("4".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/salary.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      if (this.sheet != null) {
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        int rows = this.sheet.getRows();
        SalaryBD salaryBD = new SalaryBD();
        UserBD userBD = new UserBD();
        String sendtime = "";
        String number = "";
        try {
          String usertext = this.sheet.getCell(0, 2).getContents().trim();
          if ("发放时间".equals(usertext)) {
            for (int i = 4; i < rows; i++) {
              SalaryPO salaryPO = new SalaryPO();
              EmployeeVO employeeVO = new EmployeeVO();
              Date sendDate = null;
              sendtime = this.sheet.getCell(0, i).getContents().trim();
              if (sendtime == null || "".equals(sendtime)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行发放时间不能为空！请修改后重新导入";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              if (this.sheet.getCell(0, i).getType() == CellType.DATE) {
                DateCell nc = (DateCell)this.sheet.getCell(0, i);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                sendDate = new Date(dateFormat.format(nc.getDate()));
                salaryPO.setSendTime(sendDate);
              } else {
                message = String.valueOf(message) + "第" + (i + 1) + "行发放时间格式不正确！请修改后重新导入";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              number = this.sheet.getCell(1, i).getContents().trim();
              if (number == null || "".equals(number)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行员工工号不能为空！请修改后重新导入";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              employeeVO = userBD.getEmployeeVOByNumber(number);
              if (employeeVO == null) {
                message = String.valueOf(message) + "第" + (i + 1) + "行员工工号没有对应的员工！请修改后重新导入";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              String datein = salaryBD.yanzhengDate(sendDate, employeeVO.getEmpId());
              if ("Y".equals(datein)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行该员工该月的工资记录已经导入！请修改后重新导入";
                if (!"1".equals(succeed))
                  succeed = "1"; 
              } 
              salaryPO.setEmployeeVO(employeeVO);
              if (this.sheet.getCell(4, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(4, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(4, i).getContents().trim()).matches()) {
                  salaryPO.setFixed_salary(Double.parseDouble(this.sheet.getCell(4, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行月固定工资不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(5, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(5, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(5, i).getContents().trim()).matches()) {
                  salaryPO.setAllowance(Double.parseDouble(this.sheet.getCell(5, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行月津贴不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(6, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(6, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(6, i).getContents().trim()).matches()) {
                  salaryPO.setPerformance(
                      Double.parseDouble(this.sheet.getCell(6, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行月绩效奖金不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(7, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(7, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(7, i).getContents().trim()).matches()) {
                  salaryPO.setYiecanfei(Double.parseDouble(this.sheet.getCell(7, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行夜餐费不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(8, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(8, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(8, i).getContents().trim()).matches()) {
                  salaryPO.setJiaban(Double.parseDouble(this.sheet.getCell(8, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行加班工资不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(9, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(9, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(9, i).getContents().trim()).matches()) {
                  salaryPO.setJianzhi(Double.parseDouble(this.sheet.getCell(9, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行兼职工资不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(10, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(10, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(10, i).getContents().trim()).matches()) {
                  salaryPO.setOther(Double.parseDouble(this.sheet.getCell(10, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行其他应发款不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(11, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(11, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(11, i).getContents().trim()).matches()) {
                  salaryPO.setYuetotal(Double.parseDouble(this.sheet.getCell(11, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行月应发合计不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(12, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(12, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(12, i).getContents().trim()).matches()) {
                  salaryPO.setYanglaoxian(Double.parseDouble(this.sheet.getCell(12, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行养老保险不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(13, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(13, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(13, i).getContents().trim()).matches()) {
                  salaryPO.setShiyexian(Double.parseDouble(this.sheet.getCell(13, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行失业保险不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(14, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(14, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(14, i).getContents().trim()).matches()) {
                  salaryPO.setYiliaoxian(
                      Double.parseDouble(this.sheet.getCell(14, i).getContents().trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行医疗保险不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(15, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(15, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(15, i).getContents().trim()).matches()) {
                  salaryPO.setZhufangjijin(Double.parseDouble(this.sheet.getCell(15, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行住房公积金不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(16, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(16, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(16, i).getContents().trim()).matches()) {
                  salaryPO.setBaoxiankoukuan(Double.parseDouble(this.sheet.getCell(16, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行保险扣款小计不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(17, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(17, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(17, i).getContents().trim()).matches()) {
                  salaryPO.setQueqinkoukuan(Double.parseDouble(this.sheet.getCell(17, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行缺勤扣款不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(18, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(18, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(18, i).getContents().trim()).matches()) {
                  salaryPO.setOtherkoukuan(Double.parseDouble(this.sheet.getCell(18, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行其他扣款不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(19, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(19, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(19, i).getContents().trim()).matches()) {
                  salaryPO.setYingjiaoshuijin(Double.parseDouble(this.sheet.getCell(19, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行应缴税金不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(20, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(20, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(20, i).getContents().trim()).matches()) {
                  salaryPO.setShourutotal(Double.parseDouble(this.sheet.getCell(20, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行收入合计不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              if (this.sheet.getCell(21, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(21, i).getContents().trim()))
                if (pattern.matcher(this.sheet.getCell(21, i).getContents().trim()).matches()) {
                  salaryPO.setShifagongzhi(Double.parseDouble(this.sheet.getCell(21, i).getContents()
                        .trim()));
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行实发工资不是有效数字！请修改后重新导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                }  
              salaryPO.setCreatedEmp((new Long(httpServletRequest.getSession().getAttribute("userId")
                    .toString())).longValue());
              salaryPO.setCreatedOrg((new Long(httpServletRequest.getSession().getAttribute("orgId")
                    .toString())).longValue());
              exlList.add(salaryPO);
            } 
            if ("0".equals(succeed))
              for (int j = 0; j < exlList.size(); j++) {
                SalaryPO salaryPO = new SalaryPO();
                salaryPO = (SalaryPO)exlList.get(j);
                salaryBD.save(salaryPO);
              }  
          } else {
            message = "选择的模版不正确！";
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
    } else if ("5".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/usersnkey.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      if (this.sheet != null) {
        int rows = this.sheet.getRows();
        try {
          String usertext = this.sheet.getCell(0, 2).getContents().trim();
          if ("真实姓名".equals(usertext)) {
            Map<Object, Object> userAccountMap = new HashMap<Object, Object>();
            Map<Object, Object> snMap = new HashMap<Object, Object>();
            String userAccountSql = "";
            String snSql = "";
            if (rows < 4) {
              message = String.valueOf(message) + "请填写模板！<br>";
              if (!"1".equals(succeed))
                succeed = "1"; 
            } else {
              for (int i = 4; i < rows; i++) {
                String userAccount = this.sheet.getCell(1, i).getContents().trim();
                if (userAccountMap.get(userAccount) != null && 
                  userAccount.equals(userAccountMap.get(userAccount))) {
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                  message = String.valueOf(message) + "模板用户帐号：“" + userAccount + "”重复！<br>";
                  break;
                } 
                userAccountMap.put(userAccount, userAccount);
                String sn = this.sheet.getCell(3, i).getContents().trim();
                if (snMap.get(sn) != null && sn.equals(snMap.get(sn))) {
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                  message = String.valueOf(message) + "模板SN：“" + sn + "”重复！<br>";
                  break;
                } 
                snMap.put(sn, sn);
              } 
            } 
            if (!"1".equals(succeed)) {
              String sql = "SELECT s.sn FROM com.js.oa.security.seamoon.po.SecSeaMoon s";
              SeaMoonEJBBean seaMoonEJBBean = new SeaMoonEJBBean();
              List<String> listSn = seaMoonEJBBean.getListByYourSQL(sql);
              sql = "SELECT o.userAccounts FROM com.js.oa.security.seamoon.po.SecSeaMoon s,com.js.system.vo.usermanager.EmployeeVO o  WHERE s.userId=o.empId";
              List<String> listUserAccounts = seaMoonEJBBean.getListByYourSQL(sql);
              for (int i = 4; i < rows; i++) {
                SecSeaMoon secSeaMoon = new SecSeaMoon();
                String userName = this.sheet.getCell(0, i).getContents().trim();
                if (userName == null || "".equals(userName)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行用户姓名不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
                String userAccount = this.sheet.getCell(1, i).getContents().trim();
                if (userAccount == null || "".equals(userAccount)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行用户帐号不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else {
                  sql = "SELECT o.empId,o.empName FROM com.js.system.vo.usermanager.EmployeeVO o  WHERE o.userAccounts='" + 
                    userAccount + "'";
                  List<Object[]> tempList = seaMoonEJBBean.getListByYourSQL(sql);
                  if (tempList == null || tempList.size() < 1) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行用户帐号不存在！<br>";
                    if (!"1".equals(succeed))
                      succeed = "1"; 
                  } else if (listUserAccounts.contains(userAccount)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行用户帐号已经设置过动态密码！<br>";
                    if (!"1".equals(succeed))
                      succeed = "1"; 
                  } else {
                    Object[] obj = tempList.get(0);
                    secSeaMoon.setUserId(obj[0].toString());
                    secSeaMoon.setUserName(obj[1].toString());
                  } 
                } 
                String secStatus = this.sheet.getCell(2, i).getContents().trim();
                if (secStatus == null || "".equals(secStatus)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行状态不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else if (!"1".equals(secStatus) && !"0".equals(secStatus)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行状态格式不合法！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else {
                  secSeaMoon.setSecStatus(secStatus);
                } 
                String sn = this.sheet.getCell(3, i).getContents().trim();
                if (sn == null || "".equals(sn)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行SN不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else if (listSn.contains(sn)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行SN编号已存在！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else {
                  secSeaMoon.setSn(sn);
                } 
                String snKey = this.sheet.getCell(4, i).getContents().trim();
                if (snKey == null || "".equals(snKey)) {
                  message = String.valueOf(message) + "第" + (i + 1) + "行snKey不能为空！<br>";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } else {
                  secSeaMoon.setSnKey(snKey);
                } 
                if (!"1".equals(succeed))
                  seaMoonEJBBean.saveSeaMoon(secSeaMoon); 
              } 
            } 
          } else {
            if (!"1".equals(succeed))
              succeed = "1"; 
            message = String.valueOf(message) + "选择的模版不正确！<br>";
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
    } else if ("6".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/fsalary.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        FSalaryService fSalaryService = new FSalaryService();
        Map map = fSalaryService.importSalary(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("7".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/fpayable.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        FPayableService fPayableService = new FPayableService();
        Map map = fPayableService.importPayable(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("8".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/fexpense.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        FExpenseService fExpenseService = new FExpenseService();
        Map map = fExpenseService.importExpense(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("9".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/ftax.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        FTaxService fTaxService = new FTaxService();
        Map map = fTaxService.importSalary(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("10".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/duty.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        DutyEJBBean dutyBean = new DutyEJBBean();
        Map map = dutyBean.ImportDuty(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
        messageString = (String)map.get("messageString");
        message = String.valueOf(message) + messageString;
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("11".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/station.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        NewDutyEJBBean stationBean = new NewDutyEJBBean();
        Map map = stationBean.ImportDuty(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
        messageString = (String)map.get("messageString");
        message = String.valueOf(message) + messageString;
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("12".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/office.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        OfficeRoomEJBBean office = new OfficeRoomEJBBean();
        Map map = office.ImportOffice(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("13".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/equipment.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        EquipmentEJBBean equipment = new EquipmentEJBBean();
        Map map = equipment.ImportEquipment(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("14".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/goods.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        GoodsEJBBean goods = new GoodsEJBBean();
        Map map = goods.ImportGoods(httpServletRequest);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
        messageString = message;
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("15".equals(type)) {
      realPath = httpServletRequest.getRealPath("uploadtemplate/employeeInfo.xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        FileInputStream fileInputStream = new FileInputStream(new File(realPath));
        try {
          this.workbook = Workbook.getWorkbook(fileInputStream);
        } catch (Exception e) {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
          httpServletRequest.setAttribute("succeed", succeed);
          httpServletRequest.setAttribute("message", message);
          return actionMapping.findForward("input");
        } 
        this.sheet = this.workbook.getSheet(0);
        int newInputNum = 0;
        int oldInputNum = 0;
        int ignoreInputNum = 0;
        if (this.sheet == null) {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
          httpServletRequest.setAttribute("succeed", succeed);
          httpServletRequest.setAttribute("message", message);
          return actionMapping.findForward("input");
        } 
        int rows = this.sheet.getRows();
        for (int i = 4; i < rows; i++) {
          EmployeeVO employeeVO = new EmployeeVO();
          String zsxm = this.sheet.getCell(0, i).getContents().trim();
          String yhjm = this.sheet.getCell(1, i).getContents().trim();
          String xb = this.sheet.getCell(2, i).getContents().trim();
          String gh = this.sheet.getCell(3, i).getContents().trim();
          String sszzbm = this.sheet.getCell(4, i).getContents().trim();
          String jzjgbm = this.sheet.getCell(5, i).getContents().trim();
          String zw = this.sheet.getCell(6, i).getContents().trim();
          String zzzt = this.sheet.getCell(7, i).getContents().trim();
          String ryxz = this.sheet.getCell(8, i).getContents().trim();
          String gw = this.sheet.getCell(9, i).getContents().trim();
          String gwStr = "";
          String sjld = this.sheet.getCell(10, i).getContents().trim();
          String bgdd = this.sheet.getCell(11, i).getContents().trim();
          String sjhm = this.sheet.getCell(12, i).getContents().trim();
          String bgdh = this.sheet.getCell(13, i).getContents().trim();
          String fjh = this.sheet.getCell(14, i).getContents().trim();
          String dzyj = this.sheet.getCell(15, i).getContents().trim();
          String czhm = this.sheet.getCell(16, i).getContents().trim();
          String jtzz = this.sheet.getCell(17, i).getContents().trim();
          String jtxx = this.sheet.getCell(18, i).getContents().trim();
          String zzdh = this.sheet.getCell(19, i).getContents().trim();
          String csrq = this.sheet.getCell(20, i).getContents().trim();
          String rzrq = this.sheet.getCell(21, i).getContents().trim();
          String cjgzrq = this.sheet.getCell(22, i).getContents().trim();
          String zzrq = this.sheet.getCell(23, i).getContents().trim();
          String jg = this.sheet.getCell(24, i).getContents().trim();
          String hjdz = this.sheet.getCell(25, i).getContents().trim();
          String xl = this.sheet.getCell(26, i).getContents().trim();
          String ywlzzm = this.sheet.getCell(27, i).getContents().trim();
          String ygxz = this.sheet.getCell(28, i).getContents().trim();
          String sf = this.sheet.getCell(29, i).getContents().trim();
          String gd = this.sheet.getCell(30, i).getContents().trim();
          String sfzh = this.sheet.getCell(31, i).getContents().trim();
          String ahtc = this.sheet.getCell(32, i).getContents().trim();
          String qm = this.sheet.getCell(33, i).getContents().trim();
          String zy1 = this.sheet.getCell(34, i).getContents().trim();
          String zy2 = this.sheet.getCell(35, i).getContents().trim();
          String yyzl = this.sheet.getCell(36, i).getContents().trim();
          String yydj = this.sheet.getCell(37, i).getContents().trim();
          String zzmm = this.sheet.getCell(38, i).getContents().trim();
          String zc = this.sheet.getCell(39, i).getContents().trim();
          String mz = this.sheet.getCell(40, i).getContents().trim();
          if ("".equals(zsxm)) {
            message = String.valueOf(message) + "第" + (i + 1) + "行【真实姓名】为空！<br>";
            succeed = "1";
          } else {
            employeeVO.setEmpName(zsxm);
            if ("".equals(gh)) {
              message = String.valueOf(message) + "第" + (i + 1) + "行【工号】为空！<br>";
              succeed = "1";
            } else {
              employeeVO.setEmpNumber(gh);
              if (!"".equals(xb))
                if ("男".equals(xb)) {
                  employeeVO.setEmpSex((new Byte("0")).byteValue());
                } else if ("女".equals(xb)) {
                  employeeVO.setEmpSex((new Byte("1")).byteValue());
                } else {
                  message = String.valueOf(message) + "第" + (i + 1) + "行【性别】填写错误！<br>";
                  succeed = "1";
                  i++;
                }  
              if ("".equals(sszzbm)) {
                message = String.valueOf(message) + "第" + (i + 1) + "行【所属组织编码】为空！<br>";
                succeed = "1";
              } else {
                Long long_;
                OrganizationBD organizationBD = new OrganizationBD();
                OrganizationVO organizationVO1 = organizationBD.getOrgBySerial(sszzbm);
                if (organizationVO1 == null) {
                  if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行【所属组织编码】不存在！<br>";
                    succeed = "1";
                  } 
                } else {
                  Long long_1 = organizationVO1.getOrgId();
                } 
                if (!"".equals(jzjgbm)) {
                  OrganizationVO organizationVO2 = organizationBD.getOrgBySerial(jzjgbm);
                  if (organizationVO2 == null) {
                    if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【兼职组织】不存在！<br>";
                      succeed = "1";
                    } 
                  } else {
                    Long long_1 = organizationVO2.getOrgId();
                    employeeVO.setSidelineOrg("*" + long_1 + "*");
                    employeeVO.setSidelineOrgName(organizationVO2.getOrgName());
                  } 
                } 
                PersonalKindBD personalKindBD = new PersonalKindBD();
                if (!"".equals(ryxz))
                  if (!personalKindBD.checkExistKind(null, ryxz)) {
                    if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【人员性质】不存在！<br>";
                      succeed = "1";
                    } 
                  } else {
                    long_ = personalKindBD.loadByName(ryxz).getKindId();
                  }  
                NewDutyBD newDutyBD = new NewDutyBD();
                if (!"".equals(gw)) {
                  String[] strs = newDutyBD.getSingleStationByName(gw);
                  gwStr = strs[1];
                  if ("".equals(strs[1])) {
                    if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【岗位】不存在！<br>";
                      succeed = "1";
                    } 
                  } else {
                    gw = strs[0];
                  } 
                } 
                if ("离职".equals(zzzt)) {
                  if (zzrq != null && !"".equals(zzrq))
                    if (this.sheet.getCell(23, i).getType() == CellType.DATE) {
                      DateCell nc = (DateCell)this.sheet.getCell(23, i);
                      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                      Date userzzrq = new Date(dateFormat.format(nc.getDate()));
                      employeeVO.setZhuanzhengDate(userzzrq);
                    } else {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【转正日期】格式不正确！<br>";
                      if (!"1".equals(succeed))
                        succeed = "1"; 
                    }  
                  Byte userIsDeleted = new Byte("1");
                  employeeVO.setUserIsDeleted(userIsDeleted.byteValue());
                  employeeVO.setJobStatus(zzzt);
                } else if ("临时".equals(zzzt)) {
                  employeeVO.setZhuanzhengDate(null);
                  employeeVO.setLizhiDate(null);
                  employeeVO.setEmpFireType(null);
                  employeeVO.setFireReason(null);
                  employeeVO.setJobStatus(zzzt);
                } else {
                  if (zzrq != null && !"".equals(zzrq))
                    if (this.sheet.getCell(23, i).getType() == CellType.DATE) {
                      DateCell nc = (DateCell)this.sheet.getCell(23, i);
                      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                      Date userzzrq = new Date(dateFormat.format(nc.getDate()));
                      employeeVO.setZhuanzhengDate(userzzrq);
                    } else {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【转正日期】格式不正确！<br>";
                      if (!"1".equals(succeed))
                        succeed = "1"; 
                    }  
                  employeeVO.setLizhiDate(null);
                  employeeVO.setEmpFireType(null);
                  employeeVO.setFireReason(null);
                  employeeVO.setJobStatus(zzzt);
                } 
                employeeVO.setSkin("blue");
                employeeVO.setUserIsFormalUser(Integer.valueOf("2"));
                byte userIsActive = 0;
                employeeVO.setUserIsActive(userIsActive);
                employeeVO.setDomainId(domainId);
                employeeVO.setEmpResumeNum(ygxz);
                employeeVO.setZhicheng(zc);
                employeeVO.setEmpNation(mz);
                if (csrq != null && !"".equals(csrq))
                  if (this.sheet.getCell(20, i).getType() == CellType.DATE) {
                    DateCell nc = (DateCell)this.sheet.getCell(20, i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date csrqd = new Date(dateFormat.format(nc.getDate()));
                    employeeVO.setEmpBirth(csrqd);
                  } else {
                    message = String.valueOf(message) + "第" + (i + 1) + "行【出生日期】格式不正确！<br>";
                    if (!"1".equals(succeed))
                      succeed = "1"; 
                  }  
                if (rzrq != null && !"".equals(rzrq))
                  if (this.sheet.getCell(21, i).getType() == CellType.DATE) {
                    DateCell nc = (DateCell)this.sheet.getCell(21, i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date rzrqd = new Date(dateFormat.format(nc.getDate()));
                    employeeVO.setIntoCompanyDate(rzrqd);
                  } else {
                    message = String.valueOf(message) + "第" + (i + 1) + "行【入职日期】格式不正确！<br>";
                    if (!"1".equals(succeed))
                      succeed = "1"; 
                  }  
                if (cjgzrq != null && !"".equals(cjgzrq))
                  if (this.sheet.getCell(22, i).getType() == CellType.DATE) {
                    DateCell nc = (DateCell)this.sheet.getCell(22, i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date cjgzrqd = new Date(dateFormat.format(nc.getDate()));
                    employeeVO.setEmpFireDate(cjgzrqd);
                  } else {
                    message = String.valueOf(message) + "第" + (i + 1) + "行【参加工作日期】格式不正确！<br>";
                    if (!"1".equals(succeed))
                      succeed = "1"; 
                  }  
                if (!"".equals(long_))
                  employeeVO.setPersonalKind(new Long((String)long_)); 
                if (!"".equals(gw))
                  employeeVO.setEmpPositionId(new Long(gw)); 
                employeeVO.setEmpPosition(gwStr);
                DutyBD dutyBD = new DutyBD();
                if (!"".equals(zw))
                  if (!dutyBD.validateByName(zw)) {
                    if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【职务】不存在！<br>";
                      succeed = "1";
                    } 
                  } else {
                    employeeVO.setEmpDuty(zw);
                    employeeVO.setEmpDutyLevel(getDutyLevel(zw, domainId));
                  }  
                if (!"".equals(sjld)) {
                  String[] leadersNumber = sjld.split(",");
                  String leaderIds = "";
                  String leaders = "";
                  for (int j = 0; j < leadersNumber.length; j++) {
                    String leadNumber = leadersNumber[j];
                    List<Object[]> leader = (new UserBD()).getUserIdAndNameByEmpNumber(leadNumber);
                    if (leader != null && leader.size() > 0) {
                      Object[] leaderUser = leader.get(0);
                      leaderIds = String.valueOf(leaderIds) + "$" + leaderUser[0] + "$";
                      leaders = String.valueOf(leaders) + leaderUser[1] + ",";
                    } 
                  } 
                  employeeVO.setEmpLeaderId(leaderIds);
                  employeeVO.setEmpLeaderName(leaders);
                } 
                employeeVO.setEmpNativePlace(bgdd);
                if (!"".equals(bgdd)) {
                  WorkAddressBD bd = new WorkAddressBD();
                  WorkAddressPO po = bd.loadByName(bgdd);
                  if (po == null) {
                    if (!"1".equals(succeed)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行【办公地点】不存在！<br>";
                      succeed = "1";
                    } 
                  } else {
                    employeeVO.setWorkAddress(po.getId());
                  } 
                } 
                if (!"".equals(ywlzzm))
                  if ("有".equals(ywlzzm)) {
                    employeeVO.setIsdimissionprove(Integer.valueOf(1));
                  } else if ("无".equals(ywlzzm)) {
                    employeeVO.setIsdimissionprove(Integer.valueOf(0));
                  } else if (!"1".equals(succeed)) {
                    message = String.valueOf(message) + "第" + (i + 1) + "行【有无离职证明】不正确！<br>";
                    succeed = "1";
                  }  
                employeeVO.setEmpMobilePhone(sjhm);
                employeeVO.setEmpBusinessPhone(bgdh);
                employeeVO.setSerial(fjh);
                employeeVO.setEmpEmail(dzyj);
                employeeVO.setEmpBusinessFax(czhm);
                employeeVO.setEmpAddress(jtzz);
                employeeVO.setFamilyMember(jtxx);
                employeeVO.setEmpPhone(zzdh);
                employeeVO.setSection(gd);
                employeeVO.setEmpNativePlace(jg);
                employeeVO.setHujiAddress(hjdz);
                employeeVO.setEmpStudyExperience(xl);
                employeeVO.setDignity(sf);
                employeeVO.setEmpIdCard(sfzh);
                employeeVO.setEmpInterest(ahtc);
                employeeVO.setEmpGnome(qm);
                employeeVO.setSpeciality1(zy1);
                employeeVO.setSpeciality2(zy2);
                employeeVO.setLanguage1(yyzl);
                employeeVO.setLanglevel1(yydj);
                employeeVO.setEmpPolity(zzmm);
                if (!"1".equals(succeed)) {
                  UserBD userBD = new UserBD();
                  List<Object[]> users = userBD.getUserIdAndNameByEmpNumber(gh);
                  if (users.size() > 0) {
                    if ("1".equals(savetype)) {
                      ignoreInputNum++;
                    } else if (users.size() > 1) {
                      message = String.valueOf(message) + "第" + i + "行，数据库中存在多条同名用户，无法替换！<br>";
                      succeed = "1";
                    } else {
                      NewEmployeeBD newEmployeeBD = new NewEmployeeBD();
                      Object[] user = users.get(0);
                      Long empId = Long.valueOf(user[0].toString());
                      newEmployeeBD.update(employeeVO, new EmployeeOtherInfoVO(), (String)organizationVO1.getOrgId(), 
                          empId);
                    } 
                  } else {
                    EmployeeOtherInfoVO oterInfoVO = new EmployeeOtherInfoVO();
                    NewEmployeeBD newEmployeeBD = new NewEmployeeBD();
                    newEmployeeBD.add(employeeVO, oterInfoVO, (String)organizationVO1.getOrgId());
                    newInputNum++;
                  } 
                } 
              } 
            } 
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("16".equals(type)) {
      realPath = httpServletRequest.getRealPath("/uploadtemplate/importweixin.xls");
      uploadFile(uploadForm.getFile(), realPath);
      FileInputStream fileInputStream = new FileInputStream(new File(realPath));
      try {
        this.workbook = Workbook.getWorkbook(fileInputStream);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        httpServletRequest.setAttribute("succeed", succeed);
        httpServletRequest.setAttribute("message", message);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      if (this.sheet != null) {
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        int rows = this.sheet.getRows();
        SalaryBD salaryBD = new SalaryBD();
        UserBD userBD = new UserBD();
        String sendtime = "";
        String number = "";
        HibernateBase hBase = new HibernateBase();
        Session session = null;
        try {
          session = hBase.getSession();
          session.beginTransaction();
          String weixinhaoTxt = this.sheet.getCell(4, 1).getContents().trim();
          if ("微信号".equals(weixinhaoTxt)) {
            List<Object[]> objs = new ArrayList();
            for (int i = 2; i < rows; i++) {
              if (this.sheet.getCell(1, i).getContents().trim() != null && 
                !"".equals(this.sheet.getCell(1, i).getContents().trim())) {
                List listemp = session.createQuery(
                    "select eo.empId from com.js.system.vo.usermanager.EmployeeVO  eo where eo.userAccounts='" + 
                    this.sheet.getCell(1, i).getContents().trim() + "'").list();
                if (listemp.size() != 0) {
                  if (!"".equals(this.sheet.getCell(4, i).getContents().trim())) {
                    Object a = listemp.get(0);
                    Object[] obj = { a.toString(), 
                        this.sheet.getCell(4, i).getContents().trim(), 
                        this.sheet.getCell(3, i).getContents().trim() };
                    objs.add(obj);
                  } 
                } else {
                  message = String.valueOf(message) + "账户:" + this.sheet.getCell(1, i).getContents().trim() + "不存在！请重新下载模板导入";
                  if (!"1".equals(succeed))
                    succeed = "1"; 
                } 
              } 
            } 
            if ("0".equals(succeed)) {
              int weixinNum = getWeixinUserNum();
              int limitNum = SystemCommon.getWeixinUserNum();
              for (int j = 0; j < objs.size(); j++) {
                Object[] obj = objs.get(j);
                EmployeeVO eo = (EmployeeVO)session.load(EmployeeVO.class, 
                    Long.valueOf(obj[0].toString()));
                eo.setWeixinId(obj[1].toString());
                if (obj[2] != null && !"".equals(obj[2].toString()))
                  eo.setEmpMobilePhone(obj[2].toString()); 
                if (weixinNum + j <= SystemCommon.getWeixinUserNum())
                  eo.setWeixinPost("1"); 
                session.update(eo);
              } 
              session.beginTransaction().commit();
            } 
          } else {
            message = "选择的模版不正确！";
          } 
        } catch (Exception ex) {
          session.beginTransaction().rollback();
          ex.printStackTrace();
        } finally {
          session.close();
        } 
      } 
    } else if (type.startsWith("rsxc")) {
      realPath = httpServletRequest.getRealPath("uploadtemplate/" + System.currentTimeMillis() + ".xls");
      uploadFile(uploadForm.getFile(), realPath);
      try {
        String year = type.split("-")[1];
        String month = type.split("-")[2];
        FSalaryRsNewEJBBean bean = new FSalaryRsNewEJBBean();
        Map map = bean.importSalaryFormExcel(httpServletRequest, realPath, year, month);
        succeed = map.get("succeed").toString();
        message = map.get("message").toString();
        messageString = (String)map.get("messageString");
        message = String.valueOf(message) + messageString;
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } else if ("17".equals(type)) {
      realPath = httpServletRequest.getRealPath("uploadtemplate/archivestemplate.xls");
      uploadFile(uploadForm.getFile(), realPath);
      int rows = 0;
      String succed_archives = "";
      try {
        FileInputStream fileInputStream = new FileInputStream(new File(realPath));
        try {
          this.workbook = Workbook.getWorkbook(fileInputStream);
        } catch (Exception e) {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
          httpServletRequest.setAttribute("succeed", succeed);
          httpServletRequest.setAttribute("message", message);
          return actionMapping.findForward("input");
        } 
        this.sheet = this.workbook.getSheet(0);
        if (this.sheet == null) {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
          httpServletRequest.setAttribute("succeed", succeed);
          httpServletRequest.setAttribute("message", message);
          return actionMapping.findForward("input");
        } 
        rows = this.sheet.getRows();
        if (rows < 4) {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } 
        if (rows == 4) {
          message = String.valueOf(message) + "导入的数据为空！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } 
        String arvhivesClass = this.sheet.getCell(0, 2).getContents().trim();
        if ("档案类型".equals(arvhivesClass)) {
          ArchivesFilePO archivesFilePO = null;
          for (int i = 4; i < rows; i++) {
            succed_archives = "0";
            String archivesType = this.sheet.getCell(0, i).getContents().trim();
            String dossierName = this.sheet.getCell(1, i).getContents().trim();
            String dossierNO = this.sheet.getCell(2, i).getContents().trim();
            String serialNO = this.sheet.getCell(3, i).getContents().trim();
            String registrNO = this.sheet.getCell(4, i).getContents().trim();
            String microNO = this.sheet.getCell(5, i).getContents().trim();
            String model = this.sheet.getCell(6, i).getContents().trim();
            String archiveCode = this.sheet.getCell(7, i).getContents().trim();
            String fileNo = this.sheet.getCell(8, i).getContents().trim();
            String fileName = this.sheet.getCell(9, i).getContents().trim();
            String fileKey = this.sheet.getCell(10, i).getContents().trim();
            String createdEmp = this.sheet.getCell(11, i).getContents().trim();
            String principalName = this.sheet.getCell(12, i).getContents().trim();
            String duty = this.sheet.getCell(13, i).getContents().trim();
            String attendEmpName = this.sheet.getCell(14, i).getContents().trim();
            String pigeonholeOrgName = this.sheet.getCell(15, i).getContents().trim();
            String secretLevel = this.sheet.getCell(16, i).getContents().trim();
            String saveStyle = this.sheet.getCell(17, i).getContents().trim();
            String saveBeginTime = this.sheet.getCell(18, i).getContents().trim();
            String saveEndTime = this.sheet.getCell(19, i).getContents().trim();
            String volume = this.sheet.getCell(20, i).getContents().trim();
            String copyCount = this.sheet.getCell(21, i).getContents().trim();
            String pageCount = this.sheet.getCell(22, i).getContents().trim();
            String totalLength = this.sheet.getCell(23, i).getContents().trim();
            String drawingNO = this.sheet.getCell(24, i).getContents().trim();
            String specPage = this.sheet.getCell(25, i).getContents().trim();
            String achievePhase = this.sheet.getCell(26, i).getContents().trim();
            String itemClass = this.sheet.getCell(27, i).getContents().trim();
            String cooperateUnits = this.sheet.getCell(28, i).getContents().trim();
            String appraisalUnit = this.sheet.getCell(29, i).getContents().trim();
            String appraisalDate = this.sheet.getCell(30, i).getContents().trim();
            String patentNO = this.sheet.getCell(31, i).getContents().trim();
            String approveDate = this.sheet.getCell(32, i).getContents().trim();
            String awardUnit = this.sheet.getCell(33, i).getContents().trim();
            String awardDate = this.sheet.getCell(34, i).getContents().trim();
            String hortationLevel = this.sheet.getCell(35, i).getContents().trim();
            String fileRemark = this.sheet.getCell(36, i).getContents().trim();
            String merit = this.sheet.getCell(37, i).getContents().trim();
            String technicData = this.sheet.getCell(38, i).getContents().trim();
            String reachLevel = this.sheet.getCell(39, i).getContents().trim();
            String classReadName = this.sheet.getCell(40, i).getContents().trim();
            String pigeonholedate = this.sheet.getCell(41, i).getContents().trim();
            ArchivesBD archivesBD = new ArchivesBD();
            UserBD userBD = new UserBD();
            OrganizationBD organizationBD = new OrganizationBD();
            String sql = "";
            List<E> list = null;
            Session session = null;
            HibernateBase hBase = new HibernateBase();
            boolean key = true;
            boolean isRepeated = false;
            if (!"".equals(dossierNO)) {
              try {
                session = hBase.getSession();
                session.beginTransaction();
                sql = "select afo.fileId from com.js.oa.archives.po.ArchivesFilePO afo where afo.dossierNO = '" + 
                  dossierNO + "'";
                list = session.createQuery(sql).list();
                if (list.size() != 0) {
                  if (list.size() > 1) {
                    if ("2".equals(savetype)) {
                      message = String.valueOf(message) + "第" + (i + 1) + "行数据库中有多条相同数据，覆盖失败 ！<br>";
                      if (!"1".equals(succed_archives))
                        succed_archives = "1"; 
                      if ("0".equals(succeed) && "1".equals(succed_archives))
                        succeed = succed_archives; 
                    } 
                    session.close();
                  } else {
                    String fileId = list.get(0).toString();
                    archivesFilePO = archivesBD.loadArchivesFilePO(Long.valueOf(Long.parseLong(fileId)));
                    isRepeated = true;
                    session.close();
                  } 
                  continue;
                } 
              } catch (Exception ex) {
                session.beginTransaction().rollback();
                ex.printStackTrace();
              } finally {
                session.close();
              } 
            } else {
              message = String.valueOf(message) + "第" + (i + 1) + "行【档案号】为空 ！<br>";
              if (!"1".equals(succed_archives))
                succed_archives = "1"; 
              if ("0".equals(succeed) && "1".equals(succed_archives))
                succeed = succed_archives; 
              continue;
            } 
            session.close();
          } 
        } else {
          message = String.valueOf(message) + "选择的模版不正确！<br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } 
      } catch (Exception e) {
        e.printStackTrace();
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
      } 
    } 
    if ("0".equals(succeed) && (
      "".equals(messageString) || messageString == null))
      message = "导入成功！"; 
    File file = new File(realPath);
    if (file.exists())
      file.delete(); 
    httpServletRequest.setAttribute("message", message);
    httpServletRequest.setAttribute("newType", type);
    httpServletRequest.setAttribute("succeed", succeed);
    return actionMapping.findForward("input");
  }
  
  private int getWeixinUserNum() {
    int num = 0;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select count(emp_id) from org_employee where weixinpost=1 and userisdeleted=0");
      if (rs.next())
        num = rs.getInt(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return num;
  }
  
  private String getDutyLevel(String dutyName, String domainId) throws Exception {
    String ret = "1000";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select dutylevel from oa_duty po where po.dutyName='" + dutyName + 
          "' and po.domain_Id=" + domainId);
      if (rs.next())
        ret = rs.getString("dutylevel"); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return ret;
  }
  
  private Date getDateByString(String in) {
    Date date = new Date();
    if (!"".equals(in) && in != null)
      date = new Date(in.replace("-", "/")); 
    return date;
  }
  
  public static void uploadFile(FormFile file, String dir) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(file.getInputStream());
      File file1 = new File(dir);
      if (file1.exists())
        file1.delete(); 
      out = new BufferedOutputStream(new FileOutputStream(file1));
      byte[] buffered = new byte[8192];
      int size = 0;
      while ((size = in.read(buffered, 0, 8192)) != -1)
        out.write(buffered, 0, size); 
      out.flush();
      in.close();
      out.close();
    } catch (Exception e) {
      in.close();
      out.close();
    } 
  }
}
