package com.active.e_uc.user.action;

import com.active.e_uc.user.po.TblDepartment;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.active.e_uc.user.service.TblUserBD;
import com.buguniao.TransBuguniao;
import com.js.message.lava.GKSync;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.CreateString;
import com.js.util.util.MD5;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import rtx.RTXSync;

public class InfoPhaseAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    httpServletResponse.setContentType("text/plain;charset=GBK");
    String type = request.getParameter("type");
    if ("iactive".equals(type)) {
      TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
      OrganizationBD organizationBD = new OrganizationBD();
      OrganizationVO organizationVO = new OrganizationVO();
      OrganizationVO oldOrganizationVO = new OrganizationVO();
      TblDepartment tblDepartment = new TblDepartment();
      TblUserBD tblUserBD = new TblUserBD();
      TblUser tblUser = new TblUser();
      UserBD userBD = new UserBD();
      EmployeeVO employeeVO = new EmployeeVO();
      String isempty = request.getParameter("isempty");
      if ("js".equals(isempty)) {
        List<TblDepartment> tblDepartmentList = tblDepartmentBD
          .selectAllDepartment();
        String orgSerialold = "";
        String orgSerial = "";
        String currentOrderCode = "";
        String parentIdString = "";
        List<String> organizationOrgSerialList = organizationBD
          .selectAllOrgSerial();
        for (int i = 0; i < tblDepartmentList.size(); i++) {
          tblDepartment = tblDepartmentList.get(i);
          if (!organizationOrgSerialList.contains(tblDepartment.getUrl())) {
            organizationVO.setOrgName(tblDepartment.getName());
            if (tblDepartment.getPid() != 0) {
              orgSerialold = tblDepartmentBD.findURL(tblDepartment
                  .getPid());
              oldOrganizationVO = organizationBD
                .getOrgBySerial(orgSerialold);
              organizationVO.setOrgParentOrgId(oldOrganizationVO
                  .getOrgId().longValue());
              parentIdString = oldOrganizationVO.getOrgIdString();
              organizationVO.setOrgType("1");
            } else {
              organizationVO.setOrgParentOrgId(0L);
              organizationVO.setOrgType("0");
            } 
            currentOrderCode = organizationBD
              .getMaxOrgOrderCode(organizationVO
                .getOrgParentOrgId());
            organizationVO.setDomainId("0");
            orgSerial = CreateString.randomString(5);
            organizationVO.setOrgSerial(orgSerial);
            tblDepartment.setUrl(orgSerial);
            tblDepartmentBD.updateDepartment(tblDepartment);
            organizationBD.activeAdd(organizationVO, currentOrderCode, 
                parentIdString, Integer.valueOf(1));
          } 
        } 
        List<TblUser> tblUserList = tblUserBD.selectAllUser();
        List<String> UserAccountsList = userBD.selectAllUserAccounts();
        for (int j = 0; j < tblUserList.size(); j++) {
          tblUser = tblUserList.get(j);
          if (!UserAccountsList.contains(tblUser.getUserName())) {
            employeeVO.setUserAccounts(tblUser.getUserName());
            employeeVO.setUserSimpleName(tblUser.getUserName());
            employeeVO.setUserPassword((new MD5()).getMD5Code(tblUser
                  .getPassWord()));
            employeeVO.setEmpName(tblUser.getNickName());
            employeeVO.setEmpSex((byte)0);
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
            employeeVO.setNetDiskSize("100");
            employeeVO.setEmpDutyLevel("1000");
            if (tblUser.getDeptId() != 0) {
              String urls = tblDepartmentBD.findURL(tblUser
                  .getDeptId());
              oldOrganizationVO = organizationBD.getOrgBySerial(urls);
              userBD.activeAdd(employeeVO, oldOrganizationVO
                  .getOrgId());
            } 
          } 
        } 
      } else {
        List<OrganizationVO> organizationVOList = organizationBD
          .selectAllOrganization();
        List<String> tblDepartmentUrlList = tblDepartmentBD.selectAllUrl();
        for (int i = 0; i < organizationVOList.size(); i++) {
          organizationVO = organizationVOList.get(i);
          if (!tblDepartmentUrlList.contains(organizationVO
              .getOrgSerial())) {
            int b = (int)organizationVO.getOrgParentOrgId();
            if (b != 0) {
              String sa = organizationBD.findOrgSerial(b);
              tblDepartment.setPid(tblDepartmentBD.findID(sa));
            } else {
              tblDepartment.setPid(0);
            } 
            tblDepartment.setName(organizationVO.getOrgName());
            tblDepartment.setOrgid(1);
            tblDepartment.setGrade(0);
            tblDepartment.setUrl(organizationVO.getOrgSerial());
            tblDepartment.setShowChildUser((byte)1);
            try {
              tblDepartmentBD.addDepartment(tblDepartment);
            } catch (HibernateException e) {
              e.printStackTrace();
            } 
          } 
        } 
        List<EmployeeVO> employeeVOList = userBD.selectAllEmployee();
        List<String> TblUserNameList = tblUserBD.selectAllUserName();
        for (int j = 0; j < employeeVOList.size(); j++) {
          employeeVO = employeeVOList.get(j);
          if (!TblUserNameList.contains(employeeVO.getUserAccounts())) {
            tblUser.setUserName(employeeVO.getUserAccounts());
            tblUser.setPassWord("123456");
            tblUser.setType(4);
            tblUser.setIsPrimaryAdmin((byte)0);
            tblUser.setOrgId(1);
            tblUser.setAid(1);
            tblUser.setIsValid((byte)1);
            SimpleDateFormat si = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
            tblUser.setStartValidDate(si.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 5);
            tblUser.setEndValidDate(si.format(calendar.getTime()));
            tblUser.setNickName(employeeVO.getEmpName());
            tblUser.setSex(employeeVO.getEmpSex());
            tblUser.setMailaddr("");
            tblUser.setTelephone("");
            tblUser.setMphone("");
            tblUser.setProtocolRcv((byte)0);
            tblUser.setProtocolSend((byte)0);
            tblUser.setVerifyHid((byte)0);
            tblUser.setTruename(employeeVO.getEmpName());
            tblUser.setOccupy(0);
            tblUser.setInterest(Double.valueOf(0.0D));
            tblUser.setSafeinfo(0);
            tblUser.setShengxiao((byte)0);
            tblUser.setBloodtype((byte)0);
            tblUser.setStar((byte)0);
            tblUser.setImageindex((short)1);
            Long sd = null;
            sd = userBD.getUserOrgId(Long.valueOf(employeeVO.getEmpId()));
            if (sd != null) {
              String sa = organizationBD.findOrgSerial(sd.longValue());
              int did = tblDepartmentBD.findID(sa);
              tblUser.setDeptId(did);
              tblUser.setGrade(0);
              tblUser.setAccountId(0);
              tblUser.setContinueService((byte)0);
              tblUser.setRole(0);
              tblUser.setDicOrder(0);
              tblUser.setTrolServerId(Integer.valueOf(0));
              tblUser.setTrolState(0);
              tblUser.setTrolIsOnline((byte)0);
              tblUser.setUserid(31914);
              try {
                if (1 == employeeVO.getUserIsActive()) {
                  tblUserBD.addTblUser(tblUser);
                } else {
                  tblUserBD.addTblUser1(tblUser);
                } 
              } catch (HibernateException e) {
                e.printStackTrace();
              } 
            } 
          } 
        } 
      } 
    } else if ("rtx".equals(type)) {
      System.out.println("enter rtx!");
      RTXSync rtxSync = new RTXSync();
      rtxSync.Sync();
    } else if ("jsim".equals(type)) {
      TransBuguniao trans = new TransBuguniao();
      trans.Sync();
      trans.transUser();
    } else if ("gk".equals(type)) {
      GKSync gkSync = new GKSync();
      boolean flag = gkSync.Sync();
    } else {
      System.out.println("enter type:" + type);
    } 
    try {
      PrintWriter out = httpServletResponse.getWriter();
      out.println("success");
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return null;
  }
}
